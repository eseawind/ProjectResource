package com.shlanbao.tzsc.pms.md.eqptype.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdEqpTypeChildDaoI;
import com.shlanbao.tzsc.base.mapping.MdEqpTypeChild;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeChildBean;
import com.shlanbao.tzsc.pms.md.eqptype.service.MdEqpTypeChildServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
 * @author luther.zhang
 *
 */
@Service
public class MdEqpTypeChildServiceImpl extends BaseService implements MdEqpTypeChildServiceI {
	@Autowired
	private MdEqpTypeChildDaoI mdEqpTypeChildDao;
	@Autowired
	private LoadComboboxServiceI  comboboxServiceI;
	
	@Override
	public DataGrid queryMdTypeChild(MdEqpTypeChildBean mdTypeBean, PageParams pageParams) throws Exception {
		String countSql ="select count(temp1.id) len  "; 
		String rowNumberSql ="select row_number() over(ORDER BY CODE DESC) as rownumber,";
		String titleSql = "select temp1.id as SECID,temp1.CODE,temp1.NAME,temp2.id,temp2.SEC_PID,temp2.MET_PID,TYPE ";
		
		StringBuffer temp1=new StringBuffer();
		temp1.append(titleSql).append(" from ( ")
		.append(rowNumberSql).append(" * from SYS_EQP_CATEGORY where del=0 ");
		//根据名称 模糊查询
		if(null!=mdTypeBean.getQueryName()&&!"".equals(mdTypeBean.getQueryName())){
			temp1.append("and name like '%"+mdTypeBean.getQueryName().trim()+"%' ");
		}
		temp1.append(")temp1 left join ( ")
		.append("	    select * from MD_EQP_TYPE_child where del=0 ");
		if(null!=mdTypeBean.getQueryId()&&!"".equals(mdTypeBean.getQueryId())){
			temp1.append("and MET_PID='"+mdTypeBean.getQueryId().trim()+"' ");
		}
		if(null!=mdTypeBean.getQueryType()&&!"".equals(mdTypeBean.getQueryType())){
			temp1.append("and TYPE='"+mdTypeBean.getQueryType()+"' ");
		}
		temp1.append("	)temp2 on temp1.id = temp2.sec_pid ");
		
		String lastSql = temp1.toString().replaceAll(titleSql,countSql);
			   lastSql = lastSql.replaceAll(rowNumberSql, "select ");
		List<?> list = mdEqpTypeChildDao.queryBySql(lastSql, null);//只会有一条记录
		long total= 0;//总记录数
		List<MdEqpTypeChildBean> lastList = new ArrayList<MdEqpTypeChildBean>();
		if(null!=list&&list.size()>0){
			Object arr=(Object) list.get(0);//返回一个对象，而不是 一组数组
			total =Long.parseLong(ObjectUtils.toString(arr));
		}
		if(total>0){
			temp1.append(" where  rownumber>"+(pageParams.getPage()-1)*pageParams.getRows()
					+" and rownumber<="+(pageParams.getPage()*pageParams.getRows())+"  ");//分页
			lastSql = temp1.toString();
			//System.out.println(lastSql);
			list = mdEqpTypeChildDao.queryBySql(lastSql, null);
			if(null!=list&&list.size()>0){
				MdEqpTypeChildBean childBean = null;
				for(int i=0;i<list.size();i++){
					childBean = new MdEqpTypeChildBean();
					Object[] arr=(Object[]) list.get(i);
					childBean.setSecId(ObjectUtils.toString(arr[0]));
					childBean.setSecCode(ObjectUtils.toString(arr[1]));
					childBean.setSecName(ObjectUtils.toString(arr[2]));
					String metcId = ObjectUtils.toString(arr[3]);
					childBean.setMetcId(metcId);//这个是 绑定表中的主键
					childBean.setIsCheck("false");
					if(null!=metcId&&metcId.length()>0){
						childBean.setIsCheck("true");
					}
					childBean.setSecPid(ObjectUtils.toString(arr[4]));
					childBean.setMetPid(ObjectUtils.toString(arr[5]));
					childBean.setType(ObjectUtils.toString(arr[6]));
					lastList.add(childBean);
				}
			}
		}
		return new DataGrid(lastList, total);
	}
	
