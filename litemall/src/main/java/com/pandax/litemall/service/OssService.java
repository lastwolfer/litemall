package com.pandax.litemall.service;

import com.aliyun.oss.OSSClient;
import com.pandax.litemall.component.AliyunComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class OssService {

    @Autowired
    AliyunComponent aliyunComponent;

    public void saveFile(File file){
        OSSClient ossClient = aliyunComponent.getOssClient();
        String s = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        ossClient.putObject(aliyunComponent.getOss().getBucket(),s,file);
    }
}
