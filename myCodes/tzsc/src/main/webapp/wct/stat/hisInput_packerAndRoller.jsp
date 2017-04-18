<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>包装机历史消耗</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initHighcharts.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<style>
#search_form td{
		font-size:14px;
	}
#hisOut-seach-box{
		border: 1px solid #9a9a9a;
		width: 96%;
		margin-left: 10px;
		height: 45px;
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	#hisOut-page{
		width:97%;
		margin-left:10px;
		margin-top:5px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
		padding-top:470px;
	}
	.btn-default {
color: #FFFFFF;
background-color: #5A5A5A;
border-color: #cccccc;
}
.btn {
  display: inline-block;
  padding:0px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.428571429;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
       -o-user-select: none;
          user-select: none;
}
.progress {margin-bottom: 0px;width: 330px;float: left;height:12px;}
</style>
<script type="text/javascript">
var pageIndex=1;
var allPage=0;
var params={};


$(function(){
	$("input.mh_date").manhuaDate({					       
		Event : "click",//可选				       
		Left : 0,//弹出时间停靠的左边位置
		Top : -16,//弹出时间停靠的顶部边位置
		fuhao : "-",//日期连接符默认为-
		isTime : false,//是否开启时间值默认为false
		beginY : 2010,//年份的开始默认为1949
		endY :2049//年份的结束默认为2049
	});
	
	//设置默认日期
	var today = new Date();
	var month=today.getMonth()+1;
	if(month<10){month=("0"+month);}
	var day=today.getDate();
	if(day<10){day=("0"+day);}
	var defaultlDate=today.getFullYear()+"-"+month+"-"+day;
	//前7天
	var dateOld = new Date(today.getTime() - 6 * 24 * 3600 * 1000);
	var yearOld = dateOld.getFullYear();
	var monthOld = dateOld.getMonth() + 1;
	if(monthOld<10){monthOld=("0"+monthOld);}
	var dayOld = dateOld.getDate();
	if(dayOld<10){dayOld=("0"+dayOld);}
	var defaultlDateOld=yearOld + '-' + monthOld + '-' + dayOld;
	$("#date1").val(defaultlDateOld);	//时间用这个
	$("#date2").val(defaultlDate);	//时间用这个
	
	
	
});

//设置翻页信息
function reSetPageNum(all,pageIndex,allPage){
	$('#count').html(all);
	$('#pageIndex').html(pageIndex);
	var aPage=allPage%5==0?(allPage/5):allPage/5+1
	$('#allPages').html(allPage);
}



