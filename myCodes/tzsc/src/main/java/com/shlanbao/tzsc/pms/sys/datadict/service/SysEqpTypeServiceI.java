package com.shlanbao.tzsc.pms.sys.datadict.service;

import java.util.List;
import java.util.Map;

import com.shlanbao.tzsc.base.mapping.SysEqpType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmBubricantfBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;



/**
 * 数据字典
 */
public interface SysEqpTypeServiceI {
	
	/**
	 * 批量导入二级数据字典
	 * @param list
	 * @param etb
	 */
	public void inputExeclAndReadWrite(List<SysEqpTypeBean> list,SysEqpTypeBean etb) throws Exception;
	
	
	/**
	 * 数据字典新增或者编辑
	 * @param mdEqpTypeBean 数据字典Bean对象
	 * @throws Exception 
	 */
	public void addMdType(SysEqpTypeBean mdEqpTypeBean) throws Exception;
	
	
	/**
	 * 更新 图片设置的点信息
	 * @param id			主键ID
	 * @param imagePointMsg	图片设置值
	 * @param filePath		图片路径
	 * @throws Exception
	 */
	public void addSetUpImages(String id,String imagePointMsg,String filePath) throws Exception; 
	
	
	/**
	 * 根据id获取数据字典类型
	 * @param id
	 * @throws Exception
	 */
	public SysEqpTypeBean getTypeById(String id) throws Exception;
	
	/**
	 * 查询设备类型
	 * @param mdTypeBean 设备类型bean
	 * @param pageParams
	 * @return 设备类型Grid
	 * @throws Exception
	 */
	public DataGrid queryMdType(SysEqpTypeBean mdTypeBean,PageParams pageParams) throws Exception;
	
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
	public List<SysEqpTypeBean> queryMdType()throws Exception;
	
	/**
	 * 
	* @Title: queryMdEqpTypeByCategory 
	* @Description: 获取卷烟机、包装机和成型机的机型
	* @throws Exception    设定文件 
	* @return List<MdEqpTypeBean>    返回类型 
	* @throws
	 */
	public List<SysEqpTypeBean> queryMdEqpTypeByCategory(String[] categoryIds)throws Exception;

	/**
	* @Title: queryBean 
	* @Description: 根据Bean查询List  
	* @param  mdTypeBean
	* @return List<SysEqpType>    返回类型 
	* @throws
	 */
	public List<SysEqpType> queryBean(SysEqpTypeBean mdTypeBean);


	public EqmBubricantfBean queryEqmLubricantPlanByPar(
			EqmBubricantfBean bean,String date,String unit_id, String shift_id, String eqp_id);

    /**
     * [功能说明]：根据sys_eqp_type表cid查询集合
     * @time 2015年9月24日9:19:51
     * @author wanchanghuang	
     * 
     * */
	public List<SysEqpTypeBean> querySysEqmTypeByCid(SysEqpTypeBean sysEqpType);

}
