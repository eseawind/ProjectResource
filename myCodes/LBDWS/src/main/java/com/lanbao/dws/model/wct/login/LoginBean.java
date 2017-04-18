package com.lanbao.dws.model.wct.login;
/**
 * 说明： wct主登录实体类
 * @date 2016年7月20日
 * @author wanchanghuang
 * 
 * */
public class LoginBean {
	
	/**卷包烟机公共部分*/
	private String loginName; //用户名称
	private String passWord; //用户密码
	private String windUname; //系统登录名
	private String windPassWord;//系统登录密码
	private String shiftId;//班次ID
	private String shiftName;//班次名称
	private String teamId; //班组ID
	private String teamName;//班组名称
	private String eqmCommonName;//机台信息
	private String workshopId;//车间   1-一号车间  2-二号车间
	private int loginType;//0卷包机组    1 封箱机组     2成型机组    3发射机组
	
	/**卷烟机*/
	private String rollerEquipmentId;//卷烟机ID
	private String rollerEquipmentCode;//卷烟机code
	private String rollerEquipmentType;//卷烟机型号
	private String rollerEquipmentName;//卷烟机名称
	private String rollerEquipmentIp;//卷烟机Ip
	//卷烟机对应的储烟装置
	private String cyEquipmentId;
	private String cyEquipmentCode;//储烟机code
	private String cyEquipmentType;//储烟机型号
	private String cyEquipmentName;//储烟机名称
	private String cyEquipmentIp;//储烟机Ip
	/**包装机*/
	private String packerEquipmentId;//包装机ID
	private String packerEquipmentCode;//包装机code
	private String packerEquipmentType;//包装机型号
	private String packerEquipmentName;//包装机名称
	private String packerEquipmentIp;//包装机Ip
	
	/**成型机 2台公用一个数采站  1,2 一个   3,4一个。4号没有**/
	//第一台
	private String filterEquipmentId0;//成型机ID
	private String filterEquipmentCode0;//成型机code
	private String filterEquipmentType0;//成型机型号
	private String filterEquipmentName0;//成型机名称
	private String filterEquipmentIp0;//成型机Ip
	
	//第二台
	private String filterEquipmentId1;//成型机ID
	private String filterEquipmentCode1;//成型机code
	private String filterEquipmentType1;//成型机型号
	private String filterEquipmentName1;//成型机名称
	private String filterEquipmentIp1;//成型机Ip
	
	/**封箱机  2台公用一个数采站**/
	//第一个封箱机
	private String boxerEquipmentId0;//封箱机ID
	private String boxerEquipmentCode0;//封箱机code
	private String boxerEquipmentType0;//封箱机型号
	private String boxerEquipmentName0;//封箱机名称
	private String boxerEquipmentIp0;//封箱机Ip
	//第二个封箱机
	private String boxerEquipmentId1;//封箱机ID
	private String boxerEquipmentCode1;//封箱机code
	private String boxerEquipmentType1;//封箱机型号
	private String boxerEquipmentName1;//封箱机名称
	private String boxerEquipmentIp1;//封箱机Ip
	
	/**发射机 3台公用一台数采站 **/
	private String launchEquipmentId0;//发射机ID
	private String launchEquipmentCode0;//发射机code
	private String launchEquipmentType0;//发射机型号
	private String launchEquipmentName0;//发射机名称
	private String launchEquipmentIp0;//发射机Ip
	
	private String launchEquipmentId1;//发射机ID
	private String launchEquipmentCode1;//发射机code
	private String launchEquipmentType1;//发射机型号
	private String launchEquipmentName1;//发射机名称
	private String launchEquipmentIp1;//发射机Ip
	
	
	private String launchEquipmentId2;//发射机ID
	private String launchEquipmentCode2;//发射机code
	private String launchEquipmentType2;//发射机型号
	private String launchEquipmentName2;//发射机名称
	private String launchEquipmentIp2;//发射机Ip
	
	
	private String nowTime; //当前系统时间
	
	
	
