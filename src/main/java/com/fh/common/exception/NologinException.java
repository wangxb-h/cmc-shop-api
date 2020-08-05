package com.fh.common.exception;

import com.fh.common.RedisUtil;

//自定义异常
public class NologinException extends Exception {

    public NologinException(String message){
            super(message);
    }
}
