<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>包装机实时剔除监控</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		var initEquipment4BadIsp=function(){
			$.post("${pageContext.request.contextPath}/wct/stat/initBadQtyDataPage.do",{"type":2},function(json){
				var html="";
				for(var i=0;i<json.length;i++){
					var data=json[i];
					var rej=0;
					if(data.shift==""){
						rej="当前班次无工单";
					}
					var current= "";
					if(data.eqpCod=="${loginInfo.equipmentCode}"){
						current= "current";
					}
					html+="<div class='cltj_box'>"
						+"<div class='cltj_box_title "+current+"'>"
						+"	<span class='eqpName"+data.eqpCod+"' id="+data.eqpCod+" unit="+data.unit+">"+data.eqpName+"</span>"
						+"	<span class='Team'>"+data.shift+"</span>"	
						+"	<span class='classes'>"+data.team+"</span>"
						+"</div>"	
						+"<div class='cltj_progress_box'>"	
						+"	<div class='progress' style='width:570px;'>"								
						+"		<div class='progress-bar progress-bar-warning gc_y_t' id='roller_outPst"+data.eqpCod+"' style='width:0%'> <span class='progress_bar_info'>"+data.mat+"</span></div>"
						+"		<div id='roller_rejPst"+data.eqpCod+"' style='width:100%'></div>"
						+"	</div>"			
						+"	&nbsp;&nbsp;<span class='sr-only roller_curRej roller_curRej"+data.eqpCod+"' id='roller_curRej"+data.eqpCod+"'  value="+data.eqpCod+">0</span>  包" 
						+"</div>"
						+"</div>";
				}
				$("#scroll_box1").html(null);
				$("#scroll_box1").html(html);
			},"JSON");
		};
		
		initEquipment4BadIsp();
		
		$(".eqp-sort").click(function(){
			initEquipment4BadIsp();
		});
		//产量排序
		$(".rej-sort").click(function(){
			var type=$(this).attr("type");
			var arr=[];
			var eqps=[];
			var outClass="roller_curRej";
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
			$.post("${pageContext.request.contextPath}/wct/stat/getBadData.do",{"type":2},function(json){
			if(json[0]!=undefined){
				var ft=0;
				for(var i=0;i<json.length;i++){
					//总产量
					//说明百分比（剔除，产量）
					var rejPst=0;
					var maxVal=5000;//拟定最高剔除量(实际达不到这个值)
					var data =json[i];
					var badQty=data.badQty;
					if(badQty>0){
						rejPst=parseFloat((badQty/maxVal)*100).toFixed(2);
					}
					$("#roller_outPst"+data.eqpCod).css("width",rejPst+"%");
					$("#roller_curRej"+data.eqpCod).html(parseFloat(data.badQty).toFixed(0));
					
					if(!(data.eqpCod==$(".eqpName"+data.eqpCod).attr("id"))){
						continue;
					}
					arr[ft]={"id":data.eqpCod,
							"name":$(".eqpName"+data.eqpCod).html(),
							"out":data.curOut,
							"badQty":badQty,
							"pst":badQty==0?0.000:(badQty/data.curOut).toFixed(3),
							"unit":$(".eqpName"+data.eqpCod).attr("unit")};
					ft++;
				}
				if(arr.length!=0){
					//产量降序
					arr.sort(function(a,b){return a["badQty"]<b["badQty"]?1:-1});
					var bad_top1 =arr[0];
					//最高产量
					var outNum=bad_top1.badQty;
					$("#bad_top1").html("当前<strong   style='color:red;'>最高剔除</strong>机台:"+bad_top1.name+" 剔除量为:<strong   style='color:black;'>"+outNum+"  支<\strong>");
					//最高产量产率      实际产量/标准产量
					var zNum=outNum;
					var mNum=zNum+bad_top1.out;
					//console.info(zNum+"===="+mNum);
                    var bf=((zNum/mNum)*100).toFixed(2);
					$("#bad_last1").html("当前<strong   style='color:red;'>最高剔除</strong>机台:"+bad_top1.name+" 剔除率为:<strong   style='color:black;'>"+bf+"% <\strong>");
					
				}
			}
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
	
	$("#slideDown").click(function(){$(".scroll_box").animate({ top: "-476px"}, 500 );});	
	$("#slideUp").click(function(){$(".scroll_box").animate({ top: "0px"}, 500 );});

</script>
</head>
<body>
	<div id="wkd-qua-title">包装机实时剔除监控</div>
	<div class="info_machine">	
		<div class="info_machine_cen fl">
			<ul class="nav nav-tabs" id="tabs">
			  <li><a href="#" name="#tab1" class="tab_1" code="${loginInfo.equipmentCode}" id="current">${loginInfo.equipmentName}</a></li>
			</ul>		
		</div>
		<div class="info_machine_right fl">
			<div class="fl machine_info" style="overflow: hidden">
				<ul style="margin-left: -30px;">
					<li><a href="javascript:void(0);" id="bad_top1"  style="color:#13CF0D">正在初始化中...</a></li>
					<li><a href="javascript:void(0);" id="bad_last1" style="color:#2890E7">正在初始化中...</a></li>
					
				</ul> 	
			</div>		
		</div>
		<div class="info_machine_cen fr " style="width:255px;margin-right: 5px;height: 35px;">
		<a class="btn btn-default eqp-sort">机台排序</a>
		<a class="btn btn-default rej-sort">剔除排序</a>
		<div id="button_tab1" style="width:77px;">
		<button id="slideUp"  value="0" onclick="slideUp(document.getElementById('slideUp').getAttribute('value'))" class="btn btn-default" title="点击按钮向上" style="padding: 8px 10px;"><span class="glyphicon glyphicon-arrow-up"></button>
		<button id="slideDown"  value="0" onclick="slideDown(document.getElementById('slideDown').getAttribute('value'))" class="btn btn-default" title="点击按钮向下" style="padding: 8px 10px;"><span class="glyphicon glyphicon-arrow-down"></span></button>		
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