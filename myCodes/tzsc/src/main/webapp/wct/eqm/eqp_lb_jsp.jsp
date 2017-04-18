<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>轮保</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/wct/js/public.js" type="text/javascript" ></script>
<style>
	.btn{
		padding:2px 12px;
	}
	.btn-default {
		margin-right:1px;
	}
	.demo_info_box{height: 35px;margin-top: 6px;margin-bottom: 5px;}
	.demo_info_box .demo_info_title{
		width: 808px;height: 38px;background: #5A5A5A;
		float: left;margin-left: 5px;font-size: 14px;
		font-weight: bold;text-align: left;line-height: 35px;
		color:#fff;border-radius: 4px 4px 0px 0px;border-bottom: 2px solid #AAA1A1;
	}
	.single_info_xiaohao{
		height: 521px;
	}
	.single_info{/*内容 样式*/
		margin-top:0px;
		width:808px;
	}
	
	.input-eqp{
	text-align: center;
	width: 100%;
	height: 40px;
	}
	
</style>
<script type="text/javascript">
	/*查询备件*/
	var pd=1;
	var tls=0;
	//备品备件个数
	var checkValue=new Array();
	//备品备件ID
	var value = new Array();
	//总数量
	var allNum=new Array();
	var shgChangeClick =null;
	$(function(){
		$("#shg-tab-tbody").html("");
        $("#shg-tab-thead-CKA").click(function() {
        	$("#shg-tab-tbody tr").each(function(idx) {
           		if($(this).find("#shg-tab-tbody-CK").get(0)!=null
                   	&&$(this).find("#shg-tab-tbody-CK").get(0)!=undefined){
           			$(this).find("#shg-tab-tbody-CK").get(0).checked = 
               			$("#shg-tab-thead-CKA").get(0).checked;
               }
			});
        });
        shgChangeClick=function(type){
			//编辑事件
			$("#shg-tab-tbody tr").die().live("click",function(){
				var tr=$(this);
				if(tr.attr("edit")=="false"||tr.attr("edit")==undefined){
					tr.attr("edit","true");
				}else{
					$(this).find("#shg-tab-tbody-CK").get(0).checked=true;//选中当前行
					tr.attr("edit","false");
				}
				tr.parent().find("td").css("background-color","#DDDDDD");
				tr.find("td").css("background-color","#d5f4fe");//当前行变色 其它行变色
	        });
        }
       queryList();//默认后台查询
       shgChangeClick("load");//供编辑用
	});
	var z_=1;
	//刷新
	function queryList(){
		$("#shg-tab-tbody").html("");
		$("#shg-tab-thead-CKA").get(0).checked = false;//顶部checkbox为不选状态
		var planId = $("#planId").val();
		var sbLbxType = $("#sbLbxType").val();
		var role = $("#role").val();
		var wheelParts = $("#wheelParts").val();
		if(role=='czg'){//这个是 根据角色查询 数据        操作工，维修工、轮保工
			//role="0";
			$("#button_id").val("操作工 保存");
		}else if(role=='lbg'){
			//role="1";
			$("#button_id").val("机械轮保工 保存");
		}else if(role=='dqLbg'){
			$("#button_id").val("电气轮保工 保存");
		}else if(role=='all'){
			role="all";
			$("#button_id").val("操作工、机械轮保工、电气轮保工  保存");
		}else if(role=='c_jl'){
			$("#button_id").val("操作工、机械轮保工保存");
		}else if(role=='shg'){
			role="shg";
			$("#button_id").val("审核工 保存");
		}else if(role=='c_dl'){
			$("#button_id").val("操作工、电气轮保工 保存");
		}else if(role=='jl_dl'){
			$("#button_id").val("电气轮保工、机械轮保工 保存");
		}
		var params ={
				pid : planId,//计划ID
				roleType:role
				//lxType:role
			}
		$.post("${pageContext.request.contextPath}/wct/eqm/covelplan/queryWheelParts.do?",params,function(data){
				var html="";
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var isFinsh= "<a href='${pageContext.request.contextPath}/wct/eqm/updateLB.jsp?source=3&&type=3&&id=\""+revalue.id+"\"&&type=3&&planId=\""+planId+"\"&&role=\""+role+"\"'><input type='button' value='维修'  style='height:28px;width:60px;' class='btn btn-default'/></a>";
						var isWx= "<font size=2><b>否</b></font>";
						if(revalue.status=='1'){
								isFinsh= "<font color='green' size=2><b>完成</b></font>";
								isWx="<font color='green' size=2><b>是</b></font>";
								
						}
						
						html+="<tr>"
							+"	<td width='30'  algin='center'>"
							+"	&nbsp; &nbsp;<input style='width:20px;height: 20px;' checked value="+revalue.id+" type='checkbox' name='shg-tab-tbody-CK' id='shg-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td>"+revalue.sbName+"</td>"
							+"	<td style='width:200px;'>"+revalue.remarks+"</textarea></td>"
							+"	<td >"+isWx+"</td>"
							+"  <td>"+isFinsh+"</td>"
							+"</tr>";
						z_++;
					}
				}
				$("#shg-tab-tbody").html(html);
			},"JSON");
	}	
	//保存
	function save(){
		var planId = $("#planId").val();
		var role = $("#role").val();//czg,lbg,all
		var reqString = '[';
		var isNextNumber= 0;
		 $("#shg-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#shg-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键ID，判断更新或者新增
	        		 var td6 = tr.find("td:eq(6) textarea").val();//备注
	        		 if(td6==undefined||td6=='undefined'||td6==null){
	        			 td6="";
		        	}
				     reqString += '{"id":"'+td0//主键
					     + '","planId":"'+ planId//备注
					     + '","roleType":"'+ role//角色类型:0 表示 操作工 1表示 轮保工  10表示他即时轮保工又是操作工
						+ '","remarks":"'+ td6//备注
						+ '"}';
						reqString += ',';
						isNextNumber++;
						
	            }
	        });
	   	if(reqString!="["){
	   		reqString = reqString.substring(0,(reqString.length-1));
		}else{
			jAlert("请选择需要保存的记录","保存");
			return false;
		}
		reqString += ']';
        //跑到后台处理后 并查询
         $.ajax({
            type: "post",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${pageContext.request.contextPath}/wct/eqm/covelplan/editBean.do",//要访问的后台地址
            data: "eqpArray=" + reqString,//要发送的数据
            complete :function(){
                //$("#load").hide();
            },//AJAX请求完成时隐藏loading提示
            success : function(r) {
				if(r.success){
					jAlert("保存成功","提示");
					queryList();//重新查询下
				}else{
					jAlert("保存失败!","提示");
				}
			}
         });
	}
	//弹出窗口
	function openRepairWin(planId,planParamId,equipId){
		$("#sparepage").css("display","none");
		$("#eqpSpare-page").css("display","none");
		$("#planIdFromParam").attr("value",planId);
		$("#planParamId").attr("value",planParamId);
		$("#equipId").attr("value",equipId);
		$("#hid_repair_div").css("display","block");
		$("#sparepage").css("display","block");
		$("#eqpSpare-page").css("display","block");
		$("#bjType").val(null);
		$("#remarkName").val(null);
		showEqp();
	}
	//取消
	function hiddenRepairWin(){
		//清空数组数据
		checkValue=new Array();
		//备品备件ID
		value = new Array();
		//总数量
		allNum=new Array();
		$("#remark").attr("value","");//备注
		$("#hid_repair_div").css("display","none");
	}
	
	

	
	//隐藏界
	function hiddenSpare(){
		//清空数组数据
		checkValue=new Array();
		//备品备件ID
		value = new Array();
		//总数量
		allNum=new Array();
		$("#sparepage").css("display","none");
		$("#eqpSpare-page").css("display","none");
	}
	

	//提交
	function updateCovelParam(pId,pmId,z){
		var planId =$("#planIdFromParam").val(); //计划
		var paramId =$("#planParamId").val();//计划对应的子项
		var equipId =$("#equipId").val();//设备ID
		var fixType = $("#fixtype").val();//维修类型
		var remarks=$("#remark").val();//备注
		var shiftId=$("#shiftId").val();//班次
		var remarksTest = $("#remarksTest"+z).val();
		var params ={
			eqpId : equipId,
			shiftId : shiftId,
			maintaiinType : fixType,
			remark : remarks
		};
	    	//更新维修类型和备注
			$.post("${pageContext.request.contextPath}/wct/eqm/covelplan/updateCovelParam.do",{paramId:pmId,planId:pId,remarks:remarksTest,fixType:0});
			hiddenRepairWin();
			//queryList();
			//jAlert("保存成功","提示");
			params=getJsonData($('#eqpMailPlan-wct-frm'));
			
	}
	
	
	

	 
	
