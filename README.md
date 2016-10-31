# DataCenter
Argos
极简的全基于JavaScript的Baas平台。

前端代码:
var param = {
                a : 1,
                b : 2
        };
        argos('sum')(param)(function(resp){
            console.info('argosSum return:' , resp.exeResult);//输出3
        })();

后端代码:

$argos.sum = function(param){
    return param.a + param.b;
}


详细请见：https://zhuanlan.zhihu.com/p/21965207
