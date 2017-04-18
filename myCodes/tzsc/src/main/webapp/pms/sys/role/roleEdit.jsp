<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 角色编辑 -->
<script type="text/javascript">
$(function(){
	$("#pickIcon").click(function () {
	    $.easyui.icons.showSelector({
	        onEnter: function (val) { 
	        	$("#iconCls").removeClass().addClass(val);	        	
	        	$("#iconClsHid").attr("value",val);
	        }
	    });
	    
	});
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>角色基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>名称</th>
						<td>
							<input name="del" type="hidden" value="0"/>
							<input name="id" type="hidden" value="${checkedRole.id}"/>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${checkedRole.name}"/>
						</td>
						<th>排序</th>
						<td>
							<!-- <input type="text"  class="easyui-validatebox" data-options="required:true,prompt: '请输入排序ID'" /> -->
							<input name="seq" class="easyui-numberspinner"
        					required="required" 
        					data-options="min:1,max:999" value="${checkedRole.seq}"/>
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<th>启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${checkedRole.enable=='1'}">selected="selected"</c:if>>启用</option>
								<option value="0" <c:if test="${checkedRole.enable=='0'}">selected="selected"</c:if> >禁用</option>
							</select>
						</td>
						<th>图标</th>
						<td>
							<input id="iconClsHid" name="iconCls" type="hidden" value="${checkedRole.iconCls}"/>
							<span class="${checkedRole.iconCls}"
							 id="iconCls" 
							 style="display: inline-block;
									width: 16px;
									height: 16px;
									line-height: 16px;
									position: absolute;
									top: 104px;
									left: 320px;
									margin-top: -8px;
									font-size: 12px;">&nbsp;</span>
							<input id="pickIcon" type="button" style="
								display: inline-block;
								line-height: 16px;
								position: absolute;
								top: 101px;
								left: 353px;
								margin-top: -8px;
								font-size: 12px;" value="选择" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="4" name="remark" cols="50">${checkedRole.remark}</textarea>
						</td>
					</tr>								
				</table>
			</fieldset>
		</form>
	</div>
</div>