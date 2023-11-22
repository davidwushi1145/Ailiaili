package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.UserCoin;

/**
* @author 
* @description 针对表【t_user_coin(用户硬币数量表)】的数据库操作Service
*/
public interface UserCoinService extends IService<UserCoin> {

    Long getUserCoinAmount(Long userId);

    void updateUserCoinAmount(Long userId, long amount);
}
