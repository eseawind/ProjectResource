package com.shlanbao.tzsc.pms.sys.loadjsp.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class IndexfileBean {
	private String id;
	private String fileName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String createTime;
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
