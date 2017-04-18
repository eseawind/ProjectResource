<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>工厂日历卷包</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <link href="${pageContext.request.contextPath}/jslib/edojs/edo/res/css/edo-all.css" rel="stylesheet"type="text/css" />
 <link href="${pageContext.request.contextPath}/jslib/edojs/main.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/jslib/edojs/edo/edo.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jslib/edojs/edoloading/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jslib/edojs/edoloading/jquery.blockUI.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jslib/edojs/edoloading/edo-loading.js" type="text/javascript"></script>
<!-- wctAlert -->
<script src="${pageContext.request.contextPath}/jslib/myAlert2.1/jquery.alerts.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/jslib/myAlert2.1/jquery.alerts.css" type="text/css"  media="screen" rel="stylesheet">

<style type="text/css">
	.lb-calendar-green{color:white;}
	.lb-calendar-red{color:#fff;}
	.lb-calendar-blue{color:#fff;}	
	.daybox{width:60px;height:40px;float:right;position:absolute}
	#calendarFactory{margin:0 auto;width:100%;min-width:800px}
#calendarTitle {
	background: none repeat scroll 0 0 #B4B4B4;
	border-bottom: 2px solid #838383;
	border-radius: 0 4px 0 0;
	color: #3C3C3C;
	font-size: 20px;
	font-weight: bold;
	height: 40px!important;
	line-height: 35px;
	padding-top: 4px;
	text-align: center;
}
#calendarWeek{
	background: none repeat scroll 0 0 #DEDCDA;
	border: 1px solid #9A9A9A;
	border-radius: 4px 4px 0 0;
	font-size: 14px;
	font-weight: bold;
	height: 30px!important;
	margin: 15px auto 0;
	padding-left: 15px;
	padding-top: 15px;
	width: 804px!important;
	left: 8px!important;
}
#calendarWeek div{
	width: 45px!important;
	height: 22px!important;
	top: 12.5px!important;
	position: absolute!important;
	font-weight: bold!important;
	font-style: normal!important;
	font-variant: normal!important;
	font-size: 14px!important;
	line-height: 20px!important;
	font-family: "Microsoft YaHei",Arial!important;
	overflow: hidden!important;
}
.e-label {
	font-size: 12px;
	font-family: "Microsoft YaHei",Arial!important;
	white-space: nowrap;
	line-height: 22px;

}
#calendar_box{
	top: 66px!important;
	left: 8px!important;
	background: none repeat scroll 0 0 #DEDCDA;
	border: 1px solid #9A9A9A;
	border-radius: 0 0 4px 4px;
	height: 492px!important;
	margin: 5px auto 0;
	overflow: auto;
	width: 820px;
}
.lb-calendar-prevCalendar {
	background-image: url(../img/wct_prevpage.png);
	background-repeat: no-repeat;
	background-position: 50% 50%;
	top: 5.5px!important;
}
.lb-calendar-nextCalendar {
	background-image: url(../img/wct_nextpage.png);
	background-repeat: no-repeat;
	background-position: 50% 50%;
	top: 5.5px!important;
}
#calendarCenter{top: 9.5px!important;}
#calendarMain{top:46px!important;background: #DDDDDD;height: 580px!important;}
.lb-calendar-red {background-image: url(../img/wct_calendarRed.jpg);}
.lb-calendar-grey {background-image:url(../img/wct_calendarGrey.jpg);}
.lb-calendar-green {background-image:url(../img/wct_calendarGreen.jpg);}
.lb-calendar-blue {background-image:url(../img/wct_calendarBlue.jpg);}
</style>

