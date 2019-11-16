package com.pandax.litemall.controller;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    ConfigService configService;

    /**显示商城配置
     * Request: none
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "litemall_mall_phone": "10086",
     *         "litemall_mall_address": "武汉",
     *         "litemall_mall_name": "光谷店",
     *         "litemall_mall_qq": "123450000"
     *     },
     *     "errmsg": "成功"
     * }
     * @return BaseReqvo
     */
    @RequestMapping(value = "admin/config/mall",method = RequestMethod.GET)
    public BaseReqVo mallConfig(){
        BaseReqVo baseReqVo = new BaseReqVo();
        MallConfig mallConfig = configService.getMallConfig();
        baseReqVo.setData(mallConfig);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**更新商城配置
     * Request:
     * litemall_mall_address: "武汉"
     * litemall_mall_name: "光谷店"
     * litemall_mall_phone: "10086"
     * litemall_mall_qq: "121212121"
     * Response:
     * {"errno":0,"errmsg":"成功"}
     * @param mallConfig
     * @return BaseReqVo
     */
    @RequestMapping(value = "admin/config/mall",method = RequestMethod.POST)
    public BaseReqVo mallConfigUpdate(@RequestBody MallConfig mallConfig){
        BaseReqVo baseReqVo = new BaseReqVo();
        int updateStatus = configService.updateMallConfig(mallConfig);
        if(updateStatus!=-1){
            baseReqVo.setErrmsg("成功");
        }
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**显示运费配置
     * Request:none
     * Response:
     * {"errno":0,"data":{"litemall_express_freight_min":"25","litemall_express_freight_value":"5"},"errmsg":"成功"}
     * @return BaseReqVo
     */
    @RequestMapping(value = "admin/config/express",method = RequestMethod.GET)
    public BaseReqVo expressConfig(){
        BaseReqVo baseReqVo = new BaseReqVo();
        ExpressConfig expressConfig = configService.getExpressConfig();
        baseReqVo.setData(expressConfig);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**修改运费配置
     * Request:
     * litemall_express_freight_min: "25"
     * litemall_express_freight_value: "5"
     * Response:
     * {"errno":0,"errmsg":"成功"}
     * @param expressConfig
     * @return BaseReqVo
     */
    @RequestMapping(value = "admin/config/express",method = RequestMethod.POST)
    public BaseReqVo expressConfigUpdate(@RequestBody ExpressConfig expressConfig){
        BaseReqVo baseReqVo = new BaseReqVo();
        int updateStatus = configService.updateExpressConfig(expressConfig);
        if(updateStatus!=-1){
            baseReqVo.setErrmsg("成功");
        }
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**显示订单配置
     * Request: none
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "litemall_order_comment": "7777",
     *         "litemall_order_unpaid": "45",
     *         "litemall_order_unconfirm": "454545"
     *     },
     *     "errmsg": "成功"
     * }
     * @return BaseReqVo
     */
    @RequestMapping(value = "admin/config/order",method = RequestMethod.GET)
    public BaseReqVo orderConfig(){
        BaseReqVo baseReqVo = new BaseReqVo();
        OrderConfig orderConfig = configService.getOrderConfig();
        baseReqVo.setData(orderConfig);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**修改订单设置
     * Request:
     * litemall_order_comment: "7777"
     * litemall_order_unconfirm: "454545"
     * litemall_order_unpaid: "30"
     * Response:
     * {"errno":0,"errmsg":"成功"}
     * @param orderConfig
     * @return BaseReqVo
     */
    @RequestMapping(value = "admin/config/order",method = RequestMethod.POST)
    public BaseReqVo orderConfigUpdate(@RequestBody OrderConfig orderConfig){
        BaseReqVo baseReqVo = new BaseReqVo();
        int updateStatus = configService.updateOrderConfig(orderConfig);
        if(updateStatus!=-1){
            baseReqVo.setErrmsg("成功");
        }
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**显示小程序设置
     * Request:none
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "litemall_wx_index_new": "10",
     *         "litemall_wx_catlog_goods": "7878",
     *         "litemall_wx_catlog_list": "4",
     *         "litemall_wx_share": "true",
     *         "litemall_wx_index_brand": "4",
     *         "litemall_wx_index_hot": "4",
     *         "litemall_wx_index_topic": "4"
     *     },
     *     "errmsg": "成功"
     * }
     * @return BaseReqVo
     */
    @RequestMapping(value = "admin/config/wx",method = RequestMethod.GET)
    public BaseReqVo wxConfig(){
        BaseReqVo baseReqVo = new BaseReqVo();
        WxConfig wxConfig = configService.getWxConfig();
        baseReqVo.setData(wxConfig);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**修改小程序设置
     * Request:
     * litemall_wx_catlog_goods: "787899"
     * litemall_wx_catlog_list: "6"
     * litemall_wx_index_brand: "4"
     * litemall_wx_index_hot: "4"
     * litemall_wx_index_new: "10"
     * litemall_wx_index_topic: "4"
     * litemall_wx_share: "true"
     * Response:{"errno":0,"errmsg":"成功"}
     * @param wxConfig
     * @return BaseReqVo
     */
    @RequestMapping(value = "admin/config/wx",method = RequestMethod.POST)
    public BaseReqVo wxConfigUpdate(@RequestBody WxConfig wxConfig){
        BaseReqVo baseReqVo = new BaseReqVo();
        int updateStatus = configService.updateWxConfig(wxConfig);
        if(updateStatus!=-1){
            baseReqVo.setErrmsg("成功");
        }
        baseReqVo.setErrno(0);
        return baseReqVo;
    }




}