//初始化页面数据
	$(function(){
		//实时消耗
		var bandParams=function(pageIndex,params){
			$.post("${pageContext.request.contextPath}/wct/stat/initEquipment4PackerInput2.do?eqpCod=${loginInfo.equipmentCode}&pageIndex="+pageIndex,params,function(json){
				//获取到了物料的id，可以根据物料的id设置系数
				//var matid=data.matId;
				var rows=json.rows;
				var htmls2="";
				var total=json.total;
				allPage=total%5==0?parseInt(total/5):parseInt(total/5)+1;
				$('#allPages').html(allPage);
				$("#pageIndex").html(pageIndex);
				$("#count").html(total);
				//判断设备类型 1-30卷烟机       31-60包装机
				var eqpType=${loginInfo.equipmentCode};
				//卷烟机页面显示
				if(eqpType>=1&&eqpType<=30){
					for(var i=0;i<rows.length;i++){
					var data=rows[i];
					var date=data.date;
					var qty=data.qty;
					var mat = data.mat;
					var jyzid=data.juanyanzhiid;
					var sszid=data.shuisongzhiid;
					var lbid=data.lvbangid;
					//标准单耗
					var jyzBzdh=data.juanyanzhiBzdh;
					var sszBzdh=data.shuisongzhiBzdh;
					var lbBzdh=data.lvbangBzdh;
					//实际消耗
					var jyzsj=data.juanyanzhiIn.toFixed(2);
					var sszsj=data.shuisongzhiIn.toFixed(2);
					var lbsj=data.lvbangIn.toFixed(2);
					
					//当前单耗
					if(qty==0){
						qty=1;
					}
					var jyzsjdh=(jyzsj/qty).toFixed(2);
					var sszsjdh=(sszsj/qty).toFixed(2);
					var lbsjdh=(lbsj/qty).toFixed(2);
					
					//设置进度条
					//xiaohemoPst+id
					//卷烟纸
					var jyzPst=(jyzsjdh/jyzBzdh*0.8)*100;
					//$("#xiaohemoPst"+xhmid).css("width",100+"%");
					//水松纸
					var sszPst=(sszsjdh/sszBzdh*0.8)*100;
					//$("#tiaohemoPst"+thmid).css("width",thmPst+"%");
					//滤棒
					var lbPst=(lbsjdh/lbBzdh*0.8)*100;
					//$("#xiaohezhiPst"+xhzid).css("width",xhzPst+"%");
					
					
					var unit = data.unit;
					
					var html2="	<div class='single_info_data' style='height:82px'>																															"
					+"	<div class='single_info_number'>                                                                                                                                  "
					+"<span>"+date+"</span></br>"
					+"		<span>"+data.eqpName+""+data.team+"</span><br>                                                                                                                                       "
					+"		<span>"+mat+"</span>                                                                                                                                       "
					//+"		<span id='qty"+id+"'>"+qty+"</span><span>"+unit+"</span> 实际产量                                                                                                                              "
					+"	</div>                                                                                                                                                             "
					//卷烟纸
					+"<div class='single_info_01 w220'>                                                                                                                                   "
					+"	<div class='single_info_bzdh w210'>                                                                                                                               "
					+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
					+"		<div class='progress w110'>								                                                                                                      "
					+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
					+"		  </div>                                                                                                                                                      "
					+"		</div>	                                                                                                                                                      "
					+"		<div><span class='sr-only' id='jyzBzdh'>"+jyzBzdh+"</span></div>	                                                                                                              "
					+"	</div>                                                                                                                                                            "
					+"	<div class='single_info_dqdh w210'>							                                                                                                      "
					+"			<div class='progress_title'>实际单耗</div>							                                                                                          "
					+"			<div class='progress w110'>								                                                                                                  "
					+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='jyzPst"+jyzid+"' style='width:"+jyzPst+"%'>			"			
					+"			  </div>                                                                                                                                                  "
					+"			</div>	                                                                                                                                                  "
					+"			<div><span class='sr-only'  id='jyzSjdh'>"+jyzsjdh+"</span></div>                                                                                                            "
					+"	</div>                                                                                                                                                            "
					+"	<div class='single_info_dqhy w210'><a>历史耗用：<span id='jyzIn'>"+jyzsj+"</span>公斤</a></div>                                                                                         "
					+"</div>                                                                                                                                                              "
					//水松纸
					+"<div class='single_info_02 w220'>                                                                                                                                   "
					+"	<div class='single_info_bzdh w210'>                                                                                                                               "
					+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
					+"		<div class='progress w110'>								                                                                                                      "
					+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
					+"		  </div>                                                                                                                                                      "
					+"		</div>	                                                                                                                                                      "
					+"		<div><span class='sr-only' id='sszBzdh'>"+sszBzdh+"</span></div>	                                                                                                              "
					+"	</div>                                                                                                                                                            "
					+"	<div class='single_info_dqdh w210'>							                                                                                                      "
					+"			<div class='progress_title'>历史单耗</div>							                                                                                          "
					+"			<div class='progress w110'>								                                                                                                  "
					+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='sszPst"+sszid+"' style='width:"+sszPst+"%'>			"			
					+"			  </div>                                                                                                                                                  "
					+"			</div>	                                                                                                                                                  "
					+"			<div><span class='sr-only' id='sszSjdh'>"+sszsjdh+"</span></div>                                                                                                            "
					+"	</div>                                                                                                                                                            "
					+"	<div class='single_info_dqhy w210'><a>历史耗用：<span id='sszIn'>"+sszsj+"</span>公斤</a></div>                                                                                         "
					+"</div>                                                                                                                                                              "
					//滤棒
					+"<div class='single_info_03 w220'>                                                                                                                                   "
					+"	<div class='single_info_bzdh w210'>                                                                                                                               "
					+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
					+"		<div class='progress w110'>								                                                                                                      "
					+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
					+"		  </div>                                                                                                                                                      "
					+"		</div>	                                                                                                                                                      "
					+"		<div><span class='sr-only' id='lbBzdh'>"+lbBzdh+"</span></div>	                                                                                                              "
					+"	</div>                                                                                                                                                            "
					+"	<div class='single_info_dqdh w210'>							                                                                                                      "
					+"			<div class='progress_title'>历史单耗</div>							                                                                                          "
					+"			<div class='progress w110'>								                                                                                                  "
					+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='lbPst"+lbid+"' style='width:" +lbPst+"%'>			"			
					+"			  </div>                                                                                                                                                  "
					+"			</div>	                                                                                                                                                  "
					+"			<div><span class='sr-only' id='lbSjdh'>"+lbsjdh+" </span></div>                                                                                                            "
					+"	</div>                                                                                                                                                            "
					+"	<div class='single_info_dqhy w210'><a>历史耗用：<span  id='lbIn'>"+lbsj+"</span>支</a></div>                                                                                         "
					+"</div>                                                                                                                                                              "
				    +"</div>	                                                                                                                                                          ";
						htmls2=htmls2+html2;   
					}
				}
				//包装机页面显示

				if(eqpType>=31&&eqpType<=60){
					for(var i=0;i<rows.length;i++){
						var data=rows[i];
						var date=data.date;
						var qty=data.qty;
						var mat = data.mat;
						var xhmid=data.xiaohemoid;
						var thmid=data.tiaohemoid;
						var xhzid=data.xiaohezhiid;
						var thzid=data.tiaohezhiid;
						var nczid=data.neichenzhiid;
						//标准单耗
						var xiaohemoBzdh=data.xiaohemoBzdh;
						var tiaohemoBzdh=data.tiaohemoBzdh;
						var xiaohezhiBzdh=data.xiaohezhiBzdh;
						var tiaohezhiBzdh=data.tiaohezhiBzdh;
						var neichenzhiBzdh=data.neichenzhiBzdh;
						//实际消耗
						var xiaohemosj=data.xiaohemoIn.toFixed(2);
						var tiaohemosj=data.tiaohemoIn.toFixed(2);
						var xiaohezhisj=data.xiaohezhiIn.toFixed(2);
						var tiaohezhisj=data.tiaohezhiIn.toFixed(2);
						var neichenzhisj=data.neichenzhiIn.toFixed(2);

						
						//当前单耗
						if(qty==0){
							qty=1;
						}
						var xhmsj=(xiaohemosj/qty).toFixed(2);
						var thmsj=(tiaohemosj/qty).toFixed(2);
						var xhzsj=(xiaohezhisj/qty).toFixed(2);
						var thzsj=(tiaohezhisj/qty).toFixed(2);
						var nczsj=(neichenzhisj/qty).toFixed(2);
						
						
						
						
						//设置进度条
						//xiaohemoPst+id
						//小盒膜
						var xhmPst=(xhmsj/xiaohemoBzdh*0.8)*100;
						//$("#xiaohemoPst"+xhmid).css("width",100+"%");
						//条盒膜
						var thmPst=(thmsj/tiaohemoBzdh*0.8)*100;
						//$("#tiaohemoPst"+thmid).css("width",thmPst+"%");
						//小盒纸
						var xhzPst=(xhzsj/xiaohezhiBzdh*0.8)*100;
						//$("#xiaohezhiPst"+xhzid).css("width",xhzPst+"%");
						//条盒纸
						var thzPst=(thzsj/tiaohezhiBzdh*0.8)*100;
						//$("#tiaohezhiPst"+thzid).css("width",thzPst+"%");
						//内衬纸
						var nczPst=(thzsj/neichenzhiBzdh*0.8)*100;
						//$("#neichenzhiPst"+nczid).css("width",nczPst+"%");
						
						var unit = data.unit;
						
						var html2="	<div class='single_info_data' style='height:82px'>																															"
						+"	<div class='single_info_number'>                                                                                                                                  "
						+"<span>"+date+"</span></br>"
						+"		<span>"+data.eqpName+""+data.team+"</span><br>                                                                                                                                       "
						+"		<span>"+mat+"</span>                                                                                                                                       "
						//+"		<span id='qty"+id+"'>"+qty+"</span><span>"+unit+"</span> 实际产量                                                                                                                              "
						+"	</div>                                                                                                                                                             "
						//小盒膜
						+"<div class='single_info_01 w220'>                                                                                                                                   "
						+"	<div class='single_info_bzdh w210'>                                                                                                                               "
						+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
						+"		<div class='progress w110'>								                                                                                                      "
						+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
						+"		  </div>                                                                                                                                                      "
						+"		</div>	                                                                                                                                                      "
						+"		<div><span class='sr-only' id='xiaohemoBzdh'>"+xiaohemoBzdh+"</span></div>	                                                                                                              "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqdh w210'>							                                                                                                      "
						+"			<div class='progress_title'>实际单耗</div>							                                                                                          "
						+"			<div class='progress w110'>								                                                                                                  "
						+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='xiaohemoPst"+xhmid+"' style='width:"+xhmPst+"%'>			"			
						+"			  </div>                                                                                                                                                  "
						+"			</div>	                                                                                                                                                  "
						+"			<div><span class='sr-only'  id='xiaohemoSjdh'>"+xhmsj+"</span></div>                                                                                                            "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqhy w210'><a>历史耗用：<span id='xiaohemoIn'>"+xiaohemosj+"</span>公斤</a></div>                                                                                         "
						+"</div>                                                                                                                                                              "
						//条盒膜
						+"<div class='single_info_02 w220'>                                                                                                                                   "
						+"	<div class='single_info_bzdh w210'>                                                                                                                               "
						+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
						+"		<div class='progress w110'>								                                                                                                      "
						+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
						+"		  </div>                                                                                                                                                      "
						+"		</div>	                                                                                                                                                      "
						+"		<div><span class='sr-only' id='tiaohemoBzdh'>"+tiaohemoBzdh+"</span></div>	                                                                                                              "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqdh w210'>							                                                                                                      "
						+"			<div class='progress_title'>历史单耗</div>							                                                                                          "
						+"			<div class='progress w110'>								                                                                                                  "
						+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='tiaohemoPst"+thmid+"' style='width:"+thmPst+"%'>			"			
						+"			  </div>                                                                                                                                                  "
						+"			</div>	                                                                                                                                                  "
						+"			<div><span class='sr-only' id='tiaohemoSjdh'>"+thmsj+"</span></div>                                                                                                            "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqhy w210'><a>历史耗用：<span id='tiaohemoIn'>"+tiaohemosj+"</span>公斤</a></div>                                                                                         "
						+"</div>                                                                                                                                                              "
						//小盒纸
						+"<div class='single_info_03 w220'>                                                                                                                                   "
						+"	<div class='single_info_bzdh w210'>                                                                                                                               "
						+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
						+"		<div class='progress w110'>								                                                                                                      "
						+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
						+"		  </div>                                                                                                                                                      "
						+"		</div>	                                                                                                                                                      "
						+"		<div><span class='sr-only' id='xiaohezhiBzdh'>"+xiaohezhiBzdh+"</span></div>	                                                                                                              "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqdh w210'>							                                                                                                      "
						+"			<div class='progress_title'>历史单耗</div>							                                                                                          "
						+"			<div class='progress w110'>								                                                                                                  "
						+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='xiaohezhiPst"+xhzid+"' style='width:" +xhzPst+"%'>			"			
						+"			  </div>                                                                                                                                                  "
						+"			</div>	                                                                                                                                                  "
						+"			<div><span class='sr-only' id='xiaohezhiSjdhd'>"+xhzsj+" </span></div>                                                                                                            "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqhy w210'><a>历史耗用：<span  id='xiaohezhiIn'>"+xiaohezhisj+"</span>张</a></div>                                                                                         "
						+"</div>                                                                                                                                                              "
						//条盒纸
						+"<div class='single_info_04 w220'>                                                                                                                                   "
						+"	<div class='single_info_bzdh w210'>                                                                                                                               "
						+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
						+"		<div class='progress w110'>								                                                                                                      "
						+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
						+"		  </div>                                                                                                                                                      "
						+"		</div>	                                                                                                                                                      "
						+"		<div><span class='sr-only' id='tiaohezhiBzdh'>"+tiaohezhiBzdh+"</span></div>	                                                                                                              "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqdh w210'>							                                                                                                      "
						+"			<div class='progress_title'>历史单耗</div>							                                                                                          "
						+"			<div class='progress w110'>								                                                                                                  "
						+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='tiaohezhiPst"+thzid+"' style='width:"+thzPst+"%'>			"			
						+"			  </div>                                                                                                                                                  "
						+"			</div>	                                                                                                                                                  "
						+"			<div><span class='sr-only'  id='tiaohezhiSjdh'>"+thzsj+"</span></div>                                                                                                            "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqhy w210'><a>历史耗用：<span id='tiaohezhiIn'>"+tiaohezhisj+"</span>张</a></div>                                                                                         "
						+"</div>                                                                                                                                                              "
						//内衬纸
						+"<div class='single_info_05 w220'>                                                                                                                                   "
						+"	<div class='single_info_bzdh w210'>                                                                                                                               "
						+"		<div class='progress_title'>标准单耗</div>							                                                                                              "
						+"		<div class='progress w110'>								                                                                                                      "
						+"		  <div class='progress-bar progress-normal gc_g2' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' style='width: 80%'>				"		
						+"		  </div>                                                                                                                                                      "
						+"		</div>	                                                                                                                                                      "
						+"		<div><span class='sr-only' id='neichenzhiBzdh'>"+neichenzhiBzdh+"</span></div>	                                                                                                              "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqdh w210'>							                                                                                                      "
						+"			<div class='progress_title'>历史单耗</div>							                                                                                          "
						+"			<div class='progress w110'>								                                                                                                  "
						+"			  <div class='progress-bar progress-normal gc_y_t' role='progressbar' aria-valuenow='40' aria-valuemin='0' aria-valuemax='100' id='neichenzhiPst"+nczid+"' style='width:"+nczPst+"%'>			"			
						+"			  </div>                                                                                                                                                  "
						+"			</div>	                                                                                                                                                  "
						+"			<div><span class='sr-only' id='neichenzhiSjdh'>"+nczsj+"</span></div>                                                                                                            "
						+"	</div>                                                                                                                                                            "
						+"	<div class='single_info_dqhy w210'><a>历史耗用：<span id='neichenzhiIn'>"+neichenzhisj+"</span>公斤</a></div>                                                                                         "
						+"</div>                                                                                                                                                              "
					    +"</div>	                                                                                                                                                          ";
							htmls2=htmls2+html2;                                                                                                                                                 
						}
					
					
					
				}
				/*
				*/
				$("#data-div2").html("");                                                                                                                                             
				$("#data-div2").html(htmls2);
			},"JSON");
		};	
		$("#hisOut-search").click(function(){
			params=getJsonData($('#search_form'));
			pageIndex=1;
			bandParams(pageIndex,params);
		});
		$("#hisOut-reset").click(function(){
			$('#search_form input[type!=button]').val(null);
		});

		$("#up").click(function(){
			if(pageIndex<2){
				return;
			}
			pageIndex=pageIndex-1;
			bandParams(pageIndex,params);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPage){
				return;
			}
			pageIndex=pageIndex+1;
			bandParams(pageIndex,params);
		});
		

		//初始化数据
		params=getJsonData($('#search_form'));
		bandParams(pageIndex,params);
		$("#botton_show2").click(function(){$("#scroll_box02").animate({ left: "-568px"}, 1000 );});
		$("#botton_hide2").click(function(){$("#scroll_box02").animate({ left: "0px"}, 1000 );});
		reSetPageNum(allPage,pageIndex,allPage);
});
</script>