<script type="text/javascript">
	/**
	 *	工厂日历
	 */
	Edo.util.Dom.on(window, 'domload', function(e) {
		Edo.build({
			type : 'ct',
			render : document.body,
			id : 'calendarFactory',
			width : '839',
			height : '623',
			//top:'20',
			layout : 'vertical',
			border : [ 0, 1, 1, 1 ],
			//horizontalScrollPolicy : 'on',//最下边的滚动条
			verticalGap : 0,
			children : [ /*{
				id : 'calendarTop',
				type : 'ct',
				width : '100%',
				height : '42',
				boder : [ 1, 1, 1, 1 ],
				cls : 'lb-calendar-top',
				layout : 'horizontal',
				verticalAlign : 'middle',
				children : [ {
					id : 'calendarTopLeft',
					type : 'ct',
					width : '40%',
					layout : 'horizontal',
					verticalAlign : 'middle',
					horizontalGap : 0,
					children : [ {
						type : 'ct',//box
						width : '42',
						border : [ 0, 0, 0, 0 ],
						bodyStyle : 'margin-top:-10px',
						height : '42',
						bodyCls : 'lb-calendar-left'
					}, {
						type : 'ct',//box
						bodyCls : 'lb-calendar-header',
						border : [ 0, 0, 0, 0 ],
						width : '60%',
						bodyStyle : 'padding-left:-10',
						height : '35',
						children : [ {
							type : 'label',
							text : '&nbsp;&nbsp;&nbsp;工厂日历'
						} ]
					} ]
				//calendarTopLeft
				} ]
			//calendarTop
			}, */{
				type : 'ct',
				width : '100%',
				height : '60',//设置最顶上的图片高度
				layout : 'horizontal',
				verticalAlign : 'middle',
				cls : 'e-toolbar',
				id : 'calendarTitle',
				children : [ {
					type : 'ct',//box
					width : '20%',//设置上一个月的图片往左（百分比小）往右（百分比大）定位
					height:'31',
					horizontalAlign : 'right',
					layout : 'horizontal',
					border : [ 0, 0, 0, 0 ],
					cls : 'lb-calendar-prevCalendar',
					style : 'cursor:pointer',
					verticalAlign : 'middle',
					children : [ {
						type : 'label',
						text : '<font color=green></font>',
						id : 'prevMonth'
					} ],
					onclick : function(e) {
						var str = currDate.getValue();
						var year = Number(str.substring(0, 4));
						var month = Number(str.substring(5, 7));
						if ((month - 1) < 1) {
							year -= 1;
							month = 12;
						} else {
							month -= 1;
							if (month < 10) {
								month = "0" + month;
							}
						}
						var dateStr = year + "-" + month + "-" + "01";
						var date = new Date(dateStr.replace(/-/g, "/"));

						countDate(date, dateStr, 3);//日历列表
					}
				}, {//顶部上显示的日期年月
					id : 'calendarCenter',
					type : 'ct',//box
					width : '60%',
					height : '25',
					horizontalAlign : 'center', 
					verticalAlign : 'middle',
					layout : 'horizontal',
					border : [ 0, 0, 0, 0 ],
					//cls : 'lb-calendar-center',
					children : [ {
						type : 'label',
						id : 'currDate',
						style : 'font-size:20;font-weight:bold;color:#3C3C3C'
					}]
				}, {
					type : 'ct',//box
					width : '20%',
					height:'31',
					horizontalAlign : 'left',
					layout : 'horizontal',
					style : 'cursor:pointer',
					border : [ 0, 0, 0, 0 ],
					cls : 'lb-calendar-nextCalendar',
					verticalAlign : 'middle',
					children : [ {
						type : 'label',
						text : '<font color=green><font>',
						id : 'nextMonth'
					} ],
					onclick : function(e) {
						var str = currDate.getValue();
						var year = Number(str.substring(0, 4));
						var month = Number(str.substring(5, 7));
						if ((month + 1) > 12) {
							year += 1;
							month = "01";
						} else {
							month += 1;
							if (month < 10) {
								month = "0" + month;
							}
						}
						var dateStr = year + "-" + month + "-" + "01";
						var date = new Date(dateStr.replace(/-/g, "/"));
						
						countDate(date, dateStr, 3);//日历列表

					}
				}/* , {//这个是 测试添加 排班
					type : 'ct',//box
					width : '10%',
					height:'31',
					horizontalAlign : 'left',
					layout : 'horizontal',
					style : 'cursor:pointer',
					border : [ 0, 0, 0, 0 ],
					cls : 'e-toolbar',
					verticalAlign : 'middle',
					children : [ {
						type : 'label',
						text : '添加排班',
						id : 'nextMonth2'
					} ],
					onclick : function(e) {
						var params ={workshop :'1'}
						//BeanPostProcessor 
						var url="${pageContext.request.contextPath}/wct/calendar/saveCurMonthCalendars.do";
						//var url ="${pageContext.request.contextPath}/pms/manualShift/getList.do";
						$.post(url,params,function(data){
							var reobj=eval("("+data+")");
							alert(reobj.msg);
							},"JSON");
					}
				} */ ]
			}, {
				type : 'ct',
				width : '100%',
				id :'calendarMain',
				verticalGap : 10,
				height : '560',//日期滚动的高度
				layout : 'vertical',
				border : [ 0, 0, 0, 0 ],
				children : [ {
					type : 'ct',
					cls : 'e-toolbar',
					id :'calendarWeek',
					width : '100%',
					height : '25',
					horizontalAlign : 'center',
					verticalAlign : 'middle',
					horizontalGap : '65',//调整星期之间的间隔
					layout : 'horizontal',
					border : [ 0, 0, 0, 0 ],
					children : [{
						type : 'label',
					style : 'font:bold 14px/20px arial,sans-serif;',//设置字体大小
						text : '星期日'
					}, {
						type : 'label',
						style : 'font:bold 14px/20px arial,sans-serif;',//设置字体大小
						text : '星期一'
					}, {
						type : 'label',
						style : 'font:bold 14px/20px arial,sans-serif;',//设置字体大小
						text : '星期二'
					}, {
						type : 'label',
						style : 'font:bold 14px/20px arial,sans-serif;',//设置字体大小
						text : '星期三'
					}, {
						type : 'label',
						style : 'font:bold 14px/20px arial,sans-serif;',//设置字体大小
						text : '星期四'
					}, {
						type : 'label',
						style : 'font:bold 14px/20px arial,sans-serif;',//设置字体大小
						text : '星期五'
					}, {
						type : 'label',
						style : 'font:bold 14px/20px arial,sans-serif;',//设置字体大小
						text : '星期六'
					}]
				}, {
					id : 'calendar_box',
					type : 'ct',
					width : '818',
					top:'80',
					horizontalAlign : 'center',
					//verticalScrollPolicy : 'on',//最右边的日期区域的滚动条
					height : '520',//调整滚动高度
					verticalGap : 0,//调整日期之间的高度
					layout : 'vertical',
					//border : [ 0, 0, 0, 0 ],
					children : [ {
						type : 'ct',
						width:'100%',
						horizontalAlign : 'center',
						verticalAlign : 'middle',
						horizontalGap : 0,//调整日历日期图片左右之间的距离
						layout : 'horizontal',
						border : [ 0, 0, 0, 0 ],
						children : [ {
							id : 'calendar_0',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							//horizontalAlign : 'center',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								
								text : '<font color=red >28</font>'
							} ]
						}, {
							id : 'calendar_1',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>29</font>'
							} ]
						}, {
							id : 'calendar_2',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',								
								text : '<font color=red>30</font>'
							} ]
						}, {
							id : 'calendar_3',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>01</font>'
							} ]
						}, {
							id : 'calendar_4',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>02</font>'
							} ]
						}, {
							id : 'calendar_5',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>03</font>'
							} ]
						}, {
							id : 'calendar_6',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>04</font>'
							} ]
						} ]
					}, {//日历日期第二排
						type : 'ct',
						width:'100%',
						horizontalAlign : 'center',
						verticalAlign : 'middle',
						horizontalGap :0,//日期左右之间的距离
						layout : 'horizontal',
						border : [ 0, 0, 0, 0 ],
						children : [ {
							id : 'calendar_7',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>05</font>'
							} ]
						}, {
							id : 'calendar_8',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>06</font>'
							} ]
						}, {
							id : 'calendar_9',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>07</font>'
							} ]
						}, {
							id : 'calendar_10',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>08</font>'
							} ]
						}, {
							id : 'calendar_11',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>09</font>'
							} ]
						}, {
							id : 'calendar_12',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>10</font>'
							} ]
						}, {
							id : 'calendar_13',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>11</font>'
							} ]
						} ]
					}, {//第三排
						type : 'ct',
						width:'100%',
						horizontalAlign : 'center',
						verticalAlign : 'middle',
						horizontalGap : 0,//日期左右之间距离
						layout : 'horizontal',
						border : [ 0, 0, 0, 0 ],
						children : [ {
							id : 'calendar_14',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>12</font>'
							} ]
						}, {
							id : 'calendar_15',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>13</font>'
							} ]
						}, {
							id : 'calendar_16',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>14</font>'
							} ]
						}, {
							id : 'calendar_17',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>15</font>'
							} ]
						}, {
							id : 'calendar_18',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>16</font>'
							} ]
						}, {
							id : 'calendar_19',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>17</font>'
							} ]
						}, {
							id : 'calendar_20',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>18</font>'
							} ]
						} ]
					}, {//第四排
						type : 'ct',
						width:'100%',
						horizontalAlign : 'center',
						verticalAlign : 'middle',
						horizontalGap : 0,
						layout : 'horizontal',
						border : [ 0, 0, 0, 0 ],
						children : [ {
							id : 'calendar_21',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							//horizontalAlign : 'center',
							children : [ {
								type : 'label',
								text : '<font color=red>19</font>'
							} ]
						}, {
							id : 'calendar_22',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>20</font>'
							} ]
						}, {
							id : 'calendar_23',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>21</font>'
							} ]
						}, {
							id : 'calendar_24',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>22</font>'
							} ]
						}, {
							id : 'calendar_25',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',	
// 							horizontalAlign : 'center',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>23</font>'
							} ]
						}, {
							id : 'calendar_26',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>24</font>'
							} ]
						}, {
							id : 'calendar_27',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>25</font>'
							} ]
						} ]
					}, {//第五排
						type : 'ct',
						width:'100%',
						horizontalAlign : 'center',
						verticalAlign : 'middle',
						horizontalGap : 0,
						layout : 'horizontal',
						border : [ 0, 0, 0, 0 ],
						children : [ {
							id : 'calendar_28',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>26</font>'
							} ]
						}, {
							id : 'calendar_29',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
// 							horizontalAlign : 'center',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>27</font>'
							} ]
						}, {
							id : 'calendar_30',
							type : 'ct',
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',								
								text : '<font color=red>28</font>'
							} ]
						}, {
							id : 'calendar_31',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>29</font>'
							} ]
						}, {
							id : 'calendar_32',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>30</font>'
							} ]
						}, {
							id : 'calendar_33',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',						 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>31</font>'
							} ]
						}, {
							id : 'calendar_34',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',							 
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>01</font>'
							} ]
						} ]
					}, {//第六排
						type : 'ct',
						width:'100%',
						horizontalAlign : 'center',
						verticalAlign : 'middle',
						horizontalGap : 0,
						layout : 'horizontal',
						border : [ 0, 0, 0, 0 ],
						children : [ {
							id : 'calendar_35',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>26</font>'
							} ]
						}, {
							id : 'calendar_36',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>27</font>'
							} ]
						}, {
							id : 'calendar_37',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>28</font>'
							} ]
						}, {
							id : 'calendar_38',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>29</font>'
							} ]
						}, {
							id : 'calendar_39',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>30</font>'
							} ]
						}, {
							id : 'calendar_40',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>31</font>'
							} ]
						}, {
							id : 'calendar_41',
							type : 'ct',
							border : [ 0, 0, 0, 0 ],
							layout : 'vertical',
							width : '117',
							height : '82',
							verticalAlign : 'middle',
							children : [ {
								type : 'label',
								text : '<font color=red>01</font>'
							} ]
						} ]
					} ]
				} ]
			} ]
		//app
		});
		var dateDefault = new Date();
		var strDefault;
		countDate(dateDefault, strDefault, 2);
	});
	window.onerror = function() {
		return true;
	};
	
	

	var firstDay;
