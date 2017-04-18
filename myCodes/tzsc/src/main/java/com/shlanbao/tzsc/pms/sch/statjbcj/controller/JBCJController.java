package com.shlanbao.tzsc.pms.sch.statjbcj.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.shlanbao.tzsc.base.mapping.QmChangeShiftInfo;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.stat.service.StatServiceI;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.ChangeShiftData;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.JuanBaoCJBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.MatterInfo;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.ShiftCheckBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.WorkShopShiftBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.service.JueBaoSerivceI;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.JSONUtil;
@Controller
@RequestMapping("/pms/sch/statjbcj")
public class JBCJController extends BeanConvertor {
	@Autowired
	private JueBaoSerivceI baoSerivceI;
	@Autowired
	private StatServiceI statService;
	@Autowired
	private WorkOrderServiceI workOrderService;
	@Autowired
	private JueBaoSerivceI juebaoservice;

	/**
	 *卷包车间生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllJuanBao")
	public DataGrid getAllJuanBao(WorkShopShiftBean shopShiftBean,PageParams pageParams) throws Exception{
		return baoSerivceI.getJuanYanList(shopShiftBean);
	}
	/**
	 *成型车间生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllChengXing")
	public DataGrid getAllChengXing(WorkShopShiftBean shopShiftBean,PageParams pageParams) throws Exception{
		return baoSerivceI.getChengXingList(shopShiftBean);
	}
	
	

	
	/**
	 * 查询所有卷包生产综合统计
	 */
	@ResponseBody
	@RequestMapping("/getAll")
	public DataGrid getAll(JuanBaoCJBean baoCJBean) throws Exception{
		return baoSerivceI.getJueYanList(baoCJBean,1);
	}
	/**
	 * 查询所有包装生产综合统计
	 */
	@ResponseBody
	@RequestMapping("/getAllbaozhuang")
	public DataGrid getAllbaozhuang(JuanBaoCJBean baoCJBean) throws Exception{
		return baoSerivceI.getBaoZhuangList(baoCJBean,1);
	}
	/**
	 * 查询所有成型生产综合统计
	 */
	@ResponseBody
	@RequestMapping("/getAllchengxing")
	public DataGrid getAllchengxing(JuanBaoCJBean baoCJBean,PageParams pageParams) throws Exception{
		return baoSerivceI.getChengXingList(baoCJBean,1);
	}
	/**
	 * 卷烟机组生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllJuanYanJiZu")
	public DataGrid getAllJuanYanJiZu(JuanBaoCJBean baoCJBean) throws Exception{
		return baoSerivceI.getJueYanList(baoCJBean,2);
	}
	/**
	 * 包装机组生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllBaoZhuangJiZu")
	public DataGrid getAllBaoZhuangJiZu(JuanBaoCJBean baoCJBean) throws Exception{
		return baoSerivceI.getBaoZhuangList(baoCJBean,2);
	}
	/**
	 *成型机组生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllChengXingJiZu")
	public DataGrid getAllChengXingJiZu(JuanBaoCJBean baoCJBean,PageParams pageParams) throws Exception{
		return baoSerivceI.getChengXingList(baoCJBean,2);
	}
	/**
	 *卷包车间班组生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllJuanBaoBanZu")
	public DataGrid getAllJuanBaoBanZu(WorkShopShiftBean shopShiftBean) throws Exception{
		return baoSerivceI.getJuanBaoBanZuList(shopShiftBean);
	}
	/**
	 *成型车间班组生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllChengXingBanZu")
	public DataGrid getAllChengXingBanZu(WorkShopShiftBean shopShiftBean,PageParams pageParams) throws Exception{
		return baoSerivceI.getChengXingBanZuList(shopShiftBean);
	}
	
	/**
	 *卷包产品生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllJuanBaoChanPin")
	public DataGrid getAllJuanBaoChanPin(WorkShopShiftBean shopShiftBean,PageParams pageParams) throws Exception{
		return baoSerivceI.getJuanBaoChanPinList(shopShiftBean);
	}
	/**
	 *成型产品生产统计
	 */
	@ResponseBody
	@RequestMapping("/getAllChengXingChanPin")
	public DataGrid getAllChengXingChanPin(WorkShopShiftBean shopShiftBean,PageParams pageParams) throws Exception{
		return baoSerivceI.getChengXingChanPinList(shopShiftBean);
	}
	/**成型车间班组
	 * excel導出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveChengXingBanZu")
	public void excelDeriveChengXingBanZu(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		
		HSSFWorkbook wb =baoSerivceI.ExportExcelCXBZ(shopShiftBean);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveChengXingBanZu")
	public String queryExcelDeriveChengXingBanZu(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getChengXingBanZuList(shopShiftBean);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}
	/**成型车间产品
	 * excel導出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveChengXingChanPin")
	public void excelDeriveChengXingChanPin(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		
		HSSFWorkbook wb =baoSerivceI.ExportExcelCXPP(shopShiftBean);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveChengXingChanPin")
	public String queryExcelDeriveChengXingChanPin(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getChengXingChanPinList(shopShiftBean);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}

	/**成型车间班组
	 * excel導出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveChengXingCheJian")
	public void excelDeriveChengXingCheJian(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.ExportExcelCX(shopShiftBean);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveChengXingCheJian")
	public String queryExcelDeriveChengXingCheJian(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getChengXingList(shopShiftBean);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}
	/**成型车间机组
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveChengXingJiZu")
	public void excelDeriveChengXingJiZu(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
			HSSFWorkbook wb =baoSerivceI.ExportExcelCXJZ(baoCJBean,2);
			String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
			ServletOutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveChengXingJiZu")
	public String queryExcelDeriveChengXingJiZu(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getChengXingList(baoCJBean, 2);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}
	/**成型机生产统计
	 * excel導出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveChengXingZongHe")
	public void excelDeriveChengXingZongHe(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
		

		HSSFWorkbook wb =baoSerivceI.ExportExcelCXJZ(baoCJBean,1);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	/**卷包车间
	 * excel導出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveJuanBaoCJ")
	public void excelDeriveJuanBaoCJ(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
	
		HSSFWorkbook wb =baoSerivceI.ExportExcelJB2(shopShiftBean);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveJuanBaoCJ")
	public String queryExcelDeriveJuanBaoCJ(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getJuanYanList(shopShiftBean);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}
	/**卷包班组
	 * excel導出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveJuanBaoBZ")
	public void excelDeriveJuanBaoBZ(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		
		HSSFWorkbook wb =baoSerivceI.ExportExcelJBBZ2(shopShiftBean);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveJuanBaoBZ")
	public String queryExcelDeriveJuanBaoBZ(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getJuanBaoBanZuList(shopShiftBean);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}
	/**卷包产品
	 * excel導出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveJuanBaoCP")
	public void excelDeriveJuanBaoCP(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		
		HSSFWorkbook wb =baoSerivceI.ExportExcelJBPP2(shopShiftBean);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
		
		
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveJuanBaoCP")
	public String queryExcelDeriveJuanBaoCP(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getJuanBaoChanPinList(shopShiftBean);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}
	/**卷烟机组
	 * excel導出
	 * @return
	 */
	
