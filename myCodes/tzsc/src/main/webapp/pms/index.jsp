<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>上海兰宝数据采集系统-生产管理系统(PMS)</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海兰宝数据采集系统,卷接包数据采集" />
    <meta name="description" content="" />
     
	<jsp:include page="../initlib/initAll.jsp"></jsp:include>
	<script src="../index/js/index.js" type="text/javascript"></script>
 	<link href="../index/css/index.css" rel="stylesheet" />
 	<link href="../index/css/message.css" rel="stylesheet" />
    <script src="../index/js/index-startup.js"></script>
     <script src="../pub/scroller/scroller.js"></script>
     <script src="../pub/sheyMsg.js"></script>
    <style type="text/css">
    	.text-scroller{margin-top:1px;width:430px;font-size:13px;height:18px;line-height:18px;overflow:hidden;}
		.text-scroller ul{list-style-type:none;margin:0;padding:0;margin-left:5px;}
		.text-scroller ul li{height:20px;line-height:20px;width:430px;float:left;display:inline;}
		.text-scroller ul li span{cursor:pointer;};
	</style>
    <script type="text/javascript">
    /**
	 *注销
	 */
	var logoutFun=function() {		
		location.replace('${pageContext.request.contextPath}/pms/sysUser/exit.do');				
	};
	/**
	 *锁定窗口
	 */
	var lockUser=function(){
		$.post("${pageContext.request.contextPath}/pms/sysUser/lockUser.do",function(){			
			$('#loginDialog').dialog('open');
		});		
	};
	/**
	*解锁
	*/
	var breakLock=function(){
		var f=$("#breakLockForm");
		if(f.form("validate")){
			$.post("${pageContext.request.contextPath}/pms/sysUser/breakLock.do",f.form("getData"),function(json){			
				if(json.success){
					$.easyui.messager.show(json.msg);
					form.form("clear");
					$('#loginDialog').dialog('close');
				}else{
					$.easyui.messager.show(json.msg);
				}
			},"JSON");
		}
	};
	/**
	*修改密码
	*/
	var editPwd=function(){
		 var f=$("#editPwdForm");
		 if(f.form("validate")){
			 $.post("${pageContext.request.contextPath}/pms/sysUser/editPwd.do",f.form("getData"),function(json){			
					if(json.success){
						$.easyui.messager.show(json.msg);
						form.form("clear");
						$('#editPasswordDialog').dialog('close');
					}else{
						$.easyui.messager.show(json.msg);
					}
				},"JSON");
		 }
	};
	/**
	*显示个人信息
	*/
	var showMyInfo = function() {
		$.modalDialog({
			title : '我的信息',
			maximizable : true,
			url : '${pageContext.request.contextPath}/pms/sys/user/userInfo.jsp'
		});
	};
	
	var isScorller =false;
	var scroll;
	//获取通知 向上无缝滚动显示
	function getMsgInfo(){
		$.getJSON("${pageContext.request.contextPath}/pms/msg/getMsgInfo.do",function(datas) {
			var contents = "";
			if(datas.length>0){
				//如果已经存在滚动对象，则停止滚动，待数据刷新以后，再继续滚动
				if(isScorller)
					scroll.stop();
				for(var i=0;i<datas.length;i++){
					var data = datas[i];
					contents += "<li>";
					if(i == 0)
						contents +="<span style='color:red;'";
					else
						contents +="<span style='color:green;'";
					contents +="onclick='openMsgInfo(this)' id='"+data.id+"'>";
					var content = data.time + "-" + data.title + "-" + data.content;
					if(content.length > 40)
						content = content.substring(0,40);
					contents += content;
					contents += "</a></span></li>";
				}
			}else{
				contents += "<li><a href='javascript:void(0)'>当前无通知消息</a></li>"
			}
			$("#marqueeContent").find("ul").html("");
			$("#marqueeContent").find("ul").html(contents);
			if(isScorller)
				scroll.scroll();
			setTimeout("getMsgInfo()", 7*1000 );
			//判断是否创建了滚动对象，如果false，则创建
			if(!isScorller){
				scroll = new Scroll("marqueeContent",20);
				isScorller = true;
			}
		});
	}
	//通知信息展示
	function openMsgInfo(obj){
		var id = $(obj).attr("id");
		$.modalDialog({
			title : '通知信息',
			width:800,
			height:500,
			maximizable : true,
			url : '${pageContext.request.contextPath}/pms/msg/openMsgInfoView.do?id='+id
		});
	}
	
	//获取数据
	function getDatas(url,data,sync){
		var data=null;
		$.ajax({
			 url:url,
			 data:data,
			 async:false,
			 dataType:"json",
			 success:function(v){
				data = v;
			}
		});
		return data;
	}
	
	
	//质量单耗告警
	function showMsgQmWarnDetail(obj){
		var id = $(obj).attr("id");
		$.modalDialog({
			title : '质量告警',
			width:800,
			height:500,
			maximizable : true,
			url : '${pageContext.request.contextPath}/pms/msg/qm/gotoMsgQmWarnView.do?id='+id
		});
	}
	//物料单耗告警详情
	function showMsgConWarnDetail(obj){
		var id = $(obj).attr("id");
		$.modalDialog({
			title : '物料单耗告警',
			width:800,
			height:500,
			maximizable : true,
			url : '${pageContext.request.contextPath}/pms/msg/con/gotoMsgConWarnView.do?id='+id
		});
	}
	
	//信息刷新
	function refreshWarnMsg(qmWarns,conWarns){
		var msg = '';
		for(var i=0;i<qmWarns.length;i++){ //获取质量告警信息显示
			var message = qmWarns[i];
			msg += '<div style="height:25px;line-height:25px;border-bottom:1px dashed #6FBCF4;">';
			msg += '<div style="float:left;width:25px;height:25px;background:url(\'${pageContext.request.contextPath}/pms/index/img/warn.gif\') no-repeat center center;"></div>';
			msg += '<div style="float:left;width:290px;height:25px;line-height:25px;text-align:left;">';
		   	msg += '<span style=\'background:#F07730;\'>【质量告警】</span><span style="color:#2779AA;padding-left:2px;">';
		   	msg += "<a href='javascript:void(0)'onclick='showMsgQmWarnDetail(this)' id='"+message.id+"' style='text-decoration: none'>";
		   	if(message.content.length > 18)
		    	msg += message.content.substring(0,18);
		    else
		   		msg += message.content;
		   	msg += "</a>";
   		   	msg += '</span></div></div>';
   		 	msg += "</div>";
		}
		for(var i=0;i<conWarns.length;i++){//获取单耗告警信息显示
			var message = conWarns[i];
			msg += '<div style="height:25px;line-height:25px;border-bottom:1px dashed #6FBCF4;">';
			msg += '<div style="float:left;width:25px;height:25px;background:url(\'${pageContext.request.contextPath}/pms/index/img/warn.gif\') no-repeat center center;"></div>';
			msg += '<div style="float:left;width:290px;height:25px;line-height:25px;text-align:left;">';
		   	msg += '<span style=\'background:#F0D826;\'>【单耗告警】</span><span style="color:#2779AA;padding-left:2px;">';
		   	msg += "<a href='javascript:void(0)'onclick='showMsgConWarnDetail(this)' id='"+message.id+"' style='text-decoration: none'>";
		    if(message.content.length > 18)
		    	msg += message.content.substring(0,18);
		    else
		   		msg += message.content;
		   	msg += "</a>";
   		   	msg += '</span></div></div>';
   		 	msg += "</div>";
		}
		//先清空
		$(".show_message").html("");
		$(".show_message").html(msg);
	}
	//获取质量告警和单耗告警信息，获取的信息为未读状态下的信息
	function getWarnMessage(){
		var qmWarns = getDatas("${pageContext.request.contextPath}/pms/msg/qm/getMsgQmWarns.do?sts=0",null,false);
		var conWarns = getDatas("${pageContext.request.contextPath}/pms/msg/con/getMsgConWarns.do?sts=0",null,false);
		if(qmWarns.length > 0 || conWarns.length > 0){
			refreshWarnMsg(qmWarns,conWarns);
			showWarnMessage();
		}
		//在十分钟过后重新扫描
		setTimeout("showWarnMessage()",60*10*1000);
	}
	//展示告警信息
	var msg,g;
	function showWarnMessage(){
		g=function(id){return document.getElementById(id)};
		msg=new sheyMsg("msgbox",{
		    showDelay:1,//延迟1秒
		    autoHide:10*1000 //十分钟
		});
		g("msgclose").onclick=function() {//hide
			msg.hide();
		}
	}
	
	
	$(function(){
		//显示通知信息
		//getMsgInfo(); //---->开发阶段暂时关闭
		//getWarnMessage();//--->开发阶段暂时关闭
	});
