package com.example.starterexcel.aop.joinPoint;

import com.example.starterexcel.annotation.ExportXlsx;
import com.example.starterexcel.exception.ExcelException;
import com.example.starterexcel.model.ExportCloumn;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author gengweiweng
 * @time 2021/7/27
 * @desc
 */
@Aspect
public class ExportXlsxAspect {

    public static final String EXCEL_CLOUMN_KEY = "_EXCEL_COLOUMN_KEY_";
    @Pointcut("@annotation(com.example.starterexcel.annotation.ExportXlsx)")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void around(JoinPoint point) {
        // 获取所有参数值的数组
        Object[] args = point.getArgs();
        MethodSignature ms = (MethodSignature) point.getSignature();
        // 获取方法的所有参数名称的字符串数组
        Class[] parameterTypes = ms.getParameterTypes();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Method method = ms.getMethod();
        ExportXlsx exportColumn = method.getAnnotation(ExportXlsx.class);
        HashMap<String, List<String>> excelMap = new HashMap<>(3);
        for (int i = 0; i < parameterTypes.length; i++) {
            // 指定导出列的类型
            if(ExportCloumn.class.isAssignableFrom(parameterTypes[i])){
                ExportCloumn cloumns = (ExportCloumn) args[i];
                if(!CollectionUtils.isEmpty(cloumns.getSingleColunms())){
                    excelMap = new HashMap<>(3);
                    excelMap.put(exportColumn.sheet()[0], cloumns.getSingleColunms());
                } else if(!CollectionUtils.isEmpty(cloumns.getMultiColumns())){
                    excelMap = (HashMap<String, List<String>>) cloumns.getMultiColumns();
                }
            }
        }
        Objects.requireNonNull(requestAttributes).setAttribute(EXCEL_CLOUMN_KEY, excelMap, RequestAttributes.SCOPE_REQUEST);
    }
}
