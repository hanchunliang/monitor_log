package com.sinosoft.one.monitor.application.model;
// Generated 2013-2-28 10:28:19 by One Data Tools 1.0.0


import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BizScenario.
 * 存储指定应用系统的业务场景信息
 */
@Entity
@Table(name = "GE_MONITOR_BIZ_SCENARIO"
)
public class BizScenario implements java.io.Serializable {

    /**
     * 主键ID.
     */
    private String id;
    /**
     * 所属应用系统ID.
     */
    private Application application;
    /**
     * 业务场景名称.
     */
    @NotEmpty(message = "场景名称不能为空")
    @Size(min = 1,max = 300,message = "场景名称不能为空,长度不能超过300")
    private String name;
    /**
     * 业务场景级别.
     */
    @NotEmpty(message = "场景级别不能为空")
    private String bizScenarioGrade;
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
     */
    private List<Url> urls = new ArrayList<Url>(0);
    /**
     * 创建人名字(为了页面显示)
     */
    private String userName;
    /**
     * 创建时间(为了页面显示)
     */
    private String recodeCreateTime;
    /**
     * 操作(为了页面显示，可管理url或者删除该条业务场景)
     */
    private String operation="<a href='javascript:void(0)' class='eid' onclick='managerUrl(this)'>管理Url " +
            "<a href='javascript:void(0)' class='eid' onclick='editRow(this)'>编辑 <a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除";

    public BizScenario() {
    }


    public BizScenario(String id, Application application, String name, Date createTime, String creatorId, String status) {
        this.id = id;
        this.application = application;
        this.name = name;
        this.createTime = createTime;
        this.creatorId = creatorId;
        this.status = status;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    @Column(name = "id", unique = true, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Column(name = "name", length = 300)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "biz_scenario_grade", length = 10)
    public String getBizScenarioGrade() {
        return this.bizScenarioGrade;
    }

    public void setBizScenarioGrade(String bizScenarioGrade) {
        this.bizScenarioGrade = bizScenarioGrade;
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

    //设置BizScenario为被维护端(相对于Url来说)
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "bizScenarios")
    public List<Url> getUrls() {
        return this.urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    @Transient
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Transient
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Transient
    public String getRecodeCreateTime() {
        return recodeCreateTime;
    }

    public void setRecodeCreateTime(String recodeCreateTime) {
        this.recodeCreateTime = recodeCreateTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


