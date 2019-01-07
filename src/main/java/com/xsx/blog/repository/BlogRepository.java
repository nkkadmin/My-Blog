package com.xsx.blog.repository;

import com.xsx.blog.entity.Blog;
import com.xsx.blog.entity.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 *
 */
public interface BlogRepository extends PagingAndSortingRepository<Blog,Integer>, JpaSpecificationExecutor {


}
