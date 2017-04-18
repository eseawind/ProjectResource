<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		var matPickGrid;
		var editRow = undefined; //定义全局变量：当前编辑的行
		var shiftItem=null;
		var matItem=null;
		var eqpItem=null;
		$(function() {
			//初始化班次、牌号、设备信息
			$.post("${pageContext.request.contextPath}/plugin/combobox/load.do?type=SHIFT&setAll=false",function(json){
				shiftItem=json;
				if(shiftItem!=null){
					$.post("${pageContext.request.contextPath}/plugin/combobox/load.do?type=MATPROD&setAll=false",function(json){
						matItem=json;
						if(matItem!=null){
							$.post("${pageContext.request.contextPath}/plugin/combobox/load.do?type=ALLEQPS&setAll=false",function(json){
								eqpItem=json;
								if(eqpItem!=null){
									loadGrid();
								}
							},"JSON");
						}
					},"JSON");
				}
			},"JSON");
			
		});
		function loadGrid(){
			matPickGrid = $('#matPickGrid').datagrid({
				rownumbers :true,
				idField : 'auxilId',
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
					field : 'eqmText',
					title : '设备名称',
					width : 120,
					sortable : true
					,editor: { type: 'combobox', options: { required: true ,data:eqpItem,valueField:'name',textField:'name',
					onSelect: function(rec){
						//设备Id赋值
						var ed =$('#matPickGrid').datagrid('getEditor', {index:editRow,field:'eqmId'});
						$(ed.target).val(rec.id);
						
						//动态级联
						var ed =$('#matPickGrid').datagrid('getEditor', {index:editRow,field:'ruleText'});
		        		$(ed.target).combobox({
		        			 url:'${pageContext.request.contextPath}/pms/md/eqptypeChild/getPaulbyEqp.do?eqpId='+rec.id+"&type=lb",    
		        			 valueField:'secName',    
		        			 textField:'secName'   
		        		});
			       	}
				}}
				}, {
					field : 'eqmId',
					title : '设备名称Id',
					width : 100,
					hidden: true,
					sortable : true
					,editor: { type: 'text', options: { required: true} }
				}, {
					field : 'scheduleDate',
					title : '保养日期',
					width : 100,
					sortable : true
					,editor: { type: 'datebox', options: { required: true} }
				},  {
					field : 'ruleText',
					title : '保养规则',
					width : 140,
					sortable : true,
					editor: { type: 'combobox', options: { required: true,valueField:'secName',textField:'secName',
						onSelect: function(rec){
							//Id赋值
							var ed =$('#matPickGrid').datagrid('getEditor', {index:editRow,field:'ruleId'});
							$(ed.target).val(rec.secId);
				       	}} } 
				} ,{
					field : 'ruleId',
					title : '保养规则Id',
					width : 140,
					hidden:true,
					sortable : true,
					editor: { type: 'text', options: { required: true} } 
				} , {
					field : 'equipmentMinute',
					title : '保养时长',
					width : 110,
					sortable : true
					,editor: { type: 'text', options: { required: true,value:480 } }
				} , {
					field : 'matText',
					title : '牌号',
					width : 140,
					sortable : true,
					editor: { type: 'combobox', options: { required: true ,data:matItem,valueField:'name',textField:'name',
						onSelect: function(rec){
							//Id赋值
							var ed =$('#matPickGrid').datagrid('getEditor', {index:editRow,field:'matId'});
							$(ed.target).val(rec.id);
				       	}} } 
				}, {
					field : 'matId',
					title : '牌号Id',
					width : 140,
					sortable : true,
					hidden:true
					,editor: { type: 'text', options: { required: true } }
				}, {
					field : 'shiftText',
					title : '班次',
					width : 80,
					sortable : true,
					editor: { type: 'combobox', options: { required: true ,data:shiftItem,valueField:'name',textField:'name',
						onSelect: function(rec){
							//Id赋值
							var ed =$('#matPickGrid').datagrid('getEditor', {index:editRow,field:'shiftId'});
							$(ed.target).val(rec.id);
				       	}} } 
				}, {
					field : 'shiftId',
					title : '班次Id',
					width : 80,
					sortable : true,
					hidden:true,
					editor: { type: 'text', options: { required: true } }
				}
				] ],
				onBeforeEdit:function(index,row){
			    },
			    onCancelEdit:function(index,row){
			    },
			    onAfterEdit:function(index,row){
                	editRow = undefined;		//编辑后清空
			    },
                onDblClickRow: function (rowIndex, rowData) {
                	if (editRow != undefined) {
                    	matPickGrid.datagrid("endEdit", editRow);//双击先结束编辑
                    }
                    if (editRow == undefined) {
                        matPickGrid.datagrid("beginEdit", rowIndex);//双击开启编辑行
                        editRow = rowIndex;
                    }
                },
                onClickRow:function(rowIndex,rowData){
                	if (editRow != undefined) {
                		matPickGrid.datagrid('endEdit', editRow);//点击
                		//matPickGrid.datagrid('checkRow', editRow);//选中当前行
                	}
			    },
				toolbar : '#matPickToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				}
			});
		}
		
		//新增
		function addRow(){
			if (editRow != undefined) {
				matPickGrid.datagrid('endEdit', editRow);
            }
			if (editRow == undefined) {
				//初始化时间
			    var today = new Date();
				var month=today.getMonth()+1;
				if(month<10){month=("0"+month);}
				var day=today.getDate();
				if(day<10){day=("0"+day);}
			    var date=today.getFullYear()+"-"+month+"-"+day; 
				matPickGrid.datagrid('appendRow',{
					shiftText:'早班',
					shiftId:'1',
					equipmentMinute:'480',
					scheduleDate:date
				});
				var rows = matPickGrid.datagrid('getRows');
                matPickGrid.datagrid('beginEdit', rows.length - 1);
                editRow = (rows.length - 1);
                //matPickGrid.datagrid('checkRow',editRow);
                
			}
		}
		//保存
		function saveRow(){
			matPickGrid.datagrid('endEdit', editRow);//当前编辑行 停止编辑
			//var rows = matPickGrid.datagrid('getChanges');//改变值的数据
			var rows = matPickGrid.datagrid('getRows');
			//alert(getRows.length);
			var reqString ='['; 
			for(var i=0;i<rows.length;i++){
					var jsonBean = JSON.stringify(rows[i]);
					reqString +=jsonBean+',';
			}
			if(reqString=='['){
				$.messager.show('提示','数据无变动,无需修改', 'info');
				return false;
			}else{
				reqString = reqString.substring(0,(reqString.length-1));
			}
			reqString += "]"; 
			//console.info(reqString);
			//alert(reqString);
			//return ;
			var pams = {
				reqString : reqString
			};
			 $.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/batchAddWCPlan.do",pams,function(json){
				if (json.success) {
					$.messager.show('提示','操作成功', 'info');
					this.close();
					
				}else{
					$.messager.show('提示','操作失败', 'error');
				}
			},"JSON");
			
		}


		//删除
		function deleteRow(){
			matPickGrid.datagrid("deleteRow",editRow);
			editRow = undefined;
		}
		
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div id="matPickToolbar" style="display: none;">
	<div class="topTool">
		<form id="form" method="post">
			<fieldset >
				<table style="margin:2px 0px 0px 0px">
			 		<tr>
						<td><a onclick="addRow();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a></td>
						<td><a onclick="saveRow();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">保存</a></td>
						<td><a onclick="deleteRow();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a></td>
						<td>
							<input type="hidden" id="eqmResumeId" name="eqmResumeId" value="${bean.eqmResumeId}"/>
						</td>					
					</tr>
				</table>
			</fieldset>
		</form>
	</div>		
</div>

<div data-options="region:'center',border:false">
	<table id="matPickGrid"></table>
</div>
</div>
