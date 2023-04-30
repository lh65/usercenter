package com.lh.usercenter.service.impl;


import com.lh.usercenter.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface UserService extends IService<User> {
    String salt="lh";
    String loginUserStatus="1";
    //用户注册
    Integer registerAccount(String userAccount, String password, String checkPassword, Integer planetCode);
    //用户登录
    User checkLogin(String userAccount, String userpassword, HttpServletRequest request);
    //用户密码加密的方法
    String encryPassword(String password);
    //返回安全用户
    User safetyUser(User user);

}
