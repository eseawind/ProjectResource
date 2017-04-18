package com.shlanbao.tzsc.pms.cos.matassess.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.shlanbao.tzsc.pms.cos.matassess.bean.CosMatAssessParamBean;
import com.shlanbao.tzsc.pms.cos.matassess.service.CosMatAssessParamServiceI;
import com.shlanbao.tzsc.pms.cos.matassess.service.CosMatAssessServiceI;
import com.shlanbao.tzsc.pms.sch.constd.beans.ConStdBean;
import com.shlanbao.tzsc.pms.sch.constd.service.ConStdServiceI;
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
@RequestMapping("/pms/matassessParam")
public class CosMatAssessParamController extends BaseController{

	private static final String ERRORTIP="辅料考核版本管理详细信息异常";
	@Autowired
	private CosMatAssessParamServiceI cosMatAssessParamServiceI;
	@Autowired
	private CosMatAssessServiceI cosMatAssessServiceI;
	@Autowired
	public ConStdServiceI conStdService;

	//跳转到添加界面
	@SuppressWarnings("unused")
	@RequestMapping("/gotoBean")
	private String gotoBean(HttpServletRequest request,String parentId) throws Exception{
		request.setAttribute("parentBean",cosMatAssessServiceI.getBeanById(parentId));
		return "/pms/cos/matassess/addOrUpdateParamBean";
	}
	@RequestMapping("/getConStdBean")
	public ConStdBean getConStdDes(HttpServletRequest request,String id) throws Exception{
		ConStdBean conStd=new ConStdBean();
		conStd.setMatProd(request.getParameter("prod"));//牌号ID
		conStd.setMat(request.getParameter("mat"));//辅料ID
		DataGrid dt=conStdService.getAllConStds(conStd);
		List<ConStdBean> l=dt.getRows();
		return l.get(0);
	}
	
	@RequestMapping("/gotoEditBean")
	public String gotoEditBean(HttpServletRequest request,String id){
		try {
			CosMatAssessParamBean beans=cosMatAssessParamServiceI.getBeanById(id);
			request.setAttribute("bean",beans);
			request.setAttribute("parentBean",cosMatAssessServiceI.getBeanById(beans.getCid()));
			request.setAttribute("id","i");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改"+ERRORTIP, e);
		}
		return "/pms/cos/matassess/addOrUpdateParamBean";
	}
	
	@ResponseBody
	@RequestMapping("/queryBean")
	public DataGrid queryBean(CosMatAssessParam bean,PageParams pageParams){
		try {
			DataGrid gd = cosMatAssessParamServiceI.queryBean(bean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询"+ERRORTIP, e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/addOrUpdateBean")
	public Json addOrUpdateBean(CosMatAssessParamBean bean){
		Json json = new Json();
		try {
			
			CosMatAssessParamBean c=new CosMatAssessParamBean();
			c.setMatId(bean.getMatId());
			c.setCid(bean.getCid());
			List l=cosMatAssessParamServiceI.queryBeansByExpand(c,new PageParams());
			if(l.size()>0){
				json.setMsg("此辅料的考核信息已存在!");
				json.setSuccess(false);
				return json;
			}
			
			if(cosMatAssessParamServiceI.addOrUpdateBean(bean)){
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("操作失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加或修改"+ERRORTIP, e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	
	//根据牌号和辅料code获取单耗标准的上下限值和标准值
	@ResponseBody
	@RequestMapping("/gotoChildByParentId")
	public DataGrid gotoChildByParentId(HttpServletRequest request,String id) throws Exception{
		if(!StringUtil.notNull(id)){
			return null;
		}
		CosMatAssess cma=cosMatAssessServiceI.getBeanByIds(id);
		Set<CosMatAssessParam> s = cma.getCosMatAssessParam();
		if(s.size()<1){
			return new DataGrid(new ArrayList<CosMatAssessParam>(),0L);
		}
		List<CosMatAssessParam> l=new ArrayList<CosMatAssessParam>(s);
		return new DataGrid(l,(long)l.size());
	}
}

