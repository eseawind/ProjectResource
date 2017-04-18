package com.shlanbao.tzsc.pms.msg.info.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MsgInfoDaoI;
import com.shlanbao.tzsc.base.mapping.MsgInfo;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.md.eqp.service.EquipmentsServiceI;
import com.shlanbao.tzsc.pms.msg.info.beans.MsgInfoBean;
import com.shlanbao.tzsc.pms.msg.info.beans.MsgOperatorBean;
import com.shlanbao.tzsc.pms.msg.info.service.MsgInfoServiceI;
import com.shlanbao.tzsc.pms.sys.role.beans.RoleBean;
import com.shlanbao.tzsc.pms.sys.user.service.UserServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;

@Service
public class MsgInfoServiceImpl extends BaseService implements MsgInfoServiceI{
	@Autowired
	protected EquipmentsServiceI equipmentService;
	@Autowired
	protected MsgInfoDaoI msgInfoDao;
	@Autowired
	protected UserServiceI userService;
	
	/**
	 * 该方法只是进行保存添加或者修改的数据
	 */
	@Override
	public Json addMsgInfo(MsgInfo msgInfo) throws Exception {
		Json json=new Json(); 
	MsgInfo info = null;
		SessionInfo sessionInfo = (SessionInfo) WebContextUtil.getObjectFromSession();
		//当Id不等于空时，则证明是新增的消息，否则为修改的消息
		if(sessionInfo.getUser()!=null){	
			if(StringUtil.notNull(msgInfo.getId())){
				//修改
				/*if(sessionInfo.getUser().getId().equals(msgInfo.getSysUserByApproval().getId()) ||
						sessionInfo.getUser().getId().equals(msgInfo.getSysUserByIssuer().getId())||
						msgInfo.getSysUserByApproval().getId().equals(msgInfo.getSysUserByIssuer().getId())){
					json.setMsg("审核人和签发人申请人“不能为同一人”");
					json.setSuccess(false);
					return json;
				}else{*/
					info = msgInfoDao.findById(MsgInfo.class, msgInfo.getId());
					beanConvertor.copyProperties(msgInfo, info);
					info.setTime(new Date());
					info.setFlag((long)1);
				
			}else{
				//增加
				/*if(sessionInfo.getUser().getId().equals(msgInfo.getSysUserByApproval().getId()) || 
						sessionInfo.getUser().getId().equals(msgInfo.getSysUserByIssuer().getId())||
						msgInfo.getSysUserByApproval().getId().equals(msgInfo.getSysUserByIssuer().getId())){
					json.setMsg("审核人和签发人申请人“不能为同一人”");
					json.setSuccess(false);
					return json;
					}else{*/
						info = msgInfo;
						//设置发起人
						info.setSysUserByInitiator(new SysUser(sessionInfo.getUser().getId()));
						info.setDel(0L);
						info.setId(null);
						info.setTime(new Date());
						info.setFlag((long)1);
			}
			msgInfoDao.saveOrUpdate(info);
			json.setMsg("操作成功");
			json.setSuccess(true);
		}else{
			json.setMsg("用户未登录");
			json.setSuccess(false);
		}	
		
		return json;
	}

	/**
	 * 该方法用于审批保存数据
	 */
	@Override
	public void saveMsgInfo(MsgInfo msgInfo) throws Exception {
		MsgInfo info=msgInfoDao.findById(MsgInfo.class, msgInfo.getId());
		info.setApproveContent(msgInfo.getApproveContent());
		info.setFlag(msgInfo.getFlag());
		msgInfoDao.saveOrUpdate(info);
	}

	@Override
	public DataGrid queryMsgInfo(MsgInfoBean msgInfo, PageParams pageParams)throws Exception {		
		String hql = "from MsgInfo o where  1=1 and o.del=0 and o.msgInfo is null";
		String params = "";
		if(StringUtil.notNull(msgInfo.getTitle())){
			params+=" and o.title like '%"+msgInfo.getTitle()+"%'";
		}
		/*if(msgInfo.getFlag()!=null){
			params+=" and o.flag = '"+msgInfo.getFlag()+"'";
		}*/
		//时间
		
		params+=StringUtil.fmtDateBetweenParams("o.time", msgInfo.getTime(), msgInfo.getApproveTime());
		
		List<MsgInfo> infos = msgInfoDao.queryByPage(hql.concat(params), pageParams);
		String sqlHql = "select count(*) " + hql;
		long total = msgInfoDao.queryTotal(sqlHql.concat(params));
		List<MsgInfo>  infos2=msgInfoDao.query(hql);
		List<MsgInfoBean> infoBeans=new ArrayList<MsgInfoBean>();	
		for (MsgInfo msgInfo2 : infos2) {
			MsgInfoBean infoBean=beanConvertor.copyProperties(msgInfo2, MsgInfoBean.class);
			/*if(msgInfo2.getSysUserByApproval()!=null){
				infoBean.setApprovalName(msgInfo2.getSysUserByApproval().getName());
				infoBean.setApproval(msgInfo2.getSysUserByApproval().getId());
			}*/
			
			if(msgInfo2.getSysUserByInitiator()!=null){
				infoBean.setInitiatorName(msgInfo2.getSysUserByInitiator().getName());
				infoBean.setInitiator(msgInfo2.getSysUserByInitiator().getId());
			}
			/*if(msgInfo2.getSysUserByIssuer()!=null){
				infoBean.setIssuerName(msgInfo2.getSysUserByIssuer().getName());
				infoBean.setIssuer(msgInfo2.getSysUserByIssuer().getId());
			}*/
			infoBeans.add(infoBean);
		}
		
		return new DataGrid(infoBeans, total);
	}

