<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>WCT车间协同终端</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/global.css"></link>
<!-- jq 核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.js" type="text/javascript" ></script>
<!-- WCT 公共样式 -->
 <script type='text/javascript' src='/tzsc/dwr/engine.js'></script>
  <script type='text/javascript' src='/tzsc/dwr/interface/usertest.js'></script>
  <script type='text/javascript' src='/tzsc/dwr/util.js'></script>
<!-- role变量 -->
<script src="${pageContext.request.contextPath}/wct/js/public.js" type="text/javascript" ></script>
<!-- 载入Bootstrap -->
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/js/bootstrap.min.js"></script>





<!-- 全屏 -->
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/fullScreen.js" type="text/javascript" ></script>
<script type="text/javascript">
	var yesOrNotRequestIndexPageData=true;//是否请求首页数据
    var pid=0;
	$(function(){
		//隐藏弹屏窗口
		$("#checkNum").hide();
		 dwr.engine.setActiveReverseAjax(true);
		
		
		/*   */
		if("${loginInfo}"==""){
			location="${pageContext.request.contextPath}/wct/sys/login.jsp";
		}
		var url="/wct/isp/${loginInfo.equipmentType}.jsp";		
		//主页链接
		$("#wct-main").attr("path",url);
		/* $.post("${pageContext.request.contextPath}/wct/isp/"+equipmentType+".jsp",function(htm){
			$("#wct-body").html(htm);
		}); */
		$.post("${pageContext.request.contextPath}/"+url,function(htm){
			$("#wct-body").html(htm);
		});
		
		$(".nav-a").click(function(){			
			var zs=$(this);
			//如果点击的目录不是‘机台监控’首页停止请求数据
			if(zs.children().attr("id")=="wct_jtjk"){
				yesOrNotRequestIndexPageData=true;
			}else{
				yesOrNotRequestIndexPageData=false;
			}
			//alert("change:"+yesOrNotRequestIndexPageData);
			var path="${pageContext.request.contextPath}"+zs.attr("path");
			$.post(path,function(htm){
				//console.info(path);
				$("#wct-body").html(null);
				$("#wct-body").html(htm);
			});
		});
		
		window.setInterval(function(){
			if("${loginInfo}"==""){
				location="${pageContext.request.contextPath}/wct/login.jsp";
			}
			 var now= new Date();//获取当前时间
			 var year=now.getFullYear();//年
			 var month=now.getMonth()+1;//月
			 var date=now.getDate();//日
			 var day = now.getDay();//星期
			 var hour=now.getHours();//时
			 var minute=now.getMinutes();//分
	
			 var seconds=now.getSeconds();
			    if(day == 0){
			        day=" 星期日";}
			        else if(day == 1){
			        day=" 星期一";}
			        else if(day == 2){
			        day=" 星期二";}
			        else if(day == 3){
			        day=" 星期三";}
			        else if(day == 4){
			        day=" 星期四";}
			        else if(day == 5){
			        day=" 星期五";}
			        else if(day == 6){
			        day=" 星期六"; }
			 if(hour<10)hour="0"+hour;
			 if(minute<10)minute="0"+minute;
			 if(seconds<10)seconds="0"+seconds;
			 if(date<10)date="0"+date;
			 if(month<10)month="0"+month;
			 var t = year+"-"+month+"-"+date+"&nbsp;"+day+"&nbsp;"+hour+":"+minute+":"+seconds;
			 
			$("#time").html(t);
		},1000);
		 $('#logo').click(function () {
				screenfull.toggle($('#container')[0]);
		});
		 
		 $('.nav-a').bind('click', function(){
				$('.nav-a').removeClass('active');
			    $(this).addClass('active');
		});
		 
	});
	
	//关闭弹窗
	function metClose(){
		$("#checkNum").hide();
	}
	//显示弹窗
	function text2(message){   
	    $("#checkNum").show();
	    $("#contextf").text(message);
	}
