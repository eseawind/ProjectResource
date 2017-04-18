package com.shlanbao.tzsc.wct.qm.massexcipient.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassCheckDaoI;
import com.shlanbao.tzsc.base.dao.QmMassExcipientDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.mapping.QmMassExcipient;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.massexcipient.bean.QmMassExcipientBean;
import com.shlanbao.tzsc.wct.qm.massexcipient.service.QmMassExcipientService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 辅料、自检自控装置确认记录实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-27
 */
@Service
public class QmMassExcipientServiceImpl extends BaseService implements QmMassExcipientService{
	@Autowired
	private QmMassExcipientDaoI qmMassExcipientDao;
	@Autowired
	private QmMassCheckDaoI qmMassCheckDao;
	@Override
	public DataGrid queryList(QmMassExcipientBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmMassExcipient o ");
		hql.append("left join fetch o.qmMassCheck mass ");//工单信息
		hql.append("where o.isDelete=0 ");//表示没删除
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProWorkId())){//工单主键ID
			hql.append("and mass.proWorkId = ? ");
			params.add(bean.getProWorkId());
		}
		hql.append("order by o.orderNumber asc ");
		try {
			List<QmMassExcipientBean> list= new ArrayList<QmMassExcipientBean>();
			List<QmMassExcipient> rows = qmMassExcipientDao.query(hql.toString(), params);
			for(QmMassExcipient queryBean:rows){
				QmMassExcipientBean oneBean = BeanConvertor.copyProperties(queryBean,QmMassExcipientBean.class);
				list.add(oneBean);
			}
			long total = Long.valueOf(list.size());
			return new DataGrid(list,total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}
	/**
	 * 新增修改
	 * @param checkBean		卷烟机质量自检记录主表
	 * @param processBean	过程自检记录		
	 * @param login			自检人登录信息	
	 * @throws Exception
	 */
	@Override
	public void editBean(QmMassExcipientBean[] checkBean,QmMassExcipientBean[] excipientBean,LoginBean login) throws Exception {
		//根据工单主键ID 查询下 如果没有就  保存 or 更新
		String topKey="";
		QmMassCheck ckBean= new QmMassCheck();
		if(null!=checkBean&&null!=checkBean[0].getProWorkId()&&!"".equals(checkBean[0].getProWorkId())){
			String hql="from QmMassCheck qmc where proWorkId=? ";
			List<String> args = new ArrayList<String>();
			args.add(checkBean[0].getProWorkId());
			ckBean=qmMassCheckDao.unique(QmMassCheck.class,hql,args);//.findById(QmMassCheck.class,checkBean[0].getProWorkId());//更新
			if(null!=ckBean&&!"".equals(ckBean.getQmCheckId())){//存在,更新
				topKey = ckBean.getQmCheckId();
				ckBean.setModifyUserId(login.getUserId());//修改人
				ckBean.setModifyTime(new Date());//修改时间
				ckBean.setMdShiftName(checkBean[0].getMdShiftName());//班次
				ckBean.setMdEqmentName(checkBean[0].getMdEqmentName());//机台号
				ckBean.setMdMatName(checkBean[0].getMdMatName());//牌名
				ckBean.setMdDcgName(checkBean[0].getMdDcgName());//挡车工姓名
			}else{
				ckBean=BeanConvertor.copyProperties(checkBean, QmMassCheck.class);
				ckBean.setQmCheckId(UUID.randomUUID().toString());//主键ID 质量自检ID
				ckBean.setAddUserId(login.getUserId());//新增者
				ckBean.setAddTime(new Date());//新增时间
				ckBean.setModifyUserId(login.getUserId());//修改人
				ckBean.setModifyTime(new Date());//修改时间
				ckBean.setIsDelete("0");
				ckBean.setMdShiftName(checkBean[0].getMdShiftName());//班次
				ckBean.setMdShiftId(checkBean[0].getMdShiftId());//班次id
				ckBean.setMdEqmentName(checkBean[0].getMdEqmentName());//机台号
				ckBean.setMdEqmentId(checkBean[0].getMdEqmentId());//机台id
				ckBean.setMdMatName(checkBean[0].getMdMatName());//牌名
				ckBean.setMdMatId(checkBean[0].getMdMatId());//牌id
				ckBean.setMdDcgName(checkBean[0].getMdDcgName());//挡车工姓名
				ckBean.setProWorkId(checkBean[0].getProWorkId());//工单主键ID
				boolean flag = qmMassCheckDao.save(ckBean);
				if(flag){
					topKey= ckBean.getQmCheckId();
				}
			}
		}
		if(!"".equals(topKey)){
			for(QmMassExcipientBean bean :excipientBean){//保存 or 更新
				if(null!=bean.getQmExcipientId()&&!"".equals(bean.getQmExcipientId())){//更新
					QmMassExcipient updateBean=qmMassExcipientDao.findById(QmMassExcipient.class,bean.getQmExcipientId());
					updateBean.setModifyUserId(login.getUserId());//修改人
					updateBean.setModifyTime(new Date());//修改时间
					//updateBean.setAddUserTime();//时间  不更新时间
					updateBean.setCheckItem(bean.getCheckItem());//检测项目
					updateBean.setCheckRate(bean.getCheckRate());//检查频率
					updateBean.setIsError(bean.getIsError());//检查判断
					updateBean.setSubStandard(bean.getSubStandard());//不合格情况
					updateBean.setOrderNumber(bean.getOrderNumber());
					updateBean.setCheckTime(bean.getCheckTime());//检查时间
				}else{//保存
					QmMassExcipient addBean=BeanConvertor.copyProperties(bean,QmMassExcipient.class);
					addBean.setQmMassCheck(ckBean);
					addBean.setQmExcipientId(UUID.randomUUID().toString());//主键ID
					addBean.setAddUserId(login.getUserId());//新增者
					addBean.setAddUserTime(new Date());//新增时间
					addBean.setModifyUserId(login.getUserId());//修改人
					addBean.setModifyTime(new Date());//修改时间
					addBean.setIsDelete("0");
					qmMassExcipientDao.save(addBean);
					
				}
			}
		}
	}
	@Override
	public void deleteByIds(String ids) throws Exception{
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			QmMassExcipient bean = qmMassExcipientDao.findById(QmMassExcipient.class,id);
			bean.setIsDelete("1");
		}
	}

}




