<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>换班记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jq 核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<jsp:include page='../../../initlib/initMyAlert.jsp' ></jsp:include>
<style type="text/css">
*{font-family: "Microsoft YaHei","宋体",Arial;}
#title{
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
#tab{		
	width: 824px;
	height: 426px;
	margin: 0 auto;
	font-size: 14px;
	font-weight: bold;
	overflow: hidden;
	position: relative;
	border: 1px solid #858484;
	border-radius: 4px;
	margin-top: 5px;
}
.t-header{
	text-align:center;
	font-size:14px;
}
#tab-thead tr td{	
	height:23px;
	text-align:center;
}
#tab-tbody tr td{	
	height:28px;
	text-align:center;
	font-size:12px;
}
.t-header2{
	text-align:center;
	font-size:14px;
}
#tab-thead2 tr td{	
	height:23px;
	text-align:center;
}
#tab-tbody2 tr td{	
	height:20px;
	text-align:center;
	font-size:14px;
}
.sub-button{
	border: 1px solid #9a9a9a;
	padding: 3px 2px;
	width: 80px;
	font-weight: bold;
	font-size: 12px;
	cursor: pointer;
}
.btn-default {
	color: #FFFFFF;
	background-color: #5A5A5A;
	border-color: #cccccc;
}
.btn {
	display: inline-block;
	margin-bottom: 0;
	font-size: 12px;
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
.scroll_box{
	height: 395px;
	position: absolute;
	left: 0px;
	padding:2px;	
}
#page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
		padding-top:4px;
	}
.red{color:red;}
.bold{font-weight:bold;}
.font14{font-size:14px;}
</style>
<script type="text/javascript">
	function getAllOutputs(data){
		$.post("${pageContext.request.contextPath}/wct/stat/getAllOutputs.do?equipmentCode=1",data,function(json){				
			$("#tab-tbody tr").css("backgroundColor","#DBDBDB");
			getPageInfo(json);
			$("#tab-tbody tr td").html(null);
			$("#tab-tbody tr").each(function(idx){
					if(json.rows.length>idx){
						var tr=$(this);
						var value=json.rows[idx];				
						tr.find("td:eq(0)").attr("id",value.id).html(value.workorder);                                 //工单号        
						tr.find("td:eq(1)").html(value.date);                  //时间         
						tr.find("td:eq(2)").html(value.shift);                                //班次         
						tr.find("td:eq(3)").html(value.team);                                 //班组         
						tr.find("td:eq(4)").html(value.mat);                                  //牌号         
						tr.find("td:eq(5)").html(value.qty+"/"+value.badQty+value.unit);      //产量/剔除      
						tr.find("td:eq(6)").html(value.runTime+"/"+value.stopTime);                                           //运行/停机(分钟)  
						tr.find("td:eq(7)").html(value.stopTimes);                                           //停机次数      
						tr.find("td:eq(8)").html(value.isFeedback);												//反馈  
						tr.find("td:eq(9)").html(value.feedbackUser);												//反馈用户
						tr.find("td:eq(10)").html(value.feedbackTime);												//反馈时间
					}                                                                              
			});                                                                                      
		},"JSON");                                                                                   
	}
	function getPageInfo(json){
		$("#pageIndex").html(json.curpage);
		$("#allPages").html(json.allpage);
		$("#count").html(json.total);
	}
	var curpage = 1;
	var pagesize = 10;
	var allpages = 0;
	getAllOutputs({"curpage":curpage,"pagesize":pagesize});
	function up(){
		curpage = parseInt($("#pageIndex").html());
		if(curpage<=1){
			jAlert("已经是第一页","系统提示");
			return;
		}
		curpage=curpage-1;
		getAllOutputs({"curpage":curpage,"pagesize":pagesize});
	}
	function down(){
		curpage = parseInt($("#pageIndex").html());
		allpages = parseInt($("#allPages").html());
		if(curpage>=allpages){
			jAlert("已经是最后一页","系统提示");
			return;
		}
		curpage=curpage+1;
		getAllOutputs({"curpage":curpage,"pagesize":pagesize});
	}
	$(function(){
		$("#tab-tbody tr").click(function(){
			$("#tab-tbody tr").css("background","");
			$(this).css("background","#bebebe");
			getAllInputsByOutput($(this).find("td:eq(0)").attr("id"));
		});
	});
	function getAllInputsByOutput(id){
		$.post("${pageContext.request.contextPath}/wct/stat/getAllInputsByOutput.do",{"id":id},function(json){				
			$("#tab-tbody2 tr td").html(null);
			$("#tab-tbody2 tr").each(function(idx){
					if(json.length>idx){
						var tr=$(this);
						var value=json[idx];				
						tr.find("td:eq(0)").html(idx+1);
						tr.find("td:eq(1)").html(value.mat);                                  //牌号         
						tr.find("td:eq(2)").html(value.qty);           
						tr.find("td:eq(3)").html(value.unit);           
						tr.find("td:eq(4)").html(value.orignalData);      
					}                                                                              
			});                                                                                      
		},"JSON");  
	}
</script>
<div id="title">生产实绩</div>
<div id="tab" style="height:562px;">
<div  class="scroll_box" id="scroll_box">
	<table border="1" borderColor="#9a9a9a" 
		style="BORDER-COLLAPSE:collapse;width:820px;"  cellspacing="0" cellpadding="0">
		<thead id="tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header">工单号</td>
				<td class="t-header">日期</td>
				<td class="t-header">班次</td>
				<td class="t-header">班组</td>
				<td class="t-header">牌号</td>
				<td class="t-header">产量/剔除</td>
				<td class="t-header">运行/停机(分钟)</td>
				<td class="t-header">停机次数</td>
				<td class="t-header">反馈</td>
				<td class="t-header">反馈用户</td>
				<td class="t-header">反馈时间</td>
			</tr>
		</thead>
		<tbody id="tab-tbody">
			 <tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr>
			<tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
				<td></td>			
			</tr><tr >
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
	<div id="page">
	             共<span id="count">0</span>条数据
		<input id="up" onclick="up();" type="button"  value="上一页" class="btn btn-default"/>
		<span id="pageIndex">0</span>/<span id="allPages">0</span>
	    <input id="down" onclick="down();" type="button"  value="下一页" class="btn btn-default"/>
	</div>
	<div>
		<table border="1" borderColor="#9a9a9a" 
		style="BORDER-COLLAPSE:collapse;width:820px;margin-top:10px"  cellspacing="0" cellpadding="0">
		<thead id="tab-thead2" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header2"></td>
				<td class="t-header2" >物料名称</td>
				<td class="t-header2" >消耗数量</td>
				<td class="t-header2" >单位</td>
				<td class="t-header2" >原始计数</td>
			</tr>
		</thead>
		<tbody id="tab-tbody2">
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
			 <tr>
				<td style="width:30px"></td>
				<td style="width:350px"></td>
				<td style="width:100px"></td>
				<td style="width:80px"></td>
				<td ></td>		
			 </tr>
		</tbody>
	</table>
	</div>
</div>
</div>