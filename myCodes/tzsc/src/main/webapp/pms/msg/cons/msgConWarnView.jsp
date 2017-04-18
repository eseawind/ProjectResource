<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${pageContext.request.contextPath}/css/formtab.css" rel="stylesheet" type="text/css" />
<fieldset style="border:2px solid #699AB8;width:97%;padding:7px 5px 0px 5px;height:428px;">
<form id="form" method="post">
	<fieldset style="border:2px dashed #699AB8;width:95%;height:395px;">
			<table class="table" style="width: 100%;">
				<tr>
					<th>机台</th>
					<td >
						${msgConWarn.equipName}
					</td>
					<th>车间</th>
					<td >
						${msgConWarn.workShopName } 
					</td>
				</tr>
				<tr>
					<th>工单</th>
					<td >
						${msgConWarn.workCode}
					</td>
					<th>科目</th>
					<td >
						${msgConWarn.item }
					</td>
				</tr>
				<tr>
					<th>单耗标准值</th>
					<td >
						${msgConWarn.std }
					</td>
					<th>单耗实际值</th>
					<td >
						${msgConWarn.val }
					</td>
				</tr>
				<tr>
					<th>告警内容</th>
					<td colspan="3" style="height:280px;vertical-align: top;">
						${msgConWarn.content }
					</td>
				</tr>
			</table>
	</fieldset>
	</form>
	</fieldset>
