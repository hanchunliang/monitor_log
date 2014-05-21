package com.sinosoft.monitor.agent;

import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.agent.instrumentation.ClassTransformer;
import com.sinosoft.monitor.agent.service.ApplicationService;
import com.sinosoft.monitor.agent.service.JavaAgentService;
import com.sinosoft.monitor.agent.store.UrlTraceStore;
import com.sinosoft.monitor.agent.store.UrlTraceStoreController;
//import com.sinosoft.monitor.agent.trackers.TrackerStore;
import com.sinosoft.monitor.agent.trackers.TrackerStore;
import com.sinosoft.monitor.agent.trackers.store.InvalidTypeStore;
import com.sinosoft.monitor.agent.util.JavaAgentUtil;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class JavaAgent implements JavaAgentConstants {

	private static JavaAgent agent;

	private static JavaAgentHandler agentHandler;

	private Instrumentation inst;

	private static List<ClassTransformer> classTransformersList = new ArrayList(1);

	public final JavaAgentConfig agentConfig;

	public static Logger logger;

	private JavaAgentService agentService;

	public static void agentmain(String agentArgs, Instrumentation inst)
			throws ClassNotFoundException, UnmodifiableClassException,
			InterruptedException {
		try{
			premain(agentArgs,  inst);
			inst.retransformClasses(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void premain(String options, Instrumentation inst) {
		try {
			agent = createAgent(inst);
			logger.info("Monitor Agent agent initialized successfully.");
			agent.start();
			logger.info("Monitor Agent agent started successfully.");
		} catch (Throwable th) {
			JavaAgentUtil.print("Monitor Agent: SEVERE: Could not create/start Monitor Agent agent! Exception: " + th.getMessage());
			if (logger != null) {
				logger.severe("Could not create/start Monitor Agent agent! Exception: " + th.getMessage());
			}
			th.printStackTrace(JavaAgentUtil.getDefaultPrintStream());
		}
	}

	public static JavaAgent getInstance() {
		return agent;
	}

	public static JavaAgent createAgent(Instrumentation inst) {
		if (agent == null) {
			agent = new JavaAgent(inst);
		}
		return agent;
	}

	private JavaAgent(Instrumentation inst) {
		//给本地Inst赋值，以便以后提供给service和handler使用。
		this.inst = inst;
		//实例化配置
		this.agentConfig = new JavaAgentConfig(this);
		//根据配置实例化agentservice
		this.agentService = new JavaAgentService(this.agentConfig);
        //同步monitor端配置的方法
        ApplicationService.init(this.agentService);
		agentHandler = new JavaAgentHandler(this.agentConfig, this.agentService.getTrackerService());
	}

	public void start() {
        String threadName = "APM_Insight_services_starter";

        new Thread("APM_Insight_services_starter") {
			public void run() {
				JavaAgent.logger.info("APM_Insight_services_starter started");

				JavaAgent.this.agentService.init();

				JavaAgent.logger.info("APM_Insight_services_starter finished");
			}
		}.start();
        setupTransformers(this.inst);
		setupShutdownHooks();
	}

	public void shutdown() {
		logger.info("Agent shutting down.");
		this.agentConfig.agentEnabled = false;
		removeAllTransformers();

		this.agentService.shutdown();

//		for (UrlTraceStore urlStore : UrlTraceStoreController.getAllUrlTraceStore()) {
//			urlStore.cleanUrlTraceStore();
//		}
//		SequenceTraceStore.emptySequenceTraceStore();
		TrackerStore.clean();
	}

	public static JavaAgentHandler getHandler() {
		return agentHandler;
	}

	public void deleteAgent() {
		shutdown();
		this.agentConfig.deleteAgentInfo();
	}

	public void disableAgent() {
		logger.info("Disabling the agent");
		this.agentConfig.agentEnabled = false;

//		for (UrlTraceStore urlStore : UrlTraceStoreController.getAllUrlTraceStore()) {
//			urlStore.cleanUrlTraceStore();
//		}
//		SequenceTraceStore.emptySequenceTraceStore();
		TrackerStore.clean();

		this.agentConfig.updateAgentInfo();
	}

	public void enableAgent() {
		logger.info("Enabling the agent");
		this.agentConfig.agentEnabled = true;
		this.agentConfig.updateAgentInfo();
	}

	private void setupTransformers(Instrumentation inst) {

		ClassTransformer classTransformer = new ClassTransformer(agent);
		inst.addTransformer(classTransformer);
		classTransformersList.add(classTransformer);
	}

	private void removeAllTransformers() {
		for (ClassTransformer ct : classTransformersList) {
			this.inst.removeTransformer(ct);
		}
		logger.info(classTransformersList.size() + " transformers successfully removed from instrumentation");
	}

	private void setupShutdownHooks() {
		Runtime.getRuntime().addShutdownHook(new Cleaner());
	}

	public JavaAgentService getAgentService() {
		return this.agentService;
	}

	public JavaAgentConfig getAgentConfig() {
		return this.agentConfig;
	}

	final class Cleaner extends Thread {
		Cleaner() {
			setName("APM_Insight_Cleaner");
			setDaemon(false);
		}

		public void run() {
			JavaAgent.agent.shutdown();
		}
	}
}
