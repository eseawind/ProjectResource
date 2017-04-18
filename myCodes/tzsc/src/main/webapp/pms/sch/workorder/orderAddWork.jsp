<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		$.loadComboboxData($("#matProd1"),"MATPROD",false);
		$.loadComboboxData($("#unit"),"UNIT",false);
		//$.loadComboboxData($("#eqps"),"ALLEQPS",false);
		$.loadComboboxData($("#eqps"),"ALLROLLERS",false);
		var matPickGrid;
		$(function() {
			//初始化时间
		    var today = new Date();
			var month=today.getMonth()+1;
			if(month<10){month=("0"+month);}
			var day=today.getDate();
			if(day<10){day=("0"+day);}
		    var date=today.getFullYear()+"-"+month+"-"+day; 
		   $("#scrq_date1").val(date);	//时间用这个
		   $("#scrq_date2").val(date);
			//根据工单类型选择要填充的设备
			 $("#ot").combobox({
				onChange:function(n){
					if(n==1){
						$.loadComboboxData($("#eqps"),"ALLROLLERS",false);
					}else if(n==2){
						$.loadComboboxData($("#eqps"),"ALLPACKERS",false);
					}else if(n==3){
						$.loadComboboxData($("#eqps"),"ALLBOXERS",false);
					}else if(n==4){
						$.loadComboboxData($("#eqps"),"ALLFILTERS",false);
					}
				}
			}); 
			
			
			
			//下拉框事件 根据牌号获取辅料
			$('#matProd1').combobox({
                onChange: function (n, o) {
                	var bean ={matProd : n};
        			matPickGrid.datagrid({
        				url : "${pageContext.request.contextPath}/pms/constd/getAllConStds.do",
        				queryParams :bean
        			});
                }
            });
			matPickGrid = $('#matPickGrid').datagrid({
				rownumbers :true,
				idField : 'id',
				fit : true,
				singleSelect :true,
				fitColumns : false,
				remoteSort: false,
				border : false,
				striped : true,
				nowrap : true,
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				columns : [ [ {
					title : '编号',
					field : 'id',
					checkbox : true
				}, {
					field : 'matProdCode',
					title : '产品编号',
					width : 120,
					sortable : true
				} , {
					field : 'matProd',
					title : '产品',
					width : 120,
					sortable : true
				} , {
					field : 'matName',
					title : '辅料',
					width : 160,
					sortable : true
				}, {
					field : 'matUnitId',
					title : '辅料ID',
					width : 60,
					sortable : true
				}, {
					field : 'matUnitName',
					title : '辅料单位',
					width : 60,
					sortable : true
				}, {
					field : 'matCount',
					title : '辅料量',
					width : 60,
					sortable : true
				}, {
					field : 'des',
					title : '说明',
					width : 150,
					sortable : true
				}  ] ],
				toolbar : '#matPickToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				}
			});
		});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div id="matPickToolbar" style="display: none;">
	<div class="topTool">
		<form id="form" method="post">
			<fieldset >
				<div >
					<span class="label">车间：</span>
					<select id="ot" name="type"
							class="easyui-combobox"
							data-options="panelHeight:'auto',width:130">
						    <option value="1">卷烟机工单</option>
							<option value="2">包装机工单</option>						
							<option value="3">封箱机工单</option>						
							<option value="4">成型机工单</option>						
						</select>
				</div>
				<div >
					<span class="label">设备：</span> 
					<input id="eqps" name="equipmentId"
						class="easyui-combobox easyui-validatebox"
						data-options="textField:'name',valueField:'id',editable:false,width:130,required:true" />
				</div>
				<div >
					<span class="label">计划产量：</span>
					<input name="qty"  class="easyui-validatebox" value="50" data-options="required:true" style="width:70px"/> 
					<select id="unit" name="unitId"
						data-options="panelHeight:200,width:50,panelWidth:120,editable:false,required:true">
					</select>
				</div>
				<div >
					<span class="label">牌号：</span>
					<select id="matProd1" name="matId" data-options="panelHeight:200,width:130,editable:false,required:true"/>
				</div>
				<div >
					<span class="label">工单日期：</span>
					
					<input id="scrq_date1" name="date1" readOnly=true type="text"
							class="easyui-datebox" datefmt="yyyy-MM-dd"
							style="width:130px" required="required"/>
					<!-- <input id="number" name="number" class="easyui-numberbox" min="1" max="32" value="3" data-options="required:true" style="width:70px"/> --> 
				</div>
				<div >
					<span class="label" >&nbsp;&nbsp;到：</span>
					<input id="scrq_date2" name="date2" readOnly=true type="text"
							class="easyui-datebox" datefmt="yyyy-MM-dd"
							style="width:130px" required="required"/>
				</div>
			</fieldset>
		</form>
	</div>		
</div>


<div data-options="region:'center',border:false">
	<table id="matPickGrid"></table>
</div>
</div>
