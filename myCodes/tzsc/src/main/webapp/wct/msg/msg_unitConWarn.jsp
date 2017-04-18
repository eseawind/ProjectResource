<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  id="easyuiTheme" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.cookie.js"charset="utf-8"></script>
<link id="easyuiTheme" rel="stylesheet" type="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/gray/easyui.css">
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/changeEasyuiTheme.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/JsUtil.js" charset="utf-8"></script>

<script type="text/javascript"	src="${pageContext.request.contextPath}/js/BandSelect.js" charset="utf-8"></script>
<title>质量告警</title>
<style type="text/css">
	#msg-unitcon-title{
		font-size:26px;
		font-weight:400;
		text-align:center;
		padding-top:8px;
	}
	#msg-unitcon-seach-box{
		border:2px solid #9a9a9a;
		width:96%;
		margin-left:10px;
		height:36px;
		margin-top:15px;
		font-size:14px;
		font-weight:bold;
		padding-top:8px;
		padding-left:5px;
	}
	#msg-wct-frm td{
		font-size:17px;
	}
	#msg-unitcon-tab{		
		width:97%;
		margin-left:10px;
		height:auto;
		margin-top:15px;
		font-size:14px;
		font-weight:bold;
	}
	.t-header{
		text-align:center;
	}
	#msg-unitcon-tab-thead tr td,#msg-unitcon-tab-tbody tr td{
		height:30px;
		text-align:center;
	}
	#msg-unitcon-page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:14px;
		font-weight:bold;
		text-align:right;
		padding-top:4px;
	}
	#details{
		border:2px solid #dddddd;
		width:96%;
		margin-left:10px;
		height:80px;
		margin-top:5px;
		padding:2px;
		text-indent:15px;
	}
	#up,#down{
		border:1px solid #9a9a9a;
		padding:5px 2px;
		width:100px;
		font-weight:bold;
		font-size:20px;
		cursor:pointer;
	}
</style>
<script type="text/javascript" src="">
	$(function(){
		queryWorkShop("quality-location");
			
		var pageIndex=1;
		var allPages=0;
		var params={};
		var bandParams=function(pageIndex,params){
			$.post("${pageContext.request.contextPath}/unitConWarnActionWct!getAllWarn?pageIndex="+pageIndex,params,function(v){				
				var reobj=eval("("+v+")");
				var list=reobj.list;
				pageIndex=reobj.pageIndex;
				allPages=reobj.allPages;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.count);
				clearParams();
				$("#msg-unitcon-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							/*
								0<td class="t-header" width="25"></td>
								1<td class="t-header" width="50">车间</td>
								2<td class="t-header" width="30">机台号</td>
								3<td class="t-header" width="50">牌号</td>
								4<td class="t-header" width="60">辅料名称</td>
								5<td class="t-header" width="120">额定单耗</td>
								6<td class="t-header" width="120">当前单耗</td>
								7<td class="t-header" width="100">告警时间</td>6>告警时间<
							*/
							tr.find("td:eq(0)").attr("id",revalue.ucwId).attr("details",revalue.warnContent);
							tr.find("td:eq(1)").html(revalue.location);
							tr.find("td:eq(2)").html(revalue.equipment);
							tr.find("td:eq(3)").html(revalue.materialNo);
							tr.find("td:eq(4)").html(revalue.materialId);
							tr.find("td:eq(5)").html(revalue.ratedUnitCon);
							tr.find("td:eq(6)").html(revalue.curUnitCon);
							tr.find("td:eq(7)").css({"fontSize":"12px"}).html(revalue.showTime);
						}
				});
			});
		};
		var clearParams=function(){
			$("#details").html("");
			$("#msg-unitcon-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(0)").attr("details",null);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3)").html(null);
					tr.find("td:eq(4)").html(null);
					tr.find("td:eq(5)").html(null);
					tr.find("td:eq(6)").html(null);
					tr.find("td:eq(7)").html(null);
					$("#details").html(null);
			});
		};
		
		$("#up").click(function(){
			if(pageIndex<=1){
				alert("已经是第一页");
				return;
			}
			pageIndex=pageIndex-1;
			bandParams(pageIndex,params);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPages){
				alert("已经是最后一页");
				return;
			}
			pageIndex=pageIndex+1;
			bandParams(pageIndex,params);
		});
		$("#msg-unitcon-tab-tbody tr").click(function(){
			var det=$(this).find("td:eq(0)").attr("details");
			var id=$(this).find("td:eq(0)").attr("id");
			$.post("${pageContext.request.contextPath}/unitConWarnActionWct!updateWarnState?warn.ucwId="+id,function(){});
			$("#details").html(det);
		});
		$("#msg-search").click(function(){
			var wk=$("#quality-location").combobox("getText");
			$("input[name='warn.location']").val(wk);
			params=serializeObject($('#msg-wct-frm'));
			bandParams(1,params);
		});
		$("#msg-reset").click(function(){
			document.getElementById("msg-wct-frm").reset();
			$("input[name='warn.timeStart']").val("");
			$("input[name='warn.timeEnd']").val("");
			$("input[name='warn.equipment']").val("");
			params={};
			bandParams(1,params);
		});
		clearParams();
		bandParams(1,params);	
		//根据车间找机台
		function getEqubyLoc(){
			queryEquipment("quality-location","quality-equipment");
		}
	});
