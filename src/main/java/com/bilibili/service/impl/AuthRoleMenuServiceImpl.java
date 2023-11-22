package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.auth.AuthRoleMenu;
import com.bilibili.dao.mapper.AuthRoleMenuMapper;
import com.bilibili.service.AuthRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_auth_role_menu(权限控制--角色页面菜单关联表)】的数据库操作Service实现
*/
@Service
public class AuthRoleMenuServiceImpl extends ServiceImpl<AuthRoleMenuMapper, AuthRoleMenu>
    implements AuthRoleMenuService {

    @Autowired
    private AuthRoleMenuMapper authRoleMenuMapper;
    @Override
    public List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIdSet) {
        return authRoleMenuMapper.getRoleMenusByRoleIds(roleIdSet);
    }
}




