package com.example.starterexcel.handler;

import com.alibaba.excel.event.AnalysisEventListener;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc 导入xlsx事件处理器
 */
public abstract class ImportDataEventHandler<T> extends AnalysisEventListener<T> {

    /**
     * 获取导入的数据集
     * @return 数据集合list
     */
    public abstract List<T> getData();

    /**
     * 获取异常校验结果
     * @return
     */
    public abstract Map<Long, Set<ConstraintViolation<T>>> getErrors();



}
