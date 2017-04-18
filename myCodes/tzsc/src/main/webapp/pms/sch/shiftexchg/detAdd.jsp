<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	 $("#mat").combobox({
		valueField : 'id',  
	    textField  : 'name',
	    editable   : false,
	    panelHeight: "auto",
	    required   : true,
		data       : $.parseJSON('${boms}'),
		onChange:function(newValue, oldValue){
			if(oldValue!=newValue){
				$.post("${pageContext.request.contextPath}/pms/stat/getUnitByMatId.do",
					{
					"matId":newValue,
					"workorder":"${workorder}"
					},
					function(json){
					$("#unitName").html(json.name);
					$("#unit").attr("value",json.id);
				},"JSON");
			}
		}
	}); 
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
				<table class="table" style="width: 100%;">
					<tr>
						<th>辅料</th>
						<td>
							<input type="text" name="mat" id="mat"/>
							<input type="hidden" name="exchg" value="${exchg}"/>
						</td>
					</tr>
					<tr>
						
						<th>消耗</th>
						<td style="width:180px">
							<input type="text" name="qty" class="easyui-numberbox" data-options="required:true,min:0,precision:3" style="width:120px"/>
							<input type="hidden" name="unit" id="unit"/>
							<span id="unitName" style="font-size:12px"></span>
						</td>
					</tr>
				</table>
		</form>
	</div>
</div>