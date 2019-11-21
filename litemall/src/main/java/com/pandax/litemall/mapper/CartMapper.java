package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Cart;
import com.pandax.litemall.bean.CartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    Cart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectAllCart(@Param("id")Integer id);

    void updateCheckedByProductId(@Param("productId") Integer productId, @Param("check") boolean check, @Param("id")Integer id);

    void deleteByProductId(@Param("productId") Integer productId, @Param("id")Integer id);

    void updateNumber(@Param("id")Integer id, @Param("number")Short number);
}
