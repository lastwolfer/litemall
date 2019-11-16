package com.pandax.litemall.service;

<<<<<<< HEAD
=======
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
>>>>>>> 61656379866a0d62eaae441f0e44ba083682aed5
import com.pandax.litemall.bean.Storage;
import com.pandax.litemall.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 14:05
 */
=======

import java.util.HashMap;
import java.util.List;
>>>>>>> 61656379866a0d62eaae441f0e44ba083682aed5

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;

<<<<<<< HEAD

    @Override
    public int saveStorage(Storage storage) {
        storageMapper.insertStorage(storage);
        return storage.getId();
=======
    @Override
    public HashMap<String, Object> queryStorage(Integer page, Integer limit, String sort, String order,String key,String name) {
        PageHelper.startPage(page, limit);
        HashMap<String, Object> map = new HashMap<>();
        if(key!=null){
            key = "%" + key + "%";
        }
        if(name!=null){
            name = "%" + name + "%";
        }
        List<Storage> storageList = storageMapper.selectStorage(sort,order,key,name);
        PageInfo<Storage> storagePageInfo = new PageInfo<>(storageList);
        map.put("items", storageList);
        map.put("total", storagePageInfo.getTotal());
        return map;
    }

    @Override
    public Storage updateStorage(Storage storage) {
        int updateStatus = storageMapper.updateStorage(storage);
        if(updateStatus!=-1){
            return storage;
        }
        return null;
    }

    @Override
    public int deleteStorage(Storage storage) {
        int deleteStatus = storageMapper.deleteStorage(storage);
        return deleteStatus;
>>>>>>> 61656379866a0d62eaae441f0e44ba083682aed5
    }
}
