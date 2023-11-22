package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.VideoTag;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_video_tag(视频标签关联表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.VideoTag
*/
@Mapper
public interface VideoTagMapper extends BaseMapper<VideoTag> {

}




