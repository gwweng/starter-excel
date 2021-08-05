package com.example.starterexcel.handler.export;

import com.example.starterexcel.annotation.ExportXlsx;

import javax.servlet.http.HttpServletResponse;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
public interface ExportSheetHandler {
    /**
     * 是否支持
     * @param obj
     * @return
     */
    boolean support(Object obj);

    /**
     * 校验
     * @param exportXlsx 注解
     */
    void check(ExportXlsx exportXlsx);

    /**
     * 返回的对象
     * @param o obj
     * @param response 输出对象
     * @param exportXlsx 注解
     */
    void export(Object o, HttpServletResponse response, ExportXlsx exportXlsx);

    /**
     * 写成对象
     * @param o obj
     * @param response 输出对象
     * @param exportXlsx 注解
     */
    void write(Object o, HttpServletResponse response, ExportXlsx exportXlsx);


}
