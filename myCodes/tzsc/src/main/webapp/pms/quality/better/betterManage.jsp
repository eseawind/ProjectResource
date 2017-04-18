<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>质量改善管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/betterManage";

		var betterManageWhere=null;
		var bandParams = null;
		$(function() {
			//初始化
			$.loadComboboxData($("#mdEquipmentSb"),"ALLEQPS",true);//加载下拉框数据
			$.loadComboboxData($("#mdWorkshopCj"),"WORKSHOP",true);//加载下拉框数据
			
			//初始化时间
		    var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#startTime").my97("setValue",sts);//
		    $("#endTime").my97("setValue",end);	//
		   
			//end
			betterManageWhere = $('#betterManageWhere').datagrid({
				fit : true,//为 false的时候，改变窗口大小，条件位置不变
				toolbar : '#betterManageToolbar'
			});
			//圆柱 视图显示
			var loadView=function(name,seriesName,lables,values1,values2,divID){
				$(divID).highcharts({
				 	exporting: {enabled: false},
				 	credits: {text: 'lanbaoit',href: 'http://www.shlanbao.cn'},
				 	credits: {enabled: false},
				    tooltip: {enabled: true},
				 	title: {text: "CPK统计"},
				 	//colors:["#52CF29","#FDCD1F"],//线的颜色
			        chart: {
			        	//type: 'column', // type: 'line',设置图表样式，可以为line,spline,column 
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false,
			            backgroundColor:'#f1f1f1'//背景颜色
			        },
				 	xAxis: {
				 		categories : lables,
				 		//gridLineColor: '#197F07',//纵向网格线颜色
			            //gridLineWidth: 1, //纵向网格线宽度
				 		labels: {
		                    rotation: 0,//横坐标 倾斜度
		                    align: 'center',color: 'black',style: {fontSize: '10px',fontFamily: 'Verdana, sans-serif'}
		                }
			        },
			        yAxis: {
			        	title: {text: "分析"},
			        	//gridLineColor: '#197F07',//纵向网格线颜色
		                //gridLineWidth: 1, //纵向网格线宽度
			        	 min: -0.06,                
			        	 max:0.53, 
			        	//allowDecimals:false,     //不显示小数
			        	 tickInterval: 0.06,         //Y轴值按0.02来等分
			        	 //tickPixelInterval:300, //像素控制 间距，y轴默认值72，x轴默认值100
			        	 plotBands: [{ // 设置区间背景色
			                 color: '#FCDFC5',
			                 from: -0.06,
			                 to: 0,
			                 label: {text: '偏低',style: {color: '#606060'}}
			             },{ // mark the weekend
			                 color: '#FCFFC5',
			                 from: 0.48,
			                 to: 0.54,
			                 label: {text: '偏高',style: {color: '#606060'}}
			             }]
					},
					series : [ {
						name : '平均值',
						//type: 'column',
						//color: '#FCDFC5',
						data : values1,
						//data : [9.03,5.50,3.45,4.4,9.52,8.92],
						events : {//点击事件
							legendItemClick : function(event) {
								//return false;
							}
						}
					}, {
						name : '极差',
						//color: '#FCDFC5',
						data : values2,
						//data : [8.13,7.50,3.45,5.4,9.52,6.92],
						events : {//点击事件
							legendItemClick : function(event) {
								//return false;
							}
						}
						
					}]
				});
			};

		bandParams = function(id, params) {
			$.post(tempUrl + "/getBeanData.do?id=" + id, params,
					function(obj) {
						var lables = obj.lables;
						var values1 = obj.values1;
						var values2 = obj.values2;
						var type = obj.type;
						loadView("重量合格率", "重量", lables, values1, values2,
								"#container1");
						//loadView("圆周合格率","圆周",lables,values2,"#container2");
					}, "JSON");
		};

	});

	//查询
	function getBetterManage() {
		bandParams("", $("#betterManageForm").form("getData"));
	}
	//重置
	function clearBetterManageForm() {
		try {
			$('#betterManageForm input').val(null);
			$("#mdEquipmentSb").combobox("setValue", "");//下拉框赋值
			$("#mdWorkshopCj").combobox("setValue", "");//下拉框赋值
			//初始化时间
			var today = new Date();
			var month = today.getMonth() + 1;
			if (month < 10) {
				month = ("0" + month);
			}
			var day = today.getDate();
			if (day < 10) {
				day = ("0" + day);
			}
			var date = today.getFullYear() + "-" + month + "-" + day;
			//$("#startTime").datebox("setValue",date);	//时间用这个
			//$("#endTime").datebox("setValue",date);	//时间用这个
		} catch (e) {
			alert(e);
		}
	}
	
</script>
<style type="text/css">
	.pie-cell{
		border:2px solid #bebebe;
		height:220px;
		width:540px;
		float:left;
		margin:5px;
		border-radius:5px;
	}
</style>

</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	
	<div id="betterManageToolbar"  style="display: none;width:100%;">
		<form id="betterManageForm" style="margin:0px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div>
						<span class="label">开始日期：</span>
						<input id="startTime" name="time" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width: 130px" />
					</div>
					<div>
						<span class="label">结束日期：</span>
						<input id="endTime" name="endTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width: 130px" />
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" >
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/betterManage/getList.do']}">
				<a onclick="getBetterManage()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/betterManage/cleanQuery.do']}">
				<a onclick="clearBetterManageForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'north',border:false,split:false" style="height:76px;">
		<table id="betterManageWhere"></table>
	</div>
	<div data-options="region:'center',border:true,split:true,title:'质量改善分析'">
		<div class="pie-cell">
			<div id="container1" class='pie' style="min-width:10px;height:100%;width:100%;"></div>
		</div>
		<div class="pie-cell">
			<div id="container2" class='pie' style="min-width:10px;height:100%;width:100%;"></div>
		</div>
		<div class="pie-cell">
			<div id="container3" class='pie' style="min-width:10px;height:100%;width:100%;"></div>
		</div>
		<div class="pie-cell">
			<div id="container4" class='pie' style="min-width:10px;height:100%;width:100%;"></div>
		</div>
		<div class="pie-cell">
			<div id="container5" class='pie' style="min-width:10px;height:100%;width:100%;"></div>
		</div>
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
</body>
</html>