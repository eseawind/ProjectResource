package com.traveler.util;

import java.io.PrintWriter;
import java.io.StringWriter;
/**
 * 异常打印
 * @author SShi11
 *
 */
public final class ExceptionUtil {
	public static String formatStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        try {
            PrintWriter p = new PrintWriter(sw);
            e.printStackTrace(p);
        } catch (Exception e2) {
        	e2.printStackTrace();
        }
        return sw.toString();
    }
}
