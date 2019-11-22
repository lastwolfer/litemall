package com.pandax.reponseJson;

import lombok.Data;

import java.util.Date;


@Data
public class SingleComment {
    private Integer id;
    private String nickname;
    private String content;
    private String avatar;
    private Date addTime;
    private String[]  picList;
}
