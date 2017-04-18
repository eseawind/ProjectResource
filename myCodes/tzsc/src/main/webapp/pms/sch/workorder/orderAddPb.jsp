<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		//$.loadComboboxData($("#shift1"),"SHIFT",false);
		$.loadComboboxData($("#team1"),"TEAM",false);
		$.loadComboboxData($("#team2"),"TEAM",false);
		$.loadComboboxData($("#team3"),"TEAM",false);

		var team1Data="";
		var team2Data="";
		var team3Data="";
		$("#team1").combobox({
		    onLoadSuccess: function(data){
				if(data!=null&&data.length>0){
					team1Data = data;
					$("#team1").combobox("setValue",data[0].id);
					$("#teamName").val(data[0].name);
				}
		    },onChange: function (n, o) {
			    //var intVal = parseInt(n);
			    //alert(data[intVal].name);
			    if(team1Data!=null&&team1Data.length>0){
					$("#teamName").val(team1Data[(n-1)].name);
			    }
            }
		});
		//teamTwoName
		$("#team2").combobox({
		    onLoadSuccess: function(data){
				if(data!=null&&data.length>0){
					team2Data = data;
					$("#team2").combobox("setValue",data[1].id);
					$("#teamTwoName").val(data[1].name);
				}
		    },onChange: function (n, o) {
			    if(team2Data!=null&&team2Data.length>0){
					$("#teamTwoName").val(team2Data[(n-1)].name);
			    }
            }
		});
		//teamThreeName
		$("#team3").combobox({
		    onLoadSuccess: function(data){
				if(data!=null&&data.length>0){
					team3Data = data;
					if(data.length>1){
						$("#team3").combobox("setValue",data[2].id);
						$("#teamThreeName").val(data[2].name);
					}
				}
		    },onChange: function (n, o) {
			    if(team3Data!=null&&team3Data.length>0){
					$("#teamThreeName").val(team3Data[(n-1)].name);
			    }
            }
		});
		
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>请按周排班</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>工单类型</th>
						<td>
							<select name="type"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
							    <option value="1">卷包封装车间</option>					
								<option value="2">成型机车间</option>						
							</select>
						</td>
						<th></th>
						<td></td>
					</tr>
					
					<tr>
						<th>班次1</th>
						<td>
							<select name="shiftId"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
							    <option value="1">早班</option>						
							</select>
							<input type="hidden" name="shiftName" value="早班">
						</td>
						<th>班组1</th>
						<td>
							<input  id="team1" name="teamId" style="width:130px"/>
							<input  type="hidden" name="teamName" id="teamName">
						</td>
					</tr>
					<tr>
						<th>班次2</th>
						<td>
							<select name="shiftTwoId"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
							    <option value="2">中班</option>
							    <input type="hidden" name="shiftTwoName" value="中班">				
							</select>
						</td>
						<th>班组2</th>
						<td>
							<input  id="team2" name="teamTwoId" style="width:130px"/>
							<input  type="hidden" name="teamTwoName" id="teamTwoName">
						</td>
					</tr>
					<tr>
						<th>班次3</th>
						<td>
							<select name="shiftThreeId"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
							    <option value="3">晚班</option>						
							</select>
							<input type="hidden" name="shiftThreeName" value="晚班">
						</td>
						<th>班组3</th>
						<td>
							<input  id="team3" name="teamThreeId" style="width:130px"/>
							<input  type="hidden" name="teamThreeName" id="teamThreeName">
						</td>
					</tr>
					
					
					
					<tr>
						<th>排班开始时间</th>
						<td>
							<input name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:130px"/>
						</td>
						<th>排班结束时间</th>
						<td>
							<input name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:130px"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>