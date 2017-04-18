package com.shlanbao.tzsc.pms.md.workshop.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.workshop.beans.WorkShopBean;
/**
 * 车间
 * @author Leejean
 * @create 2014年12月1日上午11:05:37
 */
public interface WorkShopServiceI {
	/**
	 * 查询所有车间
	 * @author Leejean
	 * @create 2014年11月26日下午4:23:14
	 * @return
	 */
	public List<WorkShopBean> queryAllWorkShopsForComboBox() throws Exception ;
	/**
	 * 查询所有车间
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllWorkShops(WorkShopBean workShopBean) throws Exception;
	/**
	 * 新增车间
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param WorkShopBean
	 * @throws Exception
	 */
	public void addWorkShop(WorkShopBean WorkShopBean) throws Exception;
	/**
	 * 编辑车间
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param WorkShopBean
	 * @throws Exception
	 */
	public void editWorkShop(WorkShopBean WorkShopBean) throws Exception;
	/**
	 * 删除车间
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteWorkShop(String id) throws Exception;
	/**
	 * 工具ID获取车间
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public WorkShopBean getWorkShopById(String id) throws Exception;
}
