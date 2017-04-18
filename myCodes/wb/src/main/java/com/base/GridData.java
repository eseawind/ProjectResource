package com.base;

import java.util.ArrayList;
import java.util.List;

public class GridData {
	private Long total=0L;
	private List rows=new ArrayList<>();
	
	
	public GridData(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public GridData() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	
}
