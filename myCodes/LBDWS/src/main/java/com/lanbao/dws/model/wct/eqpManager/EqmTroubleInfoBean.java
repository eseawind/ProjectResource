package com.lanbao.dws.model.wct.eqpManager;

public class EqmTroubleInfoBean {
	private String id;
	private String nodeCode;//故障代码
	private String nodeDesc;//故障描述
	private Integer nodeType;//故障种类，级标题，
	private String nodePid;//父节点
	
	private String sourceId;//来源id，用于查询来源信息，补充故障信息
	
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodePid() {
		return nodePid;
	}
	public void setNodePid(String nodePid) {
		this.nodePid = nodePid;
	}
}
