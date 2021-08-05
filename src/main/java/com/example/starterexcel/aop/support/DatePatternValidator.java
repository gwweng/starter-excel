package com.example.starterexcel.aop.support;

import com.example.starterexcel.annotation.DatePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author gengweiweng
 * @time 2021/7/28
 * @desc
 */
public class DatePatternValidator implements ConstraintValidator<DatePattern,String> {
    private DatePattern datePattern;

    @Override
    public void initialize(DatePattern datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null){
            return true;
        }
        String format = datePattern.format();
        if(s.length() > format.length()){
            return false;
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormat.parse(s);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
