package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.Video;
import com.bilibili.dao.domain.VideoLike;
import com.bilibili.dao.domain.VideoOperation;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.dao.mapper.VideoLikeMapper;
import com.bilibili.service.VideoLikeService;
import com.bilibili.service.VideoOperationService;
import com.bilibili.service.VideoService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @description 针对表【t_video_like(视频点赞表)】的数据库操作Service实现
 */
@Service
public class VideoLikeServiceImpl
    extends ServiceImpl<VideoLikeMapper, VideoLike>
    implements VideoLikeService {

  @Autowired private VideoService videoService;
  @Autowired private VideoOperationService videoOperationService;

  @Override
  public void addVideoLike(Long videoId, Long userId) {
    Video video = videoService.getById(videoId);
    if (video == null) {
      throw new ConditionException("非法视频！");
    }
    QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("video_id", videoId);
    VideoLike one = this.getOne(queryWrapper);
    if (one != null) {
      throw new ConditionException("已经点过赞！");
    }
    VideoLike videoLike = new VideoLike();
    videoLike.setUserId(userId);
    videoLike.setVideoId(videoId);
    this.save(videoLike);
    // Increase the like count
    video.setLikes(video.getLikes() + 1);
    VideoOperation videoOperation = new VideoOperation();
    videoOperation.setUserId(userId);
    videoOperation.setVideoId(videoId);
    videoOperation.setOperationType("0");
    // Update the video
    videoService.updateById(video);
    videoOperationService.save(videoOperation);
  }

  @Override
  public void deleteVideoLike(Long videoId, Long userId) {
    QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("video_id", videoId);
    this.remove(queryWrapper);
    // Get the video
    Video video = videoService.getById(videoId);
    // Decrease the like count
    video.setLikes(video.getLikes() - 1);
    // Update the video
    videoService.updateById(video);
  }

  @Override
  public Map<String, Object> getVideoLike(Long videoId, Long userId) {
    // Get the video
    Video video = videoService.getById(videoId);

    // Get the like count from the video
    int count = video.getLikes();

    // Check if the user has liked the video
    QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("video_id", videoId);
    queryWrapper.eq("user_id", userId);
    VideoLike videoLike = this.getOne(queryWrapper);
    boolean like = videoLike != null;

    // Return the result
    Map<String, Object> map = new HashMap<>();
    map.put("count", count);
    map.put("like", like);
    return map;
  }
}
