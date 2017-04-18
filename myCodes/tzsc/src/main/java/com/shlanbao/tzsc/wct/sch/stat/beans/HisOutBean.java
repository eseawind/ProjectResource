package com.shlanbao.tzsc.wct.sch.stat.beans;
/**
 * 历史产量
 * @author Leejean
 * @date 2015年3月2日 下午2:18:01
 */
public class HisOutBean {
	private Double[] values;
	private Double[] values2;
	private Double[] values3;
	private String[] lables;
	private String eqpCod;
	private String date1;
	private String date2;
	private String teamId;
	private String shiftId;
	private Integer orderFlag;
	private String unit;
	private int pageIndex;
	private int pageSize;
	private int allPage;
	private int count;
	private int up;
	private int down;
	public int getUp() {
		this.up=this.pageIndex-1<1?1:this.pageIndex-1;
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		this.down=(this.getAllPage()==1?1:this.pageIndex+1);
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getAllPage() {
		if(count!=0){			
			return (count-1)/pageSize+1;
		}else{
			return 0;
		}
	}
	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Double[] getValues() {
		return values;
	}
	public void setValues(Double[] values) {
		this.values = values;
	}
	public Double[] getValues2() {
		return values2;
	}
	public void setValues2(Double[] values2) {
		this.values2 = values2;
	}
	public String[] getLables() {
		return lables;
	}
	public void setLables(String[] lables) {
		this.lables = lables;
	}
	public HisOutBean() {
		// TODO Auto-generated constructor stub
	}
	public HisOutBean(Double[] values, Double[] values2,
			String[] lables, int pageIndex, int pageSize, int count) {
		super();
		this.values = values;
		this.values2 = values2;
		this.lables = lables;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.count = count;
	}
	
	public HisOutBean(Double[] values, Double[] values2, Double[] values3,
			String[] lables, int pageIndex, int pageSize, int count) {
		super();
		this.values = values;
		this.values2 = values2;
		this.values3 = values3;
		this.lables = lables;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.count = count;
	}

	public HisOutBean(Double[] values,
			String[] lables, int pageIndex, int pageSize, int count) {
		super();
		this.values = values;
		this.lables = lables;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.count = count;
	}
	


	public Double[] getValues3() {
		return values3;
	}

	public void setValues3(Double[] values3) {
		this.values3 = values3;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getEqpCod() {
		return eqpCod;
	}

	public void setEqpCod(String eqpCod) {
		this.eqpCod = eqpCod;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public Integer getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	
}
