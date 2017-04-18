<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>润滑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<style>
	.btn{
		padding:2px 12px;
	} 
	.btn-default {
		margin-right:1px;
	}
	.demo_info_box{height: 35px;margin-top: 6px;margin-bottom: 5px;}
	.demo_info_box .demo_info_title{
		width: 808px;height: 38px;background: #5A5A5A;
		float: left;margin-left: 5px;font-size: 14px;
		font-weight: bold;text-align: left;line-height: 35px;
		color:#fff;border-radius: 4px 4px 0px 0px;border-bottom: 2px solid #AAA1A1;
	}
	.single_info_xiaohao{
		height: 521px;
	}
	.single_info{/*内容 样式*/
		margin-top:0px;
		width:808px;
	}
	 body{  
	    overflow-x: hidden;  
	    overflow-y: hidden;  
		}  
</style>
<script type="text/javascript">
	var shgChangeClick =null;
	$(function(){
		//var type = window.location.search;
		//alert(type);
		//checked全选、反选事件
        $("#shg-tab-thead-CKA").click(function() {
        	$("#shg-tab-tbody tr").each(function(idx) {
           		if($(this).find("#shg-tab-tbody-CK").get(0)!=null
                   	&&$(this).find("#shg-tab-tbody-CK").get(0)!=undefined){
           			$(this).find("#shg-tab-tbody-CK").get(0).checked = 
               			$("#shg-tab-thead-CKA").get(0).checked;
               }
			});
        });
       queryList("${param.id}","${param.role}","","");//默认后台查询
	});
	//刷新
	function queryList(plan_id,role,type,colors){
		clearParams();
		$("#shg-tab-thead-CKA").get(0).checked = true;//顶部checkbox为不选状态
		if(role=='rh'){
			$("#button_id").val("润滑工确认完成");
		}else if(role=='czg'){//这个是 根据角色查询 数据
			$("#button_id").val("操作工确认完成");
		}
		$.post("${pageContext.request.contextPath}/wct/lubrplanParam/queryDataGridByPlanId.do?plan_id="+plan_id+"&type="+type,function(data){
				var html="";
				var list=data.rows;
				var type1="ZF12B"; var pass1=true;//ZF部位以及颜色判定 
				var type2="HCF"; var pass2= true;//HCF部位以及颜色判定 
				var type3="MAX"; var pass3= true;//MAX部位以及颜色判定 
				var type4="SE"; var pass4= true;//SE部位以及颜色判定 
				var type5="VE"; var pass5= true;//VE部位以及颜色判定 
				var type6="YB95"; // YB95部位以及颜色判定 
				var type7="YB55"; //YB55部位以及颜色判定 
				var type8="YB65"; //YB65部位以及颜色判定 
				var type9="YB25"; //YB25部位以及颜色判定 
				var type10="YB45"; //YB45部位以及颜色判定 
				var code= $("#this_plan_Code").val();
				var ZBType= $("#this_plan_Type").val();
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var isFinsh= "未完成";
						if(revalue.end_user_name!=null&&revalue.end_user_name!=""){
							isFinsh= "完成";
						}
						if(revalue.fill_quantity==null){
							revalue.fill_quantity='';
						}
						html+="<tr>"
							+"	<td height='27' algin='center'>"
							+"	&nbsp; &nbsp;<input checked value="+revalue.id+" type='checkbox' name='shg-tab-tbody-CK' id='shg-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td align='center'>"+(i+1)+"</td>"
							+"	<td>"+revalue.name+"</td>"
							+"	<td align='center'>"+revalue.oiltd+"</td>"
							+"	<td align='center'>"+revalue.methods+"</td>"
							+"	<td nowrap='nowrap' align='center'>"+revalue.fill_quantity+" "+revalue.fill_unit+"</td>"
							+"	<td align='center'>"+isFinsh+"</td>"
							/* +"	<td align='center'><input type='button' value='浏览' onclick=lookImages('"+revalue.uploadUrl+"','"+revalue.id+"') style='height:25px;width:45px;' class='btn btn-default'/></td>" */
							+"</tr>";
						 
							
					if(code>0&&code<31){//卷烟机
						if(type1==revalue.rhPart && isFinsh=='未完成'){
							pass1=false;
						   }
				
						if(type2==revalue.rhPart && isFinsh=='未完成'){
							    pass2=false;
						   } 
					
						if(type3==revalue.rhPart && isFinsh=='未完成'){
							    pass3=false;
						   } 
					
						if(type4==revalue.rhPart && isFinsh=='未完成'){
							    pass4=false;
						   } 
						
						if(type5==revalue.rhPart && isFinsh=='未完成'){
							    pass5=false;
						   } 
					}else if(ZBType=='ZB25'){//ZB25包装机
						if(type6==revalue.rhPart && isFinsh=='未完成'){
							pass1=false;
					       }
			
						if(type7==revalue.rhPart && isFinsh=='未完成'){
							    pass2=false;
						   } 
					
						if(type8==revalue.rhPart && isFinsh=='未完成'){
							    pass3=false;
						   } 
					
						if(type9==revalue.rhPart && isFinsh=='未完成'){
							    pass4=false;
						   } 
					}else if(ZBType=='ZB45'){//ZB45包装机

						if(type6==revalue.rhPart && isFinsh=='未完成'){
							pass1=false;
					       }
			
						if(type7==revalue.rhPart && isFinsh=='未完成'){
							    pass2=false;
						   } 
					
						if(type8==revalue.rhPart && isFinsh=='未完成'){
							    pass3=false;
						   } 
					
						if(type10==revalue.rhPart && isFinsh=='未完成'){
							    pass4=false;
						   } 
					}
				}
					
					
					
					if(pass1==true){
						document.getElementById("rhpart_changeColor1").style.borderColor="green";   
					}else{
						document.getElementById("rhpart_changeColor1").style.borderColor="red";
					}
					
					
					if(pass2==true){
						document.getElementById("rhpart_changeColor2").style.borderColor="green";   
					}else{
						document.getElementById("rhpart_changeColor2").style.borderColor="red";
					}
					
					
					if(pass3==true){
						document.getElementById("rhpart_changeColor3").style.borderColor="green";   
					}else{
						document.getElementById("rhpart_changeColor3").style.borderColor="red";
					}
					
					
					if(pass4==true){
						document.getElementById("rhpart_changeColor4").style.borderColor="green";   
					}else{
						document.getElementById("rhpart_changeColor4").style.borderColor="red";
					}
					
					if(document.getElementById("rhpart_changeColor5")!=null&&document.getElementById("rhpart_changeColor5")!=""){
						if(pass5==true){
							document.getElementById("rhpart_changeColor5").style.borderColor="green";   
						}else{
							document.getElementById("rhpart_changeColor5").style.borderColor="red";
						}
						
					}
					
					if(colors!=''){
						if(code>0&&code<31){
							//ZJ17卷烟机 
							if(type=='ZF12B'){//1
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
								document.getElementById("rhpart_changeColor5").style.borderColor =colors.color5;
							}else if(type=='HCF'){//2
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
								document.getElementById("rhpart_changeColor5").style.borderColor =colors.color5;
							}else if(type=='MAX'){//3
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
								document.getElementById("rhpart_changeColor5").style.borderColor =colors.color5;
							}else if(type=='SE'){//4
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor5").style.borderColor =colors.color5;
							}else if(type=='VE'){//5
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
							}
							//end
						}else if(ZBType=='ZB25'){
							//ZB25包装机 ，YB95 YB55 YB65 YB25 
							if(type=='YB95'){//1
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
							}else if(type=='YB55'){//2
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
							}else if(type=='YB65'){//3
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
							}else if(type=='YB25'){//4
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
							}
							//end
						}else if(ZBType=='ZB45'){
							//ZB45包装机 ，YB95 YB55 YB65 YB45 
							if(type=='YB95'){//1
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
							}else if(type=='YB55'){//2
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
							}else if(type=='YB65'){//3
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor4").style.borderColor =colors.color4;
							}else if(type=='YB45'){//4
								document.getElementById("rhpart_changeColor1").style.borderColor =colors.color1;
								document.getElementById("rhpart_changeColor2").style.borderColor =colors.color2;
								document.getElementById("rhpart_changeColor3").style.borderColor =colors.color3;
							}
							//end
						}
						
					}
					
				}else{}
				$("#shg-tab-tbody").html(html);
			},"JSON");
	}	
	
	
	 //查看PMS布置的图片热点 
	 function lookImages(uploadUrl,id){
			var url ='${pageContext.request.contextPath}/wct/lubrplanParam/gotoHotspotJsp.do?uploadUrl='+uploadUrl+"&id="+id; 
			/* var url ="http://www.baidu.com";  */
		   	var iWidth=1024; //弹出窗口的宽度;
			var iHeight=800; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(url,"","toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
		}
	
	
	var clearParams=function(){
		$("#shg-tab-tbody").html("");
	};
	//保存
	function save(){
		var planId = "${param.id}";
		var role = "${param.role}";
		var reqString = '[';
		var isNextNumber= 0;
		 $("#shg-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#shg-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键ID，判断更新或者新增
	        		 //td:eq(5) select
				     reqString += '{"id":"'+td0//主键
					     + '","plan_id":"'+ planId//润滑计划ID
						+ '"}';
						reqString += ',';
						isNextNumber++;
	            }
	        });
	   	if(reqString!="["){
	   		reqString = reqString.substring(0,(reqString.length-1));
		}else{
			jAlert("请选择需要保存的记录","保存");
			return false;
		}
		reqString += ']';
	 	//var params =reqString;//{eqpCod:equ}
		//alert(reqString);
		//return ;
        //跑到后台处理后 并查询
        
        	//var type= $("#this_rhpart_value").val();
			//alert(type);
			//return;
         $.ajax({
            type: "post",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${pageContext.request.contextPath}/wct/lubrplanParam/lubricantParamEnd.do",//要访问的后台地址
            data: "param_id=" + reqString,//要发送的数据
            complete :function(){
                //$("#load").hide();
            },//AJAX请求完成时隐藏loading提示
            success : function(r) {
				if(r.success){
					jAlert("保存成功","提示");
					//type 
					var color1 = document.getElementById("rhpart_changeColor1").style.borderColor;
					var color2 = document.getElementById("rhpart_changeColor2").style.borderColor;
					var color3 = document.getElementById("rhpart_changeColor3").style.borderColor;
					var color4 = document.getElementById("rhpart_changeColor4").style.borderColor;
					var color5;
					var colors;
					if(document.getElementById("rhpart_changeColor5")!=null&&document.getElementById("rhpart_changeColor5")!=""){
						color5=document.getElementById("rhpart_changeColor5").style.borderColor;
						colors={color1:color1,color2:color2,color3:color3,color4:color4,color5:color5};
					}else{
						colors={color1:color1,color2:color2,color3:color3,color4:color4};
					}
					
					var type= $("#this_rhpart_value").val();
					queryList("${param.id}","${param.role}",type,colors);//重新查询下
				}else{
					jAlert("保存失败!","提示");
				}
			}
         });
	}
	
	//图片热点事件查询 
	function zl(type){
		//先获取 原有的颜色
		var color1 = document.getElementById("rhpart_changeColor1").style.borderColor;
		var color2 = document.getElementById("rhpart_changeColor2").style.borderColor;
		var color3 = document.getElementById("rhpart_changeColor3").style.borderColor;
		var color4 = document.getElementById("rhpart_changeColor4").style.borderColor;
		var color5;
		var colors;
		if(document.getElementById("rhpart_changeColor5")!=null&&document.getElementById("rhpart_changeColor5")!=""){
			color5=document.getElementById("rhpart_changeColor5").style.borderColor;
			colors={color1:color1,color2:color2,color3:color3,color4:color4,color5:color5};
		}else{
			colors={color1:color1,color2:color2,color3:color3,color4:color4};
		}
		$("#this_rhpart_value").val(type);
		queryList("${param.id}","${param.role}",type,colors);//重新查询下
	}
	
