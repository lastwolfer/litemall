package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.bean.Region;
import com.pandax.litemall.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class MallController {
    @Autowired
    MallService mallService;

    @RequestMapping("admin/region/list")
    public BaseReqVo region() {
        Region[] region = mallService.region();
        BaseReqVo<Region[]> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(region);
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/list")
    public BaseReqVo brandList(Integer page, Integer limit, String sort, String order,Integer id,String name) {

        System.out.println(page);
        System.out.println(limit);
        System.out.println(sort);
        System.out.println(order);
        System.out.println("id="+id);
        System.out.println("name="+name);

        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = mallService.brand(page,limit,sort,order,id,name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/brand/update")
    public BaseReqVo brandUpdate(@RequestBody Brand newBrand) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Brand brand = mallService.brandUpdate(newBrand);
        baseReqVo.setData(brand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/brand/delete")
    public BaseReqVo brandDelete(@RequestBody Brand newBrand) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        mallService.brandDelete(newBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/brand/create")
    public BaseReqVo brandCreate(@RequestBody Brand newBrand) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Brand brand = mallService.brandCreate(newBrand);
        baseReqVo.setData(brand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
