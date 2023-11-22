package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.UserPreference;
import com.bilibili.dao.domain.VideoOperation;

import java.util.List;

/**
* @author 
* @description 针对表【t_video_operation(用户操作表)】的数据库操作Service
*/
public interface VideoOperationService extends IService<VideoOperation> {

    List<UserPreference> getAllUserPreference();

}
