package com.imooc.user.controller;

import com.imooc.api.BaseController;
import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.bo.RegistLoginBO;
import com.imooc.enums.UserStatus;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.grace.result.ResponseStatusEnum;
import com.imooc.pojo.AppUser;
import com.imooc.user.service.UserService;
import com.imooc.utils.IPUtil;
import com.imooc.utils.SMSUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PassportController extends BaseController implements PassportControllerApi {

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private SMSUtils smsUtils;

    @Autowired
    private UserService userService;

    @Override
    public GraceJSONResult getSMSCode(String mobile, HttpServletRequest request) {

        // 获得用户ip
        String userIp = IPUtil.getRequestIp(request);

        // 根据用户的IP进行限制，限制用户在60秒内只能获得一次验证码
        redis.setnx60s(MOBILE_SMSCODE+":"+userIp,userIp);
//        String random = String.valueOf((int)((Math.random()*9+1)*10000));

        // 生成随机验证码，并且发送短信
        String random = (int)((Math.random()*9+1)*100000)+"";
//        smsUtils.sendSMS(MyInfo.getMobile(),random);

        // 把验证码存入redis，用于后续进行验证,设置有效时间为30mins
        redis.set(MOBILE_SMSCODE+":"+mobile,random,30*60);
        logger.info("短信验证码为："+random);
        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult doLogin(@Valid RegistLoginBO registLoginBO, BindingResult bindingResult) {
        // 0.判断BingdingResult中是否保存了错误的验证信息，如果有，则需要返回
        if(bindingResult.hasErrors()){
            Map<String, String> map = getErrors(bindingResult);
            return GraceJSONResult.errorMap(map);
        }

        String mobile = registLoginBO.getMobile();
        String smsCode = registLoginBO.getSmsCode();

        // 1. 校验验证码是否匹配
        String redisSMSCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if(StringUtils.isBlank(redisSMSCode)|| !redisSMSCode.equalsIgnoreCase(smsCode)){
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        logger.info("验证码正确， "+mobile+" 登录成功");

        // 2. 查询数据库，判断该用户是否注册
        AppUser user = userService.queryMobileIsExist(mobile);

        if(user != null && user.getActiveStatus()== UserStatus.FROZEN.type){
            //如果用户不为空，并且状态为冻结，则直接抛出异常，禁止登陆
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_FROZEN);
        }else if(user == null){
            // 如果用户没有注册过，则为null，需要注册信息入库
            user = userService.createUser(mobile);
        }
        return GraceJSONResult.ok(user);
    }
}
