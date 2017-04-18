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
.combo, .combo-panel {
background-color: white;
}
</style>
<script type="text/javascript">
		var datagrid_yanzhi=null;
		$(function(){
			datagrid_yanzhi=$("#datagrid_yanzhi").datagrid({	
				frozenColumns:[[
				                {
									field : 'id',
									title : 'id',
									width : 50,
									hidden : 'true'
								},
								{
									field : 'jycx',
									title : '检验<br>&nbsp;程序',
									width : 50,
									align : 'center'
								},{
									field : 'sjfull',
									title : '完整时间',
									width : 50,
									hidden : 'true'
								},{
									field : 'sjstr',
									title : '时间',
									width : 50,
									align : 'center'
								}]],
								columns:[[{
									field : 'zl',
									title : '重量',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',
			                                required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√',"selected":true},
										{'res':'x/√','resflag':'x/√'}
										]/* ,onLoadSuccess: function () { //加载完成后,设置选中第一项
											alert(1);
							                var val = $(this).combobox("getData");
							                for (var item in val[0]) {
							                    if (item == "res") {
							                        $(this).combobox("select", val[0][item]);
							                    }
							                }
							            } */
			                            }} 
								},{
									field : 'lq',
									title : '漏气',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√',"selected":true},
										{'res':'x/√','resflag':'x/√'}
										]
			                            }} 
								} ,{
									field : 'bk',
									title : '爆口',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'jm',
									title : '夹末',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'wzhb',
									title : '污渍<br>黄斑',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'bmzw',
									title : '表面<br>&nbsp;皱纹',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'lz',
									title : '滤嘴',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'jzz',
									title : '接装纸',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'qb',
									title : '翘边',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'qk',
									title : '切口',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'gy',
									title : '钢印',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'dt',
									title : '掉头',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								},{
									field : 'cd',
									title : '长度',
									width : 50,
									align : 'center' ,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										{'res':'x/√','resflag':'x/√'},
										]
			                            }} 
								} ]],
						 toolbar:[{
					            text:'新增',
					            iconCls:'icon-add',
					            handler:function(){
					            	append();
					            }
					        },'-',{
					            text:'取消新增',
					            iconCls:'icon-save',
					            handler:function(){
					            	remove();
					            }
					        },
					        {
					            text:'尾检',
					            iconCls:'icon-add',
					            handler:function(){
					            	appendW();
					            }
					        }
						 ,'-',{
					            text:'编辑',
					            iconCls:'icon-remove',
					            handler:function(){
					            	editRow();   	
					            }
					        },'-',{
					            text:'保存',
					            iconCls:'icon-save',
					            handler:function(){
					            	accept();
					            }
					        },'-',{
					            text:'删除',
					            iconCls:'icon-remove',
					            handler:function(){
					            	deleteData();
					            }
					        }
					        ],
						rownumbers : true,
						fitColumns : false,							
						border : false,
						singleSelect : true,
						fit : true,
						border : false,
						idField : 'id'

					});
			
		      var editIndex = undefined;
		      
		           function endEditing(){
		      
		               if (editIndex == undefined){
		            	   return true;
		               }
		      
		               if (datagrid_yanzhi.datagrid('validateRow', editIndex)){
		      
		                   datagrid_yanzhi.datagrid('endEdit', editIndex);
		      
		                   editIndex = undefined; 
		      
		                   return true;
		      
		               } else {
		      
		                   return false;
		      
		               }
		      
		           }
		      
		              function append(){
		            	  if($("#wkcod").html()==""){
		            		  jAlert('当前无运行的工单.', '系统提示');
		            		  return;
		            	  }
		            	  if (editIndex != undefined){
			            	   return;
			               }
		            	  if(datagrid_yanzhi.datagrid("getRows").length>0&&(datagrid_yanzhi.datagrid("getRows")[datagrid_yanzhi.datagrid("getRows").length-1]).jycx=="W"){
		            		  jAlert('已经尾检.', '系统提示');
		            		  return;
		            	  }
		            	  /* if(datagrid_yanzhi.datagrid("getRows")&&(datagrid_yanzhi.datagrid("getRows")[datagrid_yanzhi.datagrid("getRows").length-1]).jycx=="W"){
		            		  alert("已经尾检");
		            		  return;
		            	  } */
		            	  
		                  if (endEditing()){
		                	  var HHSS=getHHSS();
		                	  var sjfull=getSjfull();
		                	  var initcon={jycx:'',sjstr:HHSS,sjfull:sjfull,
		                	  zl:'√',  
		                	  lq:'√',  
		                	  bk:'√',  
		                	  jm:'√',  
		                	  wzhb:'√',
		                	  bmzw:'√',
		                	  lz:'√',  
		                	  jzz:'√', 
		                	  qb:'√',  
		                	  qk:'√',  
		                	  gy:'√',  
		                	  dt:'√',  
		                	  cd:'√'  	  
		                	  };
		                	  if(datagrid_yanzhi.datagrid("getRows").length==0){
		                		  initcon={jycx:'S',sjstr:HHSS,sjfull:sjfull,zl:'√',  
					                	  lq:'√',  
					                	  bk:'√',  
					                	  jm:'√',  
					                	  wzhb:'√',
					                	  bmzw:'√',
					                	  lz:'√',  
					                	  jzz:'√', 
					                	  qb:'√',  
					                	  qk:'√',  
					                	  gy:'√',  
					                	  dt:'√',  
					                	  cd:'√'};
		                	  }
		                      datagrid_yanzhi.datagrid('appendRow',initcon);
		                      editIndex = datagrid_yanzhi.datagrid('getRows').length-1;
		                      datagrid_yanzhi.datagrid('selectRow', editIndex)
		                              .datagrid('beginEdit', editIndex);
		                  }
		              }
		              
		              function appendW(){
		            	  if (editIndex != undefined){
			            	   return;
			               }
		            	  if((datagrid_yanzhi.datagrid("getRows")[datagrid_yanzhi.datagrid("getRows").length-1]).jycx=="W"){
		            		  jAlert('已经尾检.', '系统提示');
		            		  return;
		            	  }
		            	  
		                  if (endEditing()){
		                	  var HHSS=getHHSS();
		                	  var sjfull=getSjfull();
		                	  var initcon={jycx:'W',sjstr:HHSS,sjfull:sjfull,
				                	  zl:'√',  
				                	  lq:'√',  
				                	  bk:'√',  
				                	  jm:'√',  
				                	  wzhb:'√',
				                	  bmzw:'√',
				                	  lz:'√',  
				                	  jzz:'√', 
				                	  qb:'√',  
				                	  qk:'√',  
				                	  gy:'√',  
				                	  dt:'√',  
				                	  cd:'√'};
		                      datagrid_yanzhi.datagrid('appendRow',initcon);
		                      editIndex = datagrid_yanzhi.datagrid('getRows').length-1;
		                      datagrid_yanzhi.datagrid('selectRow', editIndex)
		                              .datagrid('beginEdit', editIndex);
		                  }
		              }
		              
		              
		              
		              function getHHSS(){
		            	  var hhss="00:00";
		            	  $.ajax({
		            		  url:"${pageContext.request.contextPath}/pubBasis/getCurrentTime.action",
		            		  async:false,
		            		  type:'post',
		            		  success:function(r){
		            			 hhss=r;
		            		  }
		            	  });
		            	 return hhss;
		              }
		              
		              function getSjfull(){
		            	  var date="2000-1-1 00:00:00";
		            	  $.ajax({
		            		  url:"${pageContext.request.contextPath}/pubBasis/getCurrentDateTime.action",
		            		  async:false,
		            		  type:'post',
		            		  success:function(r){
		            			 date=r;
		            		  }
		            	  });
		            	 return date;
		              }
		              
		              function remove(){
		                  if (editIndex == undefined){return;}
		                  datagrid_yanzhi.datagrid('cancelEdit', editIndex)
		                          .datagrid('deleteRow', editIndex);
		                  editIndex = undefined;
		              }
		              function accept(){
		                  if (endEditing()){
		                      datagrid_yanzhi.datagrid('acceptChanges');
		                  }else{
		                	  jAlert('自检项不能为空', '系统提示');
		                	  return;
		                  }
		                  var curRow=datagrid_yanzhi.datagrid('getSelected');
		                  var wkCod=$("#wkcod").html();
		                  $.post("${pageContext.request.contextPath}/wctQm/addYzData.action",
		                		 {
		                	  "bean.id":curRow.id,
		                	  "bean.bk":curRow.bk,
		                	      "bean.bmzw":curRow.bmzw,
		                		  "bean.cd":curRow.cd,
		                		  "bean.dt":curRow.dt,
		                		  "bean.gy":curRow.gy,
		                		  "bean.jm":curRow.jm,
		                		  "bean.jycx":curRow.jycx,
		                		  "bean.jzz":curRow.jzz,
		                		  "bean.lq":curRow.lq,
		                		  "bean.lz":curRow.lz,
		                		  "bean.qb":curRow.qb,
		                		  "bean.qk":curRow.qk,
		                		  "bean.sjfull":curRow.sjfull,
		                		  "bean.wzhb":curRow.wzhb,
		                		  "bean.zl":curRow.zl,
		                		  "bean.wkCod":wkCod},function(success){
		                	  if(success=="true"){
		                		  loadQmData(wkCod);
		                	  }else{
		                		  alert(false);
		                	  }
		                	  
		                  });
		                  
		              }
		              function reject(){
		                  datagrid_yanzhi.datagrid('rejectChanges');
		                  editIndex = undefined;
		              }
		              function getChanges(){
		                  var rows = datagrid_yanzhi.datagrid('getChanges');
		              }
		              
		              /*编辑明细数据*/
		     	     function editRow(){
		    	 		 var curRow=datagrid_yanzhi.datagrid('getSelected');
		    	 		 var index=datagrid_yanzhi.datagrid('getRowIndex',curRow);
		    	         if(index==-1){
		    	        	 jAlert('请选择一行数据.', '系统提示');
		    	        	 return;
		    	         } 
		    	 		 if (editIndex != index){
		    	              if (endEditing()){
		    	            	  datagrid_yanzhi.datagrid('selectRow', index)
		    	                          .datagrid('beginEdit', index);
		    	                  editIndex = index;
		    	              } else {
		    	            	  datagrid_yanzhi.datagrid('selectRow', editIndex);
		    	              }
		    	          }
		    	      }
		     	    var loadRollerWkOrder=function(){
		     	    	var rid="${loginGroup.eqps['roller'].id}";
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
		    				if(wkcod){		    					
		    				   loadQmData(wk.cod);
		    				}
		    			});
		    		};
		    		
		    	    loadRollerWkOrder();
		    	    
		    	    var loadQmData=function(wkcod){
		    	    	datagrid_yanzhi.datagrid({
		    				url : "${pageContext.request.contextPath}/wctQm/listYzData.action",
		    				queryParams : {"bean.wkCod":wkcod},
		    				onLoadError : function(data) {
		    					jAlert('加载质检数据异常.', '系统提示');
		    				}
		    			});
		    	    };
		    	    
		    	    var deleteData=function(){
		    	    	var curRow=datagrid_yanzhi.datagrid('getSelected');
		    	    	if(curRow){
		    	    		 $.post("${pageContext.request.contextPath}/wctQm/delYzData.action",
			                		 {"bean.id":curRow.id},function(v){
			                		  if(v=="true"){
			                			  loadQmData($("#wkcod").html());
			                		  }else{
			                			  return;
			                		  }
			                	  });
		    	    	}
		    	    };
		    	    

		});
</script>	
</head>


<body class="easyui-layout" style="background:#DDDDDD;"> 
<div id="eqpRun-title">卷接工序烟支质量自检</div>
<div id="eqpRun-seach-box" style="background:#DDDDDD;">
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
<div id="eqpRunSta-tab">
<div class="easyui-layout" data-options="fit:true" style="border-radius: 4px;">
 <div data-options="region:'center',split:false,border:false" style="background:#DDDDDD;">  
        <div class="easyui-layout" data-options="fit:true,split:false,border:false" style="background:#DDDDDD;">  
            <div data-options="region:'center',split:false,border:false" style="background:#DDDDDD;">
            <table id="datagrid_yanzhi" style="background:#DDDDDD;" >
			</table>
            </div>  
        </div>  
    </div> 
    </div>
</div>
</body> 
</html>
