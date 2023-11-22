package com.bilibili.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_user_info(用户基本信息表)】的数据库操作Mapper
* @Entity generator.domain.UserInfo
*/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




