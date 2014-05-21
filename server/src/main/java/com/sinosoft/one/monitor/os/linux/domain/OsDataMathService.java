package com.sinosoft.one.monitor.os.linux.domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.OsAvailable;
import com.sinosoft.one.monitor.os.linux.model.OsAvailabletemp;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsDisk;
import com.sinosoft.one.monitor.os.linux.model.OsRam;
import com.sinosoft.one.monitor.os.linux.model.OsRespondtime;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;
import com.sinosoft.one.monitor.utils.AvailableCalculate;
import com.sinosoft.one.monitor.utils.AvailableCalculate.AvailableCountsGroupByInterval;
import com.sinosoft.one.monitor.utils.AvailableCalculate.AvailableInf;
import com.sinosoft.one.monitor.utils.AvailableCalculate.AvailableStatistics;

/**
 * 信息处理类
 * @author chenxiongxi
 * @version 1.0
 * @created 27-����-2013 14:42:30
 */
@Component
public class OsDataMathService {

	@Autowired
	private OsAvailableServcie osAvailableServcie;
	
	@Autowired
	private OsStatiService osStatiService;

	@Autowired
	private OsRamService osRamService;
	
	@Autowired
	private OsCpuService osCpuService;

	@Autowired
	private OsDiskService osDiskService;
	
