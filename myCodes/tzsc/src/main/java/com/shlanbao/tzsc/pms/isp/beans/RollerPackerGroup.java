package com.shlanbao.tzsc.pms.isp.beans;
/**
 * 卷包机组
 * @author Leejean
 * @create 2015年1月15日上午8:44:11
 */
public class RollerPackerGroup {
	private String id;
	//机组类型
	private String groupType;
	//机组名称
	private String groupName;
	//牌号
	private String matName;
	//班组
	private String teamName;
	//计划产量
	private Double qty;
	
	//卷烟机
	private String rCode;
	private String rName;
	private String rType;
	//包装机
	private String pCode;
	private String pName;
	private String pType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getrCode() {
		return rCode;
	}
	public void setrCode(String rCode) {
		this.rCode = rCode;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getrType() {
		return rType;
	}
	public void setrType(String rType) {
		this.rType = rType;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public RollerPackerGroup() {
		// TODO Auto-generated constructor stub
	}
	public RollerPackerGroup(String id, String groupType, String groupName,
			String rCode, String rName, String rType, String pCode,
			String pName, String pType) {
		super();
		this.id = id;
		this.groupType = groupType;
		this.groupName = groupName;
		this.rCode = rCode;
		this.rName = rName;
		this.rType = rType;
		this.pCode = pCode;
		this.pName = pName;
		this.pType = pType;
	}
	
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "RollerPackerGroup [id=" + id + ", groupType=" + groupType
				+ ", groupName=" + groupName + ", rCode=" + rCode + ", rName="
				+ rName + ", rType=" + rType + ", pCode=" + pCode + ", pName="
				+ pName + ", pType=" + pType + "]";
	}
	
	
	
}
