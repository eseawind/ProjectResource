<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
 li{margin:0; padding:2; list-sytle:none}
</style>
</head>
<script type="text/javascript">
function syAddTab(id,title){//id
	var params ={
			url:'${pageContext.request.contextPath}/pms/msg/openMsgInfoView.do?id='+id,
			title:title,
			iconCls:"icon-hamburg-docs"
	};
	indexAddTab(params);
}
//系统通知
function infoAll(){
	$.post("${pageContext.request.contextPath}/pms/indexInfo/getInfoAll.do",function(json){
		  if(json!=null){
			var str="<ul>";
			for(var i=0;i<json.length;i++){
				var data=json[i];
				var title = data.title.replace(/\s/g,"");
				str+="<li style='height:19px;margin-left:-25px;margin-top:7px;width:430px;overflow:hidden;'>"
				+data.time+"&nbsp<a style='color:black;font-weight:900;'  href=javascript:syAddTab('"+data.id+"','"+title+"')>"
				+data.title+"</a></li>";//查看
			}
			str+="</ul>";
			$("#div2").html("");
			$("#div2").append(str);
			AutoScroll();
		}  
	},"JSON");
}

//最新文档
function fileAll(){
	$.post("${pageContext.request.contextPath}/pms/indexInfo/getIndexfileAll.do",function(json){	
		//console.info(json);
		  if(json!=null){
			var str="<ul>";
			for(var i=0;i<json.length;i++){
				var data=json[i];
				str+="<li style='height:19px;margin-left:-25px;margin-top:7px;width:430px;overflow:hidden;'>"
				+data.createTime+"&nbsp<a style='color:Lime;font-weight:900;'  href=javascript:myfileConvert('"+data.url+"')>"
				+data.fileName+"</a></li>";//查看
			}
			str+="</ul>";
			$("#div3").html("");
			$("#div3").append(str);
			fileAutoScroll();
		}  
	},"JSON");
}

//预览
function myfileConvert(ur){	
	var params ={
			url:ur,
			title:'文件预览',
			iconCls:"icon-hamburg-docs"
	};
	indexAddTab(params);
	//window.open("${pageContext.request.contextPath}/ConvertServlet?fileId="+ur);	
}
function AutoScroll(){
	$("#div2").find("ul:first").animate({
		marginTop:"-60px"
	},8000,function(){
		$(this).css({marginTop:"0px"}).find("li:last").appendTo(this);
	});
	
}
function fileAutoScroll(){
	$("#div3").find("ul:first").animate({
		marginTop:"-60px"
	},10000,function(){
		$(this).css({marginTop:"0px"}).find("li:last").appendTo(this);
	});
}
$(function(){
	fileAll();
	setInterval('AutoScroll()',1000*8);
	setInterval('fileAutoScroll()',1000*12);
	infoAll();
	setInterval('infoAll()',1000*60);
	setInterval('fileAll()',1000*60);
	
	
});
</script>
<body>
<div style="width: 630px;height: 455px;border: 1px solid blue;float: left;margin: 3px;">
		<div class="panel-header panel-title" style="text-align: center;">
		统计模块
		</div>
		<div id="div1">
		<iframe src="${pageContext.request.contextPath}/pms/csummejs/tx.jsp" allowTransparency="true" style="overflow: hidden;border: 0; width: 100%; height: 400px;margin:0px;padding:0px" frameBorder="0"></iframe> 
			<%-- <jsp:include page="csummejs/tx.jsp"></jsp:include> --%>
		</div>
	</div>
<div style="height:455px;float: left;">
	<div  style="height: 225px;width:450px;border: 1px solid blue;margin: 3px;">
		<div class="panel-header panel-title" style="text-align: center;">
			系统通知
		</div>
		<div id="div2" style="overflow: hidden;width:450px;height: 198px;"></div><!-- 系统通知 -->
	</div>
	<div  style="height: 225px;width:450px;border: 1px solid blue;margin: 3px;">
		<div class="panel-header panel-title" style="text-align: center;">
			最新文档
		</div>
		<div id="div3"style="overflow: hidden;width:450px;height: 198px;">3内容</div>
	</div>
</div>
</body>
</html>