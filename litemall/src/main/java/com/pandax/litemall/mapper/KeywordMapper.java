package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Keyword;
import com.pandax.litemall.bean.KeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface KeywordMapper {
    long countByExample(KeywordExample example);

    int deleteByExample(KeywordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Keyword record);

    int insertSelective(Keyword record);

    List<Keyword> selectByExample(KeywordExample example);

    Keyword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByExample(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByPrimaryKeySelective(Keyword record);

    int updateByPrimaryKey(Keyword record);

    List<Keyword> selectByCondition(@Param("keyword") String keyword,@Param("url") String url, @Param("sort") String sort, @Param("order") String order);

    @Select("select * from cskaoyan_mall_keyword where is_default = 1")
    Keyword selectDefault();

    @Select("select * from cskaoyan_mall_keyword where is_hot = 1")
    List<Keyword> selectHot();

    @Select("select keyword from cskaoyan_mall_keyword where keyword like concat('%', #{keyword},'%') ")
    String[] getHelper(String keyword);



}
