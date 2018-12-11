package com.xsx.blog.repository;

import com.xsx.blog.entity.Tags;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:53
 */
public interface TagsRepository extends PagingAndSortingRepository<Tags,Integer> {
    
}
