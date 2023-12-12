package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.TAdvertisement;
import com.bilibili.dao.mapper.TAdvertisementMapper;
import com.bilibili.service.TAdvertisementService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @description 针对表【t_advertisement(广告表)】的数据库操作Service实现
 * @createDate 2023-12-10 16:14:55
 */
@Service
public class TAdvertisementServiceImpl
    extends ServiceImpl<TAdvertisementMapper, TAdvertisement>
    implements TAdvertisementService {

  @Autowired private TAdvertisementMapper tAdvertisementMapper;

  @Override
  public TAdvertisement getByAdSpaceId(Long adSpaceId) {
    QueryWrapper<TAdvertisement> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("ad_space_id", adSpaceId);
    return tAdvertisementMapper.selectOne(queryWrapper);
  }
}
