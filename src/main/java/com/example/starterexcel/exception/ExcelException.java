package com.example.starterexcel.exception;

/**
 * Excel处理异常类
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
public class ExcelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }
}
