package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.Video;
import com.bilibili.dao.domain.VideoView;
import com.bilibili.dao.mapper.VideoViewMapper;
import com.bilibili.service.VideoService;
import com.bilibili.service.VideoViewService;
import com.bilibili.service.util.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @description 针对表【t_video_view(视频观看记录表)】的数据库操作Service实现
 */
@Service
public class VideoViewServiceImpl
    extends ServiceImpl<VideoViewMapper, VideoView>
    implements VideoViewService {

  @Autowired private VideoViewMapper videoViewMapper;
  @Autowired private VideoService videoService;

  @Override
  public void addVideoView(VideoView videoView, HttpServletRequest request) {
    Long videoId = videoView.getVideoId();
    Long userId = videoView.getUserId();
    // 生成clientId
    String agent = request.getHeader("User-Agent");
    UserAgent userAgent = UserAgent.parseUserAgentString(agent);
    int agentId = userAgent.getId();
    String ip = IpUtil.getIP(request);
    Map<String, Object> params = new HashMap<>();
    if (userId != null) {
      params.put("userId", userId);
    } else {
      params.put("ip", ip);
      params.put("clientId", agentId);
    }
    params.put("videoId", videoId);
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    params.put("today", sdf.format(now));
    // 添加观看记录
    VideoView videoView1 = videoViewMapper.getVideoView(params);
    if (videoView1 == null) {
      videoView1 = new VideoView();
      videoView1.setIp(ip);
      videoView1.setClientId(String.valueOf(agentId));
      videoView1.setUserId(userId);
      videoView1.setVideoId(videoId);
      this.save(videoView1);
    }

    // Get the video
    Video video = videoService.getById(videoId);

    // Increase the view count
    video.setViews(video.getViews() + 1);

    // Update the video
    videoService.updateById(video);
  }

  @Override
  public Integer getVideoViewCounts(Long videoId) {
    // Get the video
    Video video = videoService.getById(videoId);

    // Get the view count from the video

    return video.getViews();
  }
}
