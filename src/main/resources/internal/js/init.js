(function(){
	
	print('argos init...');
	
	if(!$argos){
		print('init argos fail. $argos is null or undefine.');
		return;
	}
	
	var defaultService = {
//			serviceList : [],
//			desc : '在serviceList中填入服务名，argos则会注入服务对象,需要更新时，调用flushService.在$argos.InitArgos方法时会调用此方法.'
	} ;
	
	if(!$argos.$service){
		$argos.$service = defaultService;
	}
	
	
	
	
	//建立默认服务
	$argos.$core.makeDefaultService = function(){
		
		
	};
	
	$argos.$core.flushService = function(){
		print('this.$argosStart='+ this.$argosStart);
		
		if(!$argos.$service){
			return;
		}
		
		if(!$argos.$service.serviceList){
			return;
		}
		
		//开始注入服务
		for(var ix in $argos.$service.serviceList){
			var serviceName = $argos.$service.serviceList[ix];
			
			var service  = this.$argosStart.findService(serviceName);
			if(service){
				print('serviceName=' + serviceName + ' service=' + service);
				$argos.$service[serviceName] = service;
			}
			
		}
	}
	
	
	
	$argos.initArgos = function(initParam){
		//this.initArgosInJava(initParam);
		this.confParam = initParam;
		
		this.$core.makeDefaultService();
		
		this.$core.flushService();//加载应用需求服务
		
		if(initParam.configReneder){
			initParam.configReneder(null);
		}
	}

}
)();
