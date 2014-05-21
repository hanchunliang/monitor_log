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
 * Url.
 * 应用系统URL信息表
 */
@Entity
@Table(name = "GE_MONITOR_URL"
)
public class Url implements java.io.Serializable {

    /**
     * 主键ID.
     */
    private String id;
    /**
     * URL描述.
     */
    @NotEmpty(message = "URL描述不能为空")
    @Size(min = 1,max = 300,message = "URL描述的长度应该在{min}-{max}之间")
    private String description;
    /**
     * URL地址.
     */
    @NotEmpty(message = "URL地址不能为空")
    @Size(min = 1,max = 500,message = "URL地址的长度应该在{min}-{max}之间")
    private String url;
    /**
     * URL阈值，单位ms.
     */
    private int threshold;
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
     * 关联的业务场景
     */
    private List<BizScenario> bizScenarios = new ArrayList<BizScenario>(0);
    /**
     * 关联的方法
     */
    private List<Method> methods = new ArrayList<Method>(0);
    /**
     * 操作(为了页面显示，可管理url或者删除该条业务场景)
     */
    private String operation="<a href='javascript:void(0)' class='eid' onclick='editRow(this)'>编辑 <a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除";

    public Url() {
    }


    public Url(String id, String description, String url, int threshold, Date createTime, String creatorId, String status) {
        this.id = id;
        this.description = description;
        this.url = url;
        this.threshold = threshold;
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

    @Column(name = "description", length = 300)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "url", length = 500)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "threshold", precision = 6, scale = 0)
    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
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

    //设置Url为维护端(相对于BizScenario来说)
    //定义中间关联表的信息
    //inverseJoinColumns：被维护端的外键
    //joinColumns：维护端的外键
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinTable(name = "GE_MONITOR_BIZ_SCENARIO_URL",
            inverseJoinColumns = @JoinColumn(name = "BIZ_SCENARIO_ID"),
            joinColumns = @JoinColumn(name = "URL_ID"))
    public List<BizScenario> getBizScenarios() {
        return this.bizScenarios;
    }

    public void setBizScenarios(List<BizScenario> bizScenarios) {
        this.bizScenarios = bizScenarios;
    }

    public void setBizScenario(BizScenario bizScenario) {
        this.bizScenarios.add(bizScenario);
    }

    //设置Url为被维护端(相对于Method来说)
    //mappedBy是Url被Method维护的属性
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "urls")
    public List<Method> getMethods() {
        return this.methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    @Transient
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


