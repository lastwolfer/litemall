package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.OrderSubmitInfo;
import com.pandax.litemall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("order/submit")
    public BaseReqVo orderSubmit(@RequestBody OrderSubmitInfo orderSubmitInfo){
        Integer orderId = orderService.orderSubmit(orderSubmitInfo);
        Map<String,Object> map = new HashMap<>();
        map.put("orderId", orderId);
        return BaseReqVo.ok(map);
    }

    @RequestMapping("order/prepay")
    public BaseReqVo orderPrepay(@RequestBody Map orderId){
        Object orderIdX = orderId.get("orderId");
       if(orderIdX instanceof String){
           int orderIdY = Integer.valueOf((String)orderIdX);
           int prePayStatus = orderService.prePay(orderIdY);
       }else if(orderIdX instanceof Integer) {
           int prePayStatus = orderService.prePay((Integer) orderIdX);
       }
        return BaseReqVo.ok();
    }

    /**按状态显示订单列表
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "data": [
     *             {
     *                 "orderStatusText": "未付款",
     *                 "isGroupin": false,
     *                 "orderSn": "20191120381033",
     *                 "actualPrice": 1342,
     *                 "goodsList": [
     *                     {
     *                         "number": 2,
     *                         "picUrl": "http://192.168.2.100:8081/wx/storage/fetch/dhg9j5xmzfck4xpyu18z.jpeg",
     *                         "id": 740,
     *                         "goodsName": "小猪佩奇"
     *                     }
     *                 ],
     *                 "id": 588,
     *                 "handleOption": {
     *                     "cancel": true,
     *                     "delete": false,
     *                     "pay": true,
     *                     "comment": false,
     *                     "confirm": false,
     *                     "refund": false,
     *                     "rebuy": false
     *                 }
     *             }
     *         ],
     *         "count": 4,
     *         "totalPages": 1
     *     },
     *     "errmsg": "成功"
     * }
     * @param showType
     * @param page
     * @param size
     * @return BaseRespVo
     */
    @RequestMapping("order/list")
    public BaseReqVo orderList(Short showType, Integer page, Integer size){
        Map<String,Object> dataMap = orderService.getOrderList(showType,page,size);
        return BaseReqVo.ok(dataMap);
    }

    /**显示订单详情
     * Request:
     * orderId: 588
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "orderInfo": {
     *             "consignee": "奥力给",
     *             "address": "北京市 市辖区 东城区 阿斯顿发",
     *             "addTime": "2019-11-20 01:28:03",
     *             "orderSn": "20191120381033",
     *             "actualPrice": 1342,
     *             "mobile": "13010010010",
     *             "orderStatusText": "未付款",
     *             "goodsPrice": 1342,
     *             "couponPrice": 0,
     *             "id": 588,
     *             "freightPrice": 0,
     *             "handleOption": {
     *                 "cancel": true,
     *                 "delete": false,
     *                 "pay": true,
     *                 "comment": false,
     *                 "confirm": false,
     *                 "refund": false,
     *                 "rebuy": false
     *             }
     *         },
     *         "orderGoods": [
     *             {
     *                 "id": 740,
     *                 "orderId": 588,
     *                 "goodsId": 1181147,
     *                 "goodsName": "小猪佩奇",
     *                 "goodsSn": "6666666",
     *                 "productId": 709,
     *                 "number": 2,
     *                 "price": 671,
     *                 "specifications": [
     *                     "200",
     *                     "222"
     *                 ],
     *                 "picUrl": "http://192.168.2.100:8081/wx/storage/fetch/dhg9j5xmzfck4xpyu18z.jpeg",
     *                 "comment": 0,
     *                 "addTime": "2019-11-20 01:28:03",
     *                 "updateTime": "2019-11-20 01:28:03",
     *                 "deleted": false
     *             }
     *         ]
     *     },
     *     "errmsg": "成功"
     * }
     * @return BaseRespVo
     */
    @RequestMapping("order/detail")
    public BaseReqVo orderDetail(Integer orderId){
        Map<String,Object> dataMap = orderService.getOrderDetail(orderId);
        return BaseReqVo.ok(dataMap);
    }

    /**取消订单
     * Request: orderId: 693
     * Response:{"errno":0,"errmsg":"成功"}
     * @return BaseReqVo
     */
    @RequestMapping("order/cancel")
    public BaseReqVo orderCancel(@RequestBody Map orderId){
        int orderIdX = (int) orderId.get("orderId");
        int cancelStatus = orderService.cancelOrder((short) orderIdX);
        if(cancelStatus != -1){
            return BaseReqVo.ok();
        }
        return BaseReqVo.fail();
    }

    @RequestMapping("order/delete")
    public BaseReqVo orderDelete(@RequestBody Map orderId){
        int orderIdX = (int) orderId.get("orderId");
        int deleteStatus = orderService.deleteOrder((short) orderIdX);
        if(deleteStatus != -1){
            return BaseReqVo.ok();
        }
        return BaseReqVo.fail();
    }

    @RequestMapping("order/refund")
    public BaseReqVo orderRefund(@RequestBody Map orderId){
        Integer orderIdX = (Integer) orderId.get("orderId");
        int refundStatus = orderService.refundOrder(orderIdX);
        if(refundStatus!= -1){
            return BaseReqVo.ok();
        }else{
            return BaseReqVo.fail();
        }
    }

    @RequestMapping("order/confirm")
    public BaseReqVo orderConfirm(@RequestBody Map orderId){
        int orderIdX = (int) orderId.get("orderId");
        int confirmStatus = orderService.confirmOrder((short) orderIdX);
        if(confirmStatus != -1){
            return BaseReqVo.ok();
        }
        return BaseReqVo.fail();
    }

    @RequestMapping("order/goods")
    public BaseReqVo orderGoods(Integer orderId,Integer goodsId){

        return BaseReqVo.ok();
    }
//
//    @RequestMapping("order/comment")
//    public BaseReqVo orderComment(){
//        return BaseReqVo.ok();
//    }

}
