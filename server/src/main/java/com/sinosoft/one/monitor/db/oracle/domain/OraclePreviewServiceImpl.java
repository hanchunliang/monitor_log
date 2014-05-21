package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.*;
import com.sinosoft.one.monitor.db.oracle.monitorSql.OracleMonitorSql;
import com.sinosoft.one.monitor.db.oracle.repository.InfoRepository;
import com.sinosoft.one.monitor.db.oracle.repository.LasteventRepository;
import com.sinosoft.one.monitor.db.oracle.utils.DBUtil4Monitor;
import com.sinosoft.one.monitor.db.oracle.utils.db.DBUtil;
import com.sinosoft.one.monitor.db.oracle.utils.db.SqlObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: Chunliang.Han
 * Date: 13-3-1
 * Time: 下午3:06
 */
@Component
public class OraclePreviewServiceImpl implements OraclePreviewService {

    @Autowired
    private LasteventRepository lasteventRepository;
    @Autowired
    private DBUtil4Monitor dbUtil4Monitor;
    @Autowired
    private InfoRepository infoRepository;;

    @Override
    public EventInfoModel[] viewConnectInfo(String monitorId) {
        Long time = System.currentTimeMillis();
        Date end = new Date(time);
        Date start = new Date(time - 3600 * 1000);
        List<Lastevent> activeConnectList = lasteventRepository.findLastEventList(monitorId, start, end);
        EventInfoModel[] eventInfoModel = new EventInfoModel[2];
        eventInfoModel[0]  = new EventInfoModel();
        eventInfoModel[1]  = new EventInfoModel();
        eventInfoModel[0].setStartTime(start.getTime() + "");
        eventInfoModel[0].setEndTime(end.getTime() + "");
        eventInfoModel[0].setEventName("连接时间");
        eventInfoModel[1].setStartTime(start.getTime() + "");
        eventInfoModel[1].setEndTime(end.getTime() + "");
        eventInfoModel[1].setEventName("用户数");
        if (activeConnectList == null || activeConnectList.size() == 0) {
            return eventInfoModel;
        } else {
            double connect = activeConnectList.get(activeConnectList.size() - 1).getConnectTime();
            double active = activeConnectList.get(activeConnectList.size() - 1).getActiveCount();
            eventInfoModel[0].setEventValue(connect + " ms");
            eventInfoModel[1].setEventValue(active + "");
            int size = activeConnectList.size();

            //连接时间points
            List<Point> connectTimePoints = new ArrayList<Point>();
            //用户数points
            List<Point> activeCountPoints = new ArrayList<Point>();
            //"yyyy-MM-dd HH:mm:ss E"
            SimpleDateFormat sdf2 = new SimpleDateFormat("E,dd日-MM月-yyyy年 HH:mm");
            for (int i = 0; i < size; i++) {
                Lastevent event = activeConnectList.get(i);

                Point connectTimePoint = new Point();
                connectTimePoint.setxAxis(event.getRecordTime());
                connectTimePoint.setyAxis(Integer.parseInt(event.getConnectTime() + ""));
                connectTimePoint.setDescription("连接时间" + "(" + sdf2.format(event.getRecordTime()) + " ," + event.getConnectTime() + ")");

                connectTimePoints.add(connectTimePoint);

                Point activeCountPoint = new Point();
                activeCountPoint.setxAxis(event.getRecordTime());
                activeCountPoint.setyAxis(Integer.parseInt(event.getActiveCount() + ""));
                activeCountPoint.setDescription("用户数" + "(" + sdf2.format(event.getRecordTime()) + " ," + event.getActiveCount() + ")");

                activeCountPoints.add(activeCountPoint);
            }
            eventInfoModel[0].setPoints(connectTimePoints);
            eventInfoModel[1].setPoints(activeCountPoints);
        }
        return eventInfoModel;
    }

    @Override
    public OracleDetailModel viewDbDetail(String monitorId) {
    	Info info = infoRepository.findOne(monitorId);
        OracleDetailModel oracleDetailModel = new OracleDetailModel();
        boolean canConnect = dbUtil4Monitor.canConnect(info);
        if(canConnect){
            DBUtil dbutil = dbUtil4Monitor.getDBUtil(monitorId);
            String sql = OracleMonitorSql.dbInfo;
            List<Map<String, String>> rsList = dbutil.queryStrMaps(SqlObj.newInstance(sql));
            Map<String, String> rsObj = rsList.get(0);
            // CREATED,OPEN_MODE,LOG_MODE
            String created = rsObj.get("CREATED");
            String openMode = rsObj.get("OPEN_MODE");
            String logMode = rsObj.get("LOG_MODE");
            Long connectTime =  dbUtil4Monitor.connectTime(info);

            String sql1 = OracleMonitorSql.activeCount;
            List<Map<String, Object>> rsList1 = dbutil.queryObjMaps(SqlObj
                    .newInstance(sql1));
            int active =   ((BigDecimal)rsList1.get(0).get("active") ).intValue();

            oracleDetailModel.setDbCreateTime(created);
            oracleDetailModel.setLogType(logMode);
            oracleDetailModel.setOpenType(openMode);
            oracleDetailModel.setConnectTime(connectTime+"");
            oracleDetailModel.setActiveCount(active+"");
        } else {
            oracleDetailModel.setDbCreateTime("");
            oracleDetailModel.setLogType("");
            oracleDetailModel.setOpenType("");
            oracleDetailModel.setConnectTime("");
            oracleDetailModel.setActiveCount("");
        }
        return oracleDetailModel;
    }
}
