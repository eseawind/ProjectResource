package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MsgInfo entity. @author MyEclipse Persistence Tools
 */

public class MsgInfo implements java.io.Serializable {


    // Fields    

     private String id;
     
     private MsgInfo msgInfo;
     
     private SysUser sysUserByApproval;
     private SysUser sysUserByInitiator;
     private SysUser sysUserByIssuer;
     
     
     private Date time;
     private String content;
     private String eid;
     private Long flag;
     private Long del;
     private String title;
     private String noticeObject;
     private String approveContent;
     private Date approveTime;
     private Set msgInfos = new HashSet(0);


    // Constructors

    /** default constructor */
    public MsgInfo() {
    }

    
    /** full constructor */
    public MsgInfo(SysUser sysUserByApproval, SysUser sysUserByInitiator, MsgInfo msgInfo, SysUser sysUserByIssuer, Date time, String content, String eid, Long flag, Long del, String title, String noticeObject, String approveContent, Date approveTime, Set msgInfos) {
        this.sysUserByApproval = sysUserByApproval;
        this.sysUserByInitiator = sysUserByInitiator;
        this.msgInfo = msgInfo;
        this.sysUserByIssuer = sysUserByIssuer;
        this.time = time;
        this.content = content;
        this.eid = eid;
        this.flag = flag;
        this.del = del;
        this.title = title;
        this.noticeObject = noticeObject;
        this.approveContent = approveContent;
        this.approveTime = approveTime;
        this.msgInfos = msgInfos;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public SysUser getSysUserByApproval() {
        return this.sysUserByApproval;
    }
    
    public void setSysUserByApproval(SysUser sysUserByApproval) {
        this.sysUserByApproval = sysUserByApproval;
    }

    public SysUser getSysUserByInitiator() {
        return this.sysUserByInitiator;
    }
    
    public void setSysUserByInitiator(SysUser sysUserByInitiator) {
        this.sysUserByInitiator = sysUserByInitiator;
    }

    public MsgInfo getMsgInfo() {
        return this.msgInfo;
    }
    
    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public SysUser getSysUserByIssuer() {
        return this.sysUserByIssuer;
    }
    
    public void setSysUserByIssuer(SysUser sysUserByIssuer) {
        this.sysUserByIssuer = sysUserByIssuer;
    }

    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getEid() {
        return this.eid;
    }
    
    public void setEid(String eid) {
        this.eid = eid;
    }

    public Long getFlag() {
        return this.flag;
    }
    
    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoticeObject() {
        return this.noticeObject;
    }
    
    public void setNoticeObject(String noticeObject) {
        this.noticeObject = noticeObject;
    }

    public String getApproveContent() {
        return this.approveContent;
    }
    
    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    public Date getApproveTime() {
        return this.approveTime;
    }
    
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Set getMsgInfos() {
        return this.msgInfos;
    }
    
    public void setMsgInfos(Set msgInfos) {
        this.msgInfos = msgInfos;
    }
   




}