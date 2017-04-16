package com.entity;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dbUtil.ExecuteSql;

/**
 * <p>功能描述：日志类</p>
 *作者：SShi11
 *日期：Apr 6, 2017 1:09:39 PM
 */
public class LoggerBean {
	private final transient String sql="INSERT INTO UTILS_LOGGING (IP, USER, OPERATION, GRADE, CONTENT, REMARK, CREATETIME) values(?,?,?,?,?,?,?)";
	private Integer id;//自增
	private String ip;//ip
	private String user;//用户
	private String operatation;//操作
	private LoggerGrade grade;//等级
	private String content;//内容
	private String remark;//备注
	private Date createTime;//创建时间
	private String simpleTime;//创建时间
	
	
	public LoggerBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoggerBean(String ip, String user, String operatation, LoggerGrade grade, String content, String remark
			) {
		super();
		this.ip = ip;
		this.user = user;
		this.operatation = operatation;
		this.grade = grade;
		this.content = content;
		this.remark = remark;
		this.createTime = new Date();
	}
	
	
	public LoggerBean(LoggerGrade grade, String content,String remark) {
		super();
		this.grade = grade;
		this.content = content;
		this.remark=remark;
		this.createTime = new Date();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getOperatation() {
		return operatation;
	}
	public void setOperatation(String operatation) {
		this.operatation = operatation;
	}
	public LoggerGrade getGrade() {
		return grade;
	}
	public void setGrade(LoggerGrade grade) {
		this.grade = grade;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSimpleTime() {
		try {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			simpleTime=format.format(getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return simpleTime;
	}
	public void setSimpleTime(String simpleTime) {
		this.simpleTime = simpleTime;
	}
	/**
	 * <p>功能描述：保存日志</p>
	 *@param bean
	 *@throws SQLException
	 *作者：SShi11
	 *日期：Apr 6, 2017 1:15:21 PM
	 */
	public void saveLog(LoggerBean bean) {
		try {
			ExecuteSql.execSql(sql,
					bean.getIp(),
					bean.getUser(),
					bean.getOperatation(),
					LoggerGrade.getVal(bean.getGrade()),
					bean.getContent(),
					bean.getRemark(),
					bean.getSimpleTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
