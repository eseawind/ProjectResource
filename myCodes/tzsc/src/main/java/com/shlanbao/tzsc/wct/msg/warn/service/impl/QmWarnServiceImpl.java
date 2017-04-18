package com.shlanbao.tzsc.wct.msg.warn.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MsgQmWarnDaoI;
import com.shlanbao.tzsc.base.mapping.MsgQmWarn;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.msg.warn.beans.ConWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.beans.QmWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.service.QmWarnServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class QmWarnServiceImpl extends BaseService implements QmWarnServiceI {
	@Autowired
	private MsgQmWarnDaoI msgqmwarnDaoI;
	@Override
	public List<QmWarnBean> getAll(String name) {
		
				String date= DateUtil.getNowDateTime("yyyy-MM-dd");
		String sql="select  q.QI,q.VAL,q.CONTENT,q.STS from MSG_QM_WARN q,SCH_WORKORDER s,MD_EQUIPMENT e where  q.DEL='0' and q.STS='1' and q.OID=s.ID and s.EQP=e.ID  and e.EQUIPMENT_Name='"+name+"'  and convert(varchar,q.time,120) like '"+date+"%'";//根据时间排序取第一条
		List<Object[]>  warnBeans=(List<Object[]>) msgqmwarnDaoI.queryBySql(sql);
		List<QmWarnBean> warnBeans1=new ArrayList<QmWarnBean>();
		if(warnBeans!=null){
			try {
				for (Object[] objects : warnBeans) {					
					QmWarnBean warnBean=new QmWarnBean();
					warnBean.setQi(Double.valueOf(objects[0].toString()));
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
	public DataGrid getAll(QmWarnBean qmWarnBean, int pageIndex,HttpServletRequest httpServletRequest) throws Exception {
		LoginBean  bean=  (LoginBean) httpServletRequest.getSession().getAttribute("loginInfo");
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  String str = dateFormat.format(new Date());
		String hql="from MsgQmWarn q where 1=1 and q.del='0' and q.sts='1'";
		if(StringUtil.notNull(qmWarnBean.getEquipmentid())&&bean!=null){
			hql+=" and q.schWorkorder.mdEquipment.id='"+qmWarnBean.getEquipmentid()+"' ";
		}else{
			hql+=" and q.schWorkorder.mdEquipment.equipmentName='"+bean.getEquipmentName()+"' ";
		}
		if(StringUtil.notNull(qmWarnBean.getWorkshopid()))
			hql+=" and q.schWorkorder.mdEquipment.mdWorkshop.id='"+qmWarnBean.getWorkshopid()+"' ";
		if(!StringUtil.notNull(qmWarnBean.getStim()))
			qmWarnBean.setStim(str);
		if(!StringUtil.notNull(qmWarnBean.getEtim())){
			qmWarnBean.setEtim(str+" 23:59:59");
		}else{
			qmWarnBean.setEtim(qmWarnBean.getEtim()+" 23:59:59");			
		}
		hql+=StringUtil.fmtDateBetweenParams("q.time", qmWarnBean.getStim(), qmWarnBean.getEtim());
		List<MsgQmWarn> msgQmWarns=msgqmwarnDaoI.queryByPage(hql, pageIndex, 10);
		List<QmWarnBean> qmWarnBeans=new ArrayList<QmWarnBean>();
		if(msgQmWarns!=null){
			for (MsgQmWarn qm : msgQmWarns) {
				QmWarnBean warnBean=BeanConvertor.copyProperties(qm, QmWarnBean.class);
				if(qm.getSchWorkorder().getMdEquipment().getMdWorkshop().getName()!=null){
					warnBean.setWorkordername(qm.getSchWorkorder().getMdEquipment().getMdWorkshop().getName());
				}
				if(qm.getSchWorkorder().getMdEquipment().getEquipmentName()!=null){
					warnBean.setEquipmentname(qm.getSchWorkorder().getMdEquipment().getEquipmentName());
				}
				qmWarnBeans.add(warnBean);
			}
		}
		long t=msgqmwarnDaoI.queryTotal("select count(*) "+ hql);
		return new DataGrid(qmWarnBeans, t);
	}
	@Override
	public void updateQmStsAndTWO(String id) {
		 msgqmwarnDaoI.findById(MsgQmWarn.class, id).setSts(2L);;
		
	}


}
