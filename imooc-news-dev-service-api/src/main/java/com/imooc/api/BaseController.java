package com.imooc.api;


import com.imooc.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
    @Autowired
    public RedisOperator redis;

    public static final String MOBILE_SMSCODE = "mobile:smscode";


    /***
     * 获取BO中的错误信息
     * @param bindingResult
     * @return
     */
    public Map<String, String> getErrors(BindingResult bindingResult){
        Map<String,String> map = new HashMap<>();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error :errors){
            // 发生验证错误的时候所对应的某个属性
            String field = error.getField();
            // 验证的错误消息
            String msg = error.getDefaultMessage();
            map.put(field,msg);
        }
        return map;
    }
}
