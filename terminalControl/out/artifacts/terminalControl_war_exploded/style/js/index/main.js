function changeHtml(dom){
	//清除所有已经处于选中状态的菜单
	$(".childMenu").each(function(index,val){
		$(val).removeClass("avisited");
	})
	$(dom).toggleClass("avisited");
	var url=$(dom).attr("urlVal");
	if(url==""){
		return;
	}
	$.ajax({
		   type: "POST",
		   url: BASEURL+url,
		   dataType:"html",
		   success: function(data){
		    	$("#mainDiv").html(data);
			}
		});
}
