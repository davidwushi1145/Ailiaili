package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.auth.AuthRoleElementOperation;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_auth_role_element_operation(权限控制--角色与元素操作关联表)】的数据库操作Service
*/
public interface AuthRoleElementOperationService extends IService<AuthRoleElementOperation> {

    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet);
}
