package com.sinosoft.one.monitor.db.oracle.model;
// Generated 2013-3-4 21:44:43 by One Data Tools 1.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * AvaSta.
* ORACLE可用性统计表
 */
@Entity
@Table(name="GE_MONITOR_ORACLE_AVA_STA"
)
public class AvaSta  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
    * 主键ID.
    */
    private String id;
    /**
    * 1数据库ID.
    */
    private String databaseId;
    /**
    * 2正常运行时间.
    */
    private long normalRuntime;
    /**
    * 3总停机时间.
    */
    private long totalPoweroffTime;
    /**
    * 4停机次数.
    */
    private long poweroffCount;
    /**
    * 5平均故障间隔时间.
    */
    private long avgFailureTime;
    /**
    * 6记录时间.
    */
    private Date avaRecordTime;
    /**
     * 7创建时间
     */
    private Date recordTime;
    /**
    * 8可用次数.
    */
    private long avCount;
    /**
     * 9不可用次数.
     */
    private long unavCount;
    
    /**
     * 10未知时间
     */
    private long unknowTime;
    

    public AvaSta() {
    }

	
    public AvaSta(String id) {
        this.id = id;
    }
    @Column(name="RECORD_TIME")
    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="ID", unique=true, length=32)
    public String getId() {
    return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="DATABASE_ID")
	public String getDatabaseId() {
		return databaseId;
	}
	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}
	@Column(name="UNKNOW_TIME")
    public long getUnknowTime() {
		return unknowTime;
	}
    public void setUnknowTime(long unknowTime) {
		this.unknowTime = unknowTime;
	}
    @Column(name="NORMAL_RUNTIME")
    public long getNormalRuntime() {
    return this.normalRuntime;
    }

    public void setNormalRuntime(long normalRuntime) {
    this.normalRuntime = normalRuntime;
    }
    
    @Column(name="TOTAL_POWEROFF_TIME")
    public long getTotalPoweroffTime() {
    return this.totalPoweroffTime;
    }

    public void setTotalPoweroffTime(long totalPoweroffTime) {
    this.totalPoweroffTime = totalPoweroffTime;
    }
    
    @Column(name="POWEROFF_COUNT")
    public long getPoweroffCount() {
    return this.poweroffCount;
    }

    public void setPoweroffCount(long poweroffCount) {
    this.poweroffCount = poweroffCount;
    }
    
    @Column(name="AVG_FAILURE_TIME")
    public long getAvgFailureTime() {
    return this.avgFailureTime;
    }

    public void setAvgFailureTime(long avgFailureTime) {
    this.avgFailureTime = avgFailureTime;
    }
    @Column(name="AVA_RECORD_TIME")
    public Date getAvaRecordTime() {
    return this.avaRecordTime;
    }

    public void setAvaRecordTime(Date avaRecordTime) {
    this.avaRecordTime = avaRecordTime;
    }
    

    @Column(name="AV_COUNT")
	public long getAvCount() {
		return avCount;
	}


	public void setAvCount(long avCount) {
		this.avCount = avCount;
	}

	 @Column(name="UNAV_COUNT")
	public long getUnavCount() {
		return unavCount;
	}


	public void setUnavCount(long unavCount) {
		this.unavCount = unavCount;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


