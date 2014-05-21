package com.sinosoft.one.monitor.action.repository;
// Generated 2013-3-1 10:54:17 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.action.model.MailAction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * 邮件动作持久化类
 */
public interface MailActionRepository extends PagingAndSortingRepository<MailAction, String> {
	@SQL("DELETE FROM ge_monitor_email_action e WHERE e.id IN(?1)")
	void deleteByIDs(@Param("ids")String ids);
}

