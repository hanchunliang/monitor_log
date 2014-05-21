package com.sinosoft.one.monitor.application.model;
// Generated 2013-2-28 10:28:19 by One Data Tools 1.0.0


import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Application.
 * 存储需要监控的应用系统信息
 * TODO 是否需要考虑将1)EUMURL去除,，2)需要提炼出Resource的概念
 */

@Entity
@Table(name = "GE_MONITOR_APPLICATION")
public class Application implements java.io.Serializable {

    /**
     * 主键ID.
     */
    private String id;
    /**
     * 应用系统英文名称.
     */
    private String applicationName;
    /**
     * 应用系统中文名称.
     */
    private String cnName;
    /**
     * 应用IP
     */
    @NotEmpty(message = "主机IP不能为空")
    @Pattern(regexp = "[0-9.]{7,30}",message = "主机IP必须是数字和“.”的组合，长度应该在7到30位")
    private String applicationIp;
    /**
     * 应用端口
     */
    @NotEmpty(message = "端口不能为空")
    @Pattern(regexp = "[0-9]{1,5}",message = "端口必须是数字，长度1到5位")
    private String applicationPort;
    /**
     * 创建时间.
     */
    private Date createTime;
    /**
     * 创建人ID.
     */
    private String creatorId;
    /**
     * 修改时间.
     */
    private Date modifyTime;
    /**
     * 修改人ID.
     */
    private String modifierId;
    /**
     * 有效状态:1有效,0删除.
     */
    private String status;
    /**
     * 轮询间隔.
     */
    /*@NotNull(message = "轮询间隔不能为空")
    @Pattern(regexp = "[0-9]{1,10}",message = "轮询间隔必须是数字，长度1到10位")*/
    private BigDecimal interval;
    /**
     * 应用下的业务场景
     */
    private List<BizScenario> bizScenarios = new ArrayList<BizScenario>(0);

    /**
     * 仿真URL
     */
    private List<EumUrl> enumUrls = new ArrayList<EumUrl>(0);

    public Application() {
    }


    public Application(String id, String applicationName, Date createTime, String creatorId, String status) {
        this.id = id;
        this.applicationName = applicationName;
        this.createTime = createTime;
        this.creatorId = creatorId;
        this.status = status;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "application_name", length = 100)
    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Column(name = "cn_name", length = 300)
    public String getCnName() {
        return this.cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    @Column(name = "application_ip", length = 100)
    public String getApplicationIp() {
        return this.applicationIp;
    }

    public void setApplicationIp(String applicationIp) {
        this.applicationIp = applicationIp;
    }

    @Column(name = "application_port", length = 5)
    public String getApplicationPort() {
        return this.applicationPort;
    }

    public void setApplicationPort(String applicationPort) {
        this.applicationPort = applicationPort;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", length = 7)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "creator_id", length = 32)
    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_time", length = 7)
    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Column(name = "modifier_id", length = 32)
    public String getModifierId() {
        return this.modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    @Column(name = "status", length = 1)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "application")
    public List<BizScenario> getBizScenarios() {
        return this.bizScenarios;
    }

    public void setBizScenarios(List<BizScenario> bizScenarios) {
        this.bizScenarios = bizScenarios;
    }

    @Column(name="INTERVAL", precision=22, scale=0)
    public BigDecimal getInterval() {
        return interval;
    }

    public void setInterval(BigDecimal interval) {
        this.interval = interval;
    }

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "application")
    public List<EumUrl> getEnumUrls() {
        return enumUrls;
    }

    public void setEnumUrls(List<EumUrl> enumUrls) {
        this.enumUrls = enumUrls;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


