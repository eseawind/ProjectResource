<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<title>呼叫要料</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jq 核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<jsp:include page='../../../initlib/initMyAlert.jsp' ></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" type="text/javascript"></script>
<style type="text/css">
*{font-family: "Microsoft YaHei","宋体",Arial;}
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
  width:70px;
  text-align: center;
}
.titleul2 li{
  float: left;
  margin-right:20px;
  width: 70px;
  text-align: center;
  
}
.titleul3 li{
  float: left;
  margin-right:20px;
  width: 70px;
  text-align: center;
  
}
.titleul4 li{
  float: left;
  margin-right:20px;
  width: 70px;
  text-align: center;
  
}
.titleul5 li{
  float: left;
  margin-right:20px;
  width: 70px;
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
input{
width:60px
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
	//根据code显示辅料div
	var eqpCode= parseInt(${loginInfo.equipmentCode});
	//文本框输入限制
	function checkInput(v){
		//v.value=v.value.replace(/[^\d]/g,'0');
		//v.value=v.value.replace(/[^\d+(\.)?]/g,'0');
		//判断输入的是不是数字
		if(!$.isNumeric(v.value)){
			v.value=0;
		}else{
			v.value=v.value.trim();
		}
		
	}
	//非空验证：
	function checkNull(v){
		var files = $(".titleul"+v).find("input");
	    files.each(function(){
	       var ival= $(this).val();
	       if(ival==""){
	    	   $(this).val("0") 
	       }
	    });
	}
	
	$(function(){
		if(eqpCode<31){
			$('#roller').css('display','block');
		}else if(eqpCode>30&&eqpCode<61){
			$('li').css("width","60px");
			$('#packer').css("display","block");
		}else if(eqpCode>60&&eqpCode<71){
			$('#sealer').css("display","block");
		}else if(eqpCode>100&&eqpCode<131){
			$('#filter').css("display","block");
		}
		
		
	}
	);
	//保存辅料要料
	function openWxHjJsp(type){
		jConfirm('确定提交吗？', '提示', function(r) {
			if(r){
				var url="${pageContext.request.contextPath}/wct/eqm/shiftGl/";
				var params;//{ name: "John", time: "2pm" } 
				//需要的参数：MEC_ID  设备类型 ，辅料，EQP_ID 设备id
				if(eqpCode<31){
					checkNull(2);
					var pan=$('#zj_lb1').val();
					var zhi=$('#zj_lb2').val();
					var val=parseFloat(pan)*4800+parseInt(zhi);
					params={zj_pz:$('#zj_pz').val(),zj_ssz:$('#zj_ssz').val(),zj_lb:val}
				}else if(eqpCode>30&&eqpCode<61){
					checkNull(3);
					params={zb_lbz:$('#zb_lbz').val(),zb_xhz:$('#zb_xhz').val(),zb_xhm:$('#zb_xhm').val(),zb_thz:$('#zb_thz').val(),zb_thm:$('#zb_thm').val(),zb_kz:$('#zb_kz').val(),zb_fq:$('#zb_fq').val(),zb_lx1:$('#zb_lx1').val(),zb_lx2:$('#zb_lx2').val()}
				}else if(eqpCode>60&&eqpCode<71){
					checkNull(5);
					params={yf_xp:$('#yf_xp').val(),yf_jd:$('#yf_jd').val()}
				}else if(eqpCode>100&&eqpCode<131){
					checkNull(4);
					params={zl_pz:$('#zl_pz').val(),zl_ss:$('#zl_ss').val()}
					/* params={zl_pz:$('#zl_pz').val(),zl_ss:$('#zl_ss').val(),zl_gy:$('#zl_gy').val(),zl_dkj:$('#zl_dkj').val(),zl_rrj:$('#zl_rrj').val()} */
				}
				if(type==1){
					$.post(url+"saveCallMatter.do",params,function(json){
						if(json.success){
							jAlert("物料呼叫成功","提示信息");
						}else{
							jAlert("物料呼叫信息保存失败","错误信息");
						}
					},"JSON");
				}else if(type==2){
					//本机呼叫
				}else if(type==3){
					//辅料呼叫
				}
			}
		});
		
	}
	function help_request(){
		$("#eqp_div").css("display","block");
	}
	function openPage(){		
		$("#call_div").css("display","none");
		$("#mat_system").css("display","block");
		$("#insertWeb").attr("src","http://www.baidu.com/");
	}
	function closePage(){
		$("#mat_system").css("display","none");
		$("#call_div").css("display","block");
	}
	
	
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
				$("#sbLoginStatus").html("注销成功");
				$("#hid_div").css("display","none");//隐藏弹出层
			}else{
				jAlert("注销失败", '系统提示');
			}
		},"JSON");
	}
	//取消
	function hiddenFrom(){
		$("#hid_div").css("display","none");
	}
	
	function openMdmatCallPage(){
		/* window.location.href="${pageContext.request.contextPath}/wct/call/matRequest/openCallPage.jsp"; */
		//禁用按钮3S钟
		 //$("#callMatButtom").css("display","none");	
		$("#callButtonm2").show();
		$("#callButtonm1").hide();
	} 
	/* window.setTimeout(function(){
		alert("ttt");
	}, 3000); */
	
	$(function(){
		$("#callButtonm2").hide();
	})
	function backCallButton(){
		$("#callButtonm1").show();
		$("#callButtonm2").hide();
	}
	
