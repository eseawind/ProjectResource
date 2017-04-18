<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>设备故障信息</title>
<meta name="author" content="leejean">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<style type="text/css"> 
body{
background-color: #F4F4F4;
}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#hisBadQty-title{
		font-size: 20px;
		font-weight: bold;
		text-align: center;
		padding-top: 4px;
		background: #F4F4F4;
		color: #F4F4F4;
		border-radius: 0px 4px 0px 0px;
		line-height: 35px;
		height: 40px;
		border-bottom: 2px solid #F4F4F4;
	}
	#hisBadQty-seach-box{
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
	
	#search_form td{
		font-size:14px;
	}
	#hisBadQty-tab{		
		width:97%;
		margin-left:10px;
		height:auto;
		margin-top:5px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
	}
	.t-header{
		text-align:center;
	}
	#hisBadQty-tab-thead tr td,#hisBadQty-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#hisBadQty-page{
		width:97%;
		margin-left:10px;
		margin-top:5px;
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
	#up,#down,.od{
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
.node ul{ 
margin-left:30px; 
} 
.node .node{ 
display:none; 
} 

.ce_ceng_close{ 
background:url(img/cd_zd1.png) left  no-repeat; 
padding-left: 15px; 
} 
.ce_ceng_open{ 
background:url(img/cd_zd.png) left  no-repeat; 
} 
</style>

<script type="text/javascript">
	var id_=${param.id};
	var type_=${param.type};
	var trouble_name="";
	var description="";
	var useNum="";
	var html="";
	var html1="";
	var html2="";
	 $(function(){
		$("#area").val(null);
		$("#showAddFixResult").html(null);
		$.post("${pageContext.request.contextPath}/pms/trouble/queryTroubleInfo.do",function(list){
			list=list.substring(1,list.length-1);
			$("#all").html(list);
			$(".tree").each(function(index, element) { 
				if($(this).next(".node").length>0){ 
					$(this).addClass("ce_ceng_close"); 
				} 
			}); 
			$(".tree").click(function(e){ 
				var ul = $(this).next(".node"); 
				if(ul.css("display")=="none"){ 
					ul.slideDown(); 
					$(this).addClass("ce_ceng_open"); 
					ul.find(".ce_ceng_close").removeClass("ce_ceng_open"); 
				}else{ 
					ul.slideUp(); 
					$(this).removeClass("ce_ceng_open"); 
					ul.find(".node").slideUp(); 
					ul.find(".ce_ceng_close").removeClass("ce_ceng_open"); 
				} 
			}); 
		});
		$.post("${pageContext.request.contextPath}/wct/overhaul/queryJX.do?id="+id_,function(data){
			$("#area3").val(data.contents);
			$("#area6").val(data.note);
			$("#buwei").val(data.place);
		},"Json");
	});
	 var codetest=[];
	 var nametest=[];
	 var name="";
 //向文本域中添加选中的故障信息
 	function save(code,des){
 		if(code!=codetest[0]){
 			codetest[0]=code;
 			var areaV=$("#area").val();
 	 		areaV=areaV+"\r"+des+"\r";
 	 		$("#area").val(areaV);
 	 		if(name!=nametest[0]){
 	 			nametest[0]=name;
 	 			name=name+"\r"+code+"\r";
 	 		}
 	 		//name=code;
 		}else{
 	 		jAlert("数据重复，请勿重复添加");
 		}
 		
 	}
 	function show(){
 		$("#bjType").val(null);
		$("#remarkName").val(null);
		$("#remark").val(null);
 		$("#hid_repair_div").css("display","block");
 		showEqp();
 	}
 	
 	function queryTroubleInfo(){
 		var troubleName=$("#troubleName").val();
 		//var troubleCode=$("#troubleCode").val();
 		$.post("${pageContext.request.contextPath}/pms/trouble/queryTroubleInfo.do",{troubleName:troubleName});
 	}
 	
 	 $(function(){
 		var url="${pageContext.request.contextPath}/wct/eqm/eqp_lb_jsp.jsp";
 		$("#zlid").attr("href",url);
 	}) 
 	
 	var fourcode;
 	var fourid;
 	var pcode;
 	//当添加新的故障时，查询到此故障下有多少条原因，然后code+1，生成新的故障代码
 	function addNewCode(parentcode,code,id){
 		fourid=id;
 		fourcode=code;
 		pcode=parentcode;
 	}
 	
 	//添加新故障信息
 	function addNewTrouble(){
 		var des = $("#areaNew").val();
 		des=$.trim(des);
 		$.post("${pageContext.request.contextPath}/pms/trouble/addNewTrouble.do",{parent_id:fourid,description:des},function(data){
 			$("#areaNew").val("");
 			data=data.substring(1,data.length-1);
 			if(data!=null&&data!=""){
 				var newcode=pcode+"-"+fourcode+"-"+data;
 	 	 		save(newcode,des);	
 			}
 		});
 		 
 	}
 	
 	function queryJX(){
 		$.post("${pageContext.request.contextPath}/wct/overhaul/queryJX.do?id="+id_,function(data){
			$("#area3").val(data.contents);
			$("#area6").val(data.note);
			$("#buwei").val(data.place);
		},"Json");
 	}
 	 
 	//修改检修
 	function updateStatus(){
 		var con = $("#area3").val();
 		var note = $("#area6").val();
 		if(con!=null&&con!==""){
 			jConfirm('是否确认修改?', '提示', function(r) {
 				if(r){
 					$.post("${pageContext.request.contextPath}/wct/overhaul/updateStatus.do",{id:id_,area3:con,area6:note},function(returnData){
 	 					var arr=eval("["+returnData+"]");
 	 					if(!arr[0].success){
 	 						jAlert(arr[0].msg,"提示");
 	 					}else{
 	 						//jAlert("数据修改成功！","提示");
 	 						$.post("${pageContext.request.contextPath}/wct/overhaul/updateStatusTrouble.do",{id:id_});
 	 						window.location.href="${pageContext.request.contextPath}/wct/eqm/eqpOverhaul.jsp";
 	 					}
 	 				});  
 				}
 			})
 			
 		}else{
 			jAlert("请输入检修项目！","提示");
 		}
	}
 	queryJX();
 	
 	//备品备件添加记录后查询
 	var spare_name=new Array();
 	var spare_code=new Array();
 	var Num=new Array();
 	function showAddFixResult(tf,flag){
 		//$("#showAddFixResult").html(null);
 		
 		if(tf!=0&&flag==0){//手动添加
 			trouble_name=trouble_name.substring(1);
 			description=description.substring(1);
 			useNum=useNum.substring(1);
 			spare_name=trouble_name.split(",");
			spare_code=description.split(",");
			Num=useNum.split(",");
			for(var co=0;co<tf;co++){
				html1+="<tr><td width=10%>"+spare_name[co]+"</td><td width=10%>"+spare_code[co]+"</td><td width=10%>"+Num[co]+"</td></tr>"
			}
 		}else if(flag==1&&tf==0){//备品备件表中选取
 			/* console.info(value);
 			console.info(checkValue);
 			console.info(spareNmae);
 			console.info(sparetype); */
 			for(var cc=0;cc<checkValue.length;cc++){
 				if(checkValue[cc]!="0"){
 					spare_name[cc]=spareNmae[cc];
 					spare_code[cc]=sparetype[cc];
 					Num[cc]=checkValue[cc];
 				}
 			}
 			for(var co=0;co<Num.length;co++){
 				if(Num[co]!=undefined){
 					html1+="<tr><td width=10%>"+spare_name[co]+"</td><td width=10%>"+spare_code[co]+"</td><td width=10%>"+Num[co]+"</td></tr>"
 				}
			}
 		}else if(flag==1&&tf!=0){
 			var spare_name2=new Array();
 		 	var spare_code2=new Array();
 		 	var Num2=new Array();
 		 	for(var cc=0;cc<checkValue.length;cc++){
 				if(checkValue[cc]!=0){
 					spare_name2[cc]=spareNmae[cc];
 					spare_code2[cc]=sparetype[cc];
 					Num2[cc]=checkValue[cc];
 				}
 			}
 		 	trouble_name=trouble_name.substring(1);
 			description=description.substring(1);
 			useNum=useNum.substring(1);
 			spare_name=trouble_name.split(",");
			spare_code=description.split(",");
			Num=useNum.split(",");
			for(var co=0;co<tf;co++){
				html2+="<tr><td width=10%>"+spare_name[co]+"</td><td width=10%>"+spare_code[co]+"</td><td width=10%>"+Num[co]+"</td></tr>"
			}
			
			for(var coo=0;coo<Num2.length;coo++){
 				if(Num2[coo]!=undefined){
 					html1+="<tr><td width=10%>"+spare_name2[coo]+"</td><td width=10%>"+spare_code2[coo]+"</td><td width=10%>"+Num2[coo]+"</td></tr>"
 				}
			}
 		}
		html="<table>"
			+"<tr><td width=10%>名称</td><td width=10%>型号</td><td width=10%>更换数量</td></tr>"
			+" "+html1+" "+html2+" "
			+"</table>";
		$("#showAddFixResult").append(html);
		trouble_name="";
		description="";
		useNum="";
		spare_name=new Array();
	 	spare_code=new Array();
	 	Num=new Array();
 	}
