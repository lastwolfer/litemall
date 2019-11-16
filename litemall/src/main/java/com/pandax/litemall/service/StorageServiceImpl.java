package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Storage;
import com.pandax.litemall.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;


@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;



    @Override
    public int saveStorage(Storage storage) {
        storageMapper.insertStorage(storage);
        return storage.getId();
    }

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
    }
}
