package com.bilibili.service.util;

import com.alibaba.fastjson.JSONArray;
import com.bilibili.dao.domain.Danmu;
import com.bilibili.dao.domain.Video;
import com.bilibili.service.DanmuService;
import com.bilibili.service.VideoService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
  @Autowired private DanmuService danmuService;

  @Autowired private VideoService videoService;

  @Autowired private RedisTemplate<String, String> redisTemplate;

  // 一分钟执行一次 将存在redis中的弹幕持久化到数据库中
  @Scheduled(fixedRate = 60000)
  public void executeTask() {
    List<String> list = this.getAllVideoIds();
    for (String videoId : list) {
      String key = "new-danmu-video-" + videoId;
      String value = redisTemplate.opsForValue().get(key);
      if (StringUtils.isNotBlank(value)) {
        // 解析value为Danmu列表
        List<Danmu> danmuList = JSONArray.parseArray(value, Danmu.class);
        for (Danmu danmu : danmuList) {
          saveDanmuToDatabase(danmu);
        }
      }
      redisTemplate.delete(key);
      redisTemplate.delete("now-danmu-video-" + videoId);
    }
  }

  private List<String> getAllVideoIds() {
    List<String> list = new ArrayList<>();
    list = videoService.getAllVideoId();
    return list;
  }

  private void saveDanmuToDatabase(Danmu danmu) { danmuService.save(danmu); }
}
