<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>合格率统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/passRate";

		var passRateGrid=null;
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

			
			passRateGrid = $('#passRateGrid').datagrid({
				fit : true,
				fitColumns : false,//fitColumns= true就会自动适应宽度（无滚动条）
				//width:$(this).width()-252,  
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				remoteSort: false,
				pageSize : 10,
				pageList : [10, 20, 60, 80, 100],
				sortName : 'time',
				sortOrder : 'asc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				frozenColumns:[[//固定表头
						{field : 'id',title : '',width : 120,hidden : true}, 
						{title:'样品编号', rowspan:2,field:'sampleNo',width:100,align:'center'},//跨列
						{title:'牌号', rowspan:2,field:'mdMatName',width:100,align:'center'},//跨列
						{title:'机台', rowspan:2,field:'mdEquipmentName',width:100,align:'center'},//跨列
						{title:'样品数目', rowspan:2,field:'sampleNumber',width:100,align:'center'},//跨列
						{title:'时间', rowspan:2,field:'time',width:100,align:'center'},//跨列
				]],
				columns : [ [ 
						
						{title:'重量',colspan:10},
						{title:'圆周',colspan:10}
					],[
						{title :'中间值',rowspan:1,field : 'cb',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '上偏差',rowspan:1,field : 'sb',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '下偏差',rowspan:1,field : 'ps',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title :'最大值',rowspan:1,field : 'zt',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '最小值',rowspan:1,field : 'wz',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '不合格数',rowspan:1,field : 'dz',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '公差',rowspan:1,field : 'tmz',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '变异系数',rowspan:1,field : 'lx',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '得分',rowspan:1,field : 'df',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : 'CPK',rowspan:1,field : 'cpk',width :$(this).width() * 0.08,align:'center',sortable : true},

						{title :'中间值',rowspan:1,field : 'cb',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '上偏差',rowspan:1,field : 'sb',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '下偏差',rowspan:1,field : 'ps',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title :'最大值',rowspan:1,field : 'zt',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '最小值',rowspan:1,field : 'wz',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '不合格数',rowspan:1,field : 'dz',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '公差',rowspan:1,field : 'tmz',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '变异系数',rowspan:1,field : 'lx',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : '得分',rowspan:1,field : 'df',width :$(this).width() * 0.08,align:'center',sortable : true},
						{title : 'CPK',rowspan:1,field : 'cpk',width :$(this).width() * 0.08,align:'center',sortable : true}
					]

				],
				toolbar : '#passRateToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {//鼠标右键事件
					/* e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#shiftchgMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					}); */
				},
				onClickRow:function(rowIndex,rowData){//点击行事件
					//getShapeDetsByShapeId(rowData.id);
				}
			});

			//圆柱 视图显示
			var loadView=function(name,seriesName,lables,values,divID){
				$(divID).highcharts({//'#container'
					credits: {text: 'lanbaoit',href: 'http://www.shlanbao.cn'},
		        	exporting: {enabled: false},
		            chart: {type: 'column', backgroundColor:'#f1f1f1'},
		            colors:["#52CF29","#FDCD1F"],
		            title: {text: name},//'合格率'
		            tooltip: {
		                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		            },
		            xAxis: {
		                categories: lables,
						labels: {
			                    rotation: 0,//横坐标 倾斜度
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
		                    text: name   //'合格率'
		                },
		                labels: {//格式化y坐标显示值
		                	formatter: function() {
		                		return this.value+"%";
		                	}
		                }
		            },
		            tooltip: {
		            	enabled: true
		            },
		            series: [{
		            	name: seriesName, //重量
		                data:values,
		                events:{//点击事件
	                	 	legendItemClick:function(event) {
								return false;
			                }
				        },
				        dataLabels: {
		                    enabled: true,
		                    rotation: 0,
		                    color: 'black',
		                    align: 'center',
		                    x: 4,
		                    y: 0,//数字的坐标
		                    style: {
		                        fontSize: '12px',
		                        fontFamily: 'Verdana, sans-serif'
		                    }
		                }
		            }]
		        });
			}; 

			bandParams=function(id,params){
				$.post(tempUrl+"/getStatisticsData.do?id="+id,params,function(obj){
					var lables=obj.lables;
					var values1=obj.values1;
					var values2=obj.values2;
					var type=obj.type;
					loadView("重量合格率","重量",lables,values1,"#container1");
					loadView("圆周合格率","圆周",lables,values2,"#container2");
				},"JSON");
			};
			
		});


		
		
	   //查询
		function getShapes() {
			passRateGrid.datagrid({
				url : tempUrl+"/getList.do",
				queryParams :$("#passRateForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
			bandParams("",$("#passRateForm").form("getData"));
		}
		//重置
		function clearShapeForm(){
		   try{
			   $('#passRateForm input').val(null);
		       $("#mdEquipmentSb").combobox("setValue", "");//下拉框赋值
		       $("#mdWorkshopCj").combobox("setValue", "");//下拉框赋值
				//初始化时间
			  	var today = new Date();
				var month=today.getMonth()+1;
				if(month<10){month=("0"+month);}
				var day=today.getDate();
				if(day<10){day=("0"+day);}
			    var date=today.getFullYear()+"-"+month+"-"+day;
			   $("#startTime").datebox("setValue",date);	//时间用这个
			   $("#endTime").datebox("setValue",date);	//时间用这个
			} catch(e){
				alert(e);
			}
	   }
		//双击记载数据事件
	   function getShapeDetsByShapeId(id){
		   bandParams(id,$("#passRateForm").form("getData"));
	   }
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	
	<div id="passRateToolbar"  style="display: none;width:100%;">
		<form id="passRateForm" style="margin:4px 0px 0px 0px">
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
					<div >
						<span class="label">车间：</span>
						<select id="mdWorkshopCj" name="mdWorkshopId" class="easyui-combobox"
							data-options="panelHeight:'auto',width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">机台(设备)：</span>
						<select id="mdEquipmentSb" name="mdEquipmentId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" >
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/passRate/getList.do']}">
				<a onclick="getShapes()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/passRate/cleanQuery.do']}">
				<a onclick="clearShapeForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'north',border:true,split:false" style="height:330px;">
		<table id="passRateGrid"></table>
	</div>
	
	<div data-options="region:'center',border:true,split:true,title:'合格率统计'">
		<div id="container1" style="height: 100%;width:100%"></div>
		<div id="container2" style="height: 100%;width:100%"></div>
	</div>
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
</body>
</html>