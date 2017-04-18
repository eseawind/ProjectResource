<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>工段日报表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>

<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>


<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>

<style type="text/css">
   body{
     background:none;
   }
   *{
     font-family: "Microsoft YaHei","宋体",Arial;
   }
   #eqpMailPlan-title{
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
	#eqpMailPlan-seach-box{
		border: 1px solid #9a9a9a;
		width: 821px;
		margin-left: 10px;
		height: 36px;
		margin: 0 auto;		
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	#eqpMailPlan-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
	#eqpMailPlan-tab{		
		width:824px;
		margin: 0 auto;	
		height:auto;
		margin-top:5px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
		border: 1px solid #858484;
		border-radius: 4px;		
	}
	.t-header{
		text-align:center;
	}
	#eqpMailPlan-tab-thead tr td,#eqpMailPlan-tab-tbody tr td{
		/* padding:7px; */
		height:20px;
		text-align:center;
	}
	#eqpMailPlan-page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
		padding-top:4px;
	}
	#details{
		border:2px solid #dddddd;
		width:96%;
		margin-left:10px;
		height:80px;
		margin-top:5px;
		padding:2px;
		text-indent:15px;
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
		height:28px;width:50px;
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

	#wkd-eqp-msg-title{
		font-size: 12px;font-weight: bold;
		text-align:left;
		background: #DEDCDA;
		border-bottom: 1px solid #838383;
	}
</style>

<script type="text/javascript" >
	$(function(){
		
		//页面进来，给时间空间赋值当天日期
		var mydate = new Date();
		var year=mydate.getFullYear();
		var month=mydate.getMonth()+1;
		var day=mydate.getDate();
		var str=year+"-"+retType(month)+"-"+retType(day);
		//将日期赋值给时间控件
		$("input[name='runtime']").attr("value",str);
		$("input[name='endtime']").attr("value",str);
		
		//判断日期是否小于10，如果小于10前面加0返回
		function retType(val){
			if(val>=10){
				return val;
			}else{
				return "0"+val;
			}
		}
		
	});
	
	 //工作内容
	var contextValue = {};
	var groupTypeFlag="${groupTypeFlag}";
	var isCzg = "false";
	var isLbg = "false";
	var jsType = "";//这里牵扯到 到哪个 session取值
	var bandParams;
	var pageIndex=1;
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
		
		/*查询*/
		var allPages=0;
		var params={};
		bandParams=function(pageIndex,params){
			params.eqpCode="${loginInfo.equipmentCode}";//设备编号/wct/stat/eqpDailyInfo  	/pms/sch/statjbcj/queryDayQtyData
			$.post("${pageContext.request.contextPath}/wct/stat/eqpDailyInfo.do?pageIndex="+pageIndex,params,function(reobj){
				var list=reobj;
				allPages=reobj.total%10==0?parseInt(reobj.total/10):parseInt(reobj.total/10)+1;
				$("#pageIndexs").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.total);
				clearParams();
				$("#eqpMailPlan-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							tr.find("td:eq(1)").html(revalue.mat_name);//名称
							tr.find("td:eq(2)").html(revalue.juQty); //产量
							tr.find("td:eq(3)").html(revalue.bzQty);//
							tr.find("td:eq(4)").html(revalue.lbDH);//开始日期
							tr.find("td:eq(5)").html(revalue.pzDH);//结束日期
							tr.find("td:eq(6)").html(revalue.sszDH);
							tr.find("td:eq(7)").html(revalue.xhzDH);
							tr.find("td:eq(8)").html(revalue.thzDH);
							tr.find("td:eq(9)").html(revalue.lbzDH);
							
						}
				});
			},"JSON");
		};
		
		var clearParams=function(){
			$("#eqpMailPlan-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3)").html(null);
					tr.find("td:eq(4)").html(null);
					tr.find("td:eq(5)").html(null);
					tr.find("td:eq(6)").html(null);
					tr.find("td:eq(7)").html(null);
					tr.find("td:eq(8)").html(null);
					tr.find("td:eq(9)").html(null);
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
			pageIndex++;
			bandParams(pageIndex,params);
		});
		pageIndex=1;
		params=getJsonData($('#eqpMailPlan-wct-frm'));
		bandParams(pageIndex,params);

		//查询按钮
		$("#eqpMailPlan-search").click(function(){
			params=getJsonData($('#eqpMailPlan-wct-frm'));
			pageIndex=1;
			bandParams(pageIndex,params);
		});
		
		$("#eqpMailPlan-reset").click(function(){
			$("#eqpMailPlan-wct-frm input[type!=button]").val(null);
		});
		clearParams();
	});
	
</script>
</head>
<body>
<div id="eqpMailPlan-title">机台工段日报</div>
<!-- 用户登录窗口 -->
<%-- <jsp:include page="eqmLogin.jsp"></jsp:include> --%>

<div id="eqpMailPlan-seach-box" >
	<form id="eqpMailPlan-wct-frm">
		<table width="70%" cellspacing="0" cellpadding="0">
			<tr>	
			<td>
			       工单时间
			</td>
			<td>
			   <input  type="text" name="runtime" class="mh_date" readonly="readonly" value="" style="width:110px;height:27px;"/>
			</td>
			<th style="width:76px;text-align:center;">班组</th>
			<td style="width: 120px;">
				<select name="teamId" id="teamId" style="height:20px;">
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>
			</td>			
			<td>
				<input type="button" id="eqpMailPlan-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/>
			</td>
			<!-- <td> &nbsp;&nbsp;<input type="button" id="eqpMailPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td> -->
			
			</tr>
		</table>
	</form>
</div>
<div id="eqpMailPlan-tab" style="height:448px;overflow:auto;">
	<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="824" height="450" cellspacing="0" cellpadding="0">
		<thead id="eqpMailPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header" style="width:50px">机台号</td>
				<td class="t-header" style="width:100px">牌号</td>
				<td class="t-header" style="width:80px">卷烟产量</td>
				<td class="t-header" style="width:80px">包装产量</td>
				<td class="t-header" style="width:80px">滤棒单耗</td>
				<td class="t-header" style="width:60px">卷烟纸单耗</td>
				<td class="t-header" style="width:60px">接装纸单耗</td>
				<td class="t-header" style="width:60px">商标纸单耗</td>
				<td class="t-header" style="width:60px">条盒单耗</td>
				<td class="t-header" style="width:60px">铝箔纸单耗</td>
			</tr>
		</thead>
		<tbody id="eqpMailPlan-tab-tbody">
			<tr>
				<td>1</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>3</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>5</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>6</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>7</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>8</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>9</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>11</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>12</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>
<!-- <div id="eqpMailPlan-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页" class="btn btn-default"/>
	<span id="pageIndexs">0</span>/<span id="allPages">0</span>
    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
</div> -->

<div id="mat_system"  style="display:none;">
	<div >
		<iframe id="insertJsp" frameborder="0"  style="height: 522px;width: 818px;padding-top: -20px;"></iframe>				
	</div>
	<div>
		<input style="margin-left:384px;height:40px;width:100px;" id="closeJsp" onclick="closePage();" type="button"  value="关闭" class="btn btn-default" />
	</div>
 </div>
 

</body>
</html>