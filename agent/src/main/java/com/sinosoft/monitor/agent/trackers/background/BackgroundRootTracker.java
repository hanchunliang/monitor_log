package com.sinosoft.monitor.agent.trackers.background;

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
import com.sinosoft.monitor.agent.util.UUIDUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class BackgroundRootTracker extends AbstractRootTracker {
	public BackgroundRootTracker(String className, String methodName, Object thiz, Object[] args) {
		super(className, methodName, thiz, args);
	}

	public String assignSequenceName() {
		this.seqName = ("background/" + this.interceptedClassName.replaceAll("/", "."));
		return this.seqName;
	}

	public static BackgroundRootTracker create(DefaultTracker defaultTracker) {
		String className = defaultTracker.getInterceptedClassName();
		String methName = defaultTracker.getInterceptedMethodName();

		BackgroundRootTracker thiz = new BackgroundRootTracker(className, methName, null, null);
		thiz.startTime = defaultTracker.getStartTime();
		thiz.duration = defaultTracker.getDuration();

		return thiz;
	}

	@Override
	public boolean isUrlTraceHolded() {
		return true;
	}

	@Override
	public boolean generateUrlTraceLog(String paramString) {

//		UrlTraceStore urlStore = UrlTraceStoreController.getUrlTraceStore();
//		List<UrlTraceLog> urlTraces = urlStore.getUrlTrace(seqName);
		UrlTraceLog urlTrace = new UrlTraceLog();
		urlTrace.setConsumeTime(getDuration());
		urlTrace.setBeginTime(new Date(getStartTime()));
		urlTrace.setEndTime(new Date(getEndTime()));
		urlTrace.setRecordTime(new Date());
		urlTrace.setUrl(seqName);
		urlTrace.setId(UUIDUtil.getUUID());
		urlTrace.setApplicationId(JavaAgentConfig.getAgentInstanceId());
//			urlTrace.setRequestParams();
//			urlTrace.setSessionId(sessionId);
//			urlTrace.setUserIp(requestIp);

		//handle exception
		if(getExceptionDescription() != null) {
			urlTrace.setHasException(true);
			urlTrace.setAlarmId(UUIDUtil.getUUID());
//			List<ExceptionInfo> exceptions = urlStore.getExceptionInfo(seqName);
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
//			exceptions.add(info);
            AgentTraceStore.offerExceptionInfo(info);
		}
		generateMethodTraceLogs(urlTrace);
//		urlTraces.add(urlTrace);
        AgentTraceStore.offerUrlTraceLog(urlTrace);

		return isUrlTraceHolded();
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
	public void quit(int opcode, Object returnValue) {
		quit(returnValue);
	}

	public void quit(Throwable th) {
		super.quit(th);
		quit();
	}
}
