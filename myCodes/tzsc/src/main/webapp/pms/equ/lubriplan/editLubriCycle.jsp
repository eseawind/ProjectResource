<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 增加润滑计划 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#eqp_type_ids"),"EQPCATEGORY",false);
		
		//根据设备类型加载设备机型，根据设备机型加载设备润滑点
		$("#eqp_type_ids").combobox({
			onChange:function(newValue,oldValue){
				if(newValue!=null&&newValue!=""){
					//加载设备机型
					$("#eqp_typeId").combobox({  
					    url:"${pageContext.request.contextPath}/pms/md/eqptype/queryMdEqpTypeByCid.do?id="+newValue,
					    valueField:'id',  
					    textField:'name',
					    onChange:function(nvalue,ovalue){
					    	//加载设备保养规则
					    	$("#ruleId").combobox({  
							    url:"${pageContext.request.contextPath}/pms/md/eqptypeChild/getPaulbyEqpType.do?type=rh&eqpTypeId="+nvalue,
							    valueField:'secId',  
							    textField:'secName',
							    onHidePanel:function(){
							    	var typeName=$("#ruleId").combobox("getText");
							    	$("#ruleName").val(typeName);
							    }
							});
					    },
					    onHidePanel:function(){
					    	var typeName=$("#eqp_typeId").combobox("getText");
					    	$("#eqp_typeName").val(typeName);
					    }
					});
				}
		    }
		}); 
		//$("#eqp_typeId").combobox("setValue","");
		//$("#ruleId").combobox("setValue","${bean.ruleId}");
		 $("#eqp_typeId").combobox({  
			url:"${pageContext.request.contextPath}/pms/md/eqptype/queryMdEqpTypeByCid.do?id=${bean.eqp_category}",
			valueField:'id',  
			textField:'name',
		    onHidePanel:function(){
		    	var typeName=$("#eqp_typeId").combobox("getText");
		    	$("#eqp_typeName").val(typeName);
		    }
		});  
		$("#ruleId").combobox({  
			url:"${pageContext.request.contextPath}/pms/md/eqptypeChild/getPaulbyEqpType.do?type=rh&eqpTypeId=${bean.eqp_typeId}",
		    valueField:'secId',  
		    textField:'secName',
		    onHidePanel:function(){
		    	var typeName=$("#ruleId").combobox("getText");
		    	$("#ruleName").val(typeName);
		    }
		});  
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<table class="table" style="width:97%;">
					<tr>
						<th>润滑计划名称</th>
						<td colspan="3">
							<input id="id" name="id" type="text"  value="${bean.id}" style="display:none;"/>
							<input id="name" name="name" type="text" class="easyui-validatebox"  data-options="width:120,editable:true,required:true,prompt:'请输入日保养名称'" value="${bean.name}"/>
						</td>
					</tr>
					<tr>
						<th>设备类型</th>
						<td>
							<input id="eqp_type_ids" name="eqp_type_ids" class="easyui-combobox" data-options="panelHeight:'auto',width:175,editable:false,required:true,prompt:'请选择设备类型'" value="${bean.eqp_category}"/>
						</td>
						<th>设备型号</th>
						<td>
							<input id="eqp_typeId" name="eqp_typeId" class="easyui-combobox" data-options="panelHeight:'auto',width:175,editable:false,required:true,prompt:'请选择设备型号'" value="${bean.eqp_typeId}" />
							<input id="eqp_typeName" name="eqp_typeName" class="combo-value" type="hidden" value="${bean.eqp_typeName}"/>
						</td>
					</tr>
					<tr>
						<th>润滑规则</th>
						<td>
							<input id="ruleId" name="ruleId" class="easyui-combobox" data-options="width:140,editable:false,required:true,prompt:'请选择润滑规则'" value="${bean.ruleId}"/>
							<input id="ruleName" name="ruleName" type="hidden" value="${bean.ruleName}"/>
						</td>
						<th>润滑周期</th>
						<td>
							<input name="cycle" type="text" class="easyui-validatebox"  data-options="width:70,editable:true,required:true,prompt:'请输入润滑周期'" value="${bean.cycle}"/>天
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>