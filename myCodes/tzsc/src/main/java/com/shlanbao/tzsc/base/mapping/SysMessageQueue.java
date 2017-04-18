package com.shlanbao.tzsc.base.mapping;

import java.util.Date;


/**
 * SysMessageQueue entity. @author MyEclipse Persistence Tools
 */

public class SysMessageQueue  implements java.io.Serializable {


    // Fields    

     private String id;
     private Long msgType;
     private String des;
     private String content;
     private Long sysSend;
     private Long sysReceive;
     private Date date_;
     private Long del;
     private Integer flag;//1成功或0失败

    // Constructors

    /** default constructor */
    public SysMessageQueue() {
    }

    
    /** full constructor */
    public SysMessageQueue(Long msgType, String content, Long sysSend, Long sysReceive, Date date, Long del) {
        this.msgType = msgType;
        this.content = content;
        this.sysSend = sysSend;
        this.sysReceive = sysReceive;
        this.date_ = date;
        this.del = del;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public void setId(String id) {
        this.id = id;
    }

    public Long getMsgType() {
        return this.msgType;
    }
    
    public void setMsgType(Long msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public Long getSysSend() {
        return this.sysSend;
    }
    
    public void setSysSend(Long sysSend) {
        this.sysSend = sysSend;
    }

    public Long getSysReceive() {
        return this.sysReceive;
    }
    
    public void setSysReceive(Long sysReceive) {
        this.sysReceive = sysReceive;
    }

   

    public Date getDate_() {
		return date_;
	}


	public void setDate_(Date date_) {
		this.date_ = date_;
	}


	public Long getDel() {
        return this.del;
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