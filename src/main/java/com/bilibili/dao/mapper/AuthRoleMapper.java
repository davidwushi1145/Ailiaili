package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_auth_role(权限控制--角色表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.auth.AuthRole
*/
@Mapper
public interface AuthRoleMapper extends BaseMapper<AuthRole> {

}




