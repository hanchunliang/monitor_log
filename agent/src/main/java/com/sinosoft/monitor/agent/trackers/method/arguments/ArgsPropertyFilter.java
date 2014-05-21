package com.sinosoft.monitor.agent.trackers.method.arguments;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.trackers.store.InvalidTypeStore;
import com.sinosoft.monitor.com.alibaba.fastjson.serializer.JSONSerializer;
import com.sinosoft.monitor.com.alibaba.fastjson.serializer.PropertyPreFilter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * User: morgan
 * Date: 13-9-4
 * Time: 上午10:37
 */
public class ArgsPropertyFilter implements PropertyPreFilter {

	private static final ArgsPropertyFilter instence = new ArgsPropertyFilter();
	private static String[] includePackages ;
	private static  Pattern[] pkgParrerns;

	//@Override
//	public boolean apply(Object object, String name, Object value) {
//		if(name == null || value == null)
//			return false;
//        System.out.print("2====================>value is:"+value+",name is"+name+"object is"+object);
//        System.out.println("====================>type is:" + value.getClass().getName());
//		boolean issim = isSimplePropertyType(value);
//        boolean isNotIo = isIoPropertyType(value);
//
//		return issim && isNotIo;
//	}
    @Override
    public boolean apply(JSONSerializer serializer, Object object, String name) {
        if(name == null || object == null)
            return false;
        String className = object.getClass().getName();
        if(isSimplePropertyType(object)){
            return true;
        }
        //IO,黑名单中的类型的返回false
        else if(isIncludePackage(className)){
            if(!InvalidTypeStore.hasType(object.getClass())){
                return true;
            }
        }
        return false;
        //如果时间基本类型返回true
//        if(isSimplePropertyType(object)){
//            return true;
//        }
        //如果是配置的包中的，并且非IO,非黑名单中的类型的返回true
//        if(isIncludePackage(className)){
//            return true;
//        }
//        return true;
    }

	public static ArgsPropertyFilter getInstence() {
		return instence;
	}

	private boolean isSimplePropertyType(Object value ) {
		if( value instanceof String || value instanceof String[] )
			return true;
		if( value.getClass().isPrimitive() )
			return true;
		if( value instanceof Number || value instanceof Number[])
			return true;
		if( value instanceof Collection )
			return true;
		if( value instanceof Map )
			return true;
		if( value instanceof Date )
			return true;

		return false;
	}

    private boolean isIoPropertyType(Object value){

        if(value instanceof InputStream||value instanceof InputStream[]){
            return true;
        }
        else if(value instanceof OutputStream ||value instanceof OutputStream[]){
            return true;
        }
        else if(value instanceof Reader ||value instanceof Reader[]){
            return true;
        }
        else if(value instanceof Writer ||value instanceof Writer[]){
            return true;
        }else{
            return false;
        }
    }

	private boolean isIncludePackage(String className) {
		if(includePackages == null) {
			includePackages = JavaAgent.getInstance().getAgentConfig().getTracingIncludedPackages();
		}
		return searchPkgPatterns(className.replaceAll("\\.","/"));
	}

	private boolean searchPkgPatterns ( String className ) {
		if(pkgParrerns == null) {
			pkgParrerns = new Pattern[includePackages.length];
			for( int i = 0 ; i < includePackages.length; i++ ) {
				pkgParrerns[i] = Pattern.compile(includePackages[i]);
			}
		}
		for(Pattern pattern : pkgParrerns) {
			if(pattern.matcher(className).matches()){
                //System.out.println("匹配的类与模板，模板："+pattern+",类名："+className);
                return true;
            }
		}
		return false;
	}
}
