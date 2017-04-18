package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * SchWorkorder entity. @author MyEclipse Persistence Tools
 */

public class SchWorkorder  implements java.io.Serializable {


    // Fields    

     private String id;
     private String loginUserId;//登陆人id
     private String loginName;//登陆人
     private MdUnit mdUnit;//单位信息
     private MdEquipment mdEquipment;//设备信息
     private MdTeam mdTeam;	//班组信息
     private MdShift mdShift;//班次信息
     private MdMat mdMat;//牌号信息
     private String erpCode;//erp订单号
     private String code;
     private String bth;
     private Long isNew;
     private Long type;
     private Long prodType;
     private Double qty;
     @DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
     private Date date;
     @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
     private Date stim;
     @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
     private Date etim;
     private String bomVersion;
     private Long sts;
     private Long isCheck;
     private Date checkTime;
     private Date realStim;
     private Date realEtim;
     private Long runSeq;
     private Date recvTime;
     private Long enable;
     private Long del;
     private String isAuto;//是否自动运行 add by luther.zhang 20150417
     private Set schWorkorderCrafts = new HashSet(0);
     private Set qmSelfCheckCigarettes = new HashSet(0);
     private Set schWorkorderBoms = new HashSet(0);
     private Set qmSelfCheckStrips = new HashSet(0);
     private Set schStatOutputs = new HashSet(0);
     private Set msgQmWarns = new HashSet(0);
     private Set qmSelfCheckFilters = new HashSet(0);
     private Set qmSelfCheckCartonses = new HashSet(0);
     private Set qmOutwards = new HashSet(0);
     private Set msgConWarns = new HashSet(0);
     private Set qmSelfCheckPackets = new HashSet(0);
     
     private String erp_code; //erp批次号
     
     

    // Constructors

    public String getErp_code() {
		return erp_code;
	}



	public void setErp_code(String erp_code) {
		this.erp_code = erp_code;
	}



	/** default constructor */
    public SchWorkorder() {
    }

    
    
    public SchWorkorder(String id) {
		super();
		this.id = id;
	}



	/** full constructor */
    public SchWorkorder(MdUnit mdUnit, MdEquipment mdEquipment, MdTeam mdTeam, MdShift mdShift, MdMat mdMat, String code, String bth, Long isNew, Long type, Long prodType, Double qty, Date date, Date stim, Date etim, String bomVersion, Long sts, Long isCheck, Date checkTime, Date realStim, Date realEtim, Long runSeq, Date recvTime, Long enable, Long del, Set schWorkorderCrafts, Set qmSelfCheckCigarettes, Set schWorkorderBoms, Set qmSelfCheckStrips, Set schStatOutputs, Set msgQmWarns, Set qmSelfCheckFilters, Set qmSelfCheckCartonses, Set qmOutwards, Set msgConWarns, Set qmSelfCheckPackets) {
        this.mdUnit = mdUnit;
        this.mdEquipment = mdEquipment;
        this.mdTeam = mdTeam;
        this.mdShift = mdShift;
        this.mdMat = mdMat;
        this.code = code;
        this.bth = bth;
        this.isNew = isNew;
        this.type = type;
        this.prodType = prodType;
        this.qty = qty;
        this.date = date;
        this.stim = stim;
        this.etim = etim;
        this.bomVersion = bomVersion;
        this.sts = sts;
        this.isCheck = isCheck;
        this.checkTime = checkTime;
        this.realStim = realStim;
        this.realEtim = realEtim;
        this.runSeq = runSeq;
        this.recvTime = recvTime;
        this.enable = enable;
        this.del = del;
        this.schWorkorderCrafts = schWorkorderCrafts;
        this.qmSelfCheckCigarettes = qmSelfCheckCigarettes;
        this.schWorkorderBoms = schWorkorderBoms;
        this.qmSelfCheckStrips = qmSelfCheckStrips;
        this.schStatOutputs = schStatOutputs;
        this.msgQmWarns = msgQmWarns;
        this.qmSelfCheckFilters = qmSelfCheckFilters;
        this.qmSelfCheckCartonses = qmSelfCheckCartonses;
        this.qmOutwards = qmOutwards;
        this.msgConWarns = msgConWarns;
        this.qmSelfCheckPackets = qmSelfCheckPackets;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    



	public String getErpCode() {
		return erpCode;
	}



	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}



	public void setId(String id) {
        this.id = id;
    }

    public MdUnit getMdUnit() {
        return this.mdUnit;
    }
    
    public String getLoginName() {
		return loginName;
	}



	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	public void setMdUnit(MdUnit mdUnit) {
        this.mdUnit = mdUnit;
    }

