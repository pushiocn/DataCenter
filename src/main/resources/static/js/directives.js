app.directive(
				"yhselect",
				function($http, $q) {
					return {
						restrict : 'E',
						require : 'ngModel',
						scope : {
							dataurl : '@',
							ngModel : '=ngModel',
							multiple : '@',
							selsectconfig : '@',
							optioncontent : '@',
							optionvalue : '@',
							dataPlaceholder : '@'
						},
						replace : true,
						template : function(tElement, tAttrs) {
							var optioncontent = tAttrs.optioncontent;
							var optionvalue = tAttrs.optionvalue;
							var multiple = tAttrs.multiple;

							if (multiple != undefined) {
								// console.log($scope);
							}

							var _html = '<select ui-select2 class="populate placeholder" >'
									+ '<option>{{dataPlaceholder}}</option>'
									+ '<option ng-repeat="option in optionArray" value="{{option.'
									+ optionvalue
									+ '}}"'
									+ 'ng-selected="ngModel == option.'
									+ optionvalue
									+ '">'
									+ '{{option.'
									+ optioncontent
									+ '}}'
									+ '</option>'
									+ '</select>';

							return _html;
						},
						link : function(scope, iele, iattr) {
							$http
									.get(scope.dataurl)
									.then(
											function resolved(res) {
												scope.optionArray = res.data.data.content;
											});
						},
					}
				});

app.directive('keyvalue', function() {
	// console.info('startKeyValueDirective...');
	return {
		restrict : 'E',
		scope : false,
		link : function(scope, element, attrs) {
			//console.info('keyvalue link scope=',scope);
			for ( var i in attrs) {
				//console.info('keyvalue link attrs[i]=',attrs[i]);
				scope[i] = attrs[i];
			}
		}
	}
});

