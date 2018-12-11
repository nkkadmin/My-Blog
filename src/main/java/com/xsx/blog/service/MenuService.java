package com.xsx.blog.service;

import com.xsx.blog.entity.Menu;
import org.springframework.data.domain.Page;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:55
 */
public interface MenuService {

    public Menu findOne(Integer id);

    public Boolean save(Menu menu);

    public Page<Menu> findPage(Integer pageNo,Integer pageSize);

    public Boolean deleteOne(Integer id);
}
