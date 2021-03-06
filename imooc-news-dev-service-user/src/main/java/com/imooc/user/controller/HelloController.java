package com.imooc.user.controller;

import com.imooc.api.controller.user.HelloControllerApi;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.grace.result.IMOOCJSONResult;
import com.imooc.grace.result.ResponseStatusEnum;
import com.imooc.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloControllerApi {

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private RedisOperator redis;

    public Object hello(){
        logger.debug("Debug:Hello~");
        logger.info("Debug:Hello~");
        logger.warn("Debug:Hello~");

        return IMOOCJSONResult.ok("Hello");

//        return GraceJSONResult.errorCustom(ResponseStatusEnum.NO_AUTH);

//        return IMOOCJSONResult.errorMsg("您的信息有误～");
    }

    @GetMapping("/redis")
    public Object redis() {
        redis.set("age", "18");
        logger.info(redis.get("age"));
        return IMOOCJSONResult.ok(redis.get("age"));
    }

}
