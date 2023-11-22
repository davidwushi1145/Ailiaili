package com.bilibili.dao.domain.auth;

import lombok.Data;

import java.util.List;

@Data
public class UserAuthorities {

    List<AuthRoleElementOperation> roleElementOperationList;

    List<AuthRoleMenu> roleMenuList;

}
