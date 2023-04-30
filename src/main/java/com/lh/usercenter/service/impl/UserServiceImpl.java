package com.lh.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.usercenter.common.ErrorCode;
import com.lh.usercenter.exception.BusinessException;
import com.lh.usercenter.mapper.UserMapper;
import com.lh.usercenter.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    /**
     *注册账号
     */
    public Integer registerAccount(String userAccount, String password, String checkPassword, Integer planetCode) {
        //账号，密码，检查密码皆不能为空
        if(StringUtils.isAnyBlank(userAccount,password,checkPassword)){
            throw new BusinessException((ErrorCode.NULL_ERROR),"检测账号密码是否为空！");
        }
        //账号账号不多于12位
        if(userAccount.length()>12){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查账号的长度");
        }
        //新球编号大于零
        if(planetCode<0){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查星球编号是否大于零");
        }
        //账号不能有除数字、字母、下划线以外的特殊符号
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查账号是否含有特殊字符");
        }
        //密码个数不能小于6位，不能多于15位
        if(password.length()<6||password.length()>15){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查密码的长度");
        }
        //密码和检查密码不一致
        if(!password.equals(checkPassword)){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查两次密码是否一致");
        }
        //检查账号是否重复
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(userQueryWrapper);
        if(count>0){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查账号是否重复");
        }

        //检查星球编号是否重复
        QueryWrapper<User> userQueryWrapper2=new QueryWrapper<>();
        userQueryWrapper2.eq("planetCode", planetCode);
        long count2 = userMapper.selectCount(userQueryWrapper2);
        if(count2>0){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查星球编号是否重复");
        }
        String encryPassword = encryPassword(password);
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryPassword);
        user.setPlanetCode(planetCode);
        return userMapper.insert(user);

    }
    /**
     * 登录账号的逻辑
     * @return
     */
    @Override
    public User checkLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //账号，密码，检查密码皆不能为空
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException((ErrorCode.NULL_ERROR),"检查账号密码是否为空");
        }
        //账号不多于12位
        if(userAccount.length()>12){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查账号的长度");
        }
        //账号不能有除数字、字母、下划线以外的特殊符号
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查账号是否含有特殊字符");
        }
        //密码个数不能小于6位，不能多于15位
        if(userPassword.length()<6||userPassword.length()>15){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查密码的长度");
        }
        //1.检测账号密码是否正确
        QueryWrapper<User> userWrapper=new QueryWrapper<>();
        userWrapper.eq("userAccount",userAccount);
        String encryPassword = encryPassword(userPassword);
        userWrapper.eq("userPassword",encryPassword);
        User user = userMapper.selectOne(userWrapper);
        System.out.println(user);
        if(user==null){
            throw new BusinessException((ErrorCode.PARAMS_ERROR),"检查账号密码是否正确");
        }
        //2.用数据进行脱敏
        User safetyUser = safetyUser(user);
        //3.用户登录成功后，设置用户的session
        request.getSession().setAttribute(loginUserStatus,safetyUser);
        return safetyUser;
    }

    /**
     * 返回脱敏后的信息
     * @param user 原用户信息
     * @return
     */
    @Override
    public  User safetyUser(User user){
        if(user==null){
            throw new BusinessException((ErrorCode.NULL_ERROR),"检查用户是否为空");
        }
        User safetyUser=new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setIsDelete(user.getIsDelete());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUserAvatarUrl(user.getUserAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setStatus(user.getStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setPlanetCode(user.getPlanetCode());
        return safetyUser;
    }

    /**
     * 对密码进行加密存储
     * @param password 原密码
     * @return
     */
    @Override
    public String encryPassword(String password){
      return DigestUtils.md5DigestAsHex((salt + password).getBytes());
    }
}




