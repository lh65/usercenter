package com.lh.usercenter;

import com.lh.usercenter.mapper.UserMapper;
import com.lh.usercenter.pojo.User;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@SpringBootTest
class UsercenterApplicationTests {

    @Resource
    private UserMapper userMapper;
    @Test
    void contextLoads() {

    }

}
