package com.pandax.litemall;

import com.pandax.litemall.util.Md5Utils;
import com.pandax.litemall.util.WechatUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/21
 * @time 21:47
 */

public class DaTest {

    @Test
    public void mytest(){
        String password = "admin123";
        System.out.println(Md5Utils.getMultiMd5(password));
    }

    @Test
    public void mytest2(){
        String code = "071AUjiC1JDYf60QlWjC1jNeiC1AUjie";
        String sessionKey = WechatUtil.getSessionKey(code);
        System.out.println(sessionKey);
    }

    @Test
    public void mytest3(){

        String s = WechatUtil.decryptData("o1sl0CyYA2r0hLvrzeZBuybAkw9CN6roSNYRHrkcTlheng2IJ5M42DLd8tRopXAnELXl3MFctcbiE3gSNK48fT36r5KDho6LNq2luwbVXhmb7EQ4OEt/LOARqbF/XGCi5Jat1rJLH4lZhq0l4E05P14KMBWikJynplyYO5h+iN9R7O6kFnx2Fxp8nx+xT+3EjFUAmgbl/uY4jH1FLmtQTQ==",
                WechatUtil.getSessionKey("011htjD40N21VD1VKMC40b8vD40htjDV"), "EwnLuMzjk1+gvALLzM/f2g==");

        System.out.println(s);
    }
}
