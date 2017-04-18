package com.shlanbao.tzsc.pms.equ.lubricate.beans;


public class EqmLubricantPlanBean {
		private String id;//ID	
		private String code;//code
		private String eqp_id;//设备Id  
		private String eqp_name;//设备名称
		private String date_plan;//计划执行日期
		private String operatives_id;//轮保工确认人id
		private String operatives_name;//轮保工确认人name
		private String lubricating_id;//润滑工确认人id
		private String lubricating_name;//润滑工确认人name
		private int status;//状态
		private String etims;//结束时间
		private String remark;//备注
		private String lub_id;//轮保周期id
		private String attr1;//备用字段一
		private String attr2;//备用字段二
		private String date_plan1;//查询开始日期
		private String date_plan2;//查询结束日期
		private String shift_name;//查询结束日期
		
		
		public String getShift_name() {
			return shift_name;
		}
		public void setShift_name(String shift_name) {
			this.shift_name = shift_name;
		}
		public EqmLubricantPlanBean(){}
		public EqmLubricantPlanBean(String id){
			this.id=id;
		}
		
		public String getDate_plan1() {
			return date_plan1;
		}
		public void setDate_plan1(String date_plan1) {
			this.date_plan1 = date_plan1;
		}
		public String getDate_plan2() {
			return date_plan2;
		}
		public void setDate_plan2(String date_plan2) {
			this.date_plan2 = date_plan2;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getEqp_name() {
			return eqp_name;
		}
		public void setEqp_name(String eqp_name) {
			this.eqp_name = eqp_name;
		}
		public String getId() {
			return id;
		}
		
		public String getAttr1() {
			return attr1;
		}
		public void setAttr1(String attr1) {
			this.attr1 = attr1;
		}
		public String getAttr2() {
			return attr2;
		}
		public void setAttr2(String attr2) {
			this.attr2 = attr2;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getEqp_id() {
			return eqp_id;
		}
		public void setEqp_id(String eqp_id) {
			this.eqp_id = eqp_id;
		}
		
		
		
		public String getDate_plan() {
			return date_plan;
		}
		public void setDate_plan(String date_plan) {
			this.date_plan = date_plan;
		}
		public String getOperatives_id() {
			return operatives_id;
		}
		public void setOperatives_id(String operatives_id) {
			this.operatives_id = operatives_id;
		}
		public String getOperatives_name() {
			return operatives_name;
		}
		public void setOperatives_name(String operatives_name) {
			this.operatives_name = operatives_name;
		}
		public String getLubricating_id() {
			return lubricating_id;
		}
		public void setLubricating_id(String lubricating_id) {
			this.lubricating_id = lubricating_id;
		}
		public String getLubricating_name() {
			return lubricating_name;
		}
		public void setLubricating_name(String lubricating_name) {
			this.lubricating_name = lubricating_name;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
		public String getEtims() {
			return etims;
		}
		public void setEtims(String etims) {
			this.etims = etims;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getLub_id() {
			return lub_id;
		}
		public void setLub_id(String lub_id) {
			this.lub_id = lub_id;
		}
		
		
	}
