package com.lanbao.dws.service.wct.pdStat;

import java.util.List;

import org.springframework.ui.Model;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.pdStat.HisQtyStat;
import com.lanbao.dws.model.wct.pdStat.RealTimeFLConsume;
import com.lanbao.dws.model.wct.pdStat.RealTimeFLConsumeShowBean;
import com.lanbao.dws.model.wct.pdStat.RealTimeQty;

public interface IPdStatService {
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月4日 下午3:03:06 
	* 功能说明 ：查询辅料实时消耗信息
	 */
	PaginationResult<List<RealTimeFLConsumeShowBean>> getRealTimeFLConsumeInfo(RealTimeFLConsume param,Pagination pagination);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月5日 下午4:33:40 
	* 功能说明 ：查询历史消耗
	 */
	PaginationResult<List<RealTimeFLConsumeShowBean>> getHisFLConsumeInfo(RealTimeFLConsume param,Pagination pagination);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 上午10:00:00 
	* 功能说明 ：查询卷包实时产量（初始化）
	 */
	PaginationResult<List<RealTimeQty>> getRealTimeQty(RealTimeQty param,Pagination pagination);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 下午2:20:44 
	* 功能说明 ：查询卷包实时剔除（初始化）
	 */
	PaginationResult<List<RealTimeQty>> getRealTimeBadQty(RealTimeQty param,Pagination pagination);
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 下午3:13:18 
	* 功能说明 ：查询历史产量信息
	 */
	void getHisQtyStst(HisQtyStat param,Pagination pagination,Model model);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 下午9:33:57 
	* 功能说明 ：查询产量历史剔除
	 */
	void getHisBadQtyStst(HisQtyStat param,Pagination pagination,Model model);
}
