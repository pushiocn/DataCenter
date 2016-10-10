
var dictMap = null;

app.filter('dictId2Name', function() {
	return function(input) {
		if(dictMap == null){
			return '_无字字典数据_';
		}
		if (input !=undefined && input != null ) {
			var result = dictMap[input];
			if(!result)result = '_未定义_';
			return result;
		}else{
			return '_未输入值_';
		}
	}
});