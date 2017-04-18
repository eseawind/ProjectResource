<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
	#czg-tab-tbody tr{height:30px;text-align:center;}
	#czg-tab-thead tr td{text-align:center;}
</style>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript" ></script>
<script type="text/javascript">
	/*var arryKey、arryVal、qx_pack_key、qx_pack_val、csKey、csVal这些是公共的属性*/
	var formattedtime=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var formattedtime2=/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})-(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
	var flag;
	var inserttime;
	var changeClick =null;	
	var changeType ="init";
	$(function(){
		//默认光标选中文本框
	    $("#cardNum").focus();
		//checked全选、反选事件
        $("#czg-tab-thead-CKA").click(function() {
        	$("#czg-tab-tbody tr").each(function(idx) {
           		if($(this).find("#czg-tab-tbody-CK").get(0)!=null
                   	&&$(this).find("#czg-tab-tbody-CK").get(0)!=undefined){
           			$(this).find("#czg-tab-tbody-CK").get(0).checked = 
               			$("#czg-tab-thead-CKA").get(0).checked;
               }
			});
        });
        changeClick=function(type){
			//编辑事件
			$("#czg-tab-tbody tr").die().live("click",function(){
				var tr=$(this);
				if(tr.attr("edit")){
					return;
				};
				$(this).find("#czg-tab-tbody-CK").get(0).checked=true;//选中当前行
				tr.attr("edit",true);
				tr.find("td").each(function(index){
	            	var oldText=$(this).text();//原来的值   5
	            	var zjId = tr.find("#czg-tab-tbody-CK").get(0).value;//如果有 就表示 数据库数据
	            	//根据key 或 value 赋值
	            	if(index==2){
	            		if(oldText=="未复检时间"){
	            			oldText=getDateHourAndMinute();
	            		}
	            		var formId="form-control ";
				        var classId="zl-keyboard-"+tr.find("td:eq(4)").text()+"td"+index;
				        var classKey=".zl-keyboard-"+tr.find("td:eq(4)").text()+"td"+index;
				        var classIds =formId+classId;
	            	    var se2="<input type='text' class='"+classIds+"' style='width:100px;height:30px;' value='"+oldText+"''/>";
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
	                		oldText = tr.find("td:eq(3) select").val();
			            }
	                    var se3="<select style='width:100px;height:30px;'>";
	                    for(var i=0;i<qx_pack_key.length;i++ ){
	                    	 if(qx_pack_key[i]==oldText||qx_pack_val[i]==oldText){
	                        	se3+="<option value='"+qx_pack_key[i]+"' selected>"+qx_pack_val[i]+"</option>" ;
	                        }else{
	                        	se3+="<option value='"+qx_pack_key[i]+"' >"+qx_pack_val[i]+"</option>";
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
				        var classId="zl-keyboard-"+tr.find("td:eq(4)").text()+"td4";
				        var classKey=".zl-keyboard-"+tr.find("td:eq(4)").text()+"td4";
				        var classIds =formId+classId;
		              	var se4="<input type='text' value='"+oldText+"' " 
		                    	+" class='"+classIds+"' "
		                    	+"style='width:100px;height:30px;'/>";
		                $(this).html(se4);
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
					if(index==5){//处理措施
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(5) select").val();
			            }
	                    var se5="<select style='width:100px;height:30px;'>";
	                    for(var i=0;i<unit_key.length;i++ ){
	                        if(unit_key[i]==oldText||unit_val[i]==oldText){
	                        	se5+="<option value='"+unit_key[i]+"' selected>"+unit_val[i]+"</option>" ;
	                        }else{
	                        	se5+="<option value='"+unit_key[i]+"' >"+unit_val[i]+"</option>";
	                        }
	                    }
	                    se5+="</select>";
	                   $(this).html(se5);
					}
					if(index==6){//处理措施
						if(zjId==""||zjId==undefined){//表示是 新增的行
							oldText = tr.find("td:eq(6) select").val();
			            }
	                    var se6="<select style='width:100px;height:30px;'>";
	                    for(var i=0;i<csKey.length;i++ ){
	                        if(csKey[i]==oldText||csVal[i]==oldText){
	                        	se6+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
	                        }else{
	                        	se6+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
	                        }
	                    }
	                    se6+="</select>";
	                    $(this).html(se6);
					}
	            }); 
	        });
        };
        changeClick("load");//供编辑用
	});
	//查询
	function search(){
		changeType ="query";
		$("#czg-tab-thead-CKA").get(0).checked = false;//顶部checkbox为不选状态
		var params ={proWorkId : schWorkorderId,checkType:"2"};//工单主键ID
		$.post("${pageContext.request.contextPath}/wct/massFrist/getList.do",params,function(data){
				var html="";
				var list=data.rows;
				if(data.rows!=null&&data.rows.length>0){
					for(var i=0;i<data.rows.length;i++){
						var revalue=list[i];
						var checkCondition="无质量问题";//默认值
						var checkStep="无需处理";//默认值
						var failureUom="-";
						//根据 key 或 value找 value
						for(var j=0;j<qx_pack_key.length;j++ ){
							if(qx_pack_key[j]==revalue.checkCondition||qx_pack_val[j]==revalue.checkCondition){
								checkCondition=qx_pack_val[j];
								 break;
							}
						}
						for(var j=0;j<unit_key.length;j++ ){
							if(unit_key[j]==revalue.failureUom||unit_val[j]==revalue.failureUom){
								failureUom=unit_val[j];
								 break;
							 }
						}
						for(var j=0;j<csKey.length;j++ ){
							if(csKey[j]==revalue.checkStep||csVal[j]==revalue.checkStep){
								checkStep=csVal[j];
								break;
							 }
						}
						var checkTime=revalue.checkTime;
						if(revalue.checkTime==null||revalue.checkTime==""){
							checkTime ="未复检时间";
						}
						var addUserName=revalue.addUserName;
						if(revalue.addUserName==null||revalue.addUserName==""){
							addUserName ="未签字";
						}
						html+="<tr>"
							+"	<td>"
							+"		<input value="+revalue.qmFirstId+" type='checkbox' style='width:20px;height:20px;' name='czg-tab-tbody-CK' id='czg-tab-tbody-CK'/></td>"
							+"	</td>"
							+"	<td>"+(i+1)+"</td>"
							+"	<td>"+checkTime+"</td>"
							+"	<td>"+checkCondition+"</td>"	//存在问题
							+"	<td>"+revalue.failureNum+"</td>"		//数量
							+"	<td>"+failureUom+"</td>"		//单位
							+"	<td>"+checkStep+"</td>"		//处理措施
							+"	<td>"+addUserName+"</td>" //签名
							+"</tr>";
					}
				}else{}
				$("#czg-tab-tbody").html(html);
			},"JSON");

	}				
	//新增
	function insert(){
		changeType ="insert";
		var zjy =$("#isMassCheckZjr").text();
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("没有工单号不可以操作","提示");
			return false;
		}
	    if(zjy!='true'){
			jAlert("自检员不可为空,请先登录!","提示");
			return false;
		}
		var nowDate = getDateHourAndMinute();
		var trLength=$("#czg-tab-tbody tr").length;//获取tr的行数
        var se3="<select style='width:100px;'>";
        for(var i=0;i<qx_pack_key.length;i++ ){
        	if(i==0){
            	se3+="<option value='"+qx_pack_key[i]+"' selected>"+qx_pack_val[i]+"</option>" ;
            }else{
          		se3+="<option value='"+qx_pack_key[i]+"' >"+qx_pack_val[i]+"</option>";
            }
        }
        se3+="</select>";
        var formId="form-control ";
        var classId="zl-keyboard-"+trLength+"td4";
        var classIds =formId+classId;
     	var se4="<input type='text' value='0' class='"+classIds+"' "
       		+"style='height: 30px;border-radius:5px'/>";
        var se5="<select style='width:100px;'>";
        for(var i=0;i<unit_key.length;i++){
        	if(i==0){
        		se5+="<option value='"+unit_key[i]+"' selected>"+unit_val[i]+"</option>" ;
            }else{
            	se5+="<option value='"+unit_key[i]+"' >"+unit_val[i]+"</option>";
            }
        }
        se5+="</select>";
        var se6="<select style='width:100px;'>";
        for(var i=0;i<csKey.length;i++){
        	if(i==0){
        		se6+="<option value='"+csKey[i]+"' selected>"+csVal[i]+"</option>" ;
            }else{
            	se6+="<option value='"+csKey[i]+"' >"+csVal[i]+"</option>";
            }
        }
        se6+="</select>";
        se7="未签字";
		var html="<tr>"
			+"	<td>"
			+"		<input value='' type='checkbox' style='width:20px;height:20px;' name='czg-tab-tbody-CK' id='czg-tab-tbody-CK'/></td>"
			+"	</td>"
			+"	<td>"+(trLength+1)+"</td>"
			+"	<td>"+nowDate+"</td>"
			+"	<td>"+se3+"</td>"
			+"	<td width='80'>"+se4+"</td>"
			+"	<td>"+se5+"</td>"
			+"	<td>"+se6+"</td>"
			+"	<td>"+se7+"</td>"
			+"</tr>";
			$("#czg-tab-tbody").append(html);
			var trRow="#czg-tab-tbody tr:eq("+(trLength)+")";//当前行
			$(trRow).click();//行 获取焦点
	}
	//修改
	function update(){
		zjyChangeType ="update";
	}
	//删除
	function deletes(){
		zjyChangeType ="delete";
		var zjy =$("#isMassCheckZjr").text();
		if(zjy!='true'){
			jAlert("自检员不可为空,请先登录!","提示");
			return false;
		}
		 var ids="";
		 $("#czg-tab-tbody tr").each(function(idx) {
            var trCk = $(this).find("#czg-tab-tbody-CK").get(0);//checkbox对象
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
		            url: "${pageContext.request.contextPath}/wct/massFrist/deleteByIds.do",//要访问的后台地址
		            data: "ids=" + ids,//要发送的数据
		            complete :function(){
		                //$("#load").hide();
		            },//AJAX请求完成时隐藏loading提示
		            success : function(r) {
						if(r.success){
							search();//重新查询下
						}else{ 
							jAlert("删除失败!","提示");
						}
					}
		         });
			}
		 });
	}

	//保存
	function save(){
		changeType ="save";
		var zjy =$("#isMassCheckZjr").text();
		if(schWorkorderId==""||schWorkorderId==undefined){
			jAlert("当前没有生产的工单，不可以操作","提示");
			return false;
		}
		if(zjy!='true'){
			jAlert("自检员不可为空,请先登录!","提示");
			return false;
		}
		/* var checkString = '[';
		checkString += '{"proWorkId":"'+schWorkorderId+ '"}'; 
		checkString += ']'; */
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
		var zjUserName =$("#massCheckZjr").text();//质检人
		var reqString = '[';
		var isNextNumber= 0;
		 $("#czg-tab-tbody tr").each(function(idx) {//for循环
	           var tr = $(this);//tr对象
	           var trCk = tr.find("#czg-tab-tbody-CK").get(0);//checkbox对象,当前第0列
	        	if(trCk!=null&&trCk!=undefined&&trCk.checked){
	        		 var td0 = trCk.value;//当前第0列,主键，判断更新或者新增
	        		 var td2 = tr.find("td:eq(2)").text();//当前第2列	时间
					 var td3 = "";//当前第3列 	外观质量情况 存在问题
					 var td4 = "";//当前第4列 	不合格数
					 var td5 = "";//当前第4列 	不合格数单位
					 var td6 = "";//当前第4列 	处理措施
					 if(tr.attr("edit")){//表示当前行都在 编辑状态
						 inserttime=td2;
						 td2 = tr.find("td:eq(2) input").val();
						 td3 = tr.find("td:eq(3) select").val();
						 td4 = tr.find("td:eq(4) input").val();
						 td5 = tr.find("td:eq(5) select").val();
						 td6 = tr.find("td:eq(6) select").val();
						 inserttime=td2;
					 }else{
						 td3 = tr.find("td:eq(3)").text();
						 td4 = tr.find("td:eq(4)").text();
						 td5 = tr.find("td:eq(5)").text();
						 td6 = tr.find("td:eq(6)").text();
						 //根据中文找 key
					 	for(var j=0;j<qx_pack_key.length;j++ ){
							if(qx_pack_val[j]==td3){
								td3=qx_pack_key[j];
								 break;
							 }
						}
					 	for(var j=0;j<unit_key.length;j++ ){
							if(unit_val[j]==td5){
								td5=unit_key[j];
								break;
							 }
						}
						for(var j=0;j<csKey.length;j++ ){
							if(csVal[j]==td6){
								td6=csKey[j];
								 break;
							 }
						}
					 }
					 if(formattedtime.test(inserttime) || formattedtime2.test(inserttime)){
						 flag=true;
					 }else{
						 flag=false;
					 } 
			     	reqString += '{"qmFirstId":"'+td0//主键
			     	+ '","checkTime":"'+ td2//复检时间	
					+ '","checkCondition":"'+ td3//外观质量,存在问题
					+ '","failureNum":"'+ td4//不合格数
					+ '","failureUom":"'+ td5//不合格数单位
					+ '","checkStep":"'+ td6//处理措施
					+ '","addUserName":"'+ zjUserName//质检员
					+ '","checkType":"2' //类型
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
		//return ;
        //跑到后台处理后 并查询
		$.ajax({
            type: "post",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${pageContext.request.contextPath}/wct/massFrist/editBean.do",//要访问的后台地址
            data: "firstArray=" + reqString+"&checkArray="+checkString,//要发送的数据
            complete :function(){
                //$("#load").hide();
            },//AJAX请求完成时隐藏loading提示
            success : function(r) {
				if(r.success){
					search();//重新查询下
				}else{
					jAlert("保存失败!","提示");
				}
			}
         });
	}
	
</script>

</head>
<title>自检员_2</title>
	<!-- 内容 -->
	<table border="1" borderColor="#9a9a9a"
		style="BORDER-COLLAPSE:collapse;font-size:12px;font-weight:500;margin-bottom:300px;"
		width="100%"  cellspacing="0" cellpadding="0">
		<thead id="czg-tab-thead"
			style="background: #5a5a5a; color: #fff;">
			<tr>
				<td width="40">
					<input id="czg-tab-thead-CKA" style="width:20px;height:20px;" name="czg-tab-thead-CKA" type="checkbox"/></td>
				</td>
				<td>序号</td>
				<td>时间</td><td>质量情况</td><td>不合格数</td><td>单位</td><td>处理措施</td><td>签字</td>
			</tr>
		</thead>
		<tbody id="czg-tab-tbody">
		
		</tbody>
		<input type="hidden"  id="tfRole" value="8af2d49158d7b0260158d87f6d45026e"/>
	</table>
</html>
