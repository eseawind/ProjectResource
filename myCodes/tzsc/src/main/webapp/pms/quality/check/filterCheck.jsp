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
td{ font-size:12px;width: 30px;}

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
		clearTab("tab1",2,1,4);
		clearTab("tab2",2,0,4);
		clearTab("tab3",2,0,4);
		clearTab("tab4",3,1,18);
		clearTab("tab5",2,0,3);
		clearTab("tab6",2,2,3);
		clearTab("tab7",2,0,3);
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
					temp[oldVal]=temp[oldVal]+",不合格数:"+temp[qxVal]+":"+getUom(temp[uom]);
					list[i]=temp;
				}
			}
		}
	};
	//如果有缺陷，则显示缺陷名称和缺陷个数及单位
	var calQX=function(bean){
		updateNumber(bean.qmMassFirstC,2,4,5);
		updateNumber(bean.qmMassFirstZ,1,4,5);
		updateNumber(bean.qmMassFirstG,1,4,5);
		//updateNumber(bean.qmMassProcessD,2,7,8);
		updateNumber(bean.qmMassProcessC,1,18,19);
		
	};
	bandParams=function(){
		//params=getJsonData($('#eqpLubriPlan-wct-frm'));
		
		//var params={"mdShiftId":"2","mdEqmentId":"402899a54a80696a014a8072934e0007","time":"2015-10-28"};
		$.post("${pageContext.request.contextPath}/pms/massCheck/getFilterCheckDataList.do",params,function(bean){
			clearParams();
			if(bean==null){
				return;
			}
			calQX(bean);
			$("#team").html(bean.team);
			var shift=bean.shift;
			var shiftHtml;
			if(shift=="白班"){
				shiftHtml="( <font size='3'>白</font>、中、夜  )";
			}else if(shift=="中班"){
				shiftHtml="( 白、<font size='3'>中</font>、夜  )";
			}else if(shift=="夜班"){
				shiftHtml="( 白、中、<font size='3'>夜</font> )";
			}
			$("#shift2").html(shiftHtml);
			$("#eqp").html(bean.equ);
			$("#part").html(bean.matName);
			$("#name2").html(bean.userD);
			$("#time").html(bean.date);
			if(bean.qmMassFirstC!=null){
				showTab("tab1",bean.qmMassFirstC,2,1,[0,1,2,3]);
			}
			if(bean.qmMassFirstZ!=null){
				showTab("tab2",bean.qmMassFirstZ,2,0,[0,1,2,3]);
			}
			if(bean.qmMassFirstG!=null){
				showTab("tab3",bean.qmMassFirstG,2,0,[0,1,2,3]);
			}
			if(bean.qmMassProcessD!=null){
				showTab("tab5",bean.qmMassProcessD,2,0,[0,1,2]);
			}
			if(bean.qmMassProcessC!=null){
				showTab("tab4",bean.qmMassProcessC,3,1,[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17]);
			}
			var list=bean.qmMassExcipient;
			if(list==null){
				return;
			}
			var i=0;
			//由于辅料、自检自动装置确认记录特殊，所以单独编写数据显示
			$("#tab6 tr").each(function(idx){
				if(idx>1){
					if(list.length>i){
						var tr=$(this);
						var revalue=list[i];
						if(idx-1==revalue[5]){
							tr.find("td:eq(2)").html(revalue[2]);
							tr.find("td:eq(3)").html(revalue[3]);
							tr.find("td:eq(4)").html(revalue[4]);
							i++;
						}
					}
				}	 
			});
			if(bean.qmMassProcessD!=null){
				showTab("tab7",bean.qmMassSp,2,0,[0,1,2]);
			}
			
			//showTab("tab6",bean.qmMassExcipient,2,0,[0,1,2,3,4]);
		},"JSON");
	};
	
	
});

</script>
</head>

