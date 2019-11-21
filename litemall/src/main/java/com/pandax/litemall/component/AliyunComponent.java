package com.pandax.litemall.component;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mall.aliyun")
public class AliyunComponent {
    String accessKeyId;
    String accessSecret;
    OssComponent oss;
    SmsComponent sms;

    public OSSClient getOssClient(){

        return new OSSClient(oss.getEndPoint(),accessKeyId,accessSecret);
    }

    public IAcsClient getiacsClient(){
        DefaultProfile profile = DefaultProfile.getProfile(sms.getRegionId(), accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
