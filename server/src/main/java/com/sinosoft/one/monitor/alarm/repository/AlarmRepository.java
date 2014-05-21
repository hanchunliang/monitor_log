package com.sinosoft.one.monitor.alarm.repository;
// Generated 2013-3-1 10:29:54 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.common.HealthStaForMonitor;
import com.sinosoft.one.monitor.common.HealthStaForTime;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * 告警信息持久化接口
 */
public interface AlarmRepository extends PagingAndSortingRepository<Alarm, String> {

	@SQL("select ma.*, r.resource_name from ge_monitor_alarm ma, ge_monitor_resources r where ma.monitor_id=r.resource_id and ma.severity in (?2) order by ma.create_time desc")
	Page<Alarm> selectAlarmsBySeverity(Pageable pageable, String[] severityLevels);

    @SQL("select * from ge_monitor_alarm where monitor_id = ?1 and create_time between ?2 and ?3  order by create_time desc")
    List<Alarm> findAlarmByMonitorId(String monitorId, Date startTime, Date endTime);

	@SQL("select * from ge_monitor_alarm where monitor_id = ?1 and sub_resource_type=?2 " +
			"and sub_resource_id=?3 and create_time between ?4 and ?5 order by create_time desc")
	List<Alarm> findAlarmByMonitorId(String monitorId, String subResourceType, String subResourceId, Date startTime, Date endTime);

	/**
	 * 根据监视器ID，开始结束时间查询健康度统计数据
	 * @param monitorId 监视器ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 健康度统计对象列表
	 */
	@SQL("select severity, count(1) as count from ge_monitor_alarm where monitor_id=?1 and create_time between ?2 and ?3 group by severity")
	List<HealthStaForMonitor> selectHealthStaForMonitor(String monitorId, Date startTime, Date endTime);

	/**
	 * 根据监视器ID，开始结束时间查询健康度统计数据
	 * @param monitorId 监视器ID
	 * @param urlId URLID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 健康度统计对象列表
	 */
	@SQL("select severity, count(1) as count from ge_monitor_alarm where monitor_id=?1 and sub_resource_type='APPLICATION_SCENARIO_URL' " +
			"and sub_resource_id=?2 and create_time between ?3 and ?4 group by severity")
	List<HealthStaForMonitor> selectHealthStaForUrl(String monitorId, String urlId, Date startTime, Date endTime);

	/**
	 * 根据监视器ID、开始结束时间分页查询告警信息
	 * @param pageable 分页信息
	 * @param monitorId 监视器ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 分页告警信息
	 */
	@SQL("select * from ge_monitor_alarm where monitor_id = ?2 and create_time between ?3 and ?4")
	Page<Alarm> selectAlarmsByMonitorId(Pageable pageable, String monitorId, Date startDate, Date endDate);

	/**
	 * 根据监视器ID分页查询告警信息
	 * @param pageable 分页信息
	 * @param monitorId 监视器ID
	 * @return 分页告警信息
	 */
	@SQL("select * from ge_monitor_alarm where monitor_id = ?2 order by create_time desc")
	Page<Alarm> selectAlarmsByMonitorId(Pageable pageable, String monitorId);

	/**
	 * 根据监视器ID、开始结束时间分页查询严重告警信息
	 * @param pageable 分页信息
	 * @param monitorId 监视器ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 分页严重告警信息
	 */
	@SQL("select * from ge_monitor_alarm where monitor_id = ?2 and severity='CRITICAL' and create_time between ?3 and ?4")
	Page<Alarm> selectCriticalAlarmsByMonitorId(Pageable pageable, String monitorId, Date startDate, Date endDate);

	/**
	 * 根据监视器ID、开始结束时间分页查询严重告警信息数量
	 * @param monitorId 监视器ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 分页严重告警信息
	 */
	@SQL("select count(1) from ge_monitor_alarm where monitor_id = ?1 and severity='CRITICAL' and create_time between ?2 and ?3")
	int countCriticalByMonitorId(String monitorId, Date startDate, Date endDate);

	/**
	 * 根据监视器ID、开始结束时间分页查询警告告警信息数量
	 * @param monitorId 监视器ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 分页严重告警信息
	 */
	@SQL("select count(1) from ge_monitor_alarm where monitor_id = ?1 and severity='WARNING' and create_time between ?2 and ?3")
	int countWarningByMonitorId(String monitorId, Date startDate, Date endDate);

	/**
	 * 根据监视器ID、开始结束时间分页查询严重告警信息数量
	 * @param monitorId 监视器ID
	 * @param subResourceType 子资源类型
	 * @param subResourceId 子资源ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 分页严重告警信息
	 */
	@SQL("select count(1) from ge_monitor_alarm where monitor_id = ?1 " +
			"and sub_resource_type=?2 and sub_resource_id=?3 and severity='CRITICAL' and create_time between ?4 and ?5")
	int countCriticalBySubReousrce(String monitorId, String subResourceType, String subResourceId, Date startDate, Date endDate);

