<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>烟箱自检</title>
<link rel="stylesheet" id="easyuiTheme" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/JsUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/BandSelect.js"></script>
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.js" type="text/javascript"></script>
<link media="screen" href="${pageContext.request.contextPath}/js/wctAlert/jquery.alerts.css" type="text/css" rel="stylesheet" ><!-- Example script -->
 
<jsp:include page='../../js/initjsp/initKeyboard.jsp' ></jsp:include>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>

<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#eqpRun-title{
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
	#eqpRun-seach-box{
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
	#eqpRun-wct-frm td{
		font-size:14px;
	}
	#eqpRun-tab{		
		width:817px;
		margin-left:10px;
		margin-top:15px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
		overflow:auto;
		background-color:#DDDDDD;
		
	}
	#eqpRun-page{
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
#eqpRunSta-tab {
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
</style>


<script type="text/javascript">
		var tim="";
 		var mydatagrid=null;
 		var times="";
 		var cureditor="";
        var num=''; 
        var cols="";
    	var mdshift="";
		var stratnum=0;
		var endnum=0;
		var num1=0;
		var num1=0;
		var num1=0;
		function recolums(){
				//添加默认数据与得到班次，班组，设备
		        $.ajax({
		        	   type: "POST",
		        	   url: '${pageContext.request.contextPath}/wctQm/queryClass.action?type=X',
		        	   data: " ",
		        	   async:false,
		        	   success: function(msg){
		        		   var manger=msg.split("=");
		        			$('#time1').html(manger[0]);
		        			$('#dec1').html(manger[1]);
		        			$('#des1').html(manger[2]);
		        			mdshift=manger[3];
		        	   }
		        }); 
		        
		       	$.ajax({
					   type:"POST",
					   url: '${pageContext.request.contextPath}/wctQm/queryWorkTime.action',
					   data: '',
					   async:false,
					   success: function(msg){
						   msg=eval("("+msg+")");
						      num1=msg.rows[0].stratTime;
						      num2=msg.rows[1].stratTime;
						      num3=msg.rows[2].stratTime
						     for(var i=0;i<msg.rows.length;i++){
						    	 if(mdshift==msg.rows[i].id){
						    		// alert(msg.rows[i].id+"--id");
						    		  stratnum=msg.rows[i].stratTime;
						    		  var endtime=msg.rows[i].endTime;
						    		  if(endtime==0){
						    			  endnum=24;
						    		  }else{
						    			  endnum=endtime;
						    		  }
						    	 }
						     }
					   }
					 }); 
		/* 	var num1=parseInt(num.substring(0,2));
			var num2=parseInt(num.substring(3,5)); */
			
			    cols+="{field:'id',title:'',width:37,align:'center',sortable:true,hidden:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'},},"
			//cols+="{field:'name',title:'缺陷描述',width:200,sortable:true},"
		for(var i=stratnum;i<=endnum;i++){
				cols+="{field:'c"+i+"_00',title:'"+i+":00',width:37,align:'center',sortable:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'},},"
			}
			var cols2=cols.substring(0,cols.length-1);
			var arr="[["+cols2+"]]";
			return arr;
		}
	$(function(){
		var arr=recolums();
		mydatagrid=$('#videoTable').datagrid({
			//height : 500,
			fit:true,
			url:'${pageContext.request.contextPath}/wctQm/queryQmSelfchItm.action?type=X',
			frozenColumns:[[{field:'name',title:'缺陷描述',width:450,align:'left',sortable:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'}},]],
			columns :eval("("+arr+")"),
			toolbar:[{
	            text:'新增',
	            iconCls:'icon-add',
	            handler:function(){
	            	//的到时间
	            	 tim=time();
	            	//清除编辑类型
	            	fields();
	            	//赋列编辑类型
	            	fileds(tim);
	            	$("input[type=text]").keyboard();
	            }
	        },{
	            text:'重置',
	            iconCls:'icon-remove',
	            handler:function(){
	            	var datas=$("#videoTable").datagrid('getData').rows;
	            	for(var i=0;i<datas.length;i++){
		    			$('#videoTable').datagrid('cancelEdit', i);
		    		 }
	            }
	        },'-',{
	            text:'保存',
	            iconCls:'icon-save',
	            handler:function(){
	              var datas=$("#videoTable").datagrid('getData').rows;
	    			  if(datas.length==0){
	    				$.messager.alert('提示','列表为空！');
	    			}else{
	    				for(var i=0;i<datas.length;i++){
	        				$('#videoTable').datagrid('endEdit', i);
	        			}
	    				var vallues=retrunValues(tim);
	    				
	    				if(vallues!=""){
	    				var url="${pageContext.request.contextPath}/wctQm/addCount.action?type=X";
	    				$.ajax({
	    				   type: "POST",
	    				   url: url,
	    				   data: {"ids":vallues,"tim":tim},
	    				   success: function(msg){
	    					  
	    					   if(msg==0){
	    						   //$.messager.alert('提示','保存失败！'); 
	    						   jAlert('保存失败！', '系统提示');
	    						   
	    					   }else if(msg==1){
	    						//  $.messager.alert('提示','保存成功！');
	    						 jAlert('保存成功！', '系统提示');
	    						   $('#videoTable').datagrid('load',{}); 
		    					   $('#videoTable').datagrid("clearSelections");
	    					   }
	    					  
	    				   }
	    				 }); 
	    				}
	    			}
	            }
	        }]
		});
	});
		function time(){
			var cure="";
			var date=new Date(); 
    		var h =parseInt(date.getHours()); 
    		var m = parseInt(date.getMinutes()); 
    		var string="";
    		if(h==0){
    			string="c24_00";	
    		}
    		else{
    			string="c"+h+"_00";	
    		}
    		 return string;
		}
		
		function fileds(tim){
	       	var datas=$("#videoTable").datagrid('getData').rows;
			 cureditor=tim;
			$('#videoTable').datagrid('addEditor',{  
               field :cureditor,  
               editor : {  
                   type : 'numberbox', 
                   options : {  
                       required : false  
                   }  
               }  
           });
			for(var i=0;i<datas.length;i++){
   			$('#videoTable').datagrid('beginEdit', i);
   		  } 
			
		}
		  function fields(){
				 var opts = $('#videoTable').datagrid('getColumnFields');
				 var op=(opts.length);
				 for(var i=0;i<op;i++){
					
						$('#videoTable').datagrid('addEditor',{  
				            field :opts[i],  
				            editor : {  
				                type : '', 
				                options : { 
				                required : false  
				                }  
				            }
				        }); 
				 }
				 var datas=$("#videoTable").datagrid('getData').rows;
					for(var i=0;i<datas.length;i++){
						$('#videoTable').datagrid('endEdit', i);
					  } 
		  }	
function retrunValues(ops){
var managers=""
var count=""
var datas=$("#videoTable").datagrid('getData').rows;
for(var i=0;i<datas.length;i++){
	if(ops=="c8_00" ){
		count=datas[i].c8_00;
		if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c8_00+",";
		}
	}


	if(ops=="c9_00" ){
		count=datas[i].c9_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c9_00+",";
		}
	}
	
	if(ops=="c10_00" ){
		count=datas[i].c10_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c10_00+",";
		}
	}
	
	if(ops=="c11_00" ){
		count=datas[i].c11_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c11_00+",";
		}
	}
	if(ops=="c12_00" ){
		count=datas[i].c12_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c12_00+",";
		}
	}
	if(ops=="c13_00" ){
		count=datas[i].c13_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c13_00+",";
		}
	}
	
	if(ops=="c14_00" ){
		count=datas[i].c14_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c14_00+",";
		}
	}
	if(ops=="c15_00" ){
		count=datas[i].c15_00;
		if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c15_00+",";
		}
	}
	if(ops=="c16_00" ){
		count=datas[i].c16_00;
		if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c16_00+",";
		}
	}
	if(ops=="c17_00" ){
		count=datas[i].c17_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c17_00+",";
		}
	}
	if(ops=="c18_00" ){
		count=datas[i].c18_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c18_00+",";
		}
	}
	if(ops=="c19_00" ){
		count=datas[i].c19_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c19_00+",";
		}
	}
	if(ops=="c20_00" ){
		count=datas[i].c20_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c20_00+",";
		}
	}
	if(ops=="c21_00" ){
		count=datas[i].c21_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c21_00+",";
		}
	}
	if(ops=="c22_00" ){
		count=datas[i].c22_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c22_00+",";
		}
	}
	if(ops=="c23_00" ){
		count=datas[i].c23_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c23_00+",";
		}
	}
	if(ops=="c1_00" ){
		count=datas[i].c1_00
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c1_00+",";
		}
	}
	if(ops=="c2_00" ){
		count=datas[i].c2_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c2_00+",";
		}
	}
	
	if(ops=="c3_00" ){
		count=datas[i].c3_00
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c3_00+",";
		}
	}
	
	if(ops=="c4_00" ){
		count=datas[i].c4_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c4_00+",";
		}
	}
	
	if(ops=="c5_00" ){
		count=datas[i].c5_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_00+",";
		}
	}
	if(ops=="c6_00" ){
		count=datas[i].c6_00;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c6_00+",";
		}
	}
	
	
	if(ops=="c7_00" ){
		count=datas[i].c7_00;
		if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c7_00+",";
		}
	}
}

