package com.shlanbao.tzsc.pms.equ.paul.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.EqmPaulDay;
import com.shlanbao.tzsc.base.mapping.EqmProtectRecordBean;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.paul.beans.BatchBean;
import com.shlanbao.tzsc.pms.equ.paul.beans.EqmPaulDayBean;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCalendar;
/** * 
* @ClassName: PaulDayServiceI 
* @Description: 日保养Service 
* @author luo
* @date 2015年7月2日 上午11:15:59 
*
 */
public interface PaulDayServiceI {
	
	/**
	 * 
	* @Title: queryList 
	* @Description: 日保养查询  
	* @param  seachBean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid queryList(EqmPaulDayBean seachBean,PageParams pageParams); 

	/**
	 * 
	* @Title: batchAdd 
	* @Description: 批量添加设备日保养计划  
	* @param  bean    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchAdd(BatchBean bean);
	
	/**
	* @Title: queryListCal 
	* @Description: 轮保日历  
	* @param  date1 开始时间
	* @param  date2 结束时间
	* @return List<EqmWheelCalendar>    返回类型 
	* @throws
	 */
	public List<EqmWheelCalendar> queryListCal(String date1,String date2);
	
	/**
	* @Title: addPaulDayBean 
	* @Description: 添加日保计划  
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addPaulDayBean(EqmPaulDayBean bean);
	
	/**
	* @Title: updatePaulDayBean 
	* @Description: 修改日保计划  
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public int updatePaulDayBean(EqmPaulDayBean bean);
	/**
	 * 
	* @Title: getBeanById 
	* @Description: 根据Id获取mapping Bean  
	* @param  id
	* @return EqmPaulDay    返回类型 
	* @throws
	 */
	public EqmPaulDay getBeanById(String id);
	/**
	* @Title: getBeanByIds 
	* @Description: 根据Id获取bean  
	* @param  id
	* @return EqmPaulDayBean    返回类型 
	* @throws
	 */
	public EqmPaulDayBean getBeanByIds(String id);
	/**
	* @Title: updateBean 
	* @Description: 修改日保养  
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean updateBean(EqmPaulDay bean);
	//批量审核
	public void checkWork(String ids,int staus);
	//修改
	public boolean updatePaulDay(EqmPaulDay bean);

	/**
     *  功能说明：设备日保历史查询-查询
     *  
     *  @param pageParams 分页实体对象
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月13日15:37:54
     *  
     * */
	public DataGrid queryProtectRecordByList(EqmProtectRecordBean bean,
			PageParams pageParams);

	void delete(String id) throws Exception;

	void deletePaul(String ids) throws Exception;
}
