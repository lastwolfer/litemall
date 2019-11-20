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

    @Select("select count(id) from cskaoyan_mall_comment where value_id = #{arg[0]} and type = #{arg[1]}")
    long getComments(int valueId, int type);

    @Select("select count(id) from cskaoyan_mall_comment where value_id = #{arg[0]} and type = #{arg[1]} and has_picture = 1")
    long getPicComments(int valueId, int type);

    @Select("select * from cskaoyan_mall_comment where value_id = #{arg[0]} and type = #{arg[1]}")
    List<Comment> getCommentsList(int valueId, int type);

    @Select("select * from cskaoyan_mall_comment where value_id = #{arg[0]} and type = #{arg[1]} and has_picture = 1")
    List<Comment> getPicCommentsList(int valueId, int type);
}