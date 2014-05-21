package com.sinosoft.one.monitor.db.oracle.utils.db;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;




public final class DBUtil {
	private ConnUtil connUtil;
	private DBUtil(){}
	public static DBUtil getInstance(ConnUtil connUtil){
		DBUtil dbutil = new DBUtil();
		dbutil.connUtil = connUtil;
    	return dbutil;
    }
	private Connection getConn() {
		return connUtil.getConnection();
	}
	private void closeConn(){
		connUtil.closeConnection();
	}
	/**
	 * 获取不到PreparedStatement时返回null
	 * 
	 * @return
	 */
	private PreparedStatement getPstm(SqlObj jsql) {
		
		PreparedStatement pstm = null;
		try {
			Connection conn = getConn();
			if(conn != null){
				pstm = conn.prepareStatement(jsql.toString());
				if (jsql.getParams() != null && jsql.getParams().size() > 0) {
					Map<Integer, Object> params = jsql.getParams();
					Set<Integer> keySet = params.keySet();
					for (Integer key : keySet) {
						pstm.setObject(key, params.get(key));
					}
				}
			}
		} catch (SQLException e) {
			// 记录日志
			e.printStackTrace();
		}
		return pstm;
	}

	private void closePstm(PreparedStatement pstm) {
		
		try {
			if (pstm != null) {
				pstm.close();
			}
		} catch (SQLException e) {
			// 记录日志
			e.printStackTrace();
		}finally{
			closeConn();
		}
	}
	
	private static ResultSet getRs(PreparedStatement pstm) {
		
		ResultSet rs = null;
		if (pstm != null) {
			try {
				rs = pstm.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	private void closeRs(ResultSet rs, PreparedStatement pstm) {
		
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// 记录日志
			e.printStackTrace();
		} finally {
			closePstm(pstm);
		}
	}

	private static List<Map<String, String>> toStringMaps(ResultSet rs)
			throws Exception {
		if (rs == null) {
			return null;
		}
		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		while (rs.next()) {
			Map<String, String> column = new HashMap<String, String>();
			for (int i = 1; i <= colCount; i++) {
				column.put(rsmd.getColumnLabel(i), rs.getString(i));
			}
			rsList.add(column);
		}
		return rsList;
	}

	private static List<Map<String, Object>> toObjMaps(ResultSet rs)
			throws Exception {
		if (rs == null) {
			return null;
		}
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		while (rs.next()) {
			Map<String, Object> column = new HashMap<String, Object>();
			for (int i = 1; i <= colCount; i++) {
				column.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
			rsList.add(column);
		}
		return rsList;
	}

	private static <Bean> List<Bean> toBeans(ResultSet rs, Class<Bean> clazz)
			throws Exception {
		if (rs == null || clazz == null) {
			return null;
		}
		List<Bean> ls = new ArrayList<Bean>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		while (rs.next()) {
			Bean bean = (Bean) clazz.getConstructor().newInstance();
			for (int i = 1; i <= colCount; i++) {
				Field field = bean.getClass().getDeclaredField(
						rsmd.getColumnLabel(i));
				field.setAccessible(true);
				field.set(bean, rs.getObject(i));
			}
			ls.add(bean);
		}
		return ls;
	}

	public boolean execute(SqlObj jsql) {
		boolean isOk = false;
		PreparedStatement pstm = getPstm(jsql);
		try {
			if (pstm != null) {
				isOk = pstm.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closePstm(pstm);
		}
		return isOk;
	}

	public int update(SqlObj jsql) {
		
		int count = -1;
		PreparedStatement pstm = getPstm(jsql);
		try {
			if (pstm != null) {
				count = pstm.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closePstm(pstm);
		}
		return count;
	}

	/**
	 * 此方法为预留接口，由使用正自行解析resultset并生成为想要结果集合对象形式 如果返回Null则说明有异常，正常执行返回为对象
	 * 
	 * @param jsql
	 * @return
	 */
	public <T> T queryObj(SqlObj jsql, Rs2obj<T> Rs2obj) {
		
		PreparedStatement pstm = getPstm(jsql);
		ResultSet rs = getRs(pstm);
		T o = null;
		try {
			o = Rs2obj.rs2obj(rs);
		} catch (Exception e) {
			// 记录日志
			e.printStackTrace();
		} finally {
			closeRs(rs, pstm);
		}
		return o;
	}

	public <Bean> List<Bean> queryBeans(SqlObj jsql,
			final Class<Bean> clazz) {
		
		List<Bean> beans = queryObj(jsql, new Rs2obj<List<Bean>>() {
			public List<Bean> rs2obj(ResultSet rs) throws Exception {
				return toBeans(rs, clazz);
			}
		});
		return beans;
	}

	public List<Map<String, String>> queryStrMaps(SqlObj jsql) {
		
		List<Map<String, String>> maps = queryObj(jsql,
			new Rs2obj<List<Map<String, String>>>() {
				public List<Map<String, String>> rs2obj(ResultSet rs)
						throws Exception {
					return toStringMaps(rs);
				}
			});
		return maps;
	}

	public List<Map<String, Object>> queryObjMaps(SqlObj jsql) {
		
		List<Map<String, Object>> maps = queryObj(jsql,
			new Rs2obj<List<Map<String, Object>>>() {
				public List<Map<String, Object>> rs2obj(ResultSet rs)
						throws Exception {
					return toObjMaps(rs);
				}
			});
		return maps;
	}

	/**
	 * 用户调用DBUtils.queryObj(Jsql jsql,  Rs2obj<T> Rs2obj)方法时须实现该接口，返
	 * 回用户想要的结果集。
	 * 
	 * 该接口为预留接口，留由用户自行实现，rs2obj方法由用
	 * 户实现，用户解析ResultSet，返回自己想要的结果集
	 * 
	 * DBUtil内部通过方法queryObj(Jsql jsql,  Rs2obj<T> Rs2obj)调用Rs2obj<T>接口
	 * 实现了如下方法：
	 * List<Bean> queryBeans(Jsql jsql,final Class<Bean> clazz),
	 * List<Map<String, String>> queryStringMaps(Jsql jsql),
	 * List<Map<String, Object>> queryObjMaps(Jsql jsql)
	 * 
	 * @author Administrator
	 *
	 * @param <T>
	 */
	public interface Rs2obj<T> {
		/**
		 * 按如下规则实现：
		 * 1、该方法如果执行无误则必须返回T的对象，不可以返回null;
		 * 2、如果程序执行异常则必须返回null
		 * 3、不准在该方法内部关闭ResultSet
		 * @param rs
		 * @return
		 * @throws Exception
		 */
		abstract T rs2obj(ResultSet rs)throws Exception;
	}
	
	public static void main(String[] args) {
//		DBDriver.start(
//				"oracle.jdbc.driver.OracleDriver", 
//				"jdbc:oracle:thin:@192.168.18.151:1521:orcl", 
//				"kongyz", 
//				"kongyz", 
//				1000);
//		SqlObj jsql = SqlObj.newInstance("Select a.version as version1, b.startup_time as startupTime1 FROM Product_component_version a,v$instance b Where SUBSTR(PRODUCT,1,6)='Oracle'");	
	}
}
