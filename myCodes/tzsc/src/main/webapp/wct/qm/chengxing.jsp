<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>录入滤棒质检信息</title>
<link href="css/easyui.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/JsUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/BandSelect.js"></script>
<style type="text/css">
body{background:none;margin: 0;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#eqpRun-title{
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
	#eqpRun-seach-box{
		border: 1px solid #9a9a9a;
		width: 821px;
		margin-left: 10px;
		height: 36px;
		margin: 0 auto;
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	.btn-default {
		color: #FFFFFF;
		background-color: #5A5A5A;
		border-color: #cccccc;
}

.btn {
  display: inline-block;
  padding:0px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.428571429;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
       -o-user-select: none;
          user-select: none;
}
</style>


</head>

<body>


<script type="text/javascript">
function  radiv(){
	var valradio = $('input[name=cureqps]:checked').val();

	$.ajax({
		   type: "POST",
		   url: '${pageContext.request.contextPath}/wctQm/querySessce.action?filer='+valradio+'&type=F',
		   data: " ",
		   success: function(msg){
				if(msg==1){
			      var url="${pageContext.request.contextPath}/wct/qm/filtrate.jsp";
				  window.location =url; 
				}
		   }
	}); 
}
</script>
<div id="eqpRun-title">滤棒外观自检</div>
<div id="eqpRun-seach-box" style="background:#DDDDDD;">
	<form id="eqpRun-wct-frm">
		<table width="100%" cellspacing="0" cellpadding="0"  >
			<tr>
				<td style="font-size:18px;font-weight:bold;width: 450px;">成型机请求</td>
				<td style="width:130px;">
					<label><input type="radio" checked="checked" name="cureqps" id="radio1" value="${loginGroup.eqps['filer1'].id}">${loginGroup.eqps['filer1'].des}</label>
				</td>
				<td style="width:130px;">
					<label><input type="radio" name="cureqps" id="radio2" value="${loginGroup.eqps['filer2'].id}">${loginGroup.eqps['filer2'].des}</label>
				</td>
				<td class="title-td"></td>
				<td>
					<input type="button" id="eqpRun-search" value="确认请求" style="height:28px;width:100px;" class="btn btn-default" onclick="radiv()"/>
				</td>
			</tr>
		</table>
		</form>
		</div>
</body>
</html>
