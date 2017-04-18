<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 设备检修解决措施 -->

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>设备检修项目基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>设备</th>
						<td>
							<input id="eqp_name" name="eqp_name" class="text" readonly="readonly" data-options="prompt: '请选择卷烟机或成型机',required:true" value="${bean.eqp_name}"/>  
							<input id="eqp_id" name="eqp_id" style="display:none;" value="${bean.eqp_id}"/>  
							<input id="id" name="id" style="display:none;" value="${bean.id}"/>  
						</td>
						<th>责任人</th>
						<td>
							<input id="blame_usr_name" name="blame_usr_name" readonly="readonly" class="text"  data-options="prompt: '请选择卷烟机或成型机',required:true" value="${bean.blame_usr_name}"/>  
							<input id="blame_usr_id" name="blame_usr_id"  style="display:none;" value="${bean.blame_usr_id}"/>  
						</td>
					</tr>
					<tr>
						<th>检修日期</th>
						<td>
							<input name="date_plans" type="text" class="easyui-my97" readonly="readonly" datefmt="yyyy-MM-dd" style="width:140px" data-options="required:true,prompt:'请选择结束时间'" value="${bean.date_plans}"/>
						</td>
						<th>部位</th>
						<td>
							<input type="text" name="place" id="place" class="text" readonly="readonly" style="width:169px;" value="${bean.place }"/>
						</td>
					</tr>
					<tr>
						<th>项目内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" readonly="readonly" name="contents">${bean.contents }</textarea>
						</td>
					</tr>
					<tr>	
						<th>解决措施</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" name="solution">${bean.solution }</textarea>
						</td>
					</tr>
					<tr>	
						<th>检修后质量注意事项</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" readonly="readonly" name="note">${bean.note }</textarea>
						</td>
					</tr>
					<tr style="display:none">	
						<th>复查情况</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none;display:none" name="review">${bean.review }</textarea>
						</td>
					</tr>
					<tr style="display:none">	
						<th>后期跟进</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none;display:none" name="followup">${bean.followup }</textarea>
						</td>
					</tr>
					<tr style="display:none">
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none;display:none" name="remark">${bean.remark }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
