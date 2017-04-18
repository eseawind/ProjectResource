/**
 * WCT获取下拉框公共值
 * <select>
 * <option value="audi">Audi</option>
 * </select>
 * select ：string Easyui comboxbox 对象
 * type   ：string 类型 type定义见【com.shlanbao.tzsc.plugin.controller.LoadComboboxController】
 * setAll ：boolean 是否设置【全部】选项 不提供本参数时，默认不加载【全部】选项
 * Leejean
 */
$.loadSelectData = function(select,type,setAll) {
	var pathName=window.document.location.pathname;
	var basePath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	var url=basePath+"/plugin/combobox/load.do";
	if(!setAll){//undefined 第三个参数为空时
		setAll = false;
	}
	url+=("?type="+type+"&setAll="+setAll);
	$.post(url,function(json){
		for(var i=0;i<json.length;i++){			
			select.append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
		}
	},"JSON");
};

/*
 * 【功能说明】：启动工程，初始化辅料表 （md_mat_param）
 * @author wanchanghuang
 * @time 2015年8月11日11:16:02
 * 
 * **/
function loadMdMatParamData() {
	var url="${pageContext.request.contextPath}/plugin/combobox/loadMdMatParam.do";
	$.post(url,function(json){
		console.info("辅料ID："+json.mat+"辅料系数："+json.val);
		return json;
	},"JSON");
};