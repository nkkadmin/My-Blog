package com.xsx.blog.service.impl;

import com.xsx.blog.entity.Menu;
import com.xsx.blog.repository.MenuRepository;
import com.xsx.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:56
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu findOne(Integer id) {
        return menuRepository.findById(id).get();
    }

    @Override
    public Boolean save(Menu menu) {
        return menuRepository.save(menu).getId() != null;
    }

    @Override
    public Page<Menu> findPage(Integer pageNo,Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(pageNo,pageSize,sort);
        Page<Menu> page = menuRepository.findAll(pageable);
        return page;
    }

    @Override
    public Boolean deleteOne(Integer id) {
        Menu menu = menuRepository.findById(id).get();
        menu.setStatu(0);
        Menu m = menuRepository.save(menu);
        return true;
    }

}
