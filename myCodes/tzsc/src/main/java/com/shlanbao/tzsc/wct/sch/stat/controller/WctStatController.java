package com.shlanbao.tzsc.wct.sch.stat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageView;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.MatterInfo;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.wct.sch.stat.beans.EqpRuntime;
import com.shlanbao.tzsc.wct.sch.stat.beans.HisOutBean;
import com.shlanbao.tzsc.wct.sch.stat.beans.InputBean;
import com.shlanbao.tzsc.wct.sch.stat.beans.InputPageModel;
import com.shlanbao.tzsc.wct.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.wct.sch.stat.service.WctStatServiceI;
/**
 * 生产 统计
 * @author Leejean
 * @create 2014年11月25日下午1:31:32
 */
@Controller
@RequestMapping("/wct/stat")
public class WctStatController extends BaseController {
	@Autowired
	private WctStatServiceI wctStatService;
	/**
	 * 查询所有产出数据
	 * @author Leejean
	 * @create 2014年12月3日上午9:00:25
	 * @param outputBean 查询条件
	 * @param pageParams 参数
	 * @return datagrid
	 */
	@RequestMapping("/getAllOutputs")
	@ResponseBody
	public PageView<OutputBean> getAllOutputs(String equipmentCode,int curpage,int pagesize,String shiftId,String teamId,String date1,String date2){
		return wctStatService.getAllOutputs(equipmentCode, curpage, pagesize,shiftId,teamId,date1,date2);	
	}
	/**
	 * 根据产出获得消耗集合
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:13
	 * @param id 产出ID
	 * @return datagrid
	 */
	@RequestMapping("/getAllInputsByOutput")
	@ResponseBody
	public List<InputBean> getAllInputsByOutput(String id){
		try {
			return wctStatService.getAllInputsByOutput(id);
		} catch (Exception e) {
			super.setMessage("根据产出ID:"+id+"查询消耗数据");
			log.error(super.message, e);
		}
		return null;
	}
	/**
	 * 初始化初始化机台实时产量页面
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
	@RequestMapping("/initOutDataPage")
	@ResponseBody
	public List<EqpRuntime> initOutDataPage(HttpSession session,Long type){
		try {
			return wctStatService.initOutDataPage(session,type);
		} catch (Exception e) {
			super.setMessage("实例化机组信息异常");
			log.error(super.message, e);
		}
		return null;
	}
	/**
	 * 初始化初始化机台实时剔除页面
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
	@RequestMapping("/initBadQtyDataPage")
	@ResponseBody
	public List<EqpRuntime> initBadQtyDataPage(HttpSession session,Long type){
		try {
			return wctStatService.initOutDataPage(session,type);
		} catch (Exception e) {
			super.setMessage("实例化机组信息异常");
			log.error(super.message, e);
		}
		return null;
	}
	/**
	 * 获得实时产量数
	 * @author Leejean
	 * @create 2014年12月25日下午3:07:46
	 * @param type 规则为设备类型code
	 * @return
	 */
	@RequestMapping("/getOutData")
	@ResponseBody
	public List<EqpRuntime> getOutData(Long type){
		try {
			List<EqpRuntime> list = null;
			if(type==1){
				//获取卷烟机虚拟数据
				list = wctStatService.getRollerOutData();
			}			
			if(type==2){
				//获取包装机虚拟数据
				list = wctStatService.getPackerOutData();
			}			
			if(type==3){
				//获取封箱机虚拟数据
				list = wctStatService.getBoxerOutData();
			}			
			if(type==4){
				//获取成型机虚拟数据
				list = wctStatService.getFilterOutData();
			}			
			
			return list;
		} catch (Exception e) {
			super.setMessage("获取设备实时实时数据异常");
			log.error(super.message, e);
		}
		return null;
	}
	/**
	 * 测试数据
	 * @return
	 */
	private List<EqpRuntime> getRollerOutData() {
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		list.add(new EqpRuntime("1",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("2",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("3",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("4",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("5",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("6",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("7",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("8",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("9",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("10",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("11",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("12",  MathUtil.getRandomDouble(40,60,3)));
		return list;
	}
	/**
	 * 测试数据
	 * @return
	 */
	private List<EqpRuntime> getPackerOutData() {
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		list.add(new EqpRuntime("31",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("32",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("33",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("34",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("35",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("36",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("37",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("38",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("39",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("40",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("41",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("42",  MathUtil.getRandomDouble(40,60,3)));
		return list;
	}
	/**
	 * 测试数据
	 * @return
	 */
	private List<EqpRuntime> getBoxerOutData() {
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		list.add(new EqpRuntime("61",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("62",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("63",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("64",  MathUtil.getRandomDouble(40,60,3)));
		return list;
	}
	/**
	 * 测试数据
	 * @return
	 */
	private List<EqpRuntime> getFilterOutData() {
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		list.add(new EqpRuntime("101",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("102",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("103",  MathUtil.getRandomDouble(40,60,3)));
		list.add(new EqpRuntime("104",  MathUtil.getRandomDouble(40,60,3)));
		return list;
	}
	/**
	 * 获得实时剔除数据
	 * @author Leejean
	 * @create 2014年12月19日上午11:57:36
	 * @return
	 */
	@RequestMapping("/getBadData")
	@ResponseBody
	public List<EqpRuntime> getBadData(Long type){
		try {
			List<EqpRuntime> list = null;
			if(type==1){
				//获取卷烟机虚拟数据
				list = wctStatService.getRollerBadData();
			}			
			if(type==2){
				//获取包装机虚拟数据
				list = wctStatService.getPackerBadData();
			}			
			if(type==4){
				//获取成型机虚拟数据
				list = wctStatService.getFilterBadData();
			}
			return list;
		} catch (Exception e) {
			super.setMessage("获取设备实时实时数据异常");
			log.error(super.message, e);
		}
		return null;
	}
	/**
	 * 测试数据
	 * @return
	 */
	private List<EqpRuntime> getRollerBadData() {
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		list.add(new EqpRuntime("1", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("2", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("3", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("4", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("5", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("6", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("7", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("8", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("9", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("10", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("11", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("12", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		
		return list;
	}
	/**
	 * 测试数据
	 * @return
	 */
	private List<EqpRuntime> getPackerBadData() {
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		list.add(new EqpRuntime("31", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("32", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("33", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("34", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("35", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("36", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("37", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("38", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("39", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("40", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("41", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("42", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		
		return list;
	}
	/**
	 * 测试数据
	 * @return
	 */
	private List<EqpRuntime> getFilterBadData() {
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		list.add(new EqpRuntime("101", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("102", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("103", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		list.add(new EqpRuntime("104", MathUtil.getRandomDouble(40,60,3), MathUtil.getRandomDouble(0,1,3)));
		return list;
	}
	/**
	 * 设备历史产量
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/getHisOutData")
	@ResponseBody
	public HisOutBean getHisOutData(HisOutBean hisOutBean){
		return wctStatService.getHisOutData(hisOutBean, hisOutBean.getPageIndex(), 6);
	}
	/**
	 * 历史合计产量
	 * @author cmc
	 * @create 2016年8月22日上午9：44
	 * @throws Exception
	 */
	@RequestMapping("/getHisTotal")
	@ResponseBody
	public HisOutBean getHisTotal(HisOutBean hisOutBean) throws Exception{
		return wctStatService.getHisTotal(hisOutBean, hisOutBean.getPageIndex(), 6);
	}
	
	/**
	 * 设备历史剔除
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/getHisBadQtyData")
	@ResponseBody
	public HisOutBean getHisBadQtyData(HisOutBean hisOutBean){
		return wctStatService.getHisOutData(hisOutBean, hisOutBean.getPageIndex(), 6);
	}
	/**
	 * 初始化机台实时消耗页面
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/initEquipment4RollerInput")
	@ResponseBody
	public List<InputPageModel> initEquipment4RollerInput(){
		return wctStatService.initEqpInputInfos(1L);
	}
	/**
	 * 获得卷烟机实时消耗
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/getRollerInputData")
	@ResponseBody
	public List<InputPageModel> getRollerInputData(){
		return wctStatService.getRollerInputData();
	}
	/**
	 * 初始化包装机实时消耗页面
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/initEquipment4PackerInput")
	@ResponseBody
	public List<InputPageModel> initEquipment4PackerInput(){
		return wctStatService.initEqpInputInfos(2L);
	}
	/**
	 * 初始化历史消耗页面
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/initEquipment4PackerInput2")
	@ResponseBody
	public DataGrid initEquipment4PackerInput2(HisOutBean hisOutBean){
		//如果是卷烟机可以根据eqpcode的大小，确定工单的类型，
		return wctStatService.initEqpInputInfos2(2L,hisOutBean,15);
	}
	/**
	 * 获得包装机实时消耗
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/getPackerInputData")
	@ResponseBody
	public List<InputPageModel> getPackerInputData(){
		return wctStatService.getPackerInputData();
	}
	/**
	 * 初始化成型机消耗页面
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/initEquipment4FilterInput")
	@ResponseBody
	public List<InputPageModel> initEquipment4FilterInput(){
		return wctStatService.initEqpInputInfos(4L);
	}
	/**
	 * 获得成型机实时消耗
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/getFilterInputData")
	@ResponseBody
	public List<InputPageModel> getFilterInputData(){
		return wctStatService.getFilterInputData();
	}
	/**
	 * 初始化 根据机台号 获取设备信息
	 * @return
	 */
	@RequestMapping("/initOutDataByEqp")
	@ResponseBody
	public EqpRuntime initOutDataByEqp(EqpRuntime bean){
		try {
			return wctStatService.initOutDataByEqp(bean);
		} catch (Exception e) {
			super.setMessage("根据机台号 获取设备信息异常");
			log.error(super.message, e);
		}
		return null;
	}
	
	
	@RequestMapping("/eqpDailyInfo")
	@ResponseBody
	public List<MatterInfo> eqpDailyInfo(String runtime,String teamId){
		try {
			return wctStatService.SearchEqpDailyInfo(runtime,teamId);
		} catch (Exception e) {
			super.setMessage("根据机台号 获取设备信息异常");
			log.error(super.message, e);
		}
		return null;
	}
	
	
}
