<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 批量给用户分配机构 -->
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
            url:'${pageContext.request.contextPath}/pms/sysOrg/getAllOrgsTree.do'            
		});
		
	});
	
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="padding:10px" class="dialog-toolbar">
		<code>批量分配组织机构，将会覆盖用户原有组织机构</code>
	</div>
	<div data-options="region:'center',border:false">
		<ul id="orgTree"></ul>
	</div>
</div>