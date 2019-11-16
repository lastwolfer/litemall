package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Storage;
import org.apache.ibatis.annotations.Param;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 14:06
 */

public interface StorageMapper {
    void insertStorage(@Param("storage") Storage storage);

}
