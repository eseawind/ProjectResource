package com.shlanbao.tzsc.wct.qm.massprocess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassCheckDaoI;
import com.shlanbao.tzsc.base.dao.QmMassGlycerineProcessDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.mapping.QmMassGlycerineProcess;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.massprocess.beans.QmMassGlycerineProcessBean;
import com.shlanbao.tzsc.wct.qm.massprocess.service.QmMassGlycerineProcessService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 过程自检记录成型机实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-18
 */
@Service
public class QmMassGlycerineProcessServiceImpl extends BaseService implements QmMassGlycerineProcessService{
	@Autowired
	private QmMassGlycerineProcessDaoI qmMassProcessDao;
	@Autowired
	private QmMassCheckDaoI qmMassCheckDao;
	@Override
	public DataGrid queryList(QmMassGlycerineProcessBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmMassGlycerineProcess o ");
		hql.append("left join fetch o.qmMassCheck mass ");//工单信息
		hql.append("where o.isDelete=0 ");//表示没删除
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProWorkId())){//工单主键ID
			hql.append("and mass.proWorkId = ? ");
			params.add(bean.getProWorkId());
		}
		hql.append("order by o.addUserTime asc ");
		try {
			List<QmMassGlycerineProcessBean> list= new ArrayList<QmMassGlycerineProcessBean>();
			List<QmMassGlycerineProcess> rows = qmMassProcessDao.query(hql.toString(), params);
			for(QmMassGlycerineProcess queryBean:rows){
				QmMassGlycerineProcessBean oneBean = BeanConvertor.copyProperties(queryBean,QmMassGlycerineProcessBean.class);
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
	 * @param checkBean		成型机质量自检记录主表
	 * @param processBean	过程自检记录		
	 * @param login			自检人登录信息	
	 * @throws Exception
	 */
	@Override
	public void editBean(QmMassGlycerineProcessBean[] checkBean,QmMassGlycerineProcessBean[] processBean,LoginBean login) throws Exception {
		//根据工单主键ID 查询下 如果没有就  保存 or 更新
		String topKey="";
		QmMassCheck ckBean= new QmMassCheck();
		if(null!=checkBean&&null!=checkBean[0].getProWorkId()&&!"".equals(checkBean[0].getProWorkId())){
			String hql="from QmMassCheck qmc where proWorkId=? ";
			List<String> args = new ArrayList<String>();
			args.add(checkBean[0].getProWorkId());
			ckBean=qmMassCheckDao.unique(QmMassCheck.class,hql,args);//.findById(QmMassCheck.class,checkBean[0].getProWorkId());//更新
			if(null!=ckBean&&!"".equals(ckBean.getQmCheckId())){//更新操作
				topKey = ckBean.getQmCheckId();
				ckBean.setModifyUserId(login.getUserId());//修改人
				ckBean.setModifyTime(new Date());//修改时间
				ckBean.setMdShiftName(checkBean[0].getMdShiftName());//班次
				ckBean.setMdEqmentName(checkBean[0].getMdEqmentName());//机台号
				ckBean.setMdMatName(checkBean[0].getMdMatName());//牌名
				ckBean.setMdDcgName(checkBean[0].getMdDcgName());//创建人
			}else{//新建自检记录
				ckBean=BeanConvertor.copyProperties(checkBean, QmMassCheck.class);
				ckBean.setQmCheckId(UUID.randomUUID().toString());//主键ID 质量自检ID
				ckBean.setAddUserId(login.getUserId());//新增者
				ckBean.setAddTime(new Date());//新增时间
				ckBean.setModifyUserId(login.getUserId());//修改人
				ckBean.setModifyTime(new Date());//修改时间
				ckBean.setIsDelete("0");
				ckBean.setMdShiftName(checkBean[0].getMdShiftName());//班次
				ckBean.setMdShiftId(checkBean[0].getMdShiftId());//班次id
				ckBean.setMdEqmentName(checkBean[0].getMdEqmentName());//机台名
				ckBean.setMdEqmentId(checkBean[0].getMdEqmentId());//设备id
				ckBean.setMdMatName(checkBean[0].getMdMatName());//牌名
				ckBean.setMdMatId(checkBean[0].getMdMatId());//牌id
				ckBean.setMdDcgName(checkBean[0].getMdDcgName());//创建人姓名
				ckBean.setMdDcgId(login.getUserId());
				ckBean.setProWorkId(checkBean[0].getProWorkId());//工单主键ID
				boolean flag = qmMassCheckDao.save(ckBean);
				if(flag){
					topKey= ckBean.getQmCheckId();
				}
			}
		}
		if(!"".equals(topKey)){
			for(QmMassGlycerineProcessBean bean :processBean){//保存 or 更新
				if(null!=bean.getQmProcessId()&&!"".equals(bean.getQmProcessId())){//更新
					QmMassGlycerineProcess updateBean=qmMassProcessDao.findById(QmMassGlycerineProcess.class,bean.getQmProcessId());
					updateBean.setModifyUserId(login.getUserId());//修改人
					updateBean.setModifyTime(new Date());//修改时间
					updateBean.setDarWeight(bean.getDarWeight());
					updateBean.setWetWeight(bean.getWetWeight());
					double d=bean.getDarWeight();
					double w=bean.getWetWeight();
					double avg=MathUtil.roundHalfUp((w-d)/w, 3);
					updateBean.setUseWeight(avg);
				}else{//新增
					QmMassGlycerineProcess addBean=BeanConvertor.copyProperties(bean,QmMassGlycerineProcess.class);
					addBean.setQmMassCheck(ckBean);
					//确认数据的准确性
					double d=bean.getDarWeight();
					double w=bean.getWetWeight();
					double avg=MathUtil.roundHalfUp((w-d)/w, 3);
					addBean.setUseWeight(avg);
					
					addBean.setQmProcessId(UUID.randomUUID().toString());//主键ID
					addBean.setAddUserId(login.getUserId());//新增者
					addBean.setAddUserTime(new Date());//新增时间
					addBean.setModifyUserId(login.getUserId());//修改人
					addBean.setModifyTime(new Date());//修改时间
					addBean.setIsDelete("0");
					qmMassProcessDao.save(addBean);
					
				}
			}
		}
	}
	@Override
	public void deleteByIds(String ids) throws Exception{
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			QmMassGlycerineProcess bean = qmMassProcessDao.findById(QmMassGlycerineProcess.class,id);
			qmMassProcessDao.deleteById(id, QmMassGlycerineProcess.class);
			//bean.setIsDelete("1");
		}
	}
}




