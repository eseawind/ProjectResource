package com.shlanbao.tzsc.pms.sch.statjbcj.service;




import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shlanbao.tzsc.base.mapping.QmChangeShiftInfo;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.ChangeShiftData;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.JuanBaoCJBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.MatterInfo;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.ShiftCheckBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.WorkShopShiftBean;

/**
 * 根据生产统计表查询得到
 */
public interface JueBaoSerivceI {
	
	/**
	 * 卷包车间生产统计
	 */
	public DataGrid getJuanYanList(WorkShopShiftBean baoCJBean) throws Exception;
	
	
	/**
	 * 成型车间生产统计
	 */
	public DataGrid getChengXingList(WorkShopShiftBean baoCJBean) throws Exception;
	
	
	/**
	 * 如果type=1卷烟车间卷烟机综合生产统计 type=2机组
	 */
	public DataGrid getJueYanList(JuanBaoCJBean baoCJBean,int type) throws Exception;
	/**
	 *  如果type=1卷烟车间成型机综合生产统计 type=2机组
	 */
	public DataGrid getChengXingList(JuanBaoCJBean baoCJBean,int type) throws Exception;
	/**
	 *  如果type=1卷烟车间包装机综合生产统计 type=2机组
	 */
	public DataGrid getBaoZhuangList(JuanBaoCJBean baoCJBean,int type) throws Exception;
	
	/**
	 * 卷包车间班组生产统计
	 */
	public  DataGrid getJuanBaoBanZuList(WorkShopShiftBean shopShiftBean) throws Exception;
	
	/**
	 * 成型车间班组生产统计
	 */
	public  DataGrid getChengXingBanZuList(WorkShopShiftBean shopShiftBean) throws Exception;
	/**
	 * 卷包车间产品生产统计
	 */
	public  DataGrid getJuanBaoChanPinList(WorkShopShiftBean shopShiftBean) throws Exception;
	/**
	 * 成型车间产品生产统计
	 */
	public  DataGrid getChengXingChanPinList(WorkShopShiftBean shopShiftBean) throws Exception;
	/**
	 * ExportExcel導出成型班組
	 */
	public HSSFWorkbook ExportExcelCXBZ(WorkShopShiftBean shopShiftBean)throws Exception;
	/**
	 * ExportExcel導出成型品牌
	 */
	public HSSFWorkbook ExportExcelCXPP(WorkShopShiftBean shopShiftBean)throws Exception;
	/**
	 * ExportExcel導出成型车间
	 */
	public HSSFWorkbook ExportExcelCX(WorkShopShiftBean shopShiftBean)throws Exception;
	/**
	 * ExportExcel導出成型机组
	 */
	public HSSFWorkbook ExportExcelCXJZ(JuanBaoCJBean baoCJBean,int type)throws Exception;
	/**
	 * ExportExcel導出卷烟车间
	 */
	public HSSFWorkbook ExportExcelJB(WorkShopShiftBean baoCJBean)throws Exception;
	/**
	 * ExportExcel導出卷烟车间（ 修改后）
	 */
	public HSSFWorkbook ExportExcelJB2(WorkShopShiftBean baoCJBean)throws Exception;
	/**
	 * ExportExcel導出卷烟车间有效作业率（ 修改后）
	 */
	public void ExportExcelJBEffic(WorkShopShiftBean baoCJBean,String newName)throws Exception;
	/**
	 * ExportExcel導出卷烟班组
	 */
	public HSSFWorkbook ExportExcelJBBZ(WorkShopShiftBean baoCJBean)throws Exception;
	/**
	 * ExportExcel導出卷烟班组(修改后代码)
	 */
	public HSSFWorkbook ExportExcelJBBZ2(WorkShopShiftBean baoCJBean)throws Exception;
	/**
	 * ExportExcel導出卷烟品牌
	 */
	public HSSFWorkbook ExportExcelJBPP(WorkShopShiftBean baoCJBean)throws Exception;
	/**
	 * ExportExcel導出卷烟品牌(修改后的代码)
	 */
	public HSSFWorkbook ExportExcelJBPP2(WorkShopShiftBean baoCJBean)throws Exception;
	/**
	 * ExportExcel導出卷烟机组
	 */
	public HSSFWorkbook ExportExcelJBJZ(JuanBaoCJBean baoCJBean,int type)throws Exception;
	/**
	 * ExportExcel導出卷烟机组（修改后）
	 */
	public HSSFWorkbook ExportExcelJBJZ2(JuanBaoCJBean baoCJBean,int type)throws Exception;
	/**
	 * ExportExcel導出包装机组
	 */
	public HSSFWorkbook ExportExcelBZJZ(JuanBaoCJBean baoCJBean,int type)throws Exception;