return managers;
}

		
		
function  radiv(){
			var valradio = $('input[name=cureqps]:checked').val();
		
			if(valradio!=null){
			$.ajax({
				   type: "POST",
				   url: '${pageContext.request.contextPath}/wctQm/querySessce.action?radio='+valradio+'&type=X',
				   data: " ",
				   success: function(msg){
						if(msg==1){
							 $.ajax({
						     	   type: "POST",
						     	   url: '${pageContext.request.contextPath}/wctQm/queryClass.action?type=X',
						     	   data: " ",
						     	   async:false,
						     	   success: function(msg){
						     	
						     	   }
						     });
					    $('#videoTable').datagrid('load',{}); 
						}
				   }
			}); 
		}
	}
		
		
		
</script>	
</head>
<body class="easyui-layout" style="background:#DDDDDD;"> 
<div id="eqpRun-title">封箱质量自检</div>
<div id="eqpRun-seach-box" style="background:#DDDDDD;">
<form id="findWork" method="post" >
<div data-options="region:'north',split:false,border:false" style="background:#DDDDDD;width:100%;height:30px">
	<div style="width: 340px;float: left;">
		<table width="100%" cellspacing="0" cellpadding="0" style="line-height:30px;" >
			<tr>
				<td class="search-td-title">时间：</td>
				<td id="time1"></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="search-td-title">班次：</td>
				<td id="dec1"></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="search-td-title">班组：</td>
				<td id="des1"></td>	
			</tr>
		</table>
	</div>
	<table width="100%" cellspacing="0" cellpadding="0" style="float: right;width: 370px;" >
		<tr>		
			
			<td style="width:130px;">
				<label><input type="radio"  checked="checked" name="cureqps" id="radio1" value="${loginGroup.eqps['boxer1'].id}">${loginGroup.eqps['boxer1'].cod}#封箱机</label>
			</td>
			<td style="width:130px;">
				<label><input type="radio" name="cureqps" id="radio2" value="${loginGroup.eqps['boxer2'].id}">${loginGroup.eqps['boxer2'].cod}#封箱机</label>
			</td>
			<td class="title-td"></td>
			<td>
				<input type="button" id="eqpRun-search" value="确认请求" style="height:28px;width:100px;" class="btn btn-default" onclick="radiv()"/>
			</td>
		</tr>
	</table>
</div>
</form>
</div>
    <div id="eqpRunSta-tab">  
        <div class="easyui-layout" data-options="fit:true" style="border-radius: 4px;">  
              
    <div data-options="region:'center',split:false,border:false" style="background:#DDDDDD;">  
        <div class="easyui-layout" data-options="fit:true,split:false,border:false" style="background:#DDDDDD;">  
            <div data-options="region:'center',split:false,border:false" style="background:#DDDDDD;">
            <table id="videoTable" style="background:#DDDDDD;">
            </table>
            </div>  
        </div>  
    </div>  
  </div>  
</div>

</body> 
</html>
