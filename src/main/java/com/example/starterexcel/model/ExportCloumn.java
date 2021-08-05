package com.example.starterexcel.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author gengweiweng
 * @time 2021/7/28
 * @desc
 */
@Data
public class ExportCloumn {
    // 单个列表导出列
    List<String> singleColunms;

    // 多个列表导出列
    Map<String, List<String>> multiColumns;


}
