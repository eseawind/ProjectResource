<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">
.spanimgs{
  float: left;
  width: 120px;
  height: 130px;
}
.aaimgstl{
  text-align: center;
  cursor:pointer;
}
.aimgstl{
  text-align: center;
  cursor:pointer;
  poorfish:expression(this.onclick=function kill(){return false});
}
	/*按钮样式*/	
	.button_botton{
		width: 200px;height: auto;font-size: 12px;font-weight: bold;
		text-align: center;padding-top: 320px;margin: 0 auto;
	}
	.btn-default {color: #FFFFFF;background-color: #5A5A5A;border-color: #cccccc;}
	.input_box{
		display: block;
		z-index: 99;
		position: absolute;
		top: 15px;
		width:777px;
		font-size: 12px;
		border: 1px solid #858484;
		border-radius: 10px;
		height: 440px;
		background:#CCCCCC;
		filter:alpha(Opacity=95);-moz-opacity:0.9;opacity: 0.9;
		padding: 30px;
	}
	.input-title-group {
		 height: 40px;
		 font-size:20px;
		 padding-left: 45%;
	}
	.input-group {
		margin-bottom: 15px;
	}
	#username,#password{width:200px;}
	.ui-keyboard-preview{width:92%;}
	.form-control{height:20px;}
	/*密码验证结束*/	
	.input-group-addon {
		font-size:14px;
	}
	
	#aa{poorfish:expression(this.onclick=function kill(){return false})}
</style>

<script type="text/javascript">
function queryStaffAll(obj){
	 var url="${pageContext.request.contextPath}/wct/eqm/fixrec/queryStaffAll.do";     
	 $.post(url, {type_id:obj},function(data){
			var dataJson = JSON.parse(data);
			var str="";
			if(obj==1){
				str="<h3>机械工</h3><hr/>";
			}else if(obj==2){
				str="<h3>电气工</h3><hr/>";
			}else if(obj==3){
				str="<h3>维修主管</h3><hr/>";
			}
			$("#project").append(str);
			var len=dataJson.length;
			if(len==0){
				$("#project").append("<font style='color:red;'>未找到维修工!</font>");
			}else{
				$.each(dataJson,function(n,val) {
					var sta_="";
					 if(val.status == '0'){
					 sta_="<span style='color:green;'>在线</span>";
					 }else if(val.status == '1'){
						 sta_="<span style='color:red;'>忙碌</span>"; 
					 }else if(val.status == '2'){
						 sta_="<span style='color:red;'>离线</span>"; 
					 }
					 var img_p=val.path;
					str=" <a class='aaimgstl' onclick='addServiceInfo(\""+val.id+"\",\""+obj+"\",\""+val.status+"\")'><span class='spanimgs'><img src='"+img_p+"' style='width:80px;height:100px;'/><br/>"+val.user_name+"<br/><span>状态："+sta_+"</span></span></a>";
					//console.info(str);
					$("#project").append(str);
				});
			}
	 },"JSON");
 };
 function addServiceInfo(id,type_id,status){
	//添加呼叫记录 
	if(status=='0'){
		var url="${pageContext.request.contextPath}/wct/eqm/fixrec/addServiceInfo.do";
		$.post(url, {id:id,type_id:type_id},function(data){
		   //查询
		    var url="${pageContext.request.contextPath}/wct/eqm/fixrec/sysStaffPage.do";
		    $.get(url,null,null);
		    window.location.reload();
		    //取消
			hiddenWxHjJsp();
			//清空机械工图像
			$("#project").text("");
	    },"JSON");
		
		//修改用户状态
		$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/updateStaff.do",{designated_person_id:id,status:status});
	}else if(status=='1'){
		jAlert("该维修工正在维修中请您等待！","提示");
	}else if(status=='2'){
		jAlert("该维修工暂时无法服务！","提示");
	}
 }
</script>
</head>
	<!-- hid_repair_div -->     
	<div id="hid_repair_div" style="display:none;top:77px;left:0px;position:absolute;z-index:90;" onclick="return false;">
		<div class="input_box">
			<form id="repair-box-wct-frm">
			    <div id="project">
                   
                </div>
				<br/>
				
				<div class="button_botton">	
					<!-- <button  onclick="saveWxHjRepair();" style="height:35px;width:80px;" class="btn btn-default">
						添加
					</button> -->	
					<button  onclick="hiddenWxHjJsp();" style="height:35px;width:80px;" class="btn btn-default">
						取消
					</button>		
				</div>
			</form>
		</div>
	</div>
</html>