package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.RefreshToken;
import com.bilibili.dao.mapper.RefreshTokenMapper;
import com.bilibili.service.RefreshTokenService;
import org.springframework.stereotype.Service;

/**
* @author 
* @description 针对表【t_refresh_token(刷新令牌记录表)】的数据库操作Service实现
*/
@Service
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken>
    implements RefreshTokenService {

}




