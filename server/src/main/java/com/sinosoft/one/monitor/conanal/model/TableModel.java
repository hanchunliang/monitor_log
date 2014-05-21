package com.sinosoft.one.monitor.conanal.model;

import javax.persistence.Column;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-5
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TableModel {
    public String urlId;
    public String applicationId;
    public String url;
    public String applicationName;
    public String urlCount;

    @Column(name = "url_id")
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Column(name = "application_id")
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "application_name")
    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Column(name = "url_count")
    public String getUrlCount() {
        return urlCount;
    }

    public void setUrlCount(String urlCount) {
        this.urlCount = urlCount;
    }
}
