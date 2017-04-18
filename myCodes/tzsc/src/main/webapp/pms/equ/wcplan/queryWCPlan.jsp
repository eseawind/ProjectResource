<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备轮保计划查询</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		var param=null;
		$(function() {
			//初始化时间
		    var today = new Date();
			var month=today.getMonth()+1;
			if(month<10){month=("0"+month);}
		    var date=today.getFullYear()+"-"+month+"-01"; 
		    var day = new Date(today.getFullYear(),today.getMonth()+1,0);   
	        var lastdate = today.getFullYear() + '-' + month + '-' + day.getDate();//获取当月最后一天日期
		    $("#date1").datebox("setValue",date);	//月初 
		    $("#date2").datebox("setValue",lastdate);	//月末
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				rownumbers :true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : true,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备保养计划ID',
					width : 120,
					checkbox : true
				},{
					field : 'planName',
					title : '计划名称',
					width : 100,
					align : 'center',
					sortable : true
				},{
					field : 'planNo',
					title : '计划编号',
					width : 70,
					align:'center',
					hidden:true
				}, {
					field : 'eqmName',
					title : '设备名称',
					align : 'center',
					width : 70,
					sortable : true
				},{
					field : 'mdShiftName',
					title : '班次',
					align:'center',
					width : 60,
					sortable : true
				},{
					field : 'scheduleDate',
					title : '计划开始日期',
					align : 'center',
					width : 120,
					sortable : true
				},{
					field : 'scheduleEndDate',
					title : '计划结束日期',
					align : 'center',
					width : 120,
					sortable : true
				},{
					field : 'wheelCoverType',
					title : '状态',
					align:'center',
					width : 60,
					sortable : true,
					formatter : function(value, row, index) {
						var type = value;
						if(type=='0'){
							type="新增";
						}else if(type=='1'){
							type="审核通过";
						}else if(type=='2'){
							type="运行";
						}else if(type=='3'){
							type="停止运行";
						}else if(type=='4'){
							type="操作工操作完毕";
						}else if(type=='5'){
							type="轮保工操作完毕";
						}else if(type=='6'){
							type="操作工、轮保工操作完毕";
						}else if(type=='7'){
							type="审核完毕";
						}else if(type=='8'){
							type="计划完成";
						}else if(type=='9'){
							type="请求删除";
						}else if(type=='10'){
							type="确认删除";
						}else if(type=='11'){
							type="请求修改";
						}else if(type=='12'){
							type="确认修改";
						}
						return type;
					}
				},{
					field : 'zrName',
					title : '审批人',
					align : 'center',
					width : 100,
					sortable : true
				},{
					field : 'glName',
					title : '批准人',
					align : 'center',
					width : 100,
					sortable : true
				}] ],
				columns : [ [/* {
					field : 'wheelParts',
					title : '保养部件',
					align:'center',
					width : 100,
					sortable : true
				}, */{
					field : 'maintenanceContent',
					title : '备注',
					align:'center',
					width : 200,
					sortable : true,
					formatter : function(value, row, index) {
						if(value==null) return "";
						return value.length>20?value.substring(0,20)+"……":value;
					}
				},{
					field : 'maintenanceType',
					title : '保养类别',
					align:'center',
					width : 120,
					sortable : true,
					formatter : function(value, row, index) {
						var bylx = value;
						if(value=='lb'){
							bylx="轮保";
						}
						return bylx;
					}
				}/* ,{
					field : 'period',
					title : '周期',
					align:'center',
					width : 70,
					sortable : true
				},{
					field : 'remindCycle',
					title : '提醒周期',
					align:'center',
					width : 70,
					sortable : true
				} */,{
					field : 'startTime',
					title : '保养开始时间',
					align : 'center',
					width : 170,
					sortable : true
				},{
					field : 'endTime',
					title : '保养结束时间',
					align : 'center',
					width : 170,
					sortable : true
				} ] ],
				toolbar : '#toolbar',
				onLoadSuccess : function() {
					//$(this).datagrid('tooltip');
				}, onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#menu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					}); 
				},onClickRow:function(rowIndex,rowData){
					getParam(rowData.id);
				}
			});
			param=$('#paramGrid').datagrid({
				rownumbers :true,
				fit : true,
				fitColumns : false,//fitColumns= true就会自动适应宽度（无滚动条）
				//width:$(this).width()-252,  
				border : false,
				pagination : false,
				idField : 'id',
				striped : true,
				remoteSort: false,
				pageSize : 100,
				pageList : [80, 100],
				sortName : 'sbCode',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : 'id',
					width : 120,
					hidden : true
				}, {
					field : 'sbCode',
					title : '编号',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'sbName',
					title : '名称',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'planTimeStr',
					title : '计划保养日期',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'userName',
					title : '保养人',
					align : 'center',
					width : 150,
					sortable : true
				}
				, {
					field : 'actualStrTime',
					title : '实际保养时间',
					align : 'center',
					width : 150,
					sortable : true
				},{
					field : 'lxType',
					title : '保养角色',
					align : 'center',
					width : 150,
					sortable : true,
					formatter : function(value, row, index) {
						var js = value;
						if(js=='0'){
							js='操作工';
						}else if(js=='1'){
							js='轮保工';
						}
						return js;
					}
				}, 
				{
					field : 'enable',
					title : '是否完成',
					align : 'center',
					width : 150,
					sortable : true,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">已完成<span>':'<span style="color:red">未完成<span>';
					}
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
		
		// 查询设备保养计划
		function queryWCPlan() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/wcp/queryWCPlan.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询保养计划异常", 'error');
				}
			});
			$('#dataGrid').datagrid('clearSelections');
		}
		//新增设备保养计划 
		var addWCPlan;
		function goToAddWCPlan(){
			addWCPlan = parent.$.modalDialog({
				title : '设备保养计划添加',
				width : 860,
				height : 570,
				href : '${pageContext.request.contextPath}/pms/wcp/goToAddWCPlan.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = addWCPlan.find("#form");
						if(f.form("validate")){
							//var type  =f.form("getData").planNo;//车间
							//alert(type);
							var rows = addWCPlan.find('#addWcpLbgzGrid').datagrid('getSelected');
							if(rows==null||rows.metcId==null||rows.metcId==""){
								$.messager.show('提示', '请选择保养规则', 'info');
								return ;
							}
							var pams = {
								planCode : f.form("getData").planCode,//计划编号
								planName : f.form("getData").planName,//计划名称
								equipmentLxId : f.form("getData").equipmentLxId,//设备类型
								equipmentLxName : f.form("getData").equipmentLxName,
								equipmentXhId : f.form("getData").equipmentXhId,//设备型号
								equipmentXhName : f.form("getData").equipmentXhName,
								equipmentId : f.form("getData").equipmentId,//设备ID
								maintenanceType : f.form("getData").maintenanceType,//轮保类别
								equipmentMinute : f.form("getData").equipmentMinute,//轮保时长
								scheduleDate : f.form("getData").scheduleDate,//轮保日期
								matId : f.form("getData").matId,//牌号
								mdShiftId : f.form("getData").mdShiftId,//班次
								maintenanceContent : f.form("getData").maintenanceContent,//备注
								del : '0',
								wheelCoverType : '0',//0 表示 新增
								metcId : rows.metcId//绑定的 项目大类(父节点，根据该节点可以查询出说有 保养项)
							};
							console.info(pams);
							$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/addWCPlan.do",pams,function(json){
								if (json.success) {
									$.messager.show('提示', '操作成功', 'info');
									addWCPlan.dialog('destroy');
									queryWCPlan();
								}else{
									$.messager.show('提示', "保存失败", 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
	
	//编辑保养计划
	function goToEditWCPlan(){
		var rows = dataGrid.datagrid('getChecked');
		if(rows.length>1){
			$.messager.show('提示', '请只选一行数据', 'error');
		}else if(rows.length==1){
			//var row = dataGrid.datagrid('getSelected');
			var wheelCoverType = rows[0].wheelCoverType;//dataGrid.datagrid('getSelected').wheelCoverType;
			var zrName =rows[0].zrName; //dataGrid.datagrid('getSelected').zrName;
			var glName =rows[0].glName; //dataGrid.datagrid('getSelected').glName;
			if(zrName!=null&&glName!=null&&zrName!=""&&glName!=""){
		    	if(wheelCoverType=='12'){
					var dialog = parent.$.modalDialog({
						title : '设备保养计划编辑',
						width :  860,
						height : 570,
						href : '${pageContext.request.contextPath}/pms/equ/sbglPlan/goToEditWCPlan.do?id='+rows[0].id,
						buttons : [ {
							text : '修改',
							iconCls:'icon-standard-disk',
							handler : function() {
								var f = dialog.find("#form");
								if(f.form("validate")){
									var rows = dialog.find('#editWcpLbgzGrid').datagrid('getSelected');
									if(rows==null||rows.metcId==null||rows.metcId==""){
										$.messager.show('提示', '请选择保养规则', 'info');
										return ;
									}
									var lunbaoGzId = f.form("getData").editWcpLbgzGridId;
									if(lunbaoGzId==null||lunbaoGzId==""){
										$.messager.show('提示', '请选择保养规则', 'info');
										return ;
									}
									//alert(rows.metcId+":"+f.form("getData").editWcpLbgzGridId);
									//return ;
									var pams = {
										planId : f.form("getData").planId,//主键
										planCode : f.form("getData").planCode,//计划编号
										planName : f.form("getData").planName,//计划名称
										equipmentLxId : f.form("getData").equipmentLxId,//设备类型
										equipmentLxName : f.form("getData").equipmentLxName,
										equipmentXhId : f.form("getData").equipmentXhId,//设备型号
										equipmentXhName : f.form("getData").equipmentXhName,
										equipmentId : f.form("getData").equipmentId,//设备ID
										maintenanceType : f.form("getData").maintenanceType,//轮保类别
										equipmentMinute : f.form("getData").equipmentMinute,//轮保时长
										scheduleDate : f.form("getData").scheduleDate,//轮保日期
										matId : f.form("getData").matId,//牌号
										mdShiftId : f.form("getData").mdShiftId,//班次
										maintenanceContent : f.form("getData").maintenanceContent,//备注
										del : '0',
										//wheelCoverType : '新建', 不修改状态
										metcId : rows.metcId//绑定的 项目大类(父节点，根据该节点可以查询出说有 保养项)
									};
									$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/editWCPlan.do",pams,function(json){
										if (json.success) {
											$.messager.show('提示', '修改成功', 'info');
											dialog.dialog('destroy');
											queryWCPlan();
										}else{
											$.messager.show('提示', "修改失败", 'error');
										}
									},"JSON");
								}
							}
						} ]
					});
				}else{
					$.messager.show('提示', '状态不是确认编辑，无法进行编辑！', 'error');
				}
			}else{
				if(wheelCoverType=='0' || wheelCoverType=='11'|| wheelCoverType=='12'){//将状态修改成为 申请编辑-11
					$.messager.show('提示', '需要车间主任与车间管理同时批准！', 'error');
					$.post('${pageContext.request.contextPath}/pms/equ/sbglPlan/edit.do', {
						id : rows[0].id
					}, function(json) {queryWCPlan();}, 'JSON');
		    	}else{
		    		$.messager.show('提示', '状态已经确认，无法编辑！', 'error');
		    	}
			} 
		}
	} 
	
	//审批
	function Auditing(statusId,obj){
		if(obj!=""&&dataGrid.datagrid('getSelected')!=null){
			var data={id:dataGrid.datagrid('getSelected').id,statusId:statusId,status:obj};

			var wheelCoverType =dataGrid.datagrid('getSelected').wheelCoverType;
			if(statusId==1&&(wheelCoverType==0)){//点击了审核 ，审批:新增和审核通过 的 才可以  审核
				$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/auditing.do",data,function(json){
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryWCPlan();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				},"JSON"); 
			}else if(statusId==1&&(wheelCoverType==1)){//审核,运行状态
				$.messager.show('提示',"该单已经审核了,无需审核", 'info');
			}else if(statusId==1&&(wheelCoverType==2)){//审核,运行状态
				$.messager.show('提示',"该单已经批准运行中,无需审核", 'info');

			}else if(statusId==2&&(wheelCoverType==0)){//审批,运行状态
				$.messager.show('提示',"该单需先审核", 'info');
			}else if(statusId==2&&(wheelCoverType==1)){
				$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/auditingRun.do",data,function(json){
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryWCPlan();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				},"JSON"); 
			}else if(statusId==2&&(wheelCoverType==2)){//审批,运行状态
				$.messager.show('提示',"该单已经审批运行中,无需批准", 'info');
			}else{
				$.messager.show('提示',obj+":操作失败", 'info');
			}
		}
		
	}
	//获取维保详细
	function getParam(id){
		param.datagrid({
				url : "${pageContext.request.contextPath}/pms/equ/sbglPlan/queryParam.do?pid=" + id
		   });
	}
	/**
	*主任审批
	*/
	function batchAuditing() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
			if (rows.length > 0) {
				parent.$.messager.confirm('确认', '您是否确认审批选中行?', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
						}
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post('${pageContext.request.contextPath}/pms/equ/sbglPlan/batchAuditing.do', 
						{
							ids : ids.join(','),
							status:'1'
						}, function(json) {
							parent.$.messager.progress('close');
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								queryWCPlan();
							} else {
								$.messager.show('提示', json.msg, 'info');
							}
						},"JSON");
					}
				});
			} else {
				parent.$.messager.show({
					title : '提示',
					msg : '请勾选要需要审核的数据!'
				});
			}
	}
	/**
	*管理批准
	*/
	function batchAuditingRun() {
		var rows = dataGrid.datagrid('getChecked');
		var pass=false;
			var ids = [];
			var zrName = "";
			for ( var i = 0; i < rows.length; i++) {
				zrName = rows[i].zrName;
				if(zrName!=null&&zrName!=""){
					pass=true;
				}
			}
			if (rows.length > 0) {
		   		 if(pass==true){
						parent.$.messager.confirm('确认', '您是否确认运行选中行?', function(r) {
							if (r) {
								for ( var i = 0; i < rows.length; i++) {
										ids.push(rows[i].id);
								}
								parent.$.messager.progress({
									title : '提示',
									text : '数据处理中，请稍后....'
								});
								$.post('${pageContext.request.contextPath}/pms/equ/sbglPlan/batchAuditingRun.do', 
								{
									ids : ids.join(','),
									status:'2'
								}, function(json) {
									parent.$.messager.progress('close');
									if (json.success) {
										$.messager.show('提示', json.msg, 'info');
										queryWCPlan();
									} else {
										$.messager.show('提示', json.msg, 'info');
									}
								},"JSON");
							}
						});
				    }else{
				    	$.messager.show('提示', ' 审核失败！ 请车间主任审核后，车间管理员再审核 ', 'error');
				    }
			}else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要需要批准的数据!'
			});
		}
	}
	
	
	
    //删除设备保养计划
	function deleteWCPlan(){
		var rows = dataGrid.datagrid('getSelected');
		if(rows==null){
			$.messager.show('提示', '请只选一行数据', 'error');
		}else{
			//var row = dataGrid.datagrid('getSelected');
			var wheelCoverType = rows.wheelCoverType;//dataGrid.datagrid('getSelected').wheelCoverType;
			var zrName =rows.zrName; //dataGrid.datagrid('getSelected').zrName;
			var glName =rows.glName; //dataGrid.datagrid('getSelected').glName;
		    if(zrName!=null&&glName!=null&&zrName!=""&&glName!=""){
		    	if(wheelCoverType=='10'){
					parent.$.messager.confirm('操作提示', '您是否要删除当前设备保养计划？', function(b) {
						if (b) {	
							$.post('${pageContext.request.contextPath}/pms/wcp/deleteWCPlan.do', {
								id : rows.id
							}, function(json) {
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									queryWCPlan();
								} else {
									$.messager.show('提示', json.msg, 'error');
								}
							}, 'JSON');
						}
					});
				}else{
					$.messager.show('提示', '状态不是确认删除，无法进行删除！', 'error');
				}
		    }else{
		    	if(wheelCoverType=='0' || wheelCoverType=='9' || wheelCoverType=='10'){//将状态修改成为 申请删除-9
		    		$.messager.show('提示', '需要车间主任与车间管理同时批准！', 'error');
					$.post('${pageContext.request.contextPath}/pms/equ/sbglPlan/deleteWCPlan.do', {
						id : rows.id
					}, function(json) {queryWCPlan();}, 'JSON');
		    	}else{
		    		$.messager.show('提示', '状态已经确认，无法删除！', 'error');
		    	}
				
		    }
		}
		
	}
    
    
	function clearForm(){
		$("#searchForm input").val(null);
	}
	function wcPlanCal(){
		var dialogs=parent.$.modalDialog({
			title : '设备轮保日历',
			width : 630,
			height : 580,
			href : '${pageContext.request.contextPath}/pms/equ/wcplan/cal_opt.jsp',
			buttons : [ {
				text : '关闭',
				iconCls:'icon-standard-disk',
				handler : function() {
					dialogs.dialog('destroy');
				}
			} ]
		});
	}
	function batchAdd(){
		var dialogs=parent.$.modalDialog({
			title : '批量新增设备轮保',
			width : 750,
			height : 400,
			href : '${pageContext.request.contextPath}/pms/equ/wcplan/batchWCPlan.jsp',
			buttons : [ {
				text : '关闭',
				iconCls:'icon-standard-disk',
				handler : function() {
					dialogs.dialog('destroy');
					queryWCPlan();
				}
			} ]
		});
	}
	
	//新批量添加
	function batchAddPlan(){
		var addPlan = parent.$.modalDialog({
			title : '轮保计划添加',
			width : 660,
			height : 270,
			href : '${pageContext.request.contextPath}/pms/wcp/goToAddLubiPlan.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = addPlan.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/wcp/addEqmLubricatPlan.do",{
							eqp_category : f.form("getData").eqp_category,//设备类型
							eqp_typeId :f.form("getData").eqp_typeId,//设备机型
							eqp_typeName :f.form("getData").eqp_typeName,
							ruleId : f.form("getData").ruleId,//设备保养规则
							ruleText : f.form("getData").ruleName,//设备保养名称
							date_plan1 : f.form("getData").date_plan1,//开始时间
							date_plan2 : f.form("getData").date_plan2, //结束时间 
							matId : f.form("getData").matId, //牌号 
							equipmentMinute : f.form("getData").equipmentMinute, //轮保时长
							shiftId : f.form("getData").shiftId //班次 
					},function(json){
							if (json.success) {
								$.messager.show('提示', '操作成功', 'info');
								addPlan.dialog('destroy');
								queryLubiPlan();
							}else{
								$.messager.show('提示', "保存失败", 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false,split:true,title:'产出数据'" style="height:320px;">
		<div id="toolbar"  style="display: none;width:100%;">
			<form id="searchForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
							<span class="label">保养名称：</span>
							<input type="text" name="planName" class="easyui-validatebox "  data-options="prompt: '请输入保养计划名称'"/>
						</div>
						<div >
							<span class="label">保养编号：</span>
							<input type="text" name="planNo" class="easyui-validatebox "  data-options="prompt: '请输入保养计划编号'"/>
						</div>
						<div >
							<span class="label">保养时间：</span>
							<input id="date1" name="date1" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:130px"/>
						</div>	
						<div >
							<span class="label" style="width:10px">到</span>
							<input id="date2" name="date2" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:130px"/>
						</div>
						<div >
							<span class="label" style="width:40px">状态</span>
							<select id="wheelCoverType" name="wheelCoverType" class="easyui-combobox" name="dept" style="width:120px;">  
								<option value="">全部</option>  
							    <option value="0">新增</option>   
							    <option value="1">审核通过</option> 
							    <option value="2">运行</option> 
							    <option value="3">停止运行</option> 
							    <option value="4">操作工操作完毕</option> 
							    <option value="5">轮保工操作完毕</option> 
							    <option value="6">操作工、轮保工操作完毕</option> 
							    <option value="7">审核完毕</option> 
							    <option value="8">计划完成</option>
							    <option value="9">请求删除</option>
							    <option value="10">确认删除</option>
							    <option value="11">请求修改</option>
							    <option value="12">确认修改</option> 
							</select>
						</div>			
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/queryWCPlan']}">
					<a onclick="queryWCPlan()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/addWCPlan.do']}">
					<a onclick="goToAddWCPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
				</c:if> --%>
				<a onclick="batchAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">批量新增</a>
				<!-- <a onclick="batchAddPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">批量新增</a>  -->
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/editWCPlan.do']}">
					<a onclick="goToEditWCPlan()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/deleteWCPlan.do']}">	
					<a onclick="deleteWCPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a>
				</c:if> --%>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditing.do']}">
					<a onclick="Auditing('1','审批');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-drive-edit'">审核</a>
				</c:if> --%>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/batchAuditing.do']}">
					<a onclick="batchAuditing();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-drive-edit'">车间主任审批</a>
				</c:if>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingRun.do']}">
					<a onclick="Auditing('2','批准');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-application-go'">批准</a>
				</c:if>
               --%>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/batchAuditingrun.do']}">
					<a onclick="batchAuditingRun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-application-go'">车间管理批准</a>
				</c:if> 
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingstop.do']}">
					<!-- <a onclick="Auditing('3','停止');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-image-delete'">停止</a> -->
				</c:if>
 
				<a onclick="wcPlanCal();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-application-go'">轮保日历</a>
				
			</div>
		</div>
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'',border:false">
		<table id="paramGrid"></table>
	</div>
	 <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
	 	<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/addWCPlan.do']}">
			<div onclick="goToAddWCPlan();" href="javascript:void(0);"  data-options="iconCls:'icon-standard-plugin-add'">新增</div>
		</c:if> --%>
		<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditing.do']}">
			<div onclick="Auditing('1','审批');" href="javascript:void(0);"  data-options="iconCls:'icon-standard-drive-edit'">审批</div>
		</c:if> --%>
		<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingRun.do']}">
			<div onclick="Auditing('2','批准');" href="javascript:void(0);"  data-options="iconCls:'icon-standard-application-go'">批准</div>
		</c:if> --%>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingstop.do']}">
			<!-- <div onclick="Auditing('3','停止');" href="javascript:void(0);" data-options="iconCls:'icon-standard-image-delete'">停止</div> -->
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/editWCPlan.do']}">
			<div onclick="goToEditWCPlan()" href="javascript:void(0);"  data-options="iconCls:'icon-standard-report-edit'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/deleteWCPlan.do']}">
			<div onclick="deleteWCPlan();" href="javascript:void(0);"  data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>		
	</div>  
</body>
</html>