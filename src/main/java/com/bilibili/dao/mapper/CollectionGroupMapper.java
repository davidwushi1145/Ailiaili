package com.bilibili.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.CollectionGroup;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_collection_group(用户收藏分组表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.CollectionGroup
*/
@Mapper
public interface CollectionGroupMapper extends BaseMapper<CollectionGroup> {

}




