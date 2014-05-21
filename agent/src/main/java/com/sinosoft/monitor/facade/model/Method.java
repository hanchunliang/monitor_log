package com.sinosoft.monitor.facade.model;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-29
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class Method {
    private String methodId;
    private String method;
    private String methodClass;

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodClass() {
        return methodClass;
    }

    public void setMethodClass(String methodClass) {
        this.methodClass = methodClass;
    }
}
