package com.shlanbao.tzsc.wct.qm.massprocess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassCheckDaoI;
import com.shlanbao.tzsc.base.dao.QmMassFilterProcessDaoI;
import com.shlanbao.tzsc.base.dao.QmMassProcessDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.mapping.QmMassFilterProcess;
import com.shlanbao.tzsc.base.mapping.QmMassProcess;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.massprocess.beans.QmMassFilterProcessBean;
import com.shlanbao.tzsc.wct.qm.massprocess.beans.QmMassProcessBean;
import com.shlanbao.tzsc.wct.qm.massprocess.service.QmMassFilterProcessService;
import com.shlanbao.tzsc.wct.qm.massprocess.service.QmMassProcessService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 过程自检记录成型机实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-18
 */
@Service
public class QmMassFilterProcessServiceImpl extends BaseService implements QmMassFilterProcessService{
	@Autowired
	private QmMassFilterProcessDaoI qmMassProcessDao;
	@Autowired
	private QmMassCheckDaoI qmMassCheckDao;
	@Override
	public DataGrid queryList(QmMassFilterProcessBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmMassFilterProcess o ");
		hql.append("left join fetch o.qmMassCheck mass ");//工单信息
		hql.append("where o.isDelete=0 ");//表示没删除
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProWorkId())){//工单主键ID
			hql.append("and mass.proWorkId = ? ");
			params.add(bean.getProWorkId());
		}
		hql.append("order by o.addUserTime asc ");
		try {
			List<QmMassFilterProcessBean> list= new ArrayList<QmMassFilterProcessBean>();
			List<QmMassFilterProcess> rows = qmMassProcessDao.query(hql.toString(), params);
			for(QmMassFilterProcess queryBean:rows){
				QmMassFilterProcessBean oneBean = BeanConvertor.copyProperties(queryBean,QmMassFilterProcessBean.class);
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
	public void editBean(QmMassFilterProcessBean[] checkBean,QmMassFilterProcessBean[] processBean,LoginBean login) throws Exception {
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
			for(QmMassFilterProcessBean bean :processBean){//保存 or 更新
				if(null!=bean.getQmFilterProcessId()&&!"".equals(bean.getQmFilterProcessId())){//更新
					QmMassFilterProcess updateBean=qmMassProcessDao.findById(QmMassFilterProcess.class,bean.getQmFilterProcessId());
					updateBean.setModifyUserId(login.getUserId());//修改人
					updateBean.setModifyTime(new Date());//修改时间
					updateBean.setShortTime(bean.getShortTime());//修改的时间  简短时间
					updateBean.setRunCondition(bean.getRunCondition());//外观质量情况
					updateBean.setWeightFNum(bean.getWeightFNum());//重量
					updateBean.setWeightTNum(bean.getWeightTNum());
					updateBean.setWeightAVG(bean.getWeightAVG());
					updateBean.setPressureFnum(bean.getPressureFnum());//吸阻
					updateBean.setPressureTnum(bean.getPressureTnum());
					updateBean.setPressureAVG(bean.getPressureAVG());
					updateBean.setCircleAVG(bean.getCircleAVG());//圆周
					updateBean.setCircleFnum(bean.getCircleFnum());
					updateBean.setCircleTnum(bean.getCircleTnum());
					updateBean.setHardnessAVG(bean.getHardnessAVG());//硬度
					updateBean.setHardnessFnum(bean.getHardnessFnum());
					updateBean.setHardnessTnum(bean.getHardnessTnum());
					updateBean.setOthers(bean.getOthers());//其他
					
					
					
					updateBean.setIsError(bean.getIsError());//判断
					updateBean.setRunStep(bean.getRunStep());//处理措施
					updateBean.setBadNum(bean.getBadNum());//质量有问题数量
					updateBean.setNumUnit(bean.getNumUnit());//质量有问题数量单位  md_unit 表的code
					updateBean.setRunRemark(bean.getRunRemark());//备注
				}else{//新增
					QmMassFilterProcess addBean=BeanConvertor.copyProperties(bean,QmMassFilterProcess.class);
					addBean.setQmMassCheck(ckBean);
					addBean.setQmFilterProcessId(UUID.randomUUID().toString());//主键ID
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
			QmMassFilterProcess bean = qmMassProcessDao.findById(QmMassFilterProcess.class,id);
			qmMassProcessDao.deleteById(id, QmMassFilterProcess.class);
			//bean.setIsDelete("1");
		}
	}
}




