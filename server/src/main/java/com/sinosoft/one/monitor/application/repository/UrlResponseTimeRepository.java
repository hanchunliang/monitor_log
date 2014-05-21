package com.sinosoft.one.monitor.application.repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.UrlResponseTime;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * URL响应时间持久化接口
 * User: carvin
 * Date: 13-3-4
 * Time: 下午4:55
 */
public interface UrlResponseTimeRepository extends PagingAndSortingRepository<UrlResponseTime, String> {
	@SQL("SELECT * FROM GE_MONITOR_URL_RESPONSE_TIME t WHERE t.application_id=?1 and t.record_time >= ?2 AND t.record_time <= ?3")
	List<UrlResponseTime> selectUrlResponseTimes(String applicationId, Date startDate, Date endDate);

	/**
	 * 查询URL响应时间
	 * @param urlId 应用ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return URL响应时间列表
	 */
	@SQL("select min(urt.min_response_time) as min_response_time,\n" +
			"       max(urt.max_response_time) as max_response_time,\n" +
			"       sum(urt.total_response_time) as total_response_time,\n" +
            "       sum(urt.total_count) as total_count\n" +
			"  from ge_monitor_url_response_time urt\n" +
			" where urt.url_id = ?1\n" +
			"   and urt.record_time >= ?2 " +
            "   and urt.record_time < ?3 " +
            "   and urt.total_count != 0" +
            "   group by urt.url_id")
	UrlResponseTime selectUrlResponseTimesForMonitorUrl(String urlId, Date startDate, Date endDate);

	/**
	 * 统计平均响应时间
	 * @param applicationId 应用ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 平均响应时间
	 */
	@SQL("select avg(urt.total_response_time) from ge_monitor_url_response_time urt where urt.application_id=?1 and urt.record_time >= ?2 AND urt.record_time <= ?3")
	BigDecimal staAvgResponseTimeSta(String applicationId, Date startDate, Date endDate);

	/**
	 * 根据应用ID，URLID 以及开始结束时间查询响应时间列表
	 * @param applicationId 应用ID
	 * @param urlId URLID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 响应时间列表
	 */
	@SQL("SELECT * FROM GE_MONITOR_URL_RESPONSE_TIME t WHERE t.application_id=?1 and t.url_id=?2 and t.record_time >= ?3 AND t.record_time <= ?4")
	List<UrlResponseTime> selectUrlResponseTimes(String applicationId, String urlId, Date startDate, Date endDate);

    @SQL("delete from GE_MONITOR_URL_RESPONSE_TIME where RECORD_TIME<?1")
    void deleteByStartTime(Date startTime);
}
