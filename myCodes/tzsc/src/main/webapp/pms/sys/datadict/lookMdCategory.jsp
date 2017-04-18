<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 一级图片预览 -->
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:5px">
		
		<img src="${pageContext.request.contextPath}${mdCategory.uploadUrl}"/>
		
	</div>
</div>