<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自检记录查询</title>
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script><!--<script src="jquery/jquery-1.11.0.min.js"></script>-->

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-1.3.6/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extBrowser.js" charset="utf-8"  type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-theme/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-theme/icon.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/icons/icon-all.css" rel="stylesheet" type="text/css" />


<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/changeEasyuiTheme.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery.jdirk.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extEasyUI.js"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extJquery.js"></script>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript" ></script>
<style type="text/css">
td{ font-size:12px;height:20px}

p{text-align:left}
table tr{ text-align:center;};
</style>
<script type="text/javascript" >
var bandParams;
var showTab;
var params;
$(function(){
	//表格名,数据集合,开始行,开始列,显示数据集合的列数组集合
	showTab=function(tableName,list,stratRow,startLine,indexs){
		var t="#"+tableName+" tr";
		var i=1;
		if(list==null){
			return;
		}
		$(t).each(function(idx){
			if(i>stratRow){
				if(list.length>idx-stratRow){
					var tr=$(this);
					var revalue=list[i-1-stratRow];
					for(var index=0;index<indexs.length;index++){
						tr.find("td:eq("+(startLine+index)+")").html(revalue[indexs[index]]);//时间
					}
				}
			}	
			i++;
		});
		
	};
	//表格名称,开始行,开始列,总列数
	var clearTab=function(tableName,stratRow,startLine,countLine){
		var t="#"+tableName+" tr";
		var i=1;
		$(t).each(function(idx){
			if(stratRow<i){
				var tr=$(this);
				for(var index=startLine;index<countLine;index++){
					tr.find("td:eq("+index+")").text("");//清空
				}
			}	
			i++;
		});
	};
	//清空界面数据
	var clearParams=function(){
		clearTab("tab1",1,1,6);
		clearTab("tab2",1,0,4);
		clearTab("tab3",1,0,4);
		clearTab("tab4",1,1,9);
		clearTab("tab5",1,2,5);
		clearTab("tab6",1,1,11); 
		clearTab("tab7",1,0,5); 
		
	};
	//单位
	var getUom=function(uomKey){
		for(var i=0;i<unit_key.length;i++){
			if(unit_key[i]==uomKey){
				return unit_val[i];
			}
		}
		return "";
	};
	
	//数据集合，转换前的值，缺陷数，单位(数组下标的值)
	var updateNumber=function(list,oldVal,qxVal,uom){
		if(list!=null){
			for(var i=0;i<list.length;i++){
				var temp=list[i];
				if(temp[qxVal]!="0"){
					temp[oldVal]=temp[oldVal]+","+temp[qxVal]+":"+getUom(temp[uom]);
					list[i]=temp;
				}
			}
		}
	};
	//如果有缺陷，则显示缺陷名称和缺陷个数及单位
	var calQX=function(bean){
		updateNumber(bean.qmMassFirstC,3,5,6);
		updateNumber(bean.qmMassFirstZ,1,4,5);
		updateNumber(bean.qmMassFirstG,1,4,5);
		updateNumber(bean.qmMassProcessD,3,8,9);
	};
	bandParams=function(){
		//params=getJsonData($('#eqpLubriPlan-wct-frm'));
		
		//var params={"mdShiftId":"2","mdEqmentId":"402899a54a80696a014a8072934e0007","time":"2015-10-28"};
		$.post("${pageContext.request.contextPath}/pms/massCheck/getRolerCheckDataList.do",params,function(bean){
			clearParams();
			if(bean==null){
				return;
			}
			//console.info(bean);
			calQX(bean);
			$("#team").html(bean.team);
			var shift=bean.shift;
			var shiftHtml;
			if(shift=="白班"){
				shiftHtml="( <font size='3'>白</font>、中、夜  )";
			}else if(shift=="中班"){
				shiftHtml="( 白、<font size='3'>中</font>、夜  )";
			}else if(shift=="夜班"){
				shiftHtml="( 白、中、<font size='3'> 夜</font>  )";
			}
			$("#shift2").html(shiftHtml);
			$("#eqp").html(bean.equ);
			$("#part").html(bean.matName);
			$("#name1").html(bean.userD);
			$("#name2").html(bean.userC);
			$("#xh").html();
			$("#th").html();
			$("#time").html(bean.date);
			if(bean.qmMassFirstC!=null){
				showTab("tab1",bean.qmMassFirstC,1,1,[0,1,2,3,4	]);
			}
			if(bean.qmMassFirstZ!=null){
				showTab("tab2",bean.qmMassFirstZ,1,0,[0,1,2,3]);
			}
			if(bean.qmMassFirstG!=null){
				showTab("tab3",bean.qmMassFirstG,1,0,[0,1,2,3]);
			}
			//挡车工（卷烟机）
			if(bean.qmMassProcessD!=null){
				showTab("tab4",bean.qmMassProcessD,1,1,[0,1,2,3,4,5,6,7]);
			}
			
			var list1=bean.qmMassExcipient;
			if(list1==null){
				return;
			}
			var j=0;
			//由于辅料、自检自动装置确认记录特殊，所以单独编写数据显示
			$("#tab5 tr").each(function(idx){
				if(idx>0){
					if(list1.length>j){
						var tr=$(this);
						var revalue=list1[j];
						if(idx==revalue[5]){
							tr.find("td:eq(2)").html(revalue[2]);
							tr.find("td:eq(3)").html(revalue[3]);
							tr.find("td:eq(4)").html(revalue[4]);
							j++;
						}
					}
				}	 
			});
			
			
			//丝含梗
			if(bean.qmMassStem!=null){
				showTab("tab7",bean.qmMassStem,1,0,[0,1,2,3,4,5]);
			} 
			//在线物理指标自检纪录
			var list=bean.qmMassOnline;
			if(list==null){
				return;
			}
			var i=0;
			$("#tab6 tr").each(function(idx){
						var tr=$(this);
						var revalue=null;
						if(idx>0&&idx<=list.length){
							revalue=list[idx-1];
							if(idx==1||(idx-1)%4==0){
								tr.find("td:eq(1)").html(revalue[0]);//设置时间
								tr.find("td:eq(2)").html(revalue[1]);
								tr.find("td:eq(3)").html(revalue[2]);
								tr.find("td:eq(4)").html(revalue[3]);
								tr.find("td:eq(5)").html(revalue[4]);
								tr.find("td:eq(6)").html(revalue[5]);
								tr.find("td:eq(7)").html(revalue[6]);
								tr.find("td:eq(8)").html(revalue[7]);
								tr.find("td:eq(9)").html(revalue[8]);
								tr.find("td:eq(10)").html(revalue[9]);
							}else{
								tr.find("td:eq(0)").html(revalue[1]);
								tr.find("td:eq(1)").html(revalue[2]);
								tr.find("td:eq(2)").html(revalue[3]);
								tr.find("td:eq(3)").html(revalue[4]);
								tr.find("td:eq(4)").html(revalue[5]);
								tr.find("td:eq(5)").html(revalue[6]);
								tr.find("td:eq(6)").html(revalue[7]);
								tr.find("td:eq(7)").html(revalue[8]);
								tr.find("td:eq(8)").html(revalue[9]);
							}
						}
			}); 
			
			//showTab("tab6",bean.qmMassExcipient,2,0,[0,1,2,3,4]);
		},"JSON");
	};
	
	
});

