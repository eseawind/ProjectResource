<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 给用户分配机构 -->
<script type="text/javascript">
	var orgTree;
	$(function() {
		orgTree=$('#orgTree').tree({
			animate: true,
            lines: false,
            dataPlain: true,
            dnd: true,
            toggleOnClick: true,
            showOption: true,
            dataPlain: true,
            checkbox:true,
            valueField: 'id',  
            textField: 'name',
            parentId: 'pid',
            url:'${pageContext.request.contextPath}/pms/sysUser/getOrgsByUser.do?id=${checkedUserId}',            
		});
		
	});
	
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<ul id="orgTree"></ul>
	</div>
</div>