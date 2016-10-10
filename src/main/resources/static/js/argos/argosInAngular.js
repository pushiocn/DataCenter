/**
 * 
 */
(function() {
	"use strict";
	var app = angular.module('argos', []);
	
	var basePath = '/argos';
	var exePath = '/exe';
	
	var exePathHasBase = basePath + exePath;
	
//	app.provider('argos', function($http){
//		
//		var argos = function(){
//			console.info('start argos...$http=',$http);
//			
//			
//		}//argos主体方法
//		
//		
//		return {
//			$get: function() {
//				return argos
//			}
//		}
//	});//此处provider定义结束
	
	
	app.factory('argos',function($http){
		
		var httpSer = $http;
		
		var argos = function(url){
			var u = encodeURI(url);
			var reqInfo = {
				url : exePathHasBase + '/' + u
			};
//			console.info('start argos...$http=',$http);
			var funObjHashUrl = function(serviceParam){
				reqInfo.serviceParam = serviceParam;
				var funObjHasParam = function(callBack){
					reqInfo.callBack = callBack;
					var funObjHasCallBack = function (){
						console.info('argos start post...',reqInfo);
						httpSer.post(reqInfo.url, reqInfo.serviceParam).success(reqInfo.callBack);
					}
					return funObjHasCallBack;
				}
				
				return funObjHasParam;
			}
			
			return funObjHashUrl;
		}
		return argos;
	});
	
	
	//此处module定义结束
})();