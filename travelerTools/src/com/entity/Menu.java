package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private int id;
	private String context;
	private String url_context;
	private String module;
	private int del;
	private int pid;
	private int gread;
	private int haschilds;
	List<Menu> childMenus=new ArrayList<Menu>();
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Menu(int id, String context, String url_context, String module, int del, int pid, int gread,int haschilds) {
		super();
		this.id = id;
		this.context = context;
		this.url_context = url_context;
		this.module = module;
		this.del = del;
		this.pid = pid;
		this.gread = gread;
		this.haschilds=haschilds;
	}
	
	public int getHaschilds() {
		return haschilds;
	}
	public void setHaschilds(int haschilds) {
		this.haschilds = haschilds;
	}
	public List<Menu> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getUrl_context() {
		return url_context;
	}
	public void setUrl_context(String url_context) {
		this.url_context = url_context;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getGread() {
		return gread;
	}
	public void setGread(int gread) {
		this.gread = gread;
	}
	@Override
	public String toString() {
		return "ID："+id+"\t context： "+context+"\t PID:  "+pid+"\t 子菜单："+childMenus;
	}
	
	
}
