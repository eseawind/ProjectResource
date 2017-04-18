package com.shlanbao.tzsc.base.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Easyui combobox组件DataGrid
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:17:52
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class DataGrid implements java.io.Serializable {

	private Long total = 0L;
	private List rows = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	/**
	 * 
	 * @param rows 数据集
	 * @param total 总记录数
	 */
	public DataGrid(List rows,Long total) {
		super();
		this.total = total;
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "DataGrid [total=" + total + ", rows=" + rows + "]";
	}
	
}
