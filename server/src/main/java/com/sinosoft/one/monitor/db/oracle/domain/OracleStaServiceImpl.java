package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.*;
import com.sinosoft.one.monitor.db.oracle.repository.EventStaRepository;
import com.sinosoft.one.monitor.db.oracle.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-3-2
 * Time: 下午2:25
 */
@Component
public class OracleStaServiceImpl implements OracleStaService {
    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private EventStaRepository eventStaRepository;

    @Override
    public OracleStaInfoDetailModel getBaseInfo(String monitorId, int eventType, String title, Date now, StaTimeEnum staTimeEnum, TimeGranularityEnum timeGranularityEnum) {
        OracleStaInfoDetailModel staInfoDetailModel = new OracleStaInfoDetailModel();
        staInfoDetailModel.setTitle(title);
        Info info = infoRepository.findOne(monitorId);
        String monitorName = info.getName();
        staInfoDetailModel.setMonitorName(monitorName);
        //2013-3-2 2:42
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String end = sdf.format(now);
        Date time = StaTimeEnum.getTime(staTimeEnum, now);
        String begin = sdf.format(time);
        staInfoDetailModel.setBegin(begin);
        staInfoDetailModel.setEnd(end);
        SimpleDateFormat sdf2 = new SimpleDateFormat("E,dd日-MM月-yyyy年 HH:mm");
        switch (eventType) {
            //连接时间
            case 1: {
                //阈值信息
//                staInfoDetailModel.setNormal();
//                staInfoDetailModel.setWarn();
//                staInfoDetailModel.setError();
                staInfoDetailModel.setEventName("连接时间 ms");
                //表数据
                List<EventSta> eventStaList = eventStaRepository.findAllByTimeAndType(time, now, eventType + "",monitorId);
                staInfoDetailModel.setRecordItems(eventStaList);
                //点列
                List<Point> points = new ArrayList<Point>();
                //平均值
                double min = 0;
                double max = 0;
                double sum = 0;
                for (EventSta eventSta : eventStaList) {
                    //和
                    sum += eventSta.getAvg();
                    //最小值
                    if (min >= eventSta.getMin()) {
                        min = eventSta.getMin();
                    }
                    //最大值
                    if (max <= eventSta.getMax()) {
                        max = eventSta.getMax();
                    }
                    //点
                    Point point = new Point();
                    point.setxAxis(eventSta.getEventRecordTime());
                    point.setyAxis(eventSta.getAvg());
                    point.setDescription("连接时间" + "(" + sdf2.format(eventSta.getEventRecordTime()) + " ," + eventSta.getAvg() + ")");
                    points.add(point);
                }
                staInfoDetailModel.setMaxAvg(max);
                staInfoDetailModel.setMinAvg(min);
                Double avg1 = sum / eventStaList.size();
                int  avg2 = avg1.intValue();
                staInfoDetailModel.setAvg(Double.parseDouble(avg2+""));

                //图形点
                staInfoDetailModel.setxName("时间");
                staInfoDetailModel.setyName("连接时间");

                staInfoDetailModel.setPoints(points);
                break;
            }
            //用户数
            case 2: {
                staInfoDetailModel.setEventName("用户数");
                //表数据
                List<EventSta> eventStaList = eventStaRepository.findAllByTimeAndType(time, now, eventType + "", monitorId);
                staInfoDetailModel.setRecordItems(eventStaList);
                //点列
                List<Point> points = new ArrayList<Point>();
                //平均值
                double min = 0;
                double max = 0;
                double sum = 0;
                for (EventSta eventSta : eventStaList) {
                    //和
                    sum += eventSta.getAvg();
                    //最小值
                    if (min >= eventSta.getMin()) {
                        min = eventSta.getMin();
                    }
                    //最大值
                    if (max <= eventSta.getMax()) {
                        max = eventSta.getMax();
                    }
                    //点
                    Point point = new Point();
                    point.setxAxis(eventSta.getEventRecordTime());
                    point.setyAxis(eventSta.getAvg());
                    point.setDescription("用户数" + "(" + sdf2.format(eventSta.getEventRecordTime()) + " ," + eventSta.getAvg() + ")");
                    points.add(point);
                }
                staInfoDetailModel.setMaxAvg(max);
                staInfoDetailModel.setMinAvg(min);
                Double avg1 = sum / eventStaList.size();
                int avg2 = avg1.intValue();
                staInfoDetailModel.setAvg(Double.parseDouble(avg2+""));

                //图形点
                staInfoDetailModel.setxName("时间");
                staInfoDetailModel.setyName("用户数");

                staInfoDetailModel.setPoints(points);
                break;
            }
            //SGA命中率
            case 3: {
                staInfoDetailModel.setEventName("缓冲区击中率");
                //表数据
                List<EventSta> eventStaList = eventStaRepository.findAllByTimeAndType(time, now, eventType + "", monitorId);
                staInfoDetailModel.setRecordItems(eventStaList);
                //点列
                List<Point> points = new ArrayList<Point>();
                //平均值
                double min = 0;
                double max = 0;
                double sum = 0;
                for (EventSta eventSta : eventStaList) {
                    //和
                    sum += eventSta.getAvg();
                    //最小值
                    if (min >= eventSta.getMin()) {
                        min = eventSta.getMin();
                    }
                    //最大值
                    if (max <= eventSta.getMax()) {
                        max = eventSta.getMax();
                    }
                    //点
                    Point point = new Point();
                    point.setxAxis(eventSta.getEventRecordTime());
                    point.setyAxis(eventSta.getAvg());
                    point.setDescription("缓冲区击中率" + "(" + sdf2.format(eventSta.getEventRecordTime()) + " ," + eventSta.getAvg() + ")");
                    points.add(point);
                }
                staInfoDetailModel.setMaxAvg(max);
                staInfoDetailModel.setMinAvg(min);
                staInfoDetailModel.setAvg(sum / eventStaList.size());

                //图形点
                staInfoDetailModel.setxName("时间");
                staInfoDetailModel.setyName("缓冲区击中率");

                staInfoDetailModel.setPoints(points);
                break;
            }
        }
        return staInfoDetailModel;
    }
}
