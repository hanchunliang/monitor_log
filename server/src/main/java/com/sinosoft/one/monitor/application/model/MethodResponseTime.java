package com.sinosoft.one.monitor.application.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 方法响应时间.
 * User: carvin
 * Date: 13-3-8
 * Time: 下午8:03
 *
 */
@Entity
@Table(name = "GE_MONITOR_METHOD_RESPONSETIME")
public class MethodResponseTime {
	private String id;
	private String methodName;
	private String urlId;
	private Long minResponseTime = 0l;
	private Long maxResponseTime = 0l;
	private Long totalResponseTime = 0l;
	private Integer totalCount = 0;

	private long avgResponseTime = 0l;

	private Date recordTime;
	private String applicationId;
	private String methodId;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "METHOD_NAME")
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "URL_ID")
	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	@Column(name = "MIN_RESPONSE_TIME")
	public Long getMinResponseTime() {
		return minResponseTime == null ? 0l : minResponseTime;
	}

	public void setMinResponseTime(Long minResponseTime) {
		this.minResponseTime = minResponseTime;
	}

	@Column(name = "MAX_RESPONSE_TIME")
	public Long getMaxResponseTime() {
		return maxResponseTime == null ? 0l : maxResponseTime;
	}

	public void setMaxResponseTime(Long maxResponseTime) {
		this.maxResponseTime = maxResponseTime;
	}

	@Transient
	public long getAvgResponseTime() {
		return BigDecimal.valueOf(totalResponseTime)
				.divide(BigDecimal.valueOf(totalCount == 0 ? 1 : totalCount), 0, RoundingMode.HALF_UP).longValue();
	}

	public void setAvgResponseTime(long avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "APPLICATION_ID")
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name = "TOTAL_RESPONSE_TIME")
	public Long getTotalResponseTime() {
		return totalResponseTime == null ? 0l : totalResponseTime;
	}

	public void setTotalResponseTime(Long totalResponseTime) {
		this.totalResponseTime = totalResponseTime;
	}

	@Column(name = "TOTAL_COUNT")
	public Integer getTotalCount() {
		return totalCount == null ? 0 : totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Column(name = "METHOD_ID")
	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public void increaseTotalCount() {
		totalCount++;
	}

    public void increaseTotalCount(int plusElement) {
        totalCount+=plusElement;
    }

	public void addTotalResponseTime(long responseTime) {
		totalResponseTime += responseTime;
	}
}
