package com.pandax.litemall.controller;

import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Log;
import com.pandax.litemall.bean.RoleInfo;
import com.pandax.litemall.bean.Storage;
import com.pandax.litemall.exception.AdminException;
import com.pandax.litemall.service.AdminService;
import com.pandax.litemall.service.LogService;
import com.pandax.litemall.service.RoleService;
import com.pandax.litemall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BaseReqVo adminUpdate(@RequestBody Admin admin) throws AdminException {
        BaseReqVo baseReqVo = new BaseReqVo();
        checkUsernameAndPassword(admin);
        /*int count = adminService.queryUserCountByName(username);
        if(count == 1) {
            throw new AdminException(602, "管理员已经存在");
        }
        String password = admin.getPassword();
        if(password.length() < 6){
            throw new AdminException(602, "管理员密码长度不能小于6");
        }*/
        adminService.updateAdmin(admin);
        baseReqVo.setData(admin);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);


        //日志操作
//        Log log = new Log("unknow admin", "0:0:0:0:0:0:0:1",
//                1, "编辑管理员");
//
//        /*log.setAdmin(username);
//        log.setIp("0:0:0:0:0:0:0:1");
//        log.setType(1);
//        log.setAction("编辑管理员");
//        log.setStatus(true);
//        log.setResult("");
//        log.setComment("");
//        log.setAddTime(new Date());
//        log.setUpdateTime(new Date());
//        log.setDeleted(false);*/
//
//        logService.record(log);


        return baseReqVo;
    }


    /**
     * 添加管理员
     * @param admin 管理员信息
     * @return
     */
    @RequestMapping("admin/create")
    public BaseReqVo adminCreate(@RequestBody Admin admin) throws AdminException {
        BaseReqVo baseReqVo = new BaseReqVo();

        checkUsernameAndPassword(admin);


        Admin returnAdmin = adminService.createAdmin(admin);
        baseReqVo.setData(returnAdmin);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);


        /*Log log = new Log("unknow admin", "0:0:0:0:0:0:0:1",
                1, "添加管理员");
        logService.record(log);*/

        return baseReqVo;
    }


    /**
     * 检验用户名和密码是否正确
     * @param admin 用户
     * @return true/false
     */
    private void checkUsernameAndPassword(Admin admin) throws AdminException {
        //^[a-zA-Z0-9_-]{4,16}
        Admin queryAdmin = adminService.queryUserCountById(admin.getId());
        String username = admin.getUsername();
        if (queryAdmin != null && !username.equals(queryAdmin.getUsername())) {
            if(!username.matches("^[\\u4E00-\\u9FA5-a-zA-Z0-9_-]{6,20}")) {
                throw new AdminException(601, "管理员名称不符合规定");
            }
            int count = adminService.queryUserCountByName(username);
            if(count == 1) {
                throw new AdminException(602, "管理员已经存在");
            }
        }
        if(admin.getPassword().length() < 6){
            throw new AdminException(602, "管理员密码长度不能小于6");
        }
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
        /*Log log = new Log("unknow admin", "0:0:0:0:0:0:0:1",
                1, "删除管理员");

        logService.record(log);*/

        baseReqVo.setErrmsg("成功删除：" + admin.getUsername());
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
}
