package com.sinosoft.one.monitor.threshold.repository;
// Generated 2013-3-1 10:29:54 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.threshold.model.Threshold;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


/**
 * 阈值信息持久化接口
 */
public interface ThresholdRepository extends PagingAndSortingRepository<Threshold, String> {
	/**
	 * 根据资源ID以及属性ID得到阈值
	 * @param resourceId 资源ID
	 * @param attributeId 属性ID
	 * @return 阈值对象
	 */
	@SQL("SELECT mt.* FROM GE_MONITOR_THRESHOLD mt, GE_MONITOR_ATTRIBUTE_THRESHOLD mat WHERE mt.id=mat.THRESHOLD_ID and mat.RESOURCE_ID=?1 and mat.ATTRIBUTE_ID=?2")
	Threshold selectByResourceIdAndAttributeId(String resourceId, String attributeId);

	@SQL("DELETE FROM ge_monitor_threshold  WHERE id in (?1) ")
	void deleteByIDs(@Param("ids")String ids);
}