</script>
</head>
<body>
<!-- ZJ17卷烟机判定-->
<c:if test="${loginInfo.equipmentCode>0&&loginInfo.equipmentCode<31}"> 
	<div class="demo_info_box">
		<div class="demo_info_title">
			&nbsp;${loginInfo.equipmentType}卷烟机润滑部位（VE、SE、MAX、HCF、ZF12B）&nbsp;
			<input type="hidden" id='this_plan_value' value="${param.id}"/ ><!-- 传ID值 -->
			<input type="hidden" id='this_plan_Code' value="${loginInfo.equipmentCode}"/ ><!-- 传code值 -->
			当前部位：<input type="button" id='this_rhpart_value' style="background:#A4A4A4;" class="btn btn-default" value=""/ >
		</div>
 	</div>
<div style="background:url('img/juanyanji.jpg'); width:700px; height:240px;margin-top:10px; margin-left:25px;">
<!-- ZF12B卸盘机 -->
	
	<table class="rhpart" id="rhpart_changeColor1" style=" border:1px solid red; position:absolute;top:60px;left:145px; width:100px; height:170px;" onclick="zl('ZF12B')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>ZF12B卸盘机</b></font> --></td>
		</tr>
	</table>
	
<!-- HCF装盘机 -->
	
	<table class="rhpart" id="rhpart_changeColor2" style="border:1px solid red; position:absolute;top:70px;left:255px; width:80px; height:140px;" onclick="zl('HCF')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>HCF装盘机</b> --></font></td>
		</tr>
	</table>
	

