package com.shlanbao.tzsc.wct.sch.calendar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shlanbao.tzsc.wct.sch.calendar.beans.WctCalendarBean;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;


/**
 *  排班
 * @author Leejean
 * @create 2014年11月25日下午1:19:28
 */
public interface WctCalendarServiceI {
	/**
	 * 查询当月排班
	 * @author Leejean
	 * @create 2014年11月26日下午1:10:08
	 * @param date 月
	 * @param workshop 车间
	 * @return
	 * @throws Exception
	 */
	public List<WctCalendarBean> getCurMonthCalendars(String date,String workshop) throws Exception;
	
	/**
	 * 保存排班信息
	 * @author luther.zhang
	 * @create 20150331
	 * @param workshop 车间
	 * @return
	 * @throws Exception
	 */
	public void saveCurMonthCalendars(String workshop) throws Exception;
	
	/**
	 * 保存手动换班信息
	 * @author luther.zhang
	 * @create 20150331
	 * @param eqpId 设备号
	 * @param proWorkId 工单主键
	 * @return
	 * @throws Exception
	 */
	public void saveManualShift(String type, String eqpId, String workShop,
			String proWorkId, LoginBean login, HttpServletRequest request)
			throws Exception;
	
	/**
	 * 开启 或者 关闭工单
	 * @param eqpId				设备ID
	 * @param workShop			工单运行车间
	 * @param userId		修改人
	 * @param proWorkId			工单主键
	 * @param teamCode			班组
	 * @param shiftCode			班次
	 * @param type				运行工单 S 或 结束工单  E
	 * @param loginName			登录人姓名
	 * @param loginWorkshopCode	登录人工作车间
	 */
	public void editProdWork(String eqpId, String workShop, String userId,
			String proWorkId, String teamCode, String shiftCode, String type,
			String loginName, String loginWorkshopCode)throws Exception;

}
