package com.sinosoft.one.monitor.common;

import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 健康状况服务类
 * User: carvin
 * Date: 13-3-8
 * Time: 上午11:42
 */
@Service
public class HealthStaService {
	@Autowired
	private AlarmRepository alarmRepository;

	/**
	 * 根据天数得到健康度Map
	 * @param monitorId 监视器ID
	 * @param days 天
	 * @return 健康度Map
	 */
	public Map<Integer, SeverityLevel> healthStaForDays(String monitorId, int days) {
		Date startDate = null;
		Date endDate = null;
		if(days == 1) {
			startDate = LocalDate.now().toDate();
			endDate = LocalDateTime.now().toDate();
		} else {
			startDate = LocalDate.now().minusDays(days).toDate();
			endDate = LocalDateTime.now().toDate();
		}
		List<HealthStaForTime> healthStaForTimeList = alarmRepository.selectHealthStaForDay(monitorId, startDate, endDate);
		return generateHealthStaMap(healthStaForTimeList);
	}

	/**
	 * 根据小时数得到健康度Map
	 * @param monitorId 监控器ID
	 * @param hours 小时数
	 * @return 健康度Map
	 */
	public Map<Integer, SeverityLevel> healthStaForHours(String monitorId, String subResourceType, String subResourceId, int hours) {
		LocalDateTime localDateTime = LocalDateTime.now().withField(DateTimeFieldType.minuteOfHour(), 0)
				.withField(DateTimeFieldType.secondOfMinute(), 0).withField(DateTimeFieldType.millisOfSecond(), 0);
		Date startDate =  localDateTime.minusHours(hours).toDate();
		Date endDate = localDateTime.toDate();
		List<HealthStaForTime> healthStaForTimeList = alarmRepository.selectHealthStaForHour(monitorId, subResourceType, subResourceId, startDate, endDate);
		return generateHealthStaMap(healthStaForTimeList);
	}

	/**
	 * 根据小时数得到健康度Map
	 * @param monitorId 监控器ID
	 * @param hours 小时数
	 * @return 健康度Map
	 */
	public Map<Integer, SeverityLevel> healthStaForHours(String monitorId, int hours) {
		LocalDateTime localDateTime = LocalDateTime.now();
		Date startDate =  localDateTime.minusHours(hours).toDate();
		Date endDate = localDateTime.toDate();
		List<HealthStaForTime> healthStaForTimeList = alarmRepository.selectHealthStaForHour(monitorId, startDate, endDate);
		return generateHealthStaMap(healthStaForTimeList);
	}

	/**
	 * 获取最新的健康状况
	 * @param moinitorId 监视器ID
	 * @param minutes 分钟数
	 * @return 健康状况
	 */
	public SeverityLevel healthStaForCurrent(String moinitorId, int minutes) {
		LocalDateTime localDateTime = LocalDateTime.now();
		Date endDate = localDateTime.toDate();
		Date startDate = localDateTime.minusMinutes(minutes).toDate();
		List<Alarm> alarms = alarmRepository.findAlarmByMonitorId(moinitorId, startDate, endDate);
		if(alarms == null || alarms.size() == 0) {
			return SeverityLevel.INFO;
		}

		int warningCount = 0;
		for(Alarm alarm : alarms) {
			if(alarm.getSeverity() == SeverityLevel.CRITICAL) {
				return SeverityLevel.CRITICAL;
			} else if(alarm.getSeverity() == SeverityLevel.WARNING) {
				warningCount++;
			}
		}
		if(warningCount > 0) {
			return SeverityLevel.WARNING;
		} else {
			return SeverityLevel.INFO;
		}
	}

	/**
	 * 获取最新的健康状况
	 * @param moinitorId 监视器ID
	 * @param minutes 分钟数
	 * @return 健康状况
	 */
	public SeverityLevel healthStaForCurrent(String moinitorId, ResourceType subResourceType, String subResourceId, int minutes) {
		LocalDateTime localDateTime = LocalDateTime.now();
		Date endDate = localDateTime.toDate();
		Date startDate = localDateTime.minusMinutes(minutes).toDate();
		List<Alarm> alarms = alarmRepository.findAlarmByMonitorId(moinitorId, subResourceType.name(), subResourceId, startDate, endDate);
		if(alarms == null || alarms.size() == 0) {
			return SeverityLevel.INFO;
		}

		int warningCount = 0;
		for(Alarm alarm : alarms) {
			if(alarm.getSeverity() == SeverityLevel.CRITICAL) {
				return SeverityLevel.CRITICAL;
			} else if(alarm.getSeverity() == SeverityLevel.WARNING) {
				warningCount++;
			}
		}
		if(warningCount > 0) {
			return SeverityLevel.WARNING;
		} else {
			return SeverityLevel.INFO;
		}
	}

	private Map<Integer, SeverityLevel> generateHealthStaMap(List<HealthStaForTime> healthStaForTimes) {
		Map<Integer, HealthStaForTime> healthStaForTimeMap = new HashMap<Integer, HealthStaForTime>();

		for(HealthStaForTime healthStaForTime : healthStaForTimes) {
			int timeIndex = healthStaForTime.getTimeIndex();
			if(healthStaForTimeMap.containsKey(timeIndex)) {
				HealthStaForTime targetHealthStaForTime = healthStaForTimeMap.get(timeIndex);
				if(healthStaForTime.getSeverity() == SeverityLevel.CRITICAL) {
					targetHealthStaForTime.setCriticalCount(healthStaForTime.getCount());
				} else if(healthStaForTime.getSeverity() == SeverityLevel.WARNING) {
					targetHealthStaForTime.setWarningCount(healthStaForTime.getCount());
				} else if(healthStaForTime.getSeverity() == SeverityLevel.INFO) {
					targetHealthStaForTime.setNormalCount(healthStaForTime.getCount());
				}
			} else {
				healthStaForTimeMap.put(timeIndex, healthStaForTime);
			}
		}

		Map<Integer, SeverityLevel> result = new HashMap<Integer, SeverityLevel>();
		for(Integer key : healthStaForTimeMap.keySet()) {
			result.put(key, healthStaForTimeMap.get(key).getSeverityLevel());
		}
		return result;
	}
}
