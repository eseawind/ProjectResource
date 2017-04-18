package com.shlanbao.tzsc.pms.md.mattype.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.mattype.beans.MatTypeBean;


/**
 * 物料类型
 * @author Leejean
 * @create 2014年11月26日下午2:40:42
 */
public interface MatTypeServiceI {
	/**
	 * 查询所有班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:23:14
	 * @return
	 */
	public List<MatTypeBean> queryAllMatTypesForComboBox() throws Exception ;
	/**
	 * 查询所有班次
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllMatTypes(MatTypeBean matTypeBean) throws Exception;
	/**
	 * 新增班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param MatTypeBean
	 * @throws Exception
	 */
	public void addMatType(MatTypeBean matTypeBean) throws Exception;
	/**
	 * 编辑班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param MatTypeBean
	 * @throws Exception
	 */
	public void editMatType(MatTypeBean matTypeBean) throws Exception;
	/**
	 * 删除班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteMatType(String id) throws Exception;
	/**
	 * 工具ID获取班次
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public MatTypeBean getMatTypeById(String id) throws Exception;
	/**
	 * 根据物料组查询物料类型
	 * @author Leejean
	 * @create 2014年11月27日上午8:43:20
	 * @param gid
	 * @return
	 * @throws Exception 
	 */
	public DataGrid getAllTypesByGrp(String gid) throws Exception;
}
