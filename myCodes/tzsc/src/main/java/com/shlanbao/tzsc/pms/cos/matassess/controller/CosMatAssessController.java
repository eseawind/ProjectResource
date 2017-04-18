package com.shlanbao.tzsc.pms.cos.matassess.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.CosMatAssess;
import com.shlanbao.tzsc.base.mapping.CosMatAssessParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.matassess.bean.CosMatAssessBean;
import com.shlanbao.tzsc.pms.cos.matassess.service.CosMatAssessParamServiceI;
import com.shlanbao.tzsc.pms.cos.matassess.service.CosMatAssessServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: CosMatAssessServiceImpl 
* @Description: 辅料考核版本控制ServiceImpl 
* @author luoliang
* @date 2015-1-7 上午10:44:37 
*
 */
@Controller
@RequestMapping("/pms/matassess")
public class CosMatAssessController extends BaseController{

	@Autowired
	private CosMatAssessServiceI cosMatAssessServiceI;
	@Autowired
	private CosMatAssessParamServiceI cosMatAssessParamServiceI;
	//跳转到添加界面
	@RequestMapping("/gotoBean")
	public String gotoBean(){
		return "/pms/cos/matassess/addOrUpdateBean";
	}
	
	@RequestMapping("/gotoEditBean")
	public String gotoEditBean(HttpServletRequest request,String id){
		try {
			request.setAttribute("bean",cosMatAssessServiceI.getBeanById(id));
			request.setAttribute("id","i");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改辅料考核版本信息时读取信息异常。", e);
		}
		return "/pms/cos/matassess/addOrUpdateBean";
	}
	
	@ResponseBody
	@RequestMapping("/queryBean")
	public DataGrid queryBean(CosMatAssess bean,PageParams pageParams){
		try {
			DataGrid gd = cosMatAssessServiceI.queryBean(bean, pageParams);
			System.out.println(gd.toString());
			return gd;
		} catch (Exception e) {
			log.error("查询辅料考核版本信息异常。", e);
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/queryBeanByExtend")
	public DataGrid queryBeanByExtend(CosMatAssessBean bean,PageParams pageParams){
		try {
			DataGrid gd = cosMatAssessServiceI.queryBeanByExtends(bean, pageParams);
			//System.out.println(gd.toString());
			return gd;
		} catch (Exception e) {
			log.error("查询辅料考核版本信息异常。", e);
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/addOrUpdateBean")
	public Json addOrUpdateBean(CosMatAssessBean bean){
		Json json = new Json();
		try {
			if(cosMatAssessServiceI.addOrUpdateBean(bean)){
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("操作失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加或修改辅料考核版本信息异常。", e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/editStatus")
	public Json editStatus(HttpServletRequest request,String id){
		Json json = new Json();
		try {
			if(cosMatAssessServiceI.editStatus(id)){
				json.setMsg("生效成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("生效失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			json.setMsg("生效失败!");
			json.setSuccess(false);
			log.error("辅料考核版本生效异常。", e);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/getChildByParentId")
	public DataGrid getChildByParentId(String id) throws Exception{
		id=id.replaceAll("&#39;", "");
		if(!StringUtil.notNull(id)){
			return null;
		}
		CosMatAssess bean=cosMatAssessServiceI.getBeanByIds(id);
		if(bean!=null){
			CosMatAssessParam c=new CosMatAssessParam();
			c.setCid(bean);
			return cosMatAssessParamServiceI.queryBean(c, new PageParams());
		}
		/*CosMatAssess cma=cosMatAssessServiceI.getBeanByIds(id);
		Set<CosMatAssessParam> s = cma.getCosMatAssessParam();
		Iterator i=s.iterator();
		List<CosMatAssessParamBean> l=new ArrayList<CosMatAssessParamBean>();
		while(i.hasNext()){
			CosMatAssessParamBean c=new CosMatAssessParamBean();
			CosMatAssessParam cp=(CosMatAssessParam) i.next();
			c.setCid(cp.getCid().getId());
			c.setId(cp.getId());
			c.setLval(cp.getLval());
			c.setMatId(cp.getMat().getId());
			//c.setMatName(cp.getMat().getName());
			c.setRemark(cp.getRemark());
			c.setStdID(cp.getStdID().getId());
			c.setUnitprice(cp.getUnitprice());
			c.setUval(cp.getUval());
			c.setVal(cp.getVal());
			l.add(c);
		}
		return new DataGrid(l,(long)l.size());*/
		return null;
	}
}

