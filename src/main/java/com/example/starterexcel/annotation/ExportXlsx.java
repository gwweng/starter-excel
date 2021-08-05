package com.example.starterexcel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出注解
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportXlsx {
    /**
     * 文件名称
     * @return 默认response
     */
    String name() default "response";

    /**
     * 文件后缀(xlsx,xls)
     * @return 默认xlsx
     */
    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;

    /**
     * 配置sheet名称
     * @return 默认sheet
     */
    String[] sheet() default {"sheet"};

    /**
     * 导出模板名称，包括后缀名
     * @return false
     */
    String templateFile() default "";

}
