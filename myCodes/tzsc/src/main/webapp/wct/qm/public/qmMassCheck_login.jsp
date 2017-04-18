<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page='../../../initlib/initKeyboard.jsp' ></jsp:include>
<script type="text/javascript">
	$(function(){
		//初始化虚拟键盘
		 $('.mass-keyboard').keyboard();
		 //innerZxLogin();
		 //刷卡自动回车
		 document.onkeydown=function(event){
		      var e = event || window.event || arguments.callee.caller.arguments[0];    
		       if(e && e.keyCode==13){ // enter 键
		                var carn= $("#cardNum").val(); //获取文本框值
				  		if (carn == "") {
				  			jAlert("请将光标放入文本框!","系统提示");
				  		} else {
				  			var eno=$("#cardNum").val();
				  			//实例化登录信息（包括机台，班次班组）
				  			$.post("${pageContext.request.contextPath}/wct/system/loginWctZj.do",{eno:eno},function(json){
				  				if (json.isSuccess=="true") {
				  					checkUserRoles(json.roles);//验证登录人权限
				  					
				  					$("#massCheckZjr").html(json.name);
				  					$("#massCheckZt").html("登录成功");
				  					$("#isMassCheckZjr").html(json.isSuccess);
				  					//判断code是包装机
				  					var code=parseInt($('#QmCheckEquCode').text());
				  					if(code>30&&code<=60){
				  						fillStell();
				  					}
				  				} else {
				  					$("#isMassCheckZjr").html(json.isSuccess);
				  					$("#massCheckZjr").html("...");
				  					$("#massCheckZt").html("登录失败");
				  					$("#cardNum").val("");//清空
				  					$("#errorNum").text("刷卡失败，请再次刷卡!");
				  					$("#cardNum").focus();//选中
				  					//jAlert("未查询到相关卡号信息！","系统提示");
				  				}
				  			},"JSON");
				  		}
		       }
		 };
		// console.info(  $("#cardNum") );
		//默认光标选中文本框
	    //$("#cardNum").focus();
	});
	
	//工号登录
	function queryEnoByPass(){
		//获取用户名
		var loginName=$("#ghEno").val();
		//获取密码
		var passWord=$("#ghPassWord").val();
		if(loginName=="" || passWord==""){
			jAlert("工号或密码不能为空!","温馨提示");
		}else{
			   $.post("${pageContext.request.contextPath}/wct/system/loginWctZj.do",{"loginName":loginName,"passWord":passWord},function(json){
	  				if (json.isSuccess=="true") {
	  					checkUserRoles(json.roles);//验证登录人权限
	  					$("#massCheckZjr").html(json.name);
	  					$("#massCheckZt").html("登录成功");
	  					$("#isMassCheckZjr").html(json.isSuccess);
	  					//判断code是包装机
	  					var code=parseInt($('#QmCheckEquCode').text());
	  					if(code>30&&code<=60){
	  						fillStell();
	  					}
	  				} else {
	  					$("#isMassCheckZjr").html(json.isSuccess);
	  					$("#massCheckZjr").html("...");
	  					$("#massCheckZt").html("登录失败");
	  					$("#cardNum").val("");//清空
	  					$("#errorNum").text("工号登录失败，请重新登录!");
	  					$("#cardNum").focus();//选中
	  					//jAlert("未查询到相关卡号信息！","系统提示");
	  				}
			   },"JSON");
		}
	}
	
	//登录验证
	function checkUserRoles(roles){
		if(roles==""){
			//发送提醒信息 
			$("#errorNum").text("该用户没有分配角色权限!");
			$("#cardNum").val("");
			$("#cardNum").focus();
			return true;
		}
		var roleId=$("#tfRole:hidden").val();
		if(roleId==1){
			hideLoginFrom3();
			//跳过权限验证
			return true;
		}
		if(roles==""){
			$("#hid_div3").show();
		}else{
			/*
			*   页面加载，确定当前角色
                                                            操作工 = 8af2d43f4d73d86d014d73df6da90000
                                                            挡车工 = 8af2d4904fce586a014fd4a7f08e01a4
			*/
			console.info(roles+"="+roleId);
			if(roles.indexOf(roleId)==-1 ){
				//非操作工，弹出登录框
				$("#hid_div3").show();
				//发送提醒信息 
				$("#errorNum").text("该用户没有分配角色权限!");
				$("#cardNum").val("");
				$("#cardNum").focus();
			}else{
				hideLoginFrom3();
			}
		}
	}
	//弹出 窗口
	function openWin(){
		$("#hid_div").css("display","block");
	}
	function openWin2(){
		$("#hid_div2").css("display","block");
	}
	//隐藏窗口
	function hideLoginFrom(){
		$("#hid_div #name").val("");
		$("#hid_div #pwd").val("");
		$("#hid_div").css("display","none");
	}
	//隐藏打卡登录窗口
	function hideLoginFrom3(){
		$("#cardNum").val("");
		 $("#cardNum").focus();//默认光标选中文本框
		$("#hid_div3").css("display","none");
	}
	function hideLoginFrom2(){
		$("#hid_div2 #smallBox").val("");
		$("#hid_div2 #bigBox").val("");
		$("#hid_div2").css("display","none");
	}
	//填写钢印信息
	function fillStell(){
		var massCheckBc =$("#massCheckBc").text();
		var massCheckJth =$("#massCheckJth").text();
		var massCheckPm =$("#massCheckPm").text();
		var massCheckDcg =$("#massCheckDcg").text();
		var zjUserName =$("#massCheckZjr").text();//登录人
		var _eqpId=$('#eqpId').text();//设备id
		var _matId=$('#matId').text();//牌号id
		var _shiftID=$('#shiftID').text();
		
		var smallBox= $('#smallBox').val();
		var bigBox  = $('#bigBox').val();
		
		var params = '{"proWorkId":"'+schWorkorderId 
		+ '","mdShiftName":"'+ massCheckBc//班次
		+ '","mdEqmentName":"'+ massCheckJth//机台号
		+ '","mdEqmentId":"'+ _eqpId//机台id
		+ '","mdMatId":"'+ _matId//牌号id
		+ '","mdShiftId":"'+ _shiftID//班次id
		+ '","mdMatName":"'+ massCheckPm//牌号
		+ '","mdDcgName":"'+ massCheckDcg//挡车工
		+ '","zjUserName":"'+ zjUserName//登录人
		+ '","smallBox":"'+ smallBox //小盒钢印
		+ '","bigBox":"'+ bigBox //大盒钢印
		+ '"}';
		$.ajax({
	            type: "post",//使用get方法访问后台
	            dataType: "json",//返回json格式的数据
	            url: "${pageContext.request.contextPath}/wct/massFrist/fillSteelSeal.do",//要访问的后台地址
	            data: "params=" + params,//要发送的数据
	            success : function(r) {
					if(r.success){
						hideLoginFrom2();
					}else{
						var msg=r.msg.split(',');
						if(msg.length==2){
							$('#smallBox').val(msg[0]);
							$('#bigBox').val(msg[1]);
						}else{
							$('#smallBox').val(msg[0]);
							$('#bigBox').val(msg[0]);
						}
						openWin2();
					}
				}
	         });
	}
	//登录信息
	function innerLogin(){
		$("#massCheckZt").html("...");
		var usr = $("#name").val();
		var pwd = $("#pwd").val();
		if (usr == "" || pwd == "") {
			jAlert("请输入用户名和密码!","系统提示");
		} else {
			var params=getJsonData($('#password-box-wct-frm'));
			//实例化登录信息（包括机台，班次班组）
			$.post("${pageContext.request.contextPath}/wct/system/loginWctZj.do",params,function(json){
				//alert(json.isSuccess);
				if (json.isSuccess=="true") {
					$("#massCheckZjr").html(json.name);
					$("#massCheckZt").html("登录成功");
					$("#isMassCheckZjr").html(json.isSuccess);
					hideLoginFrom();
					//判断code是包装机
					var code=parseInt($('#QmCheckEquCode').text());
					if(code>30&&code<=60){
						fillStell();
					}
				} else {
					$("#isMassCheckZjr").html(json.isSuccess);
					$("#massCheckZjr").html("...");
					$("#massCheckZt").html("登录失败");
					jAlert("账户或密码错误!","系统提示");
				}
			},"JSON");
		}
	}
	//注销信息
	function innerZxLogin(){
		$("#massCheckZt").html("...");
		var params=getJsonData($('#password-box-wct-frm'));
		$.post("${pageContext.request.contextPath}/wct/system/loginWctDis.do",params,function(json){
			//alert(json.isSuccess);
			if (json.isSuccess=="true") {
				$("#isMassCheckZjr").html("false");
				$("#massCheckZjr").html("...");
				$("#massCheckZt").html("注销成功");
				hideLoginFrom();
			} else {
				$("#isMassCheckZjr").html("true");
				$("#massCheckZt").html("注销失败");
				jAlert("注销失败!","系统提示");
			}
		},"JSON");
	}
	
	
