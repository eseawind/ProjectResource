<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 给机构分配用户 -->
<script type="text/javascript">
	//${pageContext.request.contextPath}/pms/sysOrg/getOrgUsers.do?id=${checkedOrgId}
	var unAssignGrid;
	var assignedGrid;
	var checkedOrgId =	"${checkedOrgId}";
	$(function() {
		unAssignGrid = $('#unAssignGrid').datagrid({
			url:"${pageContext.request.contextPath}/pms/sysOrg/getOrgUnAssignedUsers.do?oid=${checkedOrgId}",
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
			idField : 'id',
			striped : true,
			rownumbers:true,
			singleSelect:true,
			checkOnSelect : true,
			selectOnCheck : false,
			nowrap : false,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'eno',
				title : '工号',
				align:'center',
				width : 80,
				sortable : true
			}, {
				field : 'name',
				title : '姓名',
				width : 60,
				align:'center',
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
			} ] ],
			toolbar : '#unAssignGrid-toolbar',
			onLoadSuccess : function() {								
				$(this).datagrid('tooltip');
			},
			onDblClickRow : function(rowIndex, rowData){
				dblClickCopyColumnTo(unAssignGrid,assignedGrid,rowData,rowIndex)
			}
		});
		 assignedGrid = $('#assignedGrid').datagrid({
			url:"${pageContext.request.contextPath}/pms/sysOrg/getOrgAssignedUsers.do?oid=${checkedOrgId}",
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
			idField : 'id',
			rownumbers:true,
			striped : true,
			singleSelect:true,
			checkOnSelect : true,
			selectOnCheck : false,
			nowrap : false,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'eno',
				title : '工号',
				align:'center',
				width : 80,
				sortable : true
			}, {
				field : 'name',
				title : '姓名',
				width : 60,
				align:'center',
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
			} ] ],
			toolbar : '#assignedGrid-toolbar',
			onLoadSuccess : function() {								
				$(this).datagrid('tooltip');
			},
			onDblClickRow : function(rowIndex, rowData){
				dblClickCopyColumnTo(assignedGrid,unAssignGrid,rowData,rowIndex)
			}
		});  
	});
	function search(){
		unAssignGrid.datagrid("reload",{name:$("input[name='name']").val()});
	}
	function refresh(){
		$("input[name='name']").val(null);
		unAssignGrid.datagrid("reload",{name:$("input[name='name']").val()});
		assignedGrid.datagrid("reload");
	}
	function copyColumnTo(sourceGrid,targetGrid,method){
		var rows=sourceGrid.datagrid(method);
		var indexs=new Array();;
		//添加
		for(var i=0;i<rows.length;i++){
			
			insertColumn(targetGrid,rows[i]);
			indexs[i]=rows[i].id;
		}
		//targetGrid.datagrid("deleteRows",indexs);
		//删除
		var arr=new Array();
		for(var i=0;i<rows.length;i++){
			var index = sourceGrid.datagrid("getRowIndex",rows[i]);			
			arr.push(index);
		}
		arr.sort(function(a,b){return a>b?1:-1});// js 默认情况下sort方法是按ascii字母顺序排序的，而非我们认为是按数字大小排序 如不重写  会出现[1,10,12,2,20]的排序
		
		var ls=0;
		for(var i=0;i<arr.length;i++){			
			deleteColumn(sourceGrid,arr[i]-ls);
			ls+=1; 
		}
	}
	//定义表格双击
	function　dblClickCopyColumnTo(sourceGrid,targetGrid,row,sourceIndex){
		insertColumn(targetGrid,row);
		deleteColumn(sourceGrid,sourceIndex);
	}
	function assignChecked(){
		copyColumnTo(unAssignGrid,assignedGrid,"getChecked");
	}
	function assignPage(){
		copyColumnTo(unAssignGrid,assignedGrid,"getRows");
	}
	function unAssignChecked(){
		copyColumnTo(assignedGrid,unAssignGrid,"getChecked");
	}
	function unAssignPage(){
		copyColumnTo(assignedGrid,unAssignGrid,"getRows");
	}
	function deleteColumn(target,index){
		target.datagrid('deleteRow',index);
	}
	function insertColumn(target,column){
		target.datagrid('insertRow',{
			index: 0,
			row:column
		});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="unAssignGrid-toolbar" style="display: none;">				
			<table>
				<tr>
					<td>
						<form id="searchForm" style="margin:4px 0px 0px 0px">
							<table>
								<tr>
									<th>用户名</th>
									<td><input name="name" style="width: 80px;height:18px"/></td>
									<td><a href="javascript:search();" title="查询" class="l-btn l-btn-small l-btn-plain"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon icon-standard-zoom">&nbsp;</span></span></a></td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
	</div>
	<div data-options="region:'west',border:false,width:315" title="" style="overflow: hidden;background-color: red">
		<table id="unAssignGrid"></table>
	</div>
	<div data-options="region:'center',border:false" title="" class="dialog-button" style="overflow: hidden;">
		<div style="width:38px;margin:60px 0px 0px 0px">
		<a href="javascript:refresh();" title="重置"  class="l-btn l-btn-small l-btn-plain"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon pagination-load">&nbsp;</span></span></a>
		<br>
		<br>
		<a href="javascript:assignChecked();" title="分配选中用户" class="l-btn l-btn-small l-btn-plain"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon pagination-next">&nbsp;</span></span></a>
		<br>
		<br>
		<a href="javascript:assignPage();" title="分配本页用户" class="l-btn l-btn-small l-btn-plain"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon pagination-last">&nbsp;</span></span></a>
		<br>
		<br>
		<a href="javascript:unAssignChecked();" title="取消分配选中用户" class="l-btn l-btn-small l-btn-plain"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon pagination-prev">&nbsp;</span></span></a>
		<br>
		<br>
		<a href="javascript:unAssignPage();" title="取消分配本页用户" class="l-btn l-btn-small l-btn-plain"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon pagination-first">&nbsp;</span></span></a>
		<br>
		<br>
		</div>
	</div>
	<div id="assignedGrid-toolbar" style="display: none;">				
			<table style="height:36px">
				<tr>
					<td>
						<code>已分配用户</code>
					</td>
				</tr>
			</table>
	</div>
	<div data-options="region:'east',border:false,width:315" title="" style="overflow: hidden;">
		<table id="assignedGrid"></table>
	</div>
</div>