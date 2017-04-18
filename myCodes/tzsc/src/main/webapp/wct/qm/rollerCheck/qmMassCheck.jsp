<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质量质检记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<script type="text/javascript">
	var schWorkorderId  ="";//工单主键ID 非常重要 全局变量
	var isMassCheckZjr = false;//质检人员是否登录
	var isLoadMassCheck1=false;
	var isLoadMassCheck2=false;
	var isLoadMassCheck3=false;
	var isLoadMassCheck4=false;
	var isLoadMassCheck5=false;
	
	$(function(){
		//默认光标选中文本框
	    $("#cardNum").focus();
		//tab1，tab2，tab3
		$("#botton_show1").click(function(){$("#scroll_box02").animate({ left: "0px"}, 1000 );});	
		$("#botton_show2").click(function(){$("#scroll_box02").animate({ left: "-810px"}, 1000 );});	
		$("#botton_show3").click(function(){$("#scroll_box02").animate({ left: "-1620px"}, 1000 );});
		//实例化登录信息（包括机台，班次班组,牌名,工单号）
		var bandParams=function(params){
			$.post("${pageContext.request.contextPath}//wct/stat/initOutDataByEqp.do",params,function(v){				
				//alert("班组:"+v.team+":机台号："+v.eqpCod+":牌号:"+v.mat);
				$("#massCheckBc").html(v.team);
				$("#massCheckJth").html(v.eqpCod);
				$("#massCheckPm").html(v.mat);
				//$("#massCheckProd").html(v.prodCode);
				schWorkorderId = v.schWorkorderId;//工单号 对应的主键
				if(schWorkorderId!=null){
					czgSearch();//首检记录
					zjySearch();//
					gdzSearch();//
					isLoadMassCheck1 =true;
				}
				$("#massCheckSchWorkorderId").html(schWorkorderId);
				$("#massCheckDcg").html("${loginInfo.name}");
				if("${loginWctZjInfo.isSuccess}"=="true"){
					$("#massCheckZt").html("登录成功");//状态
					$("#massCheckZjr").html("${loginWctZjInfo.name}");//质检人
					$("#isMassCheckZjr").html("${loginWctZjInfo.isSuccess}");
				}else{
					$("#massCheckZt").html("未登录");//状态
					$("#massCheckZjr").html("...");//质检人
					$("#isMassCheckZjr").html("false");
				}
			},"JSON");
		};
		var equ ="${loginInfo.equipmentCode}";//EG:5号 机组 测试才这样写的
		var bean ={eqpCod:equ};
		bandParams(bean);//加载数据
		//弹出窗口
		$("#button_show1").click(function(){
			openWin();
		});

	});
</script>

<style>
	.btn{
		padding:2px 12px;
	}
	.btn-default {
		margin-right:1px;
	}
	.demo_info_box{width:3480px;height: 35px;margin-top: 6px;margin-bottom: 5px;}
	.demo_info_box .demo_info_title{
		width: 805px;height: 38px;background: #5A5A5A;
		float: left;margin-left: 5px;font-size: 14px;
		font-weight: bold;text-align: left;line-height: 35px;
		color:#fff;border-radius: 4px 4px 0px 0px;border-bottom: 2px solid #AAA1A1;
	}
	#botton_show1,#botton_show2,#botton_show3{
		height: 30px;width: 70px;float: left;background: #0067b2;border-radius:10px;
		font-weight: bold;text-align: center;line-height: 30px;color: #fff;cursor: pointer;
		margin-right:10px;
	}
	#botton_show3:hover{background: #5A5A5A;}
	
	#button_show1{
		height: 30px;width: 70px;float: left;background: #a4a4a4;border-radius:5px;
		font-weight: bold;text-align: center;line-height: 30px;color: #fff;cursor: pointer;
		line-height: 38px;
		margin-right:1px;
	}
	#button_show1:hover{background: #5A5A5A;}
 
	#wkd-qua-msg-title{
		font-size: 12px;font-weight: bold;
		text-align:left;
		background: #DEDCDA;
		border-bottom: 1px solid #838383;
	}
	.info_machine{/* 首检记录 tab样式 */
		margin-top:3px;
	}
	.xhtj{/* 内容样式 */
		height: 480px;
	}
	.single_info{/*操作工(首检) 内容 样式*/
		margin-top:0px;
	}
	
	
	.gczj_info_title {
		width: 810px;
		height: 38px;
		background: #5A5A5A;
		float: left;
		margin-left: 0px;
		font-size: 14px;
		font-weight: bold;
		text-align: left;
		line-height: 35px;
		color: #fff;
		border-radius: 4px 4px 0px 0px;
		border-bottom: 2px solid #AAA1A1;
	}
