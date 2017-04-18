package com.shlanbao.tzsc.pms.isp.beans;
/**
 * 成型机机组
 * @author Leejean
 * @create 2015年1月15日上午8:44:11
 */
public class FilterGroup {
	private String id;
	//机组类型
	private String groupType;
	//机组名称
	private String name;
	//牌号
	private String matName;
	//班组
	private String teamName;
	
	private String code;
	
	private String type;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FilterGroup(String id, String groupType, String name, String code, String type) {
		super();
		this.id = id;
		this.groupType = groupType;
		this.name = name;
		this.code = code;
		this.type = type;
	}
	
	public FilterGroup() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BoxerGroup [id=" + id + ", groupType=" + groupType + ", name="
				+ name + ", matName=" + matName + ", teamName=" + teamName
				+ ", code=" + code + ", type=" + type + "]";
	}
	
}
