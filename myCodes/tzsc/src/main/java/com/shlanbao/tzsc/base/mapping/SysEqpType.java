package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * SysEqpType entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SysEqpType  implements java.io.Serializable {
    // Fields
     private String id;
     private SysEqpCategory sysEqpCategory;
     private String code;
     private String name;
     private String des;
     private Long enable;
     private String del;
     private String lxType;//保养类型角色
     
     private String number;//数量
     private String fillQuantity;//加注量
     //private String oilId;//润 滑 油（脂）
     //private String fillUnit;//加注量单位
     //private String fashion;//方式
     private EqmLubricantMaintain oilIdBean;//润 滑 油（脂）
     private SysEqpType fillUnitBean;//加注量单位
     private SysEqpType fashionBean;//方式
     
     private String djType;//点检类型角色
     private String djMethodId;//点检方法
     private String setImagePoint;//设置的图片点信息
     private String filePath;//文件路径
     private String rhPart;//润滑部位
     private Integer period;//润滑周期
     private String unit_id;//周期单位

     
     
	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	@SuppressWarnings("rawtypes")
	private Set sysEqpTypesForFillUnit = new HashSet(0);
 	@SuppressWarnings("rawtypes")
	private Set sysEqpTypesForFashion = new HashSet(0);
   
    // Constructors

    /** default constructor */
    public SysEqpType() {
    }
    
    public SysEqpType(String id){
    	super();
    	this.id = id;
    }
    
    public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	/** full constructor */
	public SysEqpType(SysEqpCategory sysEqpCategory, String code, String name,
			String des, Long enable, String del,String lxType) {
		this.sysEqpCategory = sysEqpCategory;
        this.code = code;
        this.name = name;
        this.des = des;
        this.enable = enable;
        this.del = del;
        this.lxType = lxType;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

	public SysEqpCategory getSysEqpCategory() {
		return sysEqpCategory;
	}

	public void setSysEqpCategory(SysEqpCategory sysEqpCategory) {
		this.sysEqpCategory = sysEqpCategory;
	}

	public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }

    public Long getEnable() {
        return this.enable;
    }
    
    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public String getDel() {
        return this.del;
    }
    
    public void setDel(String del) {
        this.del = del;
    }

	public String getLxType() {
		return lxType;
	}

	public void setLxType(String lxType) {
		this.lxType = lxType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFillQuantity() {
		return fillQuantity;
	}

	public void setFillQuantity(String fillQuantity) {
		this.fillQuantity = fillQuantity;
	}
	public EqmLubricantMaintain getOilIdBean() {
		return oilIdBean;
	}

	public void setOilIdBean(EqmLubricantMaintain oilIdBean) {
		this.oilIdBean = oilIdBean;
	}

	public SysEqpType getFillUnitBean() {
		return fillUnitBean;
	}

	public void setFillUnitBean(SysEqpType fillUnitBean) {
		this.fillUnitBean = fillUnitBean;
	}

	public SysEqpType getFashionBean() {
		return fashionBean;
	}

	public void setFashionBean(SysEqpType fashionBean) {
		this.fashionBean = fashionBean;
	}

	@SuppressWarnings("rawtypes")
	public Set getSysEqpTypesForFillUnit() {
		return sysEqpTypesForFillUnit;
	}

	@SuppressWarnings("rawtypes")
	public void setSysEqpTypesForFillUnit(Set sysEqpTypesForFillUnit) {
		this.sysEqpTypesForFillUnit = sysEqpTypesForFillUnit;
	}

	@SuppressWarnings("rawtypes")
	public Set getSysEqpTypesForFashion() {
		return sysEqpTypesForFashion;
	}

	@SuppressWarnings("rawtypes")
	public void setSysEqpTypesForFashion(Set sysEqpTypesForFashion) {
		this.sysEqpTypesForFashion = sysEqpTypesForFashion;
	}

	public String getDjType() {
		return djType;
	}

	public void setDjType(String djType) {
		this.djType = djType;
	}

	public String getDjMethodId() {
		return djMethodId;
	}

	public void setDjMethodId(String djMethodId) {
		this.djMethodId = djMethodId;
	}

	public String getSetImagePoint() {
		return setImagePoint;
	}

	public void setSetImagePoint(String setImagePoint) {
		this.setImagePoint = setImagePoint;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	 public String getRhPart() {
			return rhPart;
	}

		public void setRhPart(String rhPart) {
			this.rhPart = rhPart;
	}
	
}