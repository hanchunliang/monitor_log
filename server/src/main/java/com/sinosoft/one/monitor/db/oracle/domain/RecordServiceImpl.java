package com.sinosoft.one.monitor.db.oracle.domain;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.common.AlarmMessageBuilder;
import com.sinosoft.one.monitor.common.AlarmSource;
import com.sinosoft.one.monitor.common.AttributeName;
import com.sinosoft.one.monitor.db.oracle.model.Ava;
import com.sinosoft.one.monitor.db.oracle.model.AvaSta;
import com.sinosoft.one.monitor.db.oracle.model.EventSta;
import com.sinosoft.one.monitor.db.oracle.model.Info;
import com.sinosoft.one.monitor.db.oracle.model.Lastevent;
import com.sinosoft.one.monitor.db.oracle.monitorSql.OracleMonitorSql;
import com.sinosoft.one.monitor.db.oracle.repository.AvaRepository;
import com.sinosoft.one.monitor.db.oracle.repository.AvaStaRepository;
import com.sinosoft.one.monitor.db.oracle.repository.EventStaRepository;
import com.sinosoft.one.monitor.db.oracle.repository.LasteventRepository;
import com.sinosoft.one.monitor.db.oracle.utils.DBUtil4Monitor;
import com.sinosoft.one.monitor.db.oracle.utils.db.DBUtil;
import com.sinosoft.one.monitor.db.oracle.utils.db.SqlObj;
import com.sinosoft.one.monitor.utils.AvailableCalculate;
import com.sinosoft.one.monitor.utils.AvailableCalculate.AvailableInf;
import com.sinosoft.one.monitor.utils.AvailableCalculate.AvailableStatistics;
import com.sinosoft.one.monitor.utils.DateUtil;

/**
 * User: Chunliang.Han Date: 13-3-3 Time: 下午10:21
 */
@Component
public class RecordServiceImpl implements RecordService {
    @Autowired
    private AvaRepository avaRepository;
    @Autowired
    private AvaStaRepository avaStaRepository;
    @Autowired
    private EventStaRepository eventStaRepository;
    @Autowired
    private LasteventRepository lasteventRepository;
    @Autowired
    private DBUtil4Monitor dbUtil4Monitor;
    @Autowired
    private AlarmMessageBuilder alarmMessageBuilder;

