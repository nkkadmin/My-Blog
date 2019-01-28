package com.xsx.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.mapper.TagsMapper;
import com.xsx.blog.model.Tags;
import com.xsx.blog.request.TagRequest;
import com.xsx.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TagsMapper tagsMapper;

    @Override
    public Tags findOne(Integer id) {
        return tagsMapper.findById(id);
    }

    @Override
    public boolean save(Tags tag) {
        tag.setStatu(StatuEnum.OK.getStatu());
        return tagsMapper.insert(tag) > 0;
    }

    @Override
    public PageInfo<Tags> findPage(TagRequest tagRequest) {
        PageHelper.startPage(tagRequest.startPage(),tagRequest.getPageSize());
        List<Tags> tags = tagsMapper.findAll();
        return new PageInfo<>(tags);
    }

    @Override
    public boolean deleteOne(Integer id) {
        Tags tag = tagsMapper.findById(id);
        if(tag == null)
            return true;
        tag.setStatu(0);
        Integer num = tagsMapper.update(tag);
        return num > 0;
    }

    @Override
    public List<Tags> findByStatu(Integer statu) {
        return tagsMapper.findByStatuOrderByCreateTimeAsc(statu);
    }

}
