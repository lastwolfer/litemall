package com.pandax.litemall.controller;

import com.pandax.litemall.bean.Ad;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
@RestController
public class PromotionController {
    @Autowired
    PromotionService promotionService;

    @RequestMapping("admin/ad/list")
    public BaseReqVo listAd(Integer page, Integer limit, Ad ad){
        Map<String, Object> map = promotionService.selectByExample(page, limit,ad);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(map!=null){
            baseReqVo.setData(map);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/ad/create")
    public BaseReqVo createAd(@RequestBody Ad record){
        //Date date = new SimpleDateFormat("yyyy-HH-dd hh:MM:ss").format(new Date());
        record.setAddTime(new Date());
        record.setUpdateTime(new Date());
        int insert = promotionService.insertSelective(record);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(insert == 1){
            baseReqVo.setData(record);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }
//
//    @RequestMapping("admin/read")
//    public BaseReqVo readeAd(Integer page,Integer limit,@RequestBody Ad ad){
//        Map<String,Object> map = promotionService.selectAdsByNameOrContent(page,limit,ad);
//        BaseReqVo baseReqVo = new BaseReqVo();
//        if(map!=null){
//            baseReqVo.setData(map);
//            baseReqVo.setErrmsg("成功");
//            baseReqVo.setErrno(0);
//            return baseReqVo;
//        }
//        baseReqVo.setErrmsg("失败");
//        baseReqVo.setErrno(-1);
//        return baseReqVo;
//    }

    @RequestMapping("admin/ad/delete")
    public BaseReqVo deleteAd(@RequestBody Ad record){
        int delete = promotionService.deleteByPrimaryKey(record.getId());
        BaseReqVo baseReqVo = new BaseReqVo();
        if(delete == 1){
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

}
