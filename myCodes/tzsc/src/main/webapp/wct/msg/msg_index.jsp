<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/wct/message/css/global.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<title>消息首页</title>
<style type="text/css">
*{margin:0;padding:0;font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;}
	#msg-idx{
		width:1015px;
		height:622px;
		background:#CCCCCC;
		padding-left:4px;
		padding-top:2px;
		margin:0px auto;
	}
	#msg-idx-menu{
		float:left;
		width:180px;
		border:2px solid #9a9a9a;
		height:614px;
		background-color:#DBDBDB;
		
	}
	#msg-idx-content{
		margin-left:7px;
		float:left;
		width:815px;
		border:2px solid #9a9a9a;
		height:614px;
	}
</style>
<script type="text/javascript" >
	$(function(){//wctMenuAction!getSubMenus?parentId=89
		$.post("${pageContext.request.contextPath}/wct/message/msg_menu.jsp",function(v){
			$("#msg-idx-menu").html(v);
			var url="${pageContext.request.contextPath}"+$(".wctmenu-link:first").attr("path");
			$.post(url,function(v){
				$("#msg-idx-content").html(v);
			}); 
		});
		//默认
		 $.post("${pageContext.request.contextPath}/wct/message/msg_myNotice.jsp",function(v){
			$("#msg-idx-content").html(v);
		}); 
		
		$(".wctmenu-link").live("click",function(){
			var url="${pageContext.request.contextPath}"+$(this).attr("path");
			//alert(url);
			$.post(url,function(v){
				$("#msg-idx-content").html(v);
			});
		});
		
	});
	
    /**************************************消息提示************************************/
	//新消息5秒刷新一次
 	/*window.setInterval(function(){
 		//刷新当前用户的未读信息
 		$.post("${pageContext.request.contextPath}/noticeActionWct!unReadNotice?type=0",function(mess){
 			if(mess!=""){
 				alert(mess+'给您发来了信息！');
 				return;
 				 $.messager.show({
		                title:'您有新消息',
		                msg:mess+'给您发来了信息！<br/><a href="javascript:showMyNotice();">详情</a>',
		                timeout:3000,
		                height:'auto',
		                showType:'slide'
		            });  
 			}
 		});
    },100000*30);*/
 	//未读消息消息每十分钟10分钟提示一次
 	/*window.setInterval(function(){
 		//刷新当前用户的未读信息
 		$.post("${pageContext.request.contextPath}/noticeActionWct!unReadNotice?type=1",function(mess){
 			if(mess!=""){
 				alert(mess+'给您发来了信息！(重复提示)');
 				return;
 				 $.messager.show({
		                title:'您有未读消息',
		                msg:mess+'给您发来了信息！<br/><a  href="javascript:showMyNotice()">详情</a>',
		                timeout:5000,
		                height:'auto',
		                showType:'slide'
		            });  
 			}
 		});
    },1000000*10*60);*/
    /**************************************质量告警************************************/
    //新质量告警20秒刷新一次
 /*	window.setInterval(function(){
 		//0第一次弹出 1十分钟弹出 2已读
 		//刷新当前用户的未读信息
 		$.post("${pageContext.request.contextPath}/qualityWarnActionWct!getWarns?state=0",function(warn){
 			if(warn!="[]"){
 				var warnstr="";
				 var ws=eval("("+warn+")");
				 for(var i=0;i<ws.length;i++){ 					
					warnstr+=""+ws[i].location+"   "+ws[i].warnContent+"\\n";
				 } 
				 alert("质量告警\\n"+warnstr);
				 return;
				 warnstr+="<a href='javascript:showQualityList();'>详情</a>";
 				$.messager.show({
 					 title:'质量告警',
		                msg:warnstr,
		                showType:'slide',
		                height:'auto',
		                timeout:5000
		            });
 			}
 		});
    },1000000*3);*/
    //未读质量告警每十分钟10分钟提示一次
 	/*window.setInterval(function(){
 		//0第一次弹出 1十分钟弹出 2已读
 		//刷新当前用户的未读信息
 		$.post("${pageContext.request.contextPath}/qualityWarnActionWct!getWarns?state=1",function(warn){
 			if(warn!="[]"){
 				var warnstr="";
				 var ws=eval("("+warn+")");
				 for(var i=0;i<ws.length;i++){ 					
					 warnstr+=""+ws[i].location+"   "+ws[i].warnContent+"\n";
				 } 
				 alert("质量告警\n"+warnstr);
				 return;
				 warnstr+="<a  href='javascript:showQualityList();'>详情</a>";
 				$.messager.show({
 					 title:'质量告警',
		                msg:warnstr,
		                showType:'slide',
		                height:'auto',
		                timeout:5000
		            });
 			}
 		});
    },1000000*10);  */   
 	/**************************************单耗提示************************************/
 	//新质量告警5秒刷新一次
  /*window.setInterval(function(){
 		//0第一次弹出 1十分钟弹出 2已读
 		//刷新当前用户的未读信息
 		$.post("${pageContext.request.contextPath}/unitConWarnActionWct!getWarns?state=0",function(warn){
 			if(warn!="[]"){
 				 var warnstr="";
 				 var ws=eval("("+warn+")");
 				 for(var i=0;i<ws.length;i++){ 					
 					warnstr+=""+ws[i].location+" 设备："+ws[i].equipment+"  "+"牌号："+ws[i].materialNo+" "+ws[i].warnContent+"\n";
 				 } 
 				alert("质量告警\n"+warnstr);
				 return;
 				warnstr+="<a  href='javascript:showUnitConList();'>详情</a>";
 				 $.messager.show({
 					 title:'单耗告警',
		                msg:warnstr,
		                showType:'slide',
		                timeout:3000,
		                height:'auto'
		            }); 
 			}
 		});
    },1000*4);*/
    //未读单耗告警每十分钟10分钟提示一次
 	/*window.setInterval(function(){
 		//0第一次弹出 1十分钟弹出 2已读
 		//刷新当前用户的未读信息
 		$.post("${pageContext.request.contextPath}/unitConWarnActionWct!getWarns?state=1",function(warn){
 			if(warn!="[]"){
 				 var warnstr="";
 				 var ws=eval("("+warn+")");
 				 for(var i=0;i<ws.length;i++){ 					
 					warnstr+=""+ws[i].location+" 设备："+ws[i].equipment+"  "+"牌号："+ws[i].materialNo+" "+ws[i].warnContent+"\n";
 				 }
 				alert("质量告警\n"+warnstr);
				return;
 				warnstr+="<a  href='javascript:showUnitConList();'>详情</a>";
 				$.messager.show({
 					 title:'单耗告警',
		                msg:warnstr,
		                showType:'slide',
		                timeout:3000,
		                height:'auto'
		            }); 
 			}
 		});
    },1000*10);*/
</script>
</head>
<body>
	<div id="msg-idx">
		<div id="msg-idx-menu"></div>
		<div id="msg-idx-content"></div>
	</div>
</body>
</html>