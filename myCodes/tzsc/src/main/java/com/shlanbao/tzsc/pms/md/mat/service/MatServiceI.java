package com.shlanbao.tzsc.pms.md.mat.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.mat.beans.MatBean;

/**
 * 物料
 * @author Leejean
 * @create 2014年11月26日下午2:41:09
 */
public interface MatServiceI {
	/**
	 * 获得所有产品(即物料组为产品)
	 * @author Leejean
	 * @create 2014年11月26日下午3:10:31
	 * @return
	 */
	public List<MatBean> queryAllProdMat() throws Exception;
	/**
	 * 查询所有物料
	 * @author Leejean
	 * @create 2014年11月26日下午4:23:14
	 * @return
	 */
	public List<MatBean> queryAllMatsForComboBox() throws Exception ;
	/**
	 * 查询所有物料
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllMats(MatBean matBean,PageParams pageParams) throws Exception;
	/**
	 * 新增物料
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param MatBean
	 * @throws Exception
	 */
	public void addMat(MatBean matBean) throws Exception;
	/**
	 * 编辑物料
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param MatBean
	 * @throws Exception
	 */
	public void editMat(MatBean matBean) throws Exception;
	/**
	 * 删除物料
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteMat(String id) throws Exception;
	/**
	 * 工具ID获取物料
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public MatBean getMatById(String id) throws Exception;
}
