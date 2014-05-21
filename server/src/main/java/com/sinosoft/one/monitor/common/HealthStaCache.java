package com.sinosoft.one.monitor.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 健康度缓存.
 * User: carvin
 * Date: 13-3-7
 * Time: 下午10:43
 */
@Component
public final class HealthStaCache {
	private Logger logger = LoggerFactory.getLogger(HealthStaCache.class);
	@Autowired
	private AlarmRepository alarmRepository;
	private LocalDate currentDate;
	private final LoadingCache<String, HealthStaCacheModel> healthStaCacheModelCache;
	public  HealthStaCache() {
		healthStaCacheModelCache = CacheBuilder.newBuilder().initialCapacity(1024).build(new CacheLoader<String, HealthStaCacheModel>() {
			@Override
			public HealthStaCacheModel load(String key) throws Exception {
				String[] keys = key.split("#");

				List<HealthStaForMonitor> healthStaForMonitorList = alarmRepository.selectHealthStaForUrl(keys[0], keys[1], LocalDate.now().toDate(), LocalDateTime.now().toDate());
				HealthStaCacheModel healthStaCacheModel = new HealthStaCacheModel();
				for(HealthStaForMonitor healthStaForMonitor : healthStaForMonitorList) {
					healthStaCacheModel.increase(healthStaForMonitor);
				}
				return healthStaCacheModel;
			}
		});
		currentDate = LocalDate.now();
	}

	public void increase(String monitorId, String resourceId, SeverityLevel severityLevel) {
		if(LocalDate.now().isAfter(currentDate)) {
			healthStaCacheModelCache.cleanUp();
		}
		String key = getKey(monitorId, resourceId);
		HealthStaCacheModel healthStaCacheModel = healthStaCacheModelCache.getIfPresent(key);
		if(healthStaCacheModel == null) {
			healthStaCacheModel = new HealthStaCacheModel(severityLevel);
		} else {
			healthStaCacheModel.increase(severityLevel);
		}
		healthStaCacheModelCache.put(key, healthStaCacheModel);
	}

	public String getHealthBar(String monitorId, String resourceId) {
		HealthStaCacheModel healthStaCacheModel = null;
		try {
			healthStaCacheModel = healthStaCacheModelCache.get(getKey(monitorId, resourceId));
		} catch (ExecutionException e) {
			logger.error("load alarm info by url exception.", e);
		}
		return  healthStaCacheModel == null ? "" : healthStaCacheModel.getHealthBar();
	}

	private String getKey(String monitorId, String resourceId) {
		return monitorId + "#" + resourceId;
	}
}
