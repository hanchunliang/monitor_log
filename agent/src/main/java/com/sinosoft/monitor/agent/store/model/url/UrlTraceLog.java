package com.sinosoft.monitor.agent.store.model.url;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * URL 追踪类
 * User: carvin
 * Date: 12-11-28
 * Time: 上午1:57
 * 用于记录URL追踪信息.
 */

public class UrlTraceLog implements Serializable {
	public static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
    private String id;
	/**
	 * URL地址
	 */
    private String url;
	/**
	 * 开始时间
	 */
    private Date beginTime;
	/**
	 * 结束时间
	 */
    private Date endTime;
	/**
	 * 花费时间
	 */
    private long consumeTime;
	/**
	 * 会话ID
	 */
    private String sessionId;
	/**
	 * 用户IP
	 */
    private String userIp;
	/**
	 * 请求参数信息
	 */
    private String requestParams;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 业务场景追踪ID
	 */
	private String traceId;
	/**
	 * 所属业务场景ID
	 */
	private String bizScenarioId;
	/**
	 * 告警信息ID
	 */
	private String alarmId;
	/**
	 * 所属应用ID
	 */
	private String applicationId;
	/**
	 * URL信息ID
	 */
	private String urlId;
	/**
	 * 日志记录时间
	 */
	private Date recordTime;

	/**
	 * 是否有异常
	 */
	private boolean hasException;

	List<MethodTraceLog> methodTraceLogList = new ArrayList<MethodTraceLog>();


    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getBizScenarioId() {
		return bizScenarioId;
	}

	public void setBizScenarioId(String bizScenarioId) {
		this.bizScenarioId = bizScenarioId;
	}

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public boolean getHasException() {
		return hasException;
	}

	public void setHasException(boolean hasException) {
		this.hasException = hasException;
	}

	public void setMethodTraceLogList(List<MethodTraceLog> methodTraceLogList) {
		this.methodTraceLogList = methodTraceLogList;
	}

	public List<MethodTraceLog> getMethodTraceLogList() {
		return methodTraceLogList;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

//	@Override
//    public String toString() {
//        return new StringBuilder(this.toString())
//            .append("id").append( id)
//            .append("url").append( url)
//            .append("beginTime").append( beginTime)
//            .append("endTime").append( endTime)
//            .append("consumeTime").append( consumeTime)
//            .append("sessionId").append( sessionId)
//            .append("userId").append( userId)
//            .append("userIp").append( userIp).toString();
//    }
}
