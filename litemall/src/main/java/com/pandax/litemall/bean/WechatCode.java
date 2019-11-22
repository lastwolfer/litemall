package com.pandax.litemall.bean;

import lombok.Data;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/22
 * @time 13:06
 */

@Data
public class WechatCode {
    private String session_key;
    private String openid;
}
