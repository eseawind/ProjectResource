$(document).ready(function() {
	//刷新系统菜单
	$("#refrushSysMenuBtn").on("click", function(e) {
		$.ajax({
			   type: "POST",
			   url: BASEURL+"/sys/refreshMenuCache",
			   dataType:"json",
			   success: function(data){
				   if(data.success){
					   showMsg(0,"消息",data.msg+"稍后将重新加载...");
					   //刷新
					   setTimeout(function () {window.location.reload()}, 4000); 
				   }else{
					   alertMsg("提示",data.err_msg);
				   }
			   }
			});
	});
});