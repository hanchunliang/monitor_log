package com.sinosoft.monitor.agent.logging;

import com.sinosoft.monitor.agent.util.FileUtil;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APMInsightLogger
		implements LoggingConstants {
	private static Logger logger;
	private static Logger clientLogger;
	private static boolean initialized;
	private static FileHandler fh;
	private static LoggingConfig loggingConfig;

	public static Logger initialize(LoggingConfig config) {
		if (initialized) {
			return logger;
		}
		loggingConfig = config;
		logger = Logger.getLogger(loggingConfig.getLoggerName());
		logger.setUseParentHandlers(false);
		File agentDir = new File(loggingConfig.getLogDir());
		if (!agentDir.isDirectory()) {
			try {
				FileUtil.getInstance().createDirectory(loggingConfig.getLogDir());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fileName = loggingConfig.getLogFile();
		File logFile = new File(agentDir, fileName);
		try {
			int limit = loggingConfig.getLogLimit() * 1024 * 1024;
			int count = loggingConfig.getLogFileCount();
			fh = new FileHandler(logFile.getPath(), limit, count, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		fh.setFormatter(new LogFormatter());
		logger.addHandler(fh);
        if (loggingConfig.isLogConsole()){
            logger.addHandler(new ConsoleHandler());
        }
		logger.setLevel(loggingConfig.getLevel());
		initialized = true;
		return logger;
	}

	public static Logger getLogger(LoggingConfig config) {
		Logger logger = Logger.getLogger(config.getLoggerName());
		logger.setUseParentHandlers(false);
		File agentDir = new File(loggingConfig.getLogDir());
		String fileName = config.getLogFile();
		File logFile = new File(agentDir, fileName);
		FileHandler fh = null;
		try {
			int limit = config.getLogLimit() * 1024 * 1024;
			int count = config.getLogFileCount();
			fh = new FileHandler(logFile.getPath(), limit, count, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		fh.setFormatter(new LogFormatter());
		logger.addHandler(fh);
		logger.setLevel(config.getLevel());
		return logger;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static Logger getClientLogger() {
		if (clientLogger == null) {
			clientLogger = getLogger(LoggingConfig.CLIENT_LOGGER);
		}
		return clientLogger;
	}

	public static Logger reinitialize(LoggingConfig config) {
		synchronized (logger) {
			initialized = false;
			initialize(config);
			initialized = true;
		}
		return logger;
	}

	public static void logStackTrace(Level level, StackTraceElement[] trace) {
		for (StackTraceElement traceElement : trace) {
			logger.log(level, "at {0}.{1}({2}:{3})", new Object[]{traceElement.getClassName(), traceElement.getMethodName(), traceElement.getFileName(), Integer.valueOf(traceElement.getLineNumber())});
		}
	}

	public static void shutdown() {
		logger.info("Logger shutting down");
		if (fh != null) {
			try {
				fh.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
