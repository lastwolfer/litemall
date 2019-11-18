package com.pandax.litemall.exception;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/18
 * @time 16:26
 */

public class AdminException extends Exception{

    private int errorNo;

    public AdminException() {
    }

    public AdminException(int errorNo, String message) {
        super(message);
        this.errorNo = errorNo;
    }

    public int getErrorNo() {
        return errorNo;
    }
}
