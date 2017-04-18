package com.shlanbao.tzsc.pms.md.matgrp.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.matgrp.beans.MatGrpBean;

/**
 *  物料组
 * @author Leejean
 * @create 2014年11月26日下午2:40:56
 */
public interface MatGrpServiceI {
	/**
	 * 查询所有物料组
	 * @author Leejean
	 * @create 2014年11月26日下午4:23:14
	 * @return
	 */
	public List<MatGrpBean> queryAllMatGrpsForComboBox() throws Exception ;
	/**
	 * 查询所有物料组
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllMatGrps(MatGrpBean matGrpBean) throws Exception;
	/**
	 * 新增物料组
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param MatGrpBean
	 * @throws Exception
	 */
	public void addMatGrp(MatGrpBean matGrpBean) throws Exception;
	/**
	 * 编辑物料组
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param MatGrpBean
	 * @throws Exception
	 */
	public void editMatGrp(MatGrpBean matGrpBean) throws Exception;
	/**
	 * 删除物料组
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteMatGrp(String id) throws Exception;
	/**
	 * 根据ID获取物料组
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public MatGrpBean getMatGrpById(String id) throws Exception;
}
