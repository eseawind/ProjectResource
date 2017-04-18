<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>设备综合利用率</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jq 核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initHighcharts.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#hisBadQty-title{
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
	#search_form td{
		font-size:14px;
	}
	#hisBadQty-tab{		
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
	#hisBadQty-tab-thead tr td,#hisBadQty-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#hisBadQty-page{
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
<script type="text/javascript" >
	$(function(){
		var type=1;
		var typeName="机台";
		$("input.mh_date").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : false,//是否开启时间值默认为false
			beginY : 2010,//年份的开始默认为1949
			endY :2049//年份的结束默认为2049
		});
		
		var pageIndex=1;
		var allPage=0;
		var params={};
		var order=-1;
		var bandParams=function(pageIndex,params,od,type){
			$.post("${pageContext.request.contextPath}/wct/eqpRun/getEqpUtilizationData.do?eqpCod=${loginInfo.equipmentCode}&pageIndex="+pageIndex,params,function(obj){				
				var lables=obj.lables;
				var values=obj.values;
				var unit=obj.unit;
				pageIndex=obj.pageIndex;
				allPage=obj.allPage;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPage);
				$("#count").html(obj.count);
				loadView(unit,lables,values);
			},"JSON");
		};
		$("#up").click(function(){
			if(pageIndex<=1){
				return;
			}
			pageIndex=pageIndex-1;
			bandParams(pageIndex,params,order,type);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPage){
				return;
			}
			pageIndex=pageIndex+1;
			bandParams(pageIndex,params,order,type);
		});
		$("#hisBadQty-search").click(function(){
			params=getJsonData($('#search_form'));
			bandParams(1,params,order,type);
			
		});
		$("#hisBadQty-reset").click(function(){
			$('#search_form input[type!=button]').val(null);
			$('#shiftId').val("全部");
			$('#teamId').val("全部");
			$('#eqpCod').val("全部");
		});
		
		var loadView=function(unit,lables,values){
			$('#hisBadQty').highcharts({
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
	            colors:["#52CF29","#FDCD1F"],
	            title: {
	                text: '设备综合利用率'
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
	                    text: '综合利用率(%)'
	                }
	            },
	            tooltip: {
	            	enabled: false
	            },
	            series: [{
	                name: typeName,
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
	            }]
	        });
		}; 
		
		
		$(function(){
			//加载卷烟机、包装机、成型机设备
			$.loadSelectData($("#eqpCod"),"ALLROLLERS",false);
			$.loadSelectData($("#eqpCod"),"ALLPACKERS",false);
			$.loadSelectData($("#eqpCod"),"ALLFILTERS",false);
			$("#eqpCod").append("<option value=''>全部</option>");
			bandParams(1,params,order,type);
		});
	});
</script>
<div id="hisBadQty-title">设备综合利用率</div>
<div id="hisBadQty-seach-box" >
	<form id="search_form" style="margin-top: 5px;">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>	
			<th style="width:76px;text-align:center;">机组</th>
			<td>
				<select name="eqpCod" id="eqpCod" style="height:20px;">
				</select>
			</td>	
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
				<select name="shiftId" id="shiftId" style="height:20px;">
					<option value="">全部</option>
					<option value="1">早班</option>
					<option value="2">中班</option>
					<option value="3">晚班</option>
				</select>
			</td>
			<th style="width:76px;text-align:center;">班组</th>
			<td style="width: 120px;">
				<select name="teamId" id="teamId" style="height:20px;">
					<option value="">全部</option>
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>
			</td>
			<td style="width:76px;text-align:center;"><input type="button" id="hisBadQty-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			<td  style="width:86px;text-align:center;"><input type="button" id="hisBadQty-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="hisBadQty-tab" style="height:462px;overflow:hidden;">
	<div id="hisBadQty" style="min-width: 310px; height: 460px; margin: 0 auto"></div>
</div>
<div id="hisBadQty-page">
	<div style="float:left">
	      
	</div>
	<div style="float:right">
	           共<span id="count">0</span>条数据
		<input id="up" type="button"  value="上一页" class="btn btn-default"/>
		<span id="pageIndex">0</span>/<span id="allPages">0</span>
	    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
	</div>
</div>