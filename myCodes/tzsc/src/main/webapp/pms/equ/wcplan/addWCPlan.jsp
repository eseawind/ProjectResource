<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var addWcpLbgzGrid;
	var addLbgzGrid2;	
	$(function() {
		$.loadComboboxData($("#shiftId"),"SHIFT",false);
		$.loadComboboxData($("#matProd1"),"MATPROD",false);
		$("#maintenanceContent").attr("value","");//点击的时候 赋值 主键ID
		//初始化时间
	    var today = new Date();
		var month=today.getMonth()+1;
		if(month<10){month=("0"+month);}
		var day=today.getDate();
		if(day<10){day=("0"+day);}
	    var date=today.getFullYear()+"-"+month+"-"+day;
	    $("#scheduleDate").attr("value",date);//默认时间
	    //$("#scheduleDateId").datebox("setValue",date);//时间用这个
		
		addWcpLbgzGrid = $('#addWcpLbgzGrid').datagrid({
			rownumbers :true,
			idField : 'secId',
			fit : true,
			singleSelect :true,
			fitColumns : true,// 自动布局列宽
			remoteSort: false,
			border : false,
			striped : true,
			nowrap : true,
			checkOnSelect : false,
			selectOnCheck : false,
			columns : [ [ {
				title : '主键ID',
				field : 'metcId'
				,hidden : true
				//,checkbox : true
			},{
				title : '主键ID',
				field : 'secId'
				,hidden : true
			}, {
				field : 'secCode',
				title : '保养规则编号',
				width : 120,
				sortable : true
			} , {
				field : 'secName',
				title : '保养规则名称',
				width : 120,
				sortable : true
			} , {
				field : 'type',
				title : '保养类型',
				width : 160,
				sortable : true,
				formatter : function(value, row, index) {
					var thisStr = value;
					if(value=='lb'){
						thisStr="轮保";
					}
					return thisStr;
				}
			}] ],
			toolbar : '#matPickToolbar',
			onLoadSuccess : function() {
				$(this).datagrid('tooltip');
			},
			onClickRow:function(rowIndex,rowData){
				queryMdTypeByCategory(rowData.secId);
			}
		});
	
		addLbgzGrid2 = $('#addLbgzGrid2').datagrid({
			//remoteSort: false,
			rownumbers :true,
			fit : true,
			fitColumns : true,//fitColumns= true就会自动适应宽度（无滚动条）
			border : false,
			pagination : true,
			idField : 'id',
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
				title : '保养设备ID',
				field : 'id'
				,hidden : true
				//,checkbox : true
			}, {
				field : 'code',
				title : '保养设备编号',
				width : 120,
				sortable : true
			} , {
				field : 'name',
				title : '保养设备名称',
				width : 120,
				sortable : true
			} , {
				field : 'lxType',
				title : '保养角色',
				width : 160,
				sortable : true,
				formatter : function(value, row, index) {
					var thisStr = value;
					if(value=='1'){
						thisStr="保养工项目";
					}else if(value=='0'){
						thisStr="操作工项目";
					}
					return thisStr;
				}
			} ] ],
			//toolbar : '#matPickToolbar',
			onLoadSuccess : function() {
				$(this).datagrid('tooltip');
			}
		});
	});
   function queryMdTypeByCategory(id){
	   var bean ={categoryId :id};//设备型号ID
	   addLbgzGrid2.datagrid({
			url : "${pageContext.request.contextPath}/pms/sys/eqptype/queryMdType.do",
			queryParams :bean
	   });
   }


	
	$('#equipmentName').searchbox({  
	    searcher:function(value,name){  
	    var dialog = parent.$.modalDialog({
				title : '选择设备',
				width : 600,
				height : 420,
				href : '${pageContext.request.contextPath}/pms/equ/wcplan/eqpPicker.jsp',
				 buttons : [ {
					text : '选择',
					iconCls:'icon-standard-disk',
					handler : function() {
						var row = dialog.find("#eqpPickGrid").datagrid('getSelected');
						if(row){
							 $("#equipmentLxId").attr("value",row.mdEqpCategoryId);//设备类型
							 $("#equipmentLxName").attr("value",row.mdEqpCategoryName);
							 $("#planName").attr("value",row.equipmentName+"轮保");
							 $("#equipmentId").attr("value",row.id);//设备名称
							 $("#equipmentName").searchbox("setValue",row.equipmentName);
							 $("#equipmentXhId").attr("value",row.eqpTypeId);//设备型号
							 $("#equipmentXhName").attr("value",row.eqpTypeName);
							 //根据 设备型号ID 查询 对应的规则(设备项 的 大类)
							 //alert(row.eqpTypeId);
							var bean ={queryEqpTypeId : row.eqpTypeId,queryType:'lb'};//设备型号ID
				     			addWcpLbgzGrid.datagrid({
				     				url : "${pageContext.request.contextPath}/pms/equ/sbglPlan/queryEqpTypeChild.do",
				     				queryParams :bean
				     			});
							 dialog.dialog('destroy'); 
						}else{
							$.messager.show('提示', '请选择一条设备信息', 'info');
						} 
						
						
					}
				} ] 
			});
	    }  
	}); 

	
