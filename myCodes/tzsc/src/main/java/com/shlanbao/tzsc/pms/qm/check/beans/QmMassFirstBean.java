package com.shlanbao.tzsc.pms.qm.check.beans;
import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
* @ClassName: QmMassFirstBean 
* @Description: 首检记录 
* @author luo 
* @date 2015年10月20日 下午2:31:20 
*
 */
public class QmMassFirstBean{
	private String qmFirstId;
	private String addUserId;//创建人
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyTime;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String deleteUserTime;//删除时间
	private String deleteUserId;//删除人ID
	private String isDelete;
	private String checkTime;//检查时间
	private String checkMatter;//首检原因
	private String checkWeight;//重量
	private String checkCondition;//外观质量情况
	private String checkStep;//处理措施
	
	private String qmCheckId;//质量自检ID
	private String mdShiftId;//班次ID
	private String mdShiftName;
	private String mdMatId;//牌号ID
	private String mdMatName;
	private String mdEqmentId;//机台号(设备)ID
	private String mdEqmentName;
	private String mdDcgId;
	private String mdDcgName;//挡车工姓名
	private String proWorkId;//工单主键ID
	private String zjUserName;//质检员
	
	private String addUserName;//添加人名
	private String checkType;//检查类型1：操作工，2：质检员，3：工段长
	private int failureNum;//不合格数量  
	private String failureUom;//不合格单位
	
	public int getFailureNum() {
		return failureNum;
	}
	public void setFailureNum(int failureNum) {
		this.failureNum = failureNum;
	}
	public String getFailureUom() {
		return failureUom;
	}
	public void setFailureUom(String failureUom) {
		this.failureUom = failureUom;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	private String pageSort;//1、按照首检日期排序，2、按照质检员复检日期排序，3、按照工段长复检日期排序
	
	public String getDeleteUserTime() {
		return deleteUserTime;
	}
	public void setDeleteUserTime(String deleteUserTime) {
		this.deleteUserTime = deleteUserTime;
	}
	public String getDeleteUserId() {
		return deleteUserId;
	}
	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}
	public String getPageSort() {
		return pageSort;
	}
	public void setPageSort(String pageSort) {
		this.pageSort = pageSort;
	}
	public String getQmFirstId() {
		return qmFirstId;
	}
	public void setQmFirstId(String qmFirstId) {
		this.qmFirstId = qmFirstId;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public String getAddUserTime() {
		return addUserTime;
	}
	public void setAddUserTime(String addUserTime) {
		this.addUserTime = addUserTime;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckMatter() {
		return checkMatter;
	}
	public void setCheckMatter(String checkMatter) {
		this.checkMatter = checkMatter;
	}
	public String getCheckWeight() {
		return checkWeight;
	}
	public void setCheckWeight(String checkWeight) {
		this.checkWeight = checkWeight;
	}
	public String getCheckCondition() {
		return checkCondition;
	}
	public void setCheckCondition(String checkCondition) {
		this.checkCondition = checkCondition;
	}
	public String getCheckStep() {
		return checkStep;
	}
	public void setCheckStep(String checkStep) {
		this.checkStep = checkStep;
	}
	public String getQmCheckId() {
		return qmCheckId;
	}
	public void setQmCheckId(String qmCheckId) {
		this.qmCheckId = qmCheckId;
	}
	public String getMdShiftId() {
		return mdShiftId;
	}
	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}
	public String getMdShiftName() {
		return mdShiftName;
	}
	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}
	public String getMdMatId() {
		return mdMatId;
	}
	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}
	public String getMdMatName() {
		return mdMatName;
	}
	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}
	public String getMdEqmentId() {
		return mdEqmentId;
	}
	public void setMdEqmentId(String mdEqmentId) {
		this.mdEqmentId = mdEqmentId;
	}
	public String getMdEqmentName() {
		return mdEqmentName;
	}
	public void setMdEqmentName(String mdEqmentName) {
		this.mdEqmentName = mdEqmentName;
	}
	public String getMdDcgId() {
		return mdDcgId;
	}
	public void setMdDcgId(String mdDcgId) {
		this.mdDcgId = mdDcgId;
	}
	public String getMdDcgName() {
		return mdDcgName;
	}
	public void setMdDcgName(String mdDcgName) {
		this.mdDcgName = mdDcgName;
	}
	public String getProWorkId() {
		return proWorkId;
	}
	public void setProWorkId(String proWorkId) {
		this.proWorkId = proWorkId;
	}
	public String getZjUserName() {
		return zjUserName;
	}
	public void setZjUserName(String zjUserName) {
		this.zjUserName = zjUserName;
	}
	
}
