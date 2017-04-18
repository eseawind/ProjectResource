<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>卷包车间工单管理</title>
<meta charset="utf-8"/>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">	
	var dataGrid=null;
	var id;  
	$(function() {
		
		$.loadComboboxData($("#shift"),"SHIFT",true);
		$.loadComboboxData($("#team"),"TEAM",true);
		//生产日期 默认今天
		//初始化时间
	    var d=new Date();
		var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		$("#scrq_date").my97("setValue",end); 
		
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			remoteSort: false,
			idField : 'id',
			striped : true,
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'asc',
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			showPageList:false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				checkbox : true
			}, {
				field : 'code',
				title : '工单号',
				width : 100,
				align:'center',
				sortable : true
			}, {
				field : 'type',
				title : '类型',
				width : 80,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
						case 1:
							type = "卷烟机工单";
							break;
						case 2:
							type = "包装机工单";
							break;
						case 3:
							type = "封箱机工单";
							break;
						case 4:
							type = "成型机工单";
							break;
					}
					return type;
				}
			}, {
				field : 'mat',
				title : '牌号',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'equipment',
				title : '设备',
				width : 90,
				align:'center',
				sortable : true
			}, {
				field : 'date',
				title : '生产日期',
				width : 80,
				align:'center',
				sortable : true
			}
			, {
				field : 'shift',
				title : '班次',
				width : 40,
				align:'center',
				sortable : true
			}, {
				field : 'team',
				title : '班组',
				width : 40,
				align:'center',
				sortable : true
			}, {
				field : 'qty',
				title : '计划产量',
				width : 60,
				align : 'right',
				sortable : true
			}, {
				field : 'unit',
				title : '单位',
				width : 40,
				align:'center',
				sortable : true
			}
			] ],
			columns : [ [ {
				field : 'bth',
				title : '批次号',
				width : 80,
				align:'center',
				sortable : true
			}, {
				field : 'isNew',
				title : '是否为新',
				width : 60,
				align:'center',
				sortable : true,
				formatter : function(value, row, index) {
					return value==1?'是':'否';
				}
			}, {
				field : 'prodType',
				title : '生产类型',
				width : 60,
				align:'center',
				sortable : true,
				formatter : function(value, row, index) {
					return value==1?'正式生产':'中试生产';
				}
			}, {
				field : 'stim',
				title : '计划开始时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'etim',
				title : '计划结束时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'bomVersion',
				title : 'BOM版本',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'sts',
				title : '状态',
				width : 60,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
						case 1:
							type = "下发";
							break;
						case 2:
							type = "运行";
							break;
						case 3:
							type = "暂停";
							break;
						case 4:
							type = "完成";
							break;
						case 5:
							type = "中止";
							break;
						case 6:
							type = "结束";
							break;
					}
					return type;
				}
			}, {
				field : 'isCheck',
				title : '审核状态',
				width : 60,
				align:'center',
				sortable : true,
				formatter : function(value, row, index) {
					return value==1?'<span style="color:green">已审核<span>':'<span style="color:red">未审核<span>';
				}
			}
			, {
				field : 'checkTime',
				title : '审核时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'realStim',
				title : '实际开始时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'realEtim',
				title : '实际结束时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'runSeq',
				title : '工单运行次序',
				width : 80,
				align:'center',
				sortable : true
			}, {
				field : 'recvTime',
				title : '工单下发时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'enable',
				title : '状态',
				width : 40,
				align:'center',
				sortable : true,
				formatter : function(value, row, index) {
					return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {								
				$(this).datagrid('tooltip');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX-10,
					top : e.pageY-5
				});
				id=rowData.id;
			}
		});
	});
	/**
	* 查询工单
	*/	
	function getAllWorkOrder() {
		dataGrid.datagrid({
					url : "${pageContext.request.contextPath}/pms/workorder/getFormingWorkOrders.do",
					queryParams :$("#searchForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询工单异常", 'error');
					}
				});
		
	}
	/**
	*清除查询条件
	*/
	function clearForm() {
		$("#searchForm input[name!='workshop']").val(null);
	}
	/**
	* 定向到工单明细页面
	*/
	function goToWorkOrderDetail(){
		var row = dataGrid.datagrid('getSelected');
		var workOrderDetailmodalDialog = parent.$.modalDialog({
			title : '工单:'+row.code+'['+row.mat+']'+'详情',
			width : 850,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/workorder/goToWorkOrderDetail.do?id='+row.id,
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-standard-disk',
				handler : function() {
					workOrderDetailmodalDialog.dialog('destroy');
				}
			} ]
		});
	}
	function checkWorkOrder(){
		var id=dataGrid.datagrid("getSelected").id;
		parent.$.messager.confirm('您是否确认审核本工单,并下发到机台?', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.user.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/workorder/checkWorkOrder.do', {
						id : id
					}, function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllWorkOrder();
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以删除当前登录用户,请重新选择!'
					});
				}
			}
		});
	}
	/**
	*批量审核工单
	*/
	function checkWorkOrders() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否确认审核选中工单,并下发到机台?', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
					}
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/workorder/checkWorkOrders.do', 
					{
						ids : ids.join(',')
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllWorkOrder();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
					},"JSON");
				}
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要需要操作的工单!'
			});
		}
	}
	
	function editWorkOrderStatus(){
		$.post("${pageContext.request.contextPath}/pms/workorder/editWorkOrderStatus.do",
		{id:id,sts:$("#status-select").combobox("getValue")},
		function(json){
			if (json.success) {
				$.messager.show('提示', json.msg, 'info');
				$('#editStatus').window('close');
				getAllWorkOrder();
			} else {
				$.messager.show('提示', json.msg, 'info');
			}
		},"JSON");
	}
	var dialog;
	function goToOrderAddJsp(){
		dialog = parent.$.modalDialog({
			title : '新建工单',
			width : 750,
			height : 420,
			href : '${pageContext.request.contextPath}/pms/workorder/goToOrderAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/workorder/addOrder.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllWorkOrder();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}
	//begin===================================================================================
	var toPbWork;
	function goToPbWork(){
		toPbWork = parent.$.modalDialog({
			title : '新建排班',
			width : 750,
			height : 420,
			href : '${pageContext.request.contextPath}/pms/workorder/goToPbWork.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = toPbWork.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/workorder/addPbWork.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示','保存成功', 'info');
								toPbWork.dialog('destroy');
							}else{
								$.messager.show('提示','保存失败', 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}

	var toBatckWork;
	//新增一套完整的工单
	function goToBatckWork(){
		toBatckWork = parent.$.modalDialog({
			title : '根据排班新建工单(工单排班日期需要与排班时间一致)',
			width : 750,
			height : 420,
			href : '${pageContext.request.contextPath}/pms/workorder/goToBatckWork.do', 
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = toBatckWork.find('#form');
					var type  =f.form("getData").type;//车间
					var equipmentId = f.form("getData").equipmentId;//设备
					var qty = f.form("getData").qty;//计划产量
					var unitId= f.form("getData").unitId;//单位
					var matId = f.form("getData").matId;//牌号
					var number = f.form("getData").number;//排班总天数
					if(type.length>0&&equipmentId.length>0&&qty.length>0&&unitId.length>0&&matId.length>0){
						var reqString ='[';//辅料对象
						var rows = toBatckWork.find('#matPickGrid').datagrid('getChecked');
						for(var i=0;i<rows.length;i++){
							var count =1000;
							reqString += '{"id":"'+rows[i].id//主键
							+ '","mat":"'+ rows[i].mat//辅料（ID）
							+ '","matProdCode":"'+ rows[i].matProdCode//辅料名称
							+ '","matUnitId":"'+rows[i].matUnitId
							+ '","matUnitName":"'+rows[i].matUnitName
							+ '","matCount":"'+ count
							+ '"}';
							if(i<(rows.length-1)){
								reqString += ',';
							}
							
						}
						reqString += "]";
						if(reqString.length<10){
							$.messager.show('提示','请选择相应辅料', 'info');
							return false;
						}
						//return ;
						var pams = {
							type:type,
							equipmentId:equipmentId,
							qty:qty,
							unitId:unitId,
							matId:matId,
							reqString : reqString,
							number:number
						};
						//alert(grid.length);
						$.post("${pageContext.request.contextPath}/pms/workorder/saveBatckWork.do",pams,function(json){
							//alert("ok");
							if (json.success) {
								$.messager.show('提示','保存成功', 'info');
								toBatckWork.dialog('destroy');
							}else{
								$.messager.show('提示','保存失败', 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}
	//测试 end==============================================================================
	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<div id="toolbar" style="display: none;">
<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">工单：</span>
						<input name="code" class="easyui-validatebox " data-options="prompt: '工单号，牌号'"/>
					</div>
					<div >
					<span class="label">工单类型：</span>
					<select name="type"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<!-- 1,下发2,运行3,暂停4,完成,5,终止,6结束 -->
								<option value="">全部</option>
								<option value="4">成型机工单</option>								
						</select>
					</div>
					<div >
						<span class="label">班次：</span>
						<select id="shift" name="shift" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班组：</span>
						<select id="team" name="team" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">生产日期：</span>
						<input id="scrq_date" name="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:120px;"/>
					</div>
					<div >
						<span class="label">运行状态：</span>
						<select name="sts"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<!-- 1,下发2,运行3,暂停4,完成,5,终止,6结束 -->
								<option value="">全部</option>
							    <option value="1">下发</option>
								<option value="2">运行</option>
								<option value="3">暂停</option>
								<option value="4">完成</option>							
								<option value="5">终止</option>							
								<option value="6">结束</option>							
						</select>
					</div>
					<div >
						<span class="label">审核状态：</span>
						<select name="isCheck"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<!-- 1,下发2,运行3,暂停4,完成,5,终止,6结束 -->
								<option value="">全部</option>
							    <option value="1">已审核</option>
								<option value="0">未审核</option>							
						</select>
					</div>
					<input type="hidden" name="workshop" value="1"/><!-- 车间code -->
				</fieldset>
			</div>
		</form>

		<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/workorder/getAllWorkOrders.do']}">
				<a onclick="getAllWorkOrder();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-table-refresh',plain:true">重置</a>
			</c:if>
			
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/workorder/checkWorkOrders.do']}">
				<a onclick="checkWorkOrders();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-switch',plain:true">批量审核</a>
		    </c:if>
		    <c:if test="${not empty sessionInfo.resourcesMap['/pms/workorder/goToOrderAddJsp.do']}">
				<a onclick="goToOrderAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-add',plain:true">新建工单</a>
			</c:if>
			
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/workorder/batckWork.do']}">
				<a onclick="goToPbWork();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-add',plain:true">一键排班</a>
				<a onclick="goToBatckWork();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-add',plain:true">一键新增工单</a>
			</c:if>
		</div>
</div>
<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>		
<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/workorder/goToWorkOrderDetail.do']}">
		<div onclick="goToWorkOrderDetail();" data-options="iconCls:'icon-hamburg-docs'">查看详情</div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/workorder/checkWorkOrder.do']}">
		<div onclick="checkWorkOrder();" data-options="iconCls:'icon-standard-arrow-right'">审核通过</div>	
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/workorder/editWorkOrderStatus.do']}">
		<div onclick="javascript:$('#editStatus').window('open');" data-options="iconCls:'icon-standard-cog-edit'">编辑状态</div>	
	</c:if>
</div>	
<div id="editStatus" class="easyui-window" title="工单状态修改"
		data-options="modal:true,closed:true,resizable:false,collapsible:false,minimizable:false,maximizable:false,iconCls:'icon-save'"
		style="width:250px; height:100px; padding: 15px;">
					<table>					
						<tr style="height:30px">
						<td>状态：</td>
							<td>
								<select id="status-select"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<!-- 1,下发2,运行3,暂停4,完成,5,终止,6结束 -->
							    <option value="1">下发</option>
								<option value="2">运行</option>
								<option value="3">暂停</option>
								<option value="4">完成</option>							
								<option value="5">终止</option>							
								<option value="6">结束</option>							
								</select>
							</td>
							<td>
								<a id="editOrder" onclick="editWorkOrderStatus();" href="javascript:void(0);" data-options="iconCls:'icon-standard-user-edit'" class="easyui-linkbutton">保存</a>
							</td>
						</tr>
					</table>
    	</div>
</body>
</html>