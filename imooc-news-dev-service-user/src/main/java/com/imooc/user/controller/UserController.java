package com.imooc.user.controller;

import com.imooc.api.BaseController;
import com.imooc.api.controller.user.HelloControllerApi;
import com.imooc.api.controller.user.UserControllerApi;
import com.imooc.bo.UpdateUserInfoBO;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.grace.result.IMOOCJSONResult;
import com.imooc.grace.result.ResponseStatusEnum;
import com.imooc.pojo.AppUser;
import com.imooc.user.service.UserService;
import com.imooc.utils.RedisOperator;
import com.imooc.vo.UserAccountInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class UserController extends BaseController implements UserControllerApi {

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Override
    public GraceJSONResult getAccountInfo(String userId) {

        // 0. 判断参数不能为空
        if(StringUtils.isBlank(userId)){
            return GraceJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }

        // 1. 根据userId查询用户信息
        AppUser user = getUser(userId);

        // 2. 返回用户信息
        UserAccountInfoVO accountInfoVO = new UserAccountInfoVO();
        BeanUtils.copyProperties(user,accountInfoVO);
        return GraceJSONResult.ok(accountInfoVO);
    }

    private AppUser getUser(String userId){
        // TODO 本方法后续公用，并且扩展
        AppUser user = userService.getUser(userId);

        return user;
    }


    @Override
    public GraceJSONResult getAccountInfo(@Valid UpdateUserInfoBO updateUserInfoBO, BindingResult result) {
        // 0. 校验BO
        if(result.hasErrors()){
            Map<String,String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }

        // 1. 执行更新操作

        return GraceJSONResult.ok();
    }

}
