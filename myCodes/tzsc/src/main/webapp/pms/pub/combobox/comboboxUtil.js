/**
 * 滕州项目combobox数据源
 * combobox ：string Easyui comboxbox 对象
 * type   ：string 类型 type定义见【com.shlanbao.tzsc.plugin.controller.LoadComboboxController】
 * setAll ：boolean 是否设置【全部】选项 不提供本参数时，默认不加载【全部】选项
 * Leejean
 */
$.loadComboboxData = function(combobox,type,setAll) {
	var url=$.basePath+"/plugin/combobox/load.do";
	if(!setAll){//undefined 第三个参数为空时
		setAll = false;
	}
	url+=("?type="+type+"&setAll="+setAll);
	combobox.combobox({  
	    url:url,  
	    valueField:'id',  
	    textField:'name'  
	}); 
};


/**
 * 
 * 功能说明：设备检修，添加，设备型号下拉绑定
 * @author zhanglu
 * @time 2015年9月13日 18:47:18
 * 
 * */
$.loadComboboxDataQueryByEqpAll = function(combobox,type,setAll) {
	var url=$.basePath+"/plugin/combobox/load.do";
	if(!setAll){//undefined 第三个参数为空时
		setAll = false;
	}
	//url+=("?type="+type+"&setAll="+setAll);
	$.post(url,{type:type,setAll:setAll},function(date){
		for(var i=0;i<date.length;i++){
				var option = "<option value='"+date[i].id+"'>"+date[i].name+"</option>";
				$('#eqp_id').append(option);
		} 
		//console.info(date[1].name+"===="+date[1].id);
	},"JSON");
};