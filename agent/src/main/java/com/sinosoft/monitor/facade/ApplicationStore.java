package com.sinosoft.monitor.facade;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.facade.model.Application;
import com.sinosoft.monitor.facade.model.Method;
import com.sinosoft.monitor.facade.model.Url;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-29
 * Time: 上午11:46
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationStore {
    private static List<Application> applications;
    private static Map<String,List<String>> classMethodStore;
    public static void refresh(List<Application> applicationList){
        applications = applicationList;
        refreshClassMethodStore();
        JavaAgent.logger.log(Level.INFO,"refresh ApplicationStore for agent success,curr data is : \n{0}",toMethodStoreString());
    }

    private static void refreshClassMethodStore() {
        Map<String,List<String>> tempStore = new HashMap<String,List<String>>();
        for (Application application:applications){
            for (Url url : application.getSubUrls()){
                for (Method method:url.getSubMethods()){
                    String methodClass = method.getMethodClass();
                    if (tempStore.containsKey(methodClass)){
                        List<String> methods = tempStore.get(methodClass);
                        methods.add(method.getMethod());
                    } else {
                        List<String> methods = new ArrayList<String>();
                        methods.add(method.getMethod());
                        tempStore.put(methodClass,methods);
                    }
                }
            }
        }
        classMethodStore = tempStore;
    }
    public static List<String> getMethods(String methodClass){
        return classMethodStore==null?null:classMethodStore.get(methodClass);
    }

    public static int getSize(){
        return classMethodStore==null?null: classMethodStore.size();
    }

    public static String toMethodStoreString(){
        return classMethodStore==null?null: classMethodStore.toString();
    }

}