<body bgcolor="#FFFFFF">
<div align="center"><font size="6">成  型  机  滤  棒  质  量  自  检  记  录</font></div>
<div align="center" style="height:500px;width:1000px;">
<table width="158%" height="20"><tr><td align="right">编号：JL-TZ 12 012&nbsp;&nbsp;</td></tr></table>
<table width="158%" height="100%" border=1 bordercolor=#000000 style="border-collapse:collapse">
  <tr>
    <td height="33" colspan="2"><table width="100%" border="0">
      <tr>
        <td width="4%" height="25">班次：</td>
        <td width="6%"><span id="team">&nbsp;</span></td>
        <td width="8%"><span id="shift2">（白、中、夜）</span></td>
        <td width="5%">机台：</td>
        <td width="17%"><span id="eqp">&nbsp;</span></td>
        <td width="6%">牌名：</td>
        <td width="17%"><span id="part">&nbsp;</span></td>
        <td width="5%">操作工：</td>
        <td width="17%"><span id="name2">&nbsp;</span></td>
        <td width="14%">日期：      <span id="time">年    &nbsp; 月    &nbsp; 日</span></td>
        <td width="1%">&nbsp;</td>
        </tr>
    </table></td>
    </tr>
  <tr>
    <td height="28" align="center">首检记录</td>
    <td width="320" rowspan="4"vertical-align:top;><table width="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;"">
        <tr>
        <td height="222" valign="top">
        <table id="tab6" width="100%" height="100%"  border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
        <tr>
        <td colspan="5">辅料、自检自控装置确认记录</td>
        </tr>
        <tr>
        <td>检查项目</td>
        <td>检查频次</td>
        <td>检查时间</td>
        <td>检查判定</td>
        <td>不合格情况</td>
        </tr>
        <tr>
        <td>接班辅料<br>确认</td>
        <td>每班接班</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>班中接收<br>辅料确认</td>
        <td>班中接收<br>辅料</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>接头测试</td>
        <td>接班两小<br>时内</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        </table>
        </td>
        </tr>
        <tr>
        <td height="150" valign="top">
        <table id="tab7" width="100%" height="100%"  border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
        <tr>
        <td colspan="3">三乙酸甘油酯施加量</td>
        </tr>
        <tr>
        <td>干棒检测  g/10支</td>
        <td>湿棒检测  g/10支</td>
        <td>施加量%</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        </table>
        </td>
        </tr>
        <tr>
          <td height="180" align="left" valign="top">
          <font>填写说明：<br/>
          1．检验时间按24小时制填写。<br/>
          2．质量情况栏、外观栏、检查结果栏填写，当次检验无质量问题，则在填写“0”；如有质量问题，则必须填写质量缺陷代码，不合格数量（以支为单位）填写在后面；若属“其它”质量缺陷，首检应直接描述缺陷和数量，过程检验应在“备注”栏内注明缺陷具体的名称。<br/>
          3．若当次检验合格，则在“判断”栏内划“√”，；若当次检验不合格，则在“判断”栏划“×”。<br/>
          4．“产品过程检验记录中”的“负”“正”项目分别填写“超下限支数”“超上限支数”。<br/>
          5．表中结合实际无需填写项目和空余栏均划“——”<br/><br/>
          </font>
			</td>
        </tr>
        <tr>
          <td height="250" align="left" valign="top">其他备注信息：</td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="1"  border="1" cellpadding="0" height="147" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td><table width="100%" border="1" cellpadding="0" height="147" cellspacing="0" frame="void" style="border-collapse:collapse;" id="tab1">
          <tr>
            <td width="7%">&nbsp;</td>
            <td colspan="4">操作工(首检)</td>
            </tr>
          <tr height="25">
            <td height="25">序号</td>
            <td width="14%">时间</td>
            <td width="30%">首检原因</td>
            <td width="27%">质量情况</td>
            <td width="22%">处理措施</td>
            </tr>
          <tr height="25">
            <td>1</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>2</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>3</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>4</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          </table></td>
        <td width="30%"><table id="tab2"   width="100%" border="1" cellpadding="0" height="147" cellspacing="0" frame="void" style="border-collapse:collapse;"">
          <tr>
            <td colspan="4">质检员(复检)</td>
            </tr>
          <tr height="25">
            <td>时间</td>
            <td>质量情况</td>
            <td>处理措施</td>
            <td>签字</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          </table></td>
        <td width="30%"><table id="tab3"  width="100%" border="1" cellpadding="0" height="147" cellspacing="0" frame="void" style="border-collapse:collapse;">
          <tr>
            <td colspan="4">工段长(复检)</td>
            </tr>
          <tr height="25">
            <td>时间</td>
            <td>质量情况</td>
            <td>处理措施</td>
            <td>签字</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="25">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          </table></td>
        </tr>
    </table></td>
    </tr>
  <tr>
    <td width="1387"><table width="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td width="70%"><table id="tab4" width="100%" height="550" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
          <tr>
            <td width="3.5%">&nbsp;</td>
            <td colspan="18">产品过程检验记录</td>
            </tr>
          <tr>
            <td rowspan="2">序号</td>
            <td width="8%" rowspan="2">时间</td>
            <td width="8%" rowspan="2">外观</td>
            <td width="13%" colspan="3">重量  mg</td>
            <td width="13%" colspan="3">吸阻  Pa</td>
            <td width="13%" colspan="3">圆周  mm</td>
            <td width="13%" colspan="3">硬度%</td>
            <td width="8%" rowspan="2">其他</td>
            <td width="5%" rowspan="2">判断</td>
            <td width="8%" rowspan="2">处理措施</td>
            <td width="8.5%" rowspan="2">备注</td>
            </tr>
          <tr>
            <td>负</td>
            <td>正</td>
            <td>均值</td>
            <td>负</td>
            <td>正</td>
            <td>均值</td>
            <td>负</td>
            <td>正</td>
            <td>均值</td>
            <td>负</td>
            <td>正</td>
            <td>均值</td>
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
            <td>&nbsp;</td>
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
            <td>2</td>
             <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>3</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>4</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>5</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>6</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>7</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>8</td>
           <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>9</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>10</td>
           <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>11</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>12</td>
           <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>13</td>
           <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>14</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>15</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>16</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>17</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>18</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>19</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>20</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>21</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>22</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>23</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
            <td>24</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
        <td width="30%">
        <table id="tab5"  width="100%" height="550" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
        <tr>
        <td colspan="3" height="18">丝束过程检查</td>
        </tr>
        <tr height="38">
        <td>检查结果</td>
        <td>处理措施</td>
        <td>备注</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
       <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        </table>
        </td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td width="5%" rowspan="5">代码说明</td>
        <td width="10%" rowspan="3">产品检验</td>
        <td width="85%" align="left">首检原因选择：①接班开机②班中餐③设备停机超30分钟④材料换版⑤更换牌号⑥轮保后开机⑦其他</td>
        </tr>
      <tr>
        <td align="left">外观缺陷代码：A滤嘴长/	B滤嘴短/C缩头/D皱纹/E搭口粘贴不齐/F翘边/G爆口/H切口毛渣/I触头/J搭口平台/K圆周超标/L胶孔/M切口抽丝/N滤棒重量（10支）超标/X其它</td>
        </tr>
      <tr>
        <td align="left">处理措施选择：①机台处理②反馈③报告班长④故障部位修理⑤按不合格品程序处理</td>
        </tr>
      <tr>
      <td width="10%" rowspan="2">产品检验</td>
        <td align="left">丝束缺陷代码：a外观破损/b受潮  /d虫蛀/e霉变/f异味 /g其它</td>
        </tr>
      <tr>
        <td align="left">处理措施选择：①报告班长②监护使用③退料④追溯⑤按不合格品控制程序</td>
        </tr>
    </table></td>
  </tr>
 
</table>
</div>

</body>
</html>
