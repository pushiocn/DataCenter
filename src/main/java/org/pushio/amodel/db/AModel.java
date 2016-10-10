package org.pushio.amodel.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.support.JdbcUtils;

public class AModel {
	public static final String DB_CONF_URL_NAME = "url";
	public static final String DB_CONF_PASSWORD_NAME = "password";
	public static final String DB_CONF_USERNAME_NAME = "username";
	
	private Map<String,String> conf = Collections.EMPTY_MAP;
	private DataSource ds = null;
	
	private Map<String,Object> modelInfo = new HashMap<String,Object>();
	
	public AModel(Map<String,String> conf){
		this.conf = conf;
	}
	
	public AModel(DataSource ds ){
		this.ds  = ds;
	}
	
	public List<Object> query(String sql){
		List<Object> result = new LinkedList<Object>();
		
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			 PreparedStatement preStat = conn.prepareStatement(sql);
			 ResultSet rs = preStat.executeQuery();
			 ResultSetMetaData meta = rs.getMetaData();
			 Integer ix = 0;
			 while(rs.next()){
				 Map row = new HashMap<String,Object>();
				 int colCnt = meta.getColumnCount();
				 for(int i = 1; i <= colCnt; ++i){
					 String colName = meta.getColumnName(i);
					 Object value = rs.getObject(i);
					 row.put(colName, value);
				 }
				 ix++;
				 
				 result.add(row);
			 }
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(conn);
		}
		
		return result ;
	}
	
	/*
	 * 使用方式
	 * 如SQL语句如下：
	 * select * from tb_foo where param1=:param1 and param2=:param2
	 * 传入参数：{param1:'var1',param2:'var2'}
	 */
	public List<Object> qy(String sqlOrg,Map<Object,Object> param){
		List<Object> result = new LinkedList<Object>();
		
		String sql = translteSqlOrgToHasValue(sqlOrg, param);
		
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			 PreparedStatement preStat = conn.prepareStatement(sql);
			 
			
			 
			 ResultSet rs = preStat.executeQuery();
			 ResultSetMetaData meta = rs.getMetaData();
			 Integer ix = 0;
			 while(rs.next()){
				 Map row = new HashMap<String,Object>();
				 int colCnt = meta.getColumnCount();
				 for(int i = 1; i <= colCnt; ++i){
					 String colName = meta.getColumnName(i);
					 Object value = rs.getObject(i);
					 row.put(colName, value);
				 }
				 ix++;
				 
				 result.add(row);
			 }
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(conn);
		}
		
		
		
		return result;
	}
	
	/*
	 * 使用方式
	 * 如SQL语句如下：
	 * update tb_foo set a='b' where param1=:param1 and param2=:param2
	 * 传入参数：{param1:'var1',param2:'var2'}
	 */
	public int up(String sqlOrg,Map<Object,Object> param){
		int update_cnt = 0;
		
		String sql = translteSqlOrgToHasValue(sqlOrg, param);
		
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			 PreparedStatement preStat = conn.prepareStatement(sql);
			 
			
			 
			 update_cnt = preStat.executeUpdate();
			
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(conn);
		}
		
		
		
		return update_cnt;
	}
	
	/*
	 * 使用方式
	 * 如SQL语句如下：
	 * select * from tb_foo where param1=:param1 and param2=:param2
	 * 传入参数：{param1:'var1',param2:2}
	 * 返回：
	 * select * from tb_foo where param1='var1' and param2=2
	 */
	public static String translteSqlOrgToHasValue(String sqlOrg, Map<Object, Object> param) {
		String sqlRs = null;
		
		Set<Entry<Object, Object>> paramEntrys = param.entrySet();
		
		for(Entry<Object, Object> paramEntry : paramEntrys){
			String paramName = (String)paramEntry.getKey();
			Object paramValue = paramEntry.getValue();
			String valueInSql = "";
			if(paramValue instanceof Number){
				valueInSql = paramValue.toString();
			}else{
				valueInSql = "'" + paramValue.toString() + "'";
			}
			
			sqlOrg = sqlOrg.replace(":" + paramName, valueInSql);
		}
		//如果还有参数，则直接置换成空字符串('')
		int ixOfParam = -1;
		while((ixOfParam = sqlOrg.indexOf(":")) > 0){
			String subSql = sqlOrg.substring(ixOfParam);
			int paramEndIx = subSql.indexOf(" ");
			if(paramEndIx < 0){
				paramEndIx = subSql.length();
			}
			String paramName = subSql.substring(0, paramEndIx);
			
			sqlOrg = sqlOrg.replace(paramName, "''");
		}
		sqlRs = sqlOrg;
		
		return sqlRs;
	}
	
	public Map queryToMap(String sql){
		Map result = new HashMap<String,Object>(); 
		
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			 PreparedStatement preStat = conn.prepareStatement(sql);
			 ResultSet rs = preStat.executeQuery();
			 ResultSetMetaData meta = rs.getMetaData();
			 Integer ix = 0;
			 while(rs.next()){
				 Map row = new HashMap<String,Object>();
				 int colCnt = meta.getColumnCount();
				 for(int i = 1; i <= colCnt; ++i){
					 String colName = meta.getColumnName(i);
					 Object value = rs.getObject(i);
					 row.put(colName, value);
				 }
				 ix++;
				 
				 result.put(ix.toString(), row);
			 }
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(conn);
		}
		
		return result ;
	}
	
	public Integer update(String sql){
		Integer updateCnt = 0;
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			PreparedStatement preStat = conn.prepareStatement(sql);
			
			updateCnt = preStat.executeUpdate();
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(conn);
		}
		
		return updateCnt;
	}
	
	public Model getModel(String modelName){
		return new Model(this.modelInfo, modelName, this.ds);
	}
	
	public Model model(String modelName){
		return this.getModel(modelName);
	}
	

	public static void main(String[] args) {
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("param1", "abc");
		params.put("param2", new Integer(1));
//		params.put("param3", "");
		String sqlOrg = "select * from test where param1=:param1 and param2=:param2 and param3=:param3";
		String sqlReal = AModel.translteSqlOrgToHasValue(sqlOrg , params);
		
		System.out.println(sqlReal);
	}
	
	public String getDbUrl(){
		return conf.get(DB_CONF_URL_NAME);
	}

	public void setDbUrl(String url) {
		this.conf.put(DB_CONF_URL_NAME, url);
	}

	public String getUserName() {
		return conf.get(DB_CONF_USERNAME_NAME);
	}

	public void setUserName(String userName) {
		this.conf.put(DB_CONF_USERNAME_NAME, userName);
	}

	public String getPassword() {
		return conf.get(DB_CONF_PASSWORD_NAME);
	}

	public void setPassword(String passsWord) {
		this.conf.put(DB_CONF_PASSWORD_NAME, passsWord);
	}

	public Map<String, String> getConf() {
		return conf;
	}

	public void setConf(Map<String, String> conf) {
		this.conf = conf;
	}

	public DataSource getDs() {
		return ds;
	} 

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public Map<String, Object> getModelInfo() {
		return modelInfo;
	}

	public void setModelInfo(Map<String, Object> modelInfo) {
		this.modelInfo = modelInfo;
	}
	
	

}
