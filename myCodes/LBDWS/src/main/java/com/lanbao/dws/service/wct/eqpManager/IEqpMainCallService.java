package com.lanbao.dws.service.wct.eqpManager;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.eqpManager.CosSparePartsBean;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;
import com.lanbao.dws.model.wct.eqpManager.EqpMainCallInfo;
import com.lanbao.dws.model.wct.eqpManager.RepairCallLogin;
import com.lanbao.dws.model.wct.eqpManager.RepairUserBean;

public interface IEqpMainCallService {
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月19日 上午10:53:14 
	* 功能说明 ：查询维修呼叫记录
	 * @param session 
	 */
	 PaginationResult<List<EqpMainCallInfo>>  queryEqpMainCallInfo(Pagination pagination, EqpMainCallInfo param, HttpSession session);
	
	 /**
	 * @author 作者 : shisihai
	 * @version 创建时间：2016年7月19日 下午2:51:11 
	 * 功能说明 ：查询维修工
	  */
	 PaginationResult<List<RepairUserBean>> queryRepairUsers(RepairUserBean param,Pagination pagination);
	 /**
	 * @author 作者 : shisihai
	 * @version 创建时间：2016年7月19日 下午10:03:58 
	 * 功能说明 ：维修呼叫维修工，修改其状态为忙碌  1
	  */
	 void updateRepairUserStatus(RepairUserBean param,String repairCallLogin,HttpSession session);
	 /**
	 * @author 作者 : shisihai
	 * @version 创建时间：2016年7月19日 下午10:38:47 
	 * 功能说明 ：维修呼叫内登陆
	  */
	 String repairCallLogin(RepairCallLogin token);

	 /**
	 * @author 作者 : shisihai
	 * @version 创建时间：2016年7月20日 上午8:55:20 
	 * 功能说明 ：新增维修呼叫记录
	 * @param repairCallLogin 
	  */
	void addServiceInfo(RepairUserBean param, String repairCallLogin,HttpSession session);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午1:46:09 
	* 功能说明 ：受理维修请求
	 * @param session 
	 */
	void acceptRepair(EqpMainCallInfo param);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午2:03:52 
	* 功能说明 ：完成维修本次维修请求
	 */
	void finishRepair(EqpMainCallInfo param);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午3:16:12 
	* 功能说明 ：查询备品备件
	 */
	PaginationResult<List<CosSparePartsBean>> getSpareParts(Pagination pagination, CosSparePartsBean bean);
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午4:20:00 
	* 功能说明 ：更新备品备件信息
	 * @param sourceId 
	 */
	int updateSparePartCount(String str, String sourceId);

	List<RepairUserBean> queryRepairUsersByTeamId(RepairUserBean param);
	/**
	 * 添加维修呼叫记录
	* <p>Description: </p>
	* @author shisihai
	* @date 2016下午1:25:52
	* eqpNUm 记录是哪一台机器（只针对单屏显示多个机台信息。）
	 */
	void updateOpenRepairUserStatus(RepairUserBean param,
			EqmByLoginBean elbBean, HttpSession session,int eqpNum);
	
}
