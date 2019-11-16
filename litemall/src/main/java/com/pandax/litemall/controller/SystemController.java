package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.RoleInfo;
import com.pandax.litemall.bean.Storage;
import com.pandax.litemall.service.AdminService;
import com.pandax.litemall.service.RoleService;
import com.pandax.litemall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("admin")
@ResponseBody
public class SystemController {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

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
    public BaseReqVo showAdmins(Integer page, Integer limit, String sort, String order) {
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String, Object> map = adminService.queryUsers(page, limit, sort, order);
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
     * 保存静态资源（比如图片）
     * @param file 上传的静态资源
     * @return 状态码
     */
    @RequestMapping("storage/create ")
    public BaseReqVo storageResource(@RequestParam("file") MultipartFile file) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        try {
            if (file.isEmpty()) {
                return baseReqVo;
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 设置文件存储路径
            //String filePath = "D:/Test/Downloads/";
            String filePath = "static/";
            String path = filePath + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return baseReqVo;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
