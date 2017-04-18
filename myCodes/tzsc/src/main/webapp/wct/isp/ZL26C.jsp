<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成型机实时监控 ZL26C</title>
<style>
*{margin:0;padding:0;font-size: 12px;font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;}
	.info_area{height:60px;padding: 5px 10px 5px 10px;background: url(${pageContext.request.contextPath}/wct/isp/img/border.jpg) repeat-x bottom;}
	.info_area_center_tab tr td{font-size:14px;height:20px;text-align:left;}
	.info_area div{float:left;}
	#info_area_left{width:380px;text-align:center;}
	#info_area_center{width:160px;margin-top:5px;}
	#info_area_right{margin-left:20px;width:380px;text-align:center;}
	.info_area_center_tab tr td{font-size:14px;text-align:left;}	
	.info_area_left_right_tab tr td{font-size:12px;text-align:left;height:17px;}
	.arre-tt{font-weight:bold;width:160px;text-align:right;}
	.arre-tt2{font-weight:bold;width:70px;text-align:right;}
	.info_count{width:100%;height:485px;}
	.info_count_left{width:425px;height:480px;overflow: hidden;border-right:1px solid #999999;}
	.left_output{width:405px;height:60px;padding: 10px 10px 10px 10px;}
	.info_count_right_status{width:560px;height:130px;padding: 10px;margin: 0 auto;}
	.info_count_right{width:582px;height:480px;overflow: hidden;border-left:1px solid #e6e5e5; }
	.font_info{text-align:center;font-weight:400;}
	.info_machine{width:100%;height:67px;background:#e1e1e1;}
	.info_machine_left{ width:336px;height:64px;background:#c0c0c0;border:1px solid #aaaaaa;}
	.info_machine_left h1{line-height: 64px;width:110px;margin-left: 25px;font-size:18px;}
	.info_machine_left .machine_info{line-height:30px;font-weight: bold;height: 65px;padding: 2px 50px 0px 0px;}
	.info_machine_cen{width: 315px;height: 62px;margin-left: 10px;font-weight: bold;margin-top: 10px;background: #c0c0c0;border: 1px solid #aaaaaa;border-radius: 4px 4px 0px 0px;margin-top: 2px;}
	/* .info_machine_cen span{font-size: 16px;font-weight: bold;} */
	.juanyanji{width:150px;font-size: 14px;height:65px;line-height: 30px;text-align: center;/* border:1px solid red; */}
	.baozhuangji{width:150px;font-size: 14px;height:65px;line-height: 30px;text-align: center;/* border:1px solid red; */}
	
	.info_machine_right{width:336px;height:64px;background:#c0c0c0;border:1px solid #aaaaaa;}
	.info_machine_right h1{line-height: 64px;width:110px;margin-left: 25px;font-size:18px;}
	.info_machine_right .machine_info{line-height: 30px;font-weight: bold;height: 45px;padding: 2px 50px 0px 0px;}
	.fl{float:left}
	.fr{float:right}
	.left_output_blod {font-size:14px;font-weight: bold;width: 400px;height: 30px;line-height: 19px;margin:3px auto;}
	.info_count_blod {font-size:14px;font-weight: bold;width: 370px;height: 28px;line-height: 25px;margin:2px auto;position: relative;}
	.info_count_blod_right {font-size:14px;font-weight: bold;width:570px;height: 28px;line-height: 25px;margin:2px auto;position: relative;}
	.progress-bar {background-color:#f6f6f6;height:20px;margin-right: 10px;overflow:hidden}  
    .progress-bar span {display: inline-block;height: 100%;background-color: #34c2e3;
						/*非IE浏览器进度条动画效果*/
						-moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
                        -webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
                        box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
                        -webkit-transition: width .4s ease-in-out;
                        -moz-transition: width .4s ease-in-out;
                        -ms-transition: width .4s ease-in-out;
                        -o-transition: width .4s ease-in-out;
                        transition: width .4s ease-in-out;
        }
	.progress-h {height:80px;padding: 5px;width:28px;position: relative;overflow:hidden;}  
    .progress-h span {display: inline-block;width:30px;background-color: #34c2e3;position: absolute;
						bottom: 4px;
						/*非IE浏览器进度条动画效果*/
						-moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
                        -webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
                        box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
                        -webkit-transition: height .4s ease-in-out;
                        -moz-transition: height .4s ease-in-out;
                        -ms-transition: height .4s ease-in-out;
                        -o-transition: height .4s ease-in-out;
                        transition: height .4s ease-in-out;				
        }
		.depictCon{width:98%;margin:0 auto; position:relative;height: 250px;border-radius: 6px 6px 0px 0px;}
		.depictCon th{ font-size:14px; line-height:36px; background:#2483cb; color:#fff; padding-left:10px; text-align:left}
		.depictCon td{ line-height:28px; border:1px solid #b4b4b4; border-top:none}
		.depictCon td.depictTdC{ text-align:center; background:#b4b4b4;border-right: 1px solid #D1CECE;}
		.depictCon td.br_r {border-right:1px solid #b4b4b4;}
		.depictCon td.depictTdP{ padding-left:16px;}
		.depictCon td.borL{ border-left:1px solid #e6e5e5;}
		.bl_none{border-left: none;}
		
		.font_yellow{color: #FCD208;}
		.font_green{color: green;}
		.font_red{color: #d80000;}
		.font_blue{color:blue;}
		/*颜色渐变 IE6不支持*/
		.gc_g2{
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr = '#15ff0f', endColorstr = '#0e7307')";
			background-image: -moz-linear-gradient(top, #15ff0f, #0e7307);
			background-image: -ms-linear-gradient(top, #15ff0f, #0e7307);
			background-image: -o-linear-gradient(top, #15ff0f, #0e7307);
			background-image: -webkit-gradient(linear, center top, center bottom, from(#15ff0f), to(#0e7307));
			background-image: -webkit-linear-gradient(top, #15ff0f, #0e7307);
			background-image: linear-gradient(top, #15ff0f, #0e7307);
			}

		.gc_b{
			background: linear-gradient(top , rgb(115, 153, 244) , rgb(3, 55, 143) 100%);
			background: -o-linear-gradient(top , rgb(115, 153, 244) , rgb(3, 55, 143) 100%);
			background: -ms-linear-gradient(top , rgb(115, 153, 244) , rgb(3, 55, 143) 100%);
			background: -moz-linear-gradient(top , rgb(115, 153, 244) , rgb(3, 55, 143) 100%);
			background: -webkit-linear-gradient(top , rgb(115, 153, 244) , rgb(3, 55, 143) 100%);		
		}
		.gc_g{
			background: linear-gradient(left , rgb(12, 143, 0) 6% , rgb(60, 214, 86) 53% , rgb(0, 170, 0) 94%);
			background: -o-linear-gradient(left , rgb(12, 143, 0) 6% , rgb(60, 214, 86) 53% , rgb(0, 170, 0) 94%);
			background: -ms-linear-gradient(left , rgb(12, 143, 0) 6% , rgb(60, 214, 86) 53% , rgb(0, 170, 0) 94%);
			background: -moz-linear-gradient(left , rgb(12, 143, 0) 6% , rgb(60, 214, 86) 53% , rgb(0, 170, 0) 94%);
			background: -webkit-linear-gradient(left , rgb(12, 143, 0) 6% , rgb(60, 214, 86) 53% , rgb(0, 170, 0) 94%);
		}   
		.gc_y{
			background: linear-gradient(left , rgb(207, 198, 0) , rgb(251, 251, 71) 50% , rgb(211, 202, 0) 100%);
			background: -o-linear-gradient(left , rgb(207, 198, 0) , rgb(251, 251, 71) 50% , rgb(211, 202, 0) 100%);
			background: -ms-linear-gradient(left , rgb(207, 198, 0) , rgb(251, 251, 71) 50% , rgb(211, 202, 0) 100%);
			background: -moz-linear-gradient(left , rgb(207, 198, 0) , rgb(251, 251, 71) 50% , rgb(211, 202, 0) 100%);
			background: -webkit-linear-gradient(left , rgb(207, 198, 0) , rgb(251, 251, 71) 50% , rgb(211, 202, 0) 100%);
						
		}
		.gc_red{
			background: linear-gradient(top , rgb(255, 57, 57) 1% , rgb(147, 3, 3) 100%);
			background: -o-linear-gradient(top , rgb(255, 57, 57) 1% , rgb(147, 3, 3) 100%);
			background: -ms-linear-gradient(top , rgb(255, 57, 57) 1% , rgb(147, 3, 3) 100%);
			background: -moz-linear-gradient(top , rgb(255, 57, 57) 1% , rgb(147, 3, 3) 100%);
			background: -webkit-linear-gradient(top , rgb(255, 57, 57) 1% , rgb(147, 3, 3) 100%);
			
		} 
		/*ie6颜色方案*/
		.green{_background-color:#50ee4f!important;}
		.green_t{_background-color:#2cc72f!important;}
		.green_h{_background-color:#00BE00!important;}
		.red{_background-color:#fe0000!important;}
		.red_t{_background-color:#ff6666!important;}
		.yellow{_background-color: #fff100!important;}
		.yellow_h{_background-color: #e3d823!important;}
		.font_10{font-size:10px!important;-webkit-text-size-adjust:none;line-height: 18px;width: 25px;padding-left:7px;display: block;}


.progress {margin-bottom: 15px;float: left;}
.info_count_left_status_box{height: 35px;width: 100%;line-height: 23px;}
.progress_title{float: left;margin-right:10px}
#filter-jhcl,#filter-clsj,.progress_text{margin-left: 10px;font-weight: bold;}
.progress-normal{background-color: #00BE00;}
.progress-fault{background-color: #E91616;}
.progress-stop{background-color: #E91616;}
.w265{width:265px;}
.w420{width:420px;}
.w553{width:553px;}
.w384{width:384px;}
.w208{width:208px;}
.info_count_left_status{margin: 0 auto;padding: 0 10px;border-radius:6px;border: 1px solid #ACABAB;}
.info_count_left_status h1{margin: 10px 0;font-size: 16px;font-weight: bold;text-align: center;line-height:26px;/* border-bottom:1px solid #858484; */background: #b4b4b4;color: #3C3C3C;border-radius: 4px 4px 0px 0px;}
/**/
.data_span{
	display:block;width:44px;height:20px;text-align:center
}
/**/
.filter_img{height:311px;border:none;background-image:url(${pageContext.request.contextPath}/wct/isp/img/filter.png);}
.error{height:148px;float:left;margin-top:17px;margin-left:4px;width:260px}
.info{height:148px;float:left;margin-top:17px;margin-left:4px;}
.info_area div{float:left;width:318px;margin-left:8px;border:0px solid red;}
.info_area .t{font-size:16px;font-weight:bold;}
#info_div,#error_div{overflow: hidden;height:96px;margin-left:0px}
#info_div li,#error_div li{height:20px;font-size:14px;}
#run_div{position: relative;top: 230px;left: 210px;}
#run_status{margin-right:15px;heiht:50px;height:50px;float:left}
#run_info{height:50px;height:50px;float:left}
.run_title{font-size: 16px;font-weight: bold;}
.fontblue{color:#0067b2}
.fontgreen{color:#0F7D08}
.fontyellow{color:#D1C802}
.fontred{color:#C11A1A}
</style>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<script type="text/javascript">

//登录验证
var loginname = "${loginInfo.equipmentCode}";
if(loginname==null || loginname=="" || loginname.length==0){
	window.location="${pageContext.request.contextPath}/wct/sys/login.jsp";
}

	setInterval('AutoScroll("#info_div")',1000*8);
	setInterval('AutoScroll("#error_div")',1000*6);
	var panzhiBzdh;
	var panzhiEuval;
	function initFilterBaseInfo(){
		$.post("${pageContext.request.contextPath}/wct/isp/filter/initFilterBaseInfo.do",{"equipmentCode":"${loginInfo.equipmentCode}"},function(json){
			//
		    //$("#equipmentCode").html(json.);
		    //$("#equipmentType").html(json.);  //ZJ17
		    $("#equipmentEdcs").html(json.equipmentEdcs);  //7000支/分
		    $("#workorderCode").html(json.workorderCode);  //C22015010101#010
		    $("#matName").html(json.matName);  //白沙（精品）
		    $("#planQty").html(json.planQty);  // 50.5
		    $(".unit").html(json.unit);  //箱
		    $("#planStim").html(json.planStim);  //2015-01-01 07:30:00
		    $("#planEtim").html(json.planEtim);  //2015-01-01 17:30:00
		    $("#bthCode").html(json.bthCode);  //C22015010101
		    $("#stim").html(json.stim);  //2015-01-01 07:35:10
		    panzhiBzdh = json.panzhiBzdh;
		    panzhiEuval = json.panzhiEuval;
		    $("#panzhiBzdh").html(panzhiBzdh);
		},"JSON");
	}
	
	initFilterBaseInfo();
	
	function getFilterData(){
		$.post("${pageContext.request.contextPath}/wct/isp/filter/getFilterData.do",{"equipmentCode":"${loginInfo.equipmentCode}"},function(json){
		    
			var qty = json.qty.toFixed(2);
			$(".qty").html(qty);// 2.03,
			$("#qty_w").css("width",(qty/$("#planQty").html()*100).toFixed(2)+"%");
			
		    var panzhiVal = json.panzhiVal.toFixed(2);
		    
		    $("#panzhiVal").html(panzhiVal);// 2.75,
		    
		    $("#panzhiDh").html((panzhiVal/qty).toFixed(2));
		    
		    $("#panzhi_h").css("height",((panzhiVal/qty)/panzhiBzdh*80).toFixed(2)+"%");
		    
		    $("#panzhiRank").html(json.panzhiRank.toFixed(0));// 1, 
		    
		    $("#runTime").html(json.runTime.toFixed(0));// 300,
		    $("#stopTime").html(json.stopTime.toFixed(0));// 300,
		    $("#runTime_w").css("width",(json.runTime/480*100).toFixed(2)+"%"); 
		    $("#stopTime_w").css("width",(json.stopTime/480*100).toFixed(2)+"%"); 
		    
		    $("#stopTimes").html(json.stopTimes.toFixed(0));// 1,
		    $("#speed").html(json.speed.toFixed(0));// 7000,
		    var runStatus = "网络断开";
		    /*  .fontblue{color:#0067b2}
			    .fontgreen{color:#0F7D08}
			    .fontyellow{color:#D1C802}
			    .fontred{color:#C11A1A} 
			*/
		    var color = "#0067b2";
		    if(json.runStatus==-1){
		    	runStatus ="网络断开";
		    	color = "#0067b2";
		    }
			if(json.runStatus==0){
				runStatus ="运行停止";
				color = "#C11A1A";
		    }
			if(json.runStatus==1){
				runStatus ="低速运行";
				color = "#D1C802";
		    }
			if(json.runStatus==2){
				runStatus ="正常运行";
				color = "#0F7D08";
		    }
		    $("#runStatus").css("color",color).html(runStatus);// 2
		},"JSON");
	}
	
	var commonRequestTime=setInterval('getFilterData()',2000);
	
	function AutoScroll(obj){
		$(obj).find("ul:first").animate({
			marginTop:"-60px"
		},8000,function(){
			$(this).css({marginTop:"0px"}).find("li:last").appendTo(this);
		});
	}
	
	
	
	jinggao();
	/*  系统通知 
	setInterval('jinggao()',1000*60);
	*/
	function jinggao(){
		/* 系统通知 */
		$("#info_div").html("");
		$.get('${pageContext.request.contextPath}/msg/conwarn/getMsgInfo.do',function(json){
					if(json!=null){
						var inf="<ul>";
						for(var i=0;i<json.length;i++){
							inf+="<li class='info_lis' >▪<font style='color:red;'>"+json[i].title+":</font>&nbsp;<font style='font-size:12px;'>"+json[i].content+"</font></li>";
						}
						inf+="</ul>";
						$("#info_div").append(inf);
					}
		},"JSON");
	}
	//初始化设备故障
	 guzhangxinxi();
	 setInterval('guzhangxinxi()',1000*60);
	 function guzhangxinxi(){
			$("#error_div").html("");
			$.get('${pageContext.request.contextPath}/isp/get.do?',{"code":"${loginInfo.equipmentCode}"},function(json){
				if(json!=null){
					$("#error_div").html('');
					var dataArr=json.data;
					//测试数据
					//dataArr=[{"id":"8","des":"故障信息","cat":"","pc":"","typ":"GDX2","val":"第一个: 36: 9&第二个: 15: 5&第三个: 29: 3&第四个: 2: 2&第五个: 21: 3&第六个: 2: 2&第七个: 7: 3&第八个: 7: 3&第九个: 7: 3&第十个: 7: 3","cacheVal":"TASCHESIGARETTEINCOMPLETE: 36: 9&MANCANZASIGARETTENELLATRAMOGGIA: 15: 5&STOPDISERVIZIO: 29: 3&MANCANZAETICHETTASULGOMMATORE: 2: 2&CONTROLLOPRES.ETIC.SULGOMMATOREINEF.: 21: 3&INGOLFOSPONDAELASTICA: 2: 2&STAGNOLAMANCANTE: 7: 3","variable":"","position":"","returnType":"String","formula":"","cv":null}];
					//dataArr=[{"id":"8","des":"故障信息","cat":"","pc":"","typ":"GDX2","val":"STOP DI SERVIZIO:120:4&AFFLUSSO COLLA ETICHETTA DISABILITATO:100:4&CONTROLLO STAGNOLA ESTERNO INEFFICIENTE:231:11&STAGNOLA MANCANTE:3:2&SPORTELLO 3A RUOTA APERTO:46:2&REGOLATORE DI COPPIA TRASPORT. CASSERINI:9:2&TASTATORI TRAMOGGIA INEFFICIENTI:609:3&MANCANZA SIGARETTE NELLA TRAMOGGIA:147:11&TASTATORI SX FILTRI INEFFICIENTI:13:5&AFFLUSSO COLLA ETICHETTA DISABILITATO:100:4&CONTROLLO STAGNOLA ESTERNO INEFFICIENTE:231:11&STAGNOLA MANCANTE:3:2&SPORTELLO 3A RUOTA APERTO:46:2&REGOLATORE DI COPPIA TRASPORT. CASSERINI:9:2&TASTATORI TRAMOGGIA INEFFICIENTI:609:3&MANCANZA SIGARETTE NELLA TRAMOGGIA:147:11&TASTATORI SX FILTRI INEFFICIENTI:13:5&TASCHE SIGARETTE INCOMPLETE:3:3&CARTER ANTERIORE APERTO:41:2&出口通道堵塞:35:2&INGOLFO SPONDA ELASTICA:46:2&TASCHE SIGARETTE INCOMPLETE:3:3&CARTER ANTERIORE APERTO:41:2&出口通道堵塞:35:2&INGOLFO SPONDA ELASTICA:46:2","position":"","returnType":"String","formula":"","cv":null}];			
					if(dataArr!="null" && dataArr!=null){	
						//包装机故障信息eqp范围 31-60
						if(json.eqp>=31 && json.eqp<=60){
							for(var i=0;i<dataArr.length;i++){
								//故障信息的id是8
								if(dataArr[i].id!=8) continue;
								if(dataArr[i].id==8){
									var value=dataArr[i].val;
									//console.info(value);
									if(value!=""){
										//拆分故障信息标准模板 "TASCHE SIGARETTE INCOMPLETE:44:13&MANCANZA ETICHETTA SUL GOMMATORE:14:6"
										var valArr=value.split('&');
										if(valArr.length>0){
											for(var j=0;j<valArr.length;j++){
												//TASCHE SIGARETTE INCOMPLETE:44:13
												var valArr1=valArr[j].split(':');
												var errorName=valArr1[0];
												//添加故障信息列表
												var str="<ul>";
															str+="<li class='error_lis' >▶&nbsp;"+errorName+" ：<font style='color:red;'>"+valArr1[2]+"</font>次  <font style='color:red;'>"+valArr1[1]+"</font>s"+"</li>";
												    str+="</ul>";
												$("#error_div").append(str);			
													}
												}
										}
									break;
								}	
							}
						}
				}else{
					$("#error_div").append("<font color='green'>设备运行良好</font>");
				}
			}
		},"JSON"); 
	}
	
</script>
</head>
<body>
<div id="body-center">
<div class="info_machine">
	<!-- 卸盘机 -->
	<div class="info_machine_left fl">
		<h1 class="fl" id="filter-title">滤棒装盘机</h1>
		<div class="fr machine_info">
			<p>设备型号：<span id="filter-sbxh">AAA</span>
			<p>装盘数量：<span id="filter-edcs">888.89</span>盘</p>
		</div>	
	</div>
	<!-- 主机 -->
	<div class="info_machine_cen fl" >
		<div class="fl" style="font-size:35px;margin-top:5px;margin-left:20px;">
			${loginInfo.equipmentName}
		</div>
		<div class="fl">
			<div class="fl" style="margin-top:15px;margin-left:15px;">
				<span>设备型号:</span>
				<span>${loginInfo.equipmentType}</span>
			</div>
			<div style="margin-top:15px;margin-left:15px;">
				<span>额定车速:</span>
				<span id="equipmentEdcs"></span>
			</div>
		</div>
	</div>
	<!-- 接收机 -->
	<div class="info_machine_right fr">
		<h1 class="fl" id="filter-title">滤棒装盘机</h1>
		<div class="fr machine_info">
			<p>设备型号：<span id="filter-sbxh">AAA</span>
			<p>装盘数量：<span id="filter-edcs">888.89</span>盘</p>
		</div>	
	</div>
</div>
<div class="info_area">
	<div>
		<div>
			<span class="t">工单:</span>
			<span id="workorderCode"></span>
		</div>
		<div>
			<span class="t">牌号:</span>
			<span id="matName"></span>
		</div>
	</div>
	<div>
		<div>
			<span class="t">计划开始时间:</span>
			<span id="planStim"></span>
		</div>
		<div>
			<span class="t">计划结束时间:</span>
			<span id="planEtim"></span>
		</div>
	</div>
	<div>
		<div>
			<span class="t">实际开始时间:</span>
			<span id="stim"></span>
		</div>
		<div>
			<span class="t">批次号:</span>
			<span id="bthCode"></span>
		</div>
	</div>
</div>
<div class="info_count">
	<div class="info_count_left fl">
		<!-- 成型机 -->
		<div class="left_output">
			<div class="info_count_left_status_box">
				<div class="progress_title">计划产量:</div>							
				<div class="progress w265">								
				  <div style="width: 100%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_b">
					
				  </div>
				</div>	
				<span id="planQty">0</span><span class="unit"></span>
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title">实际产量:</div>							
				<div class="progress w265">								
				  <div id="qty_w"  aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_g2">
					
				  </div>
				</div>	
				<span class="qty">0.00</span><span class="unit"></span>
			</div>
		</div>
		<!-- 成型机 tab -->
		<div class="depictCon" style="width: 266px;margin-left: 68px;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>
					<tr>
						<td class="depictTdC" width="100">滤棒成型纸(盘)</td>
					</tr>
					<tr>
						<td class="depictTdP " style="text-align: center;">消耗：<span  id="panzhiVal">0</span></td>
					</tr>
					<tr>
						<td class="depictTdP " >							
							<div class="fl" style="margin-right: 26px;margin-left: 60px;">
								<span  id="panzhiBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span  id="panzhiDh" class="data_span">0.0</span>
								<div class="progress-h c_h">
									<span class="gc_y yellow_h" id="panzhi_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>
											
					</tr>
					<!-- <tr>
						<td class="depictTdP " style="text-align: center;">单耗排名：<span  id="panzhiRank">0</span></td>
					</tr> -->
				</tbody></table>
		</div>
		<!-- 成型机运行统计 -->
		<div class="info_count_left_status w384" style="height:148px">	
			<h1>成型机运行统计</h1>						
			<div class="info_count_left_status_box">
				<div class="progress_title">运行时间:</div>							
				<div class="progress w208">								
				  <div id="runTime_w" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_g2"></div>
				</div>	
				<span class="progress_text" id="runTime">0</span><span>分钟</span>			
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title">停机时间:</div>							
				<div class="progress w208">
				  <div  id="stopTime_w" aria-valuemax="100" aria-valuemin="0" aria-valuenow="60" role="progressbar" class="progress-bar progress-fault gc_red"></div>
				</div>
				<span class="progress_text" id="stopTime">0</span><span>分钟</span>
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title">停机次数:</div>							
				<span class="progress_text" id="stopTimes">0</span><span>次</span>
			</div>								
		</div>
	</div>
	
	<div class="info_count_right fl">
		<!-- 烟机图面 -->
		<div class="info_count_left_status w553 filter_img">
			<div id="run_div">
					<div id="run_status">					
					<img src="${pageContext.request.contextPath}/wct/isp/img/run.gif"/>
				</div>
				<div id="run_info">					
					<div><span class="run_title"  id="runStatus"></span></div>
					<div style="margin-top:6px;"><span >车速:</span>&nbsp;<span style="font-size: 16px;font-weight: bold;"  id="speed"></span></div>
				</div>
				</div>
		</div>
		<!-- 成型机故障信息 -->
		<div class="info_count_left_status w265 error">	
			<h1>成型机故障信息 </h1>
			<div id="error_div">
				<!-- <ul >
					<li>故障信息</li>
					<li>9故障信息</li>
					<li>故障信息</li>
					<li>11故障信息</li>
					<li>故障信息</li>
				</ul>	 -->					
			</div>						
		</div>
		<!-- 系统通知 -->
		<div class="info_count_left_status w265 info">	
			<h1>系统通知</h1>
			<div id="info_div">
				<!-- <ul >
					<li>故障信息</li>
					<li>故障信息</li>
					<li>故障信息</li>
					<li>故障信息</li>
				</ul>		 -->				
			</div>
		</div>	
	</div>
</div>

</div>
</body>
</html>
