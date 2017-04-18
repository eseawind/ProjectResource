<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 用户编辑  -->
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>用户基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>姓名</th>
						<td><input type="hidden" name="id" value="${checkedUser.id}"/><input value="${checkedUser.name}" type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
						<th>性别</th>
						<td>
							<select name="gender" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${checkedUser.gender=='1'}">selected="selected"</c:if>>男</option>
								<option value="0" <c:if test="${checkedUser.gender=='0'}">selected="selected"</c:if> >女</option>
							</select>
						</td>						
					</tr>
					<tr>
						<th>工号</th>
						<td><input value="${checkedUser.eno}" type="text" name="eno" class="easyui-validatebox" data-options="required:true"/></td>
						<th>帐户</th>
						<td><input value="${checkedUser.loginName}" type="text" name="loginName" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>				
					<tr>
						<th>手机</th>
						<td><input value="${checkedUser.phone}" type="text" name="phone" class="easyui-validatebox span2"/></td>
						<th>固定电话</th>
						<td><input value="${checkedUser.tel}" type="text" name="tel" class="easyui-validatebox span2" data-options="prompt: '区号-电话号码-分机'"/></td>
					</tr>
					<tr>
						<th>传真</th>
						<td><input value="${checkedUser.fax}" type="text" name="fax" class="easyui-validatebox span2"/></td>
						<th>邮箱</th>
						<td><input value="${checkedUser.email}" type="text" name="email" class="easyui-validatebox span2" /></td>
					</tr>
					<th>安全级别</th>
						<td colspan="3">
							<input name="securityLevel" class="easyui-numberspinner"
        					required="required" 
        					value="${checkedUser.securityLevel}"
        					data-options="min:1,max:10,width:158"/>
					</td>				
				</table>
			</fieldset>
		</form>
	</div>
</div>