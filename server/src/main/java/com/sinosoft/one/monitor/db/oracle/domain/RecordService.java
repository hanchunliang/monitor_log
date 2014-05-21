package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.Info;

import java.util.Date;

/**
 * User: Chunliang.Han
 * Date: 13-3-3
 * Time: 下午10:17
 */
public interface RecordService {
	/**
	 * 插入事件实时记录
	 * @param info
	 * @param date
	 */
    void insertLastEvent(Info info,Date date);
//    /**
//     * 插入事件统计记录，该统计在上一方法中实时处理
//     * @param info
//     * @param date
//     */
//    void insertEventSta(Info info,Date start,Date end);
    /**
     * 插入可用性临时记录
     * @param info
     * @param date
     */
    void insertAva(Info info,Date date);
//    /**
//     * 插入可用性统计记录，该统计在上一方法中实时处理
//     * @param info
//     * @param date
//     */
//    void insertAvaSta(Info info,Date start,Date end);
}
