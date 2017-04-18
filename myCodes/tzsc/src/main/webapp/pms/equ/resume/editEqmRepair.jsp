<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		var matPickGrid;
		var editRow = undefined; //定义全局变量：当前编辑的行
		var eqmResumeId =$("#eqmResumeId").val();//主表主键ID
		$(function() {
			matPickGrid = $('#matPickGrid').datagrid({
				rownumbers :true,
				idField : 'repairId',
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
					title : '主键ID',
					field : 'repairId',
					checkbox : true
				}, {
					field : 'voucherType',
					title : '凭证类',
					width : 110,
					sortable : true
					,editor: { type: 'text', options: { required: true} }
				} , {
					field : 'voucherNumber',
					title : '凭证号',
					width : 120,
					sortable : true
					,editor: { type: 'text', options: { required: true} }
				}, {
					field : 'startTime',
					title : '大修开始时间',
					width : 80,
					sortable : true
					,editor: { type: 'datebox', options: { required: false} }
				}, {
					field : 'overTime',
					title : '大修结束时间',
					width : 100,
					sortable : true
					,editor: { type: 'datebox', options: { required: false} } //validatebox
				} , {
					field : 'remarks',
					title : '摘要',
					width : 80,
					sortable : true
					,editor: { type: 'text', options: { required: true} }
				}, {
					field : 'fixMoney',
					title : '金额',
					width : 80,
					sortable : true
					,editor: { type: 'text', options: { required: true} }
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
		});

		//首次加载 这里为什么会查询2次呢?
		var beanQuery ={eqmResumeId : eqmResumeId};//设备履历管理主键ID
		matPickGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/eqmRepair/queryList.do",
			queryParams :beanQuery
		});
		
		
		//新增
		function addRow(){
			if (editRow != undefined) {
				matPickGrid.datagrid('endEdit', editRow);
            }
			if (editRow == undefined) {
			    matPickGrid.datagrid('appendRow', {});
                var rows = matPickGrid.datagrid('getRows');
                matPickGrid.datagrid('beginEdit', rows.length - 1);
                editRow = (rows.length - 1);
                matPickGrid.datagrid('checkRow',editRow);//选中当前行
			}
		}
		//保存
		function saveRow(){
			matPickGrid.datagrid('endEdit', editRow);//当前编辑行 停止编辑
			//var rows = matPickGrid.datagrid('getChanges');//改变值的数据
			var rows = matPickGrid.datagrid('getRows');
			var checkboxs  = document.getElementsByName("repairId");//所有checkbox
			 var reqString ='['; 
			 var isChecked=false; 
			for(var i=0;i<rows.length;i++){//所有的 checkboxs 
				if(checkboxs[i].checked){
					isChecked = true;
					var jsonBean = JSON.stringify(rows[i]);
					var objBean = eval( "(" + jsonBean + ")" );//转换后的JSON对象
					var startTime = objBean.startTime;
					if(startTime==null||startTime==''){
						$.messager.show('提示','开始日期不可为空', 'info');
						return false;
					}
					reqString +=jsonBean+',';
				}
			}
			if(!isChecked){
				$.messager.show('提示','请勾选需要操作的数据', 'info');
				return false;
			}else if(reqString=='['){
				$.messager.show('提示','数据无变动,无需修改', 'info');
				return false;
			}else{
				reqString = reqString.substring(0,(reqString.length-1));
			}
			reqString += "]"; 
			//alert(reqString);
			var pams = {
				eqmResumeId : eqmResumeId,
				reqString : reqString
			};
			$.post("${pageContext.request.contextPath}/pms/eqmRepair/addOrEditBean.do",pams,function(json){
				if (json.success) {
					matPickGrid.datagrid('acceptChanges'); //提交信息
					$.messager.show('提示','操作成功', 'info');
					
					var beanQuery ={eqmResumeId : eqmResumeId};//设备履历管理主键ID
					matPickGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/eqmRepair/queryList.do",
						queryParams :beanQuery
					});
				}else{
					$.messager.show('提示','操作失败', 'error');
				}
			},"JSON");
			
		}


		//删除
		function deleteRow(){

			matPickGrid.datagrid('endEdit', editRow);//当前编辑行 停止编辑
			var deleteIds = "";
			var rows = matPickGrid.datagrid('getRows');
			var checkboxs  = document.getElementsByName("repairId");//所有checkbox
			 var isChecked=false; 
			for(var i=0;i<rows.length;i++){//所有的 checkboxs 
				if(checkboxs[i].checked){
					isChecked = true;
					var jsonBean = JSON.stringify(rows[i]);
					var objBean = eval( "(" + jsonBean + ")" );//转换后的JSON对象
					var repairId = objBean.repairId;
					if(repairId!=undefined){
						deleteIds +=repairId+",";
					}
				}
			}
			if(!isChecked){
				$.messager.show('提示','请勾选需要操作的数据', 'info');
				return false;
			}
			if(deleteIds!=""){
				deleteIds = deleteIds.substring(0,(deleteIds.length-1));
			}
			var pams = {
				id : deleteIds
			};
			$.post("${pageContext.request.contextPath}/pms/eqmRepair/deleteById.do",pams,function(json){
				if (json.success) {
					$.messager.show('提示','操作成功', 'info');
					
					var beanQuery ={eqmResumeId : eqmResumeId};//设备履历管理主键ID
					matPickGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/eqmRepair/queryList.do",
						queryParams :beanQuery
					});
				}else{
					$.messager.show('提示','操作失败', 'error');
				}
			},"JSON");
			
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
