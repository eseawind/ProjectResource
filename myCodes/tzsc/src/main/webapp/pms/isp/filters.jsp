<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>成型车间监控</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="上海兰宝数据采集系统,卷接包数据采集" />
<meta name="author" content="leejean" />
<jsp:include page="../../initlib/initAll.jsp"></jsp:include>
<style type="text/css">
*{font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;}
body{background-color: #E0ECFF}
ul{list-style: none;}
ul li{margin-top:4px;}
/* 顶部信息区域 */
.info_bar {min-width:1330px;width:99.8%;height: 25px;background-color: #E0ECFF;background: -webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);background: -moz-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: -o-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);background: linear-gradient(to bottom,#EFF5FF 0,#E0ECFF 100%);background-repeat: repeat-x;filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
border-color: #95B8E7;border-width: 1px;border-style: solid;padding: 5px 0px;position: relative;}
.info_bar_botton{height: 20px;}
.info_bar_botton a{width:80px;height: 25px;display: block;float:left;margin-right: 15px;}
.info_bar_botton .run_button{background:url(img/run_botton.png) no-repeat;}
.info_bar_botton .stop_button{background:url(img/stop_button.png) no-repeat;}
.info_bar_botton .interrupt_button{background:url(img/interrupt_button.png) no-repeat;}
.info_bar_info{height: 25px;float:left;margin-left: 15px;}
.info_bar_info span{color:blue;font-weight: bold;}
/* div{border:1px solid red;} */
.fl{float:left;}
.cb{clear:both;}
.filter_div{width:540px;height:262px;float:left;margin-left:5px;margin-top:5px;;background-color:#858489;border-radius:5px;overflow: hidden;}
.filter_div .filter_img{width:550px;height:106px;background-image: url("img/filter.png")}
.filter_div .filter_info{width:550px;height:152px;background-color:#858489;}
.packer_type{font-weight: bold;
margin: 5px 0px 0px 5px;
/* border: 1px solid red; */
width: 525px;
height: 95px;
/* text-align: center; */
cursor: pointer;}
	.roller_type{font-weight: bold;
margin: 5px 0px 0px 30px;
/* border: 1px solid red; */
height: 95px;
text-align: center;
width: 240px;
cursor: pointer;}
	.roller_status{margin-left:160px;}
	.packer_status{}
	.group_info{width:240px;text-align:center}
	.group_info span{font-size:28px;font-weight:bold;line-height:50px;}
	.roller_info{width:190px;}
	.center_info{width:150px;text-align:center;}
	.packer_info{width:190px;}
</style>
<script type="text/javascript">
	//加载机组信息
	$.post("${pageContext.request.contextPath}/pms/isp/initFilterGroups.do",function(rows){
		var html = "";
		for(var i=0;i<rows.length;i++){
			//console.info(rows[i].groupName);
			html += getFilterDiv(rows[i]);
		}
		$("#content").html(html);
		initWorkOrderInfo();
	},"JSON");
	//加载机组工单信息
	function initWorkOrderInfo(){
		//type 1：卷烟机工单2：包装机工单3:封箱机工单4:成型机工
		$.post("${pageContext.request.contextPath}/pms/isp/initWorkOrderInfo.do",{type:4},function(rows){
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
	}
	function getFilterDiv(data){
		  var div = "<div class='filter_div'>"+
					"					<div class='filter_img'>"+
					"				<div class='fl packer_type' title='点击查看详情' onclick='gotoDetJsp("+data.code+");' id='type"+data.code+"' name="+data.name+">"+data.type+"</div>"+
					"			</div>"+
					"			<div class='filter_info'>"+
					"				<div>"+
					"					<div  class='fl roller_status'>"+
					"						<img id='status_img"+data.code+"' src='img/cut.gif'/>"+
					"					</div>"+
					"					<div class='fl group_info'>"+
					"						<span>"+data.name+"</span>"+
					"					</div>"+
					"				</div>"+
					"				<div class='cb'>"+
					"					<div class='fl roller_info' id='data1"+data.code+"'>"+
					"						<ul>"+
					"							<li><span>状态：</span><span class='run'></span></li>"+
					"							<li><span>车速：</span><span class='speed'>0</span>&nbsp;<span>包/分钟</span></li>"+
					"							<li><span>产量：</span><span class='qty'>0</span>&nbsp;<span>箱</span></li>"+
					"						</ul>"+
					"					</div>"+
					"					<div class='fl center_info'>"+
					"						<ul>"+
					"							<li><span id='teamName"+data.code+"'>-</span></li>"+
					"							<li><span id='matName"+data.code+"'>-</span></li>"+
					"							<li><span style='font-size:12px'>计划产量:</span><span id='planQty"+data.code+"'>0</span>&nbsp;<span style='font-size:12px'>万支</span></li>"+
					"						</ul>"+
					"					</div>"+
					"					<div class='fl packer_info' id='data2"+data.code+"'>"+
					"						<ul>"+
					"							<li><span>运行时间：</span><span class='runTime'>0</span>&nbsp;<span>分</span></li>"+
					"							<li><span>停机时间：</span><span class='stopTime'>0</span>&nbsp;<span>分</span></li>"+
					"							<li><span>停机次数：</span><span class='stopTimes'>0</span>&nbsp;<span>次</span></li>"+
					"						</ul>"+
					"					</div>"+
					"				</div>"+
					"			</div>"+
					"	</div> ";
			return div;
	}
	function gotoDetJsp(code){
		//window.location="${pageContext.request.contextPath}/pms/isp/gotoDetJsp.do?code="+code+"&type="+($("#type"+code).html());
		var type=$("#type"+code).html().toUpperCase();
		var url=type+".jsp";
		window.open("${pageContext.request.contextPath}/pms/isp/"+url+"?code="+code+"&type="+type,"myWindow");
	}
	function getAllFilterDatas(){
		$.post("${pageContext.request.contextPath}/pms/isp/getAllFilterDatas.do",function(datas){
			//console.info(JSON.stringify(datas));
			for(var i=0;i<datas.length;i++){
				var data = datas[i];
				var code= data.code;
				var online = data.online;
				var speed = data.speed;
				var runTime = data.runTime;
				var stopTime = data.stopTime;
				var stopTimes = data.speed;
				var qty = data.qty;
				$("#data1"+code+" .run").html(getRunStatus(code,online,speed));
				$("#data1"+code+" .qty").html(qty);
				$("#data1"+code+" .speed").html(speed);
				$("#data2"+code+" .runTime").html(runTime);
				$("#data2"+code+" .stopTime").html(stopTime);
				$("#data2"+code+" .stopTimes").html(stopTimes);
			}
		},"JSON");
	}
	function getRunStatus(code,online,speed){
		if(online){
			if(speed==0){
				$("#status_img"+code).attr("src","img/stop.gif");
				return "运行停止";
			}else{
				$("#status_img"+code).attr("src","img/run.gif");
				return "运行";
			}
		}else{
			$("#status_img"+code).attr("src","img/cut.gif");
			return "网络断开";
		}
	}
	window.setInterval("getAllFilterDatas()",3000);
	
</script>
</head>
<body>
<div class="info_bar" id="ccc">
	<div class="info_bar_botton" style="padding-left: 6px;">
		<a class="run_button" href="#"></a>
		<a class="stop_button" href="#"></a>
		<a class="interrupt_button" href="#"></a>		
	</div>
</div>
<div id="content">
<!-- 	<div class='filter_div'>
			<div class='filter_img'>
				<div class='fl packer_type' title="点击查看详情">ZB45</div>
			</div>
			<div class='filter_info'>
				<div>
					<div  class='fl roller_status'>
						<img src='img/cut.gif'/>
					</div>
					<div class='fl group_info'>
						<span>1号卷包机组</span>
					</div>
				</div>
				<div class='cb'>
					<div class='fl roller_info'>
						<ul>
							<li><span>状态：</span><span>运行</span></li>
							<li><span>车速：</span><span>400</span>&nbsp;<span>包/分钟</span></li>
							<li><span>产量：</span><span>40</span>&nbsp;<span>箱</span></li>
						</ul>
					</div>
					<div class='fl center_info'>
						<ul>
							<li><span >班组</span></li>
							<li><span >牌号</span></li>
							<li><span>计划产量:</span><span id='planQty"+data.rCode+"'></span>&nbsp;<span>箱</span></li>
						</ul>
					</div>
					<div class='fl packer_info'>
						<ul>
							<li><span>运行时间：</span><span>700</span>&nbsp;<span>分</span></li>
							<li><span>停机时间：</span><span>700</span>&nbsp;<span>分</span></li>
							<li><span>停机次数：</span><span>40</span>&nbsp;<span>次</span></li>
						</ul>
					</div>
				</div>
			</div>
	</div>  --> 
</div>
</body>
</html>