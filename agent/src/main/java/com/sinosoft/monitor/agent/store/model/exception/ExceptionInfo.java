package com.sinosoft.monitor.agent.store.model.exception;

import java.io.Serializable;
import java.util.Date;

public class ExceptionInfo implements Serializable {
	public static final long serialVersionUID = 3L;
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * URL追踪ID
	 */
	private String urlTraceId;
	/**
	 * 记录时间
	 */
	private Date recordTime;
	/**
	 * 异常描述
	 */
	private String exceptionDescription;
	/**
	 * 异常堆栈
	 */
	private String exceptionStackTrace;
	/**
	 * 告警信息ID
	 */
	private String alarmId;
	/**
	 * 应用系统ID
	 */
	private String applicationId;
	/**
	 * URL地址
	 */
	private String url;
	/**
	 * URL请求参数
	 */
	private String requestParams;

	/**
	 * URL ID
	 */
	private String urlId;


	public ExceptionInfo() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrlTraceId() {
		return urlTraceId;
	}

	public void setUrlTraceId(String urlTraceId) {
		this.urlTraceId = urlTraceId;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getExceptionDescription() {
		return exceptionDescription;
	}

	public void setExceptionDescription(String exceptionDescription) {
		this.exceptionDescription = exceptionDescription;
	}

	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
}
