package com.sinosoft.monitor.agent.trackers.store;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.util.JarResourceUril;
import com.sinosoft.monitor.agent.util.Utils;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-9-10
 * Time: 下午6:58
 * To change this template use File | Settings | File Templates.
 */
public class InvalidTypeStore {
    private static Class<?> clazz = InvalidTypeStore.class;
    private static final Set<String> invalidTypeSet = new HashSet<String>();
    private static final Set<String> staticBlackSet = new HashSet<String>();
    private static final Set<Class<?>> staticBlackClassSet = new HashSet<Class<?>>();
//    private static final String JAR_PARENT_PATH = JarResourceUril.getAbsolutePath(clazz,"../");
    public static String JAR_PARENT_PATH ;
    private static final String STATIC_BLACK_FILE_NAME = "staticblack.properties";
    private static final String LOCAL_FILE_NAME = "invalidfieldtype.properties";
    private static final String TMP_FILE_NAME = "invalidfieldtype.properties.bak";
    private static final String KEY = "type";
    private static boolean hasChange = false;
    private static Properties invalidTypeProperties = null;
    private static Properties staticBlackProperties = null;

    public static void init(String rootPath){
        JAR_PARENT_PATH = rootPath;
//        String path = JAR_PARENT_PATH+LOCAL_FILE_NAME;
//        if(!JarResourceUril.isExistFile(path)){
//            JarResourceUril.createNewFile(path);
//        }
        //从本地文件读取
//        invalidTypeProperties = JarResourceUril.getProperties(clazz,"../"+LOCAL_FILE_NAME);
        try {
            invalidTypeProperties = Utils.getContentAsProps(new File(JAR_PARENT_PATH+LOCAL_FILE_NAME));
        } catch (IOException e) {
            String errMsg = "Exception occured(file might not exist) while loading "+LOCAL_FILE_NAME+" in " + JAR_PARENT_PATH;
            throw new RuntimeException(errMsg);
        }
        String invalidTypeStr = invalidTypeProperties.getProperty(KEY);
        JavaAgent.logger.info(JAR_PARENT_PATH+LOCAL_FILE_NAME+"加载成功！\n\rinvalidTypeStr:"+invalidTypeStr);
        if(invalidTypeStr!=null){
            String[] types = invalidTypeStr.split(",");
            for(int i=0;i<types.length;i++){
                String s = types[i].trim();
                if(!"".equals(s)){
                    invalidTypeSet.add(s);
                }
            }
        }
//        staticBlackProperties = JarResourceUril.getProperties(clazz,"../"+STATIC_BLACK_FILE_NAME);
        try {
            staticBlackProperties = Utils.getContentAsProps(new File(JAR_PARENT_PATH+STATIC_BLACK_FILE_NAME));
        } catch (IOException e) {
            String errMsg = "Exception occured(file might not exist) while loading "+STATIC_BLACK_FILE_NAME+" in " + JAR_PARENT_PATH;
            throw new RuntimeException(errMsg);
        }
        String staticBlackStr = staticBlackProperties.getProperty(KEY);
        JavaAgent.logger.info(JAR_PARENT_PATH+STATIC_BLACK_FILE_NAME+"加载成功！\n\rstaticBlackStr:"+staticBlackStr);
        if(staticBlackStr!=null){
            String[] types = staticBlackStr.split(",");
            for(int i=0;i<types.length;i++){
                String className = types[i].trim();
                if(!"".equals(className)){
                    staticBlackSet.add(types[i]);
                    try {
                        Class<?> clazz1 = Class.forName(className);
                        staticBlackClassSet.add(clazz1);
                    } catch (ClassNotFoundException e) {
                        JavaAgent.logger.warning("加载转JSON黑名单，类["+className+"]加载失败！");
                    }
                }
            }
        }
        hasChange = false;
        JavaAgent.logger.info("转JSON黑名单加载成功！");
    }

    public synchronized static void add(Class value){
    	if(!hasType(value)){
    		invalidTypeSet.add(value.getName());
    		hasChange = true;
    	}
    }
    public static boolean hasType(Class clazz){
        if(staticBlackClassSet!=null){
            for (Class t:staticBlackClassSet){
                if(t.isAssignableFrom(clazz)){
                    return true;
                }
            }
        }
        return invalidTypeSet.contains(clazz.getName());
    }
    public static synchronized void synchronizedLocalFile(){
    	if(!hasChange){
    		return ;
    	}
        BufferedWriter bw1 = null;
        BufferedWriter bw2 = null;
        try {
            String bakFilePath = JAR_PARENT_PATH+TMP_FILE_NAME;
            String filePath = JAR_PARENT_PATH+LOCAL_FILE_NAME;
            //备份文件
            bw2 = new BufferedWriter(new FileWriter(bakFilePath));
            bw2.write(KEY + ":" + invalidTypeProperties.getProperty(KEY));
            bw2.flush();
            bw2.close();

            bw1 = new BufferedWriter(new FileWriter(filePath));
            bw1.write(getTypeString());
            bw1.flush();
            bw1.close();
            //删除备份文件
            JarResourceUril.deleteFile(bakFilePath);
            hasChange = false;
        } catch (IOException e){
            JavaAgent.logger.warning("向invalidfieldtype.properties中写数据出错!");
        } finally {
            if(bw1!=null){
                try {
                    bw1.close();
                } catch (IOException e) {
                }
            }
            if(bw2!=null){
                try {
                    bw2.close();
                } catch (IOException e) {
                }
            }
        }
    }
    private synchronized static String getTypeString(){
        StringBuilder invalidTypeString = new StringBuilder(KEY).append(":");
        for (String str:invalidTypeSet){
            invalidTypeString.append(str).append(",");
        }
        int index = invalidTypeString.lastIndexOf(",");
        if (index>KEY.length()){
            invalidTypeString.setLength(invalidTypeString.length()-1);
        }
        return invalidTypeString.toString();
    }
}