</script>


</head>
<body>

<!--轮保新增维修页面维修 -->

<!-- 内容 1-->
<div class="single_info_xiaohao">			
	<div class="single_info">
		<input id="planId" type="hidden" value="<%=request.getParameter("planId")%>"/>
				<input id="sbLbxType" type="hidden" value="<%=request.getParameter("sbLbxType")%>"/>
				<input id="role" type="hidden" value="<%=request.getParameter("role")%>"/>
				<input id="fromSession" type="hidden" value="<%=request.getParameter("fromSession")%>"/>
				<input id="wheelParts" type="hidden" value="<%=request.getParameter("wheelParts")%>"/>
				<input id="shiftId" type="hidden" value="<%=request.getParameter("shiftId")%>"/>
		<!-- 点击确定之后进入的页面 -->
		<div class="fl" style="border:1px solid 9A9A9A;width:811px;height:472px;overflow-y: scroll;">
			<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE: collapse; font-size: 12px; font-weight: 500;"
				width="100%"  cellspacing="0" cellpadding="0">
				<thead id="shg-tab-thead" style="background: #5a5a5a; color: #fff;">
					<tr>
						<td width="30" algin="center">
							&nbsp; &nbsp; <input style="width:20px;height: 20px;" id="shg-tab-thead-CKA" name="shg-tab-thead-CKA" type="checkbox"/></td>
						</td>
						
						
						<td  width="130">名称</td>
						<!-- <td nowrap="nowrap">完成时间</td>
						<td nowrap="nowrap">操作状态</td> -->
						<td  width="30">备注</td>
						<td  width="70">是否维修</td>
						<td  width="100">维修</td>
					</tr>
				</thead>
				<tbody id="shg-tab-tbody">
				</tbody>
			</table>
			
		</div>
	</div>
</body>
</html>