</script>
</head>

<body onload="<c:if test='${sessionInfo.islock==true}'>$('#loginDialog').dialog('open');</c:if>">
	<!-- 告警信息提示信息框 -->
	<div class="msg blue" id="msgbox">
		<div class="top">
		    <div class="title">告警信息</div>
		    <span title="close" id="msgclose"></span>
		</div>
		 <div class="center">
			<div class="show_message">
				
			</div>
		</div>
		<div class="bottom">
		</div>
	</div>
	<!-- 告警信息提示信息框结束 -->
    <div id="maskContainer">
        <div class="datagrid-mask" style="display: block;"></div>
        <div class="datagrid-mask-msg" style="display: block; left: 50%; margin-left: -52.5px;">
            	正在进入数据采集系统...
        </div>
    </div>

    <div id="mainLayout" class="easyui-layout hidden" data-options="fit: true">


        <div id="northPanel" data-options="region: 'north', border: false" style="height: 98px; overflow: hidden;background: url('${pageContext.request.contextPath}/pms/index/img/banner.png') repeat;">
            <div id="topbar" class="top-bar">
                <div class="top-bar-left" style="margin:10px 0px 0px 5px;height:60px;width:580px;background: url('${pageContext.request.contextPath}/pms/index/img/banner_logo.png') no-repeat scroll;">                    
                </div>
                <div class="top-bar-right" >
                    <div id="timerSpan" style="width:250px;height:70px;">
                   		<%-- <div style="width:243;height:60px;background: url('${pageContext.request.contextPath}/pms/index/img/banner_right.png') no-repeat;"></div> --%>
                   	</div>
                    <div id="themeSpan">                       
                        <a id="btnHideNorth" class="easyui-linkbutton" data-options="plain: true, iconCls: 'layout-button-up'" title="隐藏"></a>
                    </div> 
                   
                </div>
            </div>
            <div id="toolbar" class="panel-header panel-header-noborder top-toolbar">
                <div id="infobar">
                    <span class="icon-hamburg-user" style="padding-left: 25px; background-position: left center;">
                        <c:if test="${sessionInfo.ip != null}">
							您好,<strong>${sessionInfo.user.name}</strong> !   当前登录IP:[<strong>${sessionInfo.ip}</strong>]
						</c:if>
						<c:if test="${sessionInfo.ip == null}">
							您好,您尚未登录或会话已过期,请重新登录.
						</c:if>
                    </span>
                    
                </div>
                <div id="searchbar" style="height:23px; -moz-border-radius:10px;  -webkit-border-radius:10px; border-radius:10px; line-height:23px;width:500px;border:1px solid #CDCDCD;margin-top:1px;padding:0px;">
                	<div style="float:left;width:60px;height:22px;line-height:22px;">
	                	<div style="margin-left:5px;height:20px;background:url('${pageContext.request.contextPath}/pms/index/img/round.gif') no-repeat left center;">
		                	<span style="color:#2779AA;padding-left:20px;">通知</span>
	                	</div> 
                	</div>
                	<div id="marqueeContent" class="text-scroller" style="float:left;">
                		<ul>
						</ul>
                	</div>
				</div>
                <div id="buttonbar">
                    <a class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-application-view-tile'">皮肤</a>
                    <select id="themeSelector" data-options="width:40"></select>                                        
                    <a id="btnShowNorth" class="easyui-linkbutton" data-options="plain: true, iconCls: 'layout-button-down'" style="display: none;"></a>
                    <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-standard-cog'">控制面板</a>
					<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxmbMenu',iconCls:'icon-standard-application-side-expand'">注销</a>
                </div>
                <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
					<div data-options="iconCls:'icon-standard-user-comment'" onclick="showMyInfo();">我的信息</div>
					<div class="menu-sep"></div>
					<div data-options="iconCls:'icon-standard-group-edit'" onclick="$('#editPasswordDialog').dialog('open');">修改密码</div>
					<div class="menu-sep"></div>
					<div id="btnFullScreen" data-options="iconCls:'icon-standard-arrow-inout'">全屏切换[F11]</div>
				</div>
				<div id="layout_north_zxmbMenu" style="width: 100px; display: none;">	
					<div data-options="iconCls:'icon-standard-lock'" onclick="lockUser();">锁定窗口</div>
					<div class="menu-sep"></div>
					<div data-options="iconCls:'icon-standard-arrow-redo'" onclick="logoutFun();">退出系统</div>
				</div>
				
				<!--  -->
				<div id="loginDialog" class="easyui-dialog" 
					   data-options="closable:false,modal:true,width:290,height:190,title:'解锁登录',buttons:'#loginDialog-btns',closed:true">
						<form method="post" class="form" id="breakLockForm" onsubmit="return false;" style="padding:15px">
							<table class="table">
								<tr>
									<th width="50">用户名</th>
									<td>${sessionInfo.user.name}</td>
								</tr>
								<tr>
									<th>密码</th>
									<td><input name="pwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
								</tr>
							</table>
						</form>
						<div id="loginDialog-btns" style="padding-right:20px">
							<a href="javascript:void(0)" onclick="breakLock()" class="easyui-linkbutton" data-options="iconCls:'icon-standard-key'">&nbsp;解锁&nbsp;</a>
						</div>
				</div>
				<div id="editPasswordDialog" class="easyui-dialog" 
						data-options="modal:true,closed:true,width:290,height:240,buttons:'#passwordDialog-btns'" title="修改密码">
						<form method="post" class="form" id="editPwdForm" onsubmit="return false;" style="padding:15px">
							<table class="table">
								<tr>
									<th>原密码</th>
									<td><input name="oldpwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
								</tr>
								<tr>
									<th>新密码</th>
									<td><input id="pwd" name="pwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
								</tr>
								<tr>
									<th>重复密码</th>
									<td><input type="password" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#pwd\']'" /></td>
								</tr>
							</table>
						</form>
						<div id="passwordDialog-btns" style="padding-right:20px">
							<a href="javascript:void(0)" onclick="editPwd()" class="easyui-linkbutton" data-options="iconCls:'icon-standard-disk'">&nbsp;修改&nbsp;</a>
						</div>
				</div>
            </div>
        </div>

        <div data-options="region: 'west', title: '菜单导航栏', iconCls: 'icon-standard-map', split: true, minWidth: 230, maxWidth: 500" style="width: 230px; padding: 1px;">
            <div id="navTabs_tools" class="tabs-tool">
                <table>
                    <tr>
                        <td><a id="navMenu_refresh" class="easyui-linkbutton easyui-tooltip" title="刷新该选项卡及其导航菜单" data-options="plain: true, iconCls: 'icon-hamburg-refresh'"></a></td>
                    </tr>
                </table>
            </div>
            <div id="navTabs" class="easyui-tabs" data-options="fit: true, border: true, tools: '#navTabs_tools'">
                <div data-options="title: '系统菜单', iconCls: 'icon-standard-application-view-tile', refreshable: false, selected: true">
                    <div id="westLayout" class="easyui-layout" data-options="fit: true">
                        <div data-options="region: 'center', border: false" style="border-bottom-width: 1px;">
                            <div id="westCenterLayout" class="easyui-layout" data-options="fit: true">
                                <div data-options="region: 'north', split: false, border: false" style="height: 33px;">
                                    <div class="easyui-toolbar">
                                        <a id="navMenu_expand" class="easyui-splitbutton" data-options="iconCls: 'icon-metro-expand2', menu: '#navMenu_toggleMenu'">展开</a>
                                        <a id="navMenu_Favo" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-feed-add'">收藏</a>                                        
                                        <div id="navMenu_toggleMenu" class="easyui-menu">
                                            <div id="navMenu_collapse" data-options="iconCls: 'icon-metro-contract2'">折叠当前</div>
                                            <div class="menu-sep"></div>
                                            <div id="navMenu_collapseCurrentAll" data-options="iconCls: 'icon-metro-expand'">展开当前所有</div>
                                            <div id="navMenu_expandCurrentAll" data-options="iconCls: 'icon-metro-contract'">折叠当前所有</div>
                                            <div class="menu-sep"></div>
                                            <div id="navMenu_collapseAll" data-options="iconCls: 'icon-standard-arrow-out'">展开所有</div>
                                            <div id="navMenu_expandAll" data-options="iconCls: 'icon-standard-arrow-in'">折叠所有</div>
                                        </div>
                                    </div>
                                </div>
                                <div data-options="region: 'center', border: false">
                                    <ul id="navMenu_Tree" style="padding-top: 2px; padding-bottom: 2px;"></ul>
                                </div>
                            </div>
                        </div>                       
                    </div>
                </div>
                <div data-options="title: '个人收藏', iconCls: 'icon-hamburg-star', refreshable: false">
                    <div id="westFavoLayout" class="easyui-layout" data-options="fit: true">
                        <div data-options="region: 'north', split: false, border: false" style="height: 33px;">
                            <div class="easyui-toolbar">
                                <a id="favoMenu_expand" class="easyui-splitbutton" data-options="iconCls: 'icon-metro-expand2', menu: '#favoMenu_toggleMenu'">展开</a>
                                <a id="favoMenu_Favo" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-feed-delete'">取消收藏</a>                                
                                <div id="favoMenu_toggleMenu" class="easyui-menu">
                                    <div id="favoMenu_collapse" data-options="iconCls: 'icon-metro-contract2'">折叠当前</div>
                                    <div class="menu-sep"></div>
                                    <div id="favoMenu_collapseCurrentAll" data-options="iconCls: 'icon-metro-expand'">展开当前所有</div>
                                    <div id="favoMenu_expandCurrentAll" data-options="iconCls: 'icon-metro-contract'">折叠当前所有</div>
                                    <div class="menu-sep"></div>
                                    <div id="favoMenu_collapseAll" data-options="iconCls: 'icon-standard-arrow-out'">展开所有</div>
                                    <div id="favoMenu_expandAll" data-options="iconCls: 'icon-standard-arrow-in'">折叠所有</div>
                                </div>
                            </div>
                        </div>
                        <div data-options="region: 'center', border: false">
                            <ul id="favoMenu_Tree" style="padding-top: 2px; padding-bottom: 2px;"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div data-options="region: 'center'" style="padding: 1px;">
            <div id="mainTabs_tools" class="tabs-tool">
                <table>
                    <tr>
                        <td><a id="mainTabs_jumpHome" class="easyui-linkbutton easyui-tooltip" title="跳转至信息看板选项卡" data-options="plain: true, iconCls: 'icon-hamburg-home'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTabs_toggleAll" class="easyui-linkbutton easyui-tooltip" title="展开/折叠面板使选项卡最大化" data-options="plain: true, iconCls: 'icon-standard-arrow-inout'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTabs_jumpTab" class="easyui-linkbutton easyui-tooltip" title="在新页面中打开该选项卡" data-options="plain: true, iconCls: 'icon-standard-shape-move-forwards'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTabs_closeTab" class="easyui-linkbutton easyui-tooltip" title="关闭当前选中的选项卡" data-options="plain: true, iconCls: 'icon-standard-application-form-delete'"></a></td>
                        <td><a id="mainTabs_closeOther" class="easyui-linkbutton easyui-tooltip" title="关闭除当前选中外的其他所有选项卡" data-options="plain: true, iconCls: 'icon-standard-cancel'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTabs_closeLeft" class="easyui-linkbutton easyui-tooltip" title="关闭左侧所有选项卡" data-options="plain: true, iconCls: 'icon-standard-tab-close-left'"></a></td>
                        <td><a id="mainTabs_closeRight" class="easyui-linkbutton easyui-tooltip" title="关闭右侧所有选项卡" data-options="plain: true, iconCls: 'icon-standard-tab-close-right'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTabs_closeAll" class="easyui-linkbutton easyui-tooltip" title="关闭所有选项卡" data-options="plain: true, iconCls: 'icon-standard-cross'"></a></td>
                    </tr>
                </table>
            </div>
            <div id="mainTabs" class="easyui-tabs" data-options="fit: true, border: true, showOption: true, enableNewTabMenu: true, tools: '#mainTabs_tools', enableJumpTabMenu: true, onSelect: function (title, index) { window.mainpage.mainTabs.updateHash(index); }">
                 <div id="homePanel" data-options="title: '信息看板', iconCls: 'icon-hamburg-home'">
                    <div class="easyui-layout" data-options="fit: true">
                    	<jsp:include page="goldIndex.jsp"></jsp:include>
                    	<%--  <iframe src="${pageContext.request.contextPath}/pms/goldIndex.jsp" allowTransparency="true" style="overflow: hidden;border: 0; width: 100%; height: 99%;margin:0px;padding:0px" frameBorder="0"></iframe> --%>                         
                    </div>
                </div> 
            </div>
        </div>

        <!-- <div data-options="region: 'east', title: '日历', iconCls: 'icon-standard-date', split: false, minWidth: 200, maxWidth: 500,collapsed:true" style="width: 220px; padding: 1px; border-left-width: 0px;">
            <div id="eastLayout" class="easyui-layout" data-options="fit: true">
                <div data-options="region: 'north', split: false, border: false" style="height: 220px;">
                    <div class="easyui-calendar" data-options="fit: true"></div>
                </div>
                <div id="linkPanel" data-options="region: 'center', title: '工作备忘录', iconCls: 'icon-hamburg-link', tools: [{ iconCls: 'icon-hamburg-refresh', handler: function () { window.link.reload(); } }]">
                    <ul id="linkList" class="portlet-list link-list"></ul>
                </div>
            </div>
        </div> -->

        <div data-options="region: 'south', border: false">
            <div>
				<div class="panel-header panel-title" style="text-align: center;">
					版权所有&copy;<a href="http://www.shlanbao.cn" title="developed by yilijian">上海兰宝传感科技股份有限公司</a>
				</div>
				<div style="position: absolute; right: 25px; bottom: 6px;"><strong>系统时间&nbsp;&nbsp;</strong><span id="systemTime"></span></div>
			</div>
        </div>


    </div>
</body>
</html>