$argos.S.login = {
		vaildCodeSessionName : 'vaildCode'
};
$argos.S.login.makeVaildCode = function(){//生成验证码
	var result = null;
	
	result = $argos.$service.kaptchaProducer.createText();
	
	return result;
};
$argos.S.login.getVaildCode = function(param,req,resp){
	 
	var vailCode =  this.makeVaildCode();
	
	//TODO 将验证码存入session
	var session = req.getSession();
	session.setAttribute(this.vaildCodeSessionName, vailCode);
	print('S.login.getVaildCode mdn=' + param.mdn + ' vailCode='+ vailCode);
	return vaileCode;
};
$argos.S.login.login = function(param,req,resp){//登录
	
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
	
	var session = req.getSession();
	var vaildCodeInSession = session.setAttribute(this.vaildCodeSessionName);
	
	if(vailCode != vaildCodeInSession){//验证不成功,TODO 暂时不用验证功能
		regResult.code = 2;//验证码不正确
		regResult.msg = '验证码不正确';
		return regResult;
	}
	
	//TODO 验证通过，保存信息
	var save_cnt = $argos.S.account.save(param);
	
	if(save_cnt > 0){
		return regResult;
	}
	
}