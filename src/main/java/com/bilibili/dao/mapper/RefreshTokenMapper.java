package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_refresh_token(刷新令牌记录表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.RefreshToken
*/
@Mapper
public interface RefreshTokenMapper extends BaseMapper<RefreshToken> {

}




