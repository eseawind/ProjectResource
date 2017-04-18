<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备故障统计_维护时记录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">
#prodMailPlan-seach-box{
		border: 1px solid #9a9a9a;
		width: 821px;
		margin-left: 10px;
		height: 36px;
		margin: 0 auto;		
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	#prodMailPlan-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
	.panel-body {
  color: #000000;
  font-size: 12px;
  background: #F4F4F4;
}
fieldset {
  border: 1px dotted #D1D7DC;
  background: #F4F4F4;
}
#hisBadQty-title{
		font-size: 23px;
		font-weight: bold;
		text-align: center;
		padding-top: 4px;
		background: #b4b4b4;
		color: #3C3C3C;
		border-radius: 0px 4px 0px 0px;
		line-height: 35px;
		height: 40px;
		border-bottom: 2px solid #838383;
	}
	#hisBadQty-seach-box{
		border: 1px solid #9a9a9a;
		width: 96%;
		margin-left: 10px;
		height: 47px;
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	#searchForm td{
		font-size:14px;
	}
	#searchForm th{
		font-size:20px;
	}
		.btn-default {
color: #FFFFFF;
background-color: #5A5A5A;
border-color: #cccccc;
}
</style>
<script type="text/javascript">
$(function(){
	var today = new Date();
	var month=today.getMonth()+1;
	var code="${loginInfo.equipmentCode}";
	if(month<10){month=("0"+month);}
	var day=today.getDate();
	if(day<10){day=("0"+day);}
    var date=today.getFullYear()+"-"+month+"-"+day; 
   	$("#time").datebox("setValue",date);	//时间用这个
	$.loadSelectData($("#mdShiftId"),"SHIFT",false);//加载下拉框数据
	$.loadSelectData($("#mdEqmentId"),"ALLBOXERS",true);//加载下拉框数据
	//$("#mdShiftId").combobox('setValue',"1");
	//$("#mdEqmentId").combobox("getData");
	if(code<=30&&code>=1){//卷烟机
		$("#checkJsp").attr("src","${pageContext.request.contextPath}/pms/quality/check/rolerCheck.jsp");
	}else if(code<=60&&code>=31){//包装机
		$("#checkJsp").attr("src","${pageContext.request.contextPath}/pms/quality/check/packCheck.jsp");
	}else if(code<=70&&code>=61){//封箱机
		$("#checkJsp").attr("src","${pageContext.request.contextPath}/pms/quality/check/sealerCheck.jsp");
	}else if(code<=130&&code>=101){//成型机
		$("#checkJsp").attr("src","${pageContext.request.contextPath}/pms/quality/check/filterCheck.jsp");
	}
});

function queryEqmTrouble() {
	queryParams = $("#searchForm").form("getData");
}
function exportExcel(){
	var code=${loginInfo.equipmentCode};
	var tempUrl="";
	if(code<=30&&code>=1){//卷烟机
		tempUrl="${pageContext.request.contextPath}/pms/massCheck/exportRolerCheckInfo.do";
	}else if(code<=60&&code>=31){//包装机
		tempUrl="${pageContext.request.contextPath}/pms/massCheck/exportCheckInfo.do";
	}else if(code<=70&&code>=61){//封箱机
		tempUrl="${pageContext.request.contextPath}/pms/massCheck/exportFXJCheckInfo.do";
	}else if(code<=130&&code>=101){//成型机
		tempUrl="${pageContext.request.contextPath}/pms/massCheck/exportFilterCheckDataList.do";
	}
	$("#searchForm").attr("action",tempUrl);
	$("#searchForm").attr("method","post");
	$("#searchForm").submit();  
}
function queryCheckInfo(){
	$("#checkJsp")[0].contentWindow.params=$("#searchForm").form("getData");
    $("#checkJsp")[0].contentWindow.bandParams();
}
	
</script>
</head>
<body >
<div id="hisBadQty-title"><b>查询导出</b></div>
<div id="hisBadQty-seach-box" >
		<form id="searchForm" style="margin:4px 0px 0px 0px;">
			<div class="topTool">
			<table>
				<tr>
					<!-- <th style="width:76px;text-align:center;"><span class="label"><font color="black">设备名称：</font></span></th>
					<td><select id="mdEqmentId" name="mdEqmentId" ></select></td> -->
					<input type="hidden" value="${loginInfo.equipmentId}" name="mdEqmentId" id="mdEqmentId">
					<th style="width:76px;text-align:center;"><span class="label"><font color="black">班次：</font></span></th>
					<td><select id="mdShiftId" name="mdShiftId" style="width:100px;"></select></td>
					<th style="width:76px;"><span class="label"><font color="black">生产日期：</font></span></th>
					<td style="width:140px;"><input id="time" name="time" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:150px"/></td>
					<td  style="width:76px;text-align:center;"><input type="button" onclick="queryCheckInfo()" value="查询" style="height:30px;width:55px; " class="btn btn-default"/></td>
					<!-- <td  style="width:76px;text-align:center;"><input type="button" onclick="exportExcel();" value="导出" style="height:30px;width:55px; " class="btn btn-default"/></td> -->
				</tr>
			</table>
			</div>
		</form>
</div>
<div style="width:100%;height:500px;">
		<iframe id="checkJsp" name="checkJsp"  width="100%" height="100%" allowtransparency="yes" frameborder="no" border="0" marginwidth="0" marginheight="0" ></iframe>
</div>
</body>
</html>