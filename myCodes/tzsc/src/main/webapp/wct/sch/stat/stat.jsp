<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>换班记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jq 核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<jsp:include page='../../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../../initlib/initManhuaDate.jsp' ></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>
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
#search_form td{
		font-size:14px;
	}
#hisBadQty-seach-box{
		border: 1px solid #9a9a9a;
		width: 96%;
		margin-left: 10px;
		height: 45px;
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
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
var curpage = 1;
var pagesize = 8;
var allpages = 0;
 $(function(){
	$("input.mh_date").manhuaDate({					       
		Event : "click",//可选				       
		Left : 0,//弹出时间停靠的左边位置
		Top : -16,//弹出时间停靠的顶部边位置
		fuhao : "-",//日期连接符默认为-
		isTime : false,//是否开启时间值默认为false
		beginY : 2010,//年份的开始默认为1949
		endY :2049//年份的结束默认为2049
	});
	var d = new Date();
	d.setTime(d.getTime());
	var month=d.getMonth()+1;
	if(month<10){month=("0"+month);}
	var day=d.getDate();
	var day2=d.getFullYear()+"-" + month + "-" + 1;
	if(day<10){day=("0"+day);}
    var s1 =day2;
    var s2 = d.getFullYear()+"-" + month + "-" + day;
    $("input[name='date1']").val(s1);	//时间用这个
	$("input[name='date2']").val(s2);	//时间用这个
	
	var data2=getJsonData($('#search_form'));
	data2.curpage=curpage;
	data2.pagesize=pagesize;
	getAllOutputs(data2);
	
});
	function getAllOutputs(data){
		$.post("${pageContext.request.contextPath}/wct/stat/getAllOutputs.do?equipmentCode=${loginInfo.equipmentCode}",data,function(json){				
			$("#tab-tbody tr").css("backgroundColor","#DBDBDB");
			getPageInfo(json);
			$("#tab-tbody tr td").html(null);
			var eqmcode=${loginInfo.equipmentCode };
			$("#tab-tbody tr").each(function(idx){
					if(json.rows.length>idx){
						var tr=$(this);
						var value=json.rows[idx];				
						tr.find("td:eq(0)").attr("id",value.id).html(value.workorder);                                 //工单号        
						tr.find("td:eq(1)").html(value.date);                  //时间         
						tr.find("td:eq(2)").html(value.shift);                                //班次         
						tr.find("td:eq(3)").html(value.team);                                 //班组         
						tr.find("td:eq(4)").html(value.mat);                                  //牌号       
						tr.find("td:eq(5)").html((value.qty).toFixed(2)+"/"+(value.badQty).toFixed(2)+value.unit);      //产量/剔除      
						/* if(eqmcode>=1&&eqmcode<=30){//卷烟机 1~30
							tr.find("td:eq(5)").html((value.qty/50).toFixed(2)+"/"+(value.badQty/50).toFixed(2)+value.unit);      //产量/剔除      
						}else if(eqmcode>=31&&eqmcode<=60){ //包装机31~60
							tr.find("td:eq(5)").html((value.qty/2500).toFixed(2)+"/"+(value.badQty/2500).toFixed(2)+value.unit); 
						} */
						tr.find("td:eq(6)").html((value.runTime/3600).toFixed(2)+"/"+(value.stopTime/3600).toFixed(2)+"（h）");                                           //运行/停机(分钟)  
						tr.find("td:eq(7)").html(value.stopTimes+"次");                                           //停机次数      
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
	
	//getAllOutputs({"curpage":curpage,"pagesize":pagesize});
	function up(){
		curpage = parseInt($("#pageIndex").html());
		if(curpage<=1){
			jAlert("已经是第一页","系统提示");
			return;
		}
		curpage=curpage-1;
		var data2=getJsonData($('#search_form'));
		data2.curpage=curpage;
		data2.pagesize=pagesize;
		getAllOutputs(data2);
		//getAllOutputs({"curpage":curpage,"pagesize":pagesize});
	}
	function down(){
		curpage = parseInt($("#pageIndex").html());
		allpages = parseInt($("#allPages").html());
		if(curpage>=allpages){
			jAlert("已经是最后一页","系统提示");
			return;
		}
		curpage=curpage+1;
		var data2=getJsonData($('#search_form'));
		data2.curpage=curpage;
		data2.pagesize=pagesize;
		getAllOutputs(data2);
		//getAllOutputs({"curpage":curpage,"pagesize":pagesize});
	}
	function getAll(){
		var data2=getJsonData($('#search_form'));
		data2.curpage=curpage;
		data2.pagesize=pagesize;
		getAllOutputs(data2);
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
						tr.find("td:eq(2)").html(Math.round(value.qty*100)/100);           
						tr.find("td:eq(3)").html(value.unit);           
						tr.find("td:eq(4)").html(Math.round(value.orignalData*100)/100);      
					}                                                                              
			});                                                                                      
		},"JSON");  
	}
</script>
<div id="title">生产实绩</div>
<div id="hisBadQty-seach-box" >
<form id="search_form" style="margin-top: 5px;">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>	
			<th style="width:76px;text-align:right;">日期&nbsp;&nbsp;</th>
			<td style="width:140px;">
			<input type="text" name="date1" class="mh_date" readonly="readonly" style="width:110px;height:20px;"/>
			</td>
			<th>-</th>
			<td style="width:140px;">
			<input type="text" name="date2"  class="mh_date" readonly="readonly" style="width:110px;height:20px;"/>
			</td>
			<th style="width:76px;text-align:center;">班次</th>
			<td>
				<select name="shiftId" id="shiftId" style="height:20px;">
					<option value>全部</option>
					<option value="1">早班</option>
					<option value="2">中班</option>
					<option value="3">晚班</option>
				</select>
			</td>
			<th style="width:76px;text-align:center;">班组</th>
			<td style="width: 120px;">
				<select name="teamId" id="teamId" style="height:20px;">
					<option value>全部</option>
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>
			</td>
			<td style="width:76px;text-align:center;"><input type="button" onclick="getAll()" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			<td  style="width:86px;text-align:center;"><input type="button" id="hisBadQty-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>


</div>
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
				<td class="t-header">运行/停机</td>
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