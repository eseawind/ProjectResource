<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<title>外观检验</title>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>

<style type="text/css">
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#title{
		font-size: 20px;
		font-weight: bold;
		text-align: center;
		padding-top: 4px;
		background: #b4b4b4;
		color: #3C3C3C;
		border-radius: 0px 4px 0px 0px;
		line-height: 35px;
		height: 40px;
		border-bottom: 2px solid #838383;
	}
	#procMailPlan-seach-box{
		border: 1px solid #9a9a9a;
		width: 821px;
		margin-left: 10px;
		height: 72px;
		margin: 0 auto;		
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	
	
	#procMailPlan-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
	#procMailPlan-tab{		
		width:824px;
		margin: 0 auto;	
		height:auto;
		margin-top:5px;
		font-size:12px;
		font-weight:bold;
		height: 426px;
		border: 1px solid #858484;
		border-radius: 4px;	
	}
	.t-header{
		text-align:center;
	}
	#procMailPlan-tab-thead tr td{
		height:44px;
		text-align:center;
	}
	#procMailPlan-tab-tbody tr td{
		height:39px;
		text-align:center;
	}
	#procMailPlan-page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
		padding-top:4px;
	}
	#up,#down{
		border:1px solid #9a9a9a;
		padding:3px 2px;
		width:70px;
		font-weight:bold;
		font-size:12px;
		cursor:pointer;
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
</style>
<script type="text/javascript">

	var groupTypeFlag="${groupTypeFlag}";
	var group=null;
	var pageIndex=1;
	var allPages=0;
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
		
		var bandParams=function(pageIndex,params){
			$.post("${pageContext.request.contextPath}/wct/selfCheckStrip/getList.do?pageIndex="+pageIndex,params,function(v){				
				var reobj=eval("("+v+")");
				var list=reobj.rows;
				allPages=reobj.total>10?reobj.total/10:1;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.total);
				clearParams();
				$("#procMailPlan-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];							
							tr.find("td:eq(1)").html(revalue.id);//主键 FileID 隐藏
							tr.find("td:eq(2)").html(revalue.checkFlag);//检验程序
							tr.find("td:eq(3)").html(revalue.time);//自检时间
							tr.find("td:eq(4)").html(revalue.mdMatName);//牌号
							tr.find("td:eq(5)").html(revalue.mdTeamName);//班组
							tr.find("td:eq(6)").html(revalue.mdShiftName);//班次
							tr.find("td:eq(7)").html(revalue.mdWorkshopName);//车间
							tr.find("td:eq(8)").html(revalue.mdEquipmentName);//设备
							tr.find("td:eq(9)").html(revalue.uName);//操作工
							tr.find("td:eq(10)").html((revalue.cb=='0'?"√":"x"));//错包
							tr.find("td:eq(11)").html((revalue.sb=='0'?"√":"x"));//少包
							tr.find("td:eq(12)").html((revalue.ps=='0'?"√":"x"));//破损
							tr.find("td:eq(13)").html((revalue.zt=='0'?"√":"x"));//粘贴
							tr.find("td:eq(14)").html((revalue.wz=='0'?"√":"x"));//污渍
							tr.find("td:eq(15)").html((revalue.dz=='0'?"√":"x"));//倒装
							tr.find("td:eq(16)").html((revalue.tmz=='0'?"√":"x"));//透明纸
							tr.find("td:eq(17)").html((revalue.lx=='0'?"√":"x"));//拉线
						}
				});
			});
		};
		
		$("#up").click(function(){
			if(pageIndex<=1){
				return;
			}
			pageIndex=pageIndex-1;
			bandParams(pageIndex,params);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPages){
				return;
			}
			pageIndex=pageIndex+1;
			bandParams(pageIndex,params);
		});
		//初始化
		$.loadSelectData($("#mdShiftBc"),"SHIFT",true);//加载下拉框数据
		$.loadSelectData($("#mdEquipmentSb"),"ALLEQPS",true);//加载下拉框数据
		$.loadSelectData($("#mdWorkshopCj"),"WORKSHOP",true);//加载下拉框数据
		//初始化时间
	    var today = new Date();
		var month=today.getMonth()+1;
		if(month<10){month=("0"+month);}
		var day=today.getDate();
		if(day<10){day=("0"+day);}
	    var date=today.getFullYear()+"-"+month+"-"+day;
	    $("#startTime").val(date);	//时间用这个
		$("#endTime").val(date);	//时间用这个
		//end
		
		$("#procMailPlan-search").click(function(){
			params=getJsonData($('#procMailPlan-wct-frm'));
			bandParams(1,params);
		});
		
		$("#procMailPlan-reset").click(function(){
			params={};
			$("#procMailPlan-wct-frm input[type!=button]").val(null);
			$("#mdShiftBc").val( "");//下拉框赋值
	        $("#mdEquipmentSb").val( "");//下拉框赋值
	        $("#mdWorkshopCj").val( "");//下拉框赋值
	        $("#startTime").val(date);	//时间用这个
			$("#endTime").val(date);	//时间用这个
		});
		

		
		var clearParams=function(){
			$("#procMailPlan-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(1)").html(null);//主键 FileID 隐藏
					tr.find("td:eq(2)").html(null);//检验程序
					tr.find("td:eq(3)").html(null);//自检时间
					tr.find("td:eq(4)").html(null);//牌号
					tr.find("td:eq(5)").html(null);//班组
					tr.find("td:eq(6)").html(null);//班次
					tr.find("td:eq(7)").html(null);//车间
					tr.find("td:eq(8)").html(null);//设备
					tr.find("td:eq(9)").html(null);//操作工
					tr.find("td:eq(10)").html(null);//错包
					tr.find("td:eq(11)").html(null);//少包
					tr.find("td:eq(12)").html(null);//破损
					tr.find("td:eq(13)").html(null);//粘贴
					tr.find("td:eq(14)").html(null);//污渍
					tr.find("td:eq(15)").html(null);//倒装
					tr.find("td:eq(16)").html(null);//透明纸
					tr.find("td:eq(17)").html(null);//拉线
			});
		};
	});
	
