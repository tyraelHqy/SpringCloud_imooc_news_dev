package com.imooc.api.controller.user;

import com.imooc.bo.RegistLoginBO;
import com.imooc.bo.UpdateUserInfoBO;
import com.imooc.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(value = "用户信息相关",tags = {"用户信息相关的Controller"})
@RequestMapping("user")

public interface UserControllerApi {

    @ApiOperation(value = "获得用户账户信息",notes = "获得用户账户信息",httpMethod = "POST")
    @PostMapping("/getAccountInfo")
    public GraceJSONResult getAccountInfo(@RequestParam String userId);

    /**
     * 获得短信验证码
     * @return
     */
    @ApiOperation(value = "完善用户信息",notes = "获得用户账户信息",httpMethod = "POST")
    @PostMapping("/updateUserInfo")
    public GraceJSONResult getAccountInfo(@RequestBody @Valid UpdateUserInfoBO updateUserInfoBO, BindingResult result);
}
