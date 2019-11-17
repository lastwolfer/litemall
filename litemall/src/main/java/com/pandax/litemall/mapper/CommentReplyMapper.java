package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.CommentReply;
import com.pandax.litemall.bean.CommentReplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentReplyMapper {
    long countByExample(CommentReplyExample example);

    int deleteByExample(CommentReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentReply record);

    int insertSelective(CommentReply record);

    List<CommentReply> selectByExample(CommentReplyExample example);

    CommentReply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByExample(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByPrimaryKeySelective(CommentReply record);

    int updateByPrimaryKey(CommentReply record);

    CommentReply selectReply(Integer commentId);

    Integer selectCountByCommentId(Integer commentId);
}
