<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>机台通知信息-签发</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>标题</th>
						<td colspan="3">
							<input type=hidden name=id value="${msgInfo.id }"/>
							${msgInfo.title }
						</td>
					</tr>
					<tr>
						<th>发起人</th>
						<td colspan="3">
							${msgInfo.initiatorName }
						</td>
					</tr>
					<tr>
						<th>通知内容</th>
						<td colspan="3">
							${msgInfo.content }
						</td>
					</tr>
					<tr>
						<th>审批内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="approveContent"></textarea>
						</td>
					</tr>
					
				</table>
			</fieldset>
		</form>
	</div>
</div>