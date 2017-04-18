package com.shlanbao.tzsc.wct.sch.stat.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageView;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.MatterInfo;
import com.shlanbao.tzsc.wct.sch.stat.beans.EqpRuntime;
import com.shlanbao.tzsc.wct.sch.stat.beans.HisOutBean;
import com.shlanbao.tzsc.wct.sch.stat.beans.InputBean;
import com.shlanbao.tzsc.wct.sch.stat.beans.InputPageModel;
import com.shlanbao.tzsc.wct.sch.stat.beans.OutputBean;
/**
 * 生产统计业务接口
 * @author Leejean
 * @create 2014年11月18日下午3:53:04
 */
public interface WctStatServiceI {
	/**
	 * 分页查询所有产出数据
	 * @author Leejean
	 * @create 2014年12月16日下午2:24:13
	 * @param equipentCode
	 * @param curpage
	 * @param pagesize
	 * @return
	 */
	public PageView<OutputBean> getAllOutputs(String equipmentCode,int curpage,int pagesize,String shiftId,String teamId,String date1,String date2);
	/**
	 * 根据产出ID查询消耗数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:19:30
	 * @param id 产出ID
	 * @return 
	 * @throws Exception 
	 */
	public List<InputBean> getAllInputsByOutput(String id) throws Exception;
	/**
	 * 历史产量
	 * @author Leejean
	 * 日期，班次，班组，产量，产量单位
	 */
	public HisOutBean getHisOutData(HisOutBean hisOutBean,int curpage,int pagesize);
	
	/**
	 * 历史合计产量
	 * @author cmc
	 * 日期，班次，班组，产量，产量单位
	 */
	public HisOutBean getHisTotal(HisOutBean hisOutBean,int curpage,int pagesize);
	/**
	 * 初始化机台【产量】监控机台信息
	 * @author Leejean
	 * @create 2014年12月23日上午10:27:30
	 * @param type 工单类型编号
	 * 	case 1:
			type = "卷烟机工单";
			break;
		case 2:
			type = "包装机工单";
			break;
		case 3:
			type = "封箱机工单";
			break;
		case 4:
			type = "成型机工单";
			break;
	 * @return
	 */
	public List<EqpRuntime> initOutDataPage(HttpSession session,Long type);
	
	/**
	 * 根据设备查询 运行的工单
	 * @param type 工单类型编号
	 * @return
	 */
	public EqpRuntime initOutDataByEqp(EqpRuntime bean);
	/**
	 * 初始化设备实时消耗工单信息，辅料标准单耗信息
	 * @author Leejean
	 * @create 2014年12月23日下午1:40:54
	 * @param type
	 * @return
	 */
	public List<InputPageModel> initEqpInputInfos(Long type);
	
	/**
	 * 初始化设备历史消耗工单信息，辅料标准单耗信息
	 * @author Leejean
	 * @create 2014年12月23日下午1:40:54
	 * @param type
	 * @return
	 */
	public DataGrid initEqpInputInfos2(Long type,HisOutBean his,int pageSize);
	/**
	 * 获取卷烟机产量数据
	 * @author Leejean
	 * @return
	 */
	public List<EqpRuntime> getRollerOutData();
	/**
	 * 获取包装机产量数据
	 * @author Leejean
	 * @return
	 */
	public List<EqpRuntime> getPackerOutData();
	/**
	 * 获取封箱机产量数据
	 * @author Leejean
	 * @return
	 */
	public List<EqpRuntime> getBoxerOutData();
	/**
	 * 获取成型机产量数据
	 * @author Leejean
	 * @return
	 */
	public List<EqpRuntime> getFilterOutData();
	/**
	 * 获取卷烟机剔除数据
	 * @author Leejean
	 * @return
	 */
	public List<EqpRuntime> getRollerBadData();
	/**
	 * 获取包装机剔除数据
	 * @author Leejean
	 * @return
	 */
	public List<EqpRuntime> getPackerBadData();
	/**
	 * 获取成型机剔除数据
	 * @author Leejean
	 * @return
	 */
	public List<EqpRuntime> getFilterBadData();
	/**
	 * 获得包装机实时消耗
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	public List<InputPageModel> getPackerInputData();
	/**
	 * 获得卷烟机实时消耗
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	public List<InputPageModel> getRollerInputData();
	/**
	 * 获得成型机实时消耗
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	public List<InputPageModel> getFilterInputData();
	/**
	 * WCT查询机台工段产耗信息
	 * TODO
	 * @param runtime
	 * @param teamId
	 * @return
	 * TRAVLER
	 * 2015年11月27日下午3:23:23
	 */
	public List<MatterInfo> SearchEqpDailyInfo(String runtime, String teamId);
	

}
