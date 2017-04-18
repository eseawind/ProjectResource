package com.shlanbao.tzsc.pms.sch.stat.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.pms.md.mat.beans.MatUnitBean;
import com.shlanbao.tzsc.pms.md.unit.beans.UnitBean;
import com.shlanbao.tzsc.pms.sch.manualshift.beans.SchCalendarBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.InputBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.OutputBean;


/**
 * 工单实绩业务接口
 * @author Leejean
 * @create 2014年11月18日下午3:53:04
 */
public interface StatServiceI {
	/**
	 * 查询所有产出数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:17:24
	 * @param outputBean 查询条件
	 * @param pageParams 分页参数
	 * @return
	 */
	public DataGrid getAllOutputs(OutputBean outputBean,PageParams pageParams);
	/**
	 * 根据产出ID查询消耗数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:19:30
	 * @param id 产出ID
	 * @return 
	 * @throws Exception 
	 */
	public DataGrid getAllInputsByOutput(String id) throws Exception;
	/**
	 * 新增产出数据
	 * @author Leejean
	 * @create 2014年12月3日上午9:28:26
	 * @param outputBean
	 * @param flag 1表示人为手动新增 0表示工单运行时初始化产出数据
	 * @return
	 * @throws Exception 
	 */
	public int addOutput(OutputBean outputBean,int flag) throws Exception;
	/**
	 * 编辑产出数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:23:46
	 * @param outputBean
	 * @return
	 */
	public int editOutput(OutputBean outputBean);
	/**
	 * 删除产出数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:23:49
	 * @param id
	 */
	public void deleteOutput(String id);
	/**
	 * 新增消耗数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:23:51
	 * @param inputBean
	 * @return 
	 * @throws Exception 
	 */
	public int addInput(InputBean inputBean) throws Exception;
	/**
	 * 编辑消耗数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:23:53
	 * @param inputBean
	 * @return
	 */
	public int editInput(InputBean inputBean);
	/**
	 * 删除消耗数据
	 * @author Leejean
	 * @create 2014年12月2日下午1:23:58
	 * @param id
	 */
	public void deleteInput(String id);
	/**
	 * 根据ID获取产出数据
	 * @author Leejean
	 * @create 2014年12月2日下午4:39:29
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public OutputBean getOutputById(String id);
	/**
	 * 根据ID获取消耗数据
	 * @author Leejean
	 * @create 2014年12月2日下午4:39:35
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public InputBean getInputById(String id) throws Exception;
	/**
	 * 根据产出ID得到工单，根据工单得到物料清单bom集合，供页面选择辅料新增。
	 * @author Leejean
	 * @create 2014年12月3日下午1:52:35
	 * @param outputId
	 * @return
	 * @throws Exception 
	 */
	public List<Combobox> getBomsWorkorderId(String workorderId) throws Exception;
	/**
	 * 根据工单和物料，获取该获取在bom中的单位
	 * @author Leejean
	 * @create 2014年12月4日上午11:54:50
	 * @param workorder
	 * @param matId
	 * @return
	 */
	public UnitBean getUnitByMatId(String workorder, String matId);
	/**
	 * 根据工单编号和物料编号获得该辅料在BOM表中的单位
	 * @param wokorder
	 * @param matId
	 * @return
	 */
	public MatUnitBean getUnitByworkorder(String wokorder,String matId);
	/**
	 * 反馈数据到MES系统
	 * @author Leejean
	 * @create 2014年12月4日下午3:01:15
	 * @param idOrIds
	 * @param feedbackUser
	 * @return
	 */
	public int sendDataToMES(String idOrIds,String feedbackUser);
	/**
	 * 工单强制终止时，已存生产实绩应删除
	 * @author Leejean
	 * @create 2014年12月5日上午9:01:25
	 * @param workorder 工单号
	 */
	public void deleteOutputByWorkOrder(String workorder);
	/**
	 * 保存所有实时数据
	 * @author Leejean
	 * @create 2015年1月28日上午11:54:53
	 * @param eqpData
	 */
	public void saveAllData(List<EquipmentData> eqpData);
	/**
	 * 保存指定设备实时数据
	 * @author Leejean
	 * @create 2015年1月28日上午11:59:12
	 * @param equipmentCode
	 * @param data 如果data为NULL 则程序会估计equipmentCode获得该设备的实时数据
	 */
	public void saveEquipmentData(String equipmentCode,Object...data);
	
	
	public void saveAllErrorData(List<String[]> list);
	public DataGrid getAllForming(OutputBean outputBean, PageParams pageParams);
	/** 查询包装机设备所有code */
	public Map<String, String> queryByEquiment();
	/** 更新工单dac产量 
	 * @param bean */
	public void supdateSchStatInputOutById(SchCalendarBean bean, Double tSQty, String eqp_id);
	/**
	 * 导出PMS卷包机产量信息
	 * TODO
	 * @param date
	 * @param date2
	 * @return
	 * TRAVLER
	 * 2015年12月2日下午2:48:51
	 */
	public HSSFWorkbook exportQtyInfo(String date, String date2,String shift,String team,String eqp,String eqpType);
	
	/**
	 * 通过传进来的categoryid编号查出机器类编号，再通过机器类编号查出具体机器编号
	 * @param categroyid
	 * @author cmc
	 * 2016年8月24日下午4：27
	 */
	
	public List<MdEquipment> queryEqpMents(int categroyid);
	
}
