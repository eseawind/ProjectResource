<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
	#gczj-tab-thead tr td{text-align:center;}
	#gczj-tab-tbody tr{height:30px;text-align:center;font-size:12px;}
</style>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript" ></script>
<script type="text/javascript">
	/*var arryKey、arryVal、qxKey、qxVal、csKey、csVal这些是公共的属性*/
	var formattedtime=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var formattedtime2=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})-(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var flag;
	var inserttime;
	var gczjChangeClick =null;	
	var gczjChangeType ="init";
	var qty0=0;//产品段
	function getPackageData(){
		$.post("${pageContext.request.contextPath}/wct/isp/boxer/getBoxerData.do",{"equipmentCode":"${loginInfo.equipmentCode}"},function(json){
				qty0=json.qty;
				},"JSON");
	}
	$(function(){
		//登录验证
		var roleId=$("#tfRole:hidden").val();
		var roles="${loginWctZjInfo.roles}";
		if(roles==""){
			$("#hid_div3").show();
		}else{
			/*
			*   页面加载，确定当前角色
                                                            操作工 = 8af2d43f4d73d86d014d73df6da90000
                                                            挡车工 = 8af2d4904fce586a014fd4a7f08e01a4
			*/
			console.info(roleId);
			if(roles.indexOf(roleId)==-1 ){
				//非操作工，弹出登录框
				$("#hid_div3").show();
				//发送提醒信息 
				$("#errorNum").text("该用户没有分配角色权限!");
				$("#cardNum").focus();
			}
		}
		//默认光标选中文本框
	    $("#cardNum").focus();
		getPackageData();
		//checked全选、反选事件
        $("#gczj-tab-thead-CKA").click(function() {
        	$("#gczj-tab-tbody tr").each(function(idx) {
           		if($(this).find("#gczj-tab-tbody-CK").get(0)!=null
                   	&&$(this).find("#gczj-tab-tbody-CK").get(0)!=undefined){
           			$(this).find("#gczj-tab-tbody-CK").get(0).checked = 
               			$("#gczj-tab-thead-CKA").get(0).checked;
               }
			});
        });
        gczjChangeClick=function(type){
			//编辑事件
			$("#gczj-tab-tbody tr").die().live("click",function(){
				var tr=$(this);
				if(tr.attr("edit")){
					return;
				};
				$(this).find("#gczj-tab-tbody-CK").get(0).checked=true;//选中当前行
				tr.attr("edit",true);
				tr.find("td").each(function(index){
	            	var oldText=$(this).text();//原来的值   5
	            	var zjId = tr.find("#gczj-tab-tbody-CK").get(0).value;//如果有 就表示 数据库数据
	            	//根据key 或 value 赋值
	            	if(index==2){
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classIds = formId+classId;
	            		var se2="<input type='text' class='"+classIds+"' style='width:100%;height:30px;' value='"+oldText+"' />";
	                   	$(this).html(se2);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - {bksp}"
	        					]
	        				}
	        			});	
	            	}
	              
	            	 if(index==3){
	            		 if(oldText==""){
	            			oldText=qty0; 
	            		 }
	            		 var formId="form-control ";
					     var classId="zl-keyboard-"+index+"td"+index;
					     var classKey=".zl-keyboard-"+index+"td"+index;
					     var classIds = formId+classId;
		                 var se2="<input type='text' class='"+classIds+"' onkeyup='checkInput(this)' style='width:100%;height:30px;' value='"+oldText+"''/>";
		                 $(this).html(se2);
		                 $(classKey).keyboard({
		        				layout:"1234567890:",
		        				customLayout: {
		        					'default': [
		        						"{accept} 1 2 3 4 5 6 ",
		        						"7 8 9 0 : {bksp}"
		        					]
		        				}
		        		});	
		                }
					if(index==4){//外观质量情况
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(4) select").val();
			            }
	                    var se4="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<qx_carton_key.length;i++ ){
	                        if(qx_carton_key[i]==oldText||qx_carton_val[i]==oldText){
	                        	se4+="<option value='"+qx_carton_key[i]+"' selected>"+qx_carton_val[i]+"</option>" ;
	                        }else{
	                        	se4+="<option value='"+qx_carton_key[i]+"' >"+qx_carton_val[i]+"</option>";
	                        }
	                    }
	                    se4+="</select>";
	                   $(this).html(se4);
					}
					if(index==5){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(5) select").val();
			            }
	              		var se5="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<isTrueKey.length;i++ ){
	                    	if(isTrueKey[i]==oldText||isTrueVal[i]==oldText){
	                   		 se5+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
	                        }else{
	                       	 se5+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
	                        }
	                    }
	                    se5+="</select>";
	                	$(this).html(se5);
					}
					if(index==6){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(6) select").val();//	当前第4列 	重量g/20支
			            }
	              		 var se6="<select style='width:100%;height:30px;'>";
		                    for(var i=0;i<isFjKey.length;i++ ){
		                        if(isFjKey[i]==oldText||isFjVal[i]==oldText){
		                        	se6+="<option value='"+isFjKey[i]+"' selected>"+isFjVal[i]+"</option>" ;
		                        }else{
		                        	se6+="<option value='"+isFjKey[i]+"' >"+isFjVal[i]+"</option>";
		                        }
		                    }
		                    se6+="</select>";
		                   $(this).html(se6);
					}
					if(index==7){//处理措施
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(7) select").val();
			            }
	                    var se7="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<csKey.length;i++ ){
	                        if(csKey[i]==oldText||csVal[i]==oldText){
	                        	se7+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
	                        }else{
	                        	se7+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
	                        }
	                    }
	                    se7+="</select>";
	                   $(this).html(se7);
					}
					if(index==8){
						var formId="form-control ";
					    var classId="zl-keyboard-"+index+"td"+index;
					    var classKey=".zl-keyboard-"+index+"td"+index;
					    var classIds = formId+classId;
						var se8="<input type='text' class='"+classIds+"' onkeyup='checkInput(this)' style='width:100%;height:30px;' value='"+oldText+"''/>";
	                   	$(this).html(se8);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : {bksp}"
	        					]
	        				}
	        			});	
					} 
					if(index==9){
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(9) select").val();
			            }
	                    var se9="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<sealer_unit_key.length;i++ ){
	                        if(sealer_unit_key[i]==oldText||sealer_unit_val[i]==oldText){
	                        	se9+="<option value='"+sealer_unit_key[i]+"' selected>"+sealer_unit_val[i]+"</option>" ;
	                        }else{
	                        	se9+="<option value='"+sealer_unit_key[i]+"' >"+sealer_unit_val[i]+"</option>";
	                        }
	                    }
	                    se9+="</select>";
	                   $(this).html(se9);
					} 
					 if(index==10){
						 if(zjId=="" || zjId==undefined){
							 oldText=tr.find("td:eq(10) select").val();
							 }	
							 var se11="<select style='width:100%;height:30px;'>";
							 for(var i=0;i<standkey.length;i++){
								 if(standval[i]==oldText || commentskey[i]==oldText){
									 se11+="<option value'"+standval[i]+"' selected>"+standkey[i]+"</option>";
								 }else{
									 se11+="<option value='"+standval[i]+"'>"+standkey[i]+"</option>";
								 }
							 }
							 se11+="</select>";
							 $(this).html(se11);
					} 
					
	            }); 
	        });
        }
       gczjChangeClick("load");//供编辑用
	});
	//查询
	function gczjSearch(){
		gczjChangeType ="query";
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
		$("#gczj-tab-thead-CKA").get(0).checked = false;//顶部checkbox为不选状态
		var params ={proWorkId : schWorkorderId,processType:'F'}//工单主键ID
		$.post("${pageContext.request.contextPath}/wct/massProcess/getList.do",params,function(data){
				var html="";
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var prodPart="";//产品段
						var runCondition="";//外观质量情况
						var isError="";//判断
						var isAgain="";//复检
						var runStep="";//处理措施
						var badNum="";//质量问题数量
						var numUnit="";//质量问题数量单位
						//根据 key 或 value找 value
						for(var j=0;j<qx_carton_key.length;j++ ){//外观质量情况
							if(qx_carton_key[j]==revalue.runCondition||qx_carton_val[j]==revalue.runCondition){
								runCondition=qx_carton_val[j];
								 break;
							 }
						} 
						for(var j=0;j<isTrueKey.length;j++ ){//判断
							if(isTrueKey[j]==revalue.isError||isTrueVal[j]==revalue.isError){
								isError=isTrueVal[j];
								 break;
							 }
						} 
						for(var j=0;j<isFjKey.length;j++ ){//复检
							if(isFjKey[j]==revalue.isAgain||isFjVal[j]==revalue.isAgain){
								isAgain=isFjVal[j];
								 break;
							 }
						}
						for(var j=0;j<csKey.length;j++ ){//处理措施
							if(csKey[j]==revalue.runStep||csVal[j]==revalue.runStep){
								runStep=csVal[j];
								 break;
							 }
						}
						
						for(var j=0;j<sealer_unit_key.length;j++ ){
							if(sealer_unit_key[j]==revalue.numUnit||sealer_unit_val[j]==revalue.numUnit){
								numUnit=sealer_unit_val[j];
								break;
							}
						}
						html+="<tr>"
							+"	<td>"
							+"		<input value="+revalue.qmProcessId+" type='checkbox' name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td width='30px'>"+(i+1)+"</td>"
							+"	<td width='90px'>"+revalue.shortTime+"</td>"
							+"	<td width='40px'>"+revalue.prodPart+"</td>"	//产品段
							+"	<td width='80px'>"+runCondition+"</td>"
							+"	<td width='30px'>"+isError+"</td>"
							+"	<td width='40px'>"+isAgain+"</td>"
							+"	<td width='80px'>"+runStep+"</td>"
							+"	<td width='40px'>"+revalue.badNum+"</td>"
							+"	<td width='40px'>"+numUnit+"</td>"
							+"	<td width='80px'>"+revalue.runRemark+"</td>"
							+"</tr>";
					}
				}else{}
				$("#gczj-tab-tbody").html(html);
			},"JSON");

	}				
	//新增
	function gczjInsert(){
		gczjChangeType ="insert";
		var zjy =$("#isMassCheckZjr").text();
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
	    if(zjy!='true'){
			jAlert("自检员不可为空,请先登录!","提示");
			return false;
		}
		//初始化时间
	   
		var nowDate = getDateHourAndMinute();

		var trLength=$("#gczj-tab-tbody tr").length;//获取tr的行数
		var se3="<input value='"+qty0+"'></input>";
        var formId="form-control ";
        var classId="zl-keyboard-"+trLength+"td4";
        var classIds =formId+classId;
         var se4="<select width='80px'>";
         for(var i=0;i<qxKey.length;i++ ){
        	 if(i==0){
        		 se4+="<option value='"+qxKey[i]+"' selected>"+qxVal[i]+"</option>" ;
             }else{
            	 se4+="<option value='"+qxKey[i]+"' >"+qxVal[i]+"</option>";
             }
         }
         se4+="</select>";

         var se5="<select width=30px>";
         for(var i=0;i<isTrueKey.length;i++ ){
        	 if(i==0){
        		 se5+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
             }else{
            	 se5+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
             }
         }
         se5+="</select>";
		
         var se6="<select width=30px>";
         for(var i=0;i<isFjKey.length;i++ ){
        	 if(i==0){
        		 se6+="<option value='"+isFjKey[i]+"' selected>"+isFjVal[i]+"</option>" ;
             }else{
            	 se6+="<option value='"+isFjKey[i]+"' >"+isFjVal[i]+"</option>";
             }
         }
         se6+="</select>";
           
		 var se7="<select width='80px'>";
         for(var i=0;i<csKey.length;i++ ){
        	 if(i==0){
        		 se7+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
             }else{
            	 se7+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
             }
         }
         se7+="</select>";

  	 	var se8="<input name='badNumCheck' width='40px'>0</input>";
	  	var se9="<select width='40px'>";
	      for(var i=0;i<sealer_unit_key.length;i++ ){
	     	 if(i==0){
	     		 se9+="<option value='"+sealer_unit_key[i]+"' selected>"+sealer_unit_val[i]+"</option>" ;
	          }else{
	         	 se9+="<option value='"+sealer_unit_key[i]+"' >"+sealer_unit_val[i]+"</option>";
	          }
	      }
	    se9+="</select>";
	    
  	 	var se10="<select width='40px'>";
  	 	for(var i=0;i<standkey.length;i++){
  	 		if(i==0){
  	 			se10+="<option value'"+standval[i]+"' selected>"+standkey[i]+"</option>";
  	 		}else{
  	 			se10+="<option value='"+standval[i]+"'>"+standkey[i]+"</option>";
  	 		}
  	 	}
  	 	se10+="</select>";
		var html="<tr>"
			+"	<td>"
			+"		<input value='' type='checkbox' name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
			+"	</td>"
			+"	<td width='30px'>"+(trLength+1)+"</td>"  //序号
			+"	<td width='90px'>"+nowDate+"</td>"		//时间
			+"	<td width='30px'>"+se3+"</td>"			//产品段
			+"	<td width='80px'>"+se4+"</td>"			//外观质量情况
			+"	<td width='30px'>"+se5+"</td>"			//判断
			+"	<td width='40px'>"+se6+"</td>"	//复检	
			+"	<td width='80px'>"+se7+"</td>"	//处理措施
			+"	<td width='30px'>"+se8+"</td>"	//残品数量	
			+"	<td width='30px'>"+se9+"</td>"	//数量单位	
			+"	<td width='80px'>"+se10+"</td>"	//备注	
			+"</tr>";
			$("#gczj-tab-tbody").append(html);
			var trRow="#gczj-tab-tbody tr:eq("+(trLength)+")";//当前行
			$(trRow).click();//行 获取焦点
			getPackageData();
	}
	//修改
	function gczjUpdate(){
		gczjChangeType ="update";
	}
	//删除
	function gczjDeletes(){
		gczjChangeType ="delete";
		var zjy =$("#isMassCheckZjr").text();
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
		if(zjy!='true'){
			jAlert("自检员不可为空,请先登录!","提示");
			return false;
		}
		 var ids="";
		 $("#gczj-tab-tbody tr").each(function(idx) {
            var trCk = $(this).find("#gczj-tab-tbody-CK").get(0);//checkbox对象
         	if(trCk!=null&&trCk!=undefined&&trCk.checked){
         		ids +=trCk.value+",";
             }
         });
         if(ids==""){
        	 jAlert("请选择需要删除的记录","删除");
             return false;
         }
         jConfirm('删除提示?', '标题', function(r) {
 			if(r){
 				 //跑到后台处理后 并查询
 				$.ajax({
 		            type: "post",//使用get方法访问后台
 		            dataType: "json",//返回json格式的数据
 		            url: "${pageContext.request.contextPath}/wct/massProcess/deleteByIds.do",//要访问的后台地址
 		            data: "ids=" + ids,//要发送的数据
 		            complete :function(){
 		                //$("#load").hide();
 		            },//AJAX请求完成时隐藏loading提示
 		            success : function(r) {
 						if(r.success){
 							gczjSearch();//重新查询下
 						}else{ 
 							jAlert("删除失败!","提示");
 						}
 					}
 		         });
 			}
 		 });
	}

	//保存
	function gczjSave(){
		gczjChangeType ="save";
		var zjy =$("#isMassCheckZjr").text();
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
		if(zjy!='true'){
			jAlert("自检员不可为空,请先登录!","提示");
			return false;
		}
		var massCheckBc =$("#massCheckBc").text();
		var massCheckJth =$("#massCheckJth").text();
		var massCheckPm =$("#massCheckPm").text();
		var massCheckDcg =$("#massCheckDcg").text();
		var zjUserName =$("#massCheckZjr").text();//质检人
		var _eqpId=$('#eqpId').text();//设备id
		var _matId=$('#matId').text();//牌号id
		var _shiftID=$('#shiftID').text();
		var checkString = '[';
			checkString += '{"proWorkId":"'+schWorkorderId 
			+ '","mdShiftName":"'+ massCheckBc//班次
			+ '","mdEqmentName":"'+ massCheckJth//机台名
			+ '","mdEqmentId":"'+ _eqpId//机台id
			+ '","mdMatId":"'+ _matId//牌号id
			+ '","mdShiftId":"'+ _shiftID//牌号id
			+ '","mdMatName":"'+ massCheckPm//牌号
			+ '","mdDcgName":"'+ massCheckDcg//挡车工
			+ '","zjUserName":"'+ zjUserName//质检人
			+ '"}';
			checkString += ']';
		var reqString = '[';
		var isNextNumber= 0;
		 $("#gczj-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#gczj-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键，判断更新或者新增
	        		 var td1 = tr.find("td:eq(1)").text();//当前第1列 序号
	        		 var td2 = tr.find("td:eq(2)").text();//当前第2列  时间
	        		 var td3 = "";//当前第3列 	产品段 select
					 var td4 = "";//当前第4列 	外观质量情况 select
					 var td5 = "";//当前第5列 	判断    select
					 var td6 = "";//当前第6列 	复检    select
					 var td7 = "";//当前第7列 	处理措施    select
					 var td8="";//残品数量
					 var td9="";//残品单位
					 var td10="";//备注
					 inserttime=td2;
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 td2 = tr.find("td:eq(2) input").val();
						 td3 = tr.find("td:eq(3) input").val();
						 td4 = tr.find("td:eq(4) select").val();
						 td5 = tr.find("td:eq(5) select").val();
						 td6 = tr.find("td:eq(6) select").val();
						 td7 = tr.find("td:eq(7) select").val();
						 td8 = tr.find("td:eq(8) input").val();
						 td9=tr.find("td:eq(9) select").val();
						 td10 = tr.find("td:eq(10) select").val();
						 inserttime=td2;
					 }else{
						 td3 = tr.find("td:eq(3)").text();
						 td4 = tr.find("td:eq(4)").text();
						 td5 = tr.find("td:eq(5)").text();
						 td6 = tr.find("td:eq(6)").text();
						 td7 = tr.find("td:eq(7)").text();
						 td8 = tr.find("td:eq(8)").text();
						 td9 = tr.find("td:eq(9)").text();
						 td10 = tr.find("td:eq(10)").text();//这列不可以修改
						 //根据中文找 key
					 	for(var j=0;j<qxKey.length;j++ ){//外观质量情况 select
							if(qxVal[j]==td4){
								td4=qxKey[j];
								 break;
							 }
						}
						for(var j=0;j<isTrueKey.length;j++ ){//判断    select
							if(isTrueVal[j]==td5){
								td5=isTrueKey[j];
								 break;
							 }
						}
						for(var j=0;j<isFjKey.length;j++ ){//复检    select
							if(isFjVal[j]==td6){
								td6=isFjKey[j];
								 break;
							 }
						}
						for(var j=0;j<csKey.length;j++ ){//处理措施    select
							if(csVal[j]==td7){
								td7=csKey[j];
								 break;
							 }
						}
						for(var j=0;j<sealer_unit_key.length;j++ ){//处理措施    select
							if(sealer_unit_val[j]==td9){
								td9=sealer_unit_key[j];
								 break;
							 }
						}
					 }
					 
					 if(formattedtime.test(inserttime) || formattedtime2.test(inserttime)){
						 flag=true;
					 }else{
						 flag=false;
					 }  
				     reqString += '{"qmProcessId":"'+td0//主键
				    	+ '","shortTime":"'+ td2//页面显示的录入时间
				    	+ '","processType":"'+ 'F'//操作类型 D挡车工 C操作工
						+ '","prodPart":"'+ td3//产品段
						+ '","runCondition":"'+ td4//外观质量情况
						+ '","isError":"'+ td5//判断
						+ '","isAgain":"'+ td6//复检
						+ '","runStep":"'+ td7//处理措施
						+ '","badNum":"'+ td8//质量问题数量
						+ '","numUnit":"'+ td9//质量问题数量单位
						+ '","runRemark":"'+ td10//备注
						+ '","proWorkId":"'+schWorkorderId //工单主键
						+ '"}';
						reqString += ',';
						isNextNumber++;
						
	            }
	        });
	   	if(reqString!="["){
	   		reqString = reqString.substring(0,(reqString.length-1));
		}else{
			jAlert("请选择需要保存的记录","保存");
			return false;
		}
		reqString += ']';
	   	
		if(!flag){
			jAlert("请输入正确格式的时间,如 0"+inserttime,"温馨提示");
			return false;
		}
	 	var params =reqString;//{eqpCod:equ}
        //跑到后台处理后 并查询
         $.ajax({
            type: "post",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${pageContext.request.contextPath}/wct/massProcess/editBean.do",//要访问的后台地址
            data: "processArray=" + reqString+"&checkArray="+checkString,//要发送的数据
            complete :function(){
                //$("#load").hide();
            },//AJAX请求完成时隐藏loading提示
            success : function(r) {
				if(r.success){
					gczjSearch();//重新查询下
				}else{
					jAlert("保存失败!","提示");
				}
			}
         });
	}
	
</script>

</head>
<title>过程自检_1</title>
	<!-- 内容 -->
	<table border="1" borderColor="#9a9a9a"
		style="BORDER-COLLAPSE: collapse; font-size: 12px; font-weight: 500;margin-bottom:300px;"
		width="100%"  cellspacing="0" cellpadding="0">
		<thead id="gczj-tab-thead"
			style="background: #5a5a5a; color: #fff;">
			<tr>
				<td width="30px">
					<input id="gczj-tab-thead-CKA" name="gczj-tab-thead-CKA" type="checkbox"/></td>
				</td>
				<td width='30px'>序号</td>
				<td width='90px'>时间</td>
				<td width='40px'><p>产品段</p></td>
				<td width='80px'>外观质量情况</td>
				<td width='30px'>判断</td>
				<td width='40px'>复检</td>
				<td width='80px'>处理措施</td>
				<td width='40px'>缺陷数量</td>
				<td width='40px'>数量单位</td>
				<td width='80px'>备注</td> 
			</tr>
		</thead>
		<tbody id="gczj-tab-tbody">
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d43f4d73d86d014d73df6da90000"/>
	</table>
</html>
