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
 * OsDisk.

 */
@Entity
@Table(name="GE_MONITOR_OS_DISK"
)
public class OsDisk  implements java.io.Serializable {

    /**
        */
    private String id;
    /**
        */
    private Os os;
    /**
        */
    private String diskPath;
    /**
        */
    private String total;
    /**
        */
    private String used;
    /**
        */
    private String free;
    /**
        */
    private String usedUtiliZation;
    /**
        */
    private String freeUtiliZation;
    
    
    private String totalUtiliZation;
    /**
        */
    private Date sampleDate;
    
    /**
     */
    private String mountPoint;

    public OsDisk() {
    }

	
    public OsDisk(String id, Os os) {
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
    
    @Column(name="DISK_PATH", length=32)
    public String getDiskPath() {
    return this.diskPath;
    }

    public void setDiskPath(String diskPath) {
    this.diskPath = diskPath;
    }
    
    @Column(name="TOTAL", length=20)
    public String getTotal() {
    return this.total;
    }

    public void setTotal(String total) {
    this.total = total;
    }
    
    @Column(name="USED", length=20)
    public String getUsed() {
    return this.used;
    }

    public void setUsed(String used) {
    this.used = used;
    }
    
    @Column(name="FREE", length=20)
    public String getFree() {
    return this.free;
    }

    public void setFree(String free) {
    this.free = free;
    }
    
    @Column(name="USED_UTILI_ZATION", length=20)
    public String getUsedUtiliZation() {
    return this.usedUtiliZation;
    }

    public void setUsedUtiliZation(String usedUtiliZation) {
    this.usedUtiliZation = usedUtiliZation;
    }
    
    @Column(name="FREE_UTILI_ZATION", length=20)
    public String getFreeUtiliZation() {
    return this.freeUtiliZation;
    }

    public void setFreeUtiliZation(String freeUtiliZation) {
    this.freeUtiliZation = freeUtiliZation;
    }
    @Column(name="SAMPLE_DATE")
    public Date getSampleDate() {
    return this.sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
    this.sampleDate = sampleDate;
    }

    @Column(name="MOUNT_POINT", length=20)
	public String getMountPoint() {
	return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
	this.mountPoint = mountPoint;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	 @Column(name="TOTAL_UTILI_ZATION", length=20)
	public String getTotalUtiliZation() {
		return totalUtiliZation;
	}


	public void setTotalUtiliZation(String totalUtiliZation) {
		this.totalUtiliZation = totalUtiliZation;
	}
	
	

}


