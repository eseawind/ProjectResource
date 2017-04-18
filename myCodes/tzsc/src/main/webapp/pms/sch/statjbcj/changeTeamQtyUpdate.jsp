<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备故障统计_维护时记录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<style type="text/css">

.panel-body {
  color: #000000;
  font-size: 12px;
  background: #F4F4F4;
}
fieldset {
  border: 1px dotted #D1D7DC;
  background: #F4F4F4;
}
</style>
<script type="text/javascript">
	$(function(){
		var today = new Date();
		var month=today.getMonth()+1;
		if(month<10){month=("0"+month);}
		var day=today.getDate();
		if(day<10){day=("0"+day);}
	    var date=today.getFullYear()+"-"+month+"-"+day; 
	   	$("#time").datebox("setValue",date);	//时间用这个
	});
	
	function queryEqmTrouble() {
		queryParams = $("#searchForm").form("getData");
	}
	function exportExcel(){
	
		var tempUrl ="${pageContext.request.contextPath}/pms/massCheck/exportRolerCheckInfo.do";
		$("#searchForm").attr("action",tempUrl);
		$("#searchForm").attr("method","post");
		$("#searchForm").submit(); 
	
	}
	function queryCheckInfo(){
		$("#checkJsp")[0].contentWindow.params=$("#searchForm").form("getData");
	    $("#checkJsp")[0].contentWindow.bandParams();
	}
	function saveUpdatem(){
		$("#checkJsp")[0].contentWindow.saveUpdate();
	}
</script>
</head>
<body class="easyui-layout panel-body" data-options="fit : true,border : false">
	<div id="toolbar" class="datagrid-toolbar"  style="height:85px" data-options="region:'north',border:false">
		<form id="searchForm" style="margin:4px 0px 0px 0px;">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">生产日期：</span>
						 <input id="time" name="time" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>	
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryCheckInfo()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="saveUpdatem()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
				<!-- <a onclick="exportExcel();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">导出</a> -->
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<iframe id="checkJsp" name="checkJsp" src="qtyUpdate.jsp" width="100%" height="99%" allowtransparency="yes" frameborder="no" border="0" marginwidth="0" marginheight="0" ></iframe>
	</div>
	
</body>
</html>