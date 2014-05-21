package com.sinosoft.monitor.agent;

/**
 * agent配置所用的一些常量
 */

public abstract interface JavaAgentConstants {

	public static final String APMINSIGHT_HOME = "apminsight.home";
	public static final String APMINSIGHT_AGENT_JAR_NAME = "apminsight-javaagent.jar";
	public static final String APMINSIGHT_VERIFYCLASS = "apminsight_verifyclass";
	public static final String MONITOR_AGENT_FILENAME = "monitor_agent.conf";
	public static final String AGENTINFO_FILENAME = "apminsight.info";
	public static final String AGENT_ID = "agent.id";
	public static final String AGENT_ENABLED = "agent.enabled";
	public static final String APPLICATION_NAME = "application.name";
	public static final String LICENSE_KEY = "license.key";
	public static final String ONEM_HOST = "monitor.server.host";
	public static final String ONEM_PORT = "monitor.server.port";
	public static final String APM_PROTOCOL_HTTPS = "apm.protocol.https";
	public static final String APDEX_THRESHOLD = "apdex.threshold";
	public static final String TRANSACTION_TRACE_ENABLED = "transaction.trace.enabled";
	public static final String MONITOR_AGENT_PORT = "monitor.agent.port";
	public static final String TRANSACTION_TRACE_THRESHOLD = "transaction.trace.threshold";
	public static final String SQL_CAPTURE_ENABLED = "sql.capture.enabled";
	public static final String SQL_CAPTURED_PARAMETRIZE = "transaction.trace.sql.parametrize";
	public static final String SQL_STACKTRACE_THRESHOLD = "transaction.trace.sql.stacktrace.threshold";
	public static final String INCLUDE_PACKAGES = "monitor.agent.include.packages";
	public static final String SEQUENCEFILTER_URLPATTERN = "transaction.skip.listening";
	public static final String SAMPLING_FACTOR = "transaction.tracking.request.interval";
	public static final String SEQUENCE_BAG_MAX_TRACKER = "sequence.bag.max.tracker";
	public static final String TRANSACTION_INTERESTED_SEGMENTS = "transaction.interested.segments";
	public static final String AGENT_PRINT_STATISTICS = "agent.print.statistics";
	public static final String TRANSIENT_ROOTTRACKER_VAULT_SIZE = "monitor.agent.transient.rootracker.vault.size";
	public static final String TRANSACTION_TRACEITEM_EXCLUDE_THRESHOLD = "transaction.traceitem.exclude.threshold";
	public static final String TRANSACTION_TRACESTORE_SIZE = "transaction.tracestore.size";
	public static final String METRICSTORE_METRIC_BUCKET_SIZE = "metricstore.metric.bucket.size";
	public static final Boolean AGENT_ENABLED_DV = Boolean.TRUE;

	public static final Float APDEX_THRESHOLD_DV = Float.valueOf(0.5F);

	public static final Boolean TRANSACTION_TRACE_ENABLED_DV = Boolean.TRUE;

	public static final Integer AGENT_SERVER_PORT_DV = Integer.valueOf(0);

	public static final Float TRANSACTION_TRACE_THRESHOLD_DV = Float.valueOf(2.0F);

	public static final Boolean SQL_CAPTURE_ENABLED_DV = Boolean.TRUE;

	public static final Boolean SQL_CAPTURED_PARAMETRIZE_DV = Boolean.TRUE;

	public static final Float SQL_STACKTRACE_THRESHOLD_DV = Float.valueOf(1.0F);
	public static final String INCLUDE_PACKAGES_DV = "";
	public static final Integer APM_PORT_DV = Integer.valueOf(9090);

	public static final Short SAMPLING_FACTOR_DV = Short.valueOf((short) 1);
	public static final short SEQUENCE_BAG_MAX_TRACKER_DV = 1000;
	public static final byte AGENT_PRINT_STATISTICS_DV = 0;
	public static final int TRANSIENT_ROOTTRACKER_VAULT_SIZE_DV = 500;
	public static final int TRANSACTION_TRACEITEM_EXCLUDE_THRESHOLD_DV = 0;
//	public static final String SITE24X7_COLLECTOR_URL = "https://plusinsight.site24x7.com";
	public static final int TRANSACTION_TRACESTORE_SIZE_DV = 60;
	public static final int METRICSTORE_METRIC_BUCKET_SIZE_DV = 250;
	public static final String BEHIND_PROXY = "behind.proxy";
	public static final String PROXY_SERVER_HOST = "proxy.server.host";
	public static final String PROXY_SERVER_PORT = "proxy.server.port";
	public static final String PROXY_AUTH_USERNAME = "proxy.auth.username";
	public static final String PROXY_AUTH_USERNAME_DV = "";
	public static final String PROXY_AUTH_PASSWORD = "proxy.auth.password";
	public static final String PROXY_AUTH_PASSWORD_DV = "";
	public static final Boolean BEHIND_PROXY_DV = Boolean.FALSE;


}
