package com.xsx.blog.blog;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.Constants;
import com.xsx.blog.model.Blog;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.RedisService;
import com.xsx.blog.util.RedisUtils;
import com.xsx.blog.vo.BlogVo;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * @Description:
 * @Date: 2019-02-27 22:30
 * @Auther: xieshengxiang
 */
public class RedisTest extends BlogApplicationTests {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogService blogService;

    @Test
    public void testFind(){
        Blog blog = new Blog();
        blog.setId(1);
        blog.setTitle("测试博客哦哦哦");
        blog.setContent("阿斯蒂芬胜多负少的股份电饭锅电饭锅");
        redisUtils.hmset("blogTest",blog.getId(),blog);
        Blog blog2 = new Blog();
        blog2.setId(2);
        blog2.setTitle("电饭锅对方过后");
        blog2.setContent("梵蒂冈好玩儿的非官方的个和法国恢复规划");
        redisUtils.hmset("blogTest",blog2.getId(),blog2);


    }


    @Test
    public void initBlog(){
        redisService.initBlog();
        System.out.println(redisUtils.sortSetSize(Constants.REDIS_BLOG_LIST));
        PageInfo<BlogVo> pageInfo = redisService.finPage(0,10);
        System.out.println(pageInfo);
      /*  List<BlogVo> result = new ArrayList();
        Set<Long> sets = redisUtils.range(Constants.REDIS_BLOG_LIST,0,10);
        for(Iterator it = sets.iterator();it.hasNext();){
            Long blogId = Long.parseLong(it.next().toString());
            BlogVo blogVo = (BlogVo)redisUtils.hmget(Constants.REDIS_BLOG_INFO,blogId);
            result.add(blogVo);
        }
        System.out.println(result.size());*/
        //删除blogId=4
      /*  redisService.delBlogById(4);
        Blog blog = blogService.findOne(4);
        BlogVo blogVo = new BlogVo();
        BeanUtils.copyProperties(blog,blogVo);
        redisService.addBlog(blogVo);*/
    }

    @Test
    public void rangTest(){
        Random random = new Random();
       /* for (int i = 1; i <= 10; i += 1) {
            redisUtils.zadd("blog","blog"+i,Double.valueOf(i));
        }
*/

        Set set = redisUtils.gzet("blogList",1,5);
        for(Iterator it = set.iterator();it.hasNext();){
            System.out.println(it.next());
        }
    }

    @Test
    public void setTest(){
        redisUtils.set("testPro","1234",1*60l);
        System.out.println(redisUtils.get("testPro"));
       /* System.out.println(redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG));
        redisUtils.del(Constants.REDIS_DIANZAN_MAP_FLAG);
        System.out.println(redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG));*/
    }

    @Test
    public void setCode(){
        redisUtils.set(Constants.SNED_CODE_REDIS_FLAG,"1234",1*60l);
        System.out.println(redisUtils.get(Constants.SNED_CODE_REDIS_FLAG));
        System.out.println(redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG));
        redisUtils.del(Constants.REDIS_DIANZAN_MAP_FLAG);
        System.out.println(redisUtils.hmget(Constants.REDIS_DIANZAN_MAP_FLAG));
    }



}


