<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta name="author" content="leejean">
<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
a{
	color: white;
}
table{
	font-size:18px;
	font-family: "Microsoft YaHei" ! important;
}
</style>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<script type="text/javascript">

$(function(){
	changecolor(1);
});
	//顶部选中改变颜色，加深！
	function changecolor(code){
		for(var i=1;i<13;i++){
			if(i!=code){
				$("#roler"+i).css("background-color","#A3A3A3");
			}else{
				$("#roler"+code).css("background-color","#424242");
			}
		}
		$("#frame").attr("src","zj17Test.jsp?code="+code);
	}
	
</script>

<div style="background-color: #EDEDED;width:100%;">
	<table style="margin-left: -2px;">
		<tr>
			<td id="roler1" align="center" valign="middle" style="width:90px;height:50px;background-color: #D4D4D4;"><a href="javascript:void(0);" onclick="changecolor(1)">1#卷烟机</a></td>
			<td id="roler2" align="center" valign="middle" style="width:90px;height:50px;background-color: #D4D4D4;"><a href="javascript:void(0);" onclick="changecolor(2)">2#卷烟机</a></td>
			<td id="roler3" align="center" valign="middle" style="width:90px;height:50px;background-color: #D4D4D4;"><a href="javascript:void(0);" onclick="changecolor(3)">3#卷烟机</a></td>
			<td id="roler4" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(4)">4#卷烟机</a></td>
			<td id="roler5" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(5)">5#卷烟机</a></td>
			<td id="roler6" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(6)">6#卷烟机</a></td>
			<td id="roler7" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(7)">7#卷烟机</a></td>
			<td id="roler8" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(8)">8#卷烟机</a></td>
			<td id="roler9" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(9)">9#卷烟机</a></td>
			<td id="roler10" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(10)">10#卷烟机</a></td>
			<td id="roler11" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(11)">11#卷烟机</a></td>
			<td id="roler12" align="center" valign="middle" style="width:90px;height:50px;background-color: #DEDEDE;"><a href="javascript:void(0);" onclick="changecolor(12)">12#卷烟机</a></td>
		</tr>
	</table></div>
<div>
	<iframe id="frame" src="zj17Test.jsp" scrolling="no" style="width:100%;height:91%; border:0;background-color: #D6D6D6;"></iframe>
</div>

