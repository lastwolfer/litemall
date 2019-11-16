package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Storage;
import org.apache.ibatis.annotations.Param;

<<<<<<< HEAD
/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 14:06
 */

public interface StorageMapper {
    void insertStorage(@Param("storage") Storage storage);

=======
import java.util.List;

public interface StorageMapper {

    List<Storage> selectStorage(@Param("sort") String sort,@Param("order") String order,
                                @Param("key") String key,@Param("name") String name);

    int updateStorage(@Param("storage") Storage storage);

    int deleteStorage(@Param("storage") Storage storage);
>>>>>>> 61656379866a0d62eaae441f0e44ba083682aed5
}
