package com.sinosoft.one.monitor.utils;

import com.sinosoft.one.monitor.application.model.Application;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-8-30
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationUtil {
    /**
     * @param url
     * @return 返回appName,如果没有则返回空串
     */
    public static String getAppNameByUrl(String url){
        String appName = "";
        if(url!=null&&url.contains("/")){
            appName = StringUtils.substring(url,0,url.indexOf("/"));
        }
        return appName;
    }
    public static String getAppId(String ip,String port,Map<String, Application> applicationMapByName,String url){
        String appName = getAppNameByUrl(url);
        if("background".equals(appName)){
            return "background";
        }
        String key = ip+":"+port+":"+appName;
        Application application = applicationMapByName.get(key);
        if(application!=null){
            return application.getId();
        }
        return null;
    }
}
