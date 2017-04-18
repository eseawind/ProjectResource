<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>条烟输送监控</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="上海兰宝数据采集系统,卷接包数据采集" />
<meta name="author" content="leejean" />
<jsp:include page="../../initlib/initAll.jsp"></jsp:include>
<style type="text/css">
	*{font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;list-style-type :none;padding:0px;margin:0px;}
	.tab tr{height:30px;}
	/* .tab tr td{width:130px;} */
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
	.tip{width:20px;height:20px;margin:13px 0px 10px 2px;float:left;}
	.tip2{width:40px;margin:10px 0px 10px 2px;float:left;}	
	.outline{background:url(${pageContext.request.contextPath}/pms/isp/img/red.gif) no-repeat;background-size: 100%;}
	.stop{background:url(${pageContext.request.contextPath}/pms/isp/img/green.gif) no-repeat;background-size: 100%;}
	.run{background:url(${pageContext.request.contextPath}/pms/isp/img/red.gif) no-repeat;background-size: 100%;}
	.wait{filter:invert;background:url(${pageContext.request.contextPath}/pms/isp/img/green.gif) no-repeat;background-size: 100%;}
	.craft-val{color:#0000FF;font-size:18px;font-weight:bold;}
</style>
<script type="text/javascript">
	var run={"width":"20px","height":"20px","display":" block","margin-top":" 7px","background":"url(${pageContext.request.contextPath}/pms/isp/img/green.gif) no-repeat","background-size":" 100%"}
	var stop={"width":"20px","height":"20px","display":" block","margin-top":" 7px","background":"url(${pageContext.request.contextPath}/pms/isp/img/red.gif) no-repeat","background-size":" 100%"}
	var outline={"filter":"gray","width":"20px","height":"20px","display":" block","margin-top":" 7px","background":"url(${pageContext.request.contextPath}/pms/isp/img/green.gif) no-repeat","background-size":" 100%"}
	var wait={"filter":"gray","width":"20px","height":"20px","display":" block","margin-top":" 7px","background":"url(${pageContext.request.contextPath}/pms/isp/img/red.gif) no-repeat","background-size":" 100%"}
	function loadData(){
				$.post("${pageContext.request.contextPath}/isp/getFormatted.do",
					   {"code":151},function(json){
				var online = json.online;
				var shift = json.shift;
				var data = json.allData;
				for(var i=1;i<data.length;i++){
					var id=data[i].id;
					var val=data[i].val;
					var type = data[i].returnType;
					var targetDom = $("#v"+id);
					if(type=="int"){
						//状态（0为脱网，1为停机，2为运行，3为候机）
						if(val=="0"){
							targetDom.css(outline);
						}
						if(val=="1"){
							targetDom.css(stop);
						}
						if(val=="2"){
							targetDom.css(run);
						}
						if(val=="3"){
							targetDom.css(wait);
						} 
						
					}else{						
						targetDom.html(val);
					}
					var tishengji=0;
					var paibaoji=0;
					$(".tishengji").each(function(i){
						tishengji+=parseInt($(this).html());
					});
					$(".paibaoji").each(function(i){
						paibaoji+=parseInt($(this).html());
					});
					$("#tishengji").html(tishengji);
					$("#paibaoji").html(paibaoji);
				}
			},"JSON");
	}
	window.setInterval("loadData()",3000);
</script>

</head>
<body>
<div class="tt_title">条烟输送实时监控</div>
<div id="tt"  style="width:100%;height:100%;" >  
    <div class="info_gy" style="height:30px">  
    		<!-- （0为脱网，1为停机，2为运行，4为候机） -->
			<div class="tip outline" style="margin-left:20px;"></div><div class="tip2">脱网</div>
			<div class="tip stop"></div><div class="tip2">停机</div>
			<div class="tip run"></div><div class="tip2">运行</div>
			<div class="tip wait"></div><div class="tip2">候机</div>
			[目前只有红绿两色图标，待PS后替换]
			<div style="float:right;margin:10px 20px 10px 2px;">
				<span class="craft-title">提升机总产量:</span>
				<span class="craft-val" id="tishengji">0</span>
				<span class="craft-title">&nbsp;条</span>
				&nbsp;&nbsp;&nbsp;
				<span class="craft-title">排包机总产量:</span>
				<span class="craft-val" id="paibaoji">0</span>
				<span class="craft-title">&nbsp;条</span>
			</div>
			
	</div>	
    <div class="info_gy" >  
		<h1>提升机产量</h1>
     	<div style="padding: 5px 15px;">
			<table style="width: 100%;" class="craft-tab">
				<tr>
					<td class="craft-title">01#提升机：</td><td class="craft-value"><span id="v43" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">02#提升机：</td><td class="craft-value"><span id="v44" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">03#提升机：</td><td class="craft-value"><span id="v45" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">04#提升机：</td><td class="craft-value"><span id="v46" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">05#提升机：</td><td class="craft-value"><span id="v47" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">06#提升机：</td><td class="craft-value"><span id="v48" class="tishengji">0</span>&nbsp;条</td>
				</tr>
				<tr>
					<td class="craft-title">07#提升机：</td><td class="craft-value"><span id="v49" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">08#提升机：</td><td class="craft-value"><span id="v50" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">09#提升机：</td><td class="craft-value"><span id="v51" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">10#提升机：</td><td class="craft-value"><span id="v52" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">11#提升机：</td><td class="craft-value"><span id="v53" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">12#提升机：</td><td class="craft-value"><span id="v54" class="tishengji">0</span>&nbsp;条</td>
				</tr>
				<tr>
					<td class="craft-title">13#提升机：</td><td class="craft-value"><span id="v55" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">14#提升机：</td><td class="craft-value"><span id="v56" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">15#提升机：</td><td class="craft-value"><span id="v57" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">16#提升机：</td><td class="craft-value"><span id="v58" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">17#提升机：</td><td class="craft-value"><span id="v59" class="tishengji">0</span>&nbsp;条</td>
					<td class="craft-title">18#提升机：</td><td class="craft-value"><span id="v60" class="tishengji">0</span>&nbsp;条</td>
				</tr>
			</table>
		</div>
    </div>
     <div class="info_gy" style="height:70px">  
		<h1>排包机产量</h1>
     	<div style="padding: 5px 15px;">
			<table style="width: 100%;" class="craft-tab">
				<tr>
					<td class="craft-title">01#排包机：</td><td class="craft-value"><span id="v61" class="paibaoji">0</span>&nbsp;条</td>
					<td class="craft-title">02#排包机：</td><td class="craft-value"><span id="v62" class="paibaoji">0</span>&nbsp;条</td>
					<td class="craft-title">03#排包机：</td><td class="craft-value"><span id="v63" class="paibaoji">0</span>&nbsp;条</td>
					<td class="craft-title">04#排包机：</td><td class="craft-value"><span id="v64" class="paibaoji">0</span>&nbsp;条</td>
					<td class="craft-title">05#排包机：</td><td class="craft-value"><span id="v65" class="paibaoji">0</span>&nbsp;条</td>
					<td class="craft-title">06#排包机：</td><td class="craft-value"><span id="v66" class="paibaoji">0</span>&nbsp;条</td>
				</tr>
			</table>
		</div>
    </div>
    <div class="info_gz" > 
		<h1>链条电机状态</h1>
    	<div style=" padding: 18px 10px 10px 70px;">
				<div style="width:79%;height: 100px;float: left;">				
				<table style="margin-top:5px 0px 5px 5px;" class="tab">
					<tr>
						<td ><div class="normal-flag" id="v1" style="width:20px;height:20px;"></div></td><td style="width:500px;">01#链条</td>
						<td ><div class="normal-flag" id="v2" style="width:20px;height:20px;"></div></td><td style="width:500px;">02#链条</td>
						<td ><div class="normal-flag" id="v3" style="width:20px;height:20px;"></div></td><td style="width:500px;">03#链条</td>
						<td ><div class="normal-flag" id="v4" style="width:20px;height:20px;"></div></td><td style="width:500px;">04#链条</td>
						<td ><div class="normal-flag" id="v5" style="width:20px;height:20px;"></div></td><td style="width:500px;">05#链条</td>
						<td ><div class="normal-flag" id="v6" style="width:20px;height:20px;"></div></td><td style="width:500px;">06#链条</td>
					</tr>
					<tr>
						<td ><div class="normal-flag" id="v7" style="width:20px;height:20px;"></div></td><td style="width:500px;">07#链条</td>
						<td ><div class="normal-flag" id="v8" style="width:20px;height:20px;"></div></td><td style="width:500px;">08#链条</td>
						<td ><div class="normal-flag" id="v9" style="width:20px;height:20px;"></div></td><td style="width:500px;">09#链条</td>
						<td ><div class="normal-flag" id="v10" style="width:20px;height:20px;"></div></td><td style="width:500px;">10#链条</td>
						<td ><div class="normal-flag" id="v11" style="width:20px;height:20px;"></div></td><td style="width:500px;">11#链条</td>
						<td ><div class="normal-flag" id="v12" style="width:20px;height:20px;"></div></td><td style="width:500px;">12#链条</td>
					</tr>
					<tr>
						<td ><div class="normal-flag" id="v13" style="width:20px;height:20px;"></div></td><td style="width:500px;">13#链条</td>
						<td ><div class="normal-flag" id="v14" style="width:20px;height:20px;"></div></td><td style="width:500px;">14#链条</td>
						<td ><div class="normal-flag" id="v15" style="width:20px;height:20px;"></div></td><td style="width:500px;">15#链条</td>
						<td ><div class="normal-flag" id="v16" style="width:20px;height:20px;"></div></td><td style="width:500px;">16#链条</td>
						<td ><div class="normal-flag" id="v17" style="width:20px;height:20px;"></div></td><td style="width:500px;">17#链条</td>
						<td ><div class="normal-flag" id="v18" style="width:20px;height:20px;"></div></td><td style="width:500px;">18#链条</td>
					</tr>
				</table>
				<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
		</div> 
    </div>
        <div class="info_gz" > 
		<h1>提升机状态</h1>
    	<div style=" padding: 18px 10px 10px 70px;">
				<div style="width:79%;height: 100px;float: left;">				
				<table style="margin-top:5px 0px 5px 5px;" class="tab">
					<tr>
						<td ><div class="normal-flag" id="v19" style="width:20px;height:20px;"></div></td><td style="width:500px;">01#提升机</td>
						<td ><div class="normal-flag" id="v20" style="width:20px;height:20px;"></div></td><td style="width:500px;">02#提升机</td>
						<td ><div class="normal-flag" id="v21" style="width:20px;height:20px;"></div></td><td style="width:500px;">03#提升机</td>
						<td ><div class="normal-flag" id="v22" style="width:20px;height:20px;"></div></td><td style="width:500px;">04#提升机</td>
						<td ><div class="normal-flag" id="v23" style="width:20px;height:20px;"></div></td><td style="width:500px;">05#提升机</td>
						<td ><div class="normal-flag" id="v24" style="width:20px;height:20px;"></div></td><td style="width:500px;">06#提升机</td>
					</tr>
					<tr>
						<td ><div class="normal-flag" id="v25" style="width:20px;height:20px;"></div></td><td style="width:500px;">07#提升机</td>
						<td ><div class="normal-flag" id="v26" style="width:20px;height:20px;"></div></td><td style="width:500px;">08#提升机</td>
						<td ><div class="normal-flag" id="v27" style="width:20px;height:20px;"></div></td><td style="width:500px;">09#提升机</td>
						<td ><div class="normal-flag" id="v28" style="width:20px;height:20px;"></div></td><td style="width:500px;">10#提升机</td>
						<td ><div class="normal-flag" id="v29" style="width:20px;height:20px;"></div></td><td style="width:500px;">11#提升机</td>
						<td ><div class="normal-flag" id="v30" style="width:20px;height:20px;"></div></td><td style="width:500px;">12#提升机</td>
					</tr>
					<tr>
						<td ><div class="normal-flag" id="v31" style="width:20px;height:20px;"></div></td><td style="width:500px;">13#提升机</td>
						<td ><div class="normal-flag" id="v32" style="width:20px;height:20px;"></div></td><td style="width:500px;">14#提升机</td>
						<td ><div class="normal-flag" id="v33" style="width:20px;height:20px;"></div></td><td style="width:500px;">15#提升机</td>
						<td ><div class="normal-flag" id="v34" style="width:20px;height:20px;"></div></td><td style="width:500px;">16#提升机</td>
						<td ><div class="normal-flag" id="v35" style="width:20px;height:20px;"></div></td><td style="width:500px;">17#提升机</td>
						<td ><div class="normal-flag" id="v36" style="width:20px;height:20px;"></div></td><td style="width:500px;">18#提升机</td>
					</tr>
				</table>
				<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
		</div> 
    </div>
        <div class="info_gz"  style="height:70px"> 
		<h1>排包机状态</h1>
    	<div style=" padding: 0px 10px 10px 70px;">
				<div style="width:79%;height: 100px;float: left;">				
				<table style="margin-top:5px 0px 5px 5px;" class="tab">
					<tr>
						<td ><div class="normal-flag" id="v37" style="width:20px;height:20px;"></div></td><td style="width:500px;">01#排包机</td>
						<td ><div class="normal-flag" id="v38" style="width:20px;height:20px;"></div></td><td style="width:500px;">02#排包机</td>
						<td ><div class="normal-flag" id="v39" style="width:20px;height:20px;"></div></td><td style="width:500px;">03#排包机</td>
						<td ><div class="normal-flag" id="v40" style="width:20px;height:20px;"></div></td><td style="width:500px;">04#排包机</td>
						<td ><div class="normal-flag" id="v41" style="width:20px;height:20px;"></div></td><td style="width:500px;">05#排包机</td>
						<td ><div class="normal-flag" id="v42" style="width:20px;height:20px;"></div></td><td style="width:500px;">06#排包机</td>
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