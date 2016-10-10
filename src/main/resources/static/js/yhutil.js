var YHUtil = {
	hasData: function(data) {
		var hasData = false;
		if (data == undefined || data == null) {
			hasData = false;
		} else if (data.length < 1) {
			hasData = false
		} else {
			hasData = true;
		}
		return hasData;
	},
	hasObj: function(data) {
		var hasData = false;
		if (data == undefined || data == null) {
			hasData = false;
		} else {
			hasData = true;
		}
		return hasData;
	},
	getNameByTypeID:function(id){
		var curDictArray=[];		
		for(var i in dictData){
			if(dictData[i].type==id){
				var temp = {
					id:dictData[i].id,
					title:dictData[i].name
				}
				curDictArray.push(temp);
				temp=null;
			}
		}
		curDictArray.splice(0, 0,{id:null,title:"全部"});
		return curDictArray;
		
	},
	copyObj:function(srcObj, targetObj){
		if(!srcObj){
			return;
		}
		if(!targetObj){
			return;
		}
		for(var i in srcObj){
			var v = srcObj[i];
			targetObj[i] = v;
		}
	}
}

var YU = YHUtil;