package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.VideoComment;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_video_comment(视频评论表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.VideoComment
*/
@Mapper
public interface VideoCommentMapper extends BaseMapper<VideoComment> {

}




