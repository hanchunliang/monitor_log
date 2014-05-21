package com.sinosoft.one.monitor.db.oracle.utils.db;
import java.util.HashMap;
import java.util.Map;

public class SqlObj {
	
	private StringBuilder sql ;
	
	private Map<Integer, Object> params = new HashMap<Integer, Object>();
	
	private SqlObj() {
		this.sql=new StringBuilder();
	}
	
	private SqlObj(String sql) {
		this.sql=new StringBuilder(sql);
	}
	
	public static SqlObj newInstance(){
		return new SqlObj();
	}
	
	public static SqlObj newInstance(String sql){
		return new SqlObj(sql);
	}
	
	public SqlObj(StringBuilder sql,Map<Integer, Object> params) {
		this.sql=sql;
		this.params=params;
	}
	public SqlObj(StringBuilder sql,Object[] params) {
		this.sql=sql;
		setParams(params);
	}

	public SqlObj addPapam(Integer columm, Object value){
		params.put(columm, value);
		return this;
	}
	public SqlObj append(String sql){
		this.sql.append(sql);
		return this;
	}
	public void setParams(Map<Integer, Object> params) {
		this.params = params;
	}
	public void setParams(Object[] params) {
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				this.params.put(i+1, params[i]);
			}
		}
	}

	public Map<Integer, Object> getParams() {
		return params;
	}
	
	@Override
	public String toString() {
		return sql.toString();
	}
}
