package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Ad;
import com.pandax.litemall.bean.AdExample;
import com.pandax.litemall.mapper.AdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    AdMapper adMapper;

    @Override
    public Map<String,Object> selectByExample(Integer page, Integer limit, Ad ad) {
        AdExample example = new AdExample();
        AdExample.Criteria criteria = example.createCriteria();
        if(ad.getName()!=null) {
            criteria.andNameLike("%" + ad.getName() + "%");
        }
        if(ad.getContent()!=null){
            criteria.andContentLike("%" + ad.getContent() + "%");
        }
        List<Ad> ads = adMapper.selectByExample(example);
        //完成分页的查询
        PageHelper.startPage(page,limit);
        PageInfo<Ad> adPageInfo = new PageInfo<>(ads);
        long total = adPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",ads);
        return map;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
         int delete = adMapper.deleteByPrimaryKey(id);
         return delete;
    }

    @Override
    public int insertSelective(Ad record) {
        int insert = adMapper.insertSelective(record);
        return insert;
    }
}
