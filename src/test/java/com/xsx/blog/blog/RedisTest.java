package com.xsx.blog.blog;

import com.xsx.blog.common.Constants;
import com.xsx.blog.util.RedisUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description:
 * @Date: 2019-02-27 22:30
 * @Auther: xieshengxiang
 */
public class RedisTest extends BlogApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void set(){
        redisUtils.set(Constants.SNED_CODE_REDIS_FLAG,"1234",1*60l);
        System.out.println(redisUtils.get(Constants.SNED_CODE_REDIS_FLAG));
        System.out.println(redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG));
        redisUtils.del(Constants.REDIS_DIANZAN_MAP_FLAG);
        System.out.println(redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG));
    }

}


