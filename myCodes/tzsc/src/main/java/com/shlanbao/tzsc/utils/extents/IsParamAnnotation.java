package com.shlanbao.tzsc.utils.extents;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 是否为Action请求参数,如果Action的对象被isParamAnnotation修饰，则保存该对象里面的属性到数据库
 * <li>@author Leejean
 * <li>@create 2014-6-24 上午11:48:40
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IsParamAnnotation {}
