package com.shlanbao.tzsc.pms.md.eqptype.service;

import java.util.List;







import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeBean;



/**
 * 设备类型与型号业务类
 * @author liuligong 
 * @Time 2014/11/17 9:50
 */
public interface MdEqpTypeServiceI {
	/**
	 * 设备型号新增或者编辑
	 * @param mdEqpTypeBean 设备型号Bean对象
	 * @throws Exception 
	 */
	public void addMdType(MdEqpTypeBean mdEqpTypeBean) throws Exception;
	
	/**
	 * 根据id获取设备类型
	 * @param id
	 * @throws Exception
	 */
	public MdEqpTypeBean getTypeById(String id) throws Exception;
	
	/**
	 * 查询设备类型
	 * @param mdTypeBean 设备类型bean
	 * @param pageParams
	 * @return 设备类型Grid
	 * @throws Exception
	 */
	public DataGrid queryMdType(MdEqpTypeBean mdTypeBean,PageParams pageParams) throws Exception;
	
	/**
	 * 删除设备型号
	 * @param id
	 * @throws Exception
	 */
	public void deleteMdType(String id)throws Exception;
	
	/**
	 * 查询设备型号列表
	 * @return
	 * @throws Exception
	 */
	public List<MdEqpTypeBean> queryMdType(String categoryId)throws Exception;
	
	/**
	 * 
	* @Title: queryMdEqpTypeByCategory 
	* @Description: 获取卷烟机、包装机和成型机的机型
	* @throws Exception    设定文件 
	* @return List<MdEqpTypeBean>    返回类型 
	* @throws
	 */
	public List<MdEqpTypeBean> queryMdEqpTypeByCategory(String[] categoryIds)throws Exception;


}
