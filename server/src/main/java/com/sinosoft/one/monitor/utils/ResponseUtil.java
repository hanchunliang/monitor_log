package com.sinosoft.one.monitor.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;

public class ResponseUtil {
	private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	public static int getResponseCode(String urlString) {
		int responseCode = 404;
		HttpURLConnection httpConnection =null;
		try {
			URL url = new URL(urlString);
			httpConnection= (HttpURLConnection) url.openConnection();
//			httpConnection.getURL();
			System.out.println("urlString = "+urlString + "-------------------"+httpConnection.getResponseCode());
			responseCode = httpConnection.getResponseCode();
		} catch (Throwable e) {
			logger.error("scan url [" + urlString + "] exception.");
		}finally{
			if(null!=httpConnection){
				httpConnection.disconnect();
				}
		}
		return responseCode;
	}

}
