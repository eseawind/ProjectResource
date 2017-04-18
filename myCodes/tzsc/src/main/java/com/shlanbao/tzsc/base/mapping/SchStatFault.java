package com.shlanbao.tzsc.base.mapping;



/**
 * SchStatFault entity. @author MyEclipse Persistence Tools
 */

public class SchStatFault  implements java.io.Serializable {


    // Fields    

     private String id;
     private SchStatOutput schStatOutput;
     private String name;
     private double time;
     private Long times;
     private Long flag;


    // Constructors

    /** default constructor */
    public SchStatFault() {
    }

    
    /** full constructor */
    public SchStatFault(SchStatOutput schStatOutput, String name, Long time, Long times, Long flag) {
        this.schStatOutput = schStatOutput;
        this.name = name;
        this.time = time;
        this.times = times;
        this.flag = flag;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public SchStatOutput getSchStatOutput() {
        return this.schStatOutput;
    }
    
    public void setSchStatOutput(SchStatOutput schStatOutput) {
        this.schStatOutput = schStatOutput;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public double getTime() {
        return this.time;
    }
    
    public void setTime(double time) {
        this.time = time;
    }

    public Long getTimes() {
        return this.times;
    }
    
    public void setTimes(Long times) {
        this.times = times;
    }

    public Long getFlag() {
        return this.flag;
    }
    
    public void setFlag(Long flag) {
        this.flag = flag;
    }
   








}