</script>
<div class="easyui-layout" style="width:100%;height:497px;">

	<div id="matPickToolbar" style="display: none;">
		<div class="topTool">
			<form id="form" method="post">
				<fieldset>
					<table class="table" style="width: 100%;">
					<tr>
						<th style="text-align:left" >计划编号</th>
						<td>  	
							<input name="planCode" style="width:140px" 
							onkeyup="value=value.replace(/[^\a-zA-Z0-9\_-]/g,'')"
							class="easyui-validatebox"
							data-options="required:true" maxlength="50"/>  
						</td>
						<th style="text-align:left" >计划名称</th>
						<td>
						<input name="planName" id="planName" style="width:140px" class="easyui-validatebox" data-options="required:true" maxlength="50"/>  
						</td>
						<th style="text-align:left" >保养类别</th>
						<td>
							<select style="width:140px" name="maintenanceType" class="easyui-combobox fselect" readonly="readonly" data-options="panelHeight:'auto',required:true">
								<option value="lb">轮保</option>
							</select>
						</td>
					</tr>
					<tr>
						<th style="text-align:left" >设备名称</th>
						<td>	
							<input id="equipmentName" style="width:140px" class="easyui-searchbox"  data-options="prompt:'请选择卷烟机或成型机',required:true"/>  
							<input id="equipmentId" name="equipmentId" type="hidden"/>  
						</td>
						<th style="text-align:left" >设备类型</th>
						<td>
							<input id="equipmentLxId" name="equipmentLxId" type="hidden"/>  	
							<input id="equipmentLxName" name="equipmentLxName" style="width:140px" readonly="readonly"/>  
						</td>
						<th style="text-align:left" >设备型号</th>
						<td>
							<input id="equipmentXhId" name="equipmentXhId" type="hidden"/>  
							<input id="equipmentXhName" name="equipmentXhName" style="width:140px" readonly="readonly" />  
						</td>
					</tr>
					<tr>
						<th style="text-align:left" >保养时长</th>
						<td>
							<input name="equipmentMinute" class="easyui-numberspinner"
	     					required="required" 
	     					value="600"
	     					data-options="min:1,max:999,width:120"/>分钟
						</td>
						<th style="text-align:left" >保养日期</th>
						<td>
							<input id="scheduleDate" name="scheduleDate" type="text" class="easyui-my97"  style="width:140px" data-options="required:true"/>
						</td>
						<!-- <th style="text-align:left" >牌号</th>
						<td>
							<select id="matProd1" name="matId" style="width:140px"
							data-options="panelHeight:'auto',editable:false,required:true"/>
						</td> -->
					</tr>
					<tr>
						<th style="text-align:left" >班次</th>
						<td >
							<input id="shiftId" name="mdShiftId" type="text"
							data-options="required:true" readonly="readonly"
							style="width: 140px" value="1"/>
						</td>
						<th style="text-align:left" >备注
						<td colspan="4">
							<textarea style="width:432px;height:30px;resize:none" 
							name="maintenanceContent"  maxlength="1000" ></textarea>
						</td>
					</tr>
				</table>
				</fieldset>
			</form>	
		</div>		
	</div>
	
	<div style="height:300px;" split="true" iconCls="icon-reload" data-options="region:'north',border:false">
		<table id="addWcpLbgzGrid"></table>
	</div>
	<!-- style="height:100px;" -->
	<div  split="true" iconCls="icon-reload" data-options="region:'center',border:false">
		<table id="addLbgzGrid2"></table>
	</div>

</div>
