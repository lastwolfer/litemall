package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class User {
    int id;
    String username;
    String password;
    String nickname;
    String avatar;
    int age;
    String gender;
    String[] hobby; //["coding","basketball"]
}
