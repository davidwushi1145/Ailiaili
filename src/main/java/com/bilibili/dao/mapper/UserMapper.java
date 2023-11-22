package com.bilibili.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_user(用户表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




