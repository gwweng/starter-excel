package com.example.starterexcel.aop.support;

import com.example.starterexcel.annotation.ExportXlsx;
import com.example.starterexcel.handler.export.ExportSheetHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 导出接口结果拦截器
 * @author gengweiweng
 * @time 2021/7/23
 * @desc 将接口返回数据进行xlsx导出处理
 */
@Slf4j
@RequiredArgsConstructor
public class DoExportResolver implements HandlerMethodReturnValueHandler {

    private final List<ExportSheetHandler> exportSheetHandlerList;
    /**
     * 只处理含有ExportXlsx的注解
     * @param methodParameter 方法参数对象
     * @return 是否含有此注解
     */
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(ExportXlsx.class);
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse!");
        ExportXlsx exportXlsx = methodParameter.getMethodAnnotation(ExportXlsx.class);
        Assert.state(exportXlsx != null, "No @ExportXlsx");
        modelAndViewContainer.setRequestHandled(true);

        exportSheetHandlerList.stream().filter(handler -> handler.support(o)).findFirst()
                .ifPresent(handler -> handler.export(o, response, exportXlsx));
    }

}
