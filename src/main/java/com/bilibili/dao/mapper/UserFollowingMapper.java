package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_user_following(用户关注表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.UserFollowing
*/
@Mapper
public interface UserFollowingMapper extends BaseMapper<UserFollowing> {

}




