package com.shlanbao.tzsc.pms.md.eqp.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqp.beans.EquipmentsBean;
import com.shlanbao.tzsc.pms.md.matparam.beans.MatParamBean;

/**
 * 设备主数据业务类
 * @author liuligong 
 * @Time 2014/11/5 10:46
 */
public interface EquipmentsServiceI {
	/**
	 * 设备主数据新增
	 * @param equBean 设备主数据Bean对象
	 * @throws Exception
	 */
	public void addEqu(MdEquipment equBean) throws Exception;
	
	/** 
	 * 查询设备主数据
	 * @param equBean  设备主数据Bean对象
	 * @param pageParams 查询参数
	 * @return
	 */
	public DataGrid queryEqu(EquipmentsBean equBean,PageParams pageParams) throws Exception;
	
	/**
	 * 查询当前选中行数据
	 * @param id 当前选中行ID
	 * @return 当前选中行bean
	 */
	public EquipmentsBean getEquById(String id) throws Exception;
	
	/**
	 * 删除设备主数据信息
	 */
	public void deleteEqu(String id) throws Exception; 
	
	/**
	 * 获取所有设备型号
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Combobox> getAllEqpType(String id) throws Exception;
	
	/**
	 * 获取所有设备主数据
	 * @return 
	 * @throws Exception
	 */
	public List<EquipmentsBean> queryAllEquipments() throws Exception;
	/**
	 * 
	 * @author Leejean
	 * @create 2014年12月25日下午4:52:22
	 * @return
	 */
	public List<EquipmentsBean> queryAllEqpsForComboBox();
	/**
	 *  查询所有单位 combobox
	 * @author luther.zhang
	 * @create 2015.01.06
	 * @return
	 */
	public List<EquipmentsBean> queryAllPackersForComboBox(int eType) throws Exception;
	
	public List<MatParamBean> getAllMdMatParamData();
	

	public List<Map<String, String>> querySysEqpType();
	
	/**
	 * excel导出
	 * 2015.9.9--张璐 
	 * @param baoCJBean
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook ExportExcelJBPP2(EquipmentsBean baoCJBean) throws Exception;
	/**
	 * 批量导入设备主数据excel
	 * @param list
	 */
	public void inputExeclAndReadWrite(List<MdEquipment> list);
	
	
}
