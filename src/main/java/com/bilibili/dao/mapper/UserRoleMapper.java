package com.bilibili.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 
* @description 针对表【t_user_role(用户角色关联表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.auth.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> getUserRoleByUserId(Long userId);
}




