package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.VideoView;

import javax.servlet.http.HttpServletRequest;

/**
* @author 
* @description 针对表【t_video_view(视频观看记录表)】的数据库操作Service
*/
public interface VideoViewService extends IService<VideoView> {

    void addVideoView(VideoView videoView, HttpServletRequest request);

    Integer getVideoViewCounts(Long videoId);
}
