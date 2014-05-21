package com.sinosoft.one.monitor.os.linux.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsDisk;
import com.sinosoft.one.monitor.os.linux.model.OsRam;

/**
 * 拆分采样信息字符生成对象
 * @author Administrator
 *
 */
public class OsTransUtil {

	public static OsCpu getCpuInfo(String cpuInfo ) {
		// 运行队列，阻塞进程，用户时间%，系统时间%，i/o等待%，空闲时间%，中断/秒
		String[] cpuInfos = cpuInfo.split(" ");
		OsCpu osCpu = new OsCpu();
		osCpu.setRunQueue(cpuInfos[0].trim());
		osCpu.setBlockProcess(cpuInfos[1].trim());
		osCpu.setUserTime(cpuInfos[2].trim());
		osCpu.setSysTime(cpuInfos[3].trim());
		osCpu.setIoWait(cpuInfos[4].trim());
		osCpu.setCpuIdle(cpuInfos[5].trim());
		osCpu.setInterRupt(cpuInfos[6].trim());
		Integer cpuUtilZation = 100 - new Integer(osCpu.getCpuIdle());
		osCpu.setUtiliZation(cpuUtilZation+"");
		return osCpu;
	}

	public static OsRam getRamInfo(String ramInfo) {
		String MemInfo = ramInfo.substring(ramInfo.indexOf("Mem"),
				ramInfo.indexOf("Swap")).split(":")[1];
		String SwapInfo = ramInfo.substring(ramInfo.indexOf("Swap")).split(":")[1];
		OsRam osRam = new OsRam();
		for (String mems : MemInfo.split(",")) {
			String[] mem = mems.trim().split("k");
			if (mem[1].trim().equals("total")) {
				osRam.setMemTotal(mem[0]);
			} else if (mem[1].trim().equals("used")) {
				osRam.setMemUsed(mem[0]);
			}
		}
		System.out.println(countUtilZation("3", "2"));
		osRam.setMemUtiliZation(countUtilZation(osRam.getMemTotal(),
				osRam.getMemUsed()));
		for (String swaps : SwapInfo.split(",")) {
			String[] swap = swaps.trim().split("k");
			if (swap[1].trim().equals("total")) {
				osRam.setSwapTotal(swap[0]);
			} else if (swap[1].trim().equals("used")) {
				osRam.setSwapUsed(swap[0]);
			}
		}
		osRam.setSwapUtiliZation(countUtilZation(osRam.getSwapTotal(),
				osRam.getSwapUsed()));
		return osRam;
	}

	public static List<OsDisk> getDiskInfo(String diskInfo) {
		String[] diskInfos = diskInfo.split(",");
		String[] elements = null;
		List<OsDisk> osDisks = new ArrayList<OsDisk>();
		long totalCount = 0;
		long UsedCount = 0;
		String totalUtiliZation;
		String[] lastelements=diskInfos[0].split(":");
		for (int i = 0; i < diskInfos.length; i++) {
			String line = diskInfos[i];
			elements = line.split(":");
			
			if (i == 0||elements[1].trim().equals("")) {
				lastelements=elements;
				continue;
			}
			if(elements[5].trim().equals("")){
				String[] newelements={lastelements[0],elements[0],elements[1],elements[2],elements[3],elements[4]};
				totalCount += Long.parseLong(newelements[1].trim());
				UsedCount += Long.parseLong(newelements[2].trim());
			}else{
				totalCount += Long.parseLong(elements[1].trim());
				UsedCount += Long.parseLong(elements[2].trim());
			}
			lastelements=elements;
		}
		totalUtiliZation = countUtilZation(totalCount + "", UsedCount + "");
		for (int i = 0; i < diskInfos.length; i++) {
			String line = diskInfos[i];
			elements = line.split(":");
			if (i == 0||elements[1].trim().equals("")) {
				lastelements=elements;
				continue;
			}
			
			if(elements[5].trim().equals("")){
				OsDisk osDisk = new OsDisk();
				osDisk.setDiskPath(lastelements[0].trim());
				osDisk.setTotal(elements[0].trim());
				osDisk.setUsed(elements[1].trim());
				osDisk.setFree(elements[2].trim());
				osDisk.setUsedUtiliZation(elements[3].substring(0,
						elements[3].indexOf("%")).trim());
				Integer freeUtilZation = 100 - new Integer(
						osDisk.getUsedUtiliZation());
				osDisk.setFreeUtiliZation(freeUtilZation.toString());
				osDisk.setMountPoint(elements[4].trim());
				osDisk.setTotalUtiliZation(totalUtiliZation);
				osDisks.add(osDisk);
			}else{
				OsDisk osDisk = new OsDisk();
				osDisk.setDiskPath(elements[0].trim());
				osDisk.setTotal(elements[1].trim());
				osDisk.setUsed(elements[2].trim());
				osDisk.setFree(elements[3].trim());
				osDisk.setUsedUtiliZation(elements[4].substring(0,
						elements[4].indexOf("%")).trim());
				Integer freeUtilZation = 100 - new Integer(
						osDisk.getUsedUtiliZation());
				osDisk.setFreeUtiliZation(freeUtilZation.toString());
				osDisk.setMountPoint(elements[5].trim());
				osDisk.setTotalUtiliZation(totalUtiliZation);
				osDisks.add(osDisk);
			}
			lastelements=elements;
		}
		return osDisks;
	}
	