</script>
</head>
<body >
<div id="hisBadQty-title"></div>
<c:if test="${param.type==1}">
		<jsp:include page="eqp_lb_changeEqpUnit.jsp"></jsp:include>
</c:if>
<c:if test="${param.type==5}">
		<jsp:include page="eqp_jx_changeEqpUnit.jsp"></jsp:include>
</c:if>
<c:if test="${param.type==3}">
		<jsp:include page="eqp_lb_repair.jsp"></jsp:include>
</c:if>
<c:if test="${param.type==2}">
		<jsp:include page="eqp_lb_changeEqpUnit.jsp"></jsp:include>
</c:if>
<br><br>
<div>
<table style="margin-left:8%">
	<tr>
		<td><font size="5"><b>检修部位</b></font></td>
		<td><font size="5"><b>检修项目</b></font></td>
		<td><font size="5"><b>注意事项</b></font></td>
	</tr>
	<tr>
		<td><textarea id="buwei" rows="3" cols="21"  readonly="readonly" style="background-color: #F4F4F4; font-size:20px;"></textarea></td>
		<td><textarea id="area3" rows="3" cols="21" style="background-color: #F4F4F4; font-size:20px;"></textarea></td>
		<td><textarea id="area6" rows="3" cols="21" style="background-color: #F4F4F4; font-size:20px;"></textarea></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td><input type="button" id="update" onclick="updateStatus();" value="确认" style="background-color: #8F8F8F; width:100px;" class="btn btn-default"></td>
		<td><a href="javascript:void(0);" onclick="show()"><input type="button"  value="备品备件更换" style="background-color: #8F8F8F; width:100px;" class="btn btn-default"/></a></td>
		<td><a href="${pageContext.request.contextPath}/wct/eqm/eqpOverhaul.jsp"><input type="button"  value="返回" style="background-color: #8F8F8F; width:100px;" class="btn btn-default"/></a></td>
	</tr>
</table>
</div>
<div id="showAddFixResult" align="center" style="margin-top:30px; height:280px; overflow: auto;">
</div>
</body>
</html>
