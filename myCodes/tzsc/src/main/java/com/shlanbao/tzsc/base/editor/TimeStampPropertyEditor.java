package com.shlanbao.tzsc.base.editor;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class TimeStampPropertyEditor extends PropertyEditorSupport {
	private String pattern = "yyyy-MM-dd HH:mm:ss";

	private String[] formater_pattern = new String[] {"yyyy-MM-dd HH:mm:ss" };

	public TimeStampPropertyEditor() {
	}

	public TimeStampPropertyEditor(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			setValue(null);
		} else {
			try {
				Date date = DateUtil.formatStringToDate(text, formater_pattern);
				Timestamp timestamp = new Timestamp(date.getTime());
				// 设置转换完的值
				setValue(timestamp);
			} catch (ParseException e) {
				e.printStackTrace();
				setValue(null);
			}
		}

	}

	@Override
	public String getAsText() {
		// TODO Auto-generated method stub
		// 获取model的值
		Timestamp value = (Timestamp) getValue();
		if (value == null) {
			return "";
		} else {
			try {
				Date date = new Date(value.getTime());
				String str = DateUtil.formatDateToString(date, pattern);
				return str;
			} catch (Exception e) {
				return "";
			}
		}

	}

}
