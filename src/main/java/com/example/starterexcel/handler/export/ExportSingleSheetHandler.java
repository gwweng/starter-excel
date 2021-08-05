package com.example.starterexcel.handler.export;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.starterexcel.annotation.ExportXlsx;
import com.example.starterexcel.config.ExcelConfigProperties;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
public class ExportSingleSheetHandler extends AbstractExportSheetHandler {

    public ExportSingleSheetHandler(ExcelConfigProperties configProperties) {
        super(configProperties);
    }

    /**
     * 单sheet导出要满足数据是否为list，同时list不可为空且元素不能是list
     * @param obj 导出数据集
     * @return 导出数据是否合规
     */
    @Override
    public boolean support(Object obj) {
        if(obj instanceof List){
            List data = (List) obj;
            return !data.isEmpty() && !(data.get(0) instanceof List);
        }
        return false;
    }

    @Override
    public void write(Object obj, HttpServletResponse response, ExportXlsx exportXlsx) {
        List list = (List) obj;
        ExcelWriter excelWriter = getExcelWriter(response, exportXlsx);
        Class<?> dataClass = list.get(0).getClass();
        WriteSheet sheet = this.sheet(0, exportXlsx.sheet()[0], dataClass);
        excelWriter.write(list, sheet);
        excelWriter.finish();
    }
}
