package com.shlanbao.tzsc.utils.extents;

/**
 * 自定义数据方言
 * @author Leejean
 * @create 2014-8-14下午07:03:44
 * @return
 */
public class MySqlServerLocalDialect extends  org.hibernate.dialect.SQLServerDialect{ 
	public MySqlServerLocalDialect(){
	  super(); 
	  //this.registerFunction("convert", new SQLFunctionTemplate(new StringType(),"convert(?1 using ?2)"));//注册自定义函数 
	}
}
