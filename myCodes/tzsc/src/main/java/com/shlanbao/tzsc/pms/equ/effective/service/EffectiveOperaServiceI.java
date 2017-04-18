package com.shlanbao.tzsc.pms.equ.effective.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shlanbao.tzsc.base.mapping.EqmCullRecordBean;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveGraphBean;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveOperaBean;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveRunTime;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveUtilizeTime;

/**
 * 
* @ClassName: EffectiveOperaServiceI 
* @Description: 设备有效作业率 
* @author luo
* @date 2015年3月9日 下午2:45:56 
*
 */
public interface EffectiveOperaServiceI {
	/**
	* @Title: queryList 
	* @Description: 根据Bean查询设备有效作业率  
	* @param  bean
	* @param  pageParams
	* @return DataGrid  返回类型 
	* @throws
	 */
	public DataGrid queryList(EffectiveOperaBean bean,PageParams pageParams);

	/**
	 * 
	* @Title: queryGraph 
	* @Description: 根据Bean查询设备有效作业率    
	* @param  seachBean
	* @return EffectiveGraphBean  返回类型 
	* @throws
	 */
	public EffectiveGraphBean queryGraph(EffectiveOperaBean seachBean);
	
	/**
	 * 
	* @Title: queryRunTimeEffective 
	* @Description: 设备运行效率  
	* @param  bean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid queryRunTimeEffective(EffectiveRunTime bean,PageParams pageParams);
	/**
	 * 
	* @Title: queryRunTimeEffectiveChart 
	* @Description: 查询运行效率图表数据源  
	* @param  bean
	* @return EffectiveGraphBean    返回类型 
	* @throws
	 */
	public EffectiveGraphBean queryRunTimeEffectiveChart(EffectiveRunTime bean);
	
	/**
	 * 
	* @Title: quertyUtilizeEffective 
	* @Description: 设备有效利用率  
	* @param  bean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid quertyUtilizeEffective(EffectiveUtilizeTime bean,PageParams pageParams);
	/**
	 * 
	* @Title: queryRunTimeEffectiveChart 
	* @Description: 设备运行有效作用率Chart  
	* @param  bean
	* @return EffectiveUtilizeTime    返回类型 
	* @throws
	 */
	public EffectiveGraphBean quertyUtilizeEffectiveChart(EffectiveUtilizeTime bean);

	
	/**
     *  功能说明：设备停机剔除时间管理-添加
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月21日16:18:46
     *  
     * */
	public boolean addCullRecord(EqmCullRecordBean bean);

	/**
	 *  【功能说明】：设备停机剔除时间管理-查询
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月22日10:36:42
     *  
	 * */ 
	public DataGrid queryCullRecord(EqmCullRecordBean bean,
			PageParams pageParams);

	/**
	 * 【功能说明】：设备停机剔除时间管理-删除
	 *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月22日14:26:21
	 * 
	 * */
	public boolean deleteCullRecordById(EqmCullRecordBean bean);
	/**
	 * 设备有效作业率导出
	 * @param bean
	 * @return
	 */
	public HSSFWorkbook ExportExcelJBEffic(EffectiveOperaBean bean);
	/**
	 * 导出设备运行效率统计导出
	 * @param bean
	 * @return
	 */
	public HSSFWorkbook deriveEqpRunEfficExcel(EffectiveRunTime bean);
	
}