	@Autowired
	private OsRespondTimeService osRespondTimeService;
	/**
	 * 可用性统计算法 进行可用性数据统计计算
	 * @param osInfoId OSID
	 * @param currentTime当前时间
	 * @param targetTime目标时间
	 * @param interCycleTime轮询时间
	 * @param timeSpan 时间段类型标记
	 */											 //当前时间           //查询时间段的目标时间	 //轮询时间 分钟
	public OsAvailabletemp statiAvailable(String osInfoId,Date currentTime,Date targetTime,int interCycleTime ,Date timeSpan, OsAvailabletemp osAvailabletemp ){
//		int stopCount=0;//停机次数
		OsAvailable osAvailable=osAvailableServcie.getAvailable(osInfoId, timeSpan);
		OsAvailabletemp lastAvailableTemp=osAvailableServcie.getLastAvailable(osInfoId, currentTime);//最后一次采样
		//可用性停机及次数LIST
		List<AvailableCountsGroupByInterval> availableCountsGroupByIntervals=osAvailableServcie.findGroupByInterCycleTime(osInfoId, targetTime);
		AvailableStatistics availableStatistics;
		if(osAvailable==null){//当天开始的统计
			DateTime zerodateTime= new DateTime(targetTime);
			DateTime currentdatetime= new DateTime(currentTime);
			int currntTimeminute=currentdatetime.getMinuteOfHour();
			int zeroTimeminute=zerodateTime.getMinuteOfHour();
			if(currntTimeminute- zeroTimeminute<interCycleTime){
				interCycleTime=currntTimeminute- zeroTimeminute;
				osAvailabletemp.setIntercycleTime(interCycleTime);
			}
			//最后一次正确循环 补上的0点的缺失时间 
			if(lastAvailableTemp!=null){
				if(new DateTime(lastAvailableTemp.getSampleDate()).plusMinutes(interCycleTime).toDate().getTime()==currentTime.getTime()){
					OsAvailable yesterdayAva= osAvailableServcie.getAvailable(osInfoId, zerodateTime.minusDays(1).toDate());
					DateTime lastrecordDatetim=new DateTime(lastAvailableTemp.getSampleDate());
					int Dvalue=zerodateTime.minusDays(1).getMinuteOfHour()-lastrecordDatetim.getMinuteOfHour();
					yesterdayAva.setNormalRun(yesterdayAva.getNormalRun()+Dvalue);
					osAvailableServcie.updateAvailable(yesterdayAva);
				}
			}
			availableStatistics=new AvailableStatistics(Long.valueOf(0),Long.valueOf(0), 0);
			//本次采样的信息
			AvailableCalculate.AvailableCalculateParam availableCalculateParam= new AvailableCalculate.AvailableCalculateParam(availableStatistics, availableCountsGroupByIntervals, null, interCycleTime, true, null);
			AvailableCalculate availableCalculate=AvailableCalculate.calculate(availableCalculateParam);
			osAvailableServcie.saveAvailable(osInfoId,availableCalculate.getAliveTime().longValue(),availableCalculate.getStopTime().longValue(), availableCalculate.getTimeToRepair().longValue(),availableCalculate.getTimeBetweenFailures().longValue(), timeSpan,availableCalculate.getFalseCount().intValue());
		}else{
			//上一次采样记录
			AvailableInf availableInf =new AvailableInf(lastAvailableTemp.getSampleDate(),true , lastAvailableTemp.getIntercycleTime());
			//原来的统计记录
			availableStatistics= new AvailableStatistics(osAvailable.getNormalRun(), osAvailable.getCrashTime(), osAvailable.getStopCount()); 
			AvailableCalculate.AvailableCalculateParam availableCalculateParam= new AvailableCalculate.AvailableCalculateParam(availableStatistics, availableCountsGroupByIntervals, null, interCycleTime, true, availableInf);
			AvailableCalculate availableCalculate=AvailableCalculate.calculate(availableCalculateParam);
			//是否是当天最后一次统计
			
			osAvailableServcie.updateAvailable(osAvailable, availableCalculate.getAliveTime().longValue() , availableCalculate.getStopTime().longValue(), availableCalculate.getTimeToRepair().longValue(),  availableCalculate.getTimeBetweenFailures().longValue(),availableCalculate.getFalseCount().intValue());
		}
		return osAvailabletemp;
	}
	public static void main(String[] args) {
		Calendar c  = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		//取当天的前24小时整时点
		Date todayzeroTime= c.getTime();
		DateTime currentdatetime= new DateTime(todayzeroTime).minusDays(1);
		 
	}
	
//	/**
//	 * 可用性统计算法 进行可用性数据统计计算
//	 * @param osInfoId OSID
//	 * @param currentTime当前时间
//	 * @param targetTime目标时间
//	 * @param interCycleTime轮询时间
//	 * @param timeSpan 时间段类型标记
//	 */											//当前时间           //查询时间段的目标时间	 //轮询时间 分钟
//	public void statiAvailableMy(String osInfoId,Date currentTime,Date targetTime,int interCycleTime ,Date timeSpan, OsAvailabletemp osAvailabletemp ){
//		OsAvailable osAvailable=osAvailableServcie.getAvailable(osInfoId, timeSpan);
//		OsAvailabletemp lastAvailableTemp=osAvailableServcie.getLastAvailable(osInfoId, currentTime);//最后一次采样
//		//可用性停机及次数LIST
//		List<AvailableCountsGroupByInterval> availableCountsGroupByIntervals=osAvailableServcie.findGroupByInterCycleTime(osInfoId, targetTime);
//		AvailableStatistics availableStatistics;
//		if(osAvailable==null){//当天开始的统计
//			availableStatistics=new AvailableStatistics(Long.valueOf(0),Long.valueOf(0), 0);
//			//本次采样的信息
//			AvailableCalculate.AvailableCalculateParam availableCalculateParam= new AvailableCalculate.AvailableCalculateParam(availableStatistics, availableCountsGroupByIntervals, null, interCycleTime, true, null);
//			AvailableCalculate availableCalculate=AvailableCalculate.calculate(availableCalculateParam);
//			osAvailableServcie.saveAvailable(osInfoId,availableCalculate.getAliveTime().longValue(),availableCalculate.getStopTime().longValue(), availableCalculate.getTimeToRepair().longValue(),availableCalculate.getTimeBetweenFailures().longValue(), timeSpan,availableCalculate.getFalseCount().intValue());
//		}else{
//			//上一次采样记录
//			AvailableInf availableInf =new AvailableInf(lastAvailableTemp.getSampleDate(),true , lastAvailableTemp.getIntercycleTime());
//			//原来的统计记录
//			availableStatistics= new AvailableStatistics(osAvailable.getNormalRun(), osAvailable.getCrashTime(), osAvailable.getStopCount()); 
//			AvailableCalculate.AvailableCalculateParam availableCalculateParam= new AvailableCalculate.AvailableCalculateParam(availableStatistics, availableCountsGroupByIntervals, null, interCycleTime, true, availableInf);
//			AvailableCalculate availableCalculate=AvailableCalculate.calculate(availableCalculateParam);
//			osAvailableServcie.updateAvailable(osAvailable, availableCalculate.getAliveTime().longValue() , availableCalculate.getStopTime().longValue(), availableCalculate.getTimeToRepair().longValue(),  availableCalculate.getTimeBetweenFailures().longValue(),availableCalculate.getFalseCount().intValue());
//		}
//	}
	
	
	
	
	/**
	 * 统计内存，当前时间到当前小时整点
	 * @param osInfoId
	 */
	public void statiOneHourRam(String osInfoId,Date currentTime,Date hourPoint){
		List<OsRam> osRams = osRamService.getRamByDate(osInfoId, hourPoint, currentTime);
		double memUtilZationCount=0;//物理内存利用率总数
		double swapUtilZationCount = 0;//交换内存利用率总数
		for (OsRam osRam : osRams) {
			memUtilZationCount += Double.parseDouble( osRam.getMemUtiliZation() );//物理内存利用率总数
			swapUtilZationCount += Double.parseDouble(osRam.getSwapUtiliZation());//交换内存利用率总数
		}
		String remUitliZatiionAverage = OsTransUtil.countAve(memUtilZationCount, osRams.size());//物理内存利用率平均值
		String swapUitliZatiionAverage = OsTransUtil.countAve(swapUtilZationCount, osRams.size());//交换内存利用率平均值
		String ramUitliZatiionMax=osRamService.getMaxMemUtilZation(osInfoId, hourPoint, currentTime);
		String ramUitliZatiionMin=osRamService.getMinMemUtilZation(osInfoId, hourPoint, currentTime);
		String swapUitliZatiionMax=osRamService.getMaxSwapUtilZation(osInfoId, hourPoint, currentTime);
		String swapUitliZatiionMin=osRamService.getMinSwapUtilZation(osInfoId, hourPoint, currentTime);
		osStatiService.creatStatiOneHour(osInfoId, OsUtil.RAM_STATIF_FLAG, hourPoint, ramUitliZatiionMax, ramUitliZatiionMin, remUitliZatiionAverage);
		osStatiService.creatStatiOneHour(osInfoId, OsUtil.SWAP_STATIF_FLAG, hourPoint, swapUitliZatiionMax, swapUitliZatiionMin, swapUitliZatiionAverage);
	}
	