	/**
	 * 根据监视器ID、开始结束时间分页查询警告告警信息数量
	 * @param monitorId 监视器ID
	 * @param subResourceType 子资源类型
	 * @param subResourceId 子资源ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 分页严重告警信息
	 */
	@SQL("select count(1) from ge_monitor_alarm where monitor_id = ?1 " +
			"and sub_resource_type=?2 and sub_resource_id=?3 and severity='WARNING' and create_time between ?4 and ?5")
	int countWarningBySubReousrce(String monitorId, String subResourceType, String subResourceId, Date startDate, Date endDate);


	/**
	 * 根据时间查询健康度小时统计数量
	 * @param monitorId 监视器ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 健康度统计列表
	 */
	@SQL("select to_number(to_char(create_time, 'HH24')) as time_index,  severity, count(1) as count" +
			"  from ge_monitor_alarm" +
			"  where monitor_id = ?1 and create_time between ?2 and ?3" +
			" group by to_char(create_time, 'yyyy-MM-dd HH24'), to_char(create_time, 'HH24'), severity")
	List<HealthStaForTime> selectHealthStaForHour(String monitorId, Date startDate, Date endDate);

	/**
	 * 根据时间查询健康度小时统计数量
	 * @param monitorId 监视器ID
	 * @param
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 健康度统计列表
	 */
	@SQL("select to_number(to_char(create_time, 'HH24')) as time_index,  severity, count(1) as count" +
			"  from ge_monitor_alarm" +
			"  where monitor_id = ?1 and sub_resource_id=?2 and sub_resource_id=?3 and create_time between ?4 and ?5" +
			" group by to_char(create_time, 'yyyy-MM-dd HH24'), to_char(create_time, 'HH24'), severity")
	List<HealthStaForTime> selectHealthStaForHour(String monitorId, String subResourceType, String subResourceId, Date startDate, Date endDate);

	/**
	 * 根据时间查询健康度天统计数量
	 * @param monitorId 监视器ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 健康度统计列表
	 */
	@SQL("select to_number(to_char(create_time, 'dd')) as time_index,  severity, count(1) as count" +
			"  from ge_monitor_alarm" +
			"  where monitor_id = ?1 and create_time between ?2 and ?3" +
			" group by to_char(create_time, 'yyyy-MM-dd'), to_char(create_time, 'dd'), severity")
	List<HealthStaForTime> selectHealthStaForDay(String monitorId, Date startDate, Date endDate);

	/**
	 * 根据时间查询健康度天统计数量
	 * @param monitorId 监视器ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 健康度统计列表
	 */
	@SQL("select to_number(to_char(create_time, 'dd')) as time_index,  severity, count(1) as count" +
			"  from ge_monitor_alarm" +
			"  where monitor_id = ?1 and sub_resource_type = ?2 and sub_resource_id = ?3 and create_time between ?2 and ?3" +
			" group by to_char(create_time, 'yyyy-MM-dd'), to_char(create_time, 'dd'), severity")
	List<HealthStaForTime> selectHealthStaForDay(String monitorId, String subResourceType, String subResourceId, Date startDate, Date endDate);

    //获得告警信息列表
    @SQL("select * from GE_MONITOR_ALARM a order by a.CREATE_TIME desc,a.SEVERITY asc")
    List<Alarm> findAllAlarms();

    //获得当前监视器的历史告警信息
    @SQL("select * from GE_MONITOR_ALARM a where a.MONITOR_ID=?1  AND (a.severity='CRITICAL' or a.severity='WARNING') order by CREATE_TIME desc")
    List<Alarm> findByMonitorId(String monitorId);

    //获得当前监视器的历史告警信息
    @SQL("select * from GE_MONITOR_ALARM a where a.MONITOR_ID=?1  AND (a.severity='CRITICAL' or a.severity='WARNING') order by CREATE_TIME desc")
    Page<Alarm> findByMonitorId(String monitorId,Pageable pageable);

    //批量删除告警信息
    @SQL("delete GE_MONITOR_ALARM a where a.ID in (:alarmIds)")
    void batchDeleteAlarms(@Param("alarmIds")String[] alarmIds);

    //查询24小时内的告警信息
    @SQL("select * from GE_MONITOR_ALARM a #if(:givenTime=='24') { where (a.CREATE_TIME between (select sysdate - interval '24' hour from dual) and  sysdate)}" +
            "#if(:givenTime=='30'){ where (a.CREATE_TIME between (select sysdate - interval '30' day from dual) and  sysdate)}" +
            "#if(:givenType!=''){ and a.MONITOR_TYPE = :givenType} AND (a.severity='CRITICAL' or a.severity='WARNING') order by a.CREATE_TIME desc")
    List<Alarm> findAlarmsWithGivenTimeAndType(@Param("givenTime") String givenTime,@Param("givenType") String givenType);

