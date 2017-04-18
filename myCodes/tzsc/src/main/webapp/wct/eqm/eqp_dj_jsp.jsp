<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>点检</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>


<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>
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
	table {
		table-layout: fixed;
	}
	td {
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		}
	
</style>
<script type="text/javascript">
	var shgChangeClick =null;
	$(function(){
		//var type = window.location.search;
		//alert(type);
		//checked全选、反选事件
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
				//alert(tr.attr("edit"));
				if(tr.attr("edit")=="false"||tr.attr("edit")==undefined){
					//$(this).find("#shg-tab-tbody-CK").get(0).checked=false;//选中当前行
					tr.attr("edit","true");
				}else{
					$(this).find("#shg-tab-tbody-CK").get(0).checked=true;//选中当前行
					tr.attr("edit","false");
				}
	        });
        }
       queryList();//默认后台查询
       shgChangeClick("load");//供编辑用
	});
	//刷新
	function queryList(){
		$("#shg-tab-thead-CKA").get(0).checked = true;//顶部checkbox为不选状态
		var planId = $("#planId").val();
		var sbLbxType = $("#sbLbxType").val();
		var role = $("#role").val();
		var wheelParts = $("#wheelParts").val();
		if(role=='czg'){//这个是 根据角色查询 数据
			role="0";
			$("#button_id").val("操作工 保存");
		}else if(role=='lbg'){
			role="1";
			$("#button_id").val("维修工 保存");
		}else if(role=='all'){
			role="all";
			$("#button_id").val("操作工、维修工 保存");//即时操作工 又是维修工
		}else if(role=='wxzg'){
			role="2";
			$("#button_id").val("维修主管 保存");
		}else if(role=='shg'){
			role="shg";
			$("#button_id").val("审核工 保存");
		}
		//alert(planId+":"+sbLbxType+":"+role+":"+wheelParts);
		//return ;
		var params ={
				pid : planId,//计划ID
				djType : role,
				querySysCId: '402899894db7df6f014db7f7080e0001'
			}
		$.post("${pageContext.request.contextPath}/wct/eqm/checkplan/queryParts.do",params,function(data){
				var html="";
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var isFinsh= "未完成";
						if(revalue.czgUserId!=null&&revalue.czgUserId!=''){
							isFinsh= "完成";
						}
						if(revalue.lbgUserId!=null&&revalue.lbgUserId!=''){
							isFinsh= "完成";
						}
						html+="<tr>"
							+"	<td width='40'  algin='center'>"
							+"	&nbsp; &nbsp;<input checked value="+revalue.id+" type='checkbox' name='shg-tab-tbody-CK' id='shg-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td>"+(i+1)+"</td>"
							+"	<td>"+revalue.sbCode+"</td>"
							+"	<td title="+revalue.sbName+">"+revalue.sbName+"</td>"
							+"	<td title="+revalue.djMethodName+">"+revalue.djMethodName+"</td>"
							+"	<td>"+revalue.actualStrTime+"</td>"
							+"	<td>"+isFinsh+"</td>"
							+"	<td><textarea style='width:232px;height:30px;resize:none' maxlength='1000'>"+revalue.remarks+"</textarea></td>"
							+"</tr>";
					}
				}else{}
				$("#shg-tab-tbody").html(html);
			},"JSON");
	}	
	//保存
	function save(){

		var planId = $("#planId").val();
		var role = $("#role").val();//czg,lbg,all,wxzg
		var reqString = '[';
		var isNextNumber= 0;
		 $("#shg-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#shg-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键ID，判断更新或者新增
	        		 var td7 = tr.find("td:eq(7) textarea").val();//备注
	        		 if(td7==undefined||td7=='undefined'||td7==null){
	        			 td7="";
		        	}
	        		 //td:eq(5) select
				     reqString += '{"id":"'+td0//主键
					     + '","planId":"'+ planId//备注
					     + '","roleType":"'+ role//角色类型:0 表示 操作工 1表示 维修工
						+ '","remarks":"'+ td7//备注
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
	 	//var params =reqString;//{eqpCod:equ}
		//alert(reqString);
		//return ;
        //跑到后台处理后 并查询
         $.ajax({
            type: "post",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${pageContext.request.contextPath}/wct/eqm/checkplan/editBean.do",//要访问的后台地址
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
</script>


</head>
<body>
<!-- 内容 1-->
<div class="single_info_xiaohao">			
	<div class="single_info">
		<div class="demo_info_box">
			<div class="demo_info_title">
				&nbsp;点检项&nbsp;
				<input type="button" id="button_id" style="background:#A4A4A4;"
				onClick="save();" value="完成" class="btn btn-default" />
				
				<input id="planId" type="hidden" value="<%=request.getParameter("planId")%>"/>
				<input id="sbLbxType" type="hidden" value="<%=request.getParameter("sbLbxType")%>"/>
				<input id="role" type="hidden" value="<%=request.getParameter("role")%>"/>
				<input id="fromSession" type="hidden" value="<%=request.getParameter("fromSession")%>"/>
				<input id="wheelParts" type="hidden" value="<%=request.getParameter("wheelParts")%>"/>
			</div>
		</div>
		<div class="fl" style="border:1px solid 9A9A9A;width:806px;height:472px;margin-left:5px;overflow-y: scroll;">
			<table border="1" borderColor="#9a9a9a"
				style="BORDER-COLLAPSE: collapse; font-size: 12px; font-weight: 500;"
				width="100%"  cellspacing="0" cellpadding="0">
				<thead id="shg-tab-thead"
					style="background: #5a5a5a; color: #fff;">
					<tr>
						<td width="40" algin="center">
							&nbsp; &nbsp; <input id="shg-tab-thead-CKA" name="shg-tab-thead-CKA" type="checkbox"/></td>
						</td>
						<td nowrap="nowrap" width='40'>序号</td>
						<td nowrap="nowrap" width='40'>编号</td>
						<td nowrap="nowrap" width='200'>名称</td>
						<td nowrap="nowrap" width='100'>点检方式</td>
						<td nowrap="nowrap" width='120'>完成时间</td>
						<td nowrap="nowrap" width='60'>操作状态</td>
						<td>备注</td>
					</tr>
				</thead>
				<tbody id="shg-tab-tbody">
				</tbody>
			</table>
			
		</div>
	</div>
</body>
</html>