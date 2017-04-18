<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>包装机实时监控 ZB45</title>
<style>
#packer-title{
  font-weight: bold;
  font-size: 20px;
  line-height: 60px;
  margin-left:20px;
  margin-right:20px;
}
.packer-title{
  font-weight: bold;
  font-size: 16px;
  line-height: 30px;
  

}
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
#packer-jhcl,#packer-clsj,.progress_text{margin-left: 10px;font-weight: bold;}
.progress-normal{background-color: #00BE00;}
.progress-fault{background-color: #E91616;}
.progress-stop{background-color: #E91616;}
.w265{width:265px;}
.w420{width:420px;}
.w470{width:470px;}
.w553{width:553px;}
.w588{width:588px;}
.w384{width:384px;}
.w208{width:208px;}
.info_count_left_status{margin: 0 auto;padding: 0 10px;border-radius:6px;border: 1px solid #ACABAB;}
.info_count_left_status h1{margin: 10px 0;font-size: 16px;font-weight: bold;text-align: center;line-height:26px;/* border-bottom:1px solid #858484; */background: #b4b4b4;color: #3C3C3C;border-radius: 4px 4px 0px 0px;}
/**/
.data_span{
	display:block;width:44px;height:20px;text-align:center
}
/**/
.packer_img{height:311px;border:none;background-image:url(${pageContext.request.contextPath}/wct/isp/img/packer.png);}
.error{height:148px;float:left;margin-top:17px;margin-left:4px;width:260px}
.info{height:148px;float:left;margin-top:17px;margin-left:4px;}
.info_area div{float:left;width:318px;margin-left:8px;border:0px solid red;}
.info_area .t{font-size:16px;font-weight:bold;}
#info_div,#error_div{overflow: hidden;height:96px;margin-left:0px}
#info_div li,#error_div li{font-size:12px;margin-bottom:8px;}
#run_div{position: relative;top: 230px;left: 155px;}
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

	$(function(){
 		var loginname = "${loginInfo.equipmentCode}";
		if(loginname==null || loginname=="" || loginname.length==0){
			window.location="${pageContext.request.contextPath}/wct/sys/login.jsp";
		} 
	}); 

	//单耗报警上限
	var xiaohemoEuval;//小盒膜报警上限
	var tiaohemoEuval;//条盒膜报警上限
	var xiaohezhiEuval;//小盒纸报警上限
	var tiaohezhiEuval;//条盒纸报警上限
	var neichenzhiEuval;//内衬纸报警上限
	var xiaohemoBzdh=0;
	var tiaohemoBzdh=0;
	var xiaohezhiBzdh=0;
	var tiaohezhiBzdh=0; 
	var neichenzhiBzdh=0;
    var xiaohemoMatId;//小盒膜物料id
	var tiaohemoMatId;//条盒膜物料id
	var xiaohezhMatId;//小盒纸物料id
	var tiaohezhMatId;//条盒纸物料id
	var neichenzMatId;//内衬纸物料id
	/* var xiaohemoxs=0.1;//小盒膜系数
	var tiaohemoxs=0.1;//条盒膜系数
	var xiaohezhixs=0.1;//小盒纸系数
	var tiaohezhixs=0.1;//条盒纸系数
	var neichenzhixs=0.1;//内衬纸系数 */
	function initPackerBaseInfo(){
		$.post("${pageContext.request.contextPath}/wct/isp/packer/initPackerBaseInfo.do",{"equipmentCode":"${loginInfo.equipmentCode}"},function(json){
			//
		    $("#equipmentEdcs").html(json.equipmentEdcs);  //7000支/分
		    $("#workorderCode").html(json.workorderCode);  //C22015010101#010
		    $("#matName").html(json.matName);  //白沙（精品）
		    $("#planQty").html(json.planQty);  //50.5
		   //测试代码
		   //$("#planQty").html(50); 
		    $(".unit").html(json.unit);  //箱
		    $("#planStim").html(json.planStim);  //计划开始时间
		   
		    $("#planEtim").html(json.planEtim);  //计划结束时间
		    $("#bthCode").html(json.bthCode);  //C22015010101
		    $("#stim").html(json.stim);  //
		    
		    /*  
		    xiaohemoMatId=json.xiaohemoMatId;//小盒膜物料id
			tiaohemoMatId=json.tiaohemoMatId;//条盒膜物料id
			xiaohezhMatId=json.xiaohezhMatId;//小盒纸物料id
			tiaohezhMatId=json.tiaohezhMatId;//条盒纸物料id
			neichenzMatId=json.neichenzMatId;//内衬纸物料id 
			*/
		    xiaohemoBzdh = json.xiaohemoBzdh;//小盒膜标准单耗
			tiaohemoBzdh = json.tiaohemoBzdh;//条盒膜标准单耗
			xiaohezhiBzdh = json.xiaohezhiBzdh;//小盒纸标准单耗
			tiaohezhiBzdh = json.tiaohezhiBzdh;//条盒纸标准单耗
			neichenzhiBzdh = json.neichenzhiBzdh;//内衬纸标准单耗
			
			
		    $("#xiaohemoBzdh").html(json.xiaohemoBzdh);//小盒膜标准单耗
			$("#tiaohemoBzdh").html(json.tiaohemoBzdh);//条盒膜标准单耗
			$("#xiaohezhiBzdh").html(json.xiaohezhiBzdh);//小盒纸标准单耗
			$("#tiaohezhiBzdh").html(json.tiaohezhiBzdh);//条盒纸标准单耗
			$("#neichenzhiBzdh").html(json.neichenzhiBzdh);//内衬纸标准单耗
			
			/*
			xiaohemoEuval = json.xiaohemoEuval;//小盒膜报警上限
			tiaohemoEuval = json.tiaohemoEuval;//条盒膜报警上限
			xiaohezhiEuval = json.xiaohezhiEuval;//小盒纸报警上限
			tiaohezhiEuval = json.tiaohezhiEuval;//条盒纸报警上限
			neichenzhiEuval = json.neichenzhiEuval;//内衬纸报警上限
			*/
		},"JSON");
	}
	//初始化标准
	initPackerBaseInfo();
	
	
	//通知滚动
	setInterval('AutoScroll("#info_div")',1000*8);
	//故障滚动
	setInterval('AutoScroll("#error_div")',1000*8); 
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
			var error_Info_length=$('.error_lis').length;
			 if(error_Info_length>=4){
				var marginTop_=-30*error_Info_length;
		    	$(obj).find("ul:first").animate({
					marginTop:marginTop_+"px"
				},30000,function(){
					$(this).css({marginTop:"0px"}).find("li:last").appendTo(this);
				});
		     }
		}
		
	};

	
	//封装数据（实时数据 2s查询一次）
	function getPackerData(){
		$.post("${pageContext.request.contextPath}/wct/isp/packer/getPackerData.do",{"equipmentCode":"${loginInfo.equipmentCode}"},function(json){
			if(json.tsQty<0){
				//条烟提升机
				$("#packer-tsj-edcs").html(0+"（"+0+"箱）");
			}else{
				//条烟提升机
				$("#packer-tsj-edcs").html((json.tsQty).toFixed(2)+"（"+((json.tsQty)/250).toFixed(2)+"箱）");
			}
			
			//烟支卸盘机
			if("${loginInfo.equipmentCode}">=31&&"${loginInfo.equipmentCode}"<=60){//表示是 包装机
				$("#packer-tsj-sbxh").html("YF56");
				$("#packer-xpj-sbxh").html("YB18A");
				$("#packer-xpj-edcs").html("0");
			}
			var qty = json.qty;
			$(".qty").html(qty);// 2.03,
			$("#qty_w").css("width",(qty/$("#planQty").html()*89.4).toFixed(2)+"%");
			var xiaohemoVal = json.xiaohemoVal.toFixed(2);
			var tiaohemoVal = json.tiaohemoVal.toFixed(2);
			var xiaohezhiVal = json.xiaohezhiVal.toFixed(0);
			var tiaohezhiVal = json.tiaohezhiVal.toFixed(0);
			var neichenzhiVal = json.neichenzhiVal.toFixed(2);
			//消耗排名 
			/* var xiaohemoRank = json.xiaohemoRank.toFixed(0);
			var tiaohemoRank = json.tiaohemoRank.toFixed(0);
			var xiaohezhiRank = json.xiaohezhiRank.toFixed(0);
			var tiaohezhiRank = json.tiaohezhiRank.toFixed(0);
			var neichenzhiRank = json.neichenzhiRank.toFixed(0); */
			//消耗值
			$("#xiaohemoVal").html(xiaohemoVal);
			$("#tiaohemoVal").html(tiaohemoVal);
			$("#xiaohezhiVal").html(xiaohezhiVal);
			$("#tiaohezhiVal").html(tiaohezhiVal);
			$("#neichenzhiVal").html(neichenzhiVal);
			
			//实际单耗 
			if(qty!=null&&qty!=""&&qty>0){
				//使用产量去计算单耗
				$("#xiaohemoDh").html(( xiaohemoVal/qty).toFixed(2)); //小盒透明纸(千克)
				$("#tiaohemoDh").html(( tiaohemoVal/qty).toFixed(2)); // 条盒透明纸(千克)
				$("#xiaohezhiDh").html(( xiaohezhiVal/qty).toFixed(0));//小盒纸(张)
				$("#tiaohezhiDh").html(( tiaohezhiVal/qty).toFixed(0));//条盒纸(张)
				$("#neichenzhiDh").html(( neichenzhiVal/qty).toFixed(2));
				//图形控制
                $("#xiaohemo_h").css("height",( (xiaohemoVal/qty)/xiaohemoBzdh*80).toFixed(2)+"%");
				$("#tiaohemo_h").css("height",( (tiaohemoVal/qty)/tiaohemoBzdh*80).toFixed(2)+"%");
				$("#xiaohezhi_h").css("height",( (xiaohezhiVal/qty)/xiaohezhiBzdh*80).toFixed(2)+"%");
				$("#tiaohezhi_h").css("height",( (tiaohezhiVal/qty)/tiaohezhiBzdh*80).toFixed(2)+"%");
				$("#neichenzhi_h").css("height",( (neichenzhiVal/qty)/neichenzhiBzdh*80).toFixed(2)+"%");

             }else{
				 $("#xiaohemoDh").html(("0.00"));
				 $("#tiaohemoDh").html(("0.00"));
				 $("#xiaohezhiDh").html(("0"));
				 $("#tiaohezhiDh").html(("0"));
				 $("#neichenzhiDh").html(("0.00"));
				//图形控制
				$("#xiaohemo_h").css("height",(0)+"%");
				$("#tiaohemo_h").css("height",(0)+"%");
				$("#xiaohezhi_h").css("height",(0)+"%");
				$("#tiaohezhi_h").css("height",(0)+"%");
				$("#neichenzhi_h").css("height",(0)+"%");
			}
		    //运行时间
		    $("#runTime").html((json.runTime).toFixed(0));// 300,
		    $("#stopTime").html((json.stopTime).toFixed(0));// 300,
		    //console.info(json.stopTime+"      "+json.stopTime/60);
		    //运行柱状图
		    $("#runTime_w").css("width",(json.runTime/480*100).toFixed(2)+"%"); 
		    $("#stopTime_w").css("width",(json.stopTime/480*100).toFixed(2)+"%"); 
		    //停机次数
		    $("#stopTimes").html(json.stopTimes.toFixed(0));// 1,
		    //网络
		    $("#speed").html(json.speed.toFixed(0));// 7000,
		    var runStatus = "网络断开";
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
	//进来初始化一次最新数据，然后6秒钟请求一次；
	getPackerData();
	var commonRequestTime=setInterval('getPackerData()',1000*4);
	
	
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
	<!-- 条烟提升机 -->
	<div class="info_machine_left fl">
		<span class="fl" id="packer-title">条烟提升机</span>
		<div class="packer-title">
			<p>设备型号：<span id="packer-tsj-sbxh">-</span>
			<p>提升条数：<span id="packer-tsj-edcs">0箱</span></p>
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
				<span>额定车速</span>
				<span id="equipmentEdcs"></span>
			</div>
		</div>
	</div>
	<!-- 卸盘机 -->
	<div class="info_machine_right fr">
		<span  class="fl" id="packer-title">烟支卸盘机</span>
		<div class="packer-title">
			<p>设备型号：<span id="packer-xpj-sbxh">-</span>
			<p>卸盘数量：<span id="packer-xpj-edcs">-</span>盘</p>
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
		<!-- 烟机图面 -->
		<div class="info_count_left_status packer_img" >
				<div id="run_div">
					<div id="run_status">					
					<img src="${pageContext.request.contextPath}/wct/isp/img/run.gif"/>
				</div>
				<div id="run_info">					
					<div><span class="fontblue run_title" id="runStatus"></span></div>
					<div style="margin-top:6px;"><span >车速:</span>&nbsp;<span style="font-size: 16px;font-weight: bold;"  id="speed"></span></div>
				</div>
				</div>
		</div>
		<!-- 包装机运行统计 -->
		<!-- 包装机故障信息 -->
		<div class="info_count_left_status error"  style="width:200px;">	
			<h1>包装机故障信息 </h1>
			<div id="error_div">
			</div>						
		</div>
		<!-- 系统通知 -->
		<div class="info_count_left_status info" style="width:168px;">	
			<h1>系统通知</h1>
			<div id="info_div">
				<!-- <ul >
					<li>故障信息</li>
				</ul>	 -->					
			</div>
		</div>	
	</div>
	
	<div class="info_count_right fl">
		<!-- 包装机 -->
		<div class="left_output" style="width: 562px;">	
			<div class="info_count_left_status_box">
				<div class="progress_title">计划产量:</div>							
				<div class="progress w420">								
				  <div style="width: 100%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_b">
					
				  </div>
				</div>	
				<span id="planQty">0</span><span class="unit"></span>
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title">实际产量:</div>							
				<div class="progress w420" >	
				  <div id="qty_w"  aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_g2"></div>
				</div>	
				<span class="qty">0.0</span><span class="unit"></span>
			</div>
		</div>
		<!-- 包装机 tab -->
		<div class="depictCon">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>									
					<tr>
						<td class="depictTdC" width="100">小盒纸(张)</td>
						<td class="depictTdC" width="100">小盒透明纸(千克)</td>
						<td class="depictTdC" width="100">内衬纸(千克)</td>						
						<td class="depictTdC" width="100">条盒纸(张)</td>						
						<td class="depictTdC br_r" width="100">条盒透明纸(千克)</td>						
					</tr>
					<tr>
						<td class="depictTdP " >消耗：<span id="xiaohezhiVal">0</span></td>
						<td class="depictTdP " >消耗：<span id="xiaohemoVal">0</span></td>
						<td class="depictTdP " >消耗：<span id="neichenzhiVal">0</span></td>						
						<td class="depictTdP " >消耗：<span id="tiaohezhiVal">0</span></td>						
						<td class="depictTdP " >消耗：<span id="tiaohemoVal">0</span></td>						
					</tr>
					<tr>
						<td class="depictTdP ">							
							<div class="fl" style="margin-right: 5px;">
								<span id="xiaohezhiBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span id="xiaohezhiDh" class="data_span">0.0</span>
								<div class="progress-h c_h">
									<span class="gc_y yellow_h" id="xiaohezhi_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>
						<td class="depictTdP ">							
							<div class="fl" style="margin-right: 5px;">
								<span id="xiaohemoBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span id="xiaohemoDh" class="data_span">0.0</span>
								<div class="progress-h c_h">
									<span  class="gc_y yellow_h" id="xiaohemo_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>
						<td class="depictTdP ">							
							<div class="fl" style="margin-right: 5px;">
								<span id="neichenzhiBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span id="neichenzhiDh" class="data_span">0.0</span>
								<div class="progress-h c_h">
									<span  class="gc_y yellow_h" id="neichenzhi_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>
						<td class="depictTdP ">							
							<div class="fl" style="margin-right: 5px;">
								<span id="tiaohezhiBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span id="tiaohezhiDh" class="data_span">0.0</span>
								<div class="progress-h c_h">
									<span  class="gc_y yellow_h" id="tiaohezhi_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>
						<td class="depictTdP ">							
							<div class="fl" style="margin-right: 5px;">
								<span id="tiaohemoBzdh" class="data_span">0</span>
								<div class="progress-h c_h">
									<span class="gc_g green_h" style="height: 80%"></span>
								</div>
								<span  class="font_10">标准单耗</span>
							</div>
							<div class="fl">
								<span id="tiaohemoDh" class="data_span">0.0</span>
								<div class="progress-h c_h">
									<span  class="gc_y yellow_h" id="tiaohemo_h"></span>
								</div>
								<span  class="font_10">实际单耗</span>
							</div>
						</td>						
					</tr>
					<!-- <tr>
						<td class="depictTdP ">单耗排名：<span id="xiaohezhiRank">0</span></td>
						<td class="depictTdP" >单耗排名：<span id="xiaohemoRank">0</span></td>
						<td class="depictTdP" >单耗排名：<span id="neichenzhiRank">0</span></td>						
						<td class="depictTdP" >单耗排名：<span id="tiaohezhiRank">0</span></td>						
						<td class="depictTdP" >单耗排名：<span id="tiaohemoRank">0</span></td>						
					</tr> -->
				</tbody></table>
		</div>
		<!-- 包装机运行统计 -->
		<div class="info_count_left_status w553">	
			<h1>包装机运行统计</h1>						
			<div class="info_count_left_status_box">
				<div class="progress_title">运行时间:</div>							
				<div class="progress w420">								
				  <div id="runTime_w" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-normal gc_g2"></div>
				</div>	
				<span  class="progress_text" id="runTime">0</span><span>分钟</span>				
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title">停机时间:</div>							
				<div class="progress w420">
				  <div  id="stopTime_w" aria-valuemax="100" aria-valuemin="0" aria-valuenow="60" role="progressbar" class="progress-bar progress-fault gc_red"></div>
				</div>
				<span class="progress_text" id="stopTime">0</span><span>分钟</span>
			</div>
			<div class="info_count_left_status_box">
				<div class="progress_title">停机次数:</div>							
				<!-- <div class="progress w420">
				  <div id="bzj-pg-stop-times" aria-valuemax="100" aria-valuemin="0" aria-valuenow="80" role="progressbar" class="progress-bar progress-stop gc_red"></div>
				</div> -->
				<span class="progress_text" id="stopTimes">0</span><span>次</span>
			</div>								
		</div>
	</div>
</div>

</div>
</body>
</html>
