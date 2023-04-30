package com.lh.usercenter;

import com.lh.usercenter.mapper.UserMapper;
import com.lh.usercenter.service.impl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleTest {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;

    @Test
    public void testSelect() {
        //密码进行加密
        String password = "1234";
        String newPassword = DigestUtils.md5DigestAsHex(("lh" + password).getBytes());
        System.out.println(newPassword);
    }

    @Test
    public void RegisterTest() {
//
//        String userAccount;
//        String password;
//        String checkPassword;
//        Integer planet;
//        Integer result=null;
//        //测试账号重复是否可以注册成功
//        userAccount="123";
//        password="123456";
//        checkPassword="123456";
//        planet=10;
//        result=userService.registerAccount(userAccount,password,checkPassword,planet);
//        Assert.assertEquals(new Exception(),result);
//        //测试密码不一致是否可以注册成功
//        userAccount="6666";
//        password="1234577";
//        checkPassword="1234576";
//        planet=12;
//        result=userService.registerAccount(userAccount,password,checkPassword,planet);
//        Assert.assertEquals(new Exception(),result);
//        //测试星球编号相同是否可以注册成功
//        userAccount="6666";
//        password="123456";
//        checkPassword="123456";
//        planet=1;
//        result=userService.registerAccount(userAccount,password,checkPassword,planet);
//        Assert.assertEquals(new Exception(),result);
//        //测试账号含有特殊字符是否注册成功
//        userAccount="6666@";
//        password="123456";
//        checkPassword="123456";
//        planet=11;
//        result=userService.registerAccount(userAccount,password,checkPassword,planet);
//        Assert.assertEquals(new Exception(),result);
//        //测试一切正常是否可以注册成功
//        userAccount="6666";
//        password="123456";
//        checkPassword="123456";
//        planet=11;
//        result=userService.registerAccount(userAccount,password,checkPassword,planet);
//        Integer i=1;
//        Assert.assertEquals(i,result);

    }

    @Test
    public void BufferStream() throws IOException {
        InputStream is = new FileInputStream("C:\\Users\\lh123\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\1.webp");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
        int i;
        byte[] bytes = new byte[1024];
        OutputStream os = new FileOutputStream("1.webp");
        BufferedOutputStream bos = new BufferedOutputStream(os);
        while ((i = bufferedInputStream.read(bytes)) != -1) {
            bos.write(bytes, 0, i);
        }
        bos.flush();
        bufferedInputStream.close();
        bos.close();
    }

    @Test
    public void encryPassword2() {
        String encryPassword = userService.encryPassword("123456");
        System.out.println(encryPassword);
    }
}