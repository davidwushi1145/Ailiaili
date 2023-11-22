package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.auth.AuthRoleElementOperation;
import com.bilibili.dao.mapper.AuthRoleElementOperationMapper;
import com.bilibili.service.AuthRoleElementOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_auth_role_element_operation(权限控制--角色与元素操作关联表)】的数据库操作Service实现
*/
@Service
public class AuthRoleElementOperationServiceImpl extends ServiceImpl<AuthRoleElementOperationMapper, AuthRoleElementOperation>
    implements AuthRoleElementOperationService {

    @Autowired
    private AuthRoleElementOperationMapper authRoleElementOperationMapper;
    @Override
    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return  authRoleElementOperationMapper.getRoleElementOperationsByRoleIds(roleIdSet);
    }
}




