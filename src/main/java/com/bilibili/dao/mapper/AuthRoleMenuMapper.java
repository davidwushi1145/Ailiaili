package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_auth_role_menu(权限控制--角色页面菜单关联表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.auth.AuthRoleMenu
*/
@Mapper
public interface AuthRoleMenuMapper extends BaseMapper<AuthRoleMenu> {

    List<AuthRoleMenu> getRoleMenusByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);
}




