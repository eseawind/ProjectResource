<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>烟支在线物理检测结果</title>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/rollerCss/dist/css/bootstrap.css"   type="text/css" />
<!-- GZDAMS路径为 -->
<!-- 
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/bootCSS/css/bootstrap.css"></link> -->
<!-- 主样式表文件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/rollerCss/css/style.css"   type="text/css" />
<!-- 为easyui--修改版----样式文件开始 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/rollerCss/easyui.css"   type="text/css" />
<!-- 为easyui样式文件结束 -->
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.js" type="text/javascript"></script>
<link media="screen" href="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.css" type="text/css" rel="stylesheet"/>
</head>
<body>

<div id="index-content">
<div id="wct-body">
<div id="prods-idx">
<!-- <div id="prods-idx-menu">
	<div style="padding: 5px;">
		<ul class="nav nav-pills nav-stacked" id="prods-menu">
		  <li class="active"><a href="javascript:void(0)" class="prods-link" path="/wct/product/workCalendar.jsp"><span class="glyphicon glyphicon-ok-sign pull-right"></span>烟支在线检测</a></li>
		  <li class=""><a href="javascript:void(0)" class="prods-link" path="/wct/product/workOrders.jsp"><span class="glyphicon glyphicon-list-alt pull-right"></span>设备维修</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/product/workOrderDet.jsp"><span class="glyphicon glyphicon-list pull-right"></span>工单实绩</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/product/paiHaoChange.jsp"><span class="glyphicon glyphicon-transfer pull-right"></span>牌号切换</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/product/shiftChange.jsp"><span class="glyphicon glyphicon-user pull-right"></span>班次交接</a></li>
		</ul>

	</div>

</div> -->
<div id="prods-idx-content">
	<div id="wkd-qua-title">烟支在线物理检测结果</div>
	<div class="info_machine">	
		<div class="info_machine_cen fr btn-group">			
			<button type="button" class="btn btn-default query" onclick="listData(1);">查询</button>
			<button type="button" class="btn btn-default resetting" onclick="workorderFm();">重置</button>
		</div>
		<div class="info_machine_right fl" >
		<div hidden="false" >
					 <c:if test="${groupTypeFlag==1}">
							<label>
							<input id="rollereqp" type="text"  value="${loginGroup.eqps['roller'].id}" style="width: 100px"></input>
							</label>
							<label>
							<input id="packereqp" type="text" value="${loginGroup.eqps['packer'].id}" style="width: 100px"  /> 
							</label>
						</c:if>
						<input id="index" type="text" value="" style="width: 100px"  /> 
						</div>
			<span>日期：</span>
			<input id="stim" type="text" class="easyui-datebox" editable="false" required="required"    style="width: 120px"></input>
			<span>至</span>
			<input id="etim" type="text" class="easyui-datebox" editable="false" required="required"    style="width: 120px"></input>
			<span>班组：</span>
			<select class="easyui-combobox " editable="false" required="required" id="state" style="width:150px;">
				<option value="">-请选择-</option>
				<option value="1">甲班</option>
				<option value="2">乙班</option>
				<option value="3">丙班</option>
				<option value="4">丁班</option>
			</select>
		</div> 
	</div>
	<div id="content">
		<div  class="xhtj">
			<div class="maintain">
			<div class="maintainInner">
				<table class="easyui-datagrid " id="roller" title="烟支在线监测结果" style="width:821px;height:460px"
						data-options="rownumbers:true,singleSelect:true,">
					<thead data-options="frozen:true">
						<tr>
							<th data-options="field:'tbth',width:70,align:'center'">批次号</th>
							<th data-options="field:'sdat',width:130,align:'center'">时间</th>
							
						</tr>
					</thead>
					<thead>
						<tr>
						<th colspan="4">重量</th>
						<th colspan="4">长度</th>
						<th colspan="4">圆周</th>
						<th colspan="4">吸阻</th>
						<th colspan="4">圆度</th>
						
						<th colspan="4">通风率</th>
						
						
						</tr>
						<tr>
							<th data-options="field:'wgtmean',width:50,align:'right'">平均值</th>
							<th data-options="field:'wgtmax',width:50,align:'right'">最大值</th>
							<th data-options="field:'wgtmin',width:50,align:'right'">最小值</th>
							<th data-options="field:'wgtsd',width:50,align:'center'">SD</th>
							
							<th data-options="field:'lenmean',width:50,align:'right'">平均值</th>
							<th data-options="field:'lenmax',width:50,align:'right'">最大值</th>
							<th data-options="field:'lenmin',width:50,align:'right'">最小值</th>
							<th data-options="field:'lensd',width:50,align:'center'">SD</th>
							
							
							
							<th data-options="field:'dstmean',width:50,align:'right'">平均值</th>
							<th data-options="field:'dstmax',width:50,align:'right'">最大值</th>
							<th data-options="field:'dstmin',width:50,align:'right'">最小值</th>
							<th data-options="field:'dstsd',width:50,align:'center'">SD</th>
							
							
							<th data-options="field:'pdomean',width:50,align:'right'">平均值</th>
							<th data-options="field:'pdomax',width:50,align:'right'">最大值</th>
							<th data-options="field:'pdomin',width:50,align:'right'">最小值</th>
							<th data-options="field:'pdosd',width:50,align:'center'">SD</th>
							
							
							
							<th data-options="field:'rndmean',width:50,align:'right'">平均值</th>
							<th data-options="field:'rndmax',width:50,align:'right'">最大值</th>
							<th data-options="field:'rndmin',width:50,align:'right'">最小值</th>
							<th data-options="field:'rndsd',width:50,align:'center'">SD</th>
							
							
							
							<th data-options="field:'pdmean',width:50,align:'right'">平均值</th>
							<th data-options="field:'pdmax',width:50,align:'right'">最大值</th>
							<th data-options="field:'pdmin',width:50,align:'right'">最小值</th>
							<th data-options="field:'pdsd',width:50,align:'center'">SD</th>
							<!-- 
							<th data-options="field:'pdommwgmean',width:50,align:'right'">平均值</th>
							<th data-options="field:'pdommwgmax',width:50,align:'right'">最大值</th>
							<th data-options="field:'pdommwgmin',width:50">最小值</th>
							<th data-options="field:'pdommwgsd',width:50,align:'center'">SD</th>
					 -->	</tr>
					</thead>
				</table>
			</div>
			</div>
			<script type="text/javascript">
			var listData;
			var workorderFm;
		
