 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>卷包车间实时监控</title>
<meta name="author" content="leejean">
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/fullScreen.js" type="text/javascript" ></script>
<style type="text/css">
.container-fluid{
	background-image:url("img/rollerPackRoom.jpg");
	-webkit-background-size:100% 100%;
}
table{
	font-size:15px;
	font-family: "Microsoft YaHei" ! important;
	font-weight: bold;
}
	.btn-default {
		color: yellow;
		background-color: #5A5A5A;
		border-color: #cccccc;
}

.btn {
  display: inline-block;
  padding:0px;
  margin-top: 3px;
  font-size: 15px;
  font-family: "Microsoft YaHei" ! important;
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
</style>
<script type="text/javascript">
	console.info("进入卷包车间3D监控...");
	var i=1;
	//加载code值
	/* $.post("${pageContext.request.contextPath}/pms/isp/initRollerPackerGroups.do",function(rows){
		for(var i=0;i<rows.length;i++){
			$("#Rcode"+i).val(rows[i].rCode);
			$("#Pcode"+i).val(rows[i].pCode);
			var r=$("#Rcode"+i).val();
			var p=$("#Pcode"+i).val();
			alert(r+"----"+p);
		}
		//initWorkOrderInfo();
	},"JSON"); */
 	//加载机组工单信息
	function initWorkOrderInfo(){
		//type 1：卷烟机工单2：包装机工单3:封箱机工单4:成型机工
		//卷烟机和包装机为一个整体机组，该机组使用卷烟机工单作为显示
		$.post("${pageContext.request.contextPath}/pms/isp/initWorkOrderInfo.do",{type:1},function(rows){
			if(!rows){
				return;
			}
			$("#shift").val(rows[0].shiftName);//班次
			$("#shift").css("color","00FF00");
			$("#shift").css("font-size","18px");
			/* $("#wendu").val("暂无");//温度
			$("#wendu").css("color","00FF00");
			$("#wendu").css("font-size","18px");
			$("#shidu").val("暂无");//湿度
			$("#shidu").css("color","00FF00");
			$("#shidu").css("font-size","18px"); */
			/* for(var i=0;i<rows.length;i++){
				var data = rows[i];
				$("#shift").val(data.shiftName);
			} */
		},"JSON");
	} 
	initWorkOrderInfo();
	function getAllRollerPackerDatas(){
		 $.post("${pageContext.request.contextPath}/pms/isp/getAllRollerPackerDatas.do",function(datas){
			for(var i=0;i<datas.length;i++){
				var data = datas[i];
				var code= data.code;
				var online = data.online;
				var speed = data.speed;
				var qty = data.qty;
				var R="";
				var Sdanwei="";//车速单位
				if(code<30){
					R="R";
					//卷烟机产量
					qty=(qty/50).toFixed(2);
					Sdanwei="支/分钟";
				}else{
					R="P";
					//包装机产量
					qty=(qty/2500).toFixed(2);
					Sdanwei="包/分钟";
				}
			if(online){
				if(speed==0){
					$("#"+R+"speed"+code).val("运行停止");
					$("#"+R+"output"+code).val("运行停止");
					$("#"+R+"speed"+code).css("color","red");
					$("#"+R+"output"+code).css("color","red");
				}else{
					$("#"+R+"speed"+code).val(speed+Sdanwei);
					$("#"+R+"output"+code).val(qty+"箱");
					$("#"+R+"speed"+code).css("color","#00FF00");
					$("#"+R+"output"+code).css("color","#00FF00");
				}
			}else{
				$("#"+R+"speed"+code).val("网络断开");
				$("#"+R+"output"+code).val("网络断开");
				$("#"+R+"speed"+code).css("color","yellow");
				$("#"+R+"output"+code).css("color","yellow");
			}
		  }
		},"JSON"); 
		/* for(var j=1;j<13;j++){
			$("#Rspeed"+j).val(i+"支");// 卷烟机
			$("#Routput"+j).val(i+"箱");
			$("#Pspeed"+j).val(i+"支");//包装机
			$("#Poutput"+j).val(i+"箱");
			$("#Rcode"+j).val(30);
			$("#Pcode"+j).val(50);
		}
		
		i++; */
	}
	window.setInterval("getAllRollerPackerDatas()",3000);
	function gotoDetJsp(code,type){
		window.open("${pageContext.request.contextPath}/pms/isp/"+type+".jsp"+"?code="+code+"&type="+type,"myWindow"); 
	}
</script>
<body bgcolor="#ADADAD">
<!--breadcrumbs-->
<!--End-breadcrumbs-->
<div class="container-fluid" style="width:1970px;height:1060px;margin-left:-70px;">
	<!-- <img src="img/rollerPackRoom.jpg" style="width:100%;height:100%;"/> -->
	<!-- 1号卷包设备 -->
	<div style="padding-left:1620px;padding-top:230px;"><!-- 1号卷烟机 -->
		<table><input type="hidden" id="Rcode1">
			<tr><td>车速</td><td><input type="button" id="Rspeed1" value="网络断开" onclick="gotoDetJsp(1,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput1" value="网络断开" onclick="gotoDetJsp(1,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:1500px;padding-top:80px;"><!-- 1号包装机 -->
		<table><input type="hidden" id="Pcode1">
			<tr><td>车速</td><td><input type="button" id="Pspeed31" value="网络断开" onclick="gotoDetJsp(31,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput31" value="网络断开" onclick="gotoDetJsp(31,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 2号卷包设备 -->
	<div style="padding-left:1430px;margin-top:-240px;"><!-- 2号卷烟机 -->
		<table><input type="hidden" id="Rcode2">
			<tr><td>车速</td><td><input type="button" id="Rspeed2" value="网络断开" onclick="gotoDetJsp(2,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput2" value="网络断开" onclick="gotoDetJsp(2,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:1280px;margin-top:60px;"><!-- 2号包装机 -->
		<table><input type="hidden" id="Pcode2">
			<tr><td>车速</td><td><input type="button" id="Pspeed32" value="网络断开" onclick="gotoDetJsp(32,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput32" value="网络断开" onclick="gotoDetJsp(32,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 3号卷包设备 -->
	<div style="padding-left:1270px;margin-top:-220px;"><!-- 3号卷烟机 -->
		<table><input type="hidden" id="Rcode3">
			<tr><td>车速</td><td><input type="button" id="Rspeed3" value="网络断开" onclick="gotoDetJsp(3,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput3" value="网络断开" onclick="gotoDetJsp(3,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:1100px;margin-top:50px;"><!-- 3号包装机 -->
		<table><input type="hidden" id="Pcode3">
			<tr><td>车速</td><td><input type="button" id="Pspeed33" value="网络断开" onclick="gotoDetJsp(33,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput33" value="网络断开" onclick="gotoDetJsp(33,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 4号卷包设备 -->
	<div style="padding-left:1120px;margin-top:-210px;"><!-- 4号卷烟机 -->
		<table><input type="hidden" id="Rcode4">
			<tr><td>车速</td><td><input type="button" id="Rspeed4" value="网络断开" onclick="gotoDetJsp(4,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput4" value="网络断开" onclick="gotoDetJsp(4,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:930px;margin-top:45px;"><!-- 4号包装机 -->
		<table><input type="hidden" id="Pcode4">
			<tr><td>车速</td><td><input type="button" id="Pspeed34" value="网络断开" onclick="gotoDetJsp(34,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput34" value="网络断开" onclick="gotoDetJsp(34,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 5号卷包设备 -->
	<div style="padding-left:980px;margin-top:-200px;"><!-- 5号卷烟机 -->
		<table><input type="hidden" id="Rcode5">
			<tr><td>车速</td><td><input type="button" id="Rspeed5" value="网络断开" onclick="gotoDetJsp(5,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput5" value="网络断开" onclick="gotoDetJsp(5,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:790px;margin-top:40px;"><!-- 5号包装机 -->
		<table><input type="hidden" id="Pcode5">
			<tr><td>车速</td><td><input type="button" id="Pspeed35" value="网络断开" onclick="gotoDetJsp(35,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput35" value="网络断开" onclick="gotoDetJsp(35,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 6号卷包设备 -->
	<div style="padding-left:1300px;padding-top:300px;"><!-- 6号卷烟机 -->
		<table><input type="hidden" id="Rcode6">
			<tr><td>车速</td><td><input type="button" id="Rspeed6" value="网络断开" onclick="gotoDetJsp(6,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput6" value="网络断开" onclick="gotoDetJsp(6,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:1100px;padding-top:140px;"><!-- 6号包装机 -->
		<table><input type="hidden" id="Pcode6">
			<tr><td>车速</td><td><input type="button" id="Pspeed36" value="网络断开" onclick="gotoDetJsp(36,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput36" value="网络断开" onclick="gotoDetJsp(36,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 7号卷包设备 -->
	<div style="padding-left:1060px;margin-top:-340px;"><!-- 7号卷烟机 -->
		<table><input type="hidden" id="Rcode7">
			<tr><td>车速</td><td><input type="button" id="Rspeed7" value="网络断开" onclick="gotoDetJsp(7,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput7" value="网络断开" onclick="gotoDetJsp(7,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:860px;padding-top:80px;"><!-- 7号包装机 -->
		<table><input type="hidden" id="Pcode7">
			<tr><td>车速</td><td><input type="button" id="Pspeed37" value="网络断开" onclick="gotoDetJsp(37,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput37" value="网络断开" onclick="gotoDetJsp(37,'ZB25');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 8号卷包设备 -->
	<div style="padding-left:850px;margin-top:-270px;"><!-- 8号卷烟机 -->
		<table><input type="hidden" id="Rcode8">
			<tr><td>车速</td><td><input type="button" id="Rspeed8" value="网络断开" onclick="gotoDetJsp(8,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput8" value="网络断开" onclick="gotoDetJsp(8,'ZJ17');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:630px;padding-top:50px;"><!-- 8号包装机 -->
		<table><input type="hidden" id="Pcode8">
			<tr><td>车速</td><td><input type="button" id="Pspeed38" value="网络断开" onclick="gotoDetJsp(38,'GD');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput38" value="网络断开" onclick="gotoDetJsp(38,'GD');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 9号卷包设备 -->
	<div style="padding-left:670px;margin-top:-245px;"><!-- 9号卷烟机 -->
		<table><input type="hidden" id="Rcode9">
			<tr><td>车速</td><td><input type="button" id="Rspeed9" value="网络断开" onclick="gotoDetJsp(9,'PROTOS70');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput9" value="网络断开" onclick="gotoDetJsp(9,'PROTOS70');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:450px;padding-top:50px;"><!-- 9号包装机 -->
		<table><input type="hidden" id="Pcode9">
			<tr><td>车速</td><td><input type="button" id="Pspeed39" value="网络断开" onclick="gotoDetJsp(39,'GD');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput39" value="网络断开" onclick="gotoDetJsp(39,'GD');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 11号卷包设备 -->
	<div style="padding-left:420px;margin-top:-280px;"><!-- 11号卷烟机 -->
		<table><input type="hidden" id="Rcode11">
			<tr><td>车速</td><td><input type="button" id="Rspeed11" value="网络断开" onclick="gotoDetJsp(11,'PROTOS70');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput11" value="网络断开" onclick="gotoDetJsp(11,'PROTOS70');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:165px;padding-top:40px;"><!-- 11号包装机 -->
		<table><input type="hidden" id="Pcode11">
			<tr><td>车速</td><td><input type="button" id="Pspeed41" value="网络断开" onclick="gotoDetJsp(41,'ZB45');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput41" value="网络断开" onclick="gotoDetJsp(41,'ZB45');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 12号卷包设备 -->
	<div style="padding-left:845px;margin-top:-330px;"><!-- 12号卷烟机 -->
		<table><input type="hidden" id="Rcode12">
			<tr><td>车速</td><td><input type="button" id="Rspeed12" value="网络断开" onclick="gotoDetJsp(12,'PROTOS70');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput12" value="网络断开" onclick="gotoDetJsp(12,'PROTOS70');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:640px;padding-top:35px;"><!-- 12号包装机 -->
		<table><input type="hidden" id="Pcode12">
			<tr><td>车速</td><td><input type="button" id="Pspeed42" value="网络断开" onclick="gotoDetJsp(42,'ZB45');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput42" value="网络断开" onclick="gotoDetJsp(42,'ZB45');" style="background-color: #0A0A0A; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:100px;margin-top:-200px;"><!-- 湿度、温度、班次、班组 -->
		<table><input type="hidden" id="Pcode12">
			<!-- <tr><td><font size="4">温度</font></td><td><input type="button" id="wendu"  style="background-color: #5C5C5C; width:100px;" class="btn btn-default"></td></tr>
			<tr><td><font size="4">湿度</font></td><td><input type="button" id="shidu"  style="background-color: #5C5C5C; width:100px;" class="btn btn-default"></td></tr> -->
			<tr><td><font size="4">班次</font></td><td><input type="button" id="shift"  style="background-color: #5C5C5C; width:100px;" class="btn btn-default"></td></tr>
		</table>
	</div>
</div>
</body>
</html>
