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
import org.hibernate.annotations.Type;

/**
 * OsCpu.

 */
@Entity
@Table(name="GE_MONITOR_OS_CPU"
)
public class OsCpu  implements java.io.Serializable {

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
    private String utiliZation;
    /**
        */
    private String runQueue;
    /**
        */
    private String blockProcess;
    /**
        */
    private String userTime;
    /**
        */
    private String sysTime;
    /**
        */
    private String ioWait;
    /**
        */
    private String cpuIdle;
    /**
        */
    private String interRupt;

    public OsCpu() {
    }

	
    public OsCpu(String id, Os os) {
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
    @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="OS_INFO_ID")
    public Os getOs() {
    return this.os;
    }

    public void setOs(Os os) {
    this.os = os;
    }
    @Column(name="SAMPLE_DATE")
    @Temporal(TemporalType.TIMESTAMP) 
    public Date getSampleDate() {
    return this.sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
    this.sampleDate = sampleDate;
    }
    
    @Column(name="UTILI_ZATION", length=10)
    public String getUtiliZation() {
    return this.utiliZation;
    }

    public void setUtiliZation(String utiliZation) {
    this.utiliZation = utiliZation;
    }
    
    @Column(name="RUN_QUEUE", length=10)
    public String getRunQueue() {
    return this.runQueue;
    }

    public void setRunQueue(String runQueue) {
    this.runQueue = runQueue;
    }
    
    @Column(name="BLOCK_PROCESS", length=10)
    public String getBlockProcess() {
    return this.blockProcess;
    }

    public void setBlockProcess(String blockProcess) {
    this.blockProcess = blockProcess;
    }
    
    @Column(name="USER_TIME", length=10)
    public String getUserTime() {
    return this.userTime;
    }

    public void setUserTime(String userTime) {
    this.userTime = userTime;
    }
    
    @Column(name="SYS_TIME", length=10)
    public String getSysTime() {
    return this.sysTime;
    }

    public void setSysTime(String sysTime) {
    this.sysTime = sysTime;
    }
    
    @Column(name="IO_WAIT", length=10)
    public String getIoWait() {
    return this.ioWait;
    }

    public void setIoWait(String ioWait) {
    this.ioWait = ioWait;
    }
    
    @Column(name="CPU_IDLE", length=10)
    public String getCpuIdle() {
    return this.cpuIdle;
    }

    public void setCpuIdle(String cpuIdle) {
    this.cpuIdle = cpuIdle;
    }
    
    @Column(name="INTER_RUPT", length=10)
    public String getInterRupt() {
    return this.interRupt;
    }

    public void setInterRupt(String interRupt) {
    this.interRupt = interRupt;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


