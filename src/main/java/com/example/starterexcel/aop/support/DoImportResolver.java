package com.example.starterexcel.aop.support;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.example.starterexcel.annotation.ImportXlsx;
import com.example.starterexcel.converters.LocalDateTimeStringConverter;
import com.example.starterexcel.handler.ImportDataEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

/**
 * 导入接口参数拦截器
 * @author gengweiweng
 * @time 2021/7/23
 * @desc 上传的文件数据流解析到方法参数
 */
@Slf4j
public class DoImportResolver implements HandlerMethodArgumentResolver {
    /**
     * 只处理ImportXlsx注解的参数
     * @param methodParameter  方法参数对象
     * @return 是否含有目标注解
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ImportXlsx.class);
    }


    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 获取方法的参数类型
        Class<?> parameterType = methodParameter.getParameterType();
        if(!parameterType.isAssignableFrom(List.class)){
            throw new IllegalArgumentException("Excel解析失败，@ImportXlsx接收类型不是List！");
        }

        // 处理自定义的ImportXlsx
        ImportXlsx importXlsx = methodParameter.getParameterAnnotation(ImportXlsx.class);
        assert importXlsx != null;
        Class<? extends ImportDataEventHandler<?>> importHandlerClass = (Class<? extends ImportDataEventHandler<?>>) importXlsx.importHandler();
        ImportDataEventHandler importHandler = BeanUtils.instantiateClass(importHandlerClass);

        // 获取请求文件流
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        InputStream inputStream;
        if(request instanceof MultipartRequest){
            // 根据注解规定前端传过来file的属性名称获取文件对象
            MultipartFile file = ((MultipartRequest) request).getFile(importXlsx.fileName());
            assert file != null;
            inputStream = file.getInputStream();
        }else {
            inputStream = request.getInputStream();
        }

        // 获取目标类型
        Class<?> excelModelClass = ResolvableType.forMethodParameter(methodParameter).getGeneric(0).resolve();

        // 这里需要指定读用哪个class去读，然后读取第一个sheet文件流会自动关闭
        ExcelReaderSheetBuilder builder = EasyExcel.read(inputStream, excelModelClass, importHandler).ignoreEmptyRow(importXlsx.ignoreEmptyRow()).sheet();
        builder.registerConverter(new LocalDateTimeStringConverter());
        builder.doRead();
        // 校验失败的数据处理交给BindingResult
        WebDataBinder dataBinder = webDataBinderFactory.createBinder(nativeWebRequest, importHandler.getErrors(), "excel");
        ModelMap model = modelAndViewContainer.getModel();
        model.put(BindingResult.MODEL_KEY_PREFIX + "excel", dataBinder.getBindingResult());
        return importHandler.getData();
    }

}
