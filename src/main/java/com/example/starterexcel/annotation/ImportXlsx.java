package com.example.starterexcel.annotation;

import com.example.starterexcel.handler.DefaultImportDataEventHandler;
import com.example.starterexcel.handler.ImportDataEventHandler;

import java.lang.annotation.*;

/**
 * 导入注解,修饰方法形参
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportXlsx {

    /**
     * 上传的文件字段名称
     * @return 默认为file
     */
    String fileName() default "file";

    /**
     * 是否跳过空行
     * @return 默认跳过
     */
    boolean ignoreEmptyRow() default true;

    /**
     * 读取xlsx事件处理器
     * @return 默认DefaultImportDataEventHandler
     */
    Class<? extends ImportDataEventHandler> importHandler() default DefaultImportDataEventHandler.class;





}