</script>


<style type="text/css">

/* 	菜单栏样式 */
#wct_scdd{background:url(sys/images/scdd_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_scdd,#wct_scdd:hover{background:url(sys/images/scdd.png) no-repeat;;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
#wct_xxzx{background:url(sys/images/xxzx_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_xxzx,#wct_xxzx:hover{background:url(sys/images/xxzx.png) no-repeat;;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
#wct_zlgl{background:url(sys/images/zlgl_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_zlgl,#wct_zlgl:hover{background:url(sys/images/zlgl.png) no-repeat;;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
#wct_sctj{background:url(sys/images/sctj_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_sctj,#wct_sctj:hover{background:url(sys/images/sctj.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
#wct_sbgl{background:url(sys/images/sbgl_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_sbgl,#wct_sbgl:hover{background:url(sys/images/sbgl.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
#wct_wdgl{background:url(sys/images/wdgl_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_wdgl,#wct_wdgl:hover{background:url(sys/images/wdgl.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
#wct_xtgl{background:url(sys/images/xtgl_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_xtgl,#wct_xtgl:hover{background:url(sys/images/xtgl.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
#wct_jtjk{background:url(sys/images/jtjk_0.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}
.active #wct_jtjk,#wct_jtjk:hover{background:url(sys/images/jtjk.png) no-repeat;width: 70px;height: 70px;background-size: 100% 100%;margin: 0 auto;}

/*消息提示框*/
#msg-div{
	position:absolute;
	height:300px;
	width:400px;
	bottom:0px;
	right:0px;
	background:#F5F5F5;
	border:2px solid #5A5A5A;
	z-index:999;
	display:none;
}
#title-left{
	width:370px;
	background:#999999;
	text-align:center;
	float:left;
	height:30px;
	line-height:30px;
	font-size:14px;
	font-weight:bold;
}
#title-right{
	height:30px;
	width:30px;
	text-align:center;
	float:left;
	background:#BEBEBE;
}
#close-msg{
	display:block;
	height:28px;
	width:30px;
	text-decoration:none;
	line-height:30px;
	line-height:30px;
	font-size:14px;
	font-weight:bold;
	color:black;
}
#close-msg:hover{
	display:block;
	height:30px;
	width:30px;
	text-decoration:none;
	background:#5A5A5A;
	color:white;
}
#msg-con{
	height:270px;
	margin-top:30px;	
}
#msg-list{width:100%;height:270px;overflow-y:auto}
#msg-list li{height:30px}
#msg-list li a{color:#3C3C3C;font-size:16px;
line-height:30px;
text-indent:15px;
display:block;height:100%;width:100%;text-decoration:none;}
#msg-list li a:hover{color:white;background:#5A5A5A}
/* 当班结束时，弹出产量验证窗口 样式 start  */
#checkNum{
	background:url(sys/images/checkNum.png) no-repeat;
	width: 800px;height: 490px;
	position:absolute;
	z-index: 9999; 
	margin-top:6%;
	margin-left:8%;
	
}
#baozNum{
    margin-top:20px;
    margin-left:30px;
    width: 755px;
    height: 450px;
}
#baozClose{
    width: 755px;
    height:31px;
    /* background-color: green; */
}
.baozCloseGb{
    margin-left:710px;
    width:43px;
    display:block;
    line-height:31px;
    cursor:pointer;
    /* background-color: red; */
}
#baozTitle{
   width: 755px;
   text-align:center;
  /* background-color:red; */
  height:60px;
  font-weight: bold;
}
#baozTitle span{
    font-size: 20px;
    /* padding-left:2px; */
}
.baozSf{
   padding-right:30px;
}

