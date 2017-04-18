<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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

<!-- wctAlert -->
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.js" type="text/javascript"></script>

<link media="screen" href="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.css" type="text/css" rel="stylesheet"><!-- Example script -->

<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#eqpRunSta-title{
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
	#eqpRunSta-seach-box{
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
	#eqpRunSta-wct-frm td{
		font-size:14px;
	}
	#eqpRunSta-tab{		
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
	#eqpRunSta-tab-thead tr td,#eqpRunSta-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#eqpRunSta-page{
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
</style>
<script type="text/javascript" >
	$(function(){
		/*
		设备下拉框
		*/
		var groupTypeFlag="${groupTypeFlag}";
		var group=null;

		if(groupTypeFlag=="1"){
			//卷包机组交接设备
			group=[
			   {"id":"${loginGroup.eqps['roller'].id}","des":"${loginGroup.eqps['roller'].des}",selected:true},
			       {"id":"${loginGroup.eqps['packer'].id}","des":"${loginGroup.eqps['packer'].des}"}];
		}else if(groupTypeFlag=="2"){
			//成型机交接设备
			group=[
				   {"id":"${loginGroup.eqps['filer1'].id}","des":"${loginGroup.eqps['filer1'].des}",selected:true},
				       {"id":"${loginGroup.eqps['filer2'].id}","des":"${loginGroup.eqps['filer2'].des}"}];
		}else if(groupTypeFlag=="3"){
			//封箱机交接设备
				group=[
					   {"id":"${loginGroup.eqps['boxer1'].id}","des":"${loginGroup.eqps['boxer1'].des}",selected:true},
					       {"id":"${loginGroup.eqps['boxer2'].id}","des":"${loginGroup.eqps['boxer2'].des}"}];
				
		}else if(groupTypeFlag=="4"){
			//url="/wct/isp/wct_isp_fsj.jsp";
		}
		
		if(group[0].id==""){
			jAlert("登录超时,请重新登录.","系统提示",function(){				
					top.location="${pageContext.request.contextPath}/wct/login.jsp";
				});
		}
		
		$("#eqp").combobox({
			editable:false,
			panelHeight:'auto',
			valueField:'id',
			textField: 'des',
			data:group
		});
		
		var pageIndex=1;
		var allPage=0;
		var params={};
		
		
		var bandParams=function(pageIndex,params){
			$.post("${pageContext.request.contextPath}/wctEqp/listRunOper.action?curpage="+pageIndex,params,function(v){				
				var obj=eval("("+v+")");
				var lables=obj.lables;
				var values=obj.values;
				var values2=obj.values2;
				pageIndex=obj.pageIndex;
				allPage=obj.allPage;
				$("#pageIndex").html(pageIndex);
				$("#allPage").html(allPage);
				$("#count").html(obj.count);
				loadView(lables,values,values2);		
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
		$("#eqpRunSta-search").click(function(){
			params=serializeObject($('#eqpRunSta-wct-frm'));
			bandParams(1,params);
		});
		$("#eqpRunSta-reset").click(function(){
			params={};
			$('#eqpRunSta-wct-frm input[type!=button]').val(null);
			$("#eqp").combobox({
				editable:false,
				panelHeight:'auto',
				valueField:'id',
				textField: 'des',
				data:group
			});
			bandParams(1,params);
		});
		
		var loadView=function(lables,values,values2){
			$('#operation').highcharts({
				credits: {
	           	 text: '',
	           	 href: 'http://www.shlanbao.cn'
	       		 },
	       		 exporting: {
	    	            enabled: false
	    	     },
	            chart: {
	                type: 'column',
	                backgroundColor:'#f1f1f1'
	            },
				colors:["green"],
	            title: {
	                text: '设备有效作业率统计图'
	            },
	            xAxis: {
	                categories: lables
					, labels: {
	                    align: 'center',
	                    style: {
	                        fontSize: '13px',
	                        fontFamily: 'Verdana, sans-serif'
	                    }
	                }
				},
	            yAxis: {
	                min: 0,
	                title: {
	                    text: '设备有效作业率(%)'
	                }
	            },
	            tooltip: {
	            	pointFormat: '有效作业率: <b>{point.y:.2f}%</b>',
	                shared: true
	            },
	                series: [ {
	                name: '有效作业率',
	                data: values,
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
		loadView([],[],[]);
	});
</script>
</head>
<body>
<div id="eqpRunSta-title">有效作业率</div>
<div id="eqpRunSta-seach-box" >
	<form id="eqpRunSta-wct-frm">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>		
			<th style="width:50px;text-align:right;">设备&nbsp;&nbsp;</th>
			<td style="width:115px;">
				<input style="width:120px;height:20px;"   name="bean.eqpCod" id="eqp"/>
			</td>
			<th style="width:60px;text-align:right;">日期&nbsp;&nbsp;</th>
			<td style="width:140px;">
			<input class="easyui-datebox" editable="false" 
				   style="border:1px solid #9a9a9a;height:20px;width:120px;"
			       name="bean.date"/>
			</td>
			<td style="width:40px;text-align:center;">-</td>
			<td style="width: 190px;">
			<input class="easyui-datebox" 
			       editable="false" 
			       style="border:1px solid #9a9a9a;height:20px;width:120px;"
			name="bean.time"/>
			</td>
			<td style="width:80px;"><input type="button" id="eqpRunSta-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			<td style="width:140px;"><input type="button" id="eqpRunSta-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="eqpRunSta-tab" style="height:462px;overflow:auto;">
	<div id="operation" style="min-width: 310px; height: 462px; margin: 0 auto"></div>
</div>
<div id="eqpRunSta-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页" class="btn btn-default"/>
	<span id="pageIndex">0</span>/<span id="allPage">0</span>
    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
   </div>

</body>
</html>