	/**
	 * 计算利用率 保留两位小数入库
	 * @param total
	 * @param used
	 * @return
	 */
	public static String countUtilZation(String total, String used) {
		// double tota = new Double(total);
		// double use = new Double(used);
		Long totalNumber = Long.valueOf(total);
		if (totalNumber == 0) {
			totalNumber = Long.valueOf("1");
		}
		BigDecimal bigDecimal = BigDecimal.valueOf(Long.valueOf(used)).divide(
				BigDecimal.valueOf(Long.valueOf(totalNumber)), 4,
				BigDecimal.ROUND_HALF_UP);
		DecimalFormat df = new DecimalFormat("##.00%");
		// String utilZation = df.format(use / tota);
		String utilZation = df.format(bigDecimal.doubleValue());
		utilZation = utilZation.substring(0, utilZation.indexOf("%"));
		if (utilZation.startsWith(".")) {
			utilZation = "0" + utilZation;
		}
		utilZation = utilZation.substring(0, utilZation.length() - 1);
		return utilZation.toString();
	}

	/**
	 * long数字转换成日期
	 * @param lang
	 * @return
	 */

	public static String LongToHMS(long lg) {
		long hours = (lg) / (1000 * 60 * 60);
		long minutes = (lg - hours * 60 * 60 * 1000) / (1000 * 60);
		long seconds = (lg - hours * 60 * 60 * 1000 - minutes * 60 * 1000) / (1000l);
		if (seconds >= 60) {
			minutes += seconds / 60;
			seconds = seconds % 60;
		}
		if (minutes >= 60) {
			hours += minutes / 60;
			minutes = minutes % 60;
		}
		if (hours == 0) {
			return minutes + "分" + seconds + "秒";
		}
		if (minutes == 0) {
			return seconds + "秒";
		}
		return hours + "小时" + minutes + "分" + seconds + "秒";
	}

//	public static void main(String[] args) {
//		double a = 2.2;
//		int b = 2;
//		System.out.println(countUtilZation("98", "33"));
//	}
	
	public static String countAve(Object dividend, int divisor) {
		Object ave = null;
		if (dividend.getClass().equals(Double.class)) {
			Double d = (Double) dividend;
			ave = new BigDecimal(d).divide(new BigDecimal(divisor), 2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		if (dividend.getClass().equals(Long.class)) {
			Long d = (Long) dividend;
			ave = new BigDecimal(d).divide(new BigDecimal(divisor), 2,
					BigDecimal.ROUND_HALF_UP).longValue();
		}
		return ave.toString();
	}

	/*
	 * 前几天获取整点
	 */
	public static Date getBeforeDate(Date currentDate,String timespan) {
		long span;
		Date beginDate = new Date();
		Calendar c = Calendar.getInstance();
		if (Integer.valueOf(timespan) > 1) {
			span = (Integer.valueOf(timespan) - 1) * 24;
			beginDate = new Date(currentDate.getTime() - span * 60 * 60 * 1000);
			c.setTime(beginDate);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			beginDate = c.getTime();
		} else {
			span = (Integer.valueOf(timespan) * 24);
			beginDate = new Date(currentDate.getTime() - span * 60 * 60 * 1000);
			c.setTime(beginDate);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			beginDate = c.getTime();
		}
		return beginDate;
	}
	
	/*
	 * 当前时间的当天整点
	 */
	public static Date getDayPointByDate(Date currentDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/*
	 * 当前时间小时整点
	 */
	public static Date getHourPointByDate(Date currentDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
}
