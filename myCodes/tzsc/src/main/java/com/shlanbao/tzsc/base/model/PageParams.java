package com.shlanbao.tzsc.base.model;
/**
 * 分页参数实体类
 * <li>@author Leejean
 * <li>@create 2014-7-3下午03:50:55
 */
public class PageParams {
	private int page=1;//页面端当前页,默认第一页
	private int rows=100;//改参数用户存储客户端每个页面的记录条数
	private String sort=null;//排序的列
	private String order="asc";//排序的顺序
	/*注意 重要变更 基于使用Easyui datagrid 通常是多表查询，datagrid传递的排序信息通常是扁平化的，即单表查询
	  datagrid remoteSort 为 远程排序，设置false时，即为本地排序
	  
	  */
	
	
	/**
	 * 页面端当前页,默认第一页
	 */
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * 改参数用户存储客户端每个页面的记录条数
	 */
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 *排序的列名
	 */
	public String getSort() {
		return null;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * 排序的顺序
	 */
	public String getOrder() {
		return null;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public PageParams() {
		
	}
	/**
	 * 
	 * @param page 当前页
	 * @param rows 页大小
	 * @param sort 排序列
	 * @param order 升降序
	 */
	public PageParams(int page, int rows, String sort, String order) {
		super();
		this.page = page;
		this.rows = rows;
		this.sort = sort;
		this.order = order;
	}
	@Override
	public String toString() {
		return "PageParams [page=" + page + ", rows=" + rows + ", sort=" + sort
				+ ", order=" + order + "]";
	}
	
}
