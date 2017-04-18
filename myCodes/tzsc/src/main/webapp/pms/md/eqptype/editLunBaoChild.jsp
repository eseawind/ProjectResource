<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		//查询
		function getList(){
			var queryId = $("#queryId").val();
			if(queryId==null){
				$.messager.show('提示','请先选择设备型号', 'info');
			}else{
				editMdTypeChildGrid.datagrid({
	  				url : "${pageContext.request.contextPath}/pms/md/eqptypeChild/queryMdType.do?queryMetPid=" + queryId,
	  				queryParams :$("#form").form("getData")
	  		   });
			}
		  }
		var editMdTypeChildGrid;
		$(function() {
			editMdTypeChildGrid = $('#sysEqpCategoryGrid').datagrid({
				fit : true,
				fitColumns : false,//fitColumns= true就会自动适应宽度（无滚动条）
				//width:$(this).width()-252,  
				border : false,
				pagination : false,//分页显示
				idField : 'secId',
				striped : true,
				remoteSort: false,
				pageSize : 10,
				pageList : [10, 20, 60, 80, 100],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,		//这个属性很重要,确保可以多选
				showPageList:false,
				columns : [ [ {
					title : '数据字典ID',
					field : 'secId',
					checkbox : true
				}, {
					field : 'secCode',
					title : '编号',
					width : 120,
					sortable : true
				} , {
					field : 'secName',
					title : '名称',
					width : 120,
					sortable : true
				}, {
					field : 'isCheck',
					title : '是否绑定',
					width : 120,
					sortable : true
					,formatter : function(value, row, index) {
						return value=='false'?'<span style="color:green">未绑定<span>':'<span style="color:red">绑定<span>';
					}
				} , {
					field : 'type',
					title : '类别',
					width : 80,
					sortable : true
					,hidden:true
				}, {
					field : 'metcId',
					title : '主键ID',
					width : 120,
					sortable : true
					,hidden:true
				}  
				] ],
				toolbar : '#editMdTypeChildToolbar',
				onLoadSuccess : function(row) {//当表格成功加载时执行       
					$(this).datagrid('tooltip');
					var rowData = row.rows;
					 $.each(rowData,function(idx,val){//遍历JSON
	                      if(val.isCheck=='true'){
	                    	  editMdTypeChildGrid.datagrid('checkRow', idx);//选中当前行
	                    	  //editMdTypeChildGrid.datagrid("selectRow", idx);//如果数据行为已选中则选中改行
	                      }else{
	                    	  editMdTypeChildGrid.datagrid('uncheckRow', idx);//选中当前行
			              }
	                }); 
				}
			});
		});

		getList();//默认查询
		
		//新增
		function addList(){
			var queryId = $("#queryId").val();//设备型号ID
			var reqString ='[';//辅料对象
			var rows = editMdTypeChildGrid.datagrid('getChecked');
			for(var i=0;i<rows.length;i++){
				reqString += '{"secId":"'+rows[i].secId//数据字典ID
				+ '","secPid":"'+rows[i].secId//绑定的数据字典大类ID
				+ '","metPid":"'+ queryId//设备型号ID
				+ '","metcId":"'+rows[i].metcId//轮保ID,用于判断新增 or 假删除
				+ '","del":"0","type":"lb"}';
				if(i<(rows.length-1)){
					reqString += ',';
				}
			}
			reqString += "]";
			if(reqString.length<10){
				$.messager.show('提示','请勾选需要添加项', 'info');
				return false;
			}
			//alert(reqString);
			//return;
			var pams = {
				type:'lb',
				isInsert:'save',
				reqString : reqString
			};
			$.post("${pageContext.request.contextPath}/pms/md/eqptypeChild/editMdTypeChild.do",pams,function(json){
				if (json.success) {
					getList();//查询
					$.messager.show('提示','保存成功', 'info');
					//editMdTypeChildGrid.dialog('destroy');
				}else{
					$.messager.show('提示','保存失败', 'error');
				}
			},"JSON");
		}

		//删除
		function delList(){
			var queryId = $("#queryId").val();//设备型号ID
			var reqString ='[';//辅料对象
			var rows = editMdTypeChildGrid.datagrid('getChecked');
			for(var i=0;i<rows.length;i++){
				reqString += '{"secId":"'+rows[i].secId//数据字典ID
				+ '","secPid":"'+rows[i].secId//绑定的数据字典大类ID
				+ '","metPid":"'+ queryId//设备型号ID
				+ '","metcId":"'+rows[i].metcId//轮保ID,用于判断新增 or 假删除
				+ '","del":"1","type":"lb"}';
				if(i<(rows.length-1)){
					reqString += ',';
				}
			}
			reqString += "]";
			if(reqString.length<10){
				$.messager.show('提示','请勾选需要取消项', 'info');
				return false;
			}
			var pams = {
				type:'lb',
				isInsert:'update',
				reqString : reqString
			};
			$.post("${pageContext.request.contextPath}/pms/md/eqptypeChild/editMdTypeChild.do",pams,function(json){
				if (json.success) {
					getList();//查询
					$.messager.show('提示','取消成功', 'info');
					//editMdTypeChildGrid.dialog('destroy');
				}else{
					$.messager.show('提示','取消失败', 'error');
				}
			},"JSON");
		}
		
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div id="editMdTypeChildToolbar" style="display: none;">
		<div class="topTool">
			<form id="form" method="post">
				<fieldset >
				<table>
					<tr>
						<td><span class="label">数据名称：</span></td>
						<td>
							<input type="hidden" id="queryId" name="queryId" value="${bean.queryId}"/>
							<input type="hidden" id="queryType" name="queryType" value="lb"/>
							<input type="text" name="queryName" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100"/>
						</td>
						<td>
							<a onclick="getList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
						</td>
						<td>
							<a onclick="addList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-disk',plain:true">添加绑定</a>
						</td>
						<td>
							<a onclick="delList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-disk',plain:true">取消绑定</a>
						</td>
					</tr>
				</table>
				</fieldset>
			</form>
		</div>		
	</div>

	<div data-options="region:'center',border:false">
		<table id="sysEqpCategoryGrid"></table>
	</div>
</div>