    //查询时间和类型条件
    @SQL("select * from GE_MONITOR_ALARM a #if(:givenTime=='24') { where (a.CREATE_TIME between (select sysdate - interval '24' hour from dual) and  sysdate)}" +
            "#if(:givenTime=='30'){ where (a.CREATE_TIME between (select sysdate - interval '30' day from dual) and  sysdate)}" +
            "#if(:givenType!=''){ and a.MONITOR_TYPE = :givenType} AND (a.severity='CRITICAL' or a.severity='WARNING') order by a.CREATE_TIME desc")
    Page<Alarm> findAlarmsWithGivenTimeAndType(@Param("givenTime") String givenTime,@Param("givenType") String givenType,Pageable pageable);

    //查询时间和级别条件
    @SQL("select * from GE_MONITOR_ALARM a #if(:givenTime=='24') { where (a.CREATE_TIME between (select sysdate - interval '24' hour from dual) and  sysdate)}" +
            "#if(:givenTime=='30'){ where (a.CREATE_TIME between (select sysdate - interval '30' day from dual) and  sysdate)}" +
            "#if(:givenLevel!=''){ and a.severity = :givenLevel}  order by a.CREATE_TIME desc")
    Page<Alarm> findAlarmsWithGivenTimeAndLevel(@Param("givenTime") String givenTime,@Param("givenLevel") String givenLevel,Pageable pageable);

    //查询类型和级别条件
    @SQL("select * from GE_MONITOR_ALARM a #if(:givenType!=''){ where a.MONITOR_TYPE = :givenType}" +
            "#if(:givenLevel!=''){ and a.severity = :givenLevel}  order by a.CREATE_TIME desc")
    Page<Alarm> findAlarmsWithGivenTypeAndLevel(@Param("givenType") String givenType,@Param("givenLevel") String givenLevel,Pageable pageable);

    
    //查询三个条件
    @SQL("select * from GE_MONITOR_ALARM a #if(:givenTime=='24') { where (a.CREATE_TIME between (select sysdate - interval '24' hour from dual) and  sysdate)}" +
            "#if(:givenTime=='30'){ where (a.CREATE_TIME between (select sysdate - interval '30' day from dual) and  sysdate)}" +
            "#if(:givenType!=''){ and a.MONITOR_TYPE = :givenType}" +
    		"#if(:givenLevel!=''){ and a.severity = :givenLevel}  order by a.CREATE_TIME desc")
    Page<Alarm> findAlarmsWithThreeCondition(@Param("givenTime") String givenTime,@Param("givenType") String givenType,@Param("givenLevel") String givenLevel,Pageable pageable);

    
    //查询指定时间的告警信息
    @SQL("select * from GE_MONITOR_ALARM a #if(?1=='24') { where a.CREATE_TIME between (select sysdate - interval '24' hour from dual) and  sysdate}" +
            "#if(?1=='30'){ where a.CREATE_TIME between (select sysdate - interval '30' day from dual) and  sysdate} AND (a.severity='CRITICAL' or a.severity='WARNING')  order by a.CREATE_TIME desc")
    /*Page<Alarm> findAlarmsWithGivenTime(@Param("givenTime") String givenTime,Pageable pageable);*/
    Page<Alarm> findAlarmsWithGivenTime(String givenTime,Pageable pageable);

    //查询指定类型的告警信息
    @SQL("select * from GE_MONITOR_ALARM a #if(:givenType!=''){ where a.MONITOR_TYPE = :givenType} AND (a.severity='CRITICAL' or a.severity='WARNING')  order by a.CREATE_TIME desc")
    Page<Alarm> findAlarmsWithGivenType(@Param("givenType") String givenType,Pageable pageable);
    
    //查询指定级别的告警信息
    @SQL("select * from GE_MONITOR_ALARM a #if(:givenLevel!=''){ where a.severity = :givenLevel}  order by a.CREATE_TIME desc")
    Page<Alarm> findAlarmsWithGivenLevel(@Param("givenLevel") String givenLevel,Pageable pageable);

    @SQL("delete from GE_MONITOR_ALARM where monitor_id in (?1)")
    void deleteByMonitorIds(List<String> monitorId);
    @SQL("delete from GE_MONITOR_ALARM where monitor_id = ?1")
    void deleteByMonitorId(String monitorId);

    @SQL("delete from GE_MONITOR_ALARM where CREATE_TIME < (?1)")
    void deleteByStartTime(Date startTime);
}

