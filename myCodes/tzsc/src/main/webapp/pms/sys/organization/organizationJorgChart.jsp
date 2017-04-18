<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
   <title>采用jOrgChart的机构管理</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jOrgChart-master/example/css/jquery.jOrgChart.css"/>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jOrgChart-master/example/css/custom.css"/>
   <!-- jQuery includes -->
   <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jOrgChart-master/example/jquery.min.js"></script>   
   <jsp:include page="../../../initlib/initBasePath.jsp"></jsp:include>
   <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jOrgChart-master/example/jquery-ui.min.js"></script> 
   <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jOrgChart-master/example/jquery.jOrgChart.js"></script>
   <script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extJquery.js"></script>
   <%-- easyui 与 jorgchart 不兼容 所以采用批量引入js  --%>
<%-- <jsp:include page="../../../initlib/initAll.jsp"></jsp:include> --%>   
   <script>
  	var org_nodeId=null;//组织机构id
  	var org_nodePid=null;//组织机构id
  	var org_nodeText=null;//组织机构名称
   	$(function(){
      	$('.node-cell .node').live("mousedown",function(e){ 
            
			if(window.event)aevent=window.event;      //解决兼容性
			if(aevent.button==0||aevent.button==1){return false;}
			
            if(aevent.button==2){                   //当事件属性button的值为2时，表用户按下了右键
      			document.oncontextmenu=function(aevent){
      		    if(window.event){
      		        aevent=window.event;
      				aevent.returnValue=false;         //对IE 中断 默认点击右键事件处理函数
          		}else{
          			aevent.preventDefault();          //对标准DOM 中断 默认点击右键事件处理函数
          		}
      		}
      		} 
			
      		$("#menu").css({"top":e.pageY+2,"left":e.pageX+2,"display":"block"});
      		org_nodeId=$(this).attr("id");
      		org_nodePid=$(this).attr("pid");
      		org_nodeText=$(this).html();
          });
      	//body click 右击菜单屏蔽
      	$("body").click(function(){
      		$("#menu").css({"display":"none"});      		
      	});
      	
      	$("body").mousedown(function(){
      		if(window.event)aevent=window.event;      //解决兼容性
			if(aevent.button==0||aevent.button==1){return false;}
			
            if(aevent.button==2){                   //当事件属性button的值为2时，表用户按下了右键
      			document.oncontextmenu=function(aevent){
      		    if(window.event){
      		        aevent=window.event;
      				aevent.returnValue=false;         //对IE 中断 默认点击右键事件处理函数
          		}else{
          			aevent.preventDefault();          //对标准DOM 中断 默认点击右键事件处理函数
          		}
      		}
      		} 
            $("#menu").css({"display":"none"});           
      	});
      	
      	$("#menu div").click(function(e){
      		$("#menu").css({"display":"none"});
      	});
      	
    });
       function load(){
    	   $.post("${pageContext.request.contextPath}/pms/sysOrg/getAllOrgsHtml.do",function(json){
          		if(json.success){
        			console.info(json.obj);
          			if(json.obj==""){
          				$("#addOrg").attr('disabled',false);
          			}else{
              			$("#addOrg").attr('disabled',true);
          			}
          			
          			$("#org").html(json.obj); 
          			$("#chart").html(null);
          			$("#org").jOrgChart({
                          chartElement : '#chart',
                          dragAndDrop  : true
                      });
          		}
          	},"JSON");
       }
       function goToOrgAddJsp(b){
    	
   		var dialog = parent.$.modalDialog({
   			title : '组织机构添加',
   			width : 620,
   			height : 290,
   			href : '${pageContext.request.contextPath}/pms/sysOrg/goToOrgAddJsp.do',
   			buttons : [ {
   				text : '保存',
   				iconCls:'icon-standard-disk',
   				handler : function() {
   					var f = dialog.find("#form");
   					if(f.form("validate")){   						
	   					 $.post("${pageContext.request.contextPath}/pms/sysOrg/addOrg.do",f.form("getData"),function(json){
	   						if (json.success) {
	   							 parent.$.messager.show('提示', json.msg, 'info');
	   							 dialog.dialog('destroy');
	   							 load();
	   						}else{
	   							parent.$.messager.show('提示', json.msg, 'error');
	   						}
	   					},"JSON"); 
   					}
   				}
   			} ],
   			onLoad:function(){
   			if(b){
   				//alert(dialog.find("#form").html());
   				//dialog.find("#form #pid").attr("value",org_nodeId);
   				dialog.find("#pidComboTree").combotree({value:org_nodeId});
   		  	} 
   		}
   		});
   	}
       function goToOrgEditJsp(){
   		var dialog = parent.$.modalDialog({
   			title : '组织机构编辑',
   			width : 620,
   			height : 290,
   			href : '${pageContext.request.contextPath}/pms/sysOrg/goToOrgEditJsp.do?id='+org_nodeId,
   			buttons : [ {
   				text : '保存',
   				iconCls:'icon-standard-disk',
   				handler : function() {
   					var f = dialog.find("#form");
   					if(f.form("validate")){ 
   					$.post("${pageContext.request.contextPath}/pms/sysOrg/editOrg.do",f.form("getData"),function(json){
   						if (json.success) {
   							parent.$.messager.show('提示', json.msg, 'info');
   							dialog.dialog('destroy');
   							load();
   						}else{
   							parent.$.messager.show('提示', json.msg, 'error');
   						}
   					},"JSON");
   					}
   				}
   			} ]
   		});
   	}
   	function deleteOrg(){
   		parent.$.messager.confirm('操作提示', '您是否要删除当前组织机构?', function(b) {
   			if (b) {					
   				$.post('${pageContext.request.contextPath}/pms/sysOrg/deleteOrg.do', {
   					id : org_nodeId,
   					name : org_nodeText
   				}, function(json) {
   					if (json.success) {
   						parent.$.messager.show('提示', json.msg, 'info');
   						load();
   					}else{
   						parent.$.messager.show('提示', json.msg, 'error');
   					}
   				}, 'JSON');
   			}
   		});
   	}
   	
   	/**
	* 跳转到给机构分配权限页面
	*/
	function goToAssignResourceJsp(){
		var id=org_nodeId;//机构id
		dialog = parent.$.modalDialog({
			title : '权限分配',
			width : 700,
			height : 400,
			iconCls:'',
			href : '${pageContext.request.contextPath}/pms/sysOrg/goToAssignResourceJsp.do?id='+id,//跳转到授权页面,并加载该机构原有权限
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var ids=[];//资源ids(菜单,功能)
					var menus=dialog.find('input[class="menu"]:checked');
					var funcs=dialog.find('input[class="func"]:checked');
					menus.each(function(i){
						ids.push($(this).attr("id"));
					});
					funcs.each(function(i){
						ids.push($(this).attr("id"));
					});
					$.post("${pageContext.request.contextPath}/pms/sysOrg/assignResourceToOrg.do"
							,{id:id,ids:ids.join(",")},function(json){
								if (json.success) {
									parent.$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
								}else{
									parent.$.messager.show('提示', json.msg, 'error');
								}
					},"JSON");
					
				}
			} ]
		});
	}
	/**
	 * 跳转到给机构用户分配界面
	 */
	function goToAssignUserJsp(){
		var id=org_nodeId;//机构id
		dialog = parent.$.modalDialog({
			title : '分配用户',
			width : 700,
			height : 450,
			iconCls:'',
			href : '${pageContext.request.contextPath}/pms/sysOrg/goToAssignUserJsp.do?id='+id,//并加载该机构原有用户
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var rows=dialog.find("#assignedGrid").datagrid("getRows");
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
					//alert(id+","+ids.join(','));
					$.post("${pageContext.request.contextPath}/pms/sysOrg/assignUsersToOrg.do",{id:id,ids:ids.join(',')},function(json){
						if (json.success) {
							parent.$.messager.show('提示', json.msg, 'info');
						}else{
							parent.$.messager.show('提示', json.msg, 'error');
						}
						dialog.dialog('destroy');
					},"JSON");
				}
			} ]
		});
	}
    
