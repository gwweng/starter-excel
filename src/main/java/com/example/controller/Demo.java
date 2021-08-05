package com.example.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.example.starterexcel.annotation.DatePattern;
import com.example.starterexcel.converters.LocalDateTimeStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Demo {
    @ColumnWidth(50)
    @ExcelProperty("用户名")
    @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
    private String username;
    @ExcelProperty("密码")
    @Length(min = 1,max = 5,message = "密码长度范围为1-5之间")
    private String password;

    @ExcelProperty(value = "创建时间",converter = LocalDateTimeStringConverter.class)
    @DateTimeFormat("yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime createTime;

    //@ExcelIgnore
    @ExcelProperty("忽略项")
    private String ignore;

    @ExcelProperty(value = "出生日期")
    @DatePattern(format = "yyyy年MM月dd日", message = "出生日期的格式需满足yyyy年MM月dd日")
    private String birthday;
}