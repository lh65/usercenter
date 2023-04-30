create table user
(
    id            int      default 1    not null comment '用户ID（主键)'
        primary key,
    username      varchar(255)          null comment '用户名',
    userAccount   varchar(255)          null comment '用户账号',
    userAvatarUrl varchar(255)          null comment '头像',
    gender        char(2)  default '男' null comment '性别',
    userPassword  varchar(255)          null comment '用户密码',
    phone         int(255)              null comment '电话',
    email         varchar(255)          null comment '邮箱',
    status        int(2)                null comment '用户登录状态：0---用户正常登录 1---用户离线',
    createTime    date                  null comment '用户创建时间',
    updateTime    date                  null comment '用户更新时间',
    isDelete      int(255) default 0    null comment '逻辑删除：0--未被删除；1---已被删除',
    userRole      int(255) default 0    null comment '0---普通用户；1----管理员用户',
    planetCode    int(255)              null comment '星球编号'
);