	public String getCyEquipmentId() {
		return cyEquipmentId;
	}
	public void setCyEquipmentId(String cyEquipmentId) {
		this.cyEquipmentId = cyEquipmentId;
	}
	public String getCyEquipmentCode() {
		return cyEquipmentCode;
	}
	public void setCyEquipmentCode(String cyEquipmentCode) {
		this.cyEquipmentCode = cyEquipmentCode;
	}
	public String getCyEquipmentType() {
		return cyEquipmentType;
	}
	public void setCyEquipmentType(String cyEquipmentType) {
		this.cyEquipmentType = cyEquipmentType;
	}
	public String getCyEquipmentName() {
		return cyEquipmentName;
	}
	public void setCyEquipmentName(String cyEquipmentName) {
		this.cyEquipmentName = cyEquipmentName;
	}
	public String getCyEquipmentIp() {
		return cyEquipmentIp;
	}
	public void setCyEquipmentIp(String cyEquipmentIp) {
		this.cyEquipmentIp = cyEquipmentIp;
	}
	public int getLoginType() {
		return loginType;
	}
	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
	public String getLaunchEquipmentId0() {
		return launchEquipmentId0;
	}
	public void setLaunchEquipmentId0(String launchEquipmentId0) {
		this.launchEquipmentId0 = launchEquipmentId0;
	}
	public String getLaunchEquipmentCode0() {
		return launchEquipmentCode0;
	}
	public void setLaunchEquipmentCode0(String launchEquipmentCode0) {
		this.launchEquipmentCode0 = launchEquipmentCode0;
	}
	public String getLaunchEquipmentType0() {
		return launchEquipmentType0;
	}
	public void setLaunchEquipmentType0(String launchEquipmentType0) {
		this.launchEquipmentType0 = launchEquipmentType0;
	}
	public String getLaunchEquipmentName0() {
		return launchEquipmentName0;
	}
	public void setLaunchEquipmentName0(String launchEquipmentName0) {
		this.launchEquipmentName0 = launchEquipmentName0;
	}
	public String getLaunchEquipmentIp0() {
		return launchEquipmentIp0;
	}
	public void setLaunchEquipmentIp0(String launchEquipmentIp0) {
		this.launchEquipmentIp0 = launchEquipmentIp0;
	}
	public String getLaunchEquipmentId1() {
		return launchEquipmentId1;
	}
	public void setLaunchEquipmentId1(String launchEquipmentId1) {
		this.launchEquipmentId1 = launchEquipmentId1;
	}
	public String getLaunchEquipmentCode1() {
		return launchEquipmentCode1;
	}
	public void setLaunchEquipmentCode1(String launchEquipmentCode1) {
		this.launchEquipmentCode1 = launchEquipmentCode1;
	}
	public String getLaunchEquipmentType1() {
		return launchEquipmentType1;
	}
	public void setLaunchEquipmentType1(String launchEquipmentType1) {
		this.launchEquipmentType1 = launchEquipmentType1;
	}
	public String getLaunchEquipmentName1() {
		return launchEquipmentName1;
	}
	public void setLaunchEquipmentName1(String launchEquipmentName1) {
		this.launchEquipmentName1 = launchEquipmentName1;
	}
	public String getLaunchEquipmentIp1() {
		return launchEquipmentIp1;
	}
	public void setLaunchEquipmentIp1(String launchEquipmentIp1) {
		this.launchEquipmentIp1 = launchEquipmentIp1;
	}
	public String getLaunchEquipmentId2() {
		return launchEquipmentId2;
	}
	public void setLaunchEquipmentId2(String launchEquipmentId2) {
		this.launchEquipmentId2 = launchEquipmentId2;
	}
	public String getLaunchEquipmentCode2() {
		return launchEquipmentCode2;
	}
	public void setLaunchEquipmentCode2(String launchEquipmentCode2) {
		this.launchEquipmentCode2 = launchEquipmentCode2;
	}
	public String getLaunchEquipmentType2() {
		return launchEquipmentType2;
	}
	public void setLaunchEquipmentType2(String launchEquipmentType2) {
		this.launchEquipmentType2 = launchEquipmentType2;
	}
	public String getLaunchEquipmentName2() {
		return launchEquipmentName2;
	}
	public void setLaunchEquipmentName2(String launchEquipmentName2) {
		this.launchEquipmentName2 = launchEquipmentName2;
	}
	public String getLaunchEquipmentIp2() {
		return launchEquipmentIp2;
	}
	public void setLaunchEquipmentIp2(String launchEquipmentIp2) {
		this.launchEquipmentIp2 = launchEquipmentIp2;
	}
	public String getFilterEquipmentId0() {
		return filterEquipmentId0;
	}
	public void setFilterEquipmentId0(String filterEquipmentId0) {
		this.filterEquipmentId0 = filterEquipmentId0;
	}
	public String getFilterEquipmentCode0() {
		return filterEquipmentCode0;
	}
	public void setFilterEquipmentCode0(String filterEquipmentCode0) {
		this.filterEquipmentCode0 = filterEquipmentCode0;
	}
	public String getFilterEquipmentType0() {
		return filterEquipmentType0;
	}
	public void setFilterEquipmentType0(String filterEquipmentType0) {
		this.filterEquipmentType0 = filterEquipmentType0;
	}
	public String getFilterEquipmentName0() {
		return filterEquipmentName0;
	}
	public void setFilterEquipmentName0(String filterEquipmentName0) {
		this.filterEquipmentName0 = filterEquipmentName0;
	}
	public String getFilterEquipmentIp0() {
		return filterEquipmentIp0;
	}
	public void setFilterEquipmentIp0(String filterEquipmentIp0) {
		this.filterEquipmentIp0 = filterEquipmentIp0;
	}
	public String getFilterEquipmentId1() {
		return filterEquipmentId1;
	}
	public void setFilterEquipmentId1(String filterEquipmentId1) {
		this.filterEquipmentId1 = filterEquipmentId1;
	}
	public String getFilterEquipmentCode1() {
		return filterEquipmentCode1;
	}
	public void setFilterEquipmentCode1(String filterEquipmentCode1) {
		this.filterEquipmentCode1 = filterEquipmentCode1;
	}
	public String getFilterEquipmentType1() {
		return filterEquipmentType1;
	}
	public void setFilterEquipmentType1(String filterEquipmentType1) {
		this.filterEquipmentType1 = filterEquipmentType1;
	}
	public String getFilterEquipmentName1() {
		return filterEquipmentName1;
	}
	public void setFilterEquipmentName1(String filterEquipmentName1) {
		this.filterEquipmentName1 = filterEquipmentName1;
	}
	public String getFilterEquipmentIp1() {
		return filterEquipmentIp1;
	}
	public void setFilterEquipmentIp1(String filterEquipmentIp1) {
		this.filterEquipmentIp1 = filterEquipmentIp1;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getWindUname() {
		return windUname;
	}
	public void setWindUname(String windUname) {
		this.windUname = windUname;
	}
	public String getWindPassWord() {
		return windPassWord;
	}
	public void setWindPassWord(String windPassWord) {
		this.windPassWord = windPassWord;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getEqmCommonName() {
		return eqmCommonName;
	}
	public void setEqmCommonName(String eqmCommonName) {
		this.eqmCommonName = eqmCommonName;
	}
	public String getWorkshopId() {
		return workshopId;
	}
	public void setWorkshopId(String workshopId) {
		this.workshopId = workshopId;
	}
	
	public String getRollerEquipmentId() {
		return rollerEquipmentId;
	}
	public void setRollerEquipmentId(String rollerEquipmentId) {
		this.rollerEquipmentId = rollerEquipmentId;
	}
	public String getRollerEquipmentCode() {
		return rollerEquipmentCode;
	}
	public void setRollerEquipmentCode(String rollerEquipmentCode) {
		this.rollerEquipmentCode = rollerEquipmentCode;
	}
	public String getRollerEquipmentType() {
		return rollerEquipmentType;
	}
	public void setRollerEquipmentType(String rollerEquipmentType) {
		this.rollerEquipmentType = rollerEquipmentType;
	}
	public String getRollerEquipmentName() {
		return rollerEquipmentName;
	}
	public void setRollerEquipmentName(String rollerEquipmentName) {
		this.rollerEquipmentName = rollerEquipmentName;
	}
	public String getRollerEquipmentIp() {
		return rollerEquipmentIp;
	}
	public void setRollerEquipmentIp(String rollerEquipmentIp) {
		this.rollerEquipmentIp = rollerEquipmentIp;
	}
	public String getPackerEquipmentId() {
		return packerEquipmentId;
	}
	public void setPackerEquipmentId(String packerEquipmentId) {
		this.packerEquipmentId = packerEquipmentId;
	}
	public String getPackerEquipmentCode() {
		return packerEquipmentCode;
	}
	public void setPackerEquipmentCode(String packerEquipmentCode) {
		this.packerEquipmentCode = packerEquipmentCode;
	}
	public String getPackerEquipmentType() {
		return packerEquipmentType;
	}
	public void setPackerEquipmentType(String packerEquipmentType) {
		this.packerEquipmentType = packerEquipmentType;
	}
	public String getPackerEquipmentName() {
		return packerEquipmentName;
	}
	public void setPackerEquipmentName(String packerEquipmentName) {
		this.packerEquipmentName = packerEquipmentName;
	}
	public String getPackerEquipmentIp() {
		return packerEquipmentIp;
	}
	public void setPackerEquipmentIp(String packerEquipmentIp) {
		this.packerEquipmentIp = packerEquipmentIp;
	}
	public String getBoxerEquipmentId0() {
		return boxerEquipmentId0;
	}
	public void setBoxerEquipmentId0(String boxerEquipmentId0) {
		this.boxerEquipmentId0 = boxerEquipmentId0;
	}
	public String getBoxerEquipmentCode0() {
		return boxerEquipmentCode0;
	}
	public void setBoxerEquipmentCode0(String boxerEquipmentCode0) {
		this.boxerEquipmentCode0 = boxerEquipmentCode0;
	}
	public String getBoxerEquipmentType0() {
		return boxerEquipmentType0;
	}
	public void setBoxerEquipmentType0(String boxerEquipmentType0) {
		this.boxerEquipmentType0 = boxerEquipmentType0;
	}
	public String getBoxerEquipmentName0() {
		return boxerEquipmentName0;
	}
	public void setBoxerEquipmentName0(String boxerEquipmentName0) {
		this.boxerEquipmentName0 = boxerEquipmentName0;
	}
	public String getBoxerEquipmentIp0() {
		return boxerEquipmentIp0;
	}
	public void setBoxerEquipmentIp0(String boxerEquipmentIp0) {
		this.boxerEquipmentIp0 = boxerEquipmentIp0;
	}
	public String getBoxerEquipmentId1() {
		return boxerEquipmentId1;
	}
	public void setBoxerEquipmentId1(String boxerEquipmentId1) {
		this.boxerEquipmentId1 = boxerEquipmentId1;
	}
	public String getBoxerEquipmentCode1() {
		return boxerEquipmentCode1;
	}
	public void setBoxerEquipmentCode1(String boxerEquipmentCode1) {
		this.boxerEquipmentCode1 = boxerEquipmentCode1;
	}
	public String getBoxerEquipmentType1() {
		return boxerEquipmentType1;
	}
	public void setBoxerEquipmentType1(String boxerEquipmentType1) {
		this.boxerEquipmentType1 = boxerEquipmentType1;
	}
	public String getBoxerEquipmentName1() {
		return boxerEquipmentName1;
	}
	public void setBoxerEquipmentName1(String boxerEquipmentName1) {
		this.boxerEquipmentName1 = boxerEquipmentName1;
	}
	public String getBoxerEquipmentIp1() {
		return boxerEquipmentIp1;
	}
	public void setBoxerEquipmentIp1(String boxerEquipmentIp1) {
		this.boxerEquipmentIp1 = boxerEquipmentIp1;
	}
}
