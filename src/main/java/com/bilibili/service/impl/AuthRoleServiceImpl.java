package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.auth.AuthRole;
import com.bilibili.dao.domain.auth.AuthRoleElementOperation;
import com.bilibili.dao.domain.auth.AuthRoleMenu;
import com.bilibili.dao.mapper.AuthRoleMapper;
import com.bilibili.service.AuthRoleElementOperationService;
import com.bilibili.service.AuthRoleMenuService;
import com.bilibili.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_auth_role(权限控制--角色表)】的数据库操作Service实现
*/
@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole>
    implements AuthRoleService {

    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;

    @Autowired
    private AuthRoleMenuService authRoleMenuService;
    @Override
    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return  authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIdSet);
    }

    @Override
    public List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIdSet) {
        return  authRoleMenuService.getRoleMenusByRoleIds(roleIdSet);
    }

    @Override
    public AuthRole getRoleByCode(String code) {
        QueryWrapper<AuthRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",code);
        AuthRole authRole = this.getOne(queryWrapper);
        return authRole;
    }
}




