<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>包装机监控-ZB45</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="上海兰宝数据采集系统,卷接包数据采集" />
<meta name="author" content="leejean" />
<jsp:include page="../../initlib/initAll.jsp"></jsp:include>
<style type="text/css">
*{font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;list-style-type :none;padding:0px;margin:0px;}
	.outIn-tab{margin-left:30px;}
	.outIn-tab tr{height:25px;}
	.outIn-title{width: 140px;text-align: right;font-weight: bold;color: #257cbe;font-size: 14px;float: left;}
	.outIn-value{color: blue;font-weight: bold;float: left;}	
	#error-tab tr{height:30px;}
	.error-flag{width:20px;height:20px;display: block;margin-top: 7px;background:url(${pageContext.request.contextPath}/pms/isp/img/red.gif) no-repeat;background-size: 100%;}
	.normal-flag{width:20px;height:20px;display: block;	background:url(${pageContext.request.contextPath}/pms/isp/img/green.gif) no-repeat;background-size: 100%;}	
	.craft-tab tr{height:25px;}
	.craft-title{float: left;font-size: 14px;width:100px;text-align: right;font-weight: bold;color: #257cbe;}
	.craft-value{float: left;font-size: 14px;width:110px;text-align: left;font-weight: bold;color: blue;}
		.info_box{
		width: 100%;
		height:190px;
		overflow: hidden;
		padding-bottom: 10px;
		min-width: 1315px;
	}
	#tt,.tt_title{min-width:1330px;}
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
	.zb45{
		float: left;
		border: 1px solid #95B8E7;
		margin-top: 10px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		border-radius: 4px;
		background: radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -o-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -ms-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -moz-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		background: -webkit-radial-gradient(center , circle cover , rgb(251, 253, 253) , rgb(211, 236, 236) 95%);
		color: #257cbe;
		width: 44%;
		min-width:596px;
		height: 170px;
	}
	.info-div {
		float: left;
		border: 1px solid #95B8E7;
		width:18%;
		min-width: 240px;
		margin-left: 15px;
		height: 170px;
		margin-top: 10px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		border-radius: 4px;
		background: linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -o-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -ms-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -moz-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		background: -webkit-linear-gradient(top , rgb(222, 244, 250) 8% , rgb(198, 234, 246) 94%);
		color: #257cbe;
	}
	.info-tab{font-size:14px;width:250px;margin-left: 20px;}
	.info-tab tr{height:25px;}
	.tab-val{color:blue;width:65px;}
	.tab-tt{width:90px;font-weight:bold;}
	.info_gy{width: 34.5%;min-width: 458px;float: left;margin-left: 15px;margin-top: 0px;}
	.outIn-tab {padding-left: 8px;border-radius: 4px;border: 1px solid #95B8E7;height: 148px;margin: 0;}
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
			//刷新包装机
			$.post("${pageContext.request.contextPath}/isp/get.do",{"code":code},function(json){
				var data = json.allData;
				var online = json.online;
				if(online==1){
					$("#bzj-yxzt").html("网络正常");
				}else{
					$("#bzj-yxzt").html("网络断开");
				}
				for(var i=0;i<data.length;i++){
					var packerData=data[i];
					var id=packerData.id;
					var val=packerData.val;
					/*
					1	设备ID 2	车速  3	良好烟包产量
					4	剔除烟包 5	运行时间  6	停机时间
					7	停机次数 8	故障信息  9	剔除信息
					*/
					if(id==2){//速度
						if(val!=""&&!isNaN(val)){
							$("#bzj-cur-speech").html(val);
						}
					}
					if(id==3){//产量
						if(val!=""&&!isNaN(val)){
							var out=(val/2500).toFixed(2);
							$("#bzj-clsj").html(out);
						}
						
					}
					if(id==4){
						$("#bzj-tc").html(val);
					}
					 if(id==5){//运行时间
						 if(val!=""){
							 var scd=(((val)/60)+"").replace("-","");//有时前面会有‘-’
								if(parseInt(scd)!=0&&!isNaN(scd)){
									$("#bzj-run-time").html(parseInt(scd));
								}
						 }
					}
					 if(id==6){//停机时间
						 if(val!=""){
							 var scd=((val)/60)+"";//得到分钟
								if(parseInt(scd)!=0&&!isNaN(scd)){
									$("#bzj-stop-time").html(parseInt(scd));
								}
						 }
					}
					 if(id==7){//停机次数
						if(val!=""){																
							$("#bzj-stop-times").html(val);
						}
					}
				}
				
			},"JSON");
			
			//
			
		//刷新辅料
		//辅料数据
			$.post("${pageContext.request.contextPath}/isp/getFormatted.do",{"code":code*1000},function(json){
				var data = json.allData;
				for(var i=0;i<data.length;i++){
					var rollerData=data[i];
					var id=rollerData.id;
					var val=rollerData.val;
					if(id==1){//内衬
					    if(val!=""&&val>0){
						$("#in-nc").html(val);
						//$("#dh-nc").html(numFormat(val/curOut,3));
					   // $("#pg-nc").css("height",caclPercent(numFormat(val/curOut,2),csNCZ,2)-20+"%");
					    }
					}else  if(id==3){//小盒
					    if(val!=""&&val>0){
						$("#in-xh").html(val);
						//$("#dh-xh").html(numFormat(val/curOut,2));
					    //$("#pg-xh").css("height",caclPercent(numFormat(val/curOut,2),csXHZ,2)-20+"%");
					    }
					}else if(id==4){//小透
					    if(val!=""&&val>0){
						$("#in-xt").html(val);
						//$("#dh-xt").html(numFormat(val/curOut,3));
					   // $("#pg-xt").css("height",caclPercent(numFormat(val/curOut,2),csXHM,2)-20+"%");
					    }
					}else if(id==5){//条盒
					    if(val!=""&&val>0){
						$("#in-th").html(val);
					    //$("#dh-th").html(numFormat(val/curOut,3));
					    //$("#pg-th").css("height",caclPercent(numFormat(val/curOut,2),csTHZ,2)-20+"%");
					    }
					}else if(id==6){//大透									
					    if(val!=""&&val>0){
							 $("#in-dt").html(val);
							// $("#dh-dt").html(numFormat(val/curOut,3));
						    // $("#pg-dt").css("height",caclPercent(numFormat(val/curOut,2),csTHM,2)-20+"%");
					    }
					} 
				}
				
			},"JSON");
			
		}
	window.setInterval("loadData()",3000);
</script>

</head>
<body>
<div class="tt_title">---- 实时监控 ----</div>
<div id="tt"  style="width:100%;height:100%;" >  
    <div class="info_box" >
    	<div class="zb45">    	
    	 	<img src="${pageContext.request.contextPath}/pms/isp/img/ZB45.png" style="width:538px;height:169px;margin: 0 auto;display: block;"/>
    	 </div>
    	 <div class="info-div">
    	 	<table class="info-tab">
    	 		<tr>
    	 			<td class="tab-tt">运行状态：</td>
    	 			<td id="bzj-yxzt" colspan="2"></td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
    	 			<td id="bzj-cur-speech" class="tab-val">0</td>
    	 			<td class="unit">包/分</td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">运行时间：</td>
    	 			<td id="bzj-run-time" class="tab-val">0</td>
    	 			<td class="unit">分钟</td>
    	 		</tr>    	 		
    	 		<tr>
    	 			<td class="tab-tt">停机次数：</td>
    	 			<td id="bzj-stop-times" class="tab-val">0</td>
    	 			<td class="unit">次</td>
    	 		</tr>
    	 		<tr>
    	 			<td class="tab-tt">停机时间：</td>
    	 			<td id="bzj-stop-time" class="tab-val">0</td>
    	 			<td class="unit">分钟</td>
    	 		</tr>
    	 	</table>
    	 </div>
    	 <div class="info_gy">
			<h1>工艺数据</h1>
			<div class="outIn-tab">
				<table style="margin-top:15px;line-height: 35px;" class="craft-tab">
					<tr>
						<%-- <td class="craft-title">工艺数据1：</td><td class="craft-value"><span>10</span>单位</td>
						<td class="craft-title">工艺数据2：</td><td class="craft-value"><span>10</span>包</td>
						<td class="craft-title">工艺数据3：</td><td class="craft-value"><span>10</span>单位</td>
						<td class="craft-title">工艺数据4：</td><td class="craft-value"><span>10</span>单位</td>
						<td class="craft-title">工艺数据5：</td><td class="craft-value"><span>10</span>单位</td> --%>
					</tr>
				</table>
			</div>
			<div style="clear:both;"></div>
		</div>  
		 <div style="clear:both;"></div>
    </div> 
    <div>  
	<h1>产耗数据</h1>
			<div style="padding: 10px 0px 0px 30px;line-height:53px;">
			<ul >
				<li style="border-bottom: 1px solid #008597;width: 98%;">
					<div colspan="5" style="text-align:left;font-size:16px;font-weight:bold;color:green;float: left;">产量</div>
					<div class="outIn-title">实时产量：</div><div class="outIn-value"><span id="bzj-clsj">0</span>箱</div>
					<div class="outIn-title">实时剔除：</div><div class="outIn-value"><span id="bzj-tc">0</span>包</div>
					<div class="outIn-title"></div><div class="outIn-value"><span></span></div>
					<div style="clear:both;"></div>
				</li>
				<li>
					<div colspan="5" style="text-align:left;font-size:16px;font-weight:bold;color:#FF9A33;float: left;">消耗</div>
					<div class="outIn-title">小盒消耗：</div><div class="outIn-value"><span id="in-xh">0</span>张</div>
					<div class="outIn-title">小透明纸消耗：</div><div class="outIn-value"><span id="in-xt">0</span>公斤</div>
					<div class="outIn-title">内衬纸消耗：</div><div class="outIn-value"><span id="in-nc">0</span>公斤</div>
					<div class="outIn-title">条盒消耗：</div><div class="outIn-value"><span id="in-th">0</span>张</div>
					<div class="outIn-title">大透明纸消耗：</div><div class="outIn-value"><span id="in-dt">0</span>公斤</div>
					<div style="clear:both;"></div>
				</li>
				<div style="clear:both;"></div>
			</ul>
			</div>
    </div>
       <%--  <div class="info_info" >  
		<h1>INFO信息展示</h1>
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
    <div style="border-bottom: 1px solid #257cbe;"> 
		<h1>故障报警数据</h1>
		<div style=" padding: 18px 10px 18px 35px;">
			<div style="width:20%;height: 100px;float: left;">
				<div ><span class="normal-flag"></span>正常</div>
				<div ><span class="error-flag"></span>异常</div>
				<div style="clear:both;"></div>
				</div>
			<div style="width:79%;height: 100px;float: left;">			
			<table id="error-tab">
				<tr>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">取样伺服通讯</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">提升伺服通讯</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">斜向伺服通讯</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">存储伺服通讯</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">取样入口堵塞报警</td>
				</tr>
				<tr>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">提升入口堵塞报警</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">下降口堵塞报警</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">提升过载堵塞报警</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">存储器过载报警</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">存储入口堵塞报警</td>
				</tr>
				<tr>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">存储极限报警</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">存储器满报警</td>
					<td class="error-flag"><div id="e1"></div></td><td style="width:170px;">编码器报警/故障</td>
				</tr>
			</table>
			</div>
			<div style="clear:both;"></div> 
	    </div> 
	    <div style="clear:both;"></div>
    </div>  --%>
</div>

</body>
</html>