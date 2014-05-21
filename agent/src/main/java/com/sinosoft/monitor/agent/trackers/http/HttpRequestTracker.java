package com.sinosoft.monitor.agent.trackers.http;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.agent.store.AgentTraceStore;
import com.sinosoft.monitor.agent.store.UrlTraceStore;
import com.sinosoft.monitor.agent.store.UrlTraceStoreController;
import com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo;
import com.sinosoft.monitor.agent.store.model.url.MethodTraceLog;
import com.sinosoft.monitor.agent.store.model.url.UrlTraceLog;
import com.sinosoft.monitor.agent.trackers.AbstractRootTracker;
import com.sinosoft.monitor.agent.trackers.DefaultTracker;
import com.sinosoft.monitor.agent.trackers.Tracker;
import com.sinosoft.monitor.agent.util.SequenceURINormalizer;
import com.sinosoft.monitor.agent.util.UUIDUtil;
import com.sinosoft.monitor.com.alibaba.fastjson.JSON;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class HttpRequestTracker extends AbstractRootTracker {
	private boolean hasUrlTrace;
	protected Object[] args;
	private String requestMethod;
	private Map<String, String> requestParamMap;
	private String requestIp;
	private String sessionId;

//	protected

	public HttpRequestTracker(String className, String methodName, Object thisObj, Object[] args) {
		super(className, methodName, thisObj, args);

		this.args = args;
		String uri;
		try {
			HttpRequest request = new HttpRequest(this.args[0]);
			requestMethod = request.getMethod();

			requestParamMap = request.getParameterMap();

			requestIp = request.getRealIP();

			sessionId = request.getSessionId();

//			System.out.println( "requestMethod :" + requestMethod + "  paramMap:" +
//					requestParamMap + "  requestIP:" + requestIp + "  sessionId:" + sessionId);

			uri = SequenceURINormalizer.normalizeURI(request.getRequestURI());
		} catch (Exception ex) {
			uri = "unknown";
			JavaAgent.logger.log(Level.WARNING, "Exception while normalizing the url {0}", ex.getMessage());
		}
		this.seqName = ("" + uri);
	}

	public String assignSequenceName() {

		this.hasUrlTrace = true;
		//??
		this.args = null;
		return this.seqName;
	}

	public boolean isMetricHolded() {
		return this.hasUrlTrace;
	}


	public void quit(int opcode, Object returnValue) {
		quit(returnValue);
	}

	public void quit(Throwable th) {
		super.quit(th);
		quit();
	}

	@Override
	public boolean generateUrlTraceLog(String seqName) {
		if (this.hasUrlTrace) {
//			UrlTraceStore urlStore = UrlTraceStoreController.getUrlTraceStore();
//			List<UrlTraceLog> urlTraces = urlStore.getUrlTrace(seqName);
			UrlTraceLog urlTrace = new UrlTraceLog();
			urlTrace.setConsumeTime(getDuration());
			urlTrace.setBeginTime(new Date(getStartTime()));
			urlTrace.setEndTime(new Date(getEndTime()));
			urlTrace.setRecordTime(new Date());
			urlTrace.setUrl(seqName);
			urlTrace.setId(UUIDUtil.getUUID());
			urlTrace.setApplicationId(JavaAgentConfig.getAgentInstanceId());
			urlTrace.setRequestParams(JSON.toJSONString(requestParamMap));
			urlTrace.setSessionId(sessionId);
			urlTrace.setUserIp(requestIp);

			//handle exception
			if(getExceptionDescription() != null) {
				urlTrace.setHasException(true);
				urlTrace.setAlarmId(UUIDUtil.getUUID());
//				List<ExceptionInfo> exceptions = urlStore.getExceptionInfo(seqName);
				ExceptionInfo info = new ExceptionInfo();
				info.setUrlTraceId(urlTrace.getId());
				info.setAlarmId(urlTrace.getAlarmId());
				info.setId(UUIDUtil.getUUID());
				info.setRequestParams(urlTrace.getRequestParams());
				info.setUrl(urlTrace.getUrl());
				info.setExceptionDescription(getExceptionDescription());
				info.setExceptionStackTrace(getExceptionStackTrace());
				info.setRecordTime(new Date());
				info.setApplicationId(urlTrace.getApplicationId());
//				exceptions.add(info);
                AgentTraceStore.offerExceptionInfo(info);
			}
			generateMethodTraceLogs(urlTrace);
//			urlTraces.add(urlTrace);
            AgentTraceStore.offerUrlTraceLog(urlTrace);
		}
		return this.hasUrlTrace;
	}

	protected void quit(Object returnValue) {
		super.quit(returnValue);
		this.args = null;
	}

	private List<MethodTraceLog> generateMethodTraceLogs(UrlTraceLog urlTraceLog) {

		List<MethodTraceLog> methodTraceLogs = urlTraceLog.getMethodTraceLogList();
		if(methodTraceLogs != null) {

			methodTraceLogs.add(generateMethodTraceLog(urlTraceLog,this));

			List<Tracker> childs = getChildTrackers();
			if( childs != null && childs.size() > 0 ) {
				for( Tracker tracker : childs ) {
					if( tracker instanceof DefaultTracker ) {

						generateChildsMethodTraceLog((DefaultTracker) tracker,
								methodTraceLogs, urlTraceLog);

					}
				}
			}
		}
		return methodTraceLogs;
	}

	private void generateChildsMethodTraceLog(DefaultTracker childTracker,
	                                          List<MethodTraceLog> methodTraceLogs,
	                                          UrlTraceLog urlTraceLog) {
		methodTraceLogs.add(generateMethodTraceLog(urlTraceLog,childTracker));
		if(childTracker.getChildTrackers() != null) {
			for( Tracker tracker : childTracker.getChildTrackers() ) {
				if( tracker instanceof DefaultTracker ) {
					generateChildsMethodTraceLog((DefaultTracker) tracker, methodTraceLogs, urlTraceLog);
				}
			}
		}
	}

	private MethodTraceLog generateMethodTraceLog(UrlTraceLog urlTraceLog,
	                                              DefaultTracker defaultTracker) {
		MethodTraceLog methodTraceLog = new MethodTraceLog();
		methodTraceLog.setId(UUIDUtil.getUUID());
		methodTraceLog.setBeginTime(new Date(defaultTracker.getStartTime()));
		methodTraceLog.setEndTime(new Date(defaultTracker.getEndTime()));
		methodTraceLog.setConsumeTime(defaultTracker.getDuration());
		methodTraceLog.setClassName(defaultTracker.getInterceptedClassName() != null?
				defaultTracker.getInterceptedClassName().replaceAll("/",".") : "");
		methodTraceLog.setMethodName(defaultTracker.getInterceptedMethodName());
		methodTraceLog.setUrlTraceLogId(urlTraceLog.getId());
		methodTraceLog.setRecordTime(new Date());
		methodTraceLog.setInParam(defaultTracker.getMethodParams());
		methodTraceLog.setOutParam(defaultTracker.getReturnValue());
		//JavaAgent.logger.info("generateMethodTraceLog :"+methodTraceLog.getFullMethodName());
		return methodTraceLog;
	}

}
