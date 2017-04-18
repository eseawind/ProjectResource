<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>获取快照中的实时数据</title>
<meta name="author" content="leejean" />
<meta charset="utf-8"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<jsp:include page="../initlib/initAll.jsp"></jsp:include>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<pre>
说明：
*设备Code定义* 
	【卷烟机】:1~30  【包装机】:31~60【 装盘机(连接卷烟机)】=卷烟机code*1000+1
	【卸盘机(连接包装机)】=卷烟机code*1000+1  【封箱机】:61~70 储烟装置:71~100
	【成型机】:101~130【 发射机:131~140  【条烟输送】：151
	【装盘机】(连接成型机)：161~180   【卸盘机】：(连接发射机)181~200
*辅料定义*
	设备code*1000
</pre>
&nbsp;设备<input id="equipmentCode" style="width:40px" type="text"/>
轮询:
<select id="ms" style="width:70px">
	<option value="">-请选择-</option>
	<option value="1000">1秒/次</option>
	<option value="3000">3秒/次</option>
	<option value="5000">5秒/次</option>
	<option value="10000">10秒/次</option>
</select>
<span id="cycle_count"></span>
<input type="button" class="btn btn-success" onclick="get();" title="获取当前code内存中未被格式化的数据" value="获取当前"/>&nbsp;
<input type="button" class="btn btn-success" onclick="getAll();" value="获取当前所有"/>&nbsp;
<input type="button" class="btn btn-success" onclick="getFormatted();" value="获取当前formatted"/>&nbsp;
<input type="button" class="btn btn-success" onclick="getAllFormatted();" title="获取当前code内存中被格式化的数据" value="获取当前所有formatted"/>
<input type="button" class="btn btn-success" onclick="initAllRunnedWorkOrderCalcValues();" title="实例化所有运行工单辅料计算系数,包括辅料计算系数，烟机滚轴系数" value="初始化辅料计算系数"/>
<input type="button" class="btn btn-success" onclick="getAllCalcValues();" value="查看辅料计算系数" title="key:烟机编号,Axis:滚轴"/>
<input type="button" class="btn btn-success" onclick="getDbOutputOrInputInfos();" value="查看内存中产出和消耗实际信息"/>
<input type="button" class="btn btn-success" onclick="selectAll();" value="选中内容"/>
<input type="button" class="btn btn-success" onclick="clearAll();" value="重置条件"/><br><br>
&nbsp;<textarea id="jsons" rows="30" cols="125" style="width: 1100px;" title="拷贝至JSONView查看"></textarea>	
</body>
<script type="text/javascript">
	var ms;
	var t=null;
	var cycle_count=0;
	var iscycle = false;
	function getData(){
		var code = $("#equipmentCode").val();
		if(!code){
			alert("请输入设备code...");
			$("#equipmentCode").focus();
			return;
		}
		$.post("${pageContext.request.contextPath}/isp/get.do",{"code":code},function(json, textStatus, jqXHR){
			if(json){				
				$("#jsons").val(JSON.stringify(json));
				cycle_count+=1;
				if(iscycle){
					$("#cycle_count").html("第"+cycle_count+"次");
				}else{
					$("#cycle_count").html(null);
				}
			}else{
				$("#jsons").val("快照中无"+code+"的实时数据");
			}
		},"JSON").error(function() { alert("未找到当前设备实时数据"); });
	}
	function getAllData(){
		$.post("${pageContext.request.contextPath}/isp/getAll.do",function(json){
			$("#jsons").val(JSON.stringify(json));
			cycle_count+=1;
			if(iscycle){
				$("#cycle_count").html("第"+cycle_count+"次");
			}else{
				$("#cycle_count").html(null);
			}
			
		},"JSON");
	}
	function get(){
		cycle_count=0;
		ms = $("#ms").val();
		clearInterval(t);
		if(ms){
			iscycle = true;
			t= setInterval('getData()',ms);
		}else{
			iscycle = false;
			getData();			
		}
		
	}
	function getAll(){
		cycle_count=0;
		ms = $("#ms").val();		
		clearInterval(t);
		if(ms){
			iscycle = true;
			t = setInterval('getAllData()',ms);
		}else{
			iscycle = false;
			getAllData();			
		}
	}
	
	
	function getFormattedData(){
		var code = $("#equipmentCode").val();
		if(!code){
			alert("请输入设备code...");
			$("#equipmentCode").focus();
			return;
		}
		$.post("${pageContext.request.contextPath}/isp/getFormatted.do",{"code":code},function(json, textStatus, jqXHR){
			if(json){				
				$("#jsons").val(JSON.stringify(json));
				cycle_count+=1;
				if(iscycle){
					$("#cycle_count").html("第"+cycle_count+"次");
				}else{
					$("#cycle_count").html(null);
				}
			}else{
				$("#jsons").val("快照中无"+code+"的实时数据");
			}
		},"JSON").error(function() { alert("未找到当前设备实时数据"); });
	}
	function getAllFormattedData(){
		$.post("${pageContext.request.contextPath}/isp/getAllFormatted.do",function(json){
			$("#jsons").val(JSON.stringify(json));
			cycle_count+=1;
			if(iscycle){
				$("#cycle_count").html("第"+cycle_count+"次");
			}else{
				$("#cycle_count").html(null);
			}
			
		},"JSON");
	}
	
	function getFormatted(){
		cycle_count=0;
		ms = $("#ms").val();
		clearInterval(t);
		if(ms){
			iscycle = true;
			t= setInterval('getFormattedData()',ms);
		}else{
			iscycle = false;
			getFormattedData();			
		}
		
	}
	function getAllFormatted(){
		cycle_count=0;
		ms = $("#ms").val();		
		clearInterval(t);
		if(ms){
			iscycle = true;
			t = setInterval('getAllFormattedData()',ms);
		}else{
			iscycle = false;
			getAllFormattedData();			
		}
	}
	
	function clearAll(){
		clearInterval(t);
		$("#ms").val(null);
		$("#equipmentCode").val(null);
		cycle_count=0;
		$("#cycle_count").html("第"+cycle_count+"次");
	}
	function selectAll(){
		$("#jsons").select();
	}
	function getAllCalcValues(){
		clearInterval(t);
		$.post("${pageContext.request.contextPath}/isp/getAllCalcValues.do",function(json, textStatus, jqXHR){
			if(json){				
				$("#jsons").val(JSON.stringify(json));
			}
		},"JSON").error(function() { alert("查看辅料计算系数异常"); });
	}
	function initAllRunnedWorkOrderCalcValues(){
		$.post("${pageContext.request.contextPath}/pms/workorder/initAllRunnedWorkOrderCalcValues.do",function(json, textStatus, jqXHR){
			$("#jsons").val(json.msg);
		},"JSON");
	}
	function getDbOutputOrInputInfos(){
		clearInterval(t);
		$.post("${pageContext.request.contextPath}/isp/getDbOutputOrInputInfos.do",function(json, textStatus, jqXHR){
			if(json){				
				//console.info(JSON.stringify(json));
				$("#jsons").val(JSON.stringify(json));
			}
		},"JSON").error(function() { alert("查看产出实际和消耗实际信息异常"); });
	}
</script>
</html>