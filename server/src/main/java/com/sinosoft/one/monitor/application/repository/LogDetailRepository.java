package com.sinosoft.one.monitor.application.repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.ExceptionInfo;
import com.sinosoft.one.monitor.application.model.MethodTraceLog;
import com.sinosoft.one.monitor.application.model.UrlTraceLog;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Intro:
 * User: Kylin
 * Date: 13-3-9
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 */
public interface LogDetailRepository extends PagingAndSortingRepository<MethodTraceLog,String> {

    /**
     * 根据ge_monitor_url_trace_log表id查询url请求记录
     * @param logId
     * @return
     */
    @SQL("select * from ge_monitor_url_trace_log where id = ?1")
    public UrlTraceLog selectUrlDetail (String logId);

    /**
     * 根据ge_monitor_method_trace_log表url_trace_log_id查询方法详细记录
     * @param logId
     * @return
     */
    @SQL("select * from ge_monitor_method_trace_log where url_trace_log_id = ?1")
    public List<MethodTraceLog> selectMethodDetail (String logId);

    /**
     * 根据ge_monitor_exception_info表url_trace_log_id查询方法异常记录
     * @param logId
     * @return
     */
    @SQL("select * from ge_monitor_exception_info where url_trace_log_id = ?1")
    public List<ExceptionInfo> selectExceptionInfo(String logId);

    /**
     * 根据ge_monitor_method_trace_log表id查询方法异常记录
     * @param methodId
     * @return
     */
    @SQL("select * from ge_monitor_method_trace_log where id = ?1")
    MethodTraceLog getMethodDetail(String methodId);

    /**
     * 根据ge_monitor_exception_info表ALARM_ID查询异常记录
     * @param alarmDetailId
     * @return ExceptionInfo
     */
    @SQL("select * from ge_monitor_exception_info where alarm_id = ?1")
    ExceptionInfo selectExceptionInfoOfAlarm(String alarmDetailId);

    @SQL("delete from GE_MONITOR_METHOD_TRACE_LOG where RECORD_TIME<?1")
    void deleteByStartTime(Date startTime);
}
