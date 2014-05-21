package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.common.HealthStaService;
import com.sinosoft.one.monitor.common.Trend;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;

/**
 * ApplicationEnhance
 * User: ChengQi
 * Date: 13-3-10
 * Time: PM5:09
 * TODO 暂时先放着，将来移动到Application中，引来问题：One-Data是否可以增加根据Pojo的，@Autowired自动注入
 */
public class ApplicationEnhance {


    private Application application;

    private SeverityLevel health;

    private Trend available;


    public ApplicationEnhance(Application application, SeverityLevel health, Trend available) {
        this.application = application;
        this.health = health;
        this.available = available;
    }

    public Application getApplication() {
        return this.application;
    }

    public SeverityLevel getHealth() {
        return health;
    }

    public Trend getTrend() {
        return available;
    }

}
