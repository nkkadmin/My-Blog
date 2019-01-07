package com.xsx.blog.repository;

import com.xsx.blog.entity.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:53
 */
public interface MenuRepository extends PagingAndSortingRepository<Menu,Integer> {

    /**
     * 获取有效的菜单
     * @param statu
     * @return
     */
    public List<Menu> findByStatuOrderBySortIndexAsc(Integer statu);
}
