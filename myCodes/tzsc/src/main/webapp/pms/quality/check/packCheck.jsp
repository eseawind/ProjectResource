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
td{ font-size:12px;}

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
		clearTab("tab1",2,1,5);
		clearTab("tab2",2,0,4);
		clearTab("tab3",2,0,4);
		clearTab("tab4",2,1,7);
		clearTab("tab5",2,1,7);
		clearTab("tab6",2,2,5); 
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
		updateNumber(bean.qmMassProcessD,2,6,7);
		updateNumber(bean.qmMassProcessC,2,6,7);
		
	};
	bandParams=function(){
		//params=getJsonData($('#eqpLubriPlan-wct-frm'));
		
		//var params={"mdShiftId":"2","mdEqmentId":"402899a54a80696a014a8072934e0007","time":"2015-10-28"};
		$.post("${pageContext.request.contextPath}/pms/massCheck/getList.do",params,function(bean){
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
				shiftHtml="( 白、中、<font size='3'>夜</font>  )";
			}
			$("#shift2").html(shiftHtml);
			$("#eqp").html(bean.equ);
			$("#part").html(bean.matName);
			$("#name1").html(bean.userD);
			$("#name2").html(bean.userC);
			$("#xh").html(bean.xh);
			$("#th").html(bean.th);
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
				showTab("tab4",bean.qmMassProcessD,2,1,[0,1,2,3,4,5]);
			}
			if(bean.qmMassProcessC!=null){
				showTab("tab5",bean.qmMassProcessC,2,1,[0,1,2,3,4,5]);
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
			
			//showTab("tab6",bean.qmMassExcipient,2,0,[0,1,2,3,4]);
		},"JSON");
	};
	
	
});

</script>
</head>

