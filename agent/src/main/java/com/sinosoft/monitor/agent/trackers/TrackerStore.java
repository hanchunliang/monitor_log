package com.sinosoft.monitor.agent.trackers;

import com.sinosoft.monitor.agent.JavaAgent;

import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;

public class TrackerStore {

	public static ArrayBlockingQueue<RootTracker> trackerStore;

	private static void init() {
		int size = JavaAgent.getInstance().agentConfig.getTrackerStoreSize();
		trackerStore = new ArrayBlockingQueue(size);
		JavaAgent.logger.info("TrackerStore initialized with size: " + size);
	}

	public static boolean add(RootTracker rootTracker) {
		try {
			return trackerStore.add(rootTracker);
		} catch (Exception e) {
			String error = MessageFormat.format("Exception in TrackerStore.add(rootTracker) Sequence: {0} Store occupied size: {1} Exception: {2}", new Object[]{rootTracker.getSequenceName(), Integer.valueOf(trackerStore.size()), e.getMessage()});

			JavaAgent.logger.warning(error);
		}
		return false;
	}

	public static void clean() {
		trackerStore.clear();
		JavaAgent.logger.info("TrackerStore with occupied size: " + trackerStore.size() + " cleaned");
	}

	public static int getOccupiedSize() {
		return trackerStore.size();
	}

	static {
		init();
	}
}
