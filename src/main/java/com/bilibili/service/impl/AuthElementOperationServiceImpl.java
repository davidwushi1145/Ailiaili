package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.auth.AuthElementOperation;
import com.bilibili.dao.mapper.AuthElementOperationMapper;
import com.bilibili.service.AuthElementOperationService;
import org.springframework.stereotype.Service;

/**
* @author 
* @description 针对表【t_auth_element_operation(权限控制--页面元素操作表)】的数据库操作Service实现
*/
@Service
public class AuthElementOperationServiceImpl extends ServiceImpl<AuthElementOperationMapper, AuthElementOperation>
    implements AuthElementOperationService {

}




