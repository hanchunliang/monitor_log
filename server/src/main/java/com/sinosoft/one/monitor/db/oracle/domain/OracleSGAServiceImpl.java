package com.sinosoft.one.monitor.db.oracle.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.db.oracle.model.EventInfoModel;
import com.sinosoft.one.monitor.db.oracle.model.Lastevent;
import com.sinosoft.one.monitor.db.oracle.model.OracleSGAHitRateModel;
import com.sinosoft.one.monitor.db.oracle.model.OracleSGAModel;
import com.sinosoft.one.monitor.db.oracle.model.SGAStateModel;
import com.sinosoft.one.monitor.db.oracle.monitorSql.OracleMonitorSql;
import com.sinosoft.one.monitor.db.oracle.repository.LasteventRepository;
import com.sinosoft.one.monitor.db.oracle.utils.DBUtil4Monitor;
import com.sinosoft.one.monitor.db.oracle.utils.db.DBUtil;
import com.sinosoft.one.monitor.db.oracle.utils.db.SqlObj;

/**
 * User: Chunliang.Han
 * Date: 13-3-1
 * Time: 下午6:29
 */
@Component
public class OracleSGAServiceImpl implements OracleSGAService {
    @Autowired
    private LasteventRepository lasteventRepository;
    @Autowired
    private DBUtil4Monitor dbUtil4Monitor;
    private Logger logger = LoggerFactory.getLogger(OracleSGAServiceImpl.class);
    @Override
    public OracleSGAModel viewSGAInfo(String monitorId) {
        OracleSGAModel oracleSGAModel = new OracleSGAModel();
        if(dbUtil4Monitor.canConnect(monitorId)){
            DBUtil dbutil = dbUtil4Monitor.getDBUtil(monitorId);
            String sql1 = OracleMonitorSql.sgaInfo1;
            String sql2 = OracleMonitorSql.sgaInfo2;
            List<Map<String, String>> rsList1 = dbutil.queryStrMaps(SqlObj.newInstance(sql1));
            //'Fixed SGA Size','Redo Buffers','Buffer Cache Size','Shared Pool Size','Large Pool Size','Java Pool Size'
            String fixedSGASize = rsList1.get(0).get("bytes");
            String redoBuffers = rsList1.get(1).get("bytes");
            String bufferCacheSize = rsList1.get(2).get("bytes");
            String sharedPoolSize = rsList1.get(3).get("bytes");
            String largePoolSize = rsList1.get(4).get("bytes");
            String javaPoolSize = rsList1.get(5).get("bytes");
            List<Map<String, String>> rsList2 = dbutil.queryStrMaps(SqlObj.newInstance(sql2));
            // 'sql area' ,'library cache','sga dev dict ;
            String sqlArea = "";
            String libCache = "";
            String sgaDevDict = "";
            try{
                sqlArea = rsList2.get(0).get("bytes");
                libCache = rsList2.get(1).get("bytes");
                sgaDevDict = rsList2.get(2).get("bytes");
            }catch (Exception e){
                logger.error("该数据库版本不兼容SGA信息的查询");
            }
            oracleSGAModel.setBufferCacheSize(bufferCacheSize);
            oracleSGAModel.setDictSize(sgaDevDict);
            oracleSGAModel.setFixedSGASize(fixedSGASize);
            oracleSGAModel.setJavaPoolSize(javaPoolSize);
            oracleSGAModel.setRedoLogCacheSize(redoBuffers);
            oracleSGAModel.setSqlAreaSize(sqlArea);
            oracleSGAModel.setLargePoolSize(largePoolSize);
            oracleSGAModel.setLibCacheSize(libCache);
            oracleSGAModel.setSharePoolSize(sharedPoolSize);
        } else {
            oracleSGAModel.setBufferCacheSize("");
            oracleSGAModel.setDictSize("");
            oracleSGAModel.setFixedSGASize("");
            oracleSGAModel.setJavaPoolSize("");
            oracleSGAModel.setRedoLogCacheSize("");
            oracleSGAModel.setSqlAreaSize("");
            oracleSGAModel.setLargePoolSize("");
            oracleSGAModel.setLibCacheSize("");
            oracleSGAModel.setSharePoolSize("");
        }

        return oracleSGAModel;
    }

