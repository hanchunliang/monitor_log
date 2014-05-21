package com.sinosoft.monitor.agent.service;

import com.sinosoft.monitor.agent.ExceptionMessages;
import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.agent.exception.FatalException;
import com.sinosoft.monitor.agent.exception.MalformedException;
import com.sinosoft.monitor.agent.thread.WorkerThreadFactory;
import com.sinosoft.monitor.agent.tracing.TrackerService;
import com.sinosoft.monitor.agent.trackers.TrackerProcessor;
import com.sinosoft.monitor.agent.trackers.TrackerStore;
import com.sinosoft.monitor.agent.transport.TraceDataDispatcher;
import com.sinosoft.monitor.agent.util.HostNameUtil;
import com.sinosoft.monitor.agent.util.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * agent的服务，
 * 主要负责
 * 1.初始化sslcontext
 * 2.建立定时任务
 */
public class JavaAgentService implements AgentServiceConstants {
	private static final String CLZ_NAME = "[JavaAgentService]";
	private JavaAgentConfig javaAgentConfig;
	private String collectorHost;
	private int collectorPort;
	private SSLContext sslContext;
	private HostnameVerifier hostnameVerifier;
//	private static final String ARH = "/arh/";
	private ExecutorService scheduledExecutorService;
	private TrackerService tracerService;
	private TrackerProcessor trackerProcessor;

	private final String clientIp;
	private final String clientPort;
    private final String applicationName = "monitorlog";

    private int threadCount = 10;

	public JavaAgentService(JavaAgentConfig config) {
		this.javaAgentConfig = config;
		initSecureConnection();

		//定义单线程的定时执行
//		this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new WorkerThreadFactory("agentsc", true));
        this.scheduledExecutorService = Executors.newFixedThreadPool(threadCount, new WorkerThreadFactory("agentsc", true));
		this.tracerService = new TrackerService();

		clientIp = HostNameUtil.getLocalHostIP();
		clientPort = String.valueOf(this.javaAgentConfig.getAgentServerPort());
	}

	public void init() {
//		if (!this.javaAgentConfig.isDryRun()) {
//			long sleepTime = 0L;
//			byte counter = -1;
//			boolean reconnect = true;
//			while (reconnect) {
//				if (counter <= 40) {
//					counter = (byte) (counter + 1);
//					if (counter % 10 == 0)
//						sleepTime += 60000L;
//				}
//				try {
//					connect();
//					reconnect = false;
//				} catch (IOException e) {
//					this.javaAgentConfig.getLogger().log(Level.WARNING, "Could not able to connect with server which receives metrics. Will try to reconnect after {0} minute.", Long.valueOf(sleepTime / 60000L));
//
//					sleep(sleepTime);
//				} catch (MalformedException e) {
//					this.javaAgentConfig.getLogger().log(Level.WARNING, "MalformedException", e);
//					return;
//				} catch (FatalException e) {
//					this.javaAgentConfig.getLogger().log(Level.WARNING, "FatalException", e);
//					return;
//				} catch (Throwable th) {
//					th.printStackTrace();
//					this.javaAgentConfig.getLogger().log(Level.WARNING, "Exception in Connect: {0}. Will try to reconnect after {1} minute.", new Object[]{th.getMessage(), Long.valueOf(sleepTime / 60000L)});
//
//					sleep(sleepTime);
//				}
//			}
//		}

		scheduleTraceDataSender();
		this.trackerProcessor = new TrackerProcessor(TrackerStore.trackerStore, this.javaAgentConfig, null);
		this.trackerProcessor.start();
		this.javaAgentConfig.agentVerified = true;
	}

