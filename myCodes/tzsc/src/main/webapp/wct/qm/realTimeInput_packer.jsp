<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>包装机实时消耗监控</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include><style>
.progress {margin-bottom: 0px;width: 330px;float: left;height:12px;}
</style>
<script type="text/javascript">
	$(function(){

		//window.setInterval(function(){
			//刷新数据
		//}, 4000);
		//setInterval('AutoScroll(".machine_info")',3000);
		$("#botton_show1").click(function(){$("#scroll_box02").animate({ left: "0px"}, 1000 );});	
		$("#botton_show2").click(function(){$("#scroll_box02").animate({ left: "-810px"}, 1000 );});	
		$("#botton_show3").click(function(){$("#scroll_box02").animate({ left: "-1620px"}, 1000 );});

	});
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
<style>
	.demo_info_box{width:3480px;height: 35px;margin-top: 6px;margin-bottom: 5px;}
	.demo_info_box .demo_info_title{
		width: 805px;height: 32px;background: #5A5A5A;
		float: left;margin-left: 5px;font-size: 14px;
		font-weight: bold;text-align: center;line-height: 35px;
		color:#fff;border-radius: 4px 4px 0px 0px;border-bottom: 2px solid #AAA1A1;
	}
	#botton_show1,#botton_show2,#botton_show3{
		height: 30px;width: 70px;float: left;background: #0067b2;border-radius:10px;
		font-weight: bold;text-align: center;line-height: 30px;color: #fff;cursor: pointer;
		margin-right:10px;
	}
	.demo_info_box .demo_info_box_date1{
		width: 805px;height: 32px;background: #5A5A5A;
		float: left;margin-left: 5px;font-size: 14px;
		font-weight: bold;text-align: center;line-height: 35px;
		color:#fff;border-radius: 4px 4px 0px 0px;border-bottom: 2px solid #AAA1A1;
	}
</style>


<div id="prods-idx-content">
	<div id="wkd-qua-title">包装机实时消耗监控</div>
	<div class="info_machine">	
		<div class="fl" style="width:332px">
			<div id="botton_show1" style="float:left;width:100px;height:30px">操作工(首检)</div>
			<div id="botton_show2" style="float:left;width:100px;height:30px">自检员(复检)</div>
			<div id="botton_show3" style="float:left;width:100px;height:30px">工段长(复检)</div>
		</div>
	</div>
	<div id="content">
		<div id="tab2" class="xhtj">
			<div class="single_info_xiaohao" >			
				<div class="single_info" id="scroll_box02" style="width:2440px;">
					<div class="demo_info_box">
						<div class="demo_info_title">操作工(首检)</div>
						<div class="demo_info_title">自检员(复检)</div>
						<div class="demo_info_title">工段长(复检)</div>
					</div>
					
					<div class="fl" style="border:1px solid red;width:800px;height:427px;margin-left:5px;">
						1                                                                
					</div>                                                               
					<div class="fl" style="border:1px solid red;width:800px;height:427px;margin-left:7px;">
						2                                                                
					</div>                                                               
					<div class="fl" style="border:1px solid red;width:800px;height:427px;margin-left:10px;">
						3
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>