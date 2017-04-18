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
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
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
	            	if(index==3){//外观质量情况
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(3) select").val();
			            }
	                    var se3="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<qx_filter_key1.length;i++ ){
	                        if(qx_filter_key1[i]==oldText||qxVal[i]==oldText){
	                        	se3+="<option value='"+qx_filter_key1[i]+"' selected>"+qx_filter_val1[i]+"</option>" ;
	                        }else{
	                        	se3+="<option value='"+qx_filter_key1[i]+"' >"+qx_filter_val1[i]+"</option>";
	                        }
	                    }
	                    se3+="</select>";
	                   $(this).html(se3);
					}
	              	if(index==4){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(4) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se4="<input type='text' class='"+classIds+"' style='width:100%;height:30px;' value='"+oldText+"' />";
	                   	$(this).html(se4);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});	
					}
	              	
	              	if(index==5){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(5) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se5="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"' />";
	                   	$(this).html(se5);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});	
					}
	              	
	              	if(index==6){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(6) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se6="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"' />";
	                   	$(this).html(se6);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});	
					}
	              	
	              	if(index==7){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(7) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se7="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"'/>";
	                   	$(this).html(se7);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==8){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(8) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se8="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se8);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==9){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(9) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se9="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se9);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==10){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(10) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se10="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se10);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==11){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(11) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se11="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se11);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==12){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(12) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se12="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se12);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==13){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(13) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se13="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se13);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==14){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(14) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se14="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se14);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==15){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(15) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se15="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se15);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	if(index==16){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(16) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds =formId+classId;
		              	var se16="<input type='text' style='width:100%;height:30px;' class='"+classIds+"' value='"+oldText+"''/>";
	                   	$(this).html(se16);
	                   	$(classKey).keyboard({
	        				layout:"1234567890:-.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 : - . {bksp}"
	        					]
	        				}
	        			});
					}
	              	<!--判断-->
	              	if(index==17){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(17) select").val();
			            }
	              		var se17="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<isTrueKey.length;i++ ){
	                    	if(isTrueKey[i]==oldText||isTrueVal[i]==oldText){
	                   		 se17+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
	                        }else{
	                       	 se17+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
	                        }
	                    }
	                    se17+="</select>";
	                	$(this).html(se17);
					}
	              	if(index==18){//处理措施
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(18) select").val();
			            }
	                    var se18="<select style='width:100%;height:30px;'>";
	                    for(var i=0;i<csKey.length;i++ ){
	                        if(csKey[i]==oldText||csVal[i]==oldText){
	                        	se18+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
	                        }else{
	                        	se18+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
	                        }
	                    }
	                    se18+="</select>";
	                   $(this).html(se18);
					}
	              	 if(index==19){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(19) input").val();
			            }
	              		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(19)").text()+"td"+index;
				        var classKey=".zl-keyboard-"+tr.find("td:eq(19)").text()+"td"+index;
				        var classIds =formId+classId;
					 	var se19="<input type='text' onkeyup='checkInput(this)' class='"+classIds+"' style='width:100%;height:30px;' value='"+oldText+"''/>";
		             	$(this).html(se19);
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
					if(index==20){
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(20) select").val();
					    }
			            var se20="<select style='width:100%;height:30px;'>";
			            for(var i=0;i<unit_key.length;i++ ){
			            	if(unit_key[i]==oldText||unit_val[i]==oldText){
			                	se20+="<option value='"+unit_key[i]+"' selected>"+unit_val[i]+"</option>" ;
			            	}else{
			                	se20+="<option value='"+unit_key[i]+"' >"+unit_val[i]+"</option>";
			            	}
			        	}
			            se20+="</select>";
			            $(this).html(se20);
					}
					if(index==21){
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(21) input").val();
			            }
						var se21="<select style='width:100%;height:30px;'>";
						for(var i=0;i<standkey.length;i++){
							if(oldText==standkey[i] || oldText==standval[i]){
								se21+="<option value='"+standval[i]+"' selected>"+standkey[i]+"</option>" ;
		              		}else{
		              			se21+="<option value='"+standval[i]+"' >"+standkey[i]+"</option>";
		              		}
						}
						se21+="</select>";
			            $(this).html(se21);
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
		var params ={proWorkId : schWorkorderId}//工单主键ID
		$.post("${pageContext.request.contextPath}/wct/massProcess/getFilterList.do",params,function(data){
				var html="";
				if(data==null) return;
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var runCondition="";//外观质量情况
						var isError="";//判断
						var runStep="";//处理措施
						var badNum="";//质量问题数量
						var numUnit="";//质量问题数量单位
						//根据 key 或 value找 value
						for(var j=0;j<qx_filter_key1.length;j++ ){//外观质量情况
							if(qx_filter_key1[j]==revalue.runCondition||qx_filter_val1[j]==revalue.runCondition){
								runCondition=qx_filter_val1[j];
								 break;
							 }
						} 
						for(var j=0;j<isTrueKey.length;j++ ){//判断
							if(isTrueKey[j]==revalue.isError||isTrueVal[j]==revalue.isError){
								isError=isTrueVal[j];
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
						html+="<tr>"
							+"	<td>"
							+"		<input value="+revalue.qmFilterProcessId+" type='checkbox' style='width:20px;height:20px;' name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td width='20px'>"+(i+1)+"</td>"
							+"	<td width='50px'>"+revalue.shortTime+"</td>"//时间
							+"	<td width='50px'>"+runCondition+"</td>"//外观质量
							
							+"	<td width='23px'>"+revalue.weightFNum+"</td>"//重量
							+"	<td width='23px'>"+revalue.weightTNum+"</td>"//重量
							+"	<td width='23px'>"+revalue.weightAVG+"</td>"//重量
							
							+"	<td width='23px'>"+revalue.pressureFnum+"</td>"//吸阻
							+"	<td width='23px'>"+revalue.pressureTnum+"</td>"//吸阻
							+"	<td width='23px'>"+revalue.pressureAVG+"</td>"//吸阻
							
							+"	<td width='23px'>"+revalue.circleFnum+"</td>"//圆周
							+"	<td width='23px'>"+revalue.circleTnum+"</td>"//圆周
							+"	<td width='23px'>"+revalue.circleAVG+"</td>"//圆周
							
							+"	<td width='23px'>"+revalue.hardnessFnum+"</td>"//硬度
							+"	<td width='23px'>"+revalue.hardnessTnum+"</td>"//硬度
							+"	<td width='23px'>"+revalue.hardnessAVG+"</td>"//硬度
							
							+"	<td width='23px'>"+revalue.others+"</td>"//其他
							
							
							
							+"	<td width='40px'>"+isError+"</td>"//判断
							+"	<td width='60px'>"+runStep+"</td>"//处理措施
							+"	<td width='30px'>"+revalue.badNum+"</td>"//质量问题数量
							+"	<td width='40px'>"+numUnit+"</td>"//单位
							+"	<td width='70px'>"+revalue.runRemark+"</td>"//备注
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
			jAlert("质检员不可为空,请先登录!","提示");
			return false;
		}
		//初始化时间
	    
		var nowDate=getDateHourAndMinute();
		var trLength=$("#gczj-tab-tbody tr").length;//获取tr的行数
        var formId="form-control ";
        var classId="zl-keyboard-"+trLength+"td4";
        var classIds =formId+classId;
     
			
         var se3="<select style='width:100px;'>";
         for(var i=0;i<qx_filter_key1.length;i++ ){
        	 if(i==0){
        		 se3+="<option value='"+qx_filter_key1[i]+"' selected>"+qx_filter_val1[i]+"</option>" ;
             }else{
            	 se3+="<option value='"+qx_filter_key1[i]+"' >"+qx_filter_val1[i]+"</option>";
             }
         }
         se3+="</select>";

           

        var se4="<input type='text' value='0'></input>";
   	 	var se5="<input type='text' value='0'></input>";
   	 	var se6="<input type='text' value='0'></input>";
   	 	
   	 	var se7="<input type='text' value='0'></input>";
	 	var se8="<input type='text' value='0'></input>";
	 	var se9="<input type='text' value='0'></input>";
	 	
	 	var se10="<input type='text' value='0'></input>";
	 	var se11="<input type='text' value='0'></input>";
	 	var se12="<input type='text' value='0'></input>";
	 	
	 	var se13="<input type='text' value='0'></input>";
	 	var se14="<input type='text' value='0'></input>";
	 	var se15="<input type='text' value='0'></input>";
	 	
	 	var se16="<input type='text' value='0'></input>";
	 	
	   var se17="<select style='width:40px;height:30px;'>";
        for(var i=0;i<isTrueKey.length;i++ ){
       	 if(i==0){
       		 se17+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
            }else{
           	 se17+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
            }
        }
        se17+="</select>";
        
        
        <!--处理措施-->
        var se18="<select style='height:30px;'>";
        for(var i=0;i<csKey.length;i++ ){
       	 if(i==0){
       		 se18+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
            }else{
           	 se18+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
            }
        }
        se18+="</select>";
	 	
	 	<!--残缺数量-->
        var se19="<input type='text' value='0'></input>";
        <!--单位-->
 	  	var se20="<select style='width:40px;height:30px;'>";
 	      for(var i=0;i<unit_key.length;i++ ){
 	     	 if(i==0){
 	     		 se20+="<option value='"+unit_key[i]+"' selected>"+unit_val[i]+"</option>";
 	          }else{
 	         	 se20+="<option value='"+unit_key[i]+"' >"+unit_val[i]+"</option>";
 	          }
 	      }
 	    se20+="</select>";
        
        
 	   var se21="<select style='width:70px;height:30px;'>";
 	   for(var i=0;i<standkey.length;i++ ){
		   if(i==0){
		   		se21+="<option value='"+standval[i]+"' selected>"+standkey[i]+"</option>" ;
		   }else{
		        se21+="<option value='"+standval[i]+"' >"+standkey[i]+"</option>";
		   }
	   }
	    se21+="</select>";
         
		var html="<tr>"
			+"	<td>"
			+"		<input value='' type='checkbox' style='width:20px;height:20px;' name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
			+"	</td>"
			+"	<td width='20px' height='30px'>"+(trLength+1)+"</td>"  //序号
			+"	<td width='50px' height='30px'>"+nowDate+"</td>"		//时间
			+"	<td width='50px' height='30px'>"+se3+"</td>"			//外观质量
			+"	<td width='23px' height='30px'>"+se4+"</td>" 			//重量负
			+"	<td width='23px' height='30px'>"+se5+"</td>"			//
			+"	<td width='23px' height='30px'>"+se6+"</td>"			//
			+"	<td width='23px' height='30px'>"+se7+"</td>"			//吸负
			+"	<td width='23px' height='30px'>"+se8+"</td>"			//
			+"	<td width='23px' height='30px'>"+se9+"</td>"			//
			+"	<td width='23px' height='30px'>"+se10+"</td>"			//圆周
			+"	<td width='23px' height='30px'>"+se11+"</td>"	        //
			+"	<td width='23px' height='30px'>"+se12+"</td>"			//
			+"	<td width='23px' height='30px'>"+se13+"</td>"			//硬度
			+"	<td width='23px' height='30px'>"+se14+"</td>"			//
			+"	<td width='23px' height='30px'>"+se15+"</td>"			//
			+"	<td width='23px' height='30px'>"+se16+"</td>"			//其他
			+"	<td width='40px'>"+se17+"</td>"			//判断
			+"	<td width='60px'>"+se18+"</td>"			//处理措施
			+"	<td width='30px'>"+se19+"</td>"			//缺陷数量
			+"	<td width='40px'>"+se20+"</td>"			//单位
			+"	<td width='70px'>"+se21+"</td>"			//备注
			+"</tr>";
			$("#gczj-tab-tbody").append(html);
			var trRow="#gczj-tab-tbody tr:eq("+(trLength)+")";//当前行
			$(trRow).click();//行 获取焦点
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
			jAlert("质检员不可为空,请先登录!","提示");
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
 		            url: "${pageContext.request.contextPath}/wct/massProcess/deleteFilterByIds.do",//要访问的后台地址
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
			jAlert("质检员不可为空,请先登录!","提示");
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
	        		 var td3 = "";//当前第3列 	外观 select
	        		 var td4 = "";//当前第4列 	重量 input
					 var td5 = "";//当前第5列 	
					 var td6 = "";//当前第6列 	
					 var td7 = "";//当前第7列 	吸阻input
					 var td8 = "";//当前第8列 	
					 var td9 = "";//当前第9列 	
					 var td10 = "";//当前第10列 	圆周
					 var td11 = "";//当前第11列 	
					 var td12=""; //
					 var td13="";//硬度
					 var td14="";//
					 var td15="";
					 var td16="";//其他
					 var td17="";//判断
					 var td18="";//处理措施 select
					 var td19="";//缺陷数量
					 var td20="";//数量单位select
					 var td21="";//备注
					 inserttime=td2;
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 td2 = tr.find("td:eq(2) input").val();
						 td3 = tr.find("td:eq(3) select").val();
						 
						 td4 = tr.find("td:eq(4) input").val();
						 td5 = tr.find("td:eq(5) input").val();
						 td6 = tr.find("td:eq(6) input").val();
						 
						 td7 = tr.find("td:eq(7) input").val();
						 td8 = tr.find("td:eq(8) input").val();
						 td9 = tr.find("td:eq(9) input").val();
						 
						 td10 = tr.find("td:eq(10) input").val();
						 td11 = tr.find("td:eq(11) input").val();
						 td12 = tr.find("td:eq(12) input").val();
						 
						 td13 = tr.find("td:eq(13) input").val();
						 td14 = tr.find("td:eq(14) input").val();
						 td15 = tr.find("td:eq(15) input").val();
						 
						 td16 = tr.find("td:eq(16) input").val();
						 td17 = tr.find("td:eq(17) select").val();
						 td18 = tr.find("td:eq(18) select").val();
						 
						 td19 = tr.find("td:eq(19) input").val();
						 td20 = tr.find("td:eq(20) select").val();
						 td21 = tr.find("td:eq(21) select").val();
						 inserttime=td2;
					 }else{
						 td3 = tr.find("td:eq(3)").text();
						 td4 = tr.find("td:eq(4)").text();
						 td5 = tr.find("td:eq(5)").text();
						 td6 = tr.find("td:eq(6)").text();
						 td7 = tr.find("td:eq(7)").text();
						 td8 = tr.find("td:eq(8)").text();
						 td9 = tr.find("td:eq(9)").text();
						 td10 = tr.find("td:eq(10)").text();
						 td11 = tr.find("td:eq(11)").text();
						 td12 = tr.find("td:eq(12)").text();
						 td13 = tr.find("td:eq(13)").text();
						 td14 = tr.find("td:eq(14)").text();
						 td15 = tr.find("td:eq(15)").text();
						 td16 = tr.find("td:eq(16)").text();
						 td17 = tr.find("td:eq(17)").text();
						 td18 = tr.find("td:eq(18)").text();
						 td19 = tr.find("td:eq(19)").text();
						 td20 = tr.find("td:eq(20)").text();
						 td21 = tr.find("td:eq(21)").text();
						 
						 //根据中文找 key
					 	for(var j=0;j<qx_filter_key1.length;j++ ){//外观质量情况 select
							if(qx_filter_val1[j]==td3){
								td3=qx_filter_key1[j];
								 break;
							 }
						}
						for(var j=0;j<isTrueKey.length;j++ ){//判断    select
							if(isTrueVal[j]==td17){
								td17=isTrueKey[j];
								 break;
							 }
						}
						
						for(var j=0;j<csKey.length;j++ ){//处理措施    select
							if(csVal[j]==td18){
								td18=csKey[j];
								 break;
							 }
						}
						for(var j=0;j<unit_key.length;j++ ){//单位
							if(unit_val[j]==td20){
								td20=unit_key[j];
								 break;
							 }
						}
						for(var j=0;j<standkey.length;j++){
							if(standval[j]==td21){
								td21=standkey[j];
								break;
							}
						}
					 }
					 
					 if(formattedtime.test(inserttime) || formattedtime2.test(inserttime)){
						 flag=true;
					 }else{
						 flag=false;
					 }
				     reqString += '{"qmFilterProcessId":"'+td0//主键
				    	+ '","shortTime":"'+ td2//页面显示的录入时间
						+ '","runCondition":"'+ td3//外观质量情况
						+ '","weightTNum":"'+ td5//重量
						+ '","weightFNum":"'+ td4//重量
						+ '","weightAVG":"'+ td6//重量
						
						+ '","pressureTnum":"'+ td8//吸阻
						+ '","pressureFnum":"'+ td7//吸阻
						+ '","pressureAVG":"'+ td9//吸阻
						
						+ '","circleTnum":"'+ td11//圆周
						+ '","circleFnum":"'+ td10//圆周
						+ '","circleAVG":"'+ td12//圆周
						
						
						+ '","hardnessTnum":"'+ td14//硬度
						+ '","hardnessFnum":"'+ td13//硬度
						+ '","hardnessAVG":"'+ td15//硬度
						
						+ '","others":"'+ td16
						
						+ '","isError":"'+ td17//判断
						+ '","runStep":"'+ td18//处理措施
						+ '","badNum":"'+ td19//质量问题数量
						+ '","numUnit":"'+ td20//质量问题数量单位
						+ '","runRemark":"'+ td21//备注
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
            url: "${pageContext.request.contextPath}/wct/massProcess/editFilterBean.do",//要访问的后台地址
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
    			<td width='20px' rowspan="2"><input id="gczj-tab-thead-CKA" style="width:20px;height:20px;" name="gczj-tab-thead-CKA" type="checkbox"/></td>
			    <td width='20px' rowspan="2">序号</td>
			    <td width='50px'rowspan="2">时间</td>
			    <td width='50px'rowspan="2">外观</td>
			    <td width='60px'colspan="3">重量mg</td>
			    <td width='60px'colspan="3">吸阻Pa</td>
			    <td width='60px'colspan="3">圆周mm</td>
			    <td width='60px'colspan="3">硬度%</td>
			    <td width='20px'rowspan="2">其他</td>
			    <td width='40px'rowspan="2">判断</td>
			    <td width='60px'rowspan="2">处理措施</td>
			    <td width='20px'rowspan="2">缺陷数量</td>
				<td width='30px'rowspan="2">数量单位</td>
				<td width='50px'rowspan="2">备注</td>
			 </tr>
			 <tr>
			   	<td width='20px'>负</td>
			  	<td width='20px'>正</td>
				<td width='20px'>均值</td>
			    <td width='20px'>负</td>
			    <td width='20px'>正</td>
			    <td width='20px'>均值</td>
			    <td width='20px'>负</td>
			    <td width='20px'>正</td>
			    <td width='20px'>均值</td>
			    <td width='20px'>负</td>
			    <td width='20px'>正</td>
			    <td width='20px'>均值</td>
		      </tr>
		</thead>
		<tbody id="gczj-tab-tbody">
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d43f4d73d86d014d73df6da90000"/>
	</table>
</html>
