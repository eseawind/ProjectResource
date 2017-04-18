<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 用户新增 -->
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>用户基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>姓名</th>
						<td>
						<input type="text" name="name" class="easyui-validatebox " data-options="required:true"/></td>
						<th>性别</th>
						<td>
							<select name="gender" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1">男</option>
								<option value="0">女</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>工号</th>
						<td><input type="text" name="eno" class="easyui-validatebox " data-options="required:true"/></td>
						<th>帐户</th>
						<td><input type="text" name="loginName" class="easyui-validatebox " data-options="required:true"/></td>
					</tr>				
					<tr>
						<th>手机</th>
						<td><input type="text" name="phone" class="easyui-validatebox "/></td>
						<th>固定电话</th>
						<td><input type="text" name="tel" class="easyui-validatebox "  data-options="prompt: '区号-电话号码-分机'"/></td>
					</tr>
					<tr>
						<th>传真</th>
						<td><input type="text" name="fax" class="easyui-validatebox "/></td>
						<th>邮箱</th>
						<td><input type="text" name="email" class="easyui-validatebox "/></td>
					</tr>
					<tr>
						<th>安全级别</th>
						<td colspan="3">
							<input name="securityLevel" class="easyui-numberspinner"
        					required="required" 
        					value="10"
        					data-options="min:1,max:10,width:158"/>
						</td>
					</tr>				
				</table>
			</fieldset>
		</form>
	</div>
</div>