<!-- MAX滤嘴接装 -->	
	
	<table class="rhpart" id="rhpart_changeColor3" style="border:1px solid red; position:absolute;top:65px;left:340px; width:110px; height:125px;" onclick="zl('MAX')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>MAX滤嘴接装</b></font> --></td>
		</tr>
	</table>
	
	
<!-- SF卷制成型 -->
	
	<table class="rhpart" id="rhpart_changeColor4" style="border:1px solid red; position:absolute;top:90px;left:455px; width:85px; height:110px;" onclick="zl('SE')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>SF卷制成型 </b> --></font></td>
		</tr>
	</table>
	

<!-- VE喂丝机 -->	
	<table class="rhpart" id="rhpart_changeColor5" style="border:1px solid red; position:absolute;top:75px;left:545px; width:130px; height:180px;" onclick="zl('VE')">
	<tr>
	<td valign="bottom" align="center"><!-- <font size=2><b>VE喂丝机</b></font> --></td>
	</tr>
	</table>
</div>
</c:if>

 <!-- ZB25包装机判定 -->
<c:if test="${loginInfo.equipmentType=='ZB25'}"> 
<div class="demo_info_box">
	<div class="demo_info_title">
		&nbsp;${loginInfo.equipmentType}包装机润滑部位（YB25、YB55、YB65、YB95）&nbsp;
		<input type="hidden"   id='this_plan_value' value="${param.id}"/ >
		<input type="hidden" id='this_plan_Type' value="${loginInfo.equipmentType}"/ ><!-- 传code值 -->
		当前部位：<input type="button" id='this_rhpart_value' style="background:#A4A4A4;" class="btn btn-default" value=""/ >
	</div>
