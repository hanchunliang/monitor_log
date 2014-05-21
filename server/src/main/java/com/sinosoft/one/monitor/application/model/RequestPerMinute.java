package com.sinosoft.one.monitor.application.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 每分钟请求数.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午4:34
 */
@Entity
@Table(name = "GE_MONITOR_REQUEST_PER_MINUTE")
public class RequestPerMinute {
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 所属应用系统ID
	 */
	private String applicationId;
	/**
	 * 请求数
	 */
	private int requestNumber;
	/**
	 * 记录日期
	 */
	private Date recordTime;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "APPLICATION_ID")
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name = "REQUEST_NUMBER")
	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void addRpm(int rpm) {
		this.requestNumber += rpm;
	}
}
