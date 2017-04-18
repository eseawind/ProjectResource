<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>包装机实时成本消耗监控</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<style>
.progress {margin-bottom: 0px;width: 330px;float: left;height:12px;}
#ht_wct{
   height:100px;
   margin-bottom:5px;
}
</style>
<script type="text/javascript">
	$(function(){
		//实时消耗
		var initPackerIn=function(){
			$.post("${pageContext.request.contextPath}/wct/stat/initEquipment4PackerInput.do",function(json){
				var rows=json;
				var htmls2="";
				var current="";
				var currentId="${loginInfo.equipmentCode}";
				for(var i=0;i<rows.length;i++){
					var data=rows[i];
					if(currentId==data.eqpCod){
						current=" current";
					}else{
						current="";
					}
				var mat = data.mat;
				var xiaohemoBzdh=data.xiaohemoBzdh;
				var tiaohemoBzdh=data.tiaohemoBzdh;
				var xiaohezhiBzdh=data.xiaohezhiBzdh;
				var tiaohezhiBzdh=data.tiaohezhiBzdh;
				var neichenzhiBzdh=data.neichenzhiBzdh;
				var id=data.eqpCod;
				var unit = data.unit;
				var xhmdj=data.xiaohemodj;//小盒膜单价
				var thmdj=data.tiaohemodj;//条盒膜单价
				var xhzdj=data.xiaohezhidj;//小盒纸单价
				var thzdj=data.tiaohezhidj;//条盒纸单价
				var nczdj=data.neichenzhidj;//内衬纸单价
				
				
				var html2="	<div class='single_info_data "+current+"'>																															"
				+"	<div id='ht_wct' class='single_info_number'>                                                                                                                                  "
				+"		<span>"+data.eqpName+" "+data.team+"</span><br>                                                                                                                                       "
				+"		<span>"+mat+"</span><br>                                                                                                                                       "
				+"		<span id='qty"+id+"'>0</span><span>"+unit+"</span>                                                                                                                               "
				+"	</div>                                                                                                                                                             "
				//小盒膜
				+"<div class='single_info_01 w220' id='ht_wct'>                                                                                                                                   "
				+"	<div class='single_info_bzdh w210'>                                                                                                                               "
				+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
				+"		<div class='progress w110'>								                                                                                                      "
				+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
				+"		  </div>                                                                                                                                                      "
				+"		</div>	                                                                                                                                                      "
				+"		<div><span class='sr-only' id='xiaohemoBzdh"+id+"'>"+xiaohemoBzdh+"</span></div>	                                                                                                              "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqdh w210'>							                                                                                                      "
				+"			<div class='progress_title'>当前单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='xiaohemoPst"+id+"' style='width: 0%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only'  id='xiaohemoSjdh"+id+"'></span></div>                                                                                                            "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqhy w210'><a>当前耗用：<span id='xiaohemoIn"+id+"'></span>公斤</a></div>                                                                                         "
				+"  <div class='single_info_dqhy w210'><input id='xiaohemoSumPricedj"+id+"' value="+xhmdj+" style='display:none'/><a>当前成本：<span id='xiaohemoSumPrice"+id+"'>0</span>元</a></div>"
				+"</div>                                                                                                                                                              "
				//条盒膜
				+"<div id='ht_wct' class='single_info_02 w220'>                                                                                                                                   "
				+"	<div class='single_info_bzdh w210'>                                                                                                                               "
				+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
				+"		<div class='progress w110'>								                                                                                                      "
				+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
				+"		  </div>                                                                                                                                                      "
				+"		</div>	                                                                                                                                                      "
				+"		<div><span class='sr-only' id='tiaohemoBzdh"+id+"'>"+tiaohemoBzdh+"</span></div>	                                                                                                              "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqdh w210'>							                                                                                                      "
				+"			<div class='progress_title'>当前单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='tiaohemoPst"+id+"' style='width: 0%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only' id='tiaohemoSjdh"+id+"'></span></div>                                                                                                            "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqhy w210'><a>当前耗用：<span id='tiaohemoIn"+id+"'></span>公斤</a></div>                                                                                         "
				+"  <div class='single_info_dqhy w210'><input id='tiaohemoSumPricedj"+id+"' value="+thmdj+" style='display:none' /><a>当前成本：<span id='tiaohemoSumPrice"+id+"'>0</span>元</a></div>"
				+"</div>                                                                                                                                                              "
				//小盒纸
				+"<div id='ht_wct' class='single_info_03 w220'>                                                                                                                                   "
				+"	<div class='single_info_bzdh w210'>                                                                                                                               "
				+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
				+"		<div class='progress w110'>								                                                                                                      "
				+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
				+"		  </div>                                                                                                                                                      "
				+"		</div>	                                                                                                                                                      "
				+"		<div><span class='sr-only' id='xiaohezhiBzdh"+id+"'>"+xiaohezhiBzdh+"</span></div>	                                                                                                              "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqdh w210'>							                                                                                                      "
				+"			<div class='progress_title'>当前单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='xiaohezhiPst"+id+"' style='width: 0%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only' id='xiaohezhiSjdh"+id+"'> </span></div>                                                                                                            "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqhy w210'><a>当前耗用：<span  id='xiaohezhiIn"+id+"'></span>张</a></div>                                                                                         "
				+"  <div class='single_info_dqhy w210'><input id='xiaohezhiSumPricedj"+id+"' value="+xhzdj+" style='display:none' /><a>当前成本：<span  id='xiaohezhiSumPrice"+id+"'>0</span>元</a></div>"
				+"</div>                                                                                                                                                              "
				//条盒纸
				+"<div id='ht_wct' class='single_info_04 w220'>                                                                                                                                   "
				+"	<div class='single_info_bzdh w210'>                                                                                                                               "
				+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
				+"		<div class='progress w110'>								                                                                                                      "
				+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
				+"		  </div>                                                                                                                                                      "
				+"		</div>	                                                                                                                                                      "
				+"		<div><span class='sr-only' id='tiaohezhiBzdh"+id+"'>"+tiaohezhiBzdh+"</span></div>	                                                                                                              "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqdh w210'>							                                                                                                      "
				+"			<div class='progress_title'>当前单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='tiaohezhiPst"+id+"' style='width: 0%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only'  id='tiaohezhiSjdh"+id+"'></span></div>                                                                                                            "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqhy w210'><a>当前耗用：<span id='tiaohezhiIn"+id+"'></span>张</a></div>                                                                                         "
				+"  <div class='single_info_dqhy w210'><input id='tiaohezhiSumPricedj"+id+"' value="+thzdj+" style='display:none'><a>当前成本：<span id='tiaohezhiSumPrice"+id+"'>0</span>元</a></div>"
				+"</div>                                                                                                                                                              "
				//内衬纸
				+"<div id='ht_wct' class='single_info_05 w220'>                                                                                                                                   "
				+"	<div class='single_info_bzdh w210'>                                                                                                                               "
				+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
				+"		<div class='progress w110'>								                                                                                                      "
				+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
				+"		  </div>                                                                                                                                                      "
				+"		</div>	                                                                                                                                                      "
				+"		<div><span class='sr-only' id='neichenzhiBzdh"+id+"'>"+neichenzhiBzdh+"</span></div>	                                                                                                              "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqdh w210'>							                                                                                                      "
				+"			<div class='progress_title'>当前单耗</div>							                                                                                          "
				+"			<div class='progress w110'>								                                                                                                  "
				+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='neichenzhiPst"+id+"' style='width: 0%'>			"			
				+"			  </div>                                                                                                                                                  "
				+"			</div>	                                                                                                                                                  "
				+"			<div><span class='sr-only' id='neichenzhiSjdh"+id+"'></span></div>                                                                                                            "
				+"	</div>                                                                                                                                                            "
				+"	<div class='single_info_dqhy w210'><a>当前耗用：<span id='neichenzhiIn"+id+"'></span>公斤</a></div>                                                                                         "
				+"  <div class='single_info_dqhy w210'><input id='neichenzhiSumPricedj"+id+"' value="+nczdj+" style='display:none' /><a>当前成本：<span id='neichenzhiSumPrice"+id+"'>0</span>元</a></div>"
				+"</div>                                                                                                                                                              "
			    +"</div>	                                                                                                                                                          ";
					htmls2=htmls2+html2;                                                                                                                                                 
				}
				
				$("#data-div2").html("");                                                                                                                                             
				$("#data-div2").html(htmls2);
			},"JSON");
		};						
		initPackerIn();
		window.setInterval(function(){
			//请求包装机实时辅料消耗数据
			$.post("${pageContext.request.contextPath}/wct/stat/getPackerInputData.do",function(json){
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
					
					var xiaohemoIn=data.xiaohemoIn.toFixed(2)>0?data.xiaohemoIn.toFixed(2):"0.0";
					var xhmdj1=parseFloat($('#xiaohemoSumPricedj'+id).val());//单价
					//小盒膜实际单耗
					var xiaohemoSjdh=(xiaohemoIn/qty).toFixed(2);
					var xiaohemoBzdh=$("#xiaohemoBzdh"+id).html();
					var xiaohemoPst=(xiaohemoSjdh/xiaohemoBzdh*0.8)*100;
					$("#xiaohemoIn"+id).html(xiaohemoIn);
					$("#xiaohemoSjdh"+id).html(qty>0.01?xiaohemoSjdh:"0.00");
					$("#xiaohemoPst"+id).css("width",xiaohemoPst+"%");
					$('#xiaohemoSumPrice'+id).html((parseFloat(xiaohemoIn)*xhmdj1).toFixed(2));
					
					var tiaohemoIn=data.tiaohemoIn.toFixed(2)>0?data.tiaohemoIn.toFixed(2):"0.0";
					var thmdj1=parseFloat($('#tiaohemoSumPricedj'+id).val());
					//条盒膜实际单耗
					var tiaohemoSjdh=(tiaohemoIn/qty).toFixed(2);
					var tiaohemoBzdh=$("#tiaohemoBzdh"+id).html();
					var tiaohemoPst=(tiaohemoSjdh/tiaohemoBzdh*0.8)*100;
					$("#tiaohemoIn"+id).html(tiaohemoIn);
					$("#tiaohemoSjdh"+id).html(qty>0.01?tiaohemoSjdh:"0.00");
					$("#tiaohemoPst"+id).css("width",tiaohemoPst+"%");
					$('#tiaohemoSumPrice'+id).html((parseFloat(tiaohemoIn)*thmdj1).toFixed(2));
					
					var xiaohezhiIn=data.xiaohezhiIn.toFixed(2)>0?data.xiaohezhiIn.toFixed(2):"0.0";
					var xhzdj1=parseFloat($('#xiaohezhiSumPricedj'+id).val());
					//小盒纸实际单耗
					var xiaohezhiSjdh=(xiaohezhiIn/qty).toFixed(2);
					var xiaohezhiBzdh=$("#xiaohezhiBzdh"+id).html();
					var xiaohezhiPst=(xiaohezhiSjdh/xiaohezhiBzdh*0.8)*100;
					$("#xiaohezhiIn"+id).html(xiaohezhiIn);
					$("#xiaohezhiSjdh"+id).html(qty>0.01?xiaohezhiSjdh:"0.00");
					$("#xiaohezhiPst"+id).css("width",xiaohezhiPst+"%");
					$('#xiaohezhiSumPrice'+id).html((parseFloat(xiaohezhiIn)*xhzdj1).toFixed(2));
					
					var tiaohezhiIn=data.tiaohezhiIn.toFixed(2)>0?data.tiaohezhiIn.toFixed(2):"0.0";
					var thzdj1=parseFloat($('#tiaohezhiSumPricedj'+id).val());
					//条盒纸实际单耗
					var tiaohezhiSjdh=(tiaohezhiIn/qty).toFixed(2);
					var tiaohezhiBzdh=$("#tiaohezhiBzdh"+id).html();
					var tiaohezhiPst=(tiaohezhiSjdh/tiaohezhiBzdh*0.8)*100;
					$("#tiaohezhiIn"+id).html(tiaohezhiIn);
					$("#tiaohezhiSjdh"+id).html(qty>0.01?tiaohezhiSjdh:"0.00");
					$("#tiaohezhiPst"+id).css("width",tiaohezhiPst+"%");
					$('#tiaohezhiSumPrice'+id).html((parseFloat(tiaohezhiIn)*thzdj1).toFixed(2));
					
					var neichenzhiIn=data.neichenzhiIn.toFixed(2)>0?data.neichenzhiIn.toFixed(2):"0.0";
					var nczdj1=parseFloat($('#neichenzhiSumPricedj'+id).val());
					//内衬纸实际单耗
					var neichenzhiSjdh=(neichenzhiIn/qty).toFixed(2);
					var neichenzhiBzdh=$("#neichenzhiBzdh"+id).html();
					var neichenzhiPst=(neichenzhiSjdh/neichenzhiBzdh*0.8)*100;
					$("#neichenzhiIn"+id).html(neichenzhiIn);
					$("#neichenzhiSjdh"+id).html(qty>0.01?neichenzhiSjdh:"0.00");
					$("#neichenzhiPst"+id).css("width",neichenzhiPst+"%");
					$('#neichenzhiSumPrice'+id).html((parseFloat(neichenzhiIn)*nczdj1).toFixed(2));
				}
			},"json");
			
	}, 4000);
	setInterval('AutoScroll(".machine_info")',3000);
	$("#botton_show2").click(function(){$("#scroll_box02").animate({ left: "-568px"}, 1000 );});	$("#botton_hide2").click(function(){$("#scroll_box02").animate({ left: "0px"}, 1000 );});
});
function AutoScroll(obj){
	jQuery(obj).find("ul:first").animate({
		marginTop:"-30px"
	},500,function(){
		jQuery(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
	});
}
function slideUp2(x){
	var top2 = x - 527;
	if(x != 0){
		$(".scroll_box").animate({ top:'-'+top2+'px'}, 500 );$("#slideUp2").attr("value",top2);$("#slideDown2").attr("value",top2);
	}else if(x == 0){jAlert('这是页面最顶部了', '系统提示');}
}
function slideDown2(x){		
	var top = x*1 + 527*1;
	if(x <= 870){ $(".scroll_box").animate({ top: '-'+top+'px'}, 500 );$("#slideDown2").attr("value",top);$("#slideUp2").attr("value",top);
	}else{jAlert('这已经是页面的最底部了', '系统提示');}
}



</script>
</head>
<body>


<div id="prods-idx-content">
	<div id="wkd-qua-title">包装机实时成本消耗监控</div>
	<div class="info_machine">	
		<div class="info_machine_cen fl">
			<ul class="nav nav-tabs" id="tabs">
			<li><a href="javascript:void(0);" name="#tab1" class="tab_1" id="current" code="${loginInfo.equipmentCode}">${loginInfo.equipmentName}</a></li><!-- ${loginGroup.eqps['roller'].des} -->
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
		<div class="info_machine_cen fr " style="width:260px;margin-right: 5px;height: 35px;">
		<div id="button_tab2" >
		<button id="slideUp2"  value="0" onclick="slideUp2(document.getElementById('slideUp2').getAttribute('value'),this.id)" class="btn btn-default" title="点击按钮向上" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-up"></span></button>
		<button id="slideDown2"  value="0" onclick="slideDown2(document.getElementById('slideDown2').getAttribute('value'))" class="btn btn-default" title="点击按钮向下" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-down"></span></button>		
		</div>
		</div>
	</div>
	<div id="content">
	<div id="tab2" class="xhtj">
		<div class="single_info_xiaohao" >			
			<div class="single_info" id="scroll_box02">
				<div class="single_info_box">
					<div id="botton_show2" style="float:left;width:100px;height:30px">显示更多</div>
					<div class="single_info_title">小盒烟膜</div>
					<div class="single_info_title">条盒烟膜</div>
					<div class="single_info_title">小盒纸</div>
					<div class="single_info_title">条盒纸</div>
					<div class="single_info_title">内衬纸</div>
					<div id="botton_hide2">返回</div>
				</div>
				<div class="single_info_xiaohao_v" id="scroll_box2">
				<div id="data-div2" class="scroll_box" ></div>
				</div>
			</div>
		</div>
	</div>
	</div>
</div>
</body>
</html>