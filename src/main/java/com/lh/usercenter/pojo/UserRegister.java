package com.lh.usercenter.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegister implements Serializable {

    private static final long serialVersionUID =3L;
    String userAccount;
    String userPassword;
    String checkPassword;
    Integer planetCode;
}