	@RequestMapping("/excelDeriveJuanYanJZ")
	public void excelDeriveJuanYanJZ(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.ExportExcelJBJZ2(baoCJBean,2);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveJuanYanJZ")
	public String queryExcelDeriveJuanYanJZ(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getJueYanList(baoCJBean, 2);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}

	/**卷烟综合
	 * excel導出
	 * @return
	 */
	@RequestMapping("/excelDeriveJuanYanZH")
	public void excelDeriveJuanYanZH(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.ExportExcelJBJZ(baoCJBean,1);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
		
	}
	/**包装机组
	 * excel導出
	 * @return
	 */

	@RequestMapping("/excelDeriveBaoZhuangJZ")
	public void excelDeriveBaoZhuangJZ(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
		
		HSSFWorkbook wb =baoSerivceI.ExportExcelBZJZ2(baoCJBean,2);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	
	}
	/**用于判断查询到的数据是否大于10000条
	 * 张璐：2015.10.16
	 * excel導出
	 * @return 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryExcelDeriveBaoZhuangJZ")
	public String queryExcelDeriveBaoZhuangJZ(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
		DataGrid dg = baoSerivceI.getBaoZhuangList(baoCJBean, 2);
		if(dg.getTotal()>SysEqpTypeBase.rows){
			return "false";
		}else if(dg.getTotal()<=SysEqpTypeBase.rows){
			return "true";
		}
		return null;
	}
	/**包装综合
	 * excel導出
	 * @return
	 */
	
	@RequestMapping("/excelDeriveBaoZhuangZH")
	public void excelDeriveBaoZhuangZH(JuanBaoCJBean baoCJBean,HttpServletResponse response) throws Exception{		
	
		HSSFWorkbook wb =baoSerivceI.ExportExcelBZJZ(baoCJBean,1);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	@ResponseBody
	@RequestMapping("/excelDeriveEfficJuanBaoCJ")
	public void excelDeriveEfficJuanBaoCJ(WorkShopShiftBean shopShiftBean,HttpServletResponse response) throws Exception{		
	
		/*HSSFWorkbook wb =baoSerivceI.ExportExcelJBEffic(shopShiftBean);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();*/
		try {
		    String path3=DateUtil.getNowDateTime("yyyyMMddHHmmSSS")+".xls";
			//编码
			String newName = URLEncoder.encode(path3, "UTF-8"); 
			//ew.ExcelWriter(lt,newName);
			baoSerivceI.ExportExcelJBEffic(shopShiftBean,newName);
			response.addHeader("Content-Disposition", "attachment;filename="+newName);
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(newName);
			try{
				byte[] buffer = new byte[1024];
				int i = -1;
				while ((i = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, i);
				}
			} catch(Exception e){
				e.printStackTrace();
				response.getWriter().println("操作失败");
			}finally {
				inputStream.close();
				outputStream.flush();
				outputStream.close(); 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		
		
		
	}
	/**
	 * PMS查询交接班辅料/产量信息
	 * @param attr
	 * shisihai
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searchChangeShiftMatterInfo")
	public DataGrid searchChangeShiftMatterInfo(String stim,String etim,String mdTeamId,String mdShiftId){
		try {
			return baoSerivceI.queryChangeShiftMatterInfo(stim, etim,mdTeamId, mdShiftId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * PMS交接班保存修改的数据
	 * shisihai
	 * @param stim
	 * @param etim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveMatterChange")
	public Json saveMatterChange(MatterInfo bean,HttpServletRequest result){
		Json json =new Json();
		try {
			 baoSerivceI.saveMatterInfoChange(bean);
			 json.setSuccess(true);
			 json.setMsg("数据修改成功");
		} catch (Exception e) {
			json.setSuccess(false);
			 json.setMsg("数据修改失败");
			e.printStackTrace();
		}
		return json;
	}
	
	

	/**
	 * 卷包车间产耗统计导出
	 * 张璐：2015.11.16
	 * @param matterInfoBean
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/excelHandAndReceiveTeam")
	public void excelHandAndReceiveTeam(String stim,String etim,String mdTeamId,String mdShiftId,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.excelHandAndReceiveTeam(stim,etim,mdTeamId,mdShiftId);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	
	/**
	 * 交接班其他料查询
	 * shisihai
	 * @param stim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searchChangeShiftOtherMatterInfo")
	public DataGrid searchChangeShiftOtherMatterInfo(String stim,String etim,String mdTeamId,String mdShiftId){
		try {
			return baoSerivceI.queryChangeShiftOtherMatterInfo(stim,etim, mdTeamId, mdShiftId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出交接班其他料
	 * zhanglu:2015.11.16
	 * @param stim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/otherMatterHandAndReceiveInfo")
	public void otherMatterHandAndReceiveInfo(String stim,String etim,String mdTeamId,String mdShiftId,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.otherMatterHandAndReceiveInfo(stim,etim, mdTeamId, mdShiftId);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	/**
	 * 其他辅料数据修改（绑定在封箱机工单上）
	 * shisihai
	 * @param bean
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOtherMatterChange")
	public Json saveOtherMatterChange(MatterInfo bean,HttpServletRequest result){
		Json json =new Json();
		try {
			 baoSerivceI.saveOtherMatterInfoChange(bean);
			 json.setSuccess(true);
			 json.setMsg("数据修改成功");
		} catch (Exception e) {
			json.setSuccess(false);
			 json.setMsg("数据修改失败");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * PMS查询交接班辅料/产量信息  成型机
	 * @param attr
	 * shisihai
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searchFilterMatterInfo")
	public DataGrid searchFilterMatterInfo(String stim,String etim,String mdTeamId,String mdShiftId){
		try {
			return baoSerivceI.queryFilterMatterInfo(stim, etim,mdTeamId, mdShiftId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 其他辅料数据修改  成型机
	 * shisihai
	 * @param bean
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveFilterMatterChange")
	public Json saveFilterMatterChange(MatterInfo bean,HttpServletRequest result){
		Json json =new Json();
		try {
			 baoSerivceI.saveFilterMatterInfoChange(bean);
			 json.setSuccess(true);
			 json.setMsg("数据修改成功");
		} catch (Exception e) {
			json.setSuccess(false);
			 json.setMsg("数据修改失败");
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 成型机交接班数据导出
	 * shisihai
	 * @param stim
	 * @param mdTeamId
	 * @param mdShiftId
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/filterMatterInfo")
	public void filterMatterInfo(String stim,String etim,String mdTeamId,String mdShiftId,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.filterMatterInfo(stim,etim, mdTeamId, mdShiftId);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	
	/**
	 * 交接班产量查询
	 * TODO
	 * @param time
	 * @param response
	 * @return
	 * @throws Exception
	 * TRAVLER
	 * 2015年11月23日下午8:19:20
	 */
	@ResponseBody
	@RequestMapping("/getQtyData")
	public ChangeShiftData getQtyData(String time,HttpServletResponse response) throws Exception{		
		return baoSerivceI.queryQtyDataOfChangeShift(time);
	}
	/**
	 * 交接班产量修改
	 * TODO
	 * @param jsonArray
	 * @param request
	 * @return
	 * TRAVLER
	 * 2015年11月20日下午2:26:31
	 */
	@ResponseBody
	@RequestMapping("/editQty")
	public Json editBean(String jsonArray, HttpServletRequest request) {
		Json json=new Json();
		try {
			String rollerArray = request.getParameter("rollerArray");
			String packArray = request.getParameter("packArray");
			String filterArray = request.getParameter("filterArray");
			if(null!=rollerArray&&null!=packArray){
				// 填充BEAN
				ChangeShiftData[] roller = (ChangeShiftData[]) JSONUtil.JSONString2ObjectArray(rollerArray,ChangeShiftData.class);
				ChangeShiftData[] packer = (ChangeShiftData[]) JSONUtil.JSONString2ObjectArray(packArray,ChangeShiftData.class);
				ChangeShiftData[] filter = (ChangeShiftData[]) JSONUtil.JSONString2ObjectArray(packArray,ChangeShiftData.class);
				
				baoSerivceI.updateQtyInfo(roller,packer,filter);
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("保存失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 查询月绩效产量信息
	 * TODO
	 * @param stim
	 * @param etime
	 * @param request
	 * @return
	 * TRAVLER
	 * 2015年11月21日下午9:05:40
	 */
	@ResponseBody
	@RequestMapping("/queryMonthQtyData")
	public DataGrid queryMonthQtyData(String stim,String etime, HttpServletRequest request) {
			return baoSerivceI.queryMonthQtyData(stim,etime);
	}
	
	/**
	 * PMS查询工段日报
	 * TODO
	 * @param stim
	 * @param etime
	 * @param request
	 * @return
	 * TRAVLER
	 * 2015年12月1日上午8:11:52
	 */
	@ResponseBody
	@RequestMapping("/queryDayQtyData")
	public MatterInfo queryDayQtyData(String stim,String etime, HttpServletRequest request) {
			return baoSerivceI.queryDayQtyData(stim,etime);
	}
	
	/**
	 * PMS导出工段日报表
	 * TODO
	 * @param stim
	 * @param etime
	 * @param response
	 * @throws Exception
	 * TRAVLER
	 * 2015年12月1日上午8:19:02
	 */
	@ResponseBody
	@RequestMapping("/exportDayQtyData")
	public void exportDayQtyData(String stim,String etime,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.exportDayQtyData(stim,etime);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	
	/**
	 * PMS月绩效表导出
	 * TODO
	 * @param stim
	 * @param etime
	 * @param response
	 * @throws Exception
	 * TRAVLER
	 * 2015年12月1日下午5:46:17
	 */
	@ResponseBody
	@RequestMapping("/exportMonthQtyData")
	public void exportMonthQtyData(String stim,String etime,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =baoSerivceI.exportMonthQtyData(stim,etime);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	
	/**
	 * PMS班内考核管理
	 * @param shiftcheckbean
	 * @param pageparams
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryshiftcheck")
	public DataGrid queryshiftcheck(ShiftCheckBean shiftcheckbean,PageParams pageparams) throws Exception{
		return baoSerivceI.queryShiftCheck(shiftcheckbean, pageparams);
	}
	
	@ResponseBody
	@RequestMapping("/queryshiftcheckinfo")
	public DataGrid queryshiftinfobyid(String id){
		return baoSerivceI.queryShiftCheckInfo(id);
	}
	
	@RequestMapping("/goToInputAddJsp")
	public String goToInputAddJsp(HttpServletRequest request,ShiftCheckBean shiftcheckbean) throws Exception{
		request.setAttribute("lotid", shiftcheckbean.getLotid());
		request.setAttribute("shiftcheckid",shiftcheckbean.getId() );
		request.setAttribute("workorder",workOrderService.getWorkOrderById(shiftcheckbean.getEntryid()));
		request.setAttribute("boms",JSON.toJSONString(statService.getBomsWorkorderId(shiftcheckbean.getEntryid())));
		return "pms/sch/statjbcj/inshiftcheckadd";
	}
	
	@ResponseBody
	@RequestMapping("/addInput")
	public Json addInput(QmChangeShiftInfo qmchangeshiftinfo) throws Exception{
		Json json=new Json();
		try{
			boolean f=juebaoservice.addchangshiftinfo(qmchangeshiftinfo);
			if(f){
				json.setMsg("添加班内考核信息成功");
				json.setSuccess(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("添加班内考核信息失败");
			json.setSuccess(false);
		}
		return json;
	}
}
