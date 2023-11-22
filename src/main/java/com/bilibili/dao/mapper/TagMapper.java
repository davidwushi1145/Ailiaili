package com.bilibili.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_tag(标签表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




