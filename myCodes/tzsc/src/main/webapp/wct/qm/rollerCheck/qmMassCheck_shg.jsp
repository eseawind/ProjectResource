<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
	#shg-tab-thead tr td{text-align:center;}
	#shg-tab-tbody tr{height:30px;text-align:center;font-size:12px;}
</style>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript" ></script>
<script type="text/javascript">
	/*var arryKey、arryVal、qxKey、qxVal、csKey、csVal这些是公共的属性*/
	var formattedtime=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var formattedtime2=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})-(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var flag;
	var inserttime;
	var shgChangeClick =null;	
	var shgChangeType ="init";
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
        $("#shg-tab-thead-CKA").click(function() {
        	$("#shg-tab-tbody tr").each(function(idx) {
           		if($(this).find("#shg-tab-tbody-CK").get(0)!=null
                   	&&$(this).find("#shg-tab-tbody-CK").get(0)!=undefined){
           			$(this).find("#shg-tab-tbody-CK").get(0).checked = 
               			$("#shg-tab-thead-CKA").get(0).checked;
               }
			});
        });
        shgChangeClick=function(type){
			//编辑事件
			$("#shg-tab-tbody tr").die().live("click",function(){
				var tr=$(this);
				if(tr.attr("edit")){
					return;
				};
				$(this).find("#shg-tab-tbody-CK").get(0).checked=true;//选中当前行
				tr.attr("edit",true);
				tr.find("td").each(function(index){
	            	var oldText=$(this).text();//原来的值   5
	            	var zjId = tr.find("#shg-tab-tbody-CK").get(0).value;//如果有 就表示 数据库数据
	            	//根据key 或 value 赋值
	            	if(index==2){
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td4";
				        var classIds = formId+classId;
						var se2="<input onkeyup='checkInput(this)' type='text' class='"+classIds+"' style='width:140px;height:30px;' value='"+oldText+"' />";
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
	                    var temp="<select style='width:100px;height:30px;'>";
	                    for(var i=0;i<qxKey.length;i++ ){
	                        if(qxKey[i]==oldText||qxVal[i]==oldText){
	                        	temp+="<option value='"+qxKey[i]+"' selected>"+qxVal[i]+"</option>" ;
	                        }else{
	                        	temp+="<option value='"+qxKey[i]+"' >"+qxVal[i]+"</option>";
	                        }
	                    }
	                    temp+="</select>";
	                   $(this).html(temp);
					}
					if(index==4){
	              		if(zjId==""||zjId==undefined){//表示是 新增的行
	              			oldText = tr.find("td:eq(4) select").val();
			            }
	              		var temp="<select style='width:100px;height:30px;'>";
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
					if(index==5){//处理措施
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(5) select").val();
			            }
	                    var temp="<select style='width:120px;height:30px;'>";
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
					//备注
					if(index==6){
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(6) select").val();
			            }
						 var se6="<select style='width:100px;height:30px;'>";
						 for(var i=0;i<standkey.length;i++){
							 if(standval[i]==oldText || standkey[i]==oldText){
								 se6+="<option value'"+standval[i]+"' selected>"+standkey[i]+"</option>";
							 }else{
								 se6+="<option value='"+standval[i]+"'>"+standkey[i]+"</option>";
							 }
						 }
						 se6+="</select>";
						// var se2="<input style='width:100%;' value='"+oldText+"''/>";
	                   	$(this).html(se6); 
					}
					
	            }); 
	        });
        }
       shgChangeClick("load");//供编辑用
	});
	//查询
	function shgSearch(){
		shgChangeType ="query";
		//var zjy =$("#isMassCheckZjr").text();
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
		$("#shg-tab-thead-CKA").get(0).checked = false;//顶部checkbox为不选状态
		var params ={proWorkId : schWorkorderId}//工单主键ID
		$.post("${pageContext.request.contextPath}/wct/massStem/getList.do",params,function(data){
				var html="";
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var checkCondition="";//外观质量情况
						var isError="";//判断
						var stemStep="";//处理措施
						//根据 key 或 value找 value
						for(var j=0;j<qxKey.length;j++ ){//外观质量情况
							if(qxKey[j]==revalue.checkCondition||qxVal[j]==revalue.checkCondition){
								checkCondition=qxVal[j];
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
							if(csKey[j]==revalue.stemStep||csVal[j]==revalue.stemStep){
								stemStep=csVal[j];
								 break;
							 }
						}
						html+="<tr>"
							+"	<td>"
							+"		<input value="+revalue.qmStemId+" type='checkbox' name='shg-tab-tbody-CK' id='shg-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td>"+(i+1)+"</td>"
							+"	<td>"+revalue.shortTime+"</td>"
							+"	<td>"+checkCondition+"</td>"
							+"	<td>"+isError+"</td>"
							+"	<td>"+stemStep+"</td>"
							+"	<td>"+revalue.stemRemark+"</td>"
							+"</tr>";
					}
				}else{}
				$("#shg-tab-tbody").html(html);
			},"JSON");

	}				
	//新增
	function shgInsert(){
		shgChangeType ="insert";
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
		var trLength=$("#shg-tab-tbody tr").length;//获取tr的行数
        var se3="<select style='width:100px;'>";
        for(var i=0;i<qxKey.length;i++ ){
        	if(i==0){
            	se3+="<option value='"+qxKey[i]+"' selected>"+qxVal[i]+"</option>" ;
            }else{
          		se3+="<option value='"+qxKey[i]+"' >"+qxVal[i]+"</option>";
            }
        }
        se3+="</select>";

       	var se4="<select style='width:100px;'>";
        for(var i=0;i<isTrueKey.length;i++ ){
        	 if(i==0){
        		 se4+="<option value='"+isTrueKey[i]+"' selected>"+isTrueVal[i]+"</option>" ;
             }else{
            	 se4+="<option value='"+isTrueKey[i]+"' >"+isTrueVal[i]+"</option>";
             }
         }
        se4+="</select>";
		
		 var se5="<select style='width:100px;'>";
         for(var i=0;i<csKey.length;i++ ){
        	 if(i==0){
        		 se5+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
             }else{
            	 se5+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
             }
         }
         se5+="</select>";

  	 	var se6="<select style='width:100px;'>";
  	 	for(var i=0;i<standkey.length;i++){
    	 	if(i==0){
    	 		se6+="<option value='"+standval[i]+"' selected>"+standkey[i]+"</option>";
    	 	}else{
    	 		se6+="<option value='"+standval[i]+"' >"+standkey[i]+"</option>";
    	 	}
    	 }
  	 	se6+="</select>";
  	 	 	
         
		var html="<tr>"
			+"	<td>"
			+"		<input value='' type='checkbox' style='width:20px;height:20px;' name='shg-tab-tbody-CK' id='shg-tab-tbody-CK'/></td>"
			+"	</td>"
			+"	<td>"+(trLength+1)+"</td>"  //序号
			+"	<td>"+nowDate+"</td>"		//时间
			+"	<td>"+se3+"</td>"			//情况
			+"	<td width='80'>"+se4+"</td>" //判断
			+"	<td>"+se5+"</td>"			//处理措施
			+"	<td>"+se6+"</td>"	//备注	
			+"</tr>";
			$("#shg-tab-tbody").append(html);
			var trRow="#shg-tab-tbody tr:eq("+(trLength)+")";//当前行
			$(trRow).click();//行 获取焦点
	}
	//修改
	function shgUpdate(){
		shgChangeType ="update";
	}
	//删除
	function shgDeletes(){
		shgChangeType ="delete";
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
		 $("#shg-tab-tbody tr").each(function(idx) {
            var trCk = $(this).find("#shg-tab-tbody-CK").get(0);//checkbox对象
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
 		            url: "${pageContext.request.contextPath}/wct/massStem/deleteByIds.do",//要访问的后台地址
 		            data: "ids=" + ids,//要发送的数据
 		            complete :function(){
 		                //$("#load").hide();
 		            },//AJAX请求完成时隐藏loading提示
 		            success : function(r) {
 						if(r.success){
 							shgSearch();//重新查询下
 						}else{ 
 							jAlert("删除失败!","提示");
 						}
 					}
 		         });
 			}
 		 });
	}

	//保存
	function shgSave(){
		shgChangeType ="save";
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
		 $("#shg-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#shg-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键，判断更新或者新增
	        		 var td1 = tr.find("td:eq(1)").text();//当前第1列 序号
	        		 var td2 = tr.find("td:eq(2)").text();//当前第2列  时间
	        		 var td3 = "";//当前第3列 	检查情况 select
					 var td4 = "";//当前第4列 	判断    select
					 var td5 = "";//当前第5列 	处理措施    select
					 var td6 = "";//当前第6列 	备注   text
					 inserttime=td2;
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 td2 = tr.find("td:eq(2) input").val();
						 td3 = tr.find("td:eq(3) select").val();
						 td4 = tr.find("td:eq(4) select").val();
						 td5= tr.find("td:eq(5) select").val();
						 td6 = tr.find("td:eq(6) select").val();
						 inserttime=td2;
					 }else{
						 td3 = tr.find("td:eq(3)").text();
						 td4 = tr.find("td:eq(4)").text();
						 td5 = tr.find("td:eq(5)").text();
						 td6 = tr.find("td:eq(6)").text();
						 //根据中文找 key
					 	for(var j=0;j<qxKey.length;j++ ){//检查情况 select
							if(qxVal[j]==td3){
								td3=qxKey[j];
								 break;
							 }
						}
						for(var j=0;j<isTrueKey.length;j++ ){//判断    select
							if(isTrueVal[j]==td4){
								td4=isTrueKey[j];
								 break;
							 }
						}
						for(var j=0;j<csKey.length;j++ ){//处理措施    select
							if(csVal[j]==td5){
								td5=csKey[j];
								 break;
							 }
						}
					 }
					 if(formattedtime.test(inserttime) || formattedtime2.test(inserttime)){
						 flag=true;
					 }else{
						 flag=false;
					 }
				     reqString += '{"qmStemId":"'+td0//主键
				    	+ '","shortTime":"'+ td2//检测时间
						+ '","checkCondition":"'+ td3//检查情况
						+ '","isError":"'+ td4//判断
						+ '","stemStep":"'+ td5//处理措施
						+ '","stemRemark":"'+ td6//备注
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
            url: "${pageContext.request.contextPath}/wct/massStem/editBean.do",//要访问的后台地址
            data: "stemArray=" + reqString+"&checkArray="+checkString,//要发送的数据
            complete :function(){
                //$("#load").hide();
            },//AJAX请求完成时隐藏loading提示
            success : function(r) {
				if(r.success){
					shgSearch();//重新查询下
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
		<thead id="shg-tab-thead"
			style="background: #5a5a5a; color: #fff;">
			<tr>
				<td width="40">
					<input id="shg-tab-thead-CKA" style="width:20px;height:20px;" name="shg-tab-thead-CKA" type="checkbox"/></td>
				</td>
				<td>序号</td>
				<td>检测时间</td>
				<td>检查情况</td>
				<td>判断</td>
				<td>处理措施</td>
				<td>备注</td>
			</tr>
		</thead>
		<tbody id="shg-tab-tbody">
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d43f4d73d86d014d73df6da90000"/>
	</table>
</html>
