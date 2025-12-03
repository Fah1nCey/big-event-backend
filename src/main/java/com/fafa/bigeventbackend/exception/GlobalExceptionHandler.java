package com.fafa.bigeventbackend.exception;

import com.fafa.bigeventbackend.common.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }

    // 字段校验只输出第一个错误信息
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return Result.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    // TODO: 2025/11/20 businessException的处理 (自定义异常)
}
