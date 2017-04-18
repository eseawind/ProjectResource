//禁止页面选中复制
window.onload = function() {
    with(document.body) {
      oncontextmenu=function(){return false}
      ondragstart=function(){return false}
      onselectstart=function(){return false}
      onbeforecopy=function(){return false}
      onselect=function(){document.selection.empty()}
      oncopy=function(){document.selection.empty()}
    }
}


//半圆卷烟机第一次要加载动画效果
var jyjCirIsFirst=true;
//半圆包装机第一次要加载动画效果
var bzjCirIsFirst=true;
//辅料卷烟机第一次加载动画效果
var jyjFlIsFirst=true;
//辅料包装机机第一次加载动画效果
var bzjFlIsFirst=true;
var bzjUpdate;//包装机定时
var jyjUpdate;//卷烟机定时
var rollerPQty=1;
var packerPQty=1;
//辅料小数点位计算
var lbPoint=0;
var jyzPoint=0;
var sszPoint=0;

var xhzPoint=0;
var xhmPoint=0;
var thzPoint=0;
var thmPoint=0;
var nczPoint=0;

/** 实际消耗柱状图 跟 标准柱状图对比 */
var zj17_lb=0;
var zj17_jyz=0;
var jyj_ssz=0;

var zb25_xhz=0;
var zb25_xhym=0;
var zb25_ncz=0;
var zb25_thz=0;
var zb25_thym=0;

/**  cy_cs-包装机车速  cy_online-包装机运行状态  */
/*var cy_cs=0;
var cy_online=0;*/

$(document).ready(function() {
	//初始化
	cl_start();
	fl_start();
	//初始卷包值
	initMachineData();
//	roller_value();
//	packer_value();
	//隐藏包装机
	$("#bzjdiv").hide();
	//定时任务
	window.timerTaskFunction0=setInterval('roller_value()',3000);
	window.timerTaskFunction1=setInterval('packer_value()',3000);
	//延时执行函数
	/*setTimeout(function (){
	 }, 2000);*/

});
//初始化卷、包烟机数据
function initMachineData(){
	//卷烟机
	$.ajax({
		   type: "POST",
		   url: "/LBDWS/wct/realTimeData/initRollerData.json",
		   success: function(msg){
			   if(msg.equipmentEdcs==undefined){
			   }else{
				   $("#rollerMachineName").html(msg.equipmentEdcs);//设备名称
				   $("#rollerMachineType").html(msg.equipmentType);//设备型号
				   $("#rollerPStime").html(msg.planStim);//计划开始时间
				   $("#rollerPEtime").html(msg.planEtim);//计划结束时间
				   $("#rollerOrderCode").html(msg.workorderCode);//工单
				   $("#cyShift").html(msg.shiftName);//班次
				   $("#cyTeam").html(msg.teamName);//班次
				   $("#matName").html(msg.matName);//牌号
				   $("#rollerPQty1").html( (msg.planQty).toFixed(0) );//计划产量
				   $("#rollerPQtyUnit1").html(msg.unit);//计划单位
				   $("#rollerRealQty1Unit").html(msg.unit);
				   rollerPQty=msg.planQty;
				   $("#rollerPQty2").html( (msg.planQty).toFixed(0));//计划产量
				   $("#rollerPQty2Unit").html(msg.unit);//计划单位
				   $("#rollerPQty3Unit").html(msg.unit);//计划单位
				   $("#lbBZDH").html(msg.lvbangBzdh);//滤棒标准单耗
				   lbPoint=checkPoint(msg.lvbangBzdh);
				   $("#jyzBZDH").html(msg.juanyanzhiBzdh);//卷烟纸标准单耗
				   jyzPoint=checkPoint(msg.juanyanzhiBzdh);
				   $("#sszBZDH").html(msg.shuisongzhiBzdh);//水松纸标准单耗
				   sszPoint=checkPoint(msg.shuisongzhiBzdh);
				   //赋值标准单耗 
				   zj17_lb=msg.lvbangBzdh;
				   zj17_jyz=msg.juanyanzhiBzdh;
				   zj17_ssz=msg.shuisongzhiBzdh;
				   $("#cysb_title").css("background-color","rgb(139, 195, 74)");
			   }
		   }
		});
	//包装机
	$.ajax({
		   type: "POST",
		   url: "/LBDWS/wct/realTimeData/initPackerData.json",
		   success: function(msg){
			   if(msg.equipmentEdcs==undefined){
			   }else{
				   $("#packerMachineName").html(msg.equipmentEdcs);//设备名
				   $("#packerMachineType").html(msg.equipmentType);//设备类型
				   $("#packerPStime").html(msg.planStim);//计划开始
				   $("#packerPEtime").html(msg.planEtim);//计划结束
				   $("#packerOrderCode").html(msg.workorderCode);//工单code
				   $("#packerPQty1").html( (msg.planQty).toFixed(0) );//计划产量
				   $("#packerRealQty1Unit").html(msg.unit);//计划产量
				   $("#packerPQty1Unit").html(msg.unit);//计划产量
				   $("#packerPQty2").html( (msg.planQty).toFixed(0) );//计划产量
				   $("#packerPQty2Unit").html(msg.unit);//计划产量
				   $("#packerRealQty2Unit").html(msg.unit);
				   packerPQty=msg.planQty;
				   $("#xhzBZDH").html(msg.xiaohezhiBzdh);//小盒子标准单耗
				   xhzPoint=checkPoint(msg.xiaohezhiBzdh);
				   $("#xhymBZDH").html(msg.xiaohemoBzdh);//小盒膜标准单耗
				   xhmPoint=checkPoint(msg.xiaohemoBzdh);
				   $("#nczBZDH").html(msg.neichenzhiBzdh);//内衬纸标准单耗
				   nczPoint=checkPoint(msg.neichenzhiBzdh);
				   $("#thzBZDH").html(msg.tiaohezhiBzdh);//条盒纸标准单耗
				   thzPoint=checkPoint(msg.tiaohezhiBzdh);
				   $("#thmBZDH").html(msg.tiaohemoBzdh);//条盒膜标准单耗
				   thmPoint=checkPoint(msg.tiaohemoBzdh);
				   //赋值标准单耗
				   zb25_xhz=msg.xiaohezhiBzdh;
				   zb25_xhym=msg.xiaohemoBzdh;
				   zb25_ncz=msg.neichenzhiBzdh;
				   zb25_thz=msg.tiaohezhiBzdh;
				   zb25_thym=msg.tiaohemoBzdh;
			   }
		   }
		});
}


