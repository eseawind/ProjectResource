package com.shlanbao.tzsc.pms.sys.datadict.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.DocFileManageDaoI;
import com.shlanbao.tzsc.base.dao.SysEqpCategoryDaoI;
import com.shlanbao.tzsc.base.mapping.DocFilemanage;
import com.shlanbao.tzsc.base.mapping.SysEqpCategory;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpCategoryBean;
import com.shlanbao.tzsc.pms.sys.datadict.service.SysEqpCategoryServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class SysEqpCategoryServiceImpl extends BaseService implements SysEqpCategoryServiceI {
	@Autowired
	protected SysEqpCategoryDaoI sysEqpCategoryDao;
	@Autowired
	protected DocFileManageDaoI docFileManageDao;
	@Autowired
	private LoadComboboxServiceI  comboboxServiceI;
	
	@Override
	public void addMdCategory(SysEqpCategory mdEqpCategory) throws Exception {
			SysEqpCategory categroy = null;		
			//categroy = sysEqpCategoryDao.findById(SysEqpCategory.class, mdEqpCategory.getId());	
			categroy = mdEqpCategory;
			categroy.setDel("0");
			categroy.setId(UUID.randomUUID().toString());
		sysEqpCategoryDao.save(categroy);
		//comboboxServiceI.initCombobox(ComboboxType.EQPCATEGORY);
	}

	@Override
	public SysEqpCategory getMdCategoryById(String id) throws Exception {
		return sysEqpCategoryDao.findById(SysEqpCategory.class, id);
	}

	@Override
	public DataGrid queryMdCategory(SysEqpCategory mdEqpCategory,PageParams pageParams) throws Exception {
		
		StringBuffer whereSql = new StringBuffer();//设置查询参数
		if(StringUtil.notNull(mdEqpCategory.getCode())){
			whereSql.append(" and o.code like '%" + mdEqpCategory.getCode() +"%'");
		}
		if(StringUtil.notNull(mdEqpCategory.getName())){
			whereSql.append(" and o.name like '%" + mdEqpCategory.getName() +"%'");
		}
		//总记录数============================================================================================
		long total= 0;
		List<SysEqpCategoryBean> lastRows = new ArrayList<SysEqpCategoryBean>();
		String countSql =" select  count(o.id) from SYS_EQP_CATEGORY o where o.DEL=0  "; 
		List<?> list = sysEqpCategoryDao.queryBySql(countSql+whereSql.toString(), null);//只会有一条记录
		if(null!=list&&list.size()>0){
			Object arr=(Object) list.get(0);//返回一个对象，而不是 一组数组
			total =Long.parseLong(ObjectUtils.toString(arr));
		}
		//===================================================================================================
		if(total>0){
			StringBuffer lastSql = new StringBuffer();	
			lastSql.append("select * from ( ");
			lastSql.append("	    select row_number() over(ORDER BY CODE DESC) as rownumber,o.ID,o.code,o.NAME,o.DES,o.ENABLE,o.DEl,o.UPLOAD_ID, ");
			lastSql.append("	    doc.ID DOC_ID,doc.FILE_ID,doc.FILE_NAME "); 
			lastSql.append("	    from SYS_EQP_CATEGORY o left  join DOC_FILEMANAGE doc on o.UPLOAD_ID=doc.ID  and doc.IS_DELETED=0 ");
			lastSql.append("	    where o.DEL=0").append(whereSql.toString());
			lastSql.append("	)temp1 where  temp1.rownumber>").append(((pageParams.getPage()-1)*pageParams.getRows()))
			.append(" and temp1.rownumber<=").append((pageParams.getPage()*pageParams.getRows())).append(" ");
			list = sysEqpCategoryDao.queryBySql(lastSql.toString(), null);
			if(null!=list&&list.size()>0){
				SysEqpCategoryBean bean = null;
				for(int i=0;i<list.size();i++){
					bean = new SysEqpCategoryBean();
					Object[] arr=(Object[]) list.get(i);
					bean.setId(ObjectUtils.toString(arr[1]));
					bean.setCode(ObjectUtils.toString(arr[2]));
					bean.setName(ObjectUtils.toString(arr[3]));
					bean.setDes(ObjectUtils.toString(arr[4]));
					String enable = ObjectUtils.toString(arr[5]);
					if(null!=enable&&!"".equals(enable)){
						bean.setEnable(Long.parseLong(enable));
					}
					bean.setDel(ObjectUtils.toString(arr[6]));
					//bean.setIsUploadFile(ObjectUtils.toString(arr[1]));//是否上传成功
					//bean.setSecurityLevel(ObjectUtils.toString(arr[1]));//安全级别
					//bean.setOmagesVersion(ObjectUtils.toString(arr[1]));//版本号
					bean.setUplaodId(ObjectUtils.toString(arr[8]));
					bean.setUploadUrl(ObjectUtils.toString(arr[9]));
					bean.setUploadName(ObjectUtils.toString(arr[10]));
					lastRows.add(bean);
				}
			}
		}
		return new DataGrid(lastRows, total);
	}

	@Override
	public void deleteMdCategory(String id) throws Exception {
		//sysEqpCategoryDao.findById(SysEqpCategory.class, id).setDel("1");
		String sql = "delete from SYS_EQP_CATEGORY where ID='"+id+"'";
		sysEqpCategoryDao.updateBySql(sql, null);
		String typesql = "delete from SYS_EQP_TYPE where CID='"+id+"'";
		sysEqpCategoryDao.updateBySql(typesql, null);
		comboboxServiceI.initCombobox(ComboboxType.EQPCATEGORY);
	}

	@Override
	public List<SysEqpCategoryBean> queryMdCategory() throws Exception {
		String hql = "from SysEqpCategory o where 1=1 and o.enable=1 and o.del=0";
		List<SysEqpCategoryBean> mdeqpcategropbean=beanConvertor.copyList(sysEqpCategoryDao.query(hql, new Object[]{}), SysEqpCategoryBean.class);
		return mdeqpcategropbean;
	}

	@Override
	public void updateCategory(SysEqpCategoryBean mdEqpCategoryBean) throws Exception {
		mdEqpCategoryBean.setDel("0");
		sysEqpCategoryDao.saveOrUpdate(BeanConvertor.copyProperties(mdEqpCategoryBean, SysEqpCategory.class));
		//comboboxServiceI.initCombobox(ComboboxType.EQPCATEGORY);
	}
	

	@Override
	public DocFilemanage fileUpdate(String otherTableId,HttpSession session, HttpServletRequest request,
			SysEqpCategoryBean fmBean) throws Exception {
		request.setCharacterEncoding("utf-8");
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		//获取文件名
		String[] fileUrls = (String[])request.getParameterValues("fileUrl")[0].split(",");
		String[] fileNames = (String[])request.getParameterValues("fileName")[0].split(",");
		//删除原有图片 保证图片唯一
		String updateSql ="update DOC_FILEMANAGE set IS_DELETED=1  where TABLE_ZJ_ID=? ";
		List<Object> objs = new ArrayList<Object>();
		objs.add(otherTableId);
		docFileManageDao.updateBySql(updateSql, objs);
		
		Long l = Long.valueOf(request.getParameter("securityLevel"));
		DocFilemanage file = null;
		for (int i = 0; i < fileUrls.length; i++) {//这里应该只有一张图片上传
			String uuIdKey = UUID.randomUUID().toString() ;
			file = new DocFilemanage();
			file.setAttr1(fmBean.getImagesVersion());//版本号
			file.setCreatePersonId(sessionInfo.getUser().getId());//新增用户
			file.setCreateName(sessionInfo.getUser().getName());
			file.setCreateTime(new Date());
			file.setIsDeleted("0");
			file.setSecurityLevel(l);
			file.setFileId(fileUrls[i]);
			file.setFileName(fileNames[i].substring(0,fileNames[i].lastIndexOf(".")));
			file.setFileType(fileUrls[i].substring(fileUrls[i].lastIndexOf(".")+1));
			file.setId(uuIdKey);
			file.setTableZjId(otherTableId);
			String key = docFileManageDao.saveBackKey(file);
			file.setId(key);
			file.setTableZjId(otherTableId);
		}
		//更新它下面的 所有子项为 null
		updateSql ="update SYS_EQP_TYPE set SET_IMAGE_POINT=null  where CID=? and DEL=0 ";
		objs = new ArrayList<Object>();
		objs.add(otherTableId);
		docFileManageDao.updateBySql(updateSql, objs);
		return file;
	}
	@Override
	public int updateCategory(DocFilemanage file){
		//回写主表 主键 和 上传的地址
		String updateSql ="update SYS_EQP_CATEGORY set UPLOAD_ID=? where ID=? ";
		List<Object> prams = new ArrayList<Object>();
		prams.add(file.getId());prams.add(file.getTableZjId());
		return docFileManageDao.updateBySql(updateSql, prams);
	}
	
	
	
}
