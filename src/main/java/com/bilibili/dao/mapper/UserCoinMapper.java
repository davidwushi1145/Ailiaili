package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.UserCoin;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_user_coin(用户硬币数量表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.UserCoin
*/
@Mapper
public interface UserCoinMapper extends BaseMapper<UserCoin> {

}




