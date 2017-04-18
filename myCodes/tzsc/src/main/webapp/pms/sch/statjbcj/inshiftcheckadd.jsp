<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$("#mat").combobox({
		valueField : 'id',  
	    textField  : 'name',
	    editable   : false,
	    panelHeight: "auto",
	    required   : true,
		data       : $.parseJSON('${boms}'),
		onChange:function(newValue, oldValue){
			if(oldValue!=newValue){
				$.post("${pageContext.request.contextPath}/pms/stat/getunitbyworkorder.do",{"matId":newValue,"workorder":$("#workorder").attr("value")},function(json){
					$("#unitName").html(json.name);
					console.info("单位名称为："+json.name);
					$("#uom").attr("value",json.unitcode);
					console.info("单位编号为："+json.unitcode);
					$("#materialclass").attr("value",json.matclass);
					console.info("物料类型为："+json.matclass);
					$("#materialid").attr("value",json.matcode);
					console.info("辅料编号为："+json.matcode);
					$("#type").attr("value",json.mattypegrpid);
					console.info("辅料typegid为："+json.mattypegrpid);
					$("#tid").attr("value",json.mattypegrpid);
					console.info("辅料typeid为："+json.mattypegrpid);
				},"JSON");
			}
		}
	}); 
	
	/* $(function)(){
		
		
	}); */
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<table class="table" style="width:100%;">
					<tr>
						<th>工单号</th>
						<td>${workorder.id }</td>
						<input type="hidden" id="qmcsid" name="qmcsid" vallue="${shiftcheckid }" />
						<input type="hidden" id="workorder" name="workorder" value="${workorder.id }" />
						<th>班内考核编号</th>
						<td>${shiftcheckid }</td>
					</tr>
					<tr>
						<th>日期</th>
						<td>${workorder.date }</td>
						<th>机台</th>
						<td>${workorder.equipment }</td>
					</tr>
					<tr>
						<th>班组</th>
						<td>${workorder.team }</td>
						<th>班次</th>
						<td>${workorder.shift }</td>
					</tr>
					<tr>
						<th>批次号</th>
						<td>${lotid }</td>
						<th>物料名称</th>
						<td>${workorder.mat }</td>
					</tr>
					<tr>
						<th>辅料</th>
						<td>
							<input type="text" name="mat" id="mat" />
						</td>
						<th>消耗</th>
						<td>
							<input type="text" name="qty" class="easyui-numberbox" data-options="required:true,min:0,precision:3" style="width:120px"/>
							<input type="hidden" id="uom" name="uom" />
							<span id="unitname" style="font-size:12px"></span>
							<input type="hidden" id="materialclass" name="materialclass" />
							<input type="hidden" id="materialid" name="materialid" />
							<input type="hidden" id="lotid" name="lotid" value="${lotid }" />
							<input type="hidden" id="type" name="type" />
							<input type="hidden" id="tid" name="tid" />
							<input type="hidden" id="opeTime" name="opeTime" />
							<input type="hidden" id="createTime" name="createTime" />
							<input type="hidden" id="updateTime" name="updateTime" />
							<input type="hidden" id="del" name="del" value="3" />
						</td>
					</tr>
					<tr>
						<th>描述</th>
						<td>
							<input type="text" id="des" name="des" />
						</td>
						<th></th>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>