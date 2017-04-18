<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>工艺产品管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/prodManage";
		var uploadGrid = null;
		var proManageGrid=null;
		$(function() {
			$.loadComboboxData($("#matprod"),"MATPROD",true);//加载下拉框数据

			proManageGrid = $('#proManageGrid').datagrid({
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
				sortName : 'createDatetime',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {//固定表头
					sortable : true,
					checkbox : true //全选框
				},{
					field : 'id',
					title : '主键ID',
					width :$(this).width() * 0.08,//表示百分之8
					hidden : true
				}, {
					field : 'mdMatName',
					title : '品名',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'prodNumber',
					title : '编号',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'prodName',
					title : '产品规程标题',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'prodContent',
					title : '指导书内容',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'prodVersion',
					title : '版本号',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value!=null&&value!=""){
                            value = "V"+value;
						}else{
							value = "V1.0";
						}
						return value;
					}
				}, {
					field : 'addUserName',
					title : '制作人',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'createDatetime',
					title : '制作日期',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'reviewUserName',
					title : '批准人',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'reviewDatetime',
					title : '批准日期',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}
				, {
					field : 'prodStatus',
					title : '审核状态',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value==0){
							return '<span style="color:green">未审核<span>';
						}else if(value==1){
							return '<span style="color:green">审核通过<span>';
						}else if(value==2){
							return '<span style="color:green">下发成功<span>';
						}else{
							return '<span style="color:green">未知状态<span>';
						}
					}
				}, {
					field : 'prodStop',
					title : '是否停用',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						return value==0?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
					}
				}] ],
				toolbar : '#proManageToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');//提示信息
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#proManageMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				},
				onClickRow:function(rowIndex,rowData){
					queryProManage(rowData.id);
				}
			});

			
			uploadGrid = $('#uploadGrid').datagrid({
				fit : true,
				fitColumns : true,//fitColumns= true就会自动适应宽度（无滚动条）
				//width:$(this).width()-252,  
				border : false,
				pagination : false,//是否显示 分页
				idField : 'id',
				striped : true,
				remoteSort: false,
				pageSize : 5,
				pageList : [5,10, 20, 60, 80, 100],
				//sortName : 'createDatetime',
				//sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [
				{
					field : 'id',
					title : '上传ID',
					width : 120,
					hidden : true
				}, {
					field : 'fileName',
					title : '上传名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'addUserName',
					title : '上传人',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'fileId',
					title : '下载',
					width : 100,
					align:'center',
					hidden :true,
					sortable : true
				},{
					field : 'prodVersion',
					title : '版本号',
					width : 100,
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value!=null&&value!=""){
                            value = "V"+value;
						}else{
							value = "V1.0";
						}
						return value;
					}
				}
				] ],
				//toolbar : '#typeToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');//提示信息
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#typeMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
			
			
		});

	   /** 查询所有*/	
		function getList() {
			proManageGrid.datagrid({
				url : tempUrl+"/getList.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
		/**根据主键ID查询*/
	   function queryProManage(id){
		   uploadGrid.datagrid({
				url :  tempUrl+"/getListById.do?proManageId=" + id
		   });
	   }
	   /**清空*/
	   function cleanQuery(){
		   $('#searchForm input').val(null);	
		   //$("#createDatetime").datebox("setValue", null);	时间用这个
	       $("#matprod").combobox("setValue", "");//下拉框赋值
	       $("#prodStatus").combobox("setValue", "");//下拉框赋值
	   }
		/**定向到新增页面*/
		function addProdManage(){
			var addmodalDialog = parent.$.modalDialog({
				title : '添加工艺规程',
				width : 600,
				height : 300,
				href : tempUrl+"/goEditJsp.do",
				buttons : [ {
					text : '保存',
					iconCls : 'icon-standard-disk',
					handler : function() {
						var f = addmodalDialog.find('#form');
						if(f.form("validate")){
							$.post(tempUrl+"/editProdManage.do?type=insert",f.form("getData"),function(json){
								if (json.success) {
									addmodalDialog.dialog('destroy');
									$.messager.show('提示', json.msg, 'info');
									getList();	
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}

		/**定向到编辑页面*/
		function editProdManage(){
			var rowSelected = proManageGrid.datagrid("getSelected");//返回选中一行
			if(rowSelected==null||rowSelected ==""){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择需要修改的记录!'
				});
				return false;
			}else if(rowSelected.prodStatus!='0'){
				parent.$.messager.show({
					title : '提示',
					msg : '规程审核后不可修改!'
				});
				return false;
			}
			var editmodalDialog = parent.$.modalDialog({
				title : '编辑工艺规程',
				width : 600,
				height : 300,
				href : tempUrl+'/goEditJsp.do?id='+proManageGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-standard-disk',
					handler : function() {
						var f = editmodalDialog.find("#form");
						if(f.form("validate")){
							$.post(tempUrl+"/editProdManage.do?type=edit",f.form("getData"),function(json){
								if (json.success) {
									editmodalDialog.dialog('destroy');
									$.messager.show('提示', json.msg, 'info');
									getList();	
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
						
					}
				} ]
			});
		}
		/**定向到删除功能*/
		function deleteProdManage() {
			var delIds=[];
			var rowSelected = proManageGrid.datagrid("getSelected");//返回选中一行
			delIds.push(rowSelected.id); //然后把单个id放到ids的数组中  
			if(delIds.length==0){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择需要删除的记录!'
				});
				return false;
			}
			/* else if(rowSelected.prodStatus!='0'){
				parent.$.messager.show({
					title : '提示',
					msg : '规程审核后不可删除!'
				});
				return false;
			} */
			parent.$.messager.confirm('您是否要删除当前数据?', function(b) {
				if (b) {
					//var currentUserId = '${sessionInfo.user.id}';//当前登录用户的ID
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post(tempUrl+'/deleteProdManage.do', {
						ids : delIds.join(',')
					}, function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getList();
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				}
			});
		}
		/**上传*/
		function uploadProdManage() {
			var rowSelected = proManageGrid.datagrid("getSelected");//返回选中一行
			if(rowSelected==null||rowSelected==""){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择需要上传的记录!'
				});
				return false;
			}
 			/*else if(rowSelected.prodStatus!='0'){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择 未审核 记录!'
				});
				return false;
			} */
			var dialog = parent.$.modalDialog({
				title : '文件上传',
				width : 620,
				height : 450,
				href : tempUrl+'/uploadJsp.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					id:'button-id',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							//$("button-id").attr("disabled",true);
							var isTempUpload =f.form("getData").isUploadFile;//读取隐藏域中的值
							if(isTempUpload=="true"){//表示临时 上传文件了
								$.post(tempUrl+"/updateFile.do?prodManageId="+rowSelected.id,f.form("getData"),function(json){
									if (json.success) {
										queryProManage(rowSelected.id);
										$.messager.show('提示', json.msg, 'info');
										dialog.dialog('destroy');
									}else{
										$.messager.show('提示', json.msg, 'error');
									}
								},"JSON");
							}else{
								alert('请选择至少一个文件进行上传！');
							}
						}
					}
				} ]
			});
		}

		/**审核*/
		function reviewProdManage() {	
			var ids=[];
			var rowSelected = proManageGrid.datagrid("getSelected");//返回选中一行
			ids.push(rowSelected.id); //然后把单个id放到ids的数组中  
			if(ids.length==0){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择需要审核的记录!'
				});
				return false;
			}else if(rowSelected.prodStatus!='0'){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择 未审核 记录!'
				});
				return false;
			}
			parent.$.messager.confirm('您是否要审核当前数据?', function(b) {
				if (b) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post(tempUrl+'/reviewProdManage.do', {
						ids :ids.join(','),
						status:'1'
					}, function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getList();
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				}
			});
		}

		/**下发*/
		function sendProdManage() {	
			var ids=[];
			var rowSelected = proManageGrid.datagrid("getSelected");//返回选中一行
			ids.push(rowSelected.id); //然后把单个id放到ids的数组中  
			if(ids.length==0){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择需要下发的记录!'
				});
				return false;
			}else if(rowSelected.prodStatus!='1'){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择 审核通过 记录!'
				});
				return false;
			}
			parent.$.messager.confirm('您是否要下发当前数据?', function(b) {
				if (b) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post(tempUrl+'/sendProdManage.do', {
						ids :ids.join(','),
						status:'2'
					}, function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getList();
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				}
			});
		}

		/**定向到删除功能*/
		function deleteProdFile() {
			var proManageRow = proManageGrid.datagrid("getSelected");//返回选中一行
			var delIds=[];
			var rowSelected = uploadGrid.datagrid("getSelected");//返回选中一行
			delIds.push(rowSelected.id); //然后把单个id放到ids的数组中  
			if(delIds.length==0){
				parent.$.messager.show({
					title : '提示',
					msg : '请选择需要删除的记录!'
				});
				return false;
			}
			/*else if(proManageRow.prodStatus!='0'){
				parent.$.messager.show({
					title : '提示',
					msg : '规程审核后不可删除!'
				});
				return false;
			} */
			parent.$.messager.confirm('您是否要删除当前文档?', function(b) {
				if (b) {
					//var currentUserId = '${sessionInfo.user.id}';//当前登录用户的ID
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post(tempUrl+'/deleteProdFile.do', {
						ids : delIds.join(',')
					}, function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							queryProManage(proManageRow.id);
							$.messager.show('提示', json.msg, 'info');
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				}
			});
		}
		//下载
		function downloadFile(){
			var row = uploadGrid.datagrid('getSelected');
			var url ="${pageContext.request.contextPath}/pms/mfc/downloadFile.do?fileId="+row.fileId+"&fileName="+row.fileName;
			var iWidth=600; //弹出窗口的宽度;
			var iHeight=200; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(url,"","toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
		}
		//预览
		function fileConvert(){
			var row = uploadGrid.datagrid('getSelected');
			window.open(row.fileId);
			/* var row = uploadGrid.datagrid('getSelected');
			 window.open("${pageContext.request.contextPath}/ConvertServlet?fileId="+row.fileId);*/
		}
		
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	
	<div id="proManageToolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
	 			<fieldset >
	 				<div >
						<span class="label">品名：</span>
						<select id="matprod" name="procId" class="easyui-combobox" data-options="panelHeight:200,width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">规程名称：</span>
						<input type="text" name="prodName" class="easyui-validatebox "  data-options="width:120,prompt: '规程名称模糊查询'"/>
					</div>
					<div >
						<span class="label">编号：</span> 
							<input type="text"
							name="prodNumber" class="easyui-validatebox "
							onkeyup="value=value.replace(/[^\a-zA-Z0-9\_-]/g,'')"
							data-options="width:120,prompt: '编号模糊查询'" />
					</div>
					<div >
						<span class="label">版本号：</span> 
							<input type="text" name="prodVersion" class="easyui-validatebox"
							onkeyup="value=value.replace(/[^\w\.]/g,'')"
							data-options="width:120,prompt: '版本号模糊查询'" />
					</div>
					<div >
						<span class="label">审核状态：</span>
						<select  id="prodStatus" name="prodStatus" class="easyui-combobox fselect" 
							data-options="panelHeight:'auto',width:120,required:true">
								<option value="">全部</option>
								<option value="0">未审核</option>
								<option value="1">审核通过</option>
								<option value="2">下发成功</option>
						</select>
					</div>
	 			</fieldset>
	 		</div>
		</form>
		<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/getList.do']}">
				<a onclick="getList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
			</c:if>
			 <c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/cleanQuery.do']}">
				<a onclick="cleanQuery();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">清空</a>
			</c:if>
			 <c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/addProdManage.do']}">
				<a onclick="addProdManage();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-brick-add',plain:true">新增</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'west',border:true,split:false,title:'【可单击表格数据】'" style="width:630px;">
		<table id="proManageGrid"></table>		
	</div>
	<div data-options="region:'center',border:true" title="【文档】">
		<table id="uploadGrid"></table>	
	</div>  
	<!-- 右键功能 -->
	<div id="proManageMenu" class="easyui-menu" style="width: 80px; display: none;">
	 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/editProdManage.do']}">
			<div onclick="editProdManage()" data-options="iconCls:'icon-standard-brick-edit'">修改工艺</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/uploadProdManage.do']}">
			<div onclick="uploadProdManage();" data-options="iconCls:'icon-hamburg-up'">上传文档</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/deleteProdManage.do']}">
			<div onclick="deleteProdManage();" data-options="iconCls:'icon-standard-brick-delete'">删除工艺</div>
		</c:if>
		 <c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/reviewProdManage.do']}">
			<div onclick="reviewProdManage();" data-options="iconCls:'icon-standard-brick-edit'">审核工艺</div>
		</c:if>
		 <c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/sendProdManage.do']}">
			<div onclick="sendProdManage();" data-options="iconCls:'icon-standard-brick-go'">下发工艺</div>
		</c:if>
	</div>
	<!-- 删除文档功能 -->
	<div id="typeMenu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/deleteProdFile.do']}">
			<div onclick="deleteProdFile();" data-options="iconCls:'icon-standard-cross'">删除文档</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/prodManage/downloadFile.do']}">
			<div onclick="downloadFile();" data-options="iconCls:'icon-standard-arrow-down'">下载文档</div>
		</c:if>
			<div onclick="fileConvert()" data-options="iconCls:'icon-standard-plugin-add'">预览</div>
	</div>
	
</body>
</html>