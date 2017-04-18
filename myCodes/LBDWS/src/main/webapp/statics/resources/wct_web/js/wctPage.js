/**
 * 功能说明：分页点击上一页，下一页  按钮调用的方法
 *    1）url-上一页，下一页传过来的路径请求
 *    2）将url地址写入from标签的action中
 *    3）获得from标签id，js提交from表单
 * 
 * */
function chengePage(fromId,url){
	$("#"+fromId).attr("action",url).submit();
}