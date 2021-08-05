package com.example.starterexcel.util;

import com.example.controller.Demo;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Set;

/**
 * @author gengweiweng
 * @time 2021/7/26
 * @desc
 */
public class Validators {
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        if(factory != null){
            validator = factory.getValidator();
        }else {
            validator = null;
        }
    }

    public static <T> Set<ConstraintViolation<T>> validate(T object){
        if(validator == null){
            return null;
        }
        return validator.validate(object);
    }

    public static String resolveErrorString(String prefix, String suffix, BindingResult result){
        Map<Long, Set<ConstraintViolation<?>>> errorMap = (Map<Long, Set<ConstraintViolation<?>>>) result.getTarget();
        StringBuffer sb = new StringBuffer();
        if(prefix != null){
            sb.append(prefix).append("\n");
        }
        if(!CollectionUtils.isEmpty(errorMap)){
            errorMap.forEach((key, value) -> {
                ConstraintViolation<?> val = value.iterator().next();
                sb.append("第").append(key).append("行不满足要求：").append(val.getMessage());
                sb.append("\n");
            });
        }

        if(suffix != null){
            sb.append(suffix).append("\n");
        }
        return sb.toString();
    }

}
