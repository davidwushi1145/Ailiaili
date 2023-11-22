package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.UserPreference;
import com.bilibili.dao.domain.VideoOperation;
import com.bilibili.dao.mapper.VideoOperationMapper;
import com.bilibili.service.VideoOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 
* @description 针对表【t_video_operation(用户操作表)】的数据库操作Service实现
*/
@Service
public class VideoOperationServiceImpl extends ServiceImpl<VideoOperationMapper, VideoOperation>
    implements VideoOperationService {

    @Autowired
    private VideoOperationMapper videoOperationMapper;

    @Override
    public List<UserPreference> getAllUserPreference() {
        return  videoOperationMapper.getAllUserPreference();
    }
}