</style>

</head>
<body>
	<!-- 用户登录窗口 -->
	<jsp:include page="qmMassCheck_login.jsp"></jsp:include>
	
	<div id="wkd-qua-title">质量质检记录</div>
	<div id="wkd-qua-msg-title">
		<table style="font-size: 14px;">
			<tr>
				<td>&nbsp;&nbsp;<b>班次：</b></td><td><div id="massCheckBc"></div></td>
				<td><b>&nbsp;机台号：</b></td>			<td><div id="massCheckJth"></div></td>
				<td><b>&nbsp;牌名：</b></td>			<td><div id="massCheckPm"></div> </td>
				<td><b>&nbsp;登录人：</b></td>			<td><div id="massCheckDcg"></div></td>
				<!-- <td><b>&nbsp;工单号：</b></td>			<td><div id="massCheckProd"></div> -->
				<td>
					<div style="display:none;" id="massCheckSchWorkorderId"></div>
				</td>
				<td width="10"></td>
				<td><b>自检人:</b></td><td><div id="massCheckZjr">...</div></td>
				<td><b>状态:</b></td><td><div id="massCheckZt">...</div></td>
				<td><div style="display:none;" id="isMassCheckZjr"></div></td>
			</tr>
		</table>
	</div>
	<!-- 顶端tab布局 -->
	<div class="info_machine">	
		<div class="info_machine_cen fl">
			<ul class="nav nav-tabs" id="tabs" style="width: 900px;">
			 	<li><a href="#" name="#tab1" class="tab_1">首检记录</a></li>
				<li><a href="#" name="#tab2" class="tab_2" style="width:90px">过程自检记录</a></li>
			    <li><a href="#" name="#tab3" class="tab_3">综合测试台</a></li>
			  	<li><a href="#" name="#tab4" class="tab_4" style="width:190px">辅料、自检自控装置确认记录</a></li>
			  	<li><a href="#" name="#tab5" class="tab_5">丝含梗</a></li> 
			  	<li><a href="#" name="#tab6" class="tab_6">填写说明</a></li> 
			  	<div id="button_show1" style="float:left;width:80px;height:34px;">登录/注销</div> 
			  </ul>		
		</div>
	</div>
	<!-- 内容 -->
	<div id="content">
		<!-- 首标签布局 -->
		<div id="tab1" class="xhtj"  style="border:1px solid 9A9A9A;">
			<!-- tab1中的角色 -->
			<div class="info_status" style="height:36px;" >
				<div class="single_info_v" id="scroll_box1">
					<div class="fl"  style="margin-left:5px;">
						<div id="botton_show1" style="float:left;width:100px;height:30px">操作工(首检)</div>
						<div id="botton_show2" style="float:left;width:100px;height:30px">自检员(复检)</div>
						<div id="botton_show3" style="float:left;width:100px;height:30px">工段长(复检)</div>
					</div>
				</div>						 
			</div>
			<!-- 内容 1-->
			<div class="single_info_xiaohao">			
				<div class="single_info" id="scroll_box02" style="width:2440px;">
					<div class="demo_info_box">
						<div class="demo_info_title">
							&nbsp;操作工(首检)&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="czgSearch();" value="查询" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="czgInsert();" value="新增" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="czgDeletes();" value="删除" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="czgSave();" value="保存" class="btn btn-default" />
						</div>
						<div class="demo_info_title">
							&nbsp;自检员(复检)&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="zjySearch();" value="查询" class="btn btn-default" />
							&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="zjyDeletes();" value="删除" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="zjySave();" value="保存" class="btn btn-default" />
						</div>
						<div class="demo_info_title">
							&nbsp;工段长(复检)&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="gdzSearch();" value="查询" class="btn btn-default" />
							&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="gdzDeletes();" value="删除" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="gdzSave();" value="保存" class="btn btn-default" />
						</div>
					</div>
					<div class="fl" style="border:1px solid 9A9A9A;width:800px;height:390px;margin-left:5px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_czg.jsp"></jsp:include><!-- table 布局-->
					</div>                                                               
					<div class="fl" style="border:1px solid 9A9A9A;width:800px;height:390px;margin-left:7px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_zjy.jsp"></jsp:include><!-- table 布局-->                                                               
					</div>                                                               
					<div class="fl" style="border:1px solid 9A9A9A;width:800px;height:390px;margin-left:10px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_gdz.jsp"></jsp:include><!-- table 布局-->
					</div>
				</div>
			</div>
			
		</div>
		<div id="tab2" class="xhtj">
			<div class="info_status " >
				<div class="single_info_v" id="scroll_box2">
					<div class="demo_info_box">
						<div class="gczj_info_title">
							&nbsp;过程自检记录&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="gczjSearch();" value="查询" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="gczjInsert();" value="新增" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="gczjDeletes();" value="删除" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="gczjSave();" value="保存" class="btn btn-default" />
						</div>
					</div>
					<div class="fl" style="border:1px solid 9A9A9A;width:808px;height:420px;margin-left:0px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_gczj.jsp"></jsp:include><!-- table 布局-->
					</div>
				</div>
			</div>					
		</div>
		<div id="tab3" class="xhtj">
			<div class="info_status " >
				<div class="single_info_v" id="scroll_box3">
					<div class="demo_info_box">
						<div class="gczj_info_title">
							&nbsp;在线物理指标自检记录(综合测试台)&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="zhcsSearch();" value="查询" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="zhcsInsert();" value="新增" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="zhcsDeletes();" value="删除" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="zhcsSave();" value="保存" class="btn btn-default" />
						</div>
					</div>
					<div class="fl" style="border:1px solid 9A9A9A;width:808px;height:420px;margin-left:0px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_zhcs.jsp"></jsp:include><!-- table 布局-->
					</div>
				</div>
			</div>					
		</div>
		<div id="tab4" class="xhtj">
			<div class="info_status" >
				<div class="single_info_v" id="scroll_box4">
					<div class="demo_info_box">
						<div class="gczj_info_title">
							&nbsp;辅料、自检自控装置确认记录&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="flzjSearch();" value="查询" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="flzjSave();" value="保存" class="btn btn-default" />
						</div>
					</div>
					<div class="fl" style="border:1px solid 9A9A9A;width:808px;height:420px;margin-left:0px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_flzj.jsp"></jsp:include><!-- table 布局-->
					</div>
				</div>
			</div>					
		</div>
		<div id="tab5" class="xhtj">
			<div class="info_status" >
				<div class="single_info_v" id="scroll_box5">
					<div class="demo_info_box">
						<div class="gczj_info_title">
							&nbsp;丝含梗&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="shgSearch();" value="查询" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="shgInsert();" value="新增" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="shgDeletes();" value="删除" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="shgSave();" value="保存" class="btn btn-default" />
						</div>
					</div>
					<div class="fl" style="border:1px solid 9A9A9A;width:808px;height:420px;margin-left:0px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_shg.jsp"></jsp:include><!-- table 布局-->
					</div>
				</div>
			</div>					
		</div>
		<div id="tab6" class="xhtj">
			<div class="info_status" >
				<div class="single_info_v" id="scroll_box6">
					1.检验时间按24小时制填写。<br/>
					2.外观质量情况栏填写，当检验无质量问题，则在填写“0”；<br/>
					  如有质量问题，则必须填写质量缺陷代码，不合格数量（以支为单位）填写在后面；<br/>
					  若属“其它”质量缺陷，首检应直接描述缺陷和数量，过程检验应在“备注”栏内注明缺陷具体的名称。<br/>
					3.若当前检验合格，则在“判断”栏内划“√”，“复检”栏划“-”；<br/>
					 若当次检验不合格，则在“判断”栏内划“错”，并进行复检,根据复检结果在“复检栏”划“√”或“×”。<br/>
					4.“产品段”栏填写检验时机台产量，单位为万支。<br/>
					5.表中结合实际无需填写项目和空余栏均划“-”。
				</div>
			</div>					
		</div>
	</div>	
	
 <script>
    function resetTabs(){
        $("#content > div").hide();
        $("#tabs a").attr("id","");
    }
    var myUrl = window.location.href;
    var myUrlTab = myUrl.substring(myUrl.indexOf("#")); 
    var myUrlTabName = myUrlTab.substring(0,4);
    (function(){
        $("#content > div").hide(); 
        $("#tabs li:first a").attr("id","current"); 
        $("#content > div:first").fadeIn();
        
        $("#tabs a").on("click",function(e) {
            e.preventDefault();
            if ($(this).attr("id") == "current"){ 
             return       
            }
            else{             
            resetTabs();
            $(this).attr("id","current");
            $($(this).attr('name')).fadeIn();
            }
        });

        for (i = 1; i <= $("#tabs li").length; i++) {
          if (myUrlTab == myUrlTabName + i) {
              resetTabs();
              $("a[name='"+myUrlTab+"']").attr("id","current"); 
              $(myUrlTab).fadeIn();     
          }
        }
    })();    
	$("#slideDown").click(function(){$(".scroll_box").animate({ top: "-476px"}, 500 );});	
	$("#slideUp").click(function(){$(".scroll_box").animate({ top: "0px"}, 500 );});


	//通过切换按钮修改class
	$('.tab_1').click(function(){
		$("#scroll_box1").addClass('scroll_box');
		$("#scroll_box2").removeClass('scroll_box');
		$("#scroll_box3").removeClass('scroll_box');
		$("#scroll_box4").removeClass('scroll_box');
		$("#scroll_box5").removeClass('scroll_box');
		$("#scroll_box6").removeClass('scroll_box');
		if(!isLoadMassCheck1){
			czgSearch();//
			zjySearch();//
			gdzSearch();//
		}
		
    });
	$('.tab_2').click(function(){
		$("#scroll_box2").addClass('scroll_box');
		$("#scroll_box1").removeClass('scroll_box');
		$("#scroll_box3").removeClass('scroll_box');
		$("#scroll_box4").removeClass('scroll_box');
		$("#scroll_box5").removeClass('scroll_box');
		$("#scroll_box6").removeClass('scroll_box');
		if(!isLoadMassCheck2){
			gczjSearch();//
			isLoadMassCheck2=true;
		}
	});
	$('.tab_3').click(function(){
		$("#scroll_box3").addClass('scroll_box');
		$("#scroll_box1").removeClass('scroll_box');
		$("#scroll_box2").removeClass('scroll_box');
		$("#scroll_box4").removeClass('scroll_box');
		$("#scroll_box5").removeClass('scroll_box');
		$("#scroll_box6").removeClass('scroll_box');
		if(!isLoadMassCheck3){
			zhcsSearch();//
			isLoadMassCheck3 =true;
		}
	});
	$('.tab_4').click(function(){
		$("#scroll_box4").addClass('scroll_box');
		$("#scroll_box1").removeClass('scroll_box');
		$("#scroll_box2").removeClass('scroll_box');
		$("#scroll_box3").removeClass('scroll_box');
		$("#scroll_box5").removeClass('scroll_box');
		$("#scroll_box6").removeClass('scroll_box');
		if(!isLoadMassCheck4){
			flzjSearch();//查询辅料数据,填充数据
			isLoadMassCheck4 =true;
		}
		
	});
	$('.tab_5').click(function(){
		$("#scroll_box5").addClass('scroll_box');
		$("#scroll_box1").removeClass('scroll_box');
		$("#scroll_box2").removeClass('scroll_box');
		$("#scroll_box3").removeClass('scroll_box');
		$("#scroll_box4").removeClass('scroll_box');
		$("#scroll_box6").removeClass('scroll_box');
		if(!isLoadMassCheck5){
			shgSearch();//
			isLoadMassCheck5 =true;
		}
	});
	$('.tab_6').click(function(){
		$("#scroll_box6").addClass('scroll_box');
		$("#scroll_box1").removeClass('scroll_box');
		$("#scroll_box2").removeClass('scroll_box');
		$("#scroll_box3").removeClass('scroll_box');
		$("#scroll_box4").removeClass('scroll_box');
		$("#scroll_box5").removeClass('scroll_box');
	});
    
  </script>
</body>
</html>