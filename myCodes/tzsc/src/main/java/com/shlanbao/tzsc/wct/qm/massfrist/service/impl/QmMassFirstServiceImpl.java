package com.shlanbao.tzsc.wct.qm.massfrist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassCheckDaoI;
import com.shlanbao.tzsc.base.dao.QmMassFirstDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.mapping.QmMassFirst;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.massfrist.beans.QmMassFirstBean;
import com.shlanbao.tzsc.wct.qm.massfrist.service.QmMassFirstService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 首检记录控制器实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-16
 * update 根据功能拆分表  by luo 2015年10月20日14:55:18 
 */
@Service
public class QmMassFirstServiceImpl extends BaseService implements QmMassFirstService{
	@Autowired
	private QmMassFirstDaoI qmMassFirstDao;
	@Autowired
	private QmMassCheckDaoI qmMassCheckDao;
	
	//分页查询
	@Override
	public DataGrid queryList(QmMassFirstBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmMassFirst o ");
		hql.append("left join fetch o.qmMassCheck mass ");//工单信息
		hql.append("where o.isDelete=0 ");//表示没删除
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProWorkId())){//工单主键ID
			hql.append("and mass.proWorkId = ? ");
			params.add(bean.getProWorkId());
		}
		if(StringUtil.notNull(bean.getCheckType())){
			hql.append("and o.checkType = ? ");
			params.add(bean.getCheckType());
		}
		//按照用户界面填写的时分排序
		hql.append("order by o.checkTime ");
		try {
			List<QmMassFirstBean> list= new ArrayList<QmMassFirstBean>();
			List<QmMassFirst> rows = qmMassFirstDao.query(hql.toString(), params);
			for(QmMassFirst queryBean:rows){
				QmMassFirstBean oneBean = BeanConvertor.copyProperties(queryBean,QmMassFirstBean.class);
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
	 * 首检记录 新增修改
	 * @param beans
	 * @param info		WCT登录人信息
	 * @param login		自检人登录信息	
	 * @throws Exception
	 */
	@Override
	public void editBean(QmMassFirstBean[] checkBeans,QmMassFirstBean[] firstBean,LoginBean login) throws Exception {
		//根据工单主键ID 查询下 如果没有就  保存 or 更新
		String topKey="";
		QmMassCheck ckBean= new QmMassCheck();
		if(null!=checkBeans&&StringUtil.notNull(checkBeans[0].getProWorkId())){
			QmMassFirstBean checkBean=checkBeans[0];
			//根据工单查找自检记录
			ckBean=getMassCheckByProWorkId(checkBean.getProWorkId());
			//如果自检记录存在
			if(null!=ckBean&&StringUtil.notNull(ckBean.getQmCheckId())){//存在,更新
				topKey = ckBean.getQmCheckId();
			}else{
				//新增质量模块中自检生产明细
				ckBean=saveQmMassCheck(checkBean,login.getUserId());
				if(ckBean!=null){
					topKey=ckBean.getQmCheckId();
				}
			}
		}
		//判断生产明细中ID是否存在
		if(StringUtil.notNull(topKey)){
			for(QmMassFirstBean bean :firstBean){//保存 or 更新
				QmMassFirst bean_new=new QmMassFirst();
				if(StringUtil.notNull(bean.getQmFirstId())){//更新
					bean_new=qmMassFirstDao.findById(QmMassFirst.class,bean.getQmFirstId());
				}else{//保存
					bean_new=BeanConvertor.copyProperties(bean, QmMassFirst.class);
					bean_new.setIsDelete("0");
					bean_new.setQmFirstId(UUID.randomUUID().toString());//主键ID
					bean_new.setAddUserId(login.getUserId());//新增者
					bean_new.setAddUserTime(new Date());//新增时间
					bean_new.setAddUserName(bean.getAddUserName());//创建人Name
				}
				bean_new.setQmMassCheck(ckBean);
				bean_new.setModifyUserId(login.getUserId());//修改人
				bean_new.setModifyTime(new Date());//修改时间
				if(StringUtil.notNull(bean.getCheckType())){
					bean_new.setCheckType(bean.getCheckType());//类型
				}
				bean_new.setFailureNum(bean.getFailureNum());
				bean_new.setFailureUom(bean.getFailureUom());
				bean_new.setCheckTime(bean.getCheckTime());//时间  
				bean_new.setCheckMatter(bean.getCheckMatter());//首检原因
				bean_new.setCheckWeight(bean.getCheckWeight());//重量
				bean_new.setCheckCondition(bean.getCheckCondition());//外观质量情况
				bean_new.setCheckStep(bean.getCheckStep());//处理措施
				qmMassFirstDao.save(bean_new);
			}
		}
	}
	
	//根据工单查找自检生产详细记录
	private QmMassCheck getMassCheckByProWorkId(String id){
		String hql="from QmMassCheck qmc where proWorkId=? ";
		List<String> args = new ArrayList<String>();
		args.add(id);
		QmMassCheck ckBean=qmMassCheckDao.unique(QmMassCheck.class,hql,args);
		return ckBean;
	}
	
	//现在工单自检生产详细记录
	private QmMassCheck saveQmMassCheck(QmMassFirstBean checkBean,String userId) throws Exception{
		QmMassCheck ckBean= new QmMassCheck();
		ckBean=BeanConvertor.copyProperties(checkBean, QmMassCheck.class);
		ckBean.setQmCheckId(UUID.randomUUID().toString());//主键ID 质量自检ID
		ckBean.setAddUserId(userId);//新增者
		ckBean.setAddTime(new Date());//新增时间
		ckBean.setModifyUserId(userId);//修改人
		ckBean.setModifyTime(new Date());//修改时间
		ckBean.setIsDelete("0");
		ckBean.setMdShiftName(checkBean.getMdShiftName());//班次
		ckBean.setMdShiftId(checkBean.getMdShiftId());//班次id
		ckBean.setMdEqmentName(checkBean.getMdEqmentName());//机台号
		ckBean.setMdEqmentId(checkBean.getMdEqmentId());//机台id
		ckBean.setMdMatName(checkBean.getMdMatName());//牌名
		ckBean.setMdMatId(checkBean.getMdMatId());//牌id
		ckBean.setMdDcgName(checkBean.getMdDcgName());//挡车工姓名
		ckBean.setProWorkId(checkBean.getProWorkId());//工单主键ID
		if(ckBean.getBigBox()==null || !StringUtil.notNull(ckBean.getBigBox())){
			ckBean.setBigBox("");
		}
		if(ckBean.getSmallBox()==null || !StringUtil.notNull(ckBean.getSmallBox())){
			ckBean.setSmallBox("");
		}
		boolean flag = qmMassCheckDao.save(ckBean);
		if(flag){
			return ckBean;
		}else{
			return null;
		}
	}
	
	@Override
	public void deleteByIds(String ids,LoginBean login) throws Exception{
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			QmMassFirst bean = qmMassFirstDao.findById(QmMassFirst.class,id);
			bean.setIsDelete("1");
			bean.setDeleteUserId(login.getUserId());
			bean.setDeleteUserTime(new Date());
			
		}
	}
	/**
	 *返回true表示已经有false表示没有
	 */
	@Override
	public Json filledSteelSeal(QmMassFirstBean bean,LoginBean login) {
		Json json=new Json();
		boolean flag=false;
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT SMALLBOX,BIGBOX,QM_CHECK_ID from QM_MASS_CHECK where PRO_WORK_ID= '");
		if(StringUtil.notNull(bean.getProWorkId())){
			sb.append(bean.getProWorkId());
		}
		sb.append("'");
		
		List<Object[]> ls=(List<Object[]>) qmMassCheckDao.queryBySql(sb.toString());
		sb.setLength(0);
		Object[] o=null;
		if(ls!=null&&ls.size()>0){
			o=ls.get(0);
		}
		if(o==null){
				try {
					saveQmMassCheck(bean,login.getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else if(StringUtil.notNull( StringUtil.convertObjToString(o[0])) && StringUtil.notNull(StringUtil.convertObjToString(o[1]))  ){
			flag=true;
		}else if(o[2]!=null){//设置钢印信息
				flag=true;
				QmMassCheck bean2=qmMassCheckDao.findById(QmMassCheck.class, StringUtil.convertObjToString(o[2]));
				if(StringUtil.notNull(bean.getSmallBox().trim())){
					bean2.setSmallBox(bean.getSmallBox().trim());
				}else{
					flag=false;
				}
				if(StringUtil.notNull(bean.getBigBox().trim())){
					bean2.setBigBox(bean.getBigBox().trim());
				}else{
					flag=false;
				}
				
		}
		if(!flag){
			String steelSealMsg=new String();
			sb.append("SELECT a.code,a.name,CONVERT(VARCHAR(23),b.date,23),b.team,c.EQUIPMENT_CODE from MD_MAT a LEFT JOIN SCH_WORKORDER b ON a.id=b.mat LEFT JOIN MD_EQUIPMENT c ON b.EQP=c.ID");
			sb.append(" where b.id= '"+bean.getProWorkId()+"'");
			List<Object[]> list=(List<Object[]>) qmMassCheckDao.queryBySql(sb.toString());
			if(null!=list&&list.size()>0){
				Object[] obj=list.get(0);
				String code=StringUtil.convertObjToString(obj[0]).trim();
				String name=StringUtil.convertObjToString(obj[1]).trim();
				if(code.equals("TSHX")||code.equals("TSSX")||code.equals("TSHT")||code.equals("HDMR")){
					steelSealMsg=getSteelSeal(1, list);
				}else if(code.equals("HTSR")||name.equals("红塔山（软经典）")){
					steelSealMsg=getSteelSeal(2, list);
				}else if(code.equals("BSJP")||name.equals("白沙(精品)")){
					steelSealMsg=getSteelSeal(3, list);
				}
			}
			json.setMsg(steelSealMsg);
		}
		json.setSuccess(flag);
		return json;
	}
	
	/**
	 * 一     哈德门（软）和泰山系列规则：
	 * 
	 * 小盒和条盒规则一致
	 * 
	 * 班次        机台          年           月		日		补位
	 * X	   X      X     X 		xx       0（补位）
	 * 
	 * 一共7位
	 * 班次  甲  乙  丙 用  G H K 代替，月10  11   12  用 A  B   C  表示  其他编码为0~9数字
	 * 
	 * 注意：机台没有考虑超过10的情况
	 *
	 *
	 *
	 * 二    红塔山（软经典）规则：
	 *  
	 *  小盒和条盒规则一致
	 * 
	 * 烟厂		年		月		班次			机台
	 * X		X		XX		 X			 X	
	 * 
	 * 一共6位
	 * 烟厂用   T  表示   ；甲乙丙用 1 2 3 表示 其余编码0~9表示
	 * 
	 * 注意：机台没有考虑超过10的情况
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 三	白沙（精品）规则：
	 * 
	 * 小盒规则：
	 * 
	 * 月		日		机台			班次
	 * XX       XX       XX          X
	 * 
	 * 一共7位
	 * 
	 * 机台两位第一位用  W  表示  班次  甲乙丙用   1  2   3表示；其余用0~9表示
	 * 
	 * 条盒规则：
	 * 
	 * 月		日		厂		机台			班次
	 * XX		xx		x		XX			 X
	 * 
	 * 一共8位
	 * 厂用	W  表示；其余规则同小盒
	 * 
	 * 
	 * 
	 * @param type
	 * @param ls
	 * @return
	 */
	private  String getSteelSeal(int type, List<Object[]> ls) {
		Object[] o = ls.get(0);
		String team = null;
		String eqp = null;
		String date[] = null;
		StringBuffer sb = null;
		// 哈德门软 和泰山系列
		if (type == 1) {
			if (null != ls && ls.size() > 0) {
				sb = new StringBuffer();
				// 生成班次GHK
				if (o[3] != null) {
					team = o[3].toString().trim();
					if (team.equals("1")) {
						team = "G";
					} else if (team.equals("2")) {
						team = "H";
					} else if (team.equals("3")) {
						team = "K";
					}

				}
				sb.append(team);
				// 生成几台信息
				if(o[4]!=null){
					Integer eqpCode=Integer.parseInt(o[4].toString());
					eqpCode=eqpCode%10;
					eqp=String.valueOf(eqpCode);
				}
				sb.append(eqp);
				// 生成日期信息
				if (o[2] != null) {
					date = o[2].toString().split("-");
					if (date.length == 3) {
						String y = date[0];
						String m = date[1];
						String d = date[2];
						// 对年处理
						y = y.substring(y.length() - 1, y.length());
						sb.append(y);
						// 对月处理 10 11 12 用ABC
						Integer m2 = Integer.parseInt(m);
						if (m2 == 10) {
							m = "A";
						} else if (m2 == 11) {
							m = "B";
						} else if (m2 == 12) {
							m = "C";
						} else {
							m = String.valueOf(m2);
						}
						sb.append(m);
						// 日
						sb.append(d);
					}
				}
				// 补位
				sb.append("0");
			}
		}
		// 红塔山软经典
		else if (type == 2) {
			if (null != ls && ls.size() > 0) {
				sb = new StringBuffer();
				// 处理烟厂
				sb.append("T");
				// 处理日期
				if (o[2] != null) {
					date = o[2].toString().split("-");
					if (date.length == 3) {
						String y = date[0];
						String m = date[1];
						String d = date[2];
						// 对年处理
						y = y.substring(y.length() - 1, y.length());
						sb.append(y);
						// 对月处理
						sb.append(m);
					}
				}
				// 处理班次
				if (o[3] != null) {
					team = o[3].toString().trim();
					sb.append(team);
				}
				// 处理机台
				if(o[4]!=null){
					Integer eqpCode=Integer.parseInt(o[4].toString());
					eqpCode=eqpCode%10;
					eqp=String.valueOf(eqpCode);
				}
				sb.append(eqp);
			}
		}else if(type==3){
			//o[0]="HDMR";//code o[1]="哈德门(软)"; o[2]="2015-10-11";//日期 o[3]="1";
			//小盒和条盒不同规则
			//小盒
			//处理日期
			sb=new StringBuffer();
			if (o[2] != null) {
				date = o[2].toString().split("-");
				if (date.length == 3) {
					String m = date[1];
					String d = date[2];
					// 对月处理
					sb.append(m);
					//处理日
					sb.append(d);
				}
			}
			//处理机台
			if(o[4]!=null){
				Integer eqpCode=Integer.parseInt(o[4].toString());
				eqpCode=eqpCode%10;
				eqp=String.valueOf(eqpCode);
			}
			sb.append("W"+eqp);
			//处理班次
			if(o[3]!=null){
				team=o[3].toString();
				sb.append(team);
			}
			//处理条盒
			sb.append(",");
			//处理日期
			if (o[2] != null) {
				date = o[2].toString().split("-");
				if (date.length == 3) {
					String m = date[1];
					String d = date[2];
					// 对月处理
					sb.append(m);
					//处理日
					sb.append(d);
				}
			}
			//处理厂
			sb.append("W");
			//处理机台
			if(o[4]!=null){
				Integer eqpCode=Integer.parseInt(o[4].toString());
				eqpCode=eqpCode%10;
				eqp=String.valueOf(eqpCode);
			}
			sb.append("W"+eqp);
			//处理班次
			if(o[3]!=null){
				team=o[3].toString();
				sb.append(team);
			}
		}
		return sb.toString();
	}
}




