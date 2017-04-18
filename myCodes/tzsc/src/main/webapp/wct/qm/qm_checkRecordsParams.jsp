<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<title>产品规程</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>

<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>

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
	#prodMailPlan-seach-box{
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
	
	
	#prodMailPlan-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
	#prodMailPlan-tab{		
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
	#prodMailPlan-tab-thead tr td,#prodMailPlan-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#prodMailPlan-page{
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
	$(function(){
		var id="${param.pid }";
		var bandParams=function(){
			$.post("${pageContext.request.contextPath}/wct/qm/qmRecord/getDeailedList.do?id="+id,function(v){				
				var reobj=eval("("+v+")");
				var list=reobj.rows;
				allPages=reobj.total;
				for(var i=0;i<allPages;i++){
					var html="<tr><td>"+(i+1)+"</td><td></td><td></td><td></td><td></td></tr>";
					$('#prodMailPlan-tab-tbody').append(html);
				}
				$("#prodMailPlan-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];	
							console.info(revalue);
							tr.find("td:eq(1)").html(revalue.paDes);//名称
							tr.find("td:eq(2)").html(revalue.paTypeCode);//类别
							tr.find("td:eq(3)").html(revalue.valueF);//数量
							tr.find("td:eq(4)").html(revalue.scDeduct);//扣分
						}
				});
			});
		};
		bandParams();
	});
	
</script>
</head>
<body>
	<!--信息数据 div -->
	<div id="table_mes_div">
	 
		<div id="prodMailPlan-tab" style="height:472px;overflow:auto;">
			<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="100%" height="100%" cellspacing="0" cellpadding="0">
				<thead id="prodMailPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
					<tr>
						<td class="t-header" style="width:10%"></td>
						<td class="t-header" style="width:30%">名称</td>
						<td class="t-header" style="width:20%">类别</td>
						<td class="t-header" style="width:20%">数量</td>
						<td class="t-header" style="width:20%x">扣分</td>
					</tr>
				</thead>
				<tbody id="prodMailPlan-tab-tbody">
				 <%--  <input type="hidden" name="pid" id="pid" value="${param.pid }"/>   --%>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
	
	
