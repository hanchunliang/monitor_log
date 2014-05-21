package com.sinosoft.one.monitor.db.oracle.utils.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnUtil {
	
	private String DRIVER;
	private String URL ;
	private String USER ;
	private String PASSWORD ;
	
	
	private  Map<Thread, Connection> localConnPool = new HashMap<Thread, Connection>();

	public ConnUtil(String driver,String url,String user,String password) {
		DRIVER = driver;
		URL = url;
		USER = user;
		PASSWORD = password;
		ClassLoaderUtil.loadClass(DRIVER);
	}
	/**
	 * 获取不到Connection时返回null
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Connection conn = localConnPool.get(Thread.currentThread());
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				localConnPool.put(Thread.currentThread(), conn);
			}
		} catch (SQLException e1) {
			// 记录日志
			e1.printStackTrace();
		}
		return conn;
	}
	
	public void closeConnection(){
		close(getConnection());
		localConnPool = new HashMap<Thread, Connection>();
	}
	
	private void close(Connection conn){
		try {
			if(conn!=null&&!conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// 记录日志
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(null, "d");
		System.out.println(map.get(null));
	}
}
