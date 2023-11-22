package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.VideoView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
* @author 
* @description 针对表【t_video_view(视频观看记录表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.VideoView
*/
@Mapper
public interface VideoViewMapper extends BaseMapper<VideoView> {

    VideoView getVideoView(Map<String, Object> params);
}




