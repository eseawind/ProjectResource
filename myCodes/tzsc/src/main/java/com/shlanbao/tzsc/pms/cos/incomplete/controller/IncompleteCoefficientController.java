package com.shlanbao.tzsc.pms.cos.incomplete.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.CosIncompleteCoefficient;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.incomplete.service.IncompleteCoefficientServiceI;


/**
 * @ClassName: IncompleteCoefficientServiceI 
 * @Description: 残烟丝考核系数设置
 * @author luo
 * @date 2015年1月22日 下午1:58:30 
 *
 */
@Controller
@RequestMapping("/pms/incomplete")
public class IncompleteCoefficientController extends BaseController{
	
	@Autowired
	private IncompleteCoefficientServiceI incompleteCoefficientService;
	
	/**
	* @Title: queryIncompleteByBean 
	* @Description: 根据Bean查询
	* @param  bean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryIncompleteByBean")
	public DataGrid queryIncompleteByBean(CosIncompleteCoefficient bean,PageParams pageParams){
		
		return incompleteCoefficientService.queryBeanGridByBean(bean, pageParams);
	}
	
	//页面跳转
	@RequestMapping("/gotoBean")
	public String gotoDis(){
		return "/pms/cos/incomplete/addOrUpdateBean";
	} 
	/**
	* @Title: gotoEditBean 
	* @Description: 跳转到修改界面
	* @param  request
	* @param  id
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/gotoEditBean")
	public String gotoEditBean(HttpServletRequest request,String id){
		try {
			request.setAttribute("bean",incompleteCoefficientService.queryBeanById(id));
			request.setAttribute("id","i");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改 残烟丝考核系数时读取信息异常。", e);
		}
		return "/pms/cos/incomplete/addOrUpdateBean";
	}

	/**
	* @Title: addOrUpdateBean 
	* @Description: 添加或修改Bean 
	* @param @param bean
	* @param @return    设定文件 
	* @return Json    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdateBean")
	public Json addOrUpdateBean(CosIncompleteCoefficient bean){
		Json json = new Json();
		try {
			if(incompleteCoefficientService.addOrUpdateBean(bean)){
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("操作失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加或修改残烟丝考核系数信息异常。", e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
