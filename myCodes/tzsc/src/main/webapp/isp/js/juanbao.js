$(function(){
	});


//显示隐藏
function menuSh(){
	$(".menu_font1").toggle(500);
	//$(".logo_font1").toggle(500);
	if(flag){
		//$("#box_left").css("width","123px");
		$("#box_left").animate({'width':'123px'});
		flag=false;
	}else{
		$("#box_left").animate({'width':'444px'});
		$("#box_right").animate({'margin-left':'444px'});
		flag=true;
	}
}