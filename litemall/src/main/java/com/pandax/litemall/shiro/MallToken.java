package com.pandax.litemall.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/19
 * @time 19:46
 */

@Data
public class MallToken extends UsernamePasswordToken {

    private String type;

    public MallToken(String username, String password, String type) {
        super(username, password);
        this.type = type;
    }
}