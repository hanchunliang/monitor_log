package com.sinosoft.monitor.agent.sequence;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.agent.tracing.TrackerService;
import com.sinosoft.monitor.agent.trackers.*;

import java.util.logging.Level;

/**
 * 一个序列化的空间，是一个线程的局部变量
 */
public final class SequenceSpace {
	public static final ThreadLocal<SequenceSpace> SEQUENCE_BOOK = new ThreadLocal() {
		protected SequenceSpace initialValue() {
			return new SequenceSpace();
		}
	};

	public RootTracker rootTracker;
	public Tracker lastOpenTraker;
	private short trackersPresent = 0;

	private long workTime = System.currentTimeMillis();

	public byte listenBlockFlag = -1;

    private long count=0;

    public long getSize(){
        return count;
    }

    public void countIncrease(){
        count++;
    }



	/**
	 * 此方法用来转化Tracker，和记录根Tracker，一次若干个加强后的类的链式的方法调用
	 * 会在该线程的SequenceSpace记录一个rootTracker，该方法会根据这次的链式调用相应的
	 * 组织起Tracker的链式结构。由rootTracker作为起点。
	 * @param tracker
	 * @param trackerService
	 * @return
	 */
	public Tracker transformTracker(Tracker tracker, TrackerService trackerService) {
		if (tracker == null) {
			return null;
		}
		if (this.rootTracker == null) {
			if ((tracker instanceof RootTracker)) {
				this.rootTracker = ((RootTracker) tracker);

				updateDiscardFlag( this.rootTracker.assignSequenceName() );
			} else {
				if ((tracker instanceof VoidTracker)) {
					JavaAgent.logger.log(Level.FINER, "VoidTracker: {0} created in an execution flow with RootTracker as null.", tracker);

					return tracker;
				}
//				if ((tracker instanceof StatementTracker)) {
//					JavaAgent.logger.log(Level.FINER, "StatementTracker: {0} created in an execution flow with RootTracker as null.", tracker);
//
//					closeSeqSpace();
//					return null;
//				}

				this.rootTracker = trackerService.getBackgroundRootTracker((DefaultTracker) tracker);
				if (this.rootTracker == null) {
					return null;
				}
				updateDiscardFlag(this.rootTracker.assignSequenceName());
				tracker = this.rootTracker;
			}
		}
		tracker.setParentTracker(this.lastOpenTraker);
		if (this.lastOpenTraker != null) {
			this.lastOpenTraker.setChildTracker(tracker);
		}
		this.lastOpenTraker = tracker;
//		System.out.println("transformTracker : comp name" + tracker.getComponentName() + "  startTime:"+tracker.getStartTime() +
//				"  childs:" + tracker.getChildTrackers() +
//				"  parent:" + tracker.getParentTracker());
//		checkAndArrestSequence();
		return tracker;
	}

	/**
	 * JavaAgentHandler.exitTracker 时调用该方法，
	 * 该方法会在TrackerStore中add本次调用链的rootTracker
	 * @param tracker
	 */
	public void cleanAndUpdate(Tracker tracker) {
		this.lastOpenTraker = tracker.getParentTracker();

		if (this.lastOpenTraker == null) {
			closeSeqSpace();

			boolean pass = (this.listenBlockFlag != 1) || ((this.listenBlockFlag == 1) && (this.trackersPresent > 1));
			if ((pass) && ((tracker instanceof RootTracker))) {
				RootTracker rootTracker = (RootTracker) tracker;
				rootTracker.computeFrictionTime((int) (System.currentTimeMillis() - this.workTime));
				TrackerStore.add(rootTracker);
			}
		}
	}

	protected void updateDiscardFlag(String seqName) {
		if (SequenceFilterByUrl.isSkip(seqName)) {
			this.listenBlockFlag = 1;
		} else {
			updateDiscardFlagBySamplingFactor(seqName);
		}
	}

	private void updateDiscardFlagBySamplingFactor(String seqName) {
//		short samplingFactor = JavaAgentConfig.samplingFactor;
//		if (samplingFactor > 1) {
//			UrlTraceLog urlTraceLog = UrlTraceStoreController.getUrlTraceStore().findUrlTrace(seqName);
//			if(urlTraceLog != null) {
//
//			}
//			Metric metric = MetricStoreController.getMetricStore().findApdexMetric(seqName);
//			if (metric != null) {
//				ResponseTimeMetric rtMetric = metric.getResponseTimeMetric();
//
//				if (rtMetric.count * samplingFactor == rtMetric.actualCount.getAndIncrement()) {
//					this.listenBlockFlag = 0;
//					JavaAgent.logger.log(Level.FINER, "Sequence: {0} selected to be listened. sampling value used: {1}", new Object[]{seqName, Short.valueOf(samplingFactor)});
//				} else {
//					this.listenBlockFlag = 1;
//					JavaAgent.logger.log(Level.FINE, "Sequence: {0} DISCARDED based on sampling factor value: {1}", new Object[]{seqName, Short.valueOf(samplingFactor)});
//				}
//			}
//		}
	}

	protected void closeSeqSpace() {
		this.rootTracker = null;
		this.lastOpenTraker = null;
//		this.queryHolder = null;
		SEQUENCE_BOOK.remove();
	}

//	public QueryHolder getQueryHolder() {
//		if (this.queryHolder == null) {
//			this.queryHolder = new QueryHolder();
//		}
//		return this.queryHolder;
//	}

	protected void checkAndArrestSequence() {
		if ((this.trackersPresent = (short) (this.trackersPresent + 1)) == JavaAgentConfig.maxTrackerInSeq) {
			JavaAgent.logger.warning("[SequenceSpace] Sequence: " +
					this.rootTracker.getSequenceName() +
					" is blocked to track further. Seq.instore tracker size: " + JavaAgentConfig.maxTrackerInSeq);

			this.listenBlockFlag = 1;
		}
	}
}
