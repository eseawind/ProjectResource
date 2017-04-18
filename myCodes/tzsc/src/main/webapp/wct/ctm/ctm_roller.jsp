<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>卷烟机实时成本考核监控</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/ctm/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<style>
.progress {margin-bottom: 0px;width: 330px;float: left;height:12px;}
.progress .progresstop {padding: 5px;width: 330px;float: left;height:35px;}
</style>
<script type="text/javascript">
	$(function(){
		var orderNumber="";
		var eqpcode=${loginInfo.equipmentCode};
		var initRollerIn=function(){
			$.post("${pageContext.request.contextPath}/wct/costs/initEquipmentRollerInput.do",{"ecode":eqpcode},function(json){
				var rows = json;
				var htmls = "";
				var current = "";
				var currentId = "${loginInfo.equipmentCode}";
				var data=rows;
				if(currentId == data.eqpCod){
					current = "current";
				}else{
					current = "";
				}
				var mat = data.mat;
				var juanyanzhiBzdh = data.juanyanzhiBzdh;
				var shuisongzhiBzdh = data.shuisongzhiBzdh;
				var lvbangBzdh = data.lvbangBzdh;
				var id = data.eqpCod;
				var unit = data.unit;
				var html = "	<div class='single_info_data "+current+"'>	                                       "
				//卷烟纸
				+"	<div class='single_info_01 w220'>           "
				+"		<div class='single_info_bzdh w210'>     "
				+"			<div class='progress_title'>标准单耗</div>	"
				+"			<div class='progress w110'>								                                       "
				+"			  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>			"			
				+"			  </div>                            "
				+"			</div>	                            "
				+"			<div><span class='sr-only' id='juanyanzhiBzdh"+id+"'>"+juanyanzhiBzdh+"</span></div>	                                               "
				+"		</div>                                  "
				+"		<div class='single_info_dqdh w210'>							                                       "
				+"				<div class='progress_title'>当前单耗</div>							                           "
				+"				<div class='progress w110'>								                                   "
				+"				  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='juanyanzhiPst"+id+"' style='width: 0%'>		"				
				+"				  </div>                        "
				+"				</div>	                        "
				+"				<div><span class='sr-only' id='juanyanzhiSjdh"+id+"'></span></div>                                             "
				+"		</div>                                  "
				+"		<div class='single_info_dqhy w210'><a>当前耗用：<span id='juanyanzhiIn"+id+"'></span>米</a></div>  "
				+"		<div class='single_info_dqhy w210'><a>当前成本：<span id='juanyanzhiSumPrice"+id+"'>0</span>元</a></div>  "
				+"	</div>                                      "
				//水松纸
				+"	<div class='single_info_04 w220'>           "
				+"		<div class='single_info_bzdh w210'>     "
				+"			<div class='progress_title'>标准单耗</div>	"
				+"			<div class='progress w110'>								                                       "
				+"			  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100'  style='width: 80%'>			"			
				+"			  </div>                            "
				+"			</div>	                            "
				+"			<div><span class='sr-only' id='shuisongzhiBzdh"+id+"'>"+shuisongzhiBzdh+"</span></div>	                                               "
				+"		</div>                                  "
				+"		<div class='single_info_dqdh w210'>							                                       "
				+"				<div class='progress_title'>当前单耗</div>							                           "
				+"				<div class='progress w110'>								                                   "
				+"				  <div class='progress-bar progress-normal  gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='shuisongzhiPst"+id+"' style='width: 0%'>		"				
				+"				  </div>                        "
				+"				</div>	                        "
				+"				<div><span class='sr-only' id='shuisongzhiSjdh"+id+"'>"+"</span></div>   "
				+"		</div>                                  "
				+"		<div class='single_info_dqhy w210'><a>当前耗用：<span id='shuisongzhiIn"+id+"'></span>公斤</a></div>                          "
				+"		<div class='single_info_dqhy w210'><a>当前成本：<span id='juanyanzhiSumPrice"+id+"'>0</span>元</a></div>  "
				+"	</div>                                      "
				//滤棒
				+"	<div class='single_info_05 w220'>           "
				+"		<div class='single_info_bzdh w210'>     "
				+"			<div class='progress_title'>标准单耗</div>	"
				+"			<div class='progress w110'>								                                       "
				+"			  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>			"			
				+"			  </div>                            "
				+"			</div>	                            "
				+"			<div><span class='sr-only' id='lvbangBzdh"+id+"'>"+lvbangBzdh+"</span></div>	                                               "
				+"		</div>                                  "
				+"		<div class='single_info_dqdh w210'>							                                       "
				+"				<div class='progress_title'>当前单耗</div>							                           "
				+"				<div class='progress w110'>								                                   "
				+"				  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='lvbangPst"+id+"' style='width: 0%'>		"				
				+"				  </div>                        "
				+"				</div>	                        "
				+"				<div><span class='sr-only'  id='lvbangSjdh"+id+"'></span></div>                                             "
				+"		</div>                                  "
				+"		<div class='single_info_dqhy w210'><a>当前耗用：<span id='lvbangIn"+id+"'></span>万支</a></div>                          "
				+"		<div class='single_info_dqhy w210'><a>当前成本：<span id='lvbangSumPrice"+id+"'>0</span>元</a></div>  "
				+"	</div>            "
				+"</div> "
				htmls=htmls+html;
				$("#data-div").html("");
				$("#data-div").html(htmls);
			},"JSON");
		};

		initRollerIn();
		
		//最高产量拟定为100箱,用于控制进度条最大程度(实际情况无法达到这一值)
		var maxVal=100;
		var initEquipment4OutIsp=function(){
			$.post("${pageContext.request.contextPath}/wct/costs/initOutDataPage.do",{"ecode":eqpcode},function(json){
					var data=json;
					if(data==null||data==""){
						$("#matDes").html("当前无运行工单");
						$("#eqpName1").html("当前无运行工单");
					}else{
						orderNumber=data.orderNumber;
						//进度条控制
						//计划产量
						//var planOutPst=0;
						var planOut=data.planOut;
						if(planOut>0){
							planOutPst=(planOut/maxVal)*100;
						}
						//实际产量
						var curOutPst=0;
						var curOut=data.curOut;
						if(curOut>0){
							curOutPst=(curOut/maxVal)*100;
						}
						if(data.planOut==0){
							curOut="当前班次无工单";
						}
						var current= "";
						if(data.eqpCod=="${loginInfo.equipmentCode}"){
							current= "current";
						}
						//实际产量
						$("#roller_curOutPst101").css("width",curOutPst+"%");
						$("#matDes").html("牌号:"+data.mat+"  计划产量"+data.planOut+data.unit);
						$("#eqpName1").html(data.eqpName);
						$("#shiftName").html(data.shift);
						$("#teamName").html(data.team);
						$("#matUnit").html(data.unit);
					}
					
			},"JSON");
		};
		//获取工单计划产量班次等信息
		initEquipment4OutIsp();
		
		window.setInterval(function(){
			//请求卷烟机实时辅料消耗数据
			$.post("${pageContext.request.contextPath}/wct/costs/getRollerInputData.do",{"ecode":eqpcode,"orderNumber":orderNumber},function(json){
				var data=json;
				var id=data.eqpCod;
				
				var qty = data.qty.toFixed(2);
				$("#roller_curOutPst101").css("width",qty+"%");
				$("#roller_curOut101").html(qty);
				var juanyanzhiIn=data.juanyanzhiIn.toFixed(2);
				
				var juanyanzhiSjdh=(juanyanzhiIn*1.00/qty).toFixed(2);
				var juanyanzhiBzdh=$("#juanyanzhiBzdh"+id).html();
				var juanyanzhiPst=(juanyanzhiSjdh/juanyanzhiBzdh*0.8)*100;
				$("#juanyanzhiIn"+id).html(juanyanzhiIn);
				$("#juanyanzhiSjdh"+id).html(juanyanzhiSjdh);
				$("#juanyanzhiPst"+id).css("width",juanyanzhiPst+"%");
				$("#juanyanzhiSumPrice"+id).html(data.juanyanzhiSumPrice);
				var shuisongzhiIn=data.shuisongzhiIn.toFixed(2);
				var shuisongzhiSjdh=(shuisongzhiIn*1.00/qty).toFixed(2);
				var shuisongzhiBzdh=$("#shuisongzhiBzdh"+id).html();
				var shuisongzhiPst=(shuisongzhiSjdh/shuisongzhiBzdh*0.8)*100;
				$("#shuisongzhiIn"+id).html(shuisongzhiIn);
				$("#shuisongzhiSjdh"+id).html(shuisongzhiSjdh);
				$("#shuisongzhiPst"+id).css("width",shuisongzhiPst+"%");
				$("#shuisongzhiSumPrice"+id).html(data.shuisongzhiSumPrice);
				var lvbangIn=data.lvbangIn.toFixed(2);
				var lvbangSjdh=(lvbangIn/qty).toFixed(2);
				var lvbangBzdh=$("#lvbangBzdh"+id).html();
				var lvbangPst=(lvbangSjdh/lvbangBzdh*0.8)*100;
				var lvbangSumPrice=data.lvbangSumPrice;
				$("#lvbangIn"+id).html(lvbangIn);
				$("#lvbangSjdh"+id).html(lvbangSjdh);
				$("#lvbangPst"+id).css("width",lvbangPst+"%");
				$("#lvbangSumPrice"+id).html(lvbangSumPrice);
				$("#out_top1").html("卷接机实时产量："+qty+"箱");
				$("#out_last1").html("卷接机辅料总成本："+data.rollerSumPrice+"元");
			},"JSON");
			
		}, 3000);
		setInterval('AutoScroll(".machine_info")',3000);
	});
	function AutoScroll(obj){
		jQuery(obj).find("ul:first").animate({
			marginTop:"-30px"
		},500,function(){
			jQuery(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
		});
	}
	$("#botton_show1").click(function(){$("#scroll_box01").animate({ left: "-568px"}, 1000 );});	$("#botton_hide1").click(function(){$("#scroll_box01").animate({ left: "0px"}, 1000 );});
	function slideUp(x){
		var top2 = x - 435;
		if(x != 0){
			$(".scroll_box").animate({ top:'-'+top2+'px'}, 500 );$("#slideUp").attr("value",top2);$("#slideDown").attr("value",top2);
		}else if(x == 0){jAlert('这是页面最顶部了', '系统提示');}
	}
	function slideDown(x){		
		var top = x*1 + 435*1;
		if(x <= 870){ $(".scroll_box").animate({ top: '-'+top+'px'}, 500 );$("#slideDown").attr("value",top);$("#slideUp").attr("value",top);
		}else{jAlert('这已经是页面的最底部了', '系统提示');}
	}
</script>
</head>
<body>


<div id="prods-idx-content">
	<div id="wkd-qua-title">卷烟机实时成本考核监控</div>
	<div class="info_machine">	
		<div class="info_machine_cen fl">
			<ul class="nav nav-tabs" id="tabs">
				<li><a class="tab_1" id="current" code="${loginInfo.equipmentCode}">${loginInfo.equipmentName}</a></li><!-- ${loginGroup.eqps['roller'].des} -->
			</ul>		
		</div>
		<div class="info_machine_right fl">
			<div class="fl machine_info" style="overflow: hidden">
				<ul style="margin-left: -30px;">
					<li><a href="javascript:void(0);" id="out_top1"  style="color:#13CF0D">实时数据加载中...</a></li>
					<li><a href="javascript:void(0);" id="out_last1" style="color:#2890E7">实时数据加载中...</a></li>
				</ul> 	
			</div>	
		</div>
		<div class="info_machine_cen fr " style="width:260px;margin-right: 5px;height: 35px;">
		<div id="button_tab1">
		<!-- <button id="slideUp"  value="0" onclick="slideUp(document.getElementById('slideUp').getAttribute('value'))" class="btn btn-default" title="点击按钮向上" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-up"></button>
		<button id="slideDown"  value="0" onclick="slideDown(document.getElementById('slideDown').getAttribute('value'))" class="btn btn-default" title="点击按钮向下" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-down"></span></button>		
		 --></div>
		</div>
	</div>

	<div id="tab1" class="xhtj">
		<div style="height:9px;width:100%;"></div>
		<div class="info_status " >	
			<div class="cltj_box">
				<div class="cltj_box_title ">
				<span class="eqpName101" id="eqpName1" unit="万支"></span>  
				<span id="teamName"></span>	<span class="classes" i="shiftName"></span>
				</div>
				<div class="cltj_progress_box">
					<div class="progress progresstop" style="width:570px;height:33px;">
						<div class="progress-bar progress-normal gc_g3" role="progressbar"  aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 60.00%;" id="roller_curOutPst101"></div>
						<div class="pointer" style="left:60%">
							<div class="pointer_info">
							<span id="matDes">牌号:*  计划产量*万支</span>
							</div>
						</div>
					</div>
					<span class="progress_box_info roller_curOut roller_curOut101" id="roller_curOut101" value="101">60.00</span> <span id="matUnit">万支</span>
				</div>
			</div>
			<div id="content">			
					<div class="single_info" id="scroll_box01">
						<div class="single_info_box">
							<div class="single_info_left">卷烟纸</div>
							<div class="single_info_title">水松纸</div>
							<div class="single_info_title">滤棒</div>							
						</div>
						<div class="single_info_xiaohao" id="scroll_box1" style="margin: 0;">
							<div id="data-div" class="scroll_box"></div>
						</div>
					</div>
			</div>	
		</div>
	</div>
</div>
</body>
</html>