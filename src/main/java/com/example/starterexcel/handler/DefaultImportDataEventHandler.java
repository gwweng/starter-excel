package com.example.starterexcel.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.example.starterexcel.util.Validators;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认导入事件处理器
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@Slf4j
public class DefaultImportDataEventHandler extends ImportDataEventHandler<Object>{
    private final List<Object> list = new ArrayList<>();

    private final Map<Long, Set<ConstraintViolation<Object>>> errors = new ConcurrentHashMap<>();

    private Long lineNum = 1L;

    @Override
    public List<Object> getData() {

        return list;
    }

    @Override
    public Map<Long, Set<ConstraintViolation<Object>>> getErrors() {
        return errors;
    }

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        lineNum++;
        Set<ConstraintViolation<Object>> validate = Validators.validate(o);
        if(!validate.isEmpty()){
            errors.put(lineNum, validate);
        }else {
            list.add(o);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
