package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.UserPreference;
import com.bilibili.dao.domain.VideoOperation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 
* @description 针对表【t_video_operation(用户操作表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.VideoOperation
*/
@Mapper
public interface VideoOperationMapper extends BaseMapper<VideoOperation> {

    List<UserPreference> getAllUserPreference();
}




