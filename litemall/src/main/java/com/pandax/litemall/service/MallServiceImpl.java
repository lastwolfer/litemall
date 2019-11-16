package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.bean.Region;
import com.pandax.litemall.mapper.BrandMapper;
import com.pandax.litemall.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MallServiceImpl implements MallService {
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    BrandMapper brandMapper;

    @Override
    public Region[] region() {
        Region[] regions = regionMapper.setlectAllRegion();
        for (Region region : regions) {
            for (Region child : region.getChildren()) {
                Region[] regions1 = regionMapper.selectByPid(child.getId());
                child.setChildren(regions1);
                System.out.println(Arrays.toString(regions1));
            }
        }
        return regions;
    }

    @Override
    public HashMap<String, Object> brand(Integer page, Integer limit, String sort, String order, Integer id, String name) {
        PageHelper.startPage(page, limit);
        HashMap<String, Object> map = new HashMap<>();

        List<Brand> brands = null;
        brands = brandMapper.selectAllBrands(sort, order,id,name);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        long total = brandPageInfo.getTotal();
        map.put("total", total);
        map.put("items", brands);
        return map;
    }

    @Override
    public Brand brandUpdate(Brand newBrand) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*String time = format.format(new Date());*/
        newBrand.setUpdateTime(new Date());
        brandMapper.updateByExample(newBrand);
        Brand brand = brandMapper.selectByPrimaryKey(newBrand.getId());
        return brand;
    }

    @Override
    public void brandDelete(Brand newBrand) {
        brandMapper.deleteByPrimaryKey(newBrand.getId());
    }

    @Override
    public Brand brandCreate(Brand newBrand) {
        newBrand.setAddTime(new Date());
        newBrand.setUpdateTime(new Date());
        brandMapper.insert(newBrand);
        Brand brand = brandMapper.selectByName(newBrand.getName());
        return brand;
    }

}
