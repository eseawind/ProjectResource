<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>显示个人信息(基本信息,机构,角色)</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		 $('#organization').tree({
			checkbox:true,
			parentField : 'pid',
			data : $.parseJSON('${sessionInfo.organizations}'),
			toggleOnClick: true,
			animate: true,
            enableContextMenu: false
		}); 
		$('#resources').tree({
			checkbox:true,
			animate: true,
			parentField : 'pid',
			data : $.parseJSON('${sessionInfo.resources}'),
			toggleOnClick: true,
            enableContextMenu: false

		});
		console.info(JSON.stringify($.parseJSON('${sessionInfo.resources}')));
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<table style="width: 100%;">
			<tr>
				<td><fieldset>
						<legend>基本信息</legend>
						<table class="table" style="width: 100%;">
							<tr>
								<th>姓名</th>
								<td>${sessionInfo.user.name}</td>
								<th>工号</th>
								<td>${sessionInfo.user.eno}</td>
								
							</tr>
							<tr>
								<th>登录名</th>
								<td>${sessionInfo.user.loginName}</td>
								<th>当前IP</th>
								<td>${sessionInfo.ip}</td>
							</tr>
							<tr>
								<th>性别</th>
								<td>
								<c:choose>
							       <c:when test="${sessionInfo.user.gender==0}">女</c:when>
							       <c:otherwise>男</c:otherwise>
								</c:choose>
								</td>
								<th>手机</th>
								<td>${sessionInfo.user.phone}</td>
							</tr>
							<tr>
								<th>电话</th>
								<td>${sessionInfo.user.tel}</td>
								<th>邮箱</th>
								<td>${sessionInfo.user.email}</td>
							</tr>
							<tr>
								<th>创建时间</th>
								<td>${sessionInfo.user.createDatetime}</td>
								<th>最后修改时间</th>
								<td>${sessionInfo.user.modifyDatetime}</td>
							</tr>
						</table>
					</fieldset>
					</td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<legend>权限信息</legend>
						<table class="table" style="width: 100%;">
							<thead>
								<tr>
									<th>所属角色</th>
									<th>所属机构</th>
									<th>拥有权限</th>
								</tr>
							</thead>
							<tr>
								<td valign="top">
									<ul style="list-style: none;">
									<c:forEach  items="${sessionInfo.roles}" var="role">
									<c:if test="${role.checked==true}">
										<li style="margin-left:-30px"><span><c:out value="${role.name}"/></span></li>
									</c:if>	
									</c:forEach>
									</ul>									
								</td>
								<td valign="top">
									<ul id="organization">
									
									</ul>
								</td>
								<td valign="top">
									<ul id="resources">
									</ul>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>