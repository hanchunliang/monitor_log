package com.sinosoft.monitor.agent;

import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.agent.sequence.SequenceSpace;
import com.sinosoft.monitor.agent.tracing.TrackerService;
import com.sinosoft.monitor.agent.trackers.RootTracker;
import com.sinosoft.monitor.agent.trackers.Tracker;

import java.util.logging.Level;

/**
 * 提供加强的字节码一些方法调用，用来记录和追踪其本身的方法调用过程。
 *
 */
public final class JavaAgentHandler {

	private JavaAgentConfig config;

	private TrackerService trackerService;

	JavaAgentHandler(JavaAgentConfig config, TrackerService trackerService) {
		this.config = config;
		this.trackerService = trackerService;
	}

	/**
	 * 被加强的类中，在方法的开始会调用此方法，用来获取Tracker
	 * @param pointCutName InterceptorDefinition的具体子类的类名
	 * @param className 调用该方法的被加强字节码的类
	 * @param methodName 调用该方法的被加强字节码的方法
	 * @param methodDesc 调用该方法的方法描述（参数，返回值）
	 * @param thisObj 被加强的类的自身引用
	 * @param args 被加强的类的参数数组
	 * @return
	 */
	public Tracker invokeTracker(String pointCutName, String className,
	                             String methodName, String methodDesc,
	                             Object thisObj, Object[] args) {
//		System.out.println("invokeTracker : point:" + pointCutName + "  className:"+className + "  methodName:" +
//			methodName + "  methodDesc:" + methodDesc + "  thisObj:" + thisObj+ "  args:" + args);

		try {
			if ((!this.config.agentVerified) || (!this.config.agentEnabled)) {
				return null;
			}
			SequenceSpace seqSpace = (SequenceSpace) SequenceSpace.SEQUENCE_BOOK.get();

			if (seqSpace.listenBlockFlag == 1) {
				return null;
			}
            if (seqSpace.getSize() > 200) {
                return null;
            }
			Tracker tracker = this.trackerService.getTracker(pointCutName, className, methodName, thisObj, args);

            if(tracker!=null){
                //访问计数
                seqSpace.countIncrease();
            }

			return seqSpace.transformTracker(tracker, this.trackerService);
		} catch (Throwable th) {
			this.config.getLogger().log(Level.WARNING, "[JavaAgentHandler] Exception in invokeTracker", th);
		}
		return null;
	}

	public void exitTracker(Tracker tracker, Throwable th) {
		//System.out.println("exitTracker width exception : startTime:" + tracker.getStartTime() + "  duration:" + tracker.getDuration() );
		//th.printStackTrace();
		try {
			if (tracker != null) {
				tracker.quit(th);
				SequenceSpace seqSpace = (SequenceSpace) SequenceSpace.SEQUENCE_BOOK.get();
				seqSpace.cleanAndUpdate(tracker);
			}
		} catch (Throwable ex) {
			this.config.getLogger().log(Level.WARNING, "[JavaAgentHandler] Exception in exitTracker(Throwable)", ex);
		}
	}

	public void exitTracker(Tracker tracker, int opcode, Object returnValue) {
		try {
			if (tracker != null) {
				tracker.quit(opcode, returnValue);
				SequenceSpace seqSpace = (SequenceSpace) SequenceSpace.SEQUENCE_BOOK.get();
				seqSpace.cleanAndUpdate(tracker);
//				System.out.println("exit Tracker : startTime:" + tracker.getStartTime() +
//						"  duration:" + tracker.getDuration() + "  endTime:" + tracker.getEndTime() +
//						"  child:" + tracker.getChildTrackers() + "  component:" + tracker.getComponentName() + "  "
//				);
//				if( tracker instanceof RootTracker) {
//					System.out.println( " url : " + ((RootTracker) tracker).getSequenceName());
//				}
			}
		} catch (Throwable th) {
			this.config.getLogger().log(Level.WARNING, "[JavaAgentHandler] Exception in exitTracker(normal)", th);
		}
	}
}
