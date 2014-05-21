package com.sinosoft.monitor.agent.trackers;

public class MethodTracker extends DefaultTracker {
	public MethodTracker(String className, String methodName, Object thiz, Object[] args) {
		super(className, methodName, thiz, args);
	}
}
