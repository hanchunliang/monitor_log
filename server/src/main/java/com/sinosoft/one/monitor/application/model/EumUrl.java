package com.sinosoft.one.monitor.application.model;
// Generated 2013-3-4 15:45:30 by One Data Tools 1.0.0


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * EumUrl.
 * 业务仿真URL信息表
 */
@Entity
@Table(name = "GE_MONITOR_EUM_URL")
public class EumUrl implements java.io.Serializable {

    /**
     * 主键ID.
     */
    private String id;

    /**
     * 如果有url的话，url的Id
     */
    private String urlId;

    /**
     * URL地址.
     */
    private String url;

    /**
     * 所属应用系统ID.
     */
    private Application application;

    /**
     */
    private Date recordTime;
    /**
     */
    private List<EumUrlAvaSta> eumUrlAvaStas = new ArrayList<EumUrlAvaSta>(0);
    /**
     */
    private List<EumUrlAva> eumUrlAvas = new ArrayList<EumUrlAva>(0);

    public EumUrl() {
    }


    public EumUrl(String id, String url) {
        this.id = id;
        this.url = url;
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

    @Column(name = "URL", length = 300)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID")
    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RECORD_TIME", length = 7)
    public Date getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="EUM_URL_ID")
    public List<EumUrlAvaSta> getEumUrlAvaStas() {
        return this.eumUrlAvaStas;
    }

    public void setEumUrlAvaStas(List<EumUrlAvaSta> eumUrlAvaStas) {
        this.eumUrlAvaStas = eumUrlAvaStas;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="EUM_URL_ID")
    public List<EumUrlAva> getEumUrlAvas() {
        return this.eumUrlAvas;
    }

    public void setEumUrlAvas(List<EumUrlAva> eumUrlAvas) {
        this.eumUrlAvas = eumUrlAvas;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Column(name = "URL_ID", length = 32)
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }
}


