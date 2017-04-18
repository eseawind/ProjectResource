package com.shlanbao.tzsc.pms.sys.repairResquest.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shlanbao.tzsc.base.dao.SysMaintenanceStaffDaoI;
import com.shlanbao.tzsc.base.mapping.SysMaintenanceStaff;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;
import com.shlanbao.tzsc.pms.sys.repairResquest.service.RepairResquestServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class RepairResquestServiceImpl extends BaseService implements RepairResquestServiceI{
	@Autowired
	protected SysMaintenanceStaffDaoI SysMaintenanceStaffDao;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	private String imgPath=bundle.getString("service_url")+File.separator+bundle.getString("upload_img")+File.separator;//添加图片路径前缀
	/**
	 * 
	 * 添加维修人员
	 * 
	 * @author 景孟博  
	 */
	@Override
	public void addFixUser(RepairResquestBean repairResquestuserBean,MultipartFile file)throws Exception {
		//上传图片
		if(file!=null){
			String fileName = file.getOriginalFilename();
			int index1=fileName.indexOf('.');
			String hz=fileName.substring(index1);
			String name=fileName.substring(0, index1+1).hashCode()+System.currentTimeMillis()+hz;
			byte[] b=new byte[1024];
			InputStream is=file.getInputStream();
		    String tomAdd=bundle.getString("save_url");
			String add1=bundle.getString("upload_img");
			FileOutputStream os=new FileOutputStream(tomAdd+File.separator+add1+File.separator+name);//保存路径
			while(is.read(b, 0, b.length)!=-1){
				os.write(b);
			}
			is.close();
			os.close();
			//设置图片显示路径
			repairResquestuserBean.setPath(name);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT into sys_maintenance_staff (id,shift_id,user_id,user_name,remark,status,create_user_id,create_user_name,create_user_time,type_id,type_name,path,eqp_type,team_id)  VALUES('"+UUID.randomUUID().toString()+"'");
		if(StringUtil.notNull(repairResquestuserBean.getShiftId())){
			sb.append(",'"+repairResquestuserBean.getShiftId()+"'");
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getUserId())){
			sb.append(",'"+repairResquestuserBean.getUserId()+"'");
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getUserName())){
			sb.append(",'"+repairResquestuserBean.getUserName()+"'");
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getRemark())){
			sb.append(",'"+repairResquestuserBean.getRemark()+"'");
		}else{
			sb.append(",NULL");
		}
		sb.append(",'0'");
		if(StringUtil.notNull(repairResquestuserBean.getCreateUserId())){
			sb.append(",'"+repairResquestuserBean.getCreateUserId()+"'");
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getCreateUserName())){
			sb.append(",'"+repairResquestuserBean.getCreateUserName()+"'");
		}else{
			sb.append(",NULL");
		}
		sb.append(",getdate()");
		if(StringUtil.notNull(repairResquestuserBean.getTypeId())){
			sb.append(",'"+repairResquestuserBean.getTypeId()+"'");
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getTypeId())){
			if(repairResquestuserBean.getTypeId().equals("1")){
				sb.append(",'机械维修工'");
				}else if(repairResquestuserBean.getTypeId().equals("2")){
					sb.append(",'电气维修工'");
				}else if(repairResquestuserBean.getTypeId().equals("3")){
					sb.append(",'电气维修工'");
				}
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getPath())){
			sb.append(",'"+repairResquestuserBean.getPath()+"'");
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getEqpType())){
			sb.append(",'"+repairResquestuserBean.getEqpType()+"'");
		}else{
			sb.append(",NULL");
		}
		if(StringUtil.notNull(repairResquestuserBean.getTeamId())){
			sb.append(",'"+repairResquestuserBean.getTeamId()+"'");
		}else{
			sb.append(",NULL");
		}
		sb.append(")");
		SysMaintenanceStaffDao.updateBySql(sb.toString(), null);
	}
	/**
	 *查询维修人员 
	 * @author 景孟博
	 */
	@Override
	public DataGrid queryFixUser(RepairResquestBean repairResquestuserBean,PageParams pageParams)throws Exception{
		String userName="";
		String userId="";
		String shiftId="";
		/*if(isNameFuzzy && StringUtil.notNull(repairResquestuserBean.getUserName())){
			userName="user_name like'%"+repairResquestuserBean.getUserName()+"%'";
		}else */
		if(StringUtil.notNull(repairResquestuserBean.getUserName())){
			userName=" and ( user_name like '%"+repairResquestuserBean.getUserName().trim()+"%'";
			userId=" or user_id='"+repairResquestuserBean.getUserName().trim()+"' ) ";
		}
		if(StringUtil.notNull(repairResquestuserBean.getTeamId())){
			shiftId=" and team_id='"+repairResquestuserBean.getTeamId()+"' ";
		}
		if(StringUtil.notNull(repairResquestuserBean.getId())){
			shiftId=" and id='"+repairResquestuserBean.getId()+"' ";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" (select id,(SELECT name from MD_TEAM where id=team_id) as TeamName,(SELECT ENO from SYS_USER where ID=user_id) as ENO,user_name,remark,status,create_user_id,create_user_name,create_user_time,type_id,type_name,path,eqp_type,ROW_NUMBER () OVER ( ORDER BY create_user_time ) bh,update_user_name,update_user_time,shift_id");
		sb.append(" from sys_maintenance_staff");
		sb.append(" where 1=1"+userName+userId+shiftId+")");
		int page=pageParams.getPage();
		int row=pageParams.getRows();
	    String sql="select * from"+sb.toString()+"jmb where 1=1"+" AND bh >  "+(page-1)*row+" AND bh <="+page*row;
		long total= 0;//总记录数
		List<?> count = SysMaintenanceStaffDao.queryBySql(sb.toString(), null);
		List<?> list = SysMaintenanceStaffDao.queryBySql(sql, null);
		if(list.size()>0){
			int arr=count.size();//返回一个对象，而不是 一组数组
			total =Long.parseLong(ObjectUtils.toString(arr));
		}
		List<RepairResquestBean> beans = new ArrayList<RepairResquestBean>();
		if(total>0){
		RepairResquestBean bean = null;
		for(int i=0;i<list.size();i++){
			bean = new RepairResquestBean();
			Object[] arr=(Object[]) list.get(i);
			bean.setId(ObjectUtils.toString(arr[0]));//主键id
			bean.setTeamId(ObjectUtils.toString(arr[1]));
			bean.setEno(ObjectUtils.toString(arr[2]));
			bean.setUserName(ObjectUtils.toString(arr[3]));
			bean.setRemark(ObjectUtils.toString(arr[4]));
			bean.setStatus(ObjectUtils.toString(arr[5]));
			bean.setCreateUserId(ObjectUtils.toString(arr[6]));
			bean.setCreateUserName(ObjectUtils.toString(arr[7]));
			bean.setCreateUserTime(ObjectUtils.toString(arr[8]));
			bean.setTypeId(ObjectUtils.toString(arr[9]));
			bean.setTypeName(ObjectUtils.toString(arr[10]));
			bean.setPath(imgPath+ObjectUtils.toString(arr[11]));
			bean.setEqpType(ObjectUtils.toString(arr[12]));
			bean.setUpdateUserName(ObjectUtils.toString(arr[14]));
			bean.setUpdateUserTime(ObjectUtils.toString(arr[15]));
			bean.setShiftId(ObjectUtils.toString(arr[16]));
			beans.add(bean);
		  }
		 }
		return new DataGrid(beans, total);
		}
	
	/**
	 * 
	 * 修改维修工信息
	 * @author 景孟博
	 * 更改人 石四海 2015-12-4
	 */
	@Override
	public void updateFixUser(RepairResquestBean bean,MultipartFile file)throws Exception{
		SysMaintenanceStaff b=SysMaintenanceStaffDao.findById(SysMaintenanceStaff.class, bean.getId());
		if(b!=null){
			//上传图片
			if(file!=null&&!file.isEmpty()){
			    String tomAdd=bundle.getString("save_url");
				String add1=bundle.getString("upload_img");
				String name=null;
				if(StringUtil.notNull(b.getPath())){
					name=b.getPath();
					//删除原图片
					File f2=new File(tomAdd+File.separator+add1+File.separator+name);
					if(f2.exists()&&f2.isFile()){
						f2.delete();
					}
					//重新给文件起名
					String fileName = file.getOriginalFilename();
					int index1=fileName.indexOf('.');
					String hz=fileName.substring(index1);
					name=fileName.substring(0, index1+1).hashCode()+System.currentTimeMillis()+hz;
					//新图片名
					bean.setPath(name);
					byte[] bt=new byte[1024];
					InputStream is=file.getInputStream();
					FileOutputStream os=new FileOutputStream(tomAdd+File.separator+add1+File.separator+name);//保存路径
					while(is.read(bt, 0, bt.length)!=-1){
						os.write(bt);
					}
					is.close();
					os.close();
				}
			}
			//更新数据
			
			//图片路径
			if(StringUtil.notNull(bean.getPath())){
				b.setPath(bean.getPath());
			}
			//备注
			if(StringUtil.notNull(bean.getRemark())){
				b.setRemark(bean.getRemark());
			}
			//用户名
			if(StringUtil.notNull(bean.getUserName())){
				b.setUser_name(bean.getUserName());
			}
			if(StringUtil.notNull(bean.getUserId())){
				b.setUser_id(bean.getUserId());
			}
			//更新者
			if(StringUtil.notNull(bean.getUpdateUserId())){
				b.setUpdate_user_id(bean.getUpdateUserId());
			}
			if(StringUtil.notNull(bean.getUpdateUserName())){
				b.setUpdate_user_name(bean.getUpdateUserName());
			}
			//班组
			if(StringUtil.notNull(bean.getTeamId())){
				b.setTeam_id(bean.getTeamId());
			}
			//维修设备
			if(StringUtil.notNull(bean.getEqpType())){
				b.setEqp_type(bean.getEqpType());
			}
			//维修工类别
			if(StringUtil.notNull(bean.getTypeId())){
				b.setType_id(bean.getTypeId());
			}
			//状态
			if(StringUtil.notNull(bean.getStatus())){
				b.setStatus(bean.getStatus());
			}
			//更新时间
			b.setUpdate_user_time(new Date());
			SysMaintenanceStaffDao.update(b);
			
		}
		
	}
	
	
	/**
	 * 通过id查询维修工信息
	 * 
	 * @author 景孟博
	 */
	@Override
	public RepairResquestBean getFixUserById(String id,String updateUsrId,String updateUserName)throws Exception{
		String sql = "select id,team_id,user_id,user_name,remark,type_id,type_name,path,eqp_type,(SELECT ENO from SYS_USER where ID=user_id) as ENO,status from sys_maintenance_staff where id='"+id+"'";
		List<?> lt = SysMaintenanceStaffDao.queryBySql(sql);
		if(lt.size()>0){
		Object[] o=(Object[]) lt.get(0);
		RepairResquestBean bean = new RepairResquestBean();
		bean.setId(StringUtil.convertObjToString(o[0]));
		bean.setTeamId(StringUtil.convertObjToString(o[1]));
		bean.setUserId(StringUtil.convertObjToString(o[2]));
		bean.setUserName(StringUtil.convertObjToString(o[3]));
		bean.setRemark(StringUtil.convertObjToString(o[4]));
		bean.setTypeId(StringUtil.convertObjToString(o[5]));
		bean.setTypeName(StringUtil.convertObjToString(o[6]));
		if(null!=o[7]){
			bean.setPath(imgPath+o[7].toString());
		}
		bean.setEqpType(StringUtil.convertObjToString(o[8]));
		bean.setEno(StringUtil.convertObjToString(o[9]));
		bean.setStatus(StringUtil.convertObjToString(o[10]));
		bean.setUpdateUserId(updateUsrId);
		bean.setUpdateUserName(updateUserName);
		return bean;
		}else{
			return null;
		}	
	}
	
	/**
	 * 删除维修人信息
	 * @author 景孟博
	 */
	@Override
	public void deleteFixUser(String id)throws Exception{
		String sql = "delete from sys_maintenance_staff where id ='"+id+"' ";
		SysMaintenanceStaffDao.updateBySql(sql, null);
	}
	
	
	/**
	 * 批量删除
	 * @author 景孟博
	 */
	@Override
	public void batchDelete(String ids)throws Exception{
		for(String id : StringUtil.splitToStringList(ids, ",")){
			this.deleteFixUser(id);
		}
	}
	/**
	 * 查询wct维修呼叫处于等待状态的请求信息，用于推送消息
	 * shisihai
	 */
	@Override
	public List<RepairResquestBean> queryAskInfo() {
		List<RepairResquestBean> data=new ArrayList<RepairResquestBean>();
		String sql="SELECT (SELECT name from MD_SHIFT where id=shift_id) as shift_id ,(SELECT name from MD_TEAM where id=team_id) as team_name,eqp_name,create_user_name,CONVERT(VARCHAR(21),create_user_time,20),designated_person_name,type_name FROM sys_service_info WHERE status=0";
		List<?> ls=SysMaintenanceStaffDao.queryBySql(sql);
		RepairResquestBean bean=null;
		Object[] o=null;
		if(null!=ls&ls.size()>0){
			for (Object obj : ls) {
				bean=new RepairResquestBean();
				o=(Object[])obj;
				bean.setShiftName(StringUtil.convertObjToString(o[0]));//班次
				bean.setTeamName(StringUtil.convertObjToString(o[1]));//班组
				bean.setEqpName(StringUtil.convertObjToString(o[2]));//机台
				bean.setCreateUserName(StringUtil.convertObjToString(o[3]));//呼叫人
				bean.setCreateUserTime(StringUtil.convertObjToString(o[4]));//呼叫时间
				bean.setUserName(StringUtil.convertObjToString(o[5]));//维修工姓名
				bean.setTypeName(StringUtil.convertObjToString(o[6]));//维修工类别
				data.add(bean);
			}
		}
		return data;
	}
}
