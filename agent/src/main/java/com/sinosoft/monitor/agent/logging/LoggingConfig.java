package com.sinosoft.monitor.agent.logging;

import com.sinosoft.monitor.agent.util.StringUtils;

import java.io.File;
import java.util.logging.Level;

public enum LoggingConfig {
	AGENT_LOGGER(LoggingConstants.LOGGER_NAME_DV, ".", "monitor_agent.log", 10, 10, Level.ALL),
	COLLECTOR_LOGGER(LoggingConstants.LOGGER_NAME_DV+"-Collector", ".." + File.separator + "logs" + File.separator + "monitor_agent", "collector.log", 10, 10, Level.ALL),
	CLIENT_LOGGER(LoggingConstants.LOGGER_NAME_DV+"-Client", ".." + File.separator + "logs" + File.separator + "monitor_agent", "client.log", 10, 10, Level.ALL);

	private String loggerName;
	private String logDir;
	private String logFile;
	private int logLimit;
	private int logFileCount;
	private Level level;
    private boolean isLogConsole = false;

	private LoggingConfig(String loggerName, String logDir, String logFile, int logLimit, int logFileCount, Level level) {
		this.loggerName = loggerName;
		this.logDir = logDir;
		this.logFile = logFile;
		this.logLimit = logLimit;
		this.logFileCount = logFileCount;
		this.level = level;
	}

	public void customConfig(String loggerName, String logDir, String logFile, int logLimit, int logFileCount, Level level,String isLogConsole) {
		this.loggerName = (!StringUtils.isEmptyString(new String[]{loggerName}) ? loggerName : this.loggerName);
		this.logDir = (!StringUtils.isEmptyString(new String[]{logDir}) ? logDir : this.logDir);
		this.logFile = (!StringUtils.isEmptyString(new String[]{logFile}) ? logFile : this.logFile);
		this.logLimit = (logLimit > 0 ? logLimit : this.logLimit);
		this.logFileCount = (logFileCount > 0 ? logFileCount : this.logFileCount);
		this.level = (level != null ? level : this.level);
        this.isLogConsole = "1".equals(isLogConsole)?true:false;
	}

    public boolean isLogConsole() {
        return isLogConsole;
    }

    public void setLogConsole(boolean logConsole) {
        isLogConsole = logConsole;
    }

    public String getLoggerName() {
		return this.loggerName;
	}

	public String getLogDir() {
		return this.logDir;
	}

	public String getLogFile() {
		return this.logFile;
	}

	public int getLogLimit() {
		return this.logLimit;
	}

	public int getLogFileCount() {
		return this.logFileCount;
	}

	public Level getLevel() {
		return this.level;
	}

	public static void setLevel(Level level) {
		COLLECTOR_LOGGER.level = level;
		CLIENT_LOGGER.level = level;
		AGENT_LOGGER.level = level;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LoggingConfig{");
		sb.append(this.loggerName).append(",");
		sb.append(this.logDir).append(",");
		sb.append(this.logFile).append(",");
		sb.append(this.logLimit).append(",");
		sb.append(this.logFileCount).append(",");
		sb.append(this.level);
		sb.append("}");
		return sb.toString();
	}
}
