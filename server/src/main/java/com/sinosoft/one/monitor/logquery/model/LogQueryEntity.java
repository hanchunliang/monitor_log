package com.sinosoft.one.monitor.logquery.model;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-10-31
 * Time: 下午3:46
 * To change this template use File | Settings | File Templates.
 */
public class LogQueryEntity {
    //操作信息
    public String optInfo;
    //应用名称
    public String appName;
    //用户名
    public String userName;
    //用户IP
    public String userIp;
    //时间
    public String recordTime;

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getOptInfo() {
        return optInfo;
    }

    public void setOptInfo(String optInfo) {
        this.optInfo = optInfo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
}
