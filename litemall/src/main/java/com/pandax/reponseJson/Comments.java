package com.pandax.reponseJson;

import com.pandax.litemall.bean.Comment;
import lombok.Data;

import java.util.List;

@Data
public class Comments {

    List<Comment> data;

    Long count;
}
