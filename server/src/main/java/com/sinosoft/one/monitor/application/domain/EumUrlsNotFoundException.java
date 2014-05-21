package com.sinosoft.one.monitor.application.domain;

/**
 * 获取不到eumUrl异常
 */
public class EumUrlsNotFoundException extends RuntimeException{


    public EumUrlsNotFoundException(String s) {
        super(s);
    }
}
