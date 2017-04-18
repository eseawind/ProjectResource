package com.shlanbao.tzsc.wct.eqm.covelplan.service;

import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.eqm.covelplan.beans.EqmWheelCovelParamBean;
import com.shlanbao.tzsc.wct.eqm.covelplan.beans.EqpWCPBean;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
import com.shlanbao.tzsc.base.model.PageParams;

public interface EqpWheelServiceI {
	/**
	 * 设备轮保列表数据查询
	 * @param DataGrid
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	 public DataGrid getEqpList(EqpWCPBean eqmWCPBean ,int pageIndex)throws Exception;
	 
	 /**
	  * 
	 * @Title: queryParamList 
	 * @Description: 查询设备计划
	 * @param  bean
	 * @return DataGrid    返回类型 
	 * @throws
	  */
	 public DataGrid queryPlanList(EqmWheelCovelParamBean bean,int pageIndex);
	 /**
	  * 
	 * @Title: queryParamList 
	 * @Description: 查询设备维保详情  
	 * @param  bean
	 * @return DataGrid    返回类型 
	 * @throws
	  */
	 public DataGrid queryParamList(EqmWheelCovelParamBean bean,int pageIndex);
	 /**
	  * 
	 * @Title: updateBean 
	 * @Description: 修改设备维保详情  
	 * @param  bean
	 * @return boolean    返回类型 
	 * @throws
	  */
	 public boolean updateBean(EqmWheelCovelParam bean);
	 /**
	  * 
	 * @Title: findById 
	 * @Description: 根据Id查询Bean  
	 * @param  id
	 * @return EqmWheelCovelParam    返回类型 
	 * @throws
	  */
	 public EqmWheelCovelParam findById(String id);
	 /**
	  * 根据设备大类 查询其以下的所有维护项
	  * @param bean
	  * @param pageParams
	  * @return
	  */
	 public DataGrid queryWheelParts(EqmWheelCovelParamBean bean,PageParams pageParams)throws Exception;
	 
	 /**
	  * 根据设备项目做备注更新
	  * @param eqpBean
	  * @param login
	  * @throws Exception
	  */
	 public void editBean(EqmWheelCovelParamBean[] eqpBean,LoginBean login) throws Exception;
	 
	 /**
	  * 根据计划主键 更新状态
	  * @param key
	  * @param loginBean
	  * @return
	  * @throws Exception
	  */
	 public boolean updatePlanStatus(String key,LoginBean loginBean) throws Exception;
	 
	 public void updateCovelParam(String paramId,int fixType,String remarks,String planId) throws Exception;
	 /**
		 * 用于WCT轮保查询，用作修改备注
		 * 张璐-2015.11.2
		 * @param id
		 * @return
		 */
	 public EqmWheelCovelParamBean queryLB(String id);
	 
}
