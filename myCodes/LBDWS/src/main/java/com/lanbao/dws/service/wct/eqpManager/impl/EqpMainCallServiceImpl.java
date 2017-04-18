package com.lanbao.dws.service.wct.eqpManager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.ibm.framework.dal.transaction.template.CallBackTemplate;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.eqpManager.CosSparePartsBean;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;
import com.lanbao.dws.model.wct.eqpManager.EqpMainCallInfo;
import com.lanbao.dws.model.wct.eqpManager.RepairCallLogin;
import com.lanbao.dws.model.wct.eqpManager.RepairUserBean;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.service.wct.eqpManager.IEqpMainCallService;

@Service
public class EqpMainCallServiceImpl implements IEqpMainCallService {

	@Autowired
	IPaginationDalClient dalClient;
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月19日 上午10:53:41 
	* 功能说明 ：查询维修呼叫记录
	 */
	@Override
	public PaginationResult<List<EqpMainCallInfo>> queryEqpMainCallInfo(Pagination pagination, EqpMainCallInfo param,HttpSession session) {
		//查询最近1周的数据
		String sCreateTime=DateUtil.dateAdd("d", -7, new Date(), "yyyy-MM-dd");
		String eCreateTime=DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
		param.setsCreateTime(sCreateTime);
		param.seteCreateTime(eCreateTime);
		List<String> eqpCodes=param.getEqpCodes();
		//根据设备类型查询相关设备的维修呼叫信息
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		//登陆类型	0卷包机组    1 封箱机组     2成型机组    3发射机组
		int logType=loginBean.getLoginType();
		if(logType==0){
			eqpCodes.add(loginBean.getRollerEquipmentId());
			eqpCodes.add(loginBean.getPackerEquipmentId());
		}else if(logType==1){
			eqpCodes.add(loginBean.getBoxerEquipmentId0());
			eqpCodes.add(loginBean.getBoxerEquipmentId1());
		}else if(logType==2){
			eqpCodes.add(loginBean.getFilterEquipmentId0());
			eqpCodes.add(loginBean.getFilterEquipmentId1());
		}else if(logType==3){
			eqpCodes.add(loginBean.getLaunchEquipmentId0());
			eqpCodes.add(loginBean.getLaunchEquipmentId1());
			eqpCodes.add(loginBean.getLaunchEquipmentId2());
		}
		param.setEqpCodes(eqpCodes);
		return dalClient.queryForList("eqpMainCall.mainCallInfo",param,  EqpMainCallInfo.class,pagination);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月19日 下午3:07:39 
	* 功能说明 ：查询维修工
	 */
	@Override
	public PaginationResult<List<RepairUserBean>> queryRepairUsers(RepairUserBean param,Pagination pagination) {
		String eqpType=param.getEqpTypeName();
		String nowType="1";
		switch(eqpType){
		case "0":
			nowType="1,2";
			break;
		case "1":
			nowType="4";
			break;
		case "2":
			nowType="3";
			break;
		case "3":
			nowType="5";
			break;
		}
		param.setEqpTypeName(nowType);
		return dalClient.queryForList("eqpMainCall.queryRepairUsers", param, RepairUserBean.class,pagination);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月19日 下午10:05:13 
	* 功能说明 ：修改被呼叫维修工状态为忙碌
	 */
	@Override
	public void updateRepairUserStatus(final RepairUserBean param,final String repairCallLogin,final HttpSession session) {
		int flag=1;
		try {
			flag=dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>(){
				@Override
				public Integer invoke() {
					//更新维修工状态
					dalClient.execute("eqpMainCall.updateRepairUserStatus", param);
					//新增维修呼叫记录
					addServiceInfo(param,repairCallLogin,session);
					return 0;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月19日 下午10:45:21 
	* 功能说明 ：维修呼叫内登陆
	 */
	@Override
	public String repairCallLogin(RepairCallLogin token) {
			Map<String,Object> params=new HashMap<>();
			params.put("token", token.getToken());
			List<RepairCallLogin> users=dalClient.queryForList("eqpMainCall.repairCallLogin", params, RepairCallLogin.class);
			Map<String,RepairCallLogin> filter=new HashMap<>();
			RepairCallLogin bean=null;
			for (RepairCallLogin user : users) {
				//id为唯一
				bean=filter.get(user.getId());
				//新数据
				if(bean==null){
					bean=new RepairCallLogin();
					filter.put(user.getId(), bean);
				}
				//填充数据
				fillParams(bean,user);
			}
			//取出数据
			for (Map.Entry<String, RepairCallLogin>  user: filter.entrySet()) {
				bean=user.getValue();
			}
			if(bean!=null){
				bean.setFlag(true);
			}else{
				bean=new RepairCallLogin();
				bean.setFlag(false);
			}
			//转json
			return GsonUtil.bean2Json(bean);
		}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月19日 下午10:38:14 
	* 功能说明 ：
	 */
	private void fillParams(RepairCallLogin bean,RepairCallLogin param){
		String oldRole="";
		if(StringUtil.notEmpty(bean.getrName())){
			oldRole=bean.getrName();
		}
		bean.setuName(param.getuName());
		bean.setId(param.getId());
		bean.setrName(oldRole+"   "+param.getrName());
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月20日 上午8:55:54 
	* 功能说明 ：
	* 1.新增维修呼叫记录
	 * @param session 
	 */
	@Override
	public void addServiceInfo(RepairUserBean param,String repairCallLogin, HttpSession session) {
		//保存信息参数
		HashMap<String,Object> params=new HashMap<>();
		//主键
		params.put("id", StringUtil.getUUID());//维修呼叫id
		//内登陆信息
		RepairCallLogin callLoginBean=GsonUtil.fromJson2Bean(repairCallLogin, RepairCallLogin.class);
		params.put("logInUId", callLoginBean.getId());//创建人
		params.put("loginName", callLoginBean.getuName());
		params.put("createTime", new Date());//创建时间
		//机台登陆人信息（获取机台相关信息）
		LoginBean loginbean=(LoginBean) session.getAttribute("loginInfo");
		params.put("shiftId", loginbean.getShiftId());//班次
		params.put("teamId", loginbean.getTeamId());//班组
		String eqpName=param.getEqpTypeName();//根据设备类型确认是卷烟机或包装机维修呼叫
		if(eqpName.equals("2")){
			params.put("eqpId", loginbean.getPackerEquipmentId());
			params.put("eqpName", loginbean.getPackerEquipmentName());
		}else if(eqpName.equals("1")){
			params.put("eqpId", loginbean.getRollerEquipmentId());
			params.put("eqpName", loginbean.getRollerEquipmentName());
		}
		//查询当前机台运行的工单
		Map<String,Object> orderID=dalClient.queryForMap("eqpMainCall.queryNowWorkOrder", params);
		params.put("workOrderId", orderID.get("id"));//当前运行工单
		
		//维修工信息
		params.put("repairUserId", param.getUserId());//维修工id
		params.put("repairUserName", param.getUserName());//维修工
		params.put("typeName",param.getTypeId());//维修类型
		//当前呼叫状态
		params.put("status", "0");//待处理
		
		params.put("del", "0");//是否删除
		params.put("mesId", "-1");
		
		//保存维修呼叫记录
		dalClient.execute("eqpMainCall.saveRepairInfo", params);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月20日 下午1:46:32 
	* 功能说明 ：受理维修请求
	* 1.更新呼叫记录
	 */
	@Override
	public void acceptRepair(final EqpMainCallInfo param) {
		int flag=1;
		flag=dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>() {
			@Override
			public Integer invoke() {
				HashMap<String,Object> params=new HashMap<>();
				params.put("accceptTime", new Date());
				params.put("id", param.getId());
				params.put("status", "1");
				dalClient.execute("eqpMainCall.acceptRepair", params);
				return 0;
			}
		});
		
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月20日 下午2:04:38 
	* 功能说明 ：完成本次维修请求
	* 1.恢复维修工状态为空闲
	* 2.结束本次维修
	 */
	@Override
	public void finishRepair(final EqpMainCallInfo param) {
		int flag=1;
		try {
			flag=dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>() {
				@Override
				public Integer invoke() {
					HashMap<String,Object> params=new HashMap<>();
					params.put("userId", param.getDesignatedId());
					params.put("status", "0");
					dalClient.execute("eqpMainCall.updateRepairUserStatus", params);
					params.put("status", "2");
					params.put("finishTime", new Date());
					params.put("id", param.getId());
					dalClient.execute("eqpMainCall.finishRepair", params);
					//更新故障开始受理、结束、维修时长（分钟）
					dalClient.execute("eqpMainCall.updateFinishTrouble", params);
					return 0;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午3:15:32 
	* 功能说明 ：查询备品备件
	 */
	@Override
	public PaginationResult<List<CosSparePartsBean>> getSpareParts(Pagination pagination, CosSparePartsBean bean) {
		return dalClient.queryForList("eqpMainCall.queryForSpareParats", bean,CosSparePartsBean.class, pagination);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月20日 下午4:27:27 
	* 功能说明 ：
	* 1.更新备品备件信息
	* 2.查询故障id
	* 2.保存备件更换记录	EQM_FIX_REC
	 */
	@Override
	public int updateSparePartCount(final String str,final String sourceId) {
		int flag=1;
		try {
			flag=dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>() {
				@Override
				public Integer invoke() {
					String[] strf=str.split(",");
					String[] data=null;
					HashMap<String,Object> params=new HashMap<>();
					//查询维修呼叫机台班次信息
					params.put("id", sourceId);
					EqpMainCallInfo eqpCallInfo=dalClient.queryForObject("eqpMainCall.mainCallInfo", params,EqpMainCallInfo.class);
					if(eqpCallInfo==null){
						return 1;
					}
					String shiftId=eqpCallInfo.getShiftId();//班次id
					String eqpId=eqpCallInfo.getEqpId();//设备id
					String repairUserId=eqpCallInfo.getDesignatedId();//维修人
					String repairUserName=eqpCallInfo.getDesignatedName();
					String callUserId=eqpCallInfo.getCreateUserId();//呼叫人
					Date createTime=DateUtil.formatStringToDate(eqpCallInfo.getsCreateTime(), "yyyy-MM-dd HH:mm:ss");//呼叫时间
					String repairType=eqpCallInfo.getTypeName();//维修类型  1机型  2电气
					String source="4";//来源   维修呼叫
					//根据维修呼叫id查询之前添加的故障id，按创建时间最新的为当前故障
					List<Map<String,Object>> rMap=dalClient.queryForList("eqpMainCall.queryTruobleId", params);
					String troubleId="";
					if(rMap==null || rMap.size()!=1){
						return 1;//没有查找到故障信息，不能添加备件信息
					}else{
						troubleId=StringUtil.convertObjToString(rMap.get(0).get("s_id"));
						if(!StringUtil.notEmpty(troubleId)){
							return 1;//没有查找到故障信息，不能添加备件信息
						}
					}
					params.remove("id");
					for (String parts : strf) {
						data=parts.split("#");
						Double count=StringUtil.converObj2Double(data[1]);
						if(count>0){
							params.put("num", count);
							params.put("id", data[0]);
							//更新备件数量
							dalClient.execute("eqpMainCall.updateCosSparePartsById", params);
							params.remove("num");
							params.remove("id");
							//保存备件更换记录
							params.put("id",StringUtil.getUUID());
							params.put("eqpId",eqpId);
							params.put("shiftId",shiftId);
							params.put("repairUserId",repairUserId);//维修人
							params.put("repairUserName",repairUserName);//维修人
							params.put("repairType",repairType);//维修类型
							params.put("source",source);
							params.put("askUserId",callUserId);//呼叫人
							params.put("createTime",createTime);//呼叫时间
							params.put("sourceId", troubleId);//故障id
							params.put("partId",data[0]);//备件id
							params.put("num",count);//备件数量
							dalClient.execute("eqpMainCall.saveEqpFixRec", params);
						}
					}
					return 0;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据班组和类型查找维修工
	* <p>Description: </p>
	* @author shisihai   
	* 登陆类型0卷包机组    1 封箱机组     2成型机组    3发射机组
	      维修工类型  1 卷烟机   2 包装机   3成型机    4 封箱机  5 发射机
	* @date 2016上午10:04:06
	 */
	@Override
	public List<RepairUserBean> queryRepairUsersByTeamId(RepairUserBean param) {
		String eqpType=param.getEqpTypeName();
		String nowType="1";
		switch(eqpType){
		case "0":
			nowType="1,2";
			break;
		case "1":
			nowType="4";
			break;
		case "2":
			nowType="3";
			break;
		case "3":
			nowType="5";
			break;
			
		}
		param.setEqpTypeName(nowType);
		return dalClient.queryForList("eqpMainCall.queryRepairUsers", param, RepairUserBean.class);
	}
	
	
	@Override
	public void updateOpenRepairUserStatus(final RepairUserBean param,
			final EqmByLoginBean elbBean,final HttpSession session,final int eqpNum) {
			dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>(){
				@Override
				public Integer invoke() {
					//更新维修工状态
					dalClient.execute("eqpMainCall.updateRepairUserStatus", param);
					//新增维修呼叫记录
					HashMap<String,Object> params=new HashMap<>();//保存信息参数
					//主键
					params.put("id", StringUtil.getUUID());//维修呼叫id
					//内登陆信息
					params.put("logInUId", elbBean.getUserId() );//创建人
					params.put("loginName", elbBean.getUserName() );
					params.put("createTime", new Date());//创建时间
					//机台登陆人信息（获取机台相关信息）
					LoginBean loginbean=(LoginBean) session.getAttribute("loginInfo");
					params.put("shiftId", loginbean.getShiftId());//班次
					params.put("teamId", loginbean.getTeamId());//班组
					String eqpName=param.getEqpTypeName();//
					//根据设备类型确认是卷烟机或包装机维修呼叫  1 卷烟机   2 包装机   3成型机    4 封箱机  5 发射机
					//对于3成型机    4 封箱机  5 发射机，需要多传递一个参数，判断是第几个机器发出的维修请求
					//发射机  3台一块屏  ； 封箱机  2台一块  ； 成型机  2台一块
					if(eqpName.equals("2") && eqpNum==0){
						params.put("eqpId", loginbean.getPackerEquipmentId());
						params.put("eqpName", loginbean.getPackerEquipmentName());
					}else if(eqpName.equals("1") && eqpNum==0){
						params.put("eqpId", loginbean.getRollerEquipmentId());
						params.put("eqpName", loginbean.getRollerEquipmentName());
					}else if(eqpName.equals("3")){ //成型机
						if(eqpNum==1){
							params.put("eqpId", loginbean.getFilterEquipmentId0());
							params.put("eqpName", loginbean.getFilterEquipmentName0());
						}else{
							params.put("eqpId", loginbean.getFilterEquipmentId1());
							params.put("eqpName", loginbean.getFilterEquipmentName1());
						}
					}else if(eqpName.equals("4")){ //封箱机 
						if(eqpNum==1){
							params.put("eqpId", loginbean.getBoxerEquipmentId0());
							params.put("eqpName", loginbean.getBoxerEquipmentName0());
						}else{
							params.put("eqpId", loginbean.getBoxerEquipmentId1());
							params.put("eqpName", loginbean.getBoxerEquipmentName1());
						}
					}else if(eqpName.equals("5")){ //5 发射机
						if(eqpNum==1){
							params.put("eqpId", loginbean.getLaunchEquipmentId0());
							params.put("eqpName", loginbean.getLaunchEquipmentName0());
						}else if(eqpNum==2){
							params.put("eqpId", loginbean.getLaunchEquipmentId1());
							params.put("eqpName", loginbean.getLaunchEquipmentName1());
						}else{
							params.put("eqpId", loginbean.getLaunchEquipmentId2());
							params.put("eqpName", loginbean.getLaunchEquipmentName2());
						}
					}else{
						return 1;
					}
					//查询当前机台运行的工单
					Map<String,Object> orderID=dalClient.queryForMap("eqpMainCall.queryNowWorkOrder", params);
					if(orderID!=null){
						params.put("workOrderId", orderID.get("id"));//当前运行工单
					}else{
						params.put("workOrderId","-1");
					}
					//维修工信息
					params.put("repairUserId", param.getUserId());//维修工id
					params.put("repairUserName", param.getUserName());//维修工
					params.put("typeName",param.getTypeId());//维修类型
					
					//当前呼叫状态
					params.put("status", "0");//待处理
					params.put("del", "0");//是否删除
					params.put("mesId", param.getEqm_wcp_id());//MESID
					//保存维修呼叫记录
					dalClient.execute("eqpMainCall.saveRepairInfo", params);
				
					return 0;
				}
			});
		
	}
	
	
}