</script>
<style type="text/css">
	#menu{
	border:1px solid red;
	height:auto;
	border-color: #dddddd;
	background: #F4F4F4;
	width:60px;
	position:absolute;
	z-index:99999;
	display:none;
	
	}
	#menu div{
		font-size:14px;
		list-style: none;
		color: black;
		padding:2px;
		text-align: center; 
	}
	#menu div a{
		text-decoration: none;
	}
	#menu div:hover{
		background-color: #bebebe;
	}
</style>
</head>
<body onload="load();" onselectstart="return false" >  <!-- class="easyui-layout" data-options="fit:true,border:false" --> 
<div style="position: absolute;top:5px;left:10px">
	<input onclick="load();" type="button" value="刷新"/>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/addOrg.do']}">
		<input onclick="goToOrgAddJsp();" type="button" id="addOrg" value="新增"/>
	</c:if>
</div>
<div id="chart" class="orgChart"></div>
<ul id="org" style="display:none;"></ul> 
<div id="menu" 
class="dialog-button">
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/addOrg.do']}">
	<div><a href="javascript:goToOrgAddJsp(true);">新增</a></div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/editOrg.do']}">
	<div><a href="javascript:goToOrgEditJsp();">编辑</a></div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/deleteOrg.do']}">
	<div><a href="javascript:deleteOrg();">删除</a></div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/assignResourceToOrg.do']}">
	<div><a href="javascript:goToAssignResourceJsp();">分配权限</a></div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/assignUsersToOrg.do']}">
	<div><a href="javascript:goToAssignUserJsp();">分配用户</a></div>
	</c:if>
</div>           
	
</body>
</html>



