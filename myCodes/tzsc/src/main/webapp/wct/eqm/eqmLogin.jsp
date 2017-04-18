<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page='../../initlib/initKeyboard.jsp' ></jsp:include>
<script type="text/javascript">
	$(function(){
		$('.mass-keyboard').keyboard();
	});
</script>
<style type="text/css">
	/*密码验证开始*/	
	.password_botton{
		width: 200px;height: auto;font-size: 12px;font-weight: bold;
		text-align: center;padding-top: 4px;margin: 0 auto;
	}
	.btn-default {color: #FFFFFF;background-color: #5A5A5A;border-color: #cccccc;}
	.password_box{
		display: block;z-index: 99;position: absolute;
		top: 150px;left: 280px;width: 230px;font-size: 12px;
		border: 1px solid #858484;border-radius: 10px;
		height: 180px;
		background:#A0A0A0;padding: 30px;
	}
	.input-title-group {
		 height: 40px;
		 font-size:20px;
		 padding-left: 70px;
	}
	.input-group {
		margin-bottom: 15px;
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
				  <input type="text" id="name" value="11001" name="loginName" class="form-control mass-keyboard" style="height: 30px;;border-radius:5px" placeholder="用户名"/>
				</div>
				<div class="input-group">
				  <span class="input-group-addon">密&nbsp;&nbsp;&nbsp;码</span>
				  <input type="password" id="pwd" value="123456" name="passWord" class="form-control mass-keyboard" style="height: 30px;border-radius:5px" placeholder="密码"/>
				</div>
				<div class="password_botton">	
					<button  onclick="initLogin();" style="height:28px;width:50px;" class="btn btn-default">
						 确认
					</button>	
					<button  onclick="innerLogin();" style="height:28px;width:50px;" class="btn btn-default">
						注销
					</button>	
					<button  onclick="hiddenFrom();" style="height:28px;width:50px;" class="btn btn-default">
						取消
					</button>		
				</div>
			</form>
		</div>
	</div>
</html>