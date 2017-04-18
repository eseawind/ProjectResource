package com.shlanbao.tzsc.pms.equ.overhaul.beans;

/**
 * 
* @ClassName: EqmMaintainBean 
* @Description: 设备检修 
* @author luo
* @date 2015年7月22日 上午10:20:06 
*
 */
public class EqmMaintainBean {
	private String id;//ID	检修ID
	private String eqp_id;//EQU_ID	设备ID
	private String eqp_name;//设备名称
	private String eqp_type;//机型
	private String contents;//CONTENTS	检修项目内容
	private String solution;////SOLUTION	解决措施
	private String date_plans;//DATE_PLAN	计划执行日期
	private String blame_usr_id;//BLAME_USR	计划责任人
	private String blame_usr_name;
	private String real_times;//REAL_TIME	实际完成日期
	private String real_usr_id;//REAL_USR	实际完成人
	private String real_usr_name;
	private String remark;//REMARK	备注
	private String followup;//FOLLOWUP	后期跟踪
	private String place;//PLACE	部位
	private String note;//NOTE	检修注意事项
	private String review;//REVIEW	复查情况
	private String attr1;//ATTR1	备用一
	private String attr2;//ATTR2	备用二
	private String del;//是否删除0 未删除，1 删除
	private String startTime;//条件查询开始日期
	private String endTime;//条件查询结束日期
	
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEqp_type() {
		return eqp_type;
	}

	public void setEqp_type(String eqp_type) {
		this.eqp_type = eqp_type;
	}

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

	public String getEqp_id() {
		return eqp_id;
	}

	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}

	public String getEqp_name() {
		return eqp_name;
	}

	public void setEqp_name(String eqp_name) {
		this.eqp_name = eqp_name;
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

	public String getDate_plans() {
		return date_plans;
	}

	public void setDate_plans(String date_plans) {
		this.date_plans = date_plans;
	}

	public String getBlame_usr_id() {
		return blame_usr_id;
	}

	public void setBlame_usr_id(String blame_usr_id) {
		this.blame_usr_id = blame_usr_id;
	}

	public String getBlame_usr_name() {
		return blame_usr_name;
	}

	public void setBlame_usr_name(String blame_usr_name) {
		this.blame_usr_name = blame_usr_name;
	}

	public String getReal_times() {
		return real_times;
	}

	public void setReal_times(String real_times) {
		this.real_times = real_times;
	}

	public String getReal_usr_id() {
		return real_usr_id;
	}

	public void setReal_usr_id(String real_usr_id) {
		this.real_usr_id = real_usr_id;
	}

	public String getReal_usr_name() {
		return real_usr_name;
	}

	public void setReal_usr_name(String real_usr_name) {
		this.real_usr_name = real_usr_name;
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

	@Override
	public String toString() {
		return "EqmMaintainBean [attr1=" + attr1 + ", attr2=" + attr2
				+ ", blame_usr_id=" + blame_usr_id + ", blame_usr_name="
				+ blame_usr_name + ", contents=" + contents + ", date_plan="
				+ date_plans + ", eqp_id=" + eqp_id + ", eqp_name=" + eqp_name
				+ ", followup=" + followup + ", id=" + id + ", note=" + note
				+ ", place=" + place + ", real_time=" + real_times
				+ ", real_usr_id=" + real_usr_id + ", real_usr_name="
				+ real_usr_name + ", remark=" + remark + ", review=" + review
				+ ", solution=" + solution + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contents == null) ? 0 : contents.hashCode());
		result = prime * result
				+ ((date_plans == null) ? 0 : date_plans.hashCode());
		result = prime * result + ((del == null) ? 0 : del.hashCode());
		result = prime * result + ((eqp_id == null) ? 0 : eqp_id.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EqmMaintainBean other = (EqmMaintainBean) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (date_plans == null) {
			if (other.date_plans != null)
				return false;
		} else if (!date_plans.equals(other.date_plans))
			return false;
		if (del == null) {
			if (other.del != null)
				return false;
		} else if (!del.equals(other.del))
			return false;
		if (eqp_id == null) {
			if (other.eqp_id != null)
				return false;
		} else if (!eqp_id.equals(other.eqp_id))
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		return true;
	}
	
}
