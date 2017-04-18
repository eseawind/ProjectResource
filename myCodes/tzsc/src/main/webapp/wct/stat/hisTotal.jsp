<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>历史合计产量</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jq核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initHighcharts.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>
<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#hisOut-title{
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
	#search_form td{
		font-size:14px;
	}
	#hisOut-tab{		
		width:97%;
		margin-left:10px;
		height:auto;
		margin-top:5px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
	}
	.t-header{
		text-align:center;
	}
	#hisOut-tab-thead tr td,#hisOut-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#hisOut-page{
		width:97%;
		margin-left:10px;
		margin-top:5px;
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
	#up,#down,.od{
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
<script type="text/javascript">
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
	
	//初始化时间
	 //设置默认日期
	var today = new Date();
	var month=today.getMonth()+1;
	if(month<10){month=("0"+month);}
	var day=today.getDate();
	if(day<10){day=("0"+day);}
	var defaultlDate=today.getFullYear()+"-"+month+"-"+day;
	//前7天
	var dateOld = new Date(today.getTime() - 6 * 24 * 3600 * 1000);
	var yearOld = dateOld.getFullYear();
	var monthOld = dateOld.getMonth() + 1;
	if(monthOld<10){monthOld=("0"+monthOld);}
	var dayOld = dateOld.getDate();
	if(dayOld<10){dayOld=("0"+dayOld);}
	var defaultlDateOld=yearOld + '-' + monthOld + '-' + dayOld;
    $("input[name='date1']").val(defaultlDateOld);	//时间用这个
	$("input[name='date2']").val(defaultlDate);	//时间用这个
	
	
	var pageIndex=1;
	var allPage=0;
	var params={};
	var order=-1;
	var bandParams=function(pageIndex,params,od){
		$.post("${pageContext.request.contextPath}/wct/stat/getHisTotal.do?eqpCod=${loginInfo.equipmentId}&orderFlag="+od+"&pageIndex="+pageIndex,params,function(obj){				
			var lables=obj.lables;
			var values=obj.values;
			var values2=obj.values2;
			var unit=obj.unit;
			pageIndex=obj.pageIndex;
			allPage=obj.allPage;
			$("#pageIndex").html(pageIndex);
			$("#allPages").html(allPage);
			$("#count").html(obj.count);
			loadView(unit,lables,values,values2);
		},"JSON");
	};
	//初始化数据
	params=getJsonData($('#search_form'));
	bandParams(1,params,order);
	
	
	$("#up").click(function(){
		if(pageIndex<=1){
			return;
		}
		pageIndex=pageIndex-1;
		bandParams(pageIndex,params,order);
	});
	
	$("#down").click(function(){
		if(pageIndex>=allPage){
			return;
		}
		pageIndex=pageIndex+1;
		bandParams(pageIndex,params,order);
	});
	$("#hisOut-search").click(function(){
		params=getJsonData($('#search_form'));
		bandParams(1,params,order);
	});
	$("#hisOut-reset").click(function(){
		$('#search_form input[type!=button]').val(null);
		//bandParams(1,params,order);
			loadView("",[],[],[]);
	});
	var loadView=function(unit,lables,values,values2){
		$('#hisOut').highcharts({
			credits: {
            	 text: 'lanbaoit',
            	 href: 'http://www.shlanbao.cn'
        		 },
        		 exporting: {
     	            enabled: false
     	        },
            chart: {
                type: 'column',
                backgroundColor:'#f1f1f1'
            },
            colors:["#00A1F5","#52CF29"],
            title: {
                text: '历史产量统计'
            },
            xAxis: {
                categories: lables,
				labels: {
	                    //rotation: -45,
	                    align: 'center',
	                    color: 'black',
	                    style: {
	                        fontSize: '10px',
	                        fontFamily: 'Verdana, sans-serif'
	                    }
	                }
            },
            credits: {
                enabled: false
            },
			yAxis: {
                min: 0,
                title: {
                    text: '产量('+unit+')'
                }
            },
            tooltip: {
            	enabled: false
            },
            series: [{
                name: '计划产量',
                data:values,
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: 'black',
                    align: 'center',
                    x: 4,
                    y: 0,
                    style: {
                        fontSize: '12px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            }, {
                name: '实际产量',
                data: values2,
				dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: 'black',
                    align: 'center',
                    x: 4,
                    y: 0,
                    style: {
                        fontSize: '12px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            }]
        });
	}; 
	
	$("#date-od,#shift-od,#team-od,#out-od").click(function(){
		//var o=$(this).attr("od");
		var o=$(this).attr("order");
		order=-(o);
		$(this).attr("order",order);
		bandParams(1,params,order);
	});
	
	loadView("",[],[],[]);
});
</script>
<div id="hisOut-title">设备历史产量</div>
<div id="hisOut-seach-box" >
	<form id="search_form" style="margin-top: 5px;">
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
			<td  style="width:86px;text-align:center;"><input type="button" id="hisOut-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="hisOut-tab" style="height:462px;overflow:hidden;">
	<div id="hisOut" style="min-width: 310px; height: 460px; margin: 0 auto"></div>
</div>
<div id="hisOut-page">
	<div style="float:left">
	     <input id="date-od" order="1" type="button"  value="时间排序" class="btn btn-default od"/>
	     <input id="shift-od" order="2" type="button"  value="班次排序" class="btn btn-default od"/>
	     <input id="team-od" order="3" type="button"  value="班组排序" class="btn btn-default od"/>
	     <input id="out-od" order="4" type="button"  value="产量排序" class="btn btn-default od"/>
	</div>
	<div style="float:right">
	           共<span id="count">0</span>条数据
		<input id="up" type="button"  value="上一页" class="btn btn-default"/>
		<span id="pageIndex">0</span>/<span id="allPages">0</span>
	    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
	</div>
</div>
