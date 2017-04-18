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
	/*var arryKey、arryVal、qx_pack_tzKey、qx_pack_tzVal、csKey、csVal这些是公共的属性*/
	var gczjChangeClick =null;	
	var gczjChangeType ="init";
	var calculat;
	var gw=0.0;
	var sw=0.0;
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
        calculat=function(td,type){
        	var v=td.value;
        	if(type==1){
        		gw=v;
        	}else if(type==2){
        		sw=v;
        	}else if(type==3){
        		var avg=0.000;
        		if(parseFloat(sw)==0){
        		}else{
        			avg=(parseFloat(sw)-parseFloat(gw))/parseFloat(sw);
        		}
        		$(td).val(avg.toFixed(3));
        		gw=0.0;
        		sw=0.0;
        	}
        	
        }
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
	            		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(2) input").val();
			            }
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds = formId+classId;
						var se2="<input type='text' class='"+classIds+"' onblur='calculat(this,1)' style='width:100%;height:30px;border-radius:5px;font-size:2px;text-align:center;' value='"+oldText+"' />";
	                   	$(this).html(se2);
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
	            	if(index==3){
	            		if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(3) input").val();
			            }
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds = formId+classId;
						var se3="<input type='text' class='"+classIds+"' onblur='calculat(this,2)' style='width:100%;height:30px;border-radius:5px;font-size:2px;text-align:center;' value='"+oldText+"' />";
	                   	$(this).html(se3);
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
					if(index==4){
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(4) input").val();
			            }
						var formId="form-control ";
				        var classId="zl-keyboard-"+index+"td"+index;
				        var classKey=".zl-keyboard-"+index+"td"+index;
				        var classIds = formId+classId;
						var se4="<input type='text' class='"+classIds+"' onfocus='calculat(this,3)'  style='width:100%;height:30px;border-radius:5px;font-size:2px;text-align:center;' value='"+oldText+"' />";
	                   	$(this).html(se4);
	                    /* $(classKey).keyboard({
	        				layout:"1234567890.",
	        				customLayout: {
	        					'default': [
	        						"{accept} 1 2 3 4 5 6 ",
	        						"7 8 9 0 . {bksp}"
	        					]
	        				}
	        			});	 */
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
		$.post("${pageContext.request.contextPath}/wct/massProcess/getGlycerineList.do",params,function(data){
				var html="";
				if(data==null) return;
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						html+="<tr>"
							+"	<td>"
							+"		<input value="+revalue.qmProcessId+" type='checkbox' style='width:20px;height:20px;' name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td width='30px'>"+(i+1)+"</td>"
							+"	<td width='80px'>"+revalue.darWeight+"</td>"//干棒检测
							+"	<td width='80px'>"+revalue.wetWeight+"</td>"//湿棒检测
							+"	<td width='80px'>"+revalue.useWeight+"</td>"//施加量
							+"</tr>";
					}
				}
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
	    
	    var trLength=$("#czg-tab-tbody tr").length;//获取tr的行数

		//处理措施
        var se2="<input type='text' style='width:100%;height:30px;' value='0'></input>"
        var se3="<input type='text' style='width:100%;height:30px;' value='0'></input>";
	    
  	 	var se4="<input type='text' style='width:100%;height:30px;' value='0'></input>";
		var html="<tr>"
			+"	<td>"
			+"		<input value='' type='checkbox' style='width:20px;height:20px;'  name='gczj-tab-tbody-CK' id='gczj-tab-tbody-CK'/></td>"
			+"	</td>"
			+"	<td width='30px'>"+(trLength+1)+"</td>"  //序号
			+"	<td width='80px'>"+se2+"</td>"			//干
			+"	<td width='80px'>"+se3+"</td>"	//湿
			+"	<td width='80px'>"+se4+"</td>"	//施加量	
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
         //alert(ids);
         jConfirm('删除提示?', '标题', function(r) {
 			if(r){
 				 //跑到后台处理后 并查询
 				$.ajax({
 		            type: "post",//使用get方法访问后台
 		            dataType: "json",//返回json格式的数据
 		            url: "${pageContext.request.contextPath}/wct/massProcess/deleteGlycerineByIds.do",//要访问的后台地址
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
		//alert("dd:"+zjy);
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
	        		 var td2 = "";//当前第3列 	干
					 var td3 = "";//当前第4列 	湿
					 var td4 = "";//当前第5列 	施加
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 td2 = tr.find("td:eq(2) input").val();
						 td3 = tr.find("td:eq(3) input").val();
						 td4 = tr.find("td:eq(4) input").val();
					 }else{
						 td2 = tr.find("td:eq(2)").text();
						 td3 = tr.find("td:eq(3)").text();
						 td4 = tr.find("td:eq(4)").text();
					 }
				     reqString += '{"qmProcessId":"'+td0//主键
						+ '","darWeight":"'+ td2//干
						+ '","wetWeight":"'+ td3//湿
						+ '","useWeight":"'+ td4//施加量
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
	 	var params =reqString;//{eqpCod:equ}
        //跑到后台处理后 并查询
         $.ajax({
            type: "post",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${pageContext.request.contextPath}/wct/massProcess/editGlycerineBean.do",//要访问的后台地址
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
					<input id="gczj-tab-thead-CKA" style="width:20px;height:20px;" name="gczj-tab-thead-CKA" type="checkbox"/></td>
				</td>
				<td width='30px'>序号</td>
				<td width='80px'>干棒检测g/10支</td>
				<td width='80px'>湿棒检测g/10支</td>
				<td width='80px'>施加量%</td> 
			</tr>
		</thead>
		<tbody id="gczj-tab-tbody">
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d43f4d73d86d014d73df6da90000"/>
	</table>
</html>
