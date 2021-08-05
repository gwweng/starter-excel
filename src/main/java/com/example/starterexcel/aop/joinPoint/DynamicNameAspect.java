package com.example.starterexcel.aop.joinPoint;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author gengweiweng
 * @time 2021/7/26
 * @desc
 */
@Aspect
@RequiredArgsConstructor
public class DynamicNameAspect {
    private static final String EXCEL_NAME_KEY = "_EXCEL_NAME_KEY_";

}
