package com.xsx.blog.quart;

import com.xsx.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 定时器
 * @Date: 2019-02-28 21:08
 * @Auther: xieshengxiang
 */
@Component
public class QuartSynRedis {

    @Autowired
    private RedisService redisService;

    /**
     * 将redis中点赞量同步到数据库
     *  30分钟执行一次
     */
    @Scheduled(cron = "1 0/30 * * * ?")
    public void synRedisToDB(){
        System.out.println("开始同步点赞量");
        redisService.synDianzanRedisToDB();
        System.out.println("结束同步点赞量");

        System.out.println("开始同步浏览量");
        redisService.synLookNumRedisToDB();
        System.out.println("结束同步浏览量");
    }


}
