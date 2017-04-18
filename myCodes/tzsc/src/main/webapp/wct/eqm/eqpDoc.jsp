<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设备轮保</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  id="easyuiTheme" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<link id="easyuiTheme" rel="stylesheet" type="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/gray/easyui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/JsUtil.js" charset="utf-8"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/BandSelect.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/bootCSS/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>

<!-- wctAlert -->
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.js" type="text/javascript"></script>

<link media="screen" href="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.css" type="text/css" rel="stylesheet"><!-- Example script -->

<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#eqpDoc-title{
		font-size: 20px;
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
	#eqpDoc-seach-box{
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
	#eqpDoc-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
	#eqpDoc-tab{		
		width:824px;
		margin: 0 auto;	
		height:auto;
		margin-top:5px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
		border: 1px solid #858484;
		border-radius: 4px;		
	}
	.t-header{
		text-align:center;
	}
	#eqpDoc-tab-thead tr td,#eqpDoc-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#eqpDoc-page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:12px;
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
		padding:3px 2px;
		width:70px;
		font-weight:bold;
		font-size:12px;
		cursor:pointer;
	}
	.btn-default {
color: #FFFFFF;
background-color: #5A5A5A;
border-color: #cccccc;
}

.btn {
  display: inline-block;
  padding:0px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.428571429;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
       -o-user-select: none;
          user-select: none;
}
</style>
<script type="text/javascript" >
	$(function(){
		/*
		下拉框
		*/
		var pageIndex=1;
		var allPages=0;
		var params={};
		var bandParams=function(pageIndex,params){
			$.post("${pageContext.request.contextPath}/wctDoc/listEqpDocs.action?curpage="+pageIndex,function(v){				
				clearParams();
				var reobj=eval("("+v+")");
				var list=reobj.rows;
				pageIndex=reobj.curpage;
				allPages=reobj.allpage;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.total);
				$("#eqpDoc-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];							
							tr.find("td:eq(1)").html(revalue.fullName);
							tr.find("td:eq(2)").html(revalue.createTimeFmt);
							tr.find("td:eq(3) input").attr("onclick","showFile("+revalue.id+")");
						}
				});
			});
		};
		$("#up").click(function(){
			if(pageIndex<=1){
				return;
			}
			pageIndex=pageIndex-1;
			bandParams(pageIndex,params);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPages){
				return;
			}
			pageIndex=pageIndex+1;
			bandParams(pageIndex,params);
		});
		bandParams(1,params);
		var clearParams=function(){
			$("#eqpDoc-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3) input").attr("onclick","");
			});
		};
	});
	function showFile(id){
		location="${pageContext.request.contextPath}/wctDoc/showADocument.action?file.id="+id;
	}
</script>
</head>
<body>
<div id="eqpDoc-title">设备文档</div>
<!-- <div id="eqpDoc-seach-box" >
	<form id="eqpDoc-wct-frm">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>		
			<td>文档名称</td>			
			<td style="width:200px">
			<input class="easyui-validatebox" 
			       editable="false" 
			       style="border:1px solid #9a9a9a;height:28px;width:160px;"
			/>
			</td>
			<td><input type="button" id="eqpDoc-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			<td><input type="button" id="eqpDoc-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div> -->
<div id="eqpDoc-tab" style="height:472px;overflow:auto;">
	<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="800" height="474" cellspacing="0" cellpadding="0">
		<thead id="eqpDoc-tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header" style="width:20px"></td>
				<td class="t-header" style="width:130px">文档名称</td>
				<td class="t-header" style="width:100px">上传日期</td>
				<td class="t-header" style="width:50px">操作</td>
			</tr>
		</thead>
		<tbody id="eqpDoc-tab-tbody">
			<tr>
				<td>1</td>
				<td style="text-align:left"></td>
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>2</td>
				<td style="text-align:left"></td>
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>3</td>
				<td style="text-align:left"></td>
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>4</td>
				<td style="text-align:left"></td>				
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>5</td>
				<td style="text-align:left"></td>				
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>6</td>
				<td style="text-align:left"></td>				
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>7</td>
				<td style="text-align:left"></td>				
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>8</td>
				<td style="text-align:left"></td>				
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>9</td>
				<td style="text-align:left"></td>		
		        <td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
			<tr>
				<td>10</td>
				<td style="text-align:left"></td>
				<td></td>
				<td><input type="button" value="查看" class="btn btn-default"/></td>
			</tr>
		</tbody>
	</table>
</div>
<div id="eqpDoc-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页" class="btn btn-default"/>
	<span id="pageIndex">0</span>/<span id="allPages">0</span>
    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
   </div>

</body>
</html>