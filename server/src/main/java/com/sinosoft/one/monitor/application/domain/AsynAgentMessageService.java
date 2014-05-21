package com.sinosoft.one.monitor.application.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.sinosoft.monitor.agent.store.model.url.TraceLog;
import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.model.ExceptionInfo;
import com.sinosoft.one.monitor.application.model.MethodTraceLog;
import com.sinosoft.one.monitor.application.model.UrlTraceLog;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-9-3
 * Time: 下午10:09
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AsynAgentMessageService {
    private Logger logger = LoggerFactory.getLogger(AsynAgentMessageService.class);
    @Autowired
    private AgentMessageServiceFactory agentMessageServiceFactory;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UrlFilter urlFilter;

    public void refreshApplicationCache(Application application){
        String key = application.getApplicationIp()+":"+application.getApplicationPort();
        applicationCache.refresh(key);
        logger.debug("refreshApplicationCache:key="+key);
    }
   private LoadingCache<String ,  Map<String , Application>> applicationCache= CacheBuilder.newBuilder().build(new CacheLoader<String,  Map<String , Application>>() {



       private Map<String, Map<String , Application>> cacheMap = Maps.newConcurrentMap();

       private void init(){
            List<Application> applicationList = applicationService.findAllApplication();
           for(Application application:applicationList){
               String key = application.getApplicationIp()+":"+application.getApplicationPort();
               // ip+port :
               Map<String , Application> temp = cacheMap.get(key);
               if(temp == null){
                   String key1 = application.getApplicationIp()+":"+application.getApplicationPort()+":"+application.getApplicationName();
                   temp =  new HashMap<String, Application>();
                   temp.put(key1,application);
                   cacheMap.put(key,temp);
               }else{
                   String key1 = application.getApplicationIp()+":"+application.getApplicationPort()+":"+application.getApplicationName();
                   temp.put(key1,application);
               }
           }
       }

       @Override
       public  Map<String , Application> load(String key) throws Exception {
           if(cacheMap.isEmpty()){
               init();
           }

           Map<String , Application> applicationMapByName = cacheMap.get(key);
           if(applicationMapByName!=null)
               return applicationMapByName;

           String[] query = StringUtils.split(key,':');
           List<Application> applicationList = applicationService.findAllApplicationBiIpAndPort(query[0],query[1]);
           if(applicationList.size()==0){
               System.out.println("agent end : NotExist"+System.currentTimeMillis());
               return Maps.newHashMap();
           }
           applicationMapByName = new HashMap<String, Application>(applicationList.size());
           for(Application application:applicationList){
               String key1 = application.getApplicationIp()+":"+application.getApplicationPort()+":"+application.getApplicationName();
               applicationMapByName.put(key1,application) ;
           }
           cacheMap.put(key,applicationMapByName);
           return applicationMapByName;
       }
   });


    public String execute(TraceLog traceLog) throws ExecutionException {
        //如果服务端没有配置的监视器，则返回NotExist。
//        logger.info("客户端地址："+ip+":"+port);
        long start = System.currentTimeMillis();
        long total;

        Map<String , Application> applicationMapByName = Maps.newHashMap();

        applicationMapByName = applicationCache.get(traceLog.getIp()+":"+traceLog.getPort());

        total = System.currentTimeMillis()-start;
        logger.debug("Simple data process："+total+"ms");
        try {
            //根据监控信息类型获取信息处理类
            AgentMessageService agentMessageService = agentMessageServiceFactory.buildAgentMessageService(traceLog.getNotificationType());
            //如果是异常信息处理类，则强转为ExceptionAgentMessageService然后处理信息。
            if(agentMessageService instanceof ExceptionAgentMessageService){
                List<com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo> orignExceptionInfos = traceLog.getAllExciptionInfos();
                List<ExceptionInfo> exceptionInfo = copyExceptioninfo(orignExceptionInfos);
                logger.debug("exceptionInfo:"+exceptionInfo.size()+"count");
                if(exceptionInfo==null||exceptionInfo.size()==0){
                    return "Success";
                }
                ExceptionAgentMessageService eams = (ExceptionAgentMessageService) agentMessageService;
                eams.handleMessage(traceLog.getIp(),traceLog.getPort(),applicationMapByName,exceptionInfo);
            }
            //如果是日志信息处理类，则强转为LogAgentMessageService处理日志信息。
            //同时创建UrlResponseTimeAgentMessageService来处理响应时间信息
            else if(agentMessageService instanceof LogAgentMessageService){
                //将agent端数据转换为服务端数据
                List<UrlTraceLog> urlTraceLogList = copyUrlTraceLog(traceLog.getUrlTraceLogs());
                //强转为LogAgentMessageService
                LogAgentMessageService lams = (LogAgentMessageService) agentMessageService;
                start = System.currentTimeMillis();
                //过滤URL
                Map<String,List<UrlTraceLog>> newUrlTraceLogTable = urlFilter.doFilter(traceLog.getIp(),traceLog.getPort(),applicationMapByName,urlTraceLogList);
                //处理过滤后的UrlTraceLog列表
                total = System.currentTimeMillis()-start;
                logger.debug("filter URL cost："+total+"ms");
                start = System.currentTimeMillis();
                lams.handleMessage(newUrlTraceLogTable);
                total = System.currentTimeMillis()-start;
                logger.debug("process URLTraceLog cost："+total+"ms");
            }
        }catch (Throwable throwable){
            throwable.printStackTrace();
            logger.error("throwable",throwable);
            return "Exception";
        }
        return "Success";
    }

    private List<ExceptionInfo> copyExceptioninfo(List<com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo> orignExceptionInfos)  {
        List<ExceptionInfo> res = new ArrayList<ExceptionInfo>();
        for(com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo exceptionInfo:orignExceptionInfos){
            ExceptionInfo e = new ExceptionInfo();
            e.setAlarmId(exceptionInfo.getAlarmId());
            e.setApplicationId(exceptionInfo.getApplicationId());
            e.setExceptionDescription(exceptionInfo.getExceptionDescription());
            e.setExceptionStackTrace(exceptionInfo.getExceptionStackTrace());
            e.setId(exceptionInfo.getId());
            e.setRecordTime(exceptionInfo.getRecordTime());
            e.setRequestParams(exceptionInfo.getRequestParams());
            e.setUrl(exceptionInfo.getUrl());
            e.setUrlId(exceptionInfo.getUrlId());
            e.setUrlTraceId(exceptionInfo.getUrlTraceId());
            res.add(e);
        }
        return res;
    }

    //目前认为monitorserver扫描的URL为“//”开头，该方法对加“//”的URL进行了过滤
    private List<UrlTraceLog> copyUrlTraceLog(List<com.sinosoft.monitor.agent.store.model.url.UrlTraceLog> agentUrlTraceLogList) {

        List<UrlTraceLog> res = new ArrayList<UrlTraceLog>();
        for(com.sinosoft.monitor.agent.store.model.url.UrlTraceLog agentLog:agentUrlTraceLogList){
            UrlTraceLog urlTraceLog = new UrlTraceLog();
            urlTraceLog.setApplicationId(agentLog.getApplicationId());
            urlTraceLog.setAlarmId(agentLog.getAlarmId());
            urlTraceLog.setBizScenarioId(agentLog.getBizScenarioId());
            urlTraceLog.setBeginTime(new Timestamp(agentLog.getBeginTime().getTime()));
            urlTraceLog.setConsumeTime(agentLog.getConsumeTime());
            urlTraceLog.setEndTime(new Timestamp(agentLog.getEndTime().getTime()));
            urlTraceLog.setHasException(agentLog.getHasException());
            urlTraceLog.setId(agentLog.getId());
            List<MethodTraceLog> methodTraceLogList = copyMehtodTraceLog(agentLog.getMethodTraceLogList());
            urlTraceLog.setMethodTraceLogList(methodTraceLogList);
            urlTraceLog.setRecordTime(agentLog.getRecordTime());
            urlTraceLog.setRequestParams(agentLog.getRequestParams());
            urlTraceLog.setSessionId(agentLog.getSessionId());
            urlTraceLog.setTraceId(agentLog.getTraceId());
            String url = agentLog.getUrl();
            if(url.startsWith("//")){
                continue;
            }else if(url.startsWith("/")){
                url = url.substring(1);
            }
            urlTraceLog.setUrl(url);
            urlTraceLog.setUrlId(agentLog.getUrlId());
            urlTraceLog.setUserId(agentLog.getUserId());
            urlTraceLog.setUserIp(agentLog.getUserIp());
            String userName = agentLog.getUsername();
            if (userName==null||"".equals(userName)){
                userName = "unknown";
            }
            urlTraceLog.setUsername(userName);
            res.add(urlTraceLog);
        }
        return res;
    }

    private List<MethodTraceLog> copyMehtodTraceLog(List<com.sinosoft.monitor.agent.store.model.url.MethodTraceLog> methodTraceLogList) {
        List<MethodTraceLog> res = new ArrayList<MethodTraceLog>();
        for(com.sinosoft.monitor.agent.store.model.url.MethodTraceLog methodTraceLog:methodTraceLogList){
            MethodTraceLog m = new MethodTraceLog();
            m.setBeginTime(new Timestamp(methodTraceLog.getBeginTime().getTime()));
            m.setClassName(methodTraceLog.getClassName());
            m.setConsumeTime(methodTraceLog.getConsumeTime());
            m.setEndTime(new Timestamp(methodTraceLog.getEndTime().getTime()));
            m.setId(methodTraceLog.getId());
            m.setInParam(methodTraceLog.getInParam());
            m.setMethodId(methodTraceLog.getMethodId());
            m.setMethodName(methodTraceLog.getMethodName());
            m.setOutParam(methodTraceLog.getOutParam());
            m.setRecordTime(new Timestamp(methodTraceLog.getRecordTime().getTime()));
            m.setUrlTraceLogId(methodTraceLog.getUrlTraceLogId());
            res.add(m);
        }
        return res;
    }

    private  List<UrlResponseTime>  castUrlResponseTimeList(List<UrlTraceLog> urlTraceLogList){
        List<UrlResponseTime> urlResponseTimeList = new ArrayList<UrlResponseTime>();
        for(UrlTraceLog urlTraceLog:urlTraceLogList){
            if (urlTraceLog.getUrlId()==null){
                continue;
            }
            UrlResponseTime urlResponseTime = new UrlResponseTime();
            urlResponseTime.setApplicationId(urlTraceLog.getApplicationId());
            urlResponseTime.setUrlId(urlTraceLog.getUrlId());
            urlResponseTime.setUrl(urlTraceLog.getUrl());
            urlResponseTime.setId(urlTraceLog.getId());
            urlResponseTime.setRecordTime(urlTraceLog.getRecordTime());
            urlResponseTime.setResponseTime(urlTraceLog.getConsumeTime());
            urlResponseTimeList.add(urlResponseTime);
        }
        return urlResponseTimeList;
    }
}