//点击页面显示数据   查询当前月份工厂日历（样式显示）
	function countDate(date, dateStr, defaultFlag) {
        
		if (date == null) {
			date = new Date();
		} else if (date.getYear() == new Date().getYear()
				&& date.getMonth() == new Date().getMonth()) {
			date = new Date();
		}
		var SY = date.getYear();//当前年份 
		var SM = date.getMonth();//当前月
		var SD = date.getDate() - 1;//当前日
		firstDay = getWeekForFirstDao(date);//一号为星期几
		var lastDay = getDay(SY, SM);//得到本月的天数
		var up = getDay(SY, (SM - 1)) - firstDay + 1;
		var down = 1;//次月日期
		var curr = 1;//本月日期
		var month = SM;
		if (SM < 9) {
			SM = "0" + (SM + 1);
		} else {
			SM += 1;
		}
		calendarCenter.getChildAt(0).set({
			text : (date.getFullYear() + '年' + SM + '月')
		});
		for ( var i = 0; i < 42; i++) {
			var obj = Edo.get('calendar_' + i);
			obj.removeAllChildren();
			obj.addChild({
				type : 'label',
				text : '<font color=red>01</font>'
			});
			//设置日历的背景颜色图片
			//上个月的日期
			if (i < firstDay) {
				obj.getChildAt(0).set({
					style : 'font-size: 22px;font-weight: bold;margin-left:10px;',//设置日历号字体大小
					text : '<font color=gray>' + getStr(up) + '</font>'//设置字体颜色
				});
				obj.set({
					cls : ''
				});
				obj.set({
					cls : 'lb-calendar-grey'
				});
				up++;
			} else if (i >= firstDay && i < (lastDay + firstDay)) {
				obj.set({
					cls : ''
				});
				if (SD == (i - firstDay) && month == new Date().getMonth()
						&& SY == new Date().getYear()) {
					obj.set({
						cls : 'lb-calendar-red'
					});

				} else if ((month > new Date().getMonth() && SY >= new Date()
						.getYear())
						|| (SD<i - firstDay && SD>firstDay)) {
					obj.set({
						cls : 'lb-calendar-blue'
					});

				} else {
					obj.set({
						cls : 'lb-calendar-green'
					});

				}
				//本月的日期
				obj.getChildAt(0).set({
					style : 'font-size: 22px;font-weight: bold;margin-left:10px;',//设置日历号字体大小
					text : '<font>' + getStr(curr) + '</font>'
					
				});
				curr++;
			} else {//下个月的日期
				obj.getChildAt(0).set({
					style : 'font-size: 22px;font-weight: bold;margin-left:10px;',//设置日历号字体大小
					text : '<font color=gray>' + getStr(down) + '</font>'
				});
				obj.set({
					cls : ''
				});
				obj.set({
					cls : 'lb-calendar-grey'
				});
				down++;
			}
		}

		if (SD < 1) {
			SD = 1;
		}
		if (SD < 10) {
			SD = "0" + SD;
		}
		if (dateStr == null) {
			dateStr = date.getFullYear() + "-" + SM + "-" + SD + "T"
					+ date.getHours() + ":" + date.getMinutes() + ":"
					+ date.getSeconds();
		}
		//setFactory(firstDay, dateStr, defaultFlag);
		getData(firstDay, dateStr, defaultFlag);//获取日历的数据
	}

	function getStr(day) {
		if (day < 10) {
			day = "0" + day;
		}
		return day;
	}
	//显示日历的数据  查询当前月份数据setFactory(result, firstDay, defaultFlag);//点击某天出现手
	function getData(firstDay, dateStr, defaultFlag) {
		var result = [];
		Edo.util.Ajax.request({
			type : 'post',
			async : true,
			url : '${pageContext.request.contextPath}/wct/calendar/getCurMonthCalendars.do',
			params : {
				'date' : dateStr,
				'workshop':'1'
			},
			onSuccess : function(json) {

				result = eval("("+json+")");
				setFactory(result, firstDay, defaultFlag);//点击某天出现手
			},
			onFail : function(code) {
				
			}
		});
	}

	//日历上显示数据 例如：早班：乙班，中班：甲班，晚班：丙班
	function setFactory(data, firstDay, defaultFlag) {
		if (data != null && data[0] != null) {
			var year1 = '';
			var month1 = '';
			calendarCenter.getChildAt(0).set(
					{
						text : data[0].date.substring(0, 4) + "年"
								+ data[0].date.substring(5, 7) + "月"
					});
			 //循环显示数据在页面上
			for ( var i = 0; i < data.length; i++) {
				var wkDate = data[i].date;
				var date = new Date(wkDate.replace(/-/g, "/"));

				year1 = date.getYear();
				month1 = date.getMonth() + 1;
				var str = wkDate.substring(8, 10);
				if (str == '08' || str == '09') {
					str = str.substring(1, 2);
				}
				var obj = Edo.get('calendar_' + (parseInt(str) + firstDay - 1));
				 obj.addChild({
					type : 'label',
					height : '6',
					width : '60',
					style:"margin-left:46px;",
					text : '<font  style="font-size:12px;" class="hidtime" stim="'+data[i].stim+'" etim="'+data[i].etim+'">' + data[i].shift
							+ ':</font>&nbsp;<font style="font-size:12px;font-weight: bold;">'
							+ data[i].team + '</font>'
				}); 
				obj.set('style', 'cursor:pointer;');
				
				obj.set({
					verticalGap : -8
					//horizontalAlign : 'right'
				});
				
// 				//本月的日期
				obj.getChildAt(0).set({
					style : 'margin-left:10px;margin-top:20px'
				});
				
				if (data[i].lubrictal == 'true') {
					obj.addChild({
						width : 24,
						height : 24,
						border : [ 0, 0, 0, 0 ],
						type : 'box',
						bodyCls : 'lb-calendar-ok'
					});

				}
				if (parseInt(data[i].date.substring(5, 7)) >= (new Date()
						.getMonth() + 1)
						&& defaultFlag == 2) {

					obj.set({
								onclick : function(e) {
									
								}
							});
				}
				
				
			}

			var day = new Date(year1, month1, 0);
			var monthDay = '';
			var dateStrs = '';
			var date1 = year1 + "-" + month1 + "-01";

			dateStrs = new Date(date1.replace(/-/g, "/"));

			monthDay = getWeekForFirstDao(dateStrs);

			if (defaultFlag == 2 || defaultFlag == 3) {
				for ( var k = monthDay; k < day.getDate() + monthDay; k++) {
					var obj = Edo.get('calendar_' + k);
					obj.set('style', 'cursor:pointer');
					obj
							.set({
								onclick : function(e) {
									var strDate = currDate.getValue();
									var year = parseInt(strDate.substring(0, 4));
									var month = getStr(Number(strDate
											.substring(5, 7)));
									var day = this.getChildAt(0).getValue()
											.substring(16, 18);
									var dateStr = year + "-" + month + "-"
											+ day;

									//点击某一天   (无排班时)
									//showWin(dateStr);
								}
							});
				}
			}

		} else {
			for ( var i = 0; i < 42; i++) {
				var obj = Edo.get('calendar_' + i);
				obj.set('style', 'cursor:pointer');
				obj.set({
					onclick : function(e) {
						var strDate = currDate.getValue();
						var year = parseInt(strDate.substring(0, 4));
						var month = getStr(Number(strDate.substring(5, 7)));
						var day = this.getChildAt(0).getValue().substring(16,
								18);
						var dateStr = year + "-" + month + "-" + day;
						//点击某一天
						//showWin(dateStr);

					}
				});
			}
		}
	}
	function DayNumOfMonth(Year, Month) {
		return 32 - new Data(year, month, 32).getData();
	}
	function getDate() {
		var date = new Date();
		return date;
	}
	/**
	 * 获取每个月的第一天是星期几
	 */
	function getWeekForFirstDao(date) {

		date.setDate(1);
		var week = date.getDay();

		return week;
	}
	/**
	 * 获取当月的天数
	 */
	function getDay(Year, Mon) {
		var start = new Date(Year, Mon, 1);
		var end = new Date(Year, Mon + 1, 1);
		var days = (end - start) / (1000 * 60 * 60 * 24);
		return days;
	}
	
	$(function(){
		$(".lb-calendar-green,.lb-calendar-red,.lb-calendar-blue").live("click",function(){
			var str="";
			$(this).find(".hidtime").each(function(i){
				var thiz=$(this);
				str+=thiz.html()+thiz.attr("stim")+" 到  "+thiz.attr("etim")+"<br>";
			});
			jAlert(str,"排班详情");
		});
	});
	
</script>
<style>
	.lb-calendar-red .e-label{left:20px;}

</style>
</head>
<body>
</body>
</html>