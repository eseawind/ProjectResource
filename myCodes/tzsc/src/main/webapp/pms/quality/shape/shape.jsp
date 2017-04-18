<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>外观质量统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/shape";

		var shapeGrid=null;
		var bandParams = null;
		$(function() {
			//初始化
			$.loadComboboxData($("#mdEquipmentSb"),"ALLEQPS",true);//加载下拉框数据
			$.loadComboboxData($("#mdWorkshopCj"),"WORKSHOP",true);//加载下拉框数据
			
			//初始化时间
		    var today = new Date();
			var month=today.getMonth()+1;
			if(month<10){month=("0"+month);}
			var day=today.getDate();
			if(day<10){day=("0"+day);}
		    var date=today.getFullYear()+"-"+month+"-"+day;
		    $("#startTime").datebox("setValue",date);	//时间用这个
			$("#endTime").datebox("setValue",date);	//时间用这个
			//end

			
			shapeGrid = $('#shapeGrid').datagrid({
				fit : true,
				fitColumns : true,//fitColumns= true就会自动适应宽度（无滚动条）
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
				columns : [ [ {
					field : 'id',
					title : '',
					width : 120,
					hidden : true
				}, {
					field : 'time',
					title : '自检时间',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdMatName',
					title : '牌号',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdTeamName',
					title : '班组',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdShiftName',
					title : '班次',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdWorkshopName',
					title : '车间',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdEquipmentName',
					title : '设备',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'uName',
					title : '操作工',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}

				] ],
				toolbar : '#shapeToolbar',
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
			var loadView=function(type,lables,values){
				var seriesName="机台号";
				if(type=='1'){///选了机台号; 如果选定了机台号，则X轴日期，Y轴质量指标合格率平均值;如果未选定机台，则X轴机台号，Y轴质量指标合格率平均值。
					seriesName="日期";
				}
				$('#container').highcharts({
					credits: {text: 'lanbaoit',href: 'http://www.shlanbao.cn'},
		        	exporting: {enabled: false},
		            chart: {type: 'column', backgroundColor:'#f1f1f1'},
		            colors:["#52CF29","#FDCD1F"],
		            title: {text: '外观质量统计'},
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
		                    text: '合格率平均值'
		                }
		            },
		            tooltip: {
		            	enabled: true
		            },
		            series: [{
		            	name: seriesName,
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
		                    y: 0,
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
					var values=obj.values;
					var type=obj.type;
					loadView(type,lables,values);
				},"JSON");
			};
			
		});


		
		
	   //查询
		function getShapes() {
			shapeGrid.datagrid({
				url : tempUrl+"/getList.do",
				queryParams :$("#shapeForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
			bandParams("",$("#shapeForm").form("getData"));
		}
		//重置
		function clearShapeForm(){
		   try{
			   $('#shapeForm input').val(null);
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
		   bandParams(id,$("#shapeForm").form("getData"));
	   }
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	
	<div id="shapeToolbar"  style="display: none;width:100%;">
		<form id="shapeForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div>
						<span class="label">开始日期：</span>
						<input id="startTime" name="time" readOnly=true type="text"
							class="easyui-datebox" datefmt="yyyy-MM-dd HH:mm:ss"
							style="width: 130px" />
					</div>
					<div>
						<span class="label">结束日期：</span>
						<input id="endTime" name="endTime" readOnly=true type="text"
						class="easyui-datebox" datefmt="yyyy-MM-dd HH:mm:ss"
						style="width: 130px" />
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
					<div >
						<span class="label">质量指标：</span>
						<input type="text" name="zlTarget" class="easyui-validatebox "  data-options="width:120,prompt: '质量指标模糊查询'"/>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" >
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/shape/getList.do']}">
				<a onclick="getShapes()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/shape/cleanQuery.do']}">
				<a onclick="clearShapeForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'north',border:true,split:false" style="height:330px;">
		<table id="shapeGrid"></table>
	</div>
	
	<div data-options="region:'center',border:true,split:true,title:'外观质量统计'">
		<div id="container" style="height: 100%;width:100%"></div>
	</div>
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
</body>
</html>