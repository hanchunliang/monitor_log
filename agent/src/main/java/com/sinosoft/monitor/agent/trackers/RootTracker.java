package com.sinosoft.monitor.agent.trackers;

public abstract interface RootTracker extends Tracker {
	public abstract String getSequenceName();

	public abstract String assignSequenceName();

	public abstract String getThreadName();

	public abstract long getThreadId();

	public abstract void computeFrictionTime(int paramInt);

	public abstract int getFrictionTime();
}
