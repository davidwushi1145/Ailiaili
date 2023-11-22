package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.PageResult;
import com.bilibili.dao.domain.VideoComment;

/**
* @author 
* @description 针对表【t_video_comment(视频评论表)】的数据库操作Service
*/
public interface VideoCommentService extends IService<VideoComment> {

    void addVideoComments(VideoComment videoComment,Long userId);

    PageResult<VideoComment> pageListVideoComments(Long size, Integer number, Long videoId);
}
