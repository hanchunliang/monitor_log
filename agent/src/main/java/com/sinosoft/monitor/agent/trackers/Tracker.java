package com.sinosoft.monitor.agent.trackers;

import java.util.ArrayList;
import java.util.Map;

public abstract interface Tracker {
	public abstract long getStartTime();

	public abstract long getEndTime();

	public abstract int getDuration();

	public abstract void setParentTracker(Tracker paramTracker);

	public abstract Tracker getParentTracker();

	public abstract ArrayList<Tracker> getChildTrackers();

	public abstract void setChildTracker(Tracker paramTracker);

	public abstract void setComponentName(String paramString);

	public abstract void quit(int paramInt, Object returnValue);

	public abstract void quit(Throwable paramThrowable);

	public abstract String getComponentName();

	public abstract String getTrackerSignature();

	public abstract Map<String, Object> getMoreData();

	public abstract boolean generateUrlTraceLog(String paramString);

	public abstract boolean isUrlTraceHolded();
}
