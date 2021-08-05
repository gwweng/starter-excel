package com.example.starterexcel.config;

import com.example.starterexcel.aop.joinPoint.ExportXlsxAspect;
import com.example.starterexcel.aop.support.DoExportResolver;
import com.example.starterexcel.aop.support.DoImportResolver;
import com.example.starterexcel.handler.export.ExportEmptySheetHandler;
import com.example.starterexcel.handler.export.ExportMultiSheetHandler;
import com.example.starterexcel.handler.export.ExportSheetHandler;
import com.example.starterexcel.handler.export.ExportSingleSheetHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@Configuration
public class ExcelHandlerConfiguration {

    // Excel 配置信息
    private final ExcelConfigProperties configProperties;


    public ExcelHandlerConfiguration(ExcelConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    /**
     * 单个sheet导出处理Handler
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ExportSingleSheetHandler singleSheetHandler(){
        return new ExportSingleSheetHandler(configProperties);
    }

    /**
     * 空白sheet导出即模板下载
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ExportEmptySheetHandler emptySheetHandler(){
        return new ExportEmptySheetHandler(configProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExportMultiSheetHandler multiSheetHandler(){
        return new ExportMultiSheetHandler(configProperties);
    }

    /**
     * 注入Excel 导出处理器
     * @param exportSheetHandlers
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DoExportResolver doExportResolver(List<ExportSheetHandler> exportSheetHandlers){
        return new DoExportResolver(exportSheetHandlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public DoImportResolver doImportResolver(){
        return new DoImportResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExportXlsxAspect doExportColumnAspect(){
        return new ExportXlsxAspect();
    }
}
