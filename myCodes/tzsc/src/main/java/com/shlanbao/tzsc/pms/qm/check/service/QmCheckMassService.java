package com.shlanbao.tzsc.pms.qm.check.service;

import java.util.Map;

import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.pms.qm.check.beans.QmMassDataBean;

/** 
* @ClassName: QmCheckMassService 
* @Description: 自检历史记录汇总查询 
* @author luo 
* @date 2015年10月29日 下午2:23:47 
*
 */
public interface QmCheckMassService {
	//根据条件查询自检历史记录（包装机）
	public QmMassDataBean queryList(QmMassCheck bean) throws Exception;

	//导出包装机所需数据
	public Map<String, Object> exportCheckInfo(QmMassCheck bean);
	
	//导出卷烟机所需数据
	public Map<String, Object> exportRolerCheckInfo(QmMassCheck bean);
	
	
	//根据条件查询自检历史记录(卷烟机)
	public QmMassDataBean queryRolerCheckDataList(QmMassCheck bean) throws Exception;
	
	/**
	 * 张璐-2015.11.3
	 * 获取装封箱机质量自检纪录
	 */
	public QmMassDataBean queryFXJCheckDataList(QmMassCheck bean) throws Exception;
	/**
     * 张璐-2015.11.4
     * 装封箱机导出数据
     */
	public Map<String, Object> exportFXJCheckInfo(QmMassCheck bean);
	
	/**
	 * 滤棒质量自检记录
	 * @author 景孟博
	 * @time 2015年11月04日
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public QmMassDataBean queryFilterCheckDataList(QmMassCheck bean) throws Exception;
	
	/**
     * 张璐-2015-11-5
     * 滤棒质量自检记录导出数据
     */
	public Map<String, Object> exportFilterCheckDataList(QmMassCheck bean);

}