$(function() {
	
/* loadData=function (){
	
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/pmsQm/queryRollerOl_1Data.action",
		dataType:'json',
		onLoadError : function(data) {
			$.messager.alert("提示", "查询烟支在线检概要异常！");
		}
	}); 
}; */
//登录前实例化当前设备
var pageIndex=1;
var id=0;
var allPages=0;
var total2 = 0;
up= function  () {
		if (pageIndex <= 1) {
			jAlert("已经是第一页！","系统提示  :");
			return;
		}
		pageIndex = pageIndex - 1;
		listData(pageIndex);
	};
	down=function  () {
		if(pageIndex>=allPages){
			jAlert("已经是最后一页！","系统提示  :");
			return;
		}
		pageIndex = pageIndex + 1;
		listData(pageIndex);
	};
	
	var cur_eqp =$("#rollereqp").val();
	/* for(var i=0;i<cur_eqp.length;i++){
		if(cur_eqp.item(i).checked){
	 eqpVal=cur_eqp.item(i).value;
	 alert(eqpVal+"2");
		}
	}  */
	/* var cur=$("#cureqps").checked('Value');
	alert(cur); */
	
	//if(cur_eqp!=""||cur_eqp==undefined){
		
		
	
listData=function(pageIndex){
	var dbTestDate=$('#stim').datebox('getValue');
	var edat=$('#etim').datebox('getValue');
	var team = $('#state').combobox('getValue');
	
	$('#roller').datagrid({
		url:"${pageContext.request.contextPath}/pmsQm/queryRollerOl_1Data.action",
			 queryParams:{"bean.sdat":dbTestDate,"date":edat,"mdEquipment":cur_eqp,"mdTeam":team,"pagesize":14,"curpage":pageIndex}, 
			 onLoadSuccess: function(data){
				 pageIndex=data.curpage;
				 total2=data.total;
				 allPages=data.allpage;
				 $("#count").html(total2);
				 $("#allPages").html(allPages);
				 $("#pageIndex").html(pageIndex);
				},

			 onLoadError : function(data) {
				$.messager.alert("提示", "查询烟支在线检概要异常！");
			} 
	});
		},
		workorderFm=function(){
			$('#stim').datebox("setValue","");
			$('#etim').datebox("setValue","");
			$('#state').combobox("setValue","");
			listData(pageIndex);
		};
	

});$(function(){
	//初始化时间
    var today = new Date();
	var month=today.getMonth()+1;
	if(month<10){
		month=("0"+month);			
	}
	var day=today.getDate();
	if(day<10){			
		day=("0"+day);
	}
	
    var date=today.getFullYear()+"-"+month+"-"+day;
	$('#stim').datebox('setValue',date);
	$('#etim').datebox('setValue',date); 
	  });

</script>
			<!-- 分页按钮注意：数据条数为14不会出现竖向滚动条 -->
			<div class="page_nav btn-group pull-right" style="padding-right:15px;">
				<div>
				共<span id="count">0</span>条数据
				<button type="button" class="btn btn-default page_next"   onclick="up();">上一页</button>
				<span id="pageIndex">0</span>/<span id="allPages">0</span>
				<button type="button" class="btn btn-default page_prev" onclick="down();">下一页</button>
				</div>					
			</div>
		</div>
	</div>
</div>
</div>
</div>

</div>


</body>
</html>