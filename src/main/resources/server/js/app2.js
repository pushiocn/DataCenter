/**
 * 
 */



$argos.$service = {
		serviceList : ['dataSource','twitterService','amodel'],
		desc : '在serviceList中填入服务名，argos则会注入服务对象,需要更新时，调用flushService.在$argos.InitArgos方法时会调用此方法.'	
};


var initParam = {
		configReneder : function(conf){
			print('initParam configReneder...');
		}
};

$argos.initArgos(initParam);

$argos.listService = function(param){
	var rs = {};
	for(var serviceName in this.$service){
		var service  = this.$service[serviceName];
		print('serviceName=' + serviceName + ' service=' +service);
		rs[serviceName] = service.toString();
	}
	return rs;
}


$argos.hello = function(param){
	return 'hello ' + param.name;
} 

$argos.util = {
	test : function(param){
		return 'test' + param.str
	}
};


$argos.returnParam = function(param){
	
	return param;
	
}

$argos.sum = function(param){
	return param.a + param.b;
}

$argos.getTwitters = function(param){
	var twitters = this.$service.twitterService.findAll();
	return twitters;
}

$argos.testDb = function(param){
	var db = $argos.$Amodel();
	var datas = db.query('select * from tb_twitter');
	return datas;
}

$argos.testDb2 = function(param){
	var db = $argos.$Amodel();
	var datas = db.query('select * from tb_twitter');
	for(var data_ix in datas){
		var data = datas[data_ix];
		print('data_ix='+ data_ix + ' ctx='+ data.ctx);
	}
	return datas;
}

$argos.testModel1 = function(param){
	
	this.db.modelInfo = {
			'twitter' : {
				tableName : "tb_twitter",
				id : {
					idName : 'id',
					idFieldName : 'id',
					type : 'int'
				},
				field :{
					'ctx' : {
						fieldName : 'ctx',
						type : 'string'
					},
					'zan':{
						fieldName : 'zan',
						type : 'int'
					},
					'addUser':{
						fieldName : 'add_user_id',
						type : 'object',
						refModel : 'employee'
					},
					'comments':{
						fieldName : 'twitter_id',
						type : 'objects',
						refModel : 'twitterComment'
					},
					'addDate':{
						fieldName : 'add_date',
						type : 'date',
					}
				}
			},
			'twitterComment':{
				tableName : "tb_comment",
				id : {
					idName : 'id',
					idFieldName : 'id',
					type : 'int'
				},
				field : {
					'ctx' : {
						fieldName : 'ctx',
						type : 'string'
					}
				}
			},
			'employee' : {
				tableName : 'employee',
				id : {
					idName : 'id',
					idFieldName : 'id',
					type : 'int'
				},
				field : {
					'name':{
						fieldName : "name",
						type : 'string'
					},
					'email':{
						fieldName : "email",
						type : 'string'
					}
				}
			}
		};
	print('modelInfo='+this.db.modelInfo);
	var twitterModel = this.db.model('twitter');
	
	var twitters = twitterModel.data();
	
	return twitters;
}