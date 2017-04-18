package com.shlanbao.tzsc.pms.equ.overhaul.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmMaintainDaoI;
import com.shlanbao.tzsc.base.mapping.EqmMaintain;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.overhaul.beans.EqmMaintainBean;
import com.shlanbao.tzsc.pms.equ.overhaul.service.EqmMaintainServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;


@Service
public class EqmMaintainServiceImpl extends BaseService implements EqmMaintainServiceI{
	
	@Autowired
	protected EqmMaintainDaoI eqmMaintainDao;
	
	@Override
	public void addEqmMaintain(EqmMaintainBean eqmMaintain) throws Exception {
		EqmMaintain bean =new EqmMaintain(); 
		beanConvertor.copyProperties(eqmMaintain, bean);
		//设备编号
		if(StringUtil.notNull(eqmMaintain.getEqp_id())){
			bean.setEqp(new MdEquipment(eqmMaintain.getEqp_id()));
		}
		//实际完成人
		if(StringUtil.notNull(eqmMaintain.getReal_usr_id())){
			bean.setReal_usr(eqmMaintain.getReal_usr_id()+",");
		}
		//计划负责人
		if(StringUtil.notNull(eqmMaintain.getBlame_usr_id())){
			bean.setBlame_usr(new SysUser(eqmMaintain.getBlame_usr_id()));
		}
		//计划执行日期
		if(StringUtil.notNull(eqmMaintain.getDate_plans())){
			bean.setDate_plan(DateUtil.formatStringToDate(eqmMaintain.getDate_plans(), "yyyy-MM-dd"));
		}
		bean.setDel("0");
		eqmMaintainDao.saveOrUpdate(bean);
	}
	@Override
	public void editEqmMaintain(EqmMaintainBean eqmMaintain) throws Exception {
		EqmMaintain bean =eqmMaintainDao.findById(EqmMaintain.class, eqmMaintain.getId()); 
		beanConvertor.copyProperties(eqmMaintain, bean);
		if(StringUtil.notNull(eqmMaintain.getEqp_id())){
			bean.setEqp(new MdEquipment(eqmMaintain.getEqp_id()));
		}
		if(StringUtil.notNull(eqmMaintain.getReal_usr_id())){
			bean.setReal_usr(eqmMaintain.getReal_usr_id()+",");
		}
		if(StringUtil.notNull(eqmMaintain.getBlame_usr_id())){
			bean.setBlame_usr(new SysUser(eqmMaintain.getBlame_usr_id()));
		}
		if(StringUtil.notNull(eqmMaintain.getDate_plans())){
			bean.setDate_plan(DateUtil.formatStringToDate(eqmMaintain.getDate_plans(), "yyyy-MM-dd"));
		}
		bean.setDel("0");
		eqmMaintainDao.saveOrUpdate(bean);
	}
	@Override
	public void deleteEqmMaintainById(String id) throws Exception {
		EqmMaintain bean=eqmMaintainDao.findById(EqmMaintain.class, id);
		bean.setDel("1");
		eqmMaintainDao.saveOrUpdate(bean);
	}