    @Override
    public void insertLastEvent(Info info, Date date) {
        boolean canConnect = dbUtil4Monitor.canConnect(info);
        if(canConnect){
            Lastevent lastevent = new Lastevent();
            // 获取event数据
            DBUtil dbutil = dbUtil4Monitor.getDBUtil(info.getId());
            String sql1 = OracleMonitorSql.bufferRatio;
            String sql2 = OracleMonitorSql.dictionaryRatio;
            String sql3 = OracleMonitorSql.libraryRatio;
            String sql4 = OracleMonitorSql.activeCount;
            List<Map<String, Object>> rsList1 = dbutil.queryObjMaps(SqlObj
                    .newInstance(sql1));
            List<Map<String, Object>> rsList2 = dbutil.queryObjMaps(SqlObj
                    .newInstance(sql2));
            List<Map<String, Object>> rsList3 = dbutil.queryObjMaps(SqlObj
                    .newInstance(sql3));
            List<Map<String, Object>> rsList4 = dbutil.queryObjMaps(SqlObj
                    .newInstance(sql4));
            double hitRatio = ((BigDecimal) rsList1.get(0).get("Hit Ratio")).doubleValue();
            lastevent.setBufferHitRate(hitRatio);
            int active = ((BigDecimal) rsList4.get(0).get("active")).intValue();
            lastevent.setActiveCount(active);
            double libHitRatio = ((BigDecimal) rsList3.get(0).get("libHitRatio")).doubleValue();
            lastevent.setBufferLibHitRate(libHitRatio);
            long connectionTime = dbUtil4Monitor.connectTime(info);
            lastevent.setConnectTime(connectionTime);
            double dictRatio = ((BigDecimal) rsList2.get(0).get("dictRatio")).doubleValue();
            lastevent.setDickHitRate(dictRatio);
            lastevent.setDatabaseId(info.getId());
            lastevent.setRecordTime(date);
            lastevent = lasteventRepository.save(lastevent);

            alarmMessageBuilder.newMessageBase(info.getId())
                    .addAlarmAttribute(AttributeName.BufferHitRatio, hitRatio*100 + "")
                    .addAlarmAttribute(AttributeName.ResponseTime, connectionTime + "")
                    .addAlarmAttribute(AttributeName.ActiveConnection, active + "").alarmSource(AlarmSource.DB).alarm();


            Calendar calender = DateUtil.getCalender();
            calender.setTime(date);
            Calendar newCalender = DateUtil.getCalender();
            newCalender.set(
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DATE));
            newCalender.set(Calendar.HOUR_OF_DAY, calender.get(Calendar.HOUR_OF_DAY));
            Date newDate = newCalender.getTime();
            List<EventSta> eventStas = eventStaRepository.findByTimeAndMonitorId(newDate, info.getId());
            if (eventStas == null || eventStas.size() == 0) {
                insertEventSta(lastevent, newDate, date);
            } else {
                updateEventSta(lastevent, newDate);
            }
            Date timePoint = StaTimeEnum.getTime(StaTimeEnum.LAST_24HOUR, date);
            lasteventRepository.clear(timePoint);
        }
    }

    private void insertEventSta(Lastevent lastevent, Date inserTime, Date date) {
        // 连接时间统计
        EventSta connectTimeSta = new EventSta();
        connectTimeSta.setDatabaseId(lastevent.getDatabaseId());
        connectTimeSta.setEventType("1");
        connectTimeSta.setAvg(lastevent.getConnectTime() / 1.0);
        connectTimeSta.setMax(lastevent.getConnectTime() / 1.0);
        connectTimeSta.setMin(lastevent.getConnectTime() / 1.0);
        connectTimeSta.setEventRecordTime(date);
        connectTimeSta.setRecordTime(inserTime);
        connectTimeSta.setEventCount(1);
        // 连接数统计记录
        EventSta activeCountSta = new EventSta();
        activeCountSta.setDatabaseId(lastevent.getDatabaseId());
        activeCountSta.setEventType("2");
        activeCountSta.setAvg(lastevent.getActiveCount() / 1.0);
        activeCountSta.setMax(lastevent.getActiveCount() / 1.0);
        activeCountSta.setMin(lastevent.getActiveCount() / 1.0);
        activeCountSta.setEventRecordTime(date);
        activeCountSta.setRecordTime(inserTime);
        activeCountSta.setEventCount(1);
        // 缓冲区击中率统计记录
        EventSta bufferHitRateSta = new EventSta();
        bufferHitRateSta.setDatabaseId(lastevent.getDatabaseId());
        bufferHitRateSta.setEventType("3");
        bufferHitRateSta.setAvg(lastevent.getBufferHitRate() / 1.0);
        bufferHitRateSta.setMax(lastevent.getBufferHitRate() / 1.0);
        bufferHitRateSta.setMin(lastevent.getBufferHitRate() / 1.0);
        bufferHitRateSta.setEventRecordTime(date);
        bufferHitRateSta.setRecordTime(inserTime);
        bufferHitRateSta.setEventCount(1);
        eventStaRepository.save(connectTimeSta);
        eventStaRepository.save(activeCountSta);
        eventStaRepository.save(bufferHitRateSta);
    }

    private Double getAvg(EventSta connectTimeSta, double count) {
        return (connectTimeSta.getAvg() * connectTimeSta.getEventCount() + count)
                / (connectTimeSta.getEventCount() + 1);
    }

    private Double getMax(EventSta connectTimeSta, double count) {
        return count > connectTimeSta.getMax() ? count : connectTimeSta
                .getMax();
    }

    private Double getMin(EventSta connectTimeSta, double count) {
        return count < connectTimeSta.getMin() ? count : connectTimeSta
                .getMin();
    }

    private void updateEventSta(Lastevent lastevent, Date inserTime) {
        String monitorId = lastevent.getDatabaseId();
        // 连接时间统计
        EventSta connectTimeSta = eventStaRepository.findConnectTimeSta(
                monitorId, inserTime);
        connectTimeSta.setDatabaseId(lastevent.getDatabaseId());
        connectTimeSta.setEventType("1");
        Double connectTime = (double) lastevent.getConnectTime();
        Double connectTimeAvg = getAvg(connectTimeSta, connectTime);
        Double connectTimeMax = getMax(connectTimeSta, connectTime);
        Double connectTimeMin = getMin(connectTimeSta, connectTime);
        connectTimeSta.setAvg(connectTimeAvg);
        connectTimeSta.setMax(connectTimeMax);
        connectTimeSta.setMin(connectTimeMin);
        connectTimeSta.setEventCount(connectTimeSta.getEventCount() + 1);
        // 连接数统计记录
        EventSta activeCountSta = eventStaRepository.findActiveCountSta(
                monitorId, inserTime);
        activeCountSta.setDatabaseId(lastevent.getDatabaseId());
        activeCountSta.setEventType("2");
        Double activeCount = (double) lastevent.getActiveCount();
        Double activeCountAvg = getAvg(activeCountSta, activeCount);
        Double activeCountMax = getMax(activeCountSta, activeCount);
        Double activeCountMin = getMin(activeCountSta, activeCount);
        activeCountSta.setAvg(activeCountAvg);
        activeCountSta.setMax(activeCountMax);
        activeCountSta.setMin(activeCountMin);
        activeCountSta.setEventCount(connectTimeSta.getEventCount() + 1);
        // 缓冲区击中率统计记录
        EventSta bufferHitRateSta = eventStaRepository.findHitRateSta(
                monitorId, inserTime);
        bufferHitRateSta.setDatabaseId(lastevent.getDatabaseId());
        bufferHitRateSta.setEventType("3");
        Double bufferHitRateCount =  lastevent.getBufferHitRate();
        Double bufferHitRateAvg = getAvg(bufferHitRateSta, bufferHitRateCount);
        Double bufferHitRateMax = getMax(bufferHitRateSta, bufferHitRateCount);
        Double bufferHitRateMin = getMin(bufferHitRateSta, bufferHitRateCount);
        bufferHitRateSta.setAvg(bufferHitRateAvg);
        bufferHitRateSta.setMax(bufferHitRateMax);
        bufferHitRateSta.setMin(bufferHitRateMin);
        bufferHitRateSta.setEventCount(connectTimeSta.getEventCount() + 1);
        eventStaRepository.save(connectTimeSta);
        eventStaRepository.save(activeCountSta);
        eventStaRepository.save(bufferHitRateSta);
    }

    @Override
    public void insertAva(Info info, Date date) {
        Calendar calender = DateUtil.getCalender();
        calender.setTime(date);
        Calendar newCalender = DateUtil.getCalender();
        newCalender.set(calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH), calender.get(Calendar.DATE));
        Date newDate = newCalender.getTime();
