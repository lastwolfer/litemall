package com.pandax.litemall;

import com.pandax.litemall.util.Md5Utils;
import org.junit.jupiter.api.Test;

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
}
