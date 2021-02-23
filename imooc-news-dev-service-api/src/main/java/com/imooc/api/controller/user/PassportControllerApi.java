package com.imooc.api.controller.user;

import com.imooc.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "用户注册登录",tags = {"用户注册登录功能的Controller"})
public interface PassportControllerApi {

    /**
     * 获得短信验证码
     * @return
     */
    @ApiOperation(value = "获得短信验证码",notes = "获得短信验证码",httpMethod = "GET")
    @RequestMapping("/getSMSCode")
    public GraceJSONResult getSMSCode();
}
