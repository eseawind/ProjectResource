<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  id="easyuiTheme" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<link id="easyuiTheme" rel="stylesheet" type="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/gray/easyui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/JsUtil.js" charset="utf-8"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/BandSelect.js" charset="utf-8"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/JsUtil.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/bootCSS/css/bootstrap.css"></link>
<style type="text/css">
body{background:none;}
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
	#eqpRun-wct-frm td{
		font-size:14px;
	}
	#eqpRun-tab{		
		width:817px;
		margin-left:10px;
		margin-top:15px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
		overflow:auto;
		background-color:#DDDDDD;
		
	}
	#eqpRun-page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
		padding-top:4px;
	}
	#details{
		border:2px solid #dddddd;
		width:96%;
		margin-left:10px;
		height:80px;
		margin-top:5px;
		padding:2px;
		text-indent:15px;
	}
	#up,#down{
		border:1px solid #9a9a9a;
		padding:3px 2px;
		width:70px;
		font-weight:bold;
		font-size:12px;
		cursor:pointer;
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



<script type="text/javascript">
function  radiv(){
	var valradio = $('input[name=cureqps]:checked').val();
	
	$.ajax({
		   type: "POST",
		   url: '${pageContext.request.contextPath}/wctQm/querySessce.action?radio='+valradio+'&type=X',
		   data: " ",
		   success: function(msg){
				if(msg==1){
			      var url="${pageContext.request.contextPath}/wct/qm/sealbox.jsp";
				  window.location =url; 
				}
		   }
	}); 

}

</script>

</head>
<body>
<div id="eqpRun-title">封箱质量自检</div>
<div id="eqpRun-seach-box" style="background:#DDDDDD;">
	<form id="eqpRun-wct-frm">
		<table width="100%" cellspacing="0" cellpadding="0"  >
			<tr>
				<td style="width:130px;">
					<label><input type="radio" checked="checked" name="cureqps" id="radio1" value="${loginGroup.eqps['boxer1'].id}">${loginGroup.eqps['boxer1'].cod}#封箱机</label>
				</td>
				<td style="width:130px;">
					<label><input type="radio" name="cureqps" id="radio2" value="${loginGroup.eqps['boxer2'].id}">${loginGroup.eqps['boxer2'].cod}#封箱机</label>
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
