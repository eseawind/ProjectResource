package com.lanbao.dws.common.tools;

import java.util.List;

import com.lanbao.dws.model.page.WctPage;

/**
 * 分页数据json对象
 * @author shisihai
 *
 */
public class AjaxModelData {
	private List datas;
	private WctPage wctPage;
	private Object chooseParam;
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}
	public WctPage getWctPage() {
		return wctPage;
	}
	public void setWctPage(WctPage wctPage) {
		this.wctPage = wctPage;
	}
	public Object getChooseParam() {
		return chooseParam;
	}
	public void setChooseParam(Object chooseParam) {
		this.chooseParam = chooseParam;
	}
	
}
