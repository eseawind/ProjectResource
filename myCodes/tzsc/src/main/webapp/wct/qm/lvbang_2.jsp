<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>滤棒自检 by LEE</title>
<jsp:include page="../../js/initjsp/initEasyui.jsp"></jsp:include>
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
.combo, .combo-panel {
background-color: white;
}
</style>
<script type="text/javascript">
		var datagrid_lvbang=null;
		$(function(){
			datagrid_lvbang=$("#datagrid_lvbang").datagrid({
								columns:[[{
									field : 'id',
									title : 'id',
									width : 50,
									hidden : 'true',
								    rowspan : 2
								},
								{
									field : 'jycx',
									title : '检验<br>程序',
									width : 30,
									align : 'center',
								    rowspan : 2
								},{
									field : 'sjfull',
									title : '完整时间',
									hidden : 'true',
								    rowspan : 2
								},{
									field : 'sjstr',
									title : '时间',
									width : 40,
									align : 'center',
								    rowspan : 2
								},{
									field : 'zl',
									title : '重量',
									width : 40,
								    rowspan : 2,
									align : 'center',
									editor:{
				                        type:'numberbox',required:true,
				                        options:{min:0,  
				                            precision:3}
				                }
								},{
									title : '吸阻(Pa)',
									align : 'center',
									colspan:3
								},{
									title : '圆周(mm)',
									align : 'center',
									colspan:3
								},{
									title : '长度(mm)',
									align : 'center',
									colspan:3
								},{
									title : '圆度',
									align : 'center',
									colspan:3
								},{
									field : 'cd',
									title : '长度',
									align : 'center',
									width : 45,
									hidden:true,
									rowspan:2,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										//{'res':'x/√','resflag':'x/√'}
										]
			                            }} 
								},{
									field : 'yd',
									title : '圆度',
									width : 45,
									hidden:true,
									align : 'center',
									rowspan:2,editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'res',
			                                textField:'resflag',
			                                panelHeight:'auto',required:true,
			                                data:[
										{'res':'x','resflag':'x'},
										{'res':'√','resflag':'√'},
										//{'res':'x/√','resflag':'x/√'}
										]
			                            }} 
								},{
									title : ' 外观',
									width : 90,
									align : 'center',
									colspan:6
								}],[
										{
											field : 'XPjz',
											title : '平均值',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                } 
										},
										{
											field : 'XBzpc',
											title : '标准偏差',
											width : 55,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                }
										},
										{
											field : 'XCbs',
											title : '超标数',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:0}
						                } 
										},
										{
											field : 'YPjz',
											title : ' 平均值',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                }
										},
										{
											field : 'YBzpc',
											title : '标准偏差',
											width : 55,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                }
										},
										{
											field : 'YCbs',
											title : '超标数',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:0}
						                } 
										},
										{
											field : 'CDPjz',
											title : ' 平均值',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                }
										},
										{
											field : 'CDBzpc',
											title : '标准偏差',
											width : 55,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                }
										},
										{
											field : 'CDCbs',
											title : '超标数',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:0}
						                } 
										},
										{
											field : 'YDPjz',
											title : ' 平均值',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                }
										},
										{
											field : 'YDBzpc',
											title : '标准偏差',
											width : 55,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:3}
						                }
										},
										{
											field : 'YDCbs',
											title : '超标数',
											width : 50,
											align : 'center',
											editor:{
						                        type:'numberbox',required:true,
						                        options:{min:0,  
						                            precision:0}
						                } 
										},
										{
											field : 'bk',
											title : '爆口',
											width : 45,
											align : 'center',editor:{
					                            type:'combobox',
					                            options:{
					                                valueField:'res',
					                                textField:'resflag',
					                                panelHeight:'auto',required:true,
					                                data:[
												{'res':'x','resflag':'x'},
												{'res':'√','resflag':'√'},
												//{'res':'x/√','resflag':'x/√'}
												]
					                            }} 
										},
										{
											field : 'st',
											title : '缩头',
											width : 45,
											align : 'center',editor:{
					                            type:'combobox',
					                            options:{
					                                valueField:'res',
					                                textField:'resflag',
					                                panelHeight:'auto',required:true,
					                                data:[
												{'res':'x','resflag':'x'},
												{'res':'√','resflag':'√'},
												//{'res':'x/√','resflag':'x/√'}
												]
					                            }} 
										},
										{
											field : 'qk',
											title : '切口',
											width : 45,
											align : 'center',editor:{
					                            type:'combobox',
					                            options:{
					                                valueField:'res',
					                                textField:'resflag',
					                                panelHeight:'auto',required:true,
					                                data:[
												{'res':'x','resflag':'x'},
												{'res':'√','resflag':'√'},
												//{'res':'x/√','resflag':'x/√'}
												]
					                            }} 
										},
										{
											field : 'zjx',
											title : '中胶线',
											align : 'center',editor:{
					                            type:'combobox',
					                            options:{
					                                valueField:'res',
					                                textField:'resflag',
					                                panelHeight:'auto',required:true,
					                                data:[
												{'res':'x','resflag':'x'},
												{'res':'√','resflag':'√'},
												//{'res':'x/√','resflag':'x/√'}
												]
					                            }} 
										},
										{
											field : 'dk',
											title : '搭口',
											width : 45,
											align : 'center',editor:{
					                            type:'combobox',
					                            options:{
					                                valueField:'res',
					                                textField:'resflag',
					                                panelHeight:'auto',required:true,
					                                data:[
												{'res':'x','resflag':'x'},
												{'res':'√','resflag':'√'},
												//{'res':'x/√','resflag':'x/√'}
												]
					                            }} 
										},
										{
											field : 'qt',
											title : '其他',
											width : 45,
											align : 'center',editor:{
					                            type:'combobox',
					                            options:{
					                                valueField:'res',
					                                textField:'resflag',
					                                panelHeight:'auto',required:true,
					                                data:[
												{'res':'x','resflag':'x'},
												{'res':'√','resflag':'√'},
												//{'res':'x/√','resflag':'x/√'}
												]
					                            }} 
										}
								    ]],
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
						width:820,
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
		      
		               if (datagrid_lvbang.datagrid('validateRow', editIndex)){
		      
		                   datagrid_lvbang.datagrid('endEdit', editIndex);
		      
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
		            	  if(datagrid_lvbang.datagrid("getRows").length>0&&(datagrid_lvbang.datagrid("getRows")[datagrid_lvbang.datagrid("getRows").length-1]).jycx=="W"){
		            		  jAlert('已经尾检.', '系统提示');
		            		  return;
		            	  }
		            	  /* if(datagrid_lvbang.datagrid("getRows")&&(datagrid_lvbang.datagrid("getRows")[datagrid_lvbang.datagrid("getRows").length-1]).jycx=="W"){
		            		  alert("已经尾检");
		            		  return;
		            	  } */
		            	  
		                  if (endEditing()){
		                	  var HHSS=getHHSS();
		                	  var sjfull=getSjfull();
		                	  var initcon={jycx:'',sjstr:HHSS,sjfull:sjfull,
		                	  cd:'√',
		                	  yd:'√',
		                	  bk:'√',
		                	  st:'√',
		                	  qk:'√',
		                	  zjx:'√',
		                	  dk:'√',
		                	  qt:'√'	  
		                	  };
		                	  if(datagrid_lvbang.datagrid("getRows").length==0){
		                		  initcon={jycx:'S',sjstr:HHSS,sjfull:sjfull,
		                				  cd:'√',
		    		                	  yd:'√',
		    		                	  bk:'√',
		    		                	  st:'√',
		    		                	  qk:'√',
		    		                	  zjx:'√',
		    		                	  dk:'√',
		    		                	  qt:'√' 	  
		                		  };
		                	  }
		                      datagrid_lvbang.datagrid('appendRow',initcon);
		                      editIndex = datagrid_lvbang.datagrid('getRows').length-1;
		                      datagrid_lvbang.datagrid('selectRow', editIndex)
		                              .datagrid('beginEdit', editIndex);
		                  }
		                  $(".datagrid-editable-input").keyboard({
		          			layout:"1234567890.",
		        			customLayout: {
		        				'default': [
		        					"1 2 3 4 5 6 {bksp}",
		        					"7 8 9 0 . - {accept}"
		        				]
		        			}
		        		});
		              }
		              
		              function appendW(){
		            	  if (editIndex != undefined){
			            	   return;
			               }
		            	  if((datagrid_lvbang.datagrid("getRows")[datagrid_lvbang.datagrid("getRows").length-1]).jycx=="W"){
		            		  jAlert('已经尾检.', '系统提示');
		            		  return;
		            	  }
		            	  
		                  if (endEditing()){
		                	  var HHSS=getHHSS();
		                	  var sjfull=getSjfull();
		                	  var initcon={jycx:'W',sjstr:HHSS,sjfull:sjfull,
		                			  cd:'√',
				                	  yd:'√',
				                	  bk:'√',
				                	  st:'√',
				                	  qk:'√',
				                	  zjx:'√',
				                	  dk:'√',
				                	  qt:'√'	  
		                	  };
		                      datagrid_lvbang.datagrid('appendRow',initcon);
		                      editIndex = datagrid_lvbang.datagrid('getRows').length-1;
		                      datagrid_lvbang.datagrid('selectRow', editIndex)
		                              .datagrid('beginEdit', editIndex);
		                  }
		                  $(".datagrid-editable-input").keyboard({
		          			layout:"1234567890.",
		        			customLayout: {
		        				'default': [
		        					"1 2 3 4 5 6 {bksp}",
		        					"7 8 9 0 . - {accept}"
		        				]
		        			}
		        		});
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
		                  datagrid_lvbang.datagrid('cancelEdit', editIndex)
		                          .datagrid('deleteRow', editIndex);
		                  editIndex = undefined;
		              }
		              function accept(){
		                  if (endEditing()){
		                      datagrid_lvbang.datagrid('acceptChanges');
		                  }else{
		                	  jAlert('自检项不能为空', '系统提示');
		                	  return;
		                  }
		                  var curRow=datagrid_lvbang.datagrid('getSelected');
		                  var wkCod=$("#wkcod").html();
		                  $.post("${pageContext.request.contextPath}/wctQm/addLbData.action",
		                		 {
		                	      "bean.id":curRow.id,
		                		  "bean.jycx":curRow.jycx,
		                		  "bean.sjfull":curRow.sjfull,
		                	      "bean.zl":curRow.zl,
		                		  "bean.XPjz":curRow.XPjz,
		                		  "bean.XBzpc":curRow.XBzpc,
		                		  "bean.XCbs":curRow.XCbs,
		                		  "bean.YPjz":curRow.YPjz,
		                		  "bean.YBzpc":curRow.YBzpc,
		                		  "bean.YCbs":curRow.YCbs,
		                		  "bean.YDPjz":curRow.YDPjz,
		                		  "bean.YDBzpc":curRow.YDBzpc,
		                		  "bean.YDCbs":curRow.YDCbs,
		                		  "bean.CDPjz":curRow.CDPjz,
		                		  "bean.CDBzpc":curRow.CDBzpc,
		                		  "bean.CDCbs":curRow.CDCbs,
		                		  "bean.cd":curRow.cd,
		                		  "bean.yd":curRow.yd,
		                		  "bean.bk":curRow.bk,
		                		  "bean.st":curRow.st,
		                		  "bean.qk":curRow.qk,
		                		  "bean.zjx":curRow.zjx,
		                		  "bean.dk":curRow.dk,
		                		  "bean.qt":curRow.qt,
		                		  "bean.wkCod":wkCod},function(success){
		                	  if(success=="true"){
		                		  loadQmData(wkCod);
		                	  }else{
		                	  }
		                	  
		                  });
		                  
		              }
		              function reject(){
		                  datagrid_lvbang.datagrid('rejectChanges');
		                  editIndex = undefined;
		              }
		              function getChanges(){
		                  var rows = datagrid_lvbang.datagrid('getChanges');
		              }
		              
		              /*编辑明细数据*/
		     	     function editRow(){
		    	 		 var curRow=datagrid_lvbang.datagrid('getSelected');
		    	 		 var index=datagrid_lvbang.datagrid('getRowIndex',curRow);
		    	         if(index==-1){
		    	        	 jAlert('请选择一行数据.', '系统提示');
		    	        	 return;
		    	         } 
		    	 		 if (editIndex != index){
		    	              if (endEditing()){
		    	            	  datagrid_lvbang.datagrid('selectRow', index)
		    	                          .datagrid('beginEdit', index);
		    	                  editIndex = index;
		    	              } else {
		    	            	  datagrid_lvbang.datagrid('selectRow', editIndex);
		    	              }
		    	          }
		    	 		 $(".datagrid-editable-input").keyboard({
		    	 			layout:"1234567890.",
		    				customLayout: {
		    					'default': [
		    						"1 2 3 4 5 6 {bksp}",
		    						"7 8 9 0 . - {accept}"
		    					]
		    				}
		    			});
		    	      }
		     	    var loadRollerWkOrder=function(){
		     	    	var rid="${loginGroup.eqps['filer2'].id}";
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
		    	    	datagrid_lvbang.datagrid({
		    				url : "${pageContext.request.contextPath}/wctQm/listLbData.action",
		    				queryParams : {"bean.wkCod":wkcod},
		    				onLoadError : function(data) {
		    					jAlert('加载质检数据异常.', '系统提示');
		    				}
		    			});
		    	    };
		    	    
		    	    var deleteData=function(){
		    	    	var curRow=datagrid_lvbang.datagrid('getSelected');
		    	    	if(curRow){
		    	    		 $.post("${pageContext.request.contextPath}/wctQm/delLbData.action",
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
<div id="eqpRun-title">${loginGroup.eqps['filer2'].des}滤棒质量自检</div>
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
            <table id="datagrid_lvbang" style="background:#DDDDDD;" >
			</table>
            </div>  
        </div>  
    </div> 
    </div>
</div>
</body> 
</html>
