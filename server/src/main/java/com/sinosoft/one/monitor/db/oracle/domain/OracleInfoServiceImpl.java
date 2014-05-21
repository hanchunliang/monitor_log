package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.db.oracle.model.Info;
import com.sinosoft.one.monitor.db.oracle.model.OracleInfoModel;
import com.sinosoft.one.monitor.db.oracle.monitorSql.OracleMonitorSql;
import com.sinosoft.one.monitor.db.oracle.repository.*;
import com.sinosoft.one.monitor.db.oracle.utils.DBUtil4Monitor;
import com.sinosoft.one.monitor.db.oracle.utils.db.DBUtil;
import com.sinosoft.one.monitor.db.oracle.utils.db.SqlObj;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: Chunliang.Han
 * Date: 13-3-1
 * Time: 上午10:03
 */
@Component
public class OracleInfoServiceImpl implements OracleInfoService {

    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private LasteventRepository lasteventRepository;
    @Autowired
    private EventStaRepository eventStaRepository;
    @Autowired
    private AvaRepository avaRepository;
    @Autowired
    private AvaStaRepository avaStaRepository;
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private DBUtil4Monitor dbUtil4Monitor ;
    @Autowired
    private OracleMonitorTask oracleMonitorTask;
    @Override
    @Transactional
    public void saveMonitor(Info info) throws Exception{

        //插入version,startTime;
        String ip = info.getIpAddress();
        String port = info.getPort();
        String instanceName = info.getInstanceName();
        String username = info.getUsername();
        String password = info.getPassword();
        String driver = OracleMonitorSql.DRIVER;
        //"jdbc:oracle:thin:@192.168.18.151:1521:orcl"
        String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + instanceName;
        DBUtil dbuUtil = dbUtil4Monitor.getDBUtil(driver, url, username, password);
        String sql = OracleMonitorSql.versionAndStartUpTime;
        List<Map<String, String>> rsList = dbuUtil.queryStrMaps(SqlObj.newInstance(sql));
        Map<String, String> rsObj = rsList.get(0);
        String version = rsObj.get("VERSIONLABLE");
        String startTime = rsObj.get("STARTUPTIME");
        info.setOracleVersion(version);
        info.setStartTime(startTime);
        info = infoRepository.save(info);
        Resource resource = new Resource();
        resource.setResourceName(info.getName());
        resource.setResourceId(info.getId());
        resource.setResourceType(ResourceType.DB.name());
        resourcesRepository.save(resource);
        oracleMonitorTask.addTask(info);
    }

    @Override
    @Transactional
    public void editMonitor(Info info) {
        Info newInfo = infoRepository.findOne(info.getId());
        newInfo.setUsername(info.getUsername());
        newInfo.setPassword(info.getPassword());
        newInfo.setInstanceName(info.getInstanceName());
        newInfo.setName(info.getName());
        newInfo.setPullInterval(info.getPullInterval());
        infoRepository.save(newInfo);
        oracleMonitorTask.updateTask(newInfo);
    }

    @Override
    @Transactional
    public void deleteMonitor(List<String> monitorIds) {

        lasteventRepository.deleteByMonitorIds(monitorIds);
        eventStaRepository.deleteByMonitorIds(monitorIds);
        avaRepository.deleteByMonitorIds(monitorIds);
        avaStaRepository.deleteByMonitorIds(monitorIds);
        resourcesRepository.deleteByMoitorIds(monitorIds);
        alarmRepository.deleteByMonitorIds(monitorIds);
        infoRepository.deleteByIds(monitorIds);
        for(int i=0;i<monitorIds.size();i++){
            Info info = new Info(monitorIds.get(i));
            oracleMonitorTask.deleteTask(info);
        }
    }

