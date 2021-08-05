package com.example.starterexcel.handler.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.starterexcel.annotation.ExportXlsx;
import com.example.starterexcel.config.ExcelConfigProperties;
import com.example.starterexcel.exception.ExcelException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gengweiweng
 * @time 2021/7/26
 * @desc
 */
public class ExportEmptySheetHandler extends AbstractExportSheetHandler {
    public ExportEmptySheetHandler(ExcelConfigProperties configProperties) {
        super(configProperties);
    }

    @Override
    public boolean support(Object obj) {
        if(obj == null){
            return true;
        }
        return false;
    }

    @Override
    public void write(Object o, HttpServletResponse response, ExportXlsx exportXlsx) {
        ExcelWriterBuilder writerBuilder;
        try {
            writerBuilder = EasyExcel.write(response.getOutputStream()).autoCloseStream(true).excelType(exportXlsx.suffix());
            String templatePath = getTemplatePath();
            if(StringUtils.hasText(exportXlsx.templateFile())){
                ClassPathResource classPathResource = new ClassPathResource(templatePath + File.separator + exportXlsx.templateFile());
                InputStream inputStream = classPathResource.getInputStream();
                writerBuilder.withTemplate(inputStream);
            }

            ExcelWriter excelWriter = writerBuilder.build();
            // 有模板则不指定sheet名
            WriteSheet sheet = EasyExcel.writerSheet(-1).build();
            excelWriter.write(null,sheet);
            excelWriter.finish();
        } catch (IOException e) {
            throw new ExcelException(e.getMessage());
        }
    }
}
