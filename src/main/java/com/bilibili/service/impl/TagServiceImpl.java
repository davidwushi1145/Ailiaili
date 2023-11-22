package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.Tag;
import com.bilibili.dao.mapper.TagMapper;
import com.bilibili.service.TagService;
import org.springframework.stereotype.Service;

/**
* @author 
* @description 针对表【t_tag(标签表)】的数据库操作Service实现
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {

}