<body bgcolor="#FFFFFF">
<div align="center"><font size="6">包   装  机  质  量  自  检  记  录</font></div>
<div align="center" style="height:500px;width:1000px;">
<table width="158%" height="20"><tr><td align="right">编号：JL-TZ 13 054&nbsp;&nbsp;</td></tr></table>
<table width="158%" height="100%" border=1 bordercolor=#000000 style="border-collapse:collapse">
  <tr>
    <td height="33" colspan="2"><table width="100%" border="0">
      <tr>
        <td width="4%" height="25">班组：</td>
        <td width="6%"><span id="team">&nbsp;</span></td>
        <td width="8%"><span id="shift2">（白、中、夜）</span></td>
        <td width="5%">机台号：</td>
        <td width="6%"><span id="eqp">&nbsp;</span></td>
        <td width="6%">牌名：</td>
        <td width="8%"><span id="part">&nbsp;</span></td>
        <td width="5%">挡车工：</td>
        <td width="5%"><span id="name1">&nbsp;</span></td>
        <td width="5%">操作工：</td>
        <td width="5%"><span id="name2">&nbsp;</span></td>
        <td width="7%">小盒钢印：</td>
        <td width="5%"><span id="xh">&nbsp;</span></td>
        <td width="6%">条盒钢印：</td>
        <td width="4%"><span id="th">&nbsp;</span></td>
        <td width="14%">日期：      <span id="time">年    &nbsp; 月    &nbsp; 日</span></td>
        <td width="1%">&nbsp;</td>
        </tr>
    </table></td>
    </tr>
  <tr>
    <td height="28" align="center">首检记录</td>
    <td width="177" rowspan="4"vertical-align:top;><table width="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;"">
        <tr>
          <td height="422" align="left" valign="top">
          <script>document.write(remark_wirte_carton1);</script>
			</td>
        </tr>
        <tr>
          <td height="400" align="left" valign="top">其他备注信息：</td>
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
          <tr height="20">
            <td height="20">序号</td>
            <td width="14%">时间</td>
            <td width="30%">首检原因</td>
            <td width="27%">质量情况</td>
            <td width="22%">处理措施</td>
            </tr>
          <tr height="20">
            <td>1</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>2</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>3</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>4</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>5</td>
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
          <tr height="20">
            <td>时间</td>
            <td>质量情况</td>
            <td>处理措施</td>
            <td>签字</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
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
          <tr height="20">
            <td>时间</td>
            <td>质量情况</td>
            <td>处理措施</td>
            <td>签字</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr height="20">
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
        <td width="34%"><table id="tab4" width="100%" height="550" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
          <tr>
            <td colspan="8">过程自检记录(挡车工)</td>
            </tr>
          <tr>
            <td>序号</td>
            <td>时间</td>
            <td>产品段</td>
            <td>质量情况</td>
            <td>判断</td>
            <!-- <td>复检</td> -->
            <td>处理措施</td>
            <td>备注</td>
            </tr>
          <tr>
            <td>1</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>2</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>3</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>4</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>5</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>6</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>7</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>8</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>9</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>10</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>11</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>12</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>13</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>14</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>15</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>16</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>17</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>18</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>19</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>20</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>21</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          </table></td>
        <td width="35%"><table id="tab5" width="100%" height="550" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
          <tr>
            <td colspan="8">过程自检记录(操作工)</td>
            </tr>
          <tr>
            <td>序号</td>
            <td>时间</td>
            <td>产品段</td>
            <td>质量情况</td>
            <td>判断</td>
           <!--  <td>复检</td> -->
            <td>处理措施</td>
            <td>备注</td>
            </tr>
          <tr>
            <td>1</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>2</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>3</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>4</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>5</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>6</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>7</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>8</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>9</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          <!--   <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>10</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>11</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           <!--  <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>12</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>13</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>14</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>15</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>16</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>17</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        <!--     <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>18</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>19</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          <!--   <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>20</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <!-- <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>21</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
         <!--    <td>&nbsp;</td> -->
            <td>&nbsp;</td>
            </tr>
          </table></td>
        <td width="31%"  height="550"><table id="tab6" width="100%" height="550"  border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
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
            <td>空头缺支检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>内衬纸检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>框架纸检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>商标纸检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
           <tr>
            <td>小透明检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>小盒侧边粘贴不牢检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>小盒外观质量检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>小透明拉线检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>条透明拉线检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           </tr>
           <tr>
            <td>条缺包检测</td>
            <td>接班一小时内</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
           </tr>
          </table></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td width="5%" rowspan="5">代码说明</td>
        <td width="95%" align="left">首检原因选择：①接班开机②班中餐③设备停机超30分钟（含正常停机和故障停机）④材料换版⑤更换牌号⑥轮保后开机⑦其他</td>
        </tr>
      <tr>
        <td align="left">处理措施选择：①机台处理②反馈③报告班长④故障部位修理⑤按不合格品程序处理</td>
        </tr>
      <tr>
        <td align="left">质量缺陷代码：a盒装粘贴不牢/b盒装启盖不畅/d盒装污脏/e盒装脱色、划痕/f盒装错位/g盒装免角/h盒装烙痕/i盒装内衬纸残缺/j盒装内衬纸高低/k框架纸缺陷/m封签缺陷/n盒装钢印残缺/q盒装断残烟支/r盒装滤嘴脱落/t盒装缺支/v盒装短支 </td>
        </tr>
      <tr>
        <td align="left">质量缺陷代码：A条装透明纸包装不完整/B条装透明纸破损 /C条装透明纸折皱 /D条装拉线缺陷 /E条装钢印残缺 /F条装开边 /G条装脏污/H条装脱色、划痕 /I条装条盒不方正 /J条装条翘边/L条装烙痕 /M条装凹陷 /N盒透明纸松/P盒装透明纸折皱/Q盒装透明纸松/R盒装透明纸划痕/U其他</td>
        </tr>
      <tr>
        <td align="left">质量缺陷代码：(1)烟箱错用、(2)烟箱印刷不完整、(3)烟箱破损、(4)箱装露烟、(5)胶带错用、(6)胶带不牢或翘起、(7)少条、(8)烟条挤坏</td>
        </tr>
    </table></td>
  </tr>
 
</table>
</div>

</body>
</html>
