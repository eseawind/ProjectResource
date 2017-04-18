package com.shlanbao.tzsc.pms.sch.shiftexchg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SchShiftExchgDaoI;
import com.shlanbao.tzsc.base.dao.SchShiftExchgDetDaoI;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.MdTeam;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.mapping.SchShiftExchg;
import com.shlanbao.tzsc.base.mapping.SchShiftExchgDet;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.shiftexchg.beans.ShiftExchgBean;
import com.shlanbao.tzsc.pms.sch.shiftexchg.beans.ShiftExchgDetBean;
import com.shlanbao.tzsc.pms.sch.shiftexchg.service.ShiftExchgServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class ShiftExchgServiceImpl extends BaseService implements ShiftExchgServiceI{
	@Autowired
	private SchShiftExchgDaoI schShiftExchgDao;
	@Autowired
	private SchShiftExchgDetDaoI schShiftExchgDetDao;
	@SuppressWarnings("null")
	@Override
	public DataGrid getExchgs(ShiftExchgBean shiftExchgBean,
			PageParams pageParams) {
		
		String hql = "from SchShiftExchg o  "
				+ "left join fetch o.hoOrder.mdEquipment hoe "
				+ "left join fetch o.toOrder.mdEquipment toe  "
				+ "left join fetch o.sysUserByHoUser hsu "
				+ "left join fetch o.sysUserByToUser tsu "
				+ "left join fetch o.mdMatByHoMat hmm "
				+ "left join fetch o.mdMatByToMat tmm "
				+ "left join fetch o.mdShiftByHoShift hms "
				+ "left join fetch o.mdShiftByToShift tms "
				+ "left join fetch o.mdTeamByHoTeam hmt "
				+ "left join fetch o.mdTeamByToTeam tmt "
				+ "where o.del=0 ";
		
		String params="";
		params = params.concat(" and hoe.mdWorkshop.code ="+shiftExchgBean.getWorkshop());
		
		params = params.concat(" and o.type ="+shiftExchgBean.getType());
		
		if(StringUtil.notNull(shiftExchgBean.getHoShift()))
			params = params.concat(" and hms.id="+shiftExchgBean.getHoShift());
		if(StringUtil.notNull(shiftExchgBean.getToShift()))
			params = params.concat(" and tms.id="+shiftExchgBean.getToShift());
		
		if(StringUtil.notNull(shiftExchgBean.getHoTeam()))
			params = params.concat(" and hmt.id="+shiftExchgBean.getHoTeam());
		if(StringUtil.notNull(shiftExchgBean.getToTeam()))
			params = params.concat(" and tmt.id="+shiftExchgBean.getToTeam());
		
		if(StringUtil.notNull(shiftExchgBean.getHoMat()))
			params = params.concat(" and hmm.id="+shiftExchgBean.getHoMat());
		if(StringUtil.notNull(shiftExchgBean.getToMat()))
			params = params.concat(" and tmm.id="+shiftExchgBean.getToMat());
		
		if(StringUtil.notNull(shiftExchgBean.getDate()))
			params = params.concat(" and o.date = '"+shiftExchgBean.getDate()+"'");
		
		
		List<SchShiftExchg> rows=schShiftExchgDao.queryByPage(hql.concat(params), pageParams);
		
		List<ShiftExchgBean> outputBeans = new ArrayList<ShiftExchgBean>();
		
		ShiftExchgBean bean = null;
		
		try {
			
			for (SchShiftExchg schShiftExchg : rows) {
				
				bean = BeanConvertor.copyProperties(schShiftExchg, ShiftExchgBean.class);
				
				if(schShiftExchg.getHoOrder()!=null){
					bean.setEquipment(schShiftExchg.getHoOrder().getMdEquipment().getEquipmentName());
					bean.setHoOrderId(schShiftExchg.getHoOrder().getId());
				}
				
				if(schShiftExchg.getMdMatByHoMat()!=null){
					bean.setHoMat(schShiftExchg.getMdMatByHoMat().getName());
				}
				
				if(schShiftExchg.getMdShiftByHoShift()!=null){
					bean.setHoShift(schShiftExchg.getMdShiftByHoShift().getName());
				}
				
				if(schShiftExchg.getMdTeamByHoTeam()!=null){
					bean.setHoTeam(schShiftExchg.getMdTeamByHoTeam().getName());
				}
				
				if(schShiftExchg.getSysUserByHoUser()!=null){
					bean.setHoUser(schShiftExchg.getSysUserByHoUser().getName());
				}
				
				if(schShiftExchg.getMdMatByToMat()!=null){
					bean.setToMat(schShiftExchg.getMdMatByToMat().getName());
				}
				
				if(schShiftExchg.getMdShiftByToShift()!=null){
					bean.setToShift(schShiftExchg.getMdShiftByToShift().getName());
				}
				
				if(schShiftExchg.getMdTeamByToTeam()!=null){
					bean.setToTeam(schShiftExchg.getMdTeamByToTeam().getName());
				}
				
				if(schShiftExchg.getSysUserByToUser()!=null){
					bean.setToUser(schShiftExchg.getSysUserByToUser().getName());
				}
				
				outputBeans.add(bean);
				
				bean = null;
			}
			
			hql= "select count(o) ".concat(hql.replace("fetch", ""));
			
			long total=schShiftExchgDao.queryTotal(hql.concat(params));
			
			return new DataGrid(outputBeans, total);
			
		} catch (Exception e) {
			
			log.error("POVO转换异常", e);
			
		}
		
		return null;
	}
	@Override
	public void addExchg(ShiftExchgBean shiftExchgBean) {
		SchShiftExchg o = new SchShiftExchg();
		o.setDate(DateUtil.formatStringToDate(shiftExchgBean.getDate(), "yyyy-MM-dd"));
		//交班部分
		o.setSysUserByHoUser(new SysUser(shiftExchgBean.getHoUser()));
		
		o.setMdMatByHoMat(new MdMat(shiftExchgBean.getHoMat()));
		
		o.setMdShiftByHoShift(new MdShift(shiftExchgBean.getHoShift()));
		
		o.setMdTeamByHoTeam(new MdTeam(shiftExchgBean.getHoTeam()));
		
		o.setHoTime(DateUtil.formatStringToDate(shiftExchgBean.getHoTime(), "yyyy-MM-dd HH:mm"));
		
		o.setHoOrder(new SchWorkorder(shiftExchgBean.getHoOrderId()));
		//接班部分,可能会先填写交班，后填写接班
		if(StringUtil.notNull(shiftExchgBean.getToOrderId())){
			
			if(StringUtil.notNull(shiftExchgBean.getToUser()))			
			o.setSysUserByToUser(new SysUser(shiftExchgBean.getToUser()));
			
			if(StringUtil.notNull(shiftExchgBean.getToMat()))
			o.setMdMatByToMat(new MdMat(shiftExchgBean.getToMat()));
			
			if(StringUtil.notNull(shiftExchgBean.getToShift()))
			o.setMdShiftByToShift(new MdShift(shiftExchgBean.getToShift()));
			
			if(StringUtil.notNull(shiftExchgBean.getToTeam()))
			o.setMdTeamByToTeam(new MdTeam(shiftExchgBean.getToTeam()));
			
			if(StringUtil.notNull(shiftExchgBean.getToTime()))
			o.setToTime(DateUtil.formatStringToDate(shiftExchgBean.getToTime(), "yyyy-MM-dd HH:mm"));
			
			if(StringUtil.notNull(shiftExchgBean.getToOrderId()))
			o.setToOrder(new SchWorkorder(shiftExchgBean.getToOrderId()));

		}
		
		
		o.setType(shiftExchgBean.getType());
		
		o.setDel(0L);
		
		schShiftExchgDao.save(o);
	}
	@Override
	public void editExchg(ShiftExchgBean shiftExchgBean) {
		
		SchShiftExchg o = schShiftExchgDao.findById(SchShiftExchg.class, shiftExchgBean.getId());
		if(StringUtil.notNull(shiftExchgBean.getHoTime())){
			o.setHoTime(DateUtil.formatStringToDate(shiftExchgBean.getHoTime(), "yyyy-MM-dd HH:mm"));			
		}
		if(StringUtil.notNull(shiftExchgBean.getHoUser())){
			o.setSysUserByHoUser(new SysUser(shiftExchgBean.getHoUser()));			
		}
		if(StringUtil.notNull(shiftExchgBean.getToUser())){			
			o.setSysUserByToUser(new SysUser(shiftExchgBean.getToUser()));
		}
		if(StringUtil.notNull(shiftExchgBean.getToTime())){	
			o.setToTime(DateUtil.formatStringToDate(shiftExchgBean.getToTime(), "yyyy-MM-dd HH:mm"));
		}
	}
	@Override
	public void addExchgDet(ShiftExchgDetBean shiftExchgDetBean) {
		SchShiftExchgDet o = new SchShiftExchgDet();
		
		o.setSchShiftExchg(new SchShiftExchg(shiftExchgDetBean.getExchg()));
		
		o.setMdMat(new MdMat(shiftExchgDetBean.getMat()));
		
		o.setMdUnit(new MdUnit(shiftExchgDetBean.getUnit()));
		
		o.setQty(shiftExchgDetBean.getQty());
		
		o.setDel(0L);
		
		schShiftExchgDetDao.save(o);
	}
	@Override
	public void editExchgDet(ShiftExchgDetBean shiftExchgDetBean) {
		
		SchShiftExchgDet o = schShiftExchgDetDao.findById(SchShiftExchgDet.class, shiftExchgDetBean.getId());
		
		o.setQty(shiftExchgDetBean.getQty());
	}
	@Override
	public void deleteExchg(String id) {
		schShiftExchgDao.updateByParams("update SchShiftExchg o set o.del=1 where o.id=?", id);
	}
	@Override
	public void deleteExchgDet(String id) {
		schShiftExchgDetDao.updateByParams("update SchShiftExchgDet o set o.del=1 where o.id=?", id);
	}
	@Override
	public DataGrid getExchgDetsByExchgId(String id) throws Exception {
		String hql = "from SchShiftExchgDet o  left join fetch o.mdMat mt left join fetch o.mdUnit mu where o.del=0 and o.schShiftExchg.id=?";
		List<SchShiftExchgDet> list = schShiftExchgDetDao.query(hql, id);
		List<ShiftExchgDetBean> rows = new ArrayList<ShiftExchgDetBean>();
		for (SchShiftExchgDet schShiftExchgDet : list) {
			ShiftExchgDetBean bean = BeanConvertor.copyProperties(schShiftExchgDet, ShiftExchgDetBean.class);
			bean.setMat(schShiftExchgDet.getMdMat().getName());
			bean.setUnit(schShiftExchgDet.getMdUnit().getName());
			rows.add(bean);
		}
		return new DataGrid(rows, 0L);
	}
	@Override
	public List<Combobox> getBomsWorkorderId(String workorderId, String exchgId)
			throws Exception {
		String hql = "select o.mdMat from SchWorkorderBom o where o.schWorkorder.id=? and o.mdMat.id not in (select i.mdMat.id from SchShiftExchgDet i where i.schShiftExchg.id=?)";
		return BeanConvertor.copyList(schShiftExchgDetDao.query(List.class, hql,workorderId,exchgId), Combobox.class);
	}
	@Override
	public ShiftExchgBean getExchgById(String id) throws Exception {
		SchShiftExchg schShiftExchg = schShiftExchgDao.findById(SchShiftExchg.class, id);
		ShiftExchgBean bean =BeanConvertor.copyProperties(schShiftExchg,ShiftExchgBean.class);
		if(schShiftExchg.getHoOrder()!=null){
			bean.setEquipment(schShiftExchg.getHoOrder().getMdEquipment().getEquipmentName());
			bean.setHoOrderId(schShiftExchg.getHoOrder().getCode());
		}
		if(schShiftExchg.getToOrder()!=null){
			bean.setToOrderId(schShiftExchg.getToOrder().getCode());
		}
		if(schShiftExchg.getMdMatByHoMat()!=null){
			bean.setHoMat(schShiftExchg.getMdMatByHoMat().getName());
		}
		
		if(schShiftExchg.getMdShiftByHoShift()!=null){
			bean.setHoShift(schShiftExchg.getMdShiftByHoShift().getName());
		}
		
		if(schShiftExchg.getMdTeamByHoTeam()!=null){
			bean.setHoTeam(schShiftExchg.getMdTeamByHoTeam().getName());
		}
		
		if(schShiftExchg.getSysUserByHoUser()!=null){
			bean.setHoUser(schShiftExchg.getSysUserByHoUser().getName());
		}
		
		if(schShiftExchg.getMdMatByToMat()!=null){
			bean.setToMat(schShiftExchg.getMdMatByToMat().getName());
		}
		
		if(schShiftExchg.getMdShiftByToShift()!=null){
			bean.setToShift(schShiftExchg.getMdShiftByToShift().getName());
		}
		
		if(schShiftExchg.getMdTeamByToTeam()!=null){
			bean.setToTeam(schShiftExchg.getMdTeamByToTeam().getName());
		}
		
		if(schShiftExchg.getSysUserByToUser()!=null){
			bean.setToUser(schShiftExchg.getSysUserByToUser().getName());
		}
		
		return bean;
	}
	@Override
	public ShiftExchgDetBean getExchgDetById(String id) throws Exception {
		SchShiftExchgDet det = schShiftExchgDetDao.findById(SchShiftExchgDet.class, id);
		ShiftExchgDetBean bean = new ShiftExchgDetBean();
		bean.setId(det.getId());
		bean.setQty(det.getQty());
		bean.setUnit(det.getMdUnit().getName());
		bean.setMat(det.getMdMat().getName());
		return bean;
	}
	
}
