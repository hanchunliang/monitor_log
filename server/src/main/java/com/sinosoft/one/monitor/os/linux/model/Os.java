package com.sinosoft.one.monitor.os.linux.model;
// Generated 2013-2-27 21:43:48 by One Data Tools 1.0.0


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * Os.

 */
@Entity
@Table(name="GE_MONITOR_OS"
)
public class Os  implements java.io.Serializable {

    /**
        */
    private String osInfoId;
    /**
        */
    private String name;
    /**
        */
    private String type;
    /**
        */
    private String ipAddr;
    /**
        */
    private String subnetMask;
    /**
        */
    private int intercycleTime;
    /**
        */
    private List<OsCpu> osCpus = new ArrayList<OsCpu>(0) ;
    /**
        */
    private List<OsAvailabletemp> osAvailabletemps = new ArrayList<OsAvailabletemp>(0) ;
    /**
        */
    private List<OsAvailable> osAvailabiles = new ArrayList<OsAvailable>(0) ;
    /**
        */
    private List<OsRam> osRams = new ArrayList<OsRam>(0) ;
    /**
        */
    private List<OsDisk> osDisks = new ArrayList<OsDisk>(0) ;

    public Os() {
    }

	
    public Os(String osInfoId) {
        this.osInfoId = osInfoId;
    }
   
    @Id 
    @Column(name="OS_INFO_ID", unique=true, length=32)
    @GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getOsInfoId() {
    return this.osInfoId;
    }

    public void setOsInfoId(String osInfoId) {
    this.osInfoId = osInfoId;
    }
    
    @Column(name="NAME", length=16)
    public String getName() {
    return this.name;
    }

    public void setName(String name) {
    this.name = name;
    }
    
    @Column(name="TYPE", length=16)
    public String getType() {
    return this.type;
    }

    public void setType(String type) {
    this.type = type;
    }
    
    @Column(name="IP_ADDR", length=16)
    public String getIpAddr() {
    return this.ipAddr;
    }

    public void setIpAddr(String ipAddr) {
    this.ipAddr = ipAddr;
    }
    
    @Column(name="SUBNET_MASK", length=16)
    public String getSubnetMask() {
    return this.subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
    this.subnetMask = subnetMask;
    }
    
    @Column(name="INTERCYCLE_TIME", length=10)
    public int getIntercycleTime() {
    return this.intercycleTime;
    }

    public void setIntercycleTime(int intercycleTime) {
    this.intercycleTime = intercycleTime;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="os")
    public List<OsCpu> getOsCpus() {
    return this.osCpus;
    }

    public void setOsCpus(List<OsCpu> osCpus) {
    this.osCpus = osCpus;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="os")
    public List<OsAvailabletemp> getOsAvailabletemps() {
    return this.osAvailabletemps;
    }

    public void setOsAvailabletemps(List<OsAvailabletemp> osAvailabletemps) {
    this.osAvailabletemps = osAvailabletemps;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="os")
    public List<OsAvailable> getOsAvailabiles() {
    return this.osAvailabiles;
    }

    public void setOsAvailabiles(List<OsAvailable> osAvailabiles) {
    this.osAvailabiles = osAvailabiles;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="os")
    public List<OsRam> getOsRams() {
    return this.osRams;
    }

    public void setOsRams(List<OsRam> osRams) {
    this.osRams = osRams;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="os")
    public List<OsDisk> getOsDisks() {
    return this.osDisks;
    }

    public void setOsDisks(List<OsDisk> osDisks) {
    this.osDisks = osDisks;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


