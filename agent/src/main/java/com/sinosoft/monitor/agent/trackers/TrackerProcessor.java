package com.sinosoft.monitor.agent.trackers;

import com.sinosoft.monitor.agent.config.JavaAgentConfig;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TrackerProcessor extends Thread {
	private volatile boolean continueToWork = true;
	private static final String CLZ_NAME = "TrackerProcessor";
	private final ArrayBlockingQueue<RootTracker> trackerQueue;
	private final JavaAgentConfig agentConfig;
	private final Logger logger;

	public TrackerProcessor(ArrayBlockingQueue<RootTracker> trackerQueue,
	                        JavaAgentConfig agentConfig, Logger logger) {
		this.trackerQueue = trackerQueue;
		this.agentConfig = agentConfig;
		this.logger = (logger == null ? agentConfig.getLogger() : logger);
		setName(CLZ_NAME);

		setDaemon(true);
	}

	public void run() {
		this.logger.info("TrackerProcessor started successfully.");
		doWork();
		this.logger.info("TrackerProcessor shutdown successfully.");
	}

	private void doWork() {
		//byte workCounter = 0;
		while (this.continueToWork)
			try {
				doExtract();

				//workCounter = (byte) (workCounter + 1);
//				if (workCounter == 120) {
//					workCounter = 0;
//					Thread.sleep(1000L);
//				}
			} catch (InterruptedException e) {
				this.logger.warning("TrackerProcessor experienced interrupted exception {0}. extraction continues..");
			} catch (Throwable e) {
				this.logger.log(Level.WARNING, "TrackerProcessor experienced exception. extraction continues..", e);
			}
	}

	public void doExtract() throws InterruptedException {
		RootTracker rootTracker = (RootTracker) this.trackerQueue.take();
		if (rootTracker == null) {
			return;
		}
		String seqName = rootTracker.getSequenceName();

		if (seqName == null) {
			this.logger.warning("TrackerProcessor sequenceName found null in rootTracker");
			return;
		}
		produceUrlTraceLog(rootTracker,seqName);
	}

	public void produceUrlTraceLog(Tracker tracker, String seqName) {
//		System.out.println("produceUrlTraceLog ...:"+seqName);
		this.logger.info("produce UrlTraceLog url is:"+seqName);
		tracker.generateUrlTraceLog(seqName);
	}

//	public void produceMetrics(Tracker tracker, String seqName) {
//		tracker.generateUrlTraceLog(seqName);
//		//放到generateUrlTraceLog中去做child信息
////		if (tracker.getChildTrackers() == null) {
////			return;
////		}
////		for (Tracker t : tracker.getChildTrackers()) {
////			produceMetrics(t, seqName);
////		}
//	}

//	public void produceTrace(RootTracker rootTracker) {
//		if (this.agentConfig.isSequenceTraceEnabled()) {
//			float traceThreshold = this.agentConfig.getSequenceTraceThreshold();
//			boolean collectTrace = rootTracker.getDuration() > traceThreshold;
//
//			String seqName = rootTracker.getSequenceName();
//
//			if (this.agentConfig.traceDuplicate) {
//				seqName = seqName + Math.random() + "" + Math.random();
//			}
//			if (collectTrace) {
//				SequenceTrace seqTrace = SequenceTraceStore.get(seqName);
//
//				if (seqTrace == null) {
//					SequenceTraceStore.constructTraceAndPut(seqName, rootTracker);
//				} else if (rootTracker.getDuration() > seqTrace.getDuration()) {
//					SequenceTrace t = new SequenceTrace(rootTracker);
//					SequenceTraceStore.replace(seqName, t);
//				}
//			}
//		} else {
//			this.logger.log(Level.FINE, "Trace of Sequence: {0} not construced! SequenceTrace construction disabled.", rootTracker.getSequenceName());
//		}
//	}

	public void shutdown() {
		this.continueToWork = false;
	}
}
