package com.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.VideoCollection;

import java.util.Map;

/**
* @author 
* @description 针对表【t_video_collection(视频收藏表)】的数据库操作Service
*/
public interface VideoCollectionService extends IService<VideoCollection> {

    void addVideoCollection(VideoCollection videoCollection, Long userId);

    void deleteVideoCollections(Long videoId, Long userId);

    Map<String, Object> getVideoCollections(Long videoId, Long userId);
}
