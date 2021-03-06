package com.pandax.litemall.controller;



import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Region;
import com.pandax.litemall.service.GrouponService;
import com.pandax.litemall.service.UserService;
import com.pandax.reponseJson.UserAllAddress;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.GoodsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;
import java.util.*;


@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GrouponService grouponService;

    /**
     * 查詢所有的用戶
     *
     * @param page     页数
     * @param limit    每页的数目
     * @param sort     列名，按照该列名排序
     * @param order    排序方式
     * @param mobile   手机号
     * @param username 用户名
     * @return 返回json数据
     */
    @RequestMapping("/admin/user/list")
    @RequiresPermissions("admin:user:list")
    public BaseReqVo userList(Integer page, Integer limit, String sort, String order, String mobile, String username) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = userService.selectUserList(page, limit, sort, order, mobile, username);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 查询收货地址
     *
     * @param page   页数
     * @param limit  每页的数目
     * @param sort   列名，按照该列名排序
     * @param order  排序方式
     * @param name   收货人姓名
     * @param userId 用户ID
     * @return 返回json数据
     */
    @RequestMapping("/admin/address/list")
    @RequiresPermissions("admin:address:list")
    public BaseReqVo addressList(Integer page, Integer limit, String sort, String order, String name, Integer userId) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = userService.selectAddressList(page, limit, sort, order, name, userId);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 会员收藏
     *
     * @param page    页数
     * @param limit   每页的数目
     * @param sort    列名，按照该列名排序
     * @param order   排序方式
     * @param valueId 商品ID
     * @param userId  用户ID
     * @return 返回json数据
     */
    @RequestMapping("/admin/collect/list")
    @RequiresPermissions("admin:collect:list")
    public BaseReqVo collectList(Integer page, Integer limit, String sort, String order, Integer valueId, Integer userId) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = userService.selectCollectList(page, limit, sort, order, valueId, userId);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 会员足迹
     *
     * @param page    页数
     * @param limit   每页的数目
     * @param sort    列名，按照该列名排序
     * @param order   排序方式
     * @param goodsId 商品ID
     * @param userId  用户ID
     * @return 返回json数据
     */
    @RequestMapping("/admin/footprint/list")
    @RequiresPermissions("admin:footprint:list")
    public BaseReqVo footprintList(Integer page, Integer limit, String sort, String order, Integer goodsId, Integer userId) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = userService.selectFootprintList(page, limit, sort, order, goodsId, userId);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 搜索历史
     *
     * @param page    页数
     * @param limit   每页的数目
     * @param sort    列名，按照该列名排序
     * @param order   排序方式
     * @param userId  用户ID
     * @param keyword 历史关键字
     * @return 返回json数据
     */
    @RequestMapping("/admin/history/list")
    @RequiresPermissions("admin:history:list")
    public BaseReqVo historyList(Integer page, Integer limit, String sort, String order,Integer userId, String keyword) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = null;
        map = userService.selectHistoryList(page, limit, sort, order, userId, keyword);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 意见反馈
     *
     * @param page     页数
     * @param limit    每页数目
     * @param sort     列名，按照所给的列排序
     * @param order    排序方式
     * @param id       反馈ID
     * @param username 用户名
     * @return json数据
     */
    @RequestMapping("/admin/feedback/list")
    @RequiresPermissions("admin:feedback:list")
    public BaseReqVo feedbackList(Integer page, Integer limit, String sort, String order, Integer id, String username) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = userService.selectFeedbackList(page, limit, sort, order, id, username);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 宝
     * 根据pid查询区域
     * @param pid region中的pid
     * @return json
     */
    @RequestMapping("/wx/region/list")
    public BaseReqVo wxRegionList(Integer pid){
        BaseReqVo baseReqVo =new BaseReqVo();
        List<Region> regions =userService.selectRegionsList(pid);
        baseReqVo.setErrno(0);
        baseReqVo.setData(regions);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 宝
     * 用户的所有收货地址
     * @return
     */
    @RequestMapping("/wx/address/list")
    public BaseReqVo wxAddressList(){
        BaseReqVo baseReqVo =new BaseReqVo();
        List<UserAllAddress> addresses= userService.selectAllAddress();
        baseReqVo.setErrno(0);
        baseReqVo.setData(addresses);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 宝
     * @param id 收货地址id
     * @return json数据
     */
    @RequestMapping("/wx/address/detail")
    public BaseReqVo wxAddressDetail(Integer id){
        BaseReqVo baseReqVo =new BaseReqVo();
        Map map= userService.selectAddressById(id);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("/wx/address/save")
    public BaseReqVo wxAddressSave(@RequestBody Address address){
        BaseReqVo baseReqVo =new BaseReqVo();
        Integer id = userService.updateAddressSave(address);
        baseReqVo.setErrno(0);
        baseReqVo.setData(id);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("/wx/address/delete")
    public BaseReqVo wxAddressDelete(@RequestBody Map map){
        Integer id= (Integer) map.get("id");
        BaseReqVo baseReqVo =new BaseReqVo();
         userService.deleteAddress(id);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }



    /**
     * 反馈信息
     *
     * @param feedback
     * @return
     */
    @RequestMapping("wx/feedback/submit")
    public BaseReqVo submitFeedBack(@RequestBody Feedback feedback) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            return BaseReqVo.fail(500, "请先登录");
        }
        feedback.setUserId(user.getId());
        feedback.setUsername(user.getUsername());
        feedback.setAddTime(new Date());
        int i = userService.insertFeedBack(feedback);
        return BaseReqVo.ok();
    }

    /**
     * 足迹信息
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("wx/footprint/list")
    public BaseReqVo listFootprint(Integer page, Integer size) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return BaseReqVo.fail(500, "请登录");
        }
        List<FootprintListBean> footprintList = userService.selectFootprint(user.getId(),page,size);
        long totalPages = new PageInfo<>(footprintList).getTotal();
        Map dataMap = new HashMap();
        dataMap.put("footprintList",footprintList);
        dataMap.put("totalPages",totalPages);
        return BaseReqVo.ok(dataMap);
    }


    /**
     * 宝
     * 查看团购
     * @param showType 团购类型
     * @return json
     */
    @RequestMapping("/wx/groupon/my")
    public BaseReqVo wxGrouponMy(Integer showType){
        BaseReqVo baseReqVo = new BaseReqVo();
        List<Groupon> groupons = grouponService.selectGrouponMy(showType);
        baseReqVo.setErrno(0);
        baseReqVo.setData(groupons);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 删除足迹
     * @param map
     * @return
     */
    @RequestMapping("wx/footprint/delete")
    public BaseReqVo deleteFootprint(@RequestBody Map map){
        Integer id = (Integer) map.get("id");
        int i = userService.deleteFootprint(id);
        return BaseReqVo.ok();
    }

}
