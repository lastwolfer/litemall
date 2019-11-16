package com.pandax.litemall.service;

import com.pandax.litemall.bean.Storage;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 14:05
 */

public interface StorageService {

    int saveStorage(Storage storage);
}
