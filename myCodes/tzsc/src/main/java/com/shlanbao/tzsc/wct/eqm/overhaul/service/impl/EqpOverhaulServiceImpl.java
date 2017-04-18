package com.shlanbao.tzsc.wct.eqm.overhaul.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmMaintainDaoI;
import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.mapping.EqmMaintain;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.overhaul.beans.EqmMaintainBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.overhaul.bean.EqpOverhaulBean;
import com.shlanbao.tzsc.wct.eqm.overhaul.service.EqpOverhaulServiceI;


@Service
public class EqpOverhaulServiceImpl extends BaseService implements EqpOverhaulServiceI{
	@Autowired
	protected EqmMaintainDaoI eqmMaintainDao;
	@Override
	public void addEqmMaintain(EqmMaintain eqmMaintain) throws Exception {
		
	}

	@Override
	public void deleteEqmMaintainById(String id) throws Exception {
		eqmMaintainDao.findById(EqmMaintain.class, id).setDel("1");
	}

	@Override
	public DataGrid queryEqmMaintain(EqpOverhaulBean eqmMaintain,PageParams pageParams) throws Exception {
		//、查询计划负责人name和id封装成map
		String sql="SELECT id,name from SYS_USER";
		List l=eqmMaintainDao.queryBySql(sql);
		Map<String, String> userMap=new HashMap<String, String>();
		for (int i = 0; i < l.size(); i++) {
			Object[] o=(Object[]) l.get(i);
			userMap.put(String.valueOf(o[0]), String.valueOf(o[1]));
		}
		String hql="from EqmMaintain o where o.del=0 ";
		String params="";
		//设备id
		if(StringUtil.notNull(eqmMaintain.getEqp_id())){ 
			params+= " and o.eqp = '" +eqmMaintain.getEqp_id()+ "'";
		}
		//设备名称
		if(StringUtil.notNull(eqmMaintain.getEqp_name())){ 
			params+= " and o.eqp.equipmentName like '%" +eqmMaintain.getEqp_id()+ "%'";
		}
		//计划执行日期
		if(StringUtil.notNull(eqmMaintain.getDate_plans())){ 
			params+= " and convert(varchar(23),o.date_plan,23) >= '" +eqmMaintain.getDate_plans()+ "' ";
		}
		//计划执行日期
		if(StringUtil.notNull(eqmMaintain.getAttr1())){ 
			params+= " and convert(varchar(23),o.date_plan,23) <= '" +eqmMaintain.getAttr1()+ "' ";
		}
		if(StringUtil.notNull(eqmMaintain.getBlame_usr_id())){ 
			params+= " and o.blame_usr.id like '" +eqmMaintain.getBlame_usr_id()+ "'";
		}
		List<EqmMaintain> eqmMaintains = eqmMaintainDao.queryByPage(hql.concat(params)+" order by o.date_plan desc ,o.eqp.equipmentCode ", pageParams);
		List<EqmMaintainBean> beans = new ArrayList<EqmMaintainBean>();
		for (EqmMaintain mt : eqmMaintains) {
			EqmMaintainBean bean = beanConvertor.copyProperties(mt, EqmMaintainBean.class);
			if(mt.getDate_plan()!=null){
				bean.setDate_plans(DateUtil.formatDateToString(mt.getDate_plan(), "yyyy-MM-dd"));
			}
			if(mt.getReal_time()!=null){
				bean.setReal_times(DateUtil.formatDateToString(mt.getReal_time(), "yyyy-MM-dd"));
			}
			if(mt.getReal_usr()!=null){
				String sys=mt.getReal_usr();//可能是多个
				String[] usersIds=sys.split(",");
				bean.setReal_usr_id(sys);
				StringBuffer sb=new StringBuffer();
				for (int i = 0; i < usersIds.length; i++) {
					String name=userMap.get(usersIds[i]);
					if(name==null){
						name="未知用户";
					}
					sb.append(name);
					sb.append(",");
				}
				bean.setReal_usr_name(sb.toString().replace(",", "  "));
				
				
//				SysUser sys=mt.getReal_usr();
//				bean.setReal_usr_id(sys.getId());
//				bean.setReal_usr_name(sys.getName());
			}
			if(mt.getBlame_usr()!=null){
				SysUser sys=mt.getBlame_usr();
				bean.setBlame_usr_id(sys.getId());
				bean.setBlame_usr_name(sys.getName());
			}
			if(mt.getEqp()!=null){
				bean.setEqp_id(mt.getEqp().getId());
				bean.setEqp_name(mt.getEqp().getEquipmentName());
			}
			beans.add(bean);
		}
		//查询总条数
		hql="select count(*) " + hql;
		long total=eqmMaintainDao.queryTotal(hql.concat(params));
		return new DataGrid(beans, total);
	}

	@Override
	public EqpOverhaulBean getEqmMaintainById(String id) throws Exception {
		EqmMaintain eqmMaintain=eqmMaintainDao.findById(EqmMaintain.class, id);
		EqpOverhaulBean eqmMaintainBean=BeanConvertor.copyProperties(eqmMaintain, EqpOverhaulBean.class) ;
		
		
		return eqmMaintainBean;
	}

	@Override
	public void updateStatus(String id,String area3,String area6,String uid) throws Exception {
		EqmMaintain eqmMaintain=eqmMaintainDao.findById(EqmMaintain.class, id);
//		eqmMaintain.setReal_usr(new SysUser(uid));
		//eqmMaintain.setReal_usr(uid);
		eqmMaintain.setReal_time(new Date());
		eqmMaintain.setContents(area3);
		eqmMaintain.setNote(area6);
		eqmMaintainDao.saveOrUpdate(eqmMaintain);
		
	}
	/**张璐2015.10.30
	 * 用于添加故障后，跳转回来，按钮才变成已完成
	 * @param id
	 * @param uid
	 * @throws Exception
	 */
	@Override
	public void updateStatusTrouble(String id,String uid) throws Exception {
		EqmMaintain eqmMaintain=eqmMaintainDao.findById(EqmMaintain.class, id);
//		eqmMaintain.setReal_usr(new SysUser(uid));
		eqmMaintain.setReal_usr(uid);
		eqmMaintain.setReal_time(new Date());
		eqmMaintainDao.saveOrUpdate(eqmMaintain);
		
	}
	/**
	 * 添加检修记录
	 */
	@Override
	public void addBatch(List<EqmFixRec> list) {
		// TODO Auto-generated method stub
		eqmMaintainDao.batchInsert(list,EqmFixRec.class);
	}

	/**
	 * 张璐-2015.11.1
	 * 用于查询检修数据，方便修改
	 * @param id
	 * @return
	 */
	@Override
	public EqpOverhaulBean queryJX(String id) {
		StringBuffer sb=new StringBuffer(1000);
		sb.append("SELECT CONTENTS,NOTE,PLACE from EQM_MAINTAIN where id='"+id+"'");
		List<?> list = eqmMaintainDao.queryBySql(sb.toString());
		EqpOverhaulBean bean = new EqpOverhaulBean();
		if(list.size()>0){
			Object[]o=(Object[]) list.get(0);
			bean.setContents(o[0].toString());
			bean.setNote(o[1].toString());
			bean.setPlace(o[2].toString());
			return bean;
		}
		return null;
	}
}
