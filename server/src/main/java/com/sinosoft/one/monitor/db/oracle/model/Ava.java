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
 * Ava.
* ORACLE可用性临时表
 */
@Entity
@Table(name="GE_MONITOR_ORACLE_AVA"
)
public class Ava  implements java.io.Serializable {

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
    * 记录时间.
    */
    private Date recordTime;
    /**
    * 0.不可用,1.可用.
    */
    private String state;
    /**
     * 轮询时间
     */
    private long interval;

    public Ava() {
    }

	
    public Ava(String id) {
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
    @Column(name="INTERVAL")
    public long getInterval() {
		return interval;
	}
    public void setInterval(long interval) {
		this.interval = interval;
	}
    @Column(name="RECORD_TIME")
    public Date getRecordTime() {
    return this.recordTime;
    }

    public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
    }
    
    @Column(name="STATE", length=1)
    public String getState() {
    return this.state;
    }

    public void setState(String state) {
    this.state = state;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


