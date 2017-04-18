/**
 * 
 * @requires jQuery
 * 
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
/*static resource URL*/
var baseUrl="http://10.39.123.3:8080/LBDWS/statics/resources";
getJsonData = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};


/*清空表单条件*/
clearForm = function(formID){
	//formID.reset();
	$(formID).find('input').attr('value','');
	$(formID).find('select').each(function(i,n){
		//去除已经选中
		$(this).find("option").removeAttr("selected");
		$(this).find("option").eq(0).attr("selected",'selected');
	});
	formID.submit();
};

/**键盘调用事件*/
//键盘弹出框
showKey=function(inputId){
   	scWidth=652;
   	scHight=230;
   	window.open(baseUrl+"/jslib/keyboard/keyboardViem.html?inputId="+inputId,"keyboardTab","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
}
//用于子页面回调，给页面填充值
setInputVal=function(inputId,val){
	$("#"+inputId).val(val);
}