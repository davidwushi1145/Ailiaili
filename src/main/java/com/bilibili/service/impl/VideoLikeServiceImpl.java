package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.Video;
import com.bilibili.dao.domain.VideoLike;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.dao.mapper.VideoLikeMapper;
import com.bilibili.service.VideoLikeService;
import com.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 
* @description 针对表【t_video_like(视频点赞表)】的数据库操作Service实现
*/
@Service
public class VideoLikeServiceImpl extends ServiceImpl<VideoLikeMapper, VideoLike>
    implements VideoLikeService {

    @Autowired
    private VideoService videoService;

    @Override
    public void addVideoLike(Long videoId, Long userId) {
        Video video = videoService.getById(videoId);
        if(video==null){
            throw new ConditionException("非法视频！");
        }
        QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("video_id",videoId);
        VideoLike one = this.getOne(queryWrapper);
        if(one !=null){
            throw new ConditionException("已经点过赞！");
        }
        VideoLike videoLike = new VideoLike();
        videoLike.setUserId(userId);
        videoLike.setVideoId(videoId);
        this.save(videoLike);
    }

    @Override
    public void deleteVideoLike(Long videoId, Long userId) {
        QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("video_id",videoId);
        this.remove(queryWrapper);
    }

    @Override
    public Map<String, Object> getVideoLike(Long videoId, Long userId) {
        QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id",videoId);
        int count = (int) this.count(queryWrapper);
        queryWrapper.eq("user_id",userId);
        VideoLike videoLike = this.getOne(queryWrapper);
        //判断用户是否关注过
        boolean like = videoLike !=null;
        Map<String,Object> map = new HashMap<>();
        map.put("count",count);
        map.put("like",like);
        return map;
    }
}




