package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * MdShift entity. @author MyEclipse Persistence Tools
 */

public class MdShift  implements java.io.Serializable {


    // Fields    

     private String id;
     private MdWorkshop mdWorkshop;
     private String code;
     private String name;
     private Date stim;
     private Date etim;
     private Long seq;
     private Long enable;
     private Long del;
     private Set schShiftExchgsForHoShift = new HashSet(0);
     private Set schShiftExchgsForToShift = new HashSet(0);
     private Set eqmTroubles = new HashSet(0);
     private Set schWorkorders = new HashSet(0);
     private Set schCalendars = new HashSet(0);


    // Constructors

    /** default constructor */
    public MdShift() {
    }

    
    
    public MdShift(String id) {
		super();
		this.id = id;
	}



	/** full constructor */
    public MdShift(MdWorkshop mdWorkshop, String code, String name, Date stim, Date etim, Long seq, Long enable, Long del, Set schShiftExchgsForHoShift, Set schShiftExchgsForToShift, Set eqmTroubles, Set schWorkorders, Set schCalendars) {
        this.mdWorkshop = mdWorkshop;
        this.code = code;
        this.name = name;
        this.stim = stim;
        this.etim = etim;
        this.seq = seq;
        this.enable = enable;
        this.del = del;
        this.schShiftExchgsForHoShift = schShiftExchgsForHoShift;
        this.schShiftExchgsForToShift = schShiftExchgsForToShift;
        this.eqmTroubles = eqmTroubles;
        this.schWorkorders = schWorkorders;
        this.schCalendars = schCalendars;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public MdWorkshop getMdWorkshop() {
        return this.mdWorkshop;
    }
    
    public void setMdWorkshop(MdWorkshop mdWorkshop) {
        this.mdWorkshop = mdWorkshop;
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

    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
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

    public Set getSchShiftExchgsForHoShift() {
        return this.schShiftExchgsForHoShift;
    }
    
    public void setSchShiftExchgsForHoShift(Set schShiftExchgsForHoShift) {
        this.schShiftExchgsForHoShift = schShiftExchgsForHoShift;
    }

    public Set getSchShiftExchgsForToShift() {
        return this.schShiftExchgsForToShift;
    }
    
    public void setSchShiftExchgsForToShift(Set schShiftExchgsForToShift) {
        this.schShiftExchgsForToShift = schShiftExchgsForToShift;
    }

    public Set getEqmTroubles() {
        return this.eqmTroubles;
    }
    
    public void setEqmTroubles(Set eqmTroubles) {
        this.eqmTroubles = eqmTroubles;
    }

    public Set getSchWorkorders() {
        return this.schWorkorders;
    }
    
    public void setSchWorkorders(Set schWorkorders) {
        this.schWorkorders = schWorkorders;
    }

    public Set getSchCalendars() {
        return this.schCalendars;
    }
    
    public void setSchCalendars(Set schCalendars) {
        this.schCalendars = schCalendars;
    }
   








}