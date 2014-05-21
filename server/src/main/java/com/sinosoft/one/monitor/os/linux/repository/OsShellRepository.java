package com.sinosoft.one.monitor.os.linux.repository;
// Generated 2013-2-27 21:43:52 by One Data Tools 1.0.0

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.monitor.os.linux.model.OsShell;

public interface OsShellRepository extends PagingAndSortingRepository<OsShell, String> {
	
	@Query("from OsShell")
    public List<OsShell> findShell();
	
}

