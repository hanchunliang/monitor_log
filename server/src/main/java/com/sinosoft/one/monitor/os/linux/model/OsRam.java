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
 * OsRam.

 */
@Entity
@Table(name="GE_MONITOR_OS_RAM"
)
public class OsRam  implements java.io.Serializable {

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
    private String memTotal;
    /**
        */
    private String memUsed;
    /**
        */
    private String memUtiliZation;
    /**
        */
    private String swapTotal;
    /**
        */
    private String swapUsed;
    /**
        */
    private String swapUtiliZation;

    public OsRam() {
    }

	
    public OsRam(String id, Os os) {
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
    public Date getSampleDate() {
    return this.sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
    this.sampleDate = sampleDate;
    }
    
    @Column(name="MEM_TOTAL", length=20)
    public String getMemTotal() {
    return this.memTotal;
    }

    public void setMemTotal(String memTotal) {
    this.memTotal = memTotal;
    }
    
    @Column(name="MEM_USED", length=20)
    public String getMemUsed() {
    return this.memUsed;
    }

    public void setMemUsed(String memUsed) {
    this.memUsed = memUsed;
    }
    
    @Column(name="MEM_UTILI_ZATION", length=8)
    public String getMemUtiliZation() {
    return this.memUtiliZation;
    }

    public void setMemUtiliZation(String memUtiliZation) {
    this.memUtiliZation = memUtiliZation;
    }
    
    @Column(name="SWAP_TOTAL", length=20)
    public String getSwapTotal() {
    return this.swapTotal;
    }

    public void setSwapTotal(String swapTotal) {
    this.swapTotal = swapTotal;
    }
    
    @Column(name="SWAP_USED", length=20)
    public String getSwapUsed() {
    return this.swapUsed;
    }

    public void setSwapUsed(String swapUsed) {
    this.swapUsed = swapUsed;
    }
    
    @Column(name="SWAP_UTILI_ZATION", length=8)
    public String getSwapUtiliZation() {
    return this.swapUtiliZation;
    }

    public void setSwapUtiliZation(String swapUtiliZation) {
    this.swapUtiliZation = swapUtiliZation;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


