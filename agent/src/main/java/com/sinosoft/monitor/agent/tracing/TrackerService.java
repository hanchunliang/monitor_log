package com.sinosoft.monitor.agent.tracing;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.instrumentation.InterceptorDefinitionFactory;
import com.sinosoft.monitor.agent.instrumentation.interceptor.InterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.TracingInterceptorDefinition;
import com.sinosoft.monitor.agent.trackers.DefaultTracker;
import com.sinosoft.monitor.agent.trackers.Tracker;
import com.sinosoft.monitor.agent.trackers.background.BackgroundRootTracker;

import java.util.logging.Level;

/**
 * TrackerService 负责在运行时获取Tracker用以追踪方法调用的过程
 *
 */
public class TrackerService {
	/**
	 * 加强的字节码进入方法时会调用 JavaAgentHandler.invokeTracker方法，
	 * invokeTracker方法中会调用到此方法，
	 * 此方法根据pointCutName 也就是 InterceptorDefinition 的类名，
	 * 和当前调用invokeTracker方法的类和方法名，获取到最合适的Tracker。
	 * @param pointCutName
	 * @param className
	 * @param methodName
	 * @param thisObj
	 * @param args
	 * @return
	 */
	public Tracker getTracker(String pointCutName, String className, String methodName, Object thisObj, Object[] args) {
		Tracker tracer = null;
		try {
			InterceptorDefinition pcDefn = InterceptorDefinitionFactory.getInterceptorDefinition(pointCutName);
			if ((pcDefn instanceof TracingInterceptorDefinition)) {
				tracer = ((TracingInterceptorDefinition) pcDefn).getTracker(className, methodName, thisObj, args);
			}
			if (tracer != null) {
				tracer.setComponentName(pcDefn.getComponentName());
			}
		} catch (Exception ex) {
			JavaAgent.logger.log(Level.WARNING, "Exception while creating the tracer {0}.", pointCutName);
			JavaAgent.logger.log(Level.WARNING, "", ex);
		}
		return tracer;
	}

	/**
	 * 获取后台的根Tracker，当调用JavaAgentHandler.invokeTracker的类的方法
	 * 不是一个由InterceptorDefinition定义好的方法时，也就是一般的方法，或用户
	 * 项目中的业务方法时，会统一用BackgroundRootTracker.create一个后台的根Tracker
	 * 该方法主要是后台自动发起调用的方法，而不是有一个http请求发起的。
	 * @param defaultTracker
	 * @return BackgroundRootTracker
	 */
	public BackgroundRootTracker getBackgroundRootTracker(DefaultTracker defaultTracker) {
		return BackgroundRootTracker.create(defaultTracker);
	}
}
