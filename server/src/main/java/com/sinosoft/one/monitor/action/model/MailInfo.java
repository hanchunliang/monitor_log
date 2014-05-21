package com.sinosoft.one.monitor.action.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件信息.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午8:34
 */
public class MailInfo {
	private String title;
	private String monitorName;
	private String attributeName;
	private String cause;
	private String rootCause;

	private List<MailAction> mailActionList = new ArrayList<MailAction>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public List<MailAction> getMailActionList() {
		return mailActionList;
	}

	public void addMailAction(MailAction mailAction) {
		this.mailActionList.add(mailAction);
	}
}