    @Override
    public OracleInfoModel getMonitorInfo(String monitorId) {
        //2013-3-1 上午11:10      yyyy年MM月dd日 HH时mm分ss秒 E
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        OracleInfoModel oracleInfoModel = new OracleInfoModel();
        Info info = infoRepository.findOne(monitorId);
        String state = avaRepository.findState(info.getId());
        //*健康状况与预警相关 ，暂时不做
        Date endTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.MINUTE, -info.getPullInterval());
        Date startTime = calendar.getTime();
        //根据报警信息接确定当前是否可用
        String[] healthyPint = new String[2];
        String healthyFlag = "1";
        StringBuilder msg = new StringBuilder();
        //"1"代表可用，"0"代表不可用
        if("0".equals(state)){
            healthyPint[0] = "3";
            healthyPint[1] = msg.append(info.getName()).append("为停止").toString();
        } else {
            List<Alarm> alarmList = alarmRepository.findAlarmByMonitorId(info.getId(), startTime, endTime);
            for (Alarm alarm : alarmList) {
                if (alarm.getSeverity() != null) {
                    msg.append(alarm.getMessage()).append("\n");
                    if (alarm.getSeverity().equals(SeverityLevel.INFO)) {
                        healthyFlag = "1";
                    } else if (alarm.getSeverity().equals(SeverityLevel.WARNING)) {
                        healthyFlag = "2";
                    } else if (alarm.getSeverity().equals(SeverityLevel.CRITICAL)) {
                        healthyFlag = "3";
                    } else if (alarm.getSeverity().equals(SeverityLevel.UNKNOW)) {
                        healthyFlag = "4";
                    }
                }
            }
            healthyPint[0] = healthyFlag;
            healthyPint[1] = msg.toString();
        }
        oracleInfoModel.setHealth(healthyPint);

        oracleInfoModel.setHostName(info.getIpAddress());
        Date lastExecTime = lasteventRepository.findLastExecTime(monitorId);
        Long pullIntervalMillis = info.getPullInterval() * 60 * 1000L;
        if(lastExecTime==null){
            oracleInfoModel.setLastExecTime("");
            oracleInfoModel.setNextExecTime(sdf.format(new Date(info.getSysTime().getTime()+pullIntervalMillis)));
        }else if(lastExecTime.getTime()+pullIntervalMillis+1000<endTime.getTime()){
            oracleInfoModel.setLastExecTime(sdf.format(lastExecTime));
            oracleInfoModel.setNextExecTime("");
        }else{
            oracleInfoModel.setLastExecTime(sdf.format(lastExecTime));
            Long nextExecTime = lastExecTime.getTime() + pullIntervalMillis;
            oracleInfoModel.setNextExecTime(sdf.format(nextExecTime));
        }
        oracleInfoModel.setMonitorName(info.getName());
        boolean canConnect = dbUtil4Monitor.canConnect(info);
        String platformName = "";
        if(canConnect){
            DBUtil dbutil = dbUtil4Monitor.getDBUtil(monitorId);
            String sql = OracleMonitorSql.osName;
            List<Map<String, String>> rsList = dbutil.queryStrMaps(SqlObj.newInstance(sql));
            Map<String, String> rsObj = rsList.get(0);
            platformName = rsObj.get("platform_name");
            //获取开机启动时间
            sql = OracleMonitorSql.versionAndStartUpTime;
            rsList = dbutil.queryStrMaps(SqlObj.newInstance(sql));
            rsObj = rsList.get(0);
            String oracleStartTime = rsObj.get("STARTUPTIME");
            info.setStartTime(oracleStartTime);
            info = infoRepository.save(info);
        }
        //*操作系统信息
        oracleInfoModel.setOs(platformName);
        oracleInfoModel.setPort(info.getPort());
        //执行SQL语句查询
        oracleInfoModel.setStartTime(info.getStartTime());
        oracleInfoModel.setVersion(info.getOracleVersion());
        return oracleInfoModel;
    }

	@Override
	public Info getInfo(String monitorId) {
		return infoRepository.findOne(monitorId);
	}
}
