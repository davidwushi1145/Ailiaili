package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_following_group(用户关注分组表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.FollowingGroup
*/
@Mapper
public interface FollowingGroupMapper extends BaseMapper<FollowingGroup> {

}




