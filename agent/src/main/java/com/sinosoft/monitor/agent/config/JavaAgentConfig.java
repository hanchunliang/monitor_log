package com.sinosoft.monitor.agent.config;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.JavaAgentConstants;
import com.sinosoft.monitor.agent.logging.APMInsightLogger;
import com.sinosoft.monitor.agent.logging.LoggingConfig;
import com.sinosoft.monitor.agent.logging.LoggingConstants;
import com.sinosoft.monitor.agent.sequence.SequenceFilterByUrl;
import com.sinosoft.monitor.agent.trackers.store.InvalidTypeStore;
import com.sinosoft.monitor.agent.util.JavaAgentUtil;
import com.sinosoft.monitor.agent.util.StringUtils;
import com.sinosoft.monitor.agent.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaAgentConfig implements JavaAgentConstants, LoggingConstants {
	public static final String AGENT_VERSION = "1.2";
	private File agentInstallDir;
	Logger logger;
	private LoggingConfig loggingConfig;
	private static String agentInstanceId;
	public boolean agentEnabled = AGENT_ENABLED_DV.booleanValue();
	public volatile boolean agentVerified;
	private String applicationName;
	private String licenseKey;
	private String apmHost;
	private int apmPort;
	private boolean followHttpsProtocol;
	private boolean isBehindProxy;
	private float apdexT;
	private boolean ttEnabled;
	public boolean traceDuplicate;
	private float ttT;
	public boolean captureSql;
	private boolean removeQueryLiterals;
	private float sqlTraceT;
	private int agentServerPort;
	private boolean dryRun;
	private String[] tracingIncludedPackages = new String[0];

	public static short samplingFactor = 0;
	private byte printStatistics;
	private int trackerStoreSize;
	private int traceItemExcludeThreshold;
	public static InterestedMethodsStore interestedMethodsStore = null;
	private static final String APMINSIGHT_PKG = "com.sinosoft.monitor.agent";
	private AgentConfigListener configListener;
	public static short maxTrackerInSeq;
	private int seqTraceStoreSize;
	private int metricStoreMetricBucketSize;
	private String agentCollectorConnectionURL;

    public static boolean EXCEPTION_OPT = true;
    private static String EXCEPTION_OPT_KEY = "monitor.agent.exception";

	public JavaAgentConfig(JavaAgent javaAgent) {
		init();
		try {
//			this.configListener = new AgentConfigListener(this, this.logger);
		} catch (Throwable th) {
			this.logger.warning("Unable to initialize AgentConfigListener. Exception: " + th.getMessage());
		}
	}

	public void init() throws RuntimeException {
		this.agentInstallDir = JavaAgentUtil.getAgentInstallDirectory();
		if (this.agentInstallDir == null) {
			String errMsg = "Monitor Agent: SEVERE: Unable to find agent installed directory. Try setting path in apminsight.home";
			JavaAgentUtil.print(errMsg);
			throw new RuntimeException(errMsg);
		}
		String homePath = this.agentInstallDir.getAbsolutePath();

		Properties agentConfigProp = null;
		try {
			agentConfigProp = Utils.getContentAsProps(new File(homePath + File.separator + MONITOR_AGENT_FILENAME));
			if ((agentConfigProp == null) || (agentConfigProp.isEmpty())) {
				String errMsg = "Monitor Agent: SEVERE: "+MONITOR_AGENT_FILENAME+" not found in " + homePath;
				JavaAgentUtil.print(errMsg);
				throw new RuntimeException(errMsg);
			}
		} catch (IOException e) {
			String errMsg = "Exception occured(file might not exist) while loading "+MONITOR_AGENT_FILENAME+" in " + homePath;
			throw new RuntimeException(errMsg);
		}
		initLogging(agentConfigProp);
        //初始化需要转JSON的类型的黑名单列表
        InvalidTypeStore.init(homePath+File.separator);
		try {
			Properties agentInfoProp = Utils.getContentAsProps(new File(homePath + File.separator + "apminsight.info"));
			initAgentInfoValues(agentInfoProp);
		} catch (IOException e) {
		}
		this.logger.log(Level.INFO, "Monitor Agent config: {0}", agentConfigProp);
		initAgentRuntimeConfig(agentConfigProp);
		initAgentConfValues(agentConfigProp);

		this.dryRun = Boolean.getBoolean("apminsight_dryrun");
		if (this.dryRun)
			this.logger.info("Monitor Agent agent running in DRY RUN mode. Agent will run without communicating with the server/collector");
	}

	private void initLogging(Properties agentConfigProp) {
		String loggerName = LOGGER_NAME_DV;
		String logDir = agentConfigProp.getProperty(LOG_DIR, this.agentInstallDir.getAbsolutePath());
		String logFile = agentConfigProp.getProperty(LOG_FILE, LOG_FILE_DV);
		Level level = LOG_LEVEL_DV;
        String isLogConsole = "0";
		try {
			level = Level.parse(agentConfigProp.getProperty(LOG_LEVEL));
            isLogConsole = agentConfigProp.getProperty(IS_LOG_CONSOLE);
		} catch (IllegalArgumentException e) {
			JavaAgentUtil.print("Monitor Agent: Warning: invalid logging level specified. Using default level " + LOG_LEVEL_DV.toString());
		}
		this.loggingConfig = LoggingConfig.AGENT_LOGGER;
		this.loggingConfig.customConfig(loggerName, logDir, logFile, 10, 20, level,isLogConsole);

		JavaAgent.logger = this.logger = APMInsightLogger.initialize(this.loggingConfig);
		this.logger.log(Level.INFO, "Logging initialized successfully.");
	}

	public void initAgentRuntimeConfig(Properties agentConfigProp) {
		initConnectionConfig(agentConfigProp);
		this.applicationName = agentConfigProp.getProperty("application.name","defaultApp");
		if (StringUtils.isEmptyString(new String[]{this.applicationName})) {
			this.logger.log(Level.SEVERE, "Valid value for key {0} must be set in "+MONITOR_AGENT_FILENAME+".", "application.name");
			throw new RuntimeException("application.name must be set");
		}
		try {
			this.agentServerPort = Integer.parseInt(agentConfigProp.getProperty(MONITOR_AGENT_PORT));
		} catch (NumberFormatException e) {
			this.logger.log(Level.SEVERE, "Valid value for key {0} must be set in "+MONITOR_AGENT_FILENAME+".", MONITOR_AGENT_PORT);
			throw new RuntimeException("Valid "+MONITOR_AGENT_PORT+" must be set.");
		}
	}

	void initConnectionConfig(Properties agentConfigProp) {
		this.licenseKey = agentConfigProp.getProperty("license.key");

		if (this.licenseKey == null) {
			this.apmHost = agentConfigProp.getProperty(ONEM_HOST);
			if (StringUtils.isEmptyString(new String[]{this.apmHost})) {
				this.logger.log(Level.SEVERE, "Valid value for key {0} must be set in "+MONITOR_AGENT_FILENAME+".", ONEM_HOST);
				throw new RuntimeException(ONEM_HOST+" property must be set.");
			}

			this.followHttpsProtocol = Boolean.parseBoolean(agentConfigProp.getProperty("apm.protocol.https"));

			String apmPortStr = agentConfigProp.getProperty(ONEM_PORT);
			try {
				this.apmPort = Integer.parseInt(apmPortStr);
			} catch (NumberFormatException e) {
				this.logger.log(Level.SEVERE, "Valid value for key {0} must be set in "+MONITOR_AGENT_FILENAME+".", ONEM_PORT);
				throw new RuntimeException(ONEM_PORT+" property must be set or invalid.");
			}

		} else {
			this.licenseKey = (this.licenseKey.trim() + "");
			if ("".equals(this.licenseKey)) {
				this.logger.log(Level.SEVERE, "Valid value for key {0} must be set in "+MONITOR_AGENT_FILENAME+".", "license.key");
				throw new RuntimeException("license.key is invalid.");
			}
			this.apmHost = agentConfigProp.getProperty(ONEM_HOST);
			this.followHttpsProtocol = true;
		}
        this.logger.info("JavaAgentConfig: monitor.server.host="+this.apmHost+",monitor.server.port="+this.apmPort);
		constructAgentCollectorConnectionURL();
	}

	void initAgentConfValues(Properties agentConfigProp) throws RuntimeException {
		try {
			this.apdexT = Float.parseFloat(agentConfigProp.getProperty("apdex.threshold","0.5"));
		} catch (NumberFormatException e) {
			this.apdexT = APDEX_THRESHOLD_DV.floatValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"apdex.threshold", APDEX_THRESHOLD_DV});
		}

		if (agentConfigProp.getProperty("transaction.trace.enabled") == null) {
			this.ttEnabled = TRANSACTION_TRACE_ENABLED_DV.booleanValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"transaction.trace.enabled", TRANSACTION_TRACE_ENABLED_DV});
		} else {
			this.ttEnabled = Boolean.parseBoolean(agentConfigProp.getProperty("transaction.trace.enabled"));
		}
		try {
			this.ttT = Float.parseFloat(agentConfigProp.getProperty(TRANSACTION_TRACE_THRESHOLD,"2"));
		} catch (NumberFormatException e) {
			this.apdexT = TRANSACTION_TRACE_THRESHOLD_DV.floatValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"transaction.trace.threshold", TRANSACTION_TRACE_THRESHOLD_DV});
		}

		if (agentConfigProp.getProperty("sql.capture.enabled") == null) {
			this.captureSql = SQL_CAPTURE_ENABLED_DV.booleanValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"sql.capture.enabled", SQL_CAPTURE_ENABLED_DV});
		} else {
			this.captureSql = Boolean.parseBoolean(agentConfigProp.getProperty("sql.capture.enabled"));
		}

		if (agentConfigProp.getProperty("transaction.trace.sql.parametrize") == null) {
			this.removeQueryLiterals = SQL_CAPTURED_PARAMETRIZE_DV.booleanValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"transaction.trace.sql.parametrize", SQL_CAPTURED_PARAMETRIZE_DV});
		} else {
			this.removeQueryLiterals = Boolean.parseBoolean(agentConfigProp.getProperty("transaction.trace.sql.parametrize"));
		}
		try {
			this.sqlTraceT = Float.parseFloat(agentConfigProp.getProperty("transaction.trace.sql.stacktrace.threshold","3"));
		} catch (NumberFormatException e) {
			this.sqlTraceT = SQL_STACKTRACE_THRESHOLD_DV.floatValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"transaction.trace.sql.stacktrace.threshold", SQL_STACKTRACE_THRESHOLD_DV});
		}
		try {
			samplingFactor = Short.parseShort(agentConfigProp.getProperty("transaction.tracking.request.interval"));
		} catch (NumberFormatException e) {
			samplingFactor = SAMPLING_FACTOR_DV.shortValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"transaction.tracking.request.interval", SAMPLING_FACTOR_DV});
		}

		if (agentConfigProp.getProperty("behind.proxy") == null) {
			this.isBehindProxy = BEHIND_PROXY_DV.booleanValue();
			this.logger.log(Level.WARNING, "Invalid value specified for the key {0} in "+MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.", new Object[]{"behind.proxy", BEHIND_PROXY_DV});
		} else {
			this.isBehindProxy = Boolean.parseBoolean(agentConfigProp.getProperty("behind.proxy"));
		}

		if (this.isBehindProxy) {
//			ProxyConfigHandler.configureProxy(agentConfigProp.getProperty("proxy.server.host"), agentConfigProp.getProperty("proxy.server.port"), agentConfigProp.getProperty("proxy.auth.username", ""), agentConfigProp.getProperty("proxy.auth.password", ""));
		}

		String includePackages = agentConfigProp.getProperty(INCLUDE_PACKAGES, "");
		if (!StringUtils.isEmptyString(new String[]{includePackages})) {
			this.tracingIncludedPackages = includePackages.split(",");
		}

		SequenceFilterByUrl.initialize(agentConfigProp.getProperty("transaction.skip.listening"));

		String maxTrackerInSeq = agentConfigProp.getProperty("sequence.bag.max.tracker", "1000");
		try {
			this.maxTrackerInSeq = Short.parseShort(maxTrackerInSeq);
		} catch (NumberFormatException ne) {
			this.maxTrackerInSeq = 1000;
		}

		this.traceDuplicate = Boolean.parseBoolean(agentConfigProp.getProperty("transaction.trace.duplicate", "false"));

//		String interestedMethods = agentConfigProp.getProperty("transaction.interested.segments");
//		if (!StringUtils.isEmptyString(new String[]{interestedMethods})) {
//			interestedMethodsStore = new InterestedMethodsStore(interestedMethods);
//			this.logger.info("InterestedMethodsStore initialized successfully. Data: " + interestedMethodsStore);
//		}
        interestedMethodsStore = new InterestedMethodsStore(null);

		String printStatistics = agentConfigProp.getProperty("agent.print.statistics", "0");

        EXCEPTION_OPT = (
                "0".equals(agentConfigProp.getProperty(EXCEPTION_OPT_KEY))
        )?false:true ;

		try {
			this.printStatistics = Byte.parseByte(printStatistics);
		} catch (NumberFormatException ne) {
			this.printStatistics = 0;
		}
		try {
			this.trackerStoreSize = Integer.parseInt(agentConfigProp.getProperty(TRANSIENT_ROOTTRACKER_VAULT_SIZE, "500"));
		} catch (NumberFormatException e) {
			this.trackerStoreSize = 500;
		}
		try {
			this.traceItemExcludeThreshold = Integer.parseInt(agentConfigProp.getProperty("transaction.traceitem.exclude.threshold", "0"));
		} catch (NumberFormatException e) {
			this.traceItemExcludeThreshold = 0;
		}
		try {
			this.seqTraceStoreSize = Integer.parseInt(agentConfigProp.getProperty("transaction.tracestore.size", "60"));
		} catch (NumberFormatException e) {
			this.seqTraceStoreSize = 60;
		}
		try {
			this.metricStoreMetricBucketSize = Integer.parseInt(agentConfigProp.getProperty("metricstore.metric.bucket.size", "250"));
		} catch (NumberFormatException e) {
			this.metricStoreMetricBucketSize = 250;
		}
	}

	private void initAgentInfoValues(Properties agentInfoProp) throws RuntimeException {
		this.agentEnabled = AGENT_ENABLED_DV.booleanValue();
		if ((agentInfoProp != null) && (!agentInfoProp.isEmpty())) {
			try {
				this.agentInstanceId = agentInfoProp.getProperty("agent.id");
				this.logger.log(Level.INFO, "Monitor Agent agent instance-id: " + this.agentInstanceId);
			} catch (NumberFormatException e) {
				this.logger.log(Level.WARNING, "Invalid value for {0} in {1} at {2}", new Object[]{"agent.id", "apminsight.info", this.agentInstallDir.getAbsolutePath()});
			}
			if ("false".equals(agentInfoProp.getProperty("agent.enabled"))) {
				this.agentEnabled = false;
				this.logger.info("Monitor Agent agent in disabled state");
			}
		}
	}

	public Map<String, Object> getVMEnvironment() {
		Map vmEnv = new LinkedHashMap();

		vmEnv.put("Agent Install Path", this.agentInstallDir.getAbsolutePath());
		vmEnv.put("Agent Version", "1.2");

		vmEnv.put("Java vendor", System.getProperty("java.vendor"));
		vmEnv.put("Java version", System.getProperty("java.version"));

		return vmEnv;
	}

	public Map<String, Object> getAgentConfigAsMap() {
		Map agentConfigInfo = new LinkedHashMap();
		agentConfigInfo.put("last.modified.time", Long.valueOf(this.configListener.getLastModifiedTime()));
		agentConfigInfo.put("apdex.threshold", Double.valueOf(getApdexThreshold() / 1000.0D));
		agentConfigInfo.put("sql.capture.enabled", Integer.valueOf(this.captureSql ? 1 : 0));
		agentConfigInfo.put("transaction.trace.enabled", Integer.valueOf(isSequenceTraceEnabled() ? 1 : 0));
		agentConfigInfo.put("transaction.trace.threshold", Double.valueOf(getSequenceTraceThreshold() / 1000.0D));
		agentConfigInfo.put("transaction.trace.sql.parametrize", Integer.valueOf(shouldRemoveQueryLiterals() ? 1 : 0));
		agentConfigInfo.put("transaction.trace.sql.stacktrace.threshold", Double.valueOf(getSqlTraceThreshold() / 1000.0D));
		agentConfigInfo.put("transaction.tracking.request.interval", Short.valueOf(samplingFactor));
		return agentConfigInfo;
	}

	public void writeAgentInfo() {
		writeAgentInfo("" + getAgentInstanceId(), AGENT_ENABLED_DV);
	}

	public void writeAgentInfo(String agentId, Boolean agentEnabled) {
		agentEnabled = agentEnabled == null ? AGENT_ENABLED_DV : agentEnabled;
		Properties p = new Properties();
		p.setProperty("agent.id", agentId);
		p.setProperty("agent.enabled", agentEnabled.toString());
		String filePath = this.agentInstallDir + File.separator + "apminsight.info";
		try {
			Utils.writePropsAsFile(p, filePath);
			this.logger.info("Agent info successfully saved at " + filePath);
		} catch (Throwable ex) {
			this.logger.warning("Unable to write the agent info at " + filePath + ". Exception: " + ex.getMessage() + "Monitor Agent will continue to work keeping agent info in memory");
		}
	}

	public void updateAgentInfo() {
		writeAgentInfo(this.agentInstanceId + "", Boolean.valueOf(this.agentEnabled));
		this.logger.log(Level.INFO, "{0} updated", "apminsight.info");
	}

	public void deleteAgentInfo() {
		File agentInfoFile = new File(this.agentInstallDir + File.separator + "apminsight.info");
		if (agentInfoFile.exists()) {
			try {
				agentInfoFile.delete();
				this.logger.log(Level.INFO, "{0} in {1} deleted successfully.", new Object[]{"apminsight.info", this.agentInstallDir.getAbsolutePath()});
			} catch (Exception ex) {
				this.logger.log(Level.WARNING, "Unable to delete {0} in {1}.", new Object[]{"apminsight.info", this.agentInstallDir.getAbsolutePath()});
			}
		} else
			this.logger.log(Level.WARNING, "{0} not exists in {1} to delete.", new Object[]{"apminsight.info", this.agentInstallDir.getAbsolutePath()});
	}

	public synchronized void refreshNow() {
		if (this.configListener != null)
			try {
				this.configListener.execute();
			} catch (Throwable th) {
				this.logger.warning("Unable to execute configListener. Exception: " + th.getMessage());
			}
	}

	public void refreshNow(Map customizedConfig) {
		Map existingConfig = getAgentConfigAsMap();
		if ((!customizedConfig.equals(existingConfig)) && (((Number) existingConfig.get("last.modified.time")).longValue() < ((Number) customizedConfig.get("last.modified.time")).longValue())) {
			this.configListener.execute(customizedConfig);
		}
	}

	public Logger getLogger() {
		return this.logger;
	}

	public String getLicenseKey() {
		return this.licenseKey;
	}

	public String getAPMHost() {
		return this.apmHost;
	}

	public int getAPMPort() {
		return this.apmPort;
	}

	public boolean isDryRun() {
		return this.dryRun;
	}

	public static String getAgentInstanceId() {
		return agentInstanceId;
	}

	public File getAgentInstallDir() {
		return this.agentInstallDir;
	}

	public String[] getTracingIncludedPackages() {
		return this.tracingIncludedPackages;
	}

	public String getApminsightPkg() {
		return "com.sinosoft.monitor.agent";
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public float getSqlTraceThreshold() {
		return this.sqlTraceT * 1000.0F;
	}

	public void setAgentInstanceId(String agentId) {
		agentInstanceId = agentId;
	}

	public float getApdexThreshold() {
		return this.apdexT * 1000.0F;
	}

	public int getAgentServerPort() {
		return this.agentServerPort;
	}

	public boolean isSequenceTraceEnabled() {
		return this.ttEnabled;
	}

	public float getSequenceTraceThreshold() {
		return this.ttT * 1000.0F;
	}

	public boolean shouldRemoveQueryLiterals() {
		return this.removeQueryLiterals;
	}

	public boolean isFollowHttpsProtocol() {
		return this.followHttpsProtocol;
	}

	public byte printAgentStatistics() {
		return this.printStatistics;
	}

	public int getTrackerStoreSize() {
		return this.trackerStoreSize;
	}

	public int getTraceItemExcludeThreshold() {
		return this.traceItemExcludeThreshold;
	}

	public int getSeqTraceStoreSize() {
		return this.seqTraceStoreSize;
	}

	public int getMetricStoreMetricBucketSize() {
		return this.metricStoreMetricBucketSize;
	}

	public String getAgentCollectorConnectionURL() {
		return this.agentCollectorConnectionURL;
	}

	private void constructAgentCollectorConnectionURL() {
		StringBuilder sb = new StringBuilder(isFollowHttpsProtocol() ? "https://" : "http://");

		if (getLicenseKey() == null) {
			sb.append(getAPMHost());
			sb.append(':');
			sb.append(getAPMPort());
			this.agentCollectorConnectionURL = sb.toString();
		} else {
			//this.agentCollectorConnectionURL = (StringUtils.isEmptyString(new String[]{getAPMHost()}) ? "https://plusinsight.site24x7.com" : getAPMHost());
		}
	}
}
