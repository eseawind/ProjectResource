<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 增加轮保计划 -->
<script type="text/javascript">
$(function(){
		 $.loadComboboxData($("#mdEqpCategory"),"EQPCATEGORY",false);
		 $.loadComboboxData($("#shift"),"SHIFT",true);
		 $.loadComboboxData($("#workshop"),"WORKSHOP",false);
		 $.loadComboboxData($("#team"),"TEAM",true); 
		 $("#dicID").combobox({  
		    url:"${pageContext.request.contextPath}/pms/sys/eqpcategory/queryMdEqpCategory.do",
		    valueField:'id',  
		    textField:'name'  
		});  
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<table class="table" style="width:97%;">
					<tr>
						<th>开始时间</th>
						<td>
							<input name="date_plan1" type="text" class="easyui-my97"  style="width:130px" data-options="panelHeight:'auto',required:true,prompt:'请选择开始时间'"/>
						</td>
						<th>结束时间</th>
						<td>
							<input name="date_plan2" type="text" class="easyui-my97" style="width:130px" data-options="panelHeight:'auto',required:true,prompt:'请选择结束时间'"/>
						</td>
					</tr>
					<tr>
						<th>设备类型</th>
						<td>
							<select id="mdEqpCategory" name="mdEqpCategory" class="easyui-combobox" data-options="panelHeight:'auto',width:175,editable:false,required:true,prompt:'请选择设备类型'"/>
						</td>
						<th>点检规则</th>
						<td>
							<select id="dicID" name="dicID" class="easyui-combobox" data-options="required:true,panelHeight:'150',width:200,editable:false,prompt:'请选择点检规则'"></select>
						</td>
					</tr>
					<tr>
						<!-- <th>班组</th>
						<td>
							<select id="team" name="team" style="width:170px;" data-options="panelHeight:'auto',width:175,editable:false,prompt:'可不选，默认日历班组'"/>
						</td> -->
						<th>班次</th>
						<td>
							<select id="shift" name="shift" style="width:170px;" data-options="panelHeight:'auto',width:175,editable:false,prompt:'可不选，默认日历班次'"/>
						</td>
						<th>车间</th>
						<td>
							<select id="workshop" name="workshop" style="width:170px;" data-options="panelHeight:'auto',width:175,editable:false,required:true,prompt:'请选择车间'"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>