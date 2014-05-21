package com.sinosoft.one.monitor.application.domain;

/**
 * ApplicationEmuException
 * User: cq
 * Date: 13-3-6
 * Time: PM3:04
 */
class ApplicationEmuException extends RuntimeException{
    public ApplicationEmuException(String message) {
        super(message);
    }

    public ApplicationEmuException() {
        super();
    }
}
