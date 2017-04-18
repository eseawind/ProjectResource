<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>文档页面 </title> <style type ="text/css">* {
	margin: 0;
	padding: 0;
	font-family: "Microsoft YaHei", Arial;
}

#doc-idx {
	width: 1010px;
	height: 622px;
	margin: 0px auto;
	border-radius: 6px;
	border: 1px solid #979595;
}

#doc-idx .tree-title {
	font-size: 12px;
}

#doc-idx-menu {
	float: left;
	width: 170px;
	border-right: 1px solid #999;
	height: 100%;
	overflow: auto;
}

#doc-idx-content {
	float: left;
	width: 839px;
	height: 622px;
	border-radius: 0px 6px 6px 0px;
}

#biaoqianxuanzeqi {
	
}
#div1 div{
margin-top:10px;

}
#div1 div:active {
/* background-color: #5A5A5A; */
}
#div1 div:hover {

}
</style>
<script type="text/javascript">

//登录验证
var loginname = "${loginInfo.equipmentCode}";
if(loginname==null || loginname=="" || loginname.length==0){
	window.location="${pageContext.request.contextPath}/wct/sys/login.jsp";
} 
	
var ids="";
var daves="";
	function fileMenu(id,txt) {
		//先判断单击的那个按钮
		$("#div1 div").remove();//清空div
		var idsl =ids.split(",");	
		if (id == undefined) {
			id = "";
			ids+=",";
		}else if(idsl[idsl.length-1]!=id){
			ids+=","+id;
		}	
		var dave=daves.split(",");	
		if(txt==undefined || txt==""){
			txt="根目录";
			if(dave[dave.length-1]!=txt){
			daves+=",根目录";}
		}else if(dave[dave.length-1]!=txt){
			daves+=","+txt;
		}
		var str="";
		str +=  "<div><input type='button' value='返回上级目录'style='width:100px;height:40px;'/></div>";
		$.post("${pageContext.request.contextPath}//wct/file/docfile/getTreeList.do?pid="+ id,function(json) {											
				str += "";
				var img1="";	
					if(json.length>0){
					for ( var i = 0; i < json.length; i++) {
						if(json[i].type=='1'){
							img1="${pageContext.request.contextPath}/wct/doc/img/0.png";
						}else{
							img1="${pageContext.request.contextPath}/wct/doc/img/3.png";
						}
									
							str += "<div id='"+ json[i].id+"' name='"+json[i].type+"'style='border:0px solid grey; width:100px;height:68px;margin-left:40px;overflow:hidden;'>";
							str += "<img src='"+img1+"'  style='height:45px;width:45px'/><br/>"
									+ json[i].name;
								str += "</div>";
							}
					
							$("#div1").append(str);
					}	
							$("#div1 div").click(function(){
								if($(this).attr("name")=='1'){
									fileMenu($(this).attr("id"),$(this).text());
								}else{
									//文件取出文件的url
									windowOpen($(this).attr("id"));
								}
							});
							$("input[type='button']").bind('click',function() { 
								var idslength =ids.split(",");
								pid=idslength[idslength.length-2];
								var  myds=daves.split(",");
								txt=myds[myds.length-2];
								daves=daves.substring(0,daves.lastIndexOf(","));
								ids=ids.substring(0,ids.lastIndexOf(","));			
								if(idslength[idslength.length-2]==""){
									ids="";
								}
								if(myds[myds.length-2]==""){
									daves="";
								}
								fileMenu(pid,txt);
								});
					}, "JSON");
					
		
	}

//调转显示页面
function windowOpen(id){
	$.post("${pageContext.request.contextPath}//wct/file/docfile/getReadFileJsp.do?id="+id,function(data){
		if(data.success){
			$("#work").attr("src","${pageContext.request.contextPath}//wct/doc/readFile.jsp");
		}
	},"json");
	
}
//表单显示方法
function fileReJsp(){
	$("#work").attr("src","${pageContext.request.contextPath}//wct/doc/file_indexRe.jsp");
}
$(function(){
	fileReJsp();	
	fileMenu();
});
</script>
</head>
<body>
	<div id="doc-idx">
		<div id="doc-idx-menu">
			<div id="doc-menu-title">
				<span style="font-size: 18px; font-weight: bold;"></span>
			</div>
			<div >
			<!--标题不随滚动条滚动而滚动  style="position:fixed;width: 170px;text-align: center;  "-->
			<ul class="nav nav-pills nav-stacked" id="prods-menu">
				<li class="active"><a href="javascript:fileMenu();"
					class="prods-file">文档更新</a></li>
				<li style="text-align: center;"></li>
			</ul>
			</div>
			<div id="div1" style="text-align: center; ">
				
			</div>
		</div>
		<div id="doc-idx-content">
		<iframe id="work" scrolling="no" frameborder="0" src="" style="width:100%;height:100%"></iframe>
		</div>
	</div>
</body>
</html>