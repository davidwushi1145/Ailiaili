package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.auth.AuthMenu;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_auth_menu(权限控制--页面访问表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.auth.AuthMenu
*/
@Mapper
public interface AuthMenuMapper extends BaseMapper<AuthMenu> {

}




