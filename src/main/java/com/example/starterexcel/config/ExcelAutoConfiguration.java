package com.example.starterexcel.config;

import com.example.starterexcel.aop.support.DoExportResolver;
import com.example.starterexcel.aop.support.DoImportResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动配置类
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@Import(ExcelHandlerConfiguration.class)
@EnableConfigurationProperties(ExcelConfigProperties.class)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ExcelAutoConfiguration {

    // SpringMVC 接口请求处理器
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    // 导出请求返回值拦截器
    private final DoExportResolver doExportResolver;

    // 导入请求参数拦截器
    private final DoImportResolver doImportResolver;


    /**
     * 初始化注入Excel 导入请求处理器到SpringMVC 中
     */
    @PostConstruct
    public void setRequestMappingHandlerAdapter(){
        List<HandlerMethodArgumentResolver> argumentResolverList = requestMappingHandlerAdapter.getArgumentResolvers();
        int size = argumentResolverList.size() + 1;
        List<HandlerMethodArgumentResolver> allArgumentResolvers = new ArrayList<>(size);
        allArgumentResolvers.add(doImportResolver);
        allArgumentResolvers.addAll(argumentResolverList);
        requestMappingHandlerAdapter.setArgumentResolvers(allArgumentResolvers);
    }

    /**
     * 初始化注入Excel 导出处理器到SpringMVC中
     */
    @PostConstruct
    public void setDoExportResolver(){
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        int size = returnValueHandlers.size() + 1;
        List<HandlerMethodReturnValueHandler> allReturnValueHandlers = new ArrayList<>(size);
        allReturnValueHandlers.add(doExportResolver);
        allReturnValueHandlers.addAll(returnValueHandlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(allReturnValueHandlers);
    }


}
