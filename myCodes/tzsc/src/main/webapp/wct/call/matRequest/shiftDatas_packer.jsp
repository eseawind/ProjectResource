<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<title>交接班维护-包装机</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jq 核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<jsp:include page='../../../initlib/initMyAlert.jsp' ></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" type="text/javascript"></script>
<style type="text/css">
*{font-family: "Microsoft YaHei","宋体",Arial;}
.intf{
	border:0px;
	background-color:rgb(221,221,221);
}
.yellowSty{
  font-weight: bold;
  font-size: 12px;
  color:green;
}
.gzul li{
   width: 100%;
   line-height:20px;
    
}
.titleul li{
  float: left;
  margin-right:20px;
  width:60px;
  text-align: center;
}
.titleul2 li{
  float: left;
  margin-right:20px;
  width: 60px;
  text-align: center;
  
}

.titleul2 li input{
  width: 60px;
}
#call_div{
   width: 100%;
   height: 100%px;
   /* background-color: red; */
   display: block;
   margin-left:10px;
   margin-top:23px;
}
#title{
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
#tab{		
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
	font-size:14px;
}
#tab-thead tr td{	
	height:23px;
	text-align:center;
}
#tab-tbody tr td{	
	height:28px;
	text-align:center;
	font-size:12px;
}
.t-header2{
	text-align:center;
	font-size:14px;
}
#tab-thead2 tr td{	
	height:23px;
	text-align:center;
}
#tab-tbody2 tr td{	
	height:20px;
	text-align:center;
	font-size:14px;
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
	font-size: 12px;
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
#page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
		padding-top:4px;
	}
.red{color:red;}
.bold{font-weight:bold;}
.font14{font-size:14px;}

/*new style add by yilijian*/
 /* .scroll_box div{border:1px solid red;}  */
