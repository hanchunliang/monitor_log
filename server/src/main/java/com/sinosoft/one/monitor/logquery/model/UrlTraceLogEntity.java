package com.sinosoft.one.monitor.logquery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-2
 * Time: 下午10:37
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="GE_MONITOR_URL_TRACE_LOG")
public class UrlTraceLogEntity {

    /**
     * 主键ID
     */
    private String id;
    /**
     * URL地址
     */
    private String url;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 花费时间
     */
    private long consumeTime;
    /**
     * 会话ID
     */
    private String sessionId;
    /**
     * 用户IP
     */
    private String userIp;
    /**
     * 请求参数信息
     */
    private String requestParams;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 业务场景追踪ID
     */
    private String traceId;
    /**
     * 所属业务场景ID
     */
    private String bizScenarioId;
    /**
     * 告警信息ID
     */
    private String alarmId;
    /**
     * 所属应用ID
     */
    private String applicationId;
    /**
     * URL信息ID
     */
    private String urlId;
    /**
     * 日志记录时间
     */
    private Date recordTime;

    @Id
    @Column(name="ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="URL_ID")
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Column(name="RECORD_TIME")
    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @Column(name="USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="APPLICATION_ID")
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Column(name="URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name="BEGIN_TIME")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name="END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name="CONSUME_TIME")
    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }

    @Column(name="SESSION_ID")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Column(name="USERIP")
    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @Column(name="REWUEST_PAEAMS")
    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    @Column(name="USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name="TRACE_ID")
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Column(name="BIZ_SCENARIO_ID")
    public String getBizScenarioId() {
        return bizScenarioId;
    }

    public void setBizScenarioId(String bizScenarioId) {
        this.bizScenarioId = bizScenarioId;
    }

    @Column(name="ALARM_ID")
    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }
}
