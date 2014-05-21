package com.sinosoft.one.monitor.db.oracle.model;
// Generated 2013-3-4 21:44:43 by One Data Tools 1.0.0


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * Info.
* Oracle数据库信息表
 */
@Entity
@Table(name="GE_MONITOR_ORACLE_INFO")
public class Info  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
    * 主键ID.
    */
    private String id;
    /**
    * 名称.
    */
    private String name;
    /**
    * 版本.
    */
    private String oracleVersion;
    /**
     * 启动时间
     */
    private String startTime;
    /**
    * IP地址.
    */
    private String ipAddress;
    /**
    * 子网掩码.
    */
    private String subnetMask;
    /**
    * 端口.
    */
    private String port;
    /**
    * 轮询间隔.
    */
    private int pullInterval;
    /**
    * 用户名.
    */
    private String username;
    /**
    * 密码.
    */
    private String password;
    /**
    * 服务名.
    */
    private String instanceName;
    /**
    * 创建时间.
    */
    private Date sysTime;

    public Info() {
    }

	
    public Info(String id) {
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
    @Column(name="START_TIME")
    public String getStartTime() {
		return startTime;
	}
    public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
    @Column(name="NAME", length=80)
    public String getName() {
    return this.name;
    }

    public void setName(String name) {
    this.name = name;
    }
    
    @Column(name="ORACLE_VERSION", length=50)
    public String getOracleVersion() {
    return this.oracleVersion;
    }

    public void setOracleVersion(String oracleVersion) {
    this.oracleVersion = oracleVersion;
    }
    
    @Column(name="IP_ADDRESS", length=30)
    public String getIpAddress() {
    return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    }
    
    @Column(name="SUBNET_MASK", length=30)
    public String getSubnetMask() {
    return this.subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
    this.subnetMask = subnetMask;
    }
    
    @Column(name="PORT", length=5)
    public String getPort() {
    return this.port;
    }

    public void setPort(String port) {
    this.port = port;
    }
    
    @Column(name="PULL_INTERVAL")
    public int getPullInterval() {
    return this.pullInterval;
    }

    public void setPullInterval(int pullInterval) {
    this.pullInterval = pullInterval;
    }
    
    @Column(name="USERNAME", length=100)
    public String getUsername() {
    return this.username;
    }

    public void setUsername(String username) {
    this.username = username;
    }
    
    @Column(name="PASSWORD", length=50)
    public String getPassword() {
    return this.password;
    }

    public void setPassword(String password) {
    this.password = password;
    }
    
    @Column(name="INSTANCE_NAME", length=20)
    public String getInstanceName() {
    return this.instanceName;
    }

    public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
    }
    @Column(name="SYS_TIME")
    public Date getSysTime() {
    return this.sysTime;
    }

    public void setSysTime(Date sysTime) {
         this.sysTime = sysTime;
    }
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


