package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.*;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<CategoryList> selectCategory(Integer value);

    List<Goods> selectGoodsByCategoryIds(@Param("categories") List<Category> categories);

    List<Goods> selectGoodsByFootprint(@Param("footprints") List<Footprint> footprints);

    @Select("select name from cskaoyan_mall_goods where name like concat('%', #{keyword},'%')")
    String[] getHelper(@Param("keyword") String keyword);
}
