package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.bean.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    @Select("select count(id) from cskaoyan_mall_comment where value_id = #{valueId} and type = #{type}")
    long getComments(@Param("valueId")int valueId, @Param("type")int type);

    @Select("select count(id) from cskaoyan_mall_comment where value_id = #{valueId} and type = #{type} and has_picture = 1")
    long getPicComments(@Param("valueId")int valueId, @Param("type")int type);

    @Select("select * from cskaoyan_mall_comment where value_id = #{valueId} and type = #{type}")
    List<Comment> getCommentsList(@Param("valueId")int valueId, @Param("type")int type);

    @Select("select * from cskaoyan_mall_comment where value_id = #{valueId} and type = #{type} and has_picture = 1")
    List<Comment> getPicCommentsList(@Param("valueId")int valueId, @Param("type")int type);
}