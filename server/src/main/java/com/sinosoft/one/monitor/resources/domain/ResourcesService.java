package com.sinosoft.one.monitor.resources.domain;

import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理资源相关信息业务逻辑类.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午4:08
 */
@Service
public class ResourcesService {
	@Autowired
	private ResourcesRepository resourcesRepository;

	/**
	 * 根据资源ID获得资源类型
	 * @param resourceId 资源ID
	 * @return 资源类型
	 */
	public String getResourceType(String resourceId) {
		return resourcesRepository.findOne(resourceId).getResourceType();
	}

	/**
	 * 根据资源ID获得资源
	 * @param resourceId 资源ID
	 * @return 资源对象
	 */
	public Resource getResource(String resourceId) {
		return resourcesRepository.findOne(resourceId);
	}

	/**
	 * 保存一个资源
	 * @param resource 资源对象
	 */
    @Transactional(readOnly = false)
	public void saveResource(Resource resource) {
		resourcesRepository.save(resource);
	}
}
