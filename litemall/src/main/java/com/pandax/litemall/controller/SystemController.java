package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.RoleInfo;
import com.pandax.litemall.service.AdminService;
import com.pandax.litemall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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

   /*
    //明日再战

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
            String filePath = "staic/";
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

    }*/
}