<div id="wkd-qua-title">历史消耗</div>
<div id="hisOut-seach-box" >
	<form id="search_form" style="margin-top: 0px;">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>		
			<th style="width:76px;text-align:right;">日期&nbsp;&nbsp;</th>
			<td style="width:140px;">
			<input type="text" id='date1' name="date1" class="mh_date" readonly="readonly" style="width:110px;height:20px;"/>
			</td>
			<th >-</th>
			<td style="width:140px;">
			<input type="text" id='date2' name="date2"  class="mh_date" readonly="readonly" style="width:110px;height:20px;"/>
			</td>
			<th style="width:76px;text-align:center;">班次</th>
			<td>
				<select name="shiftId" style="height: 20px;">
					<option value="">全部</option>
					<option value="1">早班</option>
					<option value="2">中班</option>
					<option value="3">晚班</option>
				</select>
			</td>
			
			<th style="width:76px;text-align:center;">班组</th>
			<td style="width: 120px;">
				<select name="teamId" style="height: 20px;">
					<option value="">全部</option>
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>
			</td>
			<td style="width:76px;text-align:center;"><input type="button" id="hisOut-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			<td  style="width:86px;text-align:center;"><button type="reset" id="hisOut-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default">重置</button></td>
			</tr>
		</table>
	</form>
