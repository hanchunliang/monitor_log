package com.sinosoft.one.monitor.alarm.model;
// Generated 2013-3-1 10:29:53 by One Data Tools 1.0.0


import com.sinosoft.one.monitor.common.AlarmSource;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Alarm.
* 应用系统预警信息表
 */
@Entity
@Table(name="GE_MONITOR_ALARM")
//todo 需要将monitorId调整为resourceId
public class Alarm  implements java.io.Serializable {

    /**
    * 主键ID.
    */
    private String id;
    /**
    * 严重级别(severity).
    */
    private SeverityLevel severity;
    /**
    * 警报信息(message).
    */
    private String message;
    /**
    * 警报来源(日志,异常,OS,DB)
    */
    private AlarmSource alarmSource;
    /**
    * 监视器ID.
    */
    private String monitorId;
    /**
    * 监视器类型.
    */
    private String monitorType;
    /**
    * 属性ID.
    */
    private String attributeId;
    /**
    * 创建时间(createtime).
    */
    private Date createTime;
    /**
    * 所有者(ownername).
    */
    private String ownerName;
	/**
	 * 资源类型(resouceType).
	 */
	private ResourceType subResourceType;
	/**
	 * 资源ID(subResourceId).
	 */
	private String subResourceId;
    /**
     * 页面显示状态(status).
     */
    private String status;
    /**
     * 页面显示名称(status).
     */
    private String appName;
    /**
     * 页面显示的记录时间(recordTime).
     */
    private String recordTime;
	/**
	 * 资源名称
	 */
	private String resourceName;


    public Alarm() {
    }
	
    public Alarm(String id) {
        this.id = id;
    }
   
    @Id 
    @Column(name="id", unique=true, length=32)
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="severity", length=1)
    @Enumerated(value = EnumType.STRING)
    public SeverityLevel getSeverity() {
    return this.severity;
    }

    public void setSeverity(SeverityLevel severity) {
    this.severity = severity;
    }
    
    @Column(name="message", length=3000)
    public String getMessage() {
    return this.message;
    }

    public void setMessage(String message) {
    this.message = message;
    }
    
    @Column(name="alarm_source", length=20)
    @Enumerated(value = EnumType.STRING)
    public AlarmSource getAlarmSource() {
    return this.alarmSource;
    }

    public void setAlarmSource(AlarmSource alarmSource) {
    this.alarmSource = alarmSource;
    }
    
    @Column(name="monitor_id", length=32)
    public String getMonitorId() {
    return this.monitorId;
    }

    public void setMonitorId(String monitorId) {
    this.monitorId = monitorId;
    }
    
    @Column(name="monitor_type", length=50)
    public String getMonitorType() {
    return this.monitorType;
    }

    public void setMonitorType(String monitorType) {
    this.monitorType = monitorType;
    }
    
    @Column(name="attribute_id", length=32)
    public String getAttributeId() {
    return this.attributeId;
    }

    public void setAttributeId(String attributeId) {
    this.attributeId = attributeId;
    }

    @Column(name="create_time", length=7)
    public Date getCreateTime() {
    return this.createTime;
    }

    public void setCreateTime(Date createTime) {
    this.createTime = createTime;
    }
    
    @Column(name="owner_name", length=100)
    public String getOwnerName() {
    return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
    }

	@Enumerated(EnumType.STRING)
	@Column(name = "SUB_RESOURCE_TYPE")
	public ResourceType getSubResourceType() {
		return subResourceType;
	}

	public void setSubResourceType(ResourceType subResourceType) {
		this.subResourceType = subResourceType;
	}

	@Column(name = "SUB_RESOURCE_ID")
	public String getSubResourceId() {
		return subResourceId;
	}

	public void setSubResourceId(String subResourceId) {
		this.subResourceId = subResourceId;
	}

    @Transient
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Transient
    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Transient
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = StringUtils.isBlank(resourceName) ? "未知" : resourceName;
	}




	public boolean isSameAlarmInfo(Alarm alarm) {
		if (this == alarm) return true;
		if (alarm == null) return false;

		if (alarmSource != alarm.alarmSource) return false;
		if (attributeId != null ? !attributeId.equals(alarm.attributeId) : alarm.attributeId != null) return false;
		if (monitorId != null ? !monitorId.equals(alarm.monitorId) : alarm.monitorId != null) return false;
		if (monitorType != null ? !monitorType.equals(alarm.monitorType) : alarm.monitorType != null) return false;
		if (severity != alarm.severity) return false;
		
		if (subResourceType != alarm.subResourceType) return false;
		if (AlarmSource.EUM != alarmSource) {
			if (subResourceId != null ? !subResourceId.equals(alarm.subResourceId) : alarm.subResourceId != null)
				return false;
		}

		return true;
	}
}


