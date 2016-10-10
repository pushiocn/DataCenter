app.provider('tableDataService', function() {
	var getPageCountAndSortingConf = function(tableFilter) { //翻页与排序配置
		if (tableFilter == undefined || tableFileter == null) {
			tableFilter = {};
		}

		return {
			page: 1, // show first page
			count: 100, // count per page
			filter: tableFilter,
			sorting: {}
		}
	};

	var getHandleDataConf = function(handleData) {
		return { //返回数据配置
			counts: [100, 200, 500],
			paginationMaxBlocks: 20,
			total: 0, // length of data
			getData: handleData //输入定义
		}
	};

	//保存处理器
	var saveHandle = function($scope,$http,operData, filterFunc) {
//		console.info('startToSave...saveUrl=', $scope.saveurl);
		$scope.operData = $.convertObject2Params(operData, filterFunc);
		//console.info('startToSave...$scope.operData=', $scope.operData);

		if($scope.validMsg){
			//清空验证信息
			for(var key in $scope.validMsg){
				var __index = key.indexOf(".");
				if(__index > -1){
					var __index_str = key.substring(0,__index);
					if(!$scope.validMsg[__index_str]){
						$scope.validMsg[__index_str] = {};
					}
					$scope.validMsg[__index_str][key.substring(__index+1)] = "";
				}else{
					$scope.validMsg[key] = "";
				}
			}
		}
		
		$http.get($scope.saveurl, {
			params: $scope.operData
		}).success(function(response) {
			if(response.errorcode == 1){
				$scope.isItemShow = function(data) {
					return false;
				}
			}		
			
			if(response.errorcode == 100){
				if(!$scope.validMsg){
					$scope.validMsg = {}; //页面验证信息保存对象
				}
				//设置验证信息
				for(var key in response.validErrors){
					var __index = key.indexOf(".");
					if(__index > -1){
						var __index_str = key.substring(0,__index);
						if(!$scope.validMsg[__index_str]){
							$scope.validMsg[__index_str] = {};
						}
						$scope.validMsg[__index_str][key.substring(__index+1)] = response.validErrors[key];
					}else{
						$scope.validMsg[key] = response.validErrors[key];
					}
				}
				return;
			}
			if (YHUtil.hasObj(response.data)) {
				$scope.isAdd = !$scope.isAdd;
				$scope.operData = response.data;
				$scope.operData.isShowEdit=false;
			}
			//刷新表格
			$scope.tableParams.reload();
			$scope.operData = response.data;
			$scope.showSaveBtn = false;
			//$scope.toggleAdd();
		}).error(function() {
			//console.info('error');
			$scope.saveMsg = '连接不成功.';
		});
	}
	
	function initSelectTable($scope, NgTableParams, $resource, $http, tableFilter, $filter, localData) {
		//初始化选择框对象
		function initCheckboxes() {
			$scope.checkboxes = {
				'checked': false,
				items: {}
			}
		}
		
		function handleData($defer, params) { //刷新数据方法
			var dataUrl = $scope.dataurl;
			//console.info('$scope.dataurl=', dataUrl);
			if (!YHUtil.hasObj(dataUrl)) {
				return undefined;
			}
			var Api = $resource(dataUrl);
			var paramInUrl = params.yhUrl();
			if($scope.paramUrlHandle){
				paramInUrl = $scope.paramUrlHandle(paramInUrl);
			}
			return Api.get(paramInUrl).$promise.then(function(respData) {
				if (respData == null || respData == undefined) {
					return;
				}
				if (respData.data == null || respData.data == undefined) {
					return;
				}
				
				if($scope.handleDataInCtrl != undefined){
					$scope.data = $scope.handleDataInCtrl(respData.data.content);
				}else{
					$scope.data = respData.data.content;
				}
				
				params.total(respData.data.totalElements);
				initCheckboxes();
				return $scope.data;
			});

		}
		
		function localDataHandle($defer, params) { //刷新数据方法
			var data = $scope[$scope.localData];
			//console.info('localDataHandle data=',data);
			if (data == null || data == undefined) {
				return;
			}
			
			//$scope.data = respData.data.content;
			if($scope.handleDataInCtrl != undefined){
				$scope.data = $scope.handleDataInCtrl(data.content);
			}else{
				$scope.data = data.content;
			}
			
			params.total(data.content.length);
			initCheckboxes();
			return $scope.data;
		}

		function initTableParams() { //表格形式配置方法
			$scope.tableParams = new NgTableParams(
				getPageCountAndSortingConf(tableFilter),
				getHandleDataConf(handleData)
			);
		}
		function initTableParamsLocalData() { //表格形式配置方法
			$scope.tableParams = new NgTableParams(
				getPageCountAndSortingConf(tableFilter),
				getHandleDataConf(localDataHandle)
			);
		}

		function initTableCheackboxs() {
			initCheckboxes();
			
			if($scope.selectAllName){
				$scope.selectAllName = "select_all"
			}
			
			// watch for check all checkbox
			$scope.$watch('checkboxes.checked', function(value) {
				angular.forEach($scope.data, function(item) {

					if (angular.isDefined(item.id)) {
						$scope.checkboxes.items[item.id] = value;
					}
				});
			});
			// watch for data checkboxes
			$scope.$watch('checkboxes.items', function(values) {
				if (!$scope.data) {
					return;
				}
				var checked = 0,
					unchecked = 0,
					total = $scope.data.length;
				angular.forEach($scope.data, function(item) {
					checked += ($scope.checkboxes.items[item.id]) || 0;
					unchecked += (!$scope.checkboxes.items[item.id]) || 0;
				});
				if ((unchecked == 0) || (checked == 0)) {
					$scope.checkboxes.checked = (checked == total);
				}
				// grayed checkbox
				angular.element(document.getElementById($scope.selectAllName)).prop("indeterminate", (checked != 0 && unchecked != 0));
			}, true);
			//checkboxes end
		}
		
		$scope.$watch('dataurl',function(values){
			$scope.tableParams.reload();
		});

		//启动ng-table参数数据
		if(localData){
			initTableParamsLocalData();
		}else{
			initTableParams();
		}
		
	}
	
	var saveTableData = function($scope, $http, myScope, index, operData, filterFunc) {
		console.info('tableService $scope', $scope);
		console.info('tableService.mySope=', this.myScope);
		if($scope.validMsg){
			//清空验证信息
			for(var key in $scope.validMsg){
				var __index = key.indexOf(".");
				if(__index > -1){
					var __index_str = key.substring(0,__index);
					if(!$scope.validMsg[__index_str]){
						$scope.validMsg[__index_str] = {};
					}
					$scope.validMsg[__index_str][key.substring(__index+1)] = "";
				}else{
					$scope.validMsg[key] = "";
				}
			}
		}
		$http.post($scope.saveurl,operData).success(function(response) {
			if(response.errorcode == 1) {
				if (YHUtil.hasObj(response.data)) {
					response.data.isShowEdit = false;		
					for(i in response.data)
					{
						$scope.item[i]=response.data[i];
					}				
				}
				if(!(myScope.data instanceof Array)) {
					myScope.data = new Array();
				}
				if(isNaN(parseInt(index))) {					
					operData.id = response.data.id;
					myScope.data.add(myScope.data.length, response.data);
				}
				else {
					if(myScope.data.length == 0) {
						myScope.data.push(response.data);
					}
					else {
						myScope.data[index] = response.data;
					}
				}
				$scope.showSaveBtn = false;
			}
			else if(response.errorcode == 100){
				//将后台表单提交验证错误信息输出到页面
				if(!$scope.validMsg){
					$scope.validMsg = {}; //页面验证信息保存对象
				}
				//设置验证信息
				for(var key in response.validErrors){
					var __index = key.indexOf(".");
					if(__index > -1){
						var __index_str = key.substring(0,__index);
						if(!$scope.validMsg[__index_str]){
							$scope.validMsg[__index_str] = {};
						}
						$scope.validMsg[__index_str][key.substring(__index+1)] = response.validErrors[key];
					}else{
						$scope.validMsg[key] = response.validErrors[key];
					}
				}
				return;
			}
			else {
				alert("错误信息: " + response.message + ", 错误代号: " + response.errorcode);
			}
		}).error(function() {
			$scope.saveMsg = '连接不成功.';
		});
	};
	
	var saveTableDataWithList = function($scope, $http, operData, options) {
		var tableData =  $scope.tableData;
		var index = $scope.index;
		
		console.info('tableService $scope', $scope);
		console.info('tableService.tableData=', tableData);
		if($scope.validMsg){
			//清空验证信息
			for(var key in $scope.validMsg){
				var __index = key.indexOf(".");
				if(__index > -1){
					var __index_str = key.substring(0,__index);
					if(!$scope.validMsg[__index_str]){
						$scope.validMsg[__index_str] = {};
					}
					$scope.validMsg[__index_str][key.substring(__index+1)] = "";
				}else{
					$scope.validMsg[key] = "";
				}
			}
		}
		$http.post($scope.saveurl, operData).success(function(response) {
			if(response.errorcode == 1) {
				if(!(tableData instanceof Array)) { tableData = new Array(); }
				if(isNaN(parseInt(index))) { // 新增
					for(var key in operData) { delete operData[key]; }  // 清空新增框框所填的内容
					operData.isShowEdit = false;  // 自动将新增框框隐藏
					tableData.add(tableData.length, response.data);
					if(options && angular.isFunction(options.loadAfterSuccess)){
						options.loadAfterSuccess(tableData, tableData.length, response);
					}
				}
				else {
					response.data.isShowEdit = true;
					if(tableData.length == 0) {
						tableData.push(response.data);
					}
					else {
						tableData[index] = response.data;
					}
					if(options && angular.isFunction(options.loadAfterSuccess)){
						options.loadAfterSuccess(tableData, index, response);
					}
				}
				$scope.showSaveBtn = false;
			}
			else if(response.errorcode == 100){
				//将后台表单提交验证错误信息输出到页面
				if(!$scope.validMsg){
					$scope.validMsg = {}; //页面验证信息保存对象
				}
				//设置验证信息
				for(var key in response.validErrors){
					var __index = key.indexOf(".");
					if(__index > -1){
						var __index_str = key.substring(0,__index);
						if(!$scope.validMsg[__index_str]){
							$scope.validMsg[__index_str] = {};
						}
						$scope.validMsg[__index_str][key.substring(__index+1)] = response.validErrors[key];
					}else{
						$scope.validMsg[key] = response.validErrors[key];
					}
				}
				return;
			}
			else {
				console.info("错误信息: ", response);
			}
		}).error(function(response) {
			console.info("错误信息: ", response);
		});
	};
	
	
	// 保留该函数，对于不更新到新功能，稍微改动一下函数名，就可保持兼容
	var saveTableDataByGet = function($scope, $http, myScope, index, operData, filterFunc, options) {
//		console.info('tableService=', this);
		var params = $.convertObject2Params(operData, filterFunc);
		$http.get($scope.saveurl, {params: params}).success(function(response) {
			if(response.errorcode == 1) {
				/*if (YHUtil.hasObj(response.data)) {
					$scope.isAdd = !$scope.isAdd;
					$scope.operData = response.data;
					$scope.operData.isShowEdit = false;
				}*/
				if(!(myScope.data instanceof Array)) {
					myScope.data = new Array();
				}
				if(isNaN(parseInt(index))) {
					if(options && options.clearAdd) {  // 表示清空添加框框所填的内容
						myScope.operData = { isShowEdit: true };
					}
					else { myScope.operData.id = response.data.id; }
					myScope.data.add(myScope.data.length, response.data);
				}
				else {
					if(myScope.data.length == 0) { myScope.data.push(response.data); }
					else { 
						myScope.data[index] = response.data;
						myScope.data[index].isShowEdit = true;
					}
				}
				$scope.showSaveBtn = false;
			}
			else {
				console.info("错误信息: ", response);
			}
		}).error(function(response) {
			console.info("错误信息: ", response);
		});
	};

	function initTable($scope, NgTableParams, $resource, $http, tableFilter, vaildDataHandle, $filter,localData) {
		initSelectTable($scope, NgTableParams, $resource, $http, tableFilter, $filter, localData);

		//统一验证数据,在键入数据时进行验证
		$scope.keyUpData = function(operData) {
			if (operData == null) {
				$scope.showSaveBtn = false;
				return;
			} else {
				$scope.showSaveBtn = true;
			}

			if (vaildDataHandle(operData)) {
				$scope.showSaveBtn = true;
			} else {
				$scope.showSaveBtn = false;
				return;
			}

		}

		//删除处理器
		var delHandle = function(data) {
			$http.post($scope.delurl+"?ids="+data.id, { })
			.success(function(response) {
				if (!YHUtil.hasObj(response)) { return; }
				$("#my-alert .am-modal-bd").html(response.message);
				var $modal = $('#my-alert').modal();
				$scope.delMsg = response.message;
			}).error(function() {
				$scope.delMsg = '连接不成功.';
			});
		}

		$scope.delTableData = function(data) {
			getDelTableDataTemp = data;
			$('#my-confirm').modal({
				relatedTarget: this,
				onConfirm: function confirm() {
					delHandle(getDelTableDataTemp);
				},
				onCancel: function() { }
			});
		};

		$scope.toggleAddTableData = function() {
			$scope.operData.isShowEdit=!$scope.operData.isShowEdit;
		}
		//配舱
		$scope.conf= function(data) {
			data.isShowEdit=!data.isShowEdit;
		}
		//添加车队联系人/添加车队款项信息
		$scope.gotoPage = function(num, data) {
			window.location.href = basePath + "/index.do#/" + num;
		}
		$scope.getFilterDataByDictMap = function(type) {
			var list = dictMapByType[type];
			return list instanceof Array && list.length > 0? list : [{id: "不限"}];
		}
		$scope.toggleEditTableData = function(data) {
			data.isShowEdit=!data.isShowEdit;
		}
		$scope.isItemShow = function(data) {
			if(data){
				return data.isShowEdit;
			}
			return data;
		}
	}

	var initScope = function($scope) {
		//此配置用于显示新增/编辑窗口,默认不显示
		$scope.isAdd = false;
		//单页操作对像初始化
		$scope.operData = {};
		//是否存在保存键
		$scope.showSaveBtn = false;
		//字典对象
		$scope.dictMap = dictMap;

		$scope.defaltOperData = { };
	}
	
	var testFun = function() { };

	return {
		runTestFun: testFun,
		$get: function() {
			return {
				getDefaultPageCountAndSortingConf: function() {
					return pageCountAndSortingConf;
				},
				getDefaultHandleDataConf: getHandleDataConf,
				initTable: initTable,
				initSelectTable: initSelectTable,
				initScope: initScope,
				saveHandle : saveHandle,
				saveTableData: saveTableData,
				saveTableDataByGet: saveTableDataByGet,
				saveTableDataWithList : saveTableDataWithList
			};
		}
	}
});