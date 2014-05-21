package com.sinosoft.monitor.agent.trackers;

import java.util.ArrayList;
import java.util.Map;

public abstract class AbstractTracker
		implements Tracker {
	protected Tracker parentTracker;
	protected ArrayList<Tracker> childTrackers;

	public void setParentTracker(Tracker parentTracker) {
		this.parentTracker = parentTracker;
	}

	public Tracker getParentTracker() {
		return this.parentTracker;
	}

	public void setChildTracker(Tracker childTracker) {
		if (this.childTrackers == null) {
			this.childTrackers = new ArrayList(2);
		}
		this.childTrackers.add(childTracker);
	}

	public ArrayList<Tracker> getChildTrackers() {
		return this.childTrackers;
	}

	public Map<String, Object> getMoreData() {
		return null;
	}
}
