package com.pandax.litemall.mapper;


import com.pandax.litemall.bean.History;
import com.pandax.litemall.bean.HistoryExample;
import org.apache.ibatis.annotations.Param;

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
}