</script>
</head>

<body bgcolor="#FFFFFF">
<div align="center"><font size="6">卷   烟  机  质  量  自  检  记  录</font></div>
<div align="center" style="height:500px;width:1000px;">
<table width="158%" height="20"><tr><td align="right">编号：JL-TZ 13 053&nbsp;&nbsp;</td></tr></table>
<table width="158%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr  height="33">
    <td style="border-right:hidden">班次</td>
    <td colspan="2" style="border-right:hidden" id="team" >&nbsp;</td>
    <td colspan="2" style="border-right:hidden" id="shift2">（白、中、夜）</td>
    <td style="border-right:hidden">&nbsp;</td>
    <td colspan="2" style="border-right:hidden">机台号</td>
    <td colspan="5" style="border-right:hidden" id="eqp">&nbsp;</td>
    <td colspan="2" style="border-right:hidden">牌名</td>
    <td style="border-right:hidden;text-align:left" id="part" >&nbsp;</td>
    <td style="border-right:hidden">挡车工</td>
    <td colspan="6" style="border-right:hidden;text-align:left" id="name1" >&nbsp;</td>
    <td colspan="3" style="border-right:hidden"></td>
    <td colspan="3" style="border-right:hidden" >&nbsp;</td>
    <td style="border-right:hidden"></td>
    <td colspan="2" style="border-right:hidden">日期</td>
    <td style="border-right:hidden"></td>
    <td colspan="2" style="border-right:hidden" id="time">&nbsp;</td>
    <td colspan="2" style="border-right:hidden"></td>
  </tr>
  <tr>
    <td colspan="28">首检记录</td>
    <td colspan="10" >辅料 自检自控装置确认记录</td>
  </tr>
  <tr>
    <td colspan="12">操作工（首检）</td>
    <td colspan="6">质检员（复检）</td>
    <td colspan="10">工段长（复检）</td>
    <td colspan="10" rowspan="4"><table id="tab5" width="100%" height="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td >检查项目</td>
        <td >检查频次</td>
        <td >检查时间</td>
        <td >检查判定</td>
        <td>不合格情况</td>
      </tr>
      <tr>
        <td>接班辅料确认</td>
        <td>每班接班</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>班中接收辅料确认</td>
        <td>班中接收辅料</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>重量控制系统</td>
        <td>接班一小时内</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>OTIS控制</td>
        <td>接班一小时内</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>漏气检测</td>
        <td>接班一小时内</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>空气检测</td>
        <td>接班一小时内</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>水松纸拼接头剔除</br>检测</td>
        <td>接班一小时内</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>盘纸拼接头剔除检测</td>
        <td>接班一小时内</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="12"><table id='tab1' width="100%"  height="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td style="width: 40px;">序号</td>
        <td style="width: 60px;">时间</td>
        <td style="width: 80px;">首检原因</td>
        <td style="width: 80px;">重量g/20支</td>
        <td style="width: 120px;">外观质量情况</td>
        <td style="width: 80px;">处理措施</td>
      </tr>
      <tr>
        <td>1</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="6"><table id='tab2' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td style="width: 60px;">时间</td>
        <td style="width: 120px;">存在问题</td>
        <td style="width: 80px;">处理措施</td>
        <td style="width: 80px;">签字</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="10"><table id='tab3' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td style="width: 60px;">时间</td>
        <td style="width: 120px;">存在问题</td>
        <td style="width: 80px;">处理措施</td>
        <td style="width: 80px;">签字</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  
  <tr>
    <td colspan="13">过程自检记录</td>
    <td colspan="15">在线物理指标自检记录（综合测试台）</td>
  </tr>
  <tr>
    <td colspan="13" rowspan="5" ><table id='tab4' height="100%" width="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td style="width:40px">序号</td>
        <td style="width:60px">时间</td>
        <td style="width:80px">产品段</td>
        <td style="width:120px">重量g/20支</td>
        <td style="width:160px">外观质量情况</td>
        <td style="width:60px">判断</td>
        <td style="width:60px">复检</td>
        <td style="width:120px">处理措施</td>
        <td style="width:120px">备注</td>
      </tr>
      <tr>
        <td>1</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>6</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>7</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>8</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>9</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>10</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>11</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>12</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>13</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>14</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>15</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>16</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>17</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>18</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>19</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>20</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>21</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>22</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>23</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>24</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="15" rowspan="5"><table id="tab6" width="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td style="width:40px">序号</td>
        <td style="width:60px">时间</td>
        <td style="width:120px">检测项目</td>
        <td style="width:120px">超上限</br>支数</td>
        <td style="width:120px">超下限</br>支数</td>
        <td style="width:80px">平均值</td>
        <td style="width:60px">标偏</td>
        <td style="width:60px">判断</td>
        <td style="width:60px">复检</td>
        <td style="width:120px">处理措施</td>
        <td style="width:120px">备注</td>
      </tr>
      
      <tr>
        <td rowspan="4">1</td>
        <td rowspan="4">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="4">2</td>
        <td rowspan="4">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="4">3</td>
        <td rowspan="4">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="4">4</td>
        <td rowspan="4">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="4">5</td>
        <td rowspan="4">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="4">6</td>
        <td rowspan="4">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  
  <tr>
    <td colspan="10">丝含梗</td>
  </tr>
  <tr>
    <td colspan="10" ><table id='tab7' width="100%" height="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td>检查时间</td>
        <td>检查情况</td>
        <td>判断</td>
        <td>处理措施</td>
        <td>备注</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  
  <tr style="text-align:left;margin-top:0px">
    <td colspan="10" height="100%" valign="top" ><p>填写说明：</p>
    <p>1.检验时间按24小时制填写。</p>
    <p>2.外观质量情况栏填写，当检验无质量问题，则在填写“0”；如有质量问题，则必须填写质量缺陷代码，不合格数量（以支为单位）填写在后面；若属“其它”质量缺陷，首检应直接描述缺陷和数量，过程检验应在“备注”栏内注明缺陷具体的名称。</p>
    <p>3.若当前检验合格，则在“判断”栏内划“√”，“复检”栏划“-”；若当次检验不合格，则在“判断”栏内划“错”，并进行复检,根据复检结果在“复检栏”划“√”或“×”。</p>
    <p>4.“产品段”栏填写检验时机台产量，单位为万支。</p>
    <p>5.表中结合实际无需填写项目和空余栏均划“-”。</p></td>
  </tr>
  
  <tr style="text-align:left; margin-top:0px">
    <td colspan="10" rowspan="4"  height="100%" valign="top" ><p>其他备注信息：</p>
    </td>
  </tr>
  
  <tr">
    <td rowspan="3" >代码</br>说明</td>
    <td colspan="27"  style="text-align:left">首检原因选择：①接班开机,②班中餐,③设备停机超30分钟(含正常停机和故障停机),④材料换版,⑤更换牌号,⑥轮保后开机,⑦其他</td>
  </tr>
  <tr>
    <td colspan="27"  style="text-align:left">处理措施选择：①机台处理,②反馈,③报告班长,④故障部位修理,⑤按不合格品程序处理</td>
  </tr>
  <tr>
    <td colspan="27"  style="text-align:left">外观缺陷代码：A烟支空头,B烟支缩头,C烟支切口不齐,D卷接暴口,E搭扣粘帖不牢固,F烟支卷接夹末,G滤嘴脱落,H漏气,I烟支滤嘴变形,J烟支接装纸长短,K烟支接装纸破损,L烟支滤嘴泡皱,M烟支接装纸错位,N烟支接装纸翘边,P烟支断残,Q烟支破损,R烟支钢印不全,S烟支表面污脏、不洁,T烟支表面皱,X其他,Z长度超标,Y圆周超标</td>
  </tr>
</table>
</body>
</html>
