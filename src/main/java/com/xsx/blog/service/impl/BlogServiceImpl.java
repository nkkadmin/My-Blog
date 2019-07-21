package com.xsx.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.dto.BlogDTO;
import com.xsx.blog.mapper.BlogMapper;
import com.xsx.blog.mapper.MenuMapper;
import com.xsx.blog.mapper.TagsMapper;
import com.xsx.blog.model.Blog;
import com.xsx.blog.request.BlogEditRequest;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.CommentService;
import com.xsx.blog.service.LoggerService;
import com.xsx.blog.service.RedisService;
import com.xsx.blog.util.DateUtils;
import com.xsx.blog.vo.BlogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:56
 */
@Service
public class BlogServiceImpl extends LoggerService implements BlogService  {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisService redisService;

    @Override
    public Blog findOne(Integer id) {
        //从缓存获取
        Blog blog = redisService.getBlog(id);
        if(blog == null){
            logger.info("缓存里没有{}",id);
            blog = blogMapper.findById(id);
            //放入缓存
            redisService.addBlog(blog);
        }
        return blog;
    }

    @Override
    public Result saveOrUpdate(BlogEditRequest blogEditRequest) {
        Result result = check(blogEditRequest);
        if(!result.isSuccess()){
            return result;
        }
        Blog blog = coverBlog(blogEditRequest);
        if(blog != null && blog.getId() != null){
            update(result,blog);
        }else{
            save(result,blog);
        }
        return result;
    }

    /**
     * 新增
     * @param result
     * @param blog
     */
    private void save(Result result, Blog blog) {
        blog.setCreateTime(new Date());
        Integer saveResult = blogMapper.insert(blog);
        if(saveResult > 0){
            //写入缓存
            redisService.addBlog(blog);
        }
        result.setSuccess(saveResult > 0);
        result.setMsg("保存成功");
    }

    /**
     * 更新
     * @param result
     * @param blog
     */
    private void update(Result result, Blog blog) {
        Blog oldBlog = findOne(blog.getId());
        oldBlog.setTitle(blog.getTitle());
        oldBlog.setContent(blog.getContent());
        oldBlog.setMenuId(blog.getMenuId());
        oldBlog.setTagId(blog.getTagId());
        oldBlog.setCoverPic(blog.getCoverPic());
        oldBlog.setUpdateTime(new Date());
        Integer updateResult = blogMapper.update(oldBlog);
        if(updateResult > 0){
            //删除缓存
            redisService.delBlogById(oldBlog.getId());
        }
        result.setSuccess(updateResult > 0);
        result.setMsg("修改成功");
    }


    /**
     * 校验参数
     * @param request
     */
    private Result check(BlogEditRequest request) {
        Result result = new Result(false);
        if(StringUtils.isEmpty(request.getTitle())){
            result.setMsg("请输入标题");
            return result;
        }
        if(StringUtils.isEmpty(request.getMenuId())){
            result.setMsg("请选择所属菜单");
            return result;
        }
        if(StringUtils.isEmpty(request.getTagId())){
            result.setMsg("请选择标签");
            return result;
        }
        if(StringUtils.isEmpty(request.getContent())){
            result.setMsg("请填写博客内容");
            return result;
        }
        if(StringUtils.isEmpty(request.getCreateUserId())){
            result.setMsg("登录失效，请重新登录");
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    private Blog coverBlog(BlogEditRequest blogEditRequest) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogEditRequest,blog);
        blog.setMenuId(blogEditRequest.getMenuId());
        blog.setTagId(blogEditRequest.getTagId());
        return blog;
    }

    @Override
    public PageInfo<BlogVo> findPage(BlogSearchRequest blogSearchRequest) {
        PageHelper.startPage(blogSearchRequest.getPageNo(),blogSearchRequest.getPageSize());
        if(!StringUtils.isEmpty(blogSearchRequest.getBlogTitle())){
            blogSearchRequest.setBlogTitle("%"+blogSearchRequest.getBlogTitle()+"%");
        }
        List<BlogDTO> list = blogMapper.findAll(blogSearchRequest);
        PageInfo<BlogDTO> oldPage = new PageInfo<>(list);
        List<BlogVo> resultList = new ArrayList<>();
        for(BlogDTO blog : list){
            BlogVo blogVo = new BlogVo();
            String content = com.xsx.blog.util.StringUtils.delHTMLTag(blog.getContent());
            if(org.springframework.util.StringUtils.isEmpty(blog.getCoverPic())){
                String[] img = com.xsx.blog.util.StringUtils.getImgs(blog.getContent());
                if(img != null && img.length > 0){
                    blog.setCoverPic(img[0]);
                }
            }
            blog.setContent(content.length() >= 250 ? content.substring(0,250)+"..." : content);
            BeanUtils.copyProperties(blog,blogVo);
            blogVo.setMenuName(menuMapper.findById(blog.getMenuId()).getName());
            blogVo.setTagName(tagsMapper.findById(blog.getTagId()).getName());
            resultList.add(blogVo);
        }
        PageInfo<BlogVo> page = new PageInfo<>();
        BeanUtils.copyProperties(oldPage,page);
        page.setList(resultList);
        return page;
    }

    @Override
    public Boolean deleteOne(Integer id) {
        Blog blog = blogMapper.findById(id);
        if(blog == null)
            return true;
        blog.setStatu(0);
        blogMapper.update(blog);
        return true;
    }

    @Override
    public Integer count() {
        return blogMapper.count();
    }

    @Override
    public Integer validCount() {
        return blogMapper.validCount();
    }

    @Override
    public int dianzan(Integer id) {
        Blog blog = blogMapper.findById(id);
        if(blog == null){
            return -1;
        }
        blog.setZanNum(blog.increaseZanNum());
        int num = blogMapper.update(blog);
        if(num > 0)
            return blog.getZanNum();
        return -1;
    }

    @Override
    public Integer batchUpdateBlog(List<BlogDTO> blogList) {
        if(CollectionUtils.isEmpty(blogList))
            return 0;
        return blogMapper.batchUpdateBlog(blogList);
    }

    @Override
    public List<String> getAllYear() {
        return blogMapper.groupByCreateTime();
    }


}
