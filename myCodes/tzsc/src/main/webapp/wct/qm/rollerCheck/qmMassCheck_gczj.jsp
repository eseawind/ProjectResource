<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
	#gczj-tab-thead tr td{text-align:center;}
	#gczj-tab-tbody tr{height:30px;text-align:center;font-size:12px;}
</style>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript"></script>
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
		$.post("${pageContext.request.contextPath}/wct/isp/roller/getRollerData.do",{"equipmentCode":"${loginInfo.equipmentCode}"},function(json){
				qty0=(json.qty*5).toFixed(0);
				}
		,"JSON");
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
	            		if(oldText=="未复检时间"){
	            			oldText=getDateHourAndMinute();
	            		}
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td3";
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td3";
				        var classIds = formId+classId;
	            	    var se2="<input type='text' class='"+classIds+"' onkeyup='checkInput(this)' style='width:100%;height:30px;' value='"+oldText+"' />";
	            		/* var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classIds =formId+classId;
	            		var se2="<input value='"+oldText+"' " 
	            		+" class='"+classIds+"' "
	            		+"style='heigth:30px;border-radius:5px;font-size:2px;text-align:center;'/>"; */
	            		//tr.find("td:eq(2) input").html(se2);
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
	            		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(3) input").val();
			            }
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classIds = formId+classId;
	            	    var se3="<input type='text' class='"+classIds+"' onkeyup='checkInput(this)' style='width:100%;height:30px;' value='"+oldText+"' />";
	            		 /*  var formId="form-control ";
				         var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				         var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				         var classIds =formId+classId;
		                 var se3="<input onkeyup='checkInput(this)' type='text' value='"+oldText+"' " 
		                    +" class='"+classIds+"' "
		                    +"style='height:30px;border-radius:5px'/>"; */ 
	                	$(this).html(se3);
	                	$(classKey).keyboard({
	        				layout:"1234567890.-",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 . - {bksp}"
	        					]
	        				}
	        			});	
	                }
	              	if(index==4){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(4) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td5";
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td5";
				        var classIds = formId+classId;
	            	    var se4="<input type='text' class='"+classIds+"' onkeyup='checkInput(this)' style='width:100%;height:30px;' value='"+oldText+"' />";
			            /*var formId="form-control ";
			            var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
			            var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
			            var classIds =formId+classId;
	              		var se4="<input type='text' value='"+oldText+"' " 
	                    	+" class='"+classIds+"' "
	                    	+"style='height: 30px;border-radius:5px'/>"; */
	                	$(this).html(se4);
	                	$(classKey).keyboard({
	        				layout:"1234567890.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 . {bksp}"
	        					]
	        				}
	        			});	
					}
					if(index==5){//外观质量情况
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(5) select").val();
			            }
	                    var se5="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<qxKey.length;i++ ){
	                        if(qxKey[i]==oldText||qxVal[i]==oldText){
	                        	se5+="<option value='"+qxKey[i]+"' selected>"+qxVal[i]+"</option>" ;
	                        }else{
	                        	se5+="<option value='"+qxKey[i]+"' >"+qxVal[i]+"</option>";
	                        }
	                    }
	                    se5+="</select>";
	                   $(this).html(se5);
					}
					if(index==6){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(6) select").val();
			            }
	              		var se6="<select style='width:50px;height:30px;'>";
	                    for(var i=0;i<isTrueKey.length;i++ ){
	                    	if(isTrueKey[i]==oldText||isTrueVal[i]==oldText){
	                   		 se6+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
	                        }else{
	                       	 se6+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
	                        }
	                    }
	                    se6+="</select>";
	                	$(this).html(se6);
					}
					if(index==7){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(7) select").val();//	当前第4列 	重量g/20支
			            }
	              		 var se7="<select style='width:50px;height:30px;'>";
		                    for(var i=0;i<isFjKey.length;i++ ){
		                        if(isFjKey[i]==oldText||isFjVal[i]==oldText){
		                        	se7+="<option value='"+isFjKey[i]+"' selected>"+isFjVal[i]+"</option>" ;
		                        }else{
		                        	se7+="<option value='"+isFjKey[i]+"' >"+isFjVal[i]+"</option>";
		                        }
		                    }
		                    se7+="</select>";
		                   $(this).html(se7);
					}
					if(index==8){//处理措施
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(8) select").val();
			            }
	                    var se8="<select style='width:80px;height:30px;'>";
	                    for(var i=0;i<csKey.length;i++ ){
	                        if(csKey[i]==oldText||csVal[i]==oldText){
	                        	se8+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
	                        }else{
	                        	se8+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
	                        }
	                    }
	                    se8+="</select>";
	                   $(this).html(se8);
					}
					 if(index==9){
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(9) input").val();
				        }
						var formId="form-control ";
					    var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td9";
					    var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td9";
					    var classIds = formId+classId;
						var se9="<input type='text' class='"+classIds+"' onkeyup='checkInput(this)' style='width:100%;height:30px;' value='"+oldText+"'/>";
	                   	$(this).html(se9);
	                    $(classKey).keyboard({
	        				layout:"1234567890.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 . {bksp}"
	        					]
	        				}
	        			});	
					} 
					 if(index==10){
							if(zjId==""||zjId==undefined){//表示是 新增的行
								oldText = tr.find("td:eq(10) select").val();
				            }
		                    var se10="<select style='width:50px;height:30px;'>";
		                    for(var i=0;i<unit_key.length;i++ ){
		                        if(unit_key[i]==oldText||unit_val[i]==oldText){
		                        	se10+="<option value='"+unit_key[i]+"' selected>"+unit_val[i]+"</option>" ;
		                        }else{
		                        	se10+="<option value='"+unit_key[i]+"' >"+unit_val[i]+"</option>";
		                        }
		                    }
		                    se10+="</select>";
		                   $(this).html(se10);
						}
					 if(index==11){
						 if(zjId=="" || zjId==undefined){
						 oldText=tr.find("td:eq(11) select").val();
						 }	
						 var se11="<select style='width:100px;height:30px;'>";
						 for(var i=0;i<standkey.length;i++){
							 if(standval[i]==oldText || standkey[i]==oldText){
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
		var params ={proWorkId : schWorkorderId,processType:'J'}//工单主键ID
		$.post("${pageContext.request.contextPath}/wct/massProcess/getList.do",params,function(data){
				var html="";
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						//console.info(revalue);
						var prodPart="";//产品段
						var runCondition="";//外观质量情况
						var isError="";//判断
						var isAgain="";//复检
						var runStep="";//处理措施
						var badNum="";//质量问题数量
						var numUnit="";//质量问题数量单位
						var runRemark="";//备注
						//根据 key 或 value找 value
						
						for(var j=0;j<qxKey.length;j++ ){//外观质量情况
							if(qxKey[j]==revalue.runCondition||qxVal[j]==revalue.runCondition){
								runCondition=qxVal[j];
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
						//单位
						for(var j=0;j<unit_key.length;j++ ){
							if(unit_key[j]==revalue.numUnit||unit_val[j]==revalue.numUnit){
								numUnit=unit_val[j];
								break;
							}
						}
						//备注
						for(var j=0;j<standkey.length;j++ ){
							if(standkey[j]==revalue.runRemark||standkey[j]==revalue.runRemark){
								runRemark=standval[j];
								break;
							}
						}
						html+="<tr>"
							+"	<td>"
							+"		<input value="+revalue.qmProcessId+" type='checkbox' style='width:20px;height:20px;' name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td width='30px'>"+(i+1)+"</td>"
							+"	<td width='55px'>"+revalue.shortTime+"</td>"//时间
							+"	<td width='50px'>"+revalue.prodPart+"</td>"	//产品段
							+"	<td width='60px'>"+revalue.runWeight+"</td>" //重量
							+"	<td width='100px'>"+runCondition+"</td>"
							+"	<td width='40px'>"+isError+"</td>"
							+"	<td width='40px'>"+isAgain+"</td>"
							+"	<td width='80px'>"+runStep+"</td>"
							+"	<td width='60px'>"+revalue.badNum+"</td>"
							+"	<td width='30px'>"+numUnit+"</td>"
							+"	<td width='140px'>"+runRemark+"</td>"
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
	    
		var nowDate=getDateHourAndMinute();
		var trLength=$("#gczj-tab-tbody tr").length;//获取tr的行数
        var se3="<input type='text' value='"+qty0+"'></input>";
     	var se4="<input type='text' value=''></input>";
         var se5="<select style='width:100px;'>";
         for(var i=0;i<qxKey.length;i++ ){
        	 if(i==0){
        		 se5+="<option value='"+qxKey[i]+"' selected>"+qxVal[i]+"</option>" ;
             }else{
            	 se5+="<option value='"+qxKey[i]+"' >"+qxVal[i]+"</option>";
             }
         }
         se5+="</select>";

         var se6="<select style='width:40px;'>";
         for(var i=0;i<isTrueKey.length;i++ ){
        	 if(i==0){
        		 se6+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
             }else{
            	 se6+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
             }
         }
         se6+="</select>";
		
         var se7="<select style='width:40px;'>";
         for(var i=0;i<isFjKey.length;i++ ){
        	 if(i==0){
        		 se7+="<option value='"+isFjKey[i]+"' selected>"+isFjVal[i]+"</option>" ;
             }else{
            	 se7+="<option value='"+isFjKey[i]+"' >"+isFjVal[i]+"</option>";
             }
         }
         se7+="</select>";
           
		 var se8="<select style='width:80px;'>";
         for(var i=0;i<csKey.length;i++ ){
        	 if(i==0){
        		 se8+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
             }else{
            	 se8+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
             }
         }
         se8+="</select>";

        var se9="<input type='text' value='0'></input>";
 	  	var se10="<select style='width:30px;'>";
 	      for(var i=0;i<unit_key.length;i++ ){
 	     	 if(i==0){
 	     		 se10+="<option value='"+unit_key[i]+"' selected>"+unit_val[i]+"</option>" ;
 	          }else{
 	         	 se10+="<option value='"+unit_key[i]+"' >"+unit_val[i]+"</option>";
 	          }
 	      }
 	    se10+="</select>";
 	    
 	   
   	 	var se11="<select style='width:140px;'>";
   	 	for(var i=0;i<standkey.length;i++){
   	 		if(i==0){
   	 			se11+="<option value='"+standval[i]+"' selected>"+standkey[i]+"</option>";
   	 		}else{
   	 			se11+="<option value='"+standval[i]+"' >"+standkey[i]+"</option>";
   	 		}
   	 		}
   	 	se11+="</select>";
   		
         
		var html="<tr>"
			+"	<td>"
			+"		<input value='' type='checkbox' style='width:20px;height:20px;' name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
			+"	</td>"
			+"	<td width='30px'>"+(trLength+1)+"</td>"  //序号
			+"	<td width='55px'><input style='width:30px;'>"+nowDate+"</input></td>"		//时间
			+"	<td width='50px'>"+se3+"</td>"			//产品段
			+"	<td width='60px'>"+se4+"</td>" //重量g/20支
			+"	<td width='100px'>"+se5+"</td>"			//外观质量情况
			+"	<td width='40px'>"+se6+"</td>"			//判断
			+"	<td width='40px'>"+se7+"</td>"	//复检	
			+"	<td width='80px'>"+se8+"</td>"	//处理措施
			+"	<td width='60px'>"+se9+"</td>"	//质量问题数量
			+"	<td width='30px'>"+se10+"</td>"	//单位	
			+"	<td width='120px'>"+se11+"</td>"	//备注	 */
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
			+ '","mdShiftId":"'+ _shiftID//班次id
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
	        		 var td4 = "";//当前第4列 	重量g/20支 input
					 var td5 = "";//当前第5列 	外观质量情况 select
					 var td6 = "";//当前第6列 	判断    select
					 var td7 = "";//当前第7列 	复检    select
					 var td8 = "";//当前第8列 	处理措施    select
					 var td9 = "";//当前第9列 	质量问题数量
					 var td10 = "";//当前第10列 	单位
					 var td11 = "";//当前第11列 	备注   text
					 inserttime=td2;
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 td2 = tr.find("td:eq(2) input").val();
						 td3 = tr.find("td:eq(3) input").val();
						 td4 = tr.find("td:eq(4) input").val();
						 td5 = tr.find("td:eq(5) select").val();
						 td6 = tr.find("td:eq(6) select").val();
						 td7 = tr.find("td:eq(7) select").val();
						 td8 = tr.find("td:eq(8) select").val();
						 td9 = tr.find("td:eq(9) input").val();
						 td10 = tr.find("td:eq(10) select").val();
						 td11 = tr.find("td:eq(11) select").val();
						 inserttime=td2;
					 }else{
						 td2 = tr.find("td:eq(2)").val();
						 td3 = tr.find("td:eq(3)").text();
						 td4 = tr.find("td:eq(4)").text();
						 td5 = tr.find("td:eq(5)").text();
						 td6 = tr.find("td:eq(6)").text();
						 td7 = tr.find("td:eq(7)").text();
						 td8 = tr.find("td:eq(8)").text();
						 td9 = tr.find("td:eq(9)").text();
						 td10 = tr.find("td:eq(10)").text();
						 td11 = tr.find("td:eq(11)").text();
						 inserttime=td2;
						 //根据中文找 key
					 	for(var j=0;j<qxKey.length;j++ ){//外观质量情况 select
							if(qxVal[j]==td5){
								td5=qxKey[j];
								 break;
							 }
						}
						for(var j=0;j<isTrueKey.length;j++ ){//判断    select
							if(isTrueVal[j]==td6){
								td6=isTrueKey[j];
								 break;
							 }
						}
						for(var j=0;j<isFjKey.length;j++ ){//复检    select
							if(isFjVal[j]==td7){
								td7=isFjKey[j];
								 break;
							 }
						}
						for(var j=0;j<csKey.length;j++ ){//处理措施    select
							if(csVal[j]==td8){
								td8=csKey[j];
								 break;
							 }
						}
						for(var j=0;j<unit_key.length;j++ ){//处理措施    select
							if(unit_val[j]==td10){
								td10=unit_key[j];
								 break;
							 }
						}
						//alert(td11);
						for(var j=0;j<standkey.length;j++ ){
							if(standkval[j]==td11){
								td11=standkey[j];
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
				    	+ '","processType":"'+ 'J'//操作类型 J 卷烟机 没有分角色 D挡车工 C操作工 F封箱机
						+ '","prodPart":"'+ td3//产品段
						+ '","runWeight":"'+ td4//重量
						+ '","runCondition":"'+ td5//外观质量情况
						+ '","isError":"'+ td6//判断
						+ '","isAgain":"'+ td7//复检
						+ '","runStep":"'+ td8//处理措施
						+ '","badNum":"'+ td9//质量问题数量
						+ '","numUnit":"'+ td10//质量问题数量单位
						+ '","runRemark":"'+ td11//备注
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
<body>
	<!-- 内容 -->
	<table border="1" borderColor="#9a9a9a"
		style="BORDER-COLLAPSE: collapse; font-size: 12px; font-weight: 500;margin-bottom:300px;"
		width="100%"  cellspacing="0" cellpadding="0">
		<thead id="gczj-tab-thead"
			style="background: #5a5a5a; color: #fff;">
			<tr>
				<td width="30px">
					<input id="gczj-tab-thead-CKA" style="width:20px;height:20px;" name="gczj-tab-thead-CKA" type="checkbox"/>
				</td>
				<td width='30px'>序号</td>
				<td width='55px'>时间</td>
				<td width='50px'><p>产品段</p></td>
				<td width='60px'>重量g/20支</td>
				<td width='100px'>外观质量情况</td>
				<td width='40px'>判断</td>
				<td width='40px'>复检</td>
				<td width='80px'>处理措施</td>
				<td width='60px'>缺陷数量</td>
				<td width='30px'>数量单位</td>
				<td width='140px'>备注</td> 
			</tr>
		</thead>
		<tbody id="gczj-tab-tbody">
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d43f4d73d86d014d73df6da90000"/>
	</table>
</html>
</body>