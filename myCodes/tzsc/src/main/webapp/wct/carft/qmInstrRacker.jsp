<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>包装工艺指导</title>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.form.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/uuid.js"></script>
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
		width: 96%;
		margin-left: 10px;
		height: 36px;
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #F5F3F3;
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
.panel-header, .panel-body {
border: none !important;
}
.panel-body {
padding: 0px !important;
}
.panel {
margin-bottom: 0px;
background:none !important;
border:none !important;
border-radius: 0px !important;
-webkit-box-shadow:none !important;
box-shadow:none !important;
}
.layout-panel {
position: relative !important;
overflow: hidden;
}
</style>

<script type="text/javascript">
			$(function() {
				$.ajax({
				    type : "POST",
					url : "${pageContext.request.contextPath}/wctCarft/queryWork.action",
					data:{
						type:'P'
					},
					success : function(r) {
						if(r==0){	
							 $("#mytable").css("display","none");
							 $('#myDiv').html("没有找到对应的工单！");
							
						}else{
							r=eval("("+r+")");
							//alert(r.rows.length);
							if(r.rows.length!=0){
								loadIntserRacker(r);
							}else{
								 $("#mytable").css("display","none");
								 $('#myDiv').html("对应的牌号暂未录入产品工艺指导书");
							}
						}
					
				}
			});		
		});
			
   function loadIntserRacker(r){
		for(var i=0;i<r.rows.length;i++){
			
			$('#mat2').html(r.rows[i].matDes),
			 $('#ln').html(r.rows[i].ln),
			 $('#eqp').html(r.rows[i].eqp),
			 $('#tsk').val(r.rows[i].tsk),
			 $('#xhc').html(r.rows[i].xhc),
			 $('#xhp').html(r.rows[i].xhp),
			 $('#thc').html(r.rows[i].thc),
			 $('#thp').html(r.rows[i].fhq),
			 $('#fqc').html(r.rows[i].fqc),
			 $('#fqp').val(r.rows[i].fqp),
			 $('#nczc').html(r.rows[i].nczc),
			 $('#nczp').val(r.rows[i].nczp),
			 $('#kjzc').html(r.rows[i].kjzc),
			 $('#kjzp').val(r.rows[i].kjzp),
			 $('#xtc').html(r.rows[i].xtc),
			 $('#xtp').val(r.rows[i].xtp),
			 $('#ttc').html(r.rows[i].ttc),
			 $('#ttp').val(r.rows[i].ttp),
			 $('#lxc').html(r.rows[i].lxc),
			 $('#lxp').val(r.rows[i].lxp),
			 $('#zxc').html(r.rows[i].zxc),
			 $('#zxp').val(r.rows[i].zxp),
			 $('#fxdc').html(r.rows[i].fxdc),
			 $('#fxdp').val(r.rows[i].fxdp),
			 $('#nhjc').html(r.rows[i].nhjc),
			 $('#nhjp').val(r.rows[i].nhjp),
			 $('#gyyq1').html(r.rows[i].gyyq1),
			 $('#gyyq2').val(r.rows[i].gyyq2),
			 $('#gyyq3').val(r.rows[i].gyyq3),
			 $('#gyyq4').val(r.rows[i].gyyq4),
			 $('#gyyq5').val(r.rows[i].gyyq5),
			 $('#gyyq6').val(r.rows[i].gyyq6),
			 $('#gyyq7').val(r.rows[i].gyyq7),
			 $('#gyyq8').val(r.rows[i].gyyq8),
			 $('#gyyq9').val(r.rows[i].gyyq9),
			 $('#gyyq10').val(r.rows[i].gyyq10),
			 $('#gyyq11').val(r.rows[i].gyyq11),
			 $('#gyyq12').val(r.rows[i].gyyq12),
			 $('#cmt').val(r.rows[i].cmt),
			 $('#authr').html(r.rows[i].authr),
			 $('#authdat').html(r.rows[i].authdat),
			 $('#audtr').html(r.rows[i].audtr),
			 $('#audrdat').html(r.rows[i].audrdat)
	}
  }		

	//显示图片弹出dailog
	function showUserWin(imgName){
	
		var obj=new Object();
		if(imgName=="xhp"){
			obj.imgs=$('#xhp').val();
		}
		if(imgName=="thp"){
			obj.imgs=$('#thp').val();
		}
		if(imgName=="fqp"){
			obj.imgs=$('#fqp').val();
		}
		if(imgName=="nczp"){
			obj.imgs=$('#nczp').val();
		}
		if(imgName=="kjzp"){
			obj.imgs=$('#kjzp').val();
		}
		if(imgName=="xtp"){
			obj.imgs=$('#xtp').val();
		}
		if(imgName=="ttp"){
			obj.imgs=$('#ttp').val();
		}
		if(imgName=="lxp"){
			obj.imgs=$('#lxp').val();
		}
		if(imgName=="zxp"){
			obj.imgs=$('#zxp').val();	
		}
		if(imgName=="fxdp"){
			obj.imgs=$('#fxdp').val();
		}
		if(imgName=="nhjp"){
			obj.imgs=$('#nhjp').val();
		}
		var url="${pageContext.request.contextPath}/pms/craft/child.jsp";
		var k = window.showModalDialog(url,obj,"dialogWidth:600px;dialogHeight:500px; edge: Raised; center: Yes; help: Yes; resizable: Yes; status: Yes;"); 
	}
	
	function roller(){
	 	url="${pageContext.request.contextPath}/wct/carft/qmInstrRoller.jsp";
		 window.location =url;  
	}
	function racker(){
		url="${pageContext.request.contextPath}/wct/carft/qmInstrRacker.jsp";
		 window.location =url;  
	}
	function showRollerFile(){
		location="${pageContext.request.contextPath}/wctCarft/showCarftFile.action?type=R";
		/* $.post("${pageContext.request.contextPath}/wctCarft/showCarftFile.action",{"type":"R"},function(v){
			if(v=="true"){
				
			}else{
				alert("未找到附件");
			}
		}); */
	}
	function showPackerFile(){
		location="${pageContext.request.contextPath}/wctCarft/showCarftFile.action?type=P";/* 
		$.post("${pageContext.request.contextPath}/wctCarft/showCarftFile.action",{"type":"P"},function(v){
			if(v=="true"){
				
			}else{
				alert("未找到附件");
			}
		}); */
	}
