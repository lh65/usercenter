package com.lh.usercenter.controller;

import com.lh.usercenter.common.ErrorCode;
import com.lh.usercenter.exception.BusinessException;
import com.lh.usercenter.pojo.User;
import com.lh.usercenter.pojo.UserLogineReuquest;
import com.lh.usercenter.pojo.UserRegister;
import com.lh.usercenter.service.impl.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

;


@RestController// @RestController就是@ResponseBody和@Controller注解
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;
    private static String loginUserStatus = "1";
    @RequestMapping("/register")
    //注册账号
    public Integer registerAccount(@RequestBody UserRegister userRegister) {

        String userAccount = userRegister.getUserAccount();
        String userPassword = userRegister.getUserPassword();
        String checkPassword = userRegister.getCheckPassword();
        Integer planetCode = userRegister.getPlanetCode();
        //账号、密码和二次密码输出不能为空,空串，null
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //星球编号必须大于零
        if(planetCode<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号必须大于零");
        }
        int result = userService.registerAccount(userAccount, userPassword, checkPassword, planetCode);
        return  result;
    }
    //获取当前用户信息
    @RequestMapping("/current")
    public User getCurrent(HttpServletRequest request){
        //用session获取当前用户的信息
        Object objUser = request.getSession().getAttribute(loginUserStatus);
        if(objUser==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User user=(User) objUser;
        //查询数据库，更新数据，查询真实的信息
        Integer id = user.getId();

        User user1 = userService.safetyUser(userService.getById(id));
        return user1;
    }
//登录账号
    @PostMapping("/login")
    public User doLogin(@RequestBody UserLogineReuquest userLogineReuquest, HttpServletRequest request) {

        //向下转型
        if (userLogineReuquest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = userLogineReuquest.getUserAccount();
        String userPassword = userLogineReuquest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User user = userService.checkLogin(userAccount, userPassword, request);
        return user;

    }
    //退出登录
    @PostMapping("/outLogin")
    public Map<String,Object> outLogin(HttpServletRequest request){
        //清空session
        request.getSession().invalidate();
        return new HashMap<String,Object>(){{
            put("msg","成功退出登录");
        }};
    }
    @RequestMapping("/search")
    public List<User> queryAccount(HttpServletRequest request) {
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //查询语句
        List<User> list = userService.list();
        List<User> userList = list.stream().map(user -> userService.safetyUser(user)).collect(Collectors.toList());
        return  userList;
    }

    @PostMapping ("/delete")
    public Boolean delAccount(@RequestBody long id, HttpServletRequest request) {
        if(id<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"id不能小于0");
        }
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //进行删除操作
        return userService.removeById(id);
    }

//判断用户是否为管理员
    public Boolean isAdmin(HttpServletRequest request) {
        //帮段用户是否有权限删除
        Object user = request.getSession().getAttribute(loginUserStatus);
        //向下转型
        if (user instanceof User) {
            User user2 = (User) user;
            //判断该账号是否可以用
            if (user2.getIsDelete() != 0) {
                return false;
            }
            if (user2.getUserRole() != 1) {
                return false;
            }
        }
        return true;
    }
}
