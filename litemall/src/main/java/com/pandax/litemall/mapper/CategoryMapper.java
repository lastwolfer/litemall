package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Category;
import com.pandax.litemall.bean.CategoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category[] selectCategoryList();

    Category[] categoryL1();

    void deleteByPid(@Param("pid") Integer id);

    Category selectOneL1();

}
