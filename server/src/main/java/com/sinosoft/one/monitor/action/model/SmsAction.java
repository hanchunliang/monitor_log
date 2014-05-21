package com.sinosoft.one.monitor.action.model;
// Generated 2013-3-1 10:54:16 by One Data Tools 1.0.0


import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MailAction.
* 应用系统邮件动作信息表
 */
@Entity
@Table(name="GE_MONITOR_EMAIL_ACTION"
)
public class SmsAction implements java.io.Serializable {

    /**
    * 主键ID.
    */
    private String id;
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
    private String desc;
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
    /**
     * 动作名称(name).
     */
    private String name;

    public SmsAction() {
    }


    public SmsAction(String id, String fromAddress, String toAddress, String subject) {
        this.id = id;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.subject = subject;
    }
   
    @Id 
    
    @Column(name="id", unique=true, length=32)
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="from_address", length=250)
    public String getFromAddress() {
    return this.fromAddress;
    }

    public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
    }
    
    @Column(name="to_address", length=250)
    public String getToAddress() {
    return this.toAddress;
    }

    public void setToAddress(String toAddress) {
    this.toAddress = toAddress;
    }
    
    @Column(name="subject", length=100)
    public String getSubject() {
    return this.subject;
    }

    public void setSubject(String subject) {
    this.subject = subject;
    }
    
    @Column(name="desc", length=3000)
    public String getDesc() {
    return this.desc;
    }

    public void setDesc(String desc) {
    this.desc = desc;
    }
    
    @Column(name="smtp_server", length=100)
    public String getSmtpServer() {
    return this.smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
    }
    
    @Column(name="smtp_port", length=5)
    public String getSmtpPort() {
    return this.smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
    this.smtpPort = smtpPort;
    }
    
    @Column(name="mail_format", length=1)
    public String getMailFormat() {
    return this.mailFormat;
    }

    public void setMailFormat(String mailFormat) {
    this.mailFormat = mailFormat;
    }
    
    @Column(name="append_message", length=100)
    public String getAppendMessage() {
    return this.appendMessage;
    }

    public void setAppendMessage(String appendMessage) {
    this.appendMessage = appendMessage;
    }

    @Column(name="name", length=100)
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


