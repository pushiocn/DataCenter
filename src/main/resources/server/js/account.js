$argos.S.account = {
		
};
$argos.S.account.findUserByMdn = function(param){
	var mdn = param.mdn;
	var amodel = $argos.$service.amodel;
	var accounts = amodel.query("select * from tb_account u where u.mdn='"+mdn+"'");
	return accounts;
};

$argos.S.account.save = function(account){
	var amodel = $argos.$service.amodel;
	var save_cnt = amodel.up("insert into tb_account(mdn,email,password)value(:mdn,:email,:password)", account);
	return save_cnt;
}