#call_div{width:816px;height:550px;}
#btn_div{width:350px;margin:100px auto;}
#self_request{height: 60px;
width: 150px;
font-size: 27px;
font-weight: bold;}
#help_request{
margin-left:40px;
height: 60px;
width: 150px;
font-size: 27px;
font-weight: bold;}
#eqp_div{height:310px;}
#eqp_div div{width:300px;height:50px;margin:10px auto;}
#eqp_div select{width:150px;height:32px;}
</style>
<script type="text/javascript">
    //wct小登录全局角色ID
    var rolsId="${loginWctEqmInfo.roles}"; //内部登录角色ID
    var equipmentCode="${loginInfo.equipmentCode}"; //外部登录设备Code
    //获取提升机
    /* $.post("${pageContext.request.contextPath}/wct/isp/packer/getPackerData.do",{"equipmentCode":equipmentCode},function(json){
		if(json.tsQty<0){
			//条烟提升机
		    $("#qtyf").val( "0" );
		}else{
			//条烟提升机
		    $("#qtyf").val( ((json.tsQty)/250).toFixed(2) );
		}
    } */
    
	$(function(){
		//判断工单张，如果是工段长，把实际辅料权限放开
		var ind=rolsId.indexOf("8af2d44950da73bc0150da7df2510000");
		if(ind==0){
			$("#zb_lbz2").attr("disabled",false);
			$("#zb_xhz2").attr("disabled",false);
			$("#zb_xhm2").attr("disabled",false);
			$("#zb_thz2").attr("disabled",false);
			$("#zb_thm2").attr("disabled",false);
			$("#zb_kz2").attr("disabled",false);
			$("#zb_fq2").attr("disabled",false);
			$("#zb_lx12").attr("disabled",false);
			$("#zb_lx22").attr("disabled",false);
		}
		
		//初始化产量，有效作业率
		qtyAndZyl();
		
		/**
		   功能说明：所有卷烟机，包装机，成型机实际消耗辅料公共计算方法
		                                                                消耗辅料=实际辅料+虚领辅料-虚退辅料
		*/
		$(".yellowSty").keyup(function(){
		     //获取name
		     var val=this.value.trim();
		     if(val==""){
		    	 this.value="0";
		     }else if(isNaN( val )){
		         this.value="0";
		     }
		     var namef=this.name.trim();
		     //计算
		     var ret=Number($("#"+namef+"1").val()) + Number($("#"+namef+"2").val()) - Number($("#"+namef+"3").val());

		     //赋值，并保留2位小数
		     ret=ret.toFixed(2);
		     $("#"+namef+"4").val( ret );
		})
		//鼠标获得焦点，清空内容
		$(".yellowSty").focus(function(){
		     this.value="";
		})
	});

	//弹出窗口
	function openWin(){
		$("#hid_div").css("display","block");
	}
	//登录
	function initLogin(){
		var name=$("#name").val();
		var pwd=$("#pwd").val();
		var loginBean={"name":name,"passWord":pwd,"loginName":name};
		$.post("${pageContext.request.contextPath}/wct/system/loginWctEqm.do",loginBean,function(json){
			if(json.isSuccess=="true"){
				rolsId=json.roles;//登录后，赋新的角色ID
				//判断工单张，如果是工段长，把实际辅料权限放开
				var ind=rolsId.indexOf("8af2d44950da73bc0150da7df2510000");
				if(ind==0){
					$("#zb_lbz2").attr("disabled",false);
					$("#zb_xhz2").attr("disabled",false);
					$("#zb_xhm2").attr("disabled",false);
					$("#zb_thz2").attr("disabled",false);
					$("#zb_thm2").attr("disabled",false);
					$("#zb_kz2").attr("disabled",false);
					$("#zb_fq2").attr("disabled",false);
					$("#zb_lx12").attr("disabled",false);
					$("#zb_lx22").attr("disabled",false);
				}else{
					//交接班工段长登录，放开实领辅料修改框权限
					$("#zb_lbz2").attr("disabled",true);
					$("#zb_xhz2").attr("disabled",true);
					$("#zb_xhm2").attr("disabled",true);
					$("#zb_thz2").attr("disabled",true);
					$("#zb_thm2").attr("disabled",true);
					$("#zb_kz2").attr("disabled",true);
					$("#zb_fq2").attr("disabled",true);
					$("#zb_lx12").attr("disabled",true);
					$("#zb_lx22").attr("disabled",true);
				}
				
				$("#sbUserId").attr("value",json.userId);//用户ID
				$("#sbUserName").html(json.name);
				$("#sbUserRole").html(json.rolesNames); 
				$("#sbUserRoleIds").html(json.roles); 
				$("#sbLoginStatus").html("登录成功");
				$("#hid_div").css("display","none");//隐藏弹出层
				 
			} else {
				$("#sbUserName").html(json.name);
				$("#sbLoginStatus").html("登录失败");
				jAlert("账户或密码错误!","系统提示");
			}
		},"JSON");
	}
	//注销
	function innerLogin(){
		$.post("${pageContext.request.contextPath}/wct/system/logOutWctEqm.do",function(json){
			if(json.isSuccess=="false"){
				$("#sbUserId").attr("value","");//用户ID
				$("#sbUserName").html("");
				$("#sbUserRole").html("");
				$("#sbUserRoleIds").html(""); 
				rolsId="";//注销后，清空全局角色ID
				$("#sbLoginStatus").html("注销成功");
				$("#hid_div").css("display","none");//隐藏弹出层
				
				//交接班工段长登录，放开实领辅料修改框权限
				$("#zb_lbz2").attr("disabled",true);
				$("#zb_xhz2").attr("disabled",true);
				$("#zb_xhm2").attr("disabled",true);
				$("#zb_thz2").attr("disabled",true);
				$("#zb_thm2").attr("disabled",true);
				$("#zb_kz2").attr("disabled",true);
				$("#zb_fq2").attr("disabled",true);
				$("#zb_lx12").attr("disabled",true);
				$("#zb_lx22").attr("disabled",true);
			}else{
				jAlert("注销失败", '系统提示');
			}
		},"JSON");
	}
	//取消
	function hiddenFrom(){
		$("#hid_div").css("display","none");
	}
	
	//产量，设备有效作业率
	function qtyAndZyl(){
		var url="${pageContext.request.contextPath}/wct/eqm/shiftGl/matRequestPackerByQty.do";
		$.post(url,null,function(data){
			var dataJson = JSON.parse(data);
			//产量
		    $("#qtyf").val( dataJson[0].qty );
			//作业率
		    $("#zylf").val( dataJson[0].zyl+" %");
			//故障
			$(".equf").html( dataJson[0].equ );
			
			//实领
			var zj_sl= new Array();
			zj_sl=dataJson[0].zj_sl.split(",");
			for(var i=0;i<zj_sl.length;i++){
				if(i==0){
					$("#zb_lbz2").val(zj_sl[0]>0?zj_sl[0]:0);
				}else if(i==1){
					$("#zb_xhz2").val(zj_sl[1]>0?zj_sl[1]:0);
				}else if(i==2){
					$("#zb_xhm2").val(zj_sl[2]>0?zj_sl[2]:0);
				}else if(i==3){
					$("#zb_thz2").val(zj_sl[3]>0?zj_sl[3]:0);
				}else if(i==4){
					$("#zb_thm2").val(zj_sl[4]>0?zj_sl[4]:0);
				}else if(i==5){
					$("#zb_kz2").val(zj_sl[5]>0?zj_sl[5]:0);
				}else if(i==6){
					$("#zb_fq2").val(zj_sl[6]>0?zj_sl[6]:0);
				}else if(i==7){
					$("#zb_lx12").val(zj_sl[7]>0?zj_sl[7]:0);
				}else if(i==8){
					$("#zb_lx22").val(zj_sl[8]>0?zj_sl[8]:0);
					
				}
			}
			
			
			//虚领
			var zj_xl= new Array();
			zj_xl=dataJson[0].zj_xl.split(",");
			for(var i=0;i<zj_xl.length;i++){
				if(i==0){
					$("#zb_lbz1").val(zj_xl[0]>0?zj_xl[0]:0);
				}else if(i==1){
					$("#zb_xhz1").val(zj_xl[1]>0?zj_xl[1]:0);
				}else if(i==2){
					$("#zb_xhm1").val(zj_xl[2]>0?zj_xl[2]:0);
				}else if(i==3){
					$("#zb_thz1").val(zj_xl[3]>0?zj_xl[3]:0);
				}else if(i==4){
					$("#zb_thm1").val(zj_xl[4]>0?zj_xl[4]:0);
				}else if(i==5){
					$("#zb_kz1").val(zj_xl[5]>0?zj_xl[5]:0);
				}else if(i==6){
					$("#zb_fq1").val(zj_xl[6]>0?zj_xl[6]:0);
				}else if(i==7){
					$("#zb_lx11").val(zj_xl[7]>0?zj_xl[7]:0);
				}else if(i==8){
					$("#zb_lx21").val(zj_xl[8]>0?zj_xl[8]:0);
					
				}
			}
			
			//虚退
			 var zj_xt= new Array();
			zj_xt=dataJson[0].zj_xt.split(",");
			for(var i=0;i<zj_xt.length;i++){
				if(i==0){
					$("#zb_lbz3").val(zj_xt[0]>0?zj_xt[0]:0);
				}else if(i==1){
					$("#zb_xhz3").val(zj_xt[1]>0?zj_xt[1]:0);
				}else if(i==2){
					$("#zb_xhm3").val(zj_xt[2]>0?zj_xt[2]:0);
				}else if(i==3){
					$("#zb_thz3").val(zj_xt[3]>0?zj_xt[3]:0);
				}else if(i==4){
					$("#zb_thm3").val(zj_xt[4]>0?zj_xt[4]:0);
				}else if(i==5){
					$("#zb_kz3").val(zj_xt[5]>0?zj_xt[5]:0);
				}else if(i==6){
					$("#zb_fq3").val(zj_xt[6]>0?zj_xt[6]:0);
				}else if(i==7){
					$("#zb_lx13").val(zj_xt[7]>0?zj_xt[7]:0);
				}else if(i==8){
					$("#zb_lx23").val(zj_xt[8]>0?zj_xt[8]:0);
					
				}
				if(zj_xt[9]==1){
					//隐藏按钮,确定，仲裁
					$("#buts").hide();
					
					$("#zb_lbz3").attr("disabled",true);
					$("#zb_xhz3").attr("disabled",true);
					$("#zb_xhm3").attr("disabled",true);
					$("#zb_thz3").attr("disabled",true);
					$("#zb_thm3").attr("disabled",true);
					$("#zb_kz3").attr("disabled",true);
					$("#zb_fq3").attr("disabled",true);
					$("#zb_lx13").attr("disabled",true);
					$("#zb_lx23").attr("disabled",true);
				}
			}
		},"JSON");
	}

	/**
	            共能描述：保存，仲裁   
	            author:wch
	    type :1-保存，2-仲裁
	           工段长角色ID:8af2d44950da73bc0150da7df2510000
	*/
	function submitShiftDatas(type){
			if(type==1){
				//验证是否工段长，如果是工段长直接保存，如果操作工，判断 大登录=小登录
				var ind=rolsId.indexOf("8af2d44950da73bc0150da7df2510000");
				if(ind!=-1){
				   //工段长保存
				   saveShiftDatas();
				}else{
				   //操作工
				   var mainRoles="${loginInfo.roles}";
				   if(mainRoles==rolsId){
					   //操作工保存
					   saveShiftDatas();
				   }else{
					   jAlert("外登录和内登录必须是同一个用户!","警告");
					   return false;
				   }
				}
			}else if(type==2){
				
			}
	     
	}
	 //公用取值保存方法
	 <!-- 卷烟机:1~30    -->
	 <!-- 包装机:31~60   -->
	 <!-- 封箱机:61~70   -->
	 <!-- 成型机:101~130 -->
	 function saveShiftDatas(){
		 //提示end
	     jConfirm('确定提交吗？', '提示', function(r) {
	    	 if(r){
				  var zb_lbzf=$("#zb_lbz1").val()+","+$("#zb_lbz2").val()+","+$("#zb_lbz3").val();
				  var zb_xhzf=$("#zb_xhz1").val()+","+$("#zb_xhz2").val()+","+$("#zb_xhz3").val();
				  var zb_xhmf=$("#zb_xhm1").val()+","+$("#zb_xhm2").val()+","+$("#zb_xhm3").val();
				  var zb_thzf=$("#zb_thz1").val()+","+$("#zb_thz2").val()+","+$("#zb_thz3").val();
				  var zb_thmf=$("#zb_thm1").val()+","+$("#zb_thm2").val()+","+$("#zb_thm3").val();
				  var zb_kzf=$("#zb_kz1").val()+","+$("#zb_kz2").val()+","+$("#zb_kz3").val();
				  var zb_fqf=$("#zb_fq1").val()+","+$("#zb_fq2").val()+","+$("#zb_fq3").val();
				  var zb_lx1f=$("#zb_lx11").val()+","+$("#zb_lx12").val()+","+$("#zb_lx13").val();
				  var zb_lx2f=$("#zb_lx21").val()+","+$("#zb_lx22").val()+","+$("#zb_lx23").val();
				  //发送POST请求
				 $.post("${pageContext.request.contextPath}/wct/eqm/shiftGl/saveShiftPacker.do",{"zb_lbz":zb_lbzf,"zb_xhz":zb_xhzf,"zb_xhm":zb_xhmf,"zb_thz":zb_thzf,"zb_thm":zb_thmf, "zb_kz":zb_kzf,"zb_fq":zb_fqf, "zb_lx1":zb_lx1f, "zb_lx2":zb_lx2f},function(data){
					 if(data=='true'){
						 //隐藏按钮,确定，仲裁
					     $("#buts").hide();
						 jAlert("提交成功！","提示");
					 } else{
						 jAlert("提交失败！","提示");
					 }
					
				  },"JSON")
	    	 }
	     });
	}
	