    @Override
    public SGAStateModel viewSGAStateInfo(String monitorId) {
        SGAStateModel sgaStateModel = new SGAStateModel();
        if(dbUtil4Monitor.canConnect(monitorId)){
            DBUtil dbutil = dbUtil4Monitor.getDBUtil(monitorId);
            String sql1 = OracleMonitorSql.bufferRatio;
            String sql2 = OracleMonitorSql.dictionaryRatio;
            String sql3 = OracleMonitorSql.libraryRatio;
            String sql4 = OracleMonitorSql.sgaFreeMemory;
            List<Map<String, Object>> rsList1 = dbutil.queryObjMaps(SqlObj.newInstance(sql1));
            List<Map<String, Object>> rsList2 = dbutil.queryObjMaps(SqlObj.newInstance(sql2));
            List<Map<String, Object>> rsList3 = dbutil.queryObjMaps(SqlObj.newInstance(sql3));
            List<Map<String, Object>> rsList4 = dbutil.queryObjMaps(SqlObj.newInstance(sql4));
            String rate1 = ((BigDecimal)rsList1.get(0).get("Hit Ratio")).movePointRight(2).toString().substring(0,2);
            String rate2 = ((BigDecimal)rsList2.get(0).get("dictRatio")).movePointRight(2).toString().substring(0,2);
            String rate3 = ((BigDecimal)rsList3.get(0).get("libHitRatio")).movePointRight(2).toString().substring(0,2);
            String rate4 = ((BigDecimal)rsList4.get(0).get("free memory")).movePointRight(2).toString().substring(0,2);
            sgaStateModel.setBufferHitRate(rate1);
            sgaStateModel.setDictHitRate(rate2);
            sgaStateModel.setLibHitRate(rate3);
            sgaStateModel.setUnusedCache(rate4);
        } else {
            sgaStateModel.setBufferHitRate("");
            sgaStateModel.setDictHitRate("");
            sgaStateModel.setLibHitRate("");
            sgaStateModel.setUnusedCache("");
        }
        return sgaStateModel;
    }

    @Override
    public EventInfoModel viewHitRateStaInfo(String monitorId) {
        EventInfoModel eventInfoModel = new EventInfoModel();
        eventInfoModel.setTitle("SGA的指标");
        SimpleDateFormat sdf = new SimpleDateFormat("HH：mm");
        Long time = System.currentTimeMillis();
        Date end = new Date(time);
        Date start = new Date(time - 3600 * 1000);
        eventInfoModel.setStartTime(start.getTime() + "");
        eventInfoModel.setEndTime(end.getTime() + "");
        eventInfoModel.setEventName("连接时间");

        List<Lastevent> lasteventList = lasteventRepository.findLastEventList(monitorId, start, end);

        if (lasteventList == null || lasteventList.size() == 0) {
            return eventInfoModel;
        } else {
            List<OracleSGAHitRateModel> oracleSGAHitRateModelList = new ArrayList<OracleSGAHitRateModel>();
            for (Lastevent lastevent : lasteventList) {
                OracleSGAHitRateModel oracleSGAHitRateModel = new OracleSGAHitRateModel();
                oracleSGAHitRateModel.setBufferHitRate(lastevent.getBufferHitRate() + "");
                oracleSGAHitRateModel.setDictHitRate(lastevent.getDickHitRate() + "");
                oracleSGAHitRateModel.setLibHitRate(lastevent.getBufferLibHitRate() + "");

                oracleSGAHitRateModel.setStartTime(start.getTime() + "");
                oracleSGAHitRateModel.setEndTime(end.getTime() + "");
                String recordTime = sdf.format(lastevent.getRecordTime());
                oracleSGAHitRateModel.setRecordTime(recordTime);

                oracleSGAHitRateModelList.add(oracleSGAHitRateModel);
            }
            eventInfoModel.setSgaHitRateModels(oracleSGAHitRateModelList);
        }
        return eventInfoModel;
    }
}
