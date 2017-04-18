package com.lanbao.dws.model.wct.qm;

import javax.persistence.Entity;

/**
 * 外观缺陷定义
 * 
 * @author shisihai
 *
 */
@Entity(name="QM_OUTWARD_DEFECT_ITEM")
public class QMOutWandItem {
	private String id;
	private String code;// 编码
	private String name;// 缺陷名称
	private String type;// 缺陷大类 X - 箱装 T - 条盒 B - 小盒 C - 烟支 F - 滤棒
	private String pos;//位置  
	private String des;//描述
	private String lvl;//等级
	private String del;//描述
	
	private boolean isCheck;//是否选中
	
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	
}
