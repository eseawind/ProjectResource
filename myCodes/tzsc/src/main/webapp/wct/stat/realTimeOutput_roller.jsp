<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>卷烟机实时产量监控</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	//最高产量拟定为100箱,用于控制进度条最大程度(实际情况无法达到这一值)
	var maxVal=80;
	var initEquipment4OutIsp=function(){ 
		$.post("${pageContext.request.contextPath}/wct/stat/initOutDataPage.do",{"type":1},function(json){
			//console.info(json.length+"     "+json[0].planOut);
			var html="";
			for(var i=0;i<json.length;i++){
				var data=json[i]; 
				//进度条控制
				//计划产量
				var planOutPst=0;
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
				html +="<div class='cltj_box'><div class='cltj_box_title "+current+"'>"
				+"<span name="+data.planOut+" class='eqpName"+data.eqpCod+"'id="+data.eqpCod+" unit="+data.unit+">"+data.eqpName+"</span>  "
				+"<span >"+data.team+"</span>	"
				+"<span class='classes'>"+data.shift+"</span></div>"	
				+"<div class='cltj_progress_box'>"	
				+"<div class='progress' style='width:570px;'>"								
				+"<div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40'" 
				+"aria-valuemin='0' aria-valuemax='100' style='width: "+curOutPst+"%' id='roller_curOutPst"+data.eqpCod+"'></div>"
				+"<div class='pointer'style='left:"+planOutPst+"%'><div class='pointer_info'><span>牌号:"+data.mat+"  计划产量"+data.planOut+"箱</span></div></div>"
				+"</div>"			
				+"<span class='progress_box_info roller_curOut roller_curOut"+data.eqpCod+"'  id='roller_curOut"+data.eqpCod+"' value="+data.eqpCod+">0</span> 箱</div></div>";
			}
			$("#scroll_box1").html(html);
		},"JSON");
	};
	
	initEquipment4OutIsp();
		
	
	/** 
	      机台排序
	*/
	$(".eqp-sort").click(function(){
		initEquipment4OutIsp();
    });
	
	
	/***
	       产量排序
	*/
	$(".out-sort").click(function(){
		var arr=[];
		var eqps=[];
		var outClass="roller_curOut";
		var div="#scroll_box1";
		$("."+outClass).each(function(i){
			var obj=$(this);
			var out=obj.html();
			if(isNaN(out)){
				out=-1;
			}
			arr[i]=out;
			eqps[i]=obj.attr("value");
		});
		var map=[];			
		//合并
		for(var i=0;i<arr.length;i++){
			var e={};
			e.attributes={id:eqps[i],val:arr[i]};
			map.push(e);
		}
		for (var i = 0; i < map.length-1; i++) {
			for (var j = 0; j < map.length-i-1; j++) {
				if(parseFloat(map[j].attributes.val)<parseFloat(map[j+1].attributes.val)){
					var temp=map[j];
					map[j]=map[j+1];
					map[j+1]=temp;
				}
			}
		}
		var newHtml="";
		for(var i=0;i<map.length;i++){
			var h="<div class='cltj_box'>"+$("#"+outClass+map[i].attributes.id).parent().parent().html()+"</div>";
			newHtml+=h;
		}
		//清空当前div
		$(div).html(null);
		$(div).html(newHtml);
	 });
	
	window.setInterval(function(){
		var arr = [];
		$.post("${pageContext.request.contextPath}/wct/stat/getOutData.do",{"type":1},function(json){
			//console.info(json);
			if(json[0]!=undefined){
				var ft=0;
				for(var i=0;i<json.length;i++){
					var data=json[i];
					//实际产量-数字
					var curOutPst=0;
					var curOut=parseFloat( data.curOut/50.00 ).toFixed(2);  //卷烟机（千支）
					//实际产量-柱状图
					if(curOut>0){
						curOutPst=((curOut/maxVal)*100).toFixed(2);
					}
					//赋值-柱状图
					$("#roller_curOutPst"+data.eqpCod).css("width",curOutPst+"%");
					//赋值-数据
					$(".roller_curOut"+data.eqpCod).html(curOut);
					//过滤( 出事值 = 快照  )
					if(!(data.eqpCod==$(".eqpName"+data.eqpCod).attr("id"))){//解决数据量多于页面HTML DIV 块时出现undifined
						continue;
					}
					
					if(data.eqpCod=="${loginInfo.equipmentCode}"){
						//封装每一个数据
						arr[ft]={"id":data.eqpCod,
								"name":$(".eqpName"+data.eqpCod).html(),
								"out":curOut,
								"pst":curOutPst,
								"unit":$(".eqpName"+data.eqpCod).attr("unit")};
						ft++;
					}
				}
				//显示最高产量，产量比
				if(arr.length!=0){
					//产量降序
					//arr.sort(function(a,b){return a["out"]<b["out"]?1:-1});
					var out_top1 =arr[0];
					//最高产量
					var outNum=out_top1.out;
					$("#out_top1").html("当前机台:<strong   style='color:red;'>"+out_top1.name+" </strong>产量为:<strong   style='color:black;'>"+outNum+" 箱<\strong>");
					//最高产量产率      实际产量/标准产量
                    var planOutf=$("#"+out_top1.id).attr("name");
                    var bf=((outNum/planOutf)*100).toFixed(2);
					$("#out_last1").html("当前机台:<strong   style='color:green;'>"+out_top1.name+" </strong>生产率为:<strong   style='color:black;'>"+bf+"% <\strong>");
				}
			}
		},"JSON");
      },3000);

});

