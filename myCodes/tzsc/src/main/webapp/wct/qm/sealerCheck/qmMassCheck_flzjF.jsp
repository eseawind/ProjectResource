<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
	#flzj-tab-thead tr td{text-align:center;}
	#flzj-tab-tbody tr{height:30px;text-align:center;font-size:12px;}
</style>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript" ></script>
<script type="text/javascript">
	/*var arryKey、arryVal、qxKey、qxVal、csKey、csVal这些是公共的属性*/
	var formattedtime=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var formattedtime2=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})-(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var flag;
	var inserttime;
	var flzjChangeClick =null;	
	var flzjChangeType ="init";
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
        $("#flzj-tab-thead-CKA").click(function() {
        	$("#flzj-tab-tbody tr").each(function(idx) {
           		if($(this).find("#flzj-tab-tbody-CK").get(0)!=null&&$(this).find("#flzj-tab-tbody-CK").get(0)!=undefined){
           			$(this).find("#flzj-tab-tbody-CK").get(0).checked =$("#flzj-tab-thead-CKA").get(0).checked;
           			var tr=$(this);
        			tr.click();//行 获取焦点
               }
			});
        });
        
        flzjChangeClick=function(type){
			//编辑事件
			$("#flzj-tab-tbody tr").die().live("click",function(){
				var tr=$(this);
				
				if(tr.attr("edit")){
					return;
				};
				$(this).find("#flzj-tab-tbody-CK").get(0).checked=true;//选中当前行
				tr.attr("edit",true);
				tr.find("td").each(function(index){
	            	var oldText=$(this).text();//原来的值   5
	            	var zjId = tr.find("#flzj-tab-tbody-CK").get(0).value;//如果有 就表示 数据库数据
	            	//根据key 或 value 赋值
	            	if(index==4){
		            	//alert(zjId);
	            		if(zjId==""||zjId==undefined){//表示是 新增的行
		            		//初始化时间
		            	    var nowDate = getDateHourAndMinute();
		                	var formId="form-control ";
					    	var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+index;
					    	var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+index;
					    	var classIds = formId+classId;
		                	var se4="<input type='text' class='"+classIds+"' value='"+nowDate+"'  style='width:100%;height:30px;'></input>";
		            		tr.find("td:eq(4)").html(se4);
		            		$(classKey).keyboard({
	        					layout:"1234567890:-",
	        					customLayout: {
	        						'default': [
	        							"{accept} 1 2 3 4 5 6 ",
	        							"7 8 9 0 : - {bksp}"
	        						]
	        					}
	        				});
	            		}else{
	            			var formId="form-control ";
					        var classId="zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
					        var classKey=".zl-keyboard-"+tr.find("td:eq(1)").text()+"td"+index;
					        var classIds = formId+classId;
	            			var se4="<input type='text' class='"+classIds+"' value='"+oldText+"'  style='width:100%;height:30px;'></input>";
	            			tr.find("td:eq(4)").html(se4);
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
	                	if(zjId==""||zjId==undefined){
	                		oldText = tr.find("td:eq(6) select").val();
	                	}	
					    var se6="<select style='width:100%;height:30px;'>";
					    for(var i=0;i<commentskey.length;i++){
					        if(commentsval[i]==oldText || commentskey[i]==oldText){
					        	se6+="<option value'"+commentsval[i]+"' selected>"+commentskey[i]+"</option>";
					        }else{
					        	se6+="<option value='"+commentsval[i]+"'>"+commentskey[i]+"</option>";
					        }
					    }
					    se6+="</select>";
					    $(this).html(se6);
	                }
	            }); 
	        });
        }

      
       flzjChangeClick("load");//供编辑用
       //initFlTable();//初始化表
       //flzjSearch();//查询数据,填充数据
	});
	function clearParams(){
		$("#flzj-tab-tbody tr").each(function(idx){
			var tr=$(this);
			$(this).find("#flzj-tab-tbody-CK").get(0).checked= false;//反选
			tr.attr("edit",null);//编辑事件 取消
			tr.find("td:eq(4)").html(null);//检查时间
			var checkPd ="对";
			var checkBhg="无需填写";
			tr.find("td:eq(5)").html(checkPd);//检查判定
			tr.find("td:eq(6)").html(checkBhg);//不合格情况
		});
	}
	//查询
	function flzjSearch(){
		flzjChangeType ="query";
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
		$("#flzj-tab-thead-CKA").get(0).checked = false;//顶部checkbox为不选状态
		var params ={proWorkId : schWorkorderId}//工单主键ID massExcipient
		$.post("${pageContext.request.contextPath}/wct/massExcipient/getList.do",params,function(v){
				var reobj=eval("("+v+")");
				var list=reobj.rows;
				clearParams();
				var revalue;
				$("#flzj-tab-tbody tr").each(function(idx){
						var tr=$(this);
						revalue=null;
						var tr=$(this);
						for(var j=0;j<list.length;j++){
							var line=parseInt(list[j].orderNumber)-1;
							if(line==idx){
								revalue=list[j];
								break;
							}
						}
						if(revalue==null){
							revalue={"qmExcipientId":"","checkTime":"","isError":"-","subStandard":"-"};
						}
						var isError="";//检查判定
						var subStandard="";//不合格情况
						//根据 key 或 value找 value
						for(var j=0;j<isTrueKey.length;j++ ){//判断
							if(isTrueKey[j]==revalue.isError||isTrueVal[j]==revalue.isError){
								isError=isTrueVal[j];
								 break;
							 }
						} 
						/* for(var j=0;j<notFitKey.length;j++ ){//不合格情况
							if(notFitKey[j]==revalue.subStandard||notFitVal[j]==revalue.subStandard){
								subStandard=notFitVal[j];
								 break;
							 }
						}	 */
						tr.find("td:eq(0)").html("<input value='"+revalue.qmExcipientId+"' type='checkbox' style='width:20px;height:20px;' name='flzj-tab-tbody-CK' id='flzj-tab-tbody-CK'/>");				
						tr.find("td:eq(4)").html(revalue.checkTime);//检查时间
						tr.find("td:eq(5)").html(isError);//检查判定
						tr.find("td:eq(6)").html(revalue.subStandard);//不合格情况
					
				});
				
			});

	}				
	//新增
	function flzjInsert(){
		flzjChangeType ="insert";
		//var trRow="#flzj-tab-tbody tr:eq("+(trLength)+")";//当前行
		//$(trRow).click();//行 获取焦点
	}
	//修改
	function flzjUpdate(){
		flzjChangeType ="update";
	}
	//删除
	function flzjDeletes(){
		flzjChangeType ="delete";
	}

	//保存
	function flzjSave(){
		flzjChangeType ="save";
		//schWorkorderId="8eb29ba2-928e-4ab4-842e-a01bd5492890";
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
		var zjUserName =$("#massCheckZjr").text();//登录人
		var _eqpId=$('#eqpId').text();//设备id
		var _matId=$('#matId').text();//牌号id
		var _shiftID=$('#shiftID').text();
		//alert("dd:"+zjy);
		var checkString = '[';
			checkString += '{"proWorkId":"'+schWorkorderId 
			+ '","mdShiftName":"'+ massCheckBc//班次
			+ '","mdEqmentName":"'+ massCheckJth//机台号
			+ '","mdEqmentId":"'+ _eqpId//机台id
			+ '","mdMatId":"'+ _matId//牌号id
			+ '","mdShiftId":"'+ _shiftID//班次id
			+ '","mdMatName":"'+ massCheckPm//牌号
			+ '","mdDcgName":"'+ massCheckDcg//挡车工
			+ '","zjUserName":"'+ zjUserName//登录人
			+ '"}';
			checkString += ']';
		
		var reqString = '[';
		var isNextNumber= 0;
		 $("#flzj-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#flzj-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键，判断更新或者新增
	        		 var td1 = tr.find("td:eq(1)").text();//当前第1列 序号
	        		 var td2 = tr.find("td:eq(2)").text();//当前第2列 检查项目
	        		 var td3 = tr.find("td:eq(3)").text();//检查频率
	        		 var td4 = "";//检查时间
					 var td5 = "";//判断    select
					 var td6 = "";//不合格情况
					 inserttime=td4;
					 //alert(tr.attr("edit"));
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 td4 = tr.find("td:eq(4) input").val();
						 td5 = tr.find("td:eq(5) select").val();
						 td6 = tr.find("td:eq(6) select").val();
						 inserttime=td4;
					 }else{
						 td4 = tr.find("td:eq(4)").text();
						 td5 = tr.find("td:eq(5)").text();
						 td6 = tr.find("td:eq(6)").text();
						 inserttime=td4;
						 //根据中文找 key
						for(var j=0;j<isTrueKey.length;j++ ){//判断    select
							if(isTrueVal[j]==td5){
								td5=isTrueKey[j];
								 break;
							 }
						}
						/* for(var j=0;j<notFitKey.length;j++ ){//复检    select
							if(notFitVal[j]==td6){
								td6=notFitKey[j];
								 break;
							 }
						} */
						
					 }
					 
					 if(formattedtime.test(inserttime) || formattedtime2.test(inserttime)){
						 flag=true;
					 }else{
						 flag=false;
					 } 
				     reqString += '{"qmExcipientId":"'+td0//主键
				     	+ '","orderNumber":"'+ td1//排序号
						+ '","checkItem":"'+ td2//检测项目
						+ '","checkRate":"'+ td3//检查频率
						+'","checkTime":"'+td4//检查时间
						+ '","isError":"'+ td5//检查判断
						+ '","subStandard":"'+ td6//不合格情况
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
            url: "${pageContext.request.contextPath}/wct/massExcipient/editBean.do",//要访问的后台地址
            data: "excipientArray=" + reqString+"&checkArray="+checkString,//要发送的数据
            complete :function(){
                //$("#load").hide();
            },//AJAX请求完成时隐藏loading提示
            success : function(r) {
				if(r.success){
					flzjSearch();//重新查询下
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
		<thead id="flzj-tab-thead"
			style="background: #5a5a5a; color: #fff;">
			<tr>
				<td width="40">
					<input id="flzj-tab-thead-CKA" style="width:20px;height:20px;" name="flzj-tab-thead-CKA" type="checkbox"/></td>
				</td>
				<td width="70px">序号</td>
				<td width="130px">检查项目</td>
				<td width="132px">检查频率</td>
				<td width="132px">检查时间</td>
				<td width="132px">检查判定</td>
				<td width="165px">不合格情况</td>
			</tr>
		</thead>
		<tbody id="flzj-tab-tbody">
			<tr>
				<td width="40">
					<input value="" id="flzj-tab-tbody-CK" style="width:20px;height:20px;" name="flzj-tab-thead-CK" type="checkbox"/></td>
				</td>
				<td width="70px">1</td>
				<td width="130px">接班<br/>辅料确认</td>
				<td width="132px">每班<br/>接班</td>
				<td width="132px"></td>
				<td width="132px">对</td>
				<td width="165px">无需填写</td>
			</tr>
			<tr>
				<td width="40">
					<input value="" id="flzj-tab-tbody-CK" style="width:20px;height:20px;" name="flzj-tab-thead-CK" type="checkbox"/></td>
				</td>
				<td width="70px">2</td>
				<td width="130px">班中接收<br/>辅料确认</td>
				<td width="132px">班中接<br/>收辅料</td>
				<td width="132px"></td>
				<td width="132px">对</td>
				<td width="165px">无需填写</td>
			</tr>
			<tr>
				<td width="40">
					<input value="" id="flzj-tab-tbody-CK" style="width:20px;height:20px;" name="flzj-tab-thead-CK" type="checkbox"/></td>
				</td>
				<td width="70px">3</td>
				<td width="130px">条烟外观<br/>质量检测</td>
				<td width="132px">接班一<br/>小时内</td>
				<td width="132px"></td>
				<td width="132px">对</td>
				<td width="165px">无需填写</td>
			</tr>
			<tr>
				<td width="40">
					<input value="" id="flzj-tab-tbody-CK" style="width:20px;height:20px;" name="flzj-tab-thead-CK" type="checkbox"/></td>
				</td>
				<td width="70px">4</td>
				<td width="130px">箱装缺条<br/>检测</td>
				<td width="132px">接班一<br/>小时内</td>
				<td width="132px"></td>
				<td width="132px">对</td>
				<td width="165px">无需填写</td>
			</tr>
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d43f4d73d86d014d73df6da90000"/>
	</table>
</html>
