	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>卷接工艺指导</title>
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
		width: 100%;
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

$(function(){
		$.ajax({
		    type : "POST",
			url : "${pageContext.request.contextPath}/wctCarft/queryWork.action",
			data:{
				type:'C'
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
			 $('#ln').html(r.rows[i].ln);
			 $('#mat').html(r.rows[i].matDes);
			 $('#eqp').html(r.rows[i].eqp);
			 $('#tsk').html(r.rows[i].tsk);
			 $('#cd').html(r.rows[i].cd);
			 $('#cir').html(r.rows[i].cir);
			 $('#zl').html(r.rows[i].zl);
			 $('#yd').html(r.rows[i].yd);
			 $('#xz').html(r.rows[i].xz);
			 $('#dbls').html(r.rows[i].dbls);
			 $('#hsl').html(r.rows[i].hsl);
			 $('#hml').html(r.rows[i].hml);
			 $('#xh').html(r.rows[i].xh);
			 $('#wg').html(r.rows[i].wg);
			 $('#jyzc').html(r.rows[i].jyzc);
			 $('#jyzp').val(r.rows[i].jyzp);
			 $('#jzzc').html(r.rows[i].jzzc);
			 $('#jzzp').val(r.rows[i].jzzp);
			 $('#lbc').html(r.rows[i].lbc);
			 $('#lbp').val(r.rows[i].lbp);
			 $('#gyymc').html(r.rows[i].gyymc);
			 $('#gyymp').val(r.rows[i].gyymp);
			 $('#nhjc').html(r.rows[i].nhjc);
			 $('#nhjp').val(r.rows[i].nhjp);
			 $('#gysb1').val(r.rows[i].gysb1);
			 $('#gysb2').val(r.rows[i].gysb2);
			 $('#gysb3').val(r.rows[i].gysb3);
			 $('#gysb4').val(r.rows[i].gysb4);
			 $('#gysb5').val(r.rows[i].gysb5);
			 $('#gysb6').val(r.rows[i].gysb6);
			 $('#gysb7').val(r.rows[i].gysb7);
			 $('#gysb8').val(r.rows[i].gysb8);
			 $('#gysb9').val(r.rows[i].gysb9);
			 $('#gysb10').val(r.rows[i].gysb10);
			  $('#gysb11').val(r.rows[i].gysb11);
			 $('#gysb12').val(r.rows[i].gysb12);
			 $('#gysb13').val(r.rows[i].gysb13);
			 $('#gysb14').val(r.rows[i].gysb14);
			 $('#gysb15').val(r.rows[i].gysb15);
			 $('#gysb16').val(r.rows[i].gysb16);
			 $('#gysb17').val(r.rows[i].gysb17);
			 $('#gysb18').val(r.rows[i].gysb18);
			 $('#gysb19').val(r.rows[i].gysb19);
			 $('#gysb20').val(r.rows[i].gysb20); 
			 $('#cmt').val(r.rows[i].cmt);
			 $('#authr').html(r.rows[i].authr);
			 $('#authdat').html(r.rows[i].authdat);
			 $('#audtr').html(r.rows[i].audtr);
			 $('#audrdat').html(r.rows[i].audrdat);
	}
}

	//显示图片弹出dailog
	function showUserWin(imgName){
		var dd=$('#jyzp').val();
	    
		var obj=new Object();
		if(imgName=="jyzp"){
			obj.imgs=$('#jyzp').val();
		}
		if(imgName=="jzzp"){
			obj.imgs=$('#jzzp').val();
		}
		if(imgName=="lbp"){
			obj.imgs=$('#lbp').val();
		}
		if(imgName=="gyymp"){
			obj.imgs=$('#gyymp').val();
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
  <div data-options="region:'north',split:false" style="height:112px;background:#DDDDDD;">
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
<!-- 查询条件 -->
	<div data-options="border:false" style="height: 35px;padding:3px 0 3px 5px;background:#DDDDDD;height: 500px;overflow-x: hidden;width: 817px;">
		   <div id="myDiv" align="center" style="background:#DDDDDD;font-size:24px;font-weight:bold;height: 10px;" ></div>
 				<table width="800" border="1" id="mytable" style="border-collapse:collapse" color="black">
					  <tr style="background: #5a5a5a;color: #fff;height: 35px;">
					    <td width="68">工段</td>
					    <td colspan="2" id="ln" ></td>
					    <td width="101">机型</td>
					    <td colspan="3" id="eqp"></td>
					    <td >品名</td>
					    <td colspan="2" id="mat"></td>
				      </tr>
					  <tr>
					    <td>工艺任务</td>
					    <td colspan="9"><textarea  id="tsk" name=""  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td rowspan="10">工艺指标</td>
					    <td width="83">长度</td>
					    <td colspan="8" id="cd"></td>
				      </tr>
					  <tr>
					    <td>圆周</td>
					    <td colspan="8" id="cri"></td>
				      </tr>
					  <tr>
					    <td>质量</td>
					    <td colspan="8" id="zl"></td>
				      </tr>
					  <tr>
					    <td>硬度</td>
					    <td colspan="8" id="yd"></td>
				      </tr>
					  <tr>
					    <td>吸阻</td>
					    <td colspan="8" id="xz"></td>
	  </tr>
					  <tr>
					    <td>端部落丝量</td>
					    <td colspan="8" id="dbls"></td>
	  </tr>
					  <tr>
					    <td>含末率</td>
					    <td colspan="8" id="hml"></td>
	  </tr>
					  <tr>
					    <td>含水率</td>
					    <td colspan="8" id="hsl"></td>
	  </tr>
					  <tr>
					    <td>熄火</td>
					    <td colspan="8" id="xh"></td>
	  </tr>
					  <tr>
					    <td>外观</td>
					    <td colspan="8" id="wg"></td>
				      </tr>
					  <tr>
					    <td rowspan="3">材料要求</td>
					    <td>卷烟纸</td>
					    <td colspan="2" id="jyzc" ></td>
					    <td width="128"><input id="jyzp" type="hidden"/> <input type="button"  name="Submit" value="预览"  onclick="showUserWin('jyzp')" style="height:28px;width:100px;" class="btn btn-default" /></td>
					    <td width="65">接装纸</td>
					    <td colspan="3" id="jzzc"></td>
					    <td width="126"><input id="jzzp" type="hidden"/><input type="button"  name="Submit2" value="预览" onClick="showUserWin('jzzp')"  style="height:28px;width:100px;" class="btn btn-default" /></td>
					  </tr>
					  <tr>
					    <td>滤棒</td>
					    <td colspan="2" id="lbc"></td>
					    <td><input id="lbp" type="hidden"/><input type="button" name="Submit3" value="预览" onClick="showUserWin('lbp')" style="height:28px;width:100px;" class="btn btn-default" /></td>
					    <td>钢印油墨</td>
					    <td colspan="3" id="gyymc"></td>
					    <td><input id="gyymp" type="hidden"/><input type="button" name="Submit4" value="预览" onClick="showUserWin('gyymp')" style="height:28px;width:100px;" class="btn btn-default" /></td>
					  </tr>
					  <tr>
					    <td>粘合剂</td>
					    <td colspan="2" id="nhjc"></td>
					    <td><input id="nhjp" type="hidden"/><input type="button" name="Submit5" value="预览"  onclick="showUserWin('nhjp')" style="height:28px;width:100px;" class="btn btn-default" /></td>
					    <td colspan="5"></td>
					  </tr>
					  <tr>
					    <td rowspan="10">工艺及设备要求</td>
					    <td colspan="9"><textarea  id="gysb1" name="roller.gysb1"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea id="gysb2" name="roller.gysb2"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea id="gysb3" name="roller.gysb3"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea id="gysb4" name="roller.gysb4"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea  id="gysb5" name="roller.gysb5"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea id="gysb6" name="roller.gysb6"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea  id="gysb7" name="roller.gysb7"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea id="gysb8" name="roller.gysb8"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea id="gysb9" name="roller.gysb9"  style="width: 720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td colspan="9"><textarea id="gysb10" name="roller.gysb10"  style="width:720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr>
					    <td>备注</td>
					    <td colspan="9"><textarea id="cmt" name="roller.cmt"  style="width:720px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
					  </tr>
					  <tr style="background: #5a5a5a;color: #fff;height: 35px;">
					    <td>制作人</td>
					    <td colspan="2" id="authr"></td>
					    <td >制作日期</td>
					    <td id="authdat"></td>
					    <td>批准人</td>
					    <td width="93" id="audtr"></td>
					    <td width="77">批准日期</td>
					    <td colspan="2" id="audrdat"></td>
					  </tr>
</table>
</div>
</body>
</html>