package com.lh.usercenter.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLogineReuquest implements Serializable {

    //必须加上序列化接口
    private static final long serialVersionUID = 2L;
    String userAccount;
    String userPassword;


}
