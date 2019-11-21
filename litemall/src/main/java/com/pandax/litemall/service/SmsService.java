package com.pandax.litemall.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.pandax.litemall.component.AliyunComponent;
import com.pandax.litemall.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/20
 * @time 22:00
 */

@Service
public class SmsService {

    @Autowired
    AliyunComponent aliyunComponent;

    public String obtainVerificationCode(String mobile){
        IAcsClient client = aliyunComponent.getiacsClient();
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        SmsComponent sms = aliyunComponent.getSms();
        request.putQueryParameter("RegionId", sms.getRegionId());
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", sms.getSignName());
        request.putQueryParameter("TemplateCode", sms.getTemplateCode());
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int dig = (int)(Math.random() * 10);
            verificationCode.append(dig);
        }
        request.putQueryParameter("TemplateParam", "{\"code\": \"" + verificationCode + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            //System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return verificationCode.toString();
    }
}
