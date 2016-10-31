$argos.S.areport = {
		operaList : ['>','<','=','like','>=','<=','is not null', '!='],
		connTypeList : ['AND','OR'],
		algs : ['sum','count','avg']
};
	
$argos.S.areport.getAllDim = function(param,req,resp){//取出所有维度项
	return null;
};

$argos.S.areport.getAllMeasure = function(param,req,resp){//取出所有量度项
	return null;
};
$argos.S.areport.getOperaList = function(){
	return this.operaList;
};
$argos.S.areport.getConnTypeList = function(){
	return this.connTypeList;
};
$argos.S.areport.getAlgList = function(){
	return this.connTypeList;
};
$argos.S.areport.getSQLByReportConf = function(reportConf){
	var sql = "";
	
	if(!reportConf['source']){
		return null;
	}
	
	var select = "SELECT ";
	var from = " FROM ";
	var where = " WHERE 1=1 ";
	var groupBy = " GROUP BY ";
	var orderBy = " ORDER BY ";
	
	//制作sql
	
	//先处理select段,输出维度
	var sql_dim_f = "";
	var sql_dim_groupby = "";
	var dims_cnt = 0;
	for(var groupByKey in reportConf.groupBys){
		var dim = reportConf.groupBys[groupByKey];
		sql_dim_f = sql_dim_f + dim + ",";
		
		sql_dim_groupby = sql_dim_groupby + dim;
		++dims_cnt;
		if(dims_cnt < reportConf.groupBys.length){
			sql_dim_groupby = sql_dim_groupby + ',';
		}
		
	}
	groupBy = groupBy + sql_dim_groupby;
	
	//先处理select段,输出量度
	var sql_measure_f = "";
	for(var measuresKey in reportConf.measures){
		var measure = reportConf.measures[measuresKey];
		var algo = measure.algo;
		var measureName = measure.name;
		sql_measure_f = sql_measure_f + algo + "(" + measureName + ") AS " + measureName + "_" + algo + ",";
		
	}
	select = select + sql_dim_f  + sql_measure_f + "0"; 
	
	//处理from
	from = from + reportConf.source.tableName + " "+ reportConf.source.name;
	
	//处理where
	var sql_where_f = "";
	for(var whereKey in reportConf.wheres){
		var where_sub = reportConf.wheres[whereKey];
		sql_where_f = sql_where_f + " " + where_sub.conType + " " + where_sub.fieldName + " " +where_sub.opera + " " +
									(where_sub.valueType === 'string'?"'" + where_sub.value+"'":
										where_sub.value);
	}
	where = where  + sql_where_f;
	
	//处理order by
	var sql_orderby_f = "";
	var orders_cnt = 0;
	for(var orderByKey in reportConf.orderBys){
		var orderBy_sub = reportConf.orderBys[orderByKey];
		sql_orderby_f = sql_orderby_f + orderBy_sub ;
		
		++orders_cnt;
		if(orders_cnt < reportConf.orderBys.length){
			sql_orderby_f = sql_orderby_f + ',';
		}
	};
	orderBy = orderBy + sql_orderby_f; 
	
	//组合所有子字语句
	sql = select + from + where + groupBy + orderBy;
	return sql;
};
/*
 * 默认统计：根据传入的报表信息，生成统计结果并返回
 */
$argos.S.areport.calcDefaultReportData = function(param,req,resp){
	var reportConfFromView = param;
	
	//需要填充完整的reportConf信息
	var reportConf  = this.fullReportConfFromView(reportConfFromView);
	
	var sql = this.getSQLByReportConf(reportConf);
	
	var amodel = $argos.$service.amodel;
	var result = amodel.query(sql);
	
	reportConf.result = result;
	
	return reportConf;
	
}
/**
 * 前端传入的配置信息是用户提交的参数，需要后端将具体的数据源对象填入.
 */
$argos.S.areport.fullReportConfFromView = function(reportConfFromView){
	
	
	if(!reportConfFromView){
		return reportConfFromView;
	}
	
	//TODO 验证与填入数据源对象,从数据库中查出数据源
	if(!reportConfFromView.sourceId){
		reportConfFromView.source = this.defaultReportDataSourceInfo;
	}else{
		reportConfFromView.source = 
			this.getReportDataSourceById(reportConfFromView.sourceId);
	}
	 	
	
	//不需要管理conf在数据库存储情况
	//var reportConfFromDb = this.getReportConfFromDbByView(reportConfFromView);
	
	return reportConfFromView;
};
$argos.S.areport.getReportDataSourceById = function(sourceId){
	
	var amodel = $argos.$service.amodel;
	var rds = 
		amodel.getOne("select id,name,tableName " +
				"from tb_report_datasource rd where rd.id="+sourceId);
	
	/*
	 * rds 对象结构
	 * {
	 * 		id:1,
	 * 		name : "测试数据源",
	 * 		tableName : "test" //实际表格加入rds_前缀，此示例为rds_test
	 * }
	 */
	
	return rds;
	
};
$argos.S.areport.getReportConfFromDbByView = function(reportConfFromView){
	if(!reportConfFromView){
		return reportConfFromView;
	}
	
	var reportId = reportConfFromView.id;
	
	if(!reportId){
		return reportConfFromView;
	}
	var amodel = $argos.$service.amodel;        
	
	
}

