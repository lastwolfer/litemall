package com.pandax.litemall.controller;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.AdminService;
import com.pandax.litemall.service.LogService;
import com.pandax.litemall.service.RoleService;
import com.pandax.litemall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class SystemController {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired

    LogService logService;

    @Autowired
    StorageService storageService;


    /**
     * 显示所有管理员账号以及其详情
     *
     * @param page  页数
     * @param limit 每页数量
     * @param sort  分类情况
     * @param order 排序情况
     * @return
     */
    @RequestMapping("admin/list")
    public BaseReqVo showAdmins(Integer page, Integer limit,
                                String sort, String order, String username) {
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String, Object> map = adminService.queryUsers(page, limit, sort, order, username);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 显示所有管理员称号
     *
     * @return options数组，以及基本信息
     */
    @RequestMapping("role/options")
    public BaseReqVo adminOptions() {
        BaseReqVo baseReqVo = new BaseReqVo();
        List<RoleInfo> roleList = roleService.selectRoles();
        baseReqVo.setData(roleList);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 更新管理员信息
     * @param admin 管理员信息
     * @return
     */
    @RequestMapping("admin/update")
    public BaseReqVo adminUpdate(@RequestBody Admin admin){
        BaseReqVo baseReqVo = new BaseReqVo();
        String password = admin.getPassword();
        if(password.length() < 6){
            baseReqVo.setData(null);
            baseReqVo.setErrno(602);
            baseReqVo.setErrmsg("管理员密码长度不能小于6");
            return baseReqVo;
        }
        adminService.updateAdmin(admin);
        baseReqVo.setData(admin);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);


        //日志操作
        Log log = new Log("unknow admin", "0:0:0:0:0:0:0:1",
                1, "编辑管理员");

        /*log.setAdmin(username);
        log.setIp("0:0:0:0:0:0:0:1");
        log.setType(1);
        log.setAction("编辑管理员");
        log.setStatus(true);
        log.setResult("");
        log.setComment("");
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        log.setDeleted(false);*/

        logService.record(log);

        return baseReqVo;
    }

    /**
     * 删除管理员
     * @param admin 管理员信息
     * @return
     */
    @RequestMapping("admin/delete")
    public BaseReqVo deleteAdmin(@RequestBody Admin admin){
        BaseReqVo baseReqVo = new BaseReqVo();
        adminService.deleteAdmin(admin);

        //日志操作
        Log log = new Log("unknow admin", "0:0:0:0:0:0:0:1",
                1, "删除管理员");

        logService.record(log);

        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("log/list")
    public BaseReqVo showLogList(Integer page, Integer limit,
                                 String sort, String order,  String name){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = logService.showLogList(page,limit,sort,order, name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**显示对象存储表(条件查询)
     * Request: ?page=1&limit=20&sort=add_time&order
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "total": 1539,
     *         "items": [
     *             {
     *                 "id": 1644,
     *                 "key": "75kp0b7t9wxa8nrnw65u.jpg",
     *                 "name": "图片2.jpg",
     *                 "type": "image/jpeg",
     *                 "size": 15639,
     *                 "url": "http://192.168.2.100:8081/wx/storage/fetch/75kp0b7t9wxa8nrnw65u.jpg",
     *                 "addTime": "2019-11-16 04:02:32",
     *                 "updateTime": "2019-11-16 04:02:32",
     *                 "deleted": false
     *             }
     *         ]
     *     },
     *     "errmsg": "成功"
     * }
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping("storage/list")
    public BaseReqVo showStorageList(Integer page, Integer limit, String sort, String order,String key,String name){
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String,Object> map = storageService.queryStorage(page,limit,sort,order,key,name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**修改对象信息
     * Request:
     * addTime: "2019-11-16 05:37:13"
     * deleted: false
     * id: 1672
     * key: "0aa2r8n0qspcxb627jib.jpg"
     * name: "xixixi.jpg"
     * size: 136865
     * type: "image/jpeg"
     * updateTime: "2019-11-16 05:53:23"
     * url: "http://192.168.2.100:8081/wx/storage/fetch/0aa2r8n0qspcxb627jib.jpg"
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "id": 1672,
     *         "key": "0aa2r8n0qspcxb627jib.jpg",
     *         "name": "xixixi.jpg",
     *         "type": "image/jpeg",
     *         "size": 136865,
     *         "url": "http://192.168.2.100:8081/wx/storage/fetch/0aa2r8n0qspcxb627jib.jpg",
     *         "addTime": "2019-11-16 05:37:13",
     *         "updateTime": "2019-11-16 06:30:10",
     *         "deleted": false
     *     },
     *     "errmsg": "成功"
     * }
     * @param storage
     * @return BaseReqVo
     */
    @RequestMapping("storage/update")
    public BaseReqVo updateStorage(@RequestBody Storage storage){
        BaseReqVo baseReqVo = new BaseReqVo();
        Storage updateStorage = storageService.updateStorage(storage);
        baseReqVo.setData(updateStorage);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**删除对象信息
     * Request:
     * addTime: "2019-11-16 05:40:39"
     * deleted: false
     * id: 1673
     * key: "t27okwwr3dxshxl7zicy.jpg"
     * name: "111"
     * size: 27217
     * type: "image/jpeg"
     * updateTime: "2019-11-16 05:47:01"
     * url: "http://192.168.2.100:8081/wx/storage/fetch/t27okwwr3dxshxl7zicy.jpg"
     * Response:
     * {"errno":0,"errmsg":"成功"}
     * @param storage
     * @return BaseReqVo
     */
    @RequestMapping("storage/delete")
    public BaseReqVo deleteStorage(@RequestBody Storage storage){
        BaseReqVo baseReqVo = new BaseReqVo();
        int deleteStatus = storageService.deleteStorage(storage);
        if(deleteStatus != -1){
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
        }
        return baseReqVo;
    }


    /**显示角色信息
     * Request:
     * page: 1
     * limit: 20
     * name:
     * sort: add_time
     * order: desc
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "total": 33,
     *         "items": [
     *             {
     *                 "id": 124,
     *                 "name": "iiiiiii",
     *                 "desc": "????",
     *                 "enabled": true,
     *                 "addTime": "2019-11-17 20:58:45",
     *                 "updateTime": "2019-11-17 20:58:45",
     *                 "deleted": false
     *             },
     *             {
     *                 "id": 123,
     *                 "name": "21321",
     *                 "desc": "321321",
     *                 "enabled": true,
     *                 "addTime": "2019-11-17 08:41:12",
     *                 "updateTime": "2019-11-17 08:41:12",
     *                 "deleted": false
     *             }
     *         ]
     *     },
     *     "errmsg": "成功"
     * }
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param name
     * @return
     */
    @RequestMapping("role/list")
    public BaseReqVo showRoleList(Integer page, Integer limit, String sort, String order,String name){
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String,Object> map = roleService.getRoles(page,limit,sort,order,name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**修改角色信息
     * Request:
     * addTime: "2019-11-17 20:58:45"
     * deleted: false
     * desc: "hahaha"
     * enabled: true
     * id: 124
     * name: "iiiiiii"
     * updateTime: "2019-11-17 20:58:45"
     * Response:
     * {"errno":0,"errmsg":"成功"}
     * @param role
     * @return
     */
    @RequestMapping("role/update")
    public BaseReqVo updateRole(@RequestBody Role role){
        BaseReqVo baseReqVo = new BaseReqVo();
        Role updateRole = roleService.updateRole(role);
        baseReqVo.setData(updateRole);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**创建角色信息
     * Request:
     * {name: "xixixi234", desc: "hahaha"}
     * Response:
     * {
     *     "errno": 0,
     *     "data": {
     *         "id": 128,
     *         "name": "xixixi234",
     *         "desc": "hahaha",
     *         "addTime": "2019-11-18 03:19:10",
     *         "updateTime": "2019-11-18 03:19:10"
     *     },
     *     "errmsg": "成功"
     * }
     * @param role
     * @return BaseReqVo
     */
    @RequestMapping("role/create")
    public BaseReqVo createRole(@RequestBody Role role){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map<String,Object> map = roleService.createRole(role);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**删除角色信息
     * Request:
     * addTime: "2019-11-18 02:53:03"
     * deleted: false
     * desc: "hahah"
     * enabled: true
     * id: 126
     * name: "xixixi"
     * updateTime: "2019-11-18 02:57:47"
     * Response:
     * {"errno":0,"errmsg":"成功"}
     * @param role
     * @return BaseReqVo
     */
    @RequestMapping("role/delete")
    public BaseReqVo deleteRole(@RequestBody Role role){
        BaseReqVo baseReqVo = new BaseReqVo();
        int deleteStatus = roleService.deleteRole(role);
        if(deleteStatus != -1){
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
        }
        return baseReqVo;
    }
}
