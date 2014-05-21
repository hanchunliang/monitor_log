package com.sinosoft.monitor.facade.model;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-29
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class Url {
    private String urlId;
    private String url;
    private List<Method> subMethods;

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Method> getSubMethods() {
        return subMethods == null ? Collections.EMPTY_LIST:subMethods;
    }

    public void setSubMethods(List<Method> subMethods) {
        this.subMethods = subMethods;
    }
}