	/**
	 * 统计CPU，当前时间到当前小时整点
	 * @param osInfoId
	 */
	public void statiOneHourCpu(String osInfoId,Date currentTime,Date hourPoint){
		List<OsCpu>osCpus=osCpuService.getCpuByDate(osInfoId, hourPoint, currentTime);
		double cpuUtilZationCount=0;//cpu利用率总数
//		for (OsCpu osCpu : osCpus) {
//			cpuUtilZationCount+= Double.parseDouble( osCpu.getUtiliZation());
//		}
		for (OsCpu osCpu : osCpus) {
			cpuUtilZationCount+= Double.parseDouble( osCpu.getUserTime());
		}
		String cpuUitliZatiionAverage=OsTransUtil.countAve(cpuUtilZationCount, osCpus.size());
//		String cpuUitliZatiionMax=osCpuService.getMaxCpuUtilZation(osInfoId, hourPoint, currentTime);
//		String cpuUitliZatiionMin=osCpuService.getMinCpuUtilZation(osInfoId, hourPoint, currentTime);
		String cpuUitliZatiionMax=osCpuService.getMaxCpuUserUtilZation(osInfoId, hourPoint, currentTime);
		String cpuUitliZatiionMin=osCpuService.getMinCpuUserUtilZation(osInfoId, hourPoint, currentTime);
		osStatiService.creatStatiOneHour(osInfoId, OsUtil.CPU_STATIF_FLAG, hourPoint, cpuUitliZatiionMax, cpuUitliZatiionMin, cpuUitliZatiionAverage);
	}
	
	/**
	 * 统计磁盘，当前时间到当前小时整点
	 * @param osInfoId
	 */
	public void statiOneHourDisk(String osInfoId,Date currentTime,Date hourPoint){
		List<OsDisk>osDisks=osDiskService.getDiskByDate(osInfoId, hourPoint, currentTime);
		double diskUtilZationCount=0;//磁盘利用率总数
		for (OsDisk osDisk : osDisks) {
			diskUtilZationCount+=Double.parseDouble(osDisk.getTotalUtiliZation());
		}
		String diskUitliZatiionAverage=OsTransUtil.countAve(diskUtilZationCount, osDisks.size());
		String diskUitliZatiionMax=osDiskService.getMaxDiskUtilZation(osInfoId, hourPoint, currentTime);
		String diskUitliZatiionMin=osDiskService.getMinDiskUtilZation(osInfoId, hourPoint, currentTime);
		osStatiService.creatStatiOneHour(osInfoId, OsUtil.DISK_STATIF_FLAG, hourPoint, diskUitliZatiionMax, diskUitliZatiionMin, diskUitliZatiionAverage);
	}
	
	/**
	 * 统计响应时间，当前时间到当前小时整点
	 * @param osInfoId
	 */
	public void statiOneHourRespond(String osInfoId,Date currentTime,Date hourPoint){
		List<OsRespondtime>osRespondtimes=osRespondTimeService.getRespondTimeByTime(osInfoId, hourPoint, currentTime);
		long respondTime=0;//响应时间总数
		for (OsRespondtime osRespondtime : osRespondtimes) {
			respondTime+=Long.parseLong(osRespondtime.getRespondTime());
		}
		String respondTimeAverage=OsTransUtil.countAve(respondTime, osRespondtimes.size());
		String respondTimeMax=osRespondTimeService.getMaxRespondTime(osInfoId, hourPoint, currentTime);
		String respondTimeMin=osRespondTimeService.getMinRespondTime(osInfoId, hourPoint, currentTime);
		osStatiService.creatStatiOneHour(osInfoId, OsUtil.RSPOND_STATIF_FLAG, hourPoint, respondTimeMax, respondTimeMin, respondTimeAverage);
	}
	 
	/**
	 * 计算Long的百分比 为2位小数
	 * @param interCycleTime
	 * @param countTime
	 * @param normrolRunTime
	 * @return
	 */
	 long  normalRun(int interCycleTime ,long countTime,long normrolRunTime){
		long interCycle =interCycleTime*60*1000+1;
		BigDecimal  normalRun;
		if(normrolRunTime+interCycle<=countTime){
			normalRun=BigDecimal.valueOf(normrolRunTime).divide(BigDecimal.valueOf(countTime),4,BigDecimal.ROUND_HALF_DOWN);
		}else{
			normalRun=BigDecimal.valueOf(normrolRunTime+interCycle).divide(BigDecimal.valueOf(countTime),4, BigDecimal.ROUND_HALF_DOWN);
		}
		return normalRun.multiply(BigDecimal.valueOf(100)).setScale(2).longValue();
	}
}