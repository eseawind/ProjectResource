package com.shlanbao.tzsc.pms.qm.avgrange.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmSelfCheckStripDaoI;
import com.shlanbao.tzsc.base.mapping.QmSelfCheckStrip;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.pms.qm.avgrange.beans.AvgRangeBean;
import com.shlanbao.tzsc.pms.qm.avgrange.beans.AvgRangeQueryBean;
import com.shlanbao.tzsc.pms.qm.avgrange.service.AvgRangeService;
import com.shlanbao.tzsc.pms.qm.passrate.beans.PassRateQueryBean;
/**
 * 平均值-极差控制图分析实现类
 * <li>@author luther.zhang
 * <li>@create 2015-03-02
 */
@Service
public class AvgRangeServiceImpl extends BaseService implements AvgRangeService{
	@Autowired
	private QmSelfCheckStripDaoI qmSelfCheckStripDao;
	@Override
	public DataGrid queryList(AvgRangeQueryBean bean, PageParams pageParams) throws Exception{
		StringBuffer hql=new StringBuffer();
		hql.append("from QmSelfCheckStrip o ");
		hql.append("left join fetch o.sysUser suser ");//用户信息
		hql.append("left join fetch o.schWorkorder work ");//工单信息
		hql.append("left join fetch work.mdUnit dw ");//单位信息
		hql.append("left join fetch work.mdEquipment sb ");//设备信息
		hql.append("left join fetch work.mdTeam bz ");//班组信息
		hql.append("left join fetch work.mdShift bc ");//班次信息
		hql.append("left join fetch work.mdMat ph ");//牌号信息	
		hql.append("left join fetch sb.mdWorkshop cj ");//车间信息
		hql.append("where o.del=0 ");//表示没删除
		
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getTime())){//自检时间
			hql.append("and o.time >=? ");
			bean.setTime(bean.getTime()+" 00:00:00");
			Date dateStr = DateUtil.strToDate(bean.getTime(), "yyyy-MM-dd HH:mm:ss");
			params.add(dateStr);
		}
		if(StringUtil.notNull(bean.getEndTime())){//自检时间
			hql.append("and o.time <=? ");
			bean.setEndTime(bean.getEndTime()+" 23:59:59");
			Date dateStr = DateUtil.strToDate(bean.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			params.add(dateStr);
		}
		
		if(StringUtil.notNull(bean.getMdTeamId())){//mdTeamId 班组
			hql.append("and bz.id =? ");
			params.add(bean.getMdTeamId());
		}
		if(StringUtil.notNull(bean.getMdWorkshopId())){//车间
			hql.append("and cj.id =? ");
			params.add(bean.getMdWorkshopId());
		}
		if(StringUtil.notNull(bean.getMdShiftId())){//班次
			hql.append("and bc.id = ? ");
			params.add(bean.getMdShiftId());
		}
		if(StringUtil.notNull(bean.getMdEquipmentId())){//设备
			hql.append("and sb.id = ? ");
			params.add(bean.getMdEquipmentId());
		}
		try {
			long total=qmSelfCheckStripDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			
			List<PassRateQueryBean> list= new ArrayList<PassRateQueryBean>();
			if(total>0){
				hql.append("order by o.time asc ");
				List<QmSelfCheckStrip> rows = qmSelfCheckStripDao.queryByPage(hql.toString(), pageParams.getPage(), pageParams.getRows(), params);
				for(QmSelfCheckStrip strip:rows){
					PassRateQueryBean oneBean = BeanConvertor.copyProperties(strip, PassRateQueryBean.class);
					
					if(null!=strip.getSysUser()){//用户信息
						oneBean.setuId(strip.getSysUser().getId());//用户ID
						oneBean.setuName(strip.getSysUser().getName());//用户名称
					}
					if(null!=strip.getSchWorkorder()&&null!=strip.getSchWorkorder().getMdUnit()){//单位信息
						oneBean.setMdUnitId(strip.getSchWorkorder().getMdUnit().getId());//单位ID
						oneBean.setMdMatName(strip.getSchWorkorder().getMdUnit().getName());//单位名称
					}
					if(null!=strip.getSchWorkorder()&&null!=strip.getSchWorkorder().getMdTeam()){//班组信息
						oneBean.setMdTeamId(strip.getSchWorkorder().getMdTeam().getId());//班组ID
						oneBean.setMdTeamName(strip.getSchWorkorder().getMdTeam().getName());//班组名称
					}
					if(null!=strip.getSchWorkorder()&&null!=strip.getSchWorkorder().getMdShift()){//班次信息
						oneBean.setMdShiftId(strip.getSchWorkorder().getMdShift().getId());//班次ID
						oneBean.setMdShiftName(strip.getSchWorkorder().getMdShift().getName());//班次名称
					}
					if(null!=strip.getSchWorkorder()&&null!=strip.getSchWorkorder().getMdEquipment()){//设备信息
						oneBean.setMdEquipmentId(strip.getSchWorkorder().getMdEquipment().getId());//设备ID
						oneBean.setMdEquipmentName(strip.getSchWorkorder().getMdEquipment().getEquipmentName());//设备名称
						if(null!=strip.getSchWorkorder().getMdEquipment().getMdWorkshop()){//车间信息
							oneBean.setMdWorkshopId(strip.getSchWorkorder().getMdEquipment().getMdWorkshop().getId());
							oneBean.setMdWorkshopName(strip.getSchWorkorder().getMdEquipment().getMdWorkshop().getName());
						}
					}
					if(null!=strip.getSchWorkorder()&&null!=strip.getSchWorkorder().getMdMat()){//牌号信息
						oneBean.setMdMatId(strip.getSchWorkorder().getMdMat().getId());//牌号ID
						oneBean.setMdMatName(strip.getSchWorkorder().getMdMat().getName());//牌号名称
					} 
					list.add(oneBean);
				}
			}
			return new DataGrid(list, total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}
	
	
	/**
	 * 查询统计信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public AvgRangeBean getAvgRangeBean(AvgRangeQueryBean bean) throws Exception{
		StringBuffer hql=new StringBuffer();
		
		//如果没有选定了机台号 根据 设备排序
		hql.append("select CHECK_FLAG,Convert(varchar(10),o.time,120) tempValue, sum(coalesce(o.sb,0)) sb , sum(coalesce(o.ps,0)) ps ");//如果选定了机台号 根据 日期排序
		hql.append("from QM_SELF_CHECK_STRIP o ");
		hql.append("left join SCH_WORKORDER work on o.OID=work.ID ");//工单信息
		hql.append("left join MD_EQUIPMENT  sb on work.EQP=sb.id ");
		hql.append("where  o.DEL='0' ");
		
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getTime())){//自检时间
			hql.append("and o.time >=? ");
			bean.setTime(bean.getTime()+" 00:00:00");
			Date dateStr = DateUtil.strToDate(bean.getTime(), "yyyy-MM-dd HH:mm:ss");
			params.add(dateStr);
		}
		if(StringUtil.notNull(bean.getEndTime())){//自检时间
			hql.append("and o.time <=? ");
			bean.setEndTime(bean.getEndTime()+" 23:59:59");
			Date dateStr = DateUtil.strToDate(bean.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			params.add(dateStr);
		}
		if(StringUtil.notNull(bean.getMdEquipmentId())){//设备
			hql.append("and sb.id = ? ");
			params.add(bean.getMdEquipmentId());
		}
		hql.append("group by CHECK_FLAG,Convert(varchar(10),o.time,120) "); 
		
		List<?> list = qmSelfCheckStripDao.queryBySql(hql.toString(), params);//.queryObjectArray(hql.toString(), params);
		AvgRangeBean queryBean = null;
		String type ="0";
		if(null==list){
			list = new ArrayList<Object[]>();
		}
		String[] lables=new String[list.size()];
		Double[] values1 = new Double[list.size()];
		Double[] values2 = new Double[list.size()];
		for (int i=0;i<list.size();i++){
			//如果未选定了机台，则X轴机台号，Y轴质量指标合格率平均值
			Object[] arr=(Object[]) list.get(i);
			String time = ObjectUtils.toString(arr[1]);
			lables[i]=ObjectUtils.toString(arr[0])+"-"+time+"<br>";
			
			String value1 = ObjectUtils.toString(arr[2]);
			Double dv1 =Double.parseDouble(value1)/100;
			values1[i]=MathUtil.roundHalfUp(dv1, 2);
			
			String value2 = ObjectUtils.toString(arr[3]);
			Double dv2 =Double.parseDouble(value2)/100;
			values2[i]=MathUtil.roundHalfUp(dv2, 2);
		}
		queryBean = new AvgRangeBean(lables,type,values1,values2);
		
		return queryBean;
	}

}




