package com.sinosoft.one.monitor.attribute.repository;
// Generated 2013-3-1 10:54:17 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.attribute.model.AttributeThreshold;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AttributeThresholdRepository extends PagingAndSortingRepository<AttributeThreshold, String> {

    public AttributeThreshold findByResourceIdAndAttributeId(String resourceId,String attributeId);

    public AttributeThreshold findByResourceIdAndAttributeIdAndThresholdId(String resourceId,String attributeId,String thresholdId);
    
    @SQL("delete GE_MONITOR_ATTRIBUTE_THRESHOLD where THRESHOLD_ID =?1")
    void deleteByThresholdID(String ThresholdID);
}

