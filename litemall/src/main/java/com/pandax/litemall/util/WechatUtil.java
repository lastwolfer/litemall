package com.pandax.litemall.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.pandax.litemall.bean.WechatCode;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/22
 * @time 11:19
 */

public class WechatUtil {

    private static final String APP_SECRET="87a2640cd8f3cefd18278ba5bc7f991d";   //写上自己的的
    private static final String APP_ID = "wx848f92fc267fbb3b";   //写上自己的的APPID


    public static String getSessionKey(String code) { // 小程序端获取的CODE
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        try {
            StringBuilder urlPath = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session"); // 微信提供的API，这里最好也放在配置文件
            urlPath.append(String.format("?appid=%s", APP_ID));
            urlPath.append(String.format("&secret=%s", APP_SECRET));
            urlPath.append(String.format("&js_code=%s", code));
            urlPath.append(String.format("&grant_type=%s", "authorization_code")); // 固定值
            String data = HttpUtils.sendPost(urlPath.toString(), null); // java的网络请求，这里是我自己封装的一个工具包，返回字符串
            //System.out.println("请求结果：" + data);
            Gson gson = new Gson();
            WechatCode wechatCode = gson.fromJson(data, WechatCode.class);
            if(wechatCode.getSession_key() == null) {
                return null;
            }
            return wechatCode.getSession_key().replace("\\", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String decryptData(String encryptDataB64, String sessionKeyB64, String ivB64) {
        return new String(
                decryptOfDiyIV(
                        Base64.decode(encryptDataB64),
                        Base64.decode(sessionKeyB64),
                        Base64.decode(ivB64)
                )
        );
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;

    private static void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }
}
