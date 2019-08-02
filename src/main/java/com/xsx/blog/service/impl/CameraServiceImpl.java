package com.xsx.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.dto.CamerasDTO;
import com.xsx.blog.mapper.CamerasMapper;
import com.xsx.blog.mapper.ImagesMapper;
import com.xsx.blog.model.Cameras;
import com.xsx.blog.model.Images;
import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.CameraIndexResult;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.CameraService;
import com.xsx.blog.util.PingYinUtil;
import com.xsx.blog.vo.AdminCameraVO;
import com.xsx.blog.vo.CameraVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/29 20:00
 */
@Service
public class CameraServiceImpl implements CameraService {

    private final static Logger logger = LoggerFactory.getLogger(CameraServiceImpl.class);

    @Autowired
    private CamerasMapper camerasMapper;
    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public PageInfo<CameraVO> findPage(CamerasRequest request) {
        PageHelper.startPage(request.getPageNo(),request.getPageSize());
        if(!StringUtils.isEmpty(request.getTags())){
            request.setTags("%"+request.getTags()+"%");
        }
        List<CamerasDTO> list = camerasMapper.findAll(request);
        PageInfo<CamerasDTO> oldPage = new PageInfo<>(list);
        List<CameraVO> resultList = new ArrayList<>();
        for(CamerasDTO camera : list){
            CameraVO cameraVO = new CameraVO();
            BeanUtils.copyProperties(camera,cameraVO);
            resultList.add(cameraVO);
        }
        PageInfo<CameraVO> page = new PageInfo<>();
        BeanUtils.copyProperties(oldPage,page);
        page.setList(resultList);
        return page;
    }

    @Override
    public Result save(CamerasRequest camerasRequest) {
        Result result = checkParams(camerasRequest);
        if(!result.isSuccess()){
            return result;
        }

        Integer id = camerasRequest.getId();
        if(id == null){
           insertCamera(result,camerasRequest);
        }else{
            updateCamera(result,camerasRequest);
        }

        return result;
    }

    private void updateCamera(Result result, CamerasRequest camerasRequest) {
        Cameras cameras = camerasMapper.selectByPrimaryKey(camerasRequest.getId());
        cameras.setTitle(camerasRequest.getTitle());
        cameras.setTags(camerasRequest.getTitle());
        int flag = camerasMapper.updateByPrimaryKey(cameras);
        if(flag > 0){
            logger.info("id:{}基本信息修改成功",camerasRequest.getId());
            List<CamerasRequest.OperImags> addOrDelItems = camerasRequest.getAddOrDelImgs();
            for(CamerasRequest.OperImags operImag : addOrDelItems){
                Images images = new Images();
                images.setId(operImag.getId());
                if(CamerasRequest.OperImagType.isAdd(operImag.getType())){
                    images.setCarmeraId(camerasRequest.getId());
                    images.setUrl(operImag.getUrl());
                    images.setCover(operImag.getCover());
                    imagesMapper.insertSelective(images);
                }else if(CamerasRequest.OperImagType.isRemove(operImag.getType())){
                    images.setStatu(StatuEnum.DELETE.getStatu());
                    imagesMapper.updateByPrimaryKeySelective(images);
                }
            }
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
    }

    private Result insertCamera(Result result,CamerasRequest camerasRequest) {
        //新增基本信息
        Cameras cameras = new Cameras();
        coverBaseModel(camerasRequest,cameras);
        int flag = camerasMapper.insertSelective(cameras);
        if(flag >= 1){
            //新增相册信息
            List<Images> imagesList = buildsImageModel(camerasRequest,cameras.getId());
            for(Images image : imagesList){
                imagesMapper.insertSelective(image);
            }
        }
        result.setSuccess(flag >= 1);
        result.setMsg(result.isSuccess() ? "编辑成功" : "编辑失败");
        return result;
    }

    @Override
    public CameraIndexResult findPageIndex(CamerasRequest request) {
        request.setStatu(StatuEnum.OK.getStatu());
        PageInfo<CameraVO> page = findPage(request);
        CameraIndexResult result = coverIndexResult(page);
        return result;
    }

    @Override
    public Integer validCount() {
        return camerasMapper.validCount();
    }

    @Override
    public AdminCameraVO queryById(Integer id) {
        Cameras cameras = camerasMapper.selectByPrimaryKey(id);
        AdminCameraVO adminCameraVO = new AdminCameraVO();
        BeanUtils.copyProperties(cameras,adminCameraVO);
        adminCameraVO.setImagesList(imagesMapper.findByCamId(adminCameraVO.getId()));
        return adminCameraVO;
    }

    private CameraIndexResult coverIndexResult(PageInfo<CameraVO> page) {
        CameraIndexResult result = new CameraIndexResult();
        List<CameraIndexResult.CameraIndexVo> voList = new ArrayList<>();
        result.setCameraIndexVos(voList);
        TreeSet<CameraIndexResult.TagsVo> allInTags = new TreeSet<>();
        for(CameraVO cameraVO : page.getList()){
            CameraIndexResult.CameraIndexVo indexVo = new CameraIndexResult.CameraIndexVo();
            buildCameraIndeVo(indexVo,cameraVO);
            voList.add(indexVo);
            buildAllInTagVo(allInTags,cameraVO);
        }
        result.setAllInTags(allInTags);
        buildResultPageInfo(page,result);
        return result;
    }

    private void buildResultPageInfo(PageInfo<CameraVO> page, CameraIndexResult result) {
        result.setPageNo(page.getPageNum());
        result.setPages(page.getPages());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
    }

    private void buildCameraIndeVo(CameraIndexResult.CameraIndexVo indexVo,CameraVO cameraVO) {
        BeanUtils.copyProperties(cameraVO,indexVo);
        indexVo.setEnTags(buildEnTags(cameraVO.getTags()));
    }

    private String buildEnTags(String tags) {
        StringBuffer sb = new StringBuffer();
        String[] strings = tags.split(",");
        for(String tag : strings){
            sb.append(PingYinUtil.getPingYin(tag)).append(" ");
        }
        return sb.toString();
    }


    private void buildAllInTagVo(TreeSet<CameraIndexResult.TagsVo> allInTags,CameraVO cameraVO) {
        String[] split = cameraVO.getTags().split(",");
        for (String tag : split){
            CameraIndexResult.TagsVo tagVo = new CameraIndexResult.TagsVo();
            tagVo.setCnTag(tag.trim());
            tagVo.setEnTag(PingYinUtil.getPingYin(tag.trim()));
            tagVo.setTagsId(allInTags.size()+1);
            allInTags.add(tagVo);
        }
    }


    private List<Images> buildsImageModel(CamerasRequest camerasRequest, Integer id) {
        for(Images image : camerasRequest.getImagesList()){
            image.setCarmeraId(id);
        }
        return camerasRequest.getImagesList();
    }

    private void coverBaseModel(CamerasRequest camerasRequest, Cameras cameras) {
        BeanUtils.copyProperties(camerasRequest,cameras);
    }


    /**
     * 参数校验
     * @param request
     * @return
     */
    private Result checkParams(CamerasRequest request) {
        Result result = new Result(false);
        if(StringUtils.isEmpty(request.getTitle())){
            result.setMsg("标题不能为空");
            return result;
        }
        if(StringUtils.isEmpty(request.getTags())){
            result.setMsg("标签不能为空");
            return result;
        }

        if(CollectionUtils.isEmpty(request.getImagesList())){
            result.setMsg("请上传图片");
            return result;
        }
        result.setSuccess(true);
        return result;
    }
}
