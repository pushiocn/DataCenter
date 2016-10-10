(function() {
	"use strict";
	var app = angular.module('simpleTable', []);
	app.directive('simpleTable', function() {
		return {
			restrict: 'A',
			//		require : 'ngModel',
			scope: {
				model: '=',
				sourceLink: '@',
				pageIndex: '@',
				pageSize: '@',
				filterModel: '=',
				getData: '=',
				urlFilter: '=',
				afterGetDataHandle: '='
			},
			controller: function($scope, $http) {
				console.info('simpleTableDir $scope', $scope);

				if (!$scope.model) {
					$scope.model = {};
				}

				var model = $scope.model;

				model.initDefaultConfig = function() {
					var model = this;

					//服务端返回的翻页相关参数
					this.returnPageInfo = {};

					model.defaultGetData = function() {
						this.getDataStatus = 'getting';
						$http.get(this.sourceLink, {
								params: model.getParam()
							})
							.success(function(resp) {
								model.resp = resp;
								model.getStatus = 'getok';


								model.innerAfterGetDataHandle();
								model.afterGetDataHandle();
							});
					}

					model.getData = function() {

					}

					model.getData = model.defaultGetData; //预置默认

					model.defAtferGetDataHandle = function() {

					}

					model.returnPageInfoNameMap = {
						//				     number: "number",//代表当前页号，也就是page的值，number是js的关键字，不使用
						first: "first",
						last: "last",
						numberOfElements: "numberOfElements",
						size: "size",
						totalElements: "totalElements",
						totalPages: "totalPages"
					}

					model.innerAfterGetDataHandle = function() {

						if (!this.resp.data) {
							return;
						}
						var rdata = this.resp.data;

						//将服务端返回页面相关的值置入returnPageInfo对象中
						for (var keyNameOfPageInfoMap in this.returnPageInfoNameMap) {
							var keyNameOfPageInfo = this.returnPageInfoNameMap[keyNameOfPageInfoMap];
							var pageInfoItemValue = rdata[keyNameOfPageInfo];
							this.returnPageInfo[keyNameOfPageInfo] = pageInfoItemValue;
						}
					}


					if ($scope.afterGetDataHandle) {
						model.afterGetDataHandle = $scope.afterGetDataHandle;
					} else {
						model.afterGetDataHandle = model.defAtferGetDataHandle;
					}
					//默认的页面参数，需要提交给服务端
					model.defPageInfo = {
						page: 1,
						count: 20
					};
					//参数对象，需要提交服务端的参数对象
					model.param = {};

					model.addParam = function(obj) {
						for (var i in obj) {
							var o = obj[i];
							this.param[i] = o;
						}
					}

					model.fillPageInfo = function() {
						this.addParam(this.defPageInfo);
						if ($scope.pageIndex) {
							this.param.page = parseInt($scope.pageIndex);
						}
						if ($scope.pageSize) {
							this.param.count = parseInt($scope.pageSize);
						}
					}
					model.fillPageInfo(); //将指令页面参数设置入model对象的param值中

					model.getParam = function() {
						return $.convertObject2Params(this.param);
					}
					model.flushData = function() {
						var sourceLink = $scope.sourceLink;
						this.sourceLink = sourceLink;
						//处理filter
						//					console.info('flushData filter', filter);
						if ($scope.filterModel) {
							this.addParam($scope.filterModel);
						}

						//开始去服务端取数据
						this.getData();
					}

					model.getData = this.defaultGetData;
				}

				model.initDefaultConfig(); //调用model初始化



				$scope.$watch('sourceLink', function(nv, ov) {
					if (nv == ov && model.resp) {
						return;
					}
					//刷新数据
					if (model.flushData) {
						model.flushData();
					} else {
						console.info('not FlushData Function.');
					}
				});
				//isSelectAll
				$scope.$watch('model.isSelectAll', function(nv, ov) {
					if (nv == ov) {
						return;
					}
					if (model.resp.data.content) {
						var ctt = model.resp.data.content;
						for (var i in ctt) {
							var item = ctt[i];
							item.isSelectedInTable = nv;
						}
					}
				});

				//翻页方法
				//下一页
				model.nextPage = function() {
						this.param.page += 1;
						this.flushData();
					}
					//上一页
				model.prevPage = function() {
						this.param.page -= 1;
						this.flushData();
					}
					//一般不使用此方法
				model.skipPage = function(index) {
						this.param.page = index;
						this.flushData();
					}
					//是否第一页
				model.isFirst = function() {
						return this.returnPageInfo.first;
					}
					//是否最后一页
				model.isLast = function() {
					return this.returnPageInfo.last;
				}

				//取得已选择的数据
				model.getSelectedItem = function() {
						var selectedItems = null;
						if (model.resp.data.content) {
							var ctt = model.resp.data.content;
							selectedItems = [];
							for (var i in ctt) {
								var item = ctt[i];
								if (item.isSelectedInTable) {
									selectedItems.push(item);
								}
							}
						}
						return selectedItems;
					}
					//取得已选择的数据
				model.getUnSelectedItem = function() {
					var selectedItems = null;
					if (model.resp.data.content) {
						var ctt = model.resp.data.content;
						selectedItems = [];
						for (var i in ctt) {
							var item = ctt[i];
							if (!item.isSelectedInTable) {
								selectedItems.push(item);
							}
						}
					}
					return selectedItems;
				}

				this.getScope = function() {
					return $scope;
				}
			}
		};
	});

	app.directive('tableFlush', function() {
		return {
			restrict: 'A',
			require: '^simpleTable',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;

				var onClick = function() {
					model.flushData();
				}
				iElement.bind('click', onClick);
			},
			controller: function($scope, $http) {}
		};
	});


	app.directive('tableSort', function() {
		return {
			restrict: 'A',
			require: '^simpleTable',
			scope: {
				field: '@',
				stype: '@'
			},
			compile: function(tElem, tAttrs) {


				return {

					post: function(scope, iElement, iAttrs, parentController) {

						var parentScope = parentController.getScope();
						var model = parentScope.model;

						scope.sort = function() {
							if (!model.param.sortFields) {
								model.param.sortFields = [];
							}
							if (!model.param.sortTypes) {
								model.param.sortTypes = [];
							}

							var hasSortFields = false;
							var sortFieldIx = null;
							for (var fieldIx in model.param.sortFields) {
								var field = model.param.sortFields[fieldIx];
								if (fieldIx === scope.field) {
									hasSortFields = true;
									sortFieldsIx = fieldIx;
								}
							}
							if (!hasSortFields) {
								model.param.sortFields.push(scope.field);
								if (!scope.stype) {
									scope.stype = 'desc';
								}
								model.param.sortTypes.push(scope.stype);

							} else {
								model.param.sortFields[sortFieldIx] = scope.field;
								model.param.sortTypes[sortFieldIx] = scope.stype;
							}





							//刷新表格数据
							model.flushData();
						}

						iElement.bind('click', scope.sort);

					}
				}
			},
			controller: function($scope, $http) {}
		};
	});

	app.directive('pageSizeInput', function() {
		return {
			restrict: 'E',
			require: '^simpleTable',
			template: '<input type="number" ng-model="model.param.count" />',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;
				scope.model = model;

			},
			controller: function($scope, $http) {}
		};
	});

	app.directive('pageIndexInput', function() {
		return {
			restrict: 'E',
			require: '^simpleTable',
			template: '<input type="number" ng-model="model.param.page" />',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;
				scope.model = model;
			},
			controller: function($scope, $http) {}
		};
	});

	app.directive('pageCount', function() {
		return {
			restrict: 'E',
			require: '^simpleTable',
			template: '{{model.returnPageInfo.totalPages}}',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;
				scope.model = model;
			},
			controller: function($scope, $http) {}
		};
	});

	app.directive('itemCount', function() {
		return {
			restrict: 'E',
			require: '^simpleTable',
			template: '{{model.returnPageInfo.totalElements}}',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;
				scope.model = model;
			},
			controller: function($scope, $http) {}
		};
	});

	app.directive('prevPage', function() {
		return {
			restrict: 'A',
			require: '^simpleTable',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;
				scope.model = model;

				console.info('prevPage link ', scope.model);
				var onClick = function() {
					scope.model.prevPage();
				}
				iElement.bind('click', onClick);
				scope.$watch('model.returnPageInfo.first', function(nv, ov) {
					if (nv === ov) {
						return;
					}
					if (nv == true) {
						iElement.hide();
					} else {
						iElement.show();
					}
				});
			},
			controller: function($scope, $http) {}
		};
	});

	app.directive('nextPage', function() {
		return {
			restrict: 'A',
			require: '^simpleTable',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;
				scope.model = model;

				console.info('nextPage link ', scope.model);
				var onClick = function() {
					scope.model.nextPage();
				}
				iElement.bind('click', onClick);
				scope.$watch('model.returnPageInfo.last', function(nv, ov) {
					if (nv === ov) {
						return;
					}
					if (nv == true) {
						iElement.hide();
					} else {
						iElement.show();
					}
				});
			},
			controller: function($scope, $http) {}
		};
	});

	app.directive('pagination', function() {
		return {
			restrict: 'E',
			require: '^simpleTable',
			link: function(scope, iElement, iAttrs, parentController) {
				var parentScope = parentController.getScope();
				var model = parentScope.model;
				scope.model = model;
			},
			templateUrl: './js/simple-table/pagination.html',
			controller: function($scope) {

			}
		}
	});
})();