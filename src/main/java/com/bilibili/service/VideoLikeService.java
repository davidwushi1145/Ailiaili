package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.VideoLike;

import java.util.Map;

/**
* @author 
* @description 针对表【t_video_like(视频点赞表)】的数据库操作Service
*/
public interface VideoLikeService extends IService<VideoLike> {

    void addVideoLike(Long videoId, Long userId);

    void deleteVideoLike(Long videoId, Long userId);

    Map<String, Object> getVideoLike(Long videoId, Long userId);
}
