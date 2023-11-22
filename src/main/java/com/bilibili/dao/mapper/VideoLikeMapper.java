package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.VideoLike;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_video_like(视频点赞表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.VideoLike
*/
@Mapper
public interface VideoLikeMapper extends BaseMapper<VideoLike> {

}




