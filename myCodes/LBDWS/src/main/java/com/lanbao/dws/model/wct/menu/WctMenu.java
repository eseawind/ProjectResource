package com.lanbao.dws.model.wct.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
/**
 * WCT菜单实体
 * @author shisihai
 * 	//卷包机菜单  type=0 、  封箱机type=1 、   成型机type=2	发射机  3	通用 type=-1
 *已经实现grade字段排序
 *各个模块菜单id分配：
 *生产调度 9-60
 *物料请求61-120
 *质量管理121-180
 *生产统计181-240
 *设备管理241-300
 *文档管理301-360
 *系统管理361-420
 */
@Entity(name="WCT_MENU")
public class WctMenu implements Serializable,Comparable<WctMenu>{
	private static final long serialVersionUID = 1L;
	private String id;
	private String modul;//模块  首字母大写
	private String menu_title;//菜单文字
	private String menu_url;//url链接  为null表示有下一等级菜单
	private String imgPath;//图标链接
	private String upId;//上级菜单   左侧根菜单root   顶部菜单为top   其余为id
	private Integer grade;//排序
	private Integer type;//类型 type=0 、  封箱机type=1 、   成型机type=2	发射机  3	通用 type=-1
	
	//备用字段
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	private List<WctMenu> childMenus=new ArrayList<>();
	public List<WctMenu> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<WctMenu> childMenus) {
		this.childMenus = childMenus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModul() {
		return modul;
	}
	public void setModul(String modul) {
		this.modul = modul;
	}
	public String getMenu_title() {
		return menu_title;
	}
	public void setMenu_title(String menu_title) {
		this.menu_title = menu_title;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getUpId() {
		return upId;
	}
	public void setUpId(String upId) {
		this.upId = upId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getAttr4() {
		return attr4;
	}
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}
	@Override
	public int compareTo(WctMenu o) {
		return this.getGrade().compareTo(o.getGrade());
	}
	

}
