package com.sinosoft.monitor.agent.config;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.JavaAgentConstants;
import com.sinosoft.monitor.agent.util.Utils;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentConfigListener {
	private JavaAgentConfig javaAgentConfig;
	private long lastModifiedTime;
	private File confFile;
	private Logger logger;

	public AgentConfigListener(JavaAgentConfig javaAgentConfig, Logger logger)
			throws Throwable {
		this.javaAgentConfig = javaAgentConfig;
		this.logger = (logger == null ? javaAgentConfig.logger : logger);
		String path = this.javaAgentConfig.getAgentInstallDir().getAbsolutePath();
		path = path + File.separator + JavaAgentConstants.MONITOR_AGENT_FILENAME;
		this.confFile = new File(path);
		this.lastModifiedTime = this.confFile.lastModified();
		this.logger.info("Configuration file listener initialized successfully.");
	}

	public void execute() throws Throwable {
		if (this.confFile.lastModified() > this.lastModifiedTime) {
			this.logger.log(Level.FINE, "{0} last modified at {1} ms", new Object[]{this.confFile.getAbsolutePath(), Long.valueOf(this.lastModifiedTime)});

			loadConfigValues();

			if (this.javaAgentConfig.agentVerified) {
				Map agentConfigAsMap = this.javaAgentConfig.getAgentConfigAsMap();
//				JavaAgent.getInstance().getAgentService().sendDataToServer("agent_config_update", agentConfigAsMap);
				this.logger.info("Updated agent config sent to server.");
			}

			this.lastModifiedTime = this.confFile.lastModified();
		}
	}

	private void loadConfigValues() throws Throwable {
		Properties agentConfigProp = null;
		agentConfigProp = Utils.getContentAsProps(this.confFile);
		if ((agentConfigProp == null) || (agentConfigProp.isEmpty())) {
			this.logger.log(Level.WARNING, "Configuraion file at {0} is missing or corrupted! Monitor Agent will continue to work with old configuration values.", this.confFile.getAbsolutePath());
		} else {
			this.javaAgentConfig.initConnectionConfig(agentConfigProp);
			this.javaAgentConfig.initAgentConfValues(agentConfigProp);
			this.logger.log(Level.INFO, "Modified configuration values in file {0} updated successfully.", this.confFile.getAbsolutePath());
			this.logger.log(Level.INFO, "Updated Monitor Agent config: {0}", agentConfigProp);
		}
	}

	public void execute(Map customizedConfig) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.confFile));
			StringBuffer fileContent = new StringBuffer();
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				String key = line.split("=")[0];
				if ((!line.startsWith("#")) && (line.contains("=")) && (customizedConfig.containsKey(key))) {
					fileContent.append(key + "=" + customizedConfig.get(key).toString());
				} else {
					fileContent.append(line);
				}
				fileContent.append("\n");
			}
			in.close();
			BufferedWriter out = new BufferedWriter(new FileWriter(this.confFile));
			out.write(fileContent.toString());
			out.close();

			loadConfigValues();
			this.lastModifiedTime = this.confFile.lastModified();
		} catch (Throwable e) {
			this.logger.log(Level.WARNING, "Exception while updating the customized config info from web-client", e);
		}
	}

	public long getLastModifiedTime() {
		return this.lastModifiedTime;
	}
}
