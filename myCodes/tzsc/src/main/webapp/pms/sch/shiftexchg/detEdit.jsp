<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
				<table class="table" style="width: 100%;">
					<tr>
						<th>辅料</th>
						<td>
							${det.mat}
							<input type="hidden" name="id" value="${det.id}"/>
						</td>
					</tr>
					<tr>
						
						<th>消耗</th>
						<td style="width:180px">
							<input type="text" name="qty" class="easyui-numberbox" 
							data-options="required:true,min:0,precision:3" 
							value="${det.qty}"
							style="width:120px"/>
							<span id="unitName" style="font-size:12px">${det.unit}</span>
						</td>
					</tr>
				</table>
		</form>
	</div>
</div>