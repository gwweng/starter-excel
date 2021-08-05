package com.example.starterexcel.handler.export;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.starterexcel.annotation.ExportXlsx;
import com.example.starterexcel.config.ExcelConfigProperties;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 多sheet导出处理器
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
public class ExportMultiSheetHandler extends AbstractExportSheetHandler {

    public ExportMultiSheetHandler(ExcelConfigProperties configProperties) {
        super(configProperties);
    }

    /**
     * 多sheet导出要满足数据是否为Map，同时list不可为空且元素必须也是list
     * @param obj 导出数据集
     * @return 导出数据是否合规
     */
    @Override
    public boolean support(Object obj) {
        if(obj instanceof Map){
            Map data = (Map) obj;
            if(data.isEmpty()){
                return false;
            }
            Collection values = data.values();
            for (Object o : values){
                if(o instanceof List){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void write(Object o, HttpServletResponse response, ExportXlsx exportXlsx) {
        // 多sheet导入使用map结构接收
        Map<String,List> map = (Map<String, List>) o;
        ExcelWriter excelWriter = getExcelWriter(response, exportXlsx);
        Integer sheetNo = 0;
        // 采用entrySet遍历性能最佳
        for (Map.Entry<String, List> entry : map.entrySet()) {
            String sheetName = entry.getKey();
            List data = entry.getValue();
            Class<?> dataClass = data.get(0).getClass();
            WriteSheet sheet = this.sheet(sheetNo, sheetName, dataClass);
            excelWriter.write(data, sheet);
            sheetNo++;
        }
        excelWriter.finish();
    }
}
