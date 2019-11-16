package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Storage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorageMapper {

    List<Storage> selectStorage(@Param("sort") String sort,@Param("order") String order,
                                @Param("key") String key,@Param("name") String name);

    int updateStorage(@Param("storage") Storage storage);

    int deleteStorage(@Param("storage") Storage storage);
}
