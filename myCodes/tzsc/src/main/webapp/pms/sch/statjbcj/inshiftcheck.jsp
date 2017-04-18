<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>生产实绩</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript" src="" charset=""></script>
<script type="text/javascript">
		var inputGrid = null;
		var outputGrid=null;
		
		$(function() {
			var d = new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			 $("#date").datebox("setValue",sts);
			 $("#date2").datebox("setValue",end);
			$.loadComboboxData($("#shift"),"SHIFT",true);
			$.loadComboboxData($("#team"),"TEAM",true);
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				remoteSort:false, 
				pagination : true,
				rownumbers :true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'eqpname',
					title : '设备名称',
					width : 100,
					align:'center'
				},{
					field : 'lotid',
					title : '批次号',
					align:'center',
					width : 100
				}, {
					field : 'entryid',
					title : '工单编号',
					align:'center',
					width : 140
				},{
					field : 'outmaterial',
					title : '品牌',
					align:'center',
					width : 140
				},{
					field : 'planqty',
					title : '实际产量',
					align:'center',
					width : 100
				},{
					field : 'shiftname',
					title : '班次',
					align:'center',
					width : 100
				},{
					field : 'teamname',
					title : '班组',
					align:'center',
					width : 100
				},{
					field : 'id',
					title : '实际日期',
					width : 130,
					align:'center'
				},{
					field : 'createtime',
					title : '接收时间',
					align : 'center',
					width : 180
				} ] ],
				toolbar : '#toolbar',//toolbar属性让outputToolbar整个模块变成工具栏
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');//给form表单加载数据
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
			shiftcheckdataGrid = $("#shiftcheckdataGrid").datagrid({
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
					field : 'type',
					title : '类型',
					width : 80,
					align:'center',
					sortable : true,
					formatter:function(value,row,index){
						console.info("类型值为："+value);
						var ty="";
						switch(value){
							case "1":
								ty = "实领";
								break;
							case "2":
								ty = "实退";
								break;
							case "3":
								ty = "虚领";
								break;
							case "4":
								ty = "需退";
								break;
						}
						return ty;
					} } ,{
					field : 'id',
					title : 'id',
					width : 120,
					hidden : true
				}, {
					field : 'materialName',
					title : '物料名称',
					width : 200,
					align : 'center',
					sortable : true
				}, {
					field : 'materialClass',
					title : '物料分类',
					align : 'right',
					width : 80,
					sortable : true
				}, {
					field : 'qty',
					title : '物料消耗',
					align : 'left',
					width : 50,
					sortable : true
				}, {
					field : 'uom',
					title : '单位',
					align : 'center',
					width : 150,
					sortable : true
				}] ],
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
		//查询所有卷包车间班内考核信息
		function queryBean() {
			var tempUrl ="${pageContext.request.contextPath}/pms/sch/statjbcj";
			dataGrid.datagrid({
				url : tempUrl+"/queryshiftcheck.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
		//清空查询条件表单
	 	function clearForm(){
			$("#searchForm input").val(null);
		}
	 	//根据选中的卷包车间班内考核信息，显示该条记录的详细信息
	 	function getAllInputsByOutput(id){
	 		var tempurl="${pageContext.request.contextPath}/pms/sch/statjbcj";
	 		shiftcheckdataGrid.datagrid({
	 			url : tempurl+"/queryshiftcheckinfo.do?id="+id
	 		});
	 	}
	 	//点击添加，为该条卷包车间班内考核信息添加相应的虚领需退信息
		function goToInputAddJsp(){
			var row = dataGrid.datagrid('getSelected');
			if(row){
				var dialog = parent.$.modalDialog({
					title : '班内考核添加管理',
					width : 640,
					height : 320,
					href : '${pageContext.request.contextPath}/pms/sch/statjbcj/goToInputAddJsp.do?id='+row.id+"&entryid="+row.entryid+"&lotid="+row.lotid,
					buttons : [ {
						text : '保存',
						iconCls:'icon-standard-disk',
						handler : function() {
							var f = dialog.find("#form");
							if(f.form("validate")){
								$.post("${pageContext.request.contextPath}/pms/sch/statjbcj/addInput.do",f.form("getData"),function(json){
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
	 	//点击删除，对该条班内考核具体信息进行删除
	 	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false,split:true,title:'班内考核'" style="height:300px;">
		<div id="toolbar" style="display: none;width:100%;">
			<form id="searchForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset>
						<div>
							<span class="label">接收日期：</span>
							<input id="date" name="date" type="text" class="easyui-datebox" datefmt="yyyy-MM-dd" style="width:100px"/>
						</div>
						<div>
							<span class="label">&nbsp;到：</span>
							<input id="date2" name="date2" type="text" class="easyui-datebox" datefmt="yyyy-MM-dd" style="width:100px"/>
						</div>
						<div>
							<span class="label">班组</span>
							<select id="shift" name="shiftId" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
						</div>
						<div>
							<span class="label">班次</span>
							<select id="team" name="teamId" class="easyui-combobox" data-options="panelHeight:'auto',width:'100',editable:'false'"></select>
						</div>
						<div>
							<span class="label" style="width:60px;">型号</span>
							<select id="eqptype" name="eqptype" class="easyui-combobox" data-options="panelHeight:200,width:'150',editable:false">
								<option value="">全部</option>
								<option value="1">卷烟机</option>
								<option value="2">包装机</option>
								<option value="3">装箱机</option>
								<!-- <option value="4">成型机</option> -->
							</select>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar">
				<a onclick="queryBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			</div>
		</div>
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'center',border:false,split:true,title:'班内考核详情'">
		<div class="easyui-toolbar" >
			<a onclick="goToInputAddJsp()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
			<a onclick="goToInputupdateJsp()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'" style="height:20px;">删除</a>
		</div>
		<table id="shiftcheckdataGrid"></table>
	</div>
</body>
</html>