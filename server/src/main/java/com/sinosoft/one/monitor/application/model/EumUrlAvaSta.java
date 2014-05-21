package com.sinosoft.one.monitor.application.model;
// Generated 2013-3-4 15:45:30 by One Data Tools 1.0.0


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * EumUrlAvaSta.
 * 业务仿真URL可用性统计
 * 按照天为维度，记录具体该URL一天的可用性的统计信息
 */
@Entity
@Table(name = "GE_MONITOR_EUM_URL_AVA_STA")
public class EumUrlAvaSta implements java.io.Serializable {

    /**
     * 主键ID.
     */
    private String id;
    /**
     * 业务仿真ID.
     */
    private String eumUrlId;
    /**
     * 正常运行时间.
     */
    private BigDecimal normalRuntime;
    /**
     * 总失败时间.
     */
    private BigDecimal totalFailureTime;
    /**
     * 停止次数.
     */
    private BigDecimal failureCount;
    /**
     * 平均故障间隔时间.
     */
    private BigDecimal avgFailureTime;
    /**
     * 记录时间.
     */
    private Date recordTime;

    public EumUrlAvaSta() {
    }


    public EumUrlAvaSta(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "ID", unique = true, length = 32)
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

    @Column(name = "NORMAL_RUNTIME", precision = 22, scale = 0)
    public BigDecimal getNormalRuntime() {
        return this.normalRuntime;
    }

    public void setNormalRuntime(BigDecimal normalRuntime) {
        this.normalRuntime = normalRuntime;
    }

    @Column(name = "TOTAL_FAILURE_TIME", precision = 22, scale = 0)
    public BigDecimal getTotalFailureTime() {
        return this.totalFailureTime;
    }

    public void setTotalFailureTime(BigDecimal totalFailureTime) {
        this.totalFailureTime = totalFailureTime;
    }

    @Column(name = "FAILURE_COUNT", precision = 22, scale = 0)
    public BigDecimal getFailureCount() {
        return this.failureCount;
    }

    public void setFailureCount(BigDecimal failureCount) {
        this.failureCount = failureCount;
    }

    @Column(name = "AVG_FAILURE_TIME", precision = 22, scale = 0)
    public BigDecimal getAvgFailureTime() {
        return this.avgFailureTime;
    }

    public void setAvgFailureTime(BigDecimal avgFailureTime) {
        this.avgFailureTime = avgFailureTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "RECORD_TIME", length = 7)
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


