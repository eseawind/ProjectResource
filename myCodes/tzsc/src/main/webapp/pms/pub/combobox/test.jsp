<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="../../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function(){
		$.loadComboboxData($("#shift"),"shift");
		$.loadComboboxData($("#team"),"team");
		$.loadComboboxData($("#workshop"),"workshop");
		$.loadComboboxData($("#machinetypebeans"),"machinetypebeans");
	});
</script>
</head>
<body>
<select id="shift" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false">
</select>
<select id="team" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false">
</select>
<select id="workshop" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false">
</select>
<select id="machinetypebeans" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false">
</select>
</body>
</html>