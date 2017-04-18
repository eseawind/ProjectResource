package com.shlanbao.tzsc.wct.eqm.checkplan.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.base.mapping.EqmCheckcatParam;
import com.shlanbao.tzsc.base.mapping.EqmCullRecordBean;
import com.shlanbao.tzsc.base.mapping.EqmProtectRecord;
import com.shlanbao.tzsc.base.mapping.EqmSpotchRecode;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.paul.beans.EqmPaulDayBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmCheckParamBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmShiftDataBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqpCheckBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqpCheckRecordBean;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

public interface EqpCheckServiceI {
	/**
	 * 设备点检列表数据查询
	 * @param DataGrid
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	 public DataGrid getEqpList(EqpCheckBean eqmBean ,int pageIndex)throws Exception;
	 
	 /**
	  * 
	 * @Title: queryParamList 
	 * @Description: 查询设备点检计划
	 * @param  bean
	 * @return DataGrid    返回类型 
	 * @throws
	  */
	 public DataGrid queryPlanList(EqmCheckParamBean bean,int pageIndex);
	 /**
	  * 
	 * @Title: queryParamList 
	 * @Description: 查询设备点检详情  
	 * @param  bean
	 * @return DataGrid    返回类型 
	 * @throws
	  */
	 public DataGrid queryParamList(EqmCheckParamBean bean,int pageIndex);
	 /**
	  * 
	 * @Title: updateBean 
	 * @Description: 修改设备点检详情  
	 * @param  bean
	 * @return boolean    返回类型 
	 * @throws
	  */
	 public boolean updateBean(EqmCheckcatParam bean);
	 /**
	  * 
	 * @Title: findById 
	 * @Description: 根据Id查询Bean  
	 * @param  id
	 * @return EqmCheckParam    返回类型 
	 * @throws
	  */
	 public EqmCheckcatParam findById(String id);
	 /**
	  * 根据设备大类 查询其以下的所有点检项
	  * @param bean
	  * @param pageParams
	  * @return
	  */
	 public DataGrid queryParts(EqmCheckParamBean bean,PageParams pageParams)throws Exception;
	 
	 /**
	  * 根据设备项目做备注更新
	  * @param eqpBean
	  * @param login
	  * @throws Exception
	  */
	 public void editBean(EqmCheckParamBean[] eqpBean,LoginBean login) throws Exception;
	 
	 /**
	  * 根据计划主键 更新状态
	  * @param key
	  * @param loginBean
	  * @return
	  * @throws Exception
	  */
	 public boolean updatePlanStatus(String key,LoginBean loginBean) throws Exception;
	 
	 /**
	 * 功能说明：设备日保管理-查询
	 * @param bean
	 * @param pageIndex
	 * @return
	 * 
	 * @author wchuang
	 * @time 2015年7月8日15:39:57
	 * 
	 */
	 public DataGrid queryProtectList(HttpSession session,EqmPaulDayBean bean,int pageIndex);

	 /**
	 * 功能说明：设备日保管理-操作添加
	 * @param id,uname,uId
	 * @return 
	 * 最后修改人：张璐，添加完成和未完成ID用于插入历史记录-2015.9.25
	 * @author wchuang
	 * @time 2015年7月8日15:39:57
	 * 
	 */
	 public void addEqpProtect(EqmProtectRecord epr,String id_check,String id_nocheck);

	/**
	 * 功能描述：设备点检管理-查询
	 * 
	 * @author wchuang
	 * @time 2015年7月16日14:55:51
	 * @param ecrbean
	 * @return
	 * @throws ParseException 
	 * */
	public List<EqpCheckRecordBean> queryTayRecord(EqpCheckRecordBean ecrbean) throws ParseException;

	/**
	 * 功能描述：设备点检管理- 点检审核
	 * 
	 * @author wchuang
	 * @time 2015年7月17日9:58:03
	 * @param ecrbean
	 * @return
	 * */
	public boolean addEqmSpotchRecord(EqpCheckRecordBean ecrbean,String equipmentTypeId,EqmSpotchRecode bean);

	/**
	 * 功能描述：设备剔除时间-日保
	 * 
	 * @author wchuang
	 * @time 2015年7月17日9:58:03
	 * @param ecrbean
	 * @return
	 * */
	public void addEqmCullRecord(EqmCullRecordBean eqb);

	/**
	 * [功能说明]：设备日保，点击日保养，跳转详细页面 -查询 
	 * 
	 * @author wanchanghuang
	 * @time  2015年9月8日16:32:46
	 * 
	 * */
	public DataGrid querySysEqpType(HttpSession session, SysEqpTypeBean bean,int pageIndex);
	/**
	 * 功能：向记录表中添加更换备件记录
	 * @author zhanglu
	 * @param all_id
	 * @param use_n
	 * @param all_num
	 * @param username
	 * @param userId
	 * @param eqp_id
	 * @param shift_id
	 * @throws Exception
	 */
	public void addFixRec(String all_id,String use_n,String all_num,String username,String userId,MdEquipment eqp_id,MdShift shift_id,String trouble_id,String type,int fix_type)throws Exception;
	
	/**功能：针对角色是维修管理的进行查询
	 * @author zhanglu
	 * @param ecrbean
	 * @param zg_userid
	 * @return
	 * @throws ParseException
	 */
	public List<EqpCheckRecordBean> queryZG_USERID(EqpCheckRecordBean ecrbean,String zg_userid,String []del) throws ParseException;
	/**
	 * 用来写入故障表的模板，将来使用
	 * 张璐
	 * 2015-10-8
	 */
	public void addTrouble(List<EqmTroubleBean> trouble);
	/**
	 * 查询故障表，用作故障下拉框
	 * 张璐
	 * 2015-10-10
	 * @return
	 */
	public List<?> queryTroubleComb();

	

}
