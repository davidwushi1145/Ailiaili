package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.auth.AuthRoleMenu;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_auth_role_menu(权限控制--角色页面菜单关联表)】的数据库操作Service
*/
public interface AuthRoleMenuService extends IService<AuthRoleMenu> {

    List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIdSet);
}
