package com.xsx.blog.service.impl;

import com.xsx.blog.entity.Tags;
import com.xsx.blog.repository.TagsRepository;
import com.xsx.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:56
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public Tags findOne(Integer id) {
        return tagsRepository.findById(id).get();
    }

    @Override
    public boolean save(Tags tag) {
        return tagsRepository.save(tag).getId() != null;
    }

    @Override
    public Page<Tags> findPage(Integer pageNo,Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(pageNo,pageSize,sort);
        return tagsRepository.findAll(pageable);
    }

    @Override
    public boolean deleteOne(Integer id) {
        Tags tag = tagsRepository.findById(id).get();
        tag.setStatu(0);
        tagsRepository.save(tag);
        return true;
    }

    @Override
    public List<Tags> findByStatu(Integer statu) {
        return tagsRepository.findByStatuOrderByCreateTimeAsc(statu);
    }

}
