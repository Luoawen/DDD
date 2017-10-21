package cn.m2c.scm.application.utils;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author kang
 * @Description: excel导出注解类
 * @date 2016年8月24日
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    //导出字段在excel中的名字
    String title();
}
