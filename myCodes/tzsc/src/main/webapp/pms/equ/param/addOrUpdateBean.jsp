<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px;">
		<form id="form" method="post">
			<fieldset>
				<legend>设备模块参数维护</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>编码</th>
						<td>
							<input id="${id}d" name="${id}d" type="hidden" value="${bean.id}"/>
							<input type="text" name="code" id="code" class="easyui-validatebox" data-options="required:true" value="${bean.code}"/>
						</td>
						<th>名称</th>
						<td>
							<input type="text" name="name" id="name" class="easyui-validatebox" data-options="required:true" value="${bean.name}"/>
						</td>
					</tr>
					<tr>
						<th>系数</th>
						<td colspan="3">
							<input type="text" style="width:300px;" name="modulu" id="modulu" precision="4"  min="-99999.99" max="99999.99" class="easyui-numberbox" data-options="required:true,prompt:'请输入系数'" value="${bean.modulu}"/>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:300px;height:40px;resize:none" name="remark">${bean.remark}</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="pickPidDialog" class="easyui-dialog" title="选择父级资源" style="width:250px;height:300px;"  
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">  
	<table id="pickPidTreeGrid"></table>
	</div>
</div>