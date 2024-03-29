package com.commonUtil;

import com.entity.LoggerBean;
import com.entity.LoggerGrade;

/**
 * <p>功能描述：自定义异常类</p>
 *作者：SShi11
 *日期：Apr 10, 2017 11:47:07 PM
 */
public class SysException extends Exception {
	private static final long serialVersionUID = 1L;

	public SysException() {
		super();
	}

	public SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * <p>功能描述：非控制的异常</p>
	 *@param message
	 *@param cause
	 *作者：SShi11
	 *日期： Apr 11, 2017 9:59:24 AM
	 */
	public SysException(String message, Throwable cause) {
		super(message, cause);
		LoggerBean bean=new LoggerBean(LoggerGrade.EXECP, message,cause.getCause().toString());
		bean.saveLog(bean);
	}

	public SysException(String message) {
		super(message);
	}

	public SysException(Throwable cause) {
		super(cause);
	}
	
}