</script>
<div id="title">
     ${loginInfo.equipmentName}（交接班）
</div>
<!-- 用户登录窗口 -->
 <jsp:include page="/wct/eqm/eqmLogin.jsp"></jsp:include>
	<div id="wkd-eqp-msg-title">
		<table style="font-size: 14px;">
			<tr>
				<td><b>&nbsp;登录人：</b></td><td><div id="sbUserName">
				    
				    ${loginWctEqmInfo.name}
				</div></td>
				<td width="10"></td>
				<td><b>&nbsp;登录角色：</b></td>
				<td>
					<div id="sbUserRole">
					    ${loginWctEqmInfo.rolesNames}
					</div>
					<div id="sbUserRoleIds" style="display:none;">
						${loginWctEqmInfo.roles}
					</div>
				</td>
				<td width="10"></td>
				<td><b>登录状态：</b></td><td><div id="sbLoginStatus">
				    
					<c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.dlStatus}</c:if>
					<c:if test="${empty loginWctEqmInfo}"><font style="color:red;">未登录</font></c:if>
				</div></td>
				<td width="10"></td>
				<td>
					<button  onclick="openWin();" style="height:28px;width:80px;" class="btn btn-default">
						 登录/注销
					</button>
				</td>
			</tr>
		</table>
	</div>

	<div id="tab" style=" PADDING:0px; LINE-HEIGHT: 20px; OVERFLOW: auto;height:562px;font-size: 12px;font-weight: normal;">
		<div  class="scroll_box" id="scroll_box">
			<!-- 本机呼叫，辅助呼叫 请求请求按钮div -->
			<div id="call_div">
				<div>
				   <span style="font-weight: bold;">基本数据：</span>
				   <span style="margin-left:43px;">产量（箱）：<input disabled="disabled"  class="yellowSty" type="text" id="qtyf" value="0" /></span>
				   <span style="margin-left:60px;">有效作业率：<input disabled="disabled"  class="yellowSty" type="text" id="zylf"  value="0" /></span>
				</div>
			    <hr/>
