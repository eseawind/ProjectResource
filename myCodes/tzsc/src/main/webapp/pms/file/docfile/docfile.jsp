<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>资源管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
var tempUrl="${pageContext.request.contextPath}/pms/file/docfile";
	var treeGrid;
	var dialog;
	$(function() {
		treeGrid = $('#treeGrid').treegrid({
			idField : 'id',
			treeField : 'filename',
			dataPlain: true,
			fit : true,
			sortName : 'pid',
			sortOrder : 'esc',
			striped : true,
			fitColumns : false,
			border : false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'filename',
				title : '文件名称',
				width : 250
				
			}, {
				field : 'url',
				title : '文件路径',
				width : 230,
				hidden : true
			},{
				field : 'fileType',
				title : '文件后缀名',
				width : 80,
				align : 'center'
			}, {
				field : 'type',
				title : '文件类型',
				width : 60,
				align : 'center',
				formatter:function(value,row,index){
					return value=="1"?"<span style='color:blue;'>文件夹<span>":"文件";
				}
			}
			, {
				field : 'createTime',
				title : '上传时间',
				width : 250,
				align : 'center'
			}
			, {
				field : 'sysUserName',
				title : '上传用户',
				width : 120,
				align : 'center'
			} ] ],
			toolbar : '#toolbar',
			onContextMenu : function(e, row) {
				 e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
					
				}); 
			},
			onLoadSuccess : function() {
				parent.$.messager.progress('close');

				$(this).treegrid('tooltip');
			}
		});
	});
	function redo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('expandAll', node.id);
		} else {
			treeGrid.treegrid('expandAll');
		}
	}

	function undo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('collapseAll', node.id);
		} else {
			treeGrid.treegrid('collapseAll');
		}
	}
	/**查询*/
	function fileList() {
		treeGrid.treegrid({url : "${pageContext.request.contextPath}/pms/file/docfile/getList.do"});
	}
	/**上传*/
	function fileadd() {
		var row = treeGrid.datagrid("getSelected");//返回选中一行
		if(row==null||row==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要添加的上级文件夹!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '文件上传',
			width : 620,
			height : 450,
			href : tempUrl+'/fileuploadJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						var isTempUpload =f.form("getData").isUploadFile;//读取隐藏域中的值
						if(isTempUpload=="true"){//表示临时 上传文件了
							$.post(tempUrl+"/uploadfile.do?parentId="+row.id,f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									fileList();
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
	//添加文件夹
	function addfile(){
		var rowSelected = treeGrid.datagrid("getSelected");//返回选中一行
		if(rowSelected==null||rowSelected==""){
			//添加根文件夹
		var dialogfile = parent.$.modalDialog({
				title : '文件夹新增',
				width : 400,
				height : 180,
				href : tempUrl+'/fileAddJsp.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialogfile.find("#formcategory");
						if(f.form("validate")){
							$.post(tempUrl+"/savefile.do",f.form("getData"),function(json){				
									if (json.success) {		
										$.messager.show('提示', json.msg, 'info');
										fileList();
										dialogfile.dialog('destroy');		
									}else{
										$.messager.show('提示', json.msg, 'error');
									}
								},"JSON");
						}
					}
				} ]
			});
		}else{
		var dialogfile = parent.$.modalDialog({
			title : '文件夹新增',
			width : 400,
			height : 180,
			href : tempUrl+'/fileAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialogfile.find("#formcategory");
					if(f.form("validate")){
						$.post(tempUrl+"/savefile.do?parentId="+rowSelected.id,f.form("getData"),function(json){				
								if (json.success) {		
									$.messager.show('提示', json.msg, 'info');
									fileList();
									dialogfile.dialog('destroy');		
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
					}
				}
			} ]
		});
		}
	}
	
	//下载
	function Filedownload() {
		var row = treeGrid.datagrid('getSelected');
		if(row.type=="2"){
			var url =tempUrl+"/filedownload.do?fileurl="+row.url+"&fileName="+row.filename+"."+row.fileType;
			//var url="${service_url}"+row.url;
			var iWidth=600; //弹出窗口的宽度;
			var iHeight=200; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(url,"","toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
		}
	}
	/**定向到删除功能*/
	function deleteFile() {
		var rowSelected = treeGrid.datagrid("getSelected");//返回选中一行
	
		if(rowSelected.id==null){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要删除的记录!'
			});
			return false;
		}
		parent.$.messager.confirm('您是否要删除当前文档?', function(b) {
			if (b) {
				//var currentUserId = '${sessionInfo.user.id}';//当前登录用户的ID
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post(tempUrl+'/deleteFile.do', {
					ids : rowSelected.id
				}, function(json) {
				    parent.$.messager.progress('close');
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						fileList();
					}else{
						$.messager.show('提示', json.msg, 'info');
					}
				}, 'JSON');
			}
		});
	}
	//修改页面
	function fileEditJsp(){
		var rowSelected = treeGrid.datagrid("getSelected");//返回选中一行
		if(rowSelected==null||rowSelected==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialogfile = parent.$.modalDialog({
			title : '文件修改',
			width : 400,
			height : 180,
			href : tempUrl+'/fileupdateJsp.do?id='+rowSelected.id,
			buttons : [ {
				text : '修改',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialogfile.find("#formcategory");
					if(f.form("validate")){
						$.post(tempUrl+"/updatefilename.do",f.form("getData"),function(json){				
								if (json.success) {		
									$.messager.show('提示', json.msg, 'info');
									fileList();
									dialogfile.dialog('destroy');		
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
					}
				}
			} ]
		});
	}
	//预览
	function fileConvert(){
		var row = treeGrid.datagrid('getSelected');
		if(row.type=="2"){
			window.open(row.url);
		}else{
			$.messager.show('提示',"请选择文件预览", 'error');
		}
		
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
			<table id="treeGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		 <table style="margin:4px 0px 0px 0px ">
		 	<tr>
		 		<c:if test="${not empty sessionInfo.resourcesMap['/pms/file/docfile/docfile.do']}">	 
		 		<td><a onclick="fileList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a></td>
				</c:if>
				
				<td><a onclick="redo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-arrow-out'">展开</a></td>
				<td><a onclick="undo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-arrow-in'">折叠</a></td>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/file/docfile/savefile.do']}">
				<td><a onclick="addfile();"href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加文件夹</a></td>
		 		</c:if>
		 	</tr>
		 </table>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/file/docfile/fileuploadJsp.do']}">	 
				<div onclick="fileadd();" data-options="iconCls:'icon-hamburg-up'">上传文档</div>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/file/docfile/filedownload.do']}">	 
				<div onclick="Filedownload();" data-options="iconCls:'icon-standard-arrow-down'">下载文档</div>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/file/docfile/savefile.do']}">	 
				<div onclick="addfile();" data-options="iconCls:'icon-standard-plugin-add'">添加文件夹</div>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/file/docfile/fileupdateJsp.do']}">	 
				<div onclick="fileEditJsp();" id="updateButton" data-options="iconCls:'icon-standard-plugin-edit'">修改文档</div>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/file/docfile/deleteFile.do']}">	 
				<div onclick="deleteFile();" data-options="iconCls:'icon-standard-plugin-delete'">删除文档</div>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/ConvertServlet']}">
				<div onclick="fileConvert()" data-options="iconCls:'icon-standard-overView'">预览</div>
				</c:if>
	</div>
</body>
</html>