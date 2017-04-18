<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 储烟量标准 -->
<script type="text/javascript">
	$(function() {
		$('#matName').searchbox({  
		    searcher:function(value,name){  
		    var dialog = parent.$.modalDialog({
					title : '选择辅料',
					width : 600,
					height :420,
					href : '${pageContext.request.contextPath}/pms/cos/matassess/constd.jsp?prods='+'${parentBean.mdMatId}',
					buttons : [ {
						text : '选择',
						iconCls:'icon-standard-disk',
						handler : function() {
							var row = dialog.find("#constdsGrid").datagrid('getSelected');
							if(row){
								 $("#matId").attr("value",row.mat);
								 $("#stdID").attr("value",row.id);
								 $("#matName").searchbox("setValue",row.matName);
								 $("#val").val(row.val);
								 $("#uval").attr("value",row.uval);
								 $("#lval").attr("value",row.lval);
								 
								 dialog.dialog('destroy');
							}else{
								$.messager.show('提示', '请选择一条物料信息', 'info');
							} 
							
							
						}
					} ]
				});
		    }  
		}); 
		$("#form input[name='c']").attr("readonly","readonly");
		$("#form input[name='partDes']").attr("readonly","readonly");
		$("#form input[name='val']").attr("readonly","readonly");
		$("#form input[name='uval']").attr("readonly","readonly");
		$("#form input[name='lval']").attr("readonly","readonly"); 
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>储烟量标准维护</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>机型</th>
						<td>
							<input type="text" name="c" style="width:150px;" class="easyui-validatebox"  value="${parentBean.eqpTypeName}"/>
							<input id="${id}d" name="${id}d" type="hidden" value="${bean.id}"/>
							<input id="cid" name="cid" type="hidden" value="${parentBean.id}"/>
						</td>
						<th>牌号</th>
						<td>
							<input type="text" name="partDes" style="width:150px;height:200px;" class="easyui-validatebox" value="${parentBean.mdMatName}"/>
						</td>
					</tr>
					<tr>
						<th>辅料名称</th>
						<td>
							<input id="matName"  name="matName" class="easyui-searchbox" style="width:200px;" data-options="prompt:'请选择辅料',required:true" value="${bean.matName}"/>  
							<input id="stdID" name="stdID" type="hidden" value="${bean.stdID}"/>
							<input id="matId" name="matId" type="hidden" value="${bean.matId}"/>
						</td>
						<th>奖罚单价</th>
						<td>
							<input type="text" name="unitprice" class="easyui-numberbox" precision="2"  min="0.01" max="99999.99" data-options="required:true,prompt:'请输入奖罚单价'" value="${bean.unitprice}"/>
						</td>
					</tr>
					<tr>
						<th>标准值</th>
						<td>
							<input type="text" id="val" name="val" data-options="required:true,prompt:'请选择辅料'" value="${bean.val}"/>
						</td>
						<th>上下限值</th>
						<td>
							<input type="text" style="width:70px;" id="uval" name="uval"  data-options="required:true,prompt:'选择辅料'" value="${bean.uval}"/>
							<input type="text" style="width:70px;" id="lval" name="lval"  data-options="required:true,prompt:'选择辅料'" value="${bean.lval}"/>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3" >
							<input type="text" name="remark" style="width:150px;" class="easyui-validatebox"   value="${bean.remark }"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>