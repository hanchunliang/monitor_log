package com.sinosoft.monitor.agent.logging;

import com.sinosoft.monitor.agent.JavaAgentConstants;

import java.util.logging.Level;

public abstract interface LoggingConstants {
	public static final String LOGGER_NAME = "logger.name";
	public static final String LOG_LEVEL = "monitor.agent.log.level";
	public static final String LOG_DIR = "monitor.agent.log.dir";
	public static final String LOG_FILE = "log.file";
	public static final String LOG_LIMIT = "log.limit";
	public static final String LOG_FILECOUNT = "log.filecount";
	public static final String LOGGER_NAME_DV = "ME-Monitor-Agent";
	public static final Level LOG_LEVEL_DV = Level.INFO;
	public static final String LOG_FILE_DV = "monitor_agent.log";
	public static final String CONFIG_KEYVALUE_INVALID = "Invalid value specified for the key {0} in "+
			JavaAgentConstants.MONITOR_AGENT_FILENAME+". Monitor Agent will use the default value {1}.";
	public static final String CONFIG_KEYVALUE_MANDATORY = "Valid value for key {0} must be set in "+
			JavaAgentConstants.MONITOR_AGENT_FILENAME;
    public static final String IS_LOG_CONSOLE = "monitor.agent.log.console";
}
