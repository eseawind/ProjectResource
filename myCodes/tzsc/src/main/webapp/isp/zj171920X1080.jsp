<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卷烟机实时监控 ZJ17</title>
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
	.info_count{width:100%;height:80%;}
	.info_count_left{width:425px;height:480px;overflow: hidden;border-right:1px solid #999999;}
	.left_output{width:405px;height:60px;padding: 10px 10px 10px 10px;}
	.info_count_right_status{width:560px;height:130px;padding: 10px;margin: 0 auto;}
	.info_count_right{width:582px;height:480px;overflow: hidden;border-left:1px solid #e6e5e5; }
	.font_info{text-align:center;font-weight:400;}
	.info_machine{width:100%;height:67px;background:#D6D6D6;}
	.info_machine_left{ width:600px;height:64px;background:#c0c0c0;border:1px solid #aaaaaa;}
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
		.font_10{font-size:15px!important;-webkit-text-size-adjust:none;line-height: 18px;width: 30px;padding-left:7px;display: block;}


.progress {margin-bottom: 15px;float: left;}
.info_count_left_status_box{height: 35px;width: 102%;line-height: 23px;}
.progress_title{float: left;margin-right:10px;}
#roller-jhcl,#roller-clsj,.progress_text{margin-left: 10px;font-weight: bold;}
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
	display:block;width:44px;height:20px;text-align:center;font-size: 15px;
}
/**/
.roller_img{
	height:311px;
	border:none;
	background-image:url(${pageContext.request.contextPath}/wct/isp/img/roller.png);
	-webkit-background-size:100% 100%;
	}
.error{height:148px;float:left;margin-top:17px;margin-left:4px;width:260px;font-size:18px;}
.info{height:148px;float:left;margin-top:17px;margin-left:4px;font-size:18px;}
.info_area div{float:left;width:318px;margin-left:8px;border:0px solid red;}
.info_area .t{font-size:16px;font-weight:bold;}
#info_div,#error_div{overflow: hidden;height:96px;margin-left:0px;font-size: 18px;}
#info_div li,#error_div li{height:20px;font-size:18px;}
#run_div{position: relative;top: 195px;left: 210px;}
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
	setInterval('AutoScroll("#info_div")',1000*8);
	setInterval('AutoScroll("#error_div")',1000*6);
	//单耗报警上限
	var lvbangEuval;
	var juanyanzhiEuval;
	var shuisongzhiEuval;
	var lvbangBzdh;
	var juanyanzhiBzdh;
	var shuisongzhiBzdh;
	var code=${param.code};
	/*var lbid;//滤棒辅料ID
	var sszid;//水松纸辅料id
	var jyzid;//卷烟纸辅料id
	var lbxs=1;
	var sszxs=1;
	var jyzxs=1;
	var matKey=[];//所有辅料id数组
	var matXs=[];//所有辅料系数，与下标key对应
	$(function(){
		$.post("${pageContext.request.contextPath}/plugin/combobox/loadMdMatParam.do",function(json){
			for(var k=0;k<json.length;k++){
				matKey.push(json[k].mat);
				matXs.push(json[k].val);
			}
		},"JSON")
	}); */

	function initRollerBaseInfo(){
		$.post("${pageContext.request.contextPath}/wct/isp/roller/initRollerBaseInfo.do",{"equipmentCode":code},function(json){
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
		    $("#equtype").html(json.equipmentType); //设备型号
		    $("#team").html(json.teamName);//班次
		    $("#shift").html(json.shiftName);//班次
		   /*  //初始化辅料id
		    lbid=json.lvbangMatId;
		    sszid=json.shuisongzhiMatId;
		    jyzid=json.juanyanzhiMatId;
		    //辅料id已经有值，在下面可以为辅料设置系数
		    for(var j=0;j<matKey.length;j++){
		    	if(matKey[j]==lbid){
		    		lbxs=matXs[j];
		    	}
		    	if(matKey[j]==sszid){
		    		sszxs=matXs[j];
		    	}
		    	if(matKey[j]==jyzid){
		    		jyzxs=matXs[j];
		    	}
		    } */
		    lvbangBzdh = json.lvbangBzdh;      
		    juanyanzhiBzdh = json.juanyanzhiBzdh;  
		    shuisongzhiBzdh = json.shuisongzhiBzdh; 		   
		    
		    $("#lvbangBzdh").html( (json.lvbangBzdh).toFixed(2) );  // 1.25
		    $("#juanyanzhiBzdh").html( (juanyanzhiBzdh).toFixed(0) );  // 3060
		    $("#shuisongzhiBzdh").html( (shuisongzhiBzdh).toFixed(0));  // 1.65
		    
		    lvbangEuval = json.lvbangEuval;          // 1.28
		    juanyanzhiEuval = json.juanyanzhiEuval;  // 3080
		    shuisongzhiEuval = json.shuisongzhiEuval;// 1.33
		},"JSON");
	}
	//初始化
	initRollerBaseInfo();
	getRollerData();
	var commonRequestTime=setInterval('getRollerData()',1000*4);
	//3秒钟更新一次
    function getRollerData(){
		$.post("${pageContext.request.contextPath}/wct/isp/roller/getRollerData.do",{"equipmentCode":code},function(json){
			//console.info(json);
			//装盘机
			//接收机
			//if("${loginInfo.equipmentCode}">=1&&"${loginInfo.equipmentCode}"<=30){//表示是卷烟机
			//	$("#roller-zpj-sbxh").html("YJ35C");
				////roller-zpj-edcs
				//$("#roller-zb-sbxh").html("YF26");
				//roller-zb-edcs
				//设置嘴棒接收机数量
				//$("#roller-zb-edcs").html(lvbangVal);
			//}

			var qty = json.qty;//总支数 转化成箱
			$(".qty").html(qty);
			
			$("#qty_w").css("width",(qty/$("#planQty").html()*100).toFixed(2)+"%");
			var lvbangVal = ((json.lvbangVal)/10000).toFixed(2);
			var juanyanzhiVal = (json.juanyanzhiVal).toFixed(0);
			var shuisongzhiVal = (json.shuisongzhiVal).toFixed(0);
			//shuisongzhiVal = shuisongzhiVal*(0.06*1.2)*0.001;
			//辅料 滤棒(万支)=原机数/10000
			//卷烟纸(米) =	原机数
			//水松纸(米)= 原机数
		    $("#lvbangVal").html(lvbangVal);// 2.75,
		    $("#juanyanzhiVal").html(juanyanzhiVal);// 1.02,
		    $("#shuisongzhiVal").html(shuisongzhiVal);// 1.68,
		    if(qty!=null&&qty!=""&&qty>0){
			    //实际单耗 = 消耗(单位)/实际产量(箱)
		    	 $("#lvbangDh").html((lvbangVal/qty).toFixed(2));
				 $("#juanyanzhiDh").html((juanyanzhiVal/qty).toFixed(0));
				 $("#shuisongzhiDh").html((shuisongzhiVal/qty).toFixed(0));
				    
				 $("#lvbang_h").css("height",((lvbangVal/qty)/lvbangBzdh*80).toFixed(2)+"%");
				 $("#juanyanzhi_h").css("height",((juanyanzhiVal/qty)/juanyanzhiBzdh*80).toFixed(0)+"%");
				 $("#shuisongzhi_h").css("height",((shuisongzhiVal/qty)/shuisongzhiBzdh*80).toFixed(0)+"%");
			}else{
				$("#lvbangDh").html("0.00");
				 $("#juanyanzhiDh").html("0");
				 $("#shuisongzhiDh").html("0");
				    
				 $("#lvbang_h").css("height",((0)/lvbangBzdh*80).toFixed(2)+"%");
				 $("#juanyanzhi_h").css("height",((0)/juanyanzhiBzdh*80).toFixed(0)+"%");
				 $("#shuisongzhi_h").css("height",((0)/shuisongzhiBzdh*80).toFixed(0)+"%");
			}
		  /*   $("#lvbangRank").html(json.lvbangRank.toFixed(0));// 1,
		    $("#juanyanzhiRank").html(json.juanyanzhiRank.toFixed(0));// 2,
		    $("#shuisongzhiRank").html(json.shuisongzhiRank.toFixed(0));// 3, */
		    
		    $("#runTime").html(json.runTime.toFixed(0));// 300,
		    $("#stopTime").html(json.stopTime.toFixed(0));// 300,
		    $("#runTime_w").css("width",(json.runTime/480*100).toFixed(2)+"%"); 
		    $("#stopTime_w").css("width",(json.stopTime/480*100).toFixed(2)+"%"); 
		    
		    $("#stopTimes").html(json.stopTimes.toFixed(0));// 1,
		    $("#speed").html(json.speed.toFixed(0));// 7000,
		    
		  //接收机
			if(code>=1&&code<=30){//表示是卷烟机
				$("#roller-zpj-sbxh").html("YJ35C");
				//roller-zpj-edcs
				$("#roller-zb-sbxh").html("YF26");
				//roller-zb-edcs
				//设置嘴棒接收机数量
				$("#roller-zb-edcs").html(lvbangVal);
			}
		    
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
		};
	
	
	//滚动条
	function AutoScroll(obj){
		if(obj=='#info_div'){ //通知
		    if($('.info_lis').length>=3){
		    	$(obj).find("ul:first").animate({
					marginTop:"-60px"
				},8000,function(){
					$(this).css({marginTop:"0px"}).find("li:last").appendTo(this);
				});
		    }
			
		}else{ //故障
			if($(".error_lis").length>=3){
				$(obj).find("ul:first").animate({
					marginTop:"-60px"
				},8000,function(){
					$(this).css({marginTop:"0px"}).find("li:last").appendTo(this);
				});
			}
		}
		
	}
	;
	/* 告警 */
	jinggao();
	setInterval('jinggao()',1000*60);
	function jinggao(){
		$("#error_div").html("");
		/* 系统通知 */
		$("#info_div").html("");
		$.get('${pageContext.request.contextPath}/msg/conwarn/getMsgInfo.do',function(json){
				if(json!=null){
					var inf="<ul>";
					for(var i=0;i<json.length;i++){
						inf+="<li class='info_lis' >▪<font style='color:red;'>"+json[i].title+":</font>&nbsp;<font style='font-size:18px;'>"+json[i].content+"</font></li>";
					}
					inf+="</ul>";
					$("#info_div").append(inf);
				}
		},"JSON");
	}
	//设备故障信息
	$(function(){
		//初始化数据
	guzhangxinxi();
	setInterval('guzhangxinxi()',1000*60);
	});
	function guzhangxinxi(){
		$("#error_div").html("");
		$.get('${pageContext.request.contextPath}/isp/get.do?',{"code":code},function(json){
			if(json!=null){
				$("#error_div").html('');
					var dataArr=json.data;
					if(dataArr!="null" && dataArr!=null){
						//卷烟机故障信息
						if(json.eqp>=1 && json.eqp<=30){
							for(var i=0;i<dataArr.length;i++){
								//故障信息的id是78 漏气支数
								if(dataArr[i].id==70||dataArr[i].id==78||dataArr[i].id==80||dataArr[i].id==81||dataArr[i].id==69){
											//添加故障信息列表
											if(dataArr[i].val!=""){
												var str="<ul >";
												/* str+="<li  style='overflow-y:hidden,width:270px;font-size:12px;white-space：nowrap'>"+""+dataArr[i].des+"\t："+dataArr[i].val+"</li>"; */
												str+="<li class='error_lis' >▶&nbsp;"+dataArr[i].des+" ：<font style='color:red;'>"+dataArr[i].val+"</font></li>";
												str+="</ul>"
												$("#error_div").append(str);
											}
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
<body style="height:1080px; ">

<div id="body-center" style="width:100%;height:100%;background-color: #D6D6D6;">
<div class="info_machine">
	
	<!-- 头部-->
	<div class="info_machine_left fl" style="width:33%;">
		<div class="fl" style="margin-top:0px;margin-left:20px;"><br>
		<span style="font-size: 20px;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${param.code==1}">1号卷烟机</c:if>
		<c:if test="${param.code==2}">2号卷烟机</c:if>
		<c:if test="${param.code==3}">3号卷烟机</c:if>
		<c:if test="${param.code==4}">4号卷烟机</c:if>
		<c:if test="${param.code==5}">5号卷烟机</c:if>
		<c:if test="${param.code==6}">6号卷烟机</c:if>
		<c:if test="${param.code==7}">7号卷烟机</c:if>
		<c:if test="${param.code==8}">8号卷烟机</c:if>
		<c:if test="${param.code==9}">9号卷烟机</c:if>
		<c:if test="${param.code==10}">10号卷烟机</c:if>
		<c:if test="${param.code==11}">11号卷烟机</c:if>
		<c:if test="${param.code==12}">12号卷烟机</c:if>
		</span>
		</div>
	</div>
	<div class="info_machine_left fl" style="width:33.5%;">
			<div class="fl" style="margin-top:15px;margin-left:15px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span style="font-size: 20px;">设备型号:</span>
				<span style="font-size: 20px;" id="equtype">-</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span style="font-size: 20px;">额定车速:</span>
				<span style="font-size: 20px;" id="equipmentEdcs">-</span>
			</div>
	</div>
	 <div class="info_machine_left fl" style="width:33%">
		<div align="left"><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span style="font-size: 20px;">班组：</span><span style="font-size: 20px;" id="team">-</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span style="font-size: 20px;">班次：</span><span style="font-size: 20px;" id="shift">-</span>
		</div>	
	</div>
</div>
<div class="info_area" style="width: 100%" >
	<div style="width: 32%;">
		<div>
			<span class="t" style="font-size: 18px;text-align:right;">工单:</span>
			<span id="workorderCode" style="font-size: 18px;text-align: right;"></span>
		</div>
		<div>
			<span class="t" style="font-size: 18px;text-align: right;">牌号:</span>
			<span id="matName" style="font-size: 18px;text-align: right;"></span>
		</div>
	</div>
	<div style="width: 32%;">
		<div>
			<span class="t">计划开始时间:</span>
			<span id="planStim"></span>
		</div>
		<div>
			<span class="t">计划结束时间:</span>
			<span id="planEtim"></span>
		</div>
	</div>
	<div style="width: 32%;">
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
<div class="info_count" style="width: 100%;margin-left:5%;">
	<div class="info_count_left fl" style="width: 40%;height: 100%;">
		<!-- 卷烟机 --><br><br>
		<div class="left_output">
			<div class="info_count_left_status_box">
				<div class="progress_title" style="font-size: 18px;">计划产量:</div>							
				<div class="progress w265">								
				  <div  aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_b">
					
				  </div>
				</div>	
				<span id="planQty" style="font-size: 18px;">0</span><span class="unit" style="font-size: 18px;"></span>
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title" style="font-size: 18px;">实际产量:</div>							
				<div class="progress w265">								
				  <div id="qty_w"  aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_g2">
					
				  </div>
				</div>	
				<span class="qty" style="font-size: 18px;">0.00</span><span class="unit" style="font-size: 18px;"></span>
			</div>
		</div>
		<!-- 卷烟机 tab --><br><br>
		<div class="depictCon" style="width:100%;height: 30%;">
			<table width="90%" height="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>
					<tr>
						<td class="depictTdC" width="33%" style="font-size: 18px;">滤棒(万支)</td>
						<td class="depictTdC" width="33%" style="font-size: 18px;">卷烟纸(米)</td>
						<td class="depictTdC br_r" width="33%" style="font-size: 18px;">水松纸(米)</td>						
					</tr>
					<tr>
						<td class="depictTdP " style="font-size: 18px;">消耗：<span  id="lvbangVal" style="font-size: 18px;">0</span></td>
						<td class="depictTdP " style="font-size: 18px;">消耗：<span  id="juanyanzhiVal" style="font-size: 18px;">0</span></td>
						<td class="depictTdP " style="font-size: 18px;">消耗：<span  id="shuisongzhiVal" style="font-size: 18px;">0</span></td>						
					</tr>
					<tr>
						<td class="depictTdP " >							
							<div class="fl" style="margin-right: 5px;width: 50%;">
								<span  id="lvbangBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%" ></span>
								</div>
								<span  class="font_10" >标准单耗</span>
							</div>
							<div class="fl">
								<span  id="lvbangDh" class="data_span" >0.0</span>
								<div class="progress-h c_h">
									<span class="gc_y yellow_h" id="lvbang_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>
						<td class="depictTdP ">							
							<div class="fl" style="margin-right: 5px;width: 50%;">
								<span  id="juanyanzhiBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span  id="juanyanzhiDh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span  class="gc_y yellow_h" id="juanyanzhi_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>
						<td class="depictTdP ">							
							<div class="fl" style="margin-right: 5px;width: 50%;">
								<span  id="shuisongzhiBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span  id="shuisongzhiDh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span  class="gc_y yellow_h" id="shuisongzhi_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>						
					</tr>
					<!-- <tr>
						<td class="depictTdP ">单耗排名：<span  id="lvbangRank">0</span></td>
						<td class="depictTdP" >单耗排名：<span  id="juanyanzhiRank">0</span></td>
						<td class="depictTdP" >单耗排名：<span  id="shuisongzhiRank">0</span></td>						
					</tr> -->
				</tbody></table>
		</div>
		<!-- 卷烟机运行统计 --><br><br>
		<div class="info_count_left_status w384" style="width:85%;height: 20%;margin-left:0.5%;">	
			<h1>卷烟机运行统计</h1>						
			<div class="info_count_left_status_box">
				<div class="progress_title" style="font-size: 18px;">运行时间:</div>							
				<div class="progress w208">								
				  <div id="runTime_w" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_g2"></div>
				</div>	
				<span class="progress_text" id="runTime"  style="font-size: 18px;">0</span><span  style="font-size: 18px;">分钟</span>			
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title"  style="font-size: 18px;">停机时间:</div>							
				<div class="progress w208">
				  <div  id="stopTime_w" aria-valuemax="100" aria-valuemin="0" aria-valuenow="60" role="progressbar" class="progress-bar progress-fault gc_red"></div>
				</div>
				<span class="progress_text" id="stopTime"  style="font-size: 18px;">0</span><span  style="font-size: 18px;">分钟</span>
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title" style="font-size: 18px;">停机次数:</div>							
				<span class="progress_text" id="stopTimes" style="font-size: 18px;">0</span><span style="font-size: 18px;">次</span>
			</div>								
		</div>
	</div>
	
	<div class="info_count_right fl"  style="width: 50%;height: 100%;">
		<!-- 烟机图面 -->
		<div class="info_count_left_status w553 roller_img" style="width: 100%;height: 46%;">
			<div id="run_div">
					<div id="run_status" style="margin-top:75px;margin-left:160px;">					
					<img src="${pageContext.request.contextPath}/wct/isp/img/run.gif"/>
				</div>
				<div id="run_info">					
					<div style="margin-top:75px;"><span class="run_title"  id="runStatus"></span></div>
					<div style="margin-top:6px;"><span >车速:</span>&nbsp;<span style="font-size: 16px;font-weight: bold;"  id="speed"></span></div>
				</div>
				</div>
		</div>
		<!-- 卷烟机故障信息 -->
		<div class="info_count_left_status w265 error" style="width: 45%;height: 20%;float: left;margin-top:4.5%;margin-left: 2%;">	
			<h1>卷烟机故障信息 </h1>
			<div id="error_div" >
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
		<div class="info_count_left_status w265 info" style="width: 45%;height: 20%;float: left;margin-top:4.5%;">	
			<h1>系统通知</h1>
			<div id="info_div">
			<!--
			<ul >
					<li style='overflow-y:hidden;width:270px;'>☼标题：系统通知1</li>
					<li style='overflow-y:hidden;width:270px;'>☼标题：系统通知2</li>
					<li style='overflow-y:hidden;width:270px;'>☼标题：系统通知3</li>
					<li style='overflow-y:hidden;width:270px;'>☼标题：系统通知4</li>
			</ul>	
			-->					
			</div>
		</div>	
	</div>
</div>

</div>
</body>
</html>
