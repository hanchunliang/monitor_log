package com.sinosoft.monitor.facade.model;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-29
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    private  String applicationId;
    private  String applicationName;
    private  String ip;
    private  String port;
    private  List<Url> subUrls;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<Url> getSubUrls() {
        return subUrls == null ? Collections.EMPTY_LIST:subUrls;
    }

    public void setSubUrls(List<Url> subUrls) {
        this.subUrls = subUrls;
    }
}