    public String getLoginUserId() {
		return loginUserId;
	}



	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}



	public MdEquipment getMdEquipment() {
        return this.mdEquipment;
    }
    
    public void setMdEquipment(MdEquipment mdEquipment) {
        this.mdEquipment = mdEquipment;
    }

    public MdTeam getMdTeam() {
        return this.mdTeam;
    }
    
    public void setMdTeam(MdTeam mdTeam) {
        this.mdTeam = mdTeam;
    }

    public MdShift getMdShift() {
        return this.mdShift;
    }
    
    public void setMdShift(MdShift mdShift) {
        this.mdShift = mdShift;
    }

    public MdMat getMdMat() {
        return this.mdMat;
    }
    
    public void setMdMat(MdMat mdMat) {
        this.mdMat = mdMat;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getBth() {
        return this.bth;
    }
    
    public void setBth(String bth) {
        this.bth = bth;
    }

    public Long getIsNew() {
        return this.isNew;
    }
    
    public void setIsNew(Long isNew) {
        this.isNew = isNew;
    }

    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }

    public Long getProdType() {
        return this.prodType;
    }
    
    public void setProdType(Long prodType) {
        this.prodType = prodType;
    }

    public Double getQty() {
        return this.qty;
    }
    
    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStim() {
        return this.stim;
    }
    
    public void setStim(Date stim) {
        this.stim = stim;
    }

    public Date getEtim() {
        return this.etim;
    }
    
    public void setEtim(Date etim) {
        this.etim = etim;
    }

    public String getBomVersion() {
        return this.bomVersion;
    }
    
    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Long getSts() {
        return this.sts;
    }
    
    public void setSts(Long sts) {
        this.sts = sts;
    }

    public Long getIsCheck() {
        return this.isCheck;
    }
    
    public void setIsCheck(Long isCheck) {
        this.isCheck = isCheck;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }
    
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getRealStim() {
        return this.realStim;
    }
    
    public void setRealStim(Date realStim) {
        this.realStim = realStim;
    }

    public Date getRealEtim() {
        return this.realEtim;
    }
    
    public void setRealEtim(Date realEtim) {
        this.realEtim = realEtim;
    }

    public Long getRunSeq() {
        return this.runSeq;
    }
    
    public void setRunSeq(Long runSeq) {
        this.runSeq = runSeq;
    }

    public Date getRecvTime() {
        return this.recvTime;
    }
    
    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

    public Long getEnable() {
        return this.enable;
    }
    
    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }

    public Set getSchWorkorderCrafts() {
        return this.schWorkorderCrafts;
    }
    
    public void setSchWorkorderCrafts(Set schWorkorderCrafts) {
        this.schWorkorderCrafts = schWorkorderCrafts;
    }

    public Set getQmSelfCheckCigarettes() {
        return this.qmSelfCheckCigarettes;
    }
    
    public void setQmSelfCheckCigarettes(Set qmSelfCheckCigarettes) {
        this.qmSelfCheckCigarettes = qmSelfCheckCigarettes;
    }

    public Set getSchWorkorderBoms() {
        return this.schWorkorderBoms;
    }
    
    public void setSchWorkorderBoms(Set schWorkorderBoms) {
        this.schWorkorderBoms = schWorkorderBoms;
    }

    public Set getQmSelfCheckStrips() {
        return this.qmSelfCheckStrips;
    }
    
    public void setQmSelfCheckStrips(Set qmSelfCheckStrips) {
        this.qmSelfCheckStrips = qmSelfCheckStrips;
    }

    public Set getSchStatOutputs() {
        return this.schStatOutputs;
    }
    
    public void setSchStatOutputs(Set schStatOutputs) {
        this.schStatOutputs = schStatOutputs;
    }

    public Set getMsgQmWarns() {
        return this.msgQmWarns;
    }
    
    public void setMsgQmWarns(Set msgQmWarns) {
        this.msgQmWarns = msgQmWarns;
    }

    public Set getQmSelfCheckFilters() {
        return this.qmSelfCheckFilters;
    }
    
    public void setQmSelfCheckFilters(Set qmSelfCheckFilters) {
        this.qmSelfCheckFilters = qmSelfCheckFilters;
    }

    public Set getQmSelfCheckCartonses() {
        return this.qmSelfCheckCartonses;
    }
    
    public void setQmSelfCheckCartonses(Set qmSelfCheckCartonses) {
        this.qmSelfCheckCartonses = qmSelfCheckCartonses;
    }

    public Set getQmOutwards() {
        return this.qmOutwards;
    }
    
    public void setQmOutwards(Set qmOutwards) {
        this.qmOutwards = qmOutwards;
    }

    public Set getMsgConWarns() {
        return this.msgConWarns;
    }
    
    public void setMsgConWarns(Set msgConWarns) {
        this.msgConWarns = msgConWarns;
    }

    public Set getQmSelfCheckPackets() {
        return this.qmSelfCheckPackets;
    }
    
    public void setQmSelfCheckPackets(Set qmSelfCheckPackets) {
        this.qmSelfCheckPackets = qmSelfCheckPackets;
    }
    
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

}