</script>
</head>
<body>
<div id="msg-unitcon-title">单耗告警</div>
<div id="msg-unitcon-seach-box" >
	<form action="" id="msg-wct-frm">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
			<td>车间</td>
			<td>
				<select id="quality-location" name="warn.location" class="easyui-combobox" panelHeight="auto" editable="false" style="width:100px;height:28px;"
	            		data-options="editable:false,valueField:'id',  
	                         textField: 'text',onChange:function(){getEqubyLoc();}">
	            </select>
			</td>
			<td>机台</td>
			<td>
				<select id="quality-equipment" name="warn.equipment" class="easyui-combobox" panelHeight="auto" editable="false" style="width:100px;height:28px;"
            		data-options="editable:false,valueField:'text',  
                         textField: 'text'">
                </select>
			</td>
			<td>时间</td>
			<td><input class="easyui-datebox" editable="false" style="border:1px solid #9a9a9a;height:28px;width:120px;"
			name="warn.timeStart"
			/></td>
			<td>到</td>
			<td><input class="easyui-datebox" editable="false" style="border:1px solid #9a9a9a;height:28px;width:120px;"
			name="warn.timeEnd"
			/></td>
			<td><input type="button" id="msg-search" value="查询" style="height:28px;width:50px;"/></td>
			<td><input type="button" id="msg-reset" value="重置" style="height:28px;width:50px;"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="msg-unitcon-tab" >
	<table border="1" borderColor="white" style="BORDER-COLLAPSE:collapse;" width="100%" cellspacing="0" cellpadding="0">
		<thead id="msg-unitcon-tab-thead" style="background:#999999;">
			<tr>
				<td class="t-header" width="25"></td>
				<td class="t-header" width="50">车间</td>
				<td class="t-header" width="30">机台</td>
				<td class="t-header" width="60">牌号</td>
				<td class="t-header" width="60">辅料名称</td>
				<td class="t-header" width="100">额定单耗</td>
				<td class="t-header" width="100">当前单耗</td>
				<td class="t-header" width="120">告警时间</td>
			</tr>
		</thead>
		<tbody id="msg-unitcon-tab-tbody">
			<tr>
				<td>1</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>3</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>5</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>6</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>7</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>8</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>9</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>
<div id="details">
</div>
<div id="msg-unitcon-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页"/>
	<span id="pageIndex">0</span>/<span id="allPages">0</span>
    <input id="down" type="button"  value="下一页"/>
</div>

</body>
</html>