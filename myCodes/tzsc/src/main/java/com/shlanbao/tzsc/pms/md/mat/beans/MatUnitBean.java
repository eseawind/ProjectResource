package com.shlanbao.tzsc.pms.md.mat.beans;

public class MatUnitBean {
	
	private String id;//单位编号
	
	private String name;//单位名称
	
	private String unitcode;//单位编码
	
	private String matcode;//辅料code
	
	private String mattypeid;//辅料所属类编号md_mat_type的id
	
	private String matclass;//辅料所属类GRP中des
	
	private String mattypegrpid;//辅料所属类GRP编号id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitcode() {
		return unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getMatcode() {
		return matcode;
	}

	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	
	public String getMattypeid() {
		return mattypeid;
	}

	public void setMattypeid(String mattypeid) {
		this.mattypeid = mattypeid;
	}

	public String getMatclass() {
		return matclass;
	}

	public void setMatclass(String matclass) {
		this.matclass = matclass;
	}
	
	public String getMattypegrpid() {
		return mattypegrpid;
	}

	public void setMattypegrpid(String mattypegrpid) {
		this.mattypegrpid = mattypegrpid;
	}

	public MatUnitBean() {}

	public MatUnitBean(String id, String name, String unitcode, String matcode, String mattypeid, String matclass,
			String mattypegrpid) {
		super();
		this.id = id;
		this.name = name;
		this.unitcode = unitcode;
		this.matcode = matcode;
		this.mattypeid = mattypeid;
		this.matclass = matclass;
		this.mattypegrpid = mattypegrpid;
	}
	
}
