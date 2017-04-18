<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<html>
<head>
<title>设备运行效率</title>
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
<!-- hightCharts -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highCharts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highCharts/exporting.js"></script>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#eqpFault-title{
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
	#eqpFault-seach-box{
		border: 1px solid #9a9a9a;
		width: 96%;
		margin-left: 10px;
		height: 36px;
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	#eqpFault-wct-frm td{
		font-size:14px;
	}
	#eqpFault-tab{		
		width:100%;
		margin-left:10px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		height: 462px;
	}
	#eqpFault-tab div{	
		margin:2px 0px;
	}
	.t-header{
		text-align:center;
	}
	#eqpFault-tab-thead tr td,#eqpFault-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#eqpFault-page{
		width:97%;
		margin-left:10px;
		margin-top:10px;
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
.eqpFault-div{
	width:200px;
	height:226px;
	float:left;
}
.eqpFault-div2{
	width:200px;
	height:226px;
	float:left;
}
.eqpFault-con{
	width:200px;
	height:210px;
	
}
.eqpFault-tab{
position:relative;
	top:-40px;
	left:10px;
}
</style>
<script type="text/javascript" >
	$(function(){
		var pageIndex=1;
		var allPage=0;
		var params={};
		var order=1;
		var eid="${loginGroup.eqps['roller'].id}";
		var shift="${shift.id}";
		
		var bandParams=function(pageIndex,params){
			$("#eqpFault-tab .eqpFault-div2").remove();
			$.post("${pageContext.request.contextPath}/wctEqp/listFualtByEqp.action?curpage="+pageIndex,params,function(v){
				var obj=eval("("+v+")");
				pageIndex=obj.curpage;
				allPage=obj.allpage;
				$("#pageIndex").html(pageIndex);
				$("#allPage").html(allPage);
				$("#count").html(obj.total);
				 var datas=obj.rows;
				for(var i=0;i<datas.length;i++){
					var data=datas[i];
					var pie="<div  class='eqpFault-div2'>"+
					"<div  class='eqpFault-con' id='container"+i+"'>"+
					"</div>"+
					"<div  class='eqpFault-tab'>"+
					"<div><span>停机时间:</span><span>"+data.stoptime+"</span>&nbsp;<span>分钟</span></div>"+
					"<div><span>停机次数:</span><span>"+data.stoptimes+"</span>&nbsp;<span>次</span></div>"+
					"</div>"+
				"</div>";
				$("#eqpFault-tab").append(pie);
				loadPie(i,data.stoptime,data.tstoptime,data.title); 
				}
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
			if(pageIndex>=allPage){
				return;
			}
			pageIndex=pageIndex+1;
			bandParams(pageIndex,params);
		});
		//设备故障切换显示
		$("#eqpFault-left").click(function(){
			$("#eqpFault-title").html("${loginGroup.eqps['roller'].des}"+"故障停机统计");
			eid="${loginGroup.eqps['roller'].id}";
			params={"bean.eqpid":eid,"bean.shift":shift,"bean.order":order};
			loadTotalFault(params);						
		});
		$("#eqpFault-right").click(function(){
			$("#eqpFault-title").html("${loginGroup.eqps['packer'].des}"+"故障停机统计");
			eid="${loginGroup.eqps['packer'].id}";
			params={"bean.eqpid":eid,"bean.shift":shift,"bean.order":order};
			loadTotalFault(params);			
		});
		$("#eqpFault-time-order,#eqpFault-count-order").click(function(){
			var o=$(this).attr("order");
			order=-(o);
			$(this).attr("order",order);
			params={"bean.eqpid":eid,"bean.shift":shift,"bean.order":order};
			bandParams(1,params);
		});
		var loadTotalFault=function(params){
			$("#eqpFault-tab .eqpFault-div").remove();
			$.post("${pageContext.request.contextPath}/wctEqp/listTotalEqpFault.action",params,function(v){
				var pie="<div  class='eqpFault-div'>"+
							"<div  class='eqpFault-con' id='totalFalut'></div>"+
							"<div  class='eqpFault-tab'>"+
								"<div><span>总运行时间:</span><span>"+v.tstoptime.toFixed(0)+"</span>&nbsp;<span>分钟</span></div>"+
								"<div><span>总停机时间:</span><span>"+v.stoptime.toFixed(0)+"</span>&nbsp;<span>分钟</span></div>"+
								"<div><span>总停机次数:</span><span>"+v.tstoptimes.toFixed(0)+"</span>&nbsp;<span>次</span></div>"+
							"</div>"+ 
						 "</div>";
			$("#eqpFault-tab").append(pie);
				var data=[['停机时间', parseInt(v.stoptime)],['总停机时间',parseInt(v.tstoptime)]];
				new Highcharts.Chart({
		            chart: {
		            	renderTo: 'totalFalut',
		                plotBackgroundColor: null,
		                plotBorderWidth: null,
		                plotShadow: false,
		                backgroundColor:"#DDDDDD"
		            },exporting: {
	    	            enabled: false
		    	     },
		            title: {
		                text: "故障总停机时间统计"
		            },
		            plotOptions: {
		                pie: {
		                    allowPointSelect: false,
		                    cursor: 'pointer',
		                    dataLabels: {
		                        enabled: false
		                    },
		                    showInLegend: true
		                }
		            },
		             credits: {
		            	 text: '',
		            	 href: ''
		        	 },colors: ['#6D6D6D','#6B9FC4'],
		            series: [{
		                type: 'pie',
		                name: '分钟',
		                data: data
		            }],
		        	 legend: {
		        		 layout: 'vertical',
		                 align: 'right',
		                 verticalAlign: 'middle',
		                 borderWidth: 0	                 
		             }
		        });
				bandParams(1,params);
			},"JSON");
		};
		//初始化
		 function loadPie(i,time,total,title){
			$('#container'+i).html("");
			var data=[['当前故障停机', time],['总停机时间',total]];
			new Highcharts.Chart({
	            chart: {
	            	renderTo: 'container'+i,
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false,
	                backgroundColor:"#DDDDDD"
	            },exporting: {
    	            enabled: false
	    	     },
	            title: {
	                text: title
	            },
	            plotOptions: {
	                pie: {
	                    allowPointSelect: false,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: false
	                    },
	                    showInLegend: true
	                }
	            },
	             credits: {
	            	 text: '',
	            	 href: ''
	        	 },colors: ['#6D6D6D','#6B9FC4'],
	            series: [{
	                type: 'pie',
	                name: '秒',
	                data: data
	            }],
	        	 legend: {
	        		 layout: 'vertical',
	                 align: 'right',
	                 verticalAlign: 'middle',
	                 borderWidth: 0	                 
	             }
	        });
		} 
	});
