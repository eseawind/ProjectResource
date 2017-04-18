<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>commonRequestTime成型机实时监控</title>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>


<%-- <link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/bootCSS/css/bootstrap.css"></link> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/wct/isp/css/fsj/css/style.css"   type="text/css" />

<style type="text/css">
	a{text-decoration: none;}
	b{font-size:14px;color:#494848;}
</style>

</head>
<body>
<div id="body-center">
<div class="info_machine">	
	<div class="info_machine_cen fl">
		<ul class="nav nav-tabs" id="tabs">
		  <li id="tab1-tip"><a href="javascript:void(0)" name="#tab1" >${loginInfo.equipmentName}</a></li>
		  <%-- <li id="tab2-tip"><a href="javascript:void(0)" name="#tab2">${loginGroup.eqps['sender2'].des}</a></li>
		  <li id="tab3-tip"><a href="javascript:void(0)" name="#tab3">${loginGroup.eqps['sender3'].des}</a></li> --%>
		</ul>		
	</div>
	<!-- <div class="info_machine_right fr">
		<div class="fl machine_info">
			<span class="fault" id="twinkling">低速运行</span>
			<a>
				<span>
					<marquee scrollDelay="120" style="position:absolute;top:85px;">
								报警信息滚动
					</marquee>
				</span>
			</a>			
		</div>	
	</div> -->
	


</div>
	<div id="content">
	<div id="tab1">
		<div class="info_area">
			<ul style="width: 100%;height:290px;padding: 10px;">
				<li style="width:935px;height:50px;">
					<div class="launch_tube fl number1"><a class="mr60"><strong>1#发射管:</strong></a><a>发射数量：<span id="send-num-1-1">0</span>支</a><a>速度：<span id="send-speed-1-1">0</span>支/分</a></div>
					<div class="launch_tube fr number1"><a class="mr60"><strong>2#发射管:</strong></a><a>发射数量：<span id="send-num-1-2">0</span>支</a><a>速度：<span id="send-speed-1-2">0</span>支/分</a></div>					
				</li>
				<li style="width:935px;height:50px;">
					<div class="launch_tube fl number1"><a class="mr60"><strong>3#发射管:</strong></a><a>发射数量：<span id="send-num-1-3">0</span>支</a><a>速度：<span id="send-speed-1-3">0</span>支/分</a></div>
					<div class="launch_tube fr number1"><a class="mr60"><strong>4#发射管:</strong></a><a>发射数量：<span id="send-num-1-4">0</span>支</a><a>速度：<span id="send-speed-1-4">0</span>支/分</a></div>					
				</li>
				<li style="width:935px;height:50px;">
					<div class="launch_tube fl number1"><a class="mr60"><strong>5#发射管:</strong></a><a>发射数量：<span id="send-num-1-5">0</span>支</a><a>速度：<span id="send-speed-1-5">0</span>支/分</a></div>
					<div class="launch_tube fr number1"><a class="mr60"><strong>6#发射管:</strong></a><a>发射数量：<span id="send-num-1-6">0</span>支</a><a>速度：<span id="send-speed-1-6">0</span>支/分</a></div>					
				</li>
				<li style="width:935px;height:50px;">
					<div class="launch_tube fl number1"><a class="mr60"><strong>7#发射管:</strong></a><a>发射数量：<span id="send-num-1-7">0</span>支</a><a>速度：<span id="send-speed-1-7">0</span>支/分</a></div>
					<div class="launch_tube fr number1"><a class="mr60"><strong>8#发射管:</strong></a><a>发射数量：<span id="send-num-1-8">0</span>支</a><a>速度：<span id="send-speed-1-8">0</span>支/分</a></div>					
				</li>
				<li style="width:935px;height:50px;">
					<div class="launch_tube fl number1"><a class="mr60"><strong>9#发射管:</strong></a><a>发射数量：<span id="send-num-1-9">0</span>支</a><a>速度：<span id="send-speed-1-9">0</span>支/分</a></div>
					<div class="launch_tube fr number1"><a class="mr50"><strong>10#发射管:</strong></a><a>发射数量：<span id="send-num-1-10">0</span>支</a><a>速度：<span id="send-speed-1-10">0</span>支/分</a></div>					
				</li>
			</ul>
		</div>
		<div class="info_count">
			<div class="info_count_left fl">
				<div style="width: 960px;height:270px;">
					<div class="fsjbg fl">
						<div class="fsjbg_info">
							<a>设备型号：<span>${loginInfo.equipmentType}</span></a><a>额定速度<span>1500支/分</span></a>
						</div>
					</div>			
					<div class="fsjinfo fl">
						<div class="fsjinfo_box">
						<h1>概要信息</h1>
							<div class="fl">
							<ul>
							<li><a>额定车速：<span id="send-stime-1">1500</span>支/分</a></li>
							<li><a>设备型号：<span id="send-order-1">${loginInfo.equipmentType}</span><b class="send-uom-1"></b></a></li>
							</ul>	
							</div>
							<div class="fr">
							<ul>
							<li><a><span id="send-etime-1"></span></a></li>
							<li><a>发射数量：<span id="send-out-1">0</span><b class="send-uom-1">万支</b></a></li>	
							</ul>
							</div>		
						</div>
						<div class="info_count_left_status  depictCon">	
							<h1>系统通知</h1>						
							<div id="info_div">
							
							</div>					
						</div>
					</div>

				</div>
			</div>
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

  </script>
  <script type="text/javascript">
  
    //登录验证
	var loginname = "${loginInfo.equipmentCode}";
	if(loginname==null || loginname=="" || loginname.length==0){
		window.location="${pageContext.request.contextPath}/wct/sys/login.jsp";
	} 
	var code="${loginInfo.equipmentCode}";
	//定时请求发射机数据
	var commonRequestTime=setInterval('loadData()',1000*3);
	function loadData(){
		$.post("${pageContext.request.contextPath}/isp/getFormatted.do",{"code":code},function(json){
			var data=json.allData;
			var online=json.online;
			if(online){
				var total=0;
				for(var i=0;i<data.length;i++){
					
					var val=data[i].val;
					var id=data[i].id;
					console.info("i="+i+"   val="+val);
					if(id>0&&id<11){
						var num=val;
						total+=num;
						$("#send-speed-1-"+id).html(num);
					}
					if(id>=21&&id<=30){
						$("#send-num-1-"+(id-20)).html(val);
					}
					if(id==31){
						total=val/10000;
					}
			 	}
				console.info(total);
				$("#send-out-1").html(parseFloat(total).toFixed(3));
			}else{
				//online=false;
			}
		},"JSON");
	}
	
	
	/* 告警 */
	jinggao();
	function jinggao(){
		/* 系统通知 */
		$("#info_div").html("");
		$.get('${pageContext.request.contextPath}/msg/conwarn/getMsgInfo.do',function(json){
				if(json!=null){
					var inf="<ul>";
					for(var i=0;i<json.length;i++){
						inf+="<li class='info_lis' >▪<font style='color:red;'>"+json[i].title+":</font>&nbsp;<font style='font-size:12px;'>"+json[i].content+"</font></li>";
					}
					inf+="</ul>";
					$("#info_div").append(inf);
				}
		},"JSON");
	}
</script>
</body>
</html>