package com.sinosoft.monitor.agent.trackers;

public abstract class AbstractRootTracker extends DefaultTracker
		implements RootTracker {
	protected String seqName;
	protected int frictionTime;

	public AbstractRootTracker(String className, String methodName, Object thiz, Object[] args) {
		super(className, methodName, thiz, args);
	}

	public String getSequenceName() {
		if (this.seqName == null) {
			assignSequenceName();
		}
		return this.seqName;
	}

	public abstract String assignSequenceName();

	public void computeFrictionTime(int totalTimeInMS) {
		this.frictionTime = (totalTimeInMS - this.duration);
		if (this.frictionTime < 0)
			this.frictionTime = 0;
	}

	public int getFrictionTime() {
		return this.frictionTime;
	}

	public String getThreadName() {
		return "";
	}

	public long getThreadId() {
		return -1L;
	}
}
