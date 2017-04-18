package com.shlanbao.tzsc.wct.msg.warn.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MsgConWarnDaoI;
import com.shlanbao.tzsc.base.mapping.MsgConWarn;
import com.shlanbao.tzsc.base.mapping.MsgQmWarn;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.msg.warn.beans.ConWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.beans.QmWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.service.ConWarnServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class ConWarnServiceImpl extends BaseService implements ConWarnServiceI {
	@Autowired
	private MsgConWarnDaoI conwarnDaoI;
	
	@Override
	public List<ConWarnBean> getByEquipmentName(String name) {// and convert(varchar,o.reqtim,120) like '" +eqmFixRecBean.getReqtim()+ "%'
		String date= DateUtil.getNowDateTime("yyyy-MM-dd");
		String sql="select  m.STD,m.VAL,m.CONTENT,m.STS from MSG_CON_WARN m,SCH_WORKORDER s,MD_EQUIPMENT e where  m.DEL='0' and m.STS='1' and m.OID=s.ID and s.EQP=e.ID and e.EQUIPMENT_Name='"+name+"'  and convert(varchar,m.time,120) like '"+date+"%'";//根据时间排序取第一条
		List<Object[]>  warnBeans=(List<Object[]>) conwarnDaoI.queryBySql(sql);
		List<ConWarnBean> warnBeans1=new ArrayList<ConWarnBean>();
		if(warnBeans!=null){
			try {
				for (Object[] objects : warnBeans) {					
					ConWarnBean warnBean=new ConWarnBean();
					warnBean.setStd(Double.valueOf(objects[0].toString()));
					warnBean.setVal(Double.valueOf(objects[1].toString()));
					warnBean.setContent(objects[2].toString());
					warnBean.setSts(Long.valueOf(objects[3].toString()));
					warnBeans1.add(warnBean);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		return warnBeans1;
	}

	@Override
	public DataGrid getConList(ConWarnBean bean, int pageIndex,
			HttpSession session) throws Exception {
		LoginBean  loginbean=  (LoginBean) session.getAttribute("loginInfo");
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  String str = dateFormat.format(new Date());
		String hql="from MsgConWarn c where 1=1 and c.del='0' and c.sts='1'";
		if(StringUtil.notNull(bean.getEquipmentid())&&loginbean!=null){
			hql+=" and c.schWorkorder.mdEquipment.id='"+bean.getEquipmentid()+"' ";
		}else{
			hql+=" and c.schWorkorder.mdEquipment.equipmentName='"+loginbean.getEquipmentName()+"' ";
		}
		if(StringUtil.notNull(bean.getWorkshopid()))
			hql+=" and c.schWorkorder.mdEquipment.mdWorkshop.id='"+bean.getWorkshopid()+"' ";
		if(!StringUtil.notNull(bean.getStim()))
			bean.setStim(str);
		if(!StringUtil.notNull(bean.getEtim())){
			bean.setEtim(str+" 23:59:59");
		}else{
			bean.setEtim(bean.getEtim()+" 23:59:59");			
		}
		hql+=StringUtil.fmtDateBetweenParams("c.time", bean.getStim(), bean.getEtim());
		List<MsgConWarn> msgConWarns=conwarnDaoI.queryByPage(hql, pageIndex, 10);
		List<ConWarnBean> conWarnBeans=new ArrayList<ConWarnBean>();
		if(msgConWarns!=null){
			for (MsgConWarn con : msgConWarns) {
				ConWarnBean warnBean=BeanConvertor.copyProperties(con, ConWarnBean.class);
				if(con.getSchWorkorder().getMdEquipment().getMdWorkshop().getName()!=null){
					warnBean.setWorkordername(con.getSchWorkorder().getMdEquipment().getMdWorkshop().getName());
				}
				if(con.getSchWorkorder().getMdEquipment().getEquipmentName()!=null){
					warnBean.setEquipmentname(con.getSchWorkorder().getMdEquipment().getEquipmentName());
				}
				conWarnBeans.add(warnBean);
			}
		}
		long t=conwarnDaoI.queryTotal("select count(*) "+ hql);
		return new DataGrid(conWarnBeans, t);
	
	}

	@Override
	public void updateConAndStsTwo(String id) {
		conwarnDaoI.findById(MsgConWarn.class, id).setSts(2L);
		
	}

}
