package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.AvaSta;
import com.sinosoft.one.monitor.db.oracle.model.OraclePowerOffTimeModel;
import com.sinosoft.one.monitor.db.oracle.repository.AvaStaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-3-2
 * Time: 下午7:45
 */
@Component
public class OracleAvaServiceImpl implements OracleAvaService {
    @Autowired
    private AvaStaRepository avaStaRepository;

    @Override
    public AvaSta findAvaSta(String monitorId, StaTimeEnum avaStaTimeEnum) {
        AvaSta avaSta = null;
        switch (avaStaTimeEnum) {
            case TODAY: {
                Date date = new Date();
                date = StaTimeEnum.getTime(StaTimeEnum.TODAY,date);
                avaSta = avaStaRepository.findAvaSta(monitorId,date);

                break;
            }
        }

        return avaSta;
    }

    @Override
    public List<AvaSta> listAvaSta(String monitorId) {
        return null;
    }

    @Override
    public List<OraclePowerOffTimeModel> listPowerOffTime(String monitorId) {
        return null;
    }
}
