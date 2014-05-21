package com.sinosoft.monitor.agent.store;

import com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo;
import com.sinosoft.monitor.agent.store.model.url.UrlTraceLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: morgan
 * Date: 13-8-22
 * Time: 上午10:16
 */
public abstract class AbstractUrlTraceStore implements UrlTraceStore {

	protected Map<String, List<UrlTraceLog>> urlTraceBucket =  new HashMap<String, List<UrlTraceLog>>();
	protected Map<String, List<ExceptionInfo>> exceptionInfoBocket
			= new HashMap<String, List<ExceptionInfo>>();

	public abstract String getName() ;


	@Override
	public UrlTraceLog getUrlTrace(UrlTraceLog urlTraceLog) {
		return null;
	}

	@Override
	public List<UrlTraceLog> getUrlTrace(String url) {
		List<UrlTraceLog> urlTraces = urlTraceBucket.get(url);
		if(urlTraces == null) {
			urlTraces = new ArrayList<UrlTraceLog>();
			//urlTraces.add(createUrlTraceLog(url));
			urlTraceBucket.put(url,urlTraces);
			return urlTraces;
		}

		return urlTraces;
	}

	@Override
	public List<UrlTraceLog> getAllUrlTraces() {
		List<UrlTraceLog> result = new ArrayList<UrlTraceLog>();
		for( List<UrlTraceLog> traceLogs : urlTraceBucket.values() ) {
			result.addAll(traceLogs);
		}
		return result;
	}

	@Override
	public List<ExceptionInfo> getExceptionInfo(String url) {
		List<ExceptionInfo> exceptionInfos = exceptionInfoBocket.get(url);
		if(exceptionInfos == null) {
			exceptionInfos = new ArrayList<ExceptionInfo>();
			exceptionInfoBocket.put(url,exceptionInfos);
			return exceptionInfos;
		}
		return exceptionInfos;
	}

	@Override
	public List<ExceptionInfo> getAllExceptionInfos() {
		List<ExceptionInfo> result = new ArrayList<ExceptionInfo>();
		for( List<ExceptionInfo> exceptionInfos : exceptionInfoBocket.values() ) {
			result.addAll(exceptionInfos);
		}
		return result;
	}

	@Override
	public UrlTraceLog findUrlTrace(String url) {
		return null;
//		return this.urlTraceBucket.get(url);
	}

	@Override
	public void cleanUrlTraceStore() {
		this.urlTraceBucket.clear();
		this.exceptionInfoBocket.clear();
	}

	@Override
	public int[] getOccupiedSize() {
		return new int[0];
	}
}
