package com.shlanbao.tzsc.wct.qm.massprocess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassCheckDaoI;
import com.shlanbao.tzsc.base.dao.QmMassProcessDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.mapping.QmMassProcess;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.massprocess.beans.QmMassProcessBean;
import com.shlanbao.tzsc.wct.qm.massprocess.service.QmMassProcessService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 过程自检记录实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-18
 */
@Service
public class QmMassProcessServiceImpl extends BaseService implements QmMassProcessService{
	@Autowired
	private QmMassProcessDaoI qmMassProcessDao;
	@Autowired
	private QmMassCheckDaoI qmMassCheckDao;
	@Override
	public DataGrid queryList(QmMassProcessBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmMassProcess o ");
		hql.append("left join fetch o.qmMassCheck mass ");//工单信息
		hql.append("where o.isDelete=0 ");//表示没删除
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProWorkId())){//工单主键ID
			hql.append("and mass.proWorkId = ? ");
			params.add(bean.getProWorkId());
		}
		if(StringUtil.notNull(bean.getProcessType())){
			hql.append(" and o.processType = ? ");
			params.add(bean.getProcessType());
		}
		hql.append("order by o.addUserTime asc ");
		try {
			List<QmMassProcessBean> list= new ArrayList<QmMassProcessBean>();
			List<QmMassProcess> rows = qmMassProcessDao.query(hql.toString(), params);
			for(QmMassProcess queryBean:rows){
				QmMassProcessBean oneBean = BeanConvertor.copyProperties(queryBean,QmMassProcessBean.class);
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
	public void editBean(QmMassProcessBean[] checkBean,QmMassProcessBean[] processBean,LoginBean login) throws Exception {
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
			for(QmMassProcessBean bean :processBean){//保存 or 更新
				if(null!=bean.getQmProcessId()&&!"".equals(bean.getQmProcessId())){//更新
					QmMassProcess updateBean=qmMassProcessDao.findById(QmMassProcess.class,bean.getQmProcessId());
					updateBean.setModifyUserId(login.getUserId());//修改人
					updateBean.setProcessType(bean.getProcessType());//操作类型D挡车工、C操作工 J卷烟机的过程自检
					updateBean.setModifyTime(new Date());//修改时间
					updateBean.setShortTime(bean.getShortTime());//修改的时间  简短时间
					updateBean.setProdPart(bean.getProdPart());//产品段
					updateBean.setRunWeight(bean.getRunWeight());//重量重量
					updateBean.setRunCondition(bean.getRunCondition());//外观质量情况
					updateBean.setIsError(bean.getIsError());//判断
					updateBean.setIsAgain(bean.getIsAgain());//复检
					updateBean.setRunStep(bean.getRunStep());//处理措施
					updateBean.setBadNum(bean.getBadNum());//质量有问题数量
					updateBean.setNumUnit(bean.getNumUnit());//质量有问题数量单位  md_unit 表的code
					updateBean.setRunRemark(bean.getRunRemark());//备注
					updateBean.setWjzjNum(bean.getWjzjNum());//数量
				}else{//保存
					QmMassProcess addBean=BeanConvertor.copyProperties(bean,QmMassProcess.class);
					addBean.setQmMassCheck(ckBean);
					addBean.setQmProcessId(UUID.randomUUID().toString());//主键ID
					addBean.setAddUserId(login.getUserId());//新增者
					addBean.setAddUserTime(new Date());//新增时间
					addBean.setModifyUserId(login.getUserId());//修改人
					addBean.setModifyTime(new Date());//修改时间
					addBean.setProcessType(bean.getProcessType());//D挡车工C操作工
					addBean.setIsDelete("0");
					qmMassProcessDao.save(addBean);
					
				}
			}
		}
	}
	@Override
	public void deleteByIds(String ids) throws Exception{
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			QmMassProcess bean = qmMassProcessDao.findById(QmMassProcess.class,id);
			qmMassProcessDao.deleteById(id, QmMassProcess.class);
			//bean.setIsDelete("1");
		}
	}

}




