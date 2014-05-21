package com.sinosoft.one.monitor.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zfb
 * Date: 13-3-20
 * Time: 下午5:25
 */
public class SynAgentUtil {
    private static Logger logger = LoggerFactory.getLogger(Reflections.class);
    /**
     * URL,METHOD的更新操作時，发送http请求，同步agent.
     */
    public static void httpClientOfSynAgent(String host, int port, String applicationName, String operation,List<String> arguments){
        HttpPost httpPost=null;
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("http").setHost(host).setPort(port).setPath(applicationName + "/jolokia/");
            URI uri = builder.build();
            httpPost = new HttpPost(uri);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mbean", "log:name=LogConfigs");
            jsonObject.put("operation", operation);
            jsonObject.put("arguments", arguments);
            jsonObject.put("type", "exec");
            StringEntity stringEntity = new StringEntity(jsonObject.toJSONString(),
                    ContentType.create("text/plain", "UTF-8"));
            httpPost.setEntity(stringEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() != 200) {
                logger.error("更新客户端URL失败！");
            }
        } catch (Exception e) {
            logger.error("发送http请求失败！",e.getMessage());
        } finally {
            httpPost.releaseConnection();
        }
    }

    /**
     * URL批量删除操作時，发送http请求，同步agent.
     */
    public static void httpClientOfSynAgent(String host, int port, String applicationName, String operation,String[] arguments){
        HttpPost httpPost=null;
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(host).setPort(port).setPath(applicationName + "/jolokia/");
            try {
                for(int i=0;i<arguments.length;i++){
                    List<String> idList=new ArrayList<String>();
                    idList.add(arguments[i]);
                    URI uri = builder.build();
                    httpPost = new HttpPost(uri);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mbean", "log:name=LogConfigs");
                    jsonObject.put("operation", operation);
                    jsonObject.put("arguments", idList);
                    jsonObject.put("type", "exec");
                    StringEntity stringEntity = new StringEntity(jsonObject.toJSONString(),
                            ContentType.create("text/plain", "UTF-8"));
                    httpPost.setEntity(stringEntity);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode() != 200) {
                        logger.error("更新客户端URL失败！");
                    }
                }
            } catch (Exception e) {
                logger.error("发送http请求失败！",e.getMessage());
            } finally {
                httpPost.releaseConnection();
            }
    }
}
