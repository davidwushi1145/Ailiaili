package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.UserMoments;

import java.util.List;

/**
* @author 
* @description 针对表【t_user_moments(用户动态表)】的数据库操作Service
*/
public interface UserMomentsService extends IService<UserMoments> {

    void addUserMoments(UserMoments userMoments) throws Exception;

    List<UserMoments> getUserSubscribedMoments(Long userId);
}
