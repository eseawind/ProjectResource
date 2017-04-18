package com.shlanbao.tzsc.wct.qm.qmCheckRecord.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QualityCheckMesDataDaoI;
import com.shlanbao.tzsc.base.dao.QualityCheckMesDataParamsDaoI;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfo;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfoParams;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.qmCheckRecord.beans.QmCheckRecordParamsBean;
import com.shlanbao.tzsc.wct.qm.qmCheckRecord.beans.qmCheckRecordBean;
import com.shlanbao.tzsc.wct.qm.qmCheckRecord.service.QmCheckRecordWctService;

/**
 * wct质检记录
 * @author shisihai
 *
 */
@Service
public class QmCheckRecordWctServiceImpl extends BaseService implements
		QmCheckRecordWctService {
	@Autowired
	private QualityCheckMesDataDaoI checkDao;
	@Autowired
	private QualityCheckMesDataParamsDaoI checkParamsDao;
	/**
	 * 查询质检信息概要 QualityCheckInfo    QualityCheckInfoParams
	 */
	@Override
	public DataGrid getSummaryList(qmCheckRecordBean bean, int pageIndex) {
		List<qmCheckRecordBean> beans=null;
		//查询数据概要条数
		String sql=getSummarySql(bean, 2);
		Long num=checkDao.queryTotal(sql);
		if(num>0){
			StringBuffer sb=new StringBuffer(getSummarySql(bean, 1));
			sb.append(" order by o.checkTime desc ");
			List<QualityCheckInfo> ls=checkDao.queryByPage(sb.toString(), pageIndex, 10);
			try {
				beans=BeanConvertor.copyList(ls, qmCheckRecordBean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(beans, num);
	}
	
	/**
	 * 查询质检信息详细
	 */
	@Override
	public DataGrid getDetailedList(String id) {
		List<QmCheckRecordParamsBean> beans=null;
		String sql=getDaitaledSql(id, 1);
		Long num=checkParamsDao.queryTotal(sql);
		if(num>0){
			List<QualityCheckInfoParams> bs=checkParamsDao.query(getDaitaledSql(id, 2));
		    try {
				beans=BeanConvertor.copyList(bs, QmCheckRecordParamsBean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(beans, num);
	}
	
	
	
	
	/**
	 * 
	 *说明：获取查询质检信息概要sql
	 * @param bean
	 * @param pageIndex
	 * @param type 1 内容  2 总条数
	 * @return
	 * shisihai  
	 * 20152015年12月30日上午10:01:06
	 */
	private String getSummarySql(qmCheckRecordBean bean,int type){
		StringBuffer sb=new StringBuffer();
		if(type==1){//select o.id, o.rollerCode,o.matName,o.scScore,o.scLevel
			sb.append("from QualityCheckInfo o where 1=1 ");
		}else if(type==2){
		    sb.append(" select count(0) from QualityCheckInfo o where 1=1 ");	
		}
		if(StringUtil.notNull(bean.getCheckTime())){
			String addDate=DateUtil.dateAdd("d", 1, DateUtil.formatStringToDate(bean.getCheckTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
			sb.append(" and o.checkTime >='"+bean.getCheckTime()+"'");
			sb.append(" and o.checkTime <='"+addDate+"'");
		}
		if(StringUtil.notNull(bean.getTeamId())){
			sb.append(" and o.teamId >='"+bean.getTeamId()+"'");
		}
		if(StringUtil.notNull(bean.getShiftId())){
			sb.append(" and o.shiftId >='"+bean.getShiftId()+"'");
		}
		return sb.toString();
	}
	
	
	private String getDaitaledSql(String id,int type){
		StringBuffer sb=new StringBuffer();
		if(type==2){
			sb.append(" from QualityCheckInfoParams o where o.qId='"+id+"' and o.scDeduct>0 ");
		}else if(type==1){
			sb.append("select count(1) from QualityCheckInfoParams o where o.qId='"+id+"'and o.scDeduct>0 ");
		}
		return sb.toString();
	}
	
	
	
}
