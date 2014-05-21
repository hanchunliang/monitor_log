package com.sinosoft.monitor.agent.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * @author Chunliang.Han
 * 2013年9月11日 上午2:38:02
 */
public class JarResourceUril {
	/**
	 *  获取jar包资源URL
	 */
	private static URL getJarResource(Class<?> clazz ) {
		return clazz.getProtectionDomain().getCodeSource().getLocation();
	}
	/**
	 *  获取jar包资源路径
	 */
	private static String getJarResourcePath(Class<?> clazz ) {
		return getJarResource(clazz).getPath();
	}
	/**
	 *  获取jar包资源路径
	 */
	private static String getJarResourceUrlPath(Class<?> clazz ) {
		return getJarResource(clazz).toString();
	}
	private static int containSum(String source, String dest) {
		int containSum = 0;
		int destLength = dest.length();
		while (source.contains(dest)) {
			containSum = containSum + 1;
			source = source.substring(destLength);
		}
		return containSum;
	}
	private static String cutLastString(String source, String dest, int num) {
		
		for (int i = 0; i < num; i++) {
			source = source.substring(0,
					source.lastIndexOf(dest, source.length() - 2) + 1);
		}
		return source;
	}
	/**
     *  必须传递资源的相对路径。是相对于该类jar包的路径。如果需要查找classPath外部的资源，需要使用../来查找
     *  返回资源的绝对路径
     */
    public static String getAbsolutePath(Class<?> clazz,String relativePath){
        if (!relativePath.contains("../")) {
        	return getJarResourcePath(clazz)+relativePath;
        }
        String absoluteClassPath = getJarResourcePath(clazz);
        if (relativePath.substring(0, 1).equals("/")) {
            relativePath = relativePath.substring(1);
        }
        String wildcardString = relativePath.substring(0,relativePath.lastIndexOf("../") + 3);
        relativePath = relativePath.substring(relativePath.lastIndexOf("../") + 3);
        int containSum = containSum(wildcardString, "../");
        absoluteClassPath = cutLastString(absoluteClassPath, "/", containSum);
        String resourceAbsolutePath = absoluteClassPath + relativePath;
        return resourceAbsolutePath;
    }
    /**
     *  必须传递资源的相对路径。是相对于该类jar包的路径。如果需要查找classPath外部的资源，需要使用../来查找
     *  返回资源的绝对路径
     */
    public static String getAbsoluteUrlPath(Class<?> clazz,String relativePath){
        if (!relativePath.contains("../")) {
        	return getJarResourceUrlPath(clazz)+relativePath;
        }
        String absoluteClassPath = getJarResourceUrlPath(clazz);
        if (relativePath.substring(0, 1).equals("/")) {
            relativePath = relativePath.substring(1);
        }
        String wildcardString = relativePath.substring(0,relativePath.lastIndexOf("../") + 3);
        relativePath = relativePath.substring(relativePath.lastIndexOf("../") + 3);
        int containSum = containSum(wildcardString, "../");
        absoluteClassPath = cutLastString(absoluteClassPath, "/", containSum);
        String resourceAbsolutePath = absoluteClassPath + relativePath;
        return resourceAbsolutePath;
    }
    /**
     *  必须传递资源的相对路径。是相对于该类jar包的路径。如果需要查找classPath外部的资源，需要使用../来查找
     *  返回资源的绝对路径
     */
    public static URL getAbsoluteUrl(Class<?> clazz,String relativePath){
    	URL url = null;
    	try {
    		String path = getAbsoluteUrlPath(clazz,relativePath);
    		if(isExistFile(getAbsolutePath(clazz, relativePath))){
    			url = new URL(path);
    		}
		} catch (MalformedURLException e) {}
    	return url;
    }
    
    public static boolean isExistFile(String relativePath){
    	File file = new File(relativePath); 
    	return file.exists();
    }
    public static boolean deleteFile(String relativePath){
        File file = new File(relativePath);
        return file.delete();
    }
    public static boolean createNewFile(String relativePath){
        File file = new File(relativePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    /**
	 * getStream(String relativePath)的拓展方法
	 * 提供相对于classPath的资源路径，返回属性对象，它是一个散列表
	 */
	public static Properties getProperties(Class<?> calzz, String relativePath) {
		Properties properties = new Properties();
		try {
			URL url = getAbsoluteUrl(calzz,relativePath);
			if(url!=null){
				properties.load(url.openStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("couldn't load properties file '"+ getAbsoluteUrl(calzz,relativePath) + "'", e);
		}
		return properties;
	}
}
