package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;

@Data
public class InfoData {
    String avatar;
    String name;
    List<String> perms;
    List<String> roles;
}
