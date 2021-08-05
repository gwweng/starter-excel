package com.example.starterexcel.handler.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.starterexcel.annotation.ExportXlsx;
import com.example.starterexcel.aop.joinPoint.ExportXlsxAspect;
import com.example.starterexcel.config.ExcelConfigProperties;
import com.example.starterexcel.exception.ExcelException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@RequiredArgsConstructor
public abstract class AbstractExportSheetHandler implements ExportSheetHandler {

    private final ExcelConfigProperties configProperties;


    public String getTemplatePath(){
        return configProperties.getTemplatePath();
    }

    @Override
    public void check(ExportXlsx exportXlsx) {
        if(!StringUtils.hasText(exportXlsx.name())){
            throw new ExcelException("@ExportXlsx 文件名称不能为空!");
        }

        if(exportXlsx.sheet().length == 0){
            throw new ExcelException("@ExportXlsx 至少配置一个sheet页");
        }
    }

    @SneakyThrows
    @Override
    public void export(Object o, HttpServletResponse response, ExportXlsx exportXlsx) {
        check(exportXlsx);
     //   RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String name = exportXlsx.name();
//        String name = (String) Objects.requireNonNull(requestAttributes).getAttribute("__EXCEL_NAME_KEY__",
//                RequestAttributes.SCOPE_REQUEST);
        String fileName = String.format("%s%s", URLEncoder.encode(name, "UTF-8"), exportXlsx.suffix().getValue());
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        write(o, response, exportXlsx);
    }

    public WriteSheet sheet(Integer no, String sheetName, Class<?> dataClass) {
        Integer sheetNo = (no == null) ? 0 : no;
        // 是否为导出模板
        ExcelWriterSheetBuilder excelWriterSheetBuilder =  EasyExcel.writerSheet(sheetNo, sheetName);
        excelWriterSheetBuilder.head(dataClass);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Map<String,List<String>> keys = (Map) Objects.requireNonNull(requestAttributes).getAttribute(ExportXlsxAspect.EXCEL_CLOUMN_KEY,
                RequestAttributes.SCOPE_REQUEST);
        if(keys.containsKey(sheetName)){
            excelWriterSheetBuilder.includeColumnFiledNames(keys.get(sheetName));
        }

        return excelWriterSheetBuilder.build();
    }

    @SneakyThrows
    public ExcelWriter getExcelWriter(HttpServletResponse response, ExportXlsx exportXlsx) {
        ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream())
                .autoCloseStream(true)
                .excelType(exportXlsx.suffix());
      //  registConverters(writerBuilder);
        return writerBuilder.build();
    }

//    public void registConverters(ExcelWriterBuilder writerBuilder){
//       converterProvider.ifAvailable(converters -> converters.forEach(writerBuilder::registerConverter));
//    }
}
