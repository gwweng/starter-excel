package com.example.starterexcel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@Data
@ConfigurationProperties(prefix = ExcelConfigProperties.PREFIX)
public class ExcelConfigProperties {
    static final String PREFIX = "excel";

    /**
     * 模板路径
     */
    private String templatePath = "excel";
}
