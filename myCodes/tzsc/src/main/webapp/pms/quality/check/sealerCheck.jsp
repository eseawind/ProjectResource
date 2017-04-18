<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>装封箱机自检记录查询</title>
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
td{ font-size:12px;height:20px;}

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
		clearTab("tab4",2,1,7);
		clearTab("tab5",2,2,3);
	};
	//单位
	var getUom=function(uomKey){
		for(var i=0;i<sealer_unit_key.length;i++){
			if(sealer_unit_key[i]==uomKey){
				return sealer_unit_val[i];
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
		updateNumber(bean.qmMassProcessD,2,7,8);
		//updateNumber(bean.qmMassProcessC,2,7,8);
		
	};
	bandParams=function(){
		//params=getJsonData($('#eqpLubriPlan-wct-frm'));
		clearParams();
		//var params={"mdShiftId":"2","mdEqmentId":"402899a54a80696a014a8072934e0007","time":"2015-10-28"};
		$.post("${pageContext.request.contextPath}/pms/massCheck/queryFXJCheckDataList.do",params,function(bean){
			console.info(bean);
			if(bean==null){
				return;
			}
			calQX(bean);
			$("#team").html(bean.team);
			var shift=bean.shift;
			var shiftHtml="";
			if(shift=="白班"){
				shiftHtml="( <font size='3' color=red>白</font>、中、夜  )";
			}else if(shift=="中班"){
				shiftHtml="( 白、<font size='3' color=red>中</font>、夜  )";
			}else if(shift=="夜班"){
				shiftHtml="( 白、中、<font size='3' color=red>夜</font> )";
			}
			$("#shift2").html(shiftHtml);
			$("#eqp").html(bean.equ);
			$("#part").html(bean.matName);
			$("#name").html(bean.userD);
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
				showTab("tab4",bean.qmMassProcessD,2,1,[0,1,2,3,4,5,6]);
			}
			if(bean.qmMassProcessC!=null){
				showTab("tab4",bean.qmMassProcessC,2,1,[0,1,2,3,4,5,6]);
			}
			var list=bean.qmMassExcipient;
			if(list==null){
				return;
			}
			var i=0;
			//由于辅料、自检自动装置确认记录特殊，所以单独编写数据显示
			$("#tab5 tr").each(function(idx){
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

<body bgcolor="white">
<div align="center"><font size="6">装   封   箱  机  质  量  自  检  记  录</font></div><br>
<div style="width:1500; height:500">
<table width="100.15%" height="20"><tr><td align="right">编号：JL-TZ 13 055&nbsp;&nbsp;</td></tr></table>
<table width="100.15%"  border="1" cellspacing="0" bordercolor=#666666 style="border-bottom:hidden; border-collapse:collapse">
  <tr >
    <td width="9%"  align="center" style="border-right:hidden;">班组：</td>
    <td width="9%" align="left" style="border-right:hidden;"><span id="team">&nbsp;</span></td>
    <td width="9%"  align="center" style="border-right:hidden;"><span id="shift2">（白、中、夜）</span></td>
    <td width="9%"  align="center" style="border-right:hidden;">机台：</td>
    <td width="9%"  align="left" style="border-right:hidden;"><span id="eqp">&nbsp;</span></td>
    <td width="9%"  align="center" style="border-right:hidden;">牌名：</td>
    <td width="9%"  align="left" style="border-right:hidden;"><span id="part">&nbsp;</span></td>
    <td width="9%"  align="center" style="border-right:hidden;">操作工：</td>
    <td width="9%"  align="left" style="border-right:hidden;"><span id="name">&nbsp;</span></td>
    <td width="20%" >日期：<span id="time">&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;日</span></td>
  </tr>
  <tr>
    <td colspan="10" height="30" align="center">首检记录</td>
    </tr>
</table>


  <table width="100%" cellspacing="0" >
    <tr>
      <td width="40%" bordercolor="0">
	  <table id="tab1" width="100%" height="100%" border="1" cellspacing="0" bordercolor=#666666 style="border-bottom:hidden;border-collapse:collapse; margin-left:-1px; margin-top:-1px;">
        <tr>
          <td>&nbsp;</td>
          <td colspan="4" height="30" align="center">操作工（首检）</td>
        </tr>
        <tr>
          <td width="10%" >序号</td>
          <td width="20%" >时间</td>
          <td width="23%" >首检原因</td>
          <td width="23%" >质量情况</td>
          <td width="23%" >处理措施</td>
        </tr>
        <tr>
          <td>1</td>
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
        </tr>
        <tr>
          <td>3</td>
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
        </tr>
      </table></td>
      <td width="30%">
		<table id="tab2" width="101%" height="100%" border="1" cellspacing="0" bordercolor="#666666" style="border-bottom:hidden; border-left:hidden; border-collapse:collapse; margin-left:-3px; margin-top:-1px;">
        <tr>
          <td colspan="4" align="center" height="30">质检员（复检）</td>
        </tr>
        <tr>
          <td width="25%" >时间</td>
          <td width="25%" >质量情况</td>
          <td width="25%" >处理措施</td>
          <td width="25%" >签字</td>
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
      <td width="30%" >
      <table id="tab3" width="101%" height="100%" border="1" cellspacing="0" bordercolor="#666666" style="border-bottom:hidden; border-left:hidden; border-collapse:collapse; margin-left:-1px; margin-top:-1px;">
        <tr>
          <td colspan="4" align="center" height="30">工段长（复检）</td>
        </tr>
        <tr>
          <td width="25%" >时间</td>
          <td width="25%" >质量情况</td>
          <td width="25%" >处理措施</td>
          <td width="25%" >签字</td>
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
  </table>
  <blockquote>&nbsp;</blockquote>
  <table  width="100%"  cellspacing="0" style=" margin-top:-74px; margin-left:-1px;">
  <tr>
    <td width="38%" rowspan="2">
	<table width="100%" id="tab4" border="1" cellspacing="0" bordercolor="#666666" style="border-collapse:collapse; margin-top:22px; height:481px;">
      <tr>
        <td colspan="8" align="center" >过程自检</td>
        </tr>
      <tr>
        <td width="5%" >序号</td>
        <td width="10%" >时间</td>
        <td width="10%" >产品段</td>
        <td width="35%">质量情况</td>
        <td width="10%" >判断</td>
        <td width="10%" >复检</td>
        <td width="10%">处理措施</td>
        <td width="10%">备注</td>
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
      </tr>
    </table></td>
	<td width="33%">
	<table id="tab5" width="101.5%" height="180" border="1" cellspacing="0" bordercolor="#666666" style="border-bottom:hidden; border-left:hidden; border-collapse:collapse; margin-top:0px; margin-left:-2px;">
      <tr>
        <td colspan="5" align="center">辅料、自检自控装置确认记录</td>
        </tr>
      <tr>
        <td width="20%" >检查项目</td>
        <td width="20%" >检查频次</td>
        <td width="20%" >检查时间</td>
        <td width="20%" >检查判定</td>
        <td width="20%" >不合格情况</td>
      </tr>
      <tr>
        <td align="center" >接班<br/>辅料确认</td>
        <td align="center">每班<br/>接班</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center">班中接收<br />辅料确认</td>
        <td align="center">班中接<br/>收辅料</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center">条烟外观<br />质量检测</td>
        <td align="center">接班一<br/>小时内</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center">箱装缺条<br /> 检测</td>
        <td align="center">接班一<br/>小时内</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
      </tr>
    </table></td>
	<td width="33% " >
	<table  width="100.5%" height="200" border="1" cellspacing="0" bordercolor="#666666" style="border-bottom:hidden; border-left:hidden; border-collapse:collapse; margin-top:22px; margin-left:2px;">
      <tr>
        <td align="left" valign="top"><br>
        	<font size="1">
	        	填写说明：<br/>
	        	&nbsp;1.检验时间按24小时制填写。<br/>
				&nbsp;2.外观质量情况栏填写，当次检验无质量问题，则在填写“0”，如果有质量问题，则必须填写质量缺陷代码，不合格数量（以条/包/支为单位）填写在后面；若属“其他”质量缺陷，首检应直接描述缺陷和数量，过程检验应在“备注”栏内注明缺陷具体名称。<br />
				&nbsp;3.若当次检验合格，则在判断栏内划“√”，“复检”栏内划“-”；若当次检验不合格，则在判断栏内划“×”，并进行复检根据复检结果在“复检栏”划“√”或“×”。<br />
				&nbsp;4.表中结合实际无需填写项目和空余栏均划“-”。
			</font>
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="2"><table width="100.7%" height="286" border="1" cellspacing="0" bordercolor="#666666" style="border-left:hidden; border-collapse:collapse; margin-top:-24px; margin-left:-2px;">
      <tr>
        <td align="center">代码说明</td>
      </tr>
      <tr>
        <td align="left">首检原因选择：①接班开机②班中餐③设备停机超过30分钟（含正常停机和故障停机）④材料换版⑤更换排号⑥轮保后开机⑦其他</td>
      </tr>
      <tr>
        <td align="left">处理措施选择：①机台处理②反馈③报告班长④故障部位修理⑤按不合格品程序处理</td>
      </tr>
      <tr>
        <td align="left">质量缺陷代码：A箱装错装/B箱装少装/C箱装用错胶带/D箱装胶带不牢固/E箱装胶带粘贴不端正/F侧面胶带长短/G箱体变型/H箱面污脏/J箱盖搭口不严/K箱体不方正/L其他。          </td>
      </tr>
      <tr>
        <td valign="top" align="left">其他备注信息：<br />
          <br />
          <br />
          <br />
          <br />
          <br /></td>
      </tr>
    </table></td>
	</tr>
</table>

</div>
</body>
</html>
