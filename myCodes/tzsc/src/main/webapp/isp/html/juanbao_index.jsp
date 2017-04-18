<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卷包3D监控</title>
<link href="../css/jb_index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript">
	/**
	  右边菜单点击选中样式
	**/
	function rmenuCehOn(id){
		//获取li个数
		var len=$("#box_right_mu ul li").length;
		//取消所有选中
		for(var i=1;i<=len;i++){
			 $("#rightmu_"+i).attr("class","cheOn_01");
			 $("#rightmu_"+i+" img").attr("src","../img/right_1.png");
		}
		//给选中添加样式
		$("#rightmu_"+id).attr("class","cheOn_00");
		//给选中更改图标
		$("#rightmu_"+id+" img").attr("src","../img/right_0.png");
		
		/** 更新内容 */
		if(id==1){//数据中心
			$("#show3DIMG").attr("src","../html/juanbao_dataCont.jsp");
		}else if(id==2){//卷包车间
			$("#show3DIMG").attr("src","../html/juanbao_dataCont.jsp");
		}else if(id==3){//发射机
			
		}else if(id==4){//成型机
			
		}	
}

</script>
</head>

<body>
      <div id="box_right_mu">
         <ul>
              <li id="rightmu_1" onclick="rmenuCehOn('1')" class="cheOn_00">
                  <span class="icon_1"><img src="../img/right_0.png"/></span>
                  <span>数据中心</span>
              </li>
              <li id="rightmu_2" onclick="rmenuCehOn('2')" class="cheOn_01">
                     <span class="icon_1"><img src="../img/right_1.png"/></span>
                     <span>卷包车间</span>
              </li>
              <li id="rightmu_3" onclick="rmenuCehOn('3')" class="cheOn_01">
                    <span class="icon_1"><img src="../img/right_1.png"/></span>
                    <span>发射机</span>
              </li>
              <li id="rightmu_4" onclick="rmenuCehOn('4')" class="cheOn_01">
                    <span class="icon_1"><img src="../img/right_1.png"/></span>
                    <span>成型机</span>
              </li>
         </ul>
      </div>
      <div id="box_right_page">
          <iframe id="show3DIMG" scrolling="no" frameborder="0" src="../html/juanbao_dataCont.jsp" style="width:100%;height:100%"></iframe>
      </div>
</body>
</html>
