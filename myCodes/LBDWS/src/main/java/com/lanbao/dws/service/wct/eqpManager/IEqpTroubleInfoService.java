package com.lanbao.dws.service.wct.eqpManager;

import java.util.List;

import com.lanbao.dws.model.wct.eqpManager.EqmTroubleInfoBean;

public interface IEqpTroubleInfoService {
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午6:01:49 
	* 功能说明 ：查询故障树list
	 */
	List<EqmTroubleInfoBean> queryTroubleInfo(EqmTroubleInfoBean troubleBean);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月21日 上午9:10:35 
	* 功能说明 ：获取故障树html
	 */
	String getTroubleTreeHtml(EqmTroubleInfoBean troubleBean);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月21日 上午9:14:31 
	* 功能说明 ：添加故障描述（第五级节点）
	 */
	String addNewTrouble(EqmTroubleInfoBean troubleBean);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月21日 上午10:30:19 
	* 功能说明 ：点击重置时，删除已经添加的故障（第五级）
	 */
	void deleteTroubles(String ids);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月22日 上午9:23:53 
	* 功能说明 ：保存故障记录
	* 1.根据来源，查询班次、机台等相关信息
	* 2.保存故障记录
	 */
	int addEqmTrouble(String troubles,String sourceId);
	
}