/* 当班结束时，弹出产量验证窗口 样式 end  */
</style>
</head>
<body id="wct-index-body" scroll="no" onload="dwr.engine.setActiveReverseAjax(true)">
    
	<div id="index-content" >
	<!-- 当班结束时，弹出产量验证框 -->
	<div id="checkNum">
	    <!-- 卷烟机 -->
	    <div id="juanyNum" style="display: none;"></div>
	    <!-- 包装机 -->
	    <div id="baozNum" >
	         <div id="baozClose"><span class="baozCloseGb" onclick="metClose()">&nbsp;</span></div>
	         <div id="baozTitle">
	             <span>设备名称：</span>
	             <span class="baozSf">4#包装机</span>
	             <span>班次：</span>
	             <span class="baozSf">早班</span>
	             <span>时间：</span>
	             <span class="baozSf">7:00~15:00</span>
	         </div>
	         <div id="contextf">
	          
	         </div>次
	    </div>
	</div>
			<div id="header">
				<div id="logo">
					<img src="sys/images/logo.png" /><br/>
					<span class="soft_name">车间协同终端</span>
				</div>
				<div id="options">
						<ul>	
							<!-- 主页 -->
							<li><a href="#" class="nav-a active" id="wct-main" ><div id="wct_jtjk"></div></a>
							</li>
							<!-- 生产调度 -->           
							<li><a href="#" class="nav-a" path="/wct/sch/schIndex.jsp" ><div id="wct_scdd"></div></a>
							</li>
							<!-- 现场协同       
							<li><a href="#" class="nav-a" path="/wct/call/callIndex.jsp" ><div id="wct_xxzx"></div></a>
							</li>
							 -->   
							<!-- 成本考核 -->  
							<li><a href="#" class="nav-a" path="/wct/cost/costIndex.jsp" ><div id="wct_xxzx"></div></a>
							</li>
							<!-- 质量管理 -->          
							<li><a href="#" class="nav-a" path="/wct/qm/qmIndex.jsp" ><div id="wct_zlgl"></div></a>
							</li>
							<!-- 生产统计 -->          
							<li><a href="#" class="nav-a" path="/wct/stat/statIndex.jsp" ><div id="wct_sctj"></div></a>
							</li>
							<!-- 设备管理 -->           
							<li><a href="#" class="nav-a" path="/wct/eqm/eqmIndex.jsp" ><div id="wct_sbgl"></div></a>
							</li>
							<!-- 文档管理 -->          
							<li><a href="#" class="nav-a" path="/wct/doc/doc_index.jsp" ><div id="wct_wdgl"></div></a>
							</li>
							<!-- 系统管理 -->          
							<li><a href="#" class="nav-a" path="/wct/sys/systemIndex.jsp" ><div id="wct_xtgl"></div></a>
							</li>
						</ul>
				</div>			
						
			</div>
			<div id="wct-body">
							
			</div>
			<div id="footer">
			<div id="message_box">
				<marquee scrollDelay="150"id="message">
				山东中烟工业有限责任公司滕州卷烟厂
				</marquee>
			</div>
				<div style="padding: 0px 10px;font-size:14px;width: 990PX;line-height: 26px;">
					<table id="ft-tab" style="width: 975px;">
						<tr>
							<td style="text-align:right;">设备：</td>
							<td width="90" class="ft-det">${loginInfo.equipmentName}</td>
							<td style="text-align:right;">班次：</td>
							<td width="40" class="ft-det">${loginInfo.shift}</td>
							<td style="text-align:right;">班组：</td>
							<td width="40" class="ft-det">${loginInfo.team}</td>
							<td style="text-align:right;">登录用户：</td>
							<td style="width: 90px;" class="ft-det">${loginInfo.name}</td>
							<td style="text-align:right;">系统时间：</td>
							<td width="170" id="time" style="text-align:left;font-size:12px;"></td>
							<td width="240" style="text-align:right;color:#0067b2;font-weight: bold;">上海兰宝传感科技股份有限公司</td>
						</tr>
					</table>
				</div>
			</div>
	</div>
	
	
	
</body>
</html>