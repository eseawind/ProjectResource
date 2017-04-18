package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
/**
 * 
* @ClassName: EqmMaintain 
* @Description: 检修实体类 
* @author luo
* @date 2015年7月22日 上午9:42:34 
*
 */
public class EqmMaintain {
	private String id;//ID	检修ID
	private MdEquipment eqp;//EQU_ID	设备ID
	private String contents;//CONTENTS	检修项目内容
	private String solution;////SOLUTION	解决措施
	private Date date_plan;//DATE_PLAN	计划执行日期
	private SysUser blame_usr;//BLAME_USR	计划责任人
	private Date real_time;//REAL_TIME	实际完成日期
	private String real_usr;//REAL_USR	实际完成人
	private String remark;//REMARK	备注
	private String followup;//FOLLOWUP	后期跟踪
	private String place;//PLACE	部位
	private String note;//NOTE	检修注意事项
	private String review;//REVIEW	复查情况
	private String attr1;//ATTR1	备用一
	private String attr2;//ATTR2	备用二
	private String del;//是否删除0 未删除，1 删除
	
	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MdEquipment getEqp() {
		return eqp;
	}
	public void setEqp(MdEquipment eqp) {
		this.eqp = eqp;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public Date getDate_plan() {
		return date_plan;
	}
	public void setDate_plan(Date date_plan) {
		this.date_plan = date_plan;
	}
	public SysUser getBlame_usr() {
		return blame_usr;
	}
	public void setBlame_usr(SysUser blame_usr) {
		this.blame_usr = blame_usr;
	}
	public Date getReal_time() {
		return real_time;
	}
	public void setReal_time(Date real_time) {
		this.real_time = real_time;
	}
	
	public String getReal_usr() {
		return real_usr;
	}

	public void setReal_usr(String real_usr) {
		this.real_usr = real_usr;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFollowup() {
		return followup;
	}
	public void setFollowup(String followup) {
		this.followup = followup;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
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
	
}