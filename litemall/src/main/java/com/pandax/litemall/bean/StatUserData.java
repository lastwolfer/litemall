package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;

@Data
public class StatUserData {
    String[] columns = {"day","users"};
    private List<StatUserDb> rows;

}
