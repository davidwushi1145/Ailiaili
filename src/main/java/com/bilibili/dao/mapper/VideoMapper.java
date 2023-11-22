package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.Video;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_video(视频投稿记录表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.Video
*/
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}




