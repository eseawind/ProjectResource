<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>数据字典</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		var twoGrid = null;
		$(function() {
			twoGrid = $('#twoGrid').datagrid({
				fit : true,
				fitColumns : true,//fitColumns= true就会自动适应宽度（无滚动条）
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				remoteSort:false,
				columns : [ [ {
					field : 'id',
					title : '二级ID',
					width : 120,
					hidden : true
				}, {
					field : 'code',
					title : '二级编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'name',
					title : '二级名称',
					width : 100,
					align:'center',
					
				}, {
					field : 'lxType',
					title : '保养角色',
					width : 100,
					align:'center',
					formatter : function(value, row, index) {
						var thisVal=value;//有写死的
						if(thisVal=='8af2d43f4d73d86d014d73df6da90000'){
							thisVal="操作工项目";
						}else if(thisVal=='8af2d43f4d73d86d014d73e1615a0001'){
							thisVal="机械轮保工项目";
						}else if(thisVal=='8af2d49050d2002d0150da33910005b2'){
							thisVal="电气轮保工项目";
						}
						return thisVal;
					}
				}, {
					field : 'djType',
					title : '点检角色',
					width : 100,
					align:'center',
					formatter : function(value, row, index) {
						var thisVal=value;//点检角色被写死
						if(thisVal=='8af2d43f4d73d86d014d73df6da90000'){
							thisVal="操作工点检";
						}else if(thisVal=='402899894db72650014db78d4035004f'){
							thisVal="机械维修工点检";
						}else if(thisVal=='402899894db72650014db78daf010050'){
							thisVal="机械维修主管点检";
						}else if(thisVal=='8af2d49050d2002d0150da342dfb05b3'){
							thisVal="电气维修工点检";
						}else if(thisVal=='8af2d49050d2002d0150da35251d05b4'){
							thisVal="电气维修主管点检";
						}
						return thisVal;
					}
				}, {
					field : 'oilName',
					title : '润滑油',
					width : 100,
					align:'center'
				}, {
					field : 'fashionName',
					title : '润滑方式',
					width : 100,
					align:'center'
				}, {
					field : 'fillUnitName',
					title : '单位',
					width : 100,
					align:'center',
					hidden:true
				}, {
					field : 'fillQuantity',
					title : '加注点、加注量',
					width : 180,
					align:'center',
					hidden:true
				}, {
					field : 'des',
					title : '数据描述',
					align:'center',
					width : 150
					
				}/* , {
					field : 'setImagePoint',
					title : '是否设置图片',
					align:'center',
					width : 150,
					formatter : function(value, row, index) {
						if(value!=null&&value!=''){
							value = '<span style="color:green">已设置<span>';
						}else{
							value = '<span style="color:red">未设置<span>';
						}
						return value;
					}
					
				} */, {
					field : 'enable',
					title : '是否启用',
					align:'center',
					width : 70,
					sortable : false,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
					}
				} ] ],
				toolbar : '#typeToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
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
	   function queryMdTypeByCategory(id){
		   twoGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/sys/eqptype/queryMdType.do?categoryId=" + id,
				queryParams :$("#typeSearchForm").form("getData")
		   });
	   }
	   //查询数据
	   function queryMdType(){
			var row = categoryGrid.datagrid('getSelected');
			if(row==null){
				queryMdTypeByCategory("");
			}else{
				queryMdTypeByCategory(row.id);
			}
	   }
	   //删除数据
	   function deleteMdType(){
		   var row = twoGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前数据？', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/sys/eqptype/deleteMdType.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryMdType();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
	   }
	   //跳转到添加页面
	   function gotoTypeAdd(){
		   var row = categoryGrid.datagrid('getSelected');
		   if(row!=null){
			   var dialog = parent.$.modalDialog({
					title : '数据添加',
					width :  720,
					height : 508,
					href : '${pageContext.request.contextPath}/pms/sys/eqptype/AddMdTypeJsp.do?cateid='+row.id,
					buttons : [ {
						text : '保存',
						iconCls:'icon-standard-disk',
						handler : function() {
							var f = dialog.find("#form");
							if(f.form("validate")){
								$.post("${pageContext.request.contextPath}/pms/sys/eqptype/addMdType.do",f.form("getData"),function(json){
									if (json.success) {
										$.messager.show('提示', json.msg, 'info');
										dialog.dialog('destroy');
										queryMdType();
									}else{
										$.messager.show('提示', json.msg, 'error');
									}
								},"JSON");
							}
						}
					} ]
				});
		   }else{
			   $.messager.show('提示', "请先选择数据！", 'error');
		   }
	   }
	   
	   //跳转到编辑页面
	   function gotoTypeEdit(){
		   var dialog = parent.$.modalDialog({
				title : '数据编辑',
				width :  720,
				height : 508,
				href : '${pageContext.request.contextPath}/pms/sys/eqptype/goToMdTypeJsp.do?id='+twoGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/sys/eqptype/addMdType.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryMdType();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
		var categoryGrid=null;
		$(function() {		
			
			categoryGrid = $('#categoryGrid').datagrid({
				fit : true,
				fitColumns : true,//fitColumns= true就会自动适应宽度（无滚动条）
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : '一级ID',
					width : 120,
					hidden : true
				}, {
					field : 'code',
					title : '一级编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'name',
					title : '一级名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'des',
					title : '一级描述',
					align:'center',
					width : 150,
					sortable : true
				}/* , {
					field : 'uploadUrl',
					title : '是否上传图片',
					align:'center',
					width : 150,
					formatter : function(value, row, index) {
						if(value!=null&&value!=''){
							value = '<span style="color:green">已上传<span>';
						}else{
							value = '<span style="color:red">未上传<span>';
						}
						return value;
					}
					
				} */, {
					field : 'enable',
					title : '是否启用',
					align:'center',
					width : 100,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
					}
				} ] ],
				toolbar : '#categoryToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#categoryMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				},
				onClickRow:function(rowIndex,rowData){
					queryMdTypeByCategory(rowData.id);
				}
			});
		});
   		/**
		* 查询数据
		*/	
		function queryMdCategory() {
			categoryGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/sys/eqpcategory/queryMdCategory.do",
				queryParams :$("#categroyForm").form("getData")
			});
		}
		
		function deleteCategory(){
			var row = categoryGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前数据？', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/sys/eqpcategory/deleteMdCategory.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryMdCategory();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
		}

		function gotoCategoryAdd(){
			var dialog = parent.$.modalDialog({
				title : '添加',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/sys/eqpcategory/addMdCategoryJsp.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#formcategory");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/sys/eqpcategory/addMdCategory.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryMdCategory();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
		function gotoCategoryEdit(){
			var dialog = parent.$.modalDialog({
				title : '数据编辑',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/sys/eqpcategory/gotoMdCategoryJsp.do?id='+categoryGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '修改',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/sys/eqpcategory/updatecategory.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryMdCategory();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}

		/**清空*/
	   function cleanOneQuery(){
		   $('#categroyForm input').val(null);	
	   }
	   /**清空*/
	   function cleanTwoQuery(){
		   $('#typeSearchForm input').val(null);	
	   }
	   /**一级上传图片**/
	   function uploadImages(){
		   var row = categoryGrid.datagrid('getSelected');
		   if(row!=null){
			   var dialog = parent.$.modalDialog({
					title : '文件上传',
					width : 620,
					height : 450,
					href : '${pageContext.request.contextPath}/pms/sys/eqpcategory/uploadJsp.do',
					buttons : [ {
						text : '保存',
						iconCls:'icon-standard-disk',
						handler : function() {
							var f = dialog.find("#form");
							if(f.form("validate")){
								var isTempUpload =f.form("getData").isUploadFile;//读取隐藏域中的值
								if(isTempUpload=="true"){//表示临时 上传文件了
									$.post("${pageContext.request.contextPath}/pms/sys/eqpcategory/updateFile.do?id="+row.id,f.form("getData"),function(json){
										if (json.success) {
											//queryProManage(rowSelected.id);
											$.messager.show('提示', json.msg, 'info');
											dialog.dialog('destroy');
											queryMdCategory();//重新查询 父类
											queryMdType();//重新查询下 子类
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
		   }else{
			   $.messager.show('提示', "请先选择一行数据！", 'info');
		   }
	 }
		 //一级预览图片
	 function yjLookImages(){
		 //这个 可以先查询出 图片的上传路径 通过传值的方式 让 hotspot.jsp 加载图片
		 var row = categoryGrid.datagrid('getSelected');
		 if(row==null||row==''){
			 $.messager.show('提示', "请先选择一行数据！", 'info');
			 return ;
		 }
		 if(row.uploadUrl!=null&&row.uploadUrl!=''){
		 	if(row!=null){
			   var dialog = parent.$.modalDialog({
					title : '预览',
					width : 820,
					height :750,
					href : '${pageContext.request.contextPath}/pms/sys/eqpcategory/lookMdCategoryJsp.do?uploadUrl='+row.uploadUrl
				});
		   }else{
			   $.messager.show('提示', "请先选择一行数据！", 'info');
		   }
		 }else{
			 $.messager.show('提示', "请先上传图片！", 'info');
		 }
	}
	 function lookImages(){
		 var oneRow = categoryGrid.datagrid('getSelected');
		 if(oneRow==null||oneRow==''){
			 $.messager.show('提示', "请先选择一行数据！", 'info');
			 return ;
		 }
		 if(oneRow.uploadUrl!=null&&oneRow.uploadUrl!=''){
			 var twoRow = twoGrid.datagrid('getSelected');
			 var contextPath="${pageContext.request.contextPath}";
			   if(twoRow!=null){
					//var url ='${pageContext.request.contextPath}/pms/sys/datadict/imagesHotspot/hotspot.jsp?uploadUrl='+oneRow.uploadUrl
					//		+"&twoRowId="+twoRow.id+"&contextPath="+contextPath;
					var url ='${pageContext.request.contextPath}/pms/sys/eqptype/gotoHotspotJsp.do?uploadUrl='+oneRow.uploadUrl
							+"&twoRowId="+twoRow.id+"&contextPath="+contextPath;

				   	var iWidth=1024; //弹出窗口的宽度;
					var iHeight=800; //弹出窗口的高度;
					var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
					var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
					window.open(url,"","toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
			   }else{
				   $.messager.show('提示', "请先选择一行数据！", 'info');
			   }
		 }else{
			 $.messager.show('提示', "请先上传图片！", 'info');
		 }
	}
	 
		//批量导入
		function inputExcelDatas(){
			dialog = parent.$.modalDialog({
				title : '批量导入',
				width : 700,
				height : 350,
				href : '${pageContext.request.contextPath}/pms/sys/eqpcategory/DatadictinputExcel.do',
				//href : '${pageContext.request.contextPath}/pms/sys/datadict/iframPage.jsp',
			});
		}
	 
	 
	 
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">	
	<div data-options="region:'west',border:true,split:false,title:'数据字典【可单击表格】'" style="width: 560px;">
		<div id="categoryToolbar"  style="display: none;">
			<form id="categroyForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
							<span class="label">一级编号：</span>
							<span><input type="text" name="code" class="easyui-validatebox "  data-options="prompt: '请输入数据编号'"/></span>
						</div>
						<div >
							<span class="label">一级名称：</span>
							<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '请输入数据名称'"/>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" style="width:1oo%;">
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/queryOneList.do']}">
					<a onclick="queryMdCategory()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/addOneBean.do']}">
					<a onclick="gotoCategoryAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/cleanOneQuery.do']}">
					<a onclick="cleanOneQuery();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">清空</a>
				</c:if>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/datadict/uploadImages.do']}">
					<a onclick="uploadImages();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">上传图片</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/lookImages.do']}">
					<a onclick="yjLookImages();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">预览图片</a>
				</c:if> --%>
			</div>
		</div>
		<table id="categoryGrid"></table>
	</div>
	<div id="categoryMenu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/editOneBean.do']}">
			<div onclick="gotoCategoryEdit()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/deleteOneBean.do']}">
			<div onclick="deleteCategory();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
	
	
	
	<div data-options="region:'center',border:true,title:'二级数据'" >
		<div id="typeToolbar" style="display: none;">
			<form id="typeSearchForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
							<span class="label">二级编码：</span>
							<span><input type="text" name="code" class="easyui-validatebox "  data-options="prompt: '请输入数据编码'"/></span>
						</div>
						<div >
							<span class="label">二级名称：</span>
							<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '请输入数据名称'"/>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" style="width:1oo%;">
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/queryTwoList.do']}">
					<a onclick="queryMdType()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/addTwoBean.do']}">
					<a onclick="gotoTypeAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/cleanTwoQuery.do']}">
					<a onclick="cleanTwoQuery();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">清空</a>
				</c:if>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/datadict/uploadImages.do']}">
					<a onclick="lookImages();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">设置图片</a>
				</c:if> --%>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/datadict/uploadImages.do']}">
					<a onclick="inputExcelDatas();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-plugin-add',plain:true">批量导入</a>
				</c:if>
			</div>
		</div>
		<table id="twoGrid"></table>
	</div>
	<div id="typeMenu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/editTwoBean.do']}">
			<div onclick="gotoTypeEdit()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sys/eqptype/deleteTwoBean.do']}">
			<div onclick="deleteMdType();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
</body>
</html>