package com.sinosoft.one.monitor.os.linux.model;
// Generated 2013-2-27 21:43:48 by One Data Tools 1.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * OsAvailabletemp.

 */
@Entity
@Table(name="GE_MONITOR_OS_AVAILABLETEMP"
)
public class OsAvailabletemp  implements java.io.Serializable {

    /**
        */
    private String id;
    /**
        */
    private Os os;
    /**
        */
    private Date sampleDate;
    /**
        */
    private String status;
    /**
     */
    private int intercycleTime;
    public OsAvailabletemp() {
    }

	
    public OsAvailabletemp(String id, Os os) {
        this.id = id;
        this.os = os;
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
    @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="OS_INFO_ID")
    public Os getOs() {
    return this.os;
    }

    public void setOs(Os os) {
    this.os = os;
    }
    @Column(name="SAMPLE_DATE")
    public Date getSampleDate() {
    return this.sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
    this.sampleDate = sampleDate;
    }
    
    @Column(name="STATUS", length=2)
    public String getStatus() {
    return this.status;
    }

    public void setStatus(String status) {
    this.status = status;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Column(name="INTERCYCLE_TIME", length=2)
	public int getIntercycleTime() {
		return intercycleTime;
	}


	public void setIntercycleTime(int intercycleTime) {
		this.intercycleTime = intercycleTime;
	}
	

}


