//轮保工角色ID
var lb_role="8af2d43f4d73d86d014d73e1615a0001";
//润滑工角色ID
function getRH_Role(){
	var rh_role="402899db4b399988014b399b33490000";
	return rh_role;
}

function getCZG_Role(){
	//操作工角色ID
	var czg_role="8af2d43f4d73d86d014d73df6da90000";
	return czg_role;
}
 function getLBG_Role(){
	 //轮保工角色id
	 var lbg_role="8af2d43f4d73d86d014d73e1615a0001";
	 return lbg_role;
 }
 function getWXG_Role(){
	 //维修工角色id
	 var wxg_role="402899894db72650014db78d4035004f";
	 return wxg_role;
 }
/**
 * 【功能说明】：角色ID转换
 * 
 * @param  id - 角色ID 
 * @return   0-操作工   1- 维修工   2-维修主管
 * @time 2015年7月18日9:02:33
 * @author wchuang
 * 
 * */
function getRoleType(id){
	var pug="8af2d43f4d73d86d014d73df6da90000"; //操作工ID
	var weix="402899894db72650014db78d4035004f" ; //维修工ID
	var zhug="402899894db72650014db78daf010050" ; //主管ID
	if(id==pug){
		return '0';
	}else if(id==weix){
		return '1';
	}else{
		return '2';
	}
}


//维修主管角色ID
var wxg_role="";
