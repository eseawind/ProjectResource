<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 批量增加 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#eqp_type_id"),"EQPCATEGORY",false);
		
		$("#paul_category,#paul_category2,#paul_category3").combobox({  
		    url:"${pageContext.request.contextPath}/pms/sys/eqpcategory/queryMdEqpCategory.do",
		    valueField:'id',  
		    textField:'name'  
		}); 
		var today = new Date();
		var month=today.getMonth()+1;
		if(month<10){month=("0"+month);}
	    var date=today.getFullYear()+"-"+month; 
		//$("#date").datebox("setValue",date);
		//$("#date").attr("setValue",new Date());
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>批量添加日保养</legend>
				<table class="table" style="width:97%;">
					<tr>
					<th>开始时间</th>
						<td>
							<input name="date" id="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true,width:100,prompt:'选择开始时间'"/>
						</td>
					<th>结束时间</th>
						<td>
							<input name="date2" id="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true,width:100,prompt:'选择截止时间'"/>
						</td>
					</tr>
					<tr>
						<th>设备类型</th>
						<td>
							<select id="eqp_type_id" name="eqp_type_id" data-options="panelHeight:'auto',width:175,editable:false,required:true"></select>
						</td>
						<th>早班</th>
						<td>
							<input name="zao" id="zao" precision="0"  min="0" max="60" class="easyui-numberbox" data-options="required:true,prompt:'请输入晚班保养所用时间'" value="15"/>
						</td>
					</tr>
					<tr>
						<th>中班</th>
						<td>
							<input name="zhong" id="zhong" precision="0"  min="0" max="60" class="easyui-numberbox" data-options="required:true,prompt:'请输入晚班保养所用时间'" value="15"/>
						</td>
						<th>晚班</th>
						<td>
							<input name="wan" id="wan" precision="0"  min="0" max="60" class="easyui-numberbox" data-options="required:true,prompt:'请输入晚班保养所用时间'" value="15"/>
						</td>
					</tr>
					
					<tr>
						<th>日保养规则</th>
						<td colspan="3">
							<select id="paul_category" name="paul_category" class="easyui-combobox" data-options="required:true,panelHeight:'150',width:200,editable:false,prompt:'请选择日保养规则'"></select>
						</td>
					</tr>
					<tr>
						<td>
							<select panelHeight="120" class="easyui-combobox" name="zaoTD"">  
								    <option value="2">周一早班</option>  
								    <option value="3">周二早班</option>  
								    <option value="4">周三早班</option>  
								    <option value="5">周四早班</option>  
								    <option value="6">周五早班</option>
								    <option value="7">周六早班</option> 
								    <option value="1">周日早班</option>  
							</select> 
						</td>
						<td>
							<input name="zaoT" id="zaoT" precision="0"  min="0" max="640" class="easyui-numberbox" data-options="required:true,prompt:'请输入晚班保养所用时间'" value="400"/>
						</td>
						<td>
							<select panelHeight="120" class="easyui-combobox" name="zhongTD""> 
							        <option value="5">周四中班</option>   
								    <option value="2">周一中班</option>  
								    <option value="3">周二中班</option>  
								    <option value="4">周三中班</option>  
								    <option value="6">周五中班</option> 
								    <option value="7">周六中班</option>
								    <option value="1">周日中班</option>  
							</select> 
						</td>
						<td>
							<input name="zhongT" id="zhongT" precision="0"  min="0" max="640" class="easyui-numberbox" data-options="required:true,prompt:'请输入晚班保养所用时间'" value="60"/>
						</td>
					</tr>
					<tr>
						<th>周保养规则</th>
						<td>
							<select id="paul_category2" name="paul_category2" class="easyui-combobox" data-options="panelHeight:'150',width:175,editable:false,prompt:'请选择周保养规则'"></select>
						</td>
						<th>精细保养规则</th>
						<td>
							<select id="paul_category3" name="paul_category3" class="easyui-combobox" data-options="panelHeight:'150',width:175,editable:false,prompt:'请选择精细保养规则'"></select>
						</td>
						
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>