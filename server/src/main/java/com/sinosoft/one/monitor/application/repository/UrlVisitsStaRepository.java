package com.sinosoft.one.monitor.application.repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.UrlVisitsSta;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * URL访问量统计持久化类
 * User: carvin
 * Date: 13-3-4
 * Time: 下午10:17
 */
public interface UrlVisitsStaRepository extends PagingAndSortingRepository<UrlVisitsSta, String> {
	UrlVisitsSta findByUrlIdAndRecordTime(String urlId, Date currentHourDate);

	/**
	 * 统计访问数量
	 * @param applicationId 应用ID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 访问数量
	 */
	@SQL("select sum(visit_number) from ge_monitor_url_visits_sta where application_id=?1 and record_time between ?2 and ?3")
	Integer countVisits(String applicationId, Date startDate, Date endDate);

	/**
	 * 统计访问次数
	 * @param applicationId 应用ID
	 * @param urlId URLID
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 访问数量
	 */
	@SQL("select * from ge_monitor_url_visits_sta where application_id=?1 and url_id=?2 and record_time between ?3 and ?4")
	List<UrlVisitsSta> selectUrlVisitsSta(String applicationId, String urlId, Date startDate, Date endDate);

    @SQL("delete from GE_MONITOR_URL_VISITS_STA where RECORD_TIME<?1")
    void deleteByStartTime(Date startTime);
}
