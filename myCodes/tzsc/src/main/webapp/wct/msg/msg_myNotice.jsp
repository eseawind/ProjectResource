<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>我的通知</title>
<link rel="stylesheet"  id="easyuiTheme" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.cookie.js"charset="utf-8"></script>
<link id="easyuiTheme" rel="stylesheet" type="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/gray/easyui.css">
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/changeEasyuiTheme.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/JsUtil.js" charset="utf-8"></script>

<style type="text/css">
	#msg-mng-title{
		font-size:26px;
		font-weight:400;
		text-align:center;
		padding-top:8px;
	}
	#msg-mng-seach-box{
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
	#msg-mng-tab{		
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
	#msg-mng-tab-thead tr td,#msg-mng-tab-tbody tr td{
		text-align:center;
		height:30px;
	}
	#msg-mng-page{
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
		width:91.1%;
		margin-left:10px;
		height:80px;
		margin-top:5px;
		padding:4px 40px 0px 4px;
		overflow-y:scroll;
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
		
		
		var pageIndex=1;
		var allPages=0;
		var params={};
		var bandParams=function(pageIndex,params){
			$.post("${pageContext.request.contextPath}/noticeActionWct!getNotice?pageIndex="+pageIndex,params,function(v){				
				var reobj=eval("("+v+")");
				var list=reobj.list;
				pageIndex=reobj.pageIndex;
				allPages=reobj.allPages;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.count);
				clearParams();
				$("#msg-mng-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							tr.find("td:eq(0)").attr("id",revalue.mid).attr("details","<span style='font-weight:bold'>"+revalue.MTitle+"</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;"+revalue.MContent);
							tr.find("td:eq(1)").css({"textAlign":"left","textIndent":"5px"}).html(revalue.showTitle2);
							tr.find("td:eq(2)").css({"fontSize":"12px"}).html(revalue.showTime);
							tr.find("td:eq(3)").html(revalue.uname);
							tr.find("td:eq(4)").html(revalue.MState);
						}
				});
			});
		};
		var clearParams=function(){
			$("#details").html("");
			$("#msg-mng-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(0)").attr("details",null);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3)").html(null);
					tr.find("td:eq(4)").html(null);
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
		$("#msg-mng-tab-tbody tr").click(function(){
			var tr=$(this);
			var det=tr.find("td:eq(0)").attr("details");
			$("#details").html(det);
			var mid=$(this).find("td:eq(0)").attr("id");
			if(tr.find("td:eq(4)").html()!="已读"&&mid!=undefined){
				$.post("${pageContext.request.contextPath}/noticeActionWct!updateNoticeState?message.mid="+mid,function(){
					tr.find("td:eq(4)").html("已读");
				});
			}
				
		});
		$("#msg-search").click(function(){
			params=serializeObject($('#msg-wct-frm'));
			bandParams(1,params);
		});
		$("#msg-reset").click(function(){
			document.getElementById("msg-wct-frm").reset();
			$("input[name='message.timeStart']").val("");
			$("input[name='message.timeEnd']").val("");
			params={};
			bandParams(1,params);
		});
		clearParams();
		bandParams(pageIndex,params);
	});
</script>
</head>
<body>
<div id="msg-mng-title">我的通知</div>
<div id="msg-mng-seach-box" >
	<form action="" id="msg-wct-frm">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
			<td>标题</td>
			<td><input style="border:1px solid #9a9a9a;height:25px;width:100px;font-size:20px;"
				name="message.MTitle"
			/></td>
			<td>发送人</td>
			<td><input style="border:1px solid #9a9a9a;height:25px;width:100px;font-size:20px;"
			name="message.uname"
			/></td>
			<td>时间</td>
			<td><input class="easyui-datebox" editable="false" style="border:1px solid #9a9a9a;height:29px;width:120px;"
			name="message.timeStart"
			/></td>
			<td>到&nbsp;</td>
			<td><input class="easyui-datebox" editable="false" style="border:1px solid #9a9a9a;height:29px;width:120px;"
			name="message.timeEnd"
			/></td>
			<td><input type="button" id="msg-search" value="查询" style="height:29px;width:50px;"/></td>
			<td><input type="button" id="msg-reset" value="重置" style="height:29px;width:50px;"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="msg-mng-tab" >
	<table borderColor="white" border="1" width="100%" cellspacing="0" cellpadding="0" style="BORDER-COLLAPSE:collapse;">
		<thead id="msg-mng-tab-thead" style="background:#999999;">
			<tr>
			    <td class="t-header" width="25"></td>
				<td class="t-header" width="260">标题</td>
				<td class="t-header" width="120">时间</td>
				<td class="t-header" width="80">发送人</td>
				<td class="t-header" width="80">状态</td>
			</tr>
		</thead>
		<tbody id="msg-mng-tab-tbody">
			<tr>
				<td style="text-align:center;">1</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:center;">2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">3</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">5</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">6</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">7</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">8</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">9</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr><tr>
				<td style="text-align:center;">10</td>
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
	<div id="msg-mng-page">
		共<span id="count">0</span>条数据
		<input id="up" type="button"  value="上一页"/>
		<span id="pageIndex">0</span>/<span id="allPages">0</span>
	    <input id="down" type="button" value="下一页"/>
    </div>

</body>
</html>