package com.shlanbao.tzsc.pms.sys.loadjsp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.DocFileDaoI;
import com.shlanbao.tzsc.base.dao.DocFileManageDaoI;
import com.shlanbao.tzsc.base.mapping.DocFile;
import com.shlanbao.tzsc.base.mapping.DocFilemanage;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.loadjsp.beans.IndexfileBean;
import com.shlanbao.tzsc.pms.sys.loadjsp.service.IndexFileServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class IndexFileImpl extends BaseService implements IndexFileServiceI {
	@Autowired
	private DocFileDaoI IndexdocfiledaoI;
	@Autowired
	private DocFileManageDaoI IndexfilemanagedaoI;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	@Override
	public List<IndexfileBean> getfileAll() throws Exception {
		List<IndexfileBean> IndexfileBean=new ArrayList<IndexfileBean>();
		String hql="from DocFile where attr='0' and type<>'1'";
		List<DocFile> docFiles=IndexdocfiledaoI.query(hql);
		if(docFiles!=null&&docFiles.size()>0){
			for (DocFile docFile : docFiles) {
				IndexfileBean bean=BeanConvertor.copyProperties(docFile, IndexfileBean.class);
				bean.setFileName(docFile.getFilename());
				bean.setUrl(bundle.getString("service_url")+docFile.getUrl());
				IndexfileBean.add(bean);
			}
			
		}
		String str="from DocFilemanage where isDeleted='0'";
		List<DocFilemanage> docFilemanages=IndexfilemanagedaoI.query(str);
		if(docFilemanages!=null&&docFilemanages.size()>0){
			for (DocFilemanage doc : docFilemanages) {
				IndexfileBean bean=BeanConvertor.copyProperties(doc, IndexfileBean.class);
				bean.setUrl(doc.getFileId());
				IndexfileBean.add(bean);
			}
			
		}	
		return IndexfileBean;
	}

}