</script>

<body>
	<div id="title">外观检验</div>
	<div id="procMailPlan-seach-box" >
		<form id="procMailPlan-wct-frm">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>		
					<td>开始日期：</td>
					<td>
						<input type="text" id="startTime" name="time" class="mh_date" readonly="readonly" style="width:160px;height:20px;"/>
					</td>
					<td>结束日期：</td>
					<td>
						<input type="text" id="endTime" name="endTime" class="mh_date" readonly="readonly" style="width:160px;height:20px;"/>
					</td>
					<td>班次：</td>
					<td>
						<select style="width:110px;height:20px;" class="easyui-combobox"  id="mdShiftBc" name="mdShiftId" ></select>
					</td>
				</tr>
				<tr><td colspan="6"  height="6px"></td></tr>
				<tr>
					<td>车间：</td>
					<td>
						<select style="width:110px;height:20px;" class="easyui-combobox" id="mdWorkshopCj" name="mdWorkshopId" ></select>
					</td>
					<td>机台(设备)：</td>
					<td>
						<select style="width:110px;height:20px;" class="easyui-combobox" id="mdEquipmentSb" name="mdEquipmentId" ></select>
					</td>
					<td>
						<input type="button" id="procMailPlan-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/>
					</td>
					<td>
						<input type="button" id="procMailPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="procMailPlan-tab" style="height:436px;overflow:auto;">
		<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="824" height="439" cellspacing="0" cellpadding="0">
			<thead id="procMailPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
				<tr>
					<td class="t-header" style="width:30px"></td>
					<td class="t-header" style="width:10px;display:none"></td>
					<td class="t-header" style="width:90px">检验程序</td>
					<td class="t-header" style="width:200px">自检时间</td>
					<td class="t-header" style="width:200px">牌号</td>
					<td class="t-header" style="width:60px">班组</td>
					<td class="t-header" style="width:60px">班次</td>
					<td class="t-header" style="width:110px">车间</td>
					<td class="t-header" style="width:110px">设备</td>
					<td class="t-header" style="width:110px">操作工</td>
					<td class="t-header" style="width:60px">错包</td>
					<td class="t-header" style="width:60px">少包</td>
					<td class="t-header" style="width:60px">破损</td>
					<td class="t-header" style="width:60px">粘贴</td>
					<td class="t-header" style="width:60px">污渍</td>
					<td class="t-header" style="width:60px">倒装</td>
					<td class="t-header" style="width:60px">透明纸</td>
					<td class="t-header" style="width:60px">拉线</td>
				</tr>
			</thead>
			<tbody id="procMailPlan-tab-tbody">
				<tr>
					<td >1</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >2</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >3</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >4</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >5</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >6</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >7</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >8</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >9</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				<tr>
					<td >10</td><td style="display:none"></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
					<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="procMailPlan-page">
		共<span id="count">0</span>条数据
		<input id="up" type="button"  value="上一页" class="btn btn-default"/>
		<span id="pageIndex">0</span>/<span id="allPages">0</span>
	    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
	</div>

</body>
</html>