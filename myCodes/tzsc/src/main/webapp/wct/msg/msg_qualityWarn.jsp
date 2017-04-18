<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  id="easyuiTheme" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<%-- <script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.cookie.js"charset="utf-8"></script> --%>
<link id="easyuiTheme" rel="stylesheet" type="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/gray/easyui.css">
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/changeEasyuiTheme.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/JsUtil.js" charset="utf-8"></script>

<script type="text/javascript"	src="${pageContext.request.contextPath}/js/BandSelect.js" charset="utf-8"></script>
<style type="text/css">
	#msg-qua-title{
		font-size:26px;
		font-weight:400;
		text-align:center;
		padding-top:8px;
	}
	#msg-qua-seach-box{
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
	#msg-qua-tab{		
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
	#msg-qua-tab-thead tr td,#msg-qua-tab-tbody tr td{
		/* padding:7px; */
		height:30px;
		text-align:center;
	}
	#msg-qua-page{
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
			$.post("${pageContext.request.contextPath}/qualityWarnActionWct!getAllWarn?pageIndex="+pageIndex,params,function(v){				
				var reobj=eval("("+v+")");
				var list=reobj.list;
				pageIndex=reobj.pageIndex;
				allPages=reobj.allPages;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.count);
				clearParams();
				$("#msg-qua-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							/*车间 机台 车间</t
									机台号</
									>质量指标
									>告警内容
									告警时间<*/
							tr.find("td:eq(0)").attr("id",revalue.qid).attr("details",revalue.warnContent);
							tr.find("td:eq(1)").html(revalue.location);
							tr.find("td:eq(2)").html(revalue.equipment);
							tr.find("td:eq(3)").html(revalue.qualityFlag);
							tr.find("td:eq(4)").html(revalue.retedQualityVal);
							tr.find("td:eq(5)").html(revalue.curQualityVal);
							tr.find("td:eq(6)").css({"fontSize":"12px"}).html(revalue.showTime);
						}
				});
			});
		};
		var clearParams=function(){
			$("#details").html("");
			$("#msg-qua-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(0)").attr("details",null);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3)").html(null);
					tr.find("td:eq(4)").html(null);
					tr.find("td:eq(5)").html(null);
					tr.find("td:eq(6)").html(null);
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
		$("#msg-qua-tab-tbody tr").click(function(){
			var det=$(this).find("td:eq(0)").attr("details");
			$("#details").html(det);
			var qid=$(this).find("td:eq(0)").attr("id");
			alert(qid);
			$.post("${pageContext.request.contextPath}/qualityWarnActionWct!readWarn?warn.qid="+qid,function(){});
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
		
	});
	//根据车间找机台
	function getEqubyLoc(){
		queryEquipment("quality-location","quality-equipment");
	}
</script>
</head>
<body>
<div id="msg-qua-title">质量告警</div>
<div id="msg-qua-seach-box" >
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
<div id="msg-qua-tab" >
	<table border="1" borderColor="white" style="BORDER-COLLAPSE:collapse;" width="100%" cellspacing="0" cellpadding="0">
		<thead id="msg-qua-tab-thead" style="background:#999999;">
			<tr>
				<td class="t-header" width="25"></td>
				<td class="t-header" width="50">车间</td>
				<td class="t-header" width="50">机台号</td>
				<td class="t-header" width="120">质量指标</td>
				<td class="t-header" width="100">标准值</td>
				<td class="t-header" width="100">当前值</td>
				<td class="t-header" width="120">告警时间</td>
			</tr>
		</thead>
		<tbody id="msg-qua-tab-tbody">
			<tr>
				<td style="text-align:center;">1</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">3</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">5</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">6</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">7</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">8</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">9</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align:left:"></td>
				<td></td>
			</tr>										
		</tbody>
	</table>
</div>
<div id="details">
</div>
<div id="msg-qua-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页"/>
	<span id="pageIndex">0</span>/<span id="allPages">0</span>
    <input id="down" type="button"  value="下一页"/>
   </div>

</body>
</html>