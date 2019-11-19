package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/18
 * @time 21:16
 */

@Data
public class SystemPermissions {

    private String id;

    private String label;

    private String api;

    private List<SystemPermissions> children;
}
