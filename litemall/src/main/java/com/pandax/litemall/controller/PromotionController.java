package com.pandax.litemall.controller;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.PromotionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PromotionController {
    @Autowired
    PromotionService promotionService;

    @RequestMapping("admin/ad/list")
    public BaseReqVo listAd(Integer page, Integer limit, String sort,String order,Ad ad){
        Map<String, Object> map = promotionService.listAd(page, limit,sort,order,ad);
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
        Ad ad = promotionService.createAd(record);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(ad!=null){
            baseReqVo.setData(ad);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/ad/update")
    public BaseReqVo updateCoupon(@RequestBody Ad record){
        Ad ad = promotionService.updateAd(record);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(ad!=null){
            baseReqVo.setData(ad);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

   /* @RequestMapping("admin/read")
    public BaseReqVo readeAd(Integer page,Integer limit,@RequestBody Ad ad){
        Map<String,Object> map = promotionService.selectAdsByNameOrContent(page,limit,ad);
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
    }*/

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

    @RequestMapping("admin/coupon/list")
    public BaseReqVo listCoupon(Integer page, Integer limit, String sort,String order,Coupon coupon){
        Map<String, Object> map = promotionService.listCoupon(page, limit,sort,order,coupon);
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
    @RequestMapping("wx/coupon/mylist")
    public BaseReqVo wxListCoupon(Integer page,Integer size,Coupon coupon){
        Map<String, Object> map = promotionService.wxListCoupon(page,size,coupon);
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
    @RequestMapping("wx/coupon/receive")
    public BaseReqVo wxReceiveCoupon(@RequestBody Map<String,Integer> map){
        int result = promotionService.wxReceiveCoupon(map.get("couponId"));
        BaseReqVo baseReqVo = new BaseReqVo();
        if(result == 1){
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }
    @RequestMapping("wx/coupon/exchange")
    public BaseReqVo wxexchangeCoupon(@RequestBody Map<String,String> map){
        int result = promotionService.wxexchangeCoupon(map.get("code"));
        BaseReqVo baseReqVo = new BaseReqVo();
        if(result == 1){
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("wx/coupon/selectlist")
    public BaseReqVo wxSelectListCoupon1(Integer cartId,Integer grouponRulesId){
        List<Coupon> list = promotionService.wxSelectListCoupon1(cartId, grouponRulesId);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(list!=null){
            baseReqVo.setData(list);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }
    @RequestMapping("admin/coupon/create")
    public BaseReqVo createCoupon(@RequestBody Coupon coupon){
        Coupon coupon1 = promotionService.createCoupon(coupon);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(coupon1!=null){
            baseReqVo.setData(coupon1);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/coupon/read")
    public BaseReqVo readCoupon(Integer id){
        Coupon coupon = promotionService.readCoupon(id);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(coupon!=null){
            baseReqVo.setData(coupon);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/coupon/listuser")
    public BaseReqVo listCouponUser(Integer page, Integer limit, String sort,String order,CouponUser couponUser){
        Map<String, Object> map = promotionService.listCouponUser(page, limit,sort,order,couponUser);
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

    @RequestMapping("admin/coupon/delete")
    public BaseReqVo deleteCoupon(@RequestBody Coupon coupon){
        int i = promotionService.deleteCoupon(coupon.getId());
        BaseReqVo baseReqVo = new BaseReqVo();
        if(i==1){
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/coupon/update")
    public BaseReqVo updateCoupon(@RequestBody Coupon coupon){
        Coupon i = promotionService.updateCoupon(coupon);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(i!=null){
            baseReqVo.setData(i);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/topic/list")
    public BaseReqVo listTopic(Integer page, Integer limit, String sort,String order,Topic topic){
        Map<String, Object> map = promotionService.listTopic(page, limit,sort,order,topic);
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

    @RequestMapping("admin/topic/create")
    public BaseReqVo createCoupon(@RequestBody Topic topic){
        Topic topic1 = promotionService.createTopic(topic);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(topic1!=null){
            baseReqVo.setData(topic1);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/topic/update")
    public BaseReqVo updateTopic(@RequestBody Topic topic){
        Topic i = promotionService.updateTopic(topic);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(i!=null){
            baseReqVo.setData(i);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/topic/delete")
    public BaseReqVo deleteTopic(@RequestBody Topic record){
        int result = promotionService.deleteTopic(record.getId());
        BaseReqVo baseReqVo = new BaseReqVo();
        if(result==1){
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/groupon/list")
    public BaseReqVo listGroupon(Integer page, Integer limit, String sort,String order,GrouponRules grouponRules){
        Map<String, Object> map = promotionService.listGroupon(page, limit,sort,order,grouponRules);
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

    @RequestMapping("admin/groupon/create")
    public BaseReqVo publishGroupon(@RequestBody GrouponRules grouponRules){
        GrouponRules result = promotionService.createGrouponRules(grouponRules);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(result!=null){
            baseReqVo.setData(result);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("商品id有误");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/groupon/update")
    public BaseReqVo editGroupon(@RequestBody GrouponRules grouponRules){
        GrouponRules i = promotionService.editGroupon(grouponRules);
        BaseReqVo baseReqVo = new BaseReqVo();
        if(i!=null){
            baseReqVo.setData(i);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/groupon/delete")
    public BaseReqVo deleteGroupon(@RequestBody GrouponRules record){
        int result = promotionService.deleteGroupon(record.getId());
        BaseReqVo baseReqVo = new BaseReqVo();
        if(result==1){
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        baseReqVo.setErrmsg("失败");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    @RequestMapping("admin/groupon/listRecord")
    public BaseReqVo listRecord(Integer page, Integer limit, String sort,String order,Groupon groupon){
        Map<String, Object> map = promotionService.listRecord(page, limit,sort,order,groupon);
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
}
