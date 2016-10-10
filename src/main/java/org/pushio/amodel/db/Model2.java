package org.pushio.amodel.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class Model2 implements CONST{
	
	
	
	public Map modelInfo = Collections.EMPTY_MAP;
	private String modelName = null;
	private String findStr = null;
	private String selectStr = null;
	private Map myModelInfo = Collections.EMPTY_MAP;
	private String tableName = null;
	private Map idObject = Collections.EMPTY_MAP;
	private Map fieldObject = Collections.EMPTY_MAP;
	private DataSource ds = null;
//	private String selectFieldName = "";
//	private String selectFromModelName = "";

	public Model2(Map modelInfo, String modelName, DataSource ds){
		this.ds  = ds;
		this.modelInfo = modelInfo;
		this.modelName  = modelName;
		
		this.myModelInfo  =  (Map)modelInfo.get(modelName);
		
		this.tableName  = (String)this.myModelInfo.get(TABLE_NAME_MAPNAME);
		this.idObject = (Map)this.myModelInfo.get(ID_MAPNAME);
		this.fieldObject = (Map)this.myModelInfo.get(FIELD_MAPNAME);
		
		
//		this.selectFieldName  =  this.getSQLHasSelectFieldName();

	}
	
	public Model2 find(String findStr){
		this.findStr  = findStr;
		return this;
	}
	public Model2 select(String selectStr){
		this.selectStr  = selectStr;
		return this;
	}
	
	public List data(){
		List result = new LinkedList();
		
		if(this.myModelInfo == null){
			return null;
		}
		
		
		if(tableName == null){
			return null;
		}
		
		//生成SQL
		String sql = getSQL();
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			 PreparedStatement preStat = conn.prepareStatement(sql);
			 ResultSet rs = preStat.executeQuery();
			//通过查询结果生成结果对象
			 while(rs.next()){
				 Map row = new HashMap<Object,Object>();
				 //准备组装
				 //取出id的值
				 String idNameInData = this.modelName + "." + this.idObject.get(ID_NAME_MAPNAME);
				 Object idValue = rs.getObject(idNameInData);
				 row.put(this.idObject.get(ID_NAME_MAPNAME), idValue);
				 
				 if (null == this.fieldObject && this.fieldObject.size() > 0) {// 存在多列
						Set<Entry> fieldObjects = fieldObject.entrySet();
						for (Entry fieldNameEntry : fieldObjects) {
							String field = (String) fieldNameEntry.getKey();
							Map fieldObject = (Map) fieldNameEntry.getValue();
							String fieldName = (String) fieldObject.get(FIELD_NAME_MAPNAME);
							String type = (String) fieldObject.get(FIELD_TYPE_MAPNAME);
							String refModel = (String) fieldObject.get(FIELD_REFMODEL_MAPNAME);

							// 判断是否对象,非对象直接查询
							if ((!TYPE.OBJ.equals(type)) || (!TYPE.OBJS.equals(type))) {
								String fieldNameInData = this.modelName + "." +field;
								Object fieldValue = rs.getObject(fieldNameInData);
								row.put(field, fieldValue);

							} else if ((TYPE.OBJ.equals(type)) || (TYPE.OBJS.equals(type))) {
								//TODO 引用对象实现,暂时未开发,应交由引用对象处理
							}

						}
					}
				 
				 
			 }
			     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(conn);
		}
		
		
		
		
		return result;
	}
	/**
	 * TODO 未完成,暂时使用private
	 * @return
	 */
	
	private Map dataMap(){
		Map result = new HashMap();
		
		if(myModelInfo == null){
			return null;
		}
		
		
		
		return result;
	}

	public String getSQL() {
		String sql = "";
		
		
		
		String sel = "select " + this.getSQLHasSelectFieldName();
		
		String from = " from " + this.getSQLHasFromModelName();
		
		String where = " where " + this.getSQLHasFromWhereCtx();
		
		sql = sel + from + where; 
		return sql;
	}

	

	public String getSQLHasSelectFieldName() {
		String selectField = this.modelName + "." + this.idObject.get(ID_FIELD_NAME_MAPNAME) + " "
				 +this.modelName + "." + this.idObject.get(ID_NAME_MAPNAME) + " ";

		if (null == this.fieldObject && this.fieldObject.size() > 0) {// 存在多列
			Set<Entry> fieldObjects = fieldObject.entrySet();
			for (Entry fieldNameEntry : fieldObjects) {
				String field = (String) fieldNameEntry.getKey();
				Map fieldObject = (Map) fieldNameEntry.getValue();
				String fieldName = (String) fieldObject.get(FIELD_NAME_MAPNAME);
				String type = (String) fieldObject.get(FIELD_TYPE_MAPNAME);
				String refModelName = (String) fieldObject.get(FIELD_REFMODEL_MAPNAME);

				// 判断是否对象,非对象直接查询
				if ((!TYPE.OBJ.equals(type)) || (!TYPE.OBJS.equals(type))) {
					selectField = selectField + ", " + this.modelName + "." + fieldName + " " 
													+ this.modelName + "." +field;

				} else if ((TYPE.OBJ.equals(type)) || (TYPE.OBJS.equals(type))) {
					//TODO 引用对象实现,暂时未开发,应交由引用对象处理
				}

			}
		}

		return selectField;
	}
	
	public String getSQLHasFromModelName(){
		String fromModelName = this.tableName + " " + this.modelName;
		
		return fromModelName;
	}
	
	private String getSQLHasFromWhereCtx() {
		String whereCtx = "1=1";
		
		return whereCtx;
	}
	
	
}
