/**
 * 
 */
print('test.js start...');

$argos.S.test = function(param,req,resp){//生成验证码
	
	print('param=' + param);
	print('request=' + req);
	print('response=' + resp);
	
	return "ok";
}