package com.sinosoft.monitor.agent.store.model.url;

import java.io.Serializable;
import java.util.Date;

public class MethodTraceLog implements Serializable {
	public static final long serialVersionUID = 2L;
	/**
	 * 主键ID
	 */
    private String id;
	/**
	 * URL追踪ID
	 */
    private String urlTraceLogId;
	/**
	 * 方法名
	 */
    private String methodName;
	/**
	 * 方法所属类名
	 */
    private String className;
	/**
	 * 方法参数
	 */
    private String inParam;
	/**
	 * 方法返回值
	 */
    private String outParam;
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
	 * 记录时间
	 */
    private Date recordTime;
	/**
	 * 方法ID
	 */
	private String methodId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlTraceLogId() {
        return urlTraceLogId;
    }

    public void setUrlTraceLogId(String urlTraceLogId) {
        this.urlTraceLogId = urlTraceLogId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInParam() {
        return inParam;
    }

    public void setInParam(String inParam) {
        this.inParam = inParam;
    }

    public String getOutParam() {
        return outParam;
    }

    public void setOutParam(String outParam) {
        this.outParam = outParam;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getFullMethodName() {
        return getClassName() + "." + getMethodName();
    }

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
//
//	@Override
//    public String toString() {
//        return new StringBuilder(this.toString())
//            .append("id").append(id)
//            .append("urlTraceLogId").append(urlTraceLogId)
//            .append("methodName").append(methodName)
//            .append("className").append(className)
//            .append("inParam").append(inParam)
//            .append("outParam").append(outParam)
//            .append("beginTime").append(beginTime)
//            .append("endTime").append(endTime)
//            .append("consumeTime").append(consumeTime)
//            .append("recordTime").append(recordTime)
//            .append("methodId").append(methodId).toString();
//    }
}
