package com.lanbao.dws.model.wct.qm;

import java.util.Date;

import javax.persistence.Entity;
/**
 * 工艺规程
 * @author shisihai
 *
 */
@Entity(name="SCH_WORKORDER_CRAFT")
public class CraftRules {
		private String id;
		private String paramId;//
		private String parameterCode;//工艺参数代码
		private String parameterName;//工艺参数名称
		private Long pcp;//是否过程控制参数 0 是 1否
		private String pcpTxt;
		private Long qc;//是否检验项目0 是 1否
		private String qcTxt;
		private Double pval;//置信水平下限(P值)
		private Double zval;//Z值
		private Double cpk;//CPK值
		private Double cp;//CP值
		private Double pp;//PP值
		private Double ppk;//PPK值
		private Double ppm;//PPM值
		private Double std;//标准值
		private Double uval;//标准值允差上限
		private Double lval;//标准值允差下限
		private Double cuval;//控制线上限
		private Double clval;//控制线下限
		private Double wuval;//警戒线上限
		private Double wlval;//警戒线下限
		private String unit;//单位
		private Long dtm;//是否需要判定不合格	0 是 1否
		private String dtmTxt;
		private Double dtmuval;//不合格上限
		private Double dtmlval;//不合格下限
		private Long paramType;//计数类型	1 计数  2 计量
		private String paramTypeTxt;
		private String effectRemark;//生效备注
		private Date effectTime;//生效日期
		private String parameterSetRevision;//版本号
		private String materialCode;//物料代码id(牌号)
		
		
		public String getPcpTxt() {
			if(getPcp()!=null && getPcp()==0){
				pcpTxt="是";
			}else{
				pcpTxt="否";
			}
			return pcpTxt;
		}
		public void setPcpTxt(String pcpTxt) {
			this.pcpTxt = pcpTxt;
		}
		public String getQcTxt() {
			if(getQc()!=null && getQc()==0){
				qcTxt="是";
			}else{
				qcTxt="否";
			}
			return qcTxt;
		}
		public void setQcpTxt(String qcTxt) {
			this.qcTxt = qcTxt;
		}
		public String getDtmTxt() {
			if(getDtm()!=null && getDtm()==0){
				dtmTxt="是";
			}else{
				dtmTxt="否";
			}
			return dtmTxt;
		}
		public void setDtmTxt(String dtmTxt) {
			this.dtmTxt = dtmTxt;
		}
		public String getParamTypeTxt() {
			if(getParamType()!=null && getParamType()==1){
				paramTypeTxt="计数";
			}else{
				paramTypeTxt="计量";
			}
			return paramTypeTxt;
		}
		public void setParamTypeTxt(String paramTypeTxt) {
			this.paramTypeTxt = paramTypeTxt;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getParamId() {
			return paramId;
		}
		public void setParamId(String paramId) {
			this.paramId = paramId;
		}
		public String getParameterCode() {
			return parameterCode;
		}
		public void setParameterCode(String parameterCode) {
			this.parameterCode = parameterCode;
		}
		public String getParameterName() {
			return parameterName;
		}
		public void setParameterName(String parameterName) {
			this.parameterName = parameterName;
		}
		public Long getPcp() {
			return pcp;
		}
		public void setPcp(Long pcp) {
			this.pcp = pcp;
		}
		public Long getQc() {
			return qc;
		}
		public void setQc(Long qc) {
			this.qc = qc;
		}
		public Double getPval() {
			return pval;
		}
		public void setPval(Double pval) {
			this.pval = pval;
		}
		public Double getZval() {
			return zval;
		}
		public void setZval(Double zval) {
			this.zval = zval;
		}
		public Double getCpk() {
			return cpk;
		}
		public void setCpk(Double cpk) {
			this.cpk = cpk;
		}
		public Double getCp() {
			return cp;
		}
		public void setCp(Double cp) {
			this.cp = cp;
		}
		public Double getPp() {
			return pp;
		}
		public void setPp(Double pp) {
			this.pp = pp;
		}
		public Double getPpk() {
			return ppk;
		}
		public void setPpk(Double ppk) {
			this.ppk = ppk;
		}
		public Double getPpm() {
			return ppm;
		}
		public void setPpm(Double ppm) {
			this.ppm = ppm;
		}
		public Double getStd() {
			return std;
		}
		public void setStd(Double std) {
			this.std = std;
		}
		public Double getUval() {
			return uval;
		}
		public void setUval(Double uval) {
			this.uval = uval;
		}
		public Double getLval() {
			return lval;
		}
		public void setLval(Double lval) {
			this.lval = lval;
		}
		public Double getCuval() {
			return cuval;
		}
		public void setCuval(Double cuval) {
			this.cuval = cuval;
		}
		public Double getClval() {
			return clval;
		}
		public void setClval(Double clval) {
			this.clval = clval;
		}
		public Double getWuval() {
			return wuval;
		}
		public void setWuval(Double wuval) {
			this.wuval = wuval;
		}
		public Double getWlval() {
			return wlval;
		}
		public void setWlval(Double wlval) {
			this.wlval = wlval;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public Long getDtm() {
			return dtm;
		}
		public void setDtm(Long dtm) {
			this.dtm = dtm;
		}
		public Double getDtmuval() {
			return dtmuval;
		}
		public void setDtmuval(Double dtmuval) {
			this.dtmuval = dtmuval;
		}
		public Double getDtmlval() {
			return dtmlval;
		}
		public void setDtmlval(Double dtmlval) {
			this.dtmlval = dtmlval;
		}
		public Long getParamType() {
			return paramType;
		}
		public void setParamType(Long paramType) {
			this.paramType = paramType;
		}
		public String getEffectRemark() {
			return effectRemark;
		}
		public void setEffectRemark(String effectRemark) {
			this.effectRemark = effectRemark;
		}
		public Date getEffectTime() {
			return effectTime;
		}
		public void setEffectTime(Date effectTime) {
			this.effectTime = effectTime;
		}
		public String getParameterSetRevision() {
			return parameterSetRevision;
		}
		public void setParameterSetRevision(String parameterSetRevision) {
			this.parameterSetRevision = parameterSetRevision;
		}
		public String getMaterialCode() {
			return materialCode;
		}
		public void setMaterialCode(String materialCode) {
			this.materialCode = materialCode;
		}
}