</script>
<div id="title">
     ${loginInfo.equipmentName}(${loginInfo.shift})
</div>
<!-- 用户登录窗口 -->
 <%-- <jsp:include page="/wct/eqm/eqmLogin.jsp"></jsp:include> --%>
	<div id="wkd-eqp-msg-title">
		<table style="font-size: 14px;">
			<tr>
				<td><b>当前用户：</b></td><td><div id="sbUserName" style="color:green;">
				    <c:if test="${empty loginWctEqmInfo}">${loginInfo.name}</c:if>
				    <c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.name}</c:if>
				</div></td>
				<td width="10"></td>
				<td><b>&nbsp;登录角色：</b></td>
				<td>
					<div id="sbUserRole">
						<c:if test="${empty loginWctEqmInfo}">${loginInfo.rolesNames}</c:if>
					    <c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.rolesNames}</c:if>
					</div>
					<div id="sbUserRoleIds" style="display:none;">
						<c:if test="${empty loginWctEqmInfo}">${loginInfo.roles}</c:if>
						<c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.roles}</c:if>
					</div>
				</td>
				<td width="10"></td>
				<td><b>登录状态：</b></td><td><div id="sbLoginStatus">
				    <c:if test="${empty loginWctEqmInfo}">${loginInfo.dlStatus}</c:if>
					<c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.dlStatus}</c:if>
				</div></td>
				<td width="10"></td>
				<!-- <td>
					<button  onclick="openWin();" style="height:28px;width:80px;" class="btn btn-default">
						 登录/注销
					</button>
				</td> -->
			</tr>
		</table>
	</div>

	<div id="tab" style=" PADDING:0px; LINE-HEIGHT: 20px; OVERFLOW: auto;height:562px;font-size: 12px;font-weight: normal;">
		<div  class="scroll_box" id="scroll_box">
			<!-- 本机呼叫，辅助呼叫 请求请求按钮div -->
			<div id="call_div">
				<br/>
				
				
<!-- 卷烟机start -->
				<!-- <div class="roller" id='roller' name='roller' style="display:none">
				   <ul class="titleul">
				      <li style="font-weight: bold;">辅&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;料：</li>
				      <li >盘纸</li>
				      <li>水松纸</li>
				      <li style="margin-left: 25">滤棒</li>
				   </ul>
				   <br/><br/>
				   <ul class="titleul2" name='rolerData'>
				      <li>实领辅料：</li>
				      <li><input onkeyup="checkInput(this)" placeholder="盘" type="text" id='zj_pz'  class="yellowSty"value=""/></li>
				      <li><input onkeyup="checkInput(this)" placeholder="千克" type="text" id='zj_ssz'  class="yellowSty" value=""/></li>
				      <li style="width:30"><input placeholder="盘" style="width: 60" onkeyup="checkInput(this)" type="text" id='zj_lb1'  class="yellowSty" value=""/></li>
				      <li style="width:60"><input placeholder="支" style="width: 60" onkeyup="checkInput(this)" type="text" id='zj_lb2'  class="yellowSty" value=""/></li>
				   </ul>
				</div> -->
<!-- 卷烟机end -->
				