</script>
</head>
<body>
<div id="eqpFault-title">故障停机统计</div>
<div id="eqpFault-seach-box" >
	<form id="eqpFault-wct-frm">
		<table  cellspacing="0" cellpadding="0">
			<tr>
			<c:if test="${groupTypeFlag==1}"><!-- 卷包 -->
				<td >
					<input type="button" id="eqpFault-left" value="${loginGroup.eqps['roller'].des}" eid="${loginGroup.eqps['roller'].id}" style="height:28px;width:120px;" class="btn btn-default"/>
				</td>
				<td width="20"></td>
				<td >				
					<input type="button" id="eqpFault-right" value="${loginGroup.eqps['packer'].des}" eid="${loginGroup.eqps['packer'].id}" style="height:28px;width:120px;" class="btn btn-default"/>
				</td>
				<td style="width:260px"></td>
				<td >
					<input type="button" id="eqpFault-time-order" value="停机时间排序" order="1" style="height:28px;width:120px;" class="btn btn-default"/>
				</td>
				<td width="20"></td>
				<td >				
					<input type="button" id="eqpFault-count-order" value="停机次数排序" order="2" style="height:28px;width:120px;" class="btn btn-default"/>
				</td>			
			</c:if>					
			</tr>
		</table>
	</form>
</div>
<div id="eqpFault-tab" style="height:462px;overflow:hidden;">
	
</div>
<div id="eqpFault-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页" class="btn btn-default"/>
	<span id="pageIndex">0</span>/<span id="allPage">0</span>
    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
   </div>

</body>
</html>