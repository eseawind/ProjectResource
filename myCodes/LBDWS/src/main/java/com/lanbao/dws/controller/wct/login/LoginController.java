package com.lanbao.dws.controller.wct.login;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.lanbao.dws.common.tools.ComboboxType;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.common.tools.WebContextUtil;
import com.lanbao.dws.common.tools.XmlUtil;
import com.lanbao.dws.model.combobox.MdEquipment;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.login.ILoginService;
import com.lanbao.dws.service.wct.menu.IWctMenuService;




@Controller
@RequestMapping("/wct")
public class LoginController {
	
	@Autowired
	private IWctMenuService wctMenuService;
	
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private InitComboboxData initComboboxData;
	
	//登录页
	@RequestMapping("/index")
	public String gotoLogin( Model model){
		//返回主页面
		return "web_html/wct/login";
	}
	
	//首页
	@RequestMapping("/gotomain")
	public String loadIndex( Model model,HttpSession session){
		//根据登陆的设备code区分卷包、封箱、成型首页
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		//0卷包机组    1 封箱机组     2成型机组    3发射机组
		int loginType=loginBean.getLoginType();
		//加载WCT顶部主菜单
		model.addAttribute("datas", wctMenuService.queryWctTopMainMenu(loginType));
		String defaultUrl="web_html/wct/home/main_contents";
		switch(loginType){
		case 0:break;
		case 1:
			defaultUrl="web_html/wct/home/fzj";
			break;
		case 2:
			defaultUrl="web_html/wct/home/cxj";
			break;
		case 3:
			defaultUrl="web_html/wct/home/fsj";
			break;
		}
		model.addAttribute("mainHtmlUrl",defaultUrl);
		return "web_html/wct/main";
	}
	
	//非静态资源跳转
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath( Model model,String url){
		//返回主页面
		return url;
	}
	
