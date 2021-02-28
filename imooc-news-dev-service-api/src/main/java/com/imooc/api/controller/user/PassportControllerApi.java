package com.imooc.api.controller.user;

import com.imooc.bo.RegistLoginBO;
import com.imooc.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(value = "用户注册登录",tags = {"用户注册登录功能的Controller"})
@RequestMapping("passport")

public interface PassportControllerApi {

    /**
     * 获得短信验证码
     * @return
     */
    @ApiOperation(value = "获得短信验证码",notes = "获得短信验证码",httpMethod = "GET")
    @GetMapping("/getSMSCode")
    public GraceJSONResult getSMSCode(String mobile, HttpServletRequest request);

    @ApiOperation(value = "一键注册登录接口", notes="一键注册登录接口",httpMethod = "POST")
    @PostMapping("/doLogin")
    public GraceJSONResult doLogin(@RequestBody @Valid RegistLoginBO registLoginBO,
                                   BindingResult bindingResult);
}
