$argos.S.login = {
		vaildCodeSessionName : 'vaildCode',
		loginUserSessionName : 'loginUser',
		desc: '用于用户登录相关的api'
};
$argos.S.login.makeVaildCode = function(){//生成验证码
	var result = null;
	
	result = $argos.$service.kaptchaProducer.createText();
	
	return result;
};
/**
 * 用户获取验证码
 */
$argos.S.login.getVaildCode = function(param,req,resp){
	 
	var vailCode =  this.makeVaildCode();
	
	//将验证码存入session
	var session = req.getSession();
	session.setAttribute(this.vaildCodeSessionName, vailCode);
	print('S.login.getVaildCode mdn=' + param.mdn + ' vailCode='+ vailCode);
	return vailCode;
};
//查询此验证码是否正确
$argos.S.login.cheackVaildCode = function(vailCode,req){
	var session = req.getSession();
	var vaildCodeInSession = session.getAttribute(this.vaildCodeSessionName);
	
	return (vailCode == vaildCodeInSession);
}
$argos.S.login.resetVailCode = function(req){
	var session = req.getSession();
	session.setAttribute(this.vaildCodeSessionName, null);//重新设置验证码
}


$argos.S.login.login = function(param,req,resp){//登录
	//默认登录不成功
	var resultInfo = {
			param : param,
			code : 2,//1代表成功,2代表帐号码或密码不正确。
			msg : '登录不成功,帐号或密码不正确！'
	};
	if(!$argos.util.has(param.mdn))return resultInfo;
	if(!$argos.util.has(param.password))return resultInfo;
	
	var accounts = $argos.S.account.vaildUser(param);
	
	if(accounts.size()<1){//打不到此用户，证码帐号或密码不正确
		return resultInfo;
	}
	
	resultInfo.code = 1;//注册成功
	resultInfo.msg = '登录成功';
	resultInfo.userInfo = accounts.get(0);
	
	this.setLoginAccount(resultInfo.userInfo,req)
	
	return resultInfo;
	
};

$argos.S.login.setLoginAccount = function(account,req){
	var session = req.getSession();
	session.setAttribute(this.loginUserSessionName, resultInfo.userInfo);
};

$argos.S.login.reg = function(param,req,resp){//注册
	//var account = param.account;
	var email = param.email;
	var mdn = param.mdn;
	var passowrd = param.password;
	var vailCode = param.vailCode;
	
	var regResult = {
			param : param,
			code : 1,//1代表成功
			msg : '注册成功'
	}
	//查询此电话是否已经有此用户了
	var accountsIsThisMdn =  $argos.S.account.findUserByMdn(param);
	if(accountsIsThisMdn && accountsIsThisMdn.size()>0){
		regResult.code = 3;//3代表已经存在此手机号码了。
		regResult.msg = '此手机号码已经注册了！';
		return regResult;
	}
	//查询此邮箱是否已经存在了
	var accountsIsThisMdn =  $argos.S.account.findUserByEmail(param);
	if(accountsIsThisMdn && accountsIsThisMdn.size()>0){
		regResult.code = 4;//4代表此邮箱已经注册了。
		regResult.msg = '此邮箱已经注册了！';
		return regResult;
	}
	
	
	
	if(!this.cheackVaildCode(vailCode,req)){//验证不成功
		regResult.code = 2;//验证码不正确
		regResult.msg = '验证码不正确,请重新获取验证码！';
		this.resetVailCode(req);
		return regResult;
	}
	
	
	
	//验证通过，保存信息
	var save_cnt = $argos.S.account.save(param);
	//注册成功重置验证码
	this.resetVailCode(req);
	
	if(save_cnt > 0){
		return regResult;
	}
	
}

$argos.S.login.forgetPassword = function(param,req,resp){
//	var param = {
//		mdn:'13012345678',
//		vaildCode:123
//		newPassword : '234'
//	};
	//默认修改密码成功
	var resultInfo = {
			param : param,
			code : 1,//1代表成功
			msg : '修改密码成功！'
	};
	if(!$argos.util.has(param.mdn)){
		resultInfo.code = 2;
		resultInfo.msg = '未输入电话号码！';
		return resultInfo;
	}
	if(!$argos.util.has(param.vailCode)){
		resultInfo.code = 3;//
		resultInfo.msg = '未输入验证码！';
		return resultInfo;
	}
	if(!$argos.util.has(param.newPassword)){
		resultInfo.code = 4;//没有输入密码
		resultInfo.msg = '没有输入密码！';
		return resultInfo;
	}
	
	
	if(!this.cheackVaildCode(param.vailCode,req)){//验证不成功
		resultInfo.code = 2;//验证码不正确
		resultInfo.msg = '验证码不正确,请重新获取验证码！';
		this.resetVailCode(req);
		return resultInfo;
	}else{
		this.resetVailCode(req);
	}
	//所以内容成功，则修改密码
	var upd_cnt = $argos.S.account.updatePassword(param);
	
	if(upd_cnt >0){
		
		return resultInfo;
	}else{
		resultInfo.code = 5;//
		resultInfo.msg = '没有此用户！';
		return resultInfo;
	}
	
}