<!-- 包装机start -->
				<div class="roller">
				   <ul class="titleul">
				      <li style="font-weight: bold;">辅&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;料：</li>
				      <li>铝箔纸<br/>（kg）</li>
				      <li>小盒纸<br/>（kg）</li>
				      <li>小盒膜<br/>（kg）</li>
				      <li>条盒纸<br/>（kg）</li>
				      <li>条盒膜<br/>（kg）</li>
				      <li>卡纸<br/>（kg）</li>
				      <li>封签<br/>（万张）</li>
				      <li>拉线1<br/>（kg）</li>
				      <li>拉线2<br/>（kg）</li>
				   </ul>
				   <br/><br/>
				    <ul class="titleul2">
				      <li>虚领辅料：</li>
				      <li><input type="text" name="zb_lbz" disabled="disabled" id="zb_lbz1"  class="yellowSty"  value="0"/></li>
				      <li><input type="text" name="zb_xhz" disabled="disabled" id="zb_xhz1"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_xhm" disabled="disabled" id="zb_xhm1"  class="yellowSty"  value="0"/></li>
				      <li><input type="text" name="zb_thz" disabled="disabled" id="zb_thz1"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_thm" disabled="disabled" id="zb_thm1"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_kz"  disabled="disabled" id="zb_kz1"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_fq"  disabled="disabled" id="zb_fq1"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_lx1" disabled="disabled" id="zb_lx11"   class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_lx2" disabled="disabled" id="zb_lx21"  class="yellowSty" value="0"/></li>
				   </ul>
				    <br/><br/>
				   <ul class="titleul2">
				      <li>实领辅料：</li>
				      <li><input type="text" name="zb_lbz" disabled="disabled" id="zb_lbz2"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_xhz"  disabled="disabled" id="zb_xhz2"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_xhm" disabled="disabled" id="zb_xhm2" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_thz" disabled="disabled" id="zb_thz2"   class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_thm" disabled="disabled" id="zb_thm2"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_kz"  disabled="disabled" id="zb_kz2" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_fq"  disabled="disabled" id="zb_fq2"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_lx1" disabled="disabled" id="zb_lx12"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_lx2" disabled="disabled" id="zb_lx22" class="yellowSty" value="0"/></li>
				   </ul>
				    <br/><br/>
				   <ul class="titleul2">
				      <li>虚退辅料：</li>
				      <li><input type="text" name="zb_lbz"  id="zb_lbz3" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_xhz"  id="zb_xhz3" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_xhm"  id="zb_xhm3" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_thz"  id="zb_thz3" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_thm"  id="zb_thm3" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_kz"   id="zb_kz3" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_fq"   id="zb_fq3" class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_lx1"  id="zb_lx13"  class="yellowSty" value="0"/></li>
				      <li><input type="text" name="zb_lx2"  id="zb_lx23"  class="yellowSty" value="0"/></li>
				   </ul>
				    <br/><br/>
				   <ul class="titleul2">
				      <li>计算消耗：</li>
				     <li><input type="text"  disabled="disabled" id="zb_lbz4" class="yellowSty  intf"  value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_xhz4"  class="yellowSty  intf" value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_xhm4"  class="yellowSty  intf" value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_thz4"  class="yellowSty  intf" value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_thm4"  class="yellowSty  intf" value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_kz4"  class="yellowSty  intf" value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_fq4"  class="yellowSty  intf" value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_lx14"  class="yellowSty  intf" value=""/></li>
				      <li><input type="text" disabled="disabled" id="zb_lx24"  class="yellowSty  intf" value=""/></li>
				   </ul>
				</div>
<!-- 包装机end -->				
				

				<br/><br/> <hr/>
				<div id="buts" style="margin-top:20px;margin-bottom:20px; text-align: center;font-size: 14px;">
	                <input type="button" onclick="submitShiftDatas('1')"  value="保存" style="height:28px;width:100px;" class="btn btn-default"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- <input type="button" onclick="submitShiftDatas('2');"  value="仲裁" style="height:28px;width:100px;" class="btn btn-default"/> -->
				</div>
				<strong>故&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;障：</strong><br/><br/>
				<div>
				   <ul class="equf"></ul>
				</div>
                
              
			</div>  
		</div>
	</div>
</div>

