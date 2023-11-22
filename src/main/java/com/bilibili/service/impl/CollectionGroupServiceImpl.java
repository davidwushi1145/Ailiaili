package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.CollectionGroup;
import com.bilibili.dao.mapper.CollectionGroupMapper;
import com.bilibili.service.CollectionGroupService;
import org.springframework.stereotype.Service;

/**
* @author 
* @description 针对表【t_collection_group(用户收藏分组表)】的数据库操作Service实现
*/
@Service
public class CollectionGroupServiceImpl extends ServiceImpl<CollectionGroupMapper, CollectionGroup>
    implements CollectionGroupService {

}




