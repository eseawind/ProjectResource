<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
	#zhcs-tab-thead tr td{text-align:center;}
	#zhcs-tab-tbody tr{height:30px;text-align:center;font-size:12px;}
</style>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript" ></script>
<script type="text/javascript">
	/*var arryKey、arryVal、qxKey、qxVal、csKey、csVal这些是公共的属性*/
	var formattedtime=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var formattedtime2=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})-(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var flag;
	var inserttime;
	var zhcsChangeClick =null;	
	var zhcsChangeType ="init";
	$(function(){
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
		//checked全选、反选事件
        $("#zhcs-tab-thead-CKA").click(function() {
        	$("#zhcs-tab-tbody tr").each(function(idx) {
           		if($(this).find("#zhcs-tab-tbody-CK").get(0)!=null
                   	&&$(this).find("#zhcs-tab-tbody-CK").get(0)!=undefined){
           			$(this).find("#zhcs-tab-tbody-CK").get(0).checked = 
               			$("#zhcs-tab-thead-CKA").get(0).checked;
               }
			});
        });
        zhcsChangeClick=function(type){
			//编辑事件
			$("#zhcs-tab-tbody tr").die().live("click",function(){
				var tr=$(this);
				if(tr.attr("edit")){
					return;
				};
				$(this).find("#zhcs-tab-tbody-CK").get(0).checked=true;//选中当前行
				tr.attr("edit",true);
				tr.find("td").each(function(index){
	            	var oldText=$(this).text();//原来的值   5
	            	var zjId = tr.find("#zhcs-tab-tbody-CK").get(0).value;//如果有 就表示 数据库数据
	            	//根据key 或 value 赋值
	            	//根据key 或 value 赋值
	            	if(index==2){
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
				        var classIds = formId+classId;
	            		var se2="<input type='text' class='"+classIds+"' style='width:100px;height:30px;' value='"+oldText+"' />";
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
	                if(index==4){
	                	if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(4) input").val();//	当前第4列 	重量g/20支
			            }
			            var formId="form-control ";
			            var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classIds =formId+classId;
	              		var temp="<input type='text' value='"+oldText+"' " 
	                    	+" class='"+classIds+"' "
	                    	+"style='height: 30px;border-radius:5px'/>";/* width:120px; */
	                	$(this).html(temp);
	                	$(classKey).keyboard({
	        				layout:"1234567890:.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : . {bksp}"
	        					]
	        				}
	        			});
					}
	                if(index==5){
	                	if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(5) input").val();//	当前第4列 	重量g/20支
			            }
			            var formId="form-control ";
			            var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classIds =formId+classId;
	              		var temp="<input type='text' value='"+oldText+"' " 
	                    	+" class='"+classIds+"' "
	                    	+"style='height: 30px;border-radius:5px'/>";/* width:120px; */
	                	$(this).html(temp);
	                	$(classKey).keyboard({
	        				layout:"1234567890:.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : . {bksp}"
	        					]
	        				}
	        			});
					}
	                if(index==6){
	                	if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(6) input").val();//	当前第4列 	重量g/20支
			            }
			            var formId="form-control ";
			            var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classIds =formId+classId;
	              		var temp="<input type='text' value='"+oldText+"' " 
	                    	+" class='"+classIds+"' "
	                    	+"style='height: 30px;border-radius:5px'/>";/* width:120px; */
	                	$(this).html(temp);
	                	$(classKey).keyboard({
	        				layout:"1234567890:.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : . {bksp}"
	        					]
	        				}
	        			});
					}
	                if(index==7){
	                	if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(7) input").val();//	当前第4列 	重量g/20支
			            }
			            var formId="form-control ";
			            var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
			            var classIds =formId+classId;
	              		var temp="<input type='text' value='"+oldText+"' " 
	                    	+" class='"+classIds+"' "
	                    	+"style='height: 30px;border-radius:5px'/>";/* width:120px; */
	                	$(this).html(temp);
	                	$(classKey).keyboard({
	        				layout:"-",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ","7 8 9 0 . - {bksp}"
	        					]
	        				}
	        			});
					}
	                if(index==8){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(8) select").val();
			            }
	              		var temp="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<isTrueKey.length;i++ ){
	                    	if(isTrueKey[i]==oldText||isTrueVal[i]==oldText){
	                    		temp+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
	                        }else{
	                        	temp+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
	                        }
	                    }
	                    temp+="</select>";
	                	$(this).html(temp);
					}
					if(index==9){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(9) select").val();//	当前第4列 	重量g/20支
			            }
	              		 var temp="<select style='width:100%;height:30px;'>";
		                    for(var i=0;i<isFjKey.length;i++ ){
		                        if(isFjKey[i]==oldText||isFjVal[i]==oldText){
		                        	temp+="<option value='"+isFjKey[i]+"' selected>"+isFjVal[i]+"</option>" ;
		                        }else{
		                        	temp+="<option value='"+isFjKey[i]+"' >"+isFjVal[i]+"</option>";
		                        }
		                    }
		                    temp+="</select>";
		                   $(this).html(temp);
					}
					if(index==10){//处理措施
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(10) select").val();
			            }
	                    var temp="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<csKey.length;i++ ){
	                        if(csKey[i]==oldText||csVal[i]==oldText){
	                        	temp+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
	                        }else{
	                        	temp+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
	                        }
	                    }
	                    temp+="</select>";
	                   $(this).html(temp);
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
       zhcsChangeClick("load");//供编辑用
	});
	//查询
	function zhcsSearch(){
		zhcsChangeType ="query";
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
		$("#zhcs-tab-thead-CKA").get(0).checked = false;//顶部checkbox为不选状态
		var params ={proWorkId : schWorkorderId}//工单主键ID
		$.post("${pageContext.request.contextPath}/wct/massOnline/getList.do",params,function(data){
				var html="";
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var isError="";//判断
						var isAgain="";//复检
						var runStep="";//处理措施
						var onlineRemark="";//备注
						//根据 key 或 value找 value
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
							if(csKey[j]==revalue.onlineStep||csVal[j]==revalue.onlineStep){
								runStep=csVal[j];
								 break;
							 }
						}
						//备注
						for(var j=0;j<standkey.length;j++ ){
							if(standkey[j]==revalue.onlineRemark||standkey[j]==revalue.onlineRemark){
								onlineRemark=standval[j];
								break;
							}
						}
						html+="<tr>"
							+"	<td>"
							+"		<input value="+revalue.qmOnlineId+" type='checkbox' style='width:20px;height:20px;' name='zhcs-tab-tbody-CK' id='zhcs-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td style='width:20px'>"+(i+1)+"</td>" //序号
							+"	<td style='width:30px'>"+revalue.shortTime+"</td>" //时间
							+"	<td style='width:40px'>"+revalue.checkItem+"</td>"	//检测项目
							+"	<td style='width:30px'>"+revalue.upperNumber+"</td>" //超上限支数
							+"	<td style='width:30px'>"+revalue.lowerNumber+"</td>"//超下限支数
							+"	<td style='width:30px'>"+revalue.avgNumber+"</td>"//平均值
							+"	<td style='width:30px'>"+revalue.standardDiff+"</td>"//标偏
							+"	<td style='width:40px'>"+isError+"</td>"//判断
							+"	<td style='width:40px'>"+isAgain+"</td>"//复检
							+"	<td style='width:70px'>"+runStep+"</td>"//处理措施
							+"	<td style='width:80px'>"+onlineRemark+"</td>"//备注
							+"</tr>";
					}
				}else{}
				$("#zhcs-tab-tbody").html(html);
			},"JSON");

	}				
	//新增
	function zhcsInsert(){
		zhcsChangeType ="insert";
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
		for(var k=0;k<4;k++){
			var trLength=$("#zhcs-tab-tbody tr").length;//获取tr的行数
			var se3= "";
			if(k==0){
				se3="单重mg";
			}else if(k==1){
				se3="圆周mm";
			}else if(k==2){
				se3="长度mm";
			}else if(k==3	){
				se3="总通风率%";
			}
	        var formId="form-control ";
	        var classId4="zl-keyboard-"+trLength+"td4";
	        	classId4 =formId+classId4;
	     	var se4="<input type='text' value='0' " 
	       		+" class='"+classId4+"' "
	       		+"style='height: 30px;border-radius:5px'/>";
				
	       	var classId5="zl-keyboard-"+trLength+"td5";
	       		classId5=formId+classId5;
	      	var se5="<input type='text' value='0' " 
	       		+" class='"+classId5+"' "
	       		+"style='height: 30px;border-radius:5px'/>";
	
	     	var classId6="zl-keyboard-"+trLength+"td6";
	     		classId6=formId+classId6;
	        var se6="<input type='text' value='' " 
	       		+" class='"+classId6+"' "
	       		+"style='height: 30px;border-radius:5px'/>";
	
	   	 	var classId7="zl-keyboard-"+trLength+"td7";
	   	 		classId7=formId+classId7;
	        var se7="<input type='text' value='-' " 
	        		+" class='"+classId7+"' "
	        		+"style='height: 30px;border-radius:5px'/>";	
	        
	         var se8="<select style='width:100%;'>";//判断
	         for(var i=0;i<isTrueKey.length;i++ ){
	        	 if(i==0){
	        		 se8+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
	             }else{
	            	 se8+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
	             }
	         }
	         se8+="</select>";
	
	         var se9="<select style='width:100%;'>";//复检
	         for(var i=0;i<isFjKey.length;i++ ){
	        	 if(i==0){
	        		 se9+="<option value='"+isFjKey[i]+"' selected>"+isFjVal[i]+"</option>" ;
	             }else{
	            	 se9+="<option value='"+isFjKey[i]+"' >"+isFjVal[i]+"</option>";
	             }
	         }
	         se9+="</select>";
	
	         var se10="<select style='width:100%;'>";
	         for(var i=0;i<csKey.length;i++ ){
	        	 if(i==0){
	        		 se10+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
	             }else{
	            	 se10+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
	             }
	         }
	         se10+="</select>";
		
	         var se11="<select style='width:100px;'>";
	         for(var i=0;i<standkey.length;i++){
	    	 	if(i==0){
	    	 		se11+="<option value='"+standval[i]+"' selected>"+standkey[i]+"</option>";
	    	 	}else{
	    	 		se11+="<option value='"+standval[i]+"' >"+standkey[i]+"</option>";
	    	 	}
	    	 }
	    	 se11+="</select>";
			 
			var html="<tr>"
				+"	<td width='15px'>"
				+"		<input value='' type='checkbox' style='width:20px;height:20px;' name='zhcs-tab-tbody-CK' id='zhcs-tab-tbody-CK'/></td>"
				+"	</td>"
				+"	<td style='width:20px'>"+(trLength+1)+"</td>"  //序号
				+"	<td style='width:30px'>"+nowDate+"</td>"		//时间
				+"	<td style='width:40px'>"+se3+"</td>" //检测项目
				+"	<td style='width:30px'>"+se4+"</td>" //超上限支数
				+"	<td style='width:30px'>"+se5+"</td>" //超下限支数
				+"	<td style='width:30px'>"+se6+"</td>" //平均值
				+"	<td style='width:30px'>"+se7+"</td>" //标偏
				+"	<td style='width:40px'>"+se8+"</td>"	//判断
				+"	<td style='width:40px'>"+se9+"</td>"	//复检
				+"	<td style='width:70px'>"+se10+"</td>"	//处理措施	
				+"	<td style='width:80px'>"+se11+"</td>"	//备注
				+"</tr>";
				$("#zhcs-tab-tbody").append(html);
				var trRow="#zhcs-tab-tbody tr:eq("+(trLength)+")";//当前行
				$(trRow).click();//行 获取焦点
		}
	}
	//修改
	function zhcsUpdate(){
		zhcsChangeType ="update";
	}
	//删除
	function zhcsDeletes(){
		zhcsChangeType ="delete";
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
		 $("#zhcs-tab-tbody tr").each(function(idx) {
            var trCk = $(this).find("#zhcs-tab-tbody-CK").get(0);//checkbox对象
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
 		            url: "${pageContext.request.contextPath}/wct/massOnline/deleteByIds.do",//要访问的后台地址
 		            data: "ids=" + ids,//要发送的数据
 		            complete :function(){
 		                //$("#load").hide();
 		            },//AJAX请求完成时隐藏loading提示
 		            success : function(r) {
 						if(r.success){
 							zhcsSearch();//重新查询下
 						}else{ 
 							jAlert("删除失败!","提示");
 						}
 					}
 		         });
 			}
 		 });
	}

	//保存
	function zhcsSave(){
		zhcsChangeType ="save";
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
		 $("#zhcs-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#zhcs-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键，判断更新或者新增
	        		 var td1 = tr.find("td:eq(1)").text();//当前第1列 序号
	        		 var td2 = tr.find("td:eq(2)").text();//当前第2列  时间
	        		 var td3 = "";//当前第3列 	检测项目
	        		 var td4 = "";//当前第3列 	超上限支数
	        		 var td5 = "";//当前第4列 	超下限支数
					 var td6 = "";//当前第5列 	平均值
					 var td7 = "";//当前第6列 	标偏
					 var td8 = "";//当前第7列 	判 断    	select
					 var td9 = "";//当前第8列 	复检    	select
					 var td10 = "";//当前第9列 	处理措施   	select
					 var td11 = "";//当前第10列  备注 	text
					 inserttime=td2;
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 td2 = tr.find("td:eq(2) input").val();//检测项目
						 td3 = tr.find("td:eq(3)").text();//检测项目
						 td4 = tr.find("td:eq(4) input").val();
						 td5 = tr.find("td:eq(5) input").val();
						 td6 = tr.find("td:eq(6) input").val();
						 td7 = tr.find("td:eq(7) input").val();
						 td8 = tr.find("td:eq(8) select").val();
						 td9 = tr.find("td:eq(9) select").val();
						 td10 = tr.find("td:eq(10) select").val();
						 td11 = tr.find("td:eq(11) select").val();
						 inserttime=td2;
					 }else{
						 td2 = tr.find("td:eq(2)").text();//检测项目
						 td3 = tr.find("td:eq(3)").text();//检测项目
						 td4 = tr.find("td:eq(4)").text();
						 td5 = tr.find("td:eq(5)").text();
						 td6 = tr.find("td:eq(6)").text();
						 td7 = tr.find("td:eq(7)").text();
						 td8 = tr.find("td:eq(8)").text();
						 td9 = tr.find("td:eq(9)").text();
						 td10 = tr.find("td:eq(10)").text();
						 td11 = tr.find("td:eq(11)").text();//这列不可以修改
						 inserttime=td2;
						 //根据中文找 key
						 for(var j=0;j<isTrueKey.length;j++ ){//判断    select
							if(isTrueVal[j]==td8){
								td8=isTrueKey[j];
								 break;
							 }
						}
						 for(var j=0;j<isFjKey.length;j++ ){//复检    select
							if(isFjVal[j]==td9){
								td9=isFjKey[j];
								 break;
							 }
						}
						for(var j=0;j<csKey.length;j++ ){//处理措施    select
							if(csVal[j]==td10){
								td10=csKey[j];
								 break;
							 }
						}
						for(var j=0;j<standkey.length;j++ ){
							if(standkval[j]==td11){
								td11=standkey[j];
								break;
							}
						}
					 }
					 if(td3=="总通风率%"){
					       td3="总通风率%25";
					 }
					 if(formattedtime.test(inserttime) || formattedtime2.test(inserttime)){
						 flag=true;
					 }else{
						 flag=false;
					 }
				     reqString += '{"qmOnlineId":"'+td0//主键
				     	+ '","shortTime":"'+ td2//时间项目
				     	+ '","checkItem":"'+ td3//检测项目
						+ '","upperNumber":"'+ td4//超上限支数
						+ '","lowerNumber":"'+ td5//超下限支数
						+ '","avgNumber":"'+ td6//平均值
						+ '","standardDiff":"'+ td7//标偏
						+ '","isError":"'+ td8//判 断
						+ '","isAgain":"'+ td9//复检
						+ '","onlineStep":"'+ td10//处理措施
						+ '","onlineRemark":"'+ td11//备注 
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
            url: "${pageContext.request.contextPath}/wct/massOnline/editBean.do",//要访问的后台地址
            data: "checkArray="+checkString+"&onlineArray=" + reqString ,//要发送的数据
            complete :function(){
                //$("#load").hide();
            },//AJAX请求完成时隐藏loading提示
            success : function(r) {
				if(r.success){
					zhcsSearch();//重新查询下
				}else{
					jAlert("保存失败!","提示");
				}
			}
         });
	}
	
</script>

</head>
<title>综合测试台_1</title>
	<!-- 内容 -->
	<table border="1" borderColor="#9a9a9a"
		style="BORDER-COLLAPSE: collapse; font-size: 12px; font-weight: 500;margin-bottom:300px;"
		width="100%"  cellspacing="0" cellpadding="0">
		<thead id="zhcs-tab-thead"
			style="background: #5a5a5a; color: #fff;">
			<tr>
				<td width="15px">
					<input id="zhcs-tab-thead-CKA" style="width:20px;height:20px;" name="zhcs-tab-thead-CKA" type="checkbox"/></td>
				</td>
				<td width="20px">序号</td>
				<td width="30px">时间</td>
				<td width="40px"><p>检测项目</p></td>
				<td width="30px">超上<br>限支数</td>
				<td width="30px">超下<br>限支数</td>
				<td width="30px">平均值</td>
				<td width="30px">标偏</td>
				<td width="40px">判断</td>
				<td width="40px">复检</td>
				<td width="70px">处理措施</td>
				<td width="80px">备注</td>
			</tr>
		</thead>
		<tbody id="zhcs-tab-tbody">
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d43f4d73d86d014d73df6da90000"/>
	</table>
</html>
