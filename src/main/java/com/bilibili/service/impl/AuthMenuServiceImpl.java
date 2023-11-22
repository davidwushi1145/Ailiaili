package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.auth.AuthMenu;
import com.bilibili.dao.mapper.AuthMenuMapper;
import com.bilibili.service.AuthMenuService;
import org.springframework.stereotype.Service;

/**
* @author 
* @description 针对表【t_auth_menu(权限控制--页面访问表)】的数据库操作Service实现
*/
@Service
public class AuthMenuServiceImpl extends ServiceImpl<AuthMenuMapper, AuthMenu>
    implements AuthMenuService {

}




