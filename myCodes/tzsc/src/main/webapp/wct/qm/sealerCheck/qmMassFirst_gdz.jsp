<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>工段长(复检)记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../../initlib/initMyAlert.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<script type="text/javascript">
	var schWorkorderId  ="";//工单主键ID 非常重要 全局变量
	var isMassCheckZjr = false;//质检人员是否登录
	var isLoadMassCheck1=false;
	var isLoadMassCheck2=false;
	var isLoadMassCheck3=false;
	
	$(function(){
		//默认进来弹出刷卡登录框
		$("#hid_div3").show();
		//tab1，tab2，tab3
		//实例化登录信息（包括机台，班次班组,牌名,工单号）
		var bandParams=function(params){
			$.post("${pageContext.request.contextPath}//wct/stat/initOutDataByEqp.do",params,function(v){				
				//alert("班组:"+v.team+":机台号："+v.eqpCod+":牌号:"+v.mat);
				$("#massCheckBc").html(v.shift);
				$("#massCheckJth").html(v.eqpName);
				$("#massCheckPm").html(v.mat);
				$('#eqpId').html(v.eqpId);
				$('#shiftID').html(v.shiftID);
				$('#matId').html(v.matId);
				//$("#massCheckProd").html(v.prodCode);
				schWorkorderId = v.schWorkorderId;//工单号 对应的主键
				if(schWorkorderId!=null){
					search();//工段长复检历史记录查询
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
		/* var equ ="${loginInfo.equipmentCode}";
		var bean ={eqpCod:equ};
		bandParams(bean);//加载数据 */
		var equ ="${loginInfo.equipmentCode}";
		var shiftId ="${loginInfo.shiftId}";
		var bean ={"eqpCod":equ,"shiftID":shiftId};
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
	<jsp:include page="../public/qmMassCheck_login.jsp"></jsp:include>
	
	<div id="wkd-qua-title">工段长(复检)记录</div>
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
				<td><div style="display:none;" id="eqpId"></div></td>
				<td><div style="display:none;" id="matId"></div></td>
				<td><div style="display:none;" id="shiftID"></div></td>
			</tr>
		</table>
	</div>
	<!-- 顶端tab布局 -->
	<div class="info_machine">	
		<div class="info_machine_cen fl">
			<ul class="nav nav-tabs" id="tabs" style="width: 900px;">
			 	<li><a href="#" name="#tab1" class="tab_1">首检记录</a></li>
			  	<li><a href="#" name="#tab2" class="tab_2">填写说明</a></li> 
			  	<!-- <div id="button_show1" style="float:left;width:80px;height:34px;">登录/注销</div>  -->
			  </ul>		
		</div>
	</div>
	<!-- 内容 -->
	<div id="content">
		<!-- 首标签布局 -->
		<div id="tab1" class="xhtj"  style="border:1px solid 9A9A9A;">
			<!-- tab1中的角色 -->
			<!-- 内容 1-->
			<div class="single_info_xiaohao">			
				<div class="single_info" id="scroll_box01" style="width:2440px;">
					<div class="demo_info_box">
						<div class="demo_info_title">
							&nbsp;工段长(复检)&nbsp;
							<input type="button" style="background:#A4A4A4;"
							onClick="search();" value="查询" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="insert();" value="新增" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="deletes();" value="删除" class="btn btn-default" />
							&nbsp; 
							<input type="button" style="background:#A4A4A4;"
							onClick="save();" value="保存" class="btn btn-default" />
						</div>
					</div>                                                             
					<div class="fl" style="border:1px solid 9A9A9A;width:800px;height:390px;margin-left:10px;overflow-y: scroll;">
						<jsp:include page="qmMassCheck_gdz.jsp"></jsp:include><!-- table 布局-->
					</div>
				</div>
			</div>
		</div>
		<div id="tab2" class="xhtj">
			<div class="info_status" >
				<div class="single_info_v" id="scroll_box2">
					<script>document.write(remark_wirte_carton);</script>
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
        for (var i = 1; i <= $("#tabs li").length; i++) {
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
		if(!isLoadMassCheck1){
			isLoadMassCheck1=true;
			isLoadMassCheck2=false;
			isLoadMassCheck3=false;
		}
		
    });
	$('.tab_2').click(function(){
		$("#scroll_box2").addClass('scroll_box');
		$("#scroll_box1").removeClass('scroll_box');
		$("#scroll_box3").removeClass('scroll_box');
		if(!isLoadMassCheck2){
			isLoadMassCheck2=true;
			isLoadMassCheck1=false;
			isLoadMassCheck3=false;
		}
	});
	$('.tab_3').click(function(){
		$("#scroll_box3").addClass('scroll_box');
		$("#scroll_box1").removeClass('scroll_box');
		$("#scroll_box2").removeClass('scroll_box');
		if(!isLoadMassCheck3){
			isLoadMassCheck3 =true;
			isLoadMassCheck1=false;
			isLoadMassCheck2=false;
		}
	});
    
  </script>
</body>
</html>