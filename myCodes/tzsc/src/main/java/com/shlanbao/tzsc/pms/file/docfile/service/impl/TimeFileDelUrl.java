package com.shlanbao.tzsc.pms.file.docfile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shlanbao.tzsc.base.dao.DocFileDaoI;
import com.shlanbao.tzsc.base.mapping.DocFile;
import com.shlanbao.tzsc.utils.tools.FileOptionsUtil;

public class TimeFileDelUrl {
	
	@Autowired
	private DocFileDaoI fileDaoI;
	/**
	 * 定时删除文件
	 */
	public void removeFileUrl() {
		String hql="from DocFile where attr='1'";
		List<DocFile> docFiles=fileDaoI.query(hql);
		if(docFiles!=null){
			String url="";
			for (DocFile docFile : docFiles) {
//				url=docFile.getUrl().substring(0,
//						docFile.getUrl().lastIndexOf("."));
					try {
						FileOptionsUtil.deleteFileurl(docFile.getUrl());
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			
		}
	}
}
