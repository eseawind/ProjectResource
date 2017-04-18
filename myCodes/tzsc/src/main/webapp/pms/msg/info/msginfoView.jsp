<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
	<fieldset style="border:2px solid #699AB8;width:97%;padding:5px;">
		<fieldset style="border:2px dashed #699AB8;width:95%;height:225px;">
			<table class="table" style="width: 100%;">
				<caption><span style="font-size:24px;">${msgInfo.title }</span></caption>
				<tr>
					<td colspan="4" >
						<div style="min-height: 100px;height:110px;">${msgInfo.content }</div>
					</td>
				</tr>
				<tr>
					<td colspan="3"style="width:75%;"></td>
					<td style="text-align: left;padding-right:20px;">
						发起人:${msgInfo.initiatorName }
					</td>
				</tr>
				<tr>
				<td colspan="3"style="width:75%;"></td>
					<td style="text-align: left;padding-right:20px;">
						审核人:${msgInfo.approvalName }
					</td>
				</tr>
				<tr>
				<td colspan="3"style="width:75%;"></td>
					<td style="text-align: left;padding-right:20px;">
						签发人:${msgInfo.issuerName }
					</td>
				</tr>
				<tr>
					<td colspan="3"style="width:75%;"></td>
					<td style="text-align: left;padding-right:20px;">
						${msgInfo.time }
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="border:2px dashed #699AB8;width:95%;height:150px;">
			<legend>审批信息</legend>
			<table class="table" style="width: 100%;border:1px solid #A8BADA;">
				<tr>
					<th width=100 style="border:1px solid #A8BADA;">审批/签发人</th>
					<th width=300 style="border:1px solid #A8BADA;">审批/签发意见</th>
					<th width=150 style="border:1px solid #A8BADA;">审批/签发时间</th>
					<th width=100 style="border:1px solid #A8BADA;">操作</th>
				</tr>
				<c:forEach var="item" items="${msgInfos}">
					<tr>
						<td  width=100 style="border:1px solid #A8BADA;text-align: center;">
							<c:if test="${item.approvalName != ''}">${item.approvalName }</c:if>
							<c:if test="${item.issuerName != ''}">${item.issuerName }</c:if>
						</td>
						<td width=300 style="border:1px solid #A8BADA;">${item.approveContent }</td>
						<td width=150 style="border:1px solid #A8BADA;text-align: center;">${item.approveTime }</td>
						<td width=100 style="border:1px solid #A8BADA;text-align: center;">
							<c:if test="${item.flag == '1'}">【签发】驳回</c:if>
							<c:if test="${item.flag == '0'}">【审核】驳回</c:if>
							<c:if test="${item.flag == '2'}">【审核】通过</c:if>
							<c:if test="${item.flag == '3'}">【签发】通过</c:if>
						</td>
					</tr>
				</c:forEach>
				<c:if test=""></c:if>
			</table>
		</fieldset>
		</fieldset>
	</div>
</div>