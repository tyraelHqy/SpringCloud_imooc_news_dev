package com.imooc.user.service;

import com.imooc.pojo.AppUser;

public interface UserService {

    /**
     * 判断用户是否存在，如果存在，返回User信息
     */
    public AppUser queryMobileIsExist(String mobile);

    /**
     * 创建用户，新增用户记录到数据库
     */
    public AppUser createUser(String mobile);



}
