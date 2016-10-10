/**
 * 
 */
//load('classpath:./src/main/resources/internal/js/test.js'); //无法指定路径

$argos = {};


$argos.setIn = function(name,object){
	this[name] = object;
};


$argos.$core = {
		
}
$argos.$core.run = function(javaScripName){
	this.$argosStart.runJavaScriptFile(javaScripName);
};

