$argos.S.account = {
		
};
$argos.S.account.findUserByMdn = function(param){
	var mdn = param.mdn;
	var amodel = $argos.$service.amodel;
	var accounts = amodel.query("select * from tb_account u where u.mdn='"+mdn+"'");
	return accounts;
};
$argos.S.account.findUserByEmail = function(param){
	var email = param.email;
	var amodel = $argos.$service.amodel;
	var accounts = amodel.query("select * from tb_account u where u.email='"+email+"'");
	return accounts;
};

$argos.S.account.save = function(account){
	var amodel = $argos.$service.amodel;
	var save_cnt = amodel.up("insert into tb_account(mdn,email,password)value(:mdn,:email,:password)", account);
	return save_cnt;
}

$argos.S.account.vaildUser = function(param){
	var mdn = param.mdn;
	var password = param.password
	
	var amodel = $argos.$service.amodel;
	accounts = amodel.qy("select * from tb_account u where mdn=:mdn and password=:password", param);
	
	return accounts;
}

$argos.S.account.updatePassword = function(param){
	var mdn = param.mdn;
	var password = param.newPassword
	
	var amodel = $argos.$service.amodel;
	var up_cnt = amodel.up("update tb_account set password=:newPassword where mdn=:mdn", param);
	
	return up_cnt;
}