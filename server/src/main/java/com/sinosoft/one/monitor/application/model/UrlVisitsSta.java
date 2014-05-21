package com.sinosoft.one.monitor.application.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * URL访问量统计.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午8:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "GE_MONITOR_URL_VISITS_STA")
public class UrlVisitsSta {
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * URLID
	 */
	private String urlId;
	/**
	 * 访问量
	 */
	private int visitNumber;
	/**
	 * 记录时间
	 */
	private Date recordTime;
	/**
	 * 所属业务系统ID
	 */
	private String applicationId;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "URL_ID")
	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	@Column(name = "VISIT_NUMBER")
	public int getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(int visitNumber) {
		this.visitNumber = visitNumber;
	}

	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return recordTime;
	}

	@Column(name = "APPLICATION_ID")
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void increaseVisitNumber() {
		visitNumber++;
	}
    public void increaseVisitNumber(int plusElement) {
        visitNumber+=plusElement;
    }
}
