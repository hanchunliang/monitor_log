package com.sinosoft.one.monitor.account.repository;
// Generated 2013-9-27 13:57:20 by One Data Tools 1.0.0

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.account.model.Account;
import com.sinosoft.one.mvc.web.annotation.Param;

public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
	
	@Query("from Account a where a.loginName =?1")
	Account findByLoginName(String loginName);
	    
		
	@SQL("DELETE FROM ge_monitor_account a WHERE a.id IN(?1)")
	void deleteByIDs(@Param("ids") String ids);
		
//	@Query("from Account a where a.loginName !='admin' order by id asc")
	@Query("from Account a order by id asc")
	List<Account> findAllNotAdmin();

}

