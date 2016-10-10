console.info('app-init...');
app = angular.module('app',['simpleTable','argos']);
angular.element = jQuery;


app.controller('testCtrl', function($scope,$http, argos) {
	console.info('testCtrl, argos=',argos);
	
	 $scope.dataA = {
			 url: '/argos/exe/testDb',
			 data :{}
	 };
	$scope.runFunA = function(){
		var dataA = $scope.dataA;
		eval(dataA.js);
		
	}
	$scope.doFunA = function(){
		console.info('doFunA');
		var dataA = $scope.dataA;
	
		$http.post(dataA.url, dataA.data).success(function(resp){
			dataA.resp = resp;
		});
	}
	
	$scope.sum = function(){
		var param = {
				a : 1,
				b : 2
		};
		argos('sum')(param)(function(resp){
			console.info('argosSum return:' , resp.exeResult);//输出3
		})();
	}
	
	$scope.doArgoHello = function(){
		console.info('doArgoHello');
		var param = {
				name : 'world!'
		};
		argos('hello')(param)(function(resp){
			console.info('argosHello return:' , resp.exeResult);//输出helloWorld
		})();
	}
	
	$scope.doTestDb = function(){
		console.info('doTestDb');
		var param = {
		};
		argos('testDb')(param)(function(resp){
			console.info('testDb return:' , resp.exeResult);//输出tb_twitter表的数据结果
			$scope.testdbData = resp.exeResult;
		})();
	}
	
	$scope.restApp = function(){
		// /argos/restApp
		
		$http.get('/argos/restApp').success(function(resp){
			$scope.dataA.restMsg = resp.message;
		});
	}
});
