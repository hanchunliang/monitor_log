package com.sinosoft.one.monitor.os.linux.model.viewmodel;

/**
 * 基本信息 可用性 健康状况等 信息对象
 * @author Administrator
 *
 */
public class OsBaseInfoModel {
	 
	/**
	 * 监视器名称
	 */
     private String monitorName;
    
     /**
      * id
      */
    private String monitorID;
    
    /**
     * 可用性
     */
    private String usability;
     
    /**
     * 健康状态
     */
    private String[] healthy;

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getMonitorID() {
        return monitorID;
    }

    public void setMonitorID(String monitorID) {
        this.monitorID = monitorID;
    }

    public String getUsability() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability = usability;
    }

    public String[] getHealthy() {
        return healthy;
    }

    public void setHealthy(String[] healthy) {
        this.healthy = healthy;
    }
}
