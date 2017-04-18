<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>工单调度</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../../initlib/initMyAlert.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<style type="text/css">
*{font-family: "Microsoft YaHei","宋体",Arial;}
#pai-title{
	font-size: 20px;
	font-weight: bold;
	text-align: center;
	padding-top: 4px;
	background: #b4b4b4;
	color: #3C3C3C;
	border-radius: 0px 4px 0px 0px;
	line-height: 35px;
	height: 40px;
	border-bottom: 2px solid #838383;
}
#pai-tab{		
	width: 824px;
	height: 426px;
	margin: 0 auto;
	font-size: 14px;
	font-weight: bold;
	overflow: hidden;
	position: relative;
	border: 1px solid #858484;
	border-radius: 4px;
	margin-top: 5px;
}
.t-header{
	text-align:center;
}
#pai-tab-thead tr td{	
	height:52px;
	text-align:center;
}
#pai-tab-tbody tr td{	
	height:42px;
	text-align:center;
}
#sc-sub-box{
	float: right;
	padding-right: 15px;
	padding-top:5px;
}
.sub-button{
	border: 1px solid #9a9a9a;
	padding: 3px 2px;
	width: 80px;
	font-weight: bold;
	font-size: 12px;
	cursor: pointer;
}
.btn-default {
	color: #FFFFFF;
	background-color: #5A5A5A;
	border-color: #cccccc;
}
.btn {
	display: inline-block;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: normal;
	line-height: 1.428571429;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	cursor: pointer;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	-webkit-user-select: none;
	   -moz-user-select: none;
	    -ms-user-select: none;
	     -o-user-select: none;
	        user-select: none;
}
.scroll_box{
	height: 395px;
	position: absolute;
	left: 0px;
	padding:2px;	
}
.red{color:red;}
.bold{font-weight:bold;}
.font14{font-size:14px;}
</style>
<script type="text/javascript">
	 /* 1,下发 <br>
	 * 2,运行 -->运行时在生产实绩中保存val=0的数据，即采集程序只做update操作<br>
	 * 3,暂停 -->MES取消撤销工单时<br>
	 * 4,完成 -->工单完成,更新工单结束时间<br>
	 * 5,终止 -->错误的工单运行时，执行本操作删除生产实绩相关数据<br>
	 * 6,结束 -->工单已经反馈<br>
	 * 7,锁定 -->MES发起撤销时<br>
	 * 8,撤销 -->MES确定撤销时<br>
	 */
	function getStatus(sts){
		var status = $("<span>");
		if(sts==0){
			status.html("运行中");
			status.css({"color":"black"});
		}if(sts==1){
			status.html("下发");
			status.css({"color":"black"});
		}if(sts==2){
			status.html("运行");
			status.css({"color":"red"});
		}if(sts==3){
			status.html("暂停");
			status.css({"color":"yellow"});	
		}if(sts==4){
			status.html("完成");
			status.css({"color":"blue"});
		}if(sts==5){
			status.html("终止");
			status.css({"color":"gay"});
		}if(sts==6){
			status.html("结束");
			status.css({"color":"black"});
		}if(sts==7){
			status.html("锁定");
			status.css({"color":"black"});
		}if(sts==8){
			status.html("撤销");
			status.css({"color":"black"});
		}		
		return status;
	}
	function getOptions(id,sts){
		var options = "";
		
		if(sts==1||sts==3){//下发,暂停
			options = "<input type='button' onclick='editWorkOrderStatus("+id+",2)' value='运行' id='od-finish' class='sub-button btn btn-default'/>";
		}
		if(sts==2){//运行
			options = "<input type='button' onclick='editWorkOrderStatus("+id+",4)' value='完成' id='od-finish' class='sub-button btn btn-default'/>";
		}
		return options;
	}
	function clearParams(){
		$("#pai-tab-tbody tr").each(function(idx){
				var tr=$(this);
				tr.find("td:eq(0)").html(null);
				tr.find("td:eq(1)").html(null);
				tr.find("td:eq(2)").html(null);
				tr.find("td:eq(3)").html(null);
				tr.find("td:eq(4)").html(null);
				tr.find("td:eq(5)").html(null);
				tr.find("td:eq(6)").html(null);
				tr.find("td:eq(7)").html(null);
				/* tr.find("td:eq(8)").html(null); */
		});
	};
	
	function getNewWorkOrders(){
		$.post("${pageContext.request.contextPath}/wct/workorder/getNewWorkOrders.do",{"equipmentCode":"${loginInfo.equipmentCode}"},function(json){				
			$("#pai-tab-tbody tr").css("backgroundColor","#DBDBDB");
			clearParams();
			$("#pai-tab-tbody tr").each(function(idx){
					if(json.length>idx){
						var tr=$(this);
						var value=json[idx];				
						tr.find("td:eq(0)").html(value.code);
						tr.find("td:eq(1)").html(value.stim+"~"+value.etim);
						tr.find("td:eq(2)").html(value.shift);
						tr.find("td:eq(3)").html(value.team);
						tr.find("td:eq(4)").html(value.mat);
						tr.find("td:eq(5)").html(value.qty+value.unit);
						tr.find("td:eq(6)").attr("sts",value.sts).html(getStatus(value.sts));
						//alert(value.sts+":"+value.isAuto);
						//alert(value.shiftCode+":"+value.teamCode);  
						//return false;
						if((value.sts==1||value.sts==3)){
							tr.find("td:eq(7)").html("<input type='button' value='运行'  onclick=editWorkOrderStatus('"+value.eqp+"','"+value.shiftCode+"','"+value.teamCode+"','"+value.tyle+"','"+value.id+"',2) id='od-finish' class='sub-button btn btn-default'/>");
						}else if((value.sts==2)&&(value.isAuto=='Y')){
							tr.find("td:eq(7)").html("<input type='button' value='完成'  onclick=editWorkOrderStatus('"+value.eqp+"','"+value.shiftCode+"','"+value.teamCode+"','"+value.tyle+"','"+value.id+"',4) id='od-finish' class='sub-button btn btn-default'/>");
							/* tr.find("td:eq(8)").html("<input type='button' value='拖班'  onclick=editWorkOrderStatus('"+value.eqp+"','"+value.shiftCode+"','"+value.teamCode+"','"+value.tyle+"','"+value.id+"',0) id='od-finish' class='sub-button btn btn-default'/>"); */
						}else if(value.sts==2){
							tr.find("td:eq(7)").html("<input type='button' value='完成'  onclick=editWorkOrderStatus('"+value.eqp+"','"+value.shiftCode+"','"+value.teamCode+"','"+value.tyle+"','"+value.id+"',4) id='od-finish' class='sub-button btn btn-default'/>");
						}
						//tr.find("td:eq(7)").html(getOptions(value.id,value.sts));
					}
			});
		},"JSON");
	}
	function editWorkOrderStatus(eqp,shiftCode,teamCode,workshop,id,sts){
		var msg = "";
		if(sts==2){
			msg = "是否运行该工单?";
		}else if(sts==0){
			msg = "是否拖班?";
		}else if(sts==4){
			msg = "是否完成该工单?";
		}
		jConfirm(msg, '标题', function(r) {
			if(r){
				var eqCode = "${loginInfo.equipmentCode}";//设备ID
				$.post("${pageContext.request.contextPath}/pms/workorder/editWorkOrderStatus.do",
				{id:id,sts:sts},
				function(json){
					if (sts==4 && json.success) {
						var equipmentCode = "${loginInfo.equipmentCode}";//设备ID
						var params ={type:'end',eqpId:equipmentCode,workshop :workshop,proWorkId : id,shiftCode:shiftCode,teamCode:teamCode}
						$.post("${pageContext.request.contextPath}/wct/calendar/manualShift.do",params,function(data){
							//换班
						},"JSON"); 
					} else {
						jAlert(json.msg,"提示");
					}
					//刷新
					getNewWorkOrders();
				},"JSON");
			}
		});
		/* $.post("${pageContext.request.contextPath}/wct/workorder/queryIsRunWork.do",{"id":eqp,"sts":sts,"eqCode":eqCode},function(json){
			var isRun =0;
			if(json.success){
				isRun= json.obj;
			}
			if(isRun==2){//本机台已有运行工单，请完成该工单在运行本工单!
				jAlert(json.msg,"提示");
			}else if(isRun==3){
				jAlert(json.msg,"提示");
			}else{
				jConfirm(msg, '标题', function(r) {
					if(r){
						$.post("${pageContext.request.contextPath}/wct/workorder/editWorkOrderStatus.do",{"id":id,"sts":sts},function(json){	
							jAlert(json.msg,"提示");
							getNewWorkOrders();
							//运行工单
							if(sts==2&&json.success){
								var equipmentCode = "${loginInfo.equipmentCode}";//设备ID
								var params ={type:'begin',eqpId:equipmentCode,workshop :workshop,proWorkId : id,shiftCode:shiftCode,teamCode:teamCode}
								$.post("${pageContext.request.contextPath}/wct/calendar/manualShift.do",params,function(data){
									//换班
								},"JSON"); 
							}else if(sts==4&&json.success){//完成该工单需要换班,DAC底层采集 计数器 需要清零
								var equipmentCode = "${loginInfo.equipmentCode}";//设备ID
								var params ={type:'end',eqpId:equipmentCode,workshop :workshop,proWorkId : id,shiftCode:shiftCode,teamCode:teamCode}
								$.post("${pageContext.request.contextPath}/wct/calendar/manualShift.do",params,function(data){
									//换班
								},"JSON"); 
							}
						},"JSON");
					}
				});
			}
			
		},"JSON"); */
	}
	getNewWorkOrders();

	window.setInterval(function(){
		getNewWorkOrders();
	},(5*60*1000));/* 1分钟=1*60*1000; */

	