	/**
	 * 新增 或 删除 绑定的 轮保大类项
	 * @param type
	 * @param beans
	 * @throws Exception
	 */
	public void saveMdTypeChild(String type,MdEqpTypeChildBean[] beans) throws Exception{
		if(null!=beans&&beans.length>0){
			for(MdEqpTypeChildBean bean : beans){
				String key = bean.getMetcId();
				if(null!=key&&!"".equals(key)){//存在就更新DEL=0
					//更新
					StringBuffer temp = new StringBuffer();
					temp.append("update MD_EQP_TYPE_CHILD set DEL=? where ID=? ");
					List<Object> params = new ArrayList<Object>();
					params.add(bean.getDel());
					params.add(key);
					mdEqpTypeChildDao.updateInfo(temp.toString(),params);
				}else{//新增一条记录
					//是不是 曾经保存过，如果是 更新下
					StringBuffer temp = new StringBuffer();
					List<Object> params = new ArrayList<Object>();
					//直接更新
					temp.append("update MD_EQP_TYPE_CHILD set DEL=? where SEC_PID=? and MET_PID=? and TYPE=? ");
					params.add("0");params.add(bean.getSecPid());
					params.add(bean.getMetPid());params.add(bean.getType());
					int count = mdEqpTypeChildDao.updateBySql(temp.toString(),params);
					if(count<=0){
						String id = UUID.randomUUID().toString();
						temp = new StringBuffer();
						temp.append("insert into MD_EQP_TYPE_CHILD(")
						.append("ID,DEL,SEC_PID,MET_PID,TYPE) values(?,?,?,?,?) ");
						params = new ArrayList<Object>();
						params.add(id);
						params.add(bean.getDel());
						params.add(bean.getSecPid());
						params.add(bean.getMetPid());
						params.add(bean.getType());
						mdEqpTypeChildDao.updateInfo(temp.toString(),params);
					}
				}
			}
		}
	}
	/**
	 * 取消绑定的 轮保大类项
	 * @param type
	 * @param beans
	 * @throws Exception
	 */
	public void editMdTypeChild(String type,MdEqpTypeChildBean[] beans) throws Exception{
		if(null!=beans&&beans.length>0){
			for(MdEqpTypeChildBean bean : beans){
				String key = bean.getMetcId();//更新
				StringBuffer temp = new StringBuffer();
				temp.append("update MD_EQP_TYPE_CHILD set DEL=? where ID=? ");
				List<Object> params = new ArrayList<Object>();
				params.add(bean.getDel());
				params.add(key);
				mdEqpTypeChildDao.updateInfo(temp.toString(),params);
			}
		}
	}
	/**
	 * 
	* @Title: getPaulbyEqpType 
	* @Description: 查询设备规程  
	* @param  设备类型Id
	* @param  类型， 保养，润滑，点检 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public List<MdEqpTypeChildBean> getPaulbyEqpType(String eqpTypeId,String type){
		StringBuffer sql=new StringBuffer();
		sql.append("select sec.ID,sec.NAME from MD_EQP_TYPE met left join MD_EQP_TYPE_CHILD mc on met.ID=mc.MET_PID ");
		sql.append("left join SYS_EQP_CATEGORY sec on  mc.SEC_PID=sec.ID ");
		sql.append("where 1=1 and sec.del=0 and met.del=0  and mc.del=0");
		if(StringUtil.notNull(eqpTypeId)){
			//sql.append("and met.id = (select eqp_type_id from MD_EQUIPMENT where id='"+eqpTypeId+"') ");
			sql.append("and met.id = '"+eqpTypeId+"' ");
		}
		if(StringUtil.notNull(type)){
			sql.append("and mc.TYPE='"+type+"' ");
		}
		List<?> list = mdEqpTypeChildDao.queryBySql(sql.toString());//只会有一条记录
		List<MdEqpTypeChildBean> returnList=new ArrayList<MdEqpTypeChildBean>();
		for(int i=0;i<list.size();i++){
			MdEqpTypeChildBean childBean = new MdEqpTypeChildBean();
			Object[] arr=(Object[]) list.get(i);
			childBean.setSecId(ObjectUtils.toString(arr[0]));
			childBean.setSecName(ObjectUtils.toString(arr[1]));
			returnList.add(childBean);
		}
		return returnList;
	}
	/**
	 * 
	* @Title: getPaulbyEqpType 
	* @Description: 查询设备规程  
	* @param  设备类型Id
	* @param  类型， 保养，润滑，点检 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public List<MdEqpTypeChildBean> getPaulbyEqp(String eqpId,String type){
		StringBuffer sql=new StringBuffer();
		sql.append("select sec.ID,sec.NAME from MD_EQP_TYPE met left join MD_EQP_TYPE_CHILD mc on met.ID=mc.MET_PID ");
		sql.append("left join SYS_EQP_CATEGORY sec on  mc.SEC_PID=sec.ID ");
		sql.append("where 1=1 and sec.del=0 and met.del=0  and mc.del=0");
		if(StringUtil.notNull(eqpId)){
			sql.append("and met.id = (select eqp_type_id from MD_EQUIPMENT where id='"+eqpId+"') ");
		}
		if(StringUtil.notNull(type)){
			sql.append("and mc.TYPE='"+type+"' ");
		}
		List<?> list = mdEqpTypeChildDao.queryBySql(sql.toString());//只会有一条记录
		List<MdEqpTypeChildBean> returnList=new ArrayList<MdEqpTypeChildBean>();
		for(int i=0;i<list.size();i++){
			MdEqpTypeChildBean childBean = new MdEqpTypeChildBean();
			Object[] arr=(Object[]) list.get(i);
			childBean.setSecId(ObjectUtils.toString(arr[0]));
			childBean.setSecName(ObjectUtils.toString(arr[1]));
			returnList.add(childBean);
		}
		return returnList;
	}
	/**
	 * 根据设备ID查询对应的轮保规则
	 * @return
	 */
	@Override
	public List<MdEqpTypeChildBean> queryEqpTypeChildByEqp(String eqpid,String type)throws Exception{
		
		StringBuffer sql=new StringBuffer();
		sql.append("select sec.id,sec.CODE, sec.NAME,metc.SEC_PID,metc.MET_PID,metc.TYPE,metc.ID as METC_ID ");
		sql.append("from MD_EQP_TYPE_child metc left join SYS_EQP_CATEGORY sec on sec.id = metc.sec_pid ");
		sql.append("left join  MD_EQUIPMENT me on me.EQP_TYPE_ID = metc.MET_PID ");
		sql.append("where metc.del=0 ");	
		if(null!=eqpid&&!"".equals(eqpid)){
			sql.append("and me.id='"+eqpid+"' ");
		}
		if(null!=type&&!"".equals(type)){
			sql.append("and metc.TYPE='"+type+"' ");
		}
		List<?> list = mdEqpTypeChildDao.queryBySql(sql.toString());
		List<MdEqpTypeChildBean> lastList = new ArrayList<MdEqpTypeChildBean>();
		if(null!=list&&list.size()>0){
			MdEqpTypeChildBean childBean = null;
			for(int i=0;i<list.size();i++){
				childBean = new MdEqpTypeChildBean();
				Object[] arr=(Object[]) list.get(i);
				childBean.setSecId(ObjectUtils.toString(arr[0]));
				childBean.setSecCode(ObjectUtils.toString(arr[1]));
				childBean.setSecName(ObjectUtils.toString(arr[2]));
				childBean.setSecPid(ObjectUtils.toString(arr[3]));
				childBean.setMetPid(ObjectUtils.toString(arr[4]));
				childBean.setType(ObjectUtils.toString(arr[5]));
				childBean.setMetcId(ObjectUtils.toString(arr[6]));
				lastList.add(childBean);
			}
		}
		return lastList;
	}

}
