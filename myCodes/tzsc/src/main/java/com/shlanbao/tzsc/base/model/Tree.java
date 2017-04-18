package com.shlanbao.tzsc.base.model;

import java.util.List;

/**
 * Easyui Tree模型
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:19:33
 */
@SuppressWarnings("serial")
public class Tree implements java.io.Serializable,Comparable<Tree> {
	private String id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<Tree> children;
	private String iconCls;
	private String pid;
	private Long seq;//排序
	@Override
	public int compareTo(Tree o) {
		if(o.getSeq()>this.seq){
			return -1;
		}else if(o.getSeq()<this.seq){
			return 2;
		}
		return 0;
	}
	public Tree() {
		// TODO Auto-generated constructor stub
	}
	
	public Tree(String id, String text, boolean checked, String pid) {
		super();
		this.id = id;
		this.text = text;
		this.checked = checked;
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
}
