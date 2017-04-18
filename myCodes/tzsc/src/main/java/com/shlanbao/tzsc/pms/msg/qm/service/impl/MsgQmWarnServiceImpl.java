package com.shlanbao.tzsc.pms.msg.qm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MsgQmWarnDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.mapping.MsgQmWarn;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.msg.qm.beans.MsgQmWarnBean;
import com.shlanbao.tzsc.pms.msg.qm.service.MsgQmWarnServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class MsgQmWarnServiceImpl extends BaseService implements MsgQmWarnServiceI{

	@Autowired
	protected MsgQmWarnDaoI msgQmWarnDao;
	@Autowired
	SchWorkorderDaoI workOderDao;
	
	@Override
	public void addMsgQmWarn(MsgQmWarn msgQmWarn) throws Exception {
		MsgQmWarn msgQm = null;
		if(StringUtil.notNull(msgQmWarn.getId())){
			msgQm = msgQmWarnDao.findById(MsgQmWarn.class, msgQmWarn.getId());
			beanConvertor.copyProperties(msgQmWarn, msgQm);
		}else{
			msgQm = msgQmWarn;
			msgQm.setDel(0L);
			msgQm.setTime(new Date());
			msgQm.setSts(0L);
			msgQm.setId(null);
		}
		msgQmWarnDao.saveOrUpdate(msgQm);
	}

	@Override
	public MsgQmWarn getMsgQmWarnById(String id) throws Exception {
		return msgQmWarnDao.findById(MsgQmWarn.class, id);
	}

	/**
	 * 功能：查询质量警告记录
	 * 张璐--2015.10.13
	 */
	@Override
	public DataGrid queryMsgQmWarn(MsgQmWarnBean msgQmWarn, PageParams pageParams)throws Exception {
		StringBuffer sb= new StringBuffer();
		String star="SELECT * from";
		sb.append(" (SELECT qw.id,mw.name as mname,me.equipment_name,su.name as sname,qw.CONTENT,qw.QI,qw.VAL,qw.ITEM,qw.TIME,qw.STS,ROW_NUMBER () OVER (ORDER BY qw. TIME) AS num FROM MSG_QM_WARN qw,SCH_WORKORDER sw,SYS_USER su,MD_WORKSHOP mw,MD_EQUIPMENT me ");
		sb.append("WHERE qw.del = 0 AND sw.code = qw.oid AND qw.uid = su.id AND mw.code = sw.type AND me.id = sw.EQP AND sw.SHIFT = '1' and mw.del='0'");
		if(StringUtil.notNull(msgQmWarn.getEquipName()))
			sb.append(" and me.equipment_name like'%"+msgQmWarn.getEquipName()+"%'");
		if(StringUtil.notNull(msgQmWarn.getWorkShopId()))
			sb.append(" and mw.id='"+msgQmWarn.getWorkShopId()+"' ");
		sb.append(StringUtil.fmtDateBetweenParams("qw.time", msgQmWarn.getTime(), msgQmWarn.getTime2()));
		sb.append(") ");
		String end ="zl where num>'"+((pageParams.getPage()-1)*pageParams.getRows())+"' and num<='"+(pageParams.getPage()*pageParams.getRows())+"' ";
		List<?> listFY = msgQmWarnDao.queryBySql(sb.toString(),null);
		List<?> list=msgQmWarnDao.queryBySql(star+sb.toString()+end,null);
		List<MsgQmWarnBean> beans = new ArrayList<MsgQmWarnBean>();
		if(list.size()>0){
			for(Object ob : list){
				Object[] temp = (Object[]) ob;
				MsgQmWarnBean bean = new MsgQmWarnBean();
				bean.setId(temp[0].toString());
				bean.setWorkShopName(temp[1].toString());
				bean.setEquipName(temp[2].toString());
				bean.setuName(temp[3].toString());
				if(temp[4]!=null&&!temp[4].equals("")){
					bean.setContent(temp[4].toString());
				}
				if(temp[5]!=null&&!temp[5].equals("")){
					double qi=Double.parseDouble(temp[5].toString());
					bean.setQi(qi);
				}
				if(temp[6]!=null&&!temp[6].equals("")){
					double val=Double.parseDouble(temp[6].toString());
					bean.setVal(val);
				}
				if(temp[7]!=null&&!temp[7].equals("")){
					bean.setItem(temp[7].toString());
				}
				
				bean.setTime(temp[8].toString());
				long status = Long.parseLong(temp[9].toString());
				bean.setSts(status);
				beans.add(bean);
			}
		}
		long total = listFY.size();
		return new DataGrid(beans, total);
		
		/*String hql = "from MsgQmWarn o where 1=1 and o.del=0";
		String params = "";
		if(StringUtil.notNull(msgQmWarn.getEquipName()))
			params += " and o.schWorkorder.mdEquipment.equipmentName like'%"+msgQmWarn.getEquipName()+"%'";
		if(StringUtil.notNull(msgQmWarn.getWorkShopId()))
			params += " and o.schWorkorder.mdEquipment.mdWorkshop.id='"+msgQmWarn.getWorkShopId()+"' ";
		params+=StringUtil.fmtDateBetweenParams("o.time", msgQmWarn.getTime(), msgQmWarn.getTime2());
		List<MsgQmWarn> msgQmWarns = msgQmWarnDao.queryByPage(hql.concat(params), pageParams);
		hql = "select count(*) "+hql;
		long total = msgQmWarnDao.queryTotal(hql.concat(params));
		return new DataGrid(convertBeans(msgQmWarns), total);*/
	}

	@Override
	public List<MsgQmWarnBean> getMsgQmWarns(Long sts) throws Exception {
		String hql = "from MsgQmWarn o where 1=1 and o.del=0 and o.sts=" + sts;
		List<MsgQmWarn> msgQmWarns = msgQmWarnDao.query(hql, new Object[]{});
		return convertBeans(msgQmWarns);
	}

	@Override
	public void deleteMsgQmWarn(String id) throws Exception {
		msgQmWarnDao.findById(MsgQmWarn.class, id).setDel(1L);
	}

	/**
	 * 修改人：张璐--2015-10-13
	 * 将po实体列表转化为vo实体列表
	 * @param msgQmWarns
	 * @return
	 * @throws Exception
	 */
	private List<MsgQmWarnBean> convertBeans(List<MsgQmWarn> msgQmWarns)throws Exception{
		List<MsgQmWarnBean> beans = new ArrayList<MsgQmWarnBean>();
		for (MsgQmWarn msgQm : msgQmWarns) {
			MsgQmWarnBean bean = beanConvertor.copyProperties(msgQm, MsgQmWarnBean.class);
			if(msgQm.getSysUser() != null){
				bean.setUid(msgQm.getSysUser().getId());
				bean.setuName(msgQm.getSysUser().getName());
			}
			if(msgQm.getSchWorkorder() != null){
				String hqlByOrder="select ID from SCH_WORKORDER where code='"+msgQm.getSchWorkorder().getId()+"'";
				List<?> listO= workOderDao.queryBySql(hqlByOrder, null);
				String oder = (String) listO.get(0);
				SchWorkorder wokder = workOderDao.findById(SchWorkorder.class, oder);
				bean.setWorkId(wokder.getId());
				bean.setWorkCode(wokder.getCode());
				bean.setWorkShopId(wokder.getMdEquipment().getMdWorkshop().getId());
				bean.setWorkShopName(wokder.getMdEquipment().getMdWorkshop().getName());
				bean.setEquipId(wokder.getMdEquipment().getId());
				bean.setEquipName(wokder.getMdEquipment().getEquipmentName());
			}
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public MsgQmWarnBean getMsgQmWarnBeanById(String id) throws Exception {
		MsgQmWarn msgQmWarn = msgQmWarnDao.findById(MsgQmWarn.class, id);
		MsgQmWarnBean bean = beanConvertor.copyProperties(msgQmWarn, MsgQmWarnBean.class);
		if(msgQmWarn.getSysUser() != null){
			bean.setUid(msgQmWarn.getSysUser().getId());
			bean.setuName(msgQmWarn.getSysUser().getName());
		}
		if(msgQmWarn.getSchWorkorder() != null){
			bean.setWorkId(msgQmWarn.getSchWorkorder().getId());
			bean.setWorkCode(msgQmWarn.getSchWorkorder().getCode());
			bean.setWorkShopId(msgQmWarn.getSchWorkorder().getMdEquipment().getMdWorkshop().getId());
			bean.setWorkShopName(msgQmWarn.getSchWorkorder().getMdEquipment().getMdWorkshop().getName());
			bean.setEquipId(msgQmWarn.getSchWorkorder().getMdEquipment().getId());
			bean.setEquipName(msgQmWarn.getSchWorkorder().getMdEquipment().getEquipmentName());
		}
		bean.setContent(bean.getContent().length() > 24 ? bean.getContent().substring(24):bean.getContent());
		return bean;
	}
}