</script>
<div id="pai-title">工单调度</div>
<div id="pai-tab" style="height:562px;">
<div  class="scroll_box" id="scroll_box">
	<table border="1" borderColor="#9a9a9a" 
		style="BORDER-COLLAPSE:collapse;width:820px;"  cellspacing="0" cellpadding="0">
		<thead id="pai-tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header" >工单号</td>
				<td class="t-header" >时间</td>
				<td class="t-header" >班次</td>
				<td class="t-header" >班组</td>
				<td class="t-header" >牌号</td>
				<td class="t-header" >数量</td>
				<td class="t-header" >状态</td>
				<td class="t-header" >操作</td>
				<!-- <td class="t-header" >拖班</td> -->
			</tr>
		</thead>
		<tbody id="pai-tab-tbody">
			 <tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:130px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->			
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->			
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->			
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->			
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->				
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->			
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->				
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->				
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td>	 -->		
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->			
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->				
			</tr>
			<tr id="1">
				<td style="width:120px"></td>
				<td style="width:120px"></td>
				<td style="width:40px"></td>
				<td style="width:40px"></td>
				<td style="width:120px"></td>
				<td style="width:95px"></td>
				<td></td>
				<td></td>	
				<!-- <td></td> -->				
			</tr>
		</tbody>
	</table>
</div>
</div>