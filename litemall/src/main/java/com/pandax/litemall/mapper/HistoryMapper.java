package com.pandax.litemall.mapper;


import com.pandax.litemall.bean.History;
import com.pandax.litemall.bean.HistoryExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface HistoryMapper {
    long countByExample(HistoryExample example);

    int deleteByExample(HistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(History record);

    int insertSelective(History record);

    List<History> selectByExample(HistoryExample example);

    History selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") History record, @Param("example") HistoryExample example);

    int updateByExample(@Param("record") History record, @Param("example") HistoryExample example);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);

    @Delete("delete from cskaoyan_mall_search_history where user_id = #{id}")
    int deleleById(int id);

    @Select("select keyword from cskaoyan_mall_search_history where user_id = #{id}")
    String[] getHistoryList(int id);

    @Insert("insert into cskaoyan_mall_search_history value (null,#{id},#{keyword},'',now(),now(),null);")
    int addHistory(@Param("id") int id, @Param("keyword") String keyword);

    @Select("select id from cskaoyan_mall_search_history where user_id = #{id} and keyword = #{keyword}")
    Integer checkHistory(@Param("id") int id, @Param("keyword") String keyword);

    @Update("update cskaoyan_mall_search_history set update_time = now() where id = #{id}")
    int updateHistory(int id);
}