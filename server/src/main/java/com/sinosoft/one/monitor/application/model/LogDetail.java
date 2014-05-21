package com.sinosoft.one.monitor.application.model;

/**
 * Intro:
 * User: Kylin
 * Date: 13-3-9
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
public class LogDetail {

    private String id;
    private String urlOrMethod;
    private long consumeTime;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlOrMethod() {
        return urlOrMethod;
    }

    public void setUrlOrMethod(String urlOrMethod) {
        this.urlOrMethod = urlOrMethod;
    }

    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