	/**
	 * 【功能说明】：登录时，获取当前机台工单信息
	 * @author Administrator
	 * @date 2016年7月20日14:28:13
	 * */
	@ResponseBody
	@RequestMapping("/getWorkOrder")
	public String getWorkOrder( HttpServletRequest request,LoginBean bean){
		try {
			//获取计算机登录用户名 
			/*Properties prop = System.getProperties();
			bean.setWindUname(prop.getProperty("user.name"));*/
			//获取当前设备工单
			if(!StringUtil.notEmpty(bean.getWindUname())){
				String ip = request.getHeader("x-forwarded-for");
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getHeader("Proxy-Client-IP");
		        }
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getHeader("WL-Proxy-Client-IP");
		        }
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getRemoteAddr();
		        }
		        InetAddress address=InetAddress.getByName(ip);
		        if(address!=null){
		        	String n1=address.getHostName();
		        	String[] names =n1.split("-");
		        	bean.setWindUname(names[0]);
		        }
			}
			bean=initEquipment(bean,request);
			bean=loginService.getWorkOrder(bean);
			//保存当前登录session
			WebContextUtil.saveObjToSession(request.getSession(true), "loginInfo", bean, 1000*60*60*24);
			//返回登录页
			Gson gson = new Gson();
	        String json = gson.toJson(bean);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 说明：封装登录信息
	 * @author wanchanghuang
	 * @create 2014年12月11日下午2:00:05
	 * @param loginBean
	 * @param request
	 */
	public LoginBean initEquipment(LoginBean bean,HttpServletRequest request){
		String xmlPath = WebContextUtil.getVirtualPath("", request)+File.separator + "WEB-INF\\classes\\equipment.xml" ;
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "equipment");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(XmlUtil.getValueByNodeName(node, "winUname").equalsIgnoreCase(bean.getWindUname())){
				Integer eqmCode=Integer.parseInt( XmlUtil.getValueByNodeName(node, "code") );
				//确定当前设备（卷烟机，包装机），拆分数据
				if(1<=eqmCode && eqmCode<=30){ 
					//卷烟机
					String code=XmlUtil.getValueByNodeName(node, "code");
					bean.setRollerEquipmentId(getMdEquipmentByCode(code).getId());
					bean.setRollerEquipmentCode(code);
					bean.setRollerEquipmentName(XmlUtil.getValueByNodeName(node, "name"));
					bean.setRollerEquipmentType(XmlUtil.getValueByNodeName(node, "type"));
					bean.setWorkshopId(XmlUtil.getValueByNodeName(node, "workshop"));
					bean.setLoginType(0);
				}else if(31<=eqmCode && eqmCode<=60){ 
					//包装机
					String code=XmlUtil.getValueByNodeName(node, "code");
					bean.setPackerEquipmentId(getMdEquipmentByCode(code).getId());
					bean.setPackerEquipmentCode(code);
					bean.setPackerEquipmentName(XmlUtil.getValueByNodeName(node, "name"));
					bean.setPackerEquipmentType(XmlUtil.getValueByNodeName(node, "type"));
					bean.setWorkshopId(XmlUtil.getValueByNodeName(node, "workshop"));
					bean.setLoginType(0);
				}else if(61<=eqmCode && eqmCode<=70){
					//封箱机  4T   2
					String code=XmlUtil.getValueByNodeName(node, "code");
					String id=getMdEquipmentByCode(code).getId();
					//String id="1111";
					String eqpName=XmlUtil.getValueByNodeName(node, "name");
					String eqpType=XmlUtil.getValueByNodeName(node, "type");
					String workshopId=XmlUtil.getValueByNodeName(node, "workshop");
					bean.setWorkshopId(workshopId);
					bean.setLoginType(1);
					switch(eqmCode){
						case 61:
						case 63:
							bean.setBoxerEquipmentCode0(code);
							bean.setBoxerEquipmentId0(id);
							bean.setBoxerEquipmentName0(eqpName);
							bean.setBoxerEquipmentType0(eqpType);
							break;
						case 62:
						case 64:
							bean.setBoxerEquipmentCode1(code);
							bean.setBoxerEquipmentId1(id);
							bean.setBoxerEquipmentName1(eqpName);
							bean.setBoxerEquipmentType1(eqpType);
							break;
					}
				}else if(101<=eqmCode && eqmCode<=130){
					//成型机  3T   1
					String code=XmlUtil.getValueByNodeName(node, "code");
					String id=getMdEquipmentByCode(code).getId();
					//String id="111";
					String eqpName=XmlUtil.getValueByNodeName(node, "name");
					String eqpType=XmlUtil.getValueByNodeName(node, "type");
					String workshopId=XmlUtil.getValueByNodeName(node, "workshop");
					bean.setWorkshopId(workshopId);
					bean.setLoginType(2);
					switch(eqmCode){
						case 101:
						case 103:
							bean.setFilterEquipmentCode0(code);
							bean.setFilterEquipmentId0(id);
							bean.setFilterEquipmentName0(eqpName);
							bean.setFilterEquipmentType0(eqpType);
							break;
						case 102:
						case 104:
							bean.setFilterEquipmentCode1(code);
							bean.setFilterEquipmentId1(id);
							bean.setFilterEquipmentName1(eqpName);
							bean.setFilterEquipmentType1(eqpType);
							break;
					}
				}else if(131<=eqmCode && eqmCode<=140){
					//发射机  3T  1
					String code=XmlUtil.getValueByNodeName(node, "code");
					String id=getMdEquipmentByCode(code).getId();
					//String id="111";
					String eqpName=XmlUtil.getValueByNodeName(node, "name");
					String eqpType=XmlUtil.getValueByNodeName(node, "type");
					String workshopId=XmlUtil.getValueByNodeName(node, "workshop");
					bean.setWorkshopId(workshopId);
					bean.setLoginType(3);
					switch(eqmCode){
						case 131:
							bean.setLaunchEquipmentCode0(code);
							bean.setLaunchEquipmentId0(id);
							bean.setLaunchEquipmentName0(eqpName);
							bean.setLaunchEquipmentType0(eqpType);
							break;
						case 132:
							bean.setLaunchEquipmentCode1(code);
							bean.setLaunchEquipmentId1(id);
							bean.setLaunchEquipmentName1(eqpName);
							bean.setLaunchEquipmentType1(eqpType);
							break;
						case 133:
							bean.setLaunchEquipmentCode2(code);
							bean.setLaunchEquipmentId2(id);
							bean.setLaunchEquipmentName2(eqpName);
							bean.setLaunchEquipmentType2(eqpType);
							break;
					}
				}
			}
		}
		return bean;
	}
	
	/**
	 * 说明：通过设备code，返回当前对象
	 * @param code：设备表code
	 * */
	public MdEquipment getMdEquipmentByCode(String code){
		List<MdEquipment> list = (List<MdEquipment>) initComboboxData.getComboboxDataByType(ComboboxType.ALLEQPS);
		for(MdEquipment eqm:list){
			if(code.equals(eqm.getEquipmentCode())){
				return eqm;
			}
		}
		return null;
	}

	@RequestMapping("/logOut")
	public String logOut(HttpSession session){
		session.invalidate();
		return "redirect:/wct/index.htm";
	}
}
