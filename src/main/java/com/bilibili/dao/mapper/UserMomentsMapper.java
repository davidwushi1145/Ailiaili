package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.UserMoments;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_user_moments(用户动态表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.UserMoments
*/
@Mapper
public interface UserMomentsMapper extends BaseMapper<UserMoments> {

}




