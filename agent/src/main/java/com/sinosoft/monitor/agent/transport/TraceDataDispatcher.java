package com.sinosoft.monitor.agent.transport;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.ExplicitIdStrategy;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.agent.service.JavaAgentService;
import com.sinosoft.monitor.agent.store.AgentTraceStore;
import com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo;
import com.sinosoft.monitor.agent.store.model.url.MethodTraceLog;
import com.sinosoft.monitor.agent.store.model.url.TraceLog;
import com.sinosoft.monitor.agent.store.model.url.UrlTraceLog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.Deflater;

/**
 * 发送产生的追踪信息。
 *
 */
public class TraceDataDispatcher implements Runnable{

    public static long DISPATCH_WAIT_TIME = 10L;
    public static int ONCE_SEND_DATA_SIZE = 20;
    private static long REFRESH_TIME = System.currentTimeMillis();
    private long lastDispatched = System.currentTimeMillis();
    private JavaAgent agent;
    private JavaAgentService agentService;

    private Schema<TraceLog> schema;

    public TraceDataDispatcher(JavaAgent agent) {
        this.agent = agent;
        this.agentService = agent.getAgentService();
        ExplicitIdStrategy.Registry r = new ExplicitIdStrategy.Registry();
        r = r.registerPojo(TraceLog.class,1);
        r = r.registerPojo(UrlTraceLog.class,2);
        r = r.registerPojo(ExceptionInfo.class,3);
        r = r.registerPojo(MethodTraceLog.class,4);
        schema = RuntimeSchema.getSchema(TraceLog.class, r.strategy);
    }

    @Override
    public void run() {
        while (true) {
//            long startTime = System.currentTimeMillis();
//            JavaAgent.logger.log(Level.FINER, "Metric dispatching started at {0} ms", Long.valueOf(startTime));

//            UrlTraceStore urlTraceStore = UrlTraceStoreController.changeUrlTraceStore();

//			StatisticsPrinter.getGlobalPrinter().print(JavaAgent.logger, Level.INFO, this.agent.getAgentConfig().printAgentStatistics());
            try {
                dispatchTraceOnceSize();
            }catch (Throwable t){}

//            JavaAgent.logger.log(Level.FINER, "Metric dispatching completed. Time consumed: {0} ms",
//                Long.valueOf(System.currentTimeMillis() - startTime));
            agentConfigRefreshNow();

            try {
                Thread.sleep(DISPATCH_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private void agentConfigRefreshNow(){
        if(System.currentTimeMillis()-REFRESH_TIME>1000*60){
            this.agent.getAgentConfig().refreshNow();
            REFRESH_TIME = System.currentTimeMillis();
        }
    }

    /**
     * 每次发送20条数据
     */
    private void dispatchTraceOnceSize(){
        List<UrlTraceLog> urlTraceLogs = new ArrayList<UrlTraceLog>(100);
        AgentTraceStore.drainToUrlTraceLog(urlTraceLogs);
        dispatchUrlTraceOnceSize(urlTraceLogs);
        /*modify start 2013-11-01 001*/
        if (JavaAgentConfig.EXCEPTION_OPT){
            List<ExceptionInfo> exceptionInfos = new ArrayList<ExceptionInfo>(100);
            AgentTraceStore.drainToExceptionInfo(exceptionInfos);
            dispacthExceptionTraceOnceSize(exceptionInfos);
        }
        /*modify end 2013-11-01 001*/
    }

    private void dispatchUrlTraceOnceSize(List<UrlTraceLog> urlTraceLogs){
        if (urlTraceLogs.size()>0){
            dispatchUrlTrace(urlTraceLogs);
        }
    }

    private void dispacthExceptionTraceOnceSize(List<ExceptionInfo> exceptionInfos){
        if (exceptionInfos.size()>0){
            dispacthExceptionTrace(exceptionInfos);
        }
    }


    private void dispacthExceptionTrace(List<ExceptionInfo> allExciptionInfos) {
        int exceptionInfoSize =  allExciptionInfos.size();
        if(exceptionInfoSize == 0) return;
        try{
            long now = System.currentTimeMillis();
            LinkedBuffer linkedBuffer = LinkedBuffer.allocate(512);
            TraceLog traceLog = new TraceLog();
            traceLog.setIp(agentService.getClientIp());
            traceLog.setPort(agentService.getClientPort());
            traceLog.setNotificationType("EXCEPTION");
            traceLog.setAllExciptionInfos(allExciptionInfos);
            byte[] data;
            try {
                data = ProtostuffIOUtil.toByteArray(traceLog, schema, linkedBuffer);
                data = compress(data);
            } finally {
                linkedBuffer.clear();
            }
            JavaAgent.logger.info("send ExceptionInfos, length is:"+exceptionInfoSize+ ",ip:"+agentService.getClientIp()+" port:"+agentService.getClientPort());
            if (!this.agent.getAgentConfig().isDryRun()) {
                this.agentService.sendDataToServer("/agent/message",data);
            }
            this.lastDispatched = now;
        } catch (Exception e) {
            JavaAgent.logger.warning(e.getMessage());

        }
    }

    private void dispatchUrlTrace(List<UrlTraceLog> allUrlTraceLogs ) {
        int urlTraceLogSize =  allUrlTraceLogs.size();
        if(urlTraceLogSize == 0) return;
        try{
            long now = System.currentTimeMillis();
            LinkedBuffer linkedBuffer = LinkedBuffer.allocate(512);
            TraceLog traceLog = new TraceLog();
            traceLog.setIp(agentService.getClientIp());
            traceLog.setPort(agentService.getClientPort());
            traceLog.setNotificationType("LOG");
            traceLog.setUrlTraceLogs(allUrlTraceLogs);
            byte[] data;
            try {
                data = ProtostuffIOUtil.toByteArray(traceLog, schema, linkedBuffer);
                data = compress(data);
            } finally {
                linkedBuffer.clear();
            }
            JavaAgent.logger.info("send UrlTraceLogs, length is:"+urlTraceLogSize + ",ip:"+agentService.getClientIp()+" port:"+agentService.getClientPort());
            if (!this.agent.getAgentConfig().isDryRun()) {
                this.agentService.sendDataToServer("/agent/message",data);
            }
            this.lastDispatched = now;
        } catch (Exception e) {
            JavaAgent.logger.warning(e.getMessage());
        }
    }

    private byte[] compress(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }
}