// 选择框控件器(开始):自编写的选择框中的列表需要用到这个控件器,此控制器管理表格中的数据,实现是否选中与数据输出
var DefaultSelectTableCtrl = function($scope, NgTableParams, $resource, $http,
		tableDataService) {
	if(!$scope.idName) { $scope.idName = "id"; }  // 如果ID不存在，默认是id
	// 从数组中通过id删除指定值 开始
	Array.prototype.indexOfById = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i].id == val.id)
				return i;
		}
		return -1;
	};
	Array.prototype.removeById = function(val) {
		var index = this.indexOfById(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	// 从数组中删除指定值 结束
	tableDataService.initScope($scope, NgTableParams, $resource, $http);
	var tableFilter = null;
	tableDataService.initSelectTable($scope, NgTableParams, $resource, $http,
			tableFilter);
			
	$scope.handleDataInCtrl = function(data){
		if(($scope.handleOptionData) && typeof($scope.handleOptionData)=='function'){
			data = $scope.handleOptionData(data);
		}
			var newdata = [];
			
			if(data == null || data == undefined){
				return newdata;
			}
			
			var nullItem = {
				id:-1
			};
			
			nullItem[$scope.showName]= "<空>";
//			console.info("nullItem=", nullItem);
			
			newdata.push(nullItem);
			
			for(var i in data){
				if(typeof(data[i])!='function'){
					newdata.push(data[i]);
				}
			}
			return newdata;
	}
	
	
	
	$scope.SelectDivDispalyFlag= false;//设置选择框是默认不出现的
	
	$scope.toggleSelectDivDispaly = function() {
		var tempFlag = Boolean($scope.SelectDivDispalyFlag);
		$scope.SelectDivDispalyFlag = (!tempFlag);
	}
	$scope.hiedecloseselectdiv = function() {
		$scope.SelectDivDispalyFlag = false;
	}
	

	$scope.selcectCurrentTr = function(data) {
		if (data == null && data == undefine) {
			return;
		}
		if(angular.isFunction($scope.beforeSelect)) {
			$scope.beforeSelect($scope,data);
		}
		if ($scope.mutl == 'false') {
			$scope.hiedecloseselectdiv();//单选选择后选择框消失
			if(data.id == -1){
				$scope.sltmodel = null;
				return;
			}
			$scope.sltmodel = data;
		} else {
			if (YHUtil.hasObj($scope.sltmodel)) {
			} else {
				$scope.sltmodel = [];
			}
			var isHasObj = false;
			for ( var i in $scope.sltmodel) {
				var value = $scope.sltmodel[i];
				if (value[$scope.idName] == data[$scope.idName]) {
					isHasObj = true;
					break;
				}
			}
			//console.log("开始增删前的数组是：", $scope.sltmodel);
			if (!isHasObj) {
				$scope.sltmodel.push(data);
			} else {
				//console.log("被删除的对象是：", data);
				$scope.sltmodel.removeById(data);
			}
			//console.log("完成增删后的数组是：", $scope.sltmodel);
		}
		if(angular.isFunction($scope.afterSelect)) {
			$scope.afterSelect($scope,data);
		}
	};

	$scope.isSelected = function(item) {
		if ($scope.sltmodel == undefined || $scope.sltmodel == null) {
			return false;
		}
		if ($scope.mutl != 'true') {
//			return $scope.sltmodel.id== item.id;
			return $scope.sltmodel[$scope.idName] == item[$scope.idName];
		} else {
			var isHasObj = false;
			for ( var i in $scope.sltmodel) {
				var value = $scope.sltmodel[i];
				
				if(value == null || value == undefined){
					continue;
				}
				//console.info('value=',value);
				if (value[$scope.idName] == item[$scope.idName]) {
					isHasObj = true;
					break;
				}
			}
			return isHasObj;
		}
	}
	$scope.delSelected =function(item){
		
	}
	
	$scope.$watch('dataurl',function(newvalue){
		//console.info('yhtable-select watch dataurl newvalue=',newvalue);
		tableDataService.initScope($scope, NgTableParams, $resource, $http);
		var tableFilter = null;
		tableDataService.initSelectTable($scope, NgTableParams, $resource, $http,
				tableFilter,null,$scope.localData);
	});

};
// 选择框控件器(结束):自编写的选择框中的列表需要用到这个控件器,此控制器管理表格中的数据,实现是否选中与数据输出
/*
 * 时间选择器指令(开始)
 */
app.directive('datePicker', function() {
	return {
		restrict : 'A',
		require : 'ngModel',
		scope : {
			minDate : '@',
			dateFmt : '@',
			minDate : '@',
			maxDate : '@'
		},
		link : function(scope, element, attr, ngModel) {
			element.val(ngModel.$viewValue);

			function onpicking(dp) {
				var date = dp.cal.getNewDateStr();
				scope.$apply(function() {
					ngModel.$setViewValue(date);
				});
			}
			element.bind('click', function() {
				WdatePicker({
					onpicking : onpicking,
					dateFmt : scope.dateFmt,
					minDate : scope.minDate,
					maxDate : scope.maxDate
				})
			});
		}
	};
});
/*
 * 时间选择器指令(结束)
 */


var yhselectTableCtrl = function($scope, NgTableParams, $resource, $http,
		tableDataService) {
	//console.info('yhselectTable start... $scope', $scope);
	// 从数组中通过id删除指定值 开始
	Array.prototype.indexOfById = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i].id == val.id)
				return i;
		}
		return -1;
	};
	Array.prototype.removeById = function(val) {
		var index = this.indexOfById(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	$scope[$scope.localData] = PublicService[$scope.localData];
	
	//$scope.localData = PublicService[$scope.localData];
	//console.info("yhselect-table Ctrl localData=",$scope.localData);
	
	if(!$scope.idName){
		$scope.idName = "id";
	}
	//如果配置了数据处理器,则需要编写数据处理器
	
	$scope.handleDataInCtrl = function(data){
		if(($scope.localFilter) && typeof($scope.localFilter)=='function'){
			data = $scope.localFilter(data);
		}
		if($scope.filterData){
			data = $scope.filterData(data);
		}
		return data;
	}
	
	// 从数组中删除指定值 结束
	//初始化表格开始
	tableDataService.initScope($scope, NgTableParams, $resource, $http);
	var tableFilter = null;
	tableDataService.initSelectTable($scope, NgTableParams, $resource, $http,
			tableFilter,null,$scope.localData);
	//初始化表格结束
	
	
	
//	$scope.handleDataInCtrl = function(data){
//		if(($scope.handleOptionData) && typeof($scope.handleOptionData)=='function'){
//			data = $scope.handleOptionData(data);
//		}
//		var newdata = [];
//		
//		if(data == null || data == undefined){
//			return newdata;
//		}
//		
//		var nullItem = {
//			id:-1
//		};
//		nullItem[$scope.showName]= "<空>";
//
//		newdata.push(nullItem);
//		
//		for(var i in data){
//			if(typeof(data[i])!='function'){
//				newdata.push(data[i]);
//			}
//		}
//		return newdata;
//	}
	
	
	
	
	$scope.SelectDivDispalyFlag= false;//设置选择框是默认不出现的
	
	$scope.toggleSelectDivDispaly = function() {
		var tempFlag = Boolean($scope.SelectDivDispalyFlag);
		$scope.SelectDivDispalyFlag = (!tempFlag);
	}
	$scope.hiedecloseselectdiv = function() {
		$scope.SelectDivDispalyFlag = false;
	}
	

	$scope.selcectCurrentTr = function(data) {
//		console.info('selcectCurrentTr=',data);
		if (data == null || data == undefined ) {
			return;
		}
		if($scope.beforeSelect) { 
			var bool = $scope.beforeSelect($scope,data);
			if(!bool) { return; }
		}
		$scope.sltData = data;
		
		if ($scope.mutl != 'true') {//单选
			if(data.id == -1){
				$scope.sltmodel = null;
				$scope.sltData = null;
				$scope.toggleSelectDivDispaly();
				return;
			}
			if($scope.outPutName){
				$scope.sltmodel = data[$scope.outPutName];
			}else{
				$scope.sltmodel = data;
			}
			$scope.toggleSelectDivDispaly();
		} else {
			if (YHUtil.hasObj($scope.sltmodel)) {
			} else {
				$scope.sltmodel = [];
			}
			var isHasObj = false;
			for ( var i in $scope.sltmodel) {
				var value = $scope.sltmodel[i];
				if (value[$scope.idName] == data[$scope.idName]) {
					isHasObj = true;
					break;
				}
			}
			//console.log("开始增删前的数组是：", $scope.sltmodel);
			if (!isHasObj) {
				$scope.sltmodel.push(data);
			} else {
				//console.log("被删除的对象是：", data);
				$scope.sltmodel.removeById(data);
			}
			//console.log("完成增删后的数组是：", $scope.sltmodel);
		}
		if($scope.afterSelect) { $scope.afterSelect(data); }
	};

	$scope.isSelected = function(item) {
		var result = false;
		if ($scope.sltmodel == undefined || $scope.sltmodel == null) {
			return result;
		}
		
		if ($scope.mutl != 'true') {
			if($scope.outPutName){
				result = $scope.sltmodel == item[$scope.outPutName];
			}else{
				result = $scope.sltmodel[$scope.idName] == item[$scope.idName];
			}
			
			if(result){//将所选的内容保存,以便显示
				$scope.sltData = item;
			}
		} else {
			var isHasObj = false;
			for ( var i in $scope.sltmodel) {
				var value = $scope.sltmodel[i];
				//console.info('vale=',value);
				//console.info('$scope.idName=',$scope.idName);
				if(value == null || value == undefined){
					continue;
				}
				if (value[$scope.idName] == item[$scope.idName]) {
					isHasObj = true;
					break;
				}
			}
			result =  isHasObj;
		}
		return result;
	}
	$scope.delSelected =function(item){
		console.info('delSelected item',item);
		if (item == undefined || item == null){
			return
		}
		if($scope.mutl != 'true'){
			//单选
			 $scope.sltmodel = null;
			 $scope.sltData = null;
		}else{
			//多选
			$scope.sltmodel.removeById(item);
			$scope.sltData.removeById(item);
		}
	}
	
	$scope.$watch('dataurl',function(newvalue){
		//console.info('yhtable-select watch dataurl newvalue=',newvalue);
		tableDataService.initScope($scope, NgTableParams, $resource, $http);
		var tableFilter = null;
		tableDataService.initSelectTable($scope, NgTableParams, $resource, $http,
				tableFilter,null,$scope.localData);
	});

};


app.directive(
		'yhselectTable',
		function() {
			return {
				restrict : 'E',

				scope : {
					url : '@',
					sltmodel : '=',
					dataurl :'@',
					mutl : '@',
					idName : '@',
					showName : '@',
					filterField:'=',
					showTitle:'@',
					required:'@',
					afterSelect: '=',
					paramUrlHandle:'&',
					localData:'@',
					outPutName:'@',
					filterData:'='
				},
				replace:true,
				templateUrl : basePath + '/js/function/common/yhselectTable.html',
				link : function(scope, element, attrs) {
				},
				controller : yhselectTableCtrl 
			}
});


//app.directive('dictSelect',	function() {
//			return {
//				restrict : 'E',
//				scope:{
//					dictmodel : '=',
//					typeValue : '@'
//				},
//				replace:true,
//				templateUrl : basePath + '/js/function/common/distSelect.html',
//				link : function(scope, element, attrs) {
//					
//				},
//				controller : function($scope){
//					$scope.dictFilter = function(data){
//						if(!$scope.typeValue)return;
//						var newdata = [];
//						for(var key in data){
//							if(data[key]['type'] == $scope.typeValue){					
//								newdata.push(data[key]);
//							}
//						}
//						return newdata;
//					};
//				} 
//			}
//});



app.directive('dictSelect',	function() {
	 return {
		 restrict : 'E',
		 scope:{
			 curOptionArray:'=',
			 dictmodel:"=",
			 btnStyle:"="
		 },
		 replace:true,
		 template:'<select  ng-model="dictmodel" data-am-selected  ng-options="option.theValue as option.theName for option in curOptionArray" btnStyle="{{btnStyle}}" id="demo-maxchecked"></select>',
		 link:function(scope, element, attrs) {
			 scope.curOptionArray=[];
			 for(var i in PublicService.dictionaryForSelectDirective){
				 if(PublicService.dictionaryForSelectDirective[i].typevalue==attrs.typeValue){
					 scope.curOptionArray.push({
						 theValue:PublicService.dictionaryForSelectDirective[i].id,
						 theName:PublicService.dictionaryForSelectDirective[i].name
					 });
				 }
			 }			 
			
			// 设置下拉框的参数	
			if(!attrs.btnSize){
				attrs.btnSize="sm"
			}
			if(!attrs.btnStyle){
				attrs.btnStyle="secondary"
			}			
			if(!attrs.btnWidth){
				attrs.btnWidth="148px"
			}			
			if(!attrs.maxHeight){
				attrs.maxHeight="350px"
			}			
			if(!attrs.dictmodel){
				console.log("发生致命错误：typeValue为"+attrs.typeValue的字典选择器没有设置dictmodel的值);
				return;
			}
			else{
				scope.modelName=attrs.dictmodel;
			}
			if(attrs.required&&(!scope.dictmodel)){
				attrs.btnStyle="danger";
				element.prev("span.am-input-group-label").css({
					"background-color":"#DD514C",
					"border-color":"#DD514C"
				});
			}
			var selectedParamerObj={
			    btnWidth: attrs.btnWidth,
			    btnSize: attrs.btnSize,
			    btnStyle: attrs.btnStyle,
			    maxHeight: attrs.maxHeight
			};
			
			$('#demo-maxchecked').on('checkedOverflow.selected.amui', function() {
				  alert('最多选择' + this.getAttribute('maxchecked') + '项');
			});
			
			element.on('change',function(){
				if(scope.dictmodel>0){
					element.prev("span.am-input-group-label").css({
						"background-color":"#0E90D2",
						"border-color":"#0E90D2"
					});
					element.next().find("button.am-selected-btn").css({
						"background-color":"#19A7F0",
						"border-color":"#19A7F0"
					});
				}				
			});		
			$('select option').each(function(){
				//去掉option选项显示文本的的首尾空格后判断值是否为空
				if($(this).html().replace(/(^\s*)|(\s*$)/g, "").length<1){
					//填充默认值为空时（新增是），默认值option占位问题
					$(this).html("请选择...").prop("value","0");
				}
				//调用amazeui的selected方法渲染页面
				//console.log('$(this).parent("select"):',$(this).parent("select"));
				$(this).parent("select").selected(selectedParamerObj);
			});
			
		}
	 }
});


//删除单条数据确认对话框指令  开始
app.directive('popoverConfirm', function($http,$timeout,$location) {
	return {
		restrict : 'E',
		scope : {
			delerteData:'=',
			delurl:'@'
		},
		replace:true,
		template:'<button type="button" class="am-btn am-btn-danger am-radius am-btn-xs" uib-popover-template="templateurl" popover-trigger="click"><i class="icon-trash"></i></button>',
		link : function(scope, element, attr) {
			
			console.log($location.url());
			
			
			scope.templateurl="popoverConfirm.html"	;			
			scope.toDelTableData=function(){
				scope.templateurl = "deleteResult.html";
				var deletdObj=scope.delerteData;
				$http.post(scope.delurl+"?ids="+deletdObj.id)
				.success(function(response) {					
					scope.message = response.message;
					if(response.error!=1){
						scope.className="text-danger";
					}
					else{
						scope.className="text-success";
					}
				})
				.error(function() {
					alert('连接不成功.');
				});
				var timer = $timeout(function(){
				  $(".refresh").click();			 
			  },2000);			
			}
		}
	}
});
//删除单条数据确认对话框指令  结束