<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<link href="css/jb_index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/juanbao.js"></script>
<script type="text/javascript">
/***
  左边菜单点击选中样式
*/
//点击菜单事件
function menu_(id){
	 //选中当前
	  $(".menu_"+id).html("");
	  $(".menu_"+id).html("<img src='img/menu/menu0"+id+".png' />");
	  
	  var len=$("#box_left_menu ul li").length;
	  for(var i=1;i<=len;i++){
		  if(id!=i){
			  var img="<img src='img/menu/menu"+i+".png' />";
				 $(".menu_"+i).html("");
				 $(".menu_"+i).html(img);
		  }
	  }
	 
}



</script>
</head>

<body>
   <div id="box">
       <div id="box_left">
            <div id="box_left_logo">
                 <span class="logo_icon1" id="maxminche"><img src="img/logo_0.png" /></span>
                 <span class="logo_font1"><img src="img/logo_1.png" /></span>
            </div>
            <div id="box_left_menu">
                 <ul>
                     <li class="menu_1" onclick="menu_('1')">
                         <img src="img/menu/menu01.png" />
                     </li>
                     <li class="menu_2" onclick="menu_('2')">
                         <img src="img/menu/menu2.png" />
                     </li>
                     <li class="menu_3" onclick="menu_('3')">
                         <img src="img/menu/menu3.png" />
                     </li>
                     <li class="menu_4" onclick="menu_('4')">
                         <img src="img/menu/menu4.png" />
                     </li>
                     <li class="menu_5" onclick="menu_('5')">
                         <img src="img/menu/menu5.png" />
                     </li>
                     <li class="menu_6" onclick="menu_('6')">
                         <img src="img/menu/menu6.png" />
                     </li>
                     <li class="menu_7" onclick="menu_('7')">
                         <img src="img/menu/menu7.png" />
                     </li>
                     <li class="menu_8" onclick="menu_('8')">
                         <img src="img/menu/menu8.png" />
                     </li>
                      <li class="menu_9" onclick="menu_('9')">
                         <img src="img/menu/menu9.png" />
                     </li>
                 </ul>
            </div>
       </div>
       <div id="box_right">
            <div id="box_right_top">
                  <div class="box_right_time">
                     <span class="box_right_time01"><img src="img/times.png"/></span>
                     <span class="box_right_font01">2016年12月12日  12时12分</span>
                  </div>
                  <div id="box_right_top">
                     <span class="title">滕州卷烟厂卷包车间集中监控平台</span>
                     <span class="title_menu">
                         <ul>
                             <li><img src="img/glx/tx.png"/></li>
                             <li><img src="img/glx/sz.png"/></li>
                             <li><img src="img/glx/rt.png"/></li>
                             <li><img src="img/glx/zx.png"/></li>	
                         </ul>
                     </span>
                  </div>
            </div>
            <div id="box_right_content">
               <div id="box_right_bx">
                      <!--  根据左边菜单，显示相关页面  -->
                      <iframe id="menu_content" scrolling="no" frameborder="0" src="html/juanbao_index.jsp" style="width:100%;height:100%"></iframe>
              </div>
            </div>
       </div>
   </div>
 
</body>
</html>
