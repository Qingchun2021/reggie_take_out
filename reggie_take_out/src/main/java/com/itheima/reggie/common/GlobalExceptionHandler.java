package com.itheima.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * @author xukai
 */
@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.error(exception.getMessage());
        if(exception.getMessage().contains("Duplicate entry")){
            //空格分开
            String[] exceptionMsg = exception.getMessage().split(" ");
            String msg = exceptionMsg[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception){
        log.error(exception.getMessage());
        return R.error(exception.getMessage());
    }

}
