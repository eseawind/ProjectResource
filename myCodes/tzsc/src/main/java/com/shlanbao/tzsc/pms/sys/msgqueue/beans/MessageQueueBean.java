package com.shlanbao.tzsc.pms.sys.msgqueue.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


public class MessageQueueBean {
	private String id;
    private Long msgType;//0收1发
    private String des;
    private String content;
    private Long sysSend;
    private Long sysReceive;
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String date_;
    private Long del;
    private Integer flag;
    
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getMsgType() {
		return msgType;
	}
	public void setMsgType(Long msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getSysSend() {
		return sysSend;
	}
	public void setSysSend(Long sysSend) {
		this.sysSend = sysSend;
	}
	public Long getSysReceive() {
		return sysReceive;
	}
	public void setSysReceive(Long sysReceive) {
		this.sysReceive = sysReceive;
	}
	
	public String getDate_() {
		return date_;
	}
	public void setDate_(String date_) {
		this.date_ = date_;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
}
