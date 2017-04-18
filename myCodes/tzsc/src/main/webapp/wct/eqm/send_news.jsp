<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="author" content="leejean">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">

<script type='text/javascript' src='/tzsc/dwr/engine.js'></script>
<script type='text/javascript' src='/tzsc/dwr/interface/usertest.js'></script>
<script type='text/javascript' src='/tzsc/dwr/util.js'></script>




<style type="text/css">
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#title{
		font-size: 20px;
		font-weight: bold;
		text-align: center;
		padding-top: 4px;
		background: #f5f5f5;
		color: #3C3C3C;
		border-radius: 0px 4px 0px 0px;
		line-height: 35px;
		height: 40px;
	}
	.advLayer{
		background-color: #fff;
	    position: fixed;
	    right: 0;
	    bottom: 0;
	    z-index: 10;
	    height: 230px;
	    width: 385px;
	    border: 1px solid #dcdcdc;
	    font-family: 微软雅黑,Arial;
    }
    .advLayerHead{
        background-color: #f5f5f5;
	    text-align: center;
	    height: 35px;
	    line-height: 35px;
	    border-bottom: 1px solid #dcdcdc;
    }
    .advLclose{
    	font-size: 14px;
	    cursor: pointer;
	    padding: 5px;
	    margin-left: 190px;
    
    }
    #prodMailPlan-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
    

	
	
	
	.t-header{
		text-align:center;
	}
	#prodMailPlan-tab-thead tr td,#prodMailPlan-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	
	
</style>

</head>
<script type="text/javascript">
  $(function(){
	  //隐藏弹窗
	  $("#msgDiv").hide();
	  //建立通道
	  dwr.engine.setActiveReverseAjax(true); 
  });
  
     //被调用的方法
	function text2(message){ 
	   var data= JSON.parse(message);
   	   //拼接table
   	   var html="<tr><td>"+data[0].eqpName+"</td>"
	    +"<td>"+data[0].shiftName+"</td>"
	    +"<td>"+data[0].teamName+"</td>"
	    +"<td>"+data[0].createUserName+"</td>"
	    +"<td>"+data[0].userName+"</td>"
	    +"<td>"+data[0].typeName+"</td>"
	    +"<td>"+data[0].createUserTime+"</td></tr>";
	   $('#prodMailPlan-tab-tbody').prepend(html);
	   //写入弹窗信息
	   $("#msg").html("<font style='color:red'>"+data[0].eqpName+"呼叫</font>:"+data[0].typeName+data[0].userName);
	   //调用弹窗
	   $("#msgDiv").show(1000);
	   var B = setInterval(function(){
		   //调用弹窗
		   $("#msgDiv").hide(1000);
		   clearInterval(B);
	   },6000);
	  
	}


var iWidth=500; //弹出窗口的宽度;
var iHeight=400; //弹出窗口的高度;
var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	$(function(){
		$.post("${pageContext.request.contextPath}/pms/fixUser/queryAskInfo.do",function(data){
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					var html="<tr><td>"+data[i].eqpName+"</td>"
					         +"<td>"+data[i].shiftName+"</td>"
					         +"<td>"+data[i].teamName+"</td>"
					         +"<td>"+data[i].createUserName+"</td>"
					         +"<td>"+data[i].userName+"</td>"
					         +"<td>"+data[i].typeName+"</td>"
					         +"<td>"+data[i].createUserTime+"</td></tr>";
					$('#prodMailPlan-tab-tbody').prepend(html);
				}
			}
			
		},"Json");			 
		
	});
	
	
</script>
<body >

<div id="title">维修呼叫</div>
	<!--信息数据 div  #f5f5f5 #5a5a5a color: #fff;#b4b4b4-->
			<table border="1" style="width:100%" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;"  cellspacing="0" cellpadding="0">
				<thead id="prodMailPlan-tab-thead" style="background: #f5f5f5;">
					<tr>
						<td class="t-header" style="width:10%" >机台</td>
						<td class="t-header" style="width:10%">班次</td>
						<td class="t-header" style="width:10%">班组</td>
						<td class="t-header" style="width:10%">机台操作人</td>
						<td class="t-header" style="width:10%">维修工</td>
						<td class="t-header" style="width:20%">维修工类别</td>
						<td class="t-header" style="width:20%" >呼叫时间</td>
					</tr>
				</thead>
				<tbody id="prodMailPlan-tab-tbody">
				</tbody>
			</table>
			
			
			
		<div id="msgDiv" class="advLayer" >
			<div class="advLayerHead" style="text-align: left;padding-left:20px;margin-bottom:20px;">
				通知：
			</div>
			
			<div id="msg" style="border-bottom: 1px  dotted #dcdcdc;text-align:left; border-bottom-style: dotted;margin-left:20px;">
			
			</div>
		
		</div>
</body>
</html>