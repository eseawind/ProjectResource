<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>滤棒工艺指导</title>
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
	 $(function() {
		 query();		
});
	 function query(){
		 $.ajax({
			    type : "POST",
				url : "${pageContext.request.contextPath}/wctCarft/queryWork.action",
				data:{
					type:'F'
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
	 }

	//清空表格
	function loadIntserRacker(r){
		for(var i=0;i<r.rows.length;i++){
			$('#mat2').html(r.rows[i].matDes);
			$('#des').html(r.rows[i].des);
			$('#ln').html(r.rows[i].ln);
			$('#eqp').html(r.rows[i].eqp);
			$('#tsk').val(r.rows[i].tsk);
			$('#fbr').html(r.rows[i].fbr);
			$('#cxz').html(r.rows[i].cxz);
			$('#zsj').html(r.rows[i].zsj);
			$('#dkj').html(r.rows[i].dkj);
			$('#zxj').html(r.rows[i].zxj);
			$('#cd').html(r.rows[i].cd);
			$('#cir').html(r.rows[i].cir);
			$('#xz').html(r.rows[i].xz);
			$('#yd').html(r.rows[i].yd);
			$('#crl').html(r.rows[i].crl);
			$('#sf').html(r.rows[i].sf);
			$('#wgyq1').val(r.rows[i].wgyq1);
			$('#wgyq2').val(r.rows[i].wgyq2);
			$('#wgyq3').val(r.rows[i].wgyq3);
			$('#wgyq4').val(r.rows[i].wgyq4);
			$('#wgyq5').val(r.rows[i].wgyq5);
			$('#wgyq6').val(r.rows[i].wgyq6);
			$('#wgyq7').val(r.rows[i].wgyq7);
			$('#wgyq8').val(r.rows[i].wgyq8);
			$('#wgyq9').val(r.rows[i].wgyq9);
			$('#wgyq10').val(r.rows[i].wgyq10);
			$('#wgyq11').val(r.rows[i].wgyq11);
			$('#wgyq12').val(r.rows[i].wgyq12);
			$('#wgyq13').val(r.rows[i].wgyq13);
			$('#wgyq14').val(r.rows[i].wgyq14);
			$('#wgyq15').val(r.rows[i].wgyq15);
			$('#wgyq16').val(r.rows[i].wgyq16);
			$('#wgyq17').val(r.rows[i].wgyq17);
			$('#wgyq18').val(r.rows[i].wgyq18);
			$('#wgyq19').val(r.rows[i].wgyq19);
			$('#wgyq20').val(r.rows[i].wgyq20);
			$('#gyyq1').val(r.rows[i].gyyq1);
			$('#gyyq2').val(r.rows[i].gyyq2);
			$('#gyyq3').val(r.rows[i].gyyq3);
			$('#gyyq4').val(r.rows[i].gyyq4);
			$('#gyyq5').val(r.rows[i].gyyq5);
			$('#gyyq6').val(r.rows[i].gyyq6);
			$('#gyyq7').val(r.rows[i].gyyq7);
			$('#gyyq8').val(r.rows[i].gyyq8);
			$('#gyyq9').val(r.rows[i].gyyq9);
			$('#gyyq10').val(r.rows[i].gyyq10);
			$('#gyyq11').val(r.rows[i].gyyq11);
			$('#gyyq12').val(r.rows[i].gyyq12);
			$('#gyyq13').val(r.rows[i].gyyq13);
			$('#gyyq14').val(r.rows[i].gyyq14);
			$('#gyyq15').val(r.rows[i].gyyq15);
			$('#gyyq16').val(r.rows[i].gyyq16);
			$('#gyyq17').val(r.rows[i].gyyq17);
			$('#gyyq18').val(r.rows[i].gyyq18);
			$('#gyyq19').val(r.rows[i].gyyq19);
			$('#gyyq20').val(r.rows[i].gyyq20);
			$('#cmt').html(r.rows[i].cmt);
			$('#authr').html(r.rows[i].authr);
			$('#authdat').html(r.rows[i].authdat);
			$('#audtr').html(r.rows[i].audtr);
			$('#audrdat').html(r.rows[i].audrdat);
			
	}
}
	function  radiv(){
		var valradio = $('input[name=cureqps]:checked').val();
		
		$.ajax({
			   type: "POST",
			   url: '${pageContext.request.contextPath}/wctQm/querySessce.action?filer='+valradio+'&type=F',
			   data: " ",
			   success: function(msg){
					if(msg==1){
						query();
				    /*   var url="${pageContext.request.contextPath}/wct/carft/qmInstrFilter.jsp";
					  window.location =url;  */
					}
			   }
		}); 
	}
	
</script>
</head>
<body class="easyui-layout" >
	<!-- 数据显示 -->
	<div data-options="region:'north',border:false" style="height:80px;background:#DDDDDD;">
			<form id="eqpRun-wct-frm">
		<table width="100%" cellspacing="0" cellpadding="0"  >
			<tr>
				<td style="font-size:18px;font-weight:bold;">成型机指导书</td>
				<td style="width:130px;">
					<label><input type="radio" checked="checked" name="cureqps" id="radio1" value="${loginGroup.eqps['filer1'].id}">${loginGroup.eqps['filer1'].des}</label>
				</td>
				<td style="width:130px;">
					<label><input type="radio" name="cureqps" id="radio2" value="${loginGroup.eqps['filer2'].id}">${loginGroup.eqps['filer2'].des}</label>
				</td>
				<td class="title-td"></td>
				<td>
					<input type="button" id="submit-request" value="查看" style="height:28px;width:100px;" class="btn btn-default" onclick="radiv()"/>
				</td>
			</tr>
		</table>
		</form>
	
	</div>  
	<div data-options="region:'center',border:false" style="height: 35px;padding:3px 0 3px 5px;background:#DDDDDD;">
       <!-- <h3 align="center">滤棒作业指导书</h3> -->
       <div id="myDiv" align="center" style="background:#DDDDDD;font-size:24px;font-weight:bold;"></div>
		<table width="740" border="1" id="mytable" style="border-collapse:collapse" color="black">
			  <tr>
			    <td colspan="2">工序名称</td>
			    <td colspan="3" id="ln"></td>
			    <td width="63">品名</td>
			    <td colspan="4" id="mat2"></td>
			  </tr>
			  <tr>
			    <td width="36">设备</td>
			    <td colspan="4" id="">&nbsp;</td>
			    <td>型号</td>
			    <td colspan="4" id="eqp">&nbsp;</td>
			  </tr>
			  <tr>
			    <td>工艺任务</td>
			    <td colspan="9" ><textarea id="tsk"  name="filter.tsk1"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td rowspan="3">材料要求</td>
			    <td colspan="2">二醋酸纤维丝束</td>
			    <td colspan="2" id="fbr"></td>
			    <td>成型纸</td>
			    <td colspan="4" id="cxz"></td>
			  </tr>
			  <tr>
			    <td colspan="2">增塑剂</td>
			    <td colspan="2" id="zsj"></td>
			    <td>搭口胶</td>
			    <td colspan="4" id="dkj"></td>
			  </tr>
			  <tr>
			    <td colspan="2">中线胶</td>
			    <td colspan="2" id="zxj"></td>
			    <td colspan="5"></td>
			  </tr>
			  
			  <tr>
			    <td rowspan="3">工艺指标</td>
			    <td width="53">长度</td>
			    <td colspan="3" id="cd"></td>
			    <td>圆周</td>
			    <td colspan="4" id="cir"></td>
			  </tr>
			  <tr>
			    <td>吸阻</td>
			    <td colspan="3" id="xz"></td>
			    <td>硬度</td>
			    <td colspan="4" id="yd"></td>
			  </tr>
			  <tr>
			    <td>圆度</td>
			    <td colspan="3" id="crl"></td>
			    <td>水分</td>
			    <td colspan="4" id="sf"></td>
			  </tr>
			  <tr>
			    <td rowspan="5">滤棒外观要求</td>
			    <td colspan="9"><textarea id="wgyq1"  name="filter.wgyq1"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="wgyq2" name="filter.wgyq2"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="wgyq3" name="filter.wgyq3"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="wgyq4" name="filter.wgyq4"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="wgyq5" name="filter.wgyq5"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td rowspan="8">工艺要求</td>
			    <td colspan="9"><textarea id="gyyq1" name="filter.gyyq1"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="gyyq2" name="filter.gyyq2"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="gyyq3" name="filter.gyyq3"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="gyyq4" name="filter.gyyq4"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="gyyq5" name="filter.gyyq5"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="gyyq6" name="filter.gyyq6"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="gyyq7" name="filter.gyyq7"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td colspan="9"><textarea id="gyyq8" name="filter.gyyq8"  style="width: 700px;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td>备注</td>
			    <td colspan="9"><textarea id="cmt" name="filter.cmt"  style="width: spx;border:none;resize:none;background:#DDDDDD;"></textarea></td>
			  </tr>
			  <tr>
			    <td>&nbsp;</td>
			    <td>创建人</td>
			    <td width="96" id="authr"></td>
			    <td width="66">创建日期</td>
			    <td width="89" id="authdat"></td>
			    <td>批准人</td>
			    <td width="104" id="audtr"></td>
			    <td width="69">批准日期</td>
			    <td width="106" colspan="2" id="audrdat"></td>
			  </tr> 
        </table>
		</div>
</body>
</html>