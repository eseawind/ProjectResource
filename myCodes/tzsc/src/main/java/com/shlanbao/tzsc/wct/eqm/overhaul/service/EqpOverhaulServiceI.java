package com.shlanbao.tzsc.wct.eqm.overhaul.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.mapping.EqmMaintain;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.eqm.overhaul.bean.EqpOverhaulBean;

/**
 * 
 * @ClassName: EqpOverhaulServiceI 
 * @Description: 设备检修 
 * @author luo
 * @date 2015年2月5日 上午11:20:37 
 *
 */
public interface EqpOverhaulServiceI {
	/**
	 * 添加设备检修项目
	 * @param eqmMaintain
	 * @throws Exception
	 */
	public void addEqmMaintain(EqmMaintain eqmMaintain)throws Exception;
	
	/**
	 * 逻辑删除设备检修项目维护
	 * @param id
	 * @throws Exception
	 */
	public void deleteEqmMaintainById(String id)throws Exception;
	
	/**
	 * 分页查询设备检修项目
	 * @param eqmMaintain
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryEqmMaintain(EqpOverhaulBean eqmMaintain,PageParams pageParams) throws Exception;
	
	/**
	 * 通过ID查询设备检修
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EqpOverhaulBean getEqmMaintainById(String id)throws Exception;
	
	/**
	 * 
	* @Title: updateStatus 
	* @Description: 检修完成状态修改 
	* @param @param id
	* @param @param uid
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateStatus(String id,String area3,String area6,String uid) throws Exception;
	/**
	 * 批量添加检修记录
	 * @param list
	 */
	public void addBatch(List<EqmFixRec> list);
	/**张璐2015.10.30
	 * 用于添加故障后，跳转回来，按钮才变成已完成
	 * @param id
	 * @param uid
	 * @throws Exception
	 */
	public void updateStatusTrouble(String id,String uid) throws Exception;

	/**
	 * 张璐-2015.11.1
	 * 用于查询检修数据，方便修改
	 * @param id
	 * @return
	 */
	public EqpOverhaulBean queryJX(String id);
}
