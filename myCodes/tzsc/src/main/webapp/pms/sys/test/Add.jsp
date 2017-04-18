<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>添加用户信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>编号：</th>
						<td colspan="3" >
							<input type="text" name="id" class="easyui-numberbox" data-options="required:true,prompt:'请输入数字'">
						</td>
					</tr>
					<tr>
						<th>姓名：</th>
						<td colspan="3">
							
						<input type="text" name="name" >
							
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>