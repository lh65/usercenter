package com.lh.usercenter.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID（主键)
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户账号
     */
    @TableField(value = "userAccount")
    private String userAccount;

    /**
     * 头像
     */
    @TableField(value = "userAvatarUrl")
    private String userAvatarUrl;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 用户密码
     */
    @TableField(value = "userPassword")
    private String userPassword;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private Integer phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户登录状态：0---用户正常登录 1---用户离线
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 用户创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 用户更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 逻辑删除：0--未被删除；1---已被删除
     */
    @TableField(value = "isDelete")
    private Integer isDelete;

    /**
     * 0---普通用户；1----管理员用户
     */
    @TableField(value = "userRole")
    private Integer userRole;

    /**
     * 星球编号
     */
    @TableField(value = "planetCode")
    private Integer planetCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}