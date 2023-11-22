package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.auth.UserRole;

import java.util.List;

/**
* @author 
* @description 针对表【t_user_role(用户角色关联表)】的数据库操作Service
*/
public interface UserRoleService extends IService<UserRole> {

    List<UserRole> getUserRoleByUserId(Long userId);
}
