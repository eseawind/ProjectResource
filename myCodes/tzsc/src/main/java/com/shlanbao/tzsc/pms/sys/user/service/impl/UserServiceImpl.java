package com.shlanbao.tzsc.pms.sys.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SysUserDaoI;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
import com.shlanbao.tzsc.pms.sys.user.service.UserServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.MD5Util;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 用户管理业务实现类
 * @author Leejean
 * @create 2014年9月16日上午10:43:37
 */
@Service
public class UserServiceImpl extends BaseService implements UserServiceI{
	@Autowired
	private SysUserDaoI sysUserDao;
	public UserBean getSysUserById(String id) throws Exception {
		return BeanConvertor.copyProperties(sysUserDao.findById(SysUser.class, id), UserBean.class);
	}
	@Override
	public UserBean login(UserBean user) throws Exception{
		if(user!=null){
			Object obj=sysUserDao.unique(
					/*"from SysUser u where u.del=0 and u.enable=1 and ((u.loginName=? and u.pwd=?) or (u.eno=? and u.pwd=?))",*/
					"from SysUser u where u.del=0 and u.enable=1 and  u.loginName=? and u.pwd=? )",
					user.getLoginName(),
					MD5Util.md5(user.getPwd()));
			return obj==null?null:BeanConvertor.copyProperties(obj, UserBean.class);
		}
		return null;
	}
	
	@Override
	public DataGrid querySysUser(UserBean userBean, PageParams pageParams) throws Exception{
		String hql="from SysUser o where  o.del=0";//1 deleted data is not visible
		String params="";
		if(StringUtil.notNull(userBean.getName())){
			params+=" and (o.name like '%"+userBean.getName()+"%' or o.loginName like '%"+userBean.getName()+"%' or o.eno like '%"+userBean.getName()+"%')";
		}
		if(StringUtil.notNull(userBean.getGender())){
			params+=" and o.gender ='"+userBean.getGender()+"'";
		}
		List rows=sysUserDao.queryByPage(hql.concat(params), pageParams);
		hql="select count(*) from SysUser o where o.del=0";
		try {
			rows=BeanConvertor.copyList(rows, UserBean.class);
			long total=sysUserDao.queryTotal(hql.concat(params));
			return new DataGrid(rows, total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}

	@Override
	public void editPwd(String id, String pwd) {
		SysUser user = sysUserDao.findById(SysUser.class, id);
		user.setModifyDatetime(new Date());
		user.setPwd(MD5Util.md5(pwd));
	}
	@Override
	public void addSysUser(UserBean userBean) throws Exception {
		SysUser sysUser=BeanConvertor.copyProperties(userBean, SysUser.class);
		sysUser.setCreateDatetime(new Date());
		sysUser.setModifyDatetime(new Date());
		sysUser.setDel(0L);
		sysUser.setEnable(1L);
		sysUser.setPwd(MD5Util.md5("123456"));//default the password is 123456, when the project release ,use md5 
		sysUserDao.save(sysUser);
	}
	@Override
	public void editSysUser(UserBean userBean) throws Exception {
		SysUser sysUser=sysUserDao.findById(SysUser.class, userBean.getId());
		BeanConvertor.copyProperties(userBean, sysUser);
		sysUser.setModifyDatetime(new Date());
	}
	@Override
	public void deleteSysUser(String id) throws Exception{
		sysUserDao.findById(SysUser.class,id).setDel(1L);
	}
	@Override
	public void batchDeleteSysUser(String ids) throws Exception {
		for (String id : StringUtil.splitToStringList(ids, ",")) {			
			this.deleteSysUser(id);
		}
	}
	@Override
	public void batchInsert(List<UserBean> users) throws Exception {
		try {
			sysUserDao.batchInsert(users,SysUser.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}




