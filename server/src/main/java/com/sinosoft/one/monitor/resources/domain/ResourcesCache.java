package com.sinosoft.one.monitor.resources.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sinosoft.one.monitor.attribute.domain.AttributeService;
import com.sinosoft.one.monitor.resources.model.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * 属性缓存.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午4:01
 *
 */
@Component
public class ResourcesCache {
	private static Logger logger = LoggerFactory.getLogger(ResourcesCache.class);
	@Autowired
	private ResourcesService resourcesService;
	private LoadingCache<String, Resource> resourcesCache = CacheBuilder.newBuilder().maximumSize(1024)
			.build(new CacheLoader<String, Resource>() {
				@Override
				public Resource load(String key) throws Exception {
					logger.info("fetch resource type from database");
					return resourcesService.getResource(key);
				}
			});

	public Resource getResource(String resourceId) {
		try {
			return resourcesCache.get(resourceId);
		} catch (ExecutionException e) {
			logger.warn("get resource exception :" + e.getMessage());
			return Resource.EMPTY;
		}
	}

	public void removeResourceType(String resourceId) {
		resourcesCache.invalidate(resourceId);
	}

	public void clearUp() {
		resourcesCache.cleanUp();
	}
}
