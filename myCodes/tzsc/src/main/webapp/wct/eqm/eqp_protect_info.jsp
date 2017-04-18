<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设备日保管理</title>
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
		height:40px;
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
    var cid="${cid}";
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
		
		//下拉框
		$.loadSelectData($("#eqpId"),"ALLROLLERS",false);
		$("#eqpId").append("<option value=''>全部</option>");
		/*查询*/
		var allPages=0;
		var params={};
		var i=0;
		bandParams=function(pageIndex,params){
			params.categoryId=cid;
			$.post("${pageContext.request.contextPath}/wct/eqm/checkplan/querySysEqpType.do?pageIndex="+pageIndex,params,function(reobj){
				var list=reobj.rows;
				allPages=reobj.total%10==0?parseInt(reobj.total/10):parseInt(reobj.total/10)+1;
				$("#pageIndexs").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.total);
				clearParams();
				//var i=1;
				$("#eqpMailPlan-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							//alert(revalue.id);
							tr.find("td:eq(1)").html(revalue.code);//编号
							tr.find("td:eq(2)").html(revalue.name); //保养项
							tr.find("td:eq(3)").html(revalue.des); //备注说明
							tr.find("td:eq(4)").html("<input style='width:20px;height:20px;' name='checkbox' id='checkbox' type='checkbox' value='"+revalue.id+"' />");//班组
						}
				});
				$("[name='checkbox']").attr("checked",'true');//全选
			},"JSON");
			//console.info(contextValue);
		};
		
		var clearParams=function(){
			$("#eqpMailPlan-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3)").html(null);
					tr.find("td:eq(4)").html(null);
					tr.find("td:eq(5)").html(null);
			});
		};
		
		$("#up").click(function(){
			if(pageIndex<=1){
				return;
			}
			$("#selBoxAll").attr("onclick","selBoxAll('1')");
			$("#selBoxAll").text("全选择");
			pageIndex=pageIndex-1;
			bandParams(pageIndex,params);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPages){
				return;
			}
			$("#selBoxAll").attr("onclick","selBoxAll('1')");
			$("#selBoxAll").text("全选择");
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


	
	
	//设备日保标识
	function addEqpProtect(id,szTime){
		//判断用户是否登陆 
		var uname=$("#sbUserName").text();
		var userId=$("#userId:hidden").val();
		//var equipmentId=$("#equipmentId").val();
		if(uname==""){
			jAlert("请登录后再操作！");
			return false;
		}
		if(userId==""){
		   jAlert("请注销后再登录！");
		   return false;	
		}
		
		var url="${pageContext.request.contextPath}/wct/eqm/checkplan/addEqpProtect.do";
		$.post(url,{id:id,uname:uname,userId:userId,szTime:szTime},function(json){
			if(json.isSuccess){
				params=getJsonData($('#eqpMailPlan-wct-frm'));
				bandParams(pageIndex,params);
				//操作成功，修改确定按钮为"已完成"
				jAlert("提交成功！", '系统提示');
			}else{
				jAlert("提交失败！", '系统提示');
			}
		},"JSON");
	}
	
	//全选  1=全选   2-全取消
	function selBoxAll(id){
		if(id=='1'){
			$("[name='checkbox']").attr("checked",'true');//全选
			$("#selBoxAll").text("全取消");
			$("#selBoxAll").attr("onclick","selBoxAll('2')");
		}else{
			$("[name='checkbox']").removeAttr("checked");//取消全选 
			$("#selBoxAll").text("全选择");
			$("#selBoxAll").attr("onclick","selBoxAll('1')");
		}
	}
	//关闭
	function clocePage(){
		window.location.href="${pageContext.request.contextPath}/wct/eqm/eqp_protect.jsp";
	}
	
	//改变状态，并将详细记录插入到历史表中--张璐 
	function saveAllSel(){
		var id_check="";
		var id_nocheck="";
		$(":checkbox").each(function(){
		    if(this.checked == true){//选中得
		    	//alert(this.value);
		    	id_check+=","+this.value;    
		    }else{//没有选中的
		    	//alert(this.value);
		 	    id_nocheck+=","+this.value;
		    }
		});
		//alert(id_check+"左边没选中，右边选中"+id_nocheck);
		var url="${pageContext.request.contextPath}/wct/eqm/checkplan/addEqpProtect.do";
		$.post(url,{id:"${id}",szTime:"${szTime}",id_check:id_check,id_nocheck:id_nocheck},function(json){
			if(json.isSuccess){
			/* 	params=getJsonData($('#eqpMailPlan-wct-frm'));
				bandParams(pageIndex,params); */
				//操作成功，修改确定按钮为"已完成"
				jAlert("提交成功！", '系统提示');
				setTimeout(function(){
					clocePage();
				},400)
				
			}else{
				jAlert("提交失败！", '系统提示');
			}
		},"JSON");
		
	}
    
</script>
</head>
<body>
<div id="eqpMailPlan-title">保养详细</div>
<!-- 用户登录窗口 -->
<jsp:include page="eqmLogin.jsp"></jsp:include>

<div id="eqpMailPlan-tab" style="height:478px;overflow:auto;">
	<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="820" height="450" cellspacing="0" cellpadding="0">
		<thead id="eqpMailPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header" style="width:35px;">序号</td>
				<td class="t-header" style="width:25px;">编号</td>
				<td class="t-header" >保养项</td>
				<td class="t-header" >备注说明</td>
				<td class="t-header" style="width:55px;">操作</td>
			</tr>
		</thead>
		<tbody id="eqpMailPlan-tab-tbody">
			<tr>
				<td>1</td>
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
			</tr>
			<tr>
				<td>3</td>
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
			</tr>
			<tr>
				<td>5</td>
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
			</tr>
			<tr>
				<td>7</td>
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
			</tr>
			<tr>
				<td>9</td>
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
			</tr>
		</tbody>
	</table>
</div>
<br/>
<br/>
<div style="text-align: center;">
  <span style="float: left;margin-left:280px;">
    <button  onclick="saveAllSel();" style="height:35px;width:80px;" class="btn btn-default">
		完成
	</button>	
	<button  onclick="clocePage();" style="height:35px;width:80px;" class="btn btn-default">
		取消
	</button>
	 <button  id="selBoxAll" onclick="selBoxAll('1');" style="height:35px;width:80px;" class="btn btn-default">
		全选择
	</button>
  </span>

  <span style="float: right;margin-right:5px;"><br/>
	           共<span id="count">0</span>条数据
		<input id="up" type="button"  value="上一页" class="btn btn-default"/>
		<span id="pageIndexs">0</span>/<span id="allPages">0</span>
	    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
  </span>

</div>
 
</body>
</html>