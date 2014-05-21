package com.sinosoft.one.monitor.application.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.repository.MethodRepository;
import com.sinosoft.one.monitor.application.repository.UrlRepository;
import com.sinosoft.one.monitor.utils.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 过滤agent端发回的URL信息
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-8-26
 * Time: 下午2:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UrlFilter {

    private Logger logger = LoggerFactory.getLogger(UrlFilter.class);
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private MethodRepository methodRepository;

    private LoadingCache<String ,  List<Url>> applicationCache= CacheBuilder.newBuilder().build(new CacheLoader<String,List<Url>>() {

        @Override
        public List<Url> load(String key) throws Exception {
            return  urlRepository.findUrlByAppID(key);
        }
    });
    private LoadingCache<String ,  List<Method>> methodsCache= CacheBuilder.newBuilder().build(new CacheLoader<String,List<Method>>() {

        @Override
        public List<Method> load(String key) throws Exception {
            return  methodRepository.selectMethodsOfUrlById(key);
        }
    });

    public void refreshApplicationUrlCache(String applicationId){
        applicationCache.refresh(applicationId);
        logger.debug("refreshApplicationUrlCache:applicationId="+applicationId);
    }
    public void refreshUrlMethodCathe(String urlId){
        methodsCache.refresh(urlId);
        logger.debug("refreshUrlMethodCathe:urlId="+urlId);
    }
        /**
     * 第一版
     * 过滤agent端发回的URL信息
     * @param applicationId 监视器ID
     * @param urlTraceLogs agent端发回的UrlTraceLog列表
     * @return 过滤后的UrlTraceLog列表
     */
    public List<UrlTraceLog> doFilter(String applicationId, List<UrlTraceLog> urlTraceLogs) throws ExecutionException {
        List<UrlTraceLog> rsList = new ArrayList<UrlTraceLog>(urlTraceLogs.size());
        List<Url> urlList = urlRepository.findUrlByAppID(applicationId);
        Map<String,Url> urlMap = castUrlTraceLogMap(urlList);
        for(UrlTraceLog urlTraceLog:urlTraceLogs){
            Url url = urlMap.get(urlTraceLog.getUrl());
            if(url!=null){
                urlTraceLog.setUrlId(url.getId());
                List<MethodTraceLog> methodTraceLogList = urlTraceLog.getMethodTraceLogList();
                //过滤method
                methodTraceLogList = doMethodTraceLogFilter(methodTraceLogList,url.getId());
                urlTraceLog.setMethodTraceLogList(methodTraceLogList);
                rsList.add(urlTraceLog);
            }
        }
        return rsList;
    }
    /**
     * 第二版
     *
     * @param applicationMapByName
     *@param urlTraceLogList  @return  根据monitorId 绑定UrlTraceLog列表
     */
    public Map<String, List<UrlTraceLog>> doFilter(String ip,String port, Map<String, Application> applicationMapByName,
                                                   List<UrlTraceLog> urlTraceLogList) throws ExecutionException {
        //创建返回结果Map<monitorId-List>
        Map<String, List<UrlTraceLog>> urlTraceLogListMap = new HashMap<String, List<UrlTraceLog>>();
        //UrlMap缓存：[monitorId-[url-Url]]
        Map<String,Map<String,Url>> monitorUrlMap = new HashMap<String,Map<String,Url>>();
        for(Application application:applicationMapByName.values()){
            List<Url> urlList = applicationCache.get(application.getId());
            Map<String,Url> urlMap = castUrlTraceLogMap(urlList);
            monitorUrlMap.put(application.getId(),urlMap);
        }
        //迭代 agent端发回的log列表
        for(UrlTraceLog urlTraceLog:urlTraceLogList){
            //通过url获取监视器ID
            String appId = ApplicationUtil.getAppId(ip,port,applicationMapByName,urlTraceLog.getUrl());
            //过滤掉没有应用的url
            if("background".equals(appId)||appId==null){
                continue;
            }
            //获取当前应用对应的log列表,如果没有则创建一个
            List<UrlTraceLog> urlTraceLogs = urlTraceLogListMap.get(appId);
            if(urlTraceLogs==null){
                urlTraceLogs = new ArrayList<UrlTraceLog>();
                urlTraceLogListMap.put(appId,urlTraceLogs);
            }
            //根据monitorId在缓存中获取urlMap
            Map<String,Url> urlMap = monitorUrlMap.get(appId);
            //根据log的url在urlMap中获取Url对象
            Url url = urlMap.get(urlTraceLog.getUrl());
            //过滤掉没有对应Url对象的logTrace
            if(url!=null){
                urlTraceLog.setApplicationId(appId);
                //将logTrace与Url对象绑定
                urlTraceLog.setUrlId(url.getId());
                //获取methodTrace列表
                List<MethodTraceLog> methodTraceLogList = urlTraceLog.getMethodTraceLogList();
                //过滤method
                methodTraceLogList = doMethodTraceLogFilter(methodTraceLogList,url.getId());
                urlTraceLog.setMethodTraceLogList(methodTraceLogList);
                //向指定monitorId对应的traceLog列表中增加traceLog
                urlTraceLogs.add(urlTraceLog);
            }
            else if (urlTraceLog.getHasException()){
                urlTraceLog.setApplicationId(appId);
                urlTraceLog.setUrlId(null);
                urlTraceLogs.add(urlTraceLog);
            }
        }
        return urlTraceLogListMap;
    }
    private List<MethodTraceLog> doMethodTraceLogFilter(List<MethodTraceLog> methodTraceLogList,String urlId) throws ExecutionException {
        List<MethodTraceLog> rsList = new ArrayList<MethodTraceLog>(methodTraceLogList.size());
        long start = System.currentTimeMillis();
        long total = 0;
        List<Method> methods = methodsCache.get(urlId);
        total = System.currentTimeMillis()-start;
        System.out.println("cathe method cost："+total);
        Map<String,Method> methodMap = castMethodTraceLog(methods);
        for(MethodTraceLog methodTraceLog:methodTraceLogList){
            String key = methodTraceLog.getFullMethodName();
            Method method = methodMap.get(key);
            if (method!=null){
                methodTraceLog.setMethodId(method.getId());
                methodTraceLog.setUrlTraceLogId(urlId);
                rsList.add(methodTraceLog);
            }
        }
        return rsList;
    }
    private Map<String,Method> castMethodTraceLog(List<Method> methods){
        Map<String,Method> methodMap = new HashMap<String, Method>(methods.size());
        for(Method method:methods){
            String key = method.getFullName();
            methodMap.put(key,method);
        }
        return methodMap;
    }
    private Map<String,Url> castUrlTraceLogMap(List<Url> urls){
        Map<String,Url> urlMap = new HashMap<String, Url>(urls.size());
        for(Url url:urls){
            urlMap.put(url.getUrl(),url);
        }
        return urlMap;
    }
}
