<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>包装外观巡检</title>
<jsp:include page="../../js/initjsp/initEasyui.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.js" type="text/javascript"></script>
<link media="screen" href="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.css" type="text/css" rel="stylesheet" ><!-- Example script -->
 
 
<jsp:include page='../../js/initjsp/initKeyboard.jsp' ></jsp:include>
<!-- wctAlert -->
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.js" type="text/javascript"></script>

<link media="screen" href="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#juanjie-title{
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
	#juanjie-seach-box{
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
	#juanjie-wct-frm td{
		font-size:14px;
	}
	#juanjie-tab{		
		width:817px;
		margin-left:10px;
		margin-top:15px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
		overflow:auto;
		background-color:#DDDDDD;
		
	}
	#juanjie-page{
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
		height:30px;
		width:60px;
}
.btn-default:hover{
color: #333333;
background-color: #ebebeb;
border-color: #adadad;
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
#juanjieSta-tab {
width: 98.5%;
height: auto;
font-size: 12px;
font-weight: bold;
height: 505px;
margin: 5 auto;
border-radius: 4px;
}
.datagrid-toolbar,
.datagrid-pager {
  background: #5a5a5a !important;
}
.icon-save {background: url('../img/filesave.png') no-repeat left center !important;}
.icon-save:hover {background: url('../img/filesave_h.png') no-repeat left center !important;}
.icon-remove {background: url('../img/edit_remove.png') no-repeat left center !important;}
.icon-remove:hover {background: url('../img/edit_remove_h.png') no-repeat left center !important;}
.icon-add {background: url('../img/edit_add.png') no-repeat left center !important;}
.icon-add:hover {background: url('../img/edit_add_h.png') no-repeat left center !important;}
a.l-btn {color: #fff !important;}
a.l-btn:hover {color: #5a5a5a !important;}
.panel-header, .panel-body {
border-width: 0px;
border-style: none;
}

.datagrid-view {
background: #dddddd;
}
.datagrid-header td, .datagrid-body td, .datagrid-footer td {
border-color: #9a9a9a !important;
}
.messager-window {
    position: absolute !important;
}
.combo, .combo-panel {
background-color: white;
}

/*密码验证开始*/	
.password_botton{width: 200px;height: auto;font-size: 12px;font-weight: bold;text-align: center;padding-top: 4px;margin: 0 auto;}
.btn-default {color: #FFFFFF;background-color: #5A5A5A;border-color: #cccccc;}
.password_box{display: block;z-index: 99;position: absolute;
top: 80px;left: 170px;width: 230px;font-size: 12px;
border: 1px solid #858484;border-radius: 4px;
height: 160px;
background:#A0A0A0;padding: 30px;}
.input-group {margin-bottom: 15px;}
#username,#password{width:200px;}
.ui-keyboard-preview{width:92%;}
.form-control{height:20px;}
/*密码验证结束*/	

.input-group-addon {
font-size:14px;
}
</style>
<script type="text/javascript">
		var selfchk=null;
		var selfchk_det=null;
		var isLogin="${isLogin}";
		var userName="${userName}";
		var editRowIndex=-1;
		var editRowIndexForbatch=-1;
		var wkorderCod;
		$(function(){
			if(isLogin==1){
				$("#loginEvent span span").html("注销质检员登录");
				$("#curUserName").html(userName);
			}else{
				$("#loginEvent span span").html("质检员登录");
				$("#curUserName").html("暂未登录");
			}
			$('.keyboard').keyboard();
			selfchk=$("#selfchk").datagrid({	
				columns:[[{
				field : 'id',
				title : 'id',
				width : 100,
				hidden:true
			},{
				field : 'wkcod',
				title : '工单',
				width : 100,
				hidden:true
			},{
				field : 'batch',
				title : '检验批次',
				width : 110,
				align : 'center'
			}, {
				field : 'time',
				title : '检验时间',
				width : 130,
				align : 'center'
			}, {
				field : 'maker',
				title : '质检员',
				width : 100,
				align : 'center'
			}
				]],
		        toolbar:"#selfchk_toolbar",
		rownumbers : true,
		fitColumns : false,							
		border : false,
		singleSelect : true,
		fit : true,
		border : false,
		idField : 'id',
		onClickRow : function(i,rowData) {
			    selectItmByBatch(0);
			    editRowIndex=-1;
		}

	});
			selfchk_det=$("#selfchk_det").datagrid({	
				frozenColumns:[[
							{
								field : 'id',
								title : 'id',
								hidden:true
							}, {
								field : 'itmId',
								title : 'itmid',
								hidden:true
							}, {
								field : 'cod',
								title : '编码',
								width : 55,
								align : 'center'
							}, {
								field : 'pos',
								title : '位置',
								width : 90,
								align : 'center'
							},{
								field : 'lvl',
								title : '等级',
								width : 40,
								align : 'center'
							}, {
								field : 'name',
								title : '名称',
								width : 180,
								align : 'center'
							}, {
								field : 'val',
								title : '缺陷数',
								width : 40,
								align : 'center',
								editor:{
							            type:'numberbox',
							            options:{min:0,  
							                precision:0}
							    } 
							}
								]],
						toolbar:"#selfchk_det_toolbar",
						rownumbers : true,
						fitColumns : true,							
						border : false,
						singleSelect : true,
						fit : true,
						width:500,
						border : false,
						idField : 'id'
							,
							onClickRow:function(i,rowdata){
								if(isLogin!=1){
									jAlert("请先使用质检员用户名登录.","提示");
									return;
								}
								if(editRowIndex!=-1){					
									selfchk_det.datagrid("endEdit",editRowIndex);
								}
								selfchk_det.datagrid("beginEdit",i);
								editRowIndex=i;
								$(".datagrid-editable-input").keyboard({
									layout:"1234567890.",
									customLayout: {
										'default': [
											"1 2 3 4 5 6 {bksp}",
											"7 8 9 0 . - {accept}"
										]
									}
								});
								//$(".ui-keyboard-has-focus").css("position","absolute").css("left","150px").css("top","250px");								
							}

					});
			
			 var loadRollerWkOrder=function(){
	     	    	var rid="${loginGroup.eqps['packer'].id}";
	     	    	if(rid==""){
	     				jAlert("登录超时,请重新登录.","系统提示",function(){				
	     					top.location="${pageContext.request.contextPath}/wct/login.jsp";
	     				});
	     			}
	    			//加载卷烟机工单信息
	    			$.post("${pageContext.request.contextPath}/wctSch/getCurWorkOrderByEqp.action",{"shift":"${shift.id}","eqpid":rid},function(v){
	    				var wk=eval("("+v+")");
	    				$("#date").html(wk.date);
	    				$("#shift").html(wk.shift);
	    				$("#team").html(wk.team);
	    				$("#wkcod").html(wk.cod);
	    				$("#paihao").html(wk.paihao);
	    				wkorderCod=wk.cod;
	    				if(wkcod){		    					
	    					selectBatchByWkorder();//查询所有批次
	    				}
	    			});
	    		};
	    		
	    	    loadRollerWkOrder();   

	    	    $("#itemLvl").combobox({
	        		onChange:function(newValue, oldValue){
	        			if(editRowIndex!=-1){
	        				jConfirm("是否保存当前录入质检项目?","系统提示",function(r){
	            				if(r){
	        						saveDet(); 
	        						selectItmByBatch(1);
	            				}else{
	            					selectItmByBatch(1);
	            				}
	            			});    				
	        			}else{
	        				selectItmByBatch(1);
	        			}

	        			
	    			}
	        	});
	    	    
		});
		
		function showLoginFrom(){
			$("#hid_div").css("display","block");
		}
		function hideLoginFrom(){
			$("#hid_div #name").val(null);
			$("#hid_div #pwd").val(null);
			$("#hid_div").css("display","none");
		}
		//登录或注销
		function userLogin(){
			if(isLogin!=1){
				showLoginFrom();
			}else{
				//注销
				$.post("${pageContext.request.contextPath}/wctLogin/wctInnerLoginExit.action",function(r){
					if(r=="true"){
						$("#loginEvent span span").html("质检员登录");
						$("#curUserName").html("暂未登录");
						isLogin=0;
						jAlert("注销成功!","系统提示");
					}
				});
			}
		}
		function innerLogin(){
			var usr = $("#name").val();
			var pwd = $("#pwd").val();
			if (usr == "" || pwd == "") {
				jAlert("请输入用户名和密码!","系统提示");
			} else {
				var params = "basisUsr.acnt=" + usr + "&basisUsr.pwd=" + pwd;
				var url = "${pageContext.request.contextPath}/wctLogin/wctInnerLogin.action?" + params;
				$.post(url, function(r) {
					if (r!="false") {
						userName=r;
						isLogin=1;
						$("#curUserName").html(userName);
						$("#loginEvent span span").html("注销质检员登录");
						hideLoginFrom();
						//alert(isLogin+","+loginName);
						//addBatch();
					} else {
						jAlert("账户或密码错误!","系统提示");
					}
				});
			}
		}
		//查询当前机台所有批次
		function selectBatchByWkorder(){
			selfchk_det.datagrid("loadData",[]);
			 selfchk
					.datagrid(
							{
								url : "${pageContext.request.contextPath}/wctQm/listAllBatch.action",
								queryParams : {
									"qmSelfChkBean.wkcod" : wkorderCod
								}
							});
		}
		function selectItmByBatch(flag){//flag is not null ,the datagrid beginEdit
			var row = selfchk.datagrid('getSelected');
			 if(row){
				 var url="${pageContext.request.contextPath}/wctQm/listAllItm.action";
				 if(flag==1){
					 url="${pageContext.request.contextPath}/wctQm/listAllItmForNewBatch.action";
				 }
				 selfchk_det
					.datagrid(
							{
								url : url,
								queryParams : {
									"qmSelfChkBean.id" : row.id,
									"qmSelfChkBean.wktyp":10,
									"qmSelfChkBean.lvl":$("#itemLvl").combobox("getValue")
								},onLoadSuccess:function(data){
									if(flag==1){
										/* var det_size=datagrid_selfchk_det.datagrid("getRows").length;
										for(var i=0;i<det_size;i++){
											datagrid_selfchk_det.datagrid("beginEdit",i);										
										} */
									}
								}
							});
				 
			 }
			 editRowIndex=-1;
		}
		function addBatch(){
			if(isLogin!=1){
				jAlert("请先使用质检员用户名登录.","提示");
				return;
			}
			 if(wkorderCod){
				 selfchk
					.datagrid(
							{
								url : "${pageContext.request.contextPath}/wctQm/addBatch.action",
								queryParams : {
									"qmSelfChkBean.wkcod" : wkorderCod,
									"qmSelfChkBean.wktyp":10,
									"qmSelfChkBean.maker":userName
								},
								onLoadSuccess:function(data){
									 var size=selfchk.datagrid("getRows").length;								
									 selfchk.datagrid('selectRow',size-1);								  
									 selectItmByBatch(1);
								}
							});
			 }else{
				 jAlert("当前无运行工单","提示");
			 }
		}
		function delBatch(){
			if(isLogin!=1){
				jAlert("请先使用质检员用户名登录.","提示");
				return;
			}
			 var row= selfchk.datagrid('getSelected');
			 if(row){
				 $.post("${pageContext.request.contextPath}/wctQm/delBatch.action",{"qmSelfChkBean.id" : row.id},function(v){
					    if(v=="true"){
						 selectBatchByWkorder();
						 selfchk_det.datagrid("loadData",[]);
						 jAlert("操作成功","提示");
					 	}else{
					 		 jAlert("操作失败 ","提示");
					 	}				 
				 }
				 );
			 }else{
				 jAlert("请选择一条检测批次","提示");
			 }
		}
		function saveDet(){	
			if(isLogin!=1){
				jAlert("请先使用质检员用户名登录.","提示");
				return;
			}
			var det=selfchk_det.datagrid("getRows");
			selfchk_det.datagrid("endEdit",editRowIndex);										
			var ids = [];
			var vals = [];
			for ( var i = 0; i < det.length; i++) {
				ids.push(det[i].itmId);
				vals.push(det[i].val);
			}
			var ids=ids.join(",");
			var vals=vals.join(",");
			//console.info(ids+"\n"+vals);
			//alert(vals);
			$.post("${pageContext.request.contextPath}/wctQm/addDets.action",
					{"qmSelfChkBean.id":selfchk.datagrid('getSelected').id,
				     "itmIds":ids,
				     "vals":vals},function(v){
				if(v=="true"){
					jAlert("操作成功","提示");
					selfchk.datagrid("select",editRowIndexForbatch);
				}else{
					jAlert("保存失败","提示");
					//editDet();
				}
			});
		}
		function editDet(){
			var det_size=selfchk_det.datagrid("getRows").length;
			for(var i=0;i<det_size;i++){
				selfchk_det.datagrid("beginEdit",i);										
			}
		}
</script>	
</head>


<body class="easyui-layout" style="background:#DDDDDD;"> 
<div id="juanjie-title">${loginGroup.eqps['packer'].des}包装外观在线巡检</div>
<div id="juanjie-seach-box" style="background:#DDDDDD;">
<form id="findWork" method="post"  >
    <div data-options="region:'north',split:false,border:false" style="background:#DDDDDD;">
			<table style="width:100%">
				<tr>
				    <td>日期:</td>
					<td id="date" ></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>班次:</td>
					<td id="shift"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>班组:</td>
					<td id="team"></td>
					<td>工单:</td>
					<td id="wkcod"></td>
					<td>牌号:</td>
					<td id="paihao"></td>
			</tr>
			</table>
    </div>  
    
    	</form>
    	</div>
<div id="juanjieSta-tab">
<div class="easyui-layout" data-options="fit:true" style="border-radius: 4px;">
 <div data-options="region:'center',split:false,border:true" style="background:#DDDDDD;">  
        <div class="easyui-layout" data-options="fit:true,split:false">  
           <div data-options="region:'west',border:false,width:370"  style="background:#DDDDDD;">
             <table id="selfchk" style="background:#DDDDDD;" >
			
			</table> 
            </div> 
            <div data-options="region:'center',split:false">
            <table id="selfchk_det" style="background:#FFF;" >
			</table>
            </div>  
        </div>  
    </div>
    
    <!-- toolbar -->
    <div id="selfchk_toolbar" style="height:28px;">
    	<table cellspacing="0" cellpadding="0">
	        <tr>
	           <td style="font-size:12px;color:#FFFFFF;font-weight:bold;padding:0 3px;">检验批次</td>
				<td >
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addBatch();">新增</a>
			    </td>
			    <td >
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="delBatch();">删除</a>
			    </td>
			    <td style="width:57px">
			    </td>
			    <td style="font-size:12px;color:white"> 
			    	<span>质检员：</span>
			    	<span id="curUserName">暂未登录</span>
			    </td>			   
	        </tr>
    	</table>
	</div>
		<div id="selfchk_det_toolbar">
    	<table cellspacing="0" cellpadding="0">
	        <tr>
	           <td style="font-size:12px;color:#FFFFFF;font-weight:bold;padding:0 3px;">外观等级</td>
	            <td>
	          		<select name="bean.lvl"
	          			id="itemLvl"
						class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false,width:100">
							<option value="">全部</option>
							<option value="A">A类</option>
							<option value="B">B类</option>
							<option value="C">C类</option>
							<option value="D">D类</option>
					</select>
	            </td>
	            <td>
	           <div class="datagrid-btn-separator"></div>
	            </td>
				<td >
					<a href="javascript:void(0);" class="easyui-linkbutton" 
					data-options="iconCls:'icon-save',plain:true,disabled:false" 
					onclick="saveDet();">保存本批次</a>
			    </td>
			    <td style="width:80px">
					
			    </td>
			    <td >
					<a href="javascript:void(0);" id="loginEvent" class="easyui-linkbutton" 
					data-options="iconCls:'icon-add',plain:true,disabled:false" 
					onclick="userLogin();">质检员登录</a>
			    </td>
			    <%-- <td >
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true<c:if test="${fn:contains(powerSession, '/pmsQm/addDets')}">,disabled:false</c:if>" onclick="editDet();">修改</a>
			    </td>	 --%>	   
	        </tr>
    	</table>
	</div>
<!-- 登录hide div -->     
	<div id="hid_div"  style="display:none;height:768px;width:100%;top:0px;left:0px;position:absolute;z-index:90;" onclick="return false;">
		<div class="password_box">
			<form >
			<div class="input-group" style="font-size:20px;">
			  登录验证
			</div>
			<div class="input-group">
			  <span class="input-group-addon">用户名</span>
			  <input type="text" id="name" class="form-control keyboard" style="height: 30px;;border-radius:5px" placeholder="原始密码"/>
			</div>
			<div class="input-group">
			  <span class="input-group-addon">密&nbsp;&nbsp;&nbsp;码</span>
			  <input type="password" id="pwd" class="form-control keyboard" style="height: 30px;border-radius:5px" placeholder="确认密码"/>
			</div>
		<div class="password_botton">			
			<input id="sub" type="button" value="确认" onclick="innerLogin()" class="btn btn-default"/>			
			<input id="cancel" type="button" value="取消" onclick="hideLoginFrom()" class="btn btn-default"/>
		</div>
	</form>
	</div>
	</div>
</div>
</div>
</body> 
</html>