</div>
		<div class="single_info_xiaohao" style="height:500px;">			
			<div class="single_info" id="scroll_box02">
				<div class="single_info_box">
					
					 <c:if test="${loginInfo.equipmentCode>30&&loginInfo.equipmentCode<61}">
						<div id="botton_show2" style="float:left;width:100px;height:30px">显示更多</div>
						<div  class="single_info_title">小盒烟膜</div>
						<div class="single_info_title">条盒烟膜</div>
						<div class="single_info_title">小盒纸</div>
						<div class="single_info_title">条盒纸</div>
						<div class="single_info_title">内衬纸</div>
						<div id="botton_hide2">返回</div>
					</c:if>	
					<c:if test="${loginInfo.equipmentCode>0&&loginInfo.equipmentCode<31}">
						<div  class="single_info_title" style="margin-left: 110px">卷烟纸</div>
						<div class="single_info_title">水松纸</div>
						<div class="single_info_title">滤棒</div>
					</c:if>
					
				</div>
				<div class="single_info_xiaohao_v" style="height:420px" id="scroll_box2">
				<div id="data-div2" class="scroll_box" style="height:420px"></div>
				</div>
			</div>
	<div id="hisOut-page">
		<div style="float:right">
			  共<span id="count">0</span>条数据
			<input id="up" type="button"  value="上一页" class="btn btn-default"/>
			<span id="pageIndex">0</span>/<span id="allPages">0</span>
			<input id="down" type="button"  value="下一页" class="btn btn-default"/>
		</div>
	</div>
</div>
