package com.sinosoft.one.monitor.utils;
/**
 * 
 * User:Chunliang.han
 * Date:2013-3-4
 * Time:下午2:49:33
 */
public class BussinessUtil {
	/**
	 * 计算故障间隔时间，公式：正常运行时间/(停机次数+1)
	 * @param useableTime 正常运行时间
	 * @param errorCount  停机次数
	 * @return
	 */
	public static long getAvgErrorTime(long useableTime,int errorCount){
		return useableTime/(errorCount+1);
	}
}
