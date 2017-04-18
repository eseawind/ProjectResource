<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 批量增加 -->
<script type="text/javascript">
	var detailGrid=null;
	$(function() {
		detailGrid=$('#detailGrid').datagrid({
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
			sortName : 'id',
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
				field : 'code',
				title : '编号',
				width : 150,
				align : 'center',
				sortable : true
			}, {
				field : 'name',
				title : '名称',
				width : 500,
				align : 'left',
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
		query();
	});
	function query(){
		var param={categoryId:'${bean.paul_id}'};
		detailGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/sys/eqptype/queryMdType.do",
			queryParams:param,
			onLoadError : function(data) {
				$.messager.show('提示', "查询保养计划异常", 'error');
			}
		});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" title="" style="overflow: hidden;padding:5px;height100px;">
		
			<fieldset>
				<table class="table" style="width:97%;">
					<tr>
						<th>日保养计划名称</th>
						<td>
							<input id="id" name="id" type="text" value="${bean.id}" style="display:none;readonly" />
							${bean.name }
						</td>
						<th>设备类型</th>
						<td>
							${bean.eqp_type_name}
						</td>
					</tr>
					
					<tr>
						<th>班次</th>
						<td>
							${bean.shiftName}
						</td>
						<th>班组</th>
						<td>
							${bean.teamName}
						</td>
					</tr>
					<tr>
						<th>计划开始时间</th>
						<td>
							${bean.stim}
						</td>
						<th>计划结束时间</th>
						<td>
							${bean.etim}
						</td>
					</tr>
					
				</table>
			</fieldset>
	</div>
	
	<legend>日保养计划内容</legend>
	<div data-options="region:'center',border:false" style="width:95%;height:400px;">
		<table id="detailGrid"></table>
	</div>
	
		
</div>