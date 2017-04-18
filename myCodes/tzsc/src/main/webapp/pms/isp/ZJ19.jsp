<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>卷烟机监控-ZJ19</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="上海兰宝数据采集系统,卷接包数据采集" />
<meta name="author" content="leejean" />
<jsp:include page="../../initlib/initAll.jsp"></jsp:include>
<style type="text/css">
	*{font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;list-style-type :none;padding:0px;margin:0px;}
	#error-tab tr{height:30px;}
	.error-flag{width:20px;height:20px;display: block;margin-top: 7px;background:url(${pageContext.request.contextPath}/pms/isp/img/red.gif) no-repeat;background-size: 100%;}
	.normal-flag{width:20px;height:20px;display: block;	background:url(${pageContext.request.contextPath}/pms/isp/img/green.gif) no-repeat;background-size: 100%;}
	
	.error-base{width:20px;height:20px;display: block;margin-top: 7px;background-size: 100%;}
	.info-div{
		float:left;
		border: 1px solid #95B8E7;
		width:20%;
		min-width:240px;
		margin-left:15px;
		height:200px;
		margin-top:10px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		
		border-radius: 4px;
		background: linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -o-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -ms-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -moz-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -webkit-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		color:#257cbe;
	}
	.info-tab{font-size:14px;width:250px;margin-left: 20px;}
	.info-tab tr{height:25px;}
	.tab-val{color:blue;width:70px;}
	.tab-tt{width:90px;font-weight:bold;}
	.info_box{
		width: 100%;
		height: 215px;
		overflow: hidden;
		padding-bottom: 10px;
		min-width: 1315px;
	}
	h1 {
		margin: 10px 0;
		font-size: 16px;
		font-weight: bold;
		text-align: center;
		line-height: 26px;
		border: 1px solid #95B8E7;
		border-bottom: 1px solid #008597;
		background: linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -o-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -ms-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -moz-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -webkit-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		color: #257cbe;
		border-radius: 4px 4px 0px 0px;
		margin-bottom: 2px;
	}
	.info_ch{
		width:53%;
		min-width: 615px;
		float: left;
		margin-left: 15px;
		margin-top: 0px;		
	}
	.outIn-tab{padding-left: 8px;border-radius: 4px;border: 1px solid #95B8E7;height: 178px;margin: 0;}
	.outIn-tab tr{height:25px;}
	.outIn-title{float: left;font-size: 14px;width:85px;text-align:right;font-weight:bold;color:#257cbe;}
	.outIn-value{float: left;font-size: 14px;width:70px;text-align:left;font-weight:bold;color:blue;}
	.info_gy{
		width: 100%;
		height: 120px;
		overflow: hidden;
		padding-bottom: 10px;
		min-width: 1330px;
	}
	.craft-tab tr{height:25px;}
	.craft-title{width:140px;text-align:right;font-weight:bold;color: #257cbe;font-size: 14px;}
	.craft-value{color:blue;font-weight: bold;}
	.info_gz{
		width: 100%;
		height: 160px;
		overflow: hidden;
		padding-bottom: 10px;
		border-bottom: 1px solid #257cbe;
		min-width: 1330px;
	}
		.info_cis{
		width: 100%;
		height: 150px;
		overflow: hidden;
		padding-bottom: 10px;		
		min-width: 1330px;
	}
		.info_srm{
		width: 100%;
		height: 120px;
		overflow: hidden;
		padding-bottom: 10px;
		min-width: 1330px;
	}
	.zj17{
		float:left;
		border: 1px solid #95B8E7;		
				
		margin-top:10px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		
		border-radius: 4px;
		background: radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -o-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -ms-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -moz-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -webkit-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		color:#257cbe;
		width: 24%;min-width:325px;height:200px;
	}
	.tt_title{
		margin: 10px 0;
		font-size: 18px;
		font-weight: bold;
		text-align: center;
		line-height: 38px;
		border: 1px solid #95B8E7;
		border-bottom: 2px solid #008597;
		background-color: #E0ECFF;
		background: -webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
		background: -moz-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
		background: -o-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
		background: linear-gradient(to bottom,#EFF5FF 0,#E0ECFF 100%);
		background-repeat: repeat-x;
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
		border-color: #95B8E7;
		color: #299DD3;
		border-radius: 4px 4px 0px 0px;
		margin-bottom: 2px;
	}
	#tt,.tt_title{min-width:1330px;}
</style>
<script type="text/javascript">
	function getArgsFromHref(sArgName) 
	{ 
		var sHref = window.location.href; 
		//CuPlayer.com提示：测试数据 
		//实际情况是用window.location.href得到URL 
		var args = sHref.split("?"); 
		var retval = ""; 
		if(args[0] == sHref) /*参数为空*/ 
		{ 
			return retval; /*CuPlayer.com提示：无需做任何处理*/ 
		} 
		var str = args[1]; 
		args = str.split("&"); 
		for(var i = 0; i < args.length; i++ ) 
		{ 
		str = args[i]; 
		var arg = str.split("="); 
		if(arg.length <= 1) continue; 
		if(arg[0] == sArgName) retval = arg[1]; 
		} 
		return retval; 
	}
	var code = getArgsFromHref("code");
	function loadData(){
		var error={"width":"20px","height":"20px","display":" block","margin-top":" 7px","background":"url(${pageContext.request.contextPath}/pms/isp/img/red.gif) no-repeat","background-size":" 100%"}
		var normal={"width":"20px","height":"20px","display":" block","margin-top":" 7px","background":"url(${pageContext.request.contextPath}/pms/isp/img/green.gif) no-repeat","background-size":" 100%"}
			//刷新卷烟机
			$.post("${pageContext.request.contextPath}/isp/get.do",{"code":code},function(json){
				var online = json.online;
				if(online==1){
					$("#jyj-yxzt").html("网络正常");
				}else{
					$("#jyj-yxzt").html("网络断开");
				}
				var shift = json.shift;
				var data = json.allData;
				console.info(online);
				console.info(shift);
				console.info(JSON.stringify(data));
				for(var i=0;i<data.length;i++){
					var rollerData=data[i];
					var id=rollerData.id;
					var val=rollerData.val;
					//5开机时间
					if(id==5){
						if(val!=""){									
							$("#jyj-sjkssj").html(val);
						}
					}
					//7产量
					if(id==7){
						if(val!=""&&!isNaN(val)){
							var out=parseInt(val)/50;
							$("#jyj-clsj").html(out);												
						}
					}
					if(id==8){
						if(val!=""&&!isNaN(val)){
							var lb=parseFloat(val/10000).toFixed(2);
							$("#lb-in").html(lb);												
						}
					}
					//11废品总支数
					if(id==11){
						if(val!=""&&!isNaN(val)){
							$("#jyj-tichu").html(val);												
						}
					}
					if(id==15){//15车速
						if(val!=""&&!isNaN(val)){
							$("#jyj-cur-speech").html(val);
							
						}
					}
					if(id==16){//16总运行时间HH:NN
						var runtime=val;
						if((runtime+"")!=""){	
							//当前运行分钟
							$("#cur-run-time").html(parseFloat(runtime).toFixed(0));
						}
					}
					if(id==18){//18停机时间HH:NN
						var stoptime=val;
						if(stoptime!=""){
							$("#jyj-stop-time").html(parseFloat(stoptime).toFixed(0));
						}
					}
					if(id==19){//19停机次数
						var stops=val;
						if((stops+"")!=""){									
							$("#jyj-stop-times").html(stops);
						}
					}
					//报警
					/*
					115	胶枪温度
					116	胶枪设定温度40
					117	烙铁1温度
					118	烙铁1设定温度220
					119	烙铁2温度
					120	烙铁2设定温度230
					121	搓板温度
					122	搓板设定温度150
					123	水松纸温度
					124	水松纸设定温度50
					           浮动值超过20度报警
					*/
					if(id==115){
						var v=val;
						if(v!=""){//    60-20
							if(parseFloat(v)>60){
								$("#jjwd1").css(error);
								$("#jjwd2").css(normal);
							}else if(parseFloat(v)<20){
								$("#jjwd1").css(normal);
								$("#jjwd2").css(error);
							}else{
								$("#jjwd1").css(normal);
								$("#jjwd2").css(normal);
							}
						}
					}
					if(id==117){
						var v=val;
						if(v!=""){//   200-240
							if(parseFloat(v)>240){
								$("#lt11").css(error);
								$("#lt12").css(normal);
							}else if(parseFloat(v)<200){
								$("#lt11").css(normal);
								$("#lt12").css(error);
							}else{
								$("#lt11").css(normal);
								$("#lt12").css(normal);
							}
						}
					}							
					if(id==119){
						var v=val;
						if(v!=""){//   210-250
							if(parseFloat(v)>250){
								$("#lt21").css(error);
								$("#lt22").css(normal);
							}else if(parseFloat(v)<210){
								$("#lt21").css(normal);
								$("#lt22").css(error);
							}else{
								$("#lt21").css(normal);
								$("#lt22").css(normal);
							}
						}
					}
					if(id==121){
						var v=val;
						if(v!=""){//   130-170
							if(parseFloat(v)>170){
								$("#cb1").css(error);
								$("#cb2").css(normal);
							}else if(parseFloat(v)<130){
								$("#cb1").css(normal);
								$("#cb2").css(error);
							}else{
								$("#cb1").css(normal);
								$("#cb2").css(normal);
							}
						}
					}
					if(id==123){
						var v=val;
						if(v!=""){//   30-70
							if(parseFloat(v)>70){
								$("#ssz1").css(error);
								$("#ssz2").css(normal);
							}else if(parseFloat(v)<30){
								$("#ssz1").css(normal);
								$("#ssz2").css(error);
							}else{
								$("#ssz1").css(normal);
								$("#ssz2").css(normal);
							}
						}
					}
					//23	当前重量偏差
					if(id==23){
						if(val!=""){
							$("#val23").html(val);
						}
					}
					//24	当前重量偏移
					if(id==24){
						if(val!=""){
							$("#val24").html(val);
						}
					}
					//25	当前短期标准偏差
					if(id==25){
						if(val!=""){
							$("#val25").html(val);
						}
					}
					//26	当前长期标准偏差
					if(id==26){
						if(val!=""){
							$("#val26").html(val);
						}
					}
					//27	当前压实端位置
					if(id==27){
						if(val!=""){
							$("#val27").html(val);
						}
					}
					//28	当前压实量
					if(id==28){
						if(val!=""){
							$("#val28").html(val);
						}
					}
					//29	当前平整盘位置
					if(id==29){
						if(val!=""){
							$("#val29").html(val);
						}
					}
					//30	平均重量偏差
					if(id==30){
						if(val!=""){
							$("#val30").html(val);
						}
					}
					//31	平均重量偏移
					if(id==31){
						if(val!=""){
							$("#val31").html(val);
						}
					}
					//32	平均短期标准偏差
					if(id==32){
						if(val!=""){
							$("#val32").html(val);
						}
					}
					//33	平均长期标准偏差
					if(id==33){
						if(val!=""){
							$("#val33").html(val);
						}
					}
					//34	平均压实端位置
					if(id==34){
						if(val!=""){
							$("#val34").html(val);
						}
					}
					//35	平均压实量
					if(id==35){
						if(val!=""){
							$("#val35").html(val);
						}
					}
					//36	平均平整盘位置
					if(id==36){
						if(val!=""){
							$("#val36").html(val);
						}
					}
					

					//SRM废品报告
					//37	当前过轻
					if(id==37){
						if(val!=""){
							$("#val37").html(val);
						}
					}
					//38	当前过重
					if(id==38){
						if(val!=""){
							$("#val38").html(val);
						}
					}
					//39	当前软点
					if(id==39){
						if(val!=""){
							$("#val").html(val);
						}
					}
					//40	当前硬点
					if(id==40){
						if(val!=""){
							$("#val40").html(val);
						}
					}
					//41	当前轻烟端
					if(id==41){
						if(val!=""){
							$("#val41").html(val);
						}
					}
					//45	当前SRM取样
					if(id==45){
						if(val!=""){
							$("#val45").html(val);
						}
					}
					//46	平均过轻
					if(id==46){
						if(val!=""){
							$("#val46").html(val);
						}
					}
					//47	平均过重
					if(id==47){
						if(val!=""){
							$("#val47").html(val);
						}
					}
					//48	平均软点
					if(id==48){
						if(val!=""){
							$("#val48").html(val);
						}
					}
					//49	平均硬点
					if(id==49){
						if(val!=""){
							$("#val49").html(val);
						}
					}
					//50	平均轻烟端
					if(id==50){
						if(val!=""){
							$("#val50").html(val);
						}
					}
					//54	平均SRM取样
					if(id==54){
						if(val!=""){
							$("#val54").html(val);
						}
					}
					////CIS废品报告
					
					//57	当前松头
					if(id==57){
						if(val!=""){
							$("#val57").html(val);
						}
					}
					//58	当前缺嘴
					if(id==58){
						if(val!=""){
							$("#val58").html(val);
						}
					}
					//58	当前手动剔除
					if(id==58){
						if(val!=""){
							$("#val58").html(val);
						}
					}
					//60	当前OTIS
					if(id==60){
						if(val!=""){
							$("#val60").html(val);
						}
					}
					//61	当前水松纸缺陷
					if(id==61){
						if(val!=""){
							$("#val61").html(val);
						}
					}
					//62	平均漏气
					if(id==62){
						if(val!=""){
							$("#val62").html(val);
						}
					}
					//64	平均松头
					if(id==64){
						if(val!=""){
							$("#val64").html(val);
						}
					}
					//65	平均缺嘴
					if(id==65){
						if(val!=""){
							$("#val65").html(val);
						}
					}
					//66	平均手动剔除
					if(id==66){
						if(val!=""){
							$("#val66").html(val);
						}
					}
					//67	平均OTIS
					if(id==67){
						if(val!=""){
							$("#val67").html(val);
						}
					}
					//68	平均水松纸缺陷
					if(id==68){
						if(val!=""){
							$("#val68").html(val);
						}
					}
					//69	过轻支数
					if(id==69){
						if(val!=""){
							$("#val69").html(val);
						}
					}
					//70	过重支数
					if(id==70){
						if(val!=""){
							$("#val70").html(val);
						}
					}
					//71	软点支数
					if(id==71){
						if(val!=""){
							$("#val71").html(val);
						}
					}
					//72	硬点支数
					if(id==72){
						if(val!=""){
							$("#val72").html(val);
						}
					}
					//73	轻烟端支数
					if(id==73){
						if(val!=""){
							$("#val73").html(val);
						}
					}
					//77	SRM取样支数
					if(id==77){
						if(val!=""){
							$("#val77").html(val);
						}
					}
					//78	漏气支数
					if(id==78){
						if(val!=""){
							$("#val78").html(val);
						}
					}
					//80	松头支数
					if(id==80){
						if(val!=""){
							$("#val80").html(val);
						}
					}
					//81	缺嘴支数
					if(id==81){
						if(val!=""){
							$("#val81").html(val);
						}
					}
					//82	手动剔除支数
					if(id==82){
						if(val!=""){
							$("#val82").html(val);
						}
					}
				}
				
			},"JSON");
			
			//
			
		//刷新卷烟机辅料
		/* $.post("${pageContext.request.contextPath}/pubEqp/getRuntimeData.action?equipmentId="+(rid*1000),
				function(x){
				if(x.success){
					if(x.eqpData!=null&&x.eqpData.allData!=null){
						var pdata=x.eqpData.allData;
						for(var i=0;i<pdata.length;i++){
							var rollerData=pdata[i];
							var id=rollerData.id;
							var val=rollerData.val;
							if(id==2){//盘纸
							    if(val!=""&&parseInt(val)!=0){
								$("#pz-in").html(parseFloat(val/10000).toFixed(2));
							    }
							}
							 if(id==3){//水松纸
							    if(val!=""&&val!=0){
								$("#ssz-in").html(val);
							    }
							}
						}
					}
				}
				
			}); */
			
		}
	window.setInterval("loadData()",3000);
</script>

</head>
<body>
<div class="tt_title">---- <span id="eqpName"></span>实时监控 ----</div>
<div id="tt"  style="width:100%;height:100%;" >  
    <div class="info_box" >
    	 <div class="zj17">
    	 	<img src="${pageContext.request.contextPath}/pms/isp/img/ZJ17.png" style="width:303px;height:200px;margin: 0 auto;display: block;"/>
    	 </div>
    	 <div class="info-div">
    	 	<table class="info-tab">
    	 		<tr>
    	 			<td class="tab-tt">运行状态：</td>
    	 			<td id="jyj-yxzt" colspan="2"></td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">开机时间：</td>
    	 			<td id="jyj-sjkssj" colspan="2"></td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
    	 			<td id="jyj-cur-speech" class='tab-val' >0</td>
    	 			<td class="unit">支/分</td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">运行时间：</td>
    	 			<td id="cur-run-time" class='tab-val' >0</td>
    	 			<td class="unit">分钟</td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">停机次数：</td>
    	 			<td id="jyj-stop-times" class='tab-val' >0</td>
    	 			<td class="unit">次</td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">停机时间：</td>
    	 			<td id="jyj-stop-time" class='tab-val' >0</td>
    	 			<td class="unit">分钟</td>
    	 		</tr>
    	 	</table>
    	 </div>
    	     <div class="info_ch">  
			<h1>产耗数据</h1>
			<ul class="outIn-tab">
				<li style="border-bottom: 1px solid #008597;width: 98%;">
					<div style="text-align:left;font-size: 16px;font-weight:bold;color:green;line-height: 39px;margin-left: 10px;">产量</div>
					<div style="line-height:50px;">
					<div class="outIn-title">实时产量：</div><div class="outIn-value"><span id="jyj-clsj"></span>箱</div>
					<div class="outIn-title">实时剔除：</div><div class="outIn-value"><span id="jyj-tichu"></span>支</div>
					<div style="clear:both;"></div>
					</div>
				</li>
				<li>
					<div style="text-align:left;font-size: 16px;font-weight:bold;line-height: 39px;margin-left: 10px;color:#FF9A33">消耗</div>
					<div style="line-height: 25px;">
					<div class="outIn-title">滤棒消耗：</div><div class="outIn-value"><span id="lb-in">0</span>万支</div>
					<div class="outIn-title">盘纸消耗：</div><div class="outIn-value"><span id="pz-in">0</span>万米</div>
					<div class="outIn-title">水松纸消耗：</div><div class="outIn-value"><span id="ssz-in">0</span>公斤</div>
					<div style="clear:both;"></div>
					</div>
				</li>
			</ul>
		</div> 
		<div style="clear:both;"></div>
    </div>  
   <!--  <div title="质检数据" >
		<iframe src="http://localhost:8080/GZDAMS//pmsIspLink/linkto.action?eqpid=1&towhere=1"></iframe>
    </div>   -->

    <div class="info_gy" >  
		<h1>工艺数据</h1>
     	<div style="padding: 5px 15px;">
			<table style="width: 100%;" class="craft-tab">
				<tr>
					<td class="craft-title">当前重量偏差：</td><td class="craft-value"><span id="val23">0</span></td>
					<td class="craft-title">当前重量偏移：</td><td class="craft-value"><span id="val24">0</span></td>
					<td class="craft-title">当前短期标准偏差：</td><td class="craft-value"><span id="val25">0</span></td>
					<td class="craft-title">当前长期标准偏差：</td><td class="craft-value"><span id="val26">0</span></td>
					<td class="craft-title">当前压实端位置：</td><td class="craft-value" id="val27">0</td>
				</tr>
				<tr>
					<td class="craft-title">当前压实量：</td><td class="craft-value"><span id="val28">0</span></td>
					<td class="craft-title">当前平整盘位置：</td><td class="craft-value"><span id="val29">0</span></td>
					<td class="craft-title">平均重量偏差：</td><td class="craft-value"><span id="val30">0</span></td>
					<td class="craft-title">平均重量偏移：</td><td class="craft-value"><span id="val31">0</span></td>
					<td class="craft-title">平均短期标准偏差：</td><td class="craft-value" id="val32">0</td>
				</tr>
				<tr>
					<td class="craft-title">平均长期标准偏差：</td><td class="craft-value"><span id="val33">0</span></td>
					<td class="craft-title">平均压实端位置：</td><td class="craft-value"><span id="val34">0</span></td>
					<td class="craft-title">平均压实量：</td><td class="craft-value"><span id="val35">0</span></td>
					<td class="craft-title">平均平整盘位置：</td><td class="craft-value" id="val36"><span>0</span></td>
					<td class="craft-title"></td><td class="craft-value"></td>
				</tr>
			</table>
		</div>
    </div>
    <!-- 
    	37	当前过轻
		38	当前过重
		39	当前软点
		40	当前硬点
		41	当前轻烟端
		42
		43
		44
		45	当前SRM取样
		
		46	平均过轻
		47	平均过重
		48	平均软点
		49	平均硬点
		50	平均轻烟端
		51
		52
		53
		54	平均SRM取样

     -->
    <div class="info_srm" >  
		<h1>SRM废品报告</h1>
     	<div style="padding: 5px 15px;">
			<table style="width: 100%;" class="craft-tab">
				<tr>
					<td class="craft-title">当前过轻：</td><td class="craft-value"><span id="val37">0</span></td>
					<td class="craft-title">当前过重：</td><td class="craft-value"><span id="val38">0</span></td>
					<td class="craft-title">当前软点：</td><td class="craft-value"><span id="val39">0</span></td>
					<td class="craft-title">当前硬点：</td><td class="craft-value"><span id="val40">0</span></td>
					<td class="craft-title">当前轻烟端：</td><td class="craft-value" id="val41">0</td>
				</tr>
				<tr>
					<td class="craft-title">平均过轻：</td><td class="craft-value"><span id="val46">0</span></td>
					<td class="craft-title">平均过重：</td><td class="craft-value"><span id="va47">0</span></td>
					<td class="craft-title">平均软点：</td><td class="craft-value"><span id="val48">0</span></td>
					<td class="craft-title">平均软点：</td><td class="craft-value"><span id="val49">0</span></td>
					<td class="craft-title">平均轻烟端：</td><td class="craft-value" id="val50">0</td>
				</tr>
				<tr>
					<td class="craft-title">当前SRM取样：</td><td class="craft-value"><span id="val45">0</span></td>
					<td class="craft-title">平均SRM取样：</td><td class="craft-value"><span id="val54">0</span></td>
					<td class="craft-title"></td><td class="craft-value"><span id=""></span></td>
					<td class="craft-title"></td><td class="craft-value" id=""><span></span></td>
					<td class="craft-title"></td><td class="craft-value"></td>
				</tr>
			</table>
		</div>
    </div>
     <!-- 
		57	当前松头
		58	当前缺嘴
		59	当前手动剔除
		60	当前OTIS
		61	当前水松纸缺陷
		
		62	平均漏气
		63
		64	平均松头
		65	平均缺嘴
		66	平均手动剔除
		67	平均OTIS
		
		68	平均水松纸缺陷
		69	过轻支数
		70	过重支数
		71	软点支数
		72	硬点支数
		
		73	轻烟端支数
		74
		75
		76
		77	SRM取样支数
		78	漏气支数
		79
		80	松头支数
		81	缺嘴支数
		     
      -->
    <div class="info_cis" >  
		<h1>CIS废品报告</h1>
     	<div style="padding: 5px 15px;">
			<table style="width: 100%;" class="craft-tab">
				<tr>
					<td class="craft-title">当前松头：</td><td class="craft-value"><span id="val57">0</span></td>
					<td class="craft-title">当前缺嘴：</td><td class="craft-value"><span id="val58">0</span></td>
					<td class="craft-title">当前手动剔除：</td><td class="craft-value"><span id="val59">0</span></td>
					<td class="craft-title">当前OTIS：</td><td class="craft-value"><span id="val60">0</span></td>
					<td class="craft-title">当前水松纸缺陷：</td><td class="craft-value" id="val61">0</td>
				</tr>
				<tr>
					<td class="craft-title">平均漏气：</td><td class="craft-value"><span id="val62">0</span></td>
					<td class="craft-title">平均松头：</td><td class="craft-value"><span id="val64">0</span></td>
					<td class="craft-title">平均缺嘴：</td><td class="craft-value"><span id="val65">0</span></td>
					<td class="craft-title">平均手动剔除：</td><td class="craft-value"><span id="val66">0</span></td>
					<td class="craft-title">平均OTIS：</td><td class="craft-value"><span id="val67">0</span></td>
				</tr>
				<tr>
					<td class="craft-title">平均水松纸缺陷：</td><td class="craft-value" id="val68">0</td>
					<td class="craft-title">过轻支数：</td><td class="craft-value"><span id="val69">0</span></td>
					<td class="craft-title">过重支数：</td><td class="craft-value"><span id="val70">0</span></td>
					<td class="craft-title">软点支数：</td><td class="craft-value"><span id="val71">0</span></td>
					<td class="craft-title">硬点支数：</td><td class="craft-value" id="val72"><span>0</span></td>
				</tr>
                <tr>
					<td class="craft-title">轻烟端支数：</td><td class="craft-value" id="val73">0</td>
					<td class="craft-title">SRM取样支数：</td><td class="craft-value"><span id="val77">0</span></td>
					<td class="craft-title">漏气支数：</td><td class="craft-value"><span id="val78">0</span></td>
					<td class="craft-title">松头支数：</td><td class="craft-value"><span id="val80">0</span></td>
					<td class="craft-title">缺嘴支数：</td><td class="craft-value" id="val81"><span>0</span></td>
				</tr>
			</table>
		</div>
    </div> 
    <div class="info_gz" > 
		<h1>故障报警数据</h1>
    	<div style=" padding: 18px 10px 10px 35px;">
				<div style="width:20%;height: 100px;float: left;">
				<div ><span class="normal-flag"></span>正常</div>
				<div ><span class="error-flag"></span>异常</div>
				<div style="clear:both;"></div>
				</div>
				<div style="width:79%;height: 100px;float: left;">				
				<table style="margin-top:5px;" id="error-tab">
					<tr>
						<td ><div class="normal-flag" id="jjwd1" style="width:20px;height:20px;"></div></td><td style="width:170px;">胶枪温度过高</td>
						<td ><div class="normal-flag" id="lt11"></div></td><td style="width:170px;">烙铁1温度过高</td>
						<td ><div class="normal-flag" id="lt21"></div></td><td style="width:170px;">烙铁2温度过高</td>
						<td ><div class="normal-flag" id="cb1"></div></td><td style="width:170px;">搓板温度过高</td>
						<td ><div class="normal-flag" id="ssz1"></div></td><td style="width:170px;">水松纸温度过高</td>
					</tr>
					<tr>
						<td ><div class="normal-flag" id="jjwd2"></div></td><td style="width:170px;">胶枪温度偏低</td>
						<td ><div class="normal-flag" id="lt12"></div></td><td style="width:170px;">烙铁1温度偏低</td>
						<td ><div class="normal-flag" id="lt22"></div></td><td style="width:170px;">烙铁2温度偏低</td>
						<td ><div class="normal-flag" id="cb2"></div></td><td style="width:170px;">搓板温度偏低</td>
						<td ><div class="normal-flag" id="ssz2"></div></td><td style="width:170px;">水松纸温度偏低</td>
					</tr>
				</table>
				<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
		</div> 
    </div>
</div>

</body>
</html>