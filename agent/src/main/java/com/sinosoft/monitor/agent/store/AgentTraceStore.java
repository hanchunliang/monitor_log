package com.sinosoft.monitor.agent.store;

import com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo;
import com.sinosoft.monitor.agent.store.model.url.UrlTraceLog;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-20
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class AgentTraceStore {
    public static final int QUEUE_SIZE = 200;
    private static BlockingQueue<UrlTraceLog> urlTraceLogQueue = new LinkedBlockingQueue<UrlTraceLog>(QUEUE_SIZE);
    private static BlockingQueue<ExceptionInfo> exceptionInfoQueue = new LinkedBlockingQueue<ExceptionInfo>(QUEUE_SIZE);

    public static int getCurrSize(){
        return urlTraceLogQueue.size()>exceptionInfoQueue.size()?
                urlTraceLogQueue.size():exceptionInfoQueue.size();
    }

    public static int getUrlTraceLogQueueSize(){
        return urlTraceLogQueue.size();
    }

    public static int getExceptionInfoQueueSize(){
        return exceptionInfoQueue.size();
    }

    public static void offerUrlTraceLog(UrlTraceLog urlTraceLog){
        urlTraceLogQueue.offer(urlTraceLog);
    }
    public static void offerExceptionInfo(ExceptionInfo exceptionInfo){
        exceptionInfoQueue.offer(exceptionInfo);
    }
    public synchronized static void drainToUrlTraceLog(List<UrlTraceLog> urlTraceLogs){
        urlTraceLogQueue.drainTo(urlTraceLogs);
    }
    public synchronized static void drainToExceptionInfo(List<ExceptionInfo> exceptionInfos){
        exceptionInfoQueue.drainTo(exceptionInfos);
    }
}
