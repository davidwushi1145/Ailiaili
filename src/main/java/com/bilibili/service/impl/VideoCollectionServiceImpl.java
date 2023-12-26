package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.Video;
import com.bilibili.dao.domain.VideoCollection;
import com.bilibili.dao.domain.VideoOperation;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.dao.mapper.VideoCollectionMapper;
import com.bilibili.service.VideoCollectionService;
import com.bilibili.service.VideoOperationService;
import com.bilibili.service.VideoService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @description 针对表【t_video_collection(视频收藏表)】的数据库操作Service实现
 */
@Service
public class VideoCollectionServiceImpl
    extends ServiceImpl<VideoCollectionMapper, VideoCollection>
    implements VideoCollectionService {

  @Autowired private VideoService videoService;

  @Autowired private VideoOperationService videoOperationService;

  @Override
  @Transactional
  public void addVideoCollection(VideoCollection videoCollection, Long userId) {
    if (videoCollection == null) {
      throw new ConditionException("参数异常！");
    }
    Long videoId = videoCollection.getVideoId();
    Long groupId = videoCollection.getGroupId();
    if (videoId == null || groupId == null) {
      throw new ConditionException("参数异常！");
    }
    Video video = videoService.getById(videoId);
    if (video == null) {
      throw new ConditionException("非法视频！");
    }
    // 删除原有视频收藏
    QueryWrapper<VideoCollection> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("video_id", videoId);
    queryWrapper.eq("group_id", groupId);
    this.remove(queryWrapper);
    // 添加新的视频收藏
    videoCollection.setUserId(userId);
    this.save(videoCollection);
    // Increase the collection count
    video.setCollections(video.getCollections() + 1);

    // Update the video
    videoService.updateById(video);
    VideoOperation videoOperation = new VideoOperation();
    videoOperation.setUserId(userId);
    videoOperation.setVideoId(videoId);
    videoOperation.setOperationType("1");
    videoOperationService.save(videoOperation);
  }

  @Override
  public void deleteVideoCollections(Long videoId, Long userId) {
    QueryWrapper<VideoCollection> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("video_id", videoId);
    queryWrapper.eq("user_id", userId);
    this.remove(queryWrapper);
    // Get the video
    Video video = videoService.getById(videoId);

    // Decrease the collection count
    video.setCollections(video.getCollections() - 1);

    // Update the video
    videoService.updateById(video);
  }

  @Override
  public Map<String, Object> getVideoCollections(Long videoId, Long userId) {
    // Get the video
    Video video = videoService.getById(videoId);

    // Get the collection count from the video
    int count = video.getCollections();

    // Check if the user has collected the video
    QueryWrapper<VideoCollection> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("video_id", videoId);
    queryWrapper.eq("user_id", userId);
    VideoCollection videoCollection = this.getOne(queryWrapper);
    boolean collected = videoCollection != null;

    // Return the result
    Map<String, Object> map = new HashMap<>();
    map.put("count", count);
    map.put("collected", collected);
    return map;
  }
}