	/**
	 * ExportExcel導出包装机组（修改后代码）
	 */
	public HSSFWorkbook ExportExcelBZJZ2(JuanBaoCJBean baoCJBean,int type)throws Exception;
	/**
	 * PMS查询交接班辅料/产量信息
	 * shisihai
	 * @param stim
	 * @param etim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @return
	 */
	public DataGrid queryChangeShiftMatterInfo(String stim,String etim,String mdTeamId,String mdShiftId);
	/**
	 * PMS查询交接班其他料查询
	 * shisihai
	 * @param stim
	 * @param etim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @return
	 */
	public DataGrid queryChangeShiftOtherMatterInfo(String stim,String etim,String mdTeamId,String mdShiftId);
	
	/**
	 * PMS保存交接班数据修改
	 * shisihai
	 * @param bean
	 */
	public void saveMatterInfoChange(MatterInfo bean);
	/**
	 * 卷包车间产耗统计导出
	 * 张璐：2015.11.16
	 * @param matterInfoBean
	 * @param response
	 * @throws Exception
	 */
	public HSSFWorkbook excelHandAndReceiveTeam(String stim,String etim,String mdTeamId,String mdShiftId)throws Exception;
	
	/**
	 * 查询交接班其他料
	 * zhanglu:2015.11.16
	 * @param stim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @param response
	 * @throws Exception
	 */
	public HSSFWorkbook otherMatterHandAndReceiveInfo(String stim,String etim,String mdTeamId,String mdShiftId)throws Exception;

	/**
	 * 其他辅料数据修改（绑定在封箱机上）
	 * @param bean
	 */
	public void saveOtherMatterInfoChange(MatterInfo bean);
  
	/*
    * 成型机交接班数据查询
    * shisihai
    * 
    */
	public DataGrid queryFilterMatterInfo(String stim, String etim, String mdTeamId, String mdShiftId);

	/**
	 * 
	 * TODO成型机交接班数据修改
	 * @param bean
	 * TRAVLER
	 * 2015年11月19日下午2:31:22
	 */
	public void saveFilterMatterInfoChange(MatterInfo bean);

	/**
	 * 成型机交接班数据导出
	 * @param stim
	 * @param etim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @return
	 */

	public HSSFWorkbook filterMatterInfo(String stim, String etim, String mdTeamId, String mdShiftId);

	/**
	 * 
	 * TODO 查询交接班产量信息
	 * @param time
	 * @return
	 * TRAVLER
	 * 2015年11月23日下午7:00:50
	 */
	public ChangeShiftData queryQtyDataOfChangeShift(String time);

	/**
	 * 修改交接班产量数据
	 * TODO
	 * @param firstBean
	 * TRAVLER
	 * 2015年11月20日下午2:38:27
	 */
	public void updateQtyInfo(ChangeShiftData[] firstBean,ChangeShiftData[] packer,ChangeShiftData[] filter);

	/**
	 * 查询月绩效产耗信息
	 * TODO
	 * @param stim
	 * @param etime
	 * @return
	 * TRAVLER
	 * 2015年11月21日下午9:07:25
	 */
	public DataGrid queryMonthQtyData(String stim, String etime);

	/**
	 * 查询工段产耗信息
	 * TODO
	 * @param stim
	 * @param etime
	 * @return
	 * TRAVLER
	 * 2015年11月30日上午8:28:36
	 */
	public MatterInfo queryDayQtyData(String stim, String etime);

	/**
	 * 导出工段日报表
	 * TODO
	 * @param stim
	 * @param etime
	 * @return
	 * TRAVLER
	 * 2015年12月1日上午8:14:04
	 */
	public HSSFWorkbook exportDayQtyData(String stim, String etime);

	/**
	 * PMS导出月绩效表
	 * 
	 * TODO
	 * @param stim
	 * @param etime
	 * @return
	 * TRAVLER
	 * 2015年12月1日下午5:47:16
	 */
	public HSSFWorkbook exportMonthQtyData(String stim, String etime);
	
	/**
	 * 查询所有班内考核信息
	 * @return
	 */
	public DataGrid queryShiftCheck(ShiftCheckBean shiftcheckbean,PageParams pageParams);
	
	/**
	 * 根据班内考核编号获得详细信息
	 */
	public DataGrid queryShiftCheckInfo(String id);
	
	/**
	 * 为班内考核添加详细信息
	 * @param qmchangshiftinfo
	 * @return
	 */
	public boolean addchangshiftinfo(QmChangeShiftInfo qmchangshiftinfo); 

}