</script>
<style type="text/css">
#ghLogin{
  margin-left:60px;
  margin-top:40px;
  font-size:14px;
}
    #cardNum{
       border:0;
       outline:none;
       background-color:#FFEFD5; 
       width:200px;height: 40px;
       border-top:0px;border-left:0px;
       border-right:0px;  
       border-bottom:1px solid #FFA500;"
    }
	input:focus{
	 border: 0px;
	}
	/*密码验证开始*/	
	.password_botton{
		width: 200px;height: auto;font-size: 12px;font-weight: bold;
		text-align: center;padding-top: 4px;margin: 0 auto;
	}
	.btn-default {color: #FFFFFF;background-color: #5A5A5A;border-color: #cccccc;}
	.password_box{
		display: block;z-index: 99;position: absolute;
		top: 150px;left: 200px;width: 230px;font-size: 12px;
		border: 1px solid #FFA500;border-radius: 10px;
		height: 180px;
		background:#A0A0A0;padding: 30px;
	}
	.password_box2{
		display: block;z-index: 99;position: absolute;
		top: 110px;left: 200px;width: 330px;font-size: 12px;
		border: 1px solid #858484;border-radius: 10px;
		height: 250px;
		/* background:#A0A0A0; */
		 background-color:#FFEFD5;
		padding: 30px;
	}
	.input-title-group {
		 height: 40px;
		 font-size:20px;
		 padding-left: 60px;
	}
	.input-title-group2 {
		 height: 40px;
		 font-size:20px;
		 padding-left: 110px;
		 color:	#BC8F8F;
	}
	.input-group {
		margin-bottom: 15px ;
		margin-top: 15px;
		margin-left:70px;
		font-size:14px;
		
	}
	.input-group2 {
		margin-bottom: 8px ;
		margin-top: 15px;
		margin-left:50px;
		font-size:14px;
		
	}
	#username,#password{width:200px;}
	.ui-keyboard-preview{width:92%;}
	.form-control{height:20px;}
	/*密码验证结束*/	
	.input-group-addon {
		font-size:14px;
	}

</style>
</head>
	<!-- 登录hide div -->     
	<div id="hid_div"  style="display:none;height:768px;width:100%;top:0px;left:0px;position:absolute;z-index:90;" onclick="return false;">
		<div class="password_box">
			<form id="password-box-wct-frm">
				<div class="input-title-group">
					登录验证
				</div>
				<div class="input-group">
				  <span class="input-group-addon">用户名</span>
				  <input type="text" id="name" value="" name="loginName" class="form-control mass-keyboard" style="height: 30px;;border-radius:5px" placeholder="用户名"/>
				</div>
				<div class="input-group">
				  <span class="input-group-addon">密&nbsp;&nbsp;&nbsp;码</span>
				  <input type="password" id="pwd" value="" name="passWord" class="form-control mass-keyboard" style="height: 30px;border-radius:5px" placeholder="密码"/>
				</div>
				<div class="password_botton">			
					<input id="sub" type="button" value="确认" onclick="innerLogin()" class="btn btn-default"/>	
					<input id="sub" type="button" value="注销" onclick="innerZxLogin()" class="btn btn-default"/>		
					<input id="cancel" type="button" value="取消" onclick="hideLoginFrom()" class="btn btn-default"/>
				</div>
			</form>
		</div>
	</div>
	
	
	
	<!-- 填写钢印 -->
	
	<div id="hid_div2"  style="display:none;height:568px;width:100%;top:0px;left:0px;position:absolute;z-index:90;" onclick="return false;">
		<div class="password_box2">
			<form action="" method="post" id='steelSeal'>
				<div class="input-title-group2">
					钢印信息
				</div>
				<div>
				  <span style="font-size:14px;margin-right:15px;margin-bottom:15px;">小盒钢印</span>
				  <input type="text" id="smallBox" style="height: 30px;width:200px;border-radius:5px" class="keyboard">
				</div>
				<div>
				  <span  style="font-size:14px;margin-right:15px;">条盒钢印</span>
				  <input type="text" id="bigBox" style="height: 30px;width:200px;border-radius:5px" class="keyboard" >
				</div>
				<div style="margin-top:12px;" class="password_botton" >			
					<input id="sub" type="button" value="确认" onclick="fillStell()"class="btn btn-default">			
				</div>
			</form>
		</div>
	</div>
	
	
	<!-- 刷卡登录! -->
	<div id="hid_div3"  style="display:none; height:500px;width:100%;top:0px;left:0px;position:absolute;z-index:90;" onclick="return false;">
		<div class="password_box2">
			    <!-- 刷卡登录  -->
			    <div id="skLogin">
					<div class="input-title-group2">
						刷卡请登录
					</div>
					<div class="input-group">
					 <input  name="eno" type="password" id="cardNum" ></input>
					</div>
				</div>
				<!-- 工号登录-->
				<div id="ghLogin">
				    <span><font style="float: left;">工号：</font>
				    <input id="ghEno"  type="text" class="form-control mass-keyboard"    style="width:130px;height:25px;font-size:14px;"/></span> 
				    <br/>
				    <span style="float: left;"><font style="float: left;">密码：</font>
				    <input id="ghPassWord"  type="password" class="form-control mass-keyboard"  style="width:130px;height:25px;font-size:14px;" /></span>
				    <span><input type="button" onclick="queryEnoByPass()" id="ghButton" value="登录" style="width:50px;height: 25px;margin-left:10px;" /></span>
				    
				</div>
				<div style="margin-top:25px;margin-left:60px;">
				   <a id="errorNum" style="color:red;">默认密码：123456</a>
				</div>
				
			
		</div>
	</div>
	
<script type="text/javascript">
	$(function(){
		$('.keyboard').keyboard();
	});
</script>
</html>