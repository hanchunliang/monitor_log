package com.sinosoft.one.monitor.application.model;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 方法追踪日志.
 * User: carvin
 * Date: 12-11-28
 * Time: 上午6:12
 */
@Entity
@Table(name = "GE_MONITOR_METHOD_TRACE_LOG")
public class MethodTraceLog {
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
    private Timestamp beginTime;
	/**
	 * 结束时间
	 */
    private Timestamp endTime;
	/**
	 * 花费时间
	 */
    private long consumeTime;
	/**
	 * 记录时间
	 */
    private Timestamp recordTime;
	/**
	 * 方法ID
	 */
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

	@Column(name = "URL_TRACE_LOG_ID")
    public String getUrlTraceLogId() {
        return urlTraceLogId;
    }

    public void setUrlTraceLogId(String urlTraceLogId) {
        this.urlTraceLogId = urlTraceLogId;
    }

	@Column(name = "METHOD_NAME")
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

	@Column(name = "CLASS_NAME")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

	@Column(name = "IN_PARAM")
    public String getInParam() {
        return inParam;
    }

    public void setInParam(String inParam) {
        this.inParam = inParam;
    }

	@Column(name = "OUT_PARAM")
    public String getOutParam() {
        return outParam;
    }

    public void setOutParam(String outParam) {
        this.outParam = outParam;
    }

	@Column(name = "BEGIN_TIME")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

	@Column(name = "END_TIME")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

	@Column(name = "CONSUME_TIME")
    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }

	@Column(name = "RECORD_TIME")
    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

	@Transient
    public String getFullMethodName() {
        return getClassName() + "." + getMethodName();
    }

	@Column(name = "METHOD_ID")
	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("urlTraceLogId", urlTraceLogId)
            .append("methodName", methodName)
            .append("className", className)
            .append("inParam", inParam)
            .append("outParam", outParam)
            .append("beginTime", beginTime)
            .append("endTime", endTime)
            .append("consumeTime", consumeTime)
            .append("recordTime", recordTime)
            .append("methodId", methodId)
            .build();
    }
}
