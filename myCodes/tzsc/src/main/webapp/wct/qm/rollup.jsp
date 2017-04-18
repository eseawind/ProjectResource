<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>烟支自检</title>
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
		var cl="";
		var mdshift="";
		var stratnum=0;
		var endnum=0;
		var num1=0;
		var num1=0;
		var num1=0;
       
		function recolums(){
			 $.ajax({
		     	   type: "POST",
		     	   url: '${pageContext.request.contextPath}/wctQm/queryClass.action?type=C',
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
		for(var i=stratnum;i<=endnum;i++){
			if(i==num1){
				cols+="{field:'c"+i+"_00',title:'首检',width:39,align:'center',sortable:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'},},"
				cl+="{title:'"+i+"点',colspan:5},";
			}else{
				cl+="{title:'"+i+"点',colspan:4},";
			}
				cols+="{field:'c"+i+"_15',title:'15',width:37,align:'center',sortable:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'},},"
				cols+="{field:'c"+i+"_30',title:'30',width:37,align:'center',sortable:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'},},"
				cols+="{field:'c"+i+"_45',title:'45',width:37,align:'center',sortable:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'},},"
				cols+="{field:'c"+i+"_59',title:'59',width:37,align:'center',sortable:true,styler:function(value,rowData,rowIndex){ return 'background:#DDDDDD'},},"
			}
			var cols2=cols.substring(0,cols.length-1);
			var arr="[["+cl+"],["+cols2+"]]";
			return arr;
		}
		
		
	$(function(){
		var arr=recolums();
		mydatagrid=$('#videoTable').datagrid({
		   fit :true,
		   url:'${pageContext.request.contextPath}/wctQm/queryQmSelfchItm.action?type=C',
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
	            	$("input").keyboard();
	            }
	        },'-',{
	            text:'重置',
	            iconCls:'icon-remove',
	            handler:function(){
	            	var datas=$("#videoTable").datagrid('getData').rows;
	            	for(var i=0;i<datas.length;i++){
		    			$('#videoTable').datagrid('endEdit', i);
		    		// $('#videoTable').datagrid('loadData', { total:i, rows: [] });
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
	    				var url="${pageContext.request.contextPath}/wctQm/addCount.action?type=C";
	    				$.ajax({
	    				   type: "POST",
	    				   url: url,
	    				   data: {"ids":vallues,"tim":tim},
	    				   success: function(msg){
	    					 
	    					   if(msg==0){
	    						 //  $.messager.alert('提示','保存失败！'); 
	    						   jAlert('保存失败！', '系统提示');
	    					   }else if(msg==1){
	    						  // $.messager.alert('提示','保存成功！');
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
    		var a=0;
    		var b=15;
    		var c=30;
    		var d=45;
    		var e=59;
    		var hh=h+""+m;
    		 if(h>=8 && h<9 &&  hh<815){
    			 cure='c8_00';  
             }else{
             if(b<=m && m<c){
            	 cure='c'+h+'_'+'15'; 
             }else if(c<=m && m<d){
            	 cure='c'+h+'_'+'30'; 
             }else if(d<=m && m<e){
            	 cure='c'+h+'_'+'45'; 
             }else{
            	 
            	 cure='c'+(h-1)+'_'+'59'; 
             }
          }
    		
    		 return cure
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
	if(ops=="c8_15" ){
		count=datas[i].c8_15;
		if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c8_15+",";
		}
	}
	if(ops=="c8_30" ){
		count=datas[i].c8_30;
		if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c8_30+",";
		}
	}
	if(ops=="c8_45" ){
		count=datas[i].c8_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c8_45+",";
		}
	}
	if(ops=="c8_59" ){
		count=datas[i].c8_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c8_59+",";
		}
	}
	if(ops=="c9_15" ){
		count=datas[i].c9_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c9_15+",";
		}
	}
	if(ops=="c9_30" ){
		count=datas[i].c9_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c9_30+",";
		}
	}
	if(ops=="c9_45" ){
		count=datas[i].c9_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c9_45+",";
		}
	}
	if(ops=="c9_59" ){
		count=datas[i].c9_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c9_59+",";
		}
	}
	if(ops=="c10_15" ){
		count=datas[i].c10_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c10_15+",";
		}
	}
	if(ops=="c10_30" ){
		count=datas[i].c10_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c10_30+",";
		}
	}
	if(ops=="c10_45" ){
		count=datas[i].c10_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c10_45+",";
		}
	}
	if(ops=="c10_59" ){
		count=datas[i].c10_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c10_59+",";
		}
	}
	if(ops=="c11_15" ){
		count=datas[i].c11_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c11_15+",";
		}
	}
	if(ops=="c11_30" ){
		count=datas[i].c11_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c11_30+",";
		}
	}
	if(ops=="c11_45" ){
		count=datas[i].c11_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c11_45+",";
		}
	}
	if(ops=="c11_59" ){
		count=datas[i].c11_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c11_59+",";
		}
	}
	if(ops=="c12_15" ){
		count=datas[i].c12_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c12_15+",";
		}
	}
	if(ops=="c12_30" ){
		count=datas[i].c12_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c12_30+",";
		}
	}
	if(ops=="c12_45" ){
		count=datas[i].c12_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c12_45+",";
		}
	}
	if(ops=="c12_59" ){
		count=datas[i].c12_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c12_59+",";
		}
	}
	
	if(ops=="c13_15" ){
		count=datas[i].c13_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c13_15+",";
		}
	}
	if(ops=="c13_30" ){
		count=datas[i].c13_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c13_30+",";
		}
	}
	if(ops=="c13_45" ){
		
		count=datas[i].c13_45;
		
		if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c13_45+",";
		}
	}
	if(ops=="c13_59" ){
		count=datas[i].c13_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c13_59+",";
		}
	}
	if(ops=="c14_15" ){
		count=datas[i].c14_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c14_15+",";
		}
	}
	if(ops=="c14_30" ){
		count=datas[i].c14_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c14_30+",";
		}
	}
	if(ops=="c14_45" ){
		count=datas[i].c14_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c14_45+",";
		}
	}
	if(ops=="c14_59" ){
		count=datas[i].c14_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c14_59+",";
		}
	}
	
	if(ops=="c15_15" ){
		count=datas[i].c15_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c15_15+",";
		}
	}
	if(ops=="c15_30" ){
		count=datas[i].c15_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c15_30+",";
		}
	}
	if(ops=="c15_45" ){
		count=datas[i].c15_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c15_45+",";
		}
	}
	if(ops=="c15_59" ){
		count=datas[i].c15_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c15_59+",";
		}
	}
	if(ops=="c16_15" ){
		count=datas[i].c16_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c16_15+",";
		}
	}
	if(ops=="c16_30" ){
		count=datas[i].c16_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c16_30+",";
		}
	}
	if(ops=="c16_45" ){
		count=datas[i].c16_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c16_45+",";
		}
	}
	if(ops=="c16_59" ){
		count=datas[i].c16_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c16_59+",";
		}
	}
	
	if(ops=="c17_15" ){
		count=datas[i].c17_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c17_15+",";
		}
	}
	if(ops=="c17_30" ){
		count=datas[i].c17_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c17_30+",";
		}
	}
	if(ops=="c17_45" ){
		count=datas[i].c17_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c17_45+",";
		}
	}
	if(ops=="c17_59" ){
		count=datas[i].c17_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c17_59+",";
		}
	}
	
	
	if(ops=="c18_15" ){
		count=datas[i].c18_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c18_15+",";
		}
	}
	if(ops=="c18_30" ){
		count=datas[i].c18_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c18_30+",";
		}
	}
	if(ops=="c18_45" ){
		count=datas[i].c18_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c18_45+",";
		}
	}
	if(ops=="c18_59" ){
		count=datas[i].c18_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c18_59+",";
		}
	}
	
	
	if(ops=="c19_15" ){
		count=datas[i].c19_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c19_15+",";
		}
	}
	if(ops=="c19_30" ){
		count=datas[i].c19_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c19_30+",";
		}
	}
	if(ops=="c19_45" ){
		count=datas[i].c19_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c19_45+",";
		}
	}
	if(ops=="c19_59" ){
		count=datas[i].c19_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c19_59+",";
		}
	}

	
	if(ops=="c20_15" ){
		count=datas[i].c20_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c20_15+",";
		}
	}
	if(ops=="c20_30" ){
		count=datas[i].c20_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c20_30+",";
		}
	}
	if(ops=="c20_45" ){
		count=datas[i].c20_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c20_45+",";
		}
	}
	if(ops=="c20_59" ){
		count=datas[i].c20_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c20_59+",";
		}
	}
	
	if(ops=="c21_15" ){
		count=datas[i].c21_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c21_15+",";
		}
	}
	if(ops=="c21_30" ){
		count=datas[i].c21_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c21_30+",";
		}
	}
	if(ops=="c21_45" ){
		count=datas[i].c21_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c21_45+",";
		}
	}
	if(ops=="c21_59" ){
		count=datas[i].c21_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c21_59+",";
		}
	}
	
	if(ops=="c22_15" ){
		count=datas[i].c22_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c22_15+",";
		}
	}
	if(ops=="c22_30" ){
		count=datas[i].c22_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c22_30+",";
		}
	}
	if(ops=="c22_45" ){
		count=datas[i].c22_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c22_45+",";
		}
	}
	if(ops=="c22_59" ){
		count=datas[i].c22_59
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c22_59+",";
		}
	}
	
	if(ops=="c23_15" ){
		count=datas[i].c23_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c23_15+",";
		}
	}
	if(ops=="c23_30" ){
		count=datas[i].c23_30
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c23_30+",";
		}
	}
	if(ops=="c23_45" ){
		count=datas[i].c23_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c23_45+",";
		}
	}
	if(ops=="c23_59" ){
		count=datas[i].c23_59
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c23_59+",";
		}
	}
	if(ops=="c0_15" ){
		count=datas[i].c0_15
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c0_15+",";
		}
	}
	if(ops=="c0_30" ){
		count=datas[i].c0_30
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c0_30+",";
		}
	}
	if(ops=="c0_45" ){
		count=datas[i].c0_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c0_45+",";
		}
	}
	if(ops=="c0_59" ){
		count=datas[i].c0_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c0_59+",";
		}
	}
	
	if(ops=="c1_15" ){
		count=datas[i].c1_15
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c1_15+",";
		}
	}
	if(ops=="c1_30" ){
		count=datas[i].c1_30
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c1_30+",";
		}
	}
	if(ops=="c1_45" ){
		count=datas[i].c1_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c1_45+",";
		}
	}
	if(ops=="c1_59" ){
		count=datas[i].c1_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c1_59+",";
		}
	}
	if(ops=="c2_15" ){
		count=datas[i].c2_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c2_15+",";
		}
	}
	if(ops=="c2_30" ){
		count=datas[i].c2_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c2_30+",";
		}
	}
	if(ops=="c2_45" ){
		count=datas[i].c2_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c2_45+",";
		}
	}
	if(ops=="c2_59" ){
		count=datas[i].c2_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c2_59+",";
		}
	}
	if(ops=="c3_15" ){
		count=datas[i].c3_15
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c3_15+",";
		}
	}
	if(ops=="c3_30" ){
		count=datas[i].c3_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c3_30+",";
		}
	}
	if(ops=="c3_45" ){
		count=datas[i].c3_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c3_45+",";
		}
	}
	if(ops=="c3_59" ){
		count=datas[i].c3_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c3_59+",";
		}
	}
	if(ops=="c4_15" ){
		count=datas[i].c4_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c4_15+",";
		}
	}
	if(ops=="c4_30" ){
		count=datas[i].c4_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c4_30+",";
		}
	}
	if(ops=="c4_45" ){
		count=datas[i].c4_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c4_45+",";
		}
	}
	if(ops=="c4_59" ){
		count=datas[i].c4_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c4_59+",";
		}
	}
	if(ops=="c5_15" ){
		count=datas[i].c5_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_15+",";
		}
	}
	if(ops=="c5_30" ){
		count=datas[i].c5_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_30+",";
		}
	}
	if(ops=="c5_45" ){
		count=datas[i].c5_45
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_45+",";
		}
	}
	if(ops=="c5_59" ){
		count=datas[i].c5_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_59+",";
		}
	}
	if(ops=="c5_15" ){
		count=datas[i].c5_15
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_15+",";
		}
	}
	if(ops=="c5_30" ){
		count=datas[i].c5_30
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_30+",";
		}
	}
	if(ops=="c5_45" ){
		count=datas[i].c5_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_45+",";
		}
	}
	if(ops=="c5_59" ){
		count=datas[i].c5_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c5_59+",";
		}
	}
	if(ops=="c6_15" ){
		count=datas[i].c6_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c6_15+",";
		}
	}
	if(ops=="c6_30" ){
		count=datas[i].c6_30
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c6_30+",";
		}
	}
	if(ops=="c6_45" ){
		count=datas[i].c6_45
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c6_45+",";
		}
	}
	if(ops=="c6_59" ){
		count=datas[i].c6_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c6_59+",";
		}
	}
	
	if(ops=="c7_15" ){
		count=datas[i].c7_15;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c7_15+",";
		}
	}
	if(ops=="c7_30" ){
		count=datas[i].c7_30;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c7_30+",";
		}
	}
	if(ops=="c7_45" ){
		count=datas[i].c7_45;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c7_45+",";
		}
	}
	if(ops=="c7_59" ){
		count=datas[i].c7_59;
			if(count!=""){
		managers+=datas[i].id+"-"+datas[i].c7_59+",";
		}
	}
}
return managers
}
	 //自動加載頁面
		function timeout (){
			var date=new Date(); 
    		var h =parseInt(date.getHours()); 
    		var m = parseInt(date.getMinutes()); 
    		var url="${pageContext.request.contextPath}/wct/qm/filtrate.jsp";
		/* 	if(h==8){
			 window.location =url ;
			}else if(h==16){
			 window.location =url;
			}else if(h==24){
			 window.location =url;
			} */
		}
		setInterval('timeout()',2000);
</script>	
</head>


<body class="easyui-layout" style="background:#DDDDDD;"> 
<div id="eqpRun-title">卷接质量自检</div>
<div id="eqpRun-seach-box" style="background:#DDDDDD;">
<form id="findWork" method="post"  >
    <div data-options="region:'north',split:false,border:false" style="background:#DDDDDD;width:500px">
			<table width="400" >
				<tr>
				    <td>日期:</td>
					<td id="time1" ></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>班次:</td>
					<td id="dec1"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>班组:</td>
					<td id="des1"></td>
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
            <table id="videoTable" style="background:#DDDDDD;" >
			</table>
            </div>  
        </div>  
    </div> 
    </div>
</div>
</body> 
</html>
