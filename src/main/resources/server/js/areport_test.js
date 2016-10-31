
/*
 * 以下为测试相关代码
 */
$argos.S.areport.defaultReportDataSourceInfo = {
		name : "testDataSource",
		tableName : "test_table",
		dims :['dim1','dim2','dim3'],
		measures : ['m1','m2','m3']
};
$argos.S.areport.test1OfReportConf = {
		id : 1,
		name : 'test1',
		sourceId : 1,
		source: null,
		wheres:{
			'year':{
				fieldName  : 'dim_year',
				conType : 'and',
				opera : ">=",
				value :"2014"
			},
			'month':{
				fieldName  : 'dim_month',
				conType : 'and',
				opera : ">=",
				value :"1"
			},
			'boxtype':{
				fieldName  : 'dim_boxtype',
				conType : 'and',
				opera : "=",                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
				value :"20GP",
				valueType : 'string'
			}
		},
		groupBys : ['dim_year','dim_boxtype'],
		measures : [
			{
				name:'mea_boxcnt',
				algo : 'sum' 
			},
			{
				name:'mea_sale',
				algo : 'sum' 
			}, 
			{
				name:'mea_ben',
				algo : 'sum' 
			}
		],
		orderBys : ['dim_year','dim_boxtype'],
		info : '统计每年各柜型的柜量，销售额，利润',
};
$argos.S.areport.defaultReportConf = {
		id : 'id1',
		name : 'defaultAndTest',
		sourceId : null,
		source:$argos.S.areport.defaultReportDataSourceInfo,
		wheres:{
			'dim1':{
				fieldName  : 'dim1',
				conType : 'and',
				opera : ">",
				value :"100"
			},
			'dim2':{
				fieldName  : 'dim2',
				conType : 'and',
				opera : "<=",
				value :"1000"
			},
			'dim3':{
				fieldName  : 'dim3',
				conType : 'and',
				opera : "like",
				value :"%abc%",
				valueType : 'string'
			}
		},
		groupBys : ['dim1','dim2'],
		measures : [
			{
				name:'measure1',
				algo : 'sum' //总合
			},
			{
				name:'measure2',
				algo : 'count' //计数
			}, 
			{
				name:'measure3',
				algo : 'avg' //计数
			}
		],
		orderBys : ['dim1','dim2'],
		info : '默认的报表信息',
};

$argos.S.areport.demo1 = {
		id : 'id1',
		name : 'defaultAndTest',
		sourceId : null,
		source:$argos.S.areport.defaultReportDataSourceInfo,
		wheres:{
			'dim1':{
				fieldName  : 'dim1',
				conType : 'and',
				opera : ">",
				value :"100"
			},
			'dim2':{
				fieldName  : 'dim2',
				conType : 'and',
				opera : "<=",
				value :"1000"
			},
			'dim3':{
				fieldName  : 'dim3',
				conType : 'and',
				opera : "like",
				value :"%abc%",
				valueType : 'string'
			}
		},
		groupBys : ['dim1','dim2'],
		measures : [
			{
				name:'measure1',
				algo : 'sum' //总合
			},
			{
				name:'measure2',
				algo : 'count' //计数
			}, 
			{
				name:'measure3',
				algo : 'avg' //计数
			}
		],
		orderBys : ['dim1','dim2'],
		info : '默认的报表信息',
};


$argos.S.areport.test1 = function(param,req,resp){
	
	
	var sql = this.getSQLByReportConf(this.defaultReportConf);
	
	print('test1 sql='+sql);
	
	return sql;
}

$argos.S.areport.test2 =  function(param,req,resp){
	return this.calcDefaultReportData(this.test1OfReportConf,req,resp);
}