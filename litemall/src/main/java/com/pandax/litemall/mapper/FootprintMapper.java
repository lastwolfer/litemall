package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Footprint;
import com.pandax.litemall.bean.FootprintExample;
import java.util.List;

import com.pandax.litemall.bean.FootprintListBean;
import org.apache.ibatis.annotations.Param;

public interface FootprintMapper {
    long countByExample(FootprintExample example);

    int deleteByExample(FootprintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Footprint record);

    int insertSelective(Footprint record);

    List<Footprint> selectByExample(FootprintExample example);

    Footprint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByExample(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByPrimaryKeySelective(Footprint record);

    int updateByPrimaryKey(Footprint record);

    Footprint selectByGoodsId(Integer id);

    List<FootprintListBean> selectFootprint(Integer id);
}
