package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.UserInfo;

import java.util.List;
import java.util.Set;

/**
* @author 
* @description 针对表【t_user_info(用户基本信息表)】的数据库操作Service
*/
public interface UserInfoService extends IService<UserInfo> {

    List<UserInfo> getListByUserIds(Set<Long> ids);
}
