package com.sinosoft.monitor.agent.store;

import com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo;
import com.sinosoft.monitor.agent.store.model.url.UrlTraceLog;

import java.util.List;

/**
 * User: morgan
 * Date: 13-8-22
 * Time: 上午10:11
 */
public interface UrlTraceStore {
	public abstract String getName();

	public abstract UrlTraceLog getUrlTrace(UrlTraceLog urlTraceLog);

	public abstract List<UrlTraceLog> getUrlTrace(String url);

	public abstract List<ExceptionInfo> getExceptionInfo(String url);

	public abstract List<UrlTraceLog> getAllUrlTraces();

	public abstract List<ExceptionInfo> getAllExceptionInfos();

	public abstract UrlTraceLog findUrlTrace(String paramString);

	public abstract void cleanUrlTraceStore();

	public abstract int[] getOccupiedSize();
}
