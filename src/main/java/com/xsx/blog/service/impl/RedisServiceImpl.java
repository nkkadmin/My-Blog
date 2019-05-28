package com.xsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.Constants;
import com.xsx.blog.dto.BlogDTO;
import com.xsx.blog.model.Blog;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.RedisService;
import com.xsx.blog.util.RedisUtils;
import com.xsx.blog.vo.BlogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description:
 * @Date: 2019-02-27 22:48
 * @Auther: xieshengxiang
 */
@Service
public class RedisServiceImpl implements RedisService {

    private final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BlogService blogService;

    @Override
    public PageInfo<BlogVo> finPage(Integer pageNo, Integer pageSize) {
        PageInfo<BlogVo> pageInfo = new PageInfo<>();
        List<BlogVo> resultList = new ArrayList();
        Set<Long> sets = redisUtils.range(Constants.REDIS_BLOG_LIST,pageNo,pageSize);
        BlogVo blogVo = null;
        for(Iterator it = sets.iterator();it.hasNext();){
            Long blogId = Long.parseLong(it.next().toString());
            Blog blog = (Blog)redisUtils.hmget(Constants.REDIS_BLOG_INFO,blogId);
            blogVo = new BlogVo();
            BeanUtils.copyProperties(blog,blogVo);
            resultList.add(blogVo);
        }
        pageInfo.setList(resultList);
        //获取总数量
        Long total = redisUtils.sortSetSize(Constants.REDIS_BLOG_LIST);
        pageInfo.setTotal(total);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNo);
        return pageInfo;
    }

    @Override
    public void initBlog() {

        Integer pageNo = 1;
        Integer pageSize = 10;
        BlogSearchRequest request = new BlogSearchRequest(pageNo,pageSize);
        request.setStatu(1);
        PageInfo<BlogVo> pageInfo = blogService.findPage(request);
        List<BlogVo> blogList = new ArrayList<BlogVo>(Integer.valueOf(pageInfo.getTotal()+"")); //封装全部list
        blogList.addAll(pageInfo.getList());
        Integer pageCount = pageInfo.getPages()-1;
        while(pageCount > 0){
            request.setPageNo(++pageNo);
            pageInfo = blogService.findPage(request);
            blogList.addAll(pageInfo.getList());
            pageCount--;
        }
        //写入redis
        blogList.stream().forEach(blogVo->{
            Blog blog = new Blog();
            BeanUtils.copyProperties(blogVo,blog);
            redisUtils.zadd(Constants.REDIS_BLOG_LIST,blog.getId(),Double.valueOf(blog.getCreateTime().getTime()));
            redisUtils.hmset(Constants.REDIS_BLOG_INFO,blog.getId(),blog);
        });
    }

    @Override
    public boolean addBlog(Blog blog) {
        try{
            redisUtils.zadd(Constants.REDIS_BLOG_LIST,blog.getId(),Double.valueOf(blog.getCreateTime().getTime()));
            redisUtils.hmset(Constants.REDIS_BLOG_INFO,blog.getId(),blog);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Blog getBlog(Integer id) {
        if(id == null)
            return null;
        Object object = redisUtils.hmget(Constants.REDIS_BLOG_INFO,id);
        if(object == null)
            return null;
        return (Blog)object;
    }

    @Override
    public boolean delBlogById(Integer... blogId) {
        try {
            redisUtils.zdel(Constants.REDIS_BLOG_LIST,blogId);
            redisUtils.hdel(Constants.REDIS_BLOG_INFO,blogId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addDianzan(Integer blogId) {
        if(blogId == null)
            return false;
        try{
            Map<Object,Object> redisMap = redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG);
            if(redisMap == null || redisMap.size() == 0){
                redisMap = new HashMap<>();
            }
            Object key = blogId;
            Integer value = 1;
            Object objNum = redisMap.get(key);
            if(objNum != null){
                value = Integer.valueOf(objNum.toString()) + 1;
            }
            redisMap.put(key,value);
            redisUtils.hmset(Constants.REDIS_DIANZAN_MAP_FLAG,redisMap);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void synDianzanRedisToDB() {
        Map<Object,Object> redisMap = getDianzanRedisMap();
        if(redisMap == null)
            return;
        List<BlogDTO> blogList = new ArrayList<BlogDTO>();
        buildBlogZanList(redisMap,blogList);
        //批量同步到数据库
        Integer resultNum = blogService.batchUpdateBlog(blogList);
        if(resultNum == blogList.size()){
            //说明更新成功
            //清空点赞redis
            clearDianRedis();
        }
    }

    private void clearDianRedis() {
        redisUtils.del(Constants.REDIS_DIANZAN_MAP_FLAG);
    }

    private void clearLookRedis() {
        redisUtils.del(Constants.REDIS_LOOK_MAP_FLAG);
    }

    private Map<Object,Object> getDianzanRedisMap(){
        Map<Object,Object> redisMap = redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG);
        logger.info("redis中点赞数据：{}", JSON.toJSONString(redisMap));
        return redisMap;
    }

    private Map<Object,Object> getLookRedisMap(){
        Map<Object,Object> redisMap = redisUtils.hmget(Constants.REDIS_LOOK_MAP_FLAG);
        logger.info("redis中浏览量数据：{}", JSON.toJSONString(redisMap));
        return redisMap;
    }

    private void buildBlogZanList(Map<Object,Object> redisMap,List<BlogDTO> blogIdList){

        Set set = redisMap.keySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Object key = it.next();
            Object value = redisMap.get(key);
            BlogDTO dianzanDTO = new BlogDTO();
            dianzanDTO.setId(Integer.valueOf(key.toString()));
            dianzanDTO.setZanNum(Integer.valueOf(value.toString()));
            blogIdList.add(dianzanDTO);
        }
    }

    @Override
    public boolean addLook(Integer blogId) {
        if(blogId == null)
            return false;
        try{
            Map<Object,Object> redisMap = redisUtils.hmget(Constants.REDIS_LOOK_MAP_FLAG);
            if(redisMap == null || redisMap.size() == 0){
                redisMap = new HashMap<>();
            }
            Object key = blogId;
            Integer value = 1;
            Object objNum = redisMap.get(key);
            if(objNum != null){
                value = Integer.valueOf(objNum.toString()) + 1;
            }
            redisMap.put(key,value);
            redisUtils.hmset(Constants.REDIS_LOOK_MAP_FLAG,redisMap);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void synLookNumRedisToDB() {
        Map<Object,Object> redisMap = getLookRedisMap();
        if(redisMap == null)
            return;
        List<BlogDTO> blogList = new ArrayList<BlogDTO>();
        buildBlogLookList(redisMap,blogList);
        //批量同步到数据库
        Integer resultNum = blogService.batchUpdateBlog(blogList);
        if(resultNum == blogList.size()){
            //说明更新成功
            //清空浏览量redis
            clearLookRedis();
        }
    }

    private void buildBlogLookList(Map<Object,Object> redisMap,List<BlogDTO> blogIdList){
        Set set = redisMap.keySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Object key = it.next();
            Object value = redisMap.get(key);
            BlogDTO dianzanDTO = new BlogDTO();
            dianzanDTO.setId(Integer.valueOf(key.toString()));
            dianzanDTO.setLookNum(Integer.valueOf(value.toString()));
            blogIdList.add(dianzanDTO);
        }
    }
}
