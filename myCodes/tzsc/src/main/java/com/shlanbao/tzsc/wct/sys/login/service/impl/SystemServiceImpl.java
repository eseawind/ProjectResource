package com.shlanbao.tzsc.wct.sys.login.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.shlanbao.tzsc.base.dao.SchCalendarDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.dao.SysRoleDaoI;
import com.shlanbao.tzsc.base.dao.SysUserDaoI;
import com.shlanbao.tzsc.base.mapping.SchCalendar;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.mapping.SysRole;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MD5Util;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.utils.tools.XmlUtil;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
import com.shlanbao.tzsc.wct.sys.login.service.SystemServiceI;
@Service
public class SystemServiceImpl extends BaseService implements SystemServiceI {
	@Autowired
	private SchCalendarDaoI schCalendarDao;
	@Autowired
	private SysUserDaoI sysUserDao;	
	@Autowired
	private SysRoleDaoI sysRoleDao;	
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Override
	public LoginBean initLoginInfo(HttpServletRequest request) {
		
		//1.获取机台信息

		String ip = WebContextUtil.getRemoteIp(request);
		log.info("终端:"+ip+"请求访问");
		LoginBean loginBean = new LoginBean();
		
		this.initEquipment(loginBean, ip, request);
		
		//2.获取排班
		String hql = "from SchCalendar o where o.mdWorkshop.code=? and ('"+DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"' between o.stim and o.etim)";//根据车间code和当前系统时间匹配当前排班信息
		Object obj = schCalendarDao.unique(hql, loginBean.getWorkshop());
		if(null!=obj){
			SchCalendar schCalendar = (SchCalendar) obj;
			loginBean.setShift(schCalendar.getMdShift().getName()); //班次 早中晚
			loginBean.setTeam(schCalendar.getMdTeam().getName());   //班组
			loginBean.setShiftCode(schCalendar.getMdShift().getCode());
			loginBean.setTeamCode(schCalendar.getMdTeam().getCode());
			loginBean.setWorkshopCode(schCalendar.getMdWorkshop().getCode());//车间
			loginBean.setShiftId(schCalendar.getMdShift().getId());//班次
			loginBean.setTeamId(schCalendar.getMdTeam().getId());//班组
		}/*else{
			log.info("未确保测试阶段顺利登录，在未找到排班信息时使用【早班，甲班】 正式运行时需要删除else判断");
			//loginBean.setShift("未排班");
			//loginBean.setTeam("未排班");
			SchCalendar schCalendar = schCalendarDao.findById(SchCalendar.class, "1");
			schCalendar.setDate(DateUtil.formatStringToDate(new Date().toLocaleString(), "yyyy-MM-dd"));
			schCalendar.setStim(DateUtil.formatStringToDate(DateUtil.formatDateToString(new Date(),"yyyy-MM-dd")+" 07:00:00", "yyyy-MM-dd HH:mm:ss"));
			schCalendar.setEtim(DateUtil.formatStringToDate(DateUtil.formatDateToString(new Date(),"yyyy-MM-dd")+" 17:00:00", "yyyy-MM-dd HH:mm:ss"));
			loginBean.setShift(schCalendar.getMdShift().getName());
			loginBean.setTeam(schCalendar.getMdTeam().getName());
			loginBean.setShiftCode(schCalendar.getMdShift().getCode());
			loginBean.setTeamCode(schCalendar.getMdTeam().getCode());
			loginBean.setWorkshopCode(schCalendar.getMdWorkshop().getCode());//车间
		}*/
		return loginBean;
	}
	/**
	 * 初始化设备
	 * @author Leejean
	 * @create 2014年12月11日下午2:00:05
	 * @param loginBean
	 * @param request
	 */
	public void initEquipment(LoginBean loginBean,String ip,HttpServletRequest request){
		String xmlPath = WebContextUtil.getVirtualPath("xmlcfg", request)+File.separator+"equipment.xml";
		//String xmlPath = "E:\\tzsc\\tzsc\\src\\main\\webapp\\xmlcfg\\equipment.xml";
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "equipment");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(XmlUtil.getValueByNodeName(node, "ip").equals(ip)){
				loginBean.setEquipmentIp(ip);
				loginBean.setEquipmentCode(XmlUtil.getValueByNodeName(node, "code"));
				loginBean.setEquipmentName(XmlUtil.getValueByNodeName(node, "name"));
				loginBean.setEquipmentType(XmlUtil.getValueByNodeName(node, "type"));
				loginBean.setWorkshop(XmlUtil.getValueByNodeName(node, "workshop"));
			}
			
		}
	}
	/*public static void main(String[] args) {
		LoginBean loginBean = new LoginBean();
		new SystemServiceImpl().initEquipment(loginBean, "192.168.25.37", null);
		System.out.println(loginBean);
	}*/
	@Override
	public LoginBean login(LoginBean loginBean,HttpServletRequest request) {
		Object obj=null;
		//eno 空：打卡登录    不为空：手动输入用户名密码登录
		if(loginBean.getPassWord()==null || "".equals(loginBean.getPassWord())){
			obj=sysUserDao.unique("from SysUser u where u.del=0 and u.enable=1 and u.eno=?", loginBean.getEno());
		}else{
			//使用MD5解密
			obj=sysUserDao.unique(
					/*"from SysUser u where u.del=0 and u.enable=1 and ((u.loginName=? and u.pwd=?) or (u.eno=? and u.pwd=?))",*/
					"from SysUser u where u.del=0 and u.enable=1 and u.loginName=? and u.pwd=? )",
					loginBean.getLoginName(),
					MD5Util.md5(loginBean.getPassWord()));
					
		}
		if(obj!=null){//登录成功！保存登录信息到session
			loginBean.setName(((SysUser)obj).getName());
			loginBean.setUserId(((SysUser)obj).getId());
			loginBean.setIsSuccess("true");
			loginBean.setDlStatus("登录成功");
			//查询当前用户拥有的角色
			List<SysRole> sysUserRoles =  sysRoleDao.query("select o.sysRole from SysUserRole o where o.sysUser.id = ?",loginBean.getUserId());
			String roles = "";
			String rolesNames ="";
			if(null!=sysUserRoles&&sysUserRoles.size()>0){
				for (int i=0;i<sysUserRoles.size();i++) {
					SysRole role = sysUserRoles.get(i);
					roles +=role.getId();
					rolesNames +=role.getName();
					if(i<(sysUserRoles.size()-1)){
						roles +=",";
						rolesNames+=",";
					}
				}
			}
			loginBean.setRoles(roles);
			loginBean.setRolesNames(rolesNames);
			//根据设备CODE查询对应的设备主键ID
			//根据CODE到后台查询
			if(null!=loginBean.getEquipmentCode()&&!"".equals(loginBean.getEquipmentCode())){
				String querySql="select ID,EQP_TYPE_ID from MD_EQUIPMENT where DEL=0 and EQUIPMENT_CODE="+loginBean.getEquipmentCode();
				List<?> list = sysRoleDao.queryBySql(querySql, null);//只会有一条记录
				if(null!=list&&list.size()>0){
					Object[] arr=(Object[]) list.get(0);//返回一个对象，而不是 一组数组
					loginBean.setEquipmentId((arr[0].toString()));
					loginBean.setEquipmentTypeId((arr[1].toString()));
				}
			}
			//更新工单的登陆人信息
			if(null!=loginBean.getUserId()&&!"".equals(loginBean.getUserId())){
				String hql="FROM SchWorkorder o where o.mdShift.id='"+loginBean.getShiftId()+"'  and o.mdEquipment.equipmentCode=? and o.date='"+MESConvertToJB.rutDateStr()+"'";
				SchWorkorder workOrder=(SchWorkorder) schWorkorderDao.unique(hql, loginBean.getEquipmentCode());
				if(workOrder!=null){
					workOrder.setLoginUserId(loginBean.getUserId());
					workOrder.setLoginName(loginBean.getName());
					schWorkorderDao.update(workOrder);
				}
			}
			
			WebContextUtil.saveObjToSession(request.getSession(), "loginInfo", loginBean, 1000*60*60*24);
		}
		return loginBean;
	}
	/**
	 * 自检人员登录
	 * @create 20150312
	 * @param loginBean
	 * @return
	 */
	public LoginBean loginWctZj(LoginBean loginBean,HttpServletRequest request){
		Object obj=null;
		/**
		 * 1)验证WCT内部登录手动登录还是打卡登录；
		 * 2）如果有密码，表示人工手动登录，反之打卡登录；
		 * 
		 * */
		//使用MD5解密
		if(StringUtil.notNull(loginBean.getPassWord())){
			obj=sysUserDao.unique(
					/*"from SysUser u where u.del=0 and u.enable=1 and ((u.loginName=? and u.pwd=?) or (u.eno=? and u.pwd=?))",*/
					"from SysUser u where u.del=0 and u.enable=1 and u.loginName=? and u.pwd=? ",
					loginBean.getLoginName(),
					MD5Util.md5(loginBean.getPassWord()));
		}else{
			obj=sysUserDao.unique("from SysUser u where u.del=0 and u.enable=1 and eno=? )",loginBean.getEno());
		}
		if(obj!=null){//登录成功！保存登录信息到session
			loginBean.setIsSuccess("true");
			loginBean.setDlStatus("登录成功");
			loginBean.setName(((SysUser)obj).getName());
			loginBean.setUserId(((SysUser)obj).getId());
			//查询当前用户拥有的角色
			List<SysRole> sysUserRoles =  sysRoleDao.query("select o.sysRole from SysUserRole o where o.sysUser.id = ?",loginBean.getUserId());
			String roles = "";
			String rolesNames ="";
			if(null!=sysUserRoles&&sysUserRoles.size()>0){
				for (int i=0;i<sysUserRoles.size();i++) {
					SysRole role = sysUserRoles.get(i);
					roles +=role.getId();
					rolesNames +=role.getName();
					if(i<(sysUserRoles.size()-1)){
						roles +=",";
						rolesNames+=",";
					}
				}
			}
			loginBean.setRoles(roles);
			loginBean.setRolesNames(rolesNames);
			WebContextUtil.saveObjToSession(request.getSession(), "loginWctZjInfo", loginBean,1000*60*60*8);
		}else{
			loginBean.setIsSuccess("false");
		}
		return loginBean;
	}
	
	/**
	 * 设备人员登录
	 * @create 20150317
	 * @param loginBean
	 * @return
	 */
	@Override
	public LoginBean loginWctEqm(LoginBean loginBean,HttpServletRequest request){
		//使用MD5解密
		Object obj=sysUserDao.unique("from SysUser u where u.del=0 and u.enable=1 and u.loginName=? and u.pwd=? )",
				loginBean.getLoginName(),
				MD5Util.md5(loginBean.getPassWord()));
		if(obj!=null){//登录成功！保存登录信息到session
			loginBean.setIsSuccess("true");
			loginBean.setDlStatus("登录成功");
			loginBean.setName(((SysUser)obj).getName());
			loginBean.setUserId(((SysUser)obj).getId());
			//查询当前用户拥有的角色
			List<SysRole> sysUserRoles =  sysRoleDao.query("select o.sysRole from SysUserRole o where o.sysUser.id = ?",loginBean.getUserId());
			String roles = "";
			String rolesNames ="";
			if(null!=sysUserRoles&&sysUserRoles.size()>0){
				for (int i=0;i<sysUserRoles.size();i++) {
					SysRole role = sysUserRoles.get(i);
					roles +=role.getId();
					rolesNames +=role.getName();
					if(i<(sysUserRoles.size()-1)){
						roles +=",";
						rolesNames+=",";
					}
				}
			}
			loginBean.setRoles(roles);
			loginBean.setRolesNames(rolesNames);
			WebContextUtil.saveObjToSession(request.getSession(), "loginWctEqmInfo", loginBean, 1000*60*60*8);
		}else{
			loginBean.setIsSuccess("false");
		}
		return loginBean;
	}
}
