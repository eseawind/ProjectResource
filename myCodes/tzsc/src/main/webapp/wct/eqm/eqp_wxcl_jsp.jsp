<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.input_wxcl_box {
		display: block;z-index: 99;position: absolute;
		top: 10px;left: 3px;width:770px;font-size: 12px;
		border: 1px solid #858484;border-radius: 10px;
		height: 320px;
		background:#A0A0A0;padding: 30px;
	}
	
</style>

</head>
	<!-- hid_repair_wxcl_div -->     
	<div id="hid_repair_wxcl_div"  style="display:none;top:77px;left:1px;position:absolute;z-index:90;" onclick="return false;">
		<div class="input_wxcl_box">
			<form id="repair-wct-wxcl-frm">
				<div class="input-title-group">
					维修信息
					<input type="hidden" name="wctEqmFixRecId" id="wctEqmFixRecId"/>
				</div>
				<div class="input-group">
					<table>
						<tr>
							<td><span class="input-group-addon">计划名称</span></td>
							<td width="290px">
								<input readonly="readonly" name="wxclPlanName" id="wxclPlanName" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
							<td><span class="input-group-addon">维修部位</span></td>
							<td width="290px">
								<input readonly="readonly" name="wxclPlanParamName" id="wxclPlanParamName" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
						</tr>
						<tr>
							<td><span class="input-group-addon">请求人员</span></td>
							<td>
								<input readonly="readonly" name="wxclRequsrName" id="wxclRequsrName" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
							<td><span class="input-group-addon">请求时间</span></td>
							<td>
								<input readonly="readonly" name="wxclReqtim" id="wxclReqtim" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
						</tr>
						<tr>
							<td><span class="input-group-addon">响应时间</span></td>
							<td>
								<input readonly="readonly" name="wxclRestim" id="wxclRestim" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
							<td><span class="input-group-addon">响应人员</span></td>
							<td>
								<input readonly="readonly" name="wxclResusrName" id="wxclResusrName" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
						</tr>
						<tr>
							<td><span class="input-group-addon">维修类型</span></td>
							<td>
								<input readonly="readonly" name="wxclFixtype" id="wxclFixtype" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
							<td><span class="input-group-addon">维修状态</span></td>
							<td>
								<input readonly="readonly" name="wxclState" id="wxclState" style="height: 30px;border-radius:5px"  class="form-control"/>
								<input type="hidden" name="wxclStateId" id="wxclStateId"/>
							</td>
						</tr>
						<tr>
							<td><span class="input-group-addon">完成时间</span></td>
							<td>
								<input readonly="readonly" name="wxclFixtim" id="wxclFixtim" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
							<td><span class="input-group-addon">完成人员</span></td>
							<td>
								<input readonly="readonly" name="wxclFixUsrName" id="wxclFixUsrName" style="height: 30px;border-radius:5px"  class="form-control"/>
							</td>
						</tr>
						<tr>
							<td><span class="input-group-addon">指定人员</span></td>
							<td>
								<input readonly="readonly" name="wxclAppointUsrName" id="wxclAppointUsrName" style="height: 30px;border-radius:5px"  class="form-control"/>
								<input type="hidden" name="wxclAppointUsrId" id="wxclAppointUsrId"/>
							</td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</div>
				<div class="input-group">
					<span class="input-group-addon">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</span>
					<input name="wxclRemark" id="wxclRemark" maxlength="1000" style="height: 40px;width:97%;border-radius:5px" class="form-control">
					</input>
				</div>
				<div class="input-group">	
					<table>
						<tr>
							<td width="500px"></td>
							<td>
								<button  onclick="updateWxClRepair();" style="height:35px;width:80px;" class="btn btn-default">
									确认受理
								</button>	
							</td>
							<td>
								<button  onclick="finshWxClRepair();" style="height:35px;width:80px;" class="btn btn-default">
									处理完成
								</button>
							</td>
							<td>
								<button  onclick="hiddenWxClJsp();" style="height:35px;width:80px;" class="btn btn-default">
									取消
								</button>	
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</html>