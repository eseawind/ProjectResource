<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>CPK统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/cpk";

		var cpkGrid=null;
		var bandParams = null;
		$(function() {
			//初始化
			$.loadComboboxData($("#mdWorkshopCj"),"WORKSHOP",true);//车间
			$.loadComboboxData($("#mdMatId"),"MATPROD",true);//品名
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

			
			cpkGrid = $('#cpkGrid').datagrid({
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
				}, {
					field : 'uName1',
					title : '产品指标',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'uName2',
					title : '上下限范围',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'uName3',
					title : '产品合格率',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'uName4',
					title : 'CPK值',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'uName5',
					title : 'CPK评级标准',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'uName6',
					title : '改善意见',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}

				] ],
				toolbar : '#cpkToolbar',
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
					//getCpkDetsByCpkId(rowData.id);
				}
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
					 	//type: 'line', // type: 'line',设置图表样式，可以为line,spline,column 
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
								return false;
							}
						}
					}, {
						name : '极差',
						//color: '#FCDFC5',
						data : values2,
						//data : [8.13,7.50,3.45,5.4,9.52,6.92],
						events : {//点击事件
							legendItemClick : function(event) {
								return false;
							}
						}
						
					} ]
				});
			};

			bandParams=function(id,params){
				$.post(tempUrl+"/getCpkData.do?id="+id,params,function(obj){
					var lables=obj.lables;
					var values1=obj.values1;
					var values2=obj.values2;
					var type=obj.type;
					loadView("", "", lables, values1, values2,"#container");
				},"JSON");
			};
			
		});


		
		
	   //查询
		function getCpkQuery() {
			cpkGrid.datagrid({
				url : tempUrl+"/getList.do",
				queryParams :$("#cpkForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
			bandParams("",$("#cpkForm").form("getData"));
		}
		//重置
		function clearCpkForm(){
		   try{
			   $('#cpkForm input').val(null);
		       $("#mdMatId").combobox("setValue", "");//下拉框赋值
		       $("#mdWorkshopCj").combobox("setValue", "");//下拉框赋值
				//初始化时间
			  	var today = new Date();
				var month=today.getMonth()+1;
				if(month<10){month=("0"+month);}
				var day=today.getDate();
				if(day<10){day=("0"+day);}
			    var date=today.getFullYear()+"-"+month+"-"+day;
			   //$("#startTime").datebox("setValue",date);	//时间用这个
			   //$("#endTime").datebox("setValue",date);	//时间用这个
			} catch(e){
				alert(e);
			}
	   }
		//双击记载数据事件
	   function getCpkDetsByCpkId(id){
		   bandParams(id,$("#cpkForm").form("getData"));
	   }
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	
	<div id="cpkToolbar"  style="display: none;width:100%;">
		<form id="cpkForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">车间：</span>
						<select id="mdWorkshopCj" name="mdWorkshopId" class="easyui-combobox"
							data-options="panelHeight:'auto',width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">产品/牌号：</span> 
						<select id="mdMatId" name="mdMatId" class="easyui-combobox"
							data-options="panelHeight:200,width:120,editable:false">
						</select>
					</div>
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
						<span class="label">质量指标：</span>
						<input type="text" name="zlTarget" class="easyui-validatebox" 
						data-options="width:120,prompt: '质量指标模糊查询'"/>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" >
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/cpk/getList.do']}">
				<a onclick="getCpkQuery()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/cpk/cleanQuery.do']}">
				<a onclick="clearCpkForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'north',border:true,split:false" style="height:180px;">
		<table id="cpkGrid"></table>
	</div>
	
	<div data-options="region:'center',border:true,split:true,title:'CPK统计'">
		<div id="container" style="height: 100%;width:100%"></div>
	</div>
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
</body>
</html>