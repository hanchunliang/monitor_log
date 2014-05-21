package com.sinosoft.one.monitor.threshold.domain;

import com.sinosoft.one.monitor.attribute.repository.AttributeThresholdRepository;
import com.sinosoft.one.monitor.threshold.model.Threshold;
import com.sinosoft.one.monitor.threshold.repository.ThresholdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 阈值信息处理业务逻辑类
 * User: carvin
 * Date: 13-3-1
 * Time: 上午10:38
 *
 */
@Service
public class ThresholdService {
	private static final Logger logger = LoggerFactory
			.getLogger(ThresholdService.class);
	private ThresholdRepository thresholdRepository;
	
	private AttributeThresholdRepository attributeThresholdRepository;

	public void saveEntity(Threshold entity) {
		if (logger.isDebugEnabled()) {
			logger.info("ThresholdService$saveEntity开始执行，参数entity={}", entity);
		}
		Assert.notNull(entity, "ThresholdService$saveEntity中的参数entity不能为NULL.");
		thresholdRepository.save(entity);
	}

	public void deleteEntity(Threshold entity) {
		if (logger.isDebugEnabled()) {
			logger.info("ThresholdService$deleteEntity开始执行，参数entity={}", entity);
		}
		Assert.notNull(entity,
				"ThresholdService$deleteEntity中的参数entity不能为NULL.");
		thresholdRepository.delete(entity);
	}

	public void deleteEntities(String ids) {
		if (logger.isDebugEnabled()) {
			logger.info("ThresholdService$deleteEntities开始执行，参数ids={}", ids);
		}
		Assert.notNull(ids, "ThresholdService$deleteEntities中的参数ids不能为NULL.");
		thresholdRepository.deleteByIDs(ids);
	}

	public void deleteEntity(String id) {
		if (logger.isDebugEnabled()) {
			logger.info("ThresholdService$deleteEntity开始执行，参数id={}", id);
		}
		Assert.notNull(id, "ThresholdService$deleteEntity中的参数id不能为NULL.");
		thresholdRepository.delete(id);
		attributeThresholdRepository.deleteByThresholdID(id);
	}

	public Threshold getThreshold(String id) {
		if (logger.isDebugEnabled()) {
			logger.info("ThresholdService$getThreshold开始执行，参数id={}", id);
		}
		Assert.notNull("", "ThresholdService$findThreshold中的参数id不能为NULL.");
		return thresholdRepository.findOne(id);
	}

	public List<Threshold> getAllThreshold() {
		return this.getAllThreshold(null);
	}

	public List<Threshold> getAllThreshold(Sort sort) {
		if (sort == null) {
			sort = new Sort(Sort.Direction.ASC, "id");
		}
		if (logger.isDebugEnabled()) {
			logger.info("ThresholdService$getAllThreshold开始执行，参数sort={}", sort);
		}
		return (List<Threshold>) thresholdRepository.findAll(sort);
	}

	@Autowired
	public void setThresholdRepository(ThresholdRepository thresholdRepository) {
		this.thresholdRepository = thresholdRepository;
	}

	/**
	 * 根据资源ID以及属性ID得到阈值
	 * @param resourceId 资源ID
	 * @param attributeId 属性ID
	 * @return 阈值对象
	 */
	public Threshold queryThreshold(String resourceId, String attributeId) {
		return thresholdRepository.selectByResourceIdAndAttributeId(resourceId, attributeId);
	}
}
