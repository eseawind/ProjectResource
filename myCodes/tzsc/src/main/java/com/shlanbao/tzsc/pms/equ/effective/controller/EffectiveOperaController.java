package com.shlanbao.tzsc.pms.equ.effective.controller;

import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.shlanbao.tzsc.base.mapping.EqmCullRecordBean;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.cos.spare.beans.CosSparePartsBean;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveGraphBean;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveOperaBean;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveRunTime;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveUtilizeTime;
import com.shlanbao.tzsc.pms.equ.effective.service.EffectiveOperaServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;
/**
 * 
* @ClassName: EffectiveOperaController 
* @Description: 设备有效作业率、运行效率查询
* @author luo
* @date 2015年3月9日 下午4:18:46 
*
 */
@Controller
@RequestMapping("/pms/effect")
public class EffectiveOperaController {

	@Autowired
	public EffectiveOperaServiceI effectiveOperaServiceI;
	/**
	 * 
	* @Title: queryDataGrid 
	* @Description: 设备有效作业率查询  
	* @param  bean
	* @param  pageParams
	* @return DataGrid  返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryDataGrid")
	public DataGrid queryDataGrid(EffectiveOperaBean bean,PageParams pageParams){
		return effectiveOperaServiceI.queryList(bean, pageParams);
	}
	/**
	 * 
	* @Title: queryGraph 
	* @Description: 设备有效作用率图表查询方法
	* @param  bean
	* @return EffectiveGraphBean    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryGraph")
	public EffectiveGraphBean queryGraph(EffectiveOperaBean bean){
		return effectiveOperaServiceI.queryGraph(bean);
	}
	
	/**
	 * 
	* @Title: queryRunTimeEffective 
	* @Description: 设备运行效率  
	* @param  bean
	* @param  pageParams
	* @return DataGrid   返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryRunTimeEffect")
	public DataGrid queryRunTimeEffective(EffectiveRunTime bean,PageParams pageParams){
		return effectiveOperaServiceI.queryRunTimeEffective(bean, pageParams);
	}
	
	/**
	 * 
	* @Title: queryRunTimeEffectivesChart 
	* @Description: 设备运行效率图表  
	* @param @param bean
	* @param @return    设定文件 
	* @return EffectiveGraphBean    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryRunTimeEffectChart")
	public EffectiveGraphBean queryRunTimeEffectivesChart(EffectiveRunTime bean){
		return effectiveOperaServiceI.queryRunTimeEffectiveChart(bean);
	}
	
	
	@ResponseBody
	@RequestMapping("/queryUtilizeEffective")
	public DataGrid queryUtilizeEffective(EffectiveUtilizeTime bean,PageParams pageParams){
		return effectiveOperaServiceI.quertyUtilizeEffective(bean, pageParams);
	} 
	
	@ResponseBody
	@RequestMapping("/queryUtilizeEffectiveChart")
	public EffectiveGraphBean queryUtilizeEffectiveChart(EffectiveUtilizeTime bean){
		return effectiveOperaServiceI.quertyUtilizeEffectiveChart(bean);
	}
	
	/**
     *  功能说明：设备停机剔除时间管理-添加
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月21日16:18:46
     *  
     * */
	@ResponseBody
	@RequestMapping("/addCullRecord")
	public Json addCullRecord(HttpSession session,EqmCullRecordBean bean) throws Exception{
		Json json=new Json();
		SessionInfo loginBean = (SessionInfo) session.getAttribute("sessionInfo");
	    //创建人信息
	    bean.setCreate_user_id(loginBean.getUser().getId());
	    bean.setUpdate_user_id(loginBean.getUser().getId());
	    bean.setCreate_user_name(loginBean.getUser().getName());
	    bean.setUpdate_user_name(loginBean.getUser().getName());
	    bean.setCreate_time(new Date());
	    bean.setUpdate_time(new Date());
	    //当天（结束时间  - 起始时间） = 当天(停机时间)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date dBegin = sdf.parse(bean.getSdate());  
        Date dEnd = sdf.parse(bean.getEdate());  
        bean.setDel(1);
        List<Date> lDate = findDates(dBegin, dEnd);  
        for (Date date : lDate) {
           Date st_date = formatter.parse(sdf.format(date)+" "+bean.getStime());  
           Date ed_date = formatter.parse(sdf.format(date)+" "+bean.getEtime());
           bean.setSt_date(st_date);
           bean.setEd_date(ed_date);
           //毫秒ms
           long diff = ed_date.getTime()-st_date.getTime();
           //分钟
           int stop_time=(int) (diff/(60 * 1000));
           //当天总停机时间
           bean.setStop_time(stop_time);
           try{
        	    //保存
	   			boolean flag=effectiveOperaServiceI.addCullRecord(bean);
	   			json.setMsg("添加成功");
	   			json.setSuccess(flag);
	   	   }catch(Exception e){
	   			json.setMsg("添加失败");
	   			json.setSuccess(false);
	   	   }
        } 
		return json;
	}
	
	/**
	 * 说明：返回日期时间段内所有日期集合
	 * 
	 * */
	 public static List<Date> findDates(Date dBegin, Date dEnd) {  
	        List lDate = new ArrayList();  
	        lDate.add(dBegin);  
	        Calendar calBegin = Calendar.getInstance();  
	        // 使用给定的 Date 设置此 Calendar 的时间    
	        calBegin.setTime(dBegin);  
	        Calendar calEnd = Calendar.getInstance();  
	        // 使用给定的 Date 设置此 Calendar 的时间    
	        calEnd.setTime(dEnd);  
	        // 测试此日期是否在指定日期之后    
	        while (dEnd.after(calBegin.getTime())) {  
	            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量    
	            calBegin.add(Calendar.DAY_OF_MONTH, 1);  
	            lDate.add(calBegin.getTime());  
	        }  
	        return lDate;  
	 }  
	 
	 
	/**
	 *  【功能说明】：设备停机剔除时间管理-查询
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月22日10:36:42
     *  
	 * */ 
	@ResponseBody
	@RequestMapping("/queryCullRecord")
	public DataGrid queryCullRecord(EqmCullRecordBean bean,PageParams pageParams) throws Exception{
		
		return effectiveOperaServiceI.queryCullRecord(bean, pageParams);
	}
	 
	
	/**
	 * 【功能说明】：设备停机剔除时间管理-删除
	 *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月22日14:26:21
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/deleteCullRecordById")
	public Json deleteCullRecordById(HttpSession session,EqmCullRecordBean bean) throws Exception{
	   Json json=new Json();
       try{
    	    //保存
   			boolean flag=effectiveOperaServiceI.deleteCullRecordById(bean);
   			json.setMsg("删除成功！");
   			json.setSuccess(flag);
   	   }catch(Exception e){
   			json.setMsg("删除失败！");
   			json.setSuccess(false);
   	   }
		return json;
	}
	/**
	 * 导出设备有效作业率
	 * @param bean
	 */
	@ResponseBody
	@RequestMapping("/deriveExcel")
	public void deriveExcel(EffectiveOperaBean bean,HttpServletResponse response) {
		try {
			HSSFWorkbook wb =effectiveOperaServiceI.ExportExcelJBEffic(bean);
			String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
			ServletOutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出设备运行效率
	 * @param bean
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/deriveEqpRunEfficExcel")
	public void deriveEqpRunEfficExcel(EffectiveRunTime bean,HttpServletResponse response) {
		try {
			HSSFWorkbook wb =effectiveOperaServiceI.deriveEqpRunEfficExcel(bean);
			String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
			ServletOutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