</div>
<div style="background:url('img/ZB25baozhuangji.jpg'); width:700px; height:240px;margin-top:10px; margin-left:55px;">
<!-- YB95条盒透明质部分 -->
	
	<table class="rhpart" id="rhpart_changeColor1" style="border:1px solid red; position:absolute;top:105px;left:135px; width:120px; height:70px;" onclick="zl('YB95')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b> YB95条盒透明质部分</b></font> --></td>
		</tr>
	</table>
	
<!-- YB55小包装透明纸部分-->
	
	<table class="rhpart" id="rhpart_changeColor2" style="border:1px solid red; position:absolute;top:180px;left:175px; width:80px; height:70px;" onclick="zl('YB55')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>YB55小包装透明纸部分</b> --></font></td>
		</tr>
	</table>
	

<!-- YB65条盒部分 -->	
	
	<table class="rhpart" id="rhpart_changeColor3" style="border:1px solid red; position:absolute;top:125px;left:260px; width:45px; height:125px;" onclick="zl('YB65')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>YB64条盒部分</b></font> --></td>
		</tr>
	</table>
	
<!-- YB25软包小包机 -->
	
	<table class="rhpart" id="rhpart_changeColor4" style="border:1px solid red; position:absolute;top:105px;left:380px; width:240px; height:150px;" onclick="zl('YB25')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b> YB65软包小包机  </b> --></font></td>
		</tr>
	</table>
	
