/**
 * 洋航App
 */



$argos.$service = {
		serviceList : ['dataSource',//默认的数据源对象
		               'amodel',//数据库服务对象
		               'kaptchaProducer'//验证码服务对象
		               ],
		desc : '在serviceList中填入服务名，argos则会注入服务对象,需要更新时，调用flushService.在$argos.InitArgos方法时会调用此方法.'	
};


var initParam = {
		configReneder : function(conf){
			print('initParam configReneder...');
		}
};

$argos.initArgos(initParam);

/*
 * 自有常量集合
 */
$argos.C = {
		
}


/*
 * 自有服务S对象
 */
$argos.S = {
		
}

//$argos.$core.run('test.js');

$argos.$core.run('util.js');
$argos.$core.run('account.js');
$argos.$core.run('login.js');
$argos.$core.run('areport.js');
$argos.$core.run('areport_test.js');