/** 卷包界面切换 
   id  1-卷烟机
	   2-包转机
*/
function rollerAndPacker(id) {
	if (id == 1) { //显示卷烟机
		//显示卷烟机
		$("#jyjdiv").show();
		//隐藏包装机
		$("#bzjdiv").hide();
	} else if (id == 2) { //显示包装机
		//显示包装机
		$("#bzjdiv").show();
		//隐藏卷烟机
		$("#jyjdiv").hide();
	}
}
//卷烟机预计完成剩余时间
function preRollerTell(speed,qty){
	var pQty=$("#rollerPQty1").html()//计划产量（箱）
	var lQty=pQty-qty;
	if(speed>0 && lQty>0){
		var rTime=(lQty/(speed/50000)/60).toFixed(2);
		if(rTime>8){
			rTime=8;
		}
		$("#rollerPreFinishedTime").html(rTime);
	}else{
		$("#rollerPreFinishedTime").html("*");
	}
}

//包装机预计完成剩余时间
function prePackerTell(speed,qty){
	var pQty=$("#packerPQty1").html()//计划产量（箱）
	var lQty=pQty-qty;
	if(speed>0 && lQty>0){
		var pTime=(lQty/(speed/2500)/60).toFixed(2);
		if(pTime>8){
			pTime=8;
		}
		$("#packerPreFinished").html(pTime);
	}else{
		$("#packerPreFinished").html("*");
	}
}
//卷烟机-赋值
function roller_value() {
	 var rollerRealQty=1.0;//实际产量
	 var cyVal=0.0;//储烟量
	 var lbBZ=1.0;//滤棒标准单耗
	 var jyzBZ=1.0;//卷烟纸标准单耗
	 var sszBZ=1.0;//水松纸标准单耗
	 var lbVal=0.0;//滤棒实际
	 var jyzVal=0.0;//卷烟纸实际
	 var sszVal=0.0;//水松纸实际
	 var runTime=0.0;//运行时间
	 var stopTime=0.0;//停机时间
	 var stopTimes=0.0;//停机次数
	 var runStatusTxt="正在运行";
	 var rSpeed=0;
	 var rollerOrder=$("#rollerOrderCode").html();//卷烟机工单
	$.ajax({
		   type: "POST",
		   url: "/LBDWS/wct/realTimeData/getRealTimeRollerData.json",
		   success: function(msg){
			   if(msg.online==undefined){
				  //alert("会话已过期！请重新登录！");
				   window.parent.parent.location.href="/LBDWS/wct/index.htm";
			   }else if($.trim(rollerOrder)==""){
				   $("#jyj_title_backgd").html("卷烟机无工单！").css({"line-height":"60px","text-align":"center"});
				   //window.location.reload();
				   return;
			   }else{
				   rSpeed=msg.speed;
				   if(msg.online=="0"){
					   runStatusTxt="网络异常";
					   $("#jyj_title").css("background-color","rgb(255, 146, 7)");
				   }else if(rSpeed==0 && msg.online=="1"){
					   runStatusTxt="暂停";
					   $("#jyj_title").css("background-color","rgb(255, 146, 7)");
				   }else if(rSpeed>0 && msg.online=="1"){
					   $("#jyj_title").css("background-color","rgb(139, 195, 74)");
				   }
				   $("#rollerMachineStatus").html(runStatusTxt);//运行状态
				   $("#rollerMchineSpeed").html(rSpeed);//当前车速
				   pQty=$("#rollerPQty1").html();//计划产量
				   rollerRealQty=msg.qty;//实际产量
				   if(rollerRealQty==0){
					   rollerRealQty=0.00;
				   }
				   $("#rollerRealQty1").html(Math.round(rollerRealQty*Math.pow(10,1))/Math.pow(10,1));//实际产量
				   $("#rollerRealQty2").html(Math.round(rollerRealQty*Math.pow(10,1))/Math.pow(10,1));//实际产量
				   cyVal=msg.cy;
				   $("#cyVal").html(cyVal);//存烟量
				   lbBZ=$("#lbBZDH").html();//滤棒标准单耗
				   lbBZ=checkDH(lbBZ);
				   jyzBZ=$("#jyzBZDH").html();//卷烟纸标准单耗
				   jyzBZ=checkDH(jyzBZ);
				   sszBZ=$("#sszBZDH").html();//水松纸标准单耗
				   sszBZ=checkDH(sszBZ);
				   lbVal=msg.lvbangVal;//滤棒实际
				   jyzVal=msg.juanyanzhiVal;//卷烟纸实际
				   sszVal=msg.shuisongzhiVal;//水松纸实际
				   runTime=msg.runTime;//运行时间
				   stopTime=msg.stopTime;//停机时间
				   stopTimes=msg.stopTimes;//停机次数
				   /** 补漏：包装机DAC车速为0，就取储烟设备YF17，点位：1-车速  22-运行状态*/
				   /*cy_cs=msg.cy_cs;//包装机车速
				   cy_online=msg.cy_online;//包装机运行状态*/
				   //计算剩余多少时间完成
				   preRollerTell(rSpeed,rollerRealQty);
				   //赋值
				   $("#lbRealConsume").html(lbVal);
				   $("#jyzRealConsume").html(jyzVal);
				   $("#sszRealConsume").html(sszVal);
					//卷烟机产量
					var width0 =rollerRealQty/rollerPQty*100+"%";
					$("#jyjQtybar").width(width0);
					
					//卷烟机辅料消耗
					//console.info("卷烟机辅料数据");
					var rDHVal1=0;
					if(rollerRealQty!=0){
						rDHVal1=(lbVal*10000/rollerRealQty).toFixed(lbPoint);
					}
					//卷烟辅料为NaN时赋值
					rDHVal1 = checkIsNumNaNOr0(rDHVal1,1);
					var widthNum = rDHVal1/lbBZ * 95;//滤棒
					//柱状统计条为0赋值填满
					widthNum = checkIsNumNaNOr0(widthNum,2);
					var rDHVal2=0;
					if(rollerRealQty!=0){
						rDHVal2=(jyzVal/rollerRealQty).toFixed(jyzPoint);
					}
					rDHVal2 = checkIsNumNaNOr0(rDHVal2,1);
					var widthNum1 = rDHVal2/jyzBZ * 95;//卷烟纸
					widthNum1 = checkIsNumNaNOr0(widthNum1,2);
					var rDHVal3=0;
					if(rollerRealQty!=0){
						rDHVal3=(sszVal/rollerRealQty).toFixed(sszPoint);
					}
					rDHVal3 = checkIsNumNaNOr0(rDHVal3,1);
					var widthNum2 = rDHVal3/sszBZ * 95;//水松纸
					widthNum2 = checkIsNumNaNOr0(widthNum2,2);
					$('#bar-7').jqbar({label: '实际<br/>消耗',dhVal:rDHVal1,value:zj17_lb<rDHVal1?98:widthNum,barColor: '#8bc34a',orientation: 'v',isAdd: jyjFlIsFirst});
					$('#bar-8').jqbar({label: '实际<br/>消耗',dhVal:rDHVal2,value:zj17_jyz<rDHVal1?98:widthNum1,barColor: '#8bc34a',orientation: 'v',isAdd: jyjFlIsFirst});
					$('#bar-9').jqbar({label: '实际<br/>消耗',dhVal:rDHVal3,value:zj17_ssz<rDHVal1?98:widthNum2,barColor: '#8bc34a',orientation: 'v',isAdd: jyjFlIsFirst});
					jyjFlIsFirst=false;
					runTim2=100;
					//卷烟机仪表盘
					var g6 = Math.round(runTime);
					var g7 = Math.round(stopTime);
					var g8 = Math.round(stopTimes);
					//运行时间，停机时间，停机次数
					$(".zj17_runTime").text(g6);
					$(".zj17_stopTime").text(g7);
					$(".zj17_stopTimes").text(g8);
					//百分比  570*X=100  
					$("#gauge6").gauge( (g6*0.17544).toFixed(0), {unit: "%",type: "halfcircle",color: "#8bc34a",isAnimation: jyjCirIsFirst});
					$("#gauge7").gauge( (g7*0.17544).toFixed(0), {unit: "%",type: "halfcircle",color: "#f39c12",isAnimation: jyjCirIsFirst});
					$("#gauge8").gauge( (g8*0.17544).toFixed(0), {unit: "%",type: "halfcircle",color: "#dd4b39",isAnimation: jyjCirIsFirst});
					jyjCirIsFirst=false;
			   }
		   }
		});
}
//验证标准单耗是否为0，是0则默认设置为1.
function checkDH(val){
	if(val<=0){
		val=1;
	}
	return val;
}
//包装机-赋值
function packer_value() {
	 var realQty=1.0;//实际产量
	 var xhmBZ=1.0;//小盒膜标准单耗
	 var thmBZ=1.0;//条盒膜标准单耗
	 var xhzBZ=1.0;//小盒纸标准单耗
	 var thzBZ=1.0;//条盒纸标准单耗
	 var nczBZ=1.0;//内衬纸标准单耗
	 var xhmVal=0.0;//小盒膜实际
	 var thmVal=0.0;//条盒膜实际
	 var xhzVal=0.0;//小盒纸实际
	 var thzVal=0.0;//条盒纸实际
	 var nczVal=0.0;//内衬纸实际
	 var runTime=0.0;//运行时间
	 var stopTime=0.0;//停机时间
	 var stopTimes=0.0;//停机次数
	 var runStatusText="正在运行";
	 var tsQty=0;//提升机数据
	 var pSpeed=0;
	 var packerOrder=$("#packerOrderCode").html();
	$.ajax({
		   type: "POST",
		   url:"/LBDWS/wct/realTimeData/getRealTimePackerData.json",
		   success: function(msg){
			   			  if(msg.online==undefined){
			   				//alert("会话已过期！请重新登录！");
			   				window.parent.parent.location.href="/LBDWS/wct/index.htm";
			   			  }else if($.trim(packerOrder)==""){
			   				 window.location.reload();
			   				 return;
			   			  }else{
			   				pSpeed=msg.speed;
			   				if(msg.online=="0"){
								   runStatusText="网络异常";
								   $("#bzj_title").css("background-color","rgb(255, 146, 7)");
							   }else if(pSpeed==0 && msg.online=="1"){
								   runStatusText="暂停";
								   $("#bzj_title").css("background-color","rgb(255, 146, 7)");
							   }else if(pSpeed>0 && msg.online=="1"){
								   $("#bzj_title").css("background-color","rgb(139, 195, 74)");
							   }
				   			   //判断-补漏判断
				   			   /*if(pSpeed<cy_cs){
					   				 pSpeed=(cy_cs/20).toFixed(0);//DAC车速为0将储烟包装车速赋值
					   				 if(pSpeed==0 && cy_online=="false"){
					   				    runStatusText="暂停";
									    $("#bzj_title").css("background-color","rgb(255, 146, 7)");
					   				 }else{
					   					$("#bzj_title").css("background-color","rgb(139, 195, 74)");
					   				    runStatusText="正在运行"; 
					   				 }
				   			   }*/
							   $("#packerMachineStatus").html(runStatusText);//运行状态
							   $("#packerMachineSpeed").html(pSpeed);//运行速度
							   //$("#packerPreFinished").html(msg.speed);//预计完成时间
							   realQty= msg.qty;//实际产量
							   if(realQty==0){
								   realQty=0.00;
							   }
							   /**
							    * 判断：提升机产量>DAC产量  包装机取提升机产量
							    * */
							   var qty_tsj= (msg.tsQty/250).toFixed(2);
							   if(qty_tsj>realQty){
								   realQty=qty_tsj;
							   }
							   $("#packerRealQty2").html(Math.round(realQty*Math.pow(10,1))/Math.pow(10,1));//实际产量
							   $("#packerRealQty1").html(Math.round(realQty*Math.pow(10,1))/Math.pow(10,1));//实际产量
							   xhzBZ=$("#xhzBZDH").html();//小盒子标准单耗
							   xhzBZ=checkDH(xhzBZ);
							   xhmBZ= $("#xhymBZDH").html();//小盒膜标准单耗
							   xhmBZ=checkDH(xhmBZ);
							   nczBZ= $("#nczBZDH").html();//内衬纸标准单耗
							   nczBZ=checkDH(nczBZ);
							   thzBZ= $("#thzBZDH").html();//条盒纸标准单耗
							   thzBZ=checkDH(thzBZ);
							   thmBZ=$("#thmBZDH").html();//条盒膜标准单耗 
							   thmBZ=checkDH(thmBZ);
							   xhmVal=msg.xiaohemoVal;//小盒膜实际
							   thmVal=msg.tiaohemoVal;//条盒膜实际
							   xhzVal=msg.xiaohezhiVal;//小盒纸实际
							   thzVal= msg.tiaohezhiVal;//条盒纸实际
							   nczVal=msg.neichenzhiVal;//内衬纸实际
							   runTime=msg.runTime;//运行时间
							   stopTime=msg.stopTime;//停机时间
							   stopTimes=msg.stopTimes;//停机次数
							   tsQty=msg.tsQty;//提升机数量
							   $("#tsVal").html(tsQty);//提升机数量
							   //计算剩余时长
							   prePackerTell(pSpeed,realQty);
							   //赋值
							   var width00 =realQty/packerPQty * 100 + "%";
							   $("#bzjQtybar").width(width00);
								//包装机辅料消耗
								//console.info("包装机机辅料数据");
								$("#xhzRaalConsume").html(xhzVal);
								$("#xhymRealConsume").html(xhmVal);
								$("#nczRealConsume").html(nczVal);
								$("#thzRealConsume").html(thzVal);
								$("#thmRealConsume").html(thmVal);
								var rDHval=0;
								if(realQty!=0){
									rDHval=(xhzVal/realQty).toFixed(xhzPoint);
								}
								rDHval = checkIsNumNaNOr0(rDHval,1);
								var widthNum = rDHval/xhzBZ * 95;//小盒纸
								widthNum = checkIsNumNaNOr0(widthNum,2);
								var rDHval1=0;
								if(realQty!=0){
									rDHval1=(xhmVal/realQty).toFixed(xhmPoint);
								}
								rDHval1 = checkIsNumNaNOr0(rDHval1,1);
								var widthNum1 =rDHval1/xhmBZ * 95;//小盒烟膜
								widthNum1 = checkIsNumNaNOr0(widthNum1,2);
								var rDHval2=0;
								if(realQty!=0){
									rDHval2=(nczVal/realQty).toFixed(nczPoint);
								}
								rDHval2 = checkIsNumNaNOr0(rDHval2,1);
								var widthNum2 = rDHval2/nczBZ * 95;//内衬纸
								widthNum2 = checkIsNumNaNOr0(widthNum2,2);
								var rDHval3=0;
								if(realQty!=0){
									rDHval3=(thzVal/realQty).toFixed(thzPoint);
								}
								rDHval3 = checkIsNumNaNOr0(rDHval3,1);
								var widthNum3= rDHval3/thzBZ * 95;//条盒纸
								widthNum3 = checkIsNumNaNOr0(widthNum3,2);
								var rDHval4=0;
								if(realQty!=0){
									rDHval4=(thmVal/realQty).toFixed(thmPoint);
								}
								rDHval4 = checkIsNumNaNOr0(rDHval4,1);
								var widthNum4 = rDHval4/thmBZ * 95;//条盒烟膜
								widthNum4 = checkIsNumNaNOr0(widthNum4,2);
								$('#bar-72').jqbar({label: '实际<br/>消耗',dhVal:rDHval, value: zb25_xhz<rDHval?98:widthNum,barColor: '#8bc34a',orientation: 'v',isAdd: bzjFlIsFirst});
								$('#bar-82').jqbar({label: '实际<br/>消耗',dhVal:rDHval1,value: zb25_xhym<rDHval1?98:widthNum1,barColor: '#8bc34a',orientation: 'v',isAdd: bzjFlIsFirst});
								$('#bar-92').jqbar({label: '实际<br/>消耗',dhVal:rDHval2,value: zb25_ncz<rDHval2?98:widthNum2,barColor: '#8bc34a',orientation: 'v',isAdd: bzjFlIsFirst});
								$('#bar-102').jqbar({label: '实际<br/>消耗',dhVal:rDHval3,value:zb25_thz<rDHval3?98:widthNum3,barColor: '#8bc34a',orientation: 'v',isAdd: bzjFlIsFirst});
								$('#bar-112').jqbar({label: '实际<br/>消耗',dhVal:rDHval4,value:zb25_thym<rDHval4?98:widthNum4,barColor: '#8bc34a',orientation: 'v',isAdd: bzjFlIsFirst});
								bzjFlIsFirst=false;
								//包装机仪表盘
								var g62 = Math.round(runTime );
								var g72 = Math.round(stopTime);
								var g82= Math.round(stopTimes);
								$(".zb25_runTime").text(g62);
								$(".zb25_stopTime").text(g72);
								$(".zb25_stopTimes").text(g82);
								$("#gauge62").gauge( (g62*0.17544).toFixed(0), {unit: "%",type: "halfcircle",color: "#8bc34a",isAnimation: bzjCirIsFirst});
								$("#gauge72").gauge( (g72*0.17544).toFixed(0), {unit: "%",type: "halfcircle",color: "#f39c12",isAnimation: bzjCirIsFirst});
								$("#gauge82").gauge( (g82*0.17544).toFixed(0), {unit: "%",type: "halfcircle",color: "#dd4b39",isAnimation: bzjCirIsFirst});
								bzjCirIsFirst=false;
				   		  }
		   }
		});
	//packer qty
}
/**柱形统计数据为0或NaN统计显示情况*/
function checkIsNumNaNOr0(param,num){
	if(param=="NaN" && num==1){
		param=0.00;
	}
	if(param==0 && num==2){
		param=1;
	}
	return param;
}
function checkPoint(num){
	var attry=num.toString().split(".");
	try{
		return attry[1].length;
	}catch(e){
		return 0;
	}
}
/**初始化产量*/
function cl_start() {
	jQuery('.skillbar').each(function() {
		jQuery(this).find('.skillbar-bar').animate({
			width: jQuery(this).attr('data-percent')
		}, 2000);
	});
}

