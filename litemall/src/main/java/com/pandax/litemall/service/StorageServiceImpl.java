package com.pandax.litemall.service;

import com.pandax.litemall.bean.Storage;
import com.pandax.litemall.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 14:05
 */

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;


    @Override
    public int saveStorage(Storage storage) {
        storageMapper.insertStorage(storage);
        return storage.getId();
    }
}
