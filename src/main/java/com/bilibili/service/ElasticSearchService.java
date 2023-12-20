package com.bilibili.service;

import com.bilibili.dao.domain.TAdPerformance;
import com.bilibili.dao.domain.UserInfo;
import com.bilibili.dao.domain.Video;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticSearchService {
  void addVideo(Video video);

  Video getVideo(String keyword);

  void updateVideo(Video video);

  void deleteAllVideos();

  void addUserInfo(UserInfo userInfo);

  void deleteAllUserInfos();

  void updateUserInfo(UserInfo userInfo);

  void addAdPerformance(TAdPerformance adPerformance);

  void deleteAllAdPerformances();

  void updateAdPerformance(TAdPerformance adPerformance);

  TAdPerformance getAdPerformance(String keyword);

  List<Map<String, Object>> getContents(String keyword, Integer pageNumber,
                                        Integer pageSize) throws IOException;
}
