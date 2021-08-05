package com.example.starterexcel.annotation;

import com.example.starterexcel.aop.support.DatePatternValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gengweiweng
 * @time 2021/7/28
 * @desc
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatePatternValidator.class)
public @interface DatePattern {
    String message() default "格式错误";

    String format() default  "yyyyMM";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
