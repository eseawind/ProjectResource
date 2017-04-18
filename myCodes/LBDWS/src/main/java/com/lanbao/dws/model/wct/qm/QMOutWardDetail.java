package com.lanbao.dws.model.wct.qm;

import javax.persistence.Entity;

/**
 * 外观巡检详细
 * @author shisihai
 *
 */
@Entity(name="QM_OUTWARD_DET")
public class QMOutWardDetail {
	private String id;
	private String qo_id;//
	private String item;//缺陷id
	private Integer val;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQo_id() {
		return qo_id;
	}
	public void setQo_id(String qo_id) {
		this.qo_id = qo_id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Integer getVal() {
		return val;
	}
	public void setVal(Integer val) {
		this.val = val;
	}
	
}