/**初始化辅料消耗 */
function fl_start() {
	$.fn.extend({
		jqbar: function(options) {
			var settings = $.extend({
				animationSpeed: 2000,
				barLength: 159,
				orientation: 'h',
				barWidth: 33,
				barColor: 'red',
				label: '实际<br/>消耗',
				value: 1000,
				isAdd: true,
				dhVal:0.0
			}, options);

			return this.each(function() {

				var valueLabelHeight = 0;
				var progressContainer = $(this);

				if (settings.orientation == 'h') {
					if (settings.isAdd == true) {
						progressContainer.addClass('jqbar horizontal').append('<span class="bar-label"></span><span class="bar-level-wrapper"><span class="bar-level"></span></span><span class="bar-percent"></span>');
					}
					var progressLabel = progressContainer.find('.bar-label').html(settings.label);
					var progressBar = progressContainer.find('.bar-level').attr('data-value', settings.value);
					var progressBarWrapper = progressContainer.find('.bar-level-wrapper');

					progressBar.css({
						height: settings.barWidth,
						width: 0,
						backgroundColor: settings.barColor
					});

					var valueLabel = progressContainer.find('.bar-percent');
					valueLabel.html('0');
				} else {
					if (settings.isAdd == true) {
						progressContainer.addClass('jqbar vertical').append('<span class="bar-percent"></span><span class="bar-level-wrapper"><span class="bar-level"></span></span><span class="bar-label"></span>');
					}
					var progressLabel = progressContainer.find('.bar-label').html(settings.label);
					var progressBar = progressContainer.find('.bar-level').attr('data-value', settings.value);
					var progressBarWrapper = progressContainer.find('.bar-level-wrapper');

					progressContainer.css('height', settings.barLength);
					progressBar.css({
						height: settings.barLength,
						top: settings.barLength,
						width: settings.barWidth,
						backgroundColor: settings.barColor
					});
					progressBarWrapper.css({
						height: settings.barLength,
						width: settings.barWidth
					});

					var valueLabel = progressContainer.find('.bar-percent');
					valueLabel.html('0');
					valueLabelHeight = parseInt(valueLabel.outerHeight());
					valueLabel.css({
						top: (settings.barLength - valueLabelHeight) + 'px'
					});
				}

				animateProgressBar(progressBar);

				function animateProgressBar(progressBar) {

					var level = parseInt(progressBar.attr('data-value'));
					if (level > 100) {
						level = 100;
						//alert('max value cannot exceed 100 percent');
					}
					var w = settings.barLength * level / 100;

					if (settings.orientation == 'h') {
						progressBar.animate({
							width: w
						}, {
							duration: 2000,
							step: function(currentWidth) {
								var percent = parseInt(currentWidth / settings.barLength * 100);
								if (isNaN(percent))
									percent = 0;
								progressContainer.find('.bar-percent').html(percent + ' ');
							}
						});
					} else {
						var h = settings.barLength - settings.barLength * level / 100;
						progressBar.animate({
							top: h
						}, {
							duration: settings.isAdd==true?2000:0,
							step: function(currentValue) {
								var percent = parseInt((settings.barLength - parseInt(currentValue)) / settings.barLength * 100);
								if (isNaN(percent))
									percent = 0;
								//progressContainer.find('.bar-percent').html(Math.abs(percent));
								progressContainer.find('.bar-percent').html(settings.dhVal);
							}
						});
						progressContainer.find('.bar-percent').animate({
							top: (h - valueLabelHeight)
						}, settings.isAdd==true?2000:0);
					}
				}
			});
		}
	});
}


/** 获得当前系统时间wch  */
setInterval(function() {
    var now = new Date().format("yyyy年MM月dd日 hh:mm:ss");
    $('#current-time').text(now);
}, 1000);

Date.prototype.format = function(format) { 
    var date = { 
           "M+": this.getMonth() + 1, 
           "d+": this.getDate(), 
           "h+": this.getHours(), 
           "m+": this.getMinutes(), 
           "s+": this.getSeconds(), 
           "q+": Math.floor((this.getMonth() + 3) / 3), 
           "S+": this.getMilliseconds() 
    }; 
    if (/(y+)/i.test(format)) { 
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length)); 
    } 
    for (var k in date) { 
           if (new RegExp("(" + k + ")").test(format)) { 
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length)); 
           } 
    } 
    return format; 
} 
