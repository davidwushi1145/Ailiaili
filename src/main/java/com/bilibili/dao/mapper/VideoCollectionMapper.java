package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.VideoCollection;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_video_collection(视频收藏表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.VideoCollection
*/
@Mapper
public interface VideoCollectionMapper extends BaseMapper<VideoCollection> {

}




