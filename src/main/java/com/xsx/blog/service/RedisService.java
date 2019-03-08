package com.xsx.blog.service;

/**
 * @Description:
 * @Date: 2019-02-27 22:46
 * @Auther: xieshengxiang
 */
public interface RedisService {

    /**
     * 将点赞量写入缓存
     * @param blogId
     * @return
     */
    public boolean addDianzan(Integer blogId);

    /**
     * 将缓存点赞量同步到数据库中
     */
    public void synDianzanRedisToDB();

    /**
     * 将浏览量写入缓存
     * @param blogId
     * @return
     */
    public boolean addLook(Integer blogId);

    /**
     * 将缓存浏览量同步到数据库中
     */
    public void synLookNumRedisToDB();
}