	@Override
	public void deleteMsgInfoById(String id) throws Exception {
		msgInfoDao.findById(MsgInfo.class, id).setDel(1L);
	}

	@Override
	public MsgOperatorBean getCurrUserOperator() throws Exception {
		SessionInfo sessionInfo = (SessionInfo) WebContextUtil.getObjectFromSession();
		//判断发起人的角色是否和审批人的角色一致
		List<RoleBean> roleBeans = sessionInfo.getRoles();
		List<String> roleNames = new ArrayList<String>();
		for (RoleBean roleBean : roleBeans) {
			if(roleBean.isChecked())
				roleNames.add(roleBean.getName());
		}
		MsgOperatorBean msgOperatorBean = new MsgOperatorBean();
		if(roleNames.contains("设备管理员")) //审批
			msgOperatorBean.setName("APPROVE");
		if(roleNames.contains("车间主任"))  //签发
			msgOperatorBean.setName("ISSUER");
		msgOperatorBean.setOperId(sessionInfo.getUser().getId());
		return msgOperatorBean;
	}
	
	@Override
	public MsgInfoBean getMsgInfoBeanById(String id) throws Exception {
		MsgInfo msgInfo = msgInfoDao.findById(MsgInfo.class, id);
		MsgInfoBean bean = beanConvertor.copyProperties(msgInfo, MsgInfoBean.class);
		if(msgInfo.getSysUserByInitiator()!=null){
			bean.setInitiatorName(msgInfo.getSysUserByInitiator().getName());
		}		
		return bean;
	}

	@Override
	public List<MsgInfoBean> getMsgInfo() throws Exception {
		String hql="from MsgInfo o where 1=1 and o.del=0 and o.flag=3 and o.msgInfo is null order by o.time desc";
		//插叙前5条数据
		List<MsgInfo> msgInfos = msgInfoDao.queryByPage(hql, 1, 5, new Object[]{});
		List<MsgInfoBean> beans = beanConvertor.copyList(msgInfos, MsgInfoBean.class);
		return beans;
	}

	@Override
	public List<MsgInfoBean> getMsgInfoApprove(String id) throws Exception {
		String hql = "from MsgInfo o where 1=1 and o.del=0 and o.msgInfo.id='" + id + "'";
		List<MsgInfo> infos = msgInfoDao.query(hql, new Object[]{});
		List<MsgInfoBean> beans = new ArrayList<MsgInfoBean>();
		for (MsgInfo info : infos) {
			MsgInfoBean bean = beanConvertor.copyProperties(info, MsgInfoBean.class);
			if(StringUtil.notNull(bean.getInitiator()))
				bean.setInitiatorName(userService.getSysUserById(bean.getInitiator()).getName());
			if(StringUtil.notNull(bean.getApproval()))
				bean.setApprovalName(userService.getSysUserById(bean.getApproval()).getName());
			if(StringUtil.notNull(bean.getIssuer()))
				bean.setIssuerName(userService.getSysUserById(bean.getIssuer()).getName());
			if(info.getMsgInfo() != null){
				bean.setPid(info.getMsgInfo().getId());
				bean.setpName(info.getMsgInfo().getTitle());
			}
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public MsgInfoBean getMsgInfoById(String id) throws Exception {
		MsgInfo info=msgInfoDao.findById(MsgInfo.class, id);
		MsgInfoBean infoBean=beanConvertor.copyProperties(info, MsgInfoBean.class);
		if(info.getSysUserByApproval()!=null){
			infoBean.setApprovalName(info.getSysUserByApproval().getName());
			infoBean.setApproval(info.getSysUserByApproval().getId());
		}
		if(info.getSysUserByIssuer()!=null){
			infoBean.setIssuerName(info.getSysUserByIssuer().getName());
			infoBean.setIssuer(info.getSysUserByIssuer().getId());		
		}
		return infoBean;
	}


	
	

}
