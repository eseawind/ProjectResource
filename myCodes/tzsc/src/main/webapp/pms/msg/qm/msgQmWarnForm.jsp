<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function queryUser(){
	var name=$("input[name='name']").val();
	var gender = $("select[name='gender']").val();
	var queryParams = {name:name,gender:gender};
	$("#sysUser_id").combogrid({
		url:"${pageContext.request.contextPath}/pms/sysUser/getAllUser.do",
		panelWidth:300,
		panelheight:400,
		queryParams :queryParams,
		toolbar:"#grid-toolbar",
	    textField:'name',
	    fit : true,
		fitColumns : false,
		pagination : true,
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		sortName : 'name',
		sortOrder : 'asc',
		singleSelect:true,
		nowrap : false,
		showPageList:false,
		columns : [ [
				{
					field : 'id',
					title : '编号',
					width : 150,
					checkbox : true
				}, {
					field : 'name',
					title : '姓名',
					width : 60,
					align:'center',
					sortable : true
				} , {
					field : 'eno',
					title : '工号',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'gender',
					title : '性别',
					width : 30,
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						return value==0?'女':'男';
					}
				}
		] ],
	});
}
$(function(){
	queryUser();
});
</script>
<div id="grid-toolbar" style="display: none;">
	<table>
		<tr>
			<td>
				<form id="searchForm" style="margin:4px 0px 0px 0px">
					<table>
						<tr>
							<th>姓名/工号</th>
							<td><input type="text" name="name" class="easyui-validatebox " style="width:80px;" /></td>
							<th>性别</th>
							<td>
							<select name="gender" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
							        <option value="">　</option>
									<option value="1">男</option>
									<option value="0">女</option>
							</select>
							</td>
							<td>
								<input type="button" value="筛选" onclick="queryUser()">
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>机台通知信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>工单</th>
						<td >
							<input type=hidden name=id value="${msgQmWarn.id }"/>
							<input type="text" class="e-input" name="schWorkorder.id" value="${msgQmWarn.schWorkorder.id }">
						</td>
						<th>质检员</th>
						<td >
							<input type="text" id="sysUser_id" name="sysUser.id" value="${msgQmWarn.sysUser.id }" class="easyui-combogrid easyui-validatebox" data-options="textField:'name',valueField:'id'"/>
						</td>
					</tr>
					<tr>
						<th>质量标准值</th>
						<td >
							<input type="text" class="e-input" name="qi" value="${msgQmWarn.qi }">
						</td>
						<th>质量实际值</th>
						<td >
							<input type="text" class="e-input" name="val" value="${msgQmWarn.val }">
						</td>
					</tr>
					<tr>
						<th>科目</th>
						<td >
							<input type="text" class="e-input" name="item" value="${msgQmWarn.item }">
						</td>
						<th></th>
						<td >
						</td>
					</tr>
					<tr>
						<th>告警内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="content">${msgQmWarn.content }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>