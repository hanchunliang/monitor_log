package com.sinosoft.one.monitor.db.oracle.utils;

import com.sinosoft.one.monitor.db.oracle.model.Info;
import com.sinosoft.one.monitor.db.oracle.monitorSql.OracleMonitorSql;
import com.sinosoft.one.monitor.db.oracle.repository.InfoRepository;
import com.sinosoft.one.monitor.db.oracle.utils.db.ClassLoaderUtil;
import com.sinosoft.one.monitor.db.oracle.utils.db.ConnUtil;
import com.sinosoft.one.monitor.db.oracle.utils.db.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * User: Chunliang.Han
 * Date: 13-3-2
 * Time: 上午10:15
 */
@Component
public class DBUtil4Monitor {
    @Autowired
    private InfoRepository infoRepository;
    Logger logger = Logger.getLogger(this.getClass().getName());
    public DBUtil getDBUtil(String DRIVER,String URL,String USER,String PASSWORD) {
    	DBUtil dbutil = DBUtil.getInstance(new ConnUtil(DRIVER, URL, USER, PASSWORD));
    	return dbutil;
    }
    public DBUtil getDBUtil(String monitorId){
    	Info info = infoRepository.findOne(monitorId);
        String ip = info.getIpAddress();
        String port = info.getPort();
        String instanceName = info.getInstanceName();
        String username = info.getUsername();
        String password = info.getPassword();
        String driver = OracleMonitorSql.DRIVER;
        String url = "jdbc:oracle:thin:@"+ip+":"+port+":"+instanceName;
    	DBUtil dbutil = DBUtil.getInstance(new ConnUtil(driver, url, username, password));
       return dbutil;
    }
    public long connectTime(Info info){
        Date begin = new Date();
        Connection conn = getConnection(info);
        Date end = new Date();
        long connectTime = end.getTime() - begin .getTime() ;
        closeConnection(conn);
        return connectTime;
    }
    public void closeConnection(Connection conn){
        try {
            if(conn!=null&&!conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            // 记录日志
            //e.printStackTrace();
            logger.info(e.getMessage());
        }
    }



    public Connection getConnection(Info info){
        String ip = info.getIpAddress();
        String port = info.getPort();
        String instanceName = info.getInstanceName();
        String username = info.getUsername();
        String password = info.getPassword();
        String driver = OracleMonitorSql.DRIVER;
        String url = "jdbc:oracle:thin:@"+ip+":"+port+":"+instanceName;
        System.out.print(url);
        Connection conn = null;
        try {
            ClassLoaderUtil.loadClass(driver);
            DriverManager.setLoginTimeout(1);
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e1) {
            // 记录日志
            //e1.printStackTrace();
            logger.info(e1.getMessage());
        }
        return conn;
    }
    public Connection getConnection(String DRIVER,String URL,String USER,String PASSWORD){
        Connection conn = null;
        try {
            ClassLoaderUtil.loadClass(DRIVER);
            DriverManager.setLoginTimeout(1);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e1) {
            // 记录日志
            //e1.printStackTrace();
            logger.info(e1.getMessage());
        }
        return conn;
    }
    public boolean canConnect(String DRIVER,String URL,String USER,String PASSWORD){
        boolean canConnect = false;
        Connection connection = getConnection(DRIVER,URL,USER,PASSWORD);
        if(connection!=null){
            canConnect = true;
            closeConnection(connection);
        }
        return canConnect;
    }
    public boolean canConnect(Info info){
        boolean canConnect = false;
        Connection connection = getConnection(info);
        if(connection!=null){
            canConnect = true;
            closeConnection(connection);
        }
        return canConnect;
    }
    public boolean canConnect(String monitorId){
        Info info = infoRepository.findOne(monitorId);
        boolean canConnect = false;
        Connection connection = getConnection(info);
        if(connection!=null){
            canConnect = true;
            closeConnection(connection);
        }
        return canConnect;
    }
}
