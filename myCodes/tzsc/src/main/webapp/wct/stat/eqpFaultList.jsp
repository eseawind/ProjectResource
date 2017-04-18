<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>卷烟机故障停机统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page='../../initlib/initMyAlert.jsp'></jsp:include>
<jsp:include page='../../initlib/initHighcharts.jsp'></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp'></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript"></script>
<style type="text/css">
body {
	background: none;
}

* {
	font-family: "Microsoft YaHei", "宋体", Arial;
}

tr, td {
	font-size: 12px;
	white-space: nowrap;
	align: left;
	font-weight:bold;
}
span{
	padding-left:50px;
}
#hisOut-seach-box{
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
	#hisOut-page{
		width:97%;
		margin-left:10px;
		margin-top:5px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
	}
</style>
<script type="text/javascript">
	var json_return=null;
	var pageIndex=0;
	var allPage=0;
	var count=0;
	var eid="${loginInfo.equipmentId}";
	var eCode="${loginInfo.equipmentCode}";
	var orderType="";//工单类型
	$(function() {
		if(eCode==""){
			JAlert("提示","当前用户未登录，请重新登录");
			return ;
		}
		if(parseInt(eCode)<=30){
			orderType="1";
			$("#wkd-qua-title").html("卷烟机故障停机统计");
		}else if(parseInt(eCode)<=60){
			orderType="2";
			$("#wkd-qua-title").html("包装机故障停机统计");
		}else if(parseInt(eCode)<=70){
			orderType="3";
			$("#wkd-qua-title").html("封箱机故障停机统计");
		}else if(parseInt(eCode)>=101&&parseInt(eCode)<=130){
			orderType="4";
			$("#wkd-qua-title").html("成型机故障停机统计");
		}
		$("input.mh_date").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : false,//是否开启时间值默认为false
			beginY : 2010,//年份的开始默认为1949
			endY :2049//年份的结束默认为2049
		});
		var date_default = new Date();
		date_default.setTime(date_default.getTime());
		var month=date_default.getMonth()+1;
		if(month<10){month=("0"+month);}
		var day=date_default.getDate();
		if(day<10){day=("0"+day);}
	    var s1 =date_default.getFullYear()+"-" + month + "-01";
	    var s2 = date_default.getFullYear()+"-" + month + "-" + day;
	    $("input[name='date1']").val(s1);	//时间用这个
		$("input[name='date2']").val(s2);	//时间用这个
		var showChart = function(div_,name,value1,value2,value3) {
			div_.highcharts({                                          
		        chart: {  
		        	plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					backgroundColor : "#DDDDDD",
					height : 500
					//width : w,
					//type : 'pie'
		        },                                                                
		        title: {                                                          
		            text: ''                                     
		        },                                                                
		        xAxis: {                                                          
		            categories: value1
		        },                                                                
		        tooltip: {                                                        
		            formatter: function() {                                       
		                var s;                                                    
		                if (this.point.name) { // the pie chart                   
		                    s = ''+                                               
		                        this.point.name +': '+ this.y +' fruits';         
		                } else {                                                  
		                    s = ''+                                               
		                        this.x  +': '+ this.y;                            
		                }                                                         
		                return s;                                                 
		            }                                                             
		        },                                                                
		        plotOptions: {
		            series: {
		                dataLabels: {
		                    align: 'center',//数据值显示位置
		                    enabled: true,//是否显示数据值
		                    rotation: 0,//数据值旋转角度
		                    x: 2,//数据值在x轴上位置
		                    y: -10 //数据值在y轴上位置
		                }
		            }
				},                                                            
		        series: [{                                                              
		            type: 'column',                                               
		            name: '停机时长',                                                  
		            data: value2                                         
		        }, {                                                              
		            type: 'spline',                                               
		            name: '停机次数',                                              
		            data: value3,                               
		            marker: {                                                     
		            	lineWidth: 2,                                               
		            	lineColor: Highcharts.getOptions().colors[2],               
		            	fillColor: 'white'                                          
		            }                                                             
		        }]                                                                
		    }); 
		}
		
		$("#hisOut-search").click(function(){
			params=getJsonData($('#search_form'));
			var shiftId=params.shiftId;
			var teamId=params.teamId;
			var startTime="";
			if(params.date1!=null){
				startTime=params.date1;
			}
			var endTime="";
			if(params.date2!=null){
				endTime=params.date2;
			}	
			var where="eid="+eid+"&shiftId="+shiftId+"&teamId="+teamId+"&startTime="+startTime+"&endTime="+endTime+"&orderType="+orderType;
		 	$.post("${pageContext.request.contextPath}/wct/fault/queryFaultChart.do?"+where,function(data){
		 		//removeAllDiv();
		 		json_return=data;
		 		pageIndex=1;
		 		count=data.length;
		 		allPage=data.length%8==0?data.length/8:parseInt(data.length/8+1);
		 		$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPage);
				$("#count").html(count);
				var value1=new Array(); 
				var value2=new Array();
				var value3=new Array();
		 		for(var i=0;i<data.length&&i<10;i++){
		 			if(i!=0){
		 				bean=data[i];
		 				value1[i-1]=bean.name;
		 				value2[i-1]=bean.time;
		 				value3[i-1]=bean.times;
		 			}
		 		}
		 		showChart($("#char1"),"",value1,value2,value3);
			},"JSON"); 
		});
		//重置
		$("#hisBadQty-reset").click(function(){
			$('#search_form input[type!=button]').val(null);
			$('#date1').val("");
			$('#date2').val("");
			$('#shiftId').val("全部");
			$('#teamId').val("全部");
		});
		$("#hisOut-search").click();
	});
		
</script>
<div id="wkd-qua-title">卷烟机故障停机统计</div>
<div id="tab" style="height: 562px;">
<div id="hisOut-seach-box" >
	<form id="search_form" style="margin-top: 0px;">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>		
			<th style="width:76px;text-align:right;">日期&nbsp;&nbsp;</th>
			<td style="width:140px;">
			<input type="text" name="date1" class="mh_date" readonly="readonly" style="width:110px;height:20px;"/>
			</td>
			<th >-</th>
			<td style="width:140px;">
			<input type="text" name="date2"  class="mh_date" readonly="readonly" style="width:110px;height:20px;"/>
			</td>
			<th style="width:76px;text-align:center;">班次</th>
			<td>
				<select name="shiftId" style="height: 20px;">
					<option value="">全部</option>
					<option value="1">早班</option>
					<option value="2">中班</option>
					<option value="3">晚班</option>
				</select>
			</td>
			
			<th style="width:76px;text-align:center;">班组</th>
			<td style="width: 120px;">
				<select name="teamId" style="height: 20px;">
					<option value="">全部</option>
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>
			</td>
			<td style="width:76px;text-align:center;"><input type="button" id="hisOut-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			<td  style="width:86px;text-align:center;"><button type="reset" id="hisOut-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default">重置</button></td>
			</tr>
		</table>
	</form>
</div>
	<table width="100%" height="80%" border="0">
		<tr  height="40%">
			<td>
				<div id='char1'>
				
				</div>
			</td>
		</tr>
		<tr  height="40%">
			<td>
				<div id='char2'>
			
				</div>
			</td>
		</tr>
	</table>
</div>