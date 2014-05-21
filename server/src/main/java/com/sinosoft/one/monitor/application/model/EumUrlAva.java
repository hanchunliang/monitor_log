package com.sinosoft.one.monitor.application.model;
// Generated 2013-3-4 15:45:30 by One Data Tools 1.0.0


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * EumUrlAva.
* 应用系统可用性临时表
 */
@Entity
@Table(name="GE_MONITOR_EUM_URL_AVA"
)
public class EumUrlAva  implements java.io.Serializable {

    /**
    * 主键ID.
    */
    private String id;

    /**
    * 业务仿真ID.
    */
    private String eumUrlId;

    /**
    * 状态 1---可用 0---不可用.
    */
    private String state;

    private BigDecimal interval;

    /**
    * 记录时间.
    */
    private Date recordTime;

    public EumUrlAva() {
    }

	
    public EumUrlAva(String id) {
        this.id = id;
    }
   
    @Id 
    @Column(name="ID", unique=true, length=32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name="EUM_URL_ID")
    public String getEumUrlId() {
        return this.eumUrlId;
    }

    public void setEumUrlId(String eumUrlId) {
        this.eumUrlId = eumUrlId;
    }
    
    @Column(name="STATE", length=1)
    public String getState() {
    return this.state;
    }

    public void setState(String state) {
    this.state = state;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RECORD_TIME", length=7)
    public Date getRecordTime() {
    return this.recordTime;
    }

    public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
    }

    @Column(name="INTERVAL", precision=22, scale=0)
    public BigDecimal getInterval() {
        return interval;
    }

    public void setInterval(BigDecimal interval) {
        this.interval = interval;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