<!-- 包装机start -->
				<!-- <div class="packer" id='packer' style="display: none;">
				   <ul class="titleul">
				      <li style="font-weight: bold;">辅&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;料：</li>
				      <li>铝箔纸</li>
				      <li>小盒纸</li>
				      <li>小盒膜</li>
				      <li>条盒纸</li>
				      <li>条盒膜</li>
				      <li>卡纸</li>
				      <li>封签</li>
				      <li>拉线1</li>
				      <li>拉线2</li>
				   </ul>
				   <br/><br/>
				   <ul class="titleul3">
				      <li>实领辅料：</li>
				      <li><input placeholder="kg" style="width:60" onkeyup="checkInput(this)" type="text"  id="zb_lbz" class="yellowSty"value=""/></li>
				      <li><input placeholder="万张" onkeyup="checkInput(this)" type="text" id="zb_xhz"  class="yellowSty" value=""/></li>
				      <li><input placeholder="kg" onkeyup="checkInput(this)" type="text"  id="zb_xhm" class="yellowSty" value=""/></li>
				      <li><input placeholder="万张"onkeyup="checkInput(this)" type="text"  id="zb_thz" class="yellowSty" value=""/></li>
				      <li><input placeholder="kg" onkeyup="checkInput(this)" type="text" id="zb_thm"  class="yellowSty" value=""/></li>
				      <li><input placeholder="kg" onkeyup="checkInput(this)" type="text" id="zb_kz"  class="yellowSty" value=""/></li>
				      <li><input placeholder="万张" onkeyup="checkInput(this)" type="text" id="zb_fq"  class="yellowSty" value=""/></li>
				      <li><input placeholder="kg" onkeyup="checkInput(this)" type="text"  id="zb_lx1" class="yellowSty" value=""/></li>
				      <li><input placeholder="kg" onkeyup="checkInput(this)" type="text"  id="zb_lx2" class="yellowSty" value=""/></li>
				   </ul>
				</div> -->
<!-- 包装机end -->

<!-- 成型机start -->
				<!-- <div class="filter" id='filter' style="display: none;">
				   <ul class="titleul">
				      <li style="font-weight: bold;">辅&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;料：</li>
				      <li>盘纸</li>
				      <li>丝束</li>
				      <li>甘油</li>
				      <li>搭口胶</li>
				      <li>热熔胶</li>
				   </ul>
				   <br/><br/>
				   <ul class="titleul4">
				      <li>实领辅料：</li>
				      <li><input placeholder="盘" onkeyup="checkInput(this)" type="text" id="zl_pz"  class="yellowSty"value=""/></li>
				      <li><input placeholder="kg" onkeyup="checkInput(this)" type="text" id="zl_ss"  class="yellowSty" value=""/></li>
				      <li><input placeholder="kg" onkeyup="checkInput(this)" type="text" id="zl_gy"  class="yellowSty" value=""/></li>
				      <li><input onkeyup="checkInput(this)" type="text" id="zl_dkj"  class="yellowSty" value=""/></li>
				      <li><input onkeyup="checkInput(this)" type="text" id="zl_rrj"  class="yellowSty" value=""/></li>
				   </ul>
				</div> -->
<!-- 成型机end -->

<!-- 封箱机start -->
				<!-- <div class="sealer" id='sealer' style="display: none;">
				   <ul class="titleul">
				      <li style="font-weight: bold;">辅&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;料：</li>
				      <li>箱皮</li>
				      <li>胶带</li>
				      
				   </ul>
				   <br/><br/>
				   <ul class="titleul5">
				      <li>实领辅料：</li>
				      <li><input placeholder="张" onkeyup="checkInput(this)" type="text" id="yf_xp"  class="yellowSty"value=""/></li>
				      <li><input placeholder="卷" onkeyup="checkInput(this)" type="text" id="yf_jd" class="yellowSty" value=""/></li>
				   </ul>
				</div> -->
<!-- 封箱机end -->
                <font style="color:red;font-size:14px;">
                                                 说明：1）要料前请确定当前用户,如非本人操作请退出当前系统重新打卡登录！
                <br/>
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                              2） 交接班退料完成后，务必退出登录，如有疑问请联系相关人员。
                </font>
				<br/><br/> <hr/>
				 <div style="margin-top:20px;margin-bottom:20px; text-align: center;font-size: 14px;">
				   <!--  <input  type="button" onclick="openWxHjJsp('1');"  value="确&nbsp;&nbsp;&nbsp;&nbsp;定" style="height:28px;width:100px;" class="btn btn-default"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input  type="button" onclick="openWxHjJsp('2');"  value="本机呼叫" style="height:28px;width:100px;" class="btn btn-default"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input  type="button" onclick="openWxHjJsp('3');"  value="辅料呼叫" style="height:28px;width:100px;" class="btn btn-default"/> -->
				
                   <div id="callButtonm1">
					    <a id="callMatButtom" href ='llh://'><input   type="button" onclick="openMdmatCallPage()"  value="物料呼叫" style="height:40px;width:120px;" class="btn btn-default"/> </a>
				   </div>
				   <div id="callButtonm2"> 
				        <input  type="button" onclick="backCallButton()"  value="刷新..." style="height:40px;width:120px;" class="btn btn-default"/>
				   </div>
				</div>
				
                
               
			</div>  
		</div>
	</div>
</div>
