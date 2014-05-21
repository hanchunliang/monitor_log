package com.sinosoft.one.monitor.action.model;
// Generated 2013-3-1 10:54:16 by One Data Tools 1.0.0


import com.google.common.base.Strings;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * MailAction.
* 应用系统邮件动作信息表
 */
@Entity
@Table(name="GE_MONITOR_EMAIL_ACTION"
)
public class MailAction implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID.
	 */
	private String id;
	/**
	 * 显示姓名
	 */
	private String name;
	/**
	 * 发件地址(fromaddress).
	 */
	private String fromAddress;
	/**
	 * 收件地址(toaddress).
	 */
	private String toAddress;
	/**
	 * 主题(subject).
	 */
	private String subject;
	/**
	 * 内容(message).
	 */
	private String content;
	/**
	 * 邮件服务器(smtpserver).
	 */
	private String smtpServer;
	/**
	 * 邮件服务器端口(smtpport).
	 */
	private String smtpPort;
	/**
	 * 邮件格式(mailformat).
	 */
	private String mailFormat;
	/**
	 * 附加信息(appendmessage).
	 */
	private String appendMessage;

	private String operation="<a  href='javascript:void(0)' onclick='updRow(this)' class='eid'>编辑</a> <a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除</a>";
	public MailAction() {
	}

	@Transient
	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = Strings.nullToEmpty(operation);
	}


	public MailAction(String id, String fromAddress, String toAddress, String subject) {
		this.id = id;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.subject = subject;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="ID", unique=true, length=32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="FROM_ADDRESS", length=250)
	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = Strings.nullToEmpty(fromAddress);
	}

	@Column(name="TO_ADDRESS", length=250)
	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = Strings.nullToEmpty(toAddress);
	}

	@Column(name="SUBJECT", length=100)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = Strings.nullToEmpty(subject);
	}

	@Column(name="CONTENT", length=3000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = Strings.nullToEmpty(content);
	}

	@Column(name="SMTP_SERVER", length=100)
	public String getSmtpServer() {
		return this.smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	@Column(name="SMTP_PORT", length=5)
	public String getSmtpPort() {
		return this.smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	@Column(name="MAIL_FORMAT", length=1)
	public String getMailFormat() {
		return this.mailFormat;
	}

	public void setMailFormat(String mailFormat) {
		this.mailFormat = mailFormat;
	}

	@Column(name="APPEND_MESSAGE", length=100)
	public String getAppendMessage() {
		return this.appendMessage;
	}

	public void setAppendMessage(String appendMessage) {
		this.appendMessage = Strings.nullToEmpty(appendMessage);
	}

	@Column(name="NAME", length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}


