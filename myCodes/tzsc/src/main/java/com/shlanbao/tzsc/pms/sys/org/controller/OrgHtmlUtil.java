package com.shlanbao.tzsc.pms.sys.org.controller;

import java.util.ArrayList;
import java.util.List;

import com.shlanbao.tzsc.pms.sys.org.beans.OrgBean;
/**
 * 组织机构HTML标签拼接
 * @author Leejean
 * @create 2014年8月27日下午12:38:06
 */
public class OrgHtmlUtil {
	public static String getHtml(List<OrgBean> orgs){
		OrgBean root=OrgHtmlUtil.getRoot(orgs);
		if(root==null){
			return "";
		}
		String html="<li id='"+root.getId()+"' pid='"+root.getPid()+"'>";
		html+=root.getName();
		html+=getSonsHtml(orgs,root.getId());
		
		return (html+"</li>").replace("<ul></ul>", "");
	}
	private static String getSonsHtml(List<OrgBean> orgs, String id) {
		String html="<ul>";
		for (OrgBean orgBean : OrgHtmlUtil.getSons(orgs, id)) {
			html+="<li id='"+orgBean.getId()+"' pid='"+orgBean.getPid()+"'>";
			html+=orgBean.getName();
			html+=OrgHtmlUtil.getSonsHtml(orgs, orgBean.getId());
			html+="</li>";
		}
		return html+"</ul>";
	}
	private static OrgBean getRoot(List<OrgBean> orgs){
		for (OrgBean orgBean : orgs) {
			if(orgBean.getPid()==null){
				return orgBean;
			}
		}
		return null;
	}
	private static List<OrgBean> getSons(List<OrgBean> orgs,String id){
		List<OrgBean> orgBeans=new ArrayList<OrgBean>();
		for (OrgBean orgBean : orgs) {
			if(orgBean.getPid()!=null&&orgBean.getPid().equals(id)){
				orgBeans.add(orgBean);
			}
		}
		return orgBeans;
		
	}
}
