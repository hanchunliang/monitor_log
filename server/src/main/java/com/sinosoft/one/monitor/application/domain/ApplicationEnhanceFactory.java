package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.common.HealthStaService;
import com.sinosoft.one.monitor.common.Trend;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Application增强工厂
 * User: ChengQi
 * Date: 13-3-10
 * Time: PM5:07
 */

@Component
public class ApplicationEnhanceFactory {

    @Autowired
    private ApplicationEmuService applicationEmuService;

    @Autowired
    private HealthStaService healthStaService;


    /**
     * TODO 如果当前application获取不到任何的URL，就认为application可用的做法有待商酌
     * @param application
     * @return
     */
    public  ApplicationEnhance enhanceApplication(Application application) {
        SeverityLevel health = healthStaService.healthStaForCurrent(application.getId(), application.getInterval() == null ? 10 : application.getInterval().intValue());
        Trend available = null;
        try {
            available = applicationEmuService.getAppAvailableToday(application.getId()).getTrend();
        } catch (EumUrlsNotFoundException e) {
            available = Trend.SAME;
        }
        ApplicationEnhance applicationEnhance = new ApplicationEnhance(application, health, available);
        return applicationEnhance;
    }
}
