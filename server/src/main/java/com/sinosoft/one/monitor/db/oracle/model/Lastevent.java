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
 * Lastevent.
* ORACLE最近一小时事件记录
 */
@Entity
@Table(name="GE_MONITOR_ORACLE_LASTEVENT"
)
public class Lastevent  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
    * 主键ID.
    */
    private String id;
    /**
    * 数据库ID.
    */
    private String databaseId;
    /**
    * 活动连接数.
    */
    private int activeCount;
    /**
    * 连接时间.
    */
    private long connectTime;
    /**
    * 缓冲区命中率.
    */
    private Double bufferHitRate;
    /**
    * 数据字典命中率.
    */
    private Double dickHitRate;
    /**
    * 缓存库命中率.
    */
    private Double bufferLibHitRate;
    /**
    * 记录时间.
    */
    private Date recordTime;

    public Lastevent() {
    }

	
    public Lastevent(String id) {
        this.id = id;
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
    
    @Column(name="ACTIVE_COUNT")
    public int getActiveCount() {
    return this.activeCount;
    }

    public void setActiveCount(int activeCount) {
    this.activeCount = activeCount;
    }
    
    @Column(name="CONNECT_TIME")
    public long getConnectTime() {
    return this.connectTime;
    }

    public void setConnectTime(long connectTime) {
    this.connectTime = connectTime;
    }
    
    @Column(name="BUFFER_HIT_RATE")
    public Double getBufferHitRate() {
    return this.bufferHitRate;
    }

    public void setBufferHitRate(Double bufferHitRate) {
    this.bufferHitRate = bufferHitRate;
    }
    
    @Column(name="DICK_HIT_RATE")
    public Double getDickHitRate() {
    return this.dickHitRate;
    }

    public void setDickHitRate(Double dickHitRate) {
    this.dickHitRate = dickHitRate;
    }
    
    @Column(name="BUFFER_LIB_HIT_RATE")
    public Double getBufferLibHitRate() {
    return this.bufferLibHitRate;
    }

    public void setBufferLibHitRate(Double bufferLibHitRate) {
    this.bufferLibHitRate = bufferLibHitRate;
    }
    @Column(name="RECORD_TIME")
    public Date getRecordTime() {
    return this.recordTime;
    }

    public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


