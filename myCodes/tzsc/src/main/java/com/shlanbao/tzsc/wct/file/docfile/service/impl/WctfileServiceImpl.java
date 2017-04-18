package com.shlanbao.tzsc.wct.file.docfile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.DocFileDaoI;
import com.shlanbao.tzsc.base.mapping.DocFile;
import com.shlanbao.tzsc.base.mapping.QmProcFile;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.file.docfile.beans.WctFileBean;
import com.shlanbao.tzsc.wct.file.docfile.beans.WctTreeBean;
import com.shlanbao.tzsc.wct.file.docfile.service.WctfileServiceI;
import com.shlanbao.tzsc.wct.qm.proc.beans.QmProcWctFileBean;
@Service
public class WctfileServiceImpl extends BaseService implements WctfileServiceI {
	@Autowired
	private DocFileDaoI docFileDaoI;
	
	/**
	 *查询字节点 
	 */
	@Override
	public List<WctTreeBean> getByPid(String pid) {
		//如果非空查询子节点
		List<WctTreeBean> treeBeans=new ArrayList<WctTreeBean>();
		if(StringUtil.notNull(pid)){
			 List<DocFile> docFiels= docFileDaoI.query("from DocFile d where d.attr='0'and d.docFile.id='"+pid+"' order by d.type asc");
			 if(docFiels!=null){
				 for (DocFile docFile : docFiels) {
					 WctTreeBean treeBean=new WctTreeBean();
					 treeBean.setId(docFile.getId());
					 treeBean.setName(docFile.getFilename());
					 treeBean.setPid(docFile.getDocFile().getId());
					 treeBean.setType(docFile.getType());
					 treeBeans.add(treeBean);
				}
				 
			 }
		}else{
			List<DocFile> docFiels= docFileDaoI.query("from DocFile d where d.attr='0'and d.docFile is null order by d.type asc");
			 if(docFiels!=null){
				 for (DocFile docFile : docFiels) {
					 WctTreeBean treeBean=new WctTreeBean();
					 treeBean.setId(docFile.getId());
					 treeBean.setName(docFile.getFilename());
					 treeBean.setPid(null);
					 treeBean.setType(docFile.getType());
					 treeBeans.add(treeBean);
				}
				 
			 }
		}
		return treeBeans;
	}
	@Override
	public WctFileBean getById(String id) throws Exception {
		DocFile docFile =docFileDaoI.findById(DocFile.class, id);
		return BeanConvertor.copyProperties(docFile, WctFileBean.class);
	}
	@Override
	public DataGrid getTreeList(String filename,int pageIndex) throws Exception {
		StringBuffer hql=new StringBuffer();
		hql.append("from DocFile  where  attr=0  and type=2 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(filename)){//文件名
			hql.append(" and filename like ? ");
			params.add("%"+filename+"%");
		}
		try {
			long total=docFileDaoI.queryTotal("select count(*) "+hql.toString(),params);
			List<WctFileBean> list= new ArrayList<WctFileBean>();
			if(total>0){
				hql.append(" order by createTime desc ");//排序
				
				List<DocFile> rows = docFileDaoI.queryByPage(hql.toString(), pageIndex, 10, params);
				
				for(DocFile file:rows){
					WctFileBean manageBean = BeanConvertor.copyProperties(file, WctFileBean.class);
					list.add(manageBean);
				}
			}
			return new DataGrid(list, total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
		
	
	}

	
}