	@Override
	public DataGrid queryEqmMaintain(EqmMaintainBean eqmMaintain,PageParams pageParams) throws Exception {
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
			params+= " and o.eqp.id = '" +eqmMaintain.getEqp_id()+ "'";
		}
		//设备名称
		if(StringUtil.notNull(eqmMaintain.getEqp_name())){ 
			params+= " and o.eqp.equipmentName like '%" +eqmMaintain.getEqp_name()+ "%'";
		}
		//设备名称
		if(StringUtil.notNull(eqmMaintain.getBlame_usr_name())){ 
			params+= " and o.blame_usr.name like '%" +eqmMaintain.getBlame_usr_name()+ "%'";
		}
		//计划执行日期
		if(StringUtil.notNull(eqmMaintain.getStartTime())){ 
			params+= " and convert(varchar(23),o.date_plan,23) >= '" +eqmMaintain.getStartTime()+ "' ";
		}
		//计划执行日期
		if(StringUtil.notNull(eqmMaintain.getEndTime())){ 
			params+= " and convert(varchar(23),o.date_plan,23) <= '" +eqmMaintain.getEndTime()+ "' ";
		}
		//计划执行日期
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
	public EqmMaintainBean getEqmMaintainById(String id) throws Exception {
		//、查询计划负责人name和id封装成map
		String sql="SELECT id,name from SYS_USER";
		List l=eqmMaintainDao.queryBySql(sql);
		Map<String, String> userMap=new HashMap<String, String>();
		for (int i = 0; i < l.size(); i++) {
			Object[] o=(Object[]) l.get(i);
			userMap.put(String.valueOf(o[0]), String.valueOf(o[1]));
		}
		EqmMaintain mt=eqmMaintainDao.findById(EqmMaintain.class, id);
		EqmMaintainBean bean=BeanConvertor.copyProperties(mt, EqmMaintainBean.class) ;
		if(mt.getDate_plan()!=null){
			bean.setDate_plans(DateUtil.formatDateToString(mt.getDate_plan(), "yyyy-MM-dd"));
		}
		if(mt.getReal_time()!=null){
			bean.setReal_times(DateUtil.formatDateToString(mt.getReal_time(), "yyyy-MM-dd"));
		}
		if(mt.getReal_usr()!=null){
			String sys=mt.getReal_usr();
			bean.setReal_usr_id(sys);
			StringBuffer sb=new StringBuffer();
			String[] uIds=sys.split(",");
			for (int i = 0; i < uIds.length; i++) {
				sb.append(userMap.get(uIds[i]));
				sb.append(",");
			}
			bean.setReal_usr_name(sb.toString());
		}
		if(mt.getBlame_usr()!=null){
			SysUser sys=mt.getBlame_usr();
			bean.setBlame_usr_id(sys.getId());
			bean.setBlame_usr_name(sys.getName());
		}
		if(mt.getEqp()!=null){
			bean.setEqp_id(mt.getEqp().getId());
			bean.setEqp_name(mt.getEqp().getEquipmentName());
			bean.setEqp_type(mt.getEqp().getMdEqpType().getName());
		}
		return bean;
	}
	
	
	
	
	/**
	 * 导入excel设备检修历史
	 * shisihai
	 * 2015-10-9
	 * @throws Exception 
	 */
	@Override
	public void inputExeclAndReadWrite(List<EqmMaintainBean> list) throws Exception {
		//去掉list里面关键字段相同的对象
		HashSet<EqmMaintainBean> h  =   new  HashSet<EqmMaintainBean>(list); 
	    list.clear(); 
	    list.addAll(h); 
		//、查询计划负责人name和id封装成map
		String sql="SELECT name,id from SYS_USER";
		List l=eqmMaintainDao.queryBySql(sql);
		Map<String, String> userMap=new HashMap<String, String>();
		for (int i = 0; i < l.size(); i++) {
			Object[] o=(Object[]) l.get(i);
			userMap.put(String.valueOf(o[0]), String.valueOf(o[1]));
		}
		for(int i=0;i<list.size();i++){
			EqmMaintain eqm=new EqmMaintain();
			eqm.setDel("0");
			EqmMaintainBean eqmb=list.get(i);
			if(eqmb==null||eqmb.getEqp_id()==null||eqmb.getDate_plans()==null){
				continue;
			}
			//将责任人name转id
			if(StringUtil.notNull(eqmb.getBlame_usr_id())){
				String name=eqmb.getBlame_usr_id().trim();
				String id=userMap.get(name);
				eqmb.setBlame_usr_id(id);
			}
			if(StringUtil.notNull(eqmb.getReal_usr_id())){
				String[] ids=eqmb.getReal_usr_id().split("、");
				StringBuffer sb=new StringBuffer();
				for (int j = 0; j < ids.length; j++) {
					sb.append(userMap.get(ids[j]));
					sb.append(",");
				}
				eqmb.setReal_usr_id(sb.toString());
			}
//			//判断是否已经存在       机台、部位、日期
			String sql2="SELECT id from EQM_MAINTAIN where del=0 and equ_id='"+eqmb.getEqp_id()+"' AND date_plan='"+eqmb.getDate_plans()+"' AND place='"+eqmb.getPlace()+"' and contents='"+eqmb.getContents()+"'";
			List lt=eqmMaintainDao.queryBySql(sql2);
			if(lt!=null&&lt.size()>0 ){
				continue;
			}else{
					beanConvertor.copyProperties(eqmb, eqm);
					if(StringUtil.notNull(eqmb.getDate_plans())){
						eqm.setDate_plan(new SimpleDateFormat("yyyy-MM-dd").parse(eqmb.getDate_plans()));
					}
					if(StringUtil.notNull(eqmb.getReal_times())){
						eqm.setReal_time(new SimpleDateFormat("yyyy-MM-dd").parse(eqmb.getReal_times()));
					}
					//设备
						if(StringUtil.notNull(eqmb.getEqp_id())){
							MdEquipment e=new MdEquipment();
							e.setId(eqmb.getEqp_id());
							eqm.setEqp(e);
						}
						//负责人
						if(StringUtil.notNull(eqmb.getBlame_usr_id())){
							SysUser s=new SysUser(eqmb.getBlame_usr_id());
							eqm.setBlame_usr(s);
						}
						//完成人
						if(StringUtil.notNull(eqmb.getReal_usr_id())){
							eqm.setReal_usr(eqmb.getReal_usr_id());
						}
						//计划开始时间
						if(StringUtil.notNull(eqmb.getDate_plans())){
								eqm.setDate_plan(new SimpleDateFormat("yyyy-MM-dd").parse(eqmb.getDate_plans()));
						}
						//计划完成时间
						if(StringUtil.notNull(eqmb.getReal_times())){
								eqm.setDate_plan(new SimpleDateFormat("yyyy-MM-dd").parse(eqmb.getReal_times()));
							} 
						eqmMaintainDao.saveOrUpdate(eqm);
					}
				} 
				}
	@Override
	public void updateSpareParts(String ids, String use_num,String all_num) {
		//当总数为0时，存在使用数量不为零情况，这时只对备件更换纪录表操作，备件表数量不改变
		try {
		String[] id=ids.split(",");
		String[] useNum=use_num.split(",");
		String[] allNum=all_num.split(",");
		Integer n=0;//库存数量
		Integer n2=0;//使用数量
		Integer n3=0;//修改后数量		
		for (int i = 0; i < allNum.length; i++) {
			if(useNum[i].trim().equals("0")||allNum[i].trim().equals("0")||allNum[i].trim().equals("")|| useNum[i].trim().equals("")||useNum[i]==null||allNum[i]==null){
				continue;
			}
			n=Integer.parseInt(allNum[i]);
			n2=Integer.parseInt(useNum[i]);
			n3=n-n2;
			//数量不足时设置库存为0
			if(n3<0){
				n3=0;
			}
			String sql="UPDATE COS_SPARE_PARTS SET attr2="+n3+" where id='"+id[i]+"'";
			eqmMaintainDao.updateInfo(sql, null);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
/**
 * 查询设备code和NAME
 * 2015.9.13-张璐
 * */	
@Override
public Object[] loadToRhBuWeiCode(String equipmentId) {
	String sql = "SELECT me.EQUIPMENT_CODE,m.NAME FROM MD_EQUIPMENT me LEFT JOIN MD_EQP_TYPE m ON me.EQP_TYPE_ID = m.id WHERE me.id ='"+equipmentId+"'";
	Object[] o=(Object[]) eqmMaintainDao.queryBySql(sql, null).get(0);
	return  o; 
}

}
