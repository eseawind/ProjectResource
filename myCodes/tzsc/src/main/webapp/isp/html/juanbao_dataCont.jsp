<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="../css/juanbao_content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
   *{
      margin:0px;
      padding:0px;
      font-family: "微软雅黑";
    
   }
   body{
     width:00px;
     height:00px;
     background-image: url("../img/juanbao_3D.jpg");
   }
  
</style>
<script type="text/javascript">
   //页面加载进来，查询基础数据：(计划产量，预计完成时间)
   $(function(){
	   getAllRollerPackerDatas();
	   
	   //获取计划产量
	    $.post("${pageContext.request.contextPath}/pms/isp/getAllWorkOrderByQty.do",function(datas){
	    	var obj=eval("(" + datas + ")");
	    	var qty=obj.qty;
	    	if(qty>0){
	    		$(".jhcl").text(qty+"  箱");
	    	}else{
		    	$(".jhcl").text("0 箱");
	    	}
	    	//时间
	    	$(".jhsj").text(obj.etime);
	    });
   });
   window.setInterval("getAllRollerPackerDatas()",5000);
   function getAllRollerPackerDatas(){
		 $.post("${pageContext.request.contextPath}/pms/isp/getAllRollerPackerDatas.do",function(datas){
			for(var i=0;i<datas.length;i++){
				var data = datas[i]; //数据
				var code= data.code; //设备编号
				var online = data.online; //运行状态
				var speed = data.speed;　//车速
				var qty = data.qty;  //产量
				//console.info("产量="+qty+ "车速="+speed+ "code="+ code+"信号="+online);
				var R="";
				if(code<30){ //卷烟机
					R="jyz_";
					qty=(qty/50).toFixed(2);
				}else if(code>=31 && code<60){ //包装机
					R="bzj_";
					qty=(qty/2500).toFixed(2);
				}else if(code==152){ //成品库（螺旋提升机）
					$(".sjcl").text(qty+" 箱");
					continue;
				}else if(code>60 && code<71){//装箱机
					R="zxj_"
					$("#"+R+code+" font").text(qty+"箱");
					$("."+R+code).attr("src","../img/sos3.png");
				}
				if(online){
					if(speed==0){
						//车速为0
						$("#"+R+code+" font").text(qty+"箱");
						$("."+R+code).attr("src","../img/sos3.png");
						//$("#"+R+code).css("color","#f8e71c");
					}else{
						//车速!=0，且有产量
						$("#"+R+code+" font").text(qty+"箱");
						$("."+R+code).attr("src","../img/sos2.png");
						//$("#"+R+code).css("color","#4bc29d");
					}
				}else{
					//断开不处理
					$("#"+R+code+" font").text(qty+"箱");
					$("."+R+code).attr("src","../img/sos1.png");
					
				}
		  }
		},"JSON"); 
   }
</script>
<title>3D监控</title>
</head>
<body>
   <div>
       <span class="jhcl">150箱</span>
       <span class="sjcl">0箱</span>
       <span class="jhsj">2016年12月13日  12时12分</span>
   </div>    
   <div class="ssjl">
       	   <div>
		       <span id="zxj_61"><img class="zxj_61" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
		       <span id="zxj_62"><img class="zxj_62" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
		       <span id="zxj_63"><img class="zxj_63" src="../img/sos1.png" /><font class="ft_cl">0箱</font></span>
		       <span id="zxj_64"><img class="zxj_64" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
		   </div> 
		   <div>
			   <span id="jyj_13"><img class="jyj_31" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="jyj_6"><img class="jyj_6" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="jyj_5"><img class="jyj_5" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="jyj_4"><img class="jyj_4" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="jyj_3"><img class="jyj_3" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="jyj_2"><img class="jyj_2" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="jyj_1"><img class="jyj_1" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
		   </div>
		   <div>
			   <span id="bzj_43"><img class="bzj_43" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="bzj_36"><img class="bzj_36" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="bzj_35"><img class="bzj_35" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="bzj_34"><img class="bzj_34" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="bzj_33"><img class="bzj_33" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="bzj_32"><img class="bzj_32" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
			   <span id="bzj_31"><img class="bzj_31" src="../img/sos1.png"  /><font class="ft_cl">0箱</font></span>
		   </div>
   </div>
   <div id="juanbao_jyj2">
      <span id="bzj_44"> <img class="bzj_44" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="bzj_42"> <img class="bzj_42" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="bzj_41"> <img class="bzj_41" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="bzj_40"> <img class="bzj_40" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="bzj_39"> <img class="bzj_39" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="bzj_38"> <img class="bzj_38" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="bzj_37"> <img class="bzj_37" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
   </div>
   <div id="juanbao_bzj2">
      <span id="jyj_14"> <img class="jyj_14" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="jyj_12"> <img class="jyj_12" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="jyj_11"> <img class="jyj_11" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="jyj_10"> <img class="jyj_10" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="jyj_9"> <img  class="jyj_9" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="jyj_8"> <img  class="jyj_8" src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
      <span id="jyj_7"> <img  class="jyj_7"src="../img/sos1.png"/><font class="ft_cl">0箱</font></span>
   </div>
   
</body>
</html>