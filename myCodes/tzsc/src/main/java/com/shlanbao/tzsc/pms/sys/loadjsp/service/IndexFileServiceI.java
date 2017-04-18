package com.shlanbao.tzsc.pms.sys.loadjsp.service;

import java.util.List;

import com.shlanbao.tzsc.pms.sys.loadjsp.beans.IndexfileBean;

public interface IndexFileServiceI {
	public List<IndexfileBean> getfileAll() throws Exception;
}
