package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.auth.AuthRole;
import com.bilibili.dao.domain.auth.AuthRoleElementOperation;
import com.bilibili.dao.domain.auth.AuthRoleMenu;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_auth_role(权限控制--角色表)】的数据库操作Service
*/
public interface AuthRoleService extends IService<AuthRole> {

    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet);

    List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIdSet);

    AuthRole getRoleByCode(String code);
}
