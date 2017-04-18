package com.shlanbao.tzsc.pms.qm.prod.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmProdFileDao;
import com.shlanbao.tzsc.base.dao.QmProdManageDao;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.QmProdFile;
import com.shlanbao.tzsc.base.mapping.QmProdManage;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.qm.prod.beans.QmProdFileBean;
import com.shlanbao.tzsc.pms.qm.prod.beans.QmProdManageBean;
import com.shlanbao.tzsc.pms.qm.prod.service.QmProdManageService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.FileConvertSwfUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 工艺规程业务实现类
 * @author luther.zhang
 * @create 2014-12-22
 */
@Service
public class QmProdManageServiceImpl extends BaseService implements QmProdManageService{
	@Autowired
	private QmProdManageDao qmProdManageDao;
	@Autowired
	private QmProdFileDao qmProdFileDao;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	public QmProdManageBean getBeanById(String id) throws Exception {
		String hql=" from QmProdManage o left join fetch o.mdMat mat where o.id=? ";//品名(牌号)
		QmProdManage qmProdManage = qmProdManageDao.unique(QmProdManage.class,hql.toString(), id);
		QmProdManageBean bean = BeanConvertor.copyProperties(qmProdManage, QmProdManageBean.class);
		if(null!=qmProdManage.getMdMat()){
			bean.setProcId(qmProdManage.getMdMat().getId());
			bean.setMdMatName(qmProdManage.getMdMat().getName());
		}
		return bean;
	}
	@Override
	public DataGrid queryList(QmProdManageBean bean, PageParams pageParams) throws Exception{
		
		StringBuffer hql=new StringBuffer();
		hql.append("from QmProdManage o ");
		hql.append("left join fetch o.mdMat mat ");//品名(牌号)
		//hql.append("left join fetch o.sysUserByReviewUserid pzr "); //批准人ID
		//hql.append("left join fetch o.sysUserByAddUserid xzr "); //新增人ID
		//hql.append("left join fetch o.sysUserByUploadUserid uplaod "); //上传人ID
		hql.append("where o.prodStop=0 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProcId())){//品名
			hql.append("and mat.id = ? ");
			params.add(bean.getProcId());
		}
		if(StringUtil.notNull(bean.getProdNumber())){//编号
			hql.append("and o.prodNumber like ? ");
			params.add("%"+bean.getProdNumber()+"%");
		}
		if(StringUtil.notNull(bean.getProdName())){//名称
			hql.append("and o.prodName like ? ");
			params.add("%"+bean.getProdName()+"%");
		}
		if(StringUtil.notNull(bean.getProdVersion())){//版本号
			String prodVersion = bean.getProdVersion().toUpperCase();//大写
			prodVersion = prodVersion.replaceAll("V","");
			if(null!=prodVersion&&!"".equals(prodVersion)){
				hql.append("and o.prodVersion like ? ");
				params.add("%"+prodVersion+"%");
			}
		}
		if(StringUtil.notNull(bean.getProdStatus())){//审核状态
			hql.append("and o.prodStatus = ? ");
			params.add(bean.getProdStatus());
		}
		try {
			long total=qmProdManageDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			List<QmProdManageBean> list= new ArrayList<QmProdManageBean>();
			if(total>0){
				hql.append("order by o.createDatetime desc ");
				List<QmProdManage> rows = qmProdManageDao.queryByPage(hql.toString(), pageParams.getPage(), pageParams.getRows(), params);
				for(QmProdManage manage:rows){
					QmProdManageBean manageBean = BeanConvertor.copyProperties(manage, QmProdManageBean.class);
					
					if(null!=manage.getMdMat()){//品名
						manageBean.setProcId(manage.getMdMat().getId());
						manageBean.setMdMatName(manage.getMdMat().getName());
					}
					if(null!=manage.getSysUserByAddUserid()){//新增人
						manageBean.setAddUserId(manage.getSysUserByAddUserid().getId());
						manageBean.setAddUserName(manage.getSysUserByAddUserid().getName());
					}
					if(null!=manage.getSysUserByReviewUserid()){//批准人
						manageBean.setReviewUserId(manage.getSysUserByReviewUserid().getId());
						manageBean.setReviewUserName(manage.getSysUserByReviewUserid().getName());
					}
					list.add(manageBean);
				}
			}
			return new DataGrid(list, total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}
	/**
	 * 根据ID查询
	 */
	@Override
	public DataGrid queryListById(String id,PageParams pageParams){
		StringBuffer hql=new StringBuffer();
		String prix=bundle.getString("service_url");
		hql.append("from QmProdFile o ");//上传的文件集合
		hql.append("left join fetch o.sysUserByCreateUserId su "); //上传人ID
		hql.append("left join fetch o.qmProdManage pm "); //版本号
		List<Object> params = new ArrayList<Object>();
		hql.append("where o.isDeleted=0 and pm.id=? ");
		params.add(id);
		try {
			List<QmProdFileBean> list= new ArrayList<QmProdFileBean>();
			hql.append(" order by o.createTime desc ");//排序
			List<QmProdFile> rows = qmProdFileDao.queryByPage(hql.toString(), pageParams.getPage(), pageParams.getRows(), params);
			for(QmProdFile manage:rows){
				QmProdFileBean manageBean = BeanConvertor.copyProperties(manage, QmProdFileBean.class);
				manageBean.setFileId(prix+manageBean.getFileId());
				if(null!=manage.getSysUserByCreateUserId()){
					manageBean.setAddUserName(manage.getSysUserByCreateUserId().getName());
				}
				list.add(manageBean);
			}
			return new DataGrid(list, 0L);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}

	@Override
	public String addBean(QmProdManageBean bean) throws Exception {
		QmProdManage addBean=BeanConvertor.copyProperties(bean, QmProdManage.class);
		
		MdMat mdMat = new MdMat();
		mdMat.setId(bean.getProcId());//mdMat 品名(牌号)
		addBean.setMdMat(mdMat);
		SysUser add = new SysUser();
		add.setId(bean.getAddUserId());
		addBean.setSysUserByAddUserid(add);//新增人ID
		addBean.setSysUserByModifyUserid(add);//修改人ID
		addBean.setCreateDatetime(new Date());
		addBean.setModifyDatetime(new Date());
		addBean.setId(UUID.randomUUID().toString());
		addBean.setProdStatus("0");//审核状态
		addBean.setProdVersion("1.0");//版本号
		boolean flag = qmProdManageDao.save(addBean);
		String serializableId ="";
		if(flag){
			serializableId = addBean.getId();
		}
		return serializableId;
	}
	@Override
	public void editBean(QmProdManageBean bean) throws Exception {
		QmProdManage manageBean=qmProdManageDao.findById(QmProdManage.class, bean.getId());
		BeanConvertor.copyProperties(bean, manageBean);//修改内容
		SysUser add = new SysUser();
		add.setId(bean.getModifyUserId());
		manageBean.setSysUserByModifyUserid(add);//修改人
		manageBean.setModifyDatetime(new Date());//修改时间
		MdMat mdMat = new MdMat();
		mdMat.setId(bean.getProcId());//mdMat 品名(牌号)
		manageBean.setMdMat(mdMat);
		if(null!=manageBean.getProdVersion()&&!"".equals(manageBean.getProdVersion())){//版本号
			double vs = Double.parseDouble(manageBean.getProdVersion())+0.1;
			DecimalFormat format = new DecimalFormat("#0.0"); //java.text包里面的DecimalFormat 类搜索
			String buf = format.format(vs).toString(); 
			manageBean.setProdVersion(buf);//版本号
		}else{
			manageBean.setProdVersion("1.0");
		}
	}
	@Override
	public void deleteBean(String id) throws Exception{
		QmProdManage bean = qmProdManageDao.findById(QmProdManage.class,id);
		bean.setProdStop("1");
	}
	@Override
	public void batchDeleteByIds(String ids) throws Exception {
		for (String id : StringUtil.splitToStringList(ids, ",")) {			
			this.deleteBean(id);
		}
	}
	@Override
	public void editReview(String ids,String status,HttpSession session){
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			QmProdManage bean = qmProdManageDao.findById(QmProdManage.class,id);
			bean.setProdStatus(status);
			bean.setModifyDatetime(new Date());
			SysUser user = new SysUser();//用户
			user.setId(sessionInfo.getUser().getId());
			bean.setSysUserByModifyUserid(user);
			bean.setReviewDatetime(new Date());
			bean.setSysUserByReviewUserid(user);
		}
	}
	@Override
	public void editSend(String ids,String status,HttpSession session){
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			QmProdManage bean = qmProdManageDao.findById(QmProdManage.class,id);
			bean.setProdStatus(status);
			bean.setModifyDatetime(new Date());
			SysUser user = new SysUser();//用户
			user.setId(sessionInfo.getUser().getId());
			bean.setSysUserByModifyUserid(user);
			bean.setSendDatetime(new Date());
			bean.setSysUserBySendUserid(user);
		}
	}
	@Override
	public void deleteFiles(String ids,HttpSession session){
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			QmProdFile bean = qmProdFileDao.findById(QmProdFile.class,id);
			bean.setIsDeleted("1");
			bean.setModifyTime(new Date());
			SysUser user = new SysUser();//用户
			user.setId(sessionInfo.getUser().getId());
			bean.setSysUserByModifyUserId(user);
		}
	}
	
	
	@Override
	public void batchInsert(List<QmProdManageBean> users) throws Exception {
		try {
			qmProdManageDao.batchInsert(users,QmProdManageBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fileUpdate(String prodManageId,HttpSession session, HttpServletRequest request,
			QmProdFileBean fmBean) throws Exception {
		request.setCharacterEncoding("utf-8");
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		//获取文件名
		String[] fileUrls = (String[])request.getParameterValues("fileUrl")[0].split(",");
		String[] fileNames = (String[])request.getParameterValues("fileName")[0].split(",");
		Long l = Long.valueOf(request.getParameter("securityLevel"));
		String prodVersion =request.getParameter("prodVersion");
		
		for (int i = 0; i < fileUrls.length; i++) {
			QmProdFile file = new QmProdFile();
			file.setProdVersion(prodVersion);//版本号
			SysUser user = new SysUser();//新增用户
			user.setId(sessionInfo.getUser().getId());
			file.setSysUserByCreateUserId(user);
			
			QmProdManage prod = new QmProdManage();
			prod.setId(prodManageId);
			file.setQmProdManage(prod);
			
			file.setCreateTime(new Date());
			file.setSecurityLevel(l);

			file.setFileId(fileUrls[i]);
			file.setFileName(fileNames[i].substring(0,fileNames[i].lastIndexOf(".")));
			file.setFileType(fileUrls[i].substring(fileUrls[i].lastIndexOf(".")+1));
			file.setIsDeleted("0");
			file.setModifyTime(new Date());
			SysUser addUser = new SysUser();//用户
			addUser.setId(sessionInfo.getUser().getId());
			file.setSysUserByModifyUserId(user);
			qmProdFileDao.save(file);
			//根据主键ID查询 并回写数据
			QmProdManage findBean = qmProdManageDao.findById(QmProdManage.class,prodManageId);
			findBean.setModifyDatetime(new Date());
			findBean.setSysUserByModifyUserid(user);//修改人
			findBean.setSysUserByUploadUserid(user);//上传人
			//映射路径 供doc、txt等文档装pdf用
			/*ResourceBundle bundle = ResourceBundle.getBundle("config");
			String fileId = file.getFileId();
			String suffix = fileId.substring(file.getFileId().lastIndexOf("."));
			fileId = fileId.substring(fileId.lastIndexOf("/")+1);
			fileId = fileId.substring(0,fileId.lastIndexOf("."));
			String sourceFilePath = request.getServletContext().getRealPath(bundle.getString("upload"))+"\\"+fileId+suffix;
			String pdfFilePath = request.getServletContext().getRealPath(bundle.getString("pdf"))+"\\"+fileId+".pdf";
			String swfFilePath = request.getServletContext().getRealPath(bundle.getString("swf"))+"\\"+fileId+".swf";

			//转成pdf转成swf
			try{
				FileConvertSwfUtil.fileConvert(sourceFilePath,pdfFilePath,swfFilePath);	
			}catch(Exception e){
				log.error("QmProdManageServiceImpl->fileUpdate->fileConvert() is error:",e);
			}*/
		}
		
	}
	
 
	
}




