package com.xsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.xsx.blog.common.Constants;
import com.xsx.blog.dto.BlogDTO;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.RedisService;
import com.xsx.blog.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
