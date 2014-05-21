package com.sinosoft.one.monitor.db.oracle.domain;

import com.google.common.collect.MapMaker;
import com.sinosoft.one.monitor.db.oracle.model.Info;
import com.sinosoft.one.monitor.db.oracle.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: Chunliang.Han
 * Date: 13-3-3
 * Time: 下午4:37
 */
@Component
@Lazy(false)
public class OracleMonitorTask {
    @Autowired
    private  InfoRepository infoRepository;
    @Autowired
    private  RecordService recordService;
    Logger logger = LoggerFactory.getLogger(OracleMonitorTask.class);
    /**
     * ScheduledFuture<?>缓存
     */
    private static ConcurrentMap<String, ScheduledFuture<?>> beeperHandleMap = new MapMaker().concurrencyLevel(32).makeMap();//监控站点线程

    /**
     * 定义任务处理器，线程池的长度设定为100
     */
    public  ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    public OracleMonitorTask(){}

    /**
     * Spring注入该类后执行该方法
     */
    @PostConstruct
    public  void execute(){
        logger.warn("启动Oracle监视器...");
        List<Info> infoList = (List<Info>) infoRepository.findAll();
        for(Info info:infoList){
        	execute(scheduledExecutorService,info);
        }
    }

    /**
     * 增加oracle监听时调用
     * @param info
     */
    public  void addTask(Info info){
        logger.warn("添加Oracle监视器...");
    	execute(scheduledExecutorService,info);
    }

    /**
     * 修改oracle监听时调用
     * @param info
     */
    public  void updateTask(Info info){
        logger.warn("修改Oracle监视器...");
    	deleteTask(info);
    	execute(scheduledExecutorService,info);
    }

    /**
     * 删除oracle监听时调用
     * @param info
     */
    public  void deleteTask(Info info){
        logger.warn("删除Oracle监视器...");
        ScheduledFuture<?> beeperHandle = beeperHandleMap.get(info.getId());
        if(beeperHandle!=null&&
                (!beeperHandle.isCancelled()||!beeperHandle.isDone())){
            beeperHandle.cancel(true);
        }
        beeperHandleMap.remove(info.getId());
    }

    /**
     * 开启一个新的定时任务
     * @param scheduledExecutorService
     * @param info
     */
    private  void execute(ScheduledExecutorService scheduledExecutorService,Info info){
        int timeDuring = info.getPullInterval();
        Runnable monitorRunnable = new MonitorRunnable(info);
        //开启定时任务延时一个轮询时间数
        ScheduledFuture<?>  beeperHandle = scheduledExecutorService.scheduleAtFixedRate(monitorRunnable,timeDuring,timeDuring, TimeUnit.MINUTES);
        beeperHandleMap.put(info.getId(),beeperHandle);
    }
    
    private  class MonitorRunnable  implements Runnable{
        Info info ;
        public MonitorRunnable(Info info){
             this.info = info;
        }
        /**
         * 定时任务的执行方法
         */
        @Override
        public void run() {
            Date date = new Date();
            //向AVA表中插入数据 ，同时更新AVA_STA表中的数据
            recordService.insertAva(info,date);
            //向LASTEVENT表中插入数据，同时更新EVENT_STA表中的数据
            recordService.insertLastEvent(info,date);
        }
    }
}