//页面初始化加载一次，然后6秒钟刷新一次；
AutoScroll(".machine_info");
setInterval('AutoScroll(".machine_info")',3000);
function AutoScroll(obj){
	jQuery(obj).find("ul:first").animate({
		marginTop:"-30px"
	},500,function(){
		jQuery(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
	});
}

</script>
</head>
<body>
<div id="wkd-qua-title">卷烟机实时产量监控</div>
	<div class="info_machine">	
		<div class="info_machine_cen fl">
			<ul class="nav nav-tabs" id="tabs">
			  <li><a href="#" name="#tab1" class="tab_1" id="current" code="${loginInfo.equipmentCode}">${loginInfo.equipmentName}</a></li><!-- ${loginGroup.eqps['roller'].des} -->			  
			</ul>		
		</div>
		<div class="info_machine_right fl">
			<div class="fl machine_info"  style="overflow: hidden">
				<ul style="margin-left: -30px;">
					<li><a href="javascript:void(0);" id="out_top1"  style="color:#13CF0D">正在初始化中...</a></li>
					<li><a href="javascript:void(0);" id="out_last1" style="color:#2890E7">正在初始化中...</a></li>
				</ul> 	
			</div>	
		</div>
		<div class="info_machine_cen fr " style="width:270px;margin-right: 5px;height: 35px;">
		<a class="btn btn-default eqp-sort">机台排序</a>
		<a class="btn btn-default out-sort">产量排序</a>
		<div id="button_tab1">
		<button id="slideUp"  value="0" onclick="slideUp(document.getElementById('slideUp').getAttribute('value'))" class="btn btn-default" title="点击按钮向上" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-up"></span></button>
		<button id="slideDown"  value="0" onclick="slideDown(document.getElementById('slideDown').getAttribute('value'))" class="btn btn-default" title="点击按钮向下" style="padding: 9px 12px;"><span class="glyphicon glyphicon-arrow-down"></span></button>		
		</div>
		</div>
	</div>
	<div id="content">
		<div id="tab1" class="xhtj" >
			<div class="info_status " >
			<div class="single_info_v scroll_box" id="scroll_box1"></div>						 
		</div>
		</div>
	</div>
</body>
</html>