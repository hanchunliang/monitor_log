package com.sinosoft.one.monitor.os.linux.model;
// Generated 2013-2-27 21:43:48 by One Data Tools 1.0.0


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * OsStati.

 */
@Entity
@Table(name="GE_MONITOR_OS_STATI"
)
public class OsStati  implements java.io.Serializable {

    /**
        */
    private String id;
    /**
        */
    private String osid;
    /**
        */
    private String type;
    /**
        */
    private Date recordTime;
    /**
        */
    private String minValue;
    /**
        */
    private String maxValue;
    /**
        */
    private String averageValue;

    public OsStati() {
    }

	
    public OsStati(String id) {
        this.id = id;
    }
   
    @Id 
    
    @Column(name="ID", unique=true, length=32)
    @GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="OSID", length=32)
    public String getOsid() {
    return this.osid;
    }

    public void setOsid(String osid) {
    this.osid = osid;
    }
    
    @Column(name="TYPE", length=2)
    public String getType() {
    return this.type;
    }

    public void setType(String type) {
    this.type = type;
    }
    @Column(name="RECORD_TIME")
    public Date getRecordTime() {
    return this.recordTime;
    }

    public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
    }
    
    @Column(name="MIN_VALUE")
    public String getMinValue() {
    return this.minValue;
    }

    public void setMinValue(String minValue) {
    this.minValue = minValue;
    }
    
    @Column(name="MAX_VALUE")
    public String getMaxValue() {
    return this.maxValue;
    }

    public void setMaxValue(String maxValue) {
    this.maxValue = maxValue;
    }
    
    @Column(name="AVERAGE_VALUE")
    public String getAverageValue() {
    return this.averageValue;
    }

    public void setAverageValue(String averageValue) {
    this.averageValue = averageValue;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


