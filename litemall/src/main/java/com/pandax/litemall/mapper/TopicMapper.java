package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Topic;
import com.pandax.litemall.bean.TopicExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TopicMapper {
    long countByExample(TopicExample example);

    int deleteByExample(TopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    List<Topic> selectByExampleWithBLOBs(TopicExample example);

    List<Topic> selectByExample(TopicExample example);

    Topic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByExampleWithBLOBs(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByExample(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKeyWithBLOBs(Topic record);

    int updateByPrimaryKey(Topic record);

    int selectLastInsert();

    @Select("select * from cskaoyan_mall_topic where id = #{id}")
    Topic getTopicDetail(int id);


    //"select *,pic_url as picUrl from cskaoyan_mall_topic where id in (#{array})"
    @Select({"<script>",
            "SELECT *,pic_url as picUrl FROM cskaoyan_mall_topic",
            "WHERE id in",
            "<foreach item=\"item\" collection=\"array\" open=\"(\"  close=\")\" separator=\",\">",
            "#{item}",
            "</foreach>",
            "</script>"})
    List<Topic> getTopicRelated(int[] r);

    @Select("select *,pic_url as picUrl from cskaoyan_mall_topic")
    List<Topic> getTopic();

    @Select("select id from cskaoyan_mall_topic")
    List<Integer> getTopicIdList();
}
