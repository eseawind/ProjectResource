<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
#doc-mng-title{
font-size: 20px;
font-weight: bold;
text-align: center;
padding-top: 4px;
background: #b4b4b4;
color: #3C3C3C;
border-radius: 0px 4px 0px 0px;
line-height: 35px;
height: 40px;
border-bottom: 2px solid #838383;
}
#doc-mng-seach-box{
border: 1px solid #9a9a9a;
width: 804px;
margin: 0 auto;
height: 30px;
margin-top: 15px;
font-size: 14px;
font-weight: bold;
padding-top: 15px;
padding-left: 15px;
border-radius: 4px 4px 0px 0px;
background: #dedcda;
}
	
#doc-content{
background: #dedcda;
width: 820px;
margin: 0 auto;
height: 495px;
margin-top: 5px;
overflow: hidden;
overflow:auto;
border: 1px solid #9a9a9a;
border-radius: 0px 0px 4px 4px ;
}
.tree-file {background: url('${pageContext.request.contextPath}/img/tree_icons.png') no-repeat -37px 3px;}
.tree-folder {background: url('${pageContext.request.contextPath}/img/tree_icons.png') no-repeat  -35px -17px;}
.tree-folder-open {background: url('${pageContext.request.contextPath}/img/tree_icons.png') no-repeat -54px -17px;}

/* 菜单展开状态 */
.tree-expanded {background: url('${pageContext.request.contextPath}/img/tree_icons.png') no-repeat -16px 3px;}
/* 菜单闭合状态 */
.tree-collapsed {background: url('${pageContext.request.contextPath}/img/tree_icons.png') no-repeat 2px 3px;}
.tree-node-hover {
  background: #bebebe;
  color: #000000;
  border-radius: 2px;
}
.tree-node {
height: 22px;
white-space: nowrap;
cursor: pointer;
}
.tree-title {
font-size: 12px;
display: inline-block;
text-decoration: none;
vertical-align: top;
white-space: nowrap;
padding: 0 2px;
height: 24px;
line-height: 24px;
}
.tree-expanded, .tree-collapsed, .tree-folder, .tree-file, .tree-checkbox, .tree-indent {
display: inline-block;
width: 18px;
height: 22px;
vertical-align: top;
overflow: hidden;
}
.tree-node-selected {
background: #bebebe;
color: #000000;
}
.tree li {
white-space: nowrap;
margin-bottom: 5px;
}
#doc-menu-title{
font-size: 20px;
font-weight: bold;
text-align: center;
padding-top: 4px;
background: #b4b4b4;
color: #3C3C3C;
border-radius: 4px 0px 0px 0px;
line-height: 35px;
height: 40px;
border-bottom: 2px solid #838383;
}
</style>
</head>
<body>
<div id="doc-mng-title">文档中心</div>
<div id="doc-mng-seach-box" >
	<table cellspacing="0" cellpadding="0">
		<tr>
			<td width="80">文件名称：</td>
			<td width="350" id="fileName"></td>
			<td width="60">创建人:</td>
			<td width="100" id="author"></td>
			<td width="80">创建时间:</td>
			<td width="200" id="createTime"></td>
		</tr>
	</table>
</div>
<div id="doc-content">
<p style="width:200px;height:125px;margin:0 auto;background: url('${pageContext.request.contextPath}/img/w_bg.png') no-repeat;margin-top: 155px;"></p>
</div>
</body>
</html>