</script>
</head>
<body class="easyui-layout" >
  <div data-options="region:'north',split:false" style="height: 112px;padding:3px 0 3px 5px;background:#DDDDDD;">
  			<div id="eqpRun-title">工艺指导书</div>
			<div id="eqpRun-seach-box" style="background:#DDDDDD;font-size:24px;font-weight:bold;">
				<form id="eqpRun-wct-frm">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
			<td style="width:80px;">
			<!-- <input type="button" id="eqpRun-search" value="查看卷接工艺参数" style="height:28px;width:140px;" class="btn btn-default" onclick="roller()"/> -->
			&nbsp;&nbsp;<input type="button" id="eqpRun-search" value="查看卷接工艺附件" style="height:28px;width:140px;" class="btn btn-default" onclick="showRollerFile()"/>
			<!-- &nbsp;&nbsp;<input type="button" id="eqpRun-reset" value="查看包装工艺参数" style="height:28px;width:140px;" class="btn btn-default" onclick="racker()"/> -->
			&nbsp;&nbsp;<input type="button" id="eqpRun-reset" value="查看包装工艺附件" style="height:28px;width:140px;" class="btn btn-default" onclick="showPackerFile()"/></td>
			</tr>
					</table>
				</form>
			</div>
  </div>  
	
	<div data-options="border:false" style="height: 35px;padding:3px 0 3px 5px;background:#DDDDDD;height: 500px;overflow-x: hidden;width: 817px;">
     <!--   <h3 align="center">包装作业指导书</h3> -->
		       <div id="myDiv" align="center" style="background:#DDDDDD;font-size:24px;font-weight:bold;height: 10px;" ></div>
		   <table width="800" border="1" id="mytable" style="border-collapse:collapse" color="black">
					  <tr style="background: #5a5a5a;color: #fff;height: 35px;">
					    <td width="48">工段</td>
					    <td colspan="2" id="ln"></td>
					    <td width="79">机型</td>
					    <td colspan="2" id="eqp"></td>
					    <td width="57">品名</td>
					    <td colspan="3" id="mat2"></td>
					  </tr>
					  <tr>
					    <td>工艺任务</td>
					    <td colspan="9"><textarea id="tsk" name="racker.tsk" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td rowspan="12">工艺要求</td>
					    <td colspan="9"><textarea name="racker.gyyq1" id="gyyq1"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq2" id="gyyq2" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq3" id="gyyq3" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq4" id="gyyq4" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq5" id="gyyq5" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq6" id="gyyq6" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq7" id="gyyq7" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq8" id="gyyq8" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq9" id="gyyq9" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq10" id="gyyq10"style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq11" id="gyyq11" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea name="racker.gyyq12" id="gyyq12" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td rowspan="6">材料要求</td>
					    <td width="98">小盒商标</td>
					    <td colspan="2" id="xhc"></td>
					    <td><input type="hidden" id="xhp"><input type="button"  name="Submit" value="浏览" onclick="showUserWin('xhp');" style="height:28px;width:100px;" class="btn btn-default"  /></td>
					    <td>条盒商标</td>
					    <td colspan="3" id="thc"></td>
					    <td width="81"><input type="hidden" id="thp"><input type="button" name="Submit2" value="浏览" onclick="showUserWin('thp');"  style="height:28px;width:100px;" class="btn btn-default"  /></td>
					  </tr>
					  <tr>
					    <td>封签</td>
					    <td colspan="2" id="fqc"></td>
					    <td><input type="hidden" id="fqp"><input type="button" name="Submit3" value="浏览" onclick="showUserWin('fqp');"  style="height:28px;width:100px;" class="btn btn-default"  /></td>
					    <td>内衬纸</td>
					    <td colspan="3" id="nczc"></td>
					    <td><input type="hidden" id="nczp"><input type="button" name="Submit4" value="浏览" onclick="showUserWin('nczp');"  style="height:28px;width:100px;" class="btn btn-default" /></td>
					  </tr>
					  <tr>
					    <td>框架纸</td>
					    <td colspan="2" id="kjzc"></td>
					    <td><input type="hidden" id="kjzp"><input type="button" name="Submit5" value="浏览" onclick="showUserWin('kjzp');"  style="height:28px;width:100px;" class="btn btn-default" /></td>
					    <td>小盒透明纸</td>
					    <td colspan="3" id="xtc"></td>
					    <td><input type="hidden" id="xtp"><input type="button" name="Submit6" value="浏览"  onclick="showUserWin('xtp');"  style="height:28px;width:100px;" class="btn btn-default" /></td>
					  </tr>
					  <tr>
					    <td>条盒透明纸</td>
					    <td colspan="2" id="ttc"></td>
					    <td><input type="hidden" id="ttp"><input type="button" name="Submit7" value="浏览" onclick="showUserWin('ttp');"  style="height:28px;width:100px;" class="btn btn-default" /></td>
					    <td>拉线</td>
					    <td colspan="3" id="lxc"></td>
					    <td><input type="hidden" id="lxp"><input type="button" name="Submit8" value="浏览" onclick="showUserWin('lxp');"   style="height:28px;width:100px;" class="btn btn-default"  /></td>
					  </tr>
					  <tr>
					    <td>纸箱 </td>
					    <td colspan="2" id="zxc"></td>
					    <td><input type="hidden" id="zxp"><input type="button" name="Submit9" value="浏览" onclick="showUserWin('zxp');"  style="height:28px;width:100px;" class="btn btn-default"  /></td>
					    <td>封箱带</td>
					    <td colspan="3" id="fxdc"></td>
					    <td><input type="hidden" id="fxdp"><input type="button" name="Submit10" value="浏览" onclick="showUserWin('fxdp');" style="height:28px;width:100px;" class="btn btn-default" /></td>
					  </tr>
					  <tr>
					    <td>粘合剂</td>
					    <td colspan="2" id="nhjc"></td>
					    <td><input type="hidden" id="nhjp"><input type="button" name="Submit11" value="浏览" onclick="showUserWin('nhjp');"   style="height:28px;width:100px;" class="btn btn-default" /></td>
					    <td colspan="5"></td>
				      </tr>
					  
					
					  <tr>
					    <td>备注</td>
					    <td colspan="9"><textarea name="racker.cmt" id="cmt" style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr style="background: #5a5a5a;color: #fff;height: 35px;">
					    <td height="17">&nbsp;</td>
					    <td>创建人</td>
					    <td width="66" id="authr"></td>
					    <td>创建日期</td>
					    <td width="83" id="authdat"></td>
					    <td width="80">批准人</td>
					    <td colspan="2" id="audtr"></td>
					    <td width="76">批准日期</td>
					    <td id="audrdat"></td>
					  </tr>
</table>
 </div>
</body>
</html>