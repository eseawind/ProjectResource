<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>卷烟机实时消耗监控</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<style>
.progress {margin-bottom: 0px;width: 330px;float: left;height:12px;}
</style>
<script type="text/javascript">
var dangqianQty;
	$(function(){
		var initRollerIn=function(){
			$.post("${pageContext.request.contextPath}/wct/stat/initEquipment4RollerInput.do",function(json){
				var rows = json;
				var htmls = "";
				var current = "";
				var currentId = "${loginInfo.equipmentCode}";
				for(var i=0;i<rows.length;i++){
					var data=rows[i];
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
				var html = "	<div class='single_info_data "+current+"'>																															"
				+"	<div class='single_info_number'>                                                                                                                                  "
				+"		<span>"+data.eqpName+" "+data.team+"</span><br>                                                                                                                                       "
				+"		<span>"+mat+"</span><br>                                                                                                                                       "
				+"		<span id='qty"+id+"'>0</span><span>"+unit+"</span>                                                                                                                                "
				+"	</div>                                                                                                                                                              "
				//卷烟纸
				+"	<div class='single_info_01 w220'>                                                                                                                                 "
				+"		<div class='single_info_bzdh w210'>                                                                                                                           "
				+"			<div class='progress_title'>标准单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only' id='juanyanzhiBzdh"+id+"'>"+juanyanzhiBzdh+"</span></div>	                                                                                                          "
				+"		</div>                                                                                                                                                        "
				+"		<div class='single_info_dqdh w210'>							                                                                                                  "
				+"				<div class='progress_title'>当前单耗</div>							                                                                                      "
				+"				<div class='progress w110'>								                                                                                              "
				+"				  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='juanyanzhiPst"+id+"' style='width: 0%'>		"				
				+"				  </div>                                                                                                                                              "
				+"				</div>	                                                                                                                                              "
				+"				<div><span class='sr-only' id='juanyanzhiSjdh"+id+"'></span></div>                                                                                                        "
				+"		</div>                                                                                                                                                        "
				+"		<div class='single_info_dqhy w210'><a>当前耗用：<span id='juanyanzhiIn"+id+"'></span>米</a></div>                                                                                     "
				+"	</div>                                                                                                                                                            "
				//水松纸
				+"	<div class='single_info_04 w220'>                                                                                                                                 "
				+"		<div class='single_info_bzdh w210'>                                                                                                                           "
				+"			<div class='progress_title'>标准单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100'  style='width: 80%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only' id='shuisongzhiBzdh"+id+"'>"+shuisongzhiBzdh+"</span></div>	                                                                                                          "
				+"		</div>                                                                                                                                                        "
				+"		<div class='single_info_dqdh w210'>							                                                                                                  "
				+"				<div class='progress_title'>当前单耗</div>							                                                                                      "
				+"				<div class='progress w110'>								                                                                                              "
				+"				  <div class='progress-bar progress-normal  gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='shuisongzhiPst"+id+"' style='width: 0%'>		"				
				+"				  </div>                                                                                                                                              "
				+"				</div>	                                                                                                                                              "
				+"				<div><span class='sr-only' id='shuisongzhiSjdh"+id+"'>"+"</span></div>                                                                                                        "
				+"		</div>                                                                                                                                                        "
				+"		<div class='single_info_dqhy w210'><a>当前耗用：<span id='shuisongzhiIn"+id+"'></span>公斤</a></div>                                                                                     "
				+"	</div>                                                                                                                                                            "
				//滤棒
				+"	<div class='single_info_05 w220'>                                                                                                                                 "
				+"		<div class='single_info_bzdh w210'>                                                                                                                           "
				+"			<div class='progress_title'>标准单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only' id='lvbangBzdh"+id+"'>"+lvbangBzdh+"</span></div>	                                                                                                          "
				+"		</div>                                                                                                                                                        "
				+"		<div class='single_info_dqdh w210'>							                                                                                                  "
				+"				<div class='progress_title'>当前单耗</div>							                                                                                      "
				+"				<div class='progress w110'>								                                                                                              "
				+"				  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='lvbangPst"+id+"' style='width: 0%'>		"				
				+"				  </div>                                                                                                                                              "
				+"				</div>	                                                                                                                                              "
				+"				<div><span class='sr-only'  id='lvbangSjdh"+id+"'></span></div>                                                                                                        "
				+"		</div>                                                                                                                                                        "
				+"		<div class='single_info_dqhy w210'><a>当前耗用：<span id='lvbangIn"+id+"'></span>万支</a></div>                                                                                     "
				+"	</div>                                                                                                                                                            "
				+"</div>                                                                                                                                                              ";
					
					htmls=htmls+html;
				}
				$("#data-div").html("");
				$("#data-div").html(htmls);
			},"JSON");
		};

		initRollerIn();
		
		window.setInterval(function(){
			//请求卷烟机实时辅料消耗数据
			
			$.post("${pageContext.request.contextPath}/wct/stat/getRollerInputData.do",function(json){
				for(var i=0;i<json.length;i++){
					var data=json[i];
					var id=data.eqpCod;
					
					var qty = data.qty.toFixed(2);
					
					//设置大进度条的数据加载
					dangqianQty=qty+"箱";
					$("#out_top1").html("当前实际产量是："+dangqianQty);
					$("#out_last1").html("当前实际产量是："+dangqianQty);
					$("#pst_top1").html("当前实际产量是："+dangqianQty);
					$("#pst_last1").html("当前实际产量是："+dangqianQty);
				
					
					
					$("#qty"+id).html(qty);
					var juanyanzhiIn=data.juanyanzhiIn.toFixed(2);
					var juanyanzhiSjdh=(juanyanzhiIn/qty).toFixed(2);
					var juanyanzhiBzdh=$("#juanyanzhiBzdh"+id).html();
					var juanyanzhiPst=(juanyanzhiSjdh/juanyanzhiBzdh*0.8)*100;
					$("#juanyanzhiIn"+id).html(juanyanzhiIn);
					$("#juanyanzhiSjdh"+id).html(qty>0?juanyanzhiSjdh:"0.00");
					$("#juanyanzhiPst"+id).css("width",juanyanzhiPst+"%");
					
					var shuisongzhiIn=data.shuisongzhiIn.toFixed(2);
					var shuisongzhiSjdh=(shuisongzhiIn/qty).toFixed(2);
					var shuisongzhiBzdh=$("#shuisongzhiBzdh"+id).html();
					var shuisongzhiPst=(shuisongzhiSjdh/shuisongzhiBzdh*0.8)*100;
					$("#shuisongzhiIn"+id).html(shuisongzhiIn);
					$("#shuisongzhiSjdh"+id).html(qty>0?shuisongzhiSjdh:"0.00");
					$("#shuisongzhiPst"+id).css("width",shuisongzhiPst+"%");
					
					var lvbangIn=data.lvbangIn.toFixed(2);
					var lvbangSjdh=(lvbangIn/qty).toFixed(2);
					var lvbangBzdh=$("#lvbangBzdh"+id).html();
					var lvbangPst=(lvbangSjdh/lvbangBzdh*0.8)*100;
					$("#lvbangIn"+id).html(lvbangIn);
					$("#lvbangSjdh"+id).html(qty>0?lvbangSjdh:"0.00");
					$("#lvbangPst"+id).css("width",lvbangPst+"%");
				}
			},"JSON");
			
		}, 4000);
		setInterval('AutoScroll(".machine_info")',3000);
	});
	/*
	加载大进度条的数据
	$(function(){
		setInterval('setQty()',4000);
	});
	*/
	function setQty(){
		/*
		$("#out_top1").html(dangqianQty);
		dangqianQty=80;
		$("#out_last1").html(dangqianQty);
		$("#pst_top1").html(dangqianQty);
		$("#pst_last1").html(dangqianQty);
		$("#out_top1").html("当前实际产量是："+dangqianQty);
		$("#out_last1").html("当前实际产量是："+dangqianQty);
		$("#pst_top1").html("当前实际产量是："+dangqianQty);
		$("#pst_last1").html("当前实际产量是："+dangqianQty);
		*/
		//alert("A");
	}
	
	
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
	<div id="wkd-qua-title">卷烟机实时消耗监控</div>
	<div class="info_machine">	
		<div class="info_machine_cen fl">
			<ul class="nav nav-tabs" id="tabs">
				<li><a  class="tab_1" id="current" code="${loginInfo.equipmentCode}">${loginInfo.equipmentName}</a></li><!-- ${loginGroup.eqps['roller'].des} -->
			</ul>		
		</div>
		<div class="info_machine_right fl">
			<div class="fl machine_info" style="overflow: hidden">
				<ul style="margin-left: -30px;">
					<li><a href="javascript:void(0);" id="out_top1"  style="color:#13CF0D">实时数据加载中...</a></li>
					<li><a href="javascript:void(0);" id="out_last1" style="color:#2890E7">实时数据加载中...</a></li>
					<li><a href="javascript:void(0);" id="pst_top1"  style="color:#13CF0D">实时数据加载中...</a></li>
					<li><a href="javascript:void(0);" id="pst_last1" style="color:#2890E7">实时数据加载中...</a></li>
				</ul> 	
			</div>	
		</div>
		<div class="info_machine_cen fr " style="width:265px;margin-right: 5px;height: 35px;">
		<div id="button_tab1">
		<button id="slideUp"  value="0" onclick="slideUp(document.getElementById('slideUp').getAttribute('value'))" class="btn btn-default" title="点击按钮向上" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-up"></button>
		<button id="slideDown"  value="0" onclick="slideDown(document.getElementById('slideDown').getAttribute('value'))" class="btn btn-default" title="点击按钮向下" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-down"></span></button>		
		</div>
		</div>
	</div>
	<div id="content">
	<div id="tab1" class="xhtj">
		<div class="info_status " >			
			<div class="single_info" id="scroll_box01">
				<div class="single_info_box">
					<div style="float:left;width:100px;height:30px"></div> 
					<div class="single_info_title">卷烟纸</div>
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