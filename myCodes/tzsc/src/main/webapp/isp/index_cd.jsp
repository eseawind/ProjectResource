<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>集中监控系统-ISP</title>
<meta name="author" content="leejean" />
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="css/fullcalendar.css" />
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="css/jquery.gritter.css" />
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/fullScreen.js" type="text/javascript" ></script>
<script type="text/javascript">
	$(function(){
		 $(".add-content").click(function(){
			var thiz= $(this);
			var url =  thiz.attr("url");
			//addContent(url,thiz);
		});
		$("#homepage").click();//进入首页默认点击首页按钮
	});
	//请求子页面
	function addContent(url,thiz){
		$.post(url,function(content){
			$("#content").html(null).html(content);
		    $("li[class='active']").removeClass("active");//点击前，干掉active
			thiz.parent().addClass("active");//添加active属性
		});
	}
	window.setInterval(function(){
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
	function frameUrl(type){
		var R=window.screen.width;//用于判断分辨率
		if(type=="rollerPackege"||type=="homepage"){//卷包车间
			if(R==1366){
				$("#frame").attr("src","rollerpacker_1366X768.jsp");
				$("#quanping").attr("href","rollerpacker_1366X768.jsp");
			}else{
				$("#frame").attr("src","rollerpacker_1920X1080.jsp");	
				$("#quanping").attr("href","rollerpacker_1920X1080.jsp");	
			}
		}else if(type=="roller"){//卷烟机
			if(R==1366){
				$("#frame").attr("src","roller_3d_1366.jsp");
				$("#quanping").attr("href","roller_3d_1366.jsp");
			}else{
				$("#frame").attr("src","roller_3d.jsp");
				$("#quanping").attr("href","roller_3d.jsp");
			}
		}
	}
</script>
</head>
<body>
<!--Header-part-->
<div id="header">
  <h1><a href="dashboard.html"></a></h1>
</div>
<!--close-Header-part--> 
<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li  class="dropdown" id="profile-messages" >
      <a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle">
      	<i class="icon icon-user"></i>  
      	<span class="text">${ispSessionInfo.user.name}</span>
      	<b class="caret"></b>
      </a>
      <ul class="dropdown-menu">
        <li><a href="#"><i class="icon-user"></i> My Profile</a></li>
        <li class="divider"></li>
        <li><a href="#"><i class="icon-check"></i> My Tasks</a></li>
        <li class="divider"></li>
        <li><a href="login.html"><i class="icon-key"></i> Log Out</a></li>
      </ul>
    </li>
    <li class="dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">系统通知</span> <span class="label label-important">5</span> <b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a class="sAdd" title="" href="#"><i class="icon-plus"></i> new message</a></li>
        <li class="divider"></li>
        <li><a class="sInbox" title="" href="#"><i class="icon-envelope"></i> inbox</a></li>
        <li class="divider"></li>
        <li><a class="sOutbox" title="" href="#"><i class="icon-arrow-up"></i> outbox</a></li>
        <li class="divider"></li>
        <li><a class="sTrash" title="" href="#"><i class="icon-trash"></i> trash</a></li>
      </ul>
    </li>
    <li class=""><a title="设置" href="#"><i class="icon icon-cog"></i> <span class="text">设置</span></a></li>
    <li class=""><a title="注销登录" href="login.jsp"><i class="icon icon-share-alt"></i> <span class="text">注销登录</span></a></li>
    <li class=""><a id="quanping" title="全屏"  target=_black><i class="icon icon-share-alt"></i> <span class="text" id="logo">全屏</span></a></li>
  </ul>
</div>
<!--close-top-Header-menu-->
<!--start-top-serch-->
 <div id="search">
  <i class="icon icon-time">&nbsp;系统时间:</i>&nbsp;&nbsp;<span class="text" id="time" style="color:white;"></span>
</div>
<!--close-top-serch-->
<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i></a>
  <ul>
    <li><a href="#" id="homepage" class="add-content" onclick="frameUrl('homepage');"  url="home.jsp"><i class="icon icon-home"></i> <span>首页</span></a> </li>
    <li class="submenu"> <a href="#"><i class="icon icon-info-sign"></i> <span>车间3D监控</span> <span class="label label-info">2</span></a>
      <ul>
        <li><a href="#" id="rollerPackege" onclick="frameUrl('rollerPackege');"  class="add-content" url="rollerpacker_3d.jsp">卷包车间3D监控</a></li>
       <!--  <li><a href="#"  class="add-content" url="molding_3d.jsp">成型车间3D监控</a></li> -->
      </ul>
    </li>
    <li class="submenu"><a href="#"><i class="icon icon-signal"></i> <span>机台3D监控</span><span class="label label-important">6</span></a> 
       <ul>
	        <li><a href="#" id="roller" onclick="frameUrl('roller');"  class="add-content" url="roller_3d.jsp">卷烟机3D监控</a></li>
	        <!-- <li><a href="#"  class="add-content" url="packer_3d.jsp">包装机3D监控</a></li>
	        <li><a href="#"  class="add-content" url="linker_3d.jsp">条烟输送3D监控</a></li>
	        <li><a href="#"  class="add-content" url="boxer_3d.jsp">封箱机3D监控</a></li>
	        <li><a href="#"  class="add-content" url="shooter_3d.jsp">发射机3D监控</a></li>
	        <li><a href="#"  class="add-content" url="filter_3d.jsp">成型机3D监控</a></li> -->
      </ul>
    </li>
   <!--  <li class="submenu"><a href="#"><i class="icon icon-inbox"></i> <span>车间温度监控</span><span class="label label-important">2</span></a> 
    	<ul>
        <li><a href="#"  class="add-content" url="rollerpacker_temperature_humidity.jsp">卷包车间温度监控</a></li>
        <li><a href="#"  class="add-content" url="molding_temperature_humidity.jsp">成型车间温度监控</a></li>
      </ul>
    </li> -->
    <!-- <li><a href="#"  class="add-content" url="refrigeration.jsp"><i class="icon icon-th"></i> <span>集中制冷监控</span></a></li>
    <li><a href="#"  class="add-content" url="roller_packer_dust.jsp"><i class="icon icon-fullscreen"></i> <span>卷包除尘监控</span></a></li>
    <li><a href="#"  class="add-content" url="glycerin.jsp"><i class="icon icon-th-list"></i> <span>甘油酯集中输送监控</span></a></li>
    <li><a href="#"  class="add-content" url="filter_rod.jsp"><i class="icon icon-tint"></i> <span>滤棒交换站监控</span></a></li>
    <li><a href="#"  class="add-content" url="cut_tobacco.jsp"><i class="icon icon-pencil"></i> <span>烟丝输送交换站监控</span></a></li>
    <li><a href="#"  class="add-content" url="waste_disposal.jsp"><i class="icon icon-pencil"></i> <span>废烟支处理机监控</span></a></li>
    <li><a href="#"  class="add-content" url="second_calculation_of_energy.jsp"><i class="icon icon-pencil"></i> <span>动力能源二级计量监控</span></a></li>
    <li><a href="#"  class="add-content" url="cut_tobacco_cupboard.jsp"><i class="icon icon-pencil"></i> <span>储丝柜控制系统监控</span></a></li>
    <li><a href="#"  class="add-content" url="illumination.jsp"><i class="icon icon-align-right"></i> <span>集中照明系统监控</span></a></li> -->
  </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content" style=" background-color: #CDC9C9;width:90%;height:100%">
 <div id="content-header" style=" width:100%;height:90%;">
    <!-- 内容填充区域 -->
    <iframe id="frame" scrolling="no"  name="frame"  style="width:1700px;height:950px; background-color: #CDC9C9;border:0;"></iframe>
   		<!-- <iframe  id="frame" scrolling="no" name="frame" src="rollerpacker_1920X1080.jsp" style="width:1700px;height:1000px; background-color: #CDC9C9;border:0;"></iframe> -->
     <!-- <iframe  id="frame" name="frame" src="roller_3d.jsp" style="width:1700px;height:1080px;background-color: #EDEDED;border:0;"></iframe> -->
  </div>
</div>

<!--end-main-container-part-->

<!--Footer-part-->

<div class="row-fluid">
  <div id="footer" class="span12"> 2014 &copy;  版权所有  
  <a href="http://www.shlanbao.cn">上海兰宝传感科技股份有限公司 </a> (推荐使用IE9+,谷歌浏览器可以获得更快,更安全的页面响应速度)</div>
</div>

<!--end-Footer-part-->

 <script src="js/excanvas.min.js"></script> 
<script src="js/jquery.min.js"></script> 
<script src="js/jquery.ui.custom.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/jquery.flot.min.js"></script> 
<script src="js/jquery.flot.resize.min.js"></script> 
<script src="js/jquery.peity.min.js"></script> 
<script src="js/fullcalendar.min.js"></script> 
<script src="js/matrix.js"></script> 
<script src="js/matrix.dashboard.js"></script> 
<script src="js/jquery.gritter.min.js"></script> 
<script src="js/matrix.interface.js"></script> 
<script src="js/matrix.chat.js"></script> 
<script src="js/jquery.validate.js"></script> 
<script src="js/matrix.form_validation.js"></script> 
<script src="js/jquery.wizard.js"></script> 
<script src="js/jquery.uniform.js"></script> 
<script src="js/select2.min.js"></script> 
<script src="js/matrix.popover.js"></script> 
<script src="js/jquery.dataTables.min.js"></script> 
<script src="js/matrix.tables.js"></script> 

<script type="text/javascript">
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
  function goPage (newURL) {

      // if url is empty, skip the menu dividers and reset the menu selection to default
      if (newURL != "") {
      
          // if url is "-", it is this page -- reset the menu:
          if (newURL == "-" ) {
              resetMenu();            
          } 
          // else, send page to designated URL            
          else {  
            document.location.href = newURL;
          }
      }
  }

// resets the menu selection upon entry to this page:
function resetMenu() {
   document.gomenu.selector.selectedIndex = 2;
}
  
/* $('#logo').click(function () {
	screenfull.toggle($('#container')[0]);
}); */
</script>
</body>
</html>
