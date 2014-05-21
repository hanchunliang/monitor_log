package com.sinosoft.monitor.agent.service;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.com.alibaba.fastjson.JSON;
import com.sinosoft.monitor.facade.ApplicationStore;
import com.sinosoft.monitor.facade.model.Application;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-29
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationService {

    private static boolean isStart = false;
    private static final long INTERVAL = 3;
    /**
     * 初始化ApplicationStore
     * 启动定时任务定时更新ApplicationStore
     * @param javaAgentService
     */
    public static void init(JavaAgentService javaAgentService){
        if (isStart == false){
            //List<Application> applicationList = getApplicationList(javaAgentService);
            //ApplicationStore.refresh(applicationList);
            run(javaAgentService);
        }
        isStart = true;
    }

    /**
     * 获取monitor端数据
     */
    private static List<Application> getApplicationList(JavaAgentService javaAgentService){

        String applicationListString = javaAgentService.getApplicationStringFromServer();
        JavaAgent.logger.log(Level.INFO,
                "get methods from monitor,the methods string is :\n {0}",applicationListString);
        if (applicationListString == null  || "".equals(applicationListString)){
            return Collections.EMPTY_LIST;
        }
        List<Application> applicationList = JSON.parseArray(applicationListString,Application.class);
        return applicationList;
    }

    /**
     * 启动定时任务更新ApplicationStore
     */
    private static void run(final JavaAgentService javaAgentConfig){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Application> applicationList = getApplicationList(javaAgentConfig);
                    ApplicationStore.refresh(applicationList);
                }catch (Throwable t){}
            }
        },0,INTERVAL, TimeUnit.MINUTES);
    }
}
