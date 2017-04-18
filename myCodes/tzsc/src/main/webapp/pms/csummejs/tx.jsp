<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
</head>

<body>
    <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
    <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
    <div id="main" style="height:362px;border:1px solid #ccc;padding:10px;"></div>
   	<!-- <div id="mainMap" style="height:500px;border:1px solid #ccc;padding:10px;"></div> -->
    
    <!--Step:2 Import echarts.js-->
    <!--Step:2 引入echarts.js-->
    <script src="js/echarts.js"></script>
    <jsp:include page="../../initlib/initAll.jsp"></jsp:include>
    <script type="text/javascript">
    var qtys;
    var lbs;
    var baoqtys;
    var baolbs;
    function tuxing(){
	    // Step:3 conifg ECharts's path, link to echarts.js from current page.
	    // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
	    require.config({
	        paths: {
	            echarts: './js'
	        }
	    });
	    
	    // Step:4 require echarts and use it in the callback.
	    // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
	    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line',
	            'echarts/chart/map'
	        ],
	        function (ec) {
	            //--- 折柱 ---
	            var myChart = ec.init(document.getElementById('main'));
	            myChart.setOption({
	                tooltip : {
	                    trigger: 'axis'
	                },
	                legend: {
	                    data:['卷烟机','包装机']
	                },
	                toolbox: {
	                    show : true,
	                    feature : {
	                        mark : {show: true},
	                       // dataView : {show: true, readOnly: false},
	                        magicType : {show: true, type: ['line', 'bar']},
	                        restore : {show: true},
	                        saveAsImage : {show: true}
	                    }
	                },
	                calculable : true,
	                xAxis : [
	                    {
	                        type : 'category',
	                        data :lbs /* ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'] */
	                    }
	                ],
	                yAxis : [
	                    {
	                        type : 'value',
	                        splitArea : {show : true}
	                    }
	                ],
	                series : [
	                    {
	                        name:'卷烟机',
	                        type:'bar',
	                        data:qtys/* [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3] */
	                    } ,
	                    {
	                        name:'包装机',
	                        type:'bar',
	                        data:baoqtys/* [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3] */
	                    } 
	                ]
	            });
	         
	        }
	    );
    }
    function readqty(){
    	$.post("${pageContext.request.contextPath}/pms/indexInfo/getIndexQtyAll.do",{'begin':1,'end':12},function(json){	
    		qtys=json.values1;
    		lbs=json.lables;
    		
    	},"JSON");
		$.post("${pageContext.request.contextPath}/pms/indexInfo/getIndexQtyAll.do",{'begin':31,'end':42},function(json){	
			baoqtys=json.values1;
    		baolbs=json.lables;
    		
    	},"JSON");
		tuxing();
    }
    function shuaxing(){
    	window.location.reload();
    }
    $(function(){
    	setTimeout('readqty()',1000*3);//延后 3秒 读取数据
    	setInterval('shuaxing()',1000*60*3);
    	
    });
   
    </script>
</body>
</html>
