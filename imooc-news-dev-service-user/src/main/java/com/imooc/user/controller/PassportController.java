package com.imooc.user.controller;

import com.imooc.api.controller.user.HelloControllerApi;
import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.grace.result.ResponseStatusEnum;
import com.imooc.utils.MyInfo;
import com.imooc.utils.SMSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassportController implements PassportControllerApi {

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private SMSUtils smsUtils;

    @Override

    public GraceJSONResult getSMSCode() {
        String random = "123456";
//        smsUtils.sendSMS(MyInfo.getMobile(),random);
        logger.info("短信验证码为："+random);
        return GraceJSONResult.ok();
    }
}