	void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);

			this.javaAgentConfig.refreshNow();
		} catch (Throwable ex) {
			this.javaAgentConfig.getLogger().warning(ex.getMessage());
		}
	}

	public TrackerService getTrackerService() {
		return this.tracerService;
	}

	/**
	 * 首次启动client，向服务端发送初始化信息，并获取服务端的认证信息。
	 * @throws java.io.IOException
	 * @throws com.sinosoft.monitor.agent.exception.MalformedException
	 * @throws com.sinosoft.monitor.agent.exception.FatalException
	 */
	public void connect() throws IOException, MalformedException, FatalException {
		//TODO 目前启动时没有连接，以后可能会需要发送一些验证和配置信息。
		Map payload = new LinkedHashMap();
		payload.put("agent_info", getConnectInfo());
//		payload.put("environment", this.javaAgentConfig.getVMEnvironment());
//		payload.put("custom_config_info", this.javaAgentConfig.getAgentConfigAsMap());
		this.javaAgentConfig.getLogger().info("Connecting server.. Payload: " + payload);
		Map responseData = (Map) sendDataToServer("/manager/appmanager/match", getConnectInfo().getBytes());
		this.javaAgentConfig.getLogger().fine("Response received after connecting server: " + responseData);
		String applicationId = (String) responseData.get("applicationId");
		if(applicationId != null){
			this.javaAgentConfig.setAgentInstanceId(applicationId);
		}

//		Map collectorInfo = (Map) responseData.get("collector-info");
//		if (collectorInfo == null) {
//			this.javaAgentConfig.getLogger().warning("collector-info is not returned by the server");
//			throw new RuntimeException("collector-info is not returned by the server");
//		}
//		this.collectorHost = ((String) collectorInfo.get("host"));
//		this.collectorPort = Integer.parseInt((String) collectorInfo.get("port"));
//		Map instanceInfo = (Map) responseData.get("instance-info");
//		if (instanceInfo != null) {
//			this.javaAgentConfig.setAgentInstanceId((String) instanceInfo.get("instanceid"));
//			this.javaAgentConfig.writeAgentInfo();
//		}

		JavaAgent.logger.log(Level.FINE, "Succefully connected to the server. collector-info: {0}", applicationId);
	}

	private String getConnectInfo() {
		StringBuilder connectInfo = new StringBuilder();
		connectInfo.append("type=JAVA&agent.version=3.0")
		.append("&appName=").append(this.javaAgentConfig.getApplicationName())
		.append("&ip=").append(HostNameUtil.getLocalHostIP())
		.append("&port=").append(String.valueOf(this.javaAgentConfig.getAgentServerPort()));

		return connectInfo.toString();
	}
    public String getAgentInfo(){
        StringBuilder agentInfo = new StringBuilder();
        agentInfo.append("ip=").append(HostNameUtil.getLocalHostIP());
        agentInfo.append("&port=").append(String.valueOf(this.javaAgentConfig.getAgentServerPort()));
        return agentInfo.toString();
    }
	/**
	 * scheduleMetricDataSender
	 * 定时发送metric信息，每分钟发送一次
	 */
	private void scheduleTraceDataSender() {
//		this.scheduledExecutorService.scheduleAtFixedRate(new TraceDataDispatcher(JavaAgent.getInstance()), 60000L, 60000L, TimeUnit.MILLISECONDS);
		//for test
//		this.scheduledExecutorService.scheduleAtFixedRate(new TraceDataDispatcher(JavaAgent.getInstance()), 6000L, 1L, TimeUnit.MILLISECONDS);
        int i = 0;
        while (i<threadCount){
            scheduledExecutorService.execute(new TraceDataDispatcher(JavaAgent.getInstance()));
            i++;
        }
//        JavaAgent.logger.info("Scheduled Metric data dispatcher started successfully.");
	}

	public void shutdown() {
		if (this.javaAgentConfig.agentVerified) {
			this.trackerProcessor.shutdown();
			this.scheduledExecutorService.shutdownNow();
			JavaAgent.logger.info("Scheduled Metric data dispatcher shutdown successfully.");
		}
	}

	public Object sendDataToServer(String operation, byte[] data) throws IOException, MalformedException, FatalException {
		return sendData(this.javaAgentConfig.getLicenseKey(), operation, data);
	}

    public String getApplicationStringFromServer(){
        StringBuilder uri = new StringBuilder(javaAgentConfig.getAgentCollectorConnectionURL())
                .append("/")
                .append(applicationName)
                .append("/application/agent/listApplication?")
                .append(getAgentInfo());
        String uriStr = uri.toString().intern();
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(uriStr);
            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            JavaAgent.logger.log(Level.SEVERE,"create Url error: course by JavaAgentService.getApplicationStringFromServer",e);
        } catch (IOException e){
            JavaAgent.logger.log(Level.SEVERE,"open connection error: course by JavaAgentService.getApplicationStringFromServer",e);
        }
        try {
            if (this.javaAgentConfig.isFollowHttpsProtocol()) {
                ((HttpsURLConnection) conn).setSSLSocketFactory(this.sslContext.getSocketFactory());
                ((HttpsURLConnection) conn).setHostnameVerifier(this.hostnameVerifier);
            }
            conn.connect();
            int responseCode = conn.getResponseCode();
			//JavaAgent.logger.log(Level.INFO, "URL[{0}]:The response code is {1}", new Object[]{uriStr,Integer.valueOf(responseCode)});
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			try {
				String result = reader.readLine();
                //JavaAgent.logger.log(Level.INFO, "URL[{0}]:The result is {1}", new Object[]{uriStr,result});
                return result;
			} catch (Exception pe) {
				throw new MalformedException("ParseException", pe);
			} finally {
                reader.close();
            }
        } catch (Exception e) {
            JavaAgent.logger.warning("try get data from server but is fail");
        } finally {
            conn.disconnect();
        }
        return null;
    }

	private Object sendData(String licenseKey, String operation, byte[] data)
			throws IOException, MalformedException, FatalException {
		if (StringUtils.isEmptyString(new String[]{operation})) {
			throw new IllegalArgumentException("Operation cannot be null or empty: " + operation);
		}

		Object response = null;

		StringBuilder uri = new StringBuilder(this.javaAgentConfig.getAgentCollectorConnectionURL())
				.append("/")
                .append(applicationName)
                .append("/application")
                .append(operation);
				//.append("?").append(getConnectInfo());
//		char queryStringAdder = '?';
//		if (!StringUtils.isEmptyString(new String[]{this.javaAgentConfig.getAgentInstanceId()})) {
//			uri.append(queryStringAdder).append("instance_id").append('=').append(this.javaAgentConfig.getAgentInstanceId());
//			queryStringAdder = '&';
//		}
//		if (!StringUtils.isEmptyString(new String[]{licenseKey})) {
//			uri.append(queryStringAdder).append("license.key").append('=').append(licenseKey);
//		}

		String uriStr = uri.toString().intern();
		URL url = null;
		try {
			url = new URL(uriStr);
		} catch (MalformedURLException e) {
			throw new MalformedException("MalformedURLException", e);
		}
		//JavaAgent.logger.log("connect".equals(operation) ? Level.INFO : Level.FINE, "[JavaAgentService] URL to be executed: {0}", url.toExternalForm().intern());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try {
			if (this.javaAgentConfig.isFollowHttpsProtocol()) {
				((HttpsURLConnection) conn).setSSLSocketFactory(this.sslContext.getSocketFactory());
				((HttpsURLConnection) conn).setHostnameVerifier(this.hostnameVerifier);
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
            conn.setAllowUserInteraction(true);
//			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//			conn.setRequestProperty("accept-encoding", "application/json");
			conn.setRequestMethod("POST");
			conn.connect();
			OutputStream outStream = conn.getOutputStream();
//			Writer out = new OutputStreamWriter(outStream);
//			System.out.println("write data length is" + data.length);
//            JavaAgent.logger.info("write data length is：" + data.length);
            outStream.write(data);
            outStream.flush();
            outStream.close();

			int responseCode = conn.getResponseCode();
			if (responseCode != 200)
				throw new RuntimeException(ExceptionMessages.getMessage("HttpException:Message \" {0} \"received with response code {1}", new Object[]{response, Integer.valueOf(responseCode)}));
		} catch (Exception e) {
			JavaAgent.logger.warning("try sendData to server but is fail");
		} finally {
			conn.disconnect();
		}
		return null;
	}

	private Object parseResponse(Object response) throws FatalException {
		if ((response == null) || (!(response instanceof Map))) {
			return null;
		}
		Map responseMap = (Map) response;
		if (responseMap.isEmpty()) {
			return null;
		}

		String applicationId = (String) responseMap.get("applicationId");

		boolean result = Boolean.valueOf(String.valueOf(responseMap.get("result"))).booleanValue();
		Map responseData = new HashMap();
		if(applicationId != null){
			responseData.put("applicationId",applicationId);
		} else {
			return responseMap;
		}
//		System.out.println("response data:" + responseData);
//		if (!result) {
//			Object exception = responseData != null ? responseData.get("exception") : null;
//			if (exception != null) {
//				throw new RuntimeException(ExceptionMessages.getMessage("{0} occurred in the server with message \"{1}\"", ((Map) exception).values().toArray()));
//			}
//
//			throw new RuntimeException("Some error occurred");
//		}
//
//		if (responseData == null) {
//			return null;
//		}
//
//		Object cResponseCodeObj = responseData.get("response-code");
//		System.out.println("response code:" + cResponseCodeObj);
//		if (cResponseCodeObj != null) {
////			boolean processFurther = ResponseCodeHandler.processResponseCode(Integer.valueOf(StringUtils.getAsInteger(cResponseCodeObj.toString())));
////			if (!processFurther) {
////				throw new FatalException("Collector response code: " + cResponseCodeObj.toString() + ". Cannot proceed further.");
////			}
//
//		}
//
//		Map customizedConfig = (Map) responseData.get("custom_config_info");
//		System.out.println("response custom_config_info:" + customizedConfig);
//		if ((customizedConfig != null) && (!customizedConfig.isEmpty())) {
//			this.javaAgentConfig.refreshNow(customizedConfig);
//		}
		return responseData;
	}

	private void initSecureConnection() {
		try {
			TrustManager[] trustAllCerts = {new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}
			};
			this.hostnameVerifier = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			};
			this.sslContext = SSLContext.getInstance("SSL");
			this.sslContext.init(null, trustAllCerts, new SecureRandom());
			this.javaAgentConfig.getLogger().info("SSLContext initialized successfully.");
		} catch (Exception e) {
			JavaAgent.logger.log(Level.SEVERE, "Exception while initializing SSLContext. Exception: {0}", e.getMessage());
		}
	}

	public String getClientIp() {
		return this.clientIp;
	}
	public String getClientPort() {
		return this.clientPort;
	}
}