//        Ava oldAva = avaRepository.findAvaByTime(info.getId(),newDate, date);
        Ava ava = new Ava();
        boolean canConnect = dbUtil4Monitor.canConnect(info);
        if (canConnect) {
            ava.setState("1");
        } else {
            ava.setState("0");
            alarmMessageBuilder.newMessageBase(info.getId())
                    .addAlarmAttribute(AttributeName.Availability, "0").alarmSource(AlarmSource.DB).alarm();
        }
        ava.setRecordTime(date);
        ava.setDatabaseId(info.getId());
        long interval = info.getPullInterval();
        ava.setInterval(interval);
        //把这行移到原242行avaRepository.clear(timePoint);的上面
        AvaSta avaSta = avaStaRepository.findAvaStaByTime(info.getId(), newDate);
        if (avaSta == null || avaSta.getId() == null || avaSta.getId().equals("")) {
            long tempInterval = date.getTime()-newDate.getTime();
            tempInterval = tempInterval/60000;
            if(tempInterval<interval){
                interval = tempInterval;
            }
            ava.setInterval(interval);
            insertAvaSta(ava, date, newDate,(int)interval);
        } else {
            updateAvaSta(ava, newDate, info.getPullInterval());
        }
        Date timePoint = StaTimeEnum.getTime(StaTimeEnum.LAST_24HOUR, date);
        avaRepository.save(ava);
        avaRepository.clear(timePoint);
    }

    private void insertAvaSta(Ava ava, Date date, Date inserTime, int interval) {
        AvaSta avaSta = new AvaSta();

        avaSta.setDatabaseId(ava.getDatabaseId());
        avaSta.setAvaRecordTime(date);
        avaSta.setRecordTime(inserTime);
        AvailableCalculate.AvailableCalculateParam avaCalParam = new AvailableCalculate.AvailableCalculateParam(
                new AvailableStatistics(0L, 0L, 0),
                new ArrayList<AvailableCalculate.AvailableCountsGroupByInterval>(), new ArrayList<AvailableCalculate.AvailableCountsGroupByInterval>(), interval, ava.getState().equals("1"),
                null
        );

        AvailableCalculate availableCalculate = AvailableCalculate.calculate(avaCalParam);
        //正常运行时间
        avaSta.setNormalRuntime(availableCalculate.getAliveTime().longValue());
        //总停机时间
        avaSta.setTotalPoweroffTime(availableCalculate.getStopTime().longValue());
        //总停机次数
        avaSta.setPoweroffCount(avaSta.getTotalPoweroffTime());
        //平均故障间隔时间
        avaSta.setAvgFailureTime(availableCalculate.getTimeBetweenFailures().longValue());
        //未知时间
        long unKnownTime = availableCalculate.getUnknownTime().longValue();
        avaSta.setUnknowTime(unKnownTime);
        if(StringUtils.equals(ava.getState(), "0")){
            avaSta.setUnavCount(1);
        } else if(StringUtils.equals(ava.getState(), "1")){
            avaSta.setAvCount(1);
        }
        avaStaRepository.save(avaSta);
    }

    private void updateAvaSta(Ava ava, Date inserTime, int interval) {
        AvaSta avaSta = avaStaRepository.findAvaStaByTime(ava.getDatabaseId(), inserTime);
        //总停机次数
        int falseCount = (int) avaSta.getPoweroffCount();
        //可用次数
        long avCount = avaSta.getAvCount();
        //不可用次数
        long unAvCount = avaSta.getUnavCount();
        if (StringUtils.equals(ava.getState(), "0")) {
            //avaSta.setPoweroffCount(++falseCount);
            avaSta.setUnavCount(++unAvCount);
        } else if (StringUtils.equals(ava.getState(), "1")) {
            avaSta.setAvCount(++avCount);
        }
        List<AvailableCalculate.AvailableCountsGroupByInterval> avCountList = avaRepository.findAvCount(inserTime, ava.getDatabaseId());
        List<AvailableCalculate.AvailableCountsGroupByInterval> unAvCountList = avaRepository.findUnAvCount(inserTime, ava.getDatabaseId());

        AvailableCalculate.AvailableCalculateParam avaCalParam = new AvailableCalculate.AvailableCalculateParam(
                new AvailableStatistics(avaSta.getNormalRuntime(), avaSta.getTotalPoweroffTime(), falseCount),
                avCountList, unAvCountList, interval, ava.getState().equals("1"),
                new AvailableInf(ava.getRecordTime(), ava.getState().equals("1"), new Long(ava.getInterval()).intValue())
        );

        AvailableCalculate availableCalculate = AvailableCalculate.calculate(avaCalParam);
        //正常运行时间
        avaSta.setNormalRuntime(availableCalculate.getAliveTime().longValue());
        //总停机时间
        avaSta.setTotalPoweroffTime(availableCalculate.getStopTime().longValue());
        //总停机次数
        avaSta.setPoweroffCount(availableCalculate.getFalseCount().longValue());
        //平均故障间隔时间
        avaSta.setAvgFailureTime(availableCalculate.getTimeBetweenFailures().longValue());
        //未知时间
        long unKnownTime = availableCalculate.getUnknownTime().longValue();
        avaSta.setUnknowTime(unKnownTime);

        avaStaRepository.save(avaSta);
    }
}
