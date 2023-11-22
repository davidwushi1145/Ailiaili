package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.FollowingGroup;

import java.util.List;

/**
* @author 
* @description 针对表【t_following_group(用户关注分组表)】的数据库操作Service
*/
public interface FollowingGroupService extends IService<FollowingGroup> {


    //根据type获得关注信息
    FollowingGroup getByType(String type);

    //根据id获取关注信息
    FollowingGroup getById(String id);

    List<FollowingGroup> getByUserId(Long userId);
}
