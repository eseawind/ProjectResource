package com.shlanbao.tzsc.wct.qm.massonline.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassCheckDaoI;
import com.shlanbao.tzsc.base.dao.QmMassOnlineDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.mapping.QmMassOnline;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.massonline.beans.QmMassOnlineBean;
import com.shlanbao.tzsc.wct.qm.massonline.service.QmMassOnlineService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 在线物理指标自检记录实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-18
 */
@Service
public class QmMassOnlineServiceImpl extends BaseService implements QmMassOnlineService{
	@Autowired
	private QmMassOnlineDaoI qmMassOnlineDao;
	@Autowired
	private QmMassCheckDaoI qmMassCheckDao;
	@Override
	public DataGrid queryList(QmMassOnlineBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmMassOnline o ");
		hql.append("left join fetch o.qmMassCheck mass ");//工单信息
		hql.append("where o.isDelete=0 ");//表示没删除
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProWorkId())){//工单主键ID
			hql.append("and mass.proWorkId = ? ");
			params.add(bean.getProWorkId());
		}
		hql.append("order by o.addUserTime asc ");
		try {
			List<QmMassOnlineBean> list= new ArrayList<QmMassOnlineBean>();
			List<QmMassOnline> rows = qmMassOnlineDao.query(hql.toString(), params);
			for(QmMassOnline queryBean:rows){
				QmMassOnlineBean oneBean = BeanConvertor.copyProperties(queryBean,QmMassOnlineBean.class);
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
	 * @param onlineBean	在线物理指标记录		
	 * @param login			自检人登录信息	
	 * @throws Exception
	 */
	@Override
	public void editBean(QmMassOnlineBean[] checkBean,QmMassOnlineBean[] onlineBean,LoginBean login) throws Exception {
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
				ckBean.setMdEqmentName(checkBean[0].getMdEqmentName());//机台名
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
			for(QmMassOnlineBean bean :onlineBean){//保存 or 更新
				if(null!=bean.getQmOnlineId()&&!"".equals(bean.getQmOnlineId())){//更新
					QmMassOnline updateBean=qmMassOnlineDao.findById(QmMassOnline.class,bean.getQmOnlineId());
					updateBean.setModifyUserId(login.getUserId());//修改人
					updateBean.setModifyTime(new Date());//修改时间
					//updateBean.setAddUserTime();//时间  不更新时间
					//updateBean.setCheckItem(bean.getCheckItem());	//检测项目
					updateBean.setUpperNumber(bean.getUpperNumber());//超上限支数
					updateBean.setLowerNumber(bean.getLowerNumber());//超下限支数
					updateBean.setAvgNumber(bean.getAvgNumber());//平均值
					updateBean.setStandardDiff(bean.getStandardDiff());//标准偏差
					updateBean.setIsError(bean.getIsError());//判断
					updateBean.setShortTime(bean.getShortTime());//页面显示的简短的时间 
					updateBean.setIsAgain(bean.getIsAgain());//复检
					updateBean.setOnlineStep(bean.getOnlineStep());//处理措施
					updateBean.setOnlineRemark(bean.getOnlineRemark());//备注
				}else{//保存
					QmMassOnline addBean=BeanConvertor.copyProperties(bean,QmMassOnline.class);
					addBean.setQmMassCheck(ckBean);
					addBean.setQmOnlineId(UUID.randomUUID().toString());//主键ID
					addBean.setAddUserId(login.getUserId());//新增者
					addBean.setAddUserTime(new Date());//新增时间
					addBean.setModifyUserId(login.getUserId());//修改人
					addBean.setModifyTime(new Date());//修改时间
					addBean.setIsDelete("0");
					qmMassOnlineDao.save(addBean);
					
				}
			}
		}
	}
	@Override
	public void deleteByIds(String ids) throws Exception{
		for (String id : StringUtil.splitToStringList(ids, ",")) {
//			QmMassOnline bean = qmMassOnlineDao.findById(QmMassOnline.class,id);
//			bean.setIsDelete("1");
			qmMassOnlineDao.deleteById(id, QmMassOnline.class);
			
		}
	}

}




