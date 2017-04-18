package com.shlanbao.tzsc.wct.qm.massstem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassCheckDaoI;
import com.shlanbao.tzsc.base.dao.QmMassStemDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.mapping.QmMassStem;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.massstem.beans.QmMassStemBean;
import com.shlanbao.tzsc.wct.qm.massstem.service.QmMassStemService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 丝含梗实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-28
 */
@Service
public class QmMassStemServiceImpl extends BaseService implements QmMassStemService{
	@Autowired
	private QmMassStemDaoI qmMassStemDao;
	@Autowired
	private QmMassCheckDaoI qmMassCheckDao;
	@Override
	public DataGrid queryList(QmMassStemBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmMassStem o ");
		hql.append("left join fetch o.qmMassCheck mass ");//工单信息
		hql.append("where o.isDelete=0 ");//表示没删除
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProWorkId())){//工单主键ID
			hql.append("and mass.proWorkId = ? ");
			params.add(bean.getProWorkId());
		}
		hql.append("order by o.addUserTime asc ");
		try {
			List<QmMassStemBean> list= new ArrayList<QmMassStemBean>();
			List<QmMassStem> rows = qmMassStemDao.query(hql.toString(), params);
			for(QmMassStem queryBean:rows){
				QmMassStemBean oneBean = BeanConvertor.copyProperties(queryBean,QmMassStemBean.class);
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
	public void editBean(QmMassStemBean[] checkBean,QmMassStemBean[] processBean,LoginBean login) throws Exception {
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
				ckBean.setMdMatId(checkBean[0].getMdMatId());//牌号id
				ckBean.setMdDcgName(checkBean[0].getMdDcgName());//挡车工姓名
				ckBean.setMdDcgId(login.getUserId());//挡车工id
				ckBean.setProWorkId(checkBean[0].getProWorkId());//工单主键ID
				boolean flag = qmMassCheckDao.save(ckBean);
				if(flag){
					topKey= ckBean.getQmCheckId();
				}
			}
		}
		if(!"".equals(topKey)){
			for(QmMassStemBean bean :processBean){//保存 or 更新
				if(null!=bean.getQmStemId()&&!"".equals(bean.getQmStemId())){//更新
					QmMassStem updateBean=qmMassStemDao.findById(QmMassStem.class,bean.getQmStemId());
					updateBean.setModifyUserId(login.getUserId());//修改人
					updateBean.setModifyTime(new Date());//修改时间
					updateBean.setShortTime(bean.getShortTime());//页面显示的时间
					updateBean.setCheckCondition(bean.getCheckCondition());//检查情况
					updateBean.setIsError(bean.getIsError());//判断
					updateBean.setStemStep(bean.getStemStep());//处理措施
					updateBean.setStemRemark(bean.getStemRemark());//备注
				}else{//保存
					QmMassStem addBean=BeanConvertor.copyProperties(bean,QmMassStem.class);
					addBean.setQmMassCheck(ckBean);
					addBean.setQmStemId(UUID.randomUUID().toString());//主键ID
					addBean.setAddUserId(login.getUserId());//新增者
					addBean.setAddUserTime(new Date());//新增时间
					addBean.setModifyUserId(login.getUserId());//修改人
					addBean.setModifyTime(new Date());//修改时间
					addBean.setIsDelete("0");
					qmMassStemDao.save(addBean);
					
				}
			}
		}
	}
	@Override
	public void deleteByIds(String ids) throws Exception{
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			//QmMassStem bean = qmMassStemDao.findById(QmMassStem.class,id);
			qmMassStemDao.deleteById(id, QmMassStem.class);
			//bean.setIsDelete("1");
		}
	}

}




