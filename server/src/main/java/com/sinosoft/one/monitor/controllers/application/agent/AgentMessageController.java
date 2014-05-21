package com.sinosoft.one.monitor.controllers.application.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.sinosoft.monitor.agent.store.model.url.TraceLog;
import com.sinosoft.one.monitor.application.domain.MessageEvent;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.util.queue.QueuesHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;


/**
 * 代理端消息处理Controller.
 * User: carvin
 * Date: 13-3-3
 * Time: 下午10:31
 */
@Path
public class AgentMessageController {
	private Logger logger = LoggerFactory.getLogger(AgentMessageController.class);
//    private static final String path = "/home/weblogic/Oracle/Middleware/user_projects/domains/platform_domain/data.json";

    protected BlockingQueue<MessageEvent> queue;


//    private Schema<TraceLog> schema = RuntimeSchema.getSchema(TraceLog.class);


    /**
     * 第四版消息处理
     * @param invocation
     * @return
     */
    @Post("/message123")
    public Reply handleMessage4(MessageEvent event,@Param("data")byte[] data,Invocation invocation){

        System.out.println(data.length);

//        if(queue==null){
//            queue = QueuesHolder.getQueue(MessageEvent.QUEUE_NAME);
//        }
////        System.out.println(queue);
//        boolean success = queue.offer(event);
//        if(success){
//            logger.debug("event deal is success");
//        }else {
//            logger.error("event deal is false:{}",event);
//        }
//        String ip =  invocation.getParameter("ip");
//        String port = invocation.getParameter("port");
//        String notificationType = invocation.getParameter("notificationType");
//        String data = invocation.getParameter("data");
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("ip",ip);
//        jsonObject.put("port",port);
//        jsonObject.put("notificationType",notificationType);
//        jsonObject.put("data",data);
//        File file = new File(path);
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
//            writer.write(jsonObject.toJSONString());
//            writer.newLine();
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        return Replys.with("Success");
    }
}
