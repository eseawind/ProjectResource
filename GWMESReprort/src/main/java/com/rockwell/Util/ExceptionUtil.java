package com.rockwell.Util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtil {
	public static String formatStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        try {
            PrintWriter p = new PrintWriter(sw);
            t.printStackTrace(p);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return sw.toString();
    }
}
