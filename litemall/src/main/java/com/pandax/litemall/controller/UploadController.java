package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Storage;
import com.pandax.litemall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 10:28
 */

@RestController
@RequestMapping("admin")
public class UploadController {

    @Autowired
    StorageService storageService;

    @Value("${my.Storage.path}")
    private String prefix;

    @Value("${my.filePath}")
    private String filePath ;

    /**
     * 保存静态资源（比如图片）
     * @param file 上传的静态资源
     * @return 状态码
     */
    @RequestMapping("storage/create")
    public BaseReqVo storageResource(@RequestParam("file") MultipartFile file) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        try {
            if (file.isEmpty()) {
                baseReqVo.setErrno(1000);
                baseReqVo.setErrmsg("请选择上传文件");
                return baseReqVo;
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String hexString = Integer.toHexString(uuid.hashCode());
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));


            // 设置文件存储路径
            //String filePath = "D:/Develop/Coding WorkingSpace/static/img/";

            String newFileName = uuid + suffixName;
            //设置文件存储路径
            StringBuilder path = new StringBuilder();
            for (int i = 0; i < hexString.length(); i++) {
                path.append(hexString.charAt(i)).append("/");
            }
            File dest = new File(filePath + path.toString(), newFileName);
            // 检测是否存在目录
            if (!dest.exists() && !dest.isDirectory()) {
                dest.mkdirs();
            }
            file.transferTo(dest);// 文件写入

            Storage storage = new Storage();
            storage.setKey(newFileName);
            storage.setName(fileName);
            storage.setType(file.getContentType());
            storage.setSize(file.getSize());
            storage.setUrl(prefix + path.toString() + newFileName);
            storage.setAddTime(new Date());
            storage.setUpdateTime(new Date());
            int id = storageService.saveStorage(storage);
            storage.setId(id);

            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setData(storage);
            return baseReqVo;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseReqVo;
    }

    /*private void fileUpload(MultipartFile file, Storage storage) throws IOException {
        File path2 = null;
        path2 = new File(ResourceUtils.getURL("classpath:static").
                    getPath().replace("%20"," ").replace('/', '\\'));
        if(!path2.exists()) {
            path2 = new File("");
        }
        UUID uuid = UUID.randomUUID();
        String fileName = file.getOriginalFilename();
        String hexString = Integer.toHexString(uuid.hashCode());
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 设置文件存储路径
        String newfileName = uuid + suffixName;
        //设置文件存储路径
        StringBuilder child = new StringBuilder("img/");
        for (int i = 0; i < hexString.length(); i++) {
            child.append(hexString.charAt(i)).append("/");
        }
        //如果上传目录为/static/img，则可以如下获取：
        File upload2 = new File(path2.getAbsolutePath(),child.toString());
        if(!upload2.exists()) upload2.mkdirs();
        String path = upload2.getAbsolutePath() + newfileName + suffixName;
        File finalFile = new File(path);
        file.transferTo(finalFile);
    }*/
}
