package com.shlanbao.tzsc.wct.eqm.fix.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;
import com.shlanbao.tzsc.wct.eqm.fix.beans.SysMaintenanceStaff;
import com.shlanbao.tzsc.wct.eqm.fix.beans.SysServiceInfo;
import com.shlanbao.tzsc.wct.eqm.fix.beans.WctEqmFixInfoBean;
import com.shlanbao.tzsc.wct.eqm.fix.beans.WctEqmFixRecBean;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 设备维修记录服务接口
 *
 */
public interface WctEqmFixRecServiceI{
	/**
	 * 添加或者修改设备维修记录
	 * @param eqmFixRec
	 * @throws Exception
	 */
	public void addFixRec(WctEqmFixRecBean eqmFixRecBean,LoginBean loginBean)throws Exception;
	
	/**
	 * 查询请求的维修并处理
	 * @param DataGrid
	 * @param bean
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	 public DataGrid getFixRecList(WctEqmFixRecBean bean,PageParams pageParams)throws Exception;
	 /**
	 *根据主键更新相应状态
	 * @param eqmFixRec
	 * @throws Exception
	 */
	public void updateFixRec(WctEqmFixRecBean eqmFixRecBean,LoginBean loginBean)throws Exception;
	 /**
	 *根据主键更新相应状态
	 * @param eqmFixRec
	 * @throws Exception
	 */
	public void updateFixRecFinsh(WctEqmFixRecBean eqmFixRecBean,LoginBean loginBean)throws Exception;

	/**
	 * [功能说明]：维修请求-呼叫点餐查询页
	 * 
	 * @author  wanchanghuang
	 * @time 2015年8月24日15:49:07
	 * 
	 * type_id  1-机械工   2-电气工
	 * 
	 * */
	public List<SysMaintenanceStaff> queryStaffAll(SysMaintenanceStaff ssf);

	
	/**
	 * [功能说明]：维修请求-添加呼叫记录
	 * 
	 * @author  wanchanghuang
	 * @time 2015年8月24日15:49:07
	 * 
	 * type_id  1-机械工   2-电气工
	 * 
	 * */
	public String saveServcieInfo(SysServiceInfo ssInfo);

	/**
	 * [功能说明]：维修请求-添加呼叫记录
	 * 
	 * @author  wanchanghuang
	 * @time 2015年8月24日15:49:07
	 * 
	 * type_id  1-机械工   2-电气工
	 * 
	 * */
	public List<SchWorkorder> queryWorkOrder(SysServiceInfo ssInfo);

	/**
	 * [功能说明]：维修请求-查询
	 * 
	 * @author  wanchanghuang
	 * @time 2015年9月4日14:14:25
	 * 
	 * */
	public List<SysServiceInfo> querySysServiceInfoAll(SysServiceInfo info);
	
	/**
	 * 添加维修信息
	 * @param fixInfoBean
	 * @param loginBean
	 * @throws Exception
	 */
	public void addFix(WctEqmFixInfoBean fixInfoBean,String all_id,String all_num,String use_n,String name,String fixId)throws Exception;
	
	/**
	 * 
	 * 修改维修状态
	 * @param id
	 * @throws Exception
	 * @author 景孟博
	 */
	public void updateSlServiceInfo(String id,String status,String designated_person_id,String designated_person_name,String name);
	/**
	 * 根据id查询维修人姓名
	 * @param id
	 * @author 景孟博
	 */
	public SysServiceInfo querySlServiceInfoById(String id);
	/**
	 * 修改维修工工作状态
	 * @author 景孟博
	 * @param status
	 * @param designated_person_id
	 * @throws Exception
	 */
	public void updateStaff(String status,String designated_person_id)throws Exception;
	/**
	 * 张璐-2015.11.6
	 * WCT手动添加备品备件
	 * @param spare_name
	 * @param spare_code
	 * @return 
	 * @throws Exception
	 */
	public String[] addNewFix(String spare_name,String spare_code)throws Exception;
	/**
	 * 张璐
	 * 查询备品备件的名称和型号
	 */
	public String[] querySpareName(String[] id,String[] unum);
	
	/**
	 * 维修呼叫查询备品备件及故障
	 * @author 景孟博
	 * @time 2015年11月12日
	 * 
	 */
	public List<SysServiceInfo> querySpareTrouble(String id);

	public RepairResquestBean queryServiceInfoById(String idf);
}
