 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>卷包车间实时监控</title>
<meta name="author" content="leejean">
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<style type="text/css">
.container-fluid{
	background-image:url("img/rollerPackRoom.jpg");
	-webkit-background-size:100% 100%;
}
table{
	font-size:12px;
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
  font-size: 12px;
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
	var i=1;
	//加载code值
	$.post("${pageContext.request.contextPath}/pms/isp/initRollerPackerGroups.do",function(rows){
		for(var i=0;i<rows.length;i++){
			$("#Rcode"+(i+1)).val(rows[i].rCode);
			$("#Pcode"+(i+1)).val(rows[i].pCode);
			/* var r=$("#Rcode"+i).val();
			var p=$("#Pcode"+i).val();
			alert(r+"----"+p); */
		}
		//initWorkOrderInfo();
	},"JSON");
/* 	//加载机组工单信息
	function initWorkOrderInfo(){
		//type 1：卷烟机工单2：包装机工单3:封箱机工单4:成型机工
		//卷烟机和包装机为一个整体机组，该机组使用卷烟机工单作为显示
		$.post("${pageContext.request.contextPath}/pms/isp/initWorkOrderInfo.do",{type:1},function(rows){
			if(!rows){
				return;
			}
			for(var i=0;i<rows.length;i++){
				var data = rows[i];
				$("#teamName"+data.code).html(data.teamName);
				$("#matName"+data.code).html(data.matName);
				$("#planQty"+data.code).html(data.qty);
			}
		},"JSON");
	} */
	
	function getAllRollerPackerDatas(){
		 $.post("${pageContext.request.contextPath}/pms/isp/getAllRollerPackerDatas.do",function(datas){
			for(var i=0;i<datas.length;i++){
				var data = datas[i];
				var code= data.code;
				var online = data.online;
				var speed = data.speed;
				var qty = data.qty;
				var R="";
				if(code<30){
					R="R";
					//卷烟机产量
					qty=(qty/50).toFixed(2);
				}else{
					R="P";
					//包装机产量
					qty=(qty/2500).toFixed(2);
				}
			if(online){
				if(speed==0){
					$("#"+R+"speed"+code).val("运行停止");
					$("#"+R+"output"+code).val("运行停止");
					$("#"+R+"speed"+code).css("color","red");
					$("#"+R+"output"+code).css("color","red");
				}else{
					$("#"+R+"speed"+code).val(speed);
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
</head>
<body bgcolor="#ADADAD">
<!--breadcrumbs-->
<!--End-breadcrumbs-->
<div class="container-fluid" style="width:1200px;height:700px;">
	<!-- <img src="img/rollerPackRoom.jpg" style="width:100%;height:100%;"/> -->
	<!-- 1号卷包设备 -->
	<div style="padding-left:81%;padding-top:12%;"><!-- 1号卷烟机 -->
		<table><input type="hidden" id="Rcode1">
			<tr><td>车速</td><td><input type="button" id="Rspeed1" value="网络断开" onclick="gotoDetJsp(1,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput1" value="网络断开" onclick="gotoDetJsp(1,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:75%;padding-top:3%;"><!-- 1号包装机 -->
		<table><input type="hidden" id="Pcode1">
			<tr><td>车速</td><td><input type="button" id="Pspeed31" value="网络断开" onclick="gotoDetJsp(31,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput31" value="网络断开" onclick="gotoDetJsp(31,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 2号卷包设备 -->
	<div style="padding-left:72%;margin-top: -14%;"><!-- 2号卷烟机 -->
		<table><input type="hidden" id="Rcode2">
			<tr><td>车速</td><td><input type="button" id="Rspeed2" value="网络断开" onclick="gotoDetJsp(2,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput2" value="网络断开" onclick="gotoDetJsp(2,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:65%;margin-top:3%;"><!-- 2号包装机 -->
		<table><input type="hidden" id="Pcode2">
			<tr><td>车速</td><td><input type="button" id="Pspeed32" value="网络断开" onclick="gotoDetJsp(32,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput32" value="网络断开" onclick="gotoDetJsp(32,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 3号卷包设备 -->
	<div style="padding-left:64%;margin-top: -14%;"><!-- 3号卷烟机 -->
		<table><input type="hidden" id="Rcode3">
			<tr><td>车速</td><td><input type="button" id="Rspeed3" value="网络断开" onclick="gotoDetJsp(3,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput3" value="网络断开" onclick="gotoDetJsp(3,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:55%;margin-top:2%;"><!-- 3号包装机 -->
		<table><input type="hidden" id="Pcode3">
			<tr><td>车速</td><td><input type="button" id="Pspeed33" value="网络断开" onclick="gotoDetJsp(33,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput33" value="网络断开" onclick="gotoDetJsp(33,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 4号卷包设备 -->
	<div style="padding-left:57%;margin-top: -13%;"><!-- 4号卷烟机 -->
		<table><input type="hidden" id="Rcode4">
			<tr><td>车速</td><td><input type="button" id="Rspeed4" value="网络断开" onclick="gotoDetJsp(4,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput4" value="网络断开" onclick="gotoDetJsp(4,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:47%;margin-top:2%;"><!-- 4号包装机 -->
		<table><input type="hidden" id="Pcode4">
			<tr><td>车速</td><td><input type="button" id="Pspeed34" value="网络断开" onclick="gotoDetJsp(34,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput34" value="网络断开" onclick="gotoDetJsp(34,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 5号卷包设备 -->
	<div style="padding-left:50%;margin-top: -12%;"><!-- 5号卷烟机 -->
		<table><input type="hidden" id="Rcode5">
			<tr><td>车速</td><td><input type="button" id="Rspeed5" value="网络断开" onclick="gotoDetJsp(5,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput5" value="网络断开" onclick="gotoDetJsp(5,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:40%;margin-top:1%;"><!-- 5号包装机 -->
		<table><input type="hidden" id="Pcode5">
			<tr><td>车速</td><td><input type="button" id="Pspeed35" value="网络断开" onclick="gotoDetJsp(35,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput35" value="网络断开" onclick="gotoDetJsp(35,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 6号卷包设备 -->
	<div style="padding-left:65%;padding-top: 15%;"><!-- 6号卷烟机 -->
		<table><input type="hidden" id="Rcode6">
			<tr><td>车速</td><td><input type="button" id="Rspeed6" value="网络断开" onclick="gotoDetJsp(6,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput6" value="网络断开" onclick="gotoDetJsp(6,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:56%;padding-top:5%;"><!-- 6号包装机 -->
		<table><input type="hidden" id="Pcode6">
			<tr><td>车速</td><td><input type="button" id="Pspeed36" value="网络断开" onclick="gotoDetJsp(36,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput36" value="网络断开" onclick="gotoDetJsp(36,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 7号卷包设备 -->
	<div style="padding-left:53%;margin-top:-18%;"><!-- 7号卷烟机 -->
		<table><input type="hidden" id="Rcode7">
			<tr><td>车速</td><td><input type="button" id="Rspeed7" value="网络断开" onclick="gotoDetJsp(7,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput7" value="网络断开" onclick="gotoDetJsp(7,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:43%;padding-top:3%;"><!-- 7号包装机 -->
		<table><input type="hidden" id="Pcode7">
			<tr><td>车速</td><td><input type="button" id="Pspeed37" value="网络断开" onclick="gotoDetJsp(37,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput37" value="网络断开" onclick="gotoDetJsp(37,'ZB25');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 8号卷包设备 -->
	<div style="padding-left:42%;margin-top:-16%;"><!-- 8号卷烟机 -->
		<table><input type="hidden" id="Rcode8">
			<tr><td>车速</td><td><input type="button" id="Rspeed8" value="网络断开" onclick="gotoDetJsp(8,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput8" value="网络断开" onclick="gotoDetJsp(8,'ZJ17');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:31%;padding-top:2%;"><!-- 8号包装机 -->
		<table><input type="hidden" id="Pcode8">
			<tr><td>车速</td><td><input type="button" id="Pspeed38" value="网络断开" onclick="gotoDetJsp(38,'GD');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput38" value="网络断开" onclick="gotoDetJsp(38,'GD');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 9号卷包设备 -->
	<div style="padding-left:34%;margin-top:-14%;"><!-- 9号卷烟机 -->
		<table><input type="hidden" id="Rcode9">
			<tr><td>车速</td><td><input type="button" id="Rspeed9" value="网络断开" onclick="gotoDetJsp(9,'PROTOS70');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput9" value="网络断开" onclick="gotoDetJsp(9,'PROTOS70');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:22%;padding-top:1%;"><!-- 9号包装机 -->
		<table><input type="hidden" id="Pcode9">
			<tr><td>车速</td><td><input type="button" id="Pspeed39" value="网络断开" onclick="gotoDetJsp(39,'GD');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput39" value="网络断开" onclick="gotoDetJsp(39,'GD');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 11号卷包设备 -->
	<div style="padding-left:18%;margin-top:-16%;"><!-- 11号卷烟机 -->
		<table><input type="hidden" id="Rcode11">
			<tr><td>车速</td><td><input type="button" id="Rspeed11" value="网络断开" onclick="gotoDetJsp(11,'PROTOS70');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput11" value="网络断开" onclick="gotoDetJsp(11,'PROTOS70');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:7%;padding-top:0%;"><!-- 11号包装机 -->
		<table><input type="hidden" id="Pcode11">
			<tr><td>车速</td><td><input type="button" id="Pspeed41" value="网络断开" onclick="gotoDetJsp(41,'ZB45');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput41" value="网络断开" onclick="gotoDetJsp(41,'ZB45');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<!-- 12号卷包设备 -->
	<div style="padding-left:42%;margin-top:-19%;"><!-- 12号卷烟机 -->
		<table><input type="hidden" id="Rcode12">
			<tr><td>车速</td><td><input type="button" id="Rspeed12" value="网络断开" onclick="gotoDetJsp(12,'PROTOS70');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Routput12" value="网络断开" onclick="gotoDetJsp(12,'PROTOS70');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
	<div style="padding-left:33%;padding-top:0%;"><!-- 12号包装机 -->
		<table><input type="hidden" id="Pcode12">
			<tr><td>车速</td><td><input type="button" id="Pspeed42" value="网络断开" onclick="gotoDetJsp(42,'ZB45');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
			<tr><td>产量</td><td><input type="button" id="Poutput42" value="网络断开" onclick="gotoDetJsp(42,'ZB45');" style="background-color: #0A0A0A; width:50px;" class="btn btn-default"></td></tr>
		</table>
	</div>
</div>
</body>
</html>

