<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>生产实绩</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		var inputGrid = null;
		var outputGrid=null;
		
		$(function() {
			
			var d=new Date();
			var nd=d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#date").my97("setValue",nd);
			$.loadComboboxData($("#shift"),"SHIFT",true);
			$.loadComboboxData($("#team"),"TEAM",true);
			
			outputGrid = $('#outputGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				remoteSort:false, 
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 20,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				rownumbers :true,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : '编号',
					checkbox : true
				}, {
					field : 'date',
					title : '日期',
					width : 70,
					align : 'center',
					sortable : true
				}, {
					field : 'shift',
					title : '班次',
					width : 40,
					align:'center',
					sortable : true
				}, {
					field : 'team',
					title : '班组',
					align : 'center',
					width : 40,
					sortable : true
				}, {
					field : 'equipment',
					title : '机台',
					align : 'center',
					width : 70,
					sortable : true
				}, {
					field : 'workorderCode',
					title : '工单号',
					align : 'center',
					width : 100,
					sortable : true
				}, {
					field : 'workorder',
					title : '工单ID',
					width : 100,
					hidden: true
				}, {
					field : 'matProd',
					title : '牌号',
					align:'center',
					width : 120,
					sortable : true
				}, {
					field : 'qty',
					title : '产量',
					align : 'right',
					width : 60,
					sortable : true
					
				}, {
					field : 'badQty',
					title : '剔除',
					align : 'right',
					width : 60,
					sortable : true
				}, {
					field : 'unit',
					title : '单位',
					align : 'center',
					width : 40,
					sortable : true
				}, {
					field : 'stim',
					title : '实际开始时间',
					align : 'center',
					width : 130,
					sortable : true
				}, {
					field : 'etim',
					title : '实际结束时间',
					align : 'center',
					width : 130,
					sortable : true
				}, {
					field : 'runTime',
					title : '运行时间',
					align : 'right',
					width : 60,
					sortable : true,
					formatter : function(value, row, index) {
						return (value/60).toFixed(2);
					}
				}, {
					field : 'stopTime',
					title : '停机时间',
					align : 'right',
					width : 60,
					sortable : true,
					formatter : function(value, row, index) {
						return (value/60).toFixed(2);
					}
				}, {
					field : 'timeunit',
					title : '单位',
					align : 'center',
					width : 40,
					sortable : true,
					formatter : function(value, row, index) {
						return "小时";
					}
				}, {
					field : 'stopTimes',
					title : '停机数',
					align : 'right',
					width : 40,
					sortable : true
				}, {
					field : 'lastRecvTime',
					title : '最后数据接收时间',
					align : 'center',
					width : 130,
					sortable : true
				}, {
					field : 'lastUpdateTime',
					title : '最后数据修改时间',
					align : 'center',
					width : 130,
					sortable : true
				}, {
					field : 'isFeedback',
					title : '反馈状态 ',
					align : 'center',
					width : 60,
					sortable : true,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">已反馈<span>':'<span style="color:red">未反馈<span>';
					}
				}, {
					field : 'feedbackTime',
					title : '反馈时间',
					align : 'center',
					width : 120,
					sortable : true
				}, {
					field : 'feedbackUser',
					title : '操作用户 ',
					align : 'center',
					width : 60,
					sortable : true
				} ] ],
				toolbar : '#outputToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#outputMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				},
				onClickRow:function(rowIndex,rowData){
					getAllInputsByOutput(rowData.id);
				}
			});
			inputGrid = $('#inputGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				remoteSort:false, 
				//pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 20,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				rownumbers :true,
				columns : [ [ {
					field : 'id',
					title : 'id',
					width : 120,
					hidden : true
				}, {
					field : 'mat',
					title : '物料名称',
					width : 200,
					align : 'center',
					sortable : true
				}, {
					field : 'qty',
					title : '消耗量',
					align : 'right',
					width : 80,
					sortable : true
				}
				, {
					field : 'unit',
					title : '单位',
					align : 'left',
					width : 50,
					sortable : true
				}, {
					field : 'orignalData',
					title : '原始计数',
					align : 'right',
					width : 150,
					sortable : true
				} ] ],
				//toolbar : '#inputToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#inputMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
		});
	   /**
		* 查询
		*/	
		function getAllOutputs() {
			outputGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/stat/getAllForming.do",
				queryParams :$("#outputForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
			inputGrid.datagrid("loadData",[]);
		}
		function clearOutputForm(){
			$("#outputForm input").val(null);
	   }
		function deleteOutput(){
			var row = outputGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前生产数据?', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/stat/deleteOutput.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllOutputs();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
		}

		function goToOutputAddJsp(){
			var dialog = parent.$.modalDialog({
				title : '生产数据添加',
				width : 640,
				height : 440,
				href : '${pageContext.request.contextPath}/pms/stat/goToOutputAddJspCX.do?workshop=1',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")&&f.find("#workorder").attr("value")){
							$.post("${pageContext.request.contextPath}/pms/stat/addOutput.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getAllOutputs();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}else{
							f.find("input[name='workorderCode']").focus();;
						}
					},
					onLoad:function(){
						var f = dialog.find('#form'), ret = $.fn.dialog.defaults.onLoad();
						f.find("input[name='workorderCode']").focus();
						return ret;
					}
				} ]
			});
		}
		function goToOutputEditJsp(){
			var dialog = parent.$.modalDialog({
				title : '生产数据编辑',
				width : 640,
				height : 440,
				href : '${pageContext.request.contextPath}/pms/stat/goToOutputEditJsp.do?id='+outputGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")&&f.find("#workorder").attr("value")){
							$.post("${pageContext.request.contextPath}/pms/stat/editOutput.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getAllOutputs();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}else{
							f.find("input[name='workorderCode']").focus();;
						}
					}
				} ],
				onLoad:function(){
					var f = dialog.find('#form'), ret = $.fn.dialog.defaults.onLoad();
					f.find("input[name='workorderCode']").focus();
					return ret;
				}
			});
		}
	   function getAllInputsByOutput(id){
		   inputGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/stat/getAllInputsByOutput.do?id=" + id
		   });
	   }
	   function deleteInput(){
		   var row = inputGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前消耗数据？', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/stat/deleteInput.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							var index = inputGrid.datagrid("getRowIndex",row);
							inputGrid.datagrid("deleteRow",index);
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
	   }
	   //跳转到类型添加页面
	   function goToInputAddJsp(){
		   var row = outputGrid.datagrid('getSelected');
		   if(row){
			   var dialog = parent.$.modalDialog({
					title : '消耗数据添加',
					width : 640,
					height : 290,
					href : '${pageContext.request.contextPath}/pms/stat/goToInputAddJsp.do?id='+row.id+"&workorder="+row.workorder,
					buttons : [ {
						text : '保存',
						iconCls:'icon-standard-disk',
						handler : function() {
							var f = dialog.find("#form");
							if(f.form("validate")){
								$.post("${pageContext.request.contextPath}/pms/stat/addInput.do",f.form("getData"),function(json){
									if (json.success) {
										$.messager.show('提示', json.msg, 'info');
										dialog.dialog('destroy');
										getAllInputsByOutput(row.id);
									}else{
										$.messager.show('提示', json.msg, 'error');
									}
								},"JSON");
							}
						}
					} ]
				});
		   }else{
			   $.messager.show('提示', "请选择一条产出数据!", 'info');
		   }
	   }
	   
	   //跳转到类型编辑页面
	   function goToInputEditJsp(){
		   var row = outputGrid.datagrid('getSelected');
		   var dialog = parent.$.modalDialog({
				title : '消耗数据编辑',
				width : 620,
				height : 320,
				href : "${pageContext.request.contextPath}/pms/stat/goToInputEditJsp.do?iid="+inputGrid.datagrid('getSelected').id+"&id="+row.id+"&workorder="+row.workorder,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/stat/editInput.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getAllInputsByOutput(row.id);
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   function sendDataToMES(flag){//1单个反馈2按页反馈
		   var idOrIds;
		   if(flag==1){
			   idOrIds = outputGrid.datagrid('getSelected').id;
		   }
		   if(flag==2){
			   //var rows = outputGrid.datagrid('getRows');
			   var rows = outputGrid.datagrid('getChecked');
			   if(rows.length == 0){
				   $.messager.show('提示', '本页无数据!', 'error');
				   return;
			   }
			   
			   var ids = [];
			   
			   for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
			   }
			   
			   idOrIds = ids.join(",");
		   }
		   $.messager.progress({
				title:'工单反馈',
				msg:'反馈给mes系统，请等待...',
				interval:'1000'
			}); 
		   $.post("${pageContext.request.contextPath}/pms/stat/sendDataToMES.do",{"idOrIds":idOrIds,"feedbackUser":"${sessionInfo.user.name}"},function(json){
			   $.messager.progress('close'); 
			   if (json.success) {
					$.messager.show('提示', json.msg, 'info');
					getAllOutputs();
				}else{
					$.messager.show('提示', json.msg, 'error');
				}
			},"JSON");
	   }
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false,split:true,title:'产出数据'" style="height:320px;">
		<div id="outputToolbar"  style="display: none;width:100%;">
			<form id="outputForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
						<input type="hidden" name="workShop" value="1"/><!-- 车间code -->
						<span class="label">班次：</span>
						<select id="shift" name="shift" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班组：</span>
						<select id="team" name="team" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">生产日期：</span>
						<input id="date" name="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>
					<div >
						<span class="label">反馈状态：</span>
						<select name="isFeedback"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<!-- 1,已反馈0,未反馈-->
								<option value="">全部</option>
							    <option value="1">已反馈</option>
								<option value="0">未反馈</option>							
						</select>
					</div>
				
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<a onclick="getAllOutputs()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearOutputForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="goToOutputAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				<a onclick="sendDataToMES(2);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-package-go'" title="反馈本页本页数据到MES系统">反馈本页</a>
			</div>
		</div>
		<table id="outputGrid"></table>
	</div>
	
	<div data-options="region:'center',border:false,split:true,title:'消耗数据'">
		<div class="easyui-toolbar" >
			<a onclick="goToInputAddJsp()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
		</div>
		<table id="inputGrid"></table>
	</div>
	<div id="inputMenu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="goToInputEditJsp()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteInput();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
	<div id="outputMenu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="goToOutputEditJsp()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="sendDataToMES(1);" data-options="iconCls:'icon-standard-package-go'">反馈MES</div>
		<div onclick="deleteOutput();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>