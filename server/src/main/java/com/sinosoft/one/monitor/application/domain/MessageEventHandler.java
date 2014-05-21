package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.monitor.agent.store.model.url.TraceLog;
import com.sinosoft.one.util.queue.BlockingConsumer;
import com.sinosoft.one.util.queue.QueuesHolder;
import com.sinosoft.one.util.thread.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageEventHandler  {

    Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);
    public  static final String QUEUE_NAME="APP_AGENT_MSG";
    @Autowired
    private AsynAgentMessageService  asynAgentMessageService;

    protected BlockingQueue queue;

    protected ExecutorService executor;

    private int threadSize = 50;

    @PostConstruct
    public void start() throws IOException, ClassNotFoundException, InterruptedException {

        queue = QueuesHolder.getQueue(QUEUE_NAME);
        executor = Executors.newFixedThreadPool(threadSize,new ThreadUtils.CustomizableThreadFactory("Queue Consumer-" + QUEUE_NAME));
        for(int i=0;i<threadSize;i++){
            executor.execute(new Work());
        }

    }

    protected void processMessage(Object message) {
        logger.debug(Thread.currentThread().getName()+"processMessage:"+message);
        long start = System.currentTimeMillis();
        try{
            logger.debug(Thread.currentThread().getName()+"queueSize："+queue.size());
            TraceLog traceLog =(TraceLog)message;
            asynAgentMessageService.execute(traceLog);
        }catch (Throwable t){

        }
        logger.debug(Thread.currentThread().getName()+"耗时："+(System.currentTimeMillis()-start));
    }

    public int getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }

    class Work implements Runnable{
       public void run() {
           //循环阻塞获取消息直到线程被中断.
           try {
               while (!Thread.currentThread().isInterrupted()) {
                   Object message = queue.take();
                   processMessage(message);
               }
           } catch (InterruptedException e) {
               // Ignore.
           }
       }
   }
}