</div>
</c:if>

 <!-- ZB45包装机判定 -->
<c:if test="${loginInfo.equipmentType=='ZB45'}"> 
<div class="demo_info_box">
	<div class="demo_info_title">
		&nbsp;${loginInfo.equipmentType}包装机润滑部位（YB45、YB55、YB65、YB95）&nbsp;
		<input type="hidden"   id='this_plan_value' value="${param.id}"/ >
		<input type="hidden" id='this_plan_Type' value="${loginInfo.equipmentType}"/ ><!-- 传code值 -->
		当前部位：<input type="button" id='this_rhpart_value' style="background:#A4A4A4;" class="btn btn-default" value=""/ >
	</div>
</div>
<div style="background:url('img/ZB45baozhuangji.jpg'); width:700px; height:240px;margin-top:10px; margin-left:55px;">
<!-- YB95条盒透明质部分 -->
	
	<table class="rhpart" id="rhpart_changeColor1" style="border:1px solid red; position:absolute;top:105px;left:135px; width:120px; height:70px;" onclick="zl('YB95')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b> YB95条盒透明质部分</b></font> --></td>
		</tr>
	</table>

<!-- YB55小包装透明纸部分-->
	
	<table class="rhpart" id="rhpart_changeColor2" style="border:1px solid red; position:absolute;top:180px;left:175px; width:80px; height:70px;" onclick="zl('YB55')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>YB55小包装透明纸部分</b> --></font></td>
		</tr>
	</table>
	

<!-- YB65条盒部分 -->	
	
	<table class="rhpart" id="rhpart_changeColor3" style="border:1px solid red; position:absolute;top:125px;left:260px; width:45px; height:125px;" onclick="zl('YB65')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b>YB64条盒部分</b></font> --></td>
		</tr>
	</table>

<!-- YB45硬盒小包机 -->
	
	<table class="rhpart" id="rhpart_changeColor4" style="border:1px solid red; position:absolute;top:105px;left:380px; width:240px; height:150px;" onclick="zl('YB45')">
		<tr>
		<td valign="bottom" align="center"><!-- <font size=2><b> YB65软包小包机  </b> --></font></td>
		</tr>
	</table>

</div>
</c:if>

<!-- 部位热点数据 -->
<div class="single_info_xiaohao">	
	<div class="single_info">
		<div class="fl" style="border:1px solid 9A9A9A;width:806px;height:220px;margin-left:5px;overflow-y:scroll;">
			<table border="1" borderColor="#9a9a9a"
				style="BORDER-COLLAPSE: collapse; font-size: 12px; font-weight: 500;"
				width="100%"  cellspacing="0" cellpadding="0">
				<thead id="shg-tab-thead"
					style="background: #5a5a5a; color: #fff;">
					<tr>
						<td height="30" algin="center">
							&nbsp; &nbsp; <input id="shg-tab-thead-CKA" name="shg-tab-thead-CKA" type="checkbox"/></td>
						</td>
						<td nowrap="nowrap" align="center">序号</td>
						<td align="center">名称</td>
						<td nowrap="nowrap" align="center">油品</td>
						<td nowrap="nowrap" align="center">润滑方式</td>
						<td nowrap="nowrap" align="center">加注量:单位</td>
						<td nowrap="nowrap" align="center">是否完成</td>
						<!-- <td nowrap="nowrap" align="center">预览图片</td> -->
					</tr>
				</thead>
				<tbody id="shg-tab-tbody">
				</tbody>
			</table>
		</div>
	</div>
</div>

</body>
</html>