package com.sinosoft.one.monitor.application.model.viewmodel;

import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * URL 追踪日志展示信息类.
 * User: carvin
 * Date: 13-3-10
 * Time: 上午12:28
 */
public class UrlTraceLogViewModel {
	private String id;
	private String userIp;
	private String userId;
	private Date recordTime;
	private String status;
	private String operateStr = "";
	private String severity;
	private String exceptionId;
	private String recordTimeStr;
	private String statusStr = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		operateStr = "<a class=\"eid\" href=\"javascript:void(0);\" onclick=\"operateDetail('" + id + "')\">操作详细</a>";
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = StringUtils.isBlank(userId) ? "未知" : userId;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
		recordTimeStr = DateFormatUtils.format(recordTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperateStr() {
		return operateStr;
	}

	public void setOperateStr(String operateStr) {
		this.operateStr = operateStr;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
		if(this.status == null) {
			if(severity == null || SeverityLevel.INFO.equals(severity)) {
				this.status = SeverityLevel.INFO.name();
			} else {
				this.status = severity;
			}
		}
		statusStr = "<div class=\"" + evalStatusCss() + "\">&nbsp;</div>";
	}

	private String evalStatusCss() {
		String statusCss = "";
		if(SeverityLevel.INFO.name().equals(status)) {
			statusCss = "green_status";
		} else if(SeverityLevel.CRITICAL.name().equals(status)) {
			statusCss = "red_status";
		} else if(SeverityLevel.WARNING.name().equals(status)) {
			statusCss = "yellow_status";
		} else {
			statusCss = "green_status";
		}
		return statusCss;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
		if(!SeverityLevel.CRITICAL.name().equals(this.status) && exceptionId != null) {
			this.status = SeverityLevel.CRITICAL.name();
		}
		statusStr = "<div class=\"" + evalStatusCss() + "\">&nbsp;</div>";
	}
}
