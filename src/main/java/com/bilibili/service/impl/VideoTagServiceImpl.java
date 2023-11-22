package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.VideoTag;
import com.bilibili.dao.mapper.VideoTagMapper;
import com.bilibili.service.VideoTagService;
import org.springframework.stereotype.Service;

/**
* @author 
* @description 针对表【t_video_tag(视频标签关联表)】的数据库操作Service实现
*/
@Service
public class VideoTagServiceImpl extends ServiceImpl<VideoTagMapper, VideoTag>
    implements VideoTagService {

}




