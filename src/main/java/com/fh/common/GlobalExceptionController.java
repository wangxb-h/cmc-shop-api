package com.fh.common;

import com.fh.common.exception.CountException;
import com.fh.common.exception.NologinException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局处理异常
@ControllerAdvice
public class GlobalExceptionController {
    //处理没有登录时的异常
    @ExceptionHandler(NologinException.class) //
    @ResponseBody
    public JsonData handleNoLoginException(NologinException e){
        return JsonData.getJsonError(1000,e.getMessage());
    }

    //处理库存不足的异常
    @ExceptionHandler(CountException.class) //
    @ResponseBody
    public JsonData handleCountException(CountException e){
        return JsonData.getJsonError(2000,e.getMessage());
    }

    /**
     * 处理所有不可知异常
     *
     * @param e 异常
     * @return json结果
     */
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public JsonData handleException(Exception e) {
       return JsonData.getJsonError(e.getMessage());
   }
}
