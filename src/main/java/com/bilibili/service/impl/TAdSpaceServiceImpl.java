package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.TAdSpace;
import com.bilibili.dao.mapper.TAdSpaceMapper;
import com.bilibili.service.TAdSpaceService;
import org.springframework.stereotype.Service;

/**
 * @author
 * @description 针对表【t_ad_space(广告位表)】的数据库操作Service实现
 * @createDate 2023-12-10 16:14:55
 */
@Service
public class TAdSpaceServiceImpl
    extends ServiceImpl<TAdSpaceMapper, TAdSpace> implements TAdSpaceService {}
