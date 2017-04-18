package com.shlanbao.tzsc.pms.msg.cons.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MsgConWarnDaoI;
import com.shlanbao.tzsc.base.mapping.MsgConWarn;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.msg.cons.beans.MsgConWarnBean;
import com.shlanbao.tzsc.pms.msg.cons.service.MsgConWarnServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class MsgConWarnServiceImpl extends BaseService implements MsgConWarnServiceI{
	@Autowired
	protected MsgConWarnDaoI msgConWarnDao;
	@Override
	public void addMsgConWarn(MsgConWarn msgConWarn) throws Exception {
		MsgConWarn msgCon = null;
		if(StringUtil.notNull(msgConWarn.getId())){
			msgCon = msgConWarnDao.findById(MsgConWarn.class, msgConWarn.getId());
			beanConvertor.copyProperties(msgConWarn, msgCon);
		}else{
			msgCon = msgConWarn;
			msgCon.setDel(0L);
			msgCon.setSts(0L);
			msgCon.setTime(new Date());
			msgCon.setId(null);
		}
		msgConWarnDao.saveOrUpdate(msgCon);
	}

	@Override
	public DataGrid queryMsgConWarn(MsgConWarnBean msgConWarn, PageParams pageParams,Date startTime,Date endTime)throws Exception {
		
		String hql = "from MsgConWarn o where 1=1 and o.del=0";
		String params = "";
		if(StringUtil.notNull(msgConWarn.getEquipName()))
			params += " and o.schWorkorder.mdEquipment.equipmentName  like '%"+msgConWarn.getEquipName()+"%'";
		if(StringUtil.notNull(msgConWarn.getWorkShopId()))
			params += " and o.schWorkorder.mdEquipment.mdWorkshop.id='"+msgConWarn.getWorkShopId()+"'";
		
		params+=StringUtil.fmtDateBetweenParams("o.time", msgConWarn.getTime(), msgConWarn.getTime2());
		List<MsgConWarn> msgConWarns = msgConWarnDao.queryByPage(hql.concat(params), pageParams);
		String sqlhql = "select count(*) " + hql;
		long total = msgConWarnDao.queryTotal(sqlhql.concat(params));
		return new DataGrid(convertToBeans(msgConWarns), total);
	}

	@Override
	public List<MsgConWarnBean> getMsgConWarns(long sts) throws Exception {
		return convertToBeans(msgConWarnDao.query("from MsgConWarn o where 1=1 and o.del=0 and o.sts=" + sts));
	}

	@Override
	public MsgConWarn getMsgConWarnById(String id) throws Exception {
		return msgConWarnDao.findById(MsgConWarn.class, id);
	}

	@Override
	public MsgConWarnBean getMsgConWarnBeanById(String id) throws Exception {
		MsgConWarn msgConWarn = getMsgConWarnById(id);
		MsgConWarnBean bean = beanConvertor.copyProperties(msgConWarn, MsgConWarnBean.class);
		if(msgConWarn.getSchWorkorder() != null){
			bean.setWorkId(msgConWarn.getSchWorkorder().getId());
			bean.setWorkCode(msgConWarn.getSchWorkorder().getCode());
			bean.setWorkShopId(msgConWarn.getSchWorkorder().getMdEquipment().getMdWorkshop().getId());
			bean.setWorkShopName(msgConWarn.getSchWorkorder().getMdEquipment().getMdWorkshop().getName());
			bean.setEquipId(msgConWarn.getSchWorkorder().getMdEquipment().getId());
			bean.setEquipName(msgConWarn.getSchWorkorder().getMdEquipment().getEquipmentName());
		}
		return bean;
	}

	@Override
	public void deleteMsgConWarnById(String id) throws Exception {
		getMsgConWarnById(id).setDel(1L);
	}
	
	private List<MsgConWarnBean> convertToBeans(List<MsgConWarn> msgConWarns)throws Exception{
		List<MsgConWarnBean> beans = new ArrayList<MsgConWarnBean>();
		for (MsgConWarn msgConWarn : msgConWarns) {
			MsgConWarnBean bean = beanConvertor.copyProperties(msgConWarn, MsgConWarnBean.class);
			if(msgConWarn.getSchWorkorder() != null){
				bean.setWorkId(msgConWarn.getSchWorkorder().getId());
				bean.setWorkCode(msgConWarn.getSchWorkorder().getCode());
				bean.setWorkShopId(msgConWarn.getSchWorkorder().getMdEquipment().getMdWorkshop().getId());
				bean.setWorkShopName(msgConWarn.getSchWorkorder().getMdEquipment().getMdWorkshop().getName());
				bean.setEquipId(msgConWarn.getSchWorkorder().getMdEquipment().getId());
				bean.setEquipName(msgConWarn.getSchWorkorder().getMdEquipment().getEquipmentName());
			}
			beans.add(bean);
		}
		return